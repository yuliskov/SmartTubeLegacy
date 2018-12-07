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

    removeElem: function(elem) {
        if (elem)
            elem.outerHTML = "";
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
            EventUtils.removeListener(listener);
        }

        this.insertAfterDom(existingButton.getElem(), newButton.getElem());
    }
};
