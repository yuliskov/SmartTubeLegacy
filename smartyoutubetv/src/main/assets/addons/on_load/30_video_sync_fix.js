/**
 * Description:
 * Fix out of sync video on the specific devices.
 */

console.log("Scripts::Running script video_sync_fix.js");

/**
 * Not implemented yet!!!
 */
function VideoSyncFixAddon() {
    this.run = function() {
        // find proper timeout for device
        // post callback after timeout
        // if paused, then clear timeout and set new timer
    };

    this.syncVideo = function() {
        var v = Utils.$('video');
        v.currentTime = v.currentTime;
    };
}

new VideoSyncFixAddon().run();