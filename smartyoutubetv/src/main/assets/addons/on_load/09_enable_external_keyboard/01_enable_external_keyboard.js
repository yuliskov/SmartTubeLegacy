/**
 * Description:
 * Enable Soft Keyboard support.
 * In order to keyboard to appear you must go to the search page and them move selection to the input area.
 */

console.log("Scripts::Running script enable_external_keyboard.js");

function EnableExternalKeyboardAddon() {
    this.TAG = 'EnableExternalKeyboardAddon';

    this.run = function() {
        this.enableExternalKeyboard();
    };

    this.enableExternalKeyboard = function() {
        var $this = this;

        var handler = function(e) {
            Log.d($this.TAG, "Search page keyboard focused");

            $this.enableSearchInputField();
            $this.hideKeyboardOnSubmit();

            EventUtils.removeListener(
                YouTubeSelectors.SEARCH_KEYBOARD,
                DefaultEvents.KEY_UP,
                handler);
        };

        var handler2 = function(e) {
            Log.d($this.TAG, "Search page suggestions focused");

            $this.enableSearchInputField();

            EventUtils.removeListener(
                YouTubeSelectors.SEARCH_SUGGESTIONS,
                DefaultEvents.KEY_UP,
                handler2);
        };

        EventUtils.addListener(
            YouTubeSelectors.SEARCH_KEYBOARD,
            DefaultEvents.KEY_UP,
            handler);

        EventUtils.addListener(
            YouTubeSelectors.SEARCH_SUGGESTIONS,
            DefaultEvents.KEY_UP,
            handler2);
    };

    /**
     * Imitates click on the input field
     */
    this.enableSearchInputField = function() {
        if (this.initDone) {
            return;
        }

        Log.d(this.TAG, "Trying to enable input field on search page");
        var searchInput = Utils.$(YouTubeConstants.SEARCH_FIELD_SELECTOR);
        EventUtils.triggerEvent(searchInput, DefaultEvents.KEY_UP, null);

        this.initDone = true;
    };

    this.hideKeyboardOnSubmit = function() {
        EventUtils.addListener(YouTubeSelectors.SEARCH_INPUT_FIELD, DefaultEvents.KEY_UP, function(e) {
            if (e.keyCode == DefaultKeys.ENTER) {
                // move focus out of input field
                // by clicking on 'search' button
                EventUtils.triggerEnter(YouTubeSelectors.SEARCH_START_BUTTON);
            }
        });
    };
}

new EnableExternalKeyboardAddon().run();