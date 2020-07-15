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
    this.WIDTH_4K = 3850;
    this.HEIGHT_4K = 2160;

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
        //window.devicePixelRatio = 1.0;

        // HACK: use fake 4k resolution
        Utils.overrideProp(window, "devicePixelRatio", 4.0);

        // NOTE: WONT WORK PROPERLY
        // Where to find proper values for INIT_WIDTH_CALLS and INIT_HEIGHT_CALLS:
        // 1) use logged version of overrideProp (see below)
        // 2) measure calls needed to boot into the initial page of the app
        // Utils.overridePropNum("window.innerWidth", this.WIDTH_4K, this.INIT_WIDTH_CALLS);
        // Utils.overridePropNum("window.innerHeight", this.HEIGHT_4K, this.INIT_HEIGHT_CALLS);

        // NOTE: WONT WORK PROPERLY
        // same as above but robust one because doesn't rely on call nums
        // override until video element will be initialized or you will get zoomed video
        // var $this = this;
        // Utils.overrideProp(window, "innerWidth", this.WIDTH_4K, function() {return $this.isVideoInitialized()});
        // Utils.overrideProp(window,"innerHeight", this.HEIGHT_4K, function() {return $this.isVideoInitialized()});
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

    this.isVideoInitialized = function() {
        if (this.cachedVideo) {
            return true;
        }

        this.cachedVideo = Utils.$('video') != null;

        return this.cachedVideo;
    };
}

if (!DeviceUtils.isExo()) {
    new Enable4KAddon().run();
}
