///////////////////////////////////////////

function applyCodecFixes(deviceMap) {
    var thisDevice = app.getDeviceName();
    console.log('applyCodecFixes to ' + thisDevice);
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
    if (!codec) {
        console.log('disableCodec: codec is null');
        return;
    }
    if (!window.MediaSource) {
        console.log('disableCodec: MediaSource is null');
        return;
    }

    console.log('disableCodec: ' + codec);

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

// some devices have buggy codec support, so disable them
// device order is important
// codec exclusion list:
// MiTV2 - webm
applyCodecFixes({'MiTV': 'webm'});