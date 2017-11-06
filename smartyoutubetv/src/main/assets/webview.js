// common.js must already connected

addExitEvent();

// some devices have buggy codec support, so disable them
applyCodecFixes({
    '3719C (Hi3719CV100)': 'webm', // SYTV > Disqus
    'MiTV3S-48 (hancock)': 'webm', // MiTV3S 48 (4pda)
    'MiBOX3 (JurassicPark)': 'webm', // Xiaomi mibox3 S905 (github)
    'MStar Android TV (aosp_ponkan32)': 'mp4', // ??? (gmail)
    'MiTV2-40 (hancock)': 'webm', // MiTV2 40" (gmail)
    'MiTV3S (missionimpossible)': 'mp4', // MiTV3 ???
    'MiTV3S (pulpfiction)': 'mp4', // MiTV3S 55/60 (4pda)
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
});

console.log('webview.js is starting...');
