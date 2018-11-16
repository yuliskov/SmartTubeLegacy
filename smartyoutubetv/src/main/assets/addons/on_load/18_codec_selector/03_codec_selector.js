/**
 * Description:
 * Adds codec selector button to the 1080 launchers.
 */

console.log("Scripts::Running script codec_selector.js");

function CodecSelectorAddon() {
    this.TAG = 'CodecSelectorAddon';

    this.run = function() {
        if (DeviceUtils.isExo())
            return;

        var btn = new CodecSelectorButton();
        UiManager.addToPlayer(btn);
    }
}

new CodecSelectorAddon().run();


