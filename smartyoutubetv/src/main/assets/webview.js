// common.js must already connected

addExitEvent();

// some devices have buggy codec support, so disable them, device order is important
// codec exclusion list:
// X92 - webm
// MiTV3S-55 - mp4
// MiTv 3 60 - webm
// Q1EN.2004 Hi3798MV100 (china friend) - webm
// NG3128HD (github) - webm, mp4
// NEO-U1 (github) - webm
// all other devices (empty string) - webm
applyCodecFixes({'NG3128HD': 'webm, mp4', '': 'webm'});

fixOverlappedTextInRussian();

// 854x480, 640×360
// setNewDimensions(640, 360);