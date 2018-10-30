// NOTE: this file doesn't depend on common js files

console.log("Scripts::Running core script exo_button.js");

function YouButton(selector) {
    this.selector = selector;

    this.initializer = new YouButtonDecorator(this);

    this.doPressOnOptionsBtn = function() {
        EventUtils.triggerEnter(this.optionsBtnSelector);
    };

    this.findToggle = function() {
        var btn = Utils.$(selector);

        btn || console.warn("YouButton: unable to find " + selector);

        return btn;
    };

    this.playerIsClosed = function() {
        return Utils.$('video').style.display == 'none';
    };

    this.getChecked = function() {
        if (this.playerIsClosed())
            return null; // element not exists (see ActionReceiver.java for details)

        if (this.isChecked === undefined) {
            var toggle = this.findToggle();
            var isChecked = Utils.hasClass(toggle, this.selectedClass);
            var isDisabled = Utils.hasClass(toggle, this.disabledClass);
            this.isChecked = isDisabled ? null : isChecked;
        }
        console.log("YouButton: getChecked: " + selector + " " + this.isChecked);
        return this.isChecked;
    };

    this.setChecked = function(doChecked) {
        if (this.playerIsClosed()) {
            console.log("YouButton: setChecked: video is closed already... do nothing: " + selector);
            return;
        }

        var isChecked = this.getChecked();
        // if (isChecked === null) {
        //     console.log("YouButton: button is disabled or not exists: exiting: " + this.selector);
        //     return;
        // }
        if (isChecked == doChecked) {
            console.log("YouButton: setChecked: already checked... do nothing: " + selector + ' ' + isChecked);
            return;
        }

        console.log("YouButton: setChecked: " + selector + ' ' + doChecked + ' ' + this.findToggle());
        EventUtils.triggerEnter(this.findToggle());
        this.isChecked = doChecked;
    };

    this.initializer.apply();
}

YouButton.prototype = new ExoConstants();
YouButton.fromSelector = function(selector) {
    function createButton(selector) {
        switch (selector) {
            case PlayerActivityMapping.TRACK_ENDED:
                return new TrackEndFakeButton(selector);
            case PlayerActivityMapping.BUTTON_SUGGESTIONS:
                return new SuggestionsFakeButton(selector);
            case PlayerActivityMapping.BUTTON_BACK:
                return new BackButton(selector);
            default:
                // NOTE: all other buttons is processed here
                return new YouButton(selector);
        }
    }

    if (!this.btnMap)
        this.btnMap = {};
    if (!this.btnMap[selector]) {
        this.btnMap[selector] = createButton(selector);
    } else {
        console.log("YouButton: fromSelector: getting button from cache");
    }
    return this.btnMap[selector];
};

YouButton.resetCache = function() {
    if (this.btnMap)
        delete this.btnMap;
};