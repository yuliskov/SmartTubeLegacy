console.log("Scripts::Running core script exo_button_base.js");

function ExoButtonBase() {
    this.findToggle = function() {
        if (!this.selector) {
            console.log("YouButtonBase: selector is empty");
            return null;
        }

        for (var i = 0; i < 5; i++) {
            var btn = Utils.$(this.selector);
            if (btn == null || !Utils.hasClass(btn, ExoConstants.toggleButtonClass)) {
                console.log("YouButtonBase: button no initialized yet.. do init... " + this.selector);
                // imitate press on options button
                EventUtils.triggerEnter(ExoConstants.optionsBtnSelector);
                continue;
            }
            return btn;
        }

        return null;
    };
}