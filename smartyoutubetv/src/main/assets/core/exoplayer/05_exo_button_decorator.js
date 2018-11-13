console.log("Scripts::Running core script exo_button_decorator.js");

/**
 * Decorator for methods {@link ExoButton.setChecked} and {@link ExoButton.getChecked}<br/>
 * Decorator purpose: to initialize the buttons before they will be checked
 * @param btn element to decorate
 * @constructor
 */
function ExoButtonDecorator(btn) {
    this.TAG = 'ExoButtonDecorator';
    this.btn = btn;
    this.callbackStack = [];
    this.menuToggleTimeout = 1000; // timeout until Options show on/off

    this.doPressOnOptionsBtn = function() {
        Log.d(this.TAG, "clicking on options button");
        EventUtils.triggerEnter(ExoConstants.optionsBtnSelector);
    };

    this.doCallbackIfReady = function(callback) {
        var $this = this;
        setTimeout(function() {
            Log.d($this.TAG, "after toggle options: " + $this.btn.selector + ' ' + $this.btn.findToggle());

            callback();
            $this.callbackStack.shift();
            if ($this.callbackStack[0]) {
                $this.callbackStack[0]();
            }
        }, this.menuToggleTimeout);
    };

    this.setCheckedWrapper = function(callback) {
        var obj = this.btn.findToggle();
        if (!obj || !obj.children.length) {
            this.doPressOnOptionsBtn();
            Log.d(this.TAG, 'set checked: btn not initialized: ' + this.btn.selector);
            this.doCallbackIfReady(callback);
            return;
        }
        callback();
        this.callbackStack.shift();
        if (this.callbackStack[0]) {
            this.callbackStack[0]();
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
            if ($this.callbackStack.length === 0) {
                $this.callbackStack.push(callback);
                callback();
            } else {
                // simply push (call it later)
                $this.callbackStack.push(callback);
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