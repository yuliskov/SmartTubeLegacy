/**
 * Video open watch<br/>
 * Notifies about video open time.
 */

console.log("Scripts::Running script video_open_watcher.js");

function VideoOpenWatcherAddon() {
    this.TAG = 'VideoOpenWatcherAddon';
    this.MESSAGE_VIDEO_OPEN_TIME = 'message_video_open_time';

    this.run = function() {
        this.addListener();
    };

    this.addListener = function() {
        var $this = this;

        // There is a handler that blocks others. We should run before it.
        // Note: running on capture phase
        EventUtils.addListener(document, DefaultEvents.KEY_DOWN, function(e) {
            Log.d($this.TAG, "User pressed " + e.keyCode);

            if (e.keyCode == DefaultKeys.ENTER) {
                //var bar = Utils.$(YouTubeSelectors.FOCUSED_VIDEO_PROGRESS_BAR);

                DeviceUtils.sendMessage($this.MESSAGE_VIDEO_OPEN_TIME, Utils.getCurrentTimeMs());
            }
        }, true);
    };
}

// if (DeviceUtils.isExo()) {
//     new VideoOpenWatcherAddon().run();
// }
