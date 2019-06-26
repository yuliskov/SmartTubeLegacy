/**
 * Description:<br/>
 * Overrides volume property<br/>
 * Adds useful methods to video api<br/>
 * This methods have usage only in exoplayer context
 */

console.log("Scripts::Running script video_wrapper.js");

function VolumeHandler() {
    this.TAG = 'VolumeHandler';

    this.onCreate = function(video) {
        this.overrideVolumeProp(video);
    };

    this.overrideVolumeProp = function(video) {
        Log.d(this.TAG, "Overriding video's volume property");
        Utils.overrideProp(video, 'volume', 0);
    };
}

/**
 * Fix <b>Global AFR</b> on some devices<br/>
 * Fix excessive resource consumption<br/>
 */
function VideoSrcHandler() {
    this.TAG = 'VideoSrcHandler';

    this.onCreate = function(video) {
        this.overrideVideoSrc(video);
    };

    this.overrideVideoSrc = function(video) {
        Utils.overrideProp(video, 'src', '');
        Utils.overrideProp(video, 'currentSrc', '');
    };
}

function VideoWrapperAddon() {
    this.TAG = 'VideoWrapperAddon';
    this.handlers = [new VolumeHandler(), new VideoSrcHandler()];

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

                for (var i = 0; i < $this.handlers.length; i++) {
                    var handler = $this.handlers[i];
                    handler.onCreate(video);
                }

                return video;
            }

            return this.createElementReal(tagName);
        };
    };

    // this.addFakeListener = function(video) {
    //     var $this = this;
    //
    //     video.addEventListenerReal = video.addEventListener;
    //
    //     video.addEventListener = function(type, listener, options) {
    //         Log.d($this.TAG, "Add event listener: " + type + " " + listener);
    //
    //         if (!this.listeners) {
    //             this.listeners = {};
    //         }
    //
    //         Log.d($this.TAG, "Storing " + type + " listener for future use...");
    //         this.listeners[type] = listener;
    //
    //         // if (type == 'timeupdate' || type == 'ended' || type == 'playing' || type == 'loadeddata' || type == 'focus' || type == 'play') {
    //         //     return;
    //         // }
    //         //
    //         // this.addEventListenerReal(type, listener, options);
    //     };
    //
    //     video.dispatchEventReal = video.dispatchEvent;
    //
    //     video.dispatchEvent = function(event) {
    //         Log.d($this.TAG, "Dispatching event: " + event);
    //
    //         this.dispatchEventReal(event);
    //     };
    // };
}

if (DeviceUtils.isExo()) {
    new VideoWrapperAddon().run();
}

