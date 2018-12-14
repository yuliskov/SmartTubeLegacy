/**
 * Helps to add new buttons to the player's UI.
 */

console.log("Scripts::Running script ui_manager.js");

var UiManager = {
    LEFT_BUTTON_SELECTOR: [YouTubeSelectors.PLAYER_CAPTIONS_BUTTON, YouTubeSelectors.PLAYER_CHANNEL_BUTTON],
    RIGHT_BUTTON_SELECTOR: YouTubeSelectors.PLAYER_PLAY_BUTTON,

    /**
     * Creates button from the supplied data and adds it to the player
     * @param buttonDescription object with onClick method
     */
    insertButton: function(buttonDescription) {
        this.buttonDescription = buttonDescription;
        UiWatcher.onUiUpdate(this);
    },

    onUiUpdate: function() {
        // create buttons
        this.centerBtn = UiButton.fromData(this.buttonDescription);
        this.leftBtn = UiButton.fromSelector(this.LEFT_BUTTON_SELECTOR);
        this.rightBtn = UiButton.fromSelector(this.RIGHT_BUTTON_SELECTOR);


        // var moreBtn = Utils.$(YouTubeSelectors.PLAYER_MORE_BUTTON);
        // if (Utils.hasClass(moreBtn, YouTubeClasses.BUTTON_TOGGLED)) {
        //     UiHelpers.removeBtn(this.centerBtn);
        //     return;
        // }

        // begin to handle movements
        UiWatcher.handleMovements([this.leftBtn, this.centerBtn, this.rightBtn]);

        // add to player's ui
        UiHelpers.insertAfter(this.leftBtn, this.centerBtn);
    },

    showPlayerUi: function() {

    },

    hidePlayerUi: function() {
        
    },

    enablePlayerUi: function() {

    },

    disablePlayerUi: function() {

    },

    enablePlayerTransparency: function() {
        
    },
    
    disablePlayerTransparency: function() {
        
    }
};