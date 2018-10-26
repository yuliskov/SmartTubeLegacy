// NOTE: this file doesn't depend on common js files

console.log("Scripts::Running core script exo_button_decorator.js");

/**
 * Decorator for methods {@link YouButton.setChecked} and {@link YouButton.getChecked}<br/>
 * Decorator purpose: to initialize the buttons before they will be checked
 * @param btn element to decorate
 * @constructor
 */
function YouButtonDecorator(btn) {
    this.btn = btn;
    this.callbackStack = YouButtonDecorator.callbackStack;

    this.doPressOnOptionsBtn = function() {
        exoutils.triggerEnter(this.optionsBtnSelector);
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
        var obj = exoutils.$(this.btn.selector);
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
        var obj = exoutils.$(this.btn.selector);
        if (!obj || !obj.children.length) {
            console.log('YouButtonDecorator.initBtn2: btn not initialized: ' + this.btn.selector);
            this.doPressOnOptionsBtn();
        }
        return callback();
    };

    this.apply = function() {
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

YouButtonDecorator.prototype = new ExoConstants();
YouButtonDecorator.callbackStack = [];