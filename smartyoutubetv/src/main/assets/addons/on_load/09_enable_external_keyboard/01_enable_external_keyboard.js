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
        //this.hideKeyboardOnSubmit();
    };

    this.enableExternalKeyboard = function() {
        var $this = this;

        EventUtils.addListenerOnce(
            YouTubeSelectors.SEARCH_KEYBOARD,
            DefaultEvents.KEY_UP,
            function(e) {
                Log.d($this.TAG, "Search page keyboard focused");
                $this.applyFixes();
            });

        EventUtils.addListenerOnce(
            YouTubeSelectors.SEARCH_SUGGESTIONS,
            DefaultEvents.KEY_UP,
            function(e) {
                Log.d($this.TAG, "Search page suggestions focused");
                $this.applyFixes();
            });
    };

    this.applyFixes = function() {
        if (this.fixesApplied) {
            return;
        } else {
            this.fixesApplied = true;
        }

        this.enableSearchInputField();
        this.showKeyboardOnFocus();
        this.hideKeyboardOnSubmit();
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

    /**
     * See: enable_external_keyboard_hook.js
     */
    this.hideKeyboardOnSubmit = function() {
        var $this = this;

        EventUtils.addListener(YouTubeSelectors.SEARCH_INPUT_FIELD, DefaultEvents.KEY_UP, function(e) {
            if (e.keyCode == DefaultKeys.ENTER) {
                Log.d($this.TAG, "User pressed ENTER on soft keyboard");
                // move focus out of input field
                // by clicking on 'search' button
                EventUtils.triggerEnter(YouTubeSelectors.SEARCH_START_BUTTON);
            }
        });
    };

    this.showKeyboardOnFocus = function() {
        var $this = this;

        EventUtils.addListener(YouTubeSelectors.SEARCH_INPUT_FIELD, DefaultEvents.FOCUS, function(e) {
            Log.d($this.TAG, "Search field gained focus, showing soft keyboard...");

            // not working!
            //EventUtils.triggerEnter(YouTubeSelectors.SEARCH_INPUT_FIELD);

            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_SEARCH_FIELD_FOCUSED);
        });
    };
}

new EnableExternalKeyboardAddon().run();