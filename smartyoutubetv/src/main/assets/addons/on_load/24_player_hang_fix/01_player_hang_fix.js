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
        var $this = this;
        var plBtn = Utils.$(YouTubeSelectors.PLAYER_PLAY_BUTTON);
        if (plBtn && this.isPaused()) {
            EventUtils.triggerEnter(plBtn);
            setTimeout(function() {
                $this.isPaused() && EventUtils.triggerEnter(YouTubeSelectors.BUTTON_BACK);
            }, this.QUIT_VIDEO_TIMEOUT);
        }
    };

    this.isPaused = function() {
        var v = Utils.$('video');

        return v && v.paused;
    };
}

// if (DeviceUtils.isWebView() && !DeviceUtils.isExo()) {
//     new PlayerHangFixAddon().run();
// }
