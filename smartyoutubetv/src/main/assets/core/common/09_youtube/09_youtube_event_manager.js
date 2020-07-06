/**
 * Event abstractions for all youtube app
 */

console.log("Scripts::Running script youtube_event_manager.js");

var YouTubeEventManager = {
    TAG: 'YouTubeEventManager',
    onExitDialogShownCallbacks: [],
    onSettingsAppVersionShown: [],
    onSearchPageShown: [],
    onGenericOverlayShown: [],
    onUiChange: [],
    init: function() {
        var $this = this;
        this.onLoad(function() {
            $this.setupRootObserver();
        });
    },
    onLoad: function(callback) {
        if (callback == null) {
            Log.d(this.TAG, 'Load watcher: Callback not specified.');
            return;
        }

        var loader = Utils.$(YouTubeSelectors.MAIN_LOADER);

        if (loader == null) {
            Log.d(this.TAG, 'Load watcher: App has been loaded.');
            callback && callback();
            return;
        }

        var $this = this;

        setTimeout(function() {
            Log.d($this.TAG, 'Load watcher: App still loading... ' + EventUtils.toSelector(loader));
            $this.onLoad(callback);
        }, 500);
    },
    /**
     * https://developer.mozilla.org/en-US/docs/Web/API/MutationRecord
     */
    setupRootObserver: function() {
        Log.d(this.TAG, "Setting up root observer...");
        var $this = this;
        var observer = new MutationObserver(function(records, observer) {
            // Log.d($this.TAG,'callback that runs when observer is triggered');

            for (var i = 0; i < records.length; i++) {
                var record = records[i];

                if (record.type === 'childList') {
                    // Log.d($this.TAG, 'A child node has been added or removed. ' + record.target);
                    // for (var j = 0; j < record.addedNodes; j++) {
                    //     Log.d($this.TAG, "Added node: " + record.addedNodes[j].tagName);
                    // }
                    // for (var k = 0; k < record.removedNodes; k++) {
                    //     Log.d($this.TAG, "Removed node: " + record.removedNodes[j].tagName);
                    // }

                    // Log.d($this.TAG, "Target: " + EventUtils.toSelector(record.target));

                    $this.checkAndApply(record.target);
                } else if (record.type === 'attributes') {
                    Log.d($this.TAG, 'The ' + record.attributeName + ' attribute was modified.');
                }
            }
        });
        observer.observe(Utils.$(YouTubeSelectors.ROOT_CONTAINER), {subtree: true, childList: true});
    },
    checkAndApply: function(element) {
        if (element) {
            if (element.tagName == YouTubeTags.SETTING_APP_VERSION) {
                Log.d(this.TAG, "Settings > App Version has been opened");
                this.triggerCallbacks(this.onSettingsAppVersionShown, element);
            } else if (element.tagName == YouTubeTags.SEARCH_PAGE) {
                Log.d(this.TAG, "App Search page has been opened");
                this.triggerCallbacks(this.onSearchPageShown, element);
            } else if (element.tagName == YouTubeTags.GENERIC_OVERLAY && !Utils.hasClass(element, YouTubeClassesV2.GENERIC_OVERLAY_HIDDEN)) {
                Log.d(this.TAG, "App generic overlay has been shown");
                this.triggerCallbacks(this.onGenericOverlayShown, element);
            }

            // generic change event
            this.triggerCallbacks(this.onUiChange);
        }
    },
    triggerCallbacks: function(callbacks, element) {
        if (callbacks) {
            for (var i = 0; i < callbacks.length; i++) {
                callbacks[i](element);
            }
        }
    },
    addCallback: function(callbacks, callback) {
        if (!Utils.arrayContains(callbacks, callback)) {
            Utils.arrayAdd(callbacks, callback);
        }
    },
    addOnExitDialogShown: function(callback) {
        this.addCallback(this.onExitDialogShownCallbacks, callback);
    },
    addOnSettingsAppVersionShown: function(callback) {
        this.addCallback(this.onSettingsAppVersionShown, callback);
    },
    addOnSearchPageShown: function(callback) {
        this.addCallback(this.onSearchPageShown, callback);
    },
    addOnGenericOverlayShown: function(callback) {
        this.addCallback(this.onGenericOverlayShown, callback);
    },
    addOnUiChange: function(callback) {
        this.addCallback(this.onUiChange, callback);
    }
};

YouTubeEventManager.init();
