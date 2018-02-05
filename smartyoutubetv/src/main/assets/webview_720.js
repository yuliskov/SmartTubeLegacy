// hide ugly bg that appears on few seconds before the video
function hideShowPlayerBackground() {
    console.log("webview_720.js: hideShowPlayerBackground()");
    var callbackSet = 'data-callbackSet';

    var player = document.getElementsByTagName('video')[0];
    if (!player || player.getAttribute(callbackSet))
        return;

    function startPlayer() {
        // not helpful, at this point player already visible
        // hide (!) player in *.css files instead
        player.play();
        console.log("webview_720.js: startPlayer()");
    }

    function showPlayer() {
        player.style['-webkit-filter'] = 'brightness(1)';
        console.log("webview_720.js: showPlayer()");
    }

    function hidePlayer() {
        player.style['-webkit-filter'] = 'brightness(0)';
        console.log("webview_720.js: hidePlayer()");
    }

    hidePlayer();
    startPlayer();

    player.addEventListener('loadstart', startPlayer, false); // start loading
    player.addEventListener('loadeddata', showPlayer, false); // finished loading
    //player.addEventListener('load', showPlayer, false); // finished loading
    player.addEventListener('abort', hidePlayer, false); // video closed

    player.setAttribute(callbackSet, true);
}

delayUntilPlayerBeInitialized && delayUntilPlayerBeInitialized(hideShowPlayerBackground);