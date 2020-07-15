/*
Description: Fix UI when switching from FHD to 4K resolution
*/

console.log("Scripts::Running script resolution_fix.js");

function ResolutionFixAddon() {
    this.run = function() {
        var $this = this;
        // wait till resolution become stable
        EventUtils.onLoad(function() {
            $this.makeCurrentResolutionPermanent();
        });
    };

    this.makeCurrentResolutionPermanent = function() {
        var width = window.innerWidth;
        var height = window.innerHeight;

        Utils.overrideProp(window, 'innerWidth', width);
        Utils.overrideProp(window, 'innerHeight', height);
        Utils.overrideProp(window, 'outerWidth', width);
        Utils.overrideProp(window, 'outerHeight', height);
        Utils.overrideProp(document.documentElement, 'clientWidth', width);
        Utils.overrideProp(document.documentElement, 'clientHeight', height);
        Utils.overrideProp(window.screen, 'availWidth', width);
        Utils.overrideProp(window.screen, 'availHeight', height);
        Utils.overrideProp(window.screen, 'width', width);
        Utils.overrideProp(window.screen, 'height', height);
    };
}

if (DeviceUtils.isExo()) {
    new ResolutionFixAddon().run();
}
