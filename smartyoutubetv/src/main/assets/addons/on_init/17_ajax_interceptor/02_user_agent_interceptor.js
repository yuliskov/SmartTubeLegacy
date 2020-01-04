/**
 * Description:
 * Intercepts user agent headers
 */

console.log("Scripts::Running script uaer_agent_interceptor.js");

function UserAgentInterceptor() {
    this.TAG = 'UserAgentInterceptor';
    this.USER_AGENT_HEADER = 'User-Agent';
    this.CHROME_USER_AGENT = 'Mozilla/5.0 (SMART-TV; X11; Linux armv7l) AppleWebKit/537.42 (KHTML, like Gecko) Chromium/25.0.1349.2 Chrome/25.0.1349.2 Safari/537.42';
    this.shouldIntercept = true;

    this.interceptHeader = function(name, value) {
        if (name == this.USER_AGENT_HEADER) {
            this.value = value;
        }
    };
}
