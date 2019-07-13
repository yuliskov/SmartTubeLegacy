function DummyVideoHandler() {
    this.TAG = 'EventLoggerHandler';

    this.onCreate = function(video) {
        EventUtils.turnOffEvents(video);
        
        // // required props
        // EventUtils.turnOffProp(video, 'duration');
        // EventUtils.turnOffProp(video, 'currentTime');
        // EventUtils.turnOffProp(video, 'paused');
        //
        // // let's save some resources
        // EventUtils.turnOffProp(video, 'src', true);
        // EventUtils.turnOffProp(video, 'currentSrc', true);
        // // above lines won't work without overriding network state
        // EventUtils.turnOffProp(video, 'networkState');
        //
        // EventUtils.addImitateEndingFunction(video);

        this.redirectProps(video);
        this.initProps(video);

        // var $this = this;
        // this.onPropChange(video, 'src', function(val) {
        //     Log.d($this.TAG, "Video src changed: " + val);
        //     $this.onVideoSrcChanged(video);
        // });

        this.addImitateEndingFunction(video);
    };

    this.redirectProps = function(video) {
        // testing
        EventUtils.turnOffProp(video, 'videoWidth');
        EventUtils.turnOffProp(video, 'videoHeight');
        EventUtils.turnOffProp(video, 'webkitDecodedFrameCount');
        EventUtils.turnOffProp(video, 'src');
        EventUtils.turnOffProp(video, 'currentSrc');
        EventUtils.turnOffProp(video, 'networkState');
        EventUtils.turnOffProp(video, 'readyState');
        EventUtils.turnOffProp(video, 'currentTime');
        EventUtils.turnOffProp(video, 'duration');
        EventUtils.turnOffProp(video, 'paused');
        EventUtils.turnOffProp(video, 'webkitAudioDecodedByteCount');
        EventUtils.turnOffProp(video, 'webkitVideoDecodedByteCount');
        EventUtils.turnOffProp(video, 'baseURI');
    };

    this.initProps = function(video) {
        video.properties.videoWidth = 1280;
        video.properties.videoHeight = 1280;
        video.properties.webkitDecodedFrameCount = 500;
        video.properties.src = 'blob:https://www.youtube.com/2abe4fbe-1ff7-456c-9dc5-a28539e2035d';
        video.properties.currentSrc = 'blob:https://www.youtube.com/2abe4fbe-1ff7-456c-9dc5-a28539e2035d';
        video.properties.networkState = 2;
        video.properties.readyState = 4;
        video.properties.currentTime = 21.665161;
        video.properties.duration = 749.3775963718821;
        video.properties.paused = false;
        video.properties.webkitAudioDecodedByteCount = 203004;
        video.properties.webkitVideoDecodedByteCount = 2898507;
        video.properties.baseURI = 'https://www.youtube.com/tv#/watch/video/idle?v=bR66Yyj3p48&resume';
    };

    this.addImitateEndingFunction = function(video) {
        video.imitateEnding = function() {
            // required if you intend to override 'src' and 'currentSrc'
            this.properties.networkState = 2;
            // all props are required
            this.properties.duration = 999;
            this.properties.currentTime = 999;

            this.properties.readyState = 4;

            this.listeners['pause'][0]({type: 'pause', isTrusted: true});
            this.listeners['timeupdate'][0]({type: 'timeupdate', isTrusted: true});
        };
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

    this.onVideoSrcChanged = function(video) {
        // required if you intend to override 'src' and 'currentSrc'
        video.properties.networkState = 2;
        // all props are required
        video.properties.duration = 999;
        video.properties.currentTime = 10;

        video.properties.readyState = 4;

        video.listeners['pause'][0]({type: 'pause', isTrusted: true});
        video.listeners['timeupdate'][0]({type: 'timeupdate', isTrusted: true});
    };
}