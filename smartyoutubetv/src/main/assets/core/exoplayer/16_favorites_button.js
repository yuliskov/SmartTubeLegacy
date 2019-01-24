console.log("Scripts::Running core script favorites_button.js");

function FavoritesButton(selector) {
    this.TAG = "FavoritesButton";
    this.selector = selector;

    this.setChecked = function(doChecked) {
        Log.d(this.TAG, "setChecked " + this.selector + " " + doChecked);

        if (doChecked) {
            var $this = this;

            EventUtils.triggerEnter(this.findToggle());
        }
    };
}

FavoritesButton.prototype = new ExoButton();