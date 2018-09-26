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
            return Utils.$(YouTubeConstants.SEARCH_FIELD_SELECTOR);
        };

        var callback = function() {
            var searchInput = Utils.$(YouTubeConstants.SEARCH_FIELD_SELECTOR);
            EventUtils.triggerEvent(searchInput, EventTypes.KEY_UP, KeyCodes.UP);
        };

        Utils.delayTillTestFnSuccess(callback, testFn);
    };
}

new EnableExternalKeyboardAddon().run();