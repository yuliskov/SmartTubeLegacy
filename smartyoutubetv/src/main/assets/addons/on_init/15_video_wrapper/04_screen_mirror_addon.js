/**
 * Notifies about video position change.
 * Moved to 25_video_position_watcher
 */

console.log("Scripts::Running screen_mirror_addon.js");

function ScreenMirrorAddon() {
    this.TAG = 'VideoPositionAddon';
    this.MESSAGE_VIDEO_POSITION = 'message_video_position';
    this.MESSAGE_VIDEO_PAUSED = 'message_video_paused';

    this.onSrcChange = function(video) {
        // reset time
        Log.d(this.TAG, "New video opened??");
        //DeviceUtils.sendMessage(this.MESSAGE_VIDEO_POSITION, 0);
        this.scrChanged = true;
    };

    this.onCurrentTime = function(video) {
        // update time
        Log.d(this.TAG, "Video position changed to " + video.properties.currentTime);

        if (DeviceUtils.isMirrorEnabled()) {
            // force to resend mirror info
            video.listeners['playing'][0]({type: 'playing', isTrusted: true});
            video.listeners['pause'][0]({type: 'pause', isTrusted: true});
        }
    };

    this.onPausedChange = function(video) {
        // update time
        Log.d(this.TAG, "Video paused changed to " + video.paused);

        if (!this.scrChanged) {
            if (DeviceUtils.isMirrorEnabled()) {
                DeviceUtils.sendMessage(this.MESSAGE_VIDEO_PAUSED, video.paused);
                // force to resend mirror info
                video.listeners['playing'][0]({type: 'playing', isTrusted: true});
                video.listeners['pause'][0]({type: 'pause', isTrusted: true});
            }
        }

        this.scrChanged = false;
    };
}
