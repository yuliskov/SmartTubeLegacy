/*
Description: Common routines
*/

console.log("Scripts::Running script main_utils.js");

var Keys = {
    UP: 38,
    DOWN: 40,
    LEFT: 37,
    RIGHT: 39,
    ENTER: 13,
    ESC: 27,
};

var Utils = {
    playerContainerClass: 'watch',
    playerContainerSelector: '#watch', // div that receives keys events for player (note: some events don't reach upper levels)
    appContainerSelector: '#leanback', // div that receives keys events for app
    checkIntervalMS: 3000,
    listeners: {},

    init: function() {
        // do init here
    },

    isSelector: function(el) {
        return typeof el === 'string' || el instanceof String;
    },

    triggerEvent: function(element, type, keyCode) {
        var el = element;
        if (this.isSelector(element)) {
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
    },

    triggerEnter: function(selector) {
        // simulate mouse/enter key press
        this.triggerEvent(selector, 'keyup', Keys.ENTER);
    },

    hasClass: function(elem, klass) {
        if (!elem) {
            return null;
        }
        return (" " + elem.className + " ").indexOf(" " + klass + " ") > -1;
    },

    $: function(selector) {
        if (!this.isSelector(selector))
            return selector;
        return document.querySelectorAll(selector)[0];
    },

    getCurrentTimeMs: function() {
        var d = new Date();
        return d.getTime();
    },

    addListener: function(listener, root) {
        if (this.listeners[listener]) {
            console.log("Utils::This listener already added... do nothing");
            return;
        }

        this.listeners[listener] = true;

        var container = document.querySelector(root);
        console.log('Utils::addListener:keyup... ');
        var type = 'keyup';
        container.addEventListener(type, function(event) {
            listener.onKeyEvent(event);
        });
    },

    isPlayerInitialized: function() {
        var elem = this.$(this.playerContainerSelector);
        return this.hasClass(elem, this.playerContainerClass);
    },

    addPlayerListener: function(listener) {
        if (!this.isPlayerInitialized()) {
            // player not initialized yet
            var $this = this;
            setTimeout(function() {
                $this.addPlayerListener(listener);
            }, this.checkIntervalMS);
            return;
        }

        this.addListener(listener, this.playerContainerSelector);
    },

    addAppListener: function(listener) {
        this.addListener(listener, this.appContainerSelector);
    },

    overrideProp: function(propStr, value) {
        var arr = propStr.split(".");      // Split the string using dot as separator
        var lastVal = arr.pop();       // Get last element
        var firstVal = arr.join(".");  // Re-join the remaining substrings, using dot as separatos

        Object.defineProperty(eval(firstVal), lastVal, { get: function(){return value}, configurable: true, enumerable: true });
    },

    // temporal override, after timeout prop will be reverted to original state
    overridePropTemp: function(propStr, value, timeoutMS) {
        var currentTimeMS = this.getCurrentTimeMs();
        var originVal = eval(propStr);

        var arr = propStr.split(".");      // Split the string using dot as separator
        var lastVal = arr.pop();       // Get last element
        var firstVal = arr.join(".");  // Re-join the remaining substrings, using dot as separatos

        var $this = this;
        Object.defineProperty(eval(firstVal), lastVal, { get: function() {
            var timeSpanned = $this.getCurrentTimeMs() - currentTimeMS;
            if (timeSpanned > timeoutMS) {
                return originVal;
            }
            return value;
        }, configurable: true, enumerable: true });
    }
};

Utils.init();