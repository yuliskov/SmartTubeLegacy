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
    this.recentlyClosed = false;
    this.id = OverlayButton.id++;

    this.getChecked = function() {
        Log.d(this.TAG, "getChecked " + this.selector);
        return false;
    };

    this.cleanup = function() {
        clearTimeout(this.closeTimeout);
        clearTimeout(this.closeTimeout2);
        EventUtils.removeListener(this.selector, YouTubeEvents.COMPONENT_FOCUS_EVENT, this.handler);
    };

    this.sendClose = function() {
        Log.d(this.TAG, "Switching to the player activity... " + this.id);

        this.cleanup();

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

        this.closeTimeout = setTimeout(function() {
            $this.closePlayerControlsAndSend();
        }, 500);
    };

    this.setChecked = function(doChecked) {
        Log.d(this.TAG, "setChecked " + this.selector + " " + doChecked);

        if (doChecked) {
            var el = this.findToggle();

            if (!el) {
                Log.d(this.TAG, "Oops, " + this.selector + " button not found... return to the player");
                this.sendClose();
                return;
            }

            Log.d(this.TAG, "Location is " + location.hash);

            EventUtils.triggerEnter(el);

            this.initHandler();

            if (this.onOverlayOpen) {
                this.onOverlayOpen();
            }
        }
    };

    this.initHandler = function() {
        if (this.handler) {
            return;
        }

        var $this = this;

        this.handler = function() {
            Log.d($this.TAG, "User has closed the " + $this.selector + " overlay... return to the player");

            if ($this.onOverlayClosed) {
                $this.onOverlayClosed();
            }

            if ($this.recentlyClosed) {
                return; // event in canceled in the one of the descendant classes
            }

            $this.recentlyClosed = true;

            ExoUtils.sendAction(ExoUtils.ACTION_DISABLE_KEY_EVENTS);

            $this.closeTimeout2 = setTimeout(function() {
                Log.d($this.TAG, "Location is " + location.hash);
                $this.closePlayerControlsAndSend();
            }, 500);
        };

        EventUtils.addListenerOnce(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, YouTubeEvents.COMPONENT_FOCUS_EVENT, this.handler);
    };

    this.cancelEvents = function() {
        if (this.handler && !this.recentlyClosed) {
            this.recentlyClosed = true;
            this.sendClose();
        }
    };

    this.decorator.apply(this);
}

OverlayButton.id = 0;
OverlayButton.prototype = new ExoButton();