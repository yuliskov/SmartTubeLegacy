/**
 * Watches onto the controller's keys events.
 */

console.log("Scripts::Running script search_page_ui_watcher.js");

var SearchPageUiWatcher = {
    TAG: 'SearchPageUiWatcher',
    buttonsContainerSel: YouTubeSelectors.SEARCH_PAGE,

    resetEvents: function() {
        if (!this.buttonArr) {
            return;
        }

        for (var i = 0; i < this.buttonArr.length; i++) {
            this.unsetListener(this.buttonArr[i]);
            this.buttonArr[i].unfocus();
        }
    },

    unsetListener: function(btn) {
        var el = btn.cachedElement || btn.getElem();

        if (el && btn.onKeyDown) {
            Log.d(this.TAG, "Resetting events to: " + EventUtils.toSelector(el));
            EventUtils.removeListener(el, DefaultEvents.KEY_DOWN, btn.onKeyDown);
            btn.onKeyDown = null;
        }
    },

    handleMovements: function(buttonArr) {
        this.resetEvents();

        this.firstBtn = buttonArr[0];
        this.centerBtn = buttonArr[1];
        this.lastBtn = buttonArr[2];

        this.buttonArr = buttonArr;
        this.disableButtonEvents();
    },

    disableButtonEvents: function() {
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
    },

    addKeyDownListener: function(btn, handler) {
        if (btn.onKeyDown) { // don't add listener twice
            Log.d(this.TAG, "Handler already added to: " + EventUtils.toSelector(btn.getElem()));
            return;
        }

        btn.onKeyDown = handler;
        EventUtils.addListener(btn.getElem() || btn.selector, DefaultEvents.KEY_DOWN, btn.onKeyDown);
    },

    onFirstButtonKey: function(e) {
        if (e.keyCode != DefaultKeys.RIGHT) {
            return;
        }

        this.direction = e.keyCode;
        this.ytButton = e.target;

        // override right key
        e.stopPropagation();
        Log.d(this.TAG, "move selection from the first button to the center button, from: " + EventUtils.toSelector(e.target));

        this.centerBtn.focus();
        Utils.ytUnfocus(e.target);
    },

    onCenterButtonKey: function(e) {
        e.stopPropagation();

        var sameDirection = e.keyCode == this.direction;
        var leftOrRight = e.keyCode == DefaultKeys.LEFT || e.keyCode == DefaultKeys.RIGHT;

        if (!leftOrRight) {
            return;
        }

        Log.d(this.TAG, "move selection to the youtube button");

        if (sameDirection) {
            EventUtils.triggerEvent(this.buttonsContainerSel, DefaultEvents.KEY_DOWN, e.keyCode);
        } else {
            Utils.ytFocus(this.ytButton);
        }

        this.centerBtn.unfocus();
    },

    onLastButtonKey: function(e) {
        if (e.keyCode != DefaultKeys.LEFT) {
            return;
        }

        this.direction = e.keyCode;
        this.ytButton = e.target;

        // override left key
        e.stopPropagation();
        Log.d(this.TAG, "move selection from the last button to the center button, from: " + EventUtils.toSelector(e.target));

        this.centerBtn.focus();
        Utils.ytUnfocus(e.target);
    },

    onUiUpdate: function(listener) {
        this.listener = listener;
        this.setupUiChangeListener();
        Log.d(this.TAG, "Ui change listener has been added");
    },

    setupUiChangeListener: function() {
        if (this.setupUiChangeIsDone) {
            return;
        }

        this.setupUiChangeIsDone = true;

        var $this = this;
        var onUiChange = function() {
            Log.d($this.TAG, "Running ui change listener");
            if ($this.listener) {
                $this.listener.onUiUpdate();
            }
        };

        EventUtils.addListener(YouTubeSelectors.SEARCH_PAGE, YouTubeEvents.MODEL_CHANGED_EVENT, function(e) {
            if (!Utils.hasClass(e.target, YouTubeClasses.NO_MODEL)) { // trigger when user opens additional buttons
                setTimeout(onUiChange, 500); // let button finish initialization
            }
        });
    }
};
