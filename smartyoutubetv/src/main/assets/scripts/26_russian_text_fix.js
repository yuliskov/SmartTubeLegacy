/**
 * Description:
 * Enable Soft Keyboard support.
 * In order to keyboard to appear you must go to the search page and them move selection to the input area.
 */

console.log("Scripts::Running script enable_external_keyboard.js");

function EnableExternalKeyboardAddon() {
    this.stringToFind = "Дополнительные параметры";
    this.stringToFind2 = "Другие параметры";
    this.stringToReplace = "Параметры";

    this.run = function() {
        this.fixOverlappedTextInRussian();
    };

    this.fixOverlappedTextInRussian = function() {
        var paramButton = Utils.$(Utils.playerMoreButtonSelector).children[0];
        if (this.replaceOverlappedTextInRussian(paramButton))
            this.doObserveOverlappedTextInRussian(paramButton);
    };

    this.replaceOverlappedTextInRussian = function(paramButton) {
        if (paramButton.innerHTML == this.stringToFind || paramButton.innerHTML == this.stringToFind2) {
            paramButton.innerHTML = this.stringToReplace;
            return true;
        }
        return false;
    };

    this.doObserveOverlappedTextInRussian = function(paramButton) {
        var $this = this;
        Utils.observeDOM(paramButton, function(el) {
            console.log('dom changed, ' + el);
            $this.replaceOverlappedTextInRussian(el);
        });
    };
}

new EnableExternalKeyboardAddon().run();