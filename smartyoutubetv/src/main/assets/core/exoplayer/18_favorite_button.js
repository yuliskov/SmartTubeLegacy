console.log("Scripts::Running core script favorite_button.js");

/**
 * Any button that could open an overlay like favorites, user channel etc
 * @param selector btn selector
 * @constructor
 */
function FavoriteButton(selector) {
    this.TAG = "FavoriteButton";
    this.selector = selector;
    this.CHANNEL_CHECK_TIMEOUT_MS = 5000;
    this.stateless = true;

    this.onOverlayOpen = function() {
        var $this = this;

        // check that app switched to the channels page
        this.timeout = setTimeout(function() {
            if (!YouTubeUtils.isOverlayOpened()) {
                Log.d($this.TAG, "Favorites still not opened... return to the player...");
                $this.cancelEvents();
            }
        }, this.CHANNEL_CHECK_TIMEOUT_MS);
    };

    this.onOverlayClosed = function() {
        Log.d(this.TAG, "Disposing timeouts...");
        clearTimeout(this.timeout);
    };

    this.decorator.apply(this);
}

FavoriteButton.prototype = new OverlayButton();