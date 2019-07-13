function DummyVideoHandler() {
    this.TAG = 'DummyVideoHandler';

    this.onCreate = function(video) {
        EventUtils.turnOffEvents(video);

        this.redirectProps(video);
        this.initProps(video);

        this.addImitateEndingFunction(video);
    };

    this.redirectProps = function(video) {
        EventUtils.turnOffProp(video, 'webkitDecodedFrameCount');
        EventUtils.turnOffProp(video, 'webkitAudioDecodedByteCount');
        EventUtils.turnOffProp(video, 'webkitVideoDecodedByteCount');
        EventUtils.turnOffProp(video, 'networkState');
        EventUtils.turnOffProp(video, 'readyState');
        EventUtils.turnOffProp(video, 'paused');
        EventUtils.turnOffProp(video, 'ended');

        // ??
        EventUtils.turnOffProp(video, 'videoWidth');
        EventUtils.turnOffProp(video, 'videoHeight');

        EventUtils.turnOffProp(video, 'baseURI');
        EventUtils.turnOffProp(video, 'currentSrc');

        EventUtils.turnOffProp(video, 'duration');
        
        EventUtils.turnOffProp(video, 'currentTime', true);

        var $this = this;
        EventUtils.turnOffProp(video, 'src', false, function(val) {
            Log.d($this.TAG, "Video src changed: " + val);
            $this.imitatePlaying(video);
        });
    };

    this.initProps = function(video) {
        video.properties.webkitDecodedFrameCount = 500;
        video.properties.webkitAudioDecodedByteCount = 203004;
        video.properties.webkitVideoDecodedByteCount = 2898507;
        video.properties.networkState = 2;
        video.properties.readyState = 4;
        video.properties.paused = false;

        // ??
        video.properties.videoWidth = 1280;
        video.properties.videoHeight = 1280;

        video.properties.baseURI = 'https://www.youtube.com/tv#/watch/video/idle?v=bR66Yyj3p48&resume';
        video.properties.currentSrc = 'blob:https://www.youtube.com/2abe4fbe-1ff7-456c-9dc5-a28539e2035d';

        video.properties.duration = 749;

        // video.properties.currentTime = 21.665161;
        video.properties.src = 'blob:https://www.youtube.com/2abe4fbe-1ff7-456c-9dc5-a28539e2035d';
    };

    this.addImitateEndingFunction = function(video) {
        var $this = this;
        video.imitateEnding = function() {
            Log.d($this.TAG, "End of the video is reached");
            $this.imitateEnding(video);
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

    this.imitatePlaying = function(video) {
        var i = 0;
        var $this = this;
        video.properties.currentTime = 0;
        video.properties.paused = false;
        video.properties.ended = false;
        var interval = setInterval(function() {
            Log.d($this.TAG, "onVideoSrcChanged");

            if (i > 3) {
                clearInterval(interval);
                return;
            } else {
                i++;
            }

            video.properties.currentTime++;
            video.listeners['pause'][0]({type: 'pause', isTrusted: true});
            video.listeners['timeupdate'][0]({type: 'timeupdate', isTrusted: true});
        }, 100);
    };

    this.imitateEnding = function(video) {
        var i = 0;
        var $this = this;
        var curTime = video.properties.currentTime;
        video.properties.currentTime = video.properties.duration;
        video.properties.paused = true;
        video.properties.ended = true;
        var interval = setInterval(function() {
            Log.d($this.TAG, "imitateEnding");

            if (i > 3) {
                clearInterval(interval);
                video.properties.currentTime = curTime;
                video.properties.paused = false;
                video.properties.ended = false;
                return;
            } else {
                i++;
            }

            video.listeners['pause'][0]({type: 'pause', isTrusted: true});
            video.listeners['timeupdate'][0]({type: 'timeupdate', isTrusted: true});
        }, 100);
    };
}