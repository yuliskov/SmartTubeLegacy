/**
 * Description:
 * Intercepts Authorization headers
 */

console.log("Scripts::Running script auth_interceptor.js");

function AuthInterceptor() {
    this.TAG = 'AuthInterceptor';
    this.AUTHORIZATION_HEADER = 'authorization';
    this.VISITOR_ID_HEADER = 'x-goog-visitor-id';

    this.interceptHeader = function(name, value) {
        if (name.toLowerCase() == this.AUTHORIZATION_HEADER && this.prevValue != value) {
            Log.d(this.TAG, "Found auth header: " + value);

            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_AUTHORIZATION_HEADER, value);
            this.prevValue = value;
        } else if (name.toLowerCase() == this.VISITOR_ID_HEADER && this.prevVisitorValue != value) {
            Log.d(this.TAG, "Found visitor header: " + value);

            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_VISITOR_ID_HEADER, value);
            this.prevVisitorValue = value;
        }
    };

    this.interceptBody = function(content) {
        if (content != null && content.indexOf('googleusercontent.com') != -1) {
            Log.d(this.TAG, "Found auth body: " + content);
            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_AUTH_BODY, content);
        }
    };
}
