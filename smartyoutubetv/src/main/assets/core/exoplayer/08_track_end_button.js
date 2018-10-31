console.log("Scripts::Running core script track_end_button.js");

function TrackEndFakeButton(selector) {
    this.selector = selector;

    this.playerJumpToEnd = function() {
        console.log("TrackEndFakeButton: I'm about to start off!");
        window.lastButtonName = PlayerActivity.TRACK_ENDED;

        var player = Utils.$('video');
        if (player) {
            console.log("TrackEndFakeButton: before jumping to the end: curtime: " + player.currentTime + ", duration: " + player.duration);

            if (isNaN(player.duration)) {
                // something wrong, do workaround: play next track
                var btn = YouButton.fromSelector(PlayerActivityMapping.BUTTON_NEXT);
                btn.setChecked(true);
            } else {
                player.currentTime = player.duration - 1; // seek to the end
                player.play();
                console.log("TrackEndFakeButton: after jumping to the end: curtime: " + player.currentTime + ", duration: " + player.duration);
            }
        }
    };

    this.getChecked = function() {
        return null; // not exists
    };

    this.setChecked = function(doChecked) {
        if (doChecked)
            this.playerJumpToEnd();
    };
}