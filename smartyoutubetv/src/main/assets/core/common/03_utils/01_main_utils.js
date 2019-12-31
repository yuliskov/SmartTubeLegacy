/**
 * Common routines
 */

console.log("Scripts::Running script main_utils.js");

var Utils = {
    TAG: 'Utils',
    checkIntervalMS: 3000,
    listeners: {},

    init: function() {
        // do init here
    },

    isSelector: function(el) {
        return typeof el === 'string' || el instanceof String || this.isArray(el);
    },

    flattenArray: function(arr) {
        return [].concat.apply([], arr);
    },

    isArray: function(obj) {
        return Object.prototype.toString.call(obj) === '[object Array]';
    },

    isNodeList: function(obj) {
        return NodeList && NodeList.prototype.isPrototypeOf(obj);
    },

    isString: function(obj) {
        return Object.prototype.toString.call(obj) === "[object String]";
    },

    addClass: function(elem, cls) {
        if (!this.hasClass(elem, cls)) {
            elem.className += ' ' + cls;
        }
    },

    removeClass: function(elem, cls) {
        if (this.hasClass(elem, cls)) {
            elem.className = elem.className.replace(cls, '');
        }
    },

    hasClass: function(elem, cls) {
        if (!elem) {
            return false;
        }
        return (" " + elem.className + " ").indexOf(" " + cls + " ") > -1;
    },

    $$: function(elementOrSelector) {
        if (!elementOrSelector) {
            return null;
        }

        if (!this.isSelector(elementOrSelector))
            return elementOrSelector;

        return document.querySelectorAll(elementOrSelector);
    },

    $: function(elementOrSelector) {
        if (!elementOrSelector) {
            return null;
        }

        // allow to use arrays as selectors like ['a', 'b', 'c']
        // return first element that exists
        if (this.isArray(elementOrSelector)) {
            var selectors = this.flattenArray(elementOrSelector);
            var el = null;
            for (var i = 0; i < selectors.length; i++) {
                el = document.querySelector(selectors[i]);
                if (el && el.children && el.children.length &&
                    !Utils.hasClass(el, YouTubeClasses.ELEMENT_DISABLED)) {
                    break;
                }
            }
            return el;
        }

        if (!this.isSelector(elementOrSelector))
            return elementOrSelector;

        return document.querySelector(elementOrSelector);
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

    overridePropOld: function(propStr, value) { // pure function
        var arr = propStr.split(".");      // Split the string using dot as separator
        var lastVal = arr.pop();       // Get last element
        var firstVal = arr.join(".");  // Re-join the remaining substrings, using dot as separator

        Object.defineProperty(eval(firstVal), lastVal, { get: function(){return value}, configurable: true, enumerable: true });
    },

    overrideProp: function(obj, propName, value, callback) { // pure function
        var originVal = obj[propName];
        obj[propName] = value;
        Object.defineProperty(obj, propName, {
            get: function() {
                if (callback && callback()) { // give a chance to decide whether to use original value
                    return originVal;
                }

                return value;
            },
            set: function(val){},
            configurable: true,
            enumerable: true
        });
    },

    overridePropOnce: function(obj, propName, value) { // pure function
        //obj[propName] = value;
        obj[propName + 'Real'] = value;
        Object.defineProperty(obj, propName, {
            get: function() {
                if (obj.doneOnce) { // give a chance to decide whether to use original value
                    return obj[propName + 'Real'];
                }

                obj.doneOnce = true;

                return value;
            },
            set: function(val){
                if (obj.doneOnce) { // give a chance to decide whether to use original value
                    obj[propName + 'Real'] = val;
                    return;
                }

                obj.doneOnce = true;
            },
            configurable: true,
            enumerable: true
        });
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
    delayTillTestFnSuccess: function(callback, testFn) {
        var delayIntervalMS = 500;
        var res = testFn();
        if (res) {
            callback();
            return;
        }

        var delayFnKey = function(event) {
            clearInterval(interval); // remove concurrent event
            
            var keyCode = event.keyCode;

            if (keyCode != DefaultKeys.UP &&
                keyCode != DefaultKeys.DOWN &&
                keyCode != DefaultKeys.LEFT &&
                keyCode != DefaultKeys.RIGHT &&
                keyCode != DefaultKeys.ESC &&
                keyCode != DefaultKeys.ENTER) {
                return;
            }

            setTimeout(function() { // wait till some elms be initialized like exit btn, etc
                var res = testFn();
                if (!res)
                    return;

                // cleanup
                console.log('Utils::delayTillElementBeInitialized: onkeydown: removing callback: ' + callback.toString().slice(0, 50));
                document.removeEventListener(DefaultEvents.KEY_DOWN, delayFnKey, true);

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
                console.log('Utils::delayTillElementBeInitialized: interval: removing callback: ' + callback.toString().slice(0, 50));
                clearInterval(interval);

                // actual call
                callback();
            }, delayIntervalMS);
        };

        // concurrent triggers (only one left in the end)
        document.addEventListener(DefaultEvents.KEY_DOWN, delayFnKey, true); // useCapture: true
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
    },

    show: function(elementOrSelector) {
        var el = this.$(elementOrSelector);

        if (el) {
            el.style.display = 'initial';
        }
    },

    hide: function(elementOrSelector) {
        var el = this.$(elementOrSelector);

        if (el) {
            el.style.display = 'none';
        }
    },

    isVisible: function(elementOrSelector) {
        var el = this.$(elementOrSelector);

        if (el) {
            return el.style.display != 'none';
        }

        return false;
    },
    
    content: function(elem, newContent) {
        if (!elem)
            return null;

        if (newContent) {
            elem.innerHTML = newContent;
            return;
        }

        return elem.innerHTML;
    },

    isEven: function(n) {
        return n % 2 == 0;
    },

    removeFromArray: function(array, obj) {
        if (!array || !obj) {
            return;
        }

        var idx = array.indexOf(obj);

        if (idx != -1) {
            array.splice(idx, 1); // remove element by idx
        }
    },

    dumpObj: function(obj) {
        var output = '';
        for (var property in obj) {
            if (obj[property] != null && typeof obj[property] != "function" && property.indexOf('_') == -1) {
                output += property + ': ' + obj[property]+'; ';
            }
        }

        return output;
    },

    /**
     * True if contains any of supplied substrings<br/>
     */
    contains: function(fullSpec, specOrArray, testAll) {
        if (fullSpec == null || specOrArray == null) {
            return false;
        }

        if (this.isArray(specOrArray)) {
            var result = false;

            for (var i = 0; i < specOrArray.length; i++) {
                result = this.containsSimple(fullSpec, specOrArray[i]);

                if (result && !testAll) {
                    break;
                }

                if (!result && testAll) {
                    break;
                }
            }

            return result;
        }

        return this.containsSimple(fullSpec, specOrArray);
    },

    /**
     * Compare special strings.<br/>
     * Returns true even when the second string is empty: this.specCmp('abc', '') == true<br/>
     * Or when strings partially matched: this.specCmp('abc', 'ab') == true
     */
    containsSimple: function(fullSpec, spec) {
        if (fullSpec == null || spec == null) {
            return false;
        }

        fullSpec = fullSpec.toLowerCase();
        spec = spec.toLowerCase();
        return fullSpec.indexOf(spec) >= 0;
    },

    getQueryParams: function(qs) {
        if (!qs) {
            return null;
        }

        qs = qs.split('+').join(' ');

        var params = {},
            tokens,
            re = /([^=?&]+)=([^&]*)/g;

        while (tokens = re.exec(qs)) {
            params[decodeURIComponent(tokens[1])] = decodeURIComponent(tokens[2]);
        }

        return params;
    },

    printProps: function(obj) {
        for (var key in obj) {
            console.log(key + " " + obj[key]);
        }
    }
};

Utils.init();