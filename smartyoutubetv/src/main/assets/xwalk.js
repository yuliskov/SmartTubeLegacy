// common.js must already connected

// some devices have buggy codec support, so disable them
applyCodecFixes({
    '3719C (Hi3719CV100)': 'mp4', // SYTV > Disqus
    'MiTV3S-48 (hancock)': 'webm', // MiTV3S 48 (4pda)
    'MiBOX3 (JurassicPark)': 'webm', // Xiaomi mibox3 S905 (github)
    'MStar Android TV (aosp_ponkan32)': 'mp4', // ??? (gmail)
    'MiTV3S-43 (hancock)': 'webm', // MiTV3S 43 ???
    'MiTV2-40 (hancock)': 'webm', // MiTV2 40" (gmail)
    'MiTV3S (missionimpossible)': 'mp4', // MiTV3 ???
    'MiTV3S (pulpfiction)': 'mp4', // MiTV3S 55/60 (4pda)
    'MiTV4 (pulpfiction)': 'mp4', // MiTV4 (github > issues)
    'MiTV4A (matrix)': 'mp4', // MiTV4A (4pda)
    'MiTV4 (missionimpossible)': 'mp4', // MiTV4 65 (4pda)
    't95m': '', // (gmail)
    '32T18': '', // KTU84M (gmail, tailand)
    'Hi3798MV100': 'webm', // Q1EN.2004 (gmail, china)
    'NEO-U1': 'webm', // (github)
    'X92': 'webm', // (gmail)
    '': 'webm' // all devices (entry must be last in the list)
});

console.log('xwalk.js is starting...');