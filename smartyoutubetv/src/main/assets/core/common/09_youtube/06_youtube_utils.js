/**
 * Common utils for player etc
 */

console.log("Scripts::Running script youtube_utils.js");

var YouTubeUtils = {
    TAG: 'YouTubeUtils',
    LATEST_REVISION: 0,
    FIRST_REVISION: 1,
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

    isDisabled: function(elem) {
        var hasClass = Utils.hasClass(elem, YouTubeClasses.ELEMENT_DISABLED);
        console.log("ExoUtils: check elem is disabled: " + EventUtils.toSelector(elem) + ' ' + hasClass);
        return hasClass;
    },

    isPageOpened: function(elementOrSelector) {
        return !Utils.hasClass(Utils.$(elementOrSelector), YouTubeClasses.HIDDEN);
    },

    isUserLogged: function() {
        var key = localStorage.getItem(this.ACTIVE_ACCOUNT_KEY);

        return key != null;
    },

    triggerBack: function() {
        EventUtils.triggerEnter(YouTubeSelectors.BUTTON_BACK);
    },

    pressBack: function() {
        if (YouTubePlayerUtils.isPlayerOpened()) {
            Log.d(this.TAG, "Pressing BACK...");

            EventUtils.triggerEvent(document.activeElement, DefaultEvents.KEY_DOWN, DefaultKeys.ESC);
            EventUtils.triggerEvent(document.activeElement, DefaultEvents.KEY_UP, DefaultKeys.ESC);
        }
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

    pressDown: function() {
        if (document.activeElement) {
            EventUtils.triggerEvent(document.activeElement, DefaultEvents.KEY_DOWN, DefaultKeys.DOWN);
            EventUtils.triggerEvent(document.activeElement, DefaultEvents.KEY_UP, DefaultKeys.DOWN);
        }
    },

    pressEsc: function() {
        if (document.activeElement) {
            EventUtils.triggerEvent(document.activeElement, DefaultEvents.KEY_DOWN, DefaultKeys.ESC);
            EventUtils.triggerEvent(document.activeElement, DefaultEvents.KEY_UP, DefaultKeys.ESC);
        }
    },

    resetFocus: function() {
        var root = Utils.$(YouTubeSelectors.PLAYER_EVENTS_RECEIVER);

        root && root.focus();
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
    },

    isExitDialogShown: function(element) {
        var rows = Utils.$$(YouTubeSelectorsV2.OVERLAY_PANEL_BUTTON, element);

        // Since Exit dialog has only one menu item
        var isOneRow = rows && rows.length == 1;
        var isCatalog = Utils.contains(location.href, YouTubeConstants.CATALOG_SIGN);

        return isOneRow && isCatalog;
    },

    clickOnSearchField: function() {
        EventUtils.triggerEvent(
            YouTubeSelectors.SEARCH_INPUT_FIELD,
            DefaultEvents.ON_TEXT_TYPE,
            DefaultKeys.ENTER);
    },

    clickOnStartSearchButton: function() {
        EventUtils.triggerEnter(YouTubeSelectors.SEARCH_START_BUTTON);
    }
};