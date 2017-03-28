// common.js must already connected

// some devices have buggy codec support, so disable them, device order is important
// codec exclusion list:
// MiTV3S-55 - mp4 (missionimpossible)
// MiTV3S-60 - mp4 (pulpfiction)
// X92 - webm
// Q1EN.2004 Hi3798MV100 (china friend) - webm
// NEO-U1 (github) - webm
// t95m (gmail) - all
// KTU84M (gmail/tailand friend/jackie.hhop@gmail.com) - all
// 32T18 OF THE FAMILY GENERATION T18 BY (gmail/tailand friend/jackie.hhop@gmail.com) - all
// all other devices (empty string) - webm
applyCodecFixes({'t95m': '', '32T18': '', 'MiTV3S (missionimpossible)': 'mp4', 'MiTV3S (pulpfiction)': 'mp4', '': 'webm'});