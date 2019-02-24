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
    SEARCH_SIGN: '#/search',
    CHANNEL_SIGN: '#/channel',

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
    },

    /**
     * For other hidden ui parts see exoplayer.css
     */
    showPlayerBackground: function() {
        Utils.$('body').style.backgroundImage = '';
    },

    /**
     * For other hidden ui parts see exoplayer.css
     */
    enablePlayerSuggestions: function() {
        Utils.show(YouTubeSelectors.PLAYER_SUGGESTIONS);
    },

    /**
     * For other hidden ui parts see exoplayer.css
     */
    disablePlayerSuggestions: function() {
        Utils.hide(YouTubeSelectors.PLAYER_SUGGESTIONS);
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
        return Utils.hasClass(Utils.$(YouTubeSelectors.PLAYER_EVENTS_RECEIVER), YouTubeClasses.NO_MODEL);
    },

    isPlayerControlsClosed: function() {
        return Utils.hasClass(Utils.$(YouTubeSelectors.PLAYER_EVENTS_RECEIVER), YouTubeClasses.WATCH_IDLE_CLASS);
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

    isChannelOpened: function() {
        var isOpened = location.hash.indexOf(this.CHANNEL_SIGN) != -1;
        Log.d(this.TAG, "Channel is opened: " + isOpened + ", hash: " + location.hash);
        return isOpened;
    },

    isOverlayOpened: function() {
        return !Utils.hasClass(Utils.$(YouTubeSelectors.OVERLAY_PANEL_CONTAINER), YouTubeClasses.HIDDEN);
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
    }
};