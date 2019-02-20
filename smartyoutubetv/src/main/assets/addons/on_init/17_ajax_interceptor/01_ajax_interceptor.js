/**
 * Description:
 * Intercepts Authorization headers
 */

console.log("Scripts::Running script ajax_interceptor.js");

function AuthInterceptor() {
    this.TAG = 'AuthInterceptor';
    this.AUTHORIZATION_HEADER = 'Authorization';
    this.shouldIntercept = DeviceUtils.isExo();

    this.intercept = function(name, value) {
        if (name == this.AUTHORIZATION_HEADER) {
            Log.d(this.TAG, "Found auth header: " + value);

            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_AUTHORIZATION_HEADER, value);

            // stop override, we've found what we looking for
            this.shouldIntercept = false;
        }

        return value;
    };
}

function UserAgentInterceptor() {
    this.TAG = 'UserAgentInterceptor';
    this.USER_AGENT_HEADER = 'User-Agent';
    this.CHROME_USER_AGENT = 'Mozilla/5.0 (SMART-TV; X11; Linux armv7l) AppleWebKit/537.42 (KHTML, like Gecko) Chromium/25.0.1349.2 Chrome/25.0.1349.2 Safari/537.42';
    this.shouldIntercept = true;

    this.intercept = function(name, value) {
        if (name == this.USER_AGENT_HEADER) {
            value = this.CHROME_USER_AGENT;
        }

        return value;
    };
}

function AjaxInterceptorAddon() {
    this.TAG = 'AjaxInterceptorAddon';
    this.interceptors = [new AuthInterceptor()];

    this.run = function() {
        this.overrideSetHeaders();
    };

    this.overrideSetHeaders = function() {
        if (!window.XMLHttpRequest) {
            Log.e(this.TAG, "Can't override: XMLHttpRequest isn't exist");
            return;
        }

        var $this = this;

        var origin = window.XMLHttpRequest.prototype.setRequestHeader;

        window.XMLHttpRequest.prototype.setRequestHeader = function() {
            if (arguments.length == 2) {
                var shouldIntercept = false;

                Log.d($this.TAG, "Found header: " + arguments[0] + ' ' + arguments[1]);

                for (var i = 0; i < $this.interceptors.length; i++) {
                    var interceptor = $this.interceptors[i];
                    if (interceptor.shouldIntercept) {
                        arguments[1] = interceptor.intercept(arguments[0], arguments[1]);
                        shouldIntercept = shouldIntercept ? true : interceptor.shouldIntercept;
                    }
                }

                if (!shouldIntercept) {
                    // stop override, we've found what we looking for
                    window.XMLHttpRequest.prototype.setRequestHeader = origin;
                }
            }

            origin.apply(this, arguments);
        };
    };
}

new AjaxInterceptorAddon().run();
