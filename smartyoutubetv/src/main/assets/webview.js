// common.js must already connected

addExitEvent();

// some devices have buggy codec support, so disable them, device order is important
// codec exclusion list:
// X92 - webm
// MiTV3S-55 - mp4
// other MiTV3 - webm
// our china friend: Q1EN.2004 Hi3798MV100 - ???
// applyCodecFixes({'X92': 'webm', 'MiTV3S-55': 'mp4', 'MiTV': 'webm'});
applyCodecFixes({'X92': 'webm', 'MiTV': 'webm'});

fixOverlappedTextInRussian();

// 854x480, 640×360
// setNewDimensions(640, 360);