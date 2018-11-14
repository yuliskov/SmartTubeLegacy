/**
 * Description:
 * Codec selector button that placed inside player's ui.
 */

console.log("Scripts::Running script codec_selector_button.js");

function CodecSelectorButton() {
    this.TAG = 'CodecSelectorButton';

    this.onClick = function() {
        Log.d(this.TAG, "user have clicked on the button");
    };
}
