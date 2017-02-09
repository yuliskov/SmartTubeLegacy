// common.js must already connected

// some devices have buggy codec support, so disable them, device order is important
// codec exclusion list:
// X92 - webm
// other MiTV3 - webm
// Q1EN.2004 Hi3798MV100 (china friend) - webm

if (isThisDevice('X92', 'Q1EN', 'Hi3798MV100', 'MiTV')) {
	disableCodec('webm');
}

fixOverlappedTextInRussian();

// 854x480, 640×360
// setNewDimensions(640, 360);