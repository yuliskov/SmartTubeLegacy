/*
Description: On the last screen double back exits from the app
*/

console.log("Scripts::Running script double_back_exit.js");

function DoubleBackAddon() {
    this.TAG = 'DoubleBackAddon';
    this.MESSAGE_DOUBLE_BACK_EXIT = 'message_double_back_exit';

    this.run = function() {
        this.addRootListener();
        this.overrideClose();
    };

    this.overrideClose = function() {
        var $this = this;

        window.close = function() {
           $this.doExit();
        };
    };

    this.addRootListener = function() {
        var $this = this;

        YouTubeUtils.addExitListener(function() {
            Log.d($this.TAG, "Exit dialog is displayed");
            DeviceUtils.showExitMsg();
            DeviceUtils.sendMessage($this.MESSAGE_DOUBLE_BACK_EXIT);
        });
    };

    this.doExit = function() {
        Log.d(this.TAG, 'Exiting from the app...');
        DeviceUtils.closeApp();
    };
}

new DoubleBackAddon().run();