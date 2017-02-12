// common.js must already connected

// some devices have buggy codec support, so disable them, device order is important
// codec exclusion list:
// X92 - webm
// Q1EN.2004 Hi3798MV100 (china friend) - webm
// NEO-U1 (github) - webm
// all other devices (empty string) - webm
applyCodecFixes({'': 'webm'});

fixOverlappedTextInRussian();

// 854x480, 640×360
// setNewDimensions(640, 360);