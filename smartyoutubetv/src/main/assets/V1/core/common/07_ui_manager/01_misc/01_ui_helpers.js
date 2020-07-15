/**
 * Ui helpers
 */

console.log("Scripts::Running script ui_helpers.js");

var UiHelpers = {
    BUTTON_OFFSET_IN_RAM: 5.5,

    convertRemToPixels: function(rem) {
        return rem * parseFloat(getComputedStyle(document.documentElement).fontSize);
    },

    getCssProperty: function(elementOrSelector, propName) {
        var elem = Utils.$(elementOrSelector);
        return getComputedStyle(elem, null).getPropertyValue(propName);
    },

    append: function(container, elem) {
        container.appendChild(elem);
    },

    createElement: function(html) {
        var div = document.createElement('div');
        div.innerHTML = html;
        return div.firstChild;
    },

    insertAfterDom: function(referenceNode, newNode) {
        referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
    },

    removeFromDom: function(elementOrSelector) {
        var el = Utils.$(elementOrSelector);
        if (el && el.parentNode) {
            el.parentNode.removeChild(el);
        }
    },

    removeBtn: function(btn) {
        if (btn.getElem()) {
            this.removeFromDom(btn.getElem());
        }
    },

    setProperPosition: function(existingElem, newElem) {
        var leftPx = this.getCssProperty(existingElem, 'left');
        newElem.style.left = parseInt(leftPx) + this.convertRemToPixels(this.BUTTON_OFFSET_IN_RAM) + 'px';
    },

    insertAfter: function(existingButton, newButton, fromListener) {
        var target = existingButton.getElem();
        var $this = this;
        var listener = function(e) {
            $this.insertAfter(existingButton, newButton, true);
        };
        if (!target) {
            if (fromListener) // avoid multiple listeners
                return;

            // TODO: wait till init
            EventUtils.addListener(
                YouTubeSelectors.PLAYER_EVENTS_RECEIVER,
                YouTubeEvents.MODEL_CHANGED_EVENT,
                listener);
            return;
        } else {
            EventUtils.removeListener(
                YouTubeSelectors.PLAYER_EVENTS_RECEIVER,
                YouTubeEvents.MODEL_CHANGED_EVENT,
                listener);
        }

        this.setProperPosition(existingButton.getElem(), newButton.getElem());
        this.insertAfterDom(existingButton.getElem(), newButton.getElem());
    },

    replaceBtn: function(elementOrSelector, newButton) {
        var el = Utils.$(elementOrSelector);
        if (el && el.parentNode) {
            el.parentNode.replaceChild(newButton.getElem(), el);
        }
    }
};
