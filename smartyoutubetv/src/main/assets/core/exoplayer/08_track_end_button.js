console.log("Scripts::Running core script track_end_button.js");

function TrackEndFakeButton(selector) {
    this.TAG = 'TrackEndFakeButton';
    this.selector = selector;
    this.retryCount = 10;
    this.checkTimeoutMS = 1000;
    this.WAY_AHEAD_TIME = 1000000000;

    this.playerJumpToEnd2 = function() {
        Log.d(this.TAG, "Forcing video to end");

        var player = Utils.$('video');
        if (player) {
            if (isNaN(player.duration)) {
                this.pressNextButton();
                return;
            }

            player.currentTime = Math.floor(player.duration);
            player.play();
        }
    };

    this.playerJumpToEnd = function() {
        console.log("TrackEndFakeButton: I'm about to start off!");
        window.lastButtonName = PlayerActivity.TRACK_ENDED;

        var $this = this;
        var player = Utils.$('video');
        if (player) {
            player.play();
            console.log("TrackEndFakeButton: before jumping to the end: current time: " + player.currentTime + ", duration: " + player.duration);

            if (this.retryCount <= 0 || ExoUtils.playerIsClosed()) {
                this.pressNextButton();
                return;
            }

            this.retryCount--;

            if (isNaN(player.duration)) {
                setTimeout(function() { // do retry
                    $this.playerJumpToEnd();
                }, this.checkTimeoutMS);
            } else {
                player.currentTime = player.duration - 1; // seek to the end (minus one second!)
                this.startPlaybackCheck(player);
                console.log("TrackEndFakeButton: after jumping to the end: current time: " + player.currentTime + ", duration: " + player.duration);
            }
        }
    };

    this.pressNextButton = function() {
        Log.d(this.TAG, "Something is wrong, do workaround: switch to the next track");
        var btn = ExoButton.fromSelector(PlayerActivityMapping.BUTTON_NEXT);
        btn.setChecked(true);
    };

    this.startPlaybackCheck = function(player) {
        var position = player.currentTime;
        var $this = this;
        setTimeout(function() {
            var positionNotChanged = position == player.currentTime;
            if (positionNotChanged) {
                $this.playerJumpToEnd();
           }
        }, this.checkTimeoutMS);
    };

    this.getChecked = function() {
        return null; // not exists
    };

    this.setChecked = function(doChecked) {
        if (doChecked && !ExoUtils.playerIsClosed()) {
            ExoUtils.enablePlayerUi();
            ExoUtils.showPlayerBg();
            this.playerJumpToEnd2();
        }
    };
}