/**
 * Description:<br/>
 * Overrides volume property<br/>
 * Adds useful methods to video api<br/>
 * This methods have usage only in exoplayer context
 */

console.log("Scripts::Running script video_wrapper.js");

function VideoWrapperAddon() {
    this.TAG = 'VideoWrapperAddon';
    this.handlers = [new DummyVideoHandler()];
    //this.handlers = [new VolumeHandler(), new VideoSrcHandler()];

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
}

if (DeviceUtils.isExo()) {
    new VideoWrapperAddon().run();
}

