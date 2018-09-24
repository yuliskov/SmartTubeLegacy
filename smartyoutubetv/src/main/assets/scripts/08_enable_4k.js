/*
Description: Imitate press on OK button after seek
*/

console.log("Scripts::Running script enable_4k.js");

function Enable4KAddon() {
    this.run = function() {
        // this.applyFakeResolution();
    };

    this.applyFakeResolution = function() {
        // NOTE: WONT WORK PROPERLY

        // remove asterisk from dimensions value like "950x640*2"
        window.devicePixelRatio = 1.0;

        if (!app)
            return;

        // android resolution (can differ from physical resolution)
        // var arr = app.getDeviceResolution().split('x');
        // var w = arr[0], h = arr[1];

        // fake resolution (does't have influence on video resolution)
        var w = 2560, h = 1440;

        utils.overrideProp("window.innerWidth", w);
        utils.overrideProp("window.innerHeight", h);

        // NOTE: there is no need to override props below

        // utils.overrideProp("window.outerWidth", w);
        // utils.overrideProp("window.outerHeight", h);
        //
        // utils.overrideProp("document.documentElement.clientWidth", w);
        // utils.overrideProp("document.documentElement.clientHeight", h);
        //
        // utils.overrideProp("window.screen.availWidth", w);
        // utils.overrideProp("window.screen.availHeight", h);
        //
        // utils.overrideProp("window.screen.width", w);
        // utils.overrideProp("window.screen.height", h);
    }
}

new Enable4KAddon().run();
