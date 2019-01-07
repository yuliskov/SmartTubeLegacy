/**
 * Description:
 * Enable Soft Keyboard support.
 * In order to keyboard to appear you must go to the search page and them move selection to the input area.
 */

console.log("Scripts::Running script enable_external_keyboard.js");

function EnableExternalKeyboardAddon() {
    this.TAG = 'EnableExternalKeyboardAddon';
    this.run = function() {
        this.enableExternalKeyboard3();
    };

    this.enableExternalKeyboard2 = function() {
        var $this = this;

        var handler = function(e) {
            Log.d($this.TAG, "Search page changed");

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

    this.enableExternalKeyboard3 = function() {
        var $this = this;

        var handler = function(e) {
            Log.d($this.TAG, "Search page keyboard focused");

            $this.enableSearchInputField();

            EventUtils.removeListener(
                YouTubeSelectors.SEARCH_KEYBOARD,
                DefaultEvents.KEY_UP,
                handler);
        };

        EventUtils.addListener(
            YouTubeSelectors.SEARCH_KEYBOARD,
            DefaultEvents.KEY_UP,
            handler);
    };

    /**
     * Imitates click on the input field
     */
    this.enableSearchInputField = function() {
        Log.d(this.TAG, "Trying to enable input field on search page");
        var searchInput = Utils.$(YouTubeConstants.SEARCH_FIELD_SELECTOR);
        EventUtils.triggerEvent(searchInput, DefaultEvents.KEY_UP, null);
    };
}

new EnableExternalKeyboardAddon().run();