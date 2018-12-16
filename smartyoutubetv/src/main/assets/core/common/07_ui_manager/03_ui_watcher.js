/**
 * Watches onto the controller's keys events.
 */

console.log("Scripts::Running script ui_watcher.js");

var UiWatcher = {
    TAG: 'UiWatcher',
    buttonsContainerSel: '#buttons-list',

    resetEvents: function() {
        if (!this.buttonArr) {
            return;
        }

        for (var i = 0; i < this.buttonArr.length; i++) {
            this.unsetListener(this.buttonArr[i]);
        }
    },

    unsetListener: function(btn) {
        if (btn.getElem() && btn.onKeyDown) {
            EventUtils.removeListener(btn.getElem(), DefaultEvents.KEY_DOWN, btn.onKeyDown);
        }
    },

    handleMovements: function(buttonArr) {
        if (this.buttonArr) {
            this.resetEvents();
        }

        this.buttonArr = buttonArr;
        this.disableButtonEvents();
    },

    disableButtonEvents: function() {
        var $this = this;
        var firstBtn = this.buttonArr[0];
        var centerBtn = this.buttonArr[1];
        var lastBtn = this.buttonArr[2];

        Log.d(this.TAG, "add listeners to buttons: " +
            EventUtils.toSelector(firstBtn.getElem() || firstBtn.selector) + " "  +
            EventUtils.toSelector(centerBtn.getElem() || centerBtn.selector) + " " +
            EventUtils.toSelector(lastBtn.getElem() || lastBtn.selector));

        firstBtn.onKeyDown = function(e) {
            $this.onFirstButtonKey(e);
        };

        centerBtn.onKeyDown = function(e) {
            $this.onCenterButtonKey(e);
        };

        lastBtn.onKeyDown = function(e) {
            $this.onLastButtonKey(e);
        };

        EventUtils.addListener(firstBtn.getElem() || firstBtn.selector, DefaultEvents.KEY_DOWN, firstBtn.onKeyDown);

        EventUtils.addListener(centerBtn.getElem() || centerBtn.selector, DefaultEvents.KEY_DOWN, centerBtn.onKeyDown);

        EventUtils.addListener(lastBtn.getElem() || lastBtn.selector, DefaultEvents.KEY_DOWN, lastBtn.onKeyDown);
    },

    onFirstButtonKey: function(e) {
        if (e.keyCode != DefaultKeys.RIGHT) {
            return;
        }

        this.direction = e.keyCode;
        this.ytButton = e.target;

        // override right key
        e.stopPropagation();
        Log.d(this.TAG, "select center button");

        var centerBtn = this.buttonArr[1];
        centerBtn.focus();
        Utils.ytUnfocus(e.target);
    },

    onCenterButtonKey: function(e) {
        Log.d(this.TAG, "move selection to the youtube button");

        e.stopPropagation();

        var sameDirection = e.keyCode == this.direction;
        var leftOrRight = e.keyCode == DefaultKeys.LEFT || e.keyCode == DefaultKeys.RIGHT;

        if (!leftOrRight) {
            return;
        }

        if (sameDirection) {
            EventUtils.triggerEvent(this.buttonsContainerSel, DefaultEvents.KEY_DOWN, e.keyCode);
        } else {
            Utils.ytFocus(this.ytButton);
        }

        var centerBtn = this.buttonArr[1];
        centerBtn.unfocus();
    },

    onLastButtonKey: function(e) {
        if (e.keyCode != DefaultKeys.LEFT) {
            return;
        }

        this.direction = e.keyCode;
        this.ytButton = e.target;

        // override left key
        e.stopPropagation();
        Log.d(this.TAG, "select center button");

        var centerBtn = this.buttonArr[1];
        centerBtn.focus();
        Utils.ytUnfocus(e.target);
    },

    onUiUpdate: function(linstener) {
        this.listener = linstener;
        this.setupUiChangeListener();
        Log.d(this.TAG, "Ui change listener has been added");
    },

    setupUiChangeListener: function() {
        if (this.setupUiChangeIsDone) {
            return;
        }

        this.setupUiChangeIsDone = true;

        var $this = this;
        var onUiChange = function(e) {
            Log.d($this.TAG, "Running ui change listener");
            if ($this.listener) {
                $this.listener.onUiUpdate();
            }
        };

        EventUtils.addListener(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, YouTubeEvents.MODEL_CHANGED_EVENT, onUiChange);
        EventUtils.addListener(YouTubeSelectors.PLAYER_MORE_BUTTON, DefaultEvents.KEY_DOWN, onUiChange);
    }
};
