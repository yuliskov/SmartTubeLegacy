console.log("Scripts::Running core script video_started_button.js");

function VideoStartedButton(selector) {
    this.TAG = 'VideoStartedButton';
    this.selector = selector;
    this.stateless = true;

    this.getChecked = function() {
        return null; // not exists
    };

    this.setChecked = function(doChecked) {
        if (doChecked && YouTubeUtils.isPlayerOpened()) {
            Log.d(this.TAG, "imitate playing...");
            YouTubeUtils.getPlayer().imitatePlaying();
        }
    };
}