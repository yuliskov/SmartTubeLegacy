/**
 * Description: Contains codec configurations for different devices.
 * Format: {'device_name or platform': 'disabled_codec', ...}
 * Device's name should be named accordingly to the CPU-Z.
 * You can find name here: CPU-Z > Device tab > Model field
 */

console.log("Scripts::Running script codec_fix_config.js");

/**
 * Config for the WebView browser's engine (1th launcher)
 */
var CodecConfig_WebView = {
    'amlogic': 'mp4', // amlogic boards have troubles with avc codec
    '(vbox86p)': 'mp4', // Genymotion virtual device
    'Amazon Jem (cm_jem)': '', // Kindle Fire HD 8.9 (enable all formats)
    'MiTV3S (pulpfiction)': 'mp4', // MiTV3S 55/60 (4pda)
    'MStar Android TV (aosp_ponkan32)': 'mp4', // ??? (gmail)
    'MiTV3S (missionimpossible)': 'mp4', // MiTV3 ???
    'MiTV4A (matrix)': 'mp4', // MiTV4A (4pda)
    'MiTV4 (missionimpossible)': 'mp4', // MiTV4 65 (4pda)
    'NG3128HD': 'webm, mp4', // (github > issues)
    't95m': '', // (gmail)
    '32T18': '', // FAMILY GENERATION T18 BY (gmail)
    'mt5882': '', // Generic Android on mt5882 (gmail)
    '': 'webm' // other devices (entry must be the last in the list)
};

/**
 * Config for the CrossWalk browser's engine (2nd launcher)
 */
var CodecConfig_XWalk = {
    'amlogic': 'mp4', // amlogic boards have troubles with avc codec
    '(vbox86p)': 'mp4', // Genymotion virtual device
    'Amazon Jem (cm_jem)': '', // Kindle Fire HD 8.9 (enable all formats)
    'MiTV3S (pulpfiction)': 'mp4', // MiTV3S 55/60 (4pda)
    'MStar Android TV (aosp_ponkan32)': 'mp4', // ??? (gmail)
    'MiTV3S (missionimpossible)': 'mp4', // MiTV3 ???
    'MiTV4A (matrix)': 'mp4', // MiTV4A (4pda)
    'MiTV4 (missionimpossible)': 'mp4', // MiTV4 65 (4pda)
    't95m': '', // (gmail)
    '32T18': '', // KTU84M (gmail, thailand)
    '': 'webm' // other devices (entry must be the last in the list)
};