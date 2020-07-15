/**
 * Description:
 * Waits till loader overlay will be removed
 */

console.log("Scripts::Running script load_watcher.js");

function LoadWatcherAddon() {
    this.TAG = 'LoadWatcherAddon';

    this.run = function() {
        EventUtils.onLoad(function() {
            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_APP_LOADED);
        });
    };
}

new LoadWatcherAddon().run();
