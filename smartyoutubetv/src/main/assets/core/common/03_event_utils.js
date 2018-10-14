/*
Description: Common event routines
*/

console.log("Scripts::Running script event_utils.js");

var EventUtils = {
    checkIntervalMS: 3000,
    listeners: {},

    init: function() {
        // do init here
    },

    /**
     * Calls listener.onKeyEvent(event) every time key event arrives
     */
    addGlobalKeyPressListener: function(listener) {
        this.addKeyPressListener(listener, YouTubeConstants.APP_CONTAINER_SELECTOR);
    },

    /**
     * Calls listener.onKeyEvent(event) every time key event arrives
     */
    addKeyPressListener: function(listener, rootSelector) {
        if (this.listeners[listener.onKeyEvent]) {
            console.log("EventUtils::this listener already added... do nothing");
            return;
        }

        this.listeners[listener.onKeyEvent] = true;

        var container = Utils.$(rootSelector);
        console.log('EventUtils::addListener:keyup... ');
        container.addEventListener(EventTypes.KEY_UP, function(event) {
            listener.onKeyEvent(event);
        });
    },

    isPlayerInitialized: function() {
        var elem = Utils.$(YouTubeConstants.PLAYER_CONTAINER_SELECTOR);
        return Utils.hasClass(elem, YouTubeConstants.PLAYER_CONTAINER_CLASS);
    },

    /**
     * Calls listener.onKeyEvent(event) every time key event arrives
     */
    addPlayerKeyPressListener: function(listener) {
        if (!this.isPlayerInitialized()) {
            // player not initialized yet
            var $this = this;
            setTimeout(function() {
                $this.addPlayerKeyPressListener(listener);
            }, $this.checkIntervalMS);
            return;
        }

        console.log("EventUtils::addPlayerKeyPressListener");

        this.addKeyPressListener(listener, YouTubeConstants.PLAYER_CONTAINER_SELECTOR);
    },

    /**
     * Calls listener.onPlaybackEvent() every time player start to play an video
     */
    addPlaybackListener: function(listener) {
        // do everytime video loads:
        window.addEventListener(EventTypes.HASH_CHANGE, function(){
            var isPlayerOpened = window.location.hash.indexOf(YouTubeConstants.PLAYER_URL_KEY) != -1;
            if (isPlayerOpened) {
                Utils.postSmallDelayed(listener.onPlaybackEvent, listener); // video initialized with small delay
            }
        }, false);
    },

    delayUntilPlayerBeInitialized: function(fn) {
        var testFn = function() {
            return Utils.$(YouTubeConstants.PLAYER_PLAY_BUTTON_SELECTOR);
        };
        Utils.delayTillTestFnSuccess(fn, testFn);
    },

    triggerEvent: function(elementOrSelector, type, keyCode) {
        var el = elementOrSelector;
        if (Utils.isSelector(elementOrSelector)) {
            el = Utils.$(elementOrSelector);
        }

        console.log("EventUtils::triggerEvent: " + elementOrSelector + " " + type + " " + keyCode);

        if (!el) {
            console.warn("EventUtils::triggerEvent: unable to find " + elementOrSelector);
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
            el.fireEvent('on' + e.eventType, e);
        }
    },

    triggerEnter: function(selector) {
        // simulate mouse/enter key press
        this.triggerEvent(selector, EventTypes.KEY_UP, KeyCodes.ENTER);
    }
};

EventUtils.init();