/**
 * Description:
 * Intercepts Authorization headers
 */

console.log("Scripts::Running script auth_interceptor.js");

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
            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_AUTH_BODY, content);
        }
    };
}
