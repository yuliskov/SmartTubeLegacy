/**
 * Video sync history fix<br/>
 * Notifies about video position change.<br/>
 * Notifies about current category
 */

console.log("Scripts::Running script video_stats_watcher_addon.js");

var VideoStatsWatcherAddon = {
    TAG: 'VideoStatsWatcherAddon',
    MESSAGE_VIDEO_POSITION: 'message_video_position',
    MESSAGE_VIDEO_TYPE: 'message_video_type',
    VIDEO_TYPE_UNDEFINED: 'video_type_undefined',
    VIDEO_TYPE_DEFAULT: 'video_type_default',
    VIDEO_TYPE_LIVE: 'video_type_live',
    VIDEO_TYPE_UPCOMING: 'video_type_upcoming',
    recentVideoData: {},

    run: function() {
        this.addListener();
    },

    addListener: function() {
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
    },

    notifyPosition: function() {
        var bar = Utils.$([YouTubeSelectors.FOCUSED_VIDEO_PROGRESS_BAR, YouTubeSelectors.SUGGESTIONS_FOCUSED_VIDEO_PROGRESS_BAR]);

        if (bar && bar.style && bar.style.width) {
            var widthTemp = bar.style.width;
            var widthPercents = parseInt(widthTemp);

            Log.d(this.TAG, "Opening video. Position is " + widthPercents + "%");
            DeviceUtils.sendMessage(this.MESSAGE_VIDEO_POSITION, widthPercents);
        } else {
            Log.d(this.TAG, "Opening video. Position data not found!");
            DeviceUtils.sendMessage(this.MESSAGE_VIDEO_POSITION, -1);
        }
    },

    notifyVideoType: function() {
        var typeBage = Utils.$(YouTubeSelectors.FOCUSED_VIDEO_TYPE_BAGE);
        var durationBage = Utils.$([YouTubeSelectors.FOCUSED_VIDEO_DURATION_BAGE, YouTubeSelectors.SUGGESTIONS_FOCUSED_VIDEO_DURATION_BAGE]);
        var authorBage = Utils.$([YouTubeSelectors.FOCUSED_VIDEO_AUTHOR_BAGE, YouTubeSelectors.SUGGESTIONS_FOCUSED_VIDEO_AUTHOR_BAGE]);

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
        this.recentVideoData.type = videoType;

        if (durationBage && durationBage.innerText) {
            this.recentVideoData.duration = durationBage.innerText;
        } else {
            this.recentVideoData.duration = null;
        }

        if (authorBage && authorBage.innerText) {
            this.recentVideoData.author = authorBage.innerText;
        } else {
            this.recentVideoData.author = null;
        }
    }
}

// global obj
window.VideoStatsWatcherAddon = VideoStatsWatcherAddon;

VideoStatsWatcherAddon.run();
