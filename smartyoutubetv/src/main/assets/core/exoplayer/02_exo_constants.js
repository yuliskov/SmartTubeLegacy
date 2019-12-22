console.log("Scripts::Running core script exo_constants.js");

// java constants
// NOTE: order is important
var PlayerActivity = {
    VIDEO_CANCELED: 'video_canceled',
    //VIDEO_VIEW_COUNT: 'video_views',
    SCREEN_WIDTH: 'screen_width',
    //VIDEO_DATE: 'video_date',
    //BUTTON_LIKE: 'button_like',
    //BUTTON_DISLIKE: 'button_dislike',
    BUTTON_FAVORITES: 'button_favorites',
    //BUTTON_SUBSCRIBE: 'button_subscribe',
    BUTTON_USER_PAGE: 'button_user_page',
    BUTTON_PREV: 'button_prev',
    BUTTON_NEXT: 'button_next',
    BUTTON_SUGGESTIONS: 'button_suggestions',
    TRACK_ENDED: 'track_ended',
    BUTTON_BACK: 'button_back' // should be the last
};

// java constants in js code
var PlayerActivityMapping = {
    VIDEO_CANCELED: 'video_canceled',
    // BUTTON_LIKE: ['.material-icon-thumb-up.toggle-button', '.icon-like.toggle-button'],
    // BUTTON_DISLIKE: ['.material-icon-thumb-down.toggle-button', '.icon-dislike.toggle-button'],
    // BUTTON_SUBSCRIBE: ['.material-icon-video-youtube.toggle-button', '.icon-logo-lozenge.toggle-button'],
    BUTTON_USER_PAGE: YouTubeSelectors.PLAYER_CHANNEL_BUTTON,
    // multiple selectors: first that exists is used
    // so now we can match buttons from the different app versions
    BUTTON_NEXT: ['.skip-forward-button', '.new-skip-forward-button', '.icon-player-next.button'],
    BUTTON_PREV: ['.material-icon-skip-previous.button', '.icon-player-prev.button'],
    BUTTON_SUGGESTIONS: 'button_suggestions', // fake button (use internal logic)
    BUTTON_FAVORITES: '.material-icon-playlist-add',
    TRACK_ENDED: 'track_ended', // fake button (use internal logic)
    BUTTON_BACK: YouTubeSelectors.BUTTON_BACK // should be the last
};