console.log("Scripts::Running core script back_button.js");

function BackButton(selector) {
    this.selector = selector;
    this.retryTimes = 3;

    this.retryOnFail = function() {
        if (this.retryTimes == 0) {
            return;
        }
        this.retryTimes--;
        var $this = this;
        setTimeout(function() {
            var isBackFailed = !Utils.hasClass(Utils.$($this.eventRootSelector), $this.emptyModelClass);
            console.log("BackButton: is back failed? do retry?... " + isBackFailed);
            if (isBackFailed) {
                $this.setChecked(true);
            }
        }, 100);
    };

    this.getChecked = function() {
        console.log("BackButton: getChecked " + this.selector);
        return null; // no state
    };

    this.setChecked = function(doChecked) {
        console.log("BackButton: setChecked " + this.selector + " " + doChecked);
        if (doChecked) {
            // give chance for other events to end up their work
            EventUtils.triggerEnter(this.selector);
            this.retryOnFail();
        }
    };
}

BackButton.prototype = new ExoConstants();