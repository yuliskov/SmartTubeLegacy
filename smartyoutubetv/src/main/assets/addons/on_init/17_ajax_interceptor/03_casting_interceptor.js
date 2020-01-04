/**
 * Description:
 * Intercepts casting commands (play, pause, rewind)
 */

console.log("Scripts::Running script casting_interceptor.js");

function CastingInterceptor() {
    this.TAG = 'CastingInterceptor';
    this.url = '';

    this.interceptOpen = function(method, url, async) {
        this.url = url;
    };

    this.interceptBody = function(body) {
        if (this.url.indexOf("LOUNGE") >= 0) {
            Log.d(this.TAG, this.url + ", " + body);
        }
    };
}
