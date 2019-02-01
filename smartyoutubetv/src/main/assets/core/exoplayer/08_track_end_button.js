console.log("Scripts::Running core script track_end_button.js");

function TrackEndFakeButton(selector) {
    this.TAG = 'TrackEndFakeButton';
    this.selector = selector;

    this.pressNextButton = function() {
        Log.d(this.TAG, "Something is wrong, do workaround: switch to the next track");
        var btn = ExoButton.fromSelector(PlayerActivityMapping.BUTTON_NEXT);
        btn.setChecked(true);
    };

    this.getChecked = function() {
        return null; // not exists
    };

    this.setChecked = function(doChecked) {
        if (doChecked && !YouTubeUtils.isPlayerClosed()) {
            YouTubeUtils.enablePlayerSuggestions();
            YouTubeUtils.showPlayerBackground();

            var $this = this;
            PlayerController.jumpToEnd(function() {
                $this.pressNextButton();
            });
        }
    };
}