/////////// GoogleButton ////////////////

var PlayerActivity = {
    BUTTON_USER_PAGE: "button_user_page",
    BUTTON_LIKE: "button_like",
    BUTTON_DISLIKE: "button_dislike",
    BUTTON_SUBSCRIBE: "button_subscribe"
};

var GoogleConstants = {
    BUTTON_USER_PAGE: ".pivot-channel-tile",
    BUTTON_LIKE: ".icon-like.toggle-button",
    BUTTON_DISLIKE: ".icon-dislike.toggle-button",
    BUTTON_SUBSCRIBE: ".icon-logo-lozenge.toggle-button"
};

function GoogleButton() {
    this.disabledClass = 'disabled';
    this.selectedClass = 'toggle-selected';
    this.optionsBtnSelector = '#transport-more-button';
    this.backBtnSelector = '.back.no-model.legend-item';
    this.bottomBarSelector = '#transport-controls';
    this.bottomBarControllerSelector = '#watch';
}

////////// End GoogleButton //////////////////

/////////// Helpers ////////////////

function Helpers() {
    function isSelector(el) {
        return typeof el === 'string' || el instanceof String;
    }

    this.triggerEvent = function(element, type, keyCode) {
        var el = element;
        if (isSelector(element)) {
            el = this.$(element);
        }

        console.log("Helpers.triggerEvent: " + element + " " + type + " " + keyCode);

        if (!el) {
            console.warn("Helpers.triggerEvent: unable to find " + element);
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
            return false;
        }
        return (" " + elem.className + " ").indexOf(" " + klass + " ") > -1;
    };

    this.isDisabled = function(element) {
        var el = element;
        if (isSelector(element)) {
            el = this.$(element);
        }
        var hasClass = this.hasClass(el, this.disabledClass);
        console.log("Helpers.isDisabled: " + element + " " + hasClass);
        return hasClass;
    };

    this.$ = function(selector) {
        if (!isSelector(selector))
            return selector;
        return document.querySelectorAll(selector)[0];
    };

    this.skipLastHistoryItem = function() {
        console.log('Helpers.skipLastHistoryItem called');
        var $this = this;
        var listener = function(e) {
            window.removeEventListener('popstate', listener);
            console.log('Helpers.skipLastHistoryItem: running on popstate event');
            // e.state is equal to the data-attribute of the last image we clicked
            // window.history.go(-1);
            // window.location.href = "/tv"
            $this.muteVideo(); // fix background sound playing
            $this.triggerEnter($this.backBtnSelector);
        };
        window.addEventListener('popstate', listener);
    };

    this.muteVideo = function() {
        var player = document.getElementsByTagName('video')[0];
        if (!player)
            return;

        // we can't pause video because history will not work
        function muteVideo() {
            var player = document.getElementsByTagName('video')[0];
            console.log('Helpers.muteVideo called');
            player.muted = true;
            player.setAttribute('style', '-webkit-filter:brightness(0)');
        }

        function onLoadData() {
            console.log('Helpers.onLoadData called');
            muteVideo();
            player.removeEventListener('loadeddata', onLoadData);
        }

        // load events: loadedmetadata, loadeddata
        player.addEventListener('loadeddata', onLoadData, false);
        muteVideo();
    };

    // supply selector list
    this.getButtonStates = function() {
        var states = {};
        for(var key in GoogleConstants) {
            var selector = GoogleConstants[key];
            var btn = YouButton.fromSelector(selector);
            var newName = PlayerActivity[key];
            states[newName] = btn.getChecked();
        }

        return states;
    };
}

Helpers.prototype = new GoogleButton();

window.helpers = new Helpers();

// Usage: PressCommandBase.java
// helpers.triggerEvent(helpers.$('%s'), 'keyup', 13);

// Usage: PressCommandBase.java
// helpers.isDisabled(targetButton) && app && app.onGenericBooleanResult(false, %s);

/////////// End Helpers ////////////////

/////////// Player Button ////////////////

// Usage: YouButton.fromSelector('.my-selector').setChecked(true);

function YouButtonInitializer(selector) {
    this.isElementExists = function(selector) {
        var el = helpers.$(selector);
        var len = 0;
        if (el)
            len = el.children.length;
        return len !== 0;
    };
    this.openBottomBar = function() {
        var bottomBar = helpers.$('#watch');
        var isShown = helpers.hasClass(bottomBar, 'transport-showing');

        var upKey = 38;
        var downKey = 40;

        if (!isShown) {
            helpers.triggerEvent(bottomBar, 'keydown', upKey);
        }
    };
    this.closeOptionsBar = function() {
        var bar = helpers.$(this.optionsBtnSelector);
        var isSelected = helpers.hasClass(bar, this.selectedClass);
        if (isSelected)
            helpers.triggerEnter(bar);
    };
    this.openOptionsBar = function() {
        var bar = helpers.$(this.optionsBtnSelector);
        var isSelected = helpers.hasClass(bar, this.selectedClass);
        if (!isSelected)
            helpers.triggerEnter(bar);
    };
    this.openControlsBar = function() {
        this.closeOptionsBar();
    };
    this.initOptionsBar = function() {
        this.openBottomBar();
        this.openOptionsBar();
    };
    this.initControlsBar = function() {
        this.openBottomBar();
        this.openControlsBar();
    };
    this.ensureInitialized = function(){
        this.initOptionsBar();
        var exists = this.isElementExists(selector);
        if (exists) {
            console.log("YouButtonInitializer: element initialized " + selector);
            return;
        }

        this.initControlsBar();
        var exists = this.isElementExists(selector);
        if (exists) {
            console.log("YouButtonInitializer: element initialized " + selector);
            return;
        }

        this.isElementExists(selector) || console.log("YouButtonInitializer: can't find element " + selector);
    };
}

YouButtonInitializer.prototype = new GoogleButton();

function YouButton(selector) {
    this.initializer = new YouButtonInitializer(selector);

    this.doPressOnOptionsBtn = function() {
        helpers.triggerEnter(this.optionsBtnSelector);
    };

    this.findToggle = function() {
        var btn = helpers.$(selector);
        if (!btn) {
            // NOTE: needed button is hidden so open bar first
            // this.doPressOnOptionsBtn();
            btn = helpers.$(selector);
            // NOTE: give a change to other buttons to appear like next/prev
            // this.doPressOnOptionsBtn();
        }

        btn || console.warn("YouButton.findToggle: unable to find " + selector);

        return btn;
    };

    this.getChecked = function() {
        this.initializer.ensureInitialized();
        var isChecked = helpers.hasClass(this.findToggle(), this.selectedClass);
        console.log("YouButton.getChecked: " + selector + " " + isChecked);
        return isChecked;
    };

    this.setChecked = function(doChecked) {
        var isChecked = this.getChecked();
        if (isChecked === doChecked) {
            return;
        }
        console.log("YouButton.setChecked: " + selector + " " + doChecked);

        var $this = this;
        setTimeout(function() {
            helpers.triggerEnter($this.findToggle());
        }, 500);
    };
}

YouButton.prototype = new GoogleButton();
YouButton.fromSelector = function(selector) {
    return new YouButton(selector);
};

/////////// End Player Button ////////////////


