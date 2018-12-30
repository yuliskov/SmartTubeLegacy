/**
 * Description:
 * Opens search page when pressing voice input button.
 */

console.log("Scripts::Running script voice_search.js");

var VoiceSearch = {
    TAG: 'VoiceSearch',
    SEARCH_PAGE_URL: '/search?resume',
    SEARCH_PAGE_TAG: 'search',
    MIC_CLICKED_MESSAGE: 'mic_clicked_message',

    init: function() {
        this.overrideVoiceCaps();
        this.addMicListener();
    },

    addMicListener: function() {
        // EventUtils.addListener(YouTubeSelectors.SEARCH_MIC_BUTTON, DefaultEvents.)
    },

    onMicClicked: function() {
        DeviceUtils.sendMessage(this.MIC_CLICKED_MESSAGE);
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
        this.typeSearchText(searchText);
        this.commitChanges();
    },

    navigateToTheSearchPage: function() {
        if (location.hash.indexOf(this.SEARCH_PAGE_TAG) != -1) {
            return;
        }

        location.hash = this.SEARCH_PAGE_URL;
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
    }
};

VoiceSearch.init();