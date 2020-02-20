/**
 * Description:
 * Sends tick event after some period of time
 */

console.log("Scripts::Running script youthere_tweak.js");

function YouThereTweakAddon() {
    this.TAG = 'YouThereTweakAddon';
    this.INTERVAL_MS = 30 * 60 * 1000; // 30 min

    this.run = function() {
        this.setupTimer();
    };

    this.setupTimer = function() {
        this.setupListener();
    };

    this.setupListener = function() {
        var $this = this;

        // There is a handler that blocks others. We should run before it.
        // Note: running on capture phase
        EventUtils.addListener(document, DefaultEvents.KEY_DOWN, function(e) {
            if (e.keyCode == DefaultKeys.ENTER) {
                Log.d($this.TAG, "Resetting old interval and adding new one...");
                clearInterval($this.oldInterval);
                $this.oldInterval = setInterval(function() {
                    Log.d($this.TAG, "Running tick code");
                    EventUtils.triggerEnter(YouTubeSelectors.PLAYER_EVENTS_RECEIVER);
                }, $this.INTERVAL_MS);
            }
        }, true);
    };
}

new YouThereTweakAddon().run();
