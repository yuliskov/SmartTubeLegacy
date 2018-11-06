console.log("Scripts::Running core script no_state_button.js");

/**
 * Checks that player is running and performs button press until success.
 * @param selector button selector
 */
function NoStateButton(selector) {
    this.selector = selector;
    this.retryTimes = 10;
    this.checkDelayMS = 300;

    // if (Utils.isArray(this.selector)) {
    //     for (var i = 0; i < this.selector.length; i++) {
    //         var el = Utils.$(this.selector[i]);
    //         if (el) {
    //             this.selector = this.selector[i];
    //             break;
    //         }
    //     }
    // }

    this.retryOnFail = function() {
        if (this.retryTimes <= 0 || ExoUtils.playerIsClosed()) {
            return;
        }
        this.retryTimes--;

        EventUtils.triggerEnter(this.selector);

        var $this = this;
        setTimeout(function() {
            console.log("NoStateButton: attempt to press on the element: " + $this.selector);
            $this.retryOnFail();
        }, this.checkDelayMS);
    };

    this.getChecked = function() {
        console.log("NoStateButton: getChecked " + this.selector);
        return null; // no state
    };

    this.setChecked = function(doChecked) {
        console.log("NoStateButton: setChecked " + this.selector + " " + doChecked);
        if (doChecked) {
            var $this = this;
            // 'likes not saved' fix
            setTimeout(function() {
                $this.retryOnFail();
            }, this.checkDelayMS);
        }
    };
}

NoStateButton.prototype = new ExoButton();