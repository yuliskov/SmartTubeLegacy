/**
 * Helps to add new buttons to the player's UI.
 */

console.log("Scripts::Running script search_page_ui_manager.js");

var SearchPageUiManager = {
    TAG: 'SearchUiManager',
    LEFT_BUTTON_SELECTOR: YouTubeSelectors.SEARCH_SUGGESTIONS,
    RIGHT_BUTTON_SELECTOR: YouTubeSelectors.SEARCH_KEYBOARD,

    /**
     * Search page: creates button from the supplied data and adds it
     * @param buttonDescription object with onClick method
     */
    insertButton: function(buttonDescription) {
        // create buttons
        this.centerBtn = UiButton.createMicButton(buttonDescription);
        this.leftBtn = UiButton.fromSelector(this.LEFT_BUTTON_SELECTOR);
        this.rightBtn = UiButton.fromSelector(this.RIGHT_BUTTON_SELECTOR);

        SearchPageUiWatcher.onUiUpdate(this);
    },

    onUiUpdate: function() {
        Log.d(this.TAG, 'onUiUpdate');

        // begin to handle movements
        SearchPageUiWatcher.handleMovements([this.leftBtn, this.centerBtn, this.rightBtn]);

        // add to player's ui
        UiHelpers.replaceBtn(YouTubeSelectors.SEARCH_MIC_BUTTON, this.centerBtn);
    }
};