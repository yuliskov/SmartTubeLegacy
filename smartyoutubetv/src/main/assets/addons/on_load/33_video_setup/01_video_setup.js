/**
 * Default setting for the video tag
 */

console.log("Scripts::Running script video_setup.js");

function VideoSetupAddon() {
    this.run = function() {
        EventUtils.delayUntilElementInit('video', function(el) {
            Utils.overrideProp(el, 'volume', 0);
        });
    };
}

if (DeviceUtils.isExo()) {
    new VideoSetupAddon().run();
}
