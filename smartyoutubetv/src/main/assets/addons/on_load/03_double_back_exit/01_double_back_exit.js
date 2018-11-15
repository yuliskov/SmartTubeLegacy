/*
Description: On the last screen double back exits from the app
*/

console.log("Scripts::Running script double_back_exit.js");

function DoubleBackAddon() {
	this.prevTime = 0;
    this.timeBetweenPress = 500;
    this.timeDialogIsSpotted = 10000;
    this.prevShownTime = 0;
    this.rootListenerAdded = false;
    this.cancelListenerAdded = false;
    this.okListenerAdded = false;

    this.run = function() {
        this.addRootListener();
    };

    this.addRootListener = function() {
        if (this.rootListenerAdded) {
            return;
        }

        var $this = this;
        EventUtils.addListener(
            YouTubeConstants.APP_EVENTS_RECEIVER_SELECTOR,
            DefaultEvents.KEY_UP,
            function(event) {
                console.log('DoubleBackAddon::root:' + DefaultEvents.KEY_UP + '...');
                $this.doubleCheckAndExit(event.keyCode);
        });

        this.rootListenerAdded = true;
    };

    this.doubleCheckAndExit = function(keyCode) {
        if (keyCode === DefaultKeys.ESC) {
            this.addOKListener();
            this.addCancelListener();
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

    this.addOKListener = function() {
        if (this.okListenerAdded) {
            return;
        }

        var container = Utils.$(YouTubeConstants.DIALOG_OK_BUTTON_SELECTOR);
        if (!container)
            return;

        var $this = this;
        EventUtils.addListener(container, DefaultEvents.KEY_DOWN, function(e) {
            if (e.keyCode != DefaultKeys.ENTER)
                return;
            console.log('DoubleBackAddon::dialog:ok pressed... ');
            $this.doExit();
        });

        this.okListenerAdded = true;
    };

	// if we chosen to display the dialog
    // then we should know: 'keyup' event is swallowed by dialog
    // so to fix that we need to attach an listener to the 'cancel' button
    this.addCancelListener = function() {
        if (this.cancelListenerAdded) {
            return;
        }

        var container = Utils.$(YouTubeConstants.DIALOG_CANCEL_BUTTON_SELECTOR);
        if (!container)
            return;
        var $this = this;
        EventUtils.addListener(container, DefaultEvents.KEY_UP, function(event) {
            console.log('DoubleBackAddon::dialog:keyup... ');
            $this.doubleCheckAndExit(event.keyCode);
        });

        this.cancelListenerAdded = true;
    };

    // don't show dialog completely
    // no need to setup any addition listener
    this.doClickOnCancel = function() {
        var cancel = Utils.$(YouTubeConstants.DIALOG_CANCEL_BUTTON_SELECTOR);
        if (Utils.hasClass(cancel, YouTubeConstants.ELEMENT_FOCUSED_CLASS)) {
            EventUtils.triggerEnter(YouTubeConstants.DIALOG_CANCEL_BUTTON_SELECTOR);
        }
    };

    this.isDialogShown = function() {
        var dialog = Utils.$(YouTubeConstants.DIALOG_WINDOW_SELECTOR);
        var isShown = Utils.hasClass(dialog, YouTubeConstants.ELEMENT_FOCUSED_CLASS);
        console.log('DoubleBackAddon::isDialogShown... ' + isShown);
        if (isShown) {
            // github issue: don't hide exit dialog but allow user to exit through double back
            // this.doClickOnCancel(); // hide dialog immediately
            DeviceUtils.showExitMsg(); // display toast msg
        }
        return isShown;
    };

    this.doExit = function() {
        console.log('exiting from the app');
        DeviceUtils.closeApp();
    };
}

new DoubleBackAddon().run();