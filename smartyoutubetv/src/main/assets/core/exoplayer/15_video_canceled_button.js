console.log("Scripts::Running core script video_canceled_button.js");

/**
 * Checks that player is closed.
 * @param selector button selector
 */
function VideoCanceledButton(selector) {
    this.selector = selector;
    this.stateless = true;

    this.getChecked = function() {
        return YouTubeUtils.isPlayerClosed();
    };

    this.setChecked = function(doChecked) {
        // NOP
    };
}