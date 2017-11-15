function Helpers() {
    function isSelector(el) {
        return typeof el === 'string' || el instanceof String;
    }

    this.triggerEvent = function(el, type, keyCode) {
        if (isSelector(el)) {
            el = this.$(el);
        }

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
    };
    
    this.skipLastHistoryItem = function() {
        console.log('running skipLastHistoryItem');
        var $this = this;
        var listener = function(e) {
            window.removeEventListener('popstate', listener);
            console.log('running on popstate event');
            // e.state is equal to the data-attribute of the last image we clicked
            // window.history.go(-1);
            // window.location.href = "/tv"
            setTimeout(function() {
                $this.triggerEnter(".back.no-model.legend-item");
            }, 3000);
        };
        window.addEventListener('popstate', listener);
    }
}

window.helpers = new Helpers();

// Usage: PressCommandBase.java
// helpers.triggerEvent(helpers.$('%s'), 'keyup', 13);

// Usage: PressCommandBase.java
// helpers.isDisabled(targetButton) && app && app.onGenericBooleanResult(false, %s);
