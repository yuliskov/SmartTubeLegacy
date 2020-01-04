/**
 * Imitates different state of the video tag.
 */

console.log("Scripts::Running playback_state_util.js");

var PlaybackStateUtil = {
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

    justCreated: function(video) {
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

    startPlaying: function(video) {
        video.properties.currentTime = 0;
        video.properties.paused = false;
        video.properties.ended = false;

        // NEW
        video.properties.duration = this.defaultDuration;
        video.properties.currentSrc = this.defaultSrc;
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
    }
};
