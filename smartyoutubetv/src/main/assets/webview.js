function addExitEvent() {
    if (window.eventAdded)
        return;

    var element = document.getElementById('dialog-ok-button');
    element.addEventListener('keydown', function (e) {
        if (e.which == 13) {
            e.preventDefault();
            app.closeApp();
        }
    });
    window.eventAdded = true;
}

///////////////////////////////////////////

// codec exclusion list:
// X92 - webm
// MiTV3S-55 - mp4
// other MiTV3 - webm
// our china friend: Q1EN.2004 Hi3798MV100 - ???

function applyCodecFixes() {
	var thisDevice = app.getDeviceName();
	console.log('applyCodecFixes to ' + thisDevice);
	// some devices have buggy codec support, so disable them
    // device order is important
	var deviceMap = {'X92': 'webm', 'MiTV3S-55': 'mp4', 'MiTV': 'webm'};
	for (var device in deviceMap) {
		if (deviceMatch(thisDevice, device)) {
			disableCodec(deviceMap[device]);
            break;
        }
	}
}

function deviceMatch(thisDevice, device) {
	return strCmp(thisDevice, device);
}

function disableCodec(codec) {
	if (!codec)
		return;
    if (!window.MediaSource)
        return;

    console.log('disableCodec ' + codec);

    window.MediaSource.isTypeSupported = function(native) {
        return function(str) {
            if (strCmp(str, codec)) 
                return false;
            return native.call(window.MediaSource, str);
        }
    }(window.MediaSource.isTypeSupported);
}

function strCmp(str1, str2) {
	str1 = str1.toLowerCase();
	str2 = str2.toLowerCase();
	return str1.indexOf(str2) >= 0;
}


/////////////////////////////////////////

addExitEvent();
applyCodecFixes();