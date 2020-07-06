/**
 * Get cookie as object
 */

console.log("Scripts::Running script cookie_manager.js");

var CookieManager = {
    getRawCookie: function(name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        if (parts.length == 2) return parts.pop().split(";").shift();
    },

    getQueryCookie: function(name) {
        var raw = this.getRawCookie(name);
        return new this.QueryCookie(raw);
    },

    setQueryCookie: function(name, values, domain) {
        if (!domain) {
            domain = '.youtube.com';
        }

        var resultValue = '';

        for (var key in values) {
            if (key == 'raw') {
                continue;
            }

            resultValue += key + '=' + values[key] + ';';
        }

        document.cookie = name + '=' + resultValue  + '; domain=' + domain;
    },

    deleteCookie: function(name, domain) {
        if (!domain) {
            domain = '.youtube.com';
        }

        document.cookie = name + '=; domain=' + domain + '; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    },

    QueryCookie: function(raw) {
        this.raw = raw;

        if (!this.raw) {
            return;
        }

        var params = this.raw.split('&');

        for (var i = 0; i < params.length; i++) {
            var param = params[i];
            var keyVal = param.split('=');
            this[keyVal[0]] = keyVal[1];
        }
    }
};
