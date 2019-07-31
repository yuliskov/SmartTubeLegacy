/**
 * Description:
 * Fix black screen on webview clip load
 */

console.log("Scripts::Running script player_hang_fix.js");

function PlayerHangFixAddon() {
    this.QUIT_VIDEO_TIMEOUT = 3000;

    this.run = function() {
        var $this = this;
        EventUtils.onLoad(function() {
            setTimeout(function() {
                $this.pressOnPlayButton();
            }, $this.QUIT_VIDEO_TIMEOUT);
        });
    };

    this.pressOnPlayButton = function() {
        if (YouTubeUtils.isPlayerVisible() && YouTubeUtils.isPlayerPaused()) {
            YouTubeUtils.playerPlay();
            setTimeout(function() {
                YouTubeUtils.isPlayerPaused() && EventUtils.triggerEnter(YouTubeSelectors.BUTTON_BACK);
            }, this.QUIT_VIDEO_TIMEOUT);
        }
    };
}

// if (DeviceUtils.isWebView() && !DeviceUtils.isExo()) {
//     new PlayerHangFixAddon().run();
// }
