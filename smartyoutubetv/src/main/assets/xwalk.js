// common.js must already connected

// some devices have buggy codec support, so disable them
// device order is important
// codec exclusion list:
// MiTV2 - webm
applyCodecFixes({'MiTV': 'webm'});

fixOverlappedTextInRussian();

// 854x480, 640×360
// setNewDimensions(640, 360);