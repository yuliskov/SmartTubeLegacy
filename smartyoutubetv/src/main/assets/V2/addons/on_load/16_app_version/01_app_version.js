/**
 * Description:
 * Change about page infos like app version and device name
 */

console.log("Scripts::Running script app_version.js");

function AppVersionAddon() {
    this.TAG = 'AppVersionAddon';

    this.run = function() {
        var $this = this;
        YouTubeEventManager.addOnSettingsAppVersionShown(function() {
            $this.updateValues();
        });
    };

    this.updateValues = function() {
        var keys = Utils.$$(YouTubeSelectorsV2.OPTIONS_APP_KEY_SELECTOR);
        var values = Utils.$$(YouTubeSelectorsV2.OPTIONS_APP_VALUE_SELECTOR);

        if (keys.length == 0 || values.length == 0)
            return;

        Log.d(this.TAG, "Replacing app and device infos in the settings...");

        for (var i = 0; i < keys.length; i++) {
            var content = Utils.content(keys[i]);
            if (content == YouTubeConstants.OPTIONS_VERSION_TITLE) {
                Utils.content(values[i], DeviceUtils.getAppVersion());
            } else if (content == YouTubeConstants.OPTIONS_DEVICE_TITLE) {
                Utils.content(values[i], DeviceUtils.getDeviceName());
            }
        }
    };
}

new AppVersionAddon().run();
