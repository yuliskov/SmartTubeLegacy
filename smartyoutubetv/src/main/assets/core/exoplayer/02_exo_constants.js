// NOTE: this file doesn't depend on common js files

console.log("Scripts::Running core script exo_constants.js");

// java constants
var PlayerActivity = {
    VIDEO_DATE: "video_date",
    BUTTON_LIKE: "button_like",
    BUTTON_DISLIKE: "button_dislike",
    BUTTON_SUBSCRIBE: "button_subscribe",
    BUTTON_USER_PAGE: "button_user_page",
    BUTTON_PREV: "button_prev",
    BUTTON_NEXT: "button_next",
    BUTTON_BACK: "button_back",
    BUTTON_SUGGESTIONS: "button_suggestions",
    TRACK_ENDED: "track_ended",
    PLAYER_RUN_ONCE: "player_run_once" // indicates that webview instance not restored after player close
};

// java constants in js code
var PlayerActivityMapping = {
    BUTTON_LIKE: ".icon-like.toggle-button",
    BUTTON_DISLIKE: ".icon-dislike.toggle-button",
    BUTTON_SUBSCRIBE: ".icon-logo-lozenge.toggle-button",
    BUTTON_USER_PAGE: ".pivot-channel-tile",
    // multiple selectors: first that exists is used
    // so now we can match buttons from the different app versions
    BUTTON_NEXT: [".new-skip-forward-button", ".icon-player-next"],
    BUTTON_PREV: ".icon-player-prev",
    BUTTON_BACK: "#legend .back",
    BUTTON_SUGGESTIONS: "button_suggestions", // fake button (use internal logic)
    TRACK_ENDED: "track_ended", // fake button (use internal logic)
    PLAYER_RUN_ONCE: "player_run_once" // fake button (use internal logic)
};

function ExoConstants() {
    this.hiddenClass = 'hidden';
    this.disabledClass = 'disabled';
    this.selectedClass = 'toggle-selected';
    this.optionsBtnSelector = '#transport-more-button';
    this.backBtnSelector = '#legend .back';
    this.mainControlsSelector = '#transport-controls';
    this.controlsBarSelector = '#buttons-list';
    this.playButtonSelector = ".icon-player-play.toggle-button";
    // this.mainControlsSelector = '.fresh-transport-controls.transport-controls';
    this.mainTitleSelector = '.title-card.watch-title-tray';
    this.eventReceiverSelector = '#watch'; // div that receives keys events
    this.videoSelector = 'video';
    this.uploadDate = '.uploaded-date';
    this.videoDetails = '.player-video-details';
    this.suggestionsListSelector = 'div.pivot-shelf-list';
    this.componentBlurEvent = 'component-blur';
    this.componentFocusEvent = 'component-focus';
}