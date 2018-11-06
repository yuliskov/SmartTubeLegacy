console.log("Scripts::Running core script next_prev_button.js");

/**
 * Checks that player is running and performs button press until success.
 * @param selector button selector
 */
function NextPrevButton(selector) {
    this.selector = selector;
    this.checkDelayMS = 300;
    this.decorator = new ExoButtonDecorator(this);

    this.getChecked = function() {
        console.log("NextPrevButton: getChecked " + this.selector);
        return null; // no state
    };

    this.setChecked = function(doChecked) {
        console.log("NextPrevButton: setChecked " + this.selector + " " + doChecked);
        if (doChecked) {
            var $this = this;
            setTimeout(function() {
                EventUtils.triggerEnter($this.findToggle());
            }, this.checkDelayMS);
        }
    };

    this.decorator.apply();
}

NextPrevButton.prototype = new ExoButton();