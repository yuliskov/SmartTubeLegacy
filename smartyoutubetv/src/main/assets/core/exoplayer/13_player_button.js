console.log("Scripts::Running core script player_button.js");

function PlayerButton(selector) {
    this.selector = selector;

    this.getChecked = function() {
        console.log("PlayerButton: getChecked " + this.selector);
        return null; // no state
    };
}

PlayerButton.prototype = new ExoButton();