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

    this.setChecked = function(doChecked) {
        var $this = this;
        FavoriteButton.prototype.setChecked.apply(this, arguments);

        // check that app switched to the channels page
        setTimeout(function() {
            if (!YouTubeUtils.isOverlayOpened()) {
                Log.d($this.TAG, "Favorites still not opened... return to the player...");
                $this.cancelEvents();
            }
        }, this.CHANNEL_CHECK_TIMEOUT_MS);
    };

    this.decorator.apply(this);
}

FavoriteButton.prototype = new OverlayButton();