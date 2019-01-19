/**
 * Watches onto the controller's keys events.
 */

console.log("Scripts::Running script ui_watcher.js");

function UiWatcher(buttonsContainerSelector) {
    this.TAG = 'UiWatcher';
    this.buttonsContainerSelector = buttonsContainerSelector;

    this.resetEvents = function() {
        if (!this.buttonArr) {
            return;
        }

        for (var i = 0; i < this.buttonArr.length; i++) {
            this.unsetListener(this.buttonArr[i]);
            this.buttonArr[i].unfocus();
        }
    };

    this.unsetListener = function(btn) {
        var el = btn.cachedElement || btn.getElem();

        if (el && btn.onKeyDown) {
            Log.d(this.TAG, "Resetting events to: " + EventUtils.toSelector(el));
            EventUtils.removeListener(el, DefaultEvents.KEY_DOWN, btn.onKeyDown);
            btn.onKeyDown = null;
        }
    };

    this.handleMovements = function(buttonArr) {
        this.resetEvents();

        this.firstBtn = buttonArr[0];
        this.centerBtn = buttonArr[1];
        this.lastBtn = buttonArr[2];

        this.buttonArr = buttonArr;
        this.disableButtonEvents();
    };

    this.disableButtonEvents = function() {
        var $this = this;

        Log.d(this.TAG, "add listeners to buttons: " +
            EventUtils.toSelector(this.firstBtn.getElem() || this.firstBtn.selector) + " "  +
            EventUtils.toSelector(this.centerBtn.getElem() || this.centerBtn.selector) + " " +
            EventUtils.toSelector(this.lastBtn.getElem() || this.lastBtn.selector));

        this.addKeyDownListener(this.firstBtn, function(e) {
            $this.onFirstButtonKey(e);
        });

        this.addKeyDownListener(this.centerBtn, function(e) {
            $this.onCenterButtonKey(e);
        });

        this.addKeyDownListener(this.lastBtn, function(e) {
            $this.onLastButtonKey(e);
        });
    };

    this.addKeyDownListener = function(btn, handler) {
        if (btn.onKeyDown) { // don't add listener twice
            Log.d(this.TAG, "Handler already added to: " + EventUtils.toSelector(btn.getElem()));
            return;
        }

        btn.onKeyDown = handler;
        EventUtils.addListener(btn.getElem() || btn.selector, DefaultEvents.KEY_DOWN, btn.onKeyDown);
    };

    this.onFirstButtonKey = function(e) {
        if (e.keyCode != DefaultKeys.RIGHT) {
            return;
        }

        this.direction = e.keyCode;
        this.ytButton = e.target;

        // override right key
        e.stopPropagation();

        Log.d(this.TAG, "move selection from the first button to the center button, from: " + EventUtils.toSelector(e.target));

        this.centerBtn.focus();
        YouTubeUtils.unfocus(e.target);
    };

    this.onCenterButtonKey = function(e) {
        e.stopPropagation();

        var leftOrRight = e.keyCode == DefaultKeys.LEFT || e.keyCode == DefaultKeys.RIGHT;
        var upOrDown = e.keyCode == DefaultKeys.UP || e.keyCode == DefaultKeys.DOWN;

        // fix bug where mic button loses focus on up key
        if (upOrDown || leftOrRight) {
            e.preventDefault();
        }

        if (!leftOrRight) {
            return;
        }

        Log.d(this.TAG, "move selection to the youtube button");

        var sameDirection = e.keyCode == this.direction;

        if (sameDirection) {
            EventUtils.triggerEvent(this.buttonsContainerSelector, DefaultEvents.KEY_DOWN, e.keyCode);
        } else {
            YouTubeUtils.focus(this.ytButton);
        }

        this.centerBtn.unfocus();
    };

    this.onLastButtonKey = function(e) {
        if (e.keyCode != DefaultKeys.LEFT) {
            return;
        }

        this.direction = e.keyCode;
        this.ytButton = e.target;

        // override left key
        e.stopPropagation();

        Log.d(this.TAG, "move selection from the last button to the center button, from: " + EventUtils.toSelector(e.target));

        this.centerBtn.focus();
        YouTubeUtils.unfocus(e.target);
    };
}
