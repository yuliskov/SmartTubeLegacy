/**
 * Real button wrapper around supplied button's data
 */

console.log("Scripts::Running script ui_button.js");

function UiButton() {
    this.focus = function() {
        YouTubeUtils.focus(this.getElem());
    };

    this.unfocus = function() {
        YouTubeUtils.unfocus(this.getElem());
    };
}

function UiSelectorButton(selector) {
    this.TAG = 'UiSelectorButton';
    this.selector = selector;
    this.getElem = function() {
        this.cachedElement = Utils.$(selector);
        return this.cachedElement;
    }
}

UiSelectorButton.prototype = new UiButton();

UiButton.fromSelector = function(selector) {
    return new UiSelectorButton(selector);
};
