console.log("Scripts::Running core script stateless_button.js");

/**
 * A button that could only be pressed
 */
function StatelessButton(selector) {
    this.selector = selector;

    this.getChecked = function() {
        console.log("StatelessButton: getChecked " + this.selector);
        return null; // no state
    };

    this.setChecked = function(doChecked) {
        if (doChecked) {
            console.log("StatelessButton: setChecked " + this.selector + " " + doChecked);
            EventUtils.triggerEnter(this.findToggle());
        }
    };
}

StatelessButton.prototype = new ExoButton();