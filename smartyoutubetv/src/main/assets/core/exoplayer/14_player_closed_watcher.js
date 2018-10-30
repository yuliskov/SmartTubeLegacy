console.log("Scripts::Running core script player_closed_watcher.js");

function PlayerClosedWatcher() {
    this.PLAYER_CLOSED = "action_player_closed";
    var $this = this;

    function notifyPlayerClosed(e) {
        if (e.keyCode == DefaultKeys.ESC && Utils.hasClass(e.target, $this.playerUiHiddenClass)) {
            console.log("PlayerClosedWatcher: user cancel playback...");
            // exoutils.sendAction($this.PLAYER_CLOSED);
        }
    }

    EventUtils.addListener(this.eventRootSelector, DefaultEvents.KEY_DOWN, notifyPlayerClosed);
}

PlayerClosedWatcher.prototype = new ExoConstants();

new PlayerClosedWatcher();