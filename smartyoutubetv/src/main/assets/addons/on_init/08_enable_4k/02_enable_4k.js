/*
Description: Imitate press on OK button after seek
*/

console.log("Scripts::Running script enable_4k.js");

/**
 * HACK: give the fake width/height values only on the init stage.
 * This approach solves zoomed video problem.
 * So, we must calculate how much each prop called on the init stage.
 * Below values is calculated by me through the logging mechanism.
 */
function Enable4KAddon() {
    // HACK: give the fake width/height values only on the init stage.
    // This approach solves zoomed video problem.
    // So, we must calculate how much each prop called on the init stage.
    // Below values is calculated by me through the logging mechanism.
    this.INIT_WIDTH_CALLS = 26;
    this.INIT_HEIGHT_CALLS = 31;

    this.run = function() {
        // if (navigator.mediaCapabilities)
        //     Utils.logMethod(navigator.mediaCapabilities.decodingInfo, navigator.mediaCapabilities);
        // DeviceUtils.forceEnableAllCodecs();
        this.increaseResolution();
        // this.awaitVideoElement();
    };

    this.awaitVideoElement = function() {
        var video = Utils.$('video');
        if (!video) {
            Utils.postDelayed(this.awaitVideoElement, this, 100);
            return;
        }
        this.setErrorLogging(video);
        this.updatePlayerSupportedTypes(video);
    };

    this.setErrorLogging = function(video) {
        video.addEventListener('play', function() {
            console.log("Enable4KAddon::on play");
        });

        video.addEventListener('error', function() {
            console.log("Enable4KAddon::error occurred " + video.error.message);
        });

        video.addEventListener('canplay', function() {
            console.log("Enable4KAddon::canplay occurred " + video.error.message);
        });

        video.addEventListener('canplaythrough', function() {
            console.log("Enable4KAddon::canplaythrough occurred " + video.error.message);
        });

        // video.onerror = function() {
        //     console.log("Enable4KAddon::error occurred " + video.error.code + "; details: " + video.error.message);
        // };
    };

    this.updatePlayerSupportedTypes = function(video) {
        video.canPlayType = function(type) {
            console.log("Enable4KAddon::canPlayType " + type);
            return "probably";
        };
    };

    /**
     * Overrides {@link window.innerWidth} and {@link window.innerHeight} properties<br/>
     * There are no need to override other similar props.<br/>
     * List of that props (just for your reference):<br/>
     * {@link window.outerWidth}<br/>
     * {@link window.outerHeight}<br/>
     * {@link document.documentElement.clientWidth}<br/>
     * {@link document.documentElement.clientHeight}<br/>
     * {@link window.screen.availWidth}<br/>
     * {@link window.screen.availHeight}<br/>
     * {@link window.screen.width}<br/>
     * {@link window.screen.height}<br/>
     */
    this.increaseResolution = function() {
        // NOTE: WONT WORK PROPERLY
        // remove asterisk from dimensions value like "950x640*2"
        // window.devicePixelRatio = 1.0;

        // imitate 4K resolution
        var w = 3840, h = 2160;

        Utils.overridePropNum("window.innerWidth", w, this.INIT_WIDTH_CALLS);
        Utils.overridePropNum("window.innerHeight", h, this.INIT_HEIGHT_CALLS);
    };
}

new Enable4KAddon().run();
