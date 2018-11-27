/**
 * Description:
 * On the WebView engine low resolution play icon appears before each clip.
 * Fix that by replacing it with the simple black screen.
 */

console.log("Scripts::Running script player_background_fix2.js");

function PlayerBackgroundFixAddon() {
    this.run = function() {
        this.replacePlayerBackground();
    };
    this.replacePlayerBackground = function() {
        var player = document.getElementsByTagName('video')[0];
        if (!player) {
            console.log("PlayerBackgroundFixAddon:: player not exist... waiting for the player...");
            Utils.postDelayed(this.replacePlayerBackground, this, 500);
            return;
        }

        player.poster = "data:image/gif,AAAA"; // transparent image
    };
}

if (DeviceUtils.isWebView() && !DeviceUtils.isExo())
    new PlayerBackgroundFixAddon().run();