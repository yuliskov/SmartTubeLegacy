console.log("Scripts::Running core script exo_button_decorator.js");

/**
 * Decorator for methods {@link ExoButton.setChecked} and {@link ExoButton.getChecked}<br/>
 * Decorator purpose: to initialize the buttons before they will be checked
 * @param btn element to decorate
 * @constructor
 */
function ExoButtonDecorator(btn) {
    this.btn = btn;
    this.callbackStack = [];

    this.doPressOnOptionsBtn = function() {
        console.log("ExoButtonDecorator: clicking on options button");
        EventUtils.triggerEnter(ExoConstants.optionsBtnSelector);
    };

    this.doCallbackIfReady = function(callback) {
        var $this = this;
        var timeout = 1000; // timeout until Options show on/off
        setTimeout(function() {
            callback();
            $this.callbackStack.shift();
            if ($this.callbackStack[0]) {
                $this.callbackStack[0]();
            }
        }, timeout);
    };

    this.setCheckedWrapper = function(callback) {
        var obj = Utils.$(this.btn.selector);
        if (!obj || !obj.children.length) {
            this.doPressOnOptionsBtn();
            console.log('YouButtonDecorator.initBtn: btn not initialized: ' + this.btn.selector);
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
        var obj = Utils.$(this.btn.selector);
        if (!obj || !obj.children.length) {
            console.log('YouButtonDecorator.initBtn2: btn not initialized: ' + this.btn.selector);
            this.doPressOnOptionsBtn();
        }
        return callback();
    };

    this.apply = function() {
        if (this.btn.applyOnGetCheckedOnly) {
            this.applyGetChecked();
            return;
        }

        if (this.btn.applyOnSetCheckedOnly) {
            this.applySetChecked();
            return;
        }

        this.applySetChecked();
        this.applyGetChecked();
    };

    this.applySetChecked = function() {
        var $this = this;

        var realSetChecked = this.btn.setChecked;
        this.btn.setChecked = function(doChecked) {
            // console.log('YouButtonDecorator: setChecked wrapper called: ' + $this.btn.selector);
            var thisBtn = this;
            var callback = function() {
                $this.setCheckedWrapper(function() {
                    // console.log("YouButtonDecorator: calling real setChecked for: " + $this.btn.selector);
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
            // console.log('YouButtonDecorator: getChecked wrapper called: ' + $this.btn.selector);
            var thisBtn = this;
            return $this.getCheckedWrapper(function() {
                // console.log("YouButtonDecorator: calling real getChecked for: " + $this.btn.selector);
                return realGetChecked.call(thisBtn);
            });
        };
    };
}