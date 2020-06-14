/**
 * Description:
 * Prevent user from exit from video while using suggestions or favorites.
 */

console.log("Scripts::Running script external_player_watcher.js");

function ExternalPlayerWatcherAddon() {
    this.TAG = 'ExternalPlayerWatcherAddon';

    this.run = function() {
        this.hideKeyboardOnSubmit();
    };

    this.hideKeyboardOnSubmit = function() {
        var $this = this;

        var handler = function(e) {
            Log.d($this.TAG, "User pressed up/down " + e.keyCode);

            if (e.keyCode == DefaultKeys.ESC) {
                if ($this.checkPlayerState()) {
                    Log.d($this.TAG, "Blocking BACK key.");

                    // force return to the player (in case buttons handlers didn't work out)
                    ExoUtils.sendAction(ExoUtils.ACTION_CLOSE_SUGGESTIONS);

                    e.stopPropagation();
                }
            }
        };

        // There is a handler that blocks others. We should run before it.
        // Note: running on capture phase
        EventUtils.addListener(document, DefaultEvents.KEY_UP, handler, true);

        // There is a handler that blocks others. We should run before it.
        // Note: running on capture phase
        //EventUtils.addListener(document, DefaultEvents.KEY_DOWN, handler, true);
    };

    this.checkPlayerState = function() {
        var allPlayersActive = YouTubeUtils.isPlayerOpened() && YouTubeUtils.isExoPlayerOpen();
        var controlsClosed = YouTubeUtils.isAllPlayerUIClosed();
        return allPlayersActive && controlsClosed;
    };
}

if (DeviceUtils.isExo()) {
    new ExternalPlayerWatcherAddon().run();
}
