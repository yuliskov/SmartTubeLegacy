/////////// GoogleButton ////////////////

var PlayerActivity = {
    BUTTON_LIKE: "button_like",
    BUTTON_DISLIKE: "button_dislike",
    BUTTON_SUBSCRIBE: "button_subscribe",
    BUTTON_USER_PAGE: "button_user_page",
    BUTTON_PREV: "button_prev",
    BUTTON_NEXT: "button_next",
    BUTTON_BACK: "button_back",
    BUTTON_SUGGESTIONS: "button_suggestions",
    TRACK_ENDED: "track_ended"
};

var GoogleConstants = {
    BUTTON_LIKE: ".icon-like.toggle-button",
    BUTTON_DISLIKE: ".icon-dislike.toggle-button",
    BUTTON_SUBSCRIBE: ".icon-logo-lozenge.toggle-button",
    BUTTON_USER_PAGE: ".pivot-channel-tile",
    BUTTON_NEXT: ".icon-player-next",
    BUTTON_PREV: ".icon-player-prev",
    BUTTON_BACK: ".back.no-model.legend-item",
    BUTTON_SUGGESTIONS: "button_suggestions", // fake button (use internal logic)
    TRACK_ENDED: "track_ended" // fake button (use internal logic)
};

function GoogleButton() {
    this.hiddenClass = 'hidden';
    this.disabledClass = 'disabled';
    this.selectedClass = 'toggle-selected';
    this.optionsBtnSelector = '#transport-more-button';
    this.backBtnSelector = '.back.no-model.legend-item';
    this.bottomBarSelector = '#transport-controls';
    this.bottomBarControllerSelector = '#watch';
    this.controlsBarSelector = '#buttons-list';
    this.playButtonSelector = ".icon-player-play.toggle-button";
    this.mainControlsSelector = '.fresh-transport-controls.transport-controls';
    this.mainTitleSelector = '.title-card.watch-title-tray';
    this.keysContainerSelector = '#watch'; // div that receives keys events
    this.playerUrlTemplate = '/watch/'; // url part of the opened player
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

    this.triggerButton = function(keyCode) {
        var container = this.$(this.keysContainerSelector);
        var type = 'keydown';
        this.triggerEvent(container, type, keyCode);
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

    this.isDisabled = function(element) {
        var el = element;
        if (isSelector(element)) {
            el = this.$(element);
        }
        var hasClass = this.hasClass(el, this.disabledClass);
        console.log("Helpers.isDisabled: " + element + " " + hasClass);
        return hasClass;
    };

    this.isHidden = function(element) {
        var el = element;
        if (isSelector(element)) {
            el = this.$(element);
        }
        var hasClass = this.hasClass(el, this.hiddenClass);
        console.log("Helpers.isHidden: " + element + " " + hasClass);
        return hasClass;
    };

    this.$ = function(selector) {
        if (!isSelector(selector))
            return selector;
        return document.querySelectorAll(selector)[0];
    };

    this.muteVideo = function() {
        var player = document.getElementsByTagName('video')[0];
        if (!player)
            return;

        // we can't pause video because history will not work
        function muteVideo() {
            var player = document.getElementsByTagName('video')[0];
            console.log('Helpers.muteVideo called');
            // msg 4 future me
            // 'paused' video won't invoke history update
            player.muted = true;
            player.setAttribute('style', '-webkit-filter:brightness(0)');
            player.play();
        }

        function onLoadData() {
            console.log('Helpers.onLoadData called');
            muteVideo();
            player.removeEventListener('loadeddata', onLoadData);
        }

        muteVideo();

        // load events: loadedmetadata, loadeddata
        player.addEventListener('loadeddata', onLoadData, false);
    };

    // supply selector list
    this.getButtonStates = function() {
        this.muteVideo();
        new KeyUpDownWatcher(null); // init watcher

        YouButton.resetCache(); // activity just started

        var states = {};

        // NOTE: we can't delay here so process in reverse order
        var reversedKeys = Object.keys(GoogleConstants).reverse();

        for (var idx in reversedKeys) {
            var key = reversedKeys[idx];
            var selector = GoogleConstants[key];
            var btn = YouButton.fromSelector(selector);
            var newName = PlayerActivity[key];
            var isChecked = btn.getChecked();
            if (isChecked === null) // exclude disabled buttons from result
                continue;
            states[newName] = isChecked;
        }

        return states;
    };

    this.syncButtons = function(states) {
        this.muteVideo();
        new KeyUpDownWatcher(null); // init watcher

        YouButton.resetCache(); // activity just started
        console.log("Helpers.syncButtons: " + JSON.stringify(states));
        for (var key in PlayerActivity) {
            var btnId = PlayerActivity[key];
            var isChecked = states[btnId];
            if (isChecked === undefined) // button gone, removed etc..
                continue;
            var selector = GoogleConstants[key];
            var btn = YouButton.fromSelector(selector);
            btn.setChecked(isChecked);
        }
    };

    this.sendAction = function(action) {
        // code that sends string constant to activity
        if (app && app.onGenericStringResult) {
            app.onGenericStringResult(action);
        } else {
            console.log('Helpers: app not found');
        }
    };
}

Helpers.prototype = new GoogleButton();

window.helpers = new Helpers();

// Usage: PressCommandBase.java
// helpers.triggerEvent(helpers.$('%s'), 'keyup', 13);

// Usage: PressCommandBase.java
// helpers.isDisabled(targetButton) && app && app.onGenericBooleanResult(false, %s);

/////////// End Helpers ////////////////

///////////////// YouButtonInitializer /////////////////////////

function YouButtonInitializer(btn) {
    this.btn = btn;
    this.callbackStack = YouButtonInitializer.callbackStack;

    this.doPressOnOptionsBtn = function() {
        helpers.triggerEnter(this.optionsBtnSelector);
    };

    this.doCallbackIfReady = function(callback) {
        var $this = this;
        var timeout = 1000; // timeout until Options show on/off
        setTimeout(function() {
            callback();
            $this.callbackStack.shift();
            if ($this.callbackStack[0])
                $this.callbackStack[0]();
        }, timeout);
    };
    this.setCheckedWrapper = function(callback) {
        var obj = helpers.$(this.btn.selector);
        if (!obj || !obj.children.length) {
            this.doPressOnOptionsBtn();
            console.log('YouButtonInitializer.initBtn: btn not initialized: ' + this.btn.selector);
            this.doCallbackIfReady(callback);
            return;
        }
        callback();
        this.callbackStack.shift();
        if (this.callbackStack[0])
            this.callbackStack[0]();
    };

    this.getCheckedWrapper = function(callback) {
        var obj = helpers.$(this.btn.selector);
        if (!obj || !obj.children.length) {
            console.log('YouButtonInitializer.initBtn2: btn not initialized: ' + this.btn.selector);
            this.doPressOnOptionsBtn();
        }
        return callback();
    };

    this.apply = function() {
        var $this = this;

        var realSetChecked = this.btn.setChecked;
        this.btn.setChecked = function(doChecked) {
            console.log('YouButtonInitializer: YouButton.setChecked called');
            var thisBtn = this;
            var callback = function() {
                $this.setCheckedWrapper(function() {
                    realSetChecked.call(thisBtn, doChecked);
                });
            };
            if ($this.callbackStack.length === 0) {
                $this.callbackStack.push(callback);
                callback();
            } else {
                // simply push (call it later)
                $this.callbackStack.push(callback);
            }
        };

        // can't use stack! we have to return immediately! no delays allowed!
        var realGetChecked = this.btn.getChecked;
        this.btn.getChecked = function() {
            console.log('YouButtonInitializer: YouButton.getChecked called');
            var thisBtn = this;
            return $this.getCheckedWrapper(function() {
                return realGetChecked.call(thisBtn);
            });
        };
    };
}

YouButtonInitializer.prototype = new GoogleButton();
YouButtonInitializer.callbackStack = [];

/////////////////// End YouButtonInitializer ///////////////////////

function YouButton(selector) {
    this.selector = selector;

    this.initializer = new YouButtonInitializer(this);

    this.doPressOnOptionsBtn = function() {
        helpers.triggerEnter(this.optionsBtnSelector);
    };

    this.findToggle = function() {
        var btn = helpers.$(selector);

        btn || console.warn("YouButton.findToggle: unable to find " + selector);

        return btn;
    };

    this.playerIsClosed = function() {
        return document.location.href.indexOf(this.playerUrlTemplate) === -1;
    };

    this.getChecked = function() {
        if (this.playerIsClosed())
            return null; // element not exists (see ActionReceiver.java for details)

        if (this.isChecked === undefined) {
            var toggle = this.findToggle();
            var isChecked = helpers.hasClass(toggle, this.selectedClass);
            var isDisabled = helpers.hasClass(toggle, this.disabledClass);
            this.isChecked = isDisabled ? null : isChecked;
        }
        console.log("YouButton.getChecked: " + selector + " " + this.isChecked);
        return this.isChecked;
    };

    this.setChecked = function(doChecked) {
        if (this.playerIsClosed())
            return;

        var isChecked = this.getChecked();
        if (isChecked === null) {
            console.log("YouButton: button is disabled: exiting: " + this.selector);
            return;
        }
        if (isChecked === doChecked) {
            return;
        }

        console.log("YouButton.setChecked: " + selector + " " + doChecked);
        helpers.triggerEnter(this.findToggle());
        this.isChecked = doChecked;
    };

    this.initializer.apply();
}

YouButton.prototype = new GoogleButton();
YouButton.fromSelector = function(selector) {
    function createButton(selector) {
        if (selector === GoogleConstants.TRACK_ENDED) {
            return new TrackEndFakeButton(selector);
        }
        if (selector === GoogleConstants.BUTTON_SUGGESTIONS) {
            return new SuggestionsFakeButton(selector);
        }
        return new YouButton(selector);
    }

    if (!this.btnMap)
        this.btnMap = {};
    if (!this.btnMap[selector]) {
        this.btnMap[selector] = createButton(selector);
    } else {
        console.log("YouButton.fromSelector: getting button from cache");
    }
    return this.btnMap[selector];
};

YouButton.resetCache = function() {
    if (this.btnMap)
        delete this.btnMap;
};

/////////// End Player Button ////////////////

/////////// YouButton Wrapper //////////////////

function TrackEndFakeButton(selector) {
    this.selector = selector;

    this.playerJumpToEnd = function() {
        var player = helpers.$('video');
        if (player) {
            console.log("TrackEndFakeButton: before jump to the end: " + player.currentTime + " " + player.duration);
            player.currentTime = player.duration;
        }
    };

    this.getChecked = function() {
        return null; // not exists
    };

    this.setChecked = function(doChecked) {
        console.log("FakeButton: clicking on the fake item");
        if (doChecked)
            this.playerJumpToEnd();
    };
}

function KeyUpDownWatcher(host) {
    function KeyUpDownWatcherService() {
        var container = helpers.$(this.keysContainerSelector);
        var type = 'keydown';
        var up = 38;
        var enter = 13;
        var esc = 27;
        var $this = this;

        var myListener = function(e) {
            var code = e.keyCode;
            if (code === up && $this.host) { // up is fired only for the top row
                $this.host.needToCloseSuggestions();
                $this.host = null; // run once per host
            } else if (code === enter) { // user wants to open an new video
                $this.host = null;
            }

            console.log("Watcher: SuggestionsFakeButton: on keydown: " + code);
        };

        container.addEventListener(type, myListener);

        this.setHost = function(host) {
            this.host = host;
        };

        console.log("Watcher: do init...");
    }

    KeyUpDownWatcherService.prototype = new GoogleButton();

    if (!window.keyUpDownWatcherService) {
        window.keyUpDownWatcherService = new KeyUpDownWatcherService();
    }
    window.keyUpDownWatcherService.setHost(host);
}

KeyUpDownWatcher.prototype = new GoogleButton();
KeyUpDownWatcher.disable = function() {
    new KeyUpDownWatcher(null);
};

function SuggestionsFakeButton(selector) {
    this.selector = selector;
    this.CLOSE_SUGGESTIONS = "action_close_suggestions";
    
    this.openSuggestions = function() {
        console.log("SuggestionsFakeButton: showing suggestions list");

        var downCode = 40;
        // we assume that no interface currently shown
        // press twice
        helpers.triggerButton(downCode);
        helpers.triggerButton(downCode);

        // start point
        this.watcher = new KeyUpDownWatcher(this);
    };

    this.needToCloseSuggestions = function() {
        helpers.sendAction(this.CLOSE_SUGGESTIONS);
        console.log("SuggestionsFakeButton: needToCloseSuggestions");
    };

    this.getChecked = function() {
        return false; // not exists
    };

    this.setChecked = function(doChecked) {
        console.log("FakeButton: clicking on the fake item");
        if (doChecked) // fake btn can only be checked
            this.openSuggestions();
    };
}

SuggestionsFakeButton.prototype = new GoogleButton();

/////////// End YouButton Wrapper //////////////////

console.log('injecting exoplayer.js into ' + document.location.href);