console.log("Scripts::Running core script channel_button.js");

/**
 * Any button that could open an overlay like favorites, user channel etc
 * @param selector btn selector
 * @constructor
 */
function ChannelButton(selector) {
    this.TAG = "ChannelButton";
    this.selector = selector;
    this.CHANNEL_CHECK_TIMEOUT_MS = 5000;
    this.stateless = true;

    this.setChecked = function(doChecked) {
        var $this = this;
        FavoriteButton.prototype.setChecked.apply(this, arguments);

        // check that app switched to the channels page
        setTimeout(function() {
            if (!YouTubeUtils.isChannelOpened()) {
                Log.d($this.TAG, "Channel still not opened... return to the player...");
                $this.cancelEvents();
            }
        }, this.CHANNEL_CHECK_TIMEOUT_MS);
    };

    this.decorator.apply(this);
}

ChannelButton.prototype = new OverlayButton();