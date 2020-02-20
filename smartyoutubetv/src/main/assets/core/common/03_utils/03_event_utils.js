/**
 * Common event routines
 */

console.log("Scripts::Running script event_utils.js");

var EventUtils = {
    TAG: 'EventUtils',
    checkIntervalMS: 3000,
    listeners: {},

    init: function() {
        // do init here
    },

    /**
     * Calls listener.onKeyEvent(event) every time key event arrives
     */
    addKeyPressListener: function(listener, selector) {
        // TODO: provide more optimized routine
        if (!this.isPlayerInitialized()) {
            // player not initialized yet
            var $this = this;
            setTimeout(function() {
                $this.addKeyPressListener(listener, selector);
            }, $this.checkIntervalMS);
            return;
        }

        if (this.listeners[listener.onKeyEvent]) {
            console.log("EventUtils::this listener already added... do nothing");
            return;
        }

        this.listeners[listener.onKeyEvent] = true;

        var container = Utils.$(selector);
        console.log('EventUtils::addListener:keyup... ');
        container.addEventListener(DefaultEvents.KEY_UP, function(event) {
            listener.onKeyEvent(event);
        });
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
    addPlayerKeyPressListener: function(listener) {
        console.log("EventUtils::addPlayerKeyPressListener");

        this.addKeyPressListener(listener, YouTubeConstants.PLAYER_EVENTS_RECEIVER_SELECTOR);
    },

    /**
     * Calls listener.onPlaybackEvent() every time player start to play an video
     */
    addPlaybackListener: function(listener) {
        // do every time when video loads:
        window.addEventListener(DefaultEvents.HASH_CHANGE, function(){
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

    delayUntilElementInit: function(selector, callback) {
        var $this = this;
        var el = Utils.$(selector);

        if (!el) {
            setTimeout(function() {
                $this.delayUntilElementInit(selector, callback)
            }, 100);
            return;
        }

        if (callback) {
            callback(el);
        }
    },

    isPlayerInitialized: function() {
        var elem = Utils.$(YouTubeConstants.PLAYER_EVENTS_RECEIVER_SELECTOR);
        return Utils.hasClass(elem, YouTubeConstants.PLAYER_CONTAINER_CLASS);
    },

    toSelector: function(el) {
        if (!el) {
            return null;
        }

        if (Utils.isString(el)) {
            return el;
        }

        if (Utils.isArray(el)) {
            return el;
        }

        if (!el.tagName) {
            return el.nodeName || '#window';
        }

        var tag = el.tagName || '';
        var idPart = el.id ? '#' + el.id : '';
        var cls = el.className ? el.className.trim() : '';
        var classPart = cls ? '.' + cls.split(/[ ]+/).join('.') : '';
        return tag + idPart + classPart;
    },

    triggerEvent: function(elementOrArrayOrSelector, type, keyCode) {
        if (Utils.isArray(elementOrArrayOrSelector) || Utils.isNodeList(elementOrArrayOrSelector)) {
            for (var i = 0; i < elementOrArrayOrSelector.length; i++) {
                this._triggerEventInt(elementOrArrayOrSelector[i], type, keyCode);
            }
        } else {
            this._triggerEventInt(elementOrArrayOrSelector, type, keyCode);
        }
    },

    _triggerEventInt: function(elementOrSelector, type, keyCode) {
        if (Utils.isArray(elementOrSelector)) {
            console.log("EventUtils::triggerEvent: arrays not supported: " + elementOrSelector);
            return;
        }

        var el = elementOrSelector;
        if (Utils.isSelector(elementOrSelector)) {
            el = Utils.$(elementOrSelector);
        }

        var elSelector = this.toSelector(el) ? this.toSelector(el) : elementOrSelector;

        if (!el) {
            console.warn("EventUtils::triggerEvent: unable to find " + elSelector);
            return;
        }

        console.log("EventUtils::triggerEvent: " + elSelector + ' ' + type + ' ' + keyCode);

        this._triggerEvent(el, type, keyCode);
    },

    _triggerEvent: function(el, type, keyCode) {
        if ('createEvent' in document) {
            // modern browsers: Chrome, IE9+
            // HTMLEvents, KeyboardEvent
            // https://developer.mozilla.org/en-US/docs/Web/API/Document/createEvent#Notes
            var e = document.createEvent('HTMLEvents');
            e.keyCode = keyCode;
            e.initEvent(type, true, true);
            el.dispatchEvent(e);
        } else {
            // IE 8
            var e = document.createEventObject();
            e.keyCode = keyCode;
            e.eventType = type;
            el.fireEvent('on' + e.eventType, e);
        }
    },

    triggerEnter: function(elementOrSelector) {
        // simulate mouse/enter key press
        this.triggerEvent(elementOrSelector, DefaultEvents.KEY_UP, DefaultKeys.ENTER);
    },

    /**
     * Adds lister or waits till element be initialized
     * @param selectorOrElement desired element as selector
     * @param event desired event
     * @param handler callback
     */
    addListener: function(selectorOrElement, event, handler, useCapture) {
        ListenerUtil.addListener(selectorOrElement, event, handler, useCapture);
    },

    /**
     * Adds lister or waits till element be initialized<br/>
     * Removes handler after first call.
     * @param selectorOrElement desired element as selector
     * @param event desired event
     * @param handler callback
     */
    addListenerOnce: function(selectorOrElement, event, handler) {
        var $this = this;

        var callback = function() {
            handler();

            $this.removeListener(selectorOrElement, event, callback);
        };

        ListenerUtil.addListener(selectorOrElement, event, callback);
    },

    removeListener: function(selectorOrElement, event, handler) {
        ListenerUtil.removeListener(selectorOrElement, event, handler);
    },

    onLoad: function(callback) {
        var loader = Utils.$(YouTubeSelectors.MAIN_LOADER);

        if (loader == null) {
            Log.d(this.TAG, 'Load watcher: App has been loaded.');
            callback && callback();
            return;
        }

        var $this = this;

        setTimeout(function() {
            Log.d($this.TAG, 'Load watcher: App still loading... ' + EventUtils.toSelector(loader));
            $this.onLoad(callback);
        }, 500);
    },

    disableEvents: function(obj, events) {
        if (!obj.addEventListener) {
            return;
        }

        events = Utils.isArray(events) ? events : [events];

        var $this = this;
        var origin = obj.addEventListener;

        obj.addEventListener = function(type, listener, options) {
            for (var i = 0; i < events.length; i++) {
                if (type == events[i]) {
                    Log.d($this.TAG, "Disable event: " + type);
                    return;
                }
            }

            origin.call(document, type, listener, options);
        };
    },

    stringify: function(obj) {
        return JSON.stringify(obj, function(k, v) {
            if (v instanceof Node) {
                return 'Node';
            }
            if (v instanceof Window) {
                return 'Window';
            }
            return v;
        }, ' ');
    },

    turnOffEvents: function(elem) { // pure function
        var $this = this;

        elem.addEventListenerReal = elem.addEventListener;

        elem.addEventListener = function(type, listener, options) {
            Log.d($this.TAG, "Add fake event listener: " + type + " " + EventUtils.toSelector(elem));

            if (!elem.listeners) {
                elem.listeners = {};
            }

            if (!elem.listeners[type]) {
                elem.listeners[type] = [];
            }

            elem.listeners[type].push(listener);

            // events essential for the playback:
            // pause, timeupdate

            // next events not needed for the playback:
            // loadstart, durationchange, loadedmetadata, loadeddata, ended

            // function wrapper(e) {
            //     Log.d($this.TAG, "Calling listener: " + e.type + ", event=" + EventUtils.stringify(e));
            //     Log.d($this.TAG, "Video: " + Utils.dumpObj(elem));
            //     listener.call(elem, e);
            // }
            //
            // if (type == 'pause' || type == 'timeupdate') {
            //     this.addEventListenerReal(type, wrapper, options);
            // }
        };
    },

    turnOffProp: function(obj, propName, persist, onSet) { // pure function
        if (!obj.properties) {
            obj.properties = {};
        }

        obj.properties[propName] = obj[propName];

        Object.defineProperty(obj, propName, {
            get: function() {
                // Log.d($this.TAG, "Getting property: " + propName + ", value: " + obj.properties[propName]);

                return obj.properties[propName];
            },
            set: function(val) {
                if (persist) {
                    obj.properties[propName] = val;
                }

                if (onSet) {
                    onSet(val);
                }
            }
        });
    },

    turnOffMethod: function(obj, methodName, persist, onSet) { // pure function
        if (!obj.methods) {
            obj.methods = {};
        }

        if (obj[methodName]) {
            obj.methods[methodName] = obj[methodName];
            obj[methodName] = function() {
                if (onSet) {
                    onSet.apply(null, arguments);
                }

                if (persist) {
                    return obj.methods[methodName].apply(obj, arguments);
                }
            }
        }
    },

    handleKeyPressMessage: function(data) {
        this.triggerEvent(Utils.$$(YouTubeSelectors.FOCUSED_ELEMENT), DefaultEvents[data.action], DefaultKeys[data.keyCode]);
    }
};

EventUtils.init();