/*
Description: Imitate press on OK button after seek
*/

console.log("Scripts::Running script player_seek_fix.js");

function PlayerSeekAddon() {
	this.progressBarSelector = "#transport-controls";
	this.timeoutTimeMS = 1000;
	this.myTimeout = null;

    this.run = function() {
    	EventUtils.addPlayerKeyPressListener(this);
    };

    this.onKeyEvent = function(e) {
    	console.log("PlayerSeekAddon::onKeyEvent:" + e.keyCode);

        if (this.myTimeout) {
            console.log('PlayerSeekAddon::Clear unneeded timeout');
            clearTimeout(this.myTimeout);
        }

        var notLeftOrRight = e.keyCode !== DefaultKeys.LEFT && e.keyCode !== DefaultKeys.RIGHT;

        if (notLeftOrRight) {
            return;
    	}

    	var $this = this;
    	this.myTimeout = setTimeout(function() {
    		$this.doPressOnOKButton();
    	}, this.timeoutTimeMS);
    };

    this.doPressOnOKButton = function() {
    	console.log('PlayerSeekAddon::Imitate press on OK button');
    	EventUtils.triggerEnter(this.progressBarSelector);
    };
}

if (!DeviceUtils.isExo())
    new PlayerSeekAddon().run();
