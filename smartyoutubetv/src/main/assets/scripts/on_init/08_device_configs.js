/**
 * Description: Contains codec configurations for different devices.
 * Format: {'device name': 'codecs to exclude', ...}
 * Device's name should be named accordingly to the CPU-Z.
 * You can find name here: CPU-Z > Device tab > Model field
 */

console.log("Scripts::Running script device_configs.js");

/**
 * Configuration for the WebView browser's engine (1th and 3rd launchers)
 */
var WebViewConfig = {
    'AOSP on p383 (p383)': 'mp4', // diqus
    'UNI (m201_th12)': 'mp4', // gmail
    // 'IPTV-1000': 'mp4', // https://github.com/yuliskov/SmartYouTubeTV/issues/119
    'MiBOX3S (queenchristina)': 'mp4', // https://github.com/yuliskov/SmartYouTubeTV/issues/124
    '(vbox86p)': 'mp4', // Genymotion virtual device
    'Amazon Jem (cm_jem)': '', // Kindle Fire HD 8.9
    'MiTV3S (pulpfiction)': 'mp4', // MiTV3S 55/60 (4pda)
    'OzoneHD (m201)': 'webm', // 4pda
    '3719C (Hi3719CV100)': 'webm', // SYTV > Disqus
    'MiTV3S-48 (hancock)': 'webm', // MiTV3S 48 (4pda)
    'MiBOX3 (JurassicPark)': 'webm', // Xiaomi mibox3 S905 (github)
    'MStar Android TV (aosp_ponkan32)': 'mp4', // ??? (gmail)
    'MiTV2-40 (hancock)': 'webm', // MiTV2 40" (gmail)
    'MiTV3S (missionimpossible)': 'mp4', // MiTV3 ???
    'MiTV4 (pulpfiction)': 'mp4', // MiTV4 (github > issues)
    'MiTV4A (matrix)': 'mp4', // MiTV4A (4pda)
    'MiTV4 (missionimpossible)': 'mp4', // MiTV4 65 (4pda)
    'NG3128HD': 'webm, mp4', // (github > issues)
    't95m': '', // (gmail)
    '32T18': '', // FAMILY GENERATION T18 BY (gmail)
    'mt5882': '', // Generic Android on mt5882 (gmail)
    'HnL11620 (m201)': 'mp4', // (gmail)
    'NEO-U1': 'webm', // (github)
    'Hi3798MV100': 'webm', // Q1EN.2004 (gmail, china)
    'X92': 'webm', // (gmail)
    '': 'webm' // all devices (entry must be last in the list)
};

/**
 * Configuration for the CrossWalk browser's engine (2nd and 4th launchers)
 */
var XWalkConfig = {
    'AOSP on p383 (p383)': 'mp4', // diqus
    'UNI (m201_th12)': 'mp4', // gmail
    '(vbox86p)': 'mp4', // Genymotion virtual device
    'Amazon Jem (cm_jem)': '', // Kindle Fire HD 8.9
    'MiTV3S (pulpfiction)': 'mp4', // MiTV3S 55/60 (4pda)
    'OzoneHD (m201)': 'webm', // 4pda
    '3719C (Hi3719CV100)': 'webm', // SYTV > Disqus
    'MiTV3S-48 (hancock)': 'webm', // MiTV3S 48 (4pda)
    'MiBOX3 (JurassicPark)': 'webm', // Xiaomi mibox3 S905 (github)
    'MStar Android TV (aosp_ponkan32)': 'mp4', // ??? (gmail)
    'MiTV3S-43 (hancock)': 'webm', // MiTV3S 43 ???
    'MiTV2-40 (hancock)': 'webm', // MiTV2 40" (gmail)
    'MiTV3S (missionimpossible)': 'mp4', // MiTV3 ???
    'MiTV4 (pulpfiction)': 'mp4', // MiTV4 (github > issues)
    'MiTV4A (matrix)': 'mp4', // MiTV4A (4pda)
    'MiTV4 (missionimpossible)': 'mp4', // MiTV4 65 (4pda)
    't95m': '', // (gmail)
    '32T18': '', // KTU84M (gmail, tailand)
    'Hi3798MV100': 'webm', // Q1EN.2004 (gmail, china)
    'NEO-U1': 'webm', // (github)
    'X92': 'webm', // (gmail)
    '': 'webm' // all devices (entry must be last in the list)
};