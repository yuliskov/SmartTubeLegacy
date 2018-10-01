/*
Description: Common routines
*/

console.log("Scripts::Running script constants.js");

var KeyCodes = {
    UP: 38,
    DOWN: 40,
    LEFT: 37,
    RIGHT: 39,
    ENTER: 13,
    ESC: 27
};

var EventTypes = {
    KEY_UP: 'keyup',
    KEY_DOWN: 'keydown',
    HASH_CHANGE: 'hashchange'
};

var YouTubeConstants = {
    APP_CONTAINER_SELECTOR: '#leanback', // div that receives keys events for app,
    SEARCH_FIELD_SELECTOR: '#search-input',
    PLAYER_CONTAINER_SELECTOR: '#watch', // div that receives keys events for player (note: some events don't reach upper levels)
    PLAYER_CONTAINER_CLASS_NAME: 'watch',
    PLAYER_WRAPPER_SELECTOR: '.html5-video-container', // parent element of the 'video' tag
    PLAYER_MORE_BUTTON_SELECTOR: '#transport-more-button',
    PLAYER_PLAY_BUTTON_SELECTOR: '.icon-player-play',
    PLAYER_URL_KEY: 'watch'
};