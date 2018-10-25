// NOTE: this file doesn't depend on common js files

console.log("Scripts::Running core script run_once_button.js");

// indicates that any key is pressed at least one time
function KeyActivityWatcher() {
    function KeyActivityWatcherService() {
        var mainContainer = document;
        var type = 'keydown';
        var $this = this;
        this.runOnce = false;

        var runOnceListener = function(e) {
            $this.runOnce = true;
            mainContainer.removeEventListener(type, runOnceListener); // signature must match
            console.log("exoplayer.js: KeyActivityWatcher: runOnceListener");
        };

        mainContainer.addEventListener(type, runOnceListener);
        console.log("exoplayer.js: KeyActivityWatcher: do init...");
    }

    KeyActivityWatcherService.prototype = new ExoConstants();

    if (!window.keyActivityWatcherService) {
        window.keyActivityWatcherService = new KeyActivityWatcherService();
    }

    this.getRunOnce = function() {
        return window.keyActivityWatcherService.runOnce;
    };
}

KeyActivityWatcher.prototype = new ExoConstants();
KeyActivityWatcher.disable = function() {
    new KeyActivityWatcher(null);
};

function PlayerRunOnceFakeButton(selector) {
    this.selector = selector;

    this.getChecked = function() {
        var watcher = new KeyActivityWatcher();
        console.log("exoplayer.js: PlayerRunOnceFakeButton: user did activity: " + watcher.getRunOnce());
        return watcher.getRunOnce();
    };

    this.setChecked = function(doChecked) {
        // do nothing
    };
}

// watch for the key presses from the start of our app (see PlayerRunOnceFakeButton)
new KeyActivityWatcher();