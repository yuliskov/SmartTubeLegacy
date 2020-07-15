/*
Description: Imitate press on OK button after seek
*/

console.log("Scripts::Running script player_seek_fix.js");

function PlayerSeekAddon() {
    this.TAG = 'PlayerSeekAddon';
	this.timeoutTimeMS = 1000;
	this.myTimeout = null;
	this.progressBarSelector = '#transport-controls .new-progress-bar';

    this.run = function() {
    	EventUtils.addKeyPressListener(this, this.progressBarSelector);
    };

    this.onKeyEvent = function(e) {
    	Log.d(this.TAG, "onKeyEvent:" + e.keyCode);

        if (this.myTimeout) {
            Log.d(this.TAG, 'Clear unneeded timeout');
            clearTimeout(this.myTimeout);
        }

        var notLeftOrRight = e.keyCode != DefaultKeys.LEFT && e.keyCode != DefaultKeys.RIGHT;

        if (notLeftOrRight) {
            return;
    	}

    	var $this = this;
    	this.myTimeout = setTimeout(function() {
    		$this.doPressOnOKButton();
    	}, this.timeoutTimeMS);
    };

    this.doPressOnOKButton = function() {
    	Log.d(this.TAG, 'Imitate press on OK button');
    	EventUtils.triggerEnter(YouTubeSelectors.PLAYER_EVENTS_RECEIVER);
    };
}

// if (!DeviceUtils.isExo())
//     new PlayerSeekAddon().run();
