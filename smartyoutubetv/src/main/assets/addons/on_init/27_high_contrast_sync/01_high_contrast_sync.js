/**
 * Syncs contrast settings
 */

console.log("Scripts::Running script high_contrast_sync.js");

function HighContrastSyncAddon() {
    this.TAG = 'HighContrastSyncAddon';
    this.MESSAGE_HIGH_CONTRAST_ENABLED = "message_high_contrast_enabled";
    this.CONTRAST_KEY = 'yt.leanback.default::high-contrast-enabled';

    this.run = function() {
        var item = localStorage.getItem(this.CONTRAST_KEY);
        if (item) {
            var itemObj = JSON.parse(item);
            if (itemObj) {
                DeviceUtils.sendMessage(this.MESSAGE_HIGH_CONTRAST_ENABLED, itemObj.data);
            }
        }
    };
}

new HighContrastSyncAddon().run();
