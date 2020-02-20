/**
 * Notifies about video position change.
 * Moved to 25_video_position_watcher
 */

console.log("Scripts::Running screen_mirror_addon.js");

function ScreenMirrorAddon() {
    this.TAG = 'ScreenMirrorAddon';
    this.MESSAGE_VIDEO_POSITION = 'message_video_position';
    this.MESSAGE_VIDEO_PAUSED = 'message_video_paused';
    this.MESSAGE_VIDEO_OPENED = 'message_video_opened';

    this.onSrcChange = function(video) {
        // reset time
        Log.d(this.TAG, "New video opened??");

        this.scrChanged = true;

        if (DeviceUtils.isMirrorEnabled() && DeviceUtils.isBrowserInBackground()) {
            DeviceUtils.sendMessage(this.MESSAGE_VIDEO_OPENED, video.src);
        }
    };

    this.onCurrentTime = function(video) {
        // update time
        Log.d(this.TAG, "Video position changed to " + video.properties.currentTime);

        if (DeviceUtils.isMirrorEnabled() && DeviceUtils.isBrowserInBackground()) { // Suggestions reboot videos fix
            DeviceUtils.sendMessage(this.MESSAGE_VIDEO_POSITION, video.properties.currentTime);

            // force to resend mirror info
            video.listeners['playing'][0]({type: 'playing', isTrusted: true});
            video.listeners['pause'][0]({type: 'pause', isTrusted: true});

            setTimeout(function() {
                // fix mirror progress bar is full on start
                video.properties.currentTime = 1;
            }, 1000);
        }
    };

    this.onPausedChange = function(video) {
        // update time
        Log.d(this.TAG, "Video paused changed to " + video.paused);

        if (!this.scrChanged) {
            if (DeviceUtils.isMirrorEnabled() && DeviceUtils.isBrowserInBackground()) { // Suggestions reboot videos fix
                DeviceUtils.sendMessage(this.MESSAGE_VIDEO_PAUSED, video.paused);
                // force to resend mirror info
                video.listeners['playing'][0]({type: 'playing', isTrusted: true});
                video.listeners['pause'][0]({type: 'pause', isTrusted: true});
            }
        }

        this.scrChanged = false;
    };
}
