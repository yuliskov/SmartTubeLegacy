/////////// GoogleButton ////////////////

var PlayerActivity = {
    BUTTON_LIKE: "button_like",
    BUTTON_DISLIKE: "button_dislike",
    BUTTON_SUBSCRIBE: "button_subscribe",
    BUTTON_USER_PAGE: "button_user_page",
    BUTTON_PREV: "button_prev",
    BUTTON_NEXT: "button_next",
    BUTTON_BACK: "button_back"
};

var GoogleConstants = {
    BUTTON_LIKE: ".icon-like.toggle-button",
    BUTTON_DISLIKE: ".icon-dislike.toggle-button",
    BUTTON_SUBSCRIBE: ".icon-logo-lozenge.toggle-button",
    BUTTON_USER_PAGE: ".pivot-channel-tile",
    BUTTON_NEXT: ".icon-player-next",
    BUTTON_PREV: ".icon-player-prev",
    BUTTON_BACK: ".back.no-model.legend-item"
};

function GoogleButton() {
    this.disabledClass = 'disabled';
    this.selectedClass = 'toggle-selected';
    this.optionsBtnSelector = '#transport-more-button';
    this.backBtnSelector = '.back.no-model.legend-item';
    this.bottomBarSelector = '#transport-controls';
    this.bottomBarControllerSelector = '#watch';
    this.controlsBarSelector = '#buttons-list';
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
            // msg 4 future me
            // 'paused' video won't invoke history update
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
    }
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
        // var obj = helpers.$(this.btn.selector);
        // if (obj && (obj.children.length >= 1)) {
        //     console.log('YouButtonInitializer.initBtn: doing un-delayed call: ' + this.btn.selector);
        //     callback();
        //     this.callbackStack.shift();
        //     if (this.callbackStack[0])
        //         this.callbackStack[0]();
        //     return;
        // }

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

    this.getChecked = function() {
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
        if (selector === GoogleConstants.BUTTON_USER_PAGE) {
            return new UserPageButton(selector);
        }
        // if (selector === GoogleConstants.BUTTON_PREV) {
        //     return new PrevButton(selector);
        // }
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

//////////// User Page Button Wrapper ///////////////

function UserPageButton(selector) {
    this.btn = new YouButton(selector);

    this.getChecked = function() {
        return this.btn.getChecked();
    };

    this.setChecked = function(doChecked) {
        this.btn.setChecked(doChecked);
        console.log("UserPageButton: skipping last history item");
        if (doChecked)
            helpers.skipLastHistoryItem();
    };
}

function PrevButton(selector) {
    this.btn = new YouButton(selector);

    this.getChecked = function() {
        return this.btn.getChecked();
    };

    this.setChecked = function(doChecked) {
        this.btn.setChecked(doChecked);
        console.log("PrevButton: prev button is disabled... going back");
        if (this.btn.getChecked() === null)
            YouButton.fromSelector(GoogleConstants.BUTTON_BACK).setChecked(true);
    };
}

//////////// End User Page Button Wrapper ///////////////


