// common.js must already connected

// some devices have buggy codec support, so disable them
applyCodecFixes({
	'MiTV3S-43 (hancock)': 'webm', // MiTV3S 43" ???
	'MiTV2-40 (hancock)': 'webm', // MiTV2 40" (gmail)
	'MiTV3S (missionimpossible)': 'mp4', // MiTV3 ???
	'MiTV3S (pulpfiction)': 'mp4', // MiTV3S 55/60 (4pda)
	'MiTV4 (pulpfiction)': 'mp4', // MiTV4 (github > issues)
	't95m': '', // (gmail)
	'32T18': '', // KTU84M (gmail, tailand)
	'Hi3798MV100': 'webm', // Q1EN.2004 (gmail, china)
	'NEO-U1': 'webm', // (github)
	'X92': 'webm', // (gmail)
	'': 'webm' // all devices (entry must be last in the list)
});

console.log('xwalk.js is starting...');