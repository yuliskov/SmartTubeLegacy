/**
 * Helps to add new buttons to the player's UI.
 */

console.log("Scripts::Running script ui_manager.js");

var UiManager = {
    LEFT_BUTTON_SELECTOR: [YouTubeSelectors.PLAYER_CAPTIONS_BUTTON, YouTubeSelectors.PLAYER_CHANNEL_BUTTON],
    RIGHT_BUTTON_SELECTOR: YouTubeSelectors.PLAYER_PLAY_BUTTON,

    /**
     * Creates button from the supplied data and adds it to the player
     * @param buttonData object with onClick method
     */
    addToPlayer: function(buttonData) {
        // create buttons
        this.centerBtn = UiButton.fromData(buttonData);
        this.leftBtn = UiButton.fromSelector(this.LEFT_BUTTON_SELECTOR);
        this.rightBtn = UiButton.fromSelector(this.RIGHT_BUTTON_SELECTOR);

        // begin to handle movements
        UiWatcher.handleMovements([this.leftBtn, this.centerBtn, this.rightBtn]);

        // add to player's ui
        UiHelpers.insertAfter(this.leftBtn.getElem(), this.centerBtn.getElem());
    }
};