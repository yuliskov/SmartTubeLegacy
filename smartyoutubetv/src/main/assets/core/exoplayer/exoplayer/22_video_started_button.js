console.log("Scripts::Running core script video_started_button.js");

function VideoStartedButton(selector) {
    this.TAG = 'VideoStartedButton';
    this.selector = selector;
    this.stateless = true;

    this.getChecked = function() {
        return null; // not exists
    };

    this.setChecked = function(doChecked) {
        if (doChecked && YouTubePlayerUtils.isPlayerOpened()) {
            Log.d(this.TAG, "imitate playing...");
            YouTubePlayerUtils.getPlayer().imitatePlaying();
        }
    };
}