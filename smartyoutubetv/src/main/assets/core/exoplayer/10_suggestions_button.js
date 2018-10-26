// NOTE: this file doesn't depend on common js files

console.log("Scripts::Running core script suggestions_button.js");

/**
 * <a href="https://javascript.info/bubbling-and-capturing">More info</a> about event propagation
 * @param host callback with {@link needToCloseSuggestions} and {@link suggestionsIsClosed} methods
 * @constructor
 */
function SuggestionsWatcher(host) {
    function SuggestionsWatcherService() {
        var $this = this;

        var onBlurHandler = function() {
            if ($this.host == null) {
                return;
            }

            var playerControls = exoutils.$($this.mainControlsSelector);

            if (exoutils.hasClass(playerControls, $this.hiddenClass)) {
                $this.host.suggestionsIsClosed();
            } else {
                $this.host.needToCloseSuggestions();
            }

            $this.host = null;
        };

        var container = exoutils.$(this.suggestionsListSelector);
        if (container == null) {
            var interval = setInterval(function() {
                container = exoutils.$($this.suggestionsListSelector);
                if (container != null) {
                    clearInterval(interval);
                    container.addEventListener($this.componentBlurEvent, onBlurHandler);
                }
            }, 1000);
        } else {
            container.addEventListener(this.componentBlurEvent, onBlurHandler);
        }

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
        var player = document.getElementsByTagName('video')[0];
        player && player.pause();

        var downCode = 40;
        // we assume that no interface currently shown
        // press twice
        exoutils.triggerEvent(this.eventReceiverSelector, 'keydown', downCode);
        exoutils.triggerEvent(this.eventReceiverSelector, 'keydown', downCode);

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

        var esc = 27;
        exoutils.triggerEvent(this.eventReceiverSelector, 'keyup', esc);

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