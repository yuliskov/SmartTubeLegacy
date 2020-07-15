/**
 * Imitates different state of the video tag.
 */

console.log("Scripts::Running playback_strategy_1.js");

var PlaybackStrategy1 = {
    curTime: 0,
    defaultDuration: 1000000,
    defaultWidth: 1280,
    defaultHeight: 720,
    defaultBaseURI: 'https://www.youtube.com/tv#/watch/video/idle?v=bR66Yyj3p48&resume',
    defaultSrc: 'blob:https://www.youtube.com/2abe4fbe-1ff7-456c-9dc5-a28539e2035d',
    defaultNetworkState: 2,
    defaultReadyState: 4,
    defaultDecodedFrameCount: 500,
    defaultAudioDecodedByteCount: 203004,
    defaultVideoDecodedByteCount: 2898507,

    initProps: function(video) {
        video.properties.webkitDecodedFrameCount = this.defaultDecodedFrameCount;
        video.properties.webkitAudioDecodedByteCount = this.defaultAudioDecodedByteCount;
        video.properties.webkitVideoDecodedByteCount = this.defaultVideoDecodedByteCount;
        video.properties.networkState = this.defaultNetworkState;
        video.properties.readyState = this.defaultReadyState;
        video.properties.paused = false;

        video.properties.videoWidth = this.defaultWidth;
        video.properties.videoHeight = this.defaultHeight;

        video.properties.baseURI = this.defaultBaseURI;
        video.properties.currentSrc = this.defaultSrc;

        video.properties.duration = this.defaultDuration;

        // video.properties.currentTime = 21.665161;
        video.properties.src = this.defaultSrc;
    },

    endReached: function(video) {
        this.curTime = video.properties.currentTime;

        video.properties.currentTime = video.properties.duration;
        video.properties.paused = true;
        video.properties.ended = true;
    },

    videoClosed: function(video) {
        // do cleanup, prepare for the next video
        video.properties.currentTime = this.curTime;
        video.properties.paused = false;
        video.properties.ended = false;

        // NEW
        video.properties.duration = NaN;
        video.properties.currentTime = 0;
        video.properties.paused = true;
        video.properties.currentSrc = "";
        video.properties.src = "";
    },

    endPlayback: function(video) {
        // test
        //video.listeners['play'][0]({type: 'play', isTrusted: true});
        //video.listeners['progress'][0]({type: 'progress', isTrusted: true});

        video.listeners['pause'][0]({type: 'pause', isTrusted: true});
        // imitate ending, note that currentTime should be equals to duration
        video.listeners['timeupdate'][0]({type: 'timeupdate', isTrusted: true});

        //video.listeners['ended'][0]({type: 'ended', isTrusted: true});
    },

    startPlaying: function(video) {
        video.properties.currentTime = 0;
        video.properties.paused = false;
        video.properties.ended = false;

        // NEW
        video.properties.duration = this.defaultDuration;
        video.properties.currentSrc = this.defaultSrc;
        video.properties.src = this.defaultSrc;
    },

    playIncrement: function(video) {
        video.properties.currentTime++;
        // magically but without pause other functions won't work
        video.listeners['pause'][0]({type: 'pause', isTrusted: true});
        video.listeners['timeupdate'][0]({type: 'timeupdate', isTrusted: true});
    }
};
