console.log("Scripts::Running core script back_button.js");

/**
 * Checks that player is running and performs button press until success.
 * @param selector button selector
 */
function BackButton(selector, states) {
    this.selector = selector;
    this.retryTimes = 10;
    this.checkDelayMS = 500;
    this.stateless = true;
    this.TAG = 'BackButton';

    this.pressBackOrRetry = function() {
        if (this.retryTimes <= 0 || YouTubeUtils.isPlayerClosed()) {
            return;
        }

        this.retryTimes--;

        YouTubeUtils.pressBack();

        // var backBtn = this.findToggle();
        //
        // if (backBtn && this.retryTimes > 8) { // last option is try to imitate ESC
        //     EventUtils.triggerEnter(backBtn);
        // } else {
        //     YouTubeUtils.pressBack();
        // }

        if (YouTubeUtils.isPlayerClosed()) {
            return;
        }

        var $this = this;
        setTimeout(function() {
            console.log("BackButton: attempt to press on the element: " + $this.selector);
            $this.pressBackOrRetry();
        }, this.checkDelayMS);
    };

    this.getChecked = function() {
        console.log("BackButton: getChecked " + this.selector);
        return null; // no state
    };

    this.setChecked = function(doChecked) {
        console.log("BackButton: setChecked " + this.selector + " " + doChecked);
        if (doChecked) {
            YouTubeUtils.showPlayerBackground();

            YouTubeUtils.getPlayer().backPressed();

            this.pressBackOrRetry();
        }
    };
}

BackButton.prototype = new ExoButton();