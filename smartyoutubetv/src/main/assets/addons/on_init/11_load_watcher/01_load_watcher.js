/**
 * Description:
 * Waits till loader overlay will be removed
 */

console.log("Scripts::Running script load_watcher.js");

function LoadWatcherAddon() {
    this.TAG = 'LoadWatcherAddon';
    this.APP_LOADED_MESSAGE = 'app_loaded_message';

    this.run = function() {
        this.startLoadNotify();
    };

    this.startLoadNotify = function() {
        if (!Utils.$('#loader')) {
            Log.d(this.TAG, 'app has been loaded');
            DeviceUtils.sendMessage(this.APP_LOADED_MESSAGE);
            return;
        }

        var $this = this;
        setTimeout(function() {
           $this.startLoadNotify();
        }, 500);
    };
}

new LoadWatcherAddon().run();
