function Helpers() {
    this.triggerEvent = function(el, type, keyCode) {
        console.log('triggerEvent called', el, type, keyCode);
        if ('createEvent' in document) {
            // modern browsers, IE9+
            var e = document.createEvent('HTMLEvents');
            e.keyCode = keyCode;
            e.initEvent(type, false, true);
            el.dispatchEvent(e);
        } else {
            // IE 8
            var e = document.createEventObject();
            e.keyCode = keyCode;
            e.eventType = type;
            el.fireEvent('on'+e.eventType, e);
        }
    };

    this.triggerEnter = function(el) {
        // simulate mouse/enter key press
        this.triggerEvent(el, 'keyup', 13);
    };

    this.hasClass = function(elem, klass) {
        return (" " + elem.className + " ").indexOf(" " + klass + " ") > -1;
    };

    this.isDisabled = function(elem) {
        return this.hasClass(elem, 'disabled');
    };

    this.$ = function(selector) {
        return document.querySelectorAll(selector)[0];
    }
}

window.helpers = new Helpers();

// Usage: PressCommandBase.java
// helpers.triggerEvent(helpers.$('%s'), 'keyup', 13);

// Usage: PressCommandBase.java
// helpers.isDisabled(targetButton) && app && app.onGenericBooleanResult(false, %s);
