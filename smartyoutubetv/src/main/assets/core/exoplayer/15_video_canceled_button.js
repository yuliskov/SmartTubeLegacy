console.log("Scripts::Running core script video_canceled_button.js");

/**
 * Checks that player is closed.
 * @param selector button selector
 */
function VideoCanceledButton(selector) {
    this.selector = selector;
    this.OPENED_VIDEO_SIGN = '#/watch/video';

    this.getChecked = function() {
        return location.hash.indexOf(this.OPENED_VIDEO_SIGN) == -1;
    };

    this.setChecked = function(doChecked) {
        // NOP
    };
}