console.log("Scripts::Running core script exo_button_decorator.js");

/**
 * Decorator for methods {@link ExoButton.setChecked} and {@link ExoButton.getChecked}<br/>
 * Decorator purpose: initialize the buttons in same order they was checked
 * @constructor
 */
function ExoButtonDecorator() {
    this.TAG = 'ExoButtonDecorator';
    this.menuToggleTimeout = 500; // timeout until Options show on/off
    this.callbackStack = [];
    this.callbackBackupStack = [];

    this.doPressOnOptionsBtn = function() {
        Log.d(this.TAG, "clicking on options button");
        EventUtils.triggerEnter(YouTubeSelectors.PLAYER_MORE_BUTTON);
    };

    this.setCheckedWrapper = function(callback, btn) {
        var obj = btn.findToggle();
        var $this = this;
        var objExists = obj && obj.children.length;

        if (this.pendingOptions) { // wait till timeout riches
            Log.d(this.TAG, "Found pending set checked queries... Exiting... " + btn.selector);
            return;
        }

        if (!objExists && this.backupCopied) {
            // prevent loop
            this.callbackStack.shift(); // at least one item should be there
        } else if (!objExists) {
            Log.d(this.TAG, 'set checked wrapper: btn not initialized: ' + btn.selector);

            if (this.callbackStack.length != 0) { // save this callback for later usage
                this.callbackBackupStack.push(this.callbackStack.shift());
            }

            if (this.callbackStack.length == 0 && this.callbackBackupStack.length != 0) {
                this.callbackStack = this.callbackBackupStack;
                this.callbackBackupStack = [];
                this.backupCopied = true;

                this.pendingOptions = true;

                this.doPressOnOptionsBtn();

                setTimeout(function() {
                    $this.pendingOptions = false;
                    $this.callbackStack[0]();
                }, $this.menuToggleTimeout);

                return;
            }
        } else {
            Log.d(this.TAG, "Element is found!!! Running real set checked on " + btn.selector);
            this.callbackStack.shift(); // at least one item should be there
            callback();
        }

        if (this.callbackStack.length != 0) {
            this.callbackStack[0]();
        } else if (this.callbackBackupStack.length != 0) {
            this.callbackStack = this.callbackBackupStack;
            this.callbackBackupStack = [];
            this.backupCopied = true;
            this.callbackStack[0]();
        } else {
            Log.d($this.TAG, "Reaching top of the stack: " + this.callbackStack.length + " " + this.callbackBackupStack.length);
            this.running = false;
            this.backupCopied = false;
        }
    };

    this.getCheckedWrapper = function(callback, btn) {
        var obj = btn.findToggle();
        if (!obj || !obj.children.length) {
            Log.d(this.TAG, 'get checked wrapper: btn not initialized: ' + btn.selector);
            this.doPressOnOptionsBtn();
        }
        return callback();
    };

    this.apply = function(btn) {
        if (!btn) {
            Log.d(this.TAG, "Button is null");
            return;
        }

        if (!btn.selector) {
            Log.d(this.TAG, "Button selector not found: " + btn.constructor.name + " Exiting...");
            return;
        }

        Log.d(this.TAG, "Applying decorations to: " + btn.selector);

        this.applySetChecked(btn);
        this.applyGetChecked(btn);
    };

    this.applySetChecked = function(btn) {
        var $this = this;
        var realSetChecked = btn.setChecked;
        btn.setChecked = function(doChecked) {
            if (this.stateless && !doChecked) {
                Log.d($this.TAG, "Excluding from set checked processing: " + this.selector);
                return;
            }

            var thisBtn = this;
            var wrapper = function() {
                $this.setCheckedWrapper(function() {
                    realSetChecked.call(thisBtn, doChecked);
                }, btn);
            };

            // simply push (call it later)
            Log.d($this.TAG, "Adding set checked to the stack: " + btn.selector);
            $this.callbackStack.push(wrapper);

            if (!$this.running) {
                Log.d($this.TAG, "Starting set checked chaining: " + btn.selector + " " + doChecked);
                $this.running = true;
                wrapper();
            }
        };
    };

    this.applyGetChecked = function(btn) {
        var $this = this;

        // can't use stack! we have to return immediately! no delays allowed!
        var realGetChecked = btn.getChecked;
        btn.getChecked = function() {
            if (this.stateless) {
                Log.d($this.TAG, "Excluding from get checked processing: " + this.selector);
                return false;
            }

            var thisBtn = this;
            return $this.getCheckedWrapper(function() {
                return realGetChecked.call(thisBtn);
            }, btn);
        };
    };
}

// global storage for pending callbacks
ExoButtonDecorator.instance = function() {
    if (!this.obj) {
        this.obj = new ExoButtonDecorator();
    }

    return this.obj;
};