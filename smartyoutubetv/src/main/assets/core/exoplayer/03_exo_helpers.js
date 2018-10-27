// NOTE: this file doesn't depend on common js files

console.log("Scripts::Running core script exo_helpers.js");

/**
 * Usage: <b>PressCommandBase.java</b><br/>
 * <code>exoutils.triggerEvent(exoutils.$('%s'), 'keyup', 13);</code><br/>
 * Usage: <b>PressCommandBase.java</b><br/>
 * <code>exoutils.isDisabled(targetButton) && app && app.onGenericBooleanResult(false, %s);</code>
 * @constructor empty
 */
function ExoUtils() {
    var TAG = 'ExoUtils';

    function isSelector(el) {
        return typeof el === 'string' || el instanceof String;
    }

    this.isArray = function(obj) {
        return Object.prototype.toString.call(obj) === '[object Array]';
    };

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

    this.triggerButton = function(keyCode) {
        var container = this.$(this.eventRootSelector);
        var type = 'keydown';
        this.triggerEvent(container, type, keyCode);
    };

    this.triggerEnter = function(selector) {
        // simulate mouse/enter key press
        this.triggerEvent(selector, 'keyup', 13);
    };

    this.hasClass = function(elem, cls) {
        if (!elem) {
            return null;
        }
        return (" " + elem.className + " ").indexOf(" " + cls + " ") > -1;
    };

    this.isDisabled = function(element) {
        var el = element;
        if (isSelector(element)) {
            el = this.$(element);
        }
        var hasClass = this.hasClass(el, this.disabledClass);
        console.log("ExoUtils.isDisabled: " + element + " " + hasClass);
        return hasClass;
    };

    this.isHidden = function(element) {
        var el = element;
        if (isSelector(element)) {
            el = this.$(element);
        }
        var hasClass = this.hasClass(el, this.hiddenClass);
        console.log("ExoUtils.isHidden: " + element + " " + hasClass);
        return hasClass;
    };

    this.$ = function(selector) {
        // allow to use arrays as selectors like ['a', 'b', 'c']
        // return first element that exists
        if (this.isArray(selector)) {
            for (var i = 0; i < selector.length; i++) {
                var el = document.querySelector(selector[i]);
                if (el != null)
                    return el;
            }
            return null;
        }

        if (!isSelector(selector))
            return selector;
        return document.querySelector(selector);
    };

    // events order:
    // emptied
    // loadstart
    // loadedmetadata
    // loadeddata (first frame of the video has been loaded)
    // playing
    this.muteVideo = function() {
        var callbackSet = 'data-callbackSet';
        var player = document.querySelector('video');
        if (!player)
            return;

        // we can't pause video because history will not work
        function onStart() {
            console.log('ExoUtils.onStart called');
            // msg 4 future me
            // 'paused' video won't invoke history update
            // don't call pause!!! or video remains paused event after play
            player.play();
            player.muted = true;
        }

        onStart();

        if (player.getAttribute(callbackSet)) // callback already set
            return;

        // once player is created it will be reused by other videos
        // 'loadeddata' is first event when video can be muted
        player.addEventListener('loadeddata', onStart, false);

        player.setAttribute(callbackSet, "true");
    };

    this.getVideoDate = function() {
        var element = this.$(this.uploadDate);
        if (element != null) {
            // don't rely on : symbol parsing here! because it depends on localization
            return element.innerHTML;
        }

        element = this.$(this.videoDetails);
        if (element != null) {
            var parts = element.innerHTML.split('•');
            if (parts.length == 3) {
                return parts[2].trim();
            }
        }

        return "";
    };

    /**
     * Hide player in case it is visible
     */
    this.hidePlayerUi = function() {
        var controls = Utils.$(this.playerControlsSelector);
        if (!this.hasClass(controls, this.hiddenClass)) {
            EventUtils.triggerEvent(this.eventRootSelector, DefaultEvents.KEY_UP, DefaultKeys.ESC);
        }
    };

    // supply selector list
    this.getButtonStates = function() {
        this.muteVideo();
        new SuggestionsWatcher(null); // init watcher

        YouButton.resetCache(); // activity just started

        var states = {};

        // NOTE: we can't delay here so process in reverse order
        var reversedKeys = Object.keys(PlayerActivityMapping).reverse();

        for (var idx in reversedKeys) {
            var key = reversedKeys[idx];
            var selector = PlayerActivityMapping[key];
            var btn = YouButton.fromSelector(selector);
            var newName = PlayerActivity[key];
            var isChecked = btn.getChecked();
            if (isChecked === null) // exclude disabled buttons from result
                continue;
            states[newName] = isChecked;
        }

        states[PlayerActivity.VIDEO_DATE] = this.getVideoDate();

        // don't let app to close video player (see ActionsReceiver.java)
        if (window.lastButtonName && window.lastButtonName == PlayerActivity.TRACK_ENDED) {
            states[PlayerActivity.BUTTON_NEXT] = null;
        }

        console.log("ExoUtils.getButtonStates: " + JSON.stringify(states));

        return states;
    };

    this.syncButtons = function(states) {
        this.muteVideo();
        new SuggestionsWatcher(null); // init watcher

        window.lastButtonName = null;

        YouButton.resetCache(); // activity just started
        console.log("ExoUtils.syncButtons: " + JSON.stringify(states));

        // suggestions checked, cancel other tasks
        if (states[PlayerActivity.BUTTON_SUGGESTIONS]) {
            var suggestSelector = PlayerActivityMapping.BUTTON_SUGGESTIONS;
            var suggestBtn = YouButton.fromSelector(suggestSelector);
            suggestBtn.setChecked(true);
            return;
        }

        for (var key in PlayerActivity) {
            var btnId = PlayerActivity[key];
            var isChecked = states[btnId];
            if (isChecked == undefined) // button gone, removed etc..
                continue;
            var selector = PlayerActivityMapping[key];
            var btn = YouButton.fromSelector(selector);
            btn.setChecked(isChecked);
        }
    };

    this.sendAction = function(action) {
        // code that sends string constant to activity
        if (app && app.onGenericStringResult) {
            app.onGenericStringResult(action);
        } else {
            console.log('ExoUtils: app not found');
        }
    };
}

ExoUtils.prototype = new ExoConstants();

// if you intend to remove this var don't forget to do the same inside GetButtonStatesCommand and SyncButtonsCommand classes
window.exoutils = new ExoUtils();