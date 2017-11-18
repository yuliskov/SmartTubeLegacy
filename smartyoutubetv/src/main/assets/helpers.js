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
    this.selectedClass = 'toggle-selected';
    this.optionsBtnSelector = '#transport-more-button';
    this.backBtnSelector = '.back.no-model.legend-item';
}

////////// End GoogleButton //////////////////

/////////// Helpers ////////////////

function Helpers() {
    function isSelector(el) {
        return typeof el === 'string' || el instanceof String;
    }

    this.triggerEvent = function(selector, type, keyCode) {
        if (isSelector(selector)) {
            var el = this.$(selector);
        }

        console.log("Helpers.triggerEvent: " + selector + " " + type + " " + keyCode);

        if (!el) {
            console.warn("Helpers.triggerEvent: unable to find " + selector);
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

    this.isDisabled = function(elem) {
        return this.hasClass(elem, 'disabled');
    };

    this.$ = function(selector) {
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

function YouButton(selector) {
    this.doPressOnOptionsBtn = function() {
        helpers.triggerEnter(this.optionsBtnSelector);
    };

    this.findToggle = function() {
        var btn = helpers.$(selector);
        if (!btn) {
            this.doPressOnOptionsBtn();
            btn = helpers.$(selector);
        }

        btn || console.warn("YouButton.findToggle: unable to find " + selector);

        return btn;
    };

    this.getChecked = function() {
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
        helpers.triggerEnter(this.findToggle());
    };
}

YouButton.prototype = new GoogleButton();
YouButton.fromSelector = function(selector) {
    return new YouButton(selector);
};

/////////// End Player Button ////////////////


