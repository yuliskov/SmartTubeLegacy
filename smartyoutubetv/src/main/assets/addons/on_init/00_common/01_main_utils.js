/*
Description: Common routines
*/

console.log("Scripts::Running script main_utils.js");

var Utils = {
    checkIntervalMS: 3000,
    listeners: {},

    init: function() {
        // do init here
    },

    isSelector: function(el) {
        return typeof el === 'string' || el instanceof String;
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

    appendHtml: function(el, str) {
        var div = document.createElement('div');
        div.innerHTML = str;

        var child;
        while (div.children.length > 0) {
            child = el.appendChild(div.children[0]);
        }
        return child;
    },

    getCurrentTimeMs: function() {
        var d = new Date();
        return d.getTime();
    },

    overrideProp: function(propStr, value) { // pure function
        var arr = propStr.split(".");      // Split the string using dot as separator
        var lastVal = arr.pop();       // Get last element
        var firstVal = arr.join(".");  // Re-join the remaining substrings, using dot as separator

        Object.defineProperty(eval(firstVal), lastVal, { get: function(){return value}, configurable: true, enumerable: true });
    },

    /**
     * Override prop limited number of times.
     * After limit is reached prop will be reverted to the original state.
     */
    overridePropNum: function(propStr, value, nums) { // pure function
        var originVal = eval(propStr);

        var arr = propStr.split(".");      // Split the string using dot as separator
        var lastVal = arr.pop();       // Get last element
        var firstVal = arr.join(".");  // Re-join the remaining substrings, using dot as separatos

        var numCalled = 0;
        Object.defineProperty(eval(firstVal), lastVal, { get: function() {
                ++numCalled;
                if (numCalled > nums) {
                    return originVal;
                }
                return value;
            }, configurable: true, enumerable: true });
    },

    /**
     * Temporal override. After timeout prop will be reverted to the original state.
     */
    overridePropTemp: function(propStr, value, timeoutMS) {
        var currentTimeMS = this.getCurrentTimeMs();
        var originVal = eval(propStr);

        var arr = propStr.split(".");      // Split the string using dot as separator
        var lastVal = arr.pop();       // Get last element
        var firstVal = arr.join(".");  // Re-join the remaining substrings, using dot as separatos

        var $this = this;
        // var numCalled = 0;
        Object.defineProperty(eval(firstVal), lastVal, { get: function() {
            // console.log("Utils::" + propStr + " called " + ++numCalled + " times");
            var timeSpanned = $this.getCurrentTimeMs() - currentTimeMS;
            if (timeSpanned > timeoutMS) {
                return originVal;
            }
            return value;
        }, configurable: true, enumerable: true });
    },

    observeDOM: (function(){
        var MutationObserver = window.MutationObserver || window.WebKitMutationObserver,
            eventListenerSupported = window.addEventListener;

        return function(obj, callback){
            if( MutationObserver ){
                // define a new observer
                var obs = new MutationObserver(function(mutations, observer){
                    if( mutations[0].addedNodes.length || mutations[0].removedNodes.length )
                        callback(obj);
                });
                // have the observer observe foo for changes in children
                obs.observe( obj, { childList:true, subtree:true });
            }
            else if( eventListenerSupported ){
                obj.addEventListener('DOMNodeInserted', callback, false);
                obj.addEventListener('DOMNodeRemoved', callback, false);
            }
        }
    })(),

    // detection is based on key events
    // combined detection: first by interval then by key in sake of performance
    delayTillTestFnSuccess: function(callback, testFn, runOnce) {
        var delayIntervalMS = 500;
        var res = testFn();
        if (res) {
            callback();
            if (runOnce)
                return;
        }

        var delayFnKey = function(event) {
            clearInterval(interval); // remove concurrent event
            
            var keyCode = event.keyCode;

            if (keyCode != KeyCodes.UP &&
                keyCode != KeyCodes.DOWN &&
                keyCode != KeyCodes.LEFT &&
                keyCode != KeyCodes.RIGHT &&
                keyCode != KeyCodes.ESC &&
                keyCode != KeyCodes.ENTER) {
                return;
            }

            setTimeout(function() { // wait till some elms be initialized like exit btn, etc
                var res = testFn();
                if (!res)
                    return;

                // cleanup
                if (runOnce) {
                    console.log('Utils::delayTillElementBeInitialized: onkeydown: removing callback: ' + callback.toString().slice(0, 50));
                    document.removeEventListener(EventTypes.KEY_DOWN, delayFnKey, true);
                }
                // actual call
                callback();
            }, delayIntervalMS);
        };

        var delayFnInt = function() {
            setTimeout(function() { // wait till some elms be initialized like exit btn, etc
                var res = testFn();
                if (!res)
                    return;

                console.log('Utils::delayTillElementBeInitialized: interval: prepare to fire callback: ' + callback.toString().slice(0, 50));

                // cleanup
                if (runOnce) {
                    console.log('Utils::delayTillElementBeInitialized: interval: removing callback: ' + callback.toString().slice(0, 50));
                    clearInterval(interval);
                }

                // actual call
                callback();
            }, delayIntervalMS);
        };

        // concurrent triggers (only one left in the end)
        document.addEventListener(EventTypes.KEY_DOWN, delayFnKey, true); // useCapture: true
        var interval = setInterval(delayFnInt, delayIntervalMS);
    },

    /**
     * Same as {@link setInterval} but preserves <b>this</b> reference
     */
    postCycled: function(fn, obj, delayMS) {
        var result = null;
        if (fn) {
            result = setInterval(function() {
                fn.call(obj);
            }, delayMS);
        }
        return result;
    },

    /**
     * Same as {@link setTimeout} but preserves <b>this</b> reference
     */
    postDelayed: function(fn, obj, delayMS) {
        var result = null;
        if (fn) {
            result = setTimeout(function() {
                fn.call(obj);
            }, delayMS);
        }
        return result;
    },

    cancelPost: function(result) {
        clearTimeout(result);
        clearInterval(result);
    },

    postSmallDelayed: function(fn, obj) {
        this.postDelayed(fn, obj, 1000);
    },

    logMethod: function(fn, obj) {
        if (!obj || !fn)
            return;

        obj[fn.name] = function() {
            console.log("Utils::logMethod: " + fn + ' ' + arguments);
            return fn.apply(obj, arguments);
        };
    }
};

Utils.init();