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
        var firstBtnSel = this.buttonArr[0];
        var centerBntSel = this.buttonArr[1];
        var lastBtnSel = this.buttonArr[2];

        EventUtils.addListener(firstBtnSel, DefaultEvents.KEY_DOWN, function(e) {
            $this.onFirstButtonKey(e);
        });

        EventUtils.addListener(centerBntSel, DefaultEvents.KEY_DOWN, function(e) {
            $this.onCenterButtonKey(e);
        });

        EventUtils.addListener(lastBtnSel, DefaultEvents.KEY_DOWN, function(e) {
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
        var centerBntSel = this.buttonArr[1];
        Utils.$(centerBntSel).focus();
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
        var centerBntSel = this.buttonArr[1];
        Utils.$(centerBntSel).focus();
    },
};
