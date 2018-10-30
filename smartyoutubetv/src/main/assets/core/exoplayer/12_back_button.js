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
            var isBackFailed = !Utils.hasClass(Utils.$($this.selector), $this.emptyModelClass);
            if (isBackFailed) {
                console.log("BackButton: trigger back failed... do retry... " + $this.selector);
                EventUtils.triggerEnter($this.selector);
            }
        }, 100);
    };

    this.getChecked = function() {
        console.log("BackButton: getChecked " + this.selector);
        return null; // no state
    };

    this.setChecked = function(doChecked) {
        console.log("BackButton: setChecked " + this.selector + " " + doChecked);
        var $this = this;
        if (doChecked) {
            setTimeout(function() {
                EventUtils.triggerEnter($this.selector);
            }, 100); // give chance for other events to end up their work
        }
    };
}

BackButton.prototype = new ExoConstants();