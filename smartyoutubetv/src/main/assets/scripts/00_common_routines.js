/*
Description: Common routines
*/

console.log("Scripts::Running script common_routines.js");

var Keys = {
    UP: 38,
    DOWN: 40,
    LEFT: 37,
    RIGHT: 39,
    ENTER: 13,
    ESC: 27,
};

function Utils() {
    this.playerContainerClass = 'watch';
    this.playerContainerSelector = '#watch'; // div that receives keys events for player (note: some events don't reach upper levels)
    this.appContainerSelector = '#leanback'; // div that receives keys events for app
    this.checkIntervalMS = 3000;
    this.listeners = {};

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
        this.triggerEvent(selector, 'keyup', Keys.ENTER);
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

    this.addListener = function(listener, root) {
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
    };

    this.isPlayerInitialized = function() {
        var elem = this.$(this.playerContainerSelector);
        return this.hasClass(elem, this.playerContainerClass);
    };

    this.addPlayerListener = function(listener) {
        if (!this.isPlayerInitialized()) {
            // player not initialized yet
            var $this = this;
            setTimeout(function() {
                $this.addPlayerListener(listener);
            }, this.checkIntervalMS);
            return;
        }

        this.addListener(listener, this.playerContainerSelector);
    };

    this.addAppListener = function(listener) {
        this.addListener(listener, this.appContainerSelector);
    };

    this.overrideProp = function(propStr, value) {
        var arr = propStr.split(".");      // Split the string using dot as separator
        var lastVal = arr.pop();       // Get last element
        var firstVal = arr.join(".");  // Re-join the remaining substrings, using dot as separatos

        Object.defineProperty(eval(firstVal), lastVal, { get: function(){return value}, configurable: true, enumerable: true });
    };
}

var utils = new Utils();