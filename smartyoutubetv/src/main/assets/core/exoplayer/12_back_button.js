console.log("Scripts::Running core script back_button.js");

/**
 * Checks that player is running and performs button press until success.
 * @param selector button selector
 */
function BackButton(selector, states) {
    this.selector = selector;
    this.retryTimes = 10;
    this.checkDelayMS = 1000;
    this.stateless = true;
    this.TAG = 'BackButton';

    this.pressBackOrRetry = function() {
        if (this.retryTimes <= 0 || YouTubeUtils.isPlayerClosed()) {
            return;
        }

        // fix input lost after opening user channel page
        //Utils.$(YouTubeSelectors.PLAYER_EVENTS_RECEIVER).focus();

        this.retryTimes--;

        EventUtils.triggerEnter(this.findToggle());

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
            var $this = this;

            YouTubeUtils.showPlayerBackground();

            // update history position
            // YouTubeUtils.getPlayer().properties.currentTime = states['video_position'];
            // Log.d(this.TAG, "Current time is set to " + YouTubeUtils.getPlayer().currentTime);

            //PlayerController.advance();

            // 'likes not saved' fix
            setTimeout(function() {
                $this.pressBackOrRetry();
            }, this.checkDelayMS);
        }
    };
}

BackButton.prototype = new ExoButton();