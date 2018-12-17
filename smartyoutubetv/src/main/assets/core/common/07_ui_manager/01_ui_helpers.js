/**
 * Ui helpers
 */

console.log("Scripts::Running script ui_helpers.js");

var UiHelpers = {
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
        if (el) {
            el.parentNode.removeChild(el);
        }
    },

    removeBtn: function(btn) {
        if (btn.getElem()) {
            this.removeFromDom(btn.getElem());
        }
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

        this.insertAfterDom(existingButton.getElem(), newButton.getElem());
    }
};
