/*
Description: Imitate press on OK button after seek
*/

console.log("Scripts::Running script enable_4k.js");

function Enable4KAddon() {
    // this.originWidth = 0;
    // this.originHeight = 0;
    this.run = function() {
        // this.originWidth = window.innerWidth;
        // this.originHeight = window.innerHeight;
        this.applyFakeResolution();
        // var $this = this;
        // EventUtils.delayUntilPlayerBeInitialized(function() {
        //     $this.revertToOriginal();
        // });
    };

    // this.revertToOriginal = function() {
    //     Utils.overrideProp("window.innerWidth", this.originWidth);
    //     Utils.overrideProp("window.innerHeight", this.originHeight);
    // };

    this.applyFakeResolution = function() {
        // NOTE: WONT WORK PROPERLY

        DeviceUtils.forceEnableAllCodecs();

        // remove asterisk from dimensions value like "950x640*2"
        window.devicePixelRatio = 1.0;

        // if (!app)
        //     return;

        // android resolution (can differ from physical resolution)
        // var arr = app.getDeviceResolution().split('x');
        // var w = arr[0], h = arr[1];

        // fake resolution (does't have influence on video resolution)
        // 4k: 3840 x 2160
        // 2k: 2560 x 1440
        var w = 3840, h = 2160, timeoutMS = 10000;

        Utils.overridePropTemp("window.innerWidth", w, timeoutMS);
        Utils.overridePropTemp("window.innerHeight", h, timeoutMS);

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
    }
}

new Enable4KAddon().run();
