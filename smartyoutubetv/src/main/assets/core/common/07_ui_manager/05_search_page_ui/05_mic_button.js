/**
 * Real button wrapper around supplied button's data
 */

console.log("Scripts::Running script mic_button.js");

function MicDataButton(buttonData) {
    this.TAG = 'MicDataButton';
    this.data = buttonData;
    this.data.wrapper = this;
    this.markup =
        '<div role="button" id="microphone" class="mic-button icon-button" tabindex="-1" aria-label="Microphone">' +
        '<span class="icon icon-mic"></span>' +
        '<span class="label"></span>' +
        '</div>';

    /**
     * Get DOM element
     */
    this.getElem = function() {
        var $this = this;
        if (this.cachedElement == null) {
            this.cachedElement = UiHelpers.createElement(
                this.markup
            );

            EventUtils.addListener(this.cachedElement, DefaultEvents.ON_CLICK, function(e) {
                Log.d($this.TAG, "mic button clicked");

                if ($this.data.onClick) {
                    $this.data.onClick();
                }
            });
        }
        return this.cachedElement;
    };

    this.getId = function() {
        return 'microphone';
    };
}

MicDataButton.prototype = new UiButton();

UiButton.createMicButton = function(buttonData) {
    return new MicDataButton(buttonData);
};
