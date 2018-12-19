/**
 * Helps to add new buttons to the player's UI.
 */

console.log("Scripts::Running script ui_manager.js");

var UiManager = {
    TAG: 'UiManager',
    LEFT_BUTTON_SELECTOR: [YouTubeSelectors.PLAYER_SUBS_BUTTON, YouTubeSelectors.PLAYER_CHANNEL_BUTTON],
    RIGHT_BUTTON_SELECTOR: [YouTubeSelectors.PLAYER_PREV_BUTTON, YouTubeSelectors.PLAYER_PLAY_BUTTON],

    /**
     * Creates button from the supplied data and adds it to the player
     * @param buttonDescription object with onClick method
     */
    insertButton: function(buttonDescription) {
        // create buttons
        this.centerBtn = UiButton.fromData(buttonDescription);
        this.leftBtn = UiButton.fromSelector(this.LEFT_BUTTON_SELECTOR);
        this.rightBtn = UiButton.fromSelector(this.RIGHT_BUTTON_SELECTOR);

        UiWatcher.onUiUpdate(this);
    },

    onUiUpdate: function() {
        Log.d(this.TAG, 'onUiUpdate');

        // hide my btn when options is opened
        if (this.isMoreBtnToggled()) {
            UiHelpers.removeBtn(this.centerBtn);
            UiWatcher.resetEvents();
            return;
        }

        // begin to handle movements
        UiWatcher.handleMovements([this.leftBtn, this.centerBtn, this.rightBtn]);

        // add to player's ui
        UiHelpers.insertAfter(this.leftBtn, this.centerBtn);
    },

    isMoreBtnToggled: function() {
        var moreBtn = Utils.$(YouTubeSelectors.PLAYER_MORE_BUTTON);
        return Utils.hasClass(moreBtn, YouTubeClasses.BUTTON_TOGGLED);
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