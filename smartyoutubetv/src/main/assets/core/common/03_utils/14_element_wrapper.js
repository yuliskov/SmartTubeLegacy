/**
 * Description:<br/>
 * Overrides creation of any element
 */

console.log("Scripts::Running script element_wrapper.js");

var ElementWrapper = {
    TAG: 'ElementWrapper',
    handlers: [],

    init: function() {
        this.applyWrapping();
    },

    addHandler: function(handler) {
        this.handlers.push(handler);
    },

    removeHandler: function(handler) {
        var index = this.handlers.indexOf(handler);
        if (index !== -1) {
            this.handlers.splice(index, 1);
        }
    },

    applyWrapping: function() {
        var $this = this;

        // wrapper already established
        if (document.createElementReal) {
            return;
        }

        document.createElementReal = document.createElement;

        document.createElement = function(tagName) {
            Log.d($this.TAG, tagName);

            var elem = document.createElementReal(tagName);

            for (var i = 0; i < $this.handlers.length; i++) {
                var handler = $this.handlers[i];

                if (handler.elementTag && (tagName.toUpperCase() == handler.elementTag.toUpperCase())) {
                    Log.d($this.TAG, "calling handler for tag " + tagName);
                    handler.onCreate && handler.onCreate(elem);
                }
            }

            return elem;
        };
    }
};

ElementWrapper.init();
