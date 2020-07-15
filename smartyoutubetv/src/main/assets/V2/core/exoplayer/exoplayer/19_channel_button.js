console.log("Scripts::Running core script channel_button.js");

/**
 * Any button that could open an overlay like favorites, user channel etc
 * @param selector btn selector
 * @constructor
 */
function ChannelButton(selector) {
    this.TAG = "ChannelButton";
    this.selector = selector;
    this.stateless = true;

    this.isOverlayOpened = function() {
        return YouTubeUtils.isChannelOpened();
    };

    this.decorator.apply(this);
}

ChannelButton.prototype = new OverlayButton();