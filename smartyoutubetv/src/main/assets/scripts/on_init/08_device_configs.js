/**
 * Description: Contains codec configurations for different devices.
 * Format: {'device name': 'disabled codec', ...}
 * Device's name should be named accordingly to the CPU-Z.
 * You can find name here: CPU-Z > Device tab > Model field
 */

console.log("Scripts::Running script device_configs.js");

/**
 * Configuration for the WebView browser's engine (1th and 3rd launchers)
 */
var WebViewConfig = {
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
 * Configuration for the CrossWalk browser's engine (2nd and 4th launchers)
 */
var XWalkConfig = {
    'amlogic': 'mp4', // amlogic boards have troubles with avc codec
    '(vbox86p)': 'mp4', // Genymotion virtual device
    'Amazon Jem (cm_jem)': '', // Kindle Fire HD 8.9 (enable all formats)
    'MiTV3S (pulpfiction)': 'mp4', // MiTV3S 55/60 (4pda)
    'MStar Android TV (aosp_ponkan32)': 'mp4', // ??? (gmail)
    'MiTV3S (missionimpossible)': 'mp4', // MiTV3 ???
    'MiTV4A (matrix)': 'mp4', // MiTV4A (4pda)
    'MiTV4 (missionimpossible)': 'mp4', // MiTV4 65 (4pda)
    't95m': '', // (gmail)
    '32T18': '', // KTU84M (gmail, tailand)
    '': 'webm' // other devices (entry must be the last in the list)
};