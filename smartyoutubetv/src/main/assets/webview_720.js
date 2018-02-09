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

    function startPlayer(event) {
        var howStarted = event == null ? "normally" : "from event";
        // not helpful, at this point player already visible
        // hide (!) player in *.css files instead
        player.play();
        setTimeout(function() {
            if (player.paused) window.location.reload();
        }, 500);
        console.log("webview_720.js: startPlayer() " + howStarted);
    }

    function showPlayer() {
        player.style['-webkit-filter'] = 'brightness(1)';
        console.log("webview_720.js: showPlayer()");
    }

    function hidePlayer(event) {
        var howStarted = event == null ? " normally" : " from event";
        player.style['-webkit-filter'] = 'brightness(0)';
        console.log("webview_720.js: hidePlayer() " + howStarted);
    }

    // fix when clicked on the next button
    function hideShowPlayer() {
        if (window.location.hash.indexOf('watch') === -1) { // not a video
            return;
        }

        if (!player.currentTime) { // player not initialized
            hidePlayer();
            setTimeout(hideShowPlayer, 3000); // fire tv fix (gray screen)
        } else {
            showPlayer();
        }
    }

    hideShowPlayer();
    startPlayer();

    player.addEventListener('loadstart', startPlayer, false); // start loading
    // player.addEventListener('loadeddata', showPlayer, false); // finished loading
    player.addEventListener('abort', hidePlayer, false); // video closed
    window.addEventListener('hashchange', hideShowPlayer, false);
    player.callbackSet = true;
}

function init() {
    delayUntilPlayerBeInitialized(hideShowPlayerBackground);
}

function waitTillInit(depName, callback) {
    function hashCode(str) {
        var hash = 0, i, chr;
        if (str.length === 0) return hash;
        var maxLen = 30; // we don't need a full content
        var len = str.length < maxLen ? str.length : maxLen;
        for (i = 0; i < len; i++) {
            chr   = str.charCodeAt(i);
            hash  = ((hash << 5) - hash) + chr;
            hash |= 0; // Convert to 32bit integer
        }
        return hash;
    }

    var modHash = hashCode(callback.toString());
    var modNamePart = callback.toString().slice(0, 50);

    if (window[modHash] === 'ok') {
        console.log('Module already initialized: ' + modNamePart);
        return;
    }

    window[modHash] = 'ok';

    if (window[depName] || !depName) {
        console.log('All deps initialized. perform callback: ' + modNamePart);
        callback();
        return;
    }

    var interval = setInterval(function() {
        console.log('Check that all deps are initialized: ' + modNamePart);
        if (window[depName]) {
            console.log('All deps initialized. perform callback: ' + modNamePart);
            callback();
            clearInterval(interval);
        }
    }, 100);
}

waitTillInit('common.js', init);