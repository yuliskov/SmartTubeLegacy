/**
 * Description:
 * Intercepts Authorization headers
 */

console.log("Scripts::Running script ajax_interceptor.js");

function AjaxInterceptorAddon(interceptors) {
    this.TAG = 'AjaxInterceptorAddon';
    this.interceptors = interceptors ? interceptors : [];

    this.run = function() {
        this.bindInterceptors();
    };

    this.bindInterceptors = function() {
        if (!window.XMLHttpRequest) {
            Log.e(this.TAG, "Can't override: XMLHttpRequest isn't exist");
            return;
        }

        this.setupOpenInterceptors();

        this.setupHeaderInterceptors();

        this.setupBodyInterceptors();
    };

    this.setupOpenInterceptors = function() {
        var $this = this;

        var origin = window.XMLHttpRequest.prototype.open;

        window.XMLHttpRequest.prototype.open = function() {
            if (arguments.length == 3) {
                for (var i = 0; i < $this.interceptors.length; i++) {
                    var interceptor = $this.interceptors[i];
                    if (interceptor.interceptOpen) {
                        // method, url, async
                        interceptor.interceptOpen(arguments[0], arguments[1], arguments[2]);
                    }

                    if (interceptor.modifyOpen) {
                        // method, url, async
                        interceptor.modifyOpen(arguments);
                    }
                }

                if ($this.interceptors.length == 0) {
                    // stop override, no interceptors left
                    window.XMLHttpRequest.prototype.open = origin;
                }
            }

            origin.apply(this, arguments);
        };
    };

    this.setupHeaderInterceptors = function() {
        var $this = this;

        var origin = window.XMLHttpRequest.prototype.setRequestHeader;

        window.XMLHttpRequest.prototype.setRequestHeader = function() {
            if (arguments.length == 2) {
                for (var i = 0; i < $this.interceptors.length; i++) {
                    var interceptor = $this.interceptors[i];
                    if (interceptor.interceptHeader) {
                        // name, value
                        interceptor.interceptHeader(arguments[0], arguments[1]);
                    }

                    if (interceptor.modifyHeader) {
                        // name, value
                        interceptor.modifyHeader(arguments);
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

new AjaxInterceptorAddon([new AuthInterceptor(), new PostDataInterceptor(), new VideoInfoInterceptor()]).run();
