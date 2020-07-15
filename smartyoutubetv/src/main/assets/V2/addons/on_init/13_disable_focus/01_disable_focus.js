/**
 * Description:
 * Disable video pause on focus lost
 */

console.log("Scripts::Running script disable_focus.js");

/**
 * WARNING: Don't enable. location.hash won't work after that.
 * @constructor
 */
function DisableFocusAddon() {
    this.TAG = 'FocusOverrideAddon';

    this.run = function() {
        this.disableWindowFocus();
        this.disableDocumentFocus();
    };

    this.disableWindowFocus = function() {
        EventUtils.disableEvents(window, ['focus', 'blur']);
    };

    this.disableDocumentFocus = function() {
        var visibilityEvent = (!document.visibilityState && document.webkitVisibilityState) ? 'webkitvisibilitychange' : 'visibilitychange';

        EventUtils.disableEvents(document, visibilityEvent);
    };
}

// if (DeviceUtils.isExo()) {
//     new DisableFocusAddon().run();
// }
