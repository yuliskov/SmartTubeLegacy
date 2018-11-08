console.log("Scripts::Running core script track_end_button.js");

function TrackEndFakeButton(selector) {
    this.selector = selector;
    this.retryCount = 5;
    this.retryDelay = 500;

    this.playerJumpToEnd = function() {
        console.log("TrackEndFakeButton: I'm about to start off!");
        window.lastButtonName = PlayerActivity.TRACK_ENDED;

        var player = Utils.$('video');
        if (player) {
            player.play();
            console.log("TrackEndFakeButton: before jumping to the end: current time: " + player.currentTime + ", duration: " + player.duration);

            if (isNaN(player.duration) && this.retryCount > 0) {
                this.retryJumpToEnd();
            } else if (isNaN(player.duration)) {
                console.log("TrackEndFakeButton: something wrong, do workaround: switch to the next track");
                var btn = ExoButton.fromSelector(PlayerActivityMapping.BUTTON_NEXT);
                btn.setChecked(true);
            } else {
                player.currentTime = player.duration - 1; // seek to the end (minus one second!)
                player.pause();
                player.play();
                console.log("TrackEndFakeButton: after jumping to the end: current time: " + player.currentTime + ", duration: " + player.duration);
            }
        }
    };

    this.retryJumpToEnd = function() {
        this.retryCount--;
        var $this = this;
        setTimeout(function() {
            $this.playerJumpToEnd();
        }, this.retryDelay);
    };

    this.getChecked = function() {
        return null; // not exists
    };

    this.setChecked = function(doChecked) {
        if (doChecked && !ExoUtils.playerIsClosed()) {
            this.playerJumpToEnd();
        }
    };
}