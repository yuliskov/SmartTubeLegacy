/**
 * Description:
 * Intercepts post data to be used in the MainInterceptor.java
 */

console.log("Scripts::Running script post_data_interceptor.js");

function PostDataInterceptor() {
    this.TAG = 'PostDataInterceptor';
    this.MESSAGE_POST_DATA = 'message_post_data';

    this.interceptBody = function(body) {
        DeviceUtils.sendMessage(this.MESSAGE_POST_DATA, body);
    };
}
