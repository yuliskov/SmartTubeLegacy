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
     */
    addListener: function(selectorOrElement, event, handler) {
        this.addRemoveListener({
            selectorOrElement: selectorOrElement,
            event: event,
            handler: handler,
            type: this.ADD_HANDLER
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
        if (!EventUtils.toSelector(listenerSpec.selectorOrElement)) {
            Log.e(this.TAG, "can't " + listenerSpec.type + ": selector or element is: " + listenerSpec.selectorOrElement);
            return;
        }

        Log.d(this.TAG, "Trying to " + listenerSpec.type + " to the " + EventUtils.toSelector(listenerSpec.selectorOrElement));

        var container = this.getContainer(listenerSpec.selectorOrElement);

        if (!container || !container.children || !container.children.length) {
            Log.d(this.TAG, "Can't " + listenerSpec.type + ": element " + EventUtils.toSelector(listenerSpec.selectorOrElement) + " not initialized... waiting...");
            this.addPendingHandler(listenerSpec);
        } else if (listenerSpec.type == this.ADD_HANDLER) {
            Log.d(this.TAG, "Element initialized... add listener to it " + EventUtils.toSelector(container));
            container.addEventListener(listenerSpec.event, listenerSpec.handler, false);
        } else if (listenerSpec.type == this.REMOVE_HANDLER) {
            Log.d(this.TAG, "Element initialized... remove listener from it " + EventUtils.toSelector(container));
            container.removeEventListener(listenerSpec.event, listenerSpec.handler, false);
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
        if (this.initDone)
            return;

        this.initDone = true;

        var $this = this;
        var surface = Utils.$(YouTubeSelectors.SURFACE_AREA);

        if (!surface) { // running on early stage??
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
            if ($this.handlers.length == 0) {
                surface.removeEventListener(YouTubeConstants.MODEL_CHANGED_EVENT, onModelChanged, false);
                $this.initDone = false;
                return;
            }

            checkHandlers();
        };

        surface.addEventListener(YouTubeConstants.MODEL_CHANGED_EVENT, onModelChanged, false);
    }
};
