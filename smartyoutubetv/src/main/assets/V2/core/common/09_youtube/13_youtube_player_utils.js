/**
 * Event abstractions for all youtube app
 */

console.log("Scripts::Running script youtube_player_utils.js");

var YouTubePlayerUtils = {
    TAG: 'YouTubePlayerUtils',
    VIDEO_SIGN: '#/watch/video',
    VIDEO_LIST_SIGN: '#/watch/loading?list',
    clickOnPlayerOptions: function() {
        var el = Utils.$(YouTubeSelectorsV2.PLAYER_MORE_BUTTON);

        if (el) {
            EventUtils.triggerEnter(el); // click on options button
        } else {
            Log.e(this.TAG, "Oops. Player options button not found!!");
        }
    },
    isPlayerOptionsToggled: function() {
        return Utils.$(YouTubeSelectorsV2.PLAYER_QUALITY_BUTTON);
    },
    closePlayerIfOpened: function() {
        if (this.isPlayerOpened()) {
            Log.d(this.TAG, "Closing player...");
            this.pressBack();
        }
    },
    resetPlayerOptions: function() {
        if (YouTubePlayerUtils.isPlayerOptionsToggled()) { // options opened before
            YouTubePlayerUtils.clickOnPlayerOptions(); // click on options button
        }
    },
    closePlayerControls: function() {
        if (this.isPlayerOverlayOpened()) {
            Log.d(this.TAG, "Closing player favorites...");

            EventUtils.triggerEvent(YouTubeSelectors.OVERLAY_PANEL, DefaultEvents.KEY_DOWN, DefaultKeys.ESC);
            EventUtils.triggerEvent(YouTubeSelectors.OVERLAY_PANEL, DefaultEvents.KEY_UP, DefaultKeys.ESC);
        } else if (this.isPlayerControlsOpened() || this.isPlayerSuggestionsOpened()) {
            Log.d(this.TAG, "Closing player suggestions...");

            EventUtils.triggerEvent(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, DefaultEvents.KEY_DOWN, DefaultKeys.ESC);
            EventUtils.triggerEvent(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, DefaultEvents.KEY_UP, DefaultKeys.ESC);
        }
    },
    openPlayerControls: function() {
        if (this.isPlayerOpened()) {
            Log.d(this.TAG, "Showing player ui...");
            EventUtils.triggerEnter(YouTubeSelectors.PLAYER_EVENTS_RECEIVER);
        }
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
    showPlayerSuggestions: function() {
        EventUtils.triggerEvent(YouTubeSelectorsV2.PLAYER_EVENTS_RECEIVER, DefaultEvents.KEY_DOWN, DefaultKeys.DOWN);
        EventUtils.triggerEvent(YouTubeSelectorsV2.PLAYER_EVENTS_RECEIVER, DefaultEvents.KEY_UP, DefaultKeys.DOWN);
    },
    getPlayerRevision: function() {
        var title = Utils.$(YouTubeSelectors.PLAYER_NEW_TITLE);

        if (title) {
            return this.LATEST_REVISION;
        }

        return this.FIRST_REVISION;
    },
    isPlayerClosed: function() {
        var isClosed = Utils.hasClass(Utils.$(YouTubeSelectors.PLAYER_EVENTS_RECEIVER), YouTubeClasses.HIDDEN);
        Log.d(this.TAG, "Player is closed: " + isClosed);
        return isClosed; // NOTE: changed for kids
    },
    isAllPlayerUIClosed: function() {
        return !this.isPlayerControlsOpened() && !this.isPlayerSuggestionsOpened() && !this.isPlayerOverlayOpened();
    },
    isPlayerControlsOpened: function() {
        var isOpened = Utils.hasClass(Utils.$(YouTubeSelectors.PLAYER_CONTROLS), YouTubeClasses.ELEMENT_FOCUSED);
        Log.d(this.TAG, "Player controls opened: " + isOpened);
        return isOpened;
    },
    isPlayerSuggestionsOpened: function() {
        var isOpened = Utils.hasClass(Utils.$(YouTubeSelectors.PLAYER_SUGGESTIONS_LIST), YouTubeClasses.ELEMENT_FOCUSED);
        Log.d(this.TAG, "Player suggestions opened: " + isOpened);
        return isOpened;
    },
    isPlayerOpened: function() {
        var isOpened = Utils.$(YouTubeSelectorsV2.PLAYER_CONTAINER_ENABLED);
        Log.d(this.TAG, "Player is opened: " + isOpened);
        return isOpened;
    },
    isPlayerOverlayOpened: function() {
        var overlay = Utils.$(YouTubeSelectors.OVERLAY_PANEL_CONTAINER);
        var isOpened = overlay && !Utils.hasClass(overlay, YouTubeClasses.HIDDEN);
        Log.d(this.TAG, "Player is overlay opened: " + isOpened);
        return isOpened;
    },
    dumpUiState: function() {
        Log.d(this.TAG, "Is player opened: " + this.isPlayerOpened());
        Log.d(this.TAG, "Is player controls opened: " + this.isPlayerControlsOpened());
        Log.d(this.TAG, "Is player suggestions opened: " + this.isPlayerSuggestionsOpened());
        Log.d(this.TAG, "Is player overlay opened: " + this.isPlayerOverlayOpened());
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
    isExoPlayerOpen: function() {
        return this.sExoPlayerOpen;
    }
};
