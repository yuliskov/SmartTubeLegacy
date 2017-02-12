// common.js must already connected

// some devices have buggy codec support, so disable them, device order is important
// codec exclusion list:
// X92 - webm
// Q1EN.2004 Hi3798MV100 (china friend) - webm
// other devices - webm
applyCodecFixes({'X92': 'webm', 'Q1EN': 'webm', 'Hi3798MV100': 'webm', '': 'webm'});

fixOverlappedTextInRussian();

// 854x480, 640×360
// setNewDimensions(640, 360);