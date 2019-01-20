console.log("Scripts::Running core script exo_button_decorator.js");

/**
 * Decorator for methods {@link ExoButton.setChecked} and {@link ExoButton.getChecked}<br/>
 * Decorator purpose: initialize the buttons in same order they was checked
 * @param btn button to decorate
 * @constructor
 */
function ExoButtonDecorator(btn) {
    this.TAG = 'ExoButtonDecorator';
    this.btn = btn;
    this.menuToggleTimeout = 500; // timeout until Options show on/off

    this.doPressOnOptionsBtn = function() {
        Log.d(this.TAG, "clicking on options button");
        EventUtils.triggerEnter(YouTubeSelectors.PLAYER_MORE_BUTTON);
    };

    this.doCallbackIfReady = function(callback) {
        var $this = this;
        setTimeout(function() {
            Log.d($this.TAG, "after toggle options: " + $this.btn.selector + ' ' + $this.btn.findToggle());

            callback();
            ExoButtonDecorator.callbackStack.shift(); // remove this callback
            if (ExoButtonDecorator.callbackStack[0]) { // call previous callback
                ExoButtonDecorator.callbackStack[0]();
            }
        }, this.menuToggleTimeout);
    };

    this.setCheckedWrapper = function(callback) {
        var obj = this.btn.findToggle();
        if (!obj || !obj.children.length) {
            Log.d(this.TAG, 'set checked: btn not initialized: ' + this.btn.selector);
            this.doPressOnOptionsBtn();
            this.doCallbackIfReady(callback);
            return;
        }
        callback();
        ExoButtonDecorator.callbackStack.shift(); // remove this callback
        if (ExoButtonDecorator.callbackStack[0]) { // call previous callback
            ExoButtonDecorator.callbackStack[0]();
        }
    };

    this.getCheckedWrapper = function(callback) {
        var obj = this.btn.findToggle();
        if (!obj || !obj.children.length) {
            Log.d(this.TAG, 'get checked: btn not initialized: ' + this.btn.selector);
            this.doPressOnOptionsBtn();
        }
        return callback();
    };

    this.apply = function() {
        if (!this.btn.selector)
            return;

        this.applySetChecked();
        this.applyGetChecked();
    };

    this.applySetChecked = function() {
        var $this = this;
        var realSetChecked = this.btn.setChecked;
        this.btn.setChecked = function(doChecked) {
            var thisBtn = this;
            var callback = function() {
                $this.setCheckedWrapper(function() {
                    realSetChecked.call(thisBtn, doChecked);
                });
            };
            if (ExoButtonDecorator.callbackStack.length == 0) {
                ExoButtonDecorator.callbackStack.push(callback);
                callback();
            } else {
                // simply push (call it later)
                ExoButtonDecorator.callbackStack.push(callback);
            }
        };
    };

    this.applyGetChecked = function() {
        var $this = this;

        // can't use stack! we have to return immediately! no delays allowed!
        var realGetChecked = this.btn.getChecked;
        this.btn.getChecked = function() {
            var thisBtn = this;
            return $this.getCheckedWrapper(function() {
                return realGetChecked.call(thisBtn);
            });
        };
    };
}

// global storage for pending callbacks
ExoButtonDecorator.callbackStack = [];