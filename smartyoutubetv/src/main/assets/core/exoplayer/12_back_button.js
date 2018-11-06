console.log("Scripts::Running core script back_button.js");

/**
 * Automatic exit from the opened player.<br/>
 * Checks that player is running and performs back press.
 * @param selector back button selector
 */
function BackButton(selector) {
    this.selector = selector;
    this.retryTimes = 10;
    this.checkDelayMS = 300;

    this.retryOnFail = function() {
        if (ExoUtils.playerIsClosed() || this.retryTimes <= 0) {
            return;
        }
        this.retryTimes--;

        EventUtils.triggerEnter(this.selector);
        var $this = this;
        setTimeout(function() {
            console.log("BackButton: back failed... do retry...");
            $this.retryOnFail();
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
            // 'likes not saved' fix
            setTimeout(function() {
                $this.retryOnFail();
            }, this.checkDelayMS);
        }
    };
}

BackButton.prototype = new ExoButton();