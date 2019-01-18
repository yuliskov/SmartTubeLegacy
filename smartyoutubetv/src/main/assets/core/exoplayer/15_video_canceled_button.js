console.log("Scripts::Running core script video_canceled_button.js");

/**
 * Checks that player is closed.
 * @param selector button selector
 */
function VideoCanceledButton(selector) {
    this.selector = selector;

    this.getChecked = function() {
        var v = Utils.$('video');

        return !v || !v.src;
    };

    this.setChecked = function(doChecked) {
        // NOP
    };
}