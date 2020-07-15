/**
 * Description:<br/>
 * Overrides volume property<br/>
 * Adds useful methods to video api<br/>
 * This methods have usage only in exoplayer context
 */

console.log("Scripts::Running dummy_video_handler.js");

function DummyVideoHandler(addons) {
    this.TAG = 'DummyVideoHandler';
    this.elementTag = 'video';
    this.addons = addons;

    this.onCreate = function(video) {
        EventUtils.turnOffEvents(video);

        this.redirectProps(video);

        this.onInitAddons(video);
    };

    this.redirectProps = function(video) {
        var $this = this;

        EventUtils.turnOffProp(video, 'webkitDecodedFrameCount');
        EventUtils.turnOffProp(video, 'webkitAudioDecodedByteCount');
        EventUtils.turnOffProp(video, 'webkitVideoDecodedByteCount');
        EventUtils.turnOffProp(video, 'networkState');
        EventUtils.turnOffProp(video, 'readyState');
        EventUtils.turnOffProp(video, 'ended');

        // ??
        EventUtils.turnOffProp(video, 'videoWidth');
        EventUtils.turnOffProp(video, 'videoHeight');

        EventUtils.turnOffProp(video, 'baseURI');
        EventUtils.turnOffProp(video, 'currentSrc');

        // EventUtils.turnOffProp(video, 'duration');

        // EventUtils.turnOffProp(video, 'paused');

        EventUtils.turnOffMethod(video, 'play', false, function() {
            Log.d($this.TAG, "On paused changed " + false);
            video.properties.paused = false;
            $this.onPausedChangeAddons(video);
        });

        EventUtils.turnOffMethod(video, 'pause', false, function() {
            Log.d($this.TAG, "On paused changed " + true);
            video.properties.paused = true;
            $this.onPausedChangeAddons(video);
        });

        EventUtils.turnOffProp(video, 'paused', false, function(val) {
            Log.d($this.TAG, "On paused changed " + val);
            $this.onPausedChangeAddons(video);
        });

        EventUtils.turnOffProp(video, 'currentTime', true, function(val) {
            Log.d($this.TAG, "On set currentTime " + val);
            $this.onCurrentTimeAddons(video);
        });

        EventUtils.turnOffProp(video, 'duration', false, function(val) {
            Log.d($this.TAG, "On set duration " + val);
            $this.onDurationAddons(video);
        });

        EventUtils.turnOffProp(video, 'src', true, function(val) {
            Log.d($this.TAG, "Video src changed: " + val);
            $this.onSrcChangeAddons(video);
        });
    };

    this.onInitAddons = function(video) {
        for (var i = 0; i < this.addons.length; i++) {
            var addon = this.addons[i];
            if (addon.onInit) {
                addon.onInit(video);
            }
        }
    };

    this.onCurrentTimeAddons = function(video) {
        for (var i = 0; i < this.addons.length; i++) {
            var addon = this.addons[i];
            if (addon.onCurrentTime) {
                addon.onCurrentTime(video);
            }
        }
    };

    this.onDurationAddons = function(video) {
        for (var i = 0; i < this.addons.length; i++) {
            var addon = this.addons[i];
            if (addon.onDuration) {
                addon.onDuration(video);
            }
        }
    };

    this.onSrcChangeAddons = function(video) {
        for (var i = 0; i < this.addons.length; i++) {
            var addon = this.addons[i];
            if (addon.onSrcChange) {
                addon.onSrcChange(video);
            }
        }
    };

    this.onPausedChangeAddons = function(video) {
        for (var i = 0; i < this.addons.length; i++) {
            var addon = this.addons[i];
            if (addon.onPausedChange) {
                addon.onPausedChange(video);
            }
        }
    };

    /**
     * NOTE: you will loose original property!
     */
    this.onPropChange = function(obj, propName, callback) {
        Object.defineProperty(obj, propName, {
            set: function(val) {
                if (callback) {
                    callback(val);
                }
            }
        });
    };
}

if (DeviceUtils.isExo()) {
    ElementWrapper.addHandler(new DummyVideoHandler([new PlaybackEndAddon(), new ScreenMirrorAddon()]));
}