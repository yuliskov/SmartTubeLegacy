console.log("Scripts::Running core script favorites_button.js");

function FavoritesButton(selector) {
    this.TAG = "FavoritesButton";
    this.selector = selector;

    this.getChecked = function() {
        Log.d(this.TAG, "getChecked " + this.selector);
        return false;
    };

    this.setChecked = function(doChecked) {
        Log.d(this.TAG, "setChecked " + this.selector + " " + doChecked);

        if (doChecked) {
            var $this = this;

            var el = this.findToggle();

            if (!el) {
                Log.d(this.TAG, "Oops, suggestions button not found... return to the player");
                ExoUtils.sendAction(ExoUtils.CLOSE_SUGGESTIONS);
                return;
            }

            EventUtils.triggerEnter(el);

            EventUtils.addListenerOnce(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, YouTubeEvents.COMPONENT_FOCUS_EVENT, function() {
                Log.d($this.TAG, "User has closed the suggestions... return to the player");
                ExoUtils.sendAction(ExoUtils.CLOSE_SUGGESTIONS);
            });
        }
    };

    this.decorator.apply();
}

FavoritesButton.prototype = new ExoButton();