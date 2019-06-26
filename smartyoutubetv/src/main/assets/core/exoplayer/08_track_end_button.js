console.log("Scripts::Running core script track_end_button.js");

/**
 * Uses 'hash' url param to switch to the next video
 * @param selector css selector or null in case of the fake button
 * @param nextVideoSpec youtube video id and playlist id divided by '|' char
 */
function TrackEndFakeButton(selector, nextVideoSpec) {
    this.TAG = 'TrackEndFakeButton';
    this.selector = selector;

    var spec = nextVideoSpec.split("|");

    this.nextVideoId = spec[0];
    this.nextPlaylistId = spec[1];
    this.HASH_URL = '#/watch/video/idle?%VIDEO_DATA%resume';
    this.VIDEO_ID = 'v=%VIDEO_ID%&';
    this.PLAYLIST_ID = 'list=%PLAYLIST_ID%&';

    this.getChecked = function() {
        return null; // not exists
    };

    this.setChecked = function(doChecked) {
        if (doChecked && this.nextVideoId) {
            // YouTubeUtils.enablePlayerSuggestions();
            // YouTubeUtils.showPlayerBackground();

            var videoData = this.VIDEO_ID.replace("%VIDEO_ID%", this.nextVideoId);

            var nextHash = this.HASH_URL.replace("%VIDEO_DATA%", videoData);

            // if (this.nextPlaylistId) {
            //     nextHash = nextHash.replace("%PLAYLIST_ID%", this.nextPlaylistId);
            // } else {
            //     nextHash = nextHash.replace("&list=%PLAYLIST_ID%", "");
            // }

            location.hash = nextHash;
        }
    };
}