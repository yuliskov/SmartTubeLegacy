console.log("Scripts::Running core script exo_button_decorator.js");

/**
 * Decorator for methods {@link ExoButton.setChecked} and {@link ExoButton.getChecked}<br/>
 * Decorator purpose: initialize the buttons in same order they was checked
 * @constructor
 */
function ExoButtonDecorator() {
    this.TAG = 'ExoButtonDecorator';
    this.menuToggleTimeout = 500; // timeout until Options show on/off
    this.running = false;
    this.currentIndex = 0;
    this.callbackStack = [];

    this.doPressOnOptionsBtn = function() {
        Log.d(this.TAG, "clicking on options button");
        EventUtils.triggerEnter(YouTubeSelectors.PLAYER_MORE_BUTTON);
    };

    this.setCheckedWrapper = function(callback, btn, secondAttempt) {
        var obj = btn.findToggle();
        var $this = this;
        var objExists = obj && obj.children.length;

        if (!objExists && secondAttempt) {
            Log.d(this.TAG, "element not found, exiting " + btn.selector);
        } else if (!objExists) {
            Log.d(this.TAG, 'set checked wrapper: btn not initialized: ' + btn.selector);

            if (!this.pendingOptions) {
                $this.doPressOnOptionsBtn();
            }

            this.pendingOptions = true;

            setTimeout(function() {
                $this.pendingOptions = false;
                $this.setCheckedWrapper(callback, btn, true);
            }, $this.menuToggleTimeout);

            return;
        } else {
            Log.d(this.TAG, "Element is found!!! Running real set checked on " + btn.selector);
            callback();
        }

        this.currentIndex++;

        if (this.callbackStack[this.currentIndex]) { // call previous callback
            this.callbackStack[this.currentIndex]();
        } else {
            Log.d($this.TAG, "Reaching top of the stack: " + this.callbackStack.length);
            this.running = false;
            this.currentIndex = 0;
            this.callbackStack = [];
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