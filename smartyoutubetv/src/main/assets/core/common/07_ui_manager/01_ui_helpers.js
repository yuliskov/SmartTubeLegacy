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

    insertAfter: function(existingButton, newButton) {
        var target = existingButton.getElem();
        if (!target) {
            // TODO: wait till init
            return;
        }

        this.insertAfterDom(existingButton.getElem(), newButton.getElem());
    }
};
