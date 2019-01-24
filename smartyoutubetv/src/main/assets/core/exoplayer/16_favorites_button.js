console.log("Scripts::Running core script favorites_button.js");

function FavoritesButton(selector) {
    this.TAG = "FavoritesButton";
    this.selector = selector;

    this.getChecked = function() {
        Log.d(this.TAG, "getChecked " + this.selector);
        return false;
    };

    this.setChecked = function(doChecked) {
        Log.d(this.TAG, "setChecked " + this.selector + " " + doChecked);

        if (doChecked) {
            var el = this.findToggle();

            if (!el) {
                Log.d(this.TAG, "Oops, not found... closing");
                ExoUtils.sendAction(ExoUtils.CLOSE_SUGGESTIONS);
            }

            EventUtils.triggerEnter(el);
        }
    };
}

FavoritesButton.prototype = new ExoButton();