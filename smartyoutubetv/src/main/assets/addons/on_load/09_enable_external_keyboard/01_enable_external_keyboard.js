/**
 * Description:
 * Enable Soft Keyboard support.
 * In order to keyboard to appear you must go to the search page and them move selection to the input area.
 */

console.log("Scripts::Running script enable_external_keyboard.js");

function EnableExternalKeyboardAddon() {
    this.TAG = 'EnableExternalKeyboardAddon';
    this.run = function() {
        this.enableExternalKeyboard2();
    };

    this.enableExternalKeyboard2 = function() {
        var $this = this;

        var handler = function(e) {
            if (Utils.hasClass(e.target, YouTubeClasses.NO_MODEL)) {
                return;
            }

            $this.enableSearchInputField();

            EventUtils.removeListener(
                YouTubeSelectors.SEARCH_PAGE,
                YouTubeEvents.MODEL_CHANGED_EVENT,
                handler);
        };

        EventUtils.addListener(
            YouTubeSelectors.SEARCH_PAGE,
            YouTubeEvents.MODEL_CHANGED_EVENT,
            handler);
    };

    /**
     * Imitates click on the input field
     */
    this.enableSearchInputField = function() {
        var searchInput = Utils.$(YouTubeConstants.SEARCH_FIELD_SELECTOR);
        EventUtils.triggerEvent(searchInput, DefaultEvents.KEY_UP, DefaultKeys.UP);
    };
}

new EnableExternalKeyboardAddon().run();