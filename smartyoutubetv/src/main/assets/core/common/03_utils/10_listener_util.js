/**
 * Can add listener even to non-existent elements
 */

console.log("Scripts::Running script listener_util.js");

/**
 * Uses 'model-change' event to watch out over the element initialization
 */
var ListenerUtil = {
    TAG: 'ListenerUtil',
    handlers: [],
    initDone: false,
    ADD_HANDLER: 'add_handler',
    REMOVE_HANDLER: 'remove_handler',

    /**
     * Adds lister or waits till element be initialized
     * @param selectorOrElement desired element or selector
     * @param event desired event
     * @param handler callback
     * @param useCapture enable advanced propagation technique
     */
    addListener: function(selectorOrElement, event, handler, useCapture) {
        useCapture = useCapture == undefined ? false : useCapture;

        this.addRemoveListener({
            selectorOrElement: selectorOrElement,
            event: event,
            handler: handler,
            type: this.ADD_HANDLER,
            useCapture: useCapture
        });
    },

    /**
     * Remove lister or waits till element be initialized
     * @param selectorOrElement desired element or selector
     * @param event desired event
     * @param handler callback
     */
    removeListener: function(selectorOrElement, event, handler) {
        this.addRemoveListener({
            selectorOrElement: selectorOrElement,
            event: event,
            handler: handler,
            type: this.REMOVE_HANDLER
        });
    },

    addRemoveListener: function(listenerSpec) {
        var isWindow = listenerSpec.selectorOrElement instanceof Window;
        if (!isWindow && !EventUtils.toSelector(listenerSpec.selectorOrElement)) {
            Log.e(this.TAG, "can't " + listenerSpec.type + ": selector or element is: " + listenerSpec.selectorOrElement);
            return;
        }

        Log.d(this.TAG, "Trying to " + listenerSpec.type + " to the " + EventUtils.toSelector(listenerSpec.selectorOrElement));

        var container = this.getContainer(listenerSpec.selectorOrElement);

        if (!isWindow && (!container || !container.children || (!container.children.length && container.tagName.toUpperCase() == 'DIV'))) {
            Log.d(this.TAG, "Can't " + listenerSpec.type + ": element " + EventUtils.toSelector(listenerSpec.selectorOrElement) + " not initialized... waiting...");
            this.addPendingHandler(listenerSpec);
        } else if (listenerSpec.type == this.ADD_HANDLER) {
            Log.d(this.TAG, "Element initialized... add listener to it " + EventUtils.toSelector(container));
            container.addEventListener(listenerSpec.event, listenerSpec.handler, listenerSpec.useCapture);
        } else if (listenerSpec.type == this.REMOVE_HANDLER) {
            Log.d(this.TAG, "Element initialized... remove listener from it " + EventUtils.toSelector(container));
            container.removeEventListener(listenerSpec.event, listenerSpec.handler, listenerSpec.useCapture);
        }
    },

    getContainer: function(selectorOrElement) {
        var container = null;

        if (Utils.isSelector(selectorOrElement)) {
            container = Utils.$(selectorOrElement);
        } else {
            container = selectorOrElement;
        }

        return container;
    },

    addPendingHandler: function(handlerObj) {
        this.handlers.push(handlerObj);
        this.initModelChangeListener();
    },

    initModelChangeListener: function() {
        if (this.initDone) {
            Log.d(this.TAG, "Root listener already initialized.");
            return;
        }

        this.initDone = true;

        var $this = this;
        var surface = Utils.$(YouTubeSelectors.SURFACE_AREA);

        if (!surface || YouTubeUtils.isPlayerOpened()) { // running on early stage??
            Log.d(this.TAG, "Can't instantiate root listener: " + YouTubeSelectors.SURFACE_AREA);

            this.initDone = false;

            setTimeout(function() {
               $this.initModelChangeListener();
            }, 1000);

            return;
        }

        var checkHandlers = function() {
            Log.d($this.TAG, "Running pending handlers... " + $this.handlers.length);

            var tmpHandlers = $this.handlers;
            $this.handlers = [];

            for (var i = 0; i < tmpHandlers.length; i++) {
                $this.addRemoveListener(tmpHandlers[i]);
            }
        };

        var onModelChanged = function() {
            Log.d($this.TAG, "Root listener is called. Assume that model is changed.");

            if ($this.handlers.length == 0) {
                Log.d($this.TAG, "No pending handlers. Removing root listener...");

                surface.removeEventListener(YouTubeConstants.MODEL_CHANGED_EVENT, onModelChanged, false);
                $this.initDone = false;

                return;
            }

            checkHandlers();
        };

        Log.d(this.TAG, "Adding root listener...");
        surface.addEventListener(YouTubeConstants.MODEL_CHANGED_EVENT, onModelChanged, false);
    }
};
