console.log("Scripts::Running core script next_prev_button.js");

/**
 * Checks that player is running and performs button press until success.
 * @param selector button selector
 */
function NextPrevButton(selector) {
    this.selector = selector;
    this.checkDelayMS = 500;
    this.decorator = new ExoButtonDecorator(this);

    this.getChecked = function() {
        console.log("NextPrevButton: getChecked " + this.selector);

        return YouTubeUtils.isDisabled(this.findToggle()) ? null : false;
    };

    this.setChecked = function(doChecked) {
        console.log("NextPrevButton: setChecked " + this.selector + " " + doChecked);

        if (doChecked) {
            if (YouTubeUtils.isDisabled(this.findToggle())) { // close video if btn not active or not found
                new BackButton(PlayerActivityMapping.BUTTON_BACK).setChecked(true);
                return;
            }

            var $this = this;
            // let other addons to do its work
            setTimeout(function() {
                EventUtils.triggerEnter($this.findToggle());
            }, this.checkDelayMS);
        }
    };

    this.decorator.apply();
}

NextPrevButton.prototype = new ExoButton();