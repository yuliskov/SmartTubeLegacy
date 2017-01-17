///////////////////////////////////////////

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

disableCodec("webm");