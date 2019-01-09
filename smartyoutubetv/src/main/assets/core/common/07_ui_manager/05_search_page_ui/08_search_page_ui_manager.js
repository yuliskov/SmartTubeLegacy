/**
 * Helps to add new buttons to the player's UI.
 */

console.log("Scripts::Running script search_page_ui_manager.js");

var SearchPageUiManager = {
    TAG: 'SearchUiManager',
    LEFT_BUTTON_SELECTOR: YouTubeSelectors.SEARCH_SUGGESTIONS,
    RIGHT_BUTTON_SELECTOR: YouTubeSelectors.SEARCH_KEYBOARD_GRID,
    uiWatcher: new UiWatcher(YouTubeSelectors.SEARCH_PAGE),

    /**
     * Search page: creates button from the supplied data and adds it
     * @param buttonDescription object with onClick method
     */
    insertButton: function(buttonDescription) {
        // create buttons
        this.centerBtn = UiButton.createMicButton(buttonDescription);
        this.leftBtn = UiButton.fromSelector(this.LEFT_BUTTON_SELECTOR);
        this.rightBtn = UiButton.fromSelector(this.RIGHT_BUTTON_SELECTOR);
        
        this.setupUiChangeListener();
    },

    onUiUpdate: function() {
        Log.d(this.TAG, 'onUiUpdate');

        // begin to handle movements
        this.uiWatcher.handleMovements([this.leftBtn, this.centerBtn, this.rightBtn]);

        // add to player's ui
        UiHelpers.replaceBtn(YouTubeSelectors.SEARCH_MIC_BUTTON, this.centerBtn);
    },

    setupUiChangeListener: function() {
        if (this.setupUiChangeIsDone) {
            return;
        }

        this.setupUiChangeIsDone = true;

        var $this = this;
        var onUiChange = function() {
            Log.d($this.TAG, "Running ui change listener");
            $this.onUiUpdate();
        };

        EventUtils.addListener(YouTubeSelectors.SEARCH_PAGE, YouTubeEvents.MODEL_CHANGED_EVENT, function(e) {
            if (!Utils.hasClass(e.target, YouTubeClasses.NO_MODEL)) { // trigger when user opens additional buttons
                setTimeout(onUiChange, 500); // let button finish initialization
            }
        });

        EventUtils.onLoad(function() {
            setTimeout(function() {
                if (Utils.$(YouTubeSelectors.SEARCH_PAGE)) {
                    onUiChange();
                }
            }, 3000);
        });
    }
};