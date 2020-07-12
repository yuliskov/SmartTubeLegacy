/**
 * Description:
 * Opens search page when pressing voice input button.
 */

console.log("Scripts::Running script voice_search.js");

/**
 * NOTE: object is used through the java code<br/>
 * NOTE: if you intend to rename/delete this var don't forget to do the same inside<br/>
 * <b>VoiceSearchConnector.java</b><br/>
 */
window.VoiceSearch = {
    TAG: 'VoiceSearch',
    SEARCH_PAGE_URL: '/search?resume',
    SEARCH_PAGE_TAG: 'search',
    SHORTCUT_PRESSED: 'shortcut_pressed',

    init: function() {
        // NOTE: you need to change user-agent too

        if (DeviceUtils.isMicAvailable()) {
            this.overrideVoiceCaps();
        } else {
            this.disableBrowserVoiceFunctions();
        }
    },

    disableBrowserVoiceFunctions: function() {
        window.SpeechRecognition = window.webkitSpeechRecognition = null;
    },

    overrideVoiceCaps: function() {
        // hacks to made app think that there is a voice input

        var $this = this;

        window.SpeechRecognition = window.webkitSpeechRecognition = function() {
            this.start = function() {
                Log.d($this.TAG, "user have clicked on the voice search button");

                // open my voice dialog
                DeviceUtils.sendMessage(DeviceUtils.MESSAGE_MIC_CLICKED);

                // use hack to close youtube voice dialog
                this.onerror && this.onerror({error: ''});
            };

            this.abort = function() {

            };
        };
    },

    open: function(searchText) {
        Log.d(this.TAG, "going to the search page");

        this.navigateToTheSearchPage();
        this.checkSearchOpened();

        if (searchText != this.SHORTCUT_PRESSED) {
            this.typeSearchText(searchText);
            this.commitChanges();
            this.selectResultsRow();
            this.unfocusMicButton();
        } else {
            // shortcut pressed: focus search field
            YouTubeUtils.moveRight();
            YouTubeUtils.moveUp();
            YouTubeUtils.moveUp();
        }
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

        YouTubePlayerUtils.closePlayerIfOpened();

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
        YouTubeUtils.clickOnSearchField();
    },

    selectResultsRow: function() {
        // wait till search complete
        setTimeout(function() {
            YouTubeUtils.clickOnStartSearchButton();
        }, 1000);
    },

    unfocusMicButton: function() {
        if (this.micButton) {
            this.micButton.unfocus();
        }
    }
};

VoiceSearch.init();