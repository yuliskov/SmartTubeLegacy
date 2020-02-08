/**
 * Description:<br/>
 * Intercepts signs of large ad banner<br/>
 * Intercepts post data to be used in the MainInterceptor.java
 */

console.log("Scripts::Running script integrated_ad_interceptor.js");

function IntegratedAdInterceptor() {
    this.TAG = 'IntegratedAdInterceptor';
    this.MESSAGE_POST_DATA = 'message_post_data';
    this.url = '';
    this.testUrl = '/youtubei/v1/browse';
    this.testPostData = '"browseId":"default"';

    this.interceptOpen = function(method, url, async) {
        this.url = url;
    };

    this.interceptBody = function(body) {
        var isAd = this.url.indexOf(this.testUrl) >= 0;

        if (isAd) {
            var isFirstPage = body && body.indexOf(this.testPostData) >= 0;

            if (isFirstPage) {
                DeviceUtils.sendMessage(this.MESSAGE_POST_DATA, body);
            }
        }
    };
}
