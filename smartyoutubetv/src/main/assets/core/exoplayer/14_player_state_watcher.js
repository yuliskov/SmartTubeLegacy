console.log("Scripts::Running core script player_state_watcher.js");

function PlayerStateWatcher() {
    this.CANCEL_PENDING_ACTIONS = "cancel_pending_actions";
    this.ENABLE_PENDING_ACTIONS = "enable_pending_actions";
    var $this = this;

    function notifyPendingActions(e) {
        if (Utils.hasClass(e.target, ExoConstants.emptyModelClass)) {
            console.log("PlayerStateWatcher: cancel pending actions...");
            // ExoUtils.sendAction($this.CANCEL_PENDING_ACTIONS);
        } else {
            console.log("PlayerStateWatcher: enable pending actions...");
            // ExoUtils.sendAction($this.ENABLE_PENDING_ACTIONS);
        }
    }

    EventUtils.addListener(ExoConstants.eventRootSelector, ExoConstants.modelChangedEvent, notifyPendingActions);
}

// new PlayerStateWatcher();