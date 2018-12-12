/**
 * Real button wrapper around supplied button's data
 */

console.log("Scripts::Running script ui_button.js");

function UiButton() {
    this.focus = function() {
        Utils.ytFocus(this.getElem());
        this.getElem().focus();
    };

    this.unfocus = function() {
        Utils.ytUnfocus(this.getElem());
    };
}

function UiDataButton(buttonData) {
    this.TAG = 'UiDataButton';
    this.data = buttonData;
    this.markup =
        '<div class="transport-controls-button" tabindex="-1" role="button" style="left: 15rem; position: absolute; width: initial">' +
        '<div class="background" style="width: 100%; border-radius: 0;"></div>' +
        '<span style="padding: 10px">%TITLE%</span>' +
        '</div>';

    /**
     * Get DOM element
     */
    this.getElem = function() {
        var $this = this;
        if (this.elem == null) {
            this.elem = UiHelpers.createElement(
                this.markup.replace('%TITLE%', this.data.getTitle()));

            EventUtils.addListener(this.elem, DefaultEvents.ON_CLICK, function(e) {
                if ($this.data.onClick) {
                    $this.data.onClick();
                }
            });
        }
        return this.elem;
    };

    this.getId = function() {
        return 'codec-button';
    };
}

UiDataButton.prototype = new UiButton();

function UiSelectorButton(selector) {
    this.TAG = 'UiSelectorButton';
    this.selector = selector;
    this.getElem = function() {
        return Utils.$(selector);
    }
}

UiSelectorButton.prototype = new UiButton();

UiButton.fromData = function(buttonData) {
    return new UiDataButton(buttonData);
};

UiButton.fromSelector = function(selector) {
    return new UiSelectorButton(selector);
};
