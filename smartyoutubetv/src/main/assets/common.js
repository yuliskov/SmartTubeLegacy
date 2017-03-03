function firstRun() {
    if (!arguments.callee.done)
        return arguments.callee.done = true;

    return false;
}

////////////////////////////////////////////

function waitBeforeInit(fn) {
    var progress = document.querySelector('#progress-bar');
    var onfocus = function(){fn(); progress.removeEventListener('focus', onfocus)}
    progress.addEventListener('focus', onfocus);
}

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

////////////////////////////////////////////////

function applyCodecFixes(deviceMap) {
    for (var device in deviceMap) {
        if (isThisDevice(device)) {
        	var codecs = deviceMap[device].split(',');
        	var codecsLen = codecs.length;
        	for (var i = 0; i < codecsLen; i++) {
	            disableCodec(codecs[i].trim());
        	}
            break;
        }
    }
}

// variable number of arguments
function isThisDevice() {
	if (arguments.length == 0)
		return false;
	if (!window.thisDevice)
		window.thisDevice = app.getDeviceName();
	var argsLen = arguments.length;
	for (var i = 0; i < argsLen; i++) {
		var device = arguments[i];
		if (strCmp(window.thisDevice, device)) {
			return true;
		}
	}
    return false;
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

    console.log('disableCodec: ' + codec + ' ' + window.thisDevice);

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

/////////////////////////////////////////////////

function overrideProp(obj, propName, propValue) {
    Object.defineProperty(obj, propName, {
        get: function() { return propValue; },
        set: function(newValue) { ; },
        enumerable: true,
        configurable: true
    });
}

function setNewDimensions(width, height) {
    var newWidth = width;
    var newHeight = height;
    
    overrideProp(window.screen, 'availHeight', newHeight);
    overrideProp(window.screen, 'availWidth', newWidth);
    overrideProp(window.screen, 'height', newHeight);
    overrideProp(window.screen, 'width', newWidth);
}

/////////////////////////////////////////////////

var observeDOM = (function(){
    var MutationObserver = window.MutationObserver || window.WebKitMutationObserver,
        eventListenerSupported = window.addEventListener;

    return function(obj, callback){
        if( MutationObserver ){
            // define a new observer
            var obs = new MutationObserver(function(mutations, observer){
                if( mutations[0].addedNodes.length || mutations[0].removedNodes.length )
                    callback(obj);
            });
            // have the observer observe foo for changes in children
            obs.observe( obj, { childList:true, subtree:true });
        }
        else if( eventListenerSupported ){
            obj.addEventListener('DOMNodeInserted', callback, false);
            obj.addEventListener('DOMNodeRemoved', callback, false);
        }
    }
})();

//////////////////////////////////////////////

function replaceOverlappedTextInRussian(paramButton) {
    var stringToFind = "Дополнительные параметры";
    var stringToReplace = "Параметры";
    if (paramButton.innerHTML == stringToFind) {
        paramButton.innerHTML = stringToReplace;
        return true;
    }
    return false;
}

function doObserveOverlappedTextInRussian(paramButton) {
    observeDOM(paramButton, function(el) {
        console.log('dom changed, ' + el);
        replaceOverlappedTextInRussian(el);
	});	
}

function fixOverlappedTextInRussian() {
    console.log("fixOverlappedTextInRussian");
	var paramButton = document.getElementById("transport-more-button").children[0];
    if (replaceOverlappedTextInRussian(paramButton))
    	doObserveOverlappedTextInRussian(paramButton);
}

////////////////////////////////////////////

waitBeforeInit(fixOverlappedTextInRussian);