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

        var closeSuggestions = function() {
            if ($this.host == null) {
                return;
            }

            var playerControls = Utils.$(YouTubeSelectors.PLAYER_UI_CONTAINER);

            if (Utils.hasClass(playerControls, YouTubeClasses.HIDDEN)) {
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
                    closeSuggestions(); // event is standalone
                } else {
                    console.log("SuggestionsWatcher: user have clicked on thumbnail...");
                }
            }, 100);
        };

        var onModelChangeHandler = function(e) {
            var backToPlayer = Utils.hasClass(e.target, YouTubeClasses.HIDDEN) &&
                Utils.hasClass(e.target, YouTubeClasses.PLAYER_UI_SHOWING);
            if (backToPlayer) {
                console.log("SuggestionsWatcher: user navigated out from the channel or search screen");
                closeSuggestions();
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
    this.retryTimes = 10;
    this.callDelayMS = 500;
    this.stateless = true;

    Log.d(this.TAG, "Creating " + this.TAG);

    this.tryToOpenSuggestions = function() {
        var suggestionsShown = Utils.hasClass(Utils.$(YouTubeSelectors.PLAYER_SUGGESTIONS_LIST), YouTubeClasses.ELEMENT_FOCUSED);

        if (suggestionsShown) {
            Log.d(this.TAG, "Success. Suggestions has been shown!");
            // Empty suggestions fix
            YouTubeUtils.getPlayer() && YouTubeUtils.getPlayer().imitatePlaying();
            return;
        }
 
        if (!YouTubeUtils.isPlayerOpened()) {
            Log.d(this.TAG, "Error. Player is closed. Can't show the suggestions.");
            return;
        }

        if (this.retryTimes <= 0) {
            this.sendClose();
            return;
        }

        this.retryTimes--;

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
        if (this.alreadyHidden || YouTubeUtils.isPlayerControlsClosed()) {
            return;
        }

        Log.d(this.TAG, "closeSuggestions");
        
        EventUtils.triggerEvent(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, DefaultEvents.KEY_DOWN, DefaultKeys.ESC);
        EventUtils.triggerEvent(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, DefaultEvents.KEY_UP, DefaultKeys.ESC);

        this.alreadyHidden = true;
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
            $this.sendClose();
        }, this.callDelayMS);
    };

    this.getChecked = function() {
        return false;
    };

    this.setChecked = function(doChecked) {
        if (doChecked && !YouTubeUtils.isPlayerClosed()) { // fake btn can only be checked
            Log.d(this.TAG, "opening suggestions");
            YouTubeUtils.enablePlayerSuggestions();
            YouTubeUtils.hidePlayerBackground();
            this.openSuggestions();
        }
    };
}