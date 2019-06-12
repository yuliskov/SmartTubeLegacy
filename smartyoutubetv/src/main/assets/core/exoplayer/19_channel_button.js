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

    this.onOverlayOpen = function() {
        var $this = this;

        // check that app switched to the channels page
        this.timeout = setTimeout(function() {
            if (!YouTubeUtils.isChannelOpened()) {
                Log.d($this.TAG, "Oops... Channel still not opened... return to the player...");
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

    this.onOverlayClosed = function() {
        Log.d(this.TAG, "Disposing event listeners, timeouts...");
        EventUtils.removeListener(YouTubeSelectors.CHANNEL_CONTENT, DefaultEvents.KEY_DOWN, this.onPress);
        clearTimeout(this.timeout);
    };

    this.decorator.apply(this);
}

ChannelButton.prototype = new OverlayButton();