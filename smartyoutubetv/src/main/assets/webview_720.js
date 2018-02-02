// hide ugly bg that appears on few seconds before the video
function hideShowPlayerBackground() {
    console.log("webview.js: hideShowPlayerBackground()");

    var player = document.getElementsByTagName('video')[0];
    if (!player || window.playerCallbackSet)
        return;

    function startPlayer() {
        // not helpful, at this point player already visible
        // hide (!) player in *.css files instead
        player.play();
        console.log("webview.js: startPlayer()");
    }

    function showPlayer() {
        player.style['-webkit-filter'] = 'brightness(1)';
    }

    function hidePlayer() {
        player.style['-webkit-filter'] = 'brightness(0)';
    }

    hidePlayer();

    player.addEventListener('loadstart', startPlayer, false); // start loading
    player.addEventListener('loadeddata', showPlayer, false); // finished loading
    player.addEventListener('abort', hidePlayer, false); // video closed

    window.playerCallbackSet = true;
}

delayUntilPlayerBeInitialized && delayUntilPlayerBeInitialized(hideShowPlayerBackground);