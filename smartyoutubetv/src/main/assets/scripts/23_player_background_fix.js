/*
Description:
On the WebView engine low resolution play icon appears before each clip.
Fix that by replacing it with the simple black screen.
*/

console.log("Scripts::Running script player_background_fix.js");

function PlayerBackgroundFixAddon() {
    this.run = function() {
        if (DeviceUtils.isWebView())
            this.hideShowPlayerBackground();
    };

    this.hideShowPlayerBackground = function() {
        var player = document.getElementsByTagName('video')[0];
        if (!player) {
            console.log("PlayerBackgroundFixAddon:: player not exist... waiting for the player...");
            setTimeout(this.hideShowPlayerBackground, 500);
            return;
        }

        if (player.callbackSet) {
            return;
        }

        var container = Utils.$('.html5-video-container'); // parent element of the 'video' tag
        var loaderHtml = '<div class="loader-container"></div>';
        var loader = Utils.appendHtml(container, loaderHtml);

        function startPlayer(event) {
            var howStarted = event == null ? "normally" : "from event";
            player.play();
            console.log("PlayerBackgroundFixAddon:: startPlayer() " + howStarted);
        }

        function showPlayer() {
            loader.style['display'] = 'none';
            console.log("PlayerBackgroundFixAddon:: showPlayer()");
        }

        function hidePlayer(event) {
            loader.style['display'] = 'block';
            var howStarted = event == null ? " normally" : " from event";
            console.log("PlayerBackgroundFixAddon:: hidePlayer() " + howStarted);
        }

        // fix when clicked on the next button
        function hideShowPlayer() {
            if (!player.currentTime) { // player not initialized
                hidePlayer();
            } else {
                showPlayer();
            }
            console.log("PlayerBackgroundFixAddon:: hideShowPlayer()");
        }

        hideShowPlayer();
        startPlayer(); // its very important (fix constant reload)

        player.addEventListener('loadstart', startPlayer, false); // start loading
        player.addEventListener('playing', showPlayer, false); // finished loading
        player.addEventListener('abort', hidePlayer, false); // video closed
        window.addEventListener('hashchange', hideShowPlayer, false);
        player.callbackSet = true;
    };
}

new PlayerBackgroundFixAddon().run();