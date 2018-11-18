/**
 * Real button wrapper around supplied button's data
 */

console.log("Scripts::Running script ui_button.js");

function UiButton() {
}

function UiDataButton(buttonData) {
    this.TAG = 'UiDataButton';
    this.data = buttonData;
    this.markup =
        '<div class="transport-controls-button" tabindex="-1" role="button" style="left: 15rem; position: absolute; width: initial">' +
        '<span>%TITLE%</span>' +
        '</div>';

    /**
     * Get DOM element
     */
    this.getElem = function() {
        return UiHelpers.createElement(
            this.markup.replace('%TITLE%', this.data.getTitle()));
    };

    this.getId = function() {
        return 'codec-button';
    };
}

function UiSelectorButton(selector) {
    this.TAG = 'UiSelectorButton';
    this.selector = selector;
}

UiButton.fromData = function(buttonData) {
    return new UiDataButton(buttonData);
};

UiButton.fromSelector = function(selector) {
    return new UiSelectorButton(selector);
};
