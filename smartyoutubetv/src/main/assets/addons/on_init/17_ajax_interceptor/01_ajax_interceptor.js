/**
 * Description:
 * Intercepts Authorization headers
 */

console.log("Scripts::Running script ajax_interceptor.js");

function AuthInterceptor() {
    this.TAG = 'AuthInterceptor';
    this.AUTHORIZATION_HEADER = 'Authorization';

    this.interceptHeader = function(name, value) {
        if (name == this.AUTHORIZATION_HEADER && this.prevValue != value) {
            Log.d(this.TAG, "Found auth header: " + value);

            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_AUTHORIZATION_HEADER, value);
            this.prevValue = value;
        }
    };

    this.interceptBody = function(content) {
        if (content != null && content.indexOf('googleusercontent.com') != -1) {
            Log.d(this.TAG, "Found auth body: " + content);
        }
    };
}

// function UserAgentInterceptor() {
//     this.TAG = 'UserAgentInterceptor';
//     this.USER_AGENT_HEADER = 'User-Agent';
//     this.CHROME_USER_AGENT = 'Mozilla/5.0 (SMART-TV; X11; Linux armv7l) AppleWebKit/537.42 (KHTML, like Gecko) Chromium/25.0.1349.2 Chrome/25.0.1349.2 Safari/537.42';
//     this.shouldIntercept = true;
//
//     this.intercept = function(name, value) {
//         if (name == this.USER_AGENT_HEADER) {
//             value = this.CHROME_USER_AGENT;
//         }
//
//         return value;
//     };
// }

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

        this.setupHeaderInterceptors();

        this.setupBodyInterceptors();
    };

    this.setupHeaderInterceptors = function() {
        var $this = this;

        var origin = window.XMLHttpRequest.prototype.setRequestHeader;

        window.XMLHttpRequest.prototype.setRequestHeader = function() {
            if (arguments.length == 2) {
                // Log.d($this.TAG, "Found header: " + arguments[0] + ' ' + arguments[1]);

                for (var i = 0; i < $this.interceptors.length; i++) {
                    var interceptor = $this.interceptors[i];
                    if (interceptor.interceptHeader) {
                        interceptor.interceptHeader(arguments[0], arguments[1]);
                    }
                }

                if ($this.interceptors.length == 0) {
                    // stop override, no interceptors left
                    window.XMLHttpRequest.prototype.setRequestHeader = origin;
                }
            }

            origin.apply(this, arguments);
        };
    };

    this.setupBodyInterceptors = function() {
        var $this = this;

        var origin = window.XMLHttpRequest.prototype.send;

        window.XMLHttpRequest.prototype.send = function() {
            if (arguments.length == 1) {
                // Log.d($this.TAG, "Found header: " + arguments[0] + ' ' + arguments[1]);

                for (var i = 0; i < $this.interceptors.length; i++) {
                    var interceptor = $this.interceptors[i];
                    if (interceptor.interceptBody) {
                        interceptor.interceptBody(arguments[0]);
                    }
                }

                if ($this.interceptors.length == 0) {
                    // stop override, no interceptors left
                    window.XMLHttpRequest.prototype.send = origin;
                }
            }

            origin.apply(this, arguments);
        };
    };
}

if (DeviceUtils.isExo()) {
    new AjaxInterceptorAddon().run();
}
