/**
 * Event abstractions for all youtube app
 */

console.log("Scripts::Running script youtube_ui_utils.js");

var YouTubeUiUtils = {
    clickOnSearchField: function() {
        EventUtils.triggerEvent(
            YouTubeSelectors.SEARCH_INPUT_FIELD,
            DefaultEvents.ON_TEXT_TYPE,
            DefaultKeys.ENTER);
    },
    clickOnStartSearchButton: function() {
        EventUtils.triggerEnter(YouTubeSelectors.SEARCH_START_BUTTON);
    },
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
    }
};
