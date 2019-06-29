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

function EventLoggerHandler() {
    this.TAG = 'EventLoggerHandler';

    this.onCreate = function(video) {
        this.turnOffEvents(video);

        // required props
        this.turnOffProp(video, 'duration');
        this.turnOffProp(video, 'currentTime');
        this.turnOffProp(video, 'paused');

        // let's save some resources
        this.turnOffProp(video, 'src', true);
        this.turnOffProp(video, 'currentSrc', true);
        // above lines won't work without overriding network state
        this.turnOffProp(video, 'networkState');

        this.addImitateEndingFunction(video);
    };

    this.addImitateEndingFunction = function(video) {
        video.imitateEnding = function() {
            // required if you intend to override 'src' and 'currentSrc'
            this.properties.networkState = 2;
            // all props are required
            this.properties.duration = 999;
            this.properties.currentTime = 999;
            this.listeners['pause'][0]({type: 'pause'});
            this.listeners['timeupdate'][0]({type: 'timeupdate'});
        };
    };

    this.turnOffEvents = function(video) {
        var $this = this;

        video.addEventListenerReal = video.addEventListener;

        video.addEventListener = function(type, listener, options) {
            Log.d($this.TAG, "Add event listener: " + type + " " + listener);

            if (!video.listeners) {
                video.listeners = {};
            }

            if (!video.listeners[type]) {
                video.listeners[type] = [];
            }

            video.listeners[type].push(listener);

            // events essential for the playback:
            // pause, timeupdate

            // next events not needed for the playback:
            // loadstart, durationchange, loadedmetadata, loadeddata, ended

            // function wrapper(e) {
            //     Log.d($this.TAG, "Calling listener: " + e.type + ", event=" + EventUtils.stringify(e));
            //     listener.call(video, e);
            // }
            //
            // if (type == 'pause' || type == 'timeupdate') {
            //     this.addEventListenerReal(type, wrapper, options);
            // }
        };
    };

    this.turnOffProp = function(obj, propName, persist) { // pure function
        if (!obj.properties) {
            obj.properties = {};
        }

        obj.properties[propName] = obj[propName];

        Object.defineProperty(obj, propName, {
            get: function() {
                // Log.d($this.TAG, "Getting property: " + propName + ", value: " + obj.properties[propName]);

                return obj.properties[propName];
            },
            set: function(val) {
                if (persist) {
                    obj.properties[propName] = val;
                }
            }
        });
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
    this.handlers = [new EventLoggerHandler(), new VolumeHandler()];

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

