/**
 * Description:
 * Disable video pause on focus lost
 */

console.log("Scripts::Running script disable_focus.js");

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
        var vName = (!document.visibilityState && document.webkitVisibilityState) ? 'webkitvisibilitychange' : 'visibilitychange';

        EventUtils.disableEvents(document, vName);
    };
}

// if (DeviceUtils.isExo()) {
//     new DisableFocusAddon().run();
// }
