/**
 * Description:
 * Sends tick event after some period of time
 */

console.log("Scripts::Running script youthere_tweak.js");

function YouThereTweakAddon() {
    this.TAG = 'YouThereTweakAddon';
    this.INTERVAL_MS = 15 * 60 * 1000; // 15 min

    this.run = function() {
        this.setupTimer();
    };

    this.setupTimer = function() {
        var $this = this;

        setInterval(function() {
            Log.d($this.TAG, "Running tick code");
            EventUtils.triggerEnter(YouTubeSelectors.PLAYER_EVENTS_RECEIVER);
        }, this.INTERVAL_MS);
    };
}

new YouThereTweakAddon().run();
