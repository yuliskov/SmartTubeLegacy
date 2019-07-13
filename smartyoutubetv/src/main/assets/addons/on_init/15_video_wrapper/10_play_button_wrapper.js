/**
 * Description:
 * Adds useful methods to element api
 */

console.log("Scripts::Running script play_button_wrapper.js");

function PlayButtonWrapperAddon() {
    this.TAG = 'PlayButtonWrapperAddon';

    this.run = function() {
        this.applyWrapping();
    };

    this.applyWrapping = function() {
        var $this = this;

        document.createElementReal = document.createElement;

        document.createElement = function(tagName) {
            Log.d($this.TAG, "Wrapping tag " + tagName);

            var elem = this.createElementReal(tagName);

            elem.addEventListenerReal = elem.addEventListener;

            elem.addEventListener = function(type, listener, options) {
                Log.d($this.TAG, "Add event listener: " + type + " " + listener);

                if (!this.listeners) {
                    this.listeners = {};
                }

                this.listeners[type] = listener;

                this.addEventListenerReal(type, listener, options);
            };

            return elem;
        };
    };
}

// new PlayButtonWrapperAddon().run();

