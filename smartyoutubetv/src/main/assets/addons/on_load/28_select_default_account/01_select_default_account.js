/**
 * Extends app functionality
 */

console.log("Scripts::Running script select_default_account.js");

function SelectDefaultAccountAddon() {
    this.TAG = 'SelectDefaultAccountAddon';

    this.run = function() {
        var $this = this;

        EventUtils.onLoad(function() {
            $this.hidePanel();

            setTimeout(function() { // panel pop-ups with delay
                $this.closePanel();
                $this.showPanel();
            }, 3000);
        });
    };

    this.hidePanel = function() {
        Utils.hide(YouTubeSelectors.OVERLAY_PANEL_CONTAINER);
    };

    this.showPanel = function() {
        Utils.show(YouTubeSelectors.OVERLAY_PANEL_CONTAINER);
    };

    this.closePanel = function() {
        var dialog = Utils.$(YouTubeSelectors.OVERLAY_PANEL);

        if (dialog) {
            Log.d(this.TAG, "closing account panel...");
            EventUtils.triggerEvent(
                dialog,
                DefaultEvents.KEY_UP,
                DefaultKeys.ESC);
        }
    };
}

new SelectDefaultAccountAddon().run();