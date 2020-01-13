/**
 * Description:
 * Trying to return long press behavior to enter key.
 */

console.log("Scripts::Running script enter_long_press_fix.js");

function EnterLongPressFixAddon() {
    this.TAG = 'EnterLongPressFixAddon';
    this.lastEvent = {name: '', nums: 0};

    this.run = function() {
        this.applyLongPressFix();
    };

    this.applyLongPressFix = function() {
        var $this = this;

        // There is a handler that blocks others. We should run before it.
        // Note: running on capture phase
        EventUtils.addListener(document, DefaultEvents.ON_CLICK, function(e) {
            Log.d($this.TAG, "User clicked on " + e.keyCode);
            // e.stopPropagation();
        }, true);

        // There is a handler that blocks others. We should run before it.
        // Note: running on capture phase
        EventUtils.addListener(document, DefaultEvents.KEY_PRESS, function(e) {
            Log.d($this.TAG, "User pressed " + e.keyCode);
            // e.stopPropagation();
        }, true);

        // There is a handler that blocks others. We should run before it.
        // Note: running on capture phase
        EventUtils.addListener(document, DefaultEvents.KEY_UP, function(e) {
            Log.d($this.TAG, "User pressed up " + e.keyCode);
            // e.stopPropagation();
            //
            // if (e.keyCode == DefaultKeys.ENTER) {
            //     $this.lastEvent.name = DefaultEvents.KEY_UP;
            //     $this.lastEvent.nums = 0;
            // }
        }, true);

        // There is a handler that blocks others. We should run before it.
        // Note: running on capture phase
        EventUtils.addListener(document, DefaultEvents.KEY_DOWN, function(e) {
            //e.stopPropagation();
            // if ($this.lastEvent.name == DefaultEvents.KEY_DOWN && $this.lastEvent.nums >= 1 && e.keyCode == DefaultKeys.ENTER) {
            //     e.stopPropagation();
            //     return;
            // }
            //
            // if (e.keyCode == DefaultKeys.ENTER) {
            //     $this.lastEvent.name = DefaultEvents.KEY_DOWN;
            //     $this.lastEvent.nums++;
            // }

            Log.d($this.TAG, "User pressed down " + e.keyCode);
        }, true);
    };
}

// new EnterLongPressFixAddon().run();

window.environment && (window.environment.flags.enable_animated_thumbnails_on_atv = true);