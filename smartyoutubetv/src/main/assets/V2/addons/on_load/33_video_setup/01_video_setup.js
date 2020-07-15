/**
 * Default setting for the video tag
 */

console.log("Scripts::Running script video_setup.js");

function VideoSetupAddon() {
    this.TAG = 'VideoSetupAddon';

    this.run = function() {
        var $this = this;
        EventUtils.delayUntilElementInit('video', function(el) {
            Log.d($this.TAG, "Overriding video's volume property");
            Utils.overrideProp(el, 'volume', 0);
        });
    };
}

// if (DeviceUtils.isExo()) {
//     new VideoSetupAddon().run();
// }
