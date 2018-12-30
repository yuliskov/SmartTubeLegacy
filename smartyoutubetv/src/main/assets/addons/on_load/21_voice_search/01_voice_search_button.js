/**
 * Description:
 * Codec selector button that placed inside player's ui.
 */

console.log("Scripts::Running script voice_search_button.js");

function VoiceSearchButton() {
    this.TAG = 'VoiceSearchButton';
    this.MIC_CLICKED_MESSAGE = 'mic_clicked_message';

    this.onClick = function() {
        Log.d(this.TAG, "user have clicked on the voice search button");
        DeviceUtils.sendMessage(this.MIC_CLICKED_MESSAGE);
    };
}
