/**
 * Event abstractions for all youtube app
 */

console.log("Scripts::Running script youtube_event_manager.js");

var YouTubeEventManager = {
    TAG: 'YouTubeEventManager',
    onExitDialogShown: [],
    onSettingsAppVersionShown: [],
    onSearchPageShown: [],
    onGenericOverlayShown: [],
    onGenericOverlayHidden: [],
    onUiChange: [],
    onPlayerSuggestionsHidden: [],
    onPlayerSuggestionsShown: [],
    onPlayerControlsShown: [],
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
                    Log.d($this.TAG, "Element added: " + EventUtils.toSelector(record.target));

                    $this.checkAndApplyChildList(record.target);
                } else if (record.type === 'attributes') {
                    Log.d($this.TAG, 'Attribute was modified: ' + EventUtils.toSelector(record.target));

                    $this.checkAndApplyAttributes(record.target);
                }
            }
        });
        observer.observe(Utils.$(YouTubeSelectors.ROOT_CONTAINER), {subtree: true, childList: true, attributes: true});
    },
    checkAndApplyChildList: function(element) {
        if (element) {
            if (element.tagName == YouTubeTagsV2.SETTING_APP_VERSION) {
                Log.d(this.TAG, "Settings > App Version has been opened");
                this.triggerCallbacks(this.onSettingsAppVersionShown, element);
            } else if (element.tagName == YouTubeTagsV2.SEARCH_PAGE) {
                Log.d(this.TAG, "App Search page has been opened");
                this.triggerCallbacks(this.onSearchPageShown, element);
            } else if (element.tagName == YouTubeTagsV2.OVERLAY_PANEL) {
                if (Utils.hasClass(element, YouTubeClassesV2.GENERIC_OVERLAY_HIDDEN)) {
                    Log.d(this.TAG, "App generic overlay has been hidden");
                    this.triggerCallbacks(this.onGenericOverlayHidden, element);
                } else {
                    Log.d(this.TAG, "App generic overlay has been shown");
                    this.triggerCallbacks(this.onGenericOverlayShown, element);

                    if (YouTubeUtils.isExitDialogShown(element)) {
                        this.triggerCallbacks(this.onExitDialogShown, element);
                    }
                }
            }

            // generic change event
            this.triggerCallbacks(this.onUiChange);
        }
    },
    checkAndApplyAttributes: function(element) {
        if (element) {
            if (element.tagName == YouTubeTagsV2.WATCH_PAGE) {
                if (Utils.hasClass(element, YouTubeClassesV2.WATCH_PAGE_PIVOT)) {
                    Log.d(this.TAG, "Suggestions has been shown...");
                    this.suggestionsShown = true;
                    this.triggerCallbacks(this.onPlayerSuggestionsShown, element);
                } else if (this.suggestionsShown) {
                    Log.d(this.TAG, "Suggestions has been hidden...");
                    this.triggerCallbacks(this.onPlayerSuggestionsHidden, element);
                    this.suggestionsShown = false;
                } else if (Utils.hasClass(element, YouTubeClassesV2.WATCH_PAGE_CONTROL)) {
                    Log.d(this.TAG, "Playback controls has been shown...");
                    this.triggerCallbacks(this.onPlayerControlsShown, element);
                }
            }
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
    removeCallback: function(callbacks, callback) {
        if (Utils.arrayContains(callbacks, callback)) {
            Utils.arrayRemove(callbacks, callback);
        }
    },
    addOnExitDialogShown: function(callback) {
        this.addCallback(this.onExitDialogShown, callback);
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
    addOnGenericOverlayHidden: function(callback) {
        this.addCallback(this.onGenericOverlayHidden, callback);
    },
    addOnUiChange: function(callback) {
        this.addCallback(this.onUiChange, callback);
    },
    removeOnUiChange: function(callback) {
        this.removeCallback(this.onUiChange, callback);
    },
    addOnPlayerSuggestionsHidden: function(callback) {
        this.addCallback(this.onPlayerSuggestionsHidden, callback);
    },
    addOnPlayerSuggestionsShown: function(callback) {
        this.addCallback(this.onPlayerSuggestionsShown, callback);
    },
    addOnPlayerControlsShown: function(callback) {
        this.addCallback(this.onPlayerControlsShown, callback);
    }
};

YouTubeEventManager.init();
