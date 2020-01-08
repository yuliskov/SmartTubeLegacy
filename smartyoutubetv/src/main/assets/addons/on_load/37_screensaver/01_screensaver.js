/**
 * Description:
 * Control screensaver on LITE.
 */

console.log("Scripts::Running script screensaver.js");

function ScreensaverAddon() {
    this.TAG = 'ScreensaverAddon';
    this.isBlocked = false;

    this.run = function() {
        this.setupListener();
    };

    this.setupListener = function() {
        var $this = this;

        var blockScreensaver = function(e) {
            Log.d($this.TAG, "Blocking screensaver... " + e.type);

            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_DISABLE_SCREENSAVER);
        };

        var unblockScreensaver = function(e) {
            Log.d($this.TAG, "Unblocking screensaver... " + e.type);

            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_ENABLE_SCREENSAVER);
        };

        EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_PLAYING, blockScreensaver);

        EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_PAUSE, unblockScreensaver);
        // EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_ENDED, unblockScreensaver);
        // EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_ERROR, unblockScreensaver);
        // EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_ABORT, unblockScreensaver);
        EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_EMPTYED, unblockScreensaver);
        // EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_SUSPEND, unblockScreensaver);
    };
}

if (!DeviceUtils.isExo()) {
    new ScreensaverAddon().run();
}
