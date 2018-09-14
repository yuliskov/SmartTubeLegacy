function Utils() {
	function isSelector(el) {
        return typeof el === 'string' || el instanceof String;
    }

	this.triggerEvent = function(element, type, keyCode) {
        var el = element;
        if (isSelector(element)) {
            el = this.$(element);
        }

        console.log("ExoUtils.triggerEvent: " + element + " " + type + " " + keyCode);

        if (!el) {
            console.warn("ExoUtils.triggerEvent: unable to find " + element);
            return;
        }

        if ('createEvent' in document) {
            // modern browsers, IE9+
            var e = document.createEvent('HTMLEvents');
            e.keyCode = keyCode;
            e.initEvent(type, false, true);
            el.dispatchEvent(e);
        } else {
            // IE 8
            var e = document.createEventObject();
            e.keyCode = keyCode;
            e.eventType = type;
            el.fireEvent('on'+e.eventType, e);
        }
    };

    this.triggerEnter = function(selector) {
        // simulate mouse/enter key press
        this.triggerEvent(selector, 'keyup', 13);
    };

    this.hasClass = function(elem, klass) {
        if (!elem) {
            return null;
        }
        return (" " + elem.className + " ").indexOf(" " + klass + " ") > -1;
    };

    this.$ = function(selector) {
        if (!isSelector(selector))
            return selector;
        return document.querySelectorAll(selector)[0];
    };

    this.getCurrentTimeMs = function() {
    	var d = new Date();
		return d.getTime();
    };
}

var utils = new Utils();

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

var addon = new DoubleBackAddon().run();