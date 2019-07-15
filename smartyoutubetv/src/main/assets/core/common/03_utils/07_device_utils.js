/**
 * Device dependent routines
 */

console.log("Scripts::Running script device_utils.js");

var DeviceUtils = {
    TAG: 'DeviceUtils',
    VP9: 'vp9',
    AVC: 'avc',
    WEBVIEW: 'WebView',
    XWALK: 'XWalk',
    MESSAGE_APP_LOADED: 'message_app_loaded',
    MESSAGE_MIC_CLICKED: 'message_mic_clicked',
    MESSAGE_SYNC_LANG: 'message_sync_lang',
    MESSAGE_AUTHORIZATION_HEADER: 'message_authorization_header',
    PLAYBACK_UNKNOWN: 0,
    PLAYBACK_IS_WORKING: 1,
    PLAYBACK_NOT_WORKING: 2,

    init: function() {
        // do init here
    },

    forceEnableAllCodecs: function() {
        if (!window.MediaSource)
            window.MediaSource = window.WebKitMediaSource;

        if (!window.MediaSource) {
            console.log('DeviceUtils::forceEnableAllCodecs: MediaSource is null');
            return;
        }

        window.MediaSource.isTypeSupported = function(type) {
            console.log('DeviceUtils::force enable ' + type + ' codec');
            return true;
        };
    },

    disableCodec: function(codec) {
        if (!codec) {
            console.log('DeviceUtils::disableCodec: codec is null');
            return;
        }

        if (!window.MediaSource) {
            window.MediaSource = window.WebKitMediaSource;
        }

        if (!window.MediaSource) {
            console.log('DeviceUtils::disableCodec: MediaSource is null');
            return;
        }

        console.log('DeviceUtils::disableCodec: ' + codec + ' on ' + window.thisDevice + ' device');

        var $this = this;

        function overrideIsTypeSupported(origin, obj) {
            return function(fullCodec) {
                var supported = origin.call(obj, fullCodec);
                console.log('DeviceUtils::isTypeSupported ' + fullCodec + ' ' + supported);

                // YouTube's 4K videos encoded exclusively in WEBM codec
                if ($this.isPlaybackWorking()   &&
                    !$this.isLive(fullCodec)    &&
                    !$this.is4KCodec(codec)     &&
                    $this.specCmp(fullCodec, codec)) {
                    return false;
                }

                return supported;
            }
        }

        window.MediaSource.isTypeSupported = overrideIsTypeSupported(window.MediaSource.isTypeSupported, window.MediaSource);
    },

    disableWebmCodec: function() {
        this.disableCodec(this.VP9);
    },

    disableMP4Codec: function() {
        this.disableCodec(this.AVC);
    },

    /**
     * Compare special strings.
     * Returns true even when the second string is empty: this.specCmp('abc', '') == true
     * Or when strings partially matched: this.specCmp('abc', 'ab') == true
     */
    specCmp: function(fullSpec, spec) {
        fullSpec = fullSpec.toLowerCase();
        spec = spec.toLowerCase();
        return fullSpec.indexOf(spec) >= 0;
    },

    getApp: function() {
        if (!window.app) {
            console.log("DeviceUtils::global var 'app' isn't defined");
        }

        return window.app;
    },

    getEngineType: function() {
        return this.getApp().getEngineType();
    },

    getAppVersion: function() {
        return this.getApp().getAppVersion();
    },

    getDeviceName: function() {
        return this.getApp().getDeviceName();
    },

    getDeviceHardware: function() {
        return this.getApp().getDeviceHardware();
    },

    showExitMsg: function() {
        return this.getApp().showExitMsg();
    },

    openCodecSelector: function() {
        this.getApp().openCodecSelector();
    },

    closeApp: function() {
        this.getApp().closeApp();
    },
    
    isWebView: function() {
        var type = this.getEngineType();
        return type == this.WEBVIEW;
    },
    
    isXWalk: function() {
        var type = this.getEngineType();
        return type == this.XWALK;
    },

    isExo: function() {
        return this.getApp().isExo();
    },

    /**
     * YouTube's 4K videos encoded exclusively in WEBM codec
     */
    is4KCodec: function(codec) {
        if (codec.indexOf('width=3840') != -1) {
            console.log('DeviceUtils::force enable ' + codec + ' codec');
            return true;
        }

        return false;
    },

    /**
     * Check that we not blocked all playable codecs
     * @returns {boolean} it works!
     */
    isPlaybackWorking: function() {
        var state = this.getPlaybackWorking();

        return state == this.PLAYBACK_UNKNOWN || state == this.PLAYBACK_IS_WORKING;
    },

    /**
     * Is live stream. Such content has limited codec support.
     */
    isLive: function(codec) {
        var isVP9 = codec.indexOf('video/webm') != -1;
        var beginOfTheVideo = this.isBeginOfTheVideo();
        var isLive = beginOfTheVideo && isVP9;

        // series of queries
        if (!isLive && this.prevIsLive && !beginOfTheVideo) {
            isLive = true;
        }

        this.prevIsLive = isLive;

        Log.d(this.TAG, "Is live: " + isLive);

        return isLive;
    },

    /**
     * Test codec query intervals
     */
    isBeginOfTheVideo: function() {
        if (!this.prevVideoTime) {
            this.prevVideoTime = 0;
        }

        var currentTimeMs = Utils.getCurrentTimeMs();
        var diff = currentTimeMs - this.prevVideoTime;

        this.prevVideoTime = currentTimeMs;

        return diff > 1000;
    },

    supportsVideoType: function(type) {
        var video = Utils.$('video');

        if (!video) {
            console.log("DeviceUtils::supportsVideoType: video element is not initialized");
            return;
        }

        // Allow user to create shortcuts, i.e. just "webm"
        var formats = {
            ogg: 'video/ogg; codecs="theora"',
            h264: 'video/mp4; codecs="avc1.42E01E"',
            webm: 'video/webm; codecs="vp8, vorbis"',
            vp9: 'video/webm; codecs="vp9"',
            hls: 'application/x-mpegURL; codecs="avc1.42E01E"'
        };

        return video.canPlayType(formats[type] || type);
    },

    // variable number of arguments
    /**
     * Compares current device with device spec supplied as parameter<br/>
     * <b>Device spec</b> could be short device name or name of the board's hardware
     * @returns {boolean} is this my device
     */
    isMyDevice: function() {
        if (arguments.length == 0)
            return false;
        if (!window.thisDevice)
            window.thisDevice = DeviceUtils.getDeviceName();
        if (!window.thisDeviceHardware)
            window.thisDeviceHardware = DeviceUtils.getDeviceHardware();
        var argsLen = arguments.length;
        for (var i = 0; i < argsLen; i++) {
            var device = arguments[i];
            if (DeviceUtils.specCmp(window.thisDevice, device)) {
                return true;
            }
            if (DeviceUtils.specCmp(window.thisDeviceHardware, device)) {
                return true;
            }
        }
        return false;
    },

    getScreenWidth: function() {
        return window.innerWidth;
    },

    sendMessage: function(message, content) {
        return this.getApp().sendMessage(message, content);
    },

    getPreferredCodec: function() {
        return this.getApp().getPreferredCodec();
    },

    isMicAvailable: function() {
        return this.getApp().isMicAvailable();
    },

    getPlaybackWorking: function() {
        return this.getApp().getPlaybackWorking();
    },

    isMicAvailable2: function() {
        var jsMicAvailable = window.SpeechRecognition || window.webkitSpeechRecognition;
        return jsMicAvailable || this.getApp().isMicAvailable();
    },

    isGlobalAfrFixEnabled: function() {
        return this.getApp().isGlobalAfrFixEnabled();
    }
};

DeviceUtils.init();
