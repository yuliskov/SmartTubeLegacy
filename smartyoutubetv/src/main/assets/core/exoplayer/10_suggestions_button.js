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

            var playerControls = Utils.$($this.playerControlsSelector);

            if (Utils.hasClass(playerControls, $this.hiddenClass)) {
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
            if (Utils.hasClass(e.target, $this.focusedClass)) {
                console.log("SuggestionsWatcher: user navigated out from the channel or search screen");
                closeSuggestions();
            }
        };

        EventUtils.addListener(this.suggestionsListSelector, this.componentBlurEvent, onBlurHandler);
        EventUtils.addListener(this.suggestionsListSelector, DefaultEvents.KEY_DOWN, onKeyDownHandler);
        EventUtils.addListener(this.channelScreenSelector, this.modelChangedEvent, onFocusHandler());
        EventUtils.addListener(this.searchScreenSelector, this.modelChangedEvent, onFocusHandler());

        this.setHost = function(host) {
            this.host = host;
        };

        console.log("SuggestionsWatcher: do init...");
    }

    SuggestionsWatcherService.prototype = new ExoConstants();

    if (!window.suggestionsWatcherService) {
        window.suggestionsWatcherService = new SuggestionsWatcherService();
    }

    window.suggestionsWatcherService.setHost(host);
}

SuggestionsWatcher.prototype = new ExoConstants();
SuggestionsWatcher.disable = function() {
    new SuggestionsWatcher(null);
};

function SuggestionsFakeButton(selector) {
    this.selector = selector;
    this.CLOSE_SUGGESTIONS = "action_close_suggestions";

    this.openSuggestions = function() {
        console.log("SuggestionsFakeButton: showing suggestions list");

        // pause keeps sound off
        var player = Utils.$('video');
        player && player.pause();

        var downCode = 40;
        // we assume that no interface currently shown
        // press twice
        EventUtils.triggerEvent(this.eventRootSelector, DefaultEvents.KEY_DOWN, downCode);
        EventUtils.triggerEvent(this.eventRootSelector, DefaultEvents.KEY_DOWN, downCode);

        // start point
        this.watcher = new SuggestionsWatcher(this);
    };

    this.sendClose = function() {
        if (this.alreadySent) {
            return;
        }

        exoutils.sendAction(this.CLOSE_SUGGESTIONS);

        this.alreadySent = true;
    };

    this.closeSuggestions = function() {
        if (this.alreadyHidden) {
            return;
        }

        console.log("SuggestionsFakeButton::closeSuggestions");
        
        EventUtils.triggerEvent(this.eventRootSelector, DefaultEvents.KEY_UP, DefaultKeys.ESC);

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
        console.log("FakeButton: clicking on the fake item");
        if (doChecked) { // fake btn can only be checked
            this.openSuggestions();
            //this.showSuggestions();
        } else {
            //this.hideSuggestions();
        }

    };
}

SuggestionsFakeButton.prototype = new ExoConstants();