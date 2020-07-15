/**
 * Description:
 * Update russian localization on some player's buttons.
 */

console.log("Scripts::Running script russian_text_fix.js");

function RussianTextFixAddon() {
    this.stringToFind = "Дополнительные параметры";
    this.stringToFind2 = "Другие параметры";
    this.stringToReplace = "Параметры";

    this.run = function() {
        var $this = this;
        Utils.delayTillTestFnSuccess(function() {
           $this.fixOverlappedTextInRussian();
        }, function() {
            return Utils.$(YouTubeConstants.PLAYER_MORE_BUTTON_SELECTOR);
        });
    };

    this.fixOverlappedTextInRussian = function() {
        var moreButton = Utils.$(YouTubeConstants.PLAYER_MORE_BUTTON_SELECTOR);
        var paramButton = moreButton.children[0];
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
            console.log('RussianTextFixAddon::dom changed, ' + el);
            $this.replaceOverlappedTextInRussian(el);
        });
    };
}

// new RussianTextFixAddon().run();