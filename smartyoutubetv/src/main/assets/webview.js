//////////////////////////////////////////////

function addExitEvent() {
    if (window.eventAdded)
        return;

    console.log("addExitEvent");

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

addExitEvent();

// some devices have buggy codec support, so disable them, device order is important
// codec exclusion list:
// X92 - webm
// MiTV3S-55 - mp4
// other MiTV3 - webm
// our china friend: Q1EN.2004 Hi3798MV100 - ???
// applyCodecFixes({'X92': 'webm', 'MiTV3S-55': 'mp4', 'MiTV': 'webm'});
applyCodecFixes({'X92': 'webm', 'MiTV': 'webm', 'MiBOX': 'mp4'});