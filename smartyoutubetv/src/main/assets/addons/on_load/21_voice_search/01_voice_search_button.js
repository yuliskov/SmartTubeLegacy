/**
 * Description:
 * Codec selector button that placed inside player's ui.
 */

console.log("Scripts::Running script voice_search_button.js");

function VoiceSearchButton() {
    this.TAG = 'VoiceSearchButton';

    this.onClick = function() {
        Log.d(this.TAG, "user have clicked on the voice search button");
        DeviceUtils.sendMessage(DeviceUtils.MESSAGE_MIC_CLICKED);
    };

    this.unfocus = function() {
        // remove focus
        if (this.wrapper) {
            this.wrapper.unfocus();
        }
    };
}
