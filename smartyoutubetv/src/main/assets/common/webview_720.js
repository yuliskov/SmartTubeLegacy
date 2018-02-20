// Description:
// On the WebView version there is an ugly bg that appears on few seconds before playing the video.
// Also, it tries to fix stalled playback off the first video.

function hideShowPlayerBackground() {
    console.log("webview_720.js: hideShowPlayerBackground()");

    var player = document.getElementsByTagName('video')[0];
    if (!player) {
        console.log("webview_720.js: player not exist... waiting for the player...");
        setTimeout(hideShowPlayerBackground, 500);
        return;
    }

    if (player.callbackSet) {
        console.log("webview_720.js: callback already set");
        return;
    }

    var container = helpers.$('#leanback-player-container');
    var loaderHtml = '<div class="loader-container"></div>';
    var loader = helpers.appendHtml(container, loaderHtml);

    function startPlayer(event) {
        var howStarted = event == null ? "normally" : "from event";
        // not helpful, at this point player already visible
        // hide (!) player in *.css files instead
        player.play();
        // if (player.paused)
        //     window.location.reload();
        console.log("webview_720.js: startPlayer() " + howStarted);
    }

    function showPlayer() {
        loader.style['display'] = 'none';
        console.log("webview_720.js: showPlayer()");
    }

    function hidePlayer(event) {
        loader.style['display'] = 'block';
        var howStarted = event == null ? " normally" : " from event";
        console.log("webview_720.js: hidePlayer() " + howStarted);
    }

    // fix when clicked on the next button
    function hideShowPlayer() {
        if (!player.currentTime) { // player not initialized
            hidePlayer();
        } else {
            showPlayer();
        }
        console.log("webview_720.js: hideShowPlayer()");
    }

    hideShowPlayer();
    startPlayer(); // its very important (fix constant reload)

    player.addEventListener('loadstart', startPlayer, false); // start loading
    player.addEventListener('playing', showPlayer, false); // finished loading
    player.addEventListener('abort', hidePlayer, false); // video closed
    window.addEventListener('hashchange', hideShowPlayer, false);
    player.callbackSet = true;
}

function init() {
    delayUntilPlayerBeInitialized(hideShowPlayerBackground);
}

function waitTillInit(modName, callback, depName) {
    if (window[modName] === 'ok') {
        console.log(modName + ': module already initialized');
        return;
    }

    window[modName] = 'ok';

    if (window[depName] || !depName) {
        console.log(modName + ': all deps initialized. perform callback');
        callback();
        return;
    }

    var interval = setInterval(function() {
        console.log(modName + ': check that all deps are initialized');
        if (window[depName]) {
            console.log(modName + ': all deps initialized. perform callback');
            callback();
            clearInterval(interval);
        }
    }, 100);
}

waitTillInit('webview_720.js', init, 'common.js');