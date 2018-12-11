/**
 * Can add listener even to non-existent elements
 */

console.log("Scripts::Running script listener_util.js");

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
        var container = this.getContainer(selectorOrElement);

        if (!container || !container.children || !container.children.length) {
            Log.d(this.TAG, "Can't add listener: element " + selectorOrElement + " not initialized... waiting...");
            this.addPendingHandler(
                {
                    selectorOrElement: selectorOrElement,
                    event: event,
                    handler: handler
                });
        } else {
            Log.d(this.TAG, "Element already initialized... add listener to it " + container);
            container.addEventListener(event, handler, false);
        }
    },

    removeListener: function(selectorOrElement, event, handler) {
        var container = this.getContainer(selectorOrElement);

        if (!container || !container.children || !container.children.length) {
            Log.d(this.TAG, "Can't remove listener: element " + selectorOrElement + " not initialized... waiting...");
            this.addPendingHandler(
                {
                    selectorOrElement: selectorOrElement,
                    event: event,
                    handler: handler
                });
        } else {
            Log.d(this.TAG, "Element already initialized... add listener to it " + container);
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
        var onModelChanged = function() {
            if ($this.handlers.length == 0) {
                surface.removeEventListener(YouTubeConstants.MODEL_CHANGED_EVENT, onModelChanged, false);
                $this.initDone = false;
                return;
            }

            Log.d($this.TAG, "Checking pending handlers... " + $this.handlers);

            for (var i = 0; i < $this.handlers.length; i++) {
                var res = $this.executeHandler($this.handlers[i]);
                if (res)
                    $this.handlers.splice(i, 1); // remove handler on success
            }
        };
        surface.addEventListener(YouTubeConstants.MODEL_CHANGED_EVENT, onModelChanged, false);
    },

    executeHandler: function(handlerObj) {
        var container = this.getContainer(handlerObj.selectorOrElement);

        if (!container || !container.children || !container.children.length) {
            return false;
        } else {
            Log.d(this.TAG, "Success, element is found... add listener to it " + container);
            container.addEventListener(handlerObj.event, handlerObj.handler, false);
            return true;
        }
    }
};
