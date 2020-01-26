/**
 * Video sync history fix<br/>
 * Notifies about video position change.
 */

console.log("Scripts::Running script video_position_addon.js");

function VideoPositionWatcherAddon() {
    this.TAG = 'VideoPositionWatcherAddon';
    this.MESSAGE_VIDEO_POSITION = 'message_video_position';

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
                var bar = Utils.$(YouTubeSelectors.FOCUSED_VIDEO_PROGRESS_BAR);

                if (bar && bar.style && bar.style.width) {
                    var widthTemp = bar.style.width;
                    var widthPercents = parseInt(widthTemp);

                    Log.d($this.TAG, "Opening video. Position is " + widthPercents + "%");
                    DeviceUtils.sendMessage($this.MESSAGE_VIDEO_POSITION, widthPercents);
                } else {
                    Log.d($this.TAG, "Opening video. Position data not found!");
                    DeviceUtils.sendMessage($this.MESSAGE_VIDEO_POSITION, -1);
                }
            }
        }, true);
    };
}

if (DeviceUtils.isExo()) {
    new VideoPositionWatcherAddon().run();
}
