console.log("Scripts::Running core script player_button.js");

/**
 * Checks that player is running and performs button press until success.
 * @param selector button selector
 */
function PlayerButton(selector) {
    this.selector = selector;
    this.decorator = new ExoButtonDecorator(this);

    this.getChecked = function() {
        console.log("PlayerButton: getChecked " + this.selector);
        return null; // no state
    };

    this.setChecked = function(doChecked) {
        console.log("PlayerButton: setChecked " + this.selector + " " + doChecked);
        if (doChecked) {
            EventUtils.triggerEnter(this.findToggle());
        }
    };

    this.decorator.apply();
}

PlayerButton.prototype = new ExoButton();