/**
 * Description:
 * Opens search page when pressing voice input button.
 */

console.log("Scripts::Running script voice_search.js");

var VoiceSearch = {
    TAG: 'VoiceSearch',
    SEARCH_PAGE_URL: '/search?resume',
    SEARCH_PAGE_TAG: 'search',

    init: function() {
        // this.overrideVoiceCaps();

        if (DeviceUtils.isMicAvailable()) {
            this.addMicListener();
        }
    },

    addMicListener: function() {
        this.micButton = new VoiceSearchButton();
        SearchPageUiManager.insertButton(this.micButton);
    },

    /**
     * NOTE: App could hang after this
     */
    overrideVoiceCaps: function() {
        if (window.SpeechRecognition || window.webkitSpeechRecognition) {
            return;
        }

        window.webkitSpeechRecognition = function() {};
    },

    open: function(searchText) {
        Log.d(this.TAG, "going to the search page");

        this.navigateToTheSearchPage();
        this.checkSearchOpened();
        this.typeSearchText(searchText);
        this.commitChanges();
        this.selectResultsRow();
        this.unfocusMicButton();
    },

    checkSearchOpened: function() {
        if (!YouTubeUtils.isSearchOpened()) {
            EventUtils.triggerEnter(YouTubeSelectors.SEARCH_SIDE_BUTTON);
        }
    },

    navigateToTheSearchPage: function() {
        if (YouTubeUtils.isSearchOpened()) {
            this.moveSelectionFromResultRow();
            return;
        }

        location.hash = this.SEARCH_PAGE_URL;
    },

    /**
     * Trying to hide pop-up keyboard
     */
    moveSelectionFromResultRow: function() {
        if (Utils.hasClass(
            Utils.$(YouTubeSelectors.SEARCH_RESULTS_ROW),
            YouTubeClasses.ELEMENT_FOCUSED)) {
            // triggerEvent($('#search'), 'keydown', 38)
            EventUtils.triggerEvent(YouTubeSelectors.SEARCH_PAGE, DefaultEvents.KEY_DOWN, DefaultKeys.UP);
        }
    },

    typeSearchText: function(searchText) {
        var input = Utils.$(YouTubeSelectors.SEARCH_INPUT_FIELD);
        if (!input) {
            return;
        }

        input.value = searchText;
    },

    commitChanges: function() {
        EventUtils.triggerEvent(
            YouTubeSelectors.SEARCH_INPUT_FIELD,
            DefaultEvents.ON_TEXT_TYPE,
            DefaultKeys.ENTER);
    },

    selectResultsRow: function() {
        // wait till search complete
        setTimeout(function() {
            EventUtils.triggerEnter(YouTubeSelectors.SEARCH_START_BUTTON);
        }, 1000);
    },

    unfocusMicButton: function() {
        if (this.micButton) {
            this.micButton.unfocus();
        }
    }
};

VoiceSearch.init();