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

UiDataButton.prototype = new UiButton();

function UiSelectorButton(selector) {
    this.TAG = 'UiSelectorButton';
    this.selector = selector;
    this.getElem = function() {
        this.cachedElement = Utils.$(selector);
        return this.cachedElement;
    }
}

UiSelectorButton.prototype = new UiButton();

UiButton.fromData = function(buttonData) {
    return new UiDataButton(buttonData);
};

UiButton.fromSelector = function(selector) {
    return new UiSelectorButton(selector);
};
