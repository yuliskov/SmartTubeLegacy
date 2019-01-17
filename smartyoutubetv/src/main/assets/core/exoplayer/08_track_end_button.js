console.log("Scripts::Running core script track_end_button.js");

function TrackEndFakeButton(selector) {
    this.TAG = 'TrackEndFakeButton';
    this.selector = selector;
    this.retryCount = 10;
    this.checkTimeoutMS = 1000;
    this.numTries = 3;

    this.playerJumpToEnd2 = function() {
        Log.d(this.TAG, "Forcing end of the video");

        var player = this.getPlayer();

        if (player) {
            if (isNaN(player.duration)) {
                if (this.numTries-- <= 0) {
                    this.pressNextButton();
                    return;
                }
            } else {
                player.currentTime = Math.floor(player.duration);
            }

            player.play();
            this.checkPlayerState();
        }
    };

    this.checkPlayerState = function() {
        var $this = this;
        
        setTimeout(function() {
            var player = $this.getPlayer();

            if (player) {
                Log.d($this.TAG, "Checking player position and duration: " + player.currentTime + " " + player.duration);

                if (player.src && player.currentTime < 10) {
                    $this.playerJumpToEnd2();
                }
            }
        }, this.checkTimeoutMS);
    };

    this.getPlayer = function() {
        return Utils.$('video');
    };

    this.pressNextButton = function() {
        Log.d(this.TAG, "Something is wrong, do workaround: switch to the next track");
        var btn = ExoButton.fromSelector(PlayerActivityMapping.BUTTON_NEXT);
        btn.setChecked(true);
    };

    // this.playerJumpToEnd = function() {
    //     console.log("TrackEndFakeButton: I'm about to start off!");
    //     window.lastButtonName = PlayerActivity.TRACK_ENDED;
    //
    //     var $this = this;
    //     var player = Utils.$('video');
    //     if (player) {
    //         player.play();
    //         console.log("TrackEndFakeButton: before jumping to the end: current time: " + player.currentTime + ", duration: " + player.duration);
    //
    //         if (this.retryCount <= 0 || ExoUtils.playerIsClosed()) {
    //             this.pressNextButton();
    //             return;
    //         }
    //
    //         this.retryCount--;
    //
    //         if (isNaN(player.duration)) {
    //             setTimeout(function() { // do retry
    //                 $this.playerJumpToEnd();
    //             }, this.checkTimeoutMS);
    //         } else {
    //             player.currentTime = player.duration - 1; // seek to the end (minus one second!)
    //             this.startPlaybackCheck(player);
    //             console.log("TrackEndFakeButton: after jumping to the end: current time: " + player.currentTime + ", duration: " + player.duration);
    //         }
    //     }
    // };

    // this.startPlaybackCheck = function(player) {
    //     var position = player.currentTime;
    //     var $this = this;
    //     setTimeout(function() {
    //         var positionNotChanged = position == player.currentTime;
    //         if (positionNotChanged) {
    //             $this.playerJumpToEnd();
    //        }
    //     }, this.checkTimeoutMS);
    // };

    this.getChecked = function() {
        return null; // not exists
    };

    this.setChecked = function(doChecked) {
        if (doChecked && !ExoUtils.playerIsClosed()) {
            ExoUtils.enablePlayerSuggestions();
            ExoUtils.showPlayerBackground();
            this.playerJumpToEnd2();
        }
    };
}