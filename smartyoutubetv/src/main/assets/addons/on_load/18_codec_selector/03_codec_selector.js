/**
 * Description:
 * Adds codec selector button to the 1080 launchers.
 */

console.log("Scripts::Running script codec_selector.js");

function CodecSelectorAddon() {
    this.TAG = 'CodecSelectorAddon';

    this.run = function() {
        var btn = new CodecSelectorButton();
        PlayerUiManager.insertButton(btn);
    }
}

// if (!DeviceUtils.isExo())
//     new CodecSelectorAddon().run();


