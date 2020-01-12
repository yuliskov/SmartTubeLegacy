/**
 * Description:
 * Enable Soft Keyboard support hook.
 * Main point to run before being blocked.
 */

console.log("Scripts::Running script enable_external_keyboard_hook.js");

function EnableExternalKeyboardHookAddon() {
    this.TAG = 'EnableExternalKeyboardHookAddon';

    this.run = function() {
        this.hideKeyboardOnSubmit();
    };

    this.hideKeyboardOnSubmit = function() {
        var $this = this;

        // There is a handler that blocks others. We should run before it.
        // Note: running on capture phase
        EventUtils.addListener(document, DefaultEvents.KEY_DOWN, function(e) {
            Log.d($this.TAG, "User pressed down " + e.keyCode);

            if (e.keyCode == DefaultKeys.TAB) {
                Log.d($this.TAG, "User pressed ENTER/NEXT on soft keyboard");
                // move focus out of input field
                // by clicking on 'search' button
                EventUtils.triggerEnter(YouTubeSelectors.SEARCH_START_BUTTON);
            }
        }, true);
    };
}

new EnableExternalKeyboardHookAddon().run();