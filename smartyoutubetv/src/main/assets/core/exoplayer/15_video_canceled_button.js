console.log("Scripts::Running core script video_canceled_button.js");

/**
 * Checks that player is closed.
 * @param selector button selector
 */
function VideoCanceledButton(selector) {
    this.selector = selector;

    this.getChecked = function() {
        return ExoUtils.playerIsClosed();
    };

    this.setChecked = function(doChecked) {
        // NOP
    };
}