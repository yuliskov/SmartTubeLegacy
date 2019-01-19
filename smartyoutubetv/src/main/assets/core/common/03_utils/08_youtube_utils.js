/**
 * Common utils for player etc
 */

console.log("Scripts::Running script youtube_utils.js");

var YouTubeUtils = {
    TAG: 'YouTubeUtils',
    FIRST_REVISION: 'first_revision',
    SECOND_REVISION: 'second_revision',
    OPENED_VIDEO_SIGN: '#/watch/video',

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
        Utils.show(ExoConstants.bottomUiSelector);
    },

    /**
     * For other hidden ui parts see exoplayer.css
     */
    disablePlayerSuggestions: function() {
        Utils.hide(ExoConstants.bottomUiSelector);
    },

    playerIsClosed: function() {
        return Utils.hasClass(Utils.$(ExoConstants.playerUiSelector), ExoConstants.noModelClass);
    },

    playerIsClosed2: function() {
        return location.hash.indexOf(this.OPENED_VIDEO_SIGN) == -1;
    },

    isDisabled: function(elem) {
        var hasClass = Utils.hasClass(elem, ExoConstants.disabledClass);
        console.log("ExoUtils: check elem is disabled: " + EventUtils.toSelector(elem) + ' ' + hasClass);
        return hasClass;
    },

    getPlayerRevision: function() {
        var title = Utils.$(ExoConstants.newPlayerTitleSelector);

        if (title) {
            return this.SECOND_REVISION;
        }

        return this.FIRST_REVISION;
    },

    show: function(elementOrSelector) {
        var el = this.$(elementOrSelector);

        if (el) {
            this.removeClass(el, YouTubeClasses.HIDDEN);
        }
    },

    hide: function(elementOrSelector) {
        var el = this.$(elementOrSelector);

        if (el) {
            this.addClass(el, YouTubeClasses.HIDDEN);
        }
    },

    focus: function(elem) {
        Log.d(this.TAG, "Setting focus on element: " + EventUtils.toSelector(elem));

        this.addClass(elem, YouTubeClasses.ELEMENT_FOCUSED);
        if (elem) {
            // focus sometimes doesn't work without setting a small delay (e.g. voice search button)
            setTimeout(function() {elem.focus();}, 100);
        }
    },

    unfocus: function(elem) {
        this.removeClass(elem, YouTubeClasses.ELEMENT_FOCUSED);
    },

    getViewCount: function() {
        var element = Utils.$(ExoConstants.viewCountSelector);
        if (element != null) {
            // don't rely on , symbol parsing here! because it depends on localization
            return element.innerHTML;
        }

        // new player ui
        element = Utils.$(ExoConstants.videoDetailsSelector);
        if (element != null) {
            var parts = element.innerHTML.split('•');
            if (parts.length >= 2) {
                return parts[1].trim();
            }
        }

        return "";
    },

    getVideoDate: function() {
        var element = Utils.$(ExoConstants.uploadDateSelector);
        if (element != null) {
            // don't rely on : symbol parsing here! because it depends on localization
            return element.innerHTML;
        }

        // new player ui
        element = Utils.$(ExoConstants.videoDetailsSelector);
        if (element != null) {
            var parts = element.innerHTML.split('•');
            if (parts.length >= 3) {
                return parts[2].trim();
            }
        }

        return "";
    }
};