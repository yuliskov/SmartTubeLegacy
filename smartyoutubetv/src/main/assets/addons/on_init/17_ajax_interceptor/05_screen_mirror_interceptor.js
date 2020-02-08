/**
 * Description:<br/>
 * Intercepts signs of mirroring turned on<br/>
 * Intercepts post data to be used in the MainInterceptor.java
 */

console.log("Scripts::Running script screen_mirror_interceptor.js");

function ScreenMirrorInterceptor() {
    this.TAG = 'ScreenMirrorInterceptor';
    this.MESSAGE_POST_DATA = 'message_post_data';
    this.url = '';
    this.testUrl = '/api/lounge/bc/bind?device=LOUNGE_SCREEN';

    this.interceptOpen = function(method, url, async) {
        this.url = url;
    };

    this.interceptBody = function(body) {
        var isMirror = this.url.indexOf(this.testUrl) >= 0;

        if (isMirror) {
            DeviceUtils.sendMessage(this.MESSAGE_POST_DATA, body);
        }
    };
}
