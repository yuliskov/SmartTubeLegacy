console.log("Scripts::Running core script exo_button.js");

function ExoButton(selector) {
    this.TAG = 'ExoButton';
    this.selector = selector;
    this.decorator = ExoButtonDecorator.instance();

    /**
     * Return first element that exists (array of selectors case)
     * @returns element element or null
     */
    this.findToggle = function() {
        var btn = null;

        if (Utils.isArray(this.selector)) {
            for (var i = 0; i < this.selector.length; i++) {
                btn = Utils.$(this.selector[i]);
                if (btn) {
                    break;
                }
            }
        } else {
            btn = Utils.$(this.selector);
        }

        btn || Log.w(this.TAG, "unable to find " + this.selector);

        return btn;
    };

    this.getChecked = function() {
        if (YouTubeUtils.isPlayerClosed()) {
            return null; // element not exists (see ActionReceiver.java for details)
        }

        var toggle = this.findToggle();

        if (!toggle) {
            this.isChecked = null;
        } else {
            var isChecked = Utils.hasClass(toggle, YouTubeClasses.ELEMENT_CHECKED);
            var isDisabled = Utils.hasClass(toggle, YouTubeClasses.ELEMENT_DISABLED);
            this.isChecked = isDisabled ? null : isChecked;
        }

        Log.d(this.TAG, "getChecked: " + this.selector + " " + this.isChecked);

        return this.isChecked;
    };
    
    this.setChecked = function(doChecked) {
        if (YouTubeUtils.isPlayerClosed()) {
            Log.d(this.TAG, "setChecked: video is closed already... do nothing: " + this.selector);
            return;
        }

        var isChecked = this.getChecked();

        if (isChecked == null) {
            Log.d(this.TAG, "button is disabled or not exists: exiting: " + this.selector);
            return;
        }

        if (isChecked == doChecked) {
            Log.d(this.TAG, "setChecked: already checked... do nothing: " + this.selector + ' ' + isChecked);
            return;
        }

        Log.d(this.TAG, "setChecked: " + this.selector + ' ' + doChecked + ' ' + this.findToggle());
        EventUtils.triggerEnter(this.findToggle());
        this.isChecked = doChecked;
    };

    this.decorator.apply(this);
}

ExoButton.fromSelector = function(selector, states) {
    var TAG = 'ExoButton'; // 'this' is reference to ExoButton

    function createButton(selector, states) {
        Log.d(TAG, "fromSelector: create button " + selector);
        switch (selector.toString()) {
            case PlayerActivityMapping.TRACK_ENDED.toString():
            case PlayerActivityMapping.BUTTON_NEXT.toString():
                return new TrackEndFakeButton(selector, states);
            case PlayerActivityMapping.BUTTON_SUGGESTIONS.toString():
                return new SuggestionsFakeButton(selector);
            case PlayerActivityMapping.BUTTON_FAVORITES.toString():
                return new FavoriteButton(selector);
            case PlayerActivityMapping.BUTTON_USER_PAGE.toString():
                return new ChannelButton(selector);
            case PlayerActivityMapping.BUTTON_BACK.toString():
                return new BackButton(selector, states);
            case PlayerActivityMapping.BUTTON_PREV.toString():
                return new NextPrevButton(selector);
            case PlayerActivityMapping.VIDEO_CANCELED.toString():
                return new VideoCanceledButton(selector);
            // default:
            //     // all other buttons is processed here
            //     return new ExoButton(selector);
        }
    }

    return createButton(selector, states);
};