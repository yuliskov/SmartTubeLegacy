/**
 * Common utils for player etc
 */

console.log("Scripts::Running script youtube_utils.js");

var YouTubeUtils = {
    TAG: 'YouTubeUtils',
    LATEST_REVISION: 0,
    FIRST_REVISION: 1,
    VIDEO_SIGN: '#/watch/video',
    VIDEO_LIST_SIGN: '#/watch/loading?list',
    CATALOG_SIGN: ['#/surface', '#/zylon-surface'],
    SEARCH_SIGN: '#/search',
    CHANNEL_SIGN: ['#/channel', '#/zylon-detail-surface', '#/zylon-surface'],
    ACTIVE_ACCOUNT_KEY: 'yt.leanback.default::active-account',

    isComponentDisabled: function(element) {
        var el = element;
        if (Utils.isSelector(element)) {
            el = Utils.$(element);
        }
        var hasClass = Utils.hasClass(el, YouTubeClasses.ELEMENT_DISABLED);
        console.log("ExoUtils.isDisabled: " + element + " " + hasClass);
        return hasClass;
    },

    isComponentHidden: function(element) {
        var el = element;
        if (Utils.isSelector(element)) {
            el = Utils.$(element);
        }
        var hasClass = Utils.hasClass(el, YouTubeClasses.HIDDEN);
        console.log("ExoUtils.isHidden: " + element + " " + hasClass);
        return hasClass;
    },

    /**
     * For other hidden ui parts see exoplayer.css
     */
    hidePlayerBackground: function() {
        Utils.$('body').style.backgroundImage = 'initial';

        // NOTE: for kids
        // var playerEventsRoot = Utils.$(YouTubeSelectors.PLAYER_EVENTS_RECEIVER);
        // if (playerEventsRoot) {
        //     playerEventsRoot.style.backgroundColor = 'initial';
        // }
    },

    /**
     * For other hidden ui parts see exoplayer.css
     */
    showPlayerBackground: function() {
        Utils.$('body').style.backgroundImage = '';

        // NOTE: for kids
        // var playerEventsRoot = Utils.$(YouTubeSelectors.PLAYER_EVENTS_RECEIVER);
        // if (playerEventsRoot) {
        //     playerEventsRoot.style.backgroundColor = '';
        // }
    },

    /**
     * For other hidden ui parts see exoplayer.css
     */
    enablePlayerSuggestions: function() {
        Utils.show(YouTubeSelectors.PLAYER_SUGGESTIONS_LIST);
    },

    /**
     * For other hidden ui parts see exoplayer.css
     */
    disablePlayerSuggestions: function() {
        Utils.hide(YouTubeSelectors.PLAYER_SUGGESTIONS_LIST);
    },

    getPlayerRevision: function() {
        var title = Utils.$(YouTubeSelectors.PLAYER_NEW_TITLE);

        if (title) {
            return this.LATEST_REVISION;
        }

        return this.FIRST_REVISION;
    },

    show: function(elementOrSelector) {
        var el = this.$(elementOrSelector);

        if (el) {
            Utils.removeClass(el, YouTubeClasses.HIDDEN);
        }
    },

    hide: function(elementOrSelector) {
        var el = this.$(elementOrSelector);

        if (el) {
            Utils.addClass(el, YouTubeClasses.HIDDEN);
        }
    },

    focus: function(elem) {
        Log.d(this.TAG, "Setting focus on element: " + EventUtils.toSelector(elem));

        Utils.addClass(elem, YouTubeClasses.ELEMENT_FOCUSED);
        if (elem) {
            // focus sometimes doesn't work without setting a small delay (e.g. voice search button)
            setTimeout(function() {elem.focus();}, 100);
        }
    },

    unfocus: function(elem) {
        Utils.removeClass(elem, YouTubeClasses.ELEMENT_FOCUSED);
    },

    getViewCount: function() {
        var element = Utils.$(YouTubeSelectors.PLAYER_VIEW_COUNT);
        if (element != null) {
            // don't rely on , symbol parsing here! because it depends on localization
            return element.innerHTML;
        }

        // new player ui
        element = Utils.$(YouTubeSelectors.PLAYER_VIDEO_DETAILS);
        if (element != null) {
            var parts = element.innerHTML.split('•');
            if (parts.length >= 2) {
                return parts[1].trim();
            }
        }

        return "";
    },

    getVideoDate: function() {
        var element = Utils.$(YouTubeSelectors.PLAYER_UPLOAD_DATE);
        if (element != null) {
            // don't rely on : symbol parsing here! because it depends on localization
            return element.innerHTML;
        }

        // new player ui
        element = Utils.$(YouTubeSelectors.PLAYER_VIDEO_DETAILS);
        if (element != null) {
            var parts = element.innerHTML.split('•');
            if (parts.length >= 3) {
                return parts[2].trim();
            }
        }

        return "";
    },

    isPlayerClosed: function() {
        return Utils.hasClass(Utils.$(YouTubeSelectors.PLAYER_EVENTS_RECEIVER), YouTubeClasses.HIDDEN); // NOTE: changed for kids
    },

    isAllPlayerUIClosed: function() {
        return !this.isPlayerControlsOpened() && !this.isPlayerSuggestionsOpened() && !this.isOverlayOpened();
    },

    isPlayerControlsOpened: function() {
        return Utils.hasClass(Utils.$(YouTubeSelectors.PLAYER_CONTROLS), YouTubeClasses.ELEMENT_FOCUSED);
    },

    isPlayerSuggestionsOpened: function() {
        return Utils.hasClass(Utils.$(YouTubeSelectors.PLAYER_SUGGESTIONS_LIST), YouTubeClasses.ELEMENT_FOCUSED);
    },

    isPlayerOpened: function() {
        var isOpened =
            location.hash.indexOf(this.VIDEO_SIGN) != -1    ||
            location.hash.indexOf(this.VIDEO_LIST_SIGN) != -1;
        Log.d(this.TAG, "Player is opened: " + isOpened + ", hash: " + location.hash);
        return isOpened;
    },

    isSearchOpened: function() {
        var isOpened = this.isPageOpened(YouTubeSelectors.SEARCH_PAGE);
        Log.d(this.TAG, "Search is opened?: " + isOpened);
        return isOpened;
    },

    isSearchFieldFocused: function() {
        return this.isSearchOpened() && document.activeElement != null && document.activeElement.nodeName == 'INPUT';
    },

    isChannelOpened: function() {
        var isOpened = Utils.contains(location.hash, this.CHANNEL_SIGN);

        Log.d(this.TAG, "Channel is opened: " + isOpened + ", hash: " + location.hash);
        return isOpened;
    },

    isOverlayOpened: function() {
        var overlay = Utils.$(YouTubeSelectors.OVERLAY_PANEL_CONTAINER);
        return overlay && !Utils.hasClass(overlay, YouTubeClasses.HIDDEN);
    },

    isDisabled: function(elem) {
        var hasClass = Utils.hasClass(elem, YouTubeClasses.ELEMENT_DISABLED);
        console.log("ExoUtils: check elem is disabled: " + EventUtils.toSelector(elem) + ' ' + hasClass);
        return hasClass;
    },

    isPlayerVisible: function() {
        return Utils.isVisible('video');
    },

    isPlayerPaused: function() {
        var v = Utils.$('video');
        return v && v.paused;
    },

    playerPlay: function() {
        var v = Utils.$('video');
        v && v.pause();
        v && v.play();
    },

    getPlayer: function() {
        return Utils.$('video');
    },

    isPageOpened: function(elementOrSelector) {
        return !Utils.hasClass(Utils.$(elementOrSelector), YouTubeClasses.HIDDEN);
    },

    isUserLogged: function() {
        var key = localStorage.getItem(this.ACTIVE_ACCOUNT_KEY);

        return key != null;
    },

    addExitListener: function(callback) {
        if (callback == null) {
            return;
        }

        var $this = this;

        EventUtils.addListener(YouTubeSelectors.OVERLAY_PANEL_CONTAINER, DefaultEvents.FOCUS, function() {
            Log.d($this.TAG, "Overlay is shown!");

            var rows = Utils.$$(YouTubeSelectors.OVERLAY_PANEL_MENU_ITEM);

            // Since Exit dialog has only one menu item
            var isOneRow = rows && rows.length == 1;
            var isCatalog = Utils.contains(location.href, $this.CATALOG_SIGN);
            if (isOneRow && isCatalog) {
                callback();
            }
        });
    },

    triggerBack: function() {
        EventUtils.triggerEnter(YouTubeSelectors.BUTTON_BACK);
    },

    openPlayerControls: function() {
        if (this.isPlayerOpened()) {
            Log.d(this.TAG, "Showing player ui...");
            EventUtils.triggerEnter(YouTubeSelectors.PLAYER_EVENTS_RECEIVER);
        }
    },

    closePlayerControls: function() {
        if (this.isOverlayOpened()) {
            Log.d(this.TAG, "Closing player favorites...");

            EventUtils.triggerEvent(YouTubeSelectors.OVERLAY_PANEL, DefaultEvents.KEY_DOWN, DefaultKeys.ESC);
            EventUtils.triggerEvent(YouTubeSelectors.OVERLAY_PANEL, DefaultEvents.KEY_UP, DefaultKeys.ESC);
        } else if (this.isPlayerControlsOpened() || this.isPlayerSuggestionsOpened()) {
            Log.d(this.TAG, "Closing player suggestions...");

            EventUtils.triggerEvent(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, DefaultEvents.KEY_DOWN, DefaultKeys.ESC);
            EventUtils.triggerEvent(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, DefaultEvents.KEY_UP, DefaultKeys.ESC);
        }
    },

    pressBack: function() {
        if (this.isPlayerOpened()) {
            Log.d(this.TAG, "Pressing BACK...");

            EventUtils.triggerEvent(document.activeElement, DefaultEvents.KEY_DOWN, DefaultKeys.ESC);
            EventUtils.triggerEvent(document.activeElement, DefaultEvents.KEY_UP, DefaultKeys.ESC);
        }
    },

    isExoPlayerOpen: function() {
        return this.sExoPlayerOpen;
    },

    moveRight: function() {
        if (document.activeElement) {
            EventUtils.triggerEvent(document.activeElement, DefaultEvents.KEY_DOWN, DefaultKeys.RIGHT);
            EventUtils.triggerEvent(document.activeElement, DefaultEvents.KEY_UP, DefaultKeys.RIGHT);
        }
    },

    moveUp: function() {
        if (document.activeElement) {
            EventUtils.triggerEvent(document.activeElement, DefaultEvents.KEY_DOWN, DefaultKeys.UP);
            EventUtils.triggerEvent(document.activeElement, DefaultEvents.KEY_UP, DefaultKeys.UP);
        }
    },

    togglePlayerOptions: function() {
        var el = Utils.$(YouTubeSelectors.PLAYER_MORE_BUTTON);

        if (el) {
            EventUtils.triggerEnter(el); // click on options button
        } else {
            Log.e(this.TAG, "Oops. Player options button not found!!");
        }
    },

    resetPlayerOptions: function() {
        if (Utils.$(YouTubeSelectors.PLAYER_MORE_BUTTON_TOGGLED)) { // options opened before
            this.togglePlayerOptions(); // click on options button
        }
    },

    resetFocus: function() {
        var root = Utils.$(YouTubeSelectors.PLAYER_EVENTS_RECEIVER);

        root && root.focus();
    },

    dumpUiState: function() {
        Log.d(this.TAG, "Is player opened: " + this.isPlayerOpened());
        Log.d(this.TAG, "Is player controls opened: " + this.isPlayerControlsOpened());
        Log.d(this.TAG, "Is player suggestions opened: " + this.isPlayerSuggestionsOpened());
        Log.d(this.TAG, "Is player overlay opened: " + this.isOverlayOpened());
    },

    closePlayerIfOpened: function() {
        if (this.isPlayerOpened()) {
            Log.d(this.TAG, "Closing player...");
            this.pressBack();
        }
    },

    /**
     * Detect video clip based on duration and author name
     */
    isAgeRestrictedVideo: function(videoData) {
        if (!videoData) {
            return false;
        }

        if (videoData.type == window.VideoStatsWatcherAddon.VIDEO_TYPE_DEFAULT ||
            videoData.type == window.VideoStatsWatcherAddon.VIDEO_TYPE_UNDEFINED) { // video launched from suggestions
            if (Utils.contains(videoData.author, 
                ["Official", "gameplay", "Ubisoft", "PlayStation",
                    "Rockstar", "Battlefield", "Cyberpunk", "Distructive",
                    "Resident", "Just Cause"])) { // channels with age restricted content
                return true;
            }
        }

        return false;
    }
};