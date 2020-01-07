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

        var blockScreensaver = function() {
            Log.d($this.TAG, "Blocking screensaver...");

            if ($this.isBlocked) {
                return;
            }

            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_DISABLE_SCREENSAVER);

            $this.isBlocked = true;
        };

        var unblockScreensaver = function() {
            Log.d($this.TAG, "Unblocking screensaver...");

            if (!$this.isBlocked) {
                return;
            }

            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_ENABLE_SCREENSAVER);

            $this.isBlocked = false;
        };

        EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_PLAYING, blockScreensaver);

        EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_PAUSE, unblockScreensaver);
        EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_ENDED, unblockScreensaver);
        EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_ERROR, unblockScreensaver);
        EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_ABORT, unblockScreensaver);
        EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_EMPTYED, unblockScreensaver);
        EventUtils.addListener(YouTubeSelectors.PLAYER_OBJ, DefaultEvents.PLAYER_SUSPEND, unblockScreensaver);
    };
}

if (!DeviceUtils.isExo()) {
    new ScreensaverAddon().run();
}
