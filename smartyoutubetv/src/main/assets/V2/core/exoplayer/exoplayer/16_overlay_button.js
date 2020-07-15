console.log("Scripts::Running core script overlay_button.js");

/**
 * <a href="https://javascript.info/bubbling-and-capturing">More info</a> about event propagation
 * @param host callback with {@link needToCloseSuggestions} and {@link suggestionsIsClosed} methods
 * @constructor
 */
function OverlayWatcher(host) {
    function OverlayWatcherService() {
        var $this = this;

        var checkOverlay = function() {
            if ($this.host == null) {
                return;
            }

            $this.host.tryToCloseOverlay();

            $this.host = null;
        };

        var onBlurHandler = function() {
            if ($this.host == null) {
                return;
            }

            setTimeout(function() {
                console.log("OverlayWatcher: simple close overlay");
                checkOverlay(); // event is standalone
            }, 100);
        };

        // V2: may not work properly!
        YouTubeEventManager.addOnGenericOverlayHidden(onBlurHandler);

        this.setHost = function(host) {
            this.host = host;
        };

        console.log("OverlayWatcher: do init...");
    }

    if (!OverlayWatcher.service) {
        OverlayWatcher.service = new OverlayWatcherService();
    }

    OverlayWatcher.service.setHost(host);
}

OverlayWatcher.disable = function() {
    new OverlayWatcher(null);
};

/**
 * Any button that could open an overlay like favorites, user channel etc
 * @param selector btn selector
 * @constructor
 */
function OverlayButton(selector) {
    this.TAG = "OverlayButton";
    this.selector = selector;
    this.stateless = true;
    this.closeRetryTimes = 4;
    this.callDelayMS = 500;
    this.CHECK_TIMEOUT_MS = 5000;

    this.getChecked = function() {
        Log.d(this.TAG, "getChecked " + this.selector);
        return false;
    };

    this.cleanup = function() {
        clearTimeout(this.closeTimeout);
        clearTimeout(this.checkTimeout);
        OverlayWatcher.disable();
    };

    this.sendClose = function() {
        if (!this.recentlyClosed) {
            Log.d(this.TAG, "Switching to the player...");

            this.cleanup();

            this.recentlyClosed = true;

            ExoUtils.sendAction(ExoUtils.ACTION_CLOSE_SUGGESTIONS);
        } else {
            Log.d(this.TAG, "Overlay already closed...");
        }
    };

    this.setChecked = function(doChecked) {
        Log.d(this.TAG, "setChecked " + this.selector + " " + doChecked);

        if (doChecked) {
            if (!this.isOverlayOpened()) {
                var el = this.findToggle();

                if (!el) {
                    Log.d(this.TAG, "Oops, " + this.selector + " button not found... return to the player");
                    this.sendClose();
                    return;
                }

                EventUtils.triggerEnter(el);

                var $this = this;

                // check that app switched to the channels page
                this.checkTimeout = setTimeout(function() {
                    if (!$this.isOverlayOpened()) {
                        Log.d($this.TAG, "Favorites still not opened... return to the player...");
                        $this.cancelEvents();
                    }
                }, this.CHECK_TIMEOUT_MS);
            }

            // start point
            new OverlayWatcher(this);
        }
    };

    this.closeOverlay = function() {
        Log.d(this.TAG, "closeOverlay");

        this.sendClose();

        // this.closeRetryTimes--;
        //
        // if (!this.isOverlayOpened() || this.closeRetryTimes <= 0) {
        //     Log.d(this.TAG, "overlay has been closed or retries is out");
        //     this.sendClose();
        // } else {
        //     Log.d(this.TAG, "try to close the overlay");
        //     YouTubeUtils.pressBack();
        //
        //     var $this = this;
        //     setTimeout(function() {
        //         $this.closeOverlay();
        //     }, this.callDelayMS);
        // }
    };

    this.needToCloseOverlay = function() {
        Log.d(this.TAG, "needToCloseSuggestions");

        var $this = this;
        // immediate close not working here, so take delay
        this.closeTimeout = setTimeout(function() {
            $this.closeOverlay();
        }, this.callDelayMS);
    };

    this.overlayIsClosed = function() {
        Log.d(this.TAG, "overlay has been closed");
        this.sendClose();
    };

    this.cancelEvents = function() {
        this.sendClose();
    };

    this.tryToCloseOverlay = function() {
        if (this.isOverlayOpened()) {
            this.needToCloseOverlay();
        } else {
            this.overlayIsClosed();
        }
    };

    this.decorator.apply(this);
}

OverlayButton.prototype = new ExoButton();