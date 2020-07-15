console.log("Scripts::Running core script suggestions_button.js");

/**
 * <a href="https://javascript.info/bubbling-and-capturing">More info</a> about event propagation
 * @param host callback with {@link needToCloseSuggestions} and {@link suggestionsIsClosed} methods
 * @constructor
 */
function SuggestionsWatcher(host) {
    function SuggestionsWatcherService() {
        var $this = this;
        var modelChangeTimeStampMS = 0;
        var relatedEventsTimeWindowMS = 1000;

        var checkSuggestions = function() {
            if ($this.host == null) {
                return;
            }

            console.log("SuggestionsWatcher: sending close signal to host...");

            if (YouTubeUtils.isAllPlayerUIClosed()) {
                $this.host.suggestionsIsClosed();
            } else {
                $this.host.needToCloseSuggestions();
            }

            $this.host = null;
        };

        var onBlurHandler = function() {
            var currentTimeMS = Utils.getCurrentTimeMs();
            setTimeout(function() { // change event ordering: set 'modelChangedEvent' before 'componentBlurEvent'
                var diff = Math.abs(modelChangeTimeStampMS - currentTimeMS);
                if (diff > relatedEventsTimeWindowMS) {
                    console.log("SuggestionsWatcher: simple close suggestions: " + diff);
                    checkSuggestions(); // event is standalone
                } else {
                    console.log("SuggestionsWatcher: user has clicked on thumbnail...");
                }
            }, 100);
        };

        // used when the user opens channel or search from the suggestions
        var onModelChangeHandler = function(e) {
            var playerAboutToOpen = Utils.hasClass(e.target, YouTubeClasses.HIDDEN) &&
                Utils.hasClass(e.target, YouTubeClasses.PLAYER_CONTROLS_SHOWING);
            if (playerAboutToOpen) {
                console.log("SuggestionsWatcher: user navigated out from the channel or search screen");
                checkSuggestions();
                return;
            }

            modelChangeTimeStampMS = Utils.getCurrentTimeMs();
        };

        EventUtils.addListener(YouTubeSelectors.PLAYER_SUGGESTIONS_LIST, YouTubeEvents.COMPONENT_BLUR_EVENT, onBlurHandler);
        EventUtils.addListener(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, YouTubeEvents.MODEL_CHANGED_EVENT, onModelChangeHandler);

        this.setHost = function(host) {
            this.host = host;
        };

        console.log("SuggestionsWatcher: do init...");
    }

    if (!SuggestionsWatcher.service) {
        SuggestionsWatcher.service = new SuggestionsWatcherService();
    }

    SuggestionsWatcher.service.setHost(host);
}

SuggestionsWatcher.disable = function() {
    new SuggestionsWatcher(null);
};

function SuggestionsFakeButton(selector) {
    this.TAG = "SuggestionsFakeButton";
    this.selector = selector;
    this.openRetryTimes = 10;
    this.closeRetryTimes = 4;
    this.callDelayMS = 500;
    this.stateless = true;

    Log.d(this.TAG, "Creating " + this.TAG);

    this.tryToOpenSuggestions = function() {
        var suggestionsShown = YouTubeUtils.isPlayerSuggestionsOpened();

        if (suggestionsShown) {
            Log.d(this.TAG, "Success. Suggestions has been shown!");
            // Fix: suggestions list is empty (grey squares)
            //YouTubeUtils.getPlayer() && YouTubeUtils.getPlayer().imitatePlaying();
            return;
        }
 
        if (!YouTubeUtils.isPlayerOpened()) {
            Log.d(this.TAG, "Error. Player is closed. Can't show the suggestions.");
            return;
        }

        if (this.openRetryTimes <= 0) {
            this.sendClose();
            return;
        }

        this.openRetryTimes--;

        if (YouTubeUtils.isOverlayOpened()) { // check favorites is showed
            Log.d(this.TAG, "Closing overlay...");
            EventUtils.triggerEvent(document.activeElement, DefaultEvents.KEY_DOWN, DefaultKeys.ESC);
            EventUtils.triggerEvent(document.activeElement, DefaultEvents.KEY_UP, DefaultKeys.ESC);
        }

        Log.d(this.TAG, "Suggestions not showed... trying to open...");

        // we assume that no interface currently shown
        // press multiple times util suggestion will have focus
        EventUtils.triggerEvent(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, DefaultEvents.KEY_DOWN, DefaultKeys.DOWN);
        EventUtils.triggerEvent(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, DefaultEvents.KEY_UP, DefaultKeys.DOWN);

        var $this = this;
        this.openTimeout = setTimeout(function() {
           $this.tryToOpenSuggestions();
        }, this.callDelayMS);
    };

    this.openSuggestions = function() {
        YouTubeUtils.enablePlayerSuggestions();
        this.tryToOpenSuggestions();

        // start point
        new SuggestionsWatcher(this);
    };

    this.sendClose = function() {
        clearTimeout(this.openTimeout);
        clearTimeout(this.closeTimeout);
        SuggestionsWatcher.disable();

        if (this.alreadySent) {
            return;
        }
        
        // immediate close not working here, so take delay
        setTimeout(function() {
            ExoUtils.sendAction(ExoUtils.ACTION_CLOSE_SUGGESTIONS);
        }, 100);

        this.alreadySent = true;
    };

    this.closeSuggestions = function() {
        Log.d(this.TAG, "closeSuggestions");

        this.sendClose();

        // this.closeRetryTimes--;
        //
        // if (YouTubeUtils.isPlayerControlsClosed() || this.closeRetryTimes <= 0) {
        //     Log.d(this.TAG, "suggestions has been closed or retries is out");
        //     this.sendClose();
        // } else {
        //     Log.d(this.TAG, "try to close the suggestions");
        //     YouTubeUtils.closePlayerControls();
        //
        //     var $this = this;
        //     setTimeout(function() {
        //         $this.closeSuggestions();
        //     }, this.callDelayMS);
        // }
    };

    this.suggestionsIsClosed = function() {
        Log.d(this.TAG, "suggestionsIsClosed");

        this.sendClose();
    };

    this.needToCloseSuggestions = function() {
        Log.d(this.TAG, "needToCloseSuggestions");

        var $this = this;
        // immediate close not working here, so take delay
        this.closeTimeout = setTimeout(function() {
            $this.closeSuggestions();
        }, this.callDelayMS);
    };

    this.getChecked = function() {
        return false;
    };

    this.setChecked = function(doChecked) {
        if (doChecked) { // fake btn can only be checked
            if (!YouTubeUtils.isPlayerClosed()) {
                Log.d(this.TAG, "opening suggestions");
                YouTubeUtils.enablePlayerSuggestions();
                YouTubeUtils.hidePlayerBackground();
                this.openSuggestions();
            } else {
                this.sendClose();
            }
        }
    };
}