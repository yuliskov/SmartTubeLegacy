console.log("Scripts::Running core script exo_helpers.js");

/**
 * Note: if you intend to rename this var don't forget to do the same inside
 * <b>GetButtonStatesCommand</b> and <b>SyncButtonsCommand</b> classes<br/>
 *
 * Usage: <b>PressCommandBase.java</b><br/>
 * <code>ExoUtils.triggerEvent(ExoUtils.$('%s'), 'keyup', 13);</code><br/>
 *
 * Usage: <b>PressCommandBase.java</b><br/>
 * <code>ExoUtils.isDisabled(targetButton) && app && app.onGenericBooleanResult(false, %s);</code>
 * @constructor empty
 */
var ExoUtils = {
    isComponentDisabled: function(element) {
        var el = element;
        if (Utils.isSelector(element)) {
            el = Utils.$(element);
        }
        var hasClass = Utils.hasClass(el, ExoConstants.disabledClass);
        console.log("ExoUtils.isDisabled: " + element + " " + hasClass);
        return hasClass;
    },

    isComponentHidden: function(element) {
        var el = element;
        if (Utils.isSelector(element)) {
            el = Utils.$(element);
        }
        var hasClass = Utils.hasClass(el, ExoConstants.hiddenClass);
        console.log("ExoUtils.isHidden: " + element + " " + hasClass);
        return hasClass;
    },

    // events order:
    // emptied
    // loadstart
    // loadedmetadata
    // loadeddata (first frame of the video has been loaded)
    // playing
    preparePlayer: function() {
        this.disablePlayerUi();
        var player = Utils.$('video');
        if (!player || this.preparePlayerDone)
            return;

        Utils.overrideProp2(player, 'volume', 0);

        // we can't pause video because history will not work
        function onLoad() {
            console.log('ExoUtils: video has been loaded into webview... force start playback');
            // msg 4 future me
            // 'paused' video won't invoke history update
            // don't call pause!!! or video remains paused event after play
            player.play();
        }

        // once player is created it will be reused by other videos
        // 'loadeddata' is first event when video can be muted
        player.addEventListener(DefaultEvents.PLAYER_DATA_LOADED, onLoad, false);

        this.preparePlayerDone = true;
    },

    getViewCount: function() {
        var element = Utils.$(ExoConstants.viewCountSelector);
        if (element != null) {
            // don't rely on : symbol parsing here! because it depends on localization
            return element.innerHTML;
        }

        return "";
    },

    getVideoDate: function() {
        var element = Utils.$(ExoConstants.uploadDateSelector);
        if (element != null) {
            // don't rely on : symbol parsing here! because it depends on localization
            return element.innerHTML;
        }

        element = Utils.$(ExoConstants.videoDetails);
        if (element != null) {
            var parts = element.innerHTML.split('•');
            if (parts.length == 3) {
                return parts[2].trim();
            }
        }

        return "";
    },

    getScreenWidth: function() {
        return window.innerWidth;
    },

    enablePlayerUi: function() {
        Utils.show(ExoConstants.bottomUiSelector);
        Utils.show(ExoConstants.cornerButtonsSelector);
    },

    disablePlayerUi: function() {
        Utils.hide(ExoConstants.bottomUiSelector);
        Utils.hide(ExoConstants.cornerButtonsSelector);
    },

    // supply selector list
    getButtonStates: function() {
        this.preparePlayer();
        new SuggestionsWatcher(null); // init watcher

        ExoButton.resetCache(); // activity just started

        var states = {};

        // NOTE: we can't delay here so process in reverse order
        var reversedKeys = Object.keys(PlayerActivityMapping).reverse();

        for (var idx in reversedKeys) {
            var key = reversedKeys[idx];
            var selector = PlayerActivityMapping[key];
            var btn = ExoButton.fromSelector(selector);
            var newName = PlayerActivity[key];
            var isChecked = btn.getChecked();
            if (isChecked === null) // exclude disabled buttons from result
                continue;
            states[newName] = isChecked;
        }

        states[PlayerActivity.VIDEO_DATE] = this.getVideoDate();
        states[PlayerActivity.SCREEN_WIDTH] = this.getScreenWidth();
        states[PlayerActivity.VIDEO_VIEW_COUNT] = this.getViewCount();

        // don't let app to close video player (see ActionsReceiver.java)
        if (window.lastButtonName && window.lastButtonName == PlayerActivity.TRACK_ENDED) {
            states[PlayerActivity.BUTTON_NEXT] = null;
        }

        console.log("ExoUtils.getButtonStates: " + JSON.stringify(states));
        return states;
    },

    syncButtons: function(states) {
        var $this = this;
        // 'likes not saved' fix
        setTimeout(function() {
            $this.syncButtonsReal(states);
        }, 100);
    },

    syncButtonsReal: function(states) {
        this.preparePlayer();
        new SuggestionsWatcher(null); // init watcher

        window.lastButtonName = null;

        ExoButton.resetCache(); // activity just started
        console.log("ExoUtils.syncButtons: " + JSON.stringify(states));

        for (var key in PlayerActivity) {
            var btnId = PlayerActivity[key];
            var isChecked = states[btnId];
            if (isChecked == undefined) // button gone, removed etc..
                continue;
            var selector = PlayerActivityMapping[key];
            var btn = ExoButton.fromSelector(selector);
            btn.setChecked(isChecked);
        }
    },

    sendAction: function(action) {
        // code that sends string constant to activity
        if (app && app.onGenericStringResult) {
            app.onGenericStringResult(action);
        } else {
            console.log('ExoUtils: app not found');
        }
    },

    playerIsClosed: function() {
        return Utils.hasClass(Utils.$(ExoConstants.playerUiSelector), ExoConstants.emptyModelClass);
    }
};