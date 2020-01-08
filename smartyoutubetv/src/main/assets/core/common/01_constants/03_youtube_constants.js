/**
 * YouTube constants
 */

console.log("Scripts::Running script youtube_constants.js");

var YouTubeConstants = {
    // CSS Selectors
    APP_EVENTS_RECEIVER_SELECTOR: '#leanback', // div that receives keys events for app
    DIALOG_WINDOW_SELECTOR: ['#dialog-stage', '#overlay-stage'],
    DIALOG_CANCEL_BUTTON_SELECTOR: ['#dialog-cancel-button', '.overlay-partials-menu-item'],
    DIALOG_OK_BUTTON_SELECTOR: ['#dialog-ok-button', '.overlay-partials-menu-item'],
    APP_CONTAINER_SELECTOR: '#leanback', // div that receives keys events for app,
    SEARCH_FIELD_SELECTOR: '#search-input',
    PLAYER_EVENTS_RECEIVER_SELECTOR: '#watch', // div that receives keys events for player (note: some events don't reach upper levels)
    PLAYER_WRAPPER_SELECTOR: '.html5-video-container', // parent element of the 'video' tag
    PLAYER_MORE_BUTTON_SELECTOR: '#transport-more-button',
    PLAYER_PLAY_BUTTON_SELECTOR: '.icon-player-play',
    SURFACE_CONTENT_SELECTOR: '#surface-content', // holds all right area content
    OPTIONS_APP_KEY_SELECTOR: '.app-version-key',
    OPTIONS_APP_VALUE_SELECTOR: '.app-version-value',
    // CSS Classes
    ELEMENT_FOCUSED_CLASS: 'focused',
    PLAYER_CONTAINER_CLASS: 'watch',
    MODEL_LOADED_CLASS: 'loaded',
    // DOM Events
    MODEL_CHANGED_EVENT: 'model:changed',
    COMPONENT_FOCUS_EVENT: 'component-focus',
    COMPONENT_BLUR_EVENT: 'component-blur',
    // Other
    PLAYER_URL_KEY: 'watch',
    OPTIONS_VERSION_TITLE: 'Version',
    OPTIONS_DEVICE_TITLE: 'Device'
};

/**
 * Selectors for the YouTube elements
 */
var YouTubeSelectors = {
    APP_ROOT: '#app-markup',
    PLAYER_EVENTS_RECEIVER: '#watch',
    PLAYER_CONTROLS_CONTAINER: '.controls-row',
    PLAYER_BUTTONS: '#buttons-list',
    PLAYER_BUTTONS_CONTAINER: '#buttons-list > .new-list-container',
    PLAYER_SUBS_BUTTON: ['.material-icon-closed-caption.transport-controls-toggle-button', '.icon-player-closedcaptions.transport-controls-toggle-button'],
    PLAYER_CHANNEL_BUTTON: ['.transport-channel-button', '.pivot-channel-tile'],
    PLAYER_PREV_BUTTON: '.material-icon-skip-previous',
    PLAYER_PLAY_BUTTON: ['.material-icon-play-arrow', '.icon-player-play'],
    PLAYER_MORE_BUTTON: '#transport-more-button',
    SEARCH_SIDE_BUTTON: '.guide-button',
    SEARCH_PAGE: '#search',
    SEARCH_INPUT_FIELD: '#text-input input',
    SEARCH_MIC_BUTTON: '#microphone',
    SEARCH_SUGGESTIONS: '#search-suggestions',
    SEARCH_FIRST_SUGGESTION: '#vertical-search-suggestions .selected.search-suggestion',
    SEARCH_KEYBOARD: '#search-keyboard',
    SEARCH_START_BUTTON: '#keyboard-search',
    SEARCH_KEYBOARD_GRID: '#keyboard-grid',
    SEARCH_KEYBOARD_SPACE: '#keyboard-spacebar',
    SEARCH_RESULTS_ROW: '#search-results',
    // NOTE: '#browse' - for Kids only
    // NOTE: '#zylon-surface', '#surface' - for Main app only
    SURFACE_AREA: ['#zylon-surface', '#surface', '#browse'],
    SURFACE_AREA_CONTENT: '#surface-content',
    MAIN_LOADER: '#loader',
    // NOTE: '#legend .back' - origin, '.back-button' - kids
    BUTTON_BACK: ['#legend .back', '.back-button'],
    OVERLAY_PANEL_CONTAINER: '#overlay-stage',
    OVERLAY_PANEL_MENU_ITEM: '#overlay-stage .overlay-partials-menu-item',
    OVERLAY_PANEL: '.overlay-action-panel',
    MULTI_ACCOUNT_PANEL: '.overlay-action-panel.overlay-list-with-header',
    WELCOME_SCREEN_PANEL: '.overlay-action-panel.welcome',
    PLAYER_UPLOAD_DATE: '.uploaded-date',
    PLAYER_VIDEO_DETAILS: '.player-video-details',
    PLAYER_VIEW_COUNT: '.view-count-label',
    PLAYER_NEW_TITLE: '.watch-title-tray',
    //PLAYER_SUGGESTIONS: '#bottom-half.bottom-half',
    // NOTE: 'div.pivot-shelf-list' - original, '#pivot-list' - kids
    PLAYER_SUGGESTIONS_LIST: ['div.pivot-shelf-list', '#pivot-list'],
    // NOTE: '#transport-controls' - original, '.transport-controls' - kids
    PLAYER_UI_CONTAINER: ['#transport-controls', '.transport-controls'],
    CHANNEL_CONTENT: '.ytlr-tv-browse-renderer',
    VOICE_SEARCH: '#voice-search',
    FOCUSED_ELEMENT: '.focused',
    FOCUSED_VIDEO_PROGRESS_BAR: '.yt-virtual-list__item--selected ytlr-tile-renderer.zylon-focus .ytlr-thumbnail-overlay-resume-playback-renderer__progress-watched',
    PLAYER_OBJ: 'video'
};

/**
 * Pure CSS classes (without dot notation)
 */
var YouTubeClasses = {
    ELEMENT_FOCUSED: 'focused',
    ELEMENT_DISABLED: 'disabled',
    ELEMENT_SELECTED: 'selected',
    ELEMENT_CHECKED: 'toggle-selected',
    PLAYER_CONTAINER: 'watch',
    MODEL_LOADED: 'loaded',
    WATCH_IDLE_CLASS: 'watch-idle',
    BUTTON_TOGGLED: 'toggle-selected',
    NO_MODEL: 'no-model',
    HIDDEN: 'hidden',
    PLAYER_UI_SHOWING: 'transport-showing'
};

/**
 * Custom events
 */
var YouTubeEvents = {
    MODEL_CHANGED_EVENT: 'model:changed',
    COMPONENT_FOCUS_EVENT: 'component-focus',
    COMPONENT_BLUR_EVENT: 'component-blur',
    BUTTON_ENTER: 'button-enter'
};

/**
 * Unclassified YouTube keywords
 */
var YouTubeOther = {
    PLAYER_URL_KEY: 'watch',
    OPTIONS_VERSION_TITLE: 'Version',
    OPTIONS_DEVICE_TITLE: 'Device'
};

/**
 * YouTube checks next types through the {@link window.MediaSource.isTypeSupported} method
 */
var YouTubeMediaTypes = [
    'video/webm; codecs="vp9"',
    'video/webm; codecs="vp9"; width=640',
    'video/webm; codecs="vp9"; width=99999',
    'video/webm; codecs="vp9"; height=360',
    'video/webm; codecs="vp9"; height=99999',
    'video/webm; codecs="vp9"; framerate=30',
    'video/webm; codecs="vp9"; framerate=9999',
    'video/webm; codecs="vp9"; width=3840; height=2160; bitrate=2000000',
    'video/webm; codecs="vp9"; width=3840; height=2160; bitrate=20000000',
    'video/webm; codecs="vp9"; bitrate=300000',
    'video/webm; codecs="vp9"; bitrate=2000000000',
    'video/webm; codecs="vp9"; eotf=bt709',
    'video/webm; codecs="vp9"; eotf=catavision',
    'video/webm; codecs="vp09.02.51.10.01.09.16.09"',
    'video/webm; codecs="vp09.02.51.10.01.09.99.99"',
    'video/mp4; codecs="avc1.4d4015"',
    'video/mp4; codecs="avc1.4d401e"',
    'video/mp4; codecs="avc1.4d401f"',
    'video/mp4; codecs="avc1.640028"',
    'video/mp4; codecs="avc1.4d400c"',
    'video/mp4; codecs="avc1.42001E"',
    'audio/webm; codecs="opus"',
    'audio/webm; codecs="opus"; channels=2',
    'audio/webm; codecs="opus"; channels=99',
    'audio/webm; codecs="vorbis"',
    'audio/mp4; codecs="mp4a.40.2"'
];