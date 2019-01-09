/**
 * Description:
 * Waits till loader overlay will be removed
 */

console.log("Scripts::Running script load_watcher.js");

function LoadWatcherAddon() {
    this.TAG = 'LoadWatcherAddon';
    this.APP_LOADED_MESSAGE = 'app_loaded_message';

    this.run = function() {
        var $this = this;

        EventUtils.onLoad(function() {
            DeviceUtils.sendMessage($this.APP_LOADED_MESSAGE);
        });
    };
}

new LoadWatcherAddon().run();
