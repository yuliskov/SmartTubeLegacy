console.log("Scripts::Running core script suggestions_button.js");

/**
 * <a href="https://javascript.info/bubbling-and-capturing">More info</a> about event propagation
 * @param host callback with {@link needToCloseSuggestions} and {@link suggestionsIsClosed} methods
 * @constructor
 */
function SuggestionsWatcher(host) {
    function SuggestionsWatcherService() {
        var $this = this;
        var skipBlur = false;

        var closeSuggestions = function() {
            if ($this.host == null) {
                return;
            }

            var playerControls = Utils.$(ExoConstants.playerControlsSelector);

            if (Utils.hasClass(playerControls, ExoConstants.hiddenClass)) {
                $this.host.suggestionsIsClosed();
            } else {
                $this.host.needToCloseSuggestions();
            }

            $this.host = null;
        };

        var onBlurHandler = function() {
            if (!skipBlur) {
                closeSuggestions();
            }
            skipBlur = false;
        };

        var onKeyDownHandler = function(e) {
            if (e.keyCode == DefaultKeys.ENTER) {
                console.log("SuggestionsWatcher: user have clicked on thumbnail");
                skipBlur = true;
            }
        };

        var onFocusHandler = function(e) {
            if (Utils.hasClass(e.target, ExoConstants.hiddenClass) &&
                Utils.hasClass(e.target, ExoConstants.transportShowingClass)) {
                console.log("SuggestionsWatcher: user navigated out from the channel or search screen");
                closeSuggestions();
            }
        };

        EventUtils.addListener(ExoConstants.suggestionsListSelector, ExoConstants.componentBlurEvent, onBlurHandler);
        EventUtils.addListener(ExoConstants.suggestionsListSelector, DefaultEvents.KEY_DOWN, onKeyDownHandler);
        EventUtils.addListener(ExoConstants.playerUiSelector, ExoConstants.modelChangedEvent, onFocusHandler);

        this.setHost = function(host) {
            this.host = host;
        };

        console.log("SuggestionsWatcher: do init...");
    }

    if (!window.suggestionsWatcherService) {
        window.suggestionsWatcherService = new SuggestionsWatcherService();
    }

    window.suggestionsWatcherService.setHost(host);
}

SuggestionsWatcher.disable = function() {
    new SuggestionsWatcher(null);
};

function SuggestionsFakeButton(selector) {
    this.selector = selector;
    this.CLOSE_SUGGESTIONS = "action_close_suggestions";
    this.retryTimes = 4;

    this.utilSuggestionsShown = function() {
        var suggestionsShown = Utils.hasClass(Utils.$(ExoConstants.suggestionsListSelector), ExoConstants.focusedClass);
        if (suggestionsShown || this.retryTimes <= 0)
            return;

        this.retryTimes--;

        // we assume that no interface currently shown
        // press multiple times util suggestion will have focus
        EventUtils.triggerEvent(ExoConstants.playerUiSelector, DefaultEvents.KEY_DOWN, DefaultKeys.DOWN);

        var $this = this;
        setTimeout(function() {
           $this.utilSuggestionsShown();
        }, 100);
    };

    this.openSuggestions = function() {
        console.log("SuggestionsFakeButton: showing suggestions list");

        ExoUtils.enablePlayerUi();

        this.utilSuggestionsShown();

        // start point
        this.watcher = new SuggestionsWatcher(this);
    };

    this.sendClose = function() {
        if (this.alreadySent) {
            return;
        }

        var $this = this;
        // immediate close not working here, so take delay
        setTimeout(function() {
            ExoUtils.sendAction($this.CLOSE_SUGGESTIONS);
        }, 100);

        this.alreadySent = true;
    };

    this.closeSuggestions = function() {
        if (this.alreadyHidden) {
            return;
        }

        console.log("SuggestionsFakeButton::closeSuggestions");
        
        EventUtils.triggerEvent(ExoConstants.playerUiSelector, DefaultEvents.KEY_UP, DefaultKeys.ESC);

        this.alreadyHidden = true;
    };

    this.suggestionsIsClosed = function() {
        console.log("SuggestionsFakeButton: suggestionsIsClosed");

        this.sendClose();
    };

    this.needToCloseSuggestions = function() {
        console.log("SuggestionsFakeButton: needToCloseSuggestions");

        var $this = this;
        // immediate close not working here, so take delay
        setTimeout(function() {
            $this.closeSuggestions();
            $this.sendClose();
        }, 100);
    };

    this.getChecked = function() {
        return false; // not exists
    };

    this.setChecked = function(doChecked) {
        if (doChecked) { // fake btn can only be checked
            console.log("SuggestionsFakeButton: opening suggestions");
            this.openSuggestions();
        }

    };
}