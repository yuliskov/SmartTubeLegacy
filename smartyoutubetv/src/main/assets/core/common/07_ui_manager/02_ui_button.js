/**
 * Real button wrapper around supplied button's data
 */

console.log("Scripts::Running script ui_button.js");

function UiButton(buttonData) {
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

UiButton.fromData = function(buttonData) {

};

UiButton.fromSelector = function(selector) {

};
