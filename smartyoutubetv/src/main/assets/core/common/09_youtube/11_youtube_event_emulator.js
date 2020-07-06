/**
 * Event abstractions for all youtube app
 */

console.log("Scripts::Running script youtube_event_emulator.js");

var YouTubeEventEmulator = {
    clickOnSearchField: function() {
        EventUtils.triggerEvent(
            YouTubeSelectors.SEARCH_INPUT_FIELD,
            DefaultEvents.ON_TEXT_TYPE,
            DefaultKeys.ENTER);
    },
    clickOnStartSearchButton: function() {
        EventUtils.triggerEnter(YouTubeSelectors.SEARCH_START_BUTTON);
    }
};
