/**
 * Description: How often video should by synced with audio in milliseconds.
 * Format: {'device_name or platform': time_millis, ...}
 * Device's name should be named accordingly to the CPU-Z.
 * You can find name here: CPU-Z > Device tab > Model field
 */

console.log("Scripts::Running script video_sync_fix_config.js");

/**
 * Config for the WebView browser's engine (1th launcher)
 */
var SyncConfig_WebView = {
    'X96mini (p281)': 15*1000, // amlogic: Telegram group
    'MiBOX_mini': 30*1000
};

/**
 * Config for the CrossWalk browser's engine (2nd launcher)
 */
var SyncConfig_XWalk = {
    'X96mini (p281)': 15*1000, // amlogic: Telegram group
    'MiBOX_mini': 3*60*1000
};