/**
 * Description:
 * Intercepts Authorization headers
 */

console.log("Scripts::Running script auth_interceptor.js");

function AuthInterceptor() {
    this.TAG = 'AuthInterceptor';
    this.AUTHORIZATION_HEADER = 'authorization';
    this.VISITOR_ID_HEADER = 'x-goog-visitor-id';
    this.CLIENT_DATA_HEADER = 'x-client-data';
    this.MESSAGE_AUTHORIZATION_HEADER = 'message_authorization_header';
    this.MESSAGE_VISITOR_ID_HEADER = 'message_visitor_id_header';
    this.MESSAGE_CLIENT_DATA_HEADER = "message_client_data_header";
    this.MESSAGE_AUTH_BODY = 'message_auth_body';

    this.initMapping = function() {
        this.mapping = {};
        this.mapping[this.AUTHORIZATION_HEADER] = this.MESSAGE_AUTHORIZATION_HEADER;
        this.mapping[this.VISITOR_ID_HEADER] = this.MESSAGE_VISITOR_ID_HEADER;
        this.mapping[this.CLIENT_DATA_HEADER] = this.MESSAGE_CLIENT_DATA_HEADER;

        this.prevMapping = {};
    }

    this.interceptHeader = function(name, value) {
        var simpleName = name.toLowerCase();
        var messageId = this.mapping[simpleName];

        if (messageId) { // header found
            var prevVal = this.prevMapping[simpleName];

            if (prevVal != value) {
                Log.d(this.TAG, "Found header: " + simpleName + " with value: " + value);

                DeviceUtils.sendMessage(messageId, value);
                this.prevMapping[simpleName] = value;
            }
        }
    };

    this.interceptBody = function(content) {
        if (content != null && content.indexOf('googleusercontent.com') != -1) {
            Log.d(this.TAG, "Found auth body: " + content);
            DeviceUtils.sendMessage(this.MESSAGE_AUTH_BODY, content);
        }
    };

    this.initMapping();
}
