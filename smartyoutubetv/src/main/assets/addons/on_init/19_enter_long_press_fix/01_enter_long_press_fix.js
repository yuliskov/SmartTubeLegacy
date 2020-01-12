/**
 * Description:
 * Trying to return long press behavior to enter key.
 */

console.log("Scripts::Running script enter_long_press_fix.js");

function EnterLongPressFixAddon() {
    this.TAG = 'EnterLongPressFixAddon';

    this.run = function() {
        this.applyLongPressFix();
    };

    this.applyLongPressFix = function() {
        var $this = this;

        // There is a handler that blocks others. We should run before it.
        // Note: running on capture phase
        EventUtils.addListener(document, DefaultEvents.KEY_PRESS, function(e) {
            Log.d($this.TAG, "User pressed " + e.keyCode);
        }, true);

        // There is a handler that blocks others. We should run before it.
        // Note: running on capture phase
        EventUtils.addListener(document, DefaultEvents.KEY_UP, function(e) {
            Log.d($this.TAG, "User pressed up " + e.keyCode);

            if (e.keyCode == DefaultKeys.ENTER) {
                $this.lastEvent = DefaultEvents.KEY_UP;
            }
        }, true);

        // There is a handler that blocks others. We should run before it.
        // Note: running on capture phase
        EventUtils.addListener(document, DefaultEvents.KEY_DOWN, function(e) {
            if ($this.lastEvent == DefaultEvents.KEY_DOWN && e.keyCode == DefaultKeys.ENTER) {
                e.stopPropagation();
                return;
            }

            if (e.keyCode == DefaultKeys.ENTER) {
                $this.lastEvent = DefaultEvents.KEY_DOWN;
            }

            Log.d($this.TAG, "User pressed down " + e.keyCode);
        }, true);
    };
}

new EnterLongPressFixAddon().run();