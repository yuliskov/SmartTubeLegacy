/**
 * Real button wrapper around supplied button's data
 */

console.log("Scripts::Running script player_button.js");

function PlayerDataButton(buttonData) {
    this.TAG = 'PlayerDataButton';
    this.data = buttonData;
    this.markup =
        '<div class="%ICON_CLASS% toggle-button transport-controls-toggle-button" ' +
        'tabindex="-1" role="button" style="left: 15rem; position: absolute;">' +
        '<div class="background"></div>' +
        '<span class="label">%TITLE%</span>' +
        '</div>';

    /**
     * Get DOM element
     */
    this.getElem = function() {
        var $this = this;
        if (this.cachedElement == null) {
            this.cachedElement = UiHelpers.createElement(
                this.markup
                    .replace('%TITLE%', this.data.getTitle())
                    .replace('%ICON_CLASS%', this.data.getIconClass())
            );

            EventUtils.addListener(this.cachedElement, DefaultEvents.ON_CLICK, function(e) {
                if ($this.data.onClick) {
                    $this.data.onClick();
                }
            });
        }
        return this.cachedElement;
    };

    this.getId = function() {
        return 'codec-button';
    };
}

PlayerDataButton.prototype = new UiButton();

UiButton.createPlayerButton = function(buttonData) {
    return new PlayerDataButton(buttonData);
};
