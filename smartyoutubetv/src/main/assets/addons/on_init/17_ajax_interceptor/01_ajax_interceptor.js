/**
 * Description:
 * Intercepts Authorization headers
 */

console.log("Scripts::Running script ajax_interceptor.js");

function AjaxInterceptorAddon() {
    this.TAG = 'AjaxInterceptorAddon';
    this.AUTHORIZATION_HEADER = 'Authorization';

    this.run = function() {
        this.overrideSetHeaders();
    };

    this.overrideSetHeaders = function() {
        if (!window.XMLHttpRequest) {
            Log.e(this.TAG, "can't override: XMLHttpRequest isn't exist");
            return;
        }

        var $this = this;

        var origin = window.XMLHttpRequest.prototype.setRequestHeader;

        window.XMLHttpRequest.prototype.setRequestHeader = function() {
            if (arguments.length == 2 && arguments[0] == $this.AUTHORIZATION_HEADER) {
                Log.d($this.TAG, "Found header: " + arguments[1]);

                DeviceUtils.sendMessage(DeviceUtils.MESSAGE_AUTHORIZATION_HEADER, arguments[1]);

                // stop override, we've found what we looking for
                window.XMLHttpRequest.prototype.setRequestHeader = origin;
            }

            origin.apply(this, arguments);
        };
    };
}

if (DeviceUtils.isExo()) {
    new AjaxInterceptorAddon().run();
}
