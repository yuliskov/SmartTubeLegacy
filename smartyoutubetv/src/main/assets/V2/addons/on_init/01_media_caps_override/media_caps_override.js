/**
 * Override media capabilities on unsupported devices<br/>
 * Only usable with external player like ExoPlayer
 */

console.log("Scripts::Running script media_caps_override.js");

function MediaCapsOverrideAddon() {
    this.TAG = "MediaCapsOverrideAddon";

    this.run = function() {
        if (!window.MediaSource && !window.WebKitMediaSource && !(HTMLMediaElement && HTMLMediaElement.prototype.webkitSourceAddId)) {
            this.overrideMediaSource();
        }
    };

    this.overrideMediaSource = function() {
        var MediaSource = function() {

        };

        MediaSource.isTypeSupported = function(){return true};

        window.MediaSource = window.WebKitMediaSource = MediaSource;
    };

    this.overrideURL = function() {
        var URL = function() {

        };

        URL.createObjectURL = function() {
            return "blob:chrome-search://local-ntp/763d7be9-779b-4009-8847-338e46b54b1e";
        };

        window.URL = URL;
    };
}

new MediaCapsOverrideAddon().run();
