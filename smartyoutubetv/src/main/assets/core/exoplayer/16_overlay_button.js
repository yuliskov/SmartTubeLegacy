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
        ExoUtils.sendAction(ExoUtils.ACTION_CLOSE_SUGGESTIONS);
    };

    this.closePlayerControlsAndSend = function() {
        var $this = this;

        if (YouTubeUtils.isPlayerControlsClosed()) {
            this.sendClose();
            return;
        }

        Log.d(this.TAG, "closing player controls");

        EventUtils.triggerEvent(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, DefaultEvents.KEY_UP, DefaultKeys.ESC);

        setTimeout(function() {
            $this.closePlayerControlsAndSend();
        }, 500);
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

                ExoUtils.sendAction(ExoUtils.ACTION_DISABLE_KEY_EVENTS);

                setTimeout(function() {
                    $this.closePlayerControlsAndSend();
                }, 500);
            });
        }
    };

    this.decorator.apply(this);
}

OverlayButton.prototype = new ExoButton();