/**
 * Description:
 * Adds useful methods to video api
 * This methods have usage only in exoplayer context
 */

console.log("Scripts::Running script video_wrapper.js");

function VideoWrapperAddon() {
    this.TAG = 'VideoWrapperAddon';

    this.run = function() {
        this.applyWrapping();
    };

    this.applyWrapping = function() {
        var $this = this;

        document.createElementReal = document.createElement;

        document.createElement = function(tagName) {
            if (tagName.toUpperCase() == 'VIDEO') {
                Log.d($this.TAG, "Wrapping tag " + tagName);

                var video = document.createElementReal(tagName);

                video.addEventListenerReal = video.addEventListener;

                video.addEventListener = function(type, listener, options) {
                    if (!this.listeners) {
                        this.listeners = {};
                    }

                    Log.d($this.TAG, "Storing " + type + " listener for future use...");
                    this.listeners[type] = listener;

                    if (type == 'timeupdate' || type == 'ended' || type == 'abort') {
                        return;
                    }

                    this.addEventListenerReal(type, listener, options);
                };

                return video;
            }

            return this.createElementReal(tagName);
        };
    };
}

// new VideoWrapperAddon().run();

