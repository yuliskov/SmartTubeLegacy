function addExitEvent() {
    if (window.eventAdded)
        return;

    var element = document.getElementById('dialog-ok-button');
    element.addEventListener('keydown', function (e) {
        if (e.which == 13) {
            e.preventDefault();
            app.closeApp();
        }
    });
    window.eventAdded = true;
}


// Xiaomi Mi TV 3 has bug playing webm format, so use mp4 instead
function fixWebmArtifacts() {
    if (!window.MediaSource)
        return;

    window.MediaSource.isTypeSupported = function(native) {
        return function(str) {
            if (str.indexOf('webm') >= 0) 
                return false;
            return native.call(window.MediaSource, str);
        }
    }(window.MediaSource.isTypeSupported);
}

function fixVideoPlaybackWhenRestored() {
	var e = new Event("keydown");
	// e.key="a";    // just enter the char you want to send 
	// e.keyCode=e.key.charCodeAt(0);
	e.keyCode = 13; // Enter key
	e.which=e.keyCode;
	e.altKey=false;
	e.ctrlKey=true;
	e.shiftKey=false;
	e.metaKey=false;
	e.bubbles=true;

	var toggleButton = document.getElementsByClassName('icon-player-play')[0];
	toggleButton.dispatchEvent(e);
}

addExitEvent();
// fixVideoPlaybackWhenRestored();
// fixWebmArtifacts();