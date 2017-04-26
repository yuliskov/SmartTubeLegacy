// common.js must already connected

addExitEvent();

// some devices have buggy codec support, so disable them, device order is important
// codec exclusion list:
// X92 - webm
// MiTV3S-55 - mp4
// MiTV3S-60 - mp4
// MiTv 3 60 - webm
// Q1EN.2004 Hi3798MV100 (china friend) - webm
// NG3128HD (github) - webm, mp4
// NEO-U1 (github) - webm
// t95m (gmail) - all
// HnL11620 (m201) (gmail) - mp4
// Generic Android on mt5882 (gmail/tailand friend/jackie.hhop@gmail.com) - all
// 32T18 OF THE FAMILY GENERATION T18 BY (gmail/tailand friend/jackie.hhop@gmail.com) - all
// all other devices (empty string) - webm
applyCodecFixes({'NG3128HD': 'webm, mp4', 't95m': '', '32T18': '', 'MiTV3S (missionimpossible)': 'mp4', 'MiTV3S (pulpfiction)': 'mp4', 'HnL11620 (m201)': 'mp4', '': 'webm'});
