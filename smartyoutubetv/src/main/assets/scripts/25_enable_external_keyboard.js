/**
 * Description:
 * Enable Soft Keyboard support.
 * In order to keyboard to appear you must go to the search page and them move selection to the input area.
 */

console.log("Scripts::Running script enable_external_keyboard.js");

function EnableExternalKeyboardAddon() {
    this.run = function() {
        this.enableExternalKeyboard();
    };

    /**
     * Imitate click on the input field
     */
    this.enableExternalKeyboard = function() {
        var testFn = function() {
            return Utils.$(Utils.searchSelector);
        };

        var callback = function() {
            var searchInput = Utils.$(Utils.searchSelector);
            Utils.triggerEvent(searchInput, KeyEvents.KEYUP, KeyCodes.UP);
        };

        Utils.delayTillElementBeInitialized(callback, testFn);
    };
}

new EnableExternalKeyboardAddon().run();