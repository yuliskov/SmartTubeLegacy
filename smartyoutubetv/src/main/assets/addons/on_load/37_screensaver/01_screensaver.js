/**
 * Description:
 * Control screensaver on LITE.
 */

console.log("Scripts::Running script screensaver.js");

function ScreensaverAddon() {
    this.TAG = 'ScreensaverAddon';

    this.run = function() {
        this.setupListener();
    };

    this.setupListener = function() {
        var $this = this;

        var blockScreensaver = function() {
            Log.d($this.TAG, "Blocking screensaver...");

            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_DISABLE_SCREENSAVER);
        };

        var unblockScreensaver = function() {
            Log.d($this.TAG, "Unblocking screensaver...");

            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_ENABLE_SCREENSAVER);
        };

        EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_PLAY, blockScreensaver);

        EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_PAUSE, unblockScreensaver);
        EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_ENDED, unblockScreensaver);
        EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_ERROR, unblockScreensaver);
    };
}

if (!DeviceUtils.isExo()) {
    new ScreensaverAddon().run();
}
