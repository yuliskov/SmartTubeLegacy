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
        this.applyFakeResolution();
    };

    this.applyFakeResolution = function() {
        // NOTE: WONT WORK PROPERLY

        // DeviceUtils.forceEnableAllCodecs();

        // remove asterisk from dimensions value like "950x640*2"
        // window.devicePixelRatio = 1.0;

        // if (!app)
        //     return;

        // android resolution (can differ from physical resolution)
        // var arr = app.getDeviceResolution().split('x');
        // var w = arr[0], h = arr[1];

        // fake resolution (does't have influence on video resolution)
        // 4k: 3840 x 2160
        // 2k: 2560 x 1440
        var w = 3840, h = 2160;

        Utils.overridePropNum("window.innerWidth", w, this.INIT_WIDTH_CALLS);
        Utils.overridePropNum("window.innerHeight", h, this.INIT_HEIGHT_CALLS);

        // Utils.overridePropTemp("window.innerWidth", w, timeoutMS);
        // Utils.overridePropTemp("window.innerHeight", h, timeoutMS);

        // Utils.overrideProp("window.innerWidth", w);
        // Utils.overrideProp("window.innerHeight", h);

        // NOTE: there is no need to override props below

        // Utils.overrideProp("window.outerWidth", w);
        // Utils.overrideProp("window.outerHeight", h);
        //
        // Utils.overrideProp("document.documentElement.clientWidth", w);
        // Utils.overrideProp("document.documentElement.clientHeight", h);
        //
        // Utils.overrideProp("window.screen.availWidth", w);
        // Utils.overrideProp("window.screen.availHeight", h);
        //
        // Utils.overrideProp("window.screen.width", w);
        // Utils.overrideProp("window.screen.height", h);
    };
}

new Enable4KAddon().run();
