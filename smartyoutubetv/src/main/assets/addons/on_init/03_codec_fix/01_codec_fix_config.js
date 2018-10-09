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
    // 'amlogic': 'mp4', // be careful: not all amlogic devices have troubles playing mp4 (issue #162)
    'X96mini (p281)': 'mp4', // amlogic: Telegram group
    'AOSP on p383 (p383)': 'mp4', // amlogic: disqus
    'UNI (m201_th12)': 'mp4', // amlogic: gmail
    'MiBOX3S (queenchristina)': 'mp4', // amlogic: https://github.com/yuliskov/SmartYouTubeTV/issues/124
    'MiTV4 (pulpfiction)': 'mp4', // amlogic: MiTV4 (github > issues)
    'HnL11620 (m201)': 'mp4', // amlogic: (gmail)
    'IPTV-1000': 'mp4', // https://github.com/yuliskov/SmartYouTubeTV/issues/119
    '(vbox86p)': 'mp4', // Genymotion virtual device
    'MiTV3S (pulpfiction)': 'mp4', // MiTV3S 55/60 (4pda)
    'MStar Android TV (aosp_ponkan32)': 'mp4', // ??? (gmail)
    'MiTV3S (missionimpossible)': 'mp4', // MiTV3 ???
    'MiTV4 (missionimpossible)': 'mp4', // MiTV4 65 (4pda)
    'NG3128HD': 'webm, mp4', // (github > issues)
    'Amazon Jem (cm_jem)': '', // Kindle Fire HD 8.9 (enable all formats)
    't95m': '', // (gmail)
    '32T18': '', // FAMILY GENERATION T18 BY (gmail)
    'mt5882': '', // Generic Android on mt5882 (gmail)
    // 'MiTV4A (matrix)': 'mp4', // amlogic: MiTV4A 43 (4pda)
    // 'MiTV2-40 (hancock)': 'webm', // entrapment: MiTV2 40" (gmail)
    // 'OzoneHD (m201)': 'webm', // 4pda
    // '3719C (Hi3719CV100)': 'webm', // SYTV > Disqus
    // 'MiTV3S-48 (hancock)': 'webm', // MiTV3S 48 (4pda)
    // 'MiBOX3 (JurassicPark)': 'webm', // Xiaomi mibox3 S905 (github)
    // 'NEO-U1': 'webm', // (github)
    // 'Hi3798MV100': 'webm', // Q1EN.2004 (gmail, china)
    // 'X92': 'webm', // (gmail)
    '': 'webm' // other devices (entry should be the last in the list)
};

/**
 * Config for the CrossWalk browser's engine (2nd launcher)
 */
var CodecConfig_XWalk = {
    // 'amlogic': 'mp4', // be careful: not all amlogic devices have troubles playing mp4 (issue #162)
    'X96mini (p281)': 'mp4', // amlogic: Telegram group
    'AOSP on p383 (p383)': 'mp4', // amlogic: disqus
    'MiTV4 (pulpfiction)': 'mp4', // amlogic: MiTV4 (github > issues)
    'UNI (m201_th12)': 'mp4', // gmail
    '(vbox86p)': 'mp4', // Genymotion virtual device
    'MiTV3S (pulpfiction)': 'mp4', // MiTV3S 55/60 (4pda)
    'MStar Android TV (aosp_ponkan32)': 'mp4', // ??? (gmail)
    'MiTV3S (missionimpossible)': 'mp4', // MiTV3 ???
    'MiTV4 (missionimpossible)': 'mp4', // MiTV4 65 (4pda)
    'Amazon Jem (cm_jem)': '', // Kindle Fire HD 8.9 (enable all formats)
    't95m': '', // (gmail)
    '32T18': '', // KTU84M (gmail, thailand)
    // 'MiTV4A (matrix)': 'mp4', // amlogic: MiTV4A 43 (4pda)
    // 'MiTV3S-43 (hancock)': 'webm', // MiTV3S 43 ???
    // 'MiTV2-40 (hancock)': 'webm', // entrapment: MiTV2 40" (gmail)
    // 'OzoneHD (m201)': 'webm', // 4pda
    // '3719C (Hi3719CV100)': 'webm', // SYTV > Disqus
    // 'MiTV3S-48 (hancock)': 'webm', // MiTV3S 48 (4pda)
    // 'MiBOX3 (JurassicPark)': 'webm', // Xiaomi mibox3 S905 (github)
    // 'Hi3798MV100': 'webm', // Q1EN.2004 (gmail, china)
    // 'NEO-U1': 'webm', // (github)
    // 'X92': 'webm', // (gmail)
    '': 'webm' // other devices (entry should be the last in the list)
};