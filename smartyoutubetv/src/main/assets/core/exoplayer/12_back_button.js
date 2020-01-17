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

        var backBtn = this.findToggle();

        if (backBtn && this.retryTimes > 1) { // last option is try imitate ESC
            EventUtils.triggerEnter(backBtn);
        } else {
            EventUtils.triggerEvent(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, DefaultEvents.KEY_DOWN, DefaultKeys.ESC);
            EventUtils.triggerEvent(YouTubeSelectors.PLAYER_EVENTS_RECEIVER, DefaultEvents.KEY_UP, DefaultKeys.ESC);
        }

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

            this.pressBackOrRetry();

            YouTubeUtils.getPlayer().backPressed();
        }
    };
}

BackButton.prototype = new ExoButton();