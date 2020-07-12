/**
 * YouTube constants
 */

console.log("Scripts::Running script youtube_constants.js");

var YouTubeConstants = {
    // CSS Selectors
    APP_CONTAINER_SELECTOR: '#leanback', // div that receives keys events for app,
    SEARCH_FIELD_SELECTOR: '#search-input',
    PLAYER_EVENTS_RECEIVER_SELECTOR: '#watch', // div that receives keys events for player (note: some events don't reach upper levels)
    // CSS Classes
    PLAYER_CONTAINER_CLASS: 'watch',
    // Other
    PLAYER_URL_KEY: 'watch',
    OPTIONS_VERSION_TITLE: 'Version',
    OPTIONS_DEVICE_TITLE: 'Device',
    CATALOG_SIGN: ['#/surface', '#/zylon-surface']
};

/**
 * Selectors for the YouTube elements
 */
var YouTubeSelectors = {
    ROOT_CONTAINER: '#container', // app v2.0
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
    PLAYER_MORE_BUTTON_TOGGLED: '#transport-more-button.toggle-selected',
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
    PLAYER_CONTROLS: ['#transport-controls', '.transport-controls'],
    CHANNEL_CONTENT: '.ytlr-tv-browse-renderer',
    VOICE_SEARCH: '#voice-search',
    FOCUSED_ELEMENT: '.focused',
    FOCUSED_VIDEO_PROGRESS_BAR: '.yt-virtual-list__item--selected ytlr-tile-renderer.zylon-focus .ytlr-thumbnail-overlay-resume-playback-renderer__progress-watched',
    SUGGESTIONS_FOCUSED_VIDEO_PROGRESS_BAR: '.focused.watchable-tile .airstream-video-tile-progress-watched',
    // NOTE: suggested videos doesn't contain type bage
    FOCUSED_VIDEO_TYPE_BAGE: '.yt-virtual-list__item--selected ytlr-tile-renderer.zylon-focus .ytlr-thumbnail-overlay-time-status-renderer',
    FOCUSED_VIDEO_DURATION_BAGE: '.yt-virtual-list__item--selected ytlr-tile-renderer.zylon-focus .ytlr-thumbnail-overlay-time-status-renderer__text',
    FOCUSED_VIDEO_AUTHOR_BAGE: '.yt-virtual-list__item--selected ytlr-tile-renderer.zylon-focus ytlr-tile-metadata-renderer ytlr-line-renderer',
    SUGGESTIONS_FOCUSED_VIDEO_DURATION_BAGE: '.focused.watchable-tile .airstream-video-tile-decoration-text',
    SUGGESTIONS_FOCUSED_VIDEO_AUTHOR_BAGE: '.focused.watchable-tile .channel-name',
    PLAYER_OBJ: 'video',
    MASTHEAD_SECTION: '.masthead-overlay',
    SETTING_APP_VERSION: 'YTLR-SETTING-APP-VERSION.ytlr-setting-app-version'
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
    PLAYER_CONTROLS_SHOWING: 'transport-showing',
    PLAYER_CONTROLS_SHOWING_KIDS: 'transport-controls-showing',
    VIDEO_TYPE_LIVE: 'ytlr-thumbnail-overlay-time-status-renderer--LIVE',
    VIDEO_TYPE_DEFAULT: 'ytlr-thumbnail-overlay-time-status-renderer--DEFAULT',
    VIDEO_TYPE_UPCOMING: 'ytlr-thumbnail-overlay-time-status-renderer--UPCOMING'
};

var YouTubeClassesV2 = {
    GENERIC_OVERLAY_HIDDEN: 'yt-overlay-stage--hidden',
    WATCH_PAGE_CONTROL: 'ytlr-watch-page--control',
    WATCH_PAGE_IDLE: 'ytlr-watch-page--idle',
    WATCH_PAGE_OVERLAY: 'ytlr-watch-page--overlay', // overlay has been shown
    WATCH_PAGE_PIVOT: 'ytlr-watch-page--pivot' // player suggestions
};

var YouTubeSelectorsV2 = {
    OPTIONS_APP_KEY_SELECTOR: '.ytlr-setting-app-version__app-version-key',
    OPTIONS_APP_VALUE_SELECTOR: '.ytlr-setting-app-version__app-version-value',
    SEARCH_PAGE_SUGGESTIONS: 'ytlr-search-suggestions',
    SEARCH_PAGE_KEYBOARD: 'ytlr-search-keyboard',
    SEARCH_PAGE_TEXT_BOX: 'ytlr-search-text-box',
    OVERLAY_PANEL_BUTTON: 'yt-overlay-stage ytlr-button-renderer',
    PLAYER_NEXT_BUTTON: 'ytlr-skip-forward-button',
    PLAYER_PREV_BUTTON: 'ytlr-skip-backward-button',
    PLAYER_PLAY_BUTTON: 'ytlr-play-button',
    PLAYER_CHANNEL_BUTTON: 'ytlr-video-owner-renderer',
    PLAYER_BACK_BUTTON: 'ytlr-legend .ytlr-legend-item__icon--back',
    PLAYER_MORE_BUTTON: 'ytlr-button-renderer.ytlr-transport-controls-renderer__more-options-button',
    PLAYER_QUALITY_BUTTON: 'ytlr-quality-button',
    PLAYER_ADD_TO_FAV_BUTTON: 'ytlr-button-renderer .material-icon-playlist-add',
    PLAYER_LIKE_BUTTON: 'ytlr-toggle-button-renderer .material-icon-thumb-up',
    PLAYER_DISLIKE_BUTTON: 'ytlr-toggle-button-renderer .material-icon-thumb-down',
    PLAYER_SUBSCRIBE_BUTTON: 'ytlr-toggle-button-renderer .material-icon-video-youtube',
    PLAYER_CONTAINER_ENABLED: 'ytlr-player.ytlr-player--enabled',
    PLAYER_EVENTS_RECEIVER: 'ytlr-watch-page'
};

// /**
//  * Custom events
//  */
// var YouTubeEvents = {
//     MODEL_CHANGED_EVENT: 'model:changed',
//     COMPONENT_FOCUS_EVENT: 'component-focus',
//     COMPONENT_BLUR_EVENT: 'component-blur',
//     BUTTON_ENTER: 'button-enter'
// };

var YouTubeTagsV2 = {
    SETTING_APP_VERSION: 'YTLR-SETTING-APP-VERSION',
    SEARCH_PAGE: 'YTLR-SEARCH-CONTAINER',
    OVERLAY_PANEL: 'YT-OVERLAY-STAGE',
    WATCH_PAGE: 'YTLR-WATCH-PAGE'
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