/**
 * Video sync history fix<br/>
 * Notifies about video position change.<br/>
 * Notifies about current category
 */

console.log("Scripts::Running script video_stats_watcher_addon.js");

function VideoStatsWatcherAddon() {
    this.TAG = 'VideoStatsWatcherAddon';
    this.MESSAGE_VIDEO_POSITION = 'message_video_position';
    this.MESSAGE_VIDEO_TYPE = 'message_video_type';
    this.VIDEO_TYPE_UNDEFINED = 'video_type_undefined';
    this.VIDEO_TYPE_DEFAULT = 'video_type_default';
    this.VIDEO_TYPE_LIVE = 'video_type_live';
    this.VIDEO_TYPE_UPCOMING = 'video_type_upcoming';

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
                $this.notifyPosition();
                $this.notifyVideoType();
            }
        }, true);
    };

    this.notifyPosition = function() {
        var bar = Utils.$(YouTubeSelectors.FOCUSED_VIDEO_PROGRESS_BAR);

        if (bar && bar.style && bar.style.width) {
            var widthTemp = bar.style.width;
            var widthPercents = parseInt(widthTemp);

            Log.d(this.TAG, "Opening video. Position is " + widthPercents + "%");
            DeviceUtils.sendMessage(this.MESSAGE_VIDEO_POSITION, widthPercents);
        } else {
            Log.d(this.TAG, "Opening video. Position data not found!");
            DeviceUtils.sendMessage(this.MESSAGE_VIDEO_POSITION, -1);
        }
    };

    this.notifyVideoType = function() {
        var typeBage = Utils.$(YouTubeSelectors.FOCUSED_VIDEO_TYPE_BAGE);

        var videoType = this.VIDEO_TYPE_UNDEFINED;

        if (typeBage) {
            if (Utils.hasClass(typeBage, YouTubeClasses.VIDEO_TYPE_DEFAULT)) {
                videoType = this.VIDEO_TYPE_DEFAULT;
            } else if (Utils.hasClass(typeBage, YouTubeClasses.VIDEO_TYPE_LIVE)){
                videoType = this.VIDEO_TYPE_LIVE;
            } else if (Utils.hasClass(typeBage, YouTubeClasses.VIDEO_TYPE_UPCOMING)) {
                videoType = this.VIDEO_TYPE_UPCOMING;
            }
        }

        Log.d(this.TAG, "Current video type: " + videoType);
        DeviceUtils.sendMessage(this.MESSAGE_VIDEO_TYPE, videoType);
    };
}

if (DeviceUtils.isExo()) {
    new VideoStatsWatcherAddon().run();
}
