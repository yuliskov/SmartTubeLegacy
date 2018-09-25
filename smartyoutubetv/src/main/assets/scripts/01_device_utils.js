/*
Description: Imitate press on OK button after seek
*/

console.log("Scripts::Running script device_utils.js");

var DeviceUtils = {
    WEBM: 'webm',
    MP4: 'mp4',
    WEBVIEW: '????',
    XWALK: '?????',

    init: function() {
        // do init here
    },

    disableCodec: function(codec) {
        if (!codec) {
            console.log('DeviceUtils::disableCodec: codec is null');
            return;
        }
        if (!window.MediaSource) {
            console.log('DeviceUtils::disableCodec: MediaSource is null');
            return;
        }

        console.log('DeviceUtils::disableCodec: ' + codec + ' on ' + window.thisDevice + ' device');

        window.MediaSource.isTypeSupported = function(native) {
            return function(str) {
                if (this.specCmp(str, codec))
                    return false;
                return native.call(window.MediaSource, str);
            }
        }(window.MediaSource.isTypeSupported);
    },

    disableWebmCodec: function() {
        this.disableCodec(this.WEBM);
    },

    disableMP4Codec: function() {
        this.disableCodec(this.MP4);
    },

    /*
    Compare special strings.
    Returns true even when the second string is empty: this.specCmp('abc', '') == true
    Or when strings partially matched: this.specCmp('abc', 'ab') == true
    */
    specCmp: function(str1, str2) {
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        return str1.indexOf(str2) >= 0;
    },

    getApp: function() {
        if (!app) {
            console.log("DeviceUtils::global var 'app' isn't defined");
        }

        return app;
    },

    getEngineType: function() {
        return this.getApp().getEngineType();
    },

    getDeviceName: function() {
        return this.getApp().getDeviceName();
    }
};

DeviceUtils.init();
