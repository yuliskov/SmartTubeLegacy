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
        // call super
        ChannelButton.prototype.setChecked.apply(this, arguments);

        // check that app switched to the channels page
        setTimeout(function() {
            if (!YouTubeUtils.isChannelOpened()) {
                Log.d($this.TAG, "Channel still not opened... return to the player...");
                $this.cancelEvents();
            } else {
                $this.onPress = function(e) {
                    if (e.keyCode == DefaultKeys.ENTER) {
                        Log.d($this.TAG, "Opening new video...");
                        $this.recentlyClosed = true; // do nothing, just disable all stuff in the super class
                    }
                };

                EventUtils.addListener(YouTubeSelectors.CHANNEL_CONTENT, DefaultEvents.KEY_DOWN, $this.onPress);
            }
        }, this.CHANNEL_CHECK_TIMEOUT_MS);
    };

    this.onChannelClosed = function() {
        Log.d(this.TAG, "Disposing event listeners...");
        EventUtils.removeListener(YouTubeSelectors.CHANNEL_CONTENT, DefaultEvents.KEY_DOWN, this.onPress);
    };

    this.decorator.apply(this);
}

ChannelButton.prototype = new OverlayButton();