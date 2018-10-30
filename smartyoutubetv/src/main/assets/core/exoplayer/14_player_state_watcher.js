console.log("Scripts::Running core script player_state_watcher.js");

function PlayerStateWatcher() {
    this.CANCEL_PENDING_ACTIONS = "cancel_pending_actions";
    this.ENABLE_PENDING_ACTIONS = "enable_pending_actions";
    var $this = this;

    function notifyPendingActions(e) {
        if (Utils.hasClass(e.target, $this.emptyModelClass)) {
            console.log("PlayerStateWatcher: cancel pending actions...");
            // exoutils.sendAction($this.CANCEL_PENDING_ACTIONS);
        } else {
            console.log("PlayerStateWatcher: enable pending actions...");
            // exoutils.sendAction($this.ENABLE_PENDING_ACTIONS);
        }
    }

    EventUtils.addListener(this.eventRootSelector, this.modelChangedEvent, notifyPendingActions);
}

PlayerStateWatcher.prototype = new ExoConstants();

// new PlayerStateWatcher();