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
    this.INIT_WIDTH_CALLS = 33;
    this.INIT_HEIGHT_CALLS = 38;

    this.run = function() {
        // this.saveOriginalValues();
        this.increaseResolution();
        // this.awaitVideoElement();
    };

    this.awaitVideoElement = function() {
        var video = Utils.$('video');
        if (!video) {
            Utils.postDelayed(this.awaitVideoElement, this, 100);
            return;
        }
        this.updatePlayerSupportedTypes(video); // produces an error when launching the video
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
     * {@link window.innerWidth}<br/>
     * {@link window.outerWidth}<br/>
     * {@link document.documentElement.clientWidth}<br/>
     * {@link window.screen.availWidth}<br/>
     * {@link window.screen.width}<br/>
     */
    this.increaseResolution = function() {
        // NOTE: WONT WORK PROPERLY
        // remove asterisk from dimensions value like "950x640*2"
        // window.devicePixelRatio = 1.0;

        // imitate 4K resolution
        var w = 3840, h = 2160;

        Utils.overridePropNum("window.innerWidth", w, this.INIT_WIDTH_CALLS);
        Utils.overridePropNum("window.innerHeight", h, this.INIT_HEIGHT_CALLS);

        // Utils.overrideProp(window, "innerWidth", w);
        // Utils.overrideProp(window,"innerHeight", h);
    };

    this.saveOriginalValues = function() {
        if (this.originWidth || this.originHeight) {
            return;
        }

        this.originWidth = window.innerWidth;
        this.originHeight = window.innerHeight;

        var $this = this;

        EventUtils.onLoad(function() {
            Utils.overrideProp(window, "innerWidth", $this.originWidth);
            Utils.overrideProp(window,"innerHeight", $this.originHeight);
        });
    };
}

new Enable4KAddon().run();
