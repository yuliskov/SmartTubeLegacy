// NOTE: this file doesn't depend on common js files

console.log("Scripts::Running core script track_end_button.js");

function BackButton(selector) {
    this.selector = selector;

    this.getChecked = function() {
        return null; // no state
    };

    this.retryOnFail = function() {
        var $this = this;
        setTimeout(function() {
            var isBackFailed = !Utils.hasClass(Utils.$($this.selector), $this.emptyModelClass);
            if (isBackFailed) {
                console.log("BackButton: trigger back failed... do retry...");
                $this.setChecked(true);
            }
        }, 100);
    };

    this.setChecked = function(doChecked) {
        if (doChecked) {
            EventUtils.triggerEnter(this.selector);
            this.retryOnFail();
        }
    };
}

BackButton.prototype = new ExoConstants();