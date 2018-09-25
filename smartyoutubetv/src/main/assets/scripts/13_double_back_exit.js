/*
Description: On the last screen double back exits from the app
*/

console.log("Scripts::Running script double_back_exit.js");

function DoubleBackAddon() {
	this.keysContainerSelector = '#leanback'; // div that receives keys events
	this.cancelBtnSel = '#dialog-cancel-button';
	this.dialogSel = '#dialog-stage';
    this.dialogShownClass = 'focused';
	this.prevTime = 0;
    this.timeBetweenPress = 500;
    this.timeDialogIsSpotted = 10000;
    this.prevShownTime = 0;
    this.rootListenerAdded = false;
    this.cancelListenerAdded = false;

    this.run = function() {
        this.addRootListener();
    };

    this.addRootListener = function() {
        if (this.rootListenerAdded) {
            return;
        }

        var container = document.querySelector(this.keysContainerSelector);
        var $this = this;
        container.addEventListener('keyup', function(event) {
            console.log('DoubleBackAddon::root:keyup... ');
            $this.doubleCheckAndExit(event.keyCode);
        });
        this.rootListenerAdded = true;
    };

	this.doubleCheckAndExit = function(keyCode) {
        var esc = 27;
        if (keyCode === esc) {
            this.detectDoublePress();
        }
    };

    // detect double press and exit
	this.detectDoublePress = function() {
		// double back within two seconds
		var keyPressedTwoTimeInARow = (Utils.getCurrentTimeMs() - this.prevTime) <= this.timeBetweenPress;
        var dialogNearlyShown = (Utils.getCurrentTimeMs() - this.prevShownTime) <= this.timeDialogIsSpotted;
        if (keyPressedTwoTimeInARow && dialogNearlyShown) {
			this.doExit();
		}

        this.prevShownTime = this.isDialogShown() ? Utils.getCurrentTimeMs() : this.prevShownTime;
        this.prevTime = Utils.getCurrentTimeMs();
    };

	// if we chosen to display the dialog
    // then we should know: 'keyup' event is swallowed by dialog
    // so to fix that we need to attach an listener to the 'cancel' button
    this.addCancelListener = function() {
        if (this.cancelListenerAdded) {
            return;
        }

        var container = document.querySelector(this.cancelBtnSel);
        var $this = this;
        container.addEventListener('keyup', function(event) {
            console.log('DoubleBackAddon::dialog:keyup... ');
            $this.doubleCheckAndExit(event.keyCode);
        });
        this.cancelListenerAdded = true;
    };

    // don't show dialog completely
    // no need to setup any addition listener
    this.doClickOnCancel = function() {
        var cancel = document.querySelector(this.cancelBtnSel);
        if (Utils.hasClass(cancel, this.dialogShownClass)) {
            Utils.triggerEnter(this.cancelBtnSel);
        }
    };

    this.isDialogShown = function() {
        var dialog = document.querySelector(this.dialogSel);
        var isShown = Utils.hasClass(dialog, this.dialogShownClass);
        console.log('DoubleBackAddon::isDialogShown... ' + isShown);
        if (isShown) {
            // this.addCancelListener();
            this.doClickOnCancel();
            window.app && window.app.showExitMsg();
        }
        return isShown;
    };

    this.doExit = function() {
        console.log('exiting from the app');
        window.app && window.app.closeApp();
    };
}

new DoubleBackAddon().run();