/*
Description: On the last screen double back exits from the app
*/

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
		var keyPressedTwoTimeInARow = (utils.getCurrentTimeMs() - this.prevTime) <= this.timeBetweenPress;
        var dialogNearlyShown = (utils.getCurrentTimeMs() - this.prevShownTime) <= this.timeDialogIsSpotted;
        if (keyPressedTwoTimeInARow && dialogNearlyShown) {
			this.doExit();
		}

        this.prevShownTime = this.isDialogShown() ? utils.getCurrentTimeMs() : this.prevShownTime;
        this.prevTime = utils.getCurrentTimeMs();
    };

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

    this.isDialogShown = function() {
        var dialog = document.querySelector(this.dialogSel);
        var isShown = utils.hasClass(dialog, this.dialogShownClass);
        console.log('DoubleBackAddon::isDialogShown... ' + isShown);
        if (isShown) {
            this.addCancelListener();
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