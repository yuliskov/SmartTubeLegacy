console.log("Scripts::Running core script overlay_button.js");

/**
 * Any button that could open an overlay like favorites, user channel etc
 * @param selector btn selector
 * @constructor
 */
function OverlayButton(selector) {
    this.TAG = "OverlayButton";
    this.selector = selector;
    this.stateless = true;

    this.getChecked = function() {
        Log.d(this.TAG, "getChecked " + this.selector);
        return false;
    };

    this.sendClose = function() {
        // immediate close not working here, so take delay
        setTimeout(function() {
            ExoUtils.sendAction(ExoUtils.ACTION_CLOSE_SUGGESTIONS);
        }, 500);
    };

    this.closePlayerControls = function() {
        if (YouTubeUtils.isPlayerControlsClosed()) {
            return;
        }

        Log.d(this.TAG, "closing player controls");

        EventUtils.triggerEvent(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, DefaultEvents.KEY_UP, DefaultKeys.ESC);
    };

    this.setChecked = function(doChecked) {
        Log.d(this.TAG, "setChecked " + this.selector + " " + doChecked);

        if (doChecked) {
            var $this = this;

            var el = this.findToggle();

            if (!el) {
                Log.d(this.TAG, "Oops, " + this.selector + " button not found... return to the player");
                this.sendClose();
                return;
            }

            EventUtils.triggerEnter(el);

            EventUtils.addListenerOnce(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, YouTubeEvents.COMPONENT_FOCUS_EVENT, function() {
                Log.d($this.TAG, "User has closed the " + $this.selector + " overlay... return to the player");

                $this.closePlayerControls();
                $this.sendClose();
            });
        }
    };

    this.decorator.apply(this);
}

OverlayButton.prototype = new ExoButton();