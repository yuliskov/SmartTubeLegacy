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

    /**
     * Adds lister or waits till element be initialized
     * @param selectorOrElement desired element as selector
     * @param event desired event
     * @param handler callback
     */
    addListener: function(selectorOrElement, event, handler) {
        if (!EventUtils.toSelector(selectorOrElement)) {
            Log.e(this.TAG, "can't add add listener: selector or element is: " + selectorOrElement);
            return;
        }

        Log.d(this.TAG, "Trying to add listener to the " + EventUtils.toSelector(selectorOrElement));

        var container = this.getContainer(selectorOrElement);

        if (!container || !container.children || !container.children.length) {
            Log.d(this.TAG, "Can't add listener: element " + EventUtils.toSelector(selectorOrElement) + " not initialized... waiting...");
            this.addPendingHandler(
                {
                    selectorOrElement: selectorOrElement,
                    event: event,
                    handler: handler
                });
        } else {
            Log.d(this.TAG, "Element already initialized... add listener to it " + EventUtils.toSelector(container));
            container.addEventListener(event, handler, false);
        }
    },

    removeListener: function(selectorOrElement, event, handler) {
        var container = this.getContainer(selectorOrElement);

        if (!container || !container.children || !container.children.length) {
            Log.d(this.TAG, "Can't remove listener: element " + EventUtils.toSelector(selectorOrElement) + " not initialized... waiting...");
            this.addPendingHandler(
                {
                    selectorOrElement: selectorOrElement,
                    event: event,
                    handler: handler
                });
        } else {
            Log.d(this.TAG, "Element already initialized... remove listener from it " + EventUtils.toSelector(container));
            container.removeEventListener(event, handler, false);
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
        var surface = Utils.$(YouTubeConstants.SURFACE_CONTENT_SELECTOR);
        var checkHandlers = function() {
            Log.d($this.TAG, "Checking pending handlers... " + $this.handlers);

            for (var i = 0; i < $this.handlers.length; i++) {
                var res = $this.executeHandler($this.handlers[i]);
                if (res)
                    $this.handlers.splice(i, 1); // remove handler on success
            }
        };
        var onModelChanged = function() {
            if ($this.handlers.length == 0) {
                surface.removeEventListener(YouTubeConstants.MODEL_CHANGED_EVENT, onModelChanged, false);
                $this.initDone = false;
                return;
            }

            setTimeout(checkHandlers, 1000);
        };
        surface.addEventListener(YouTubeConstants.MODEL_CHANGED_EVENT, onModelChanged, false);
    },

    executeHandler: function(handlerObj) {
        var container = this.getContainer(handlerObj.selectorOrElement);

        if (!container || !container.children || !container.children.length) {
            Log.d(this.TAG, "Can't execute handler: element " + EventUtils.toSelector(handlerObj.selectorOrElement) + " not initialized... waiting...");
            return false;
        } else {
            Log.d(this.TAG, "Success, element is found... add listener to it " + EventUtils.toSelector(container));
            container.addEventListener(handlerObj.event, handlerObj.handler, false);
            return true;
        }
    }
};
