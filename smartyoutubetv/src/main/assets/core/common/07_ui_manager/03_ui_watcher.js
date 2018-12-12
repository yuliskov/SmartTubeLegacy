/**
 * Watches onto the controller's keys events.
 */

console.log("Scripts::Running script ui_watcher.js");

var UiWatcher = {
    TAG: 'UiWatcher',
    buttonsContainerSel: '#buttons-list',

    handleMovements: function(buttonArr) {
        this.buttonArr = buttonArr;
        this.disableButtonEvents();
    },

    disableButtonEvents: function() {
        var $this = this;
        var firstBtn = this.buttonArr[0];
        var centerBnt = this.buttonArr[1];
        var lastBtn = this.buttonArr[2];

        Log.d(this.TAG, "add listeners to buttons: " + firstBtn.getElem());

        EventUtils.addListener(firstBtn.getElem() || firstBtn.selector, DefaultEvents.KEY_DOWN, function(e) {
            Log.d($this.TAG, "on keydown " + e);
            $this.onFirstButtonKey(e);
        });

        EventUtils.addListener(centerBnt.getElem() || centerBnt.selector, DefaultEvents.KEY_DOWN, function(e) {
            Log.d($this.TAG, "on keydown " + e);
            $this.onCenterButtonKey(e);
        });

        EventUtils.addListener(lastBtn.getElem() || lastBtn.selector, DefaultEvents.KEY_DOWN, function(e) {
            Log.d($this.TAG, "on keydown " + e);
            $this.onLastButtonKey(e);
        });
    },

    onFirstButtonKey: function(e) {
        if (e.keyCode != DefaultKeys.RIGHT) {
            return;
        }

        // override right key
        e.stopPropagation();

        Log.d(this.TAG, "select center button");
        var centerBtn = this.buttonArr[1];
        centerBtn.getElem().focus();
    },

    onCenterButtonKey: function(e) {
        Log.d(this.TAG, "move selection to the youtube button");
        EventUtils.triggerEvent(this.buttonsContainerSel, DefaultEvents.KEY_DOWN, e.keyCode);
    },

    onLastButtonKey: function(e) {
        if (e.keyCode != DefaultKeys.LEFT) {
            return;
        }

        // override left key
        e.stopPropagation();

        Log.d(this.TAG, "select center button");
        var centerBtn = this.buttonArr[1];
        centerBtn.getElem().focus();
    },
};
