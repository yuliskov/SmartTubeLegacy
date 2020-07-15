/**
 * Description:
 * Intercepts user agent headers
 */

console.log("Scripts::Running script uaer_agent_interceptor.js");

function UserAgentInterceptor() {
    this.TAG = 'UserAgentInterceptor';
    this.USER_AGENT_HEADER = 'user-agent';
    this.BROWSE_URL = '/youtubei/v1/browse';
    this.COBALT_USER_AGENT = 'Mozilla/5.0 (DirectFB; Linux x86_64) Cobalt/40.13031-qa (unlike Gecko) Starboard/1';
    this.shouldIntercept = true;

    this.interceptOpen = function(method, url, async) {
        Log.d(this.TAG, "Open: " + url);
        this.isBrowse = url.endsWith(this.BROWSE_URL);
    };

    this.modifyHeader = function(args) {
        if (args.length == 2) {
            var name = args[0];

            Log.d(this.TAG, "Header: " + args[0]);

            if (this.isBrowse && name && (name.toLowerCase() == this.USER_AGENT_HEADER)) {
                Log.d(this.TAG, "Browse request found");
                args[1] = this.COBALT_USER_AGENT;
            }
        }
    };
}
