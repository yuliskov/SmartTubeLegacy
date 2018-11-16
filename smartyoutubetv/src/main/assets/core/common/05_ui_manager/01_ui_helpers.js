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

    insertAfter: function(referenceNode, newNode) {
        referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
    },

    removeElem: function(elem) {
        if (elem)
            elem.outerHTML = "";
    }
};
