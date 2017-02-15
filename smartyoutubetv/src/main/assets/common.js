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
    // encodeURI("Дополнительные параметры")
    var stringToFind = "%D0%94%D0%BE%D0%BF%D0%BE%D0%BB%D0%BD%D0%B8%D1%82%D0%B5%D0%BB%D1%8C%D0%BD%D1%8B%D0%B5%20%D0%BF%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80%D1%8B";
    // encodeURI("Параметры")
    var stringToReplace = "%D0%9F%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80%D1%8B";
    if (paramButton.innerHTML == decodeURI(stringToFind)){
        paramButton.innerHTML = decodeURI(stringToReplace);
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

/////////////////////////////////////////////////////

function createElement(html) {
    var div = document.createElement('div');
    div.innerHTML = html;
    return div.firstChild;
}

function addQualityControls() {
    if (window.addQualityControlsDone)
        return;

    // jQuery here has very limited functionality
    var jQuery = $;

    // toggle-selected - when not collapsed
    var qualityToggle = createElement(
    '<div id="transport-more-button" class="toggle-button" tabindex="-1"> \
        <span>Quality Options</span> \
    </div>');
    var qualityButtonRow = createElement(
    '<div id="buttons-list" class=" list" data-enable-sounds="false" tabindex="-1"> \
        <div class="toggle-button" tabindex="-1">240p</div> \
        <div class="toggle-button" tabindex="-1">360p</div> \
        <div class="toggle-button" tabindex="-1">480p</div> \
        <div class="toggle-button" tabindex="-1">720p</div> \
        <div class="toggle-button" tabindex="-1">1080p</div> \
    </div>');
    
    jQuery('.controls-row').append(qualityToggle);

    var backup;
    qualityToggle.addEventListener('click', function(event) {
        if (backup) {
            var elems = backup;
            backup = null;
        } else {
            backup = jQuery('#buttons-list');
            var elems = qualityButtonRow;
        }
        jQuery('#buttons-list').remove();
        jQuery('.controls-row').prepend(elems);

        // problem: can't stop propagation
        event.stopPropagation();
        event.preventDefault();
    });

    window.addQualityControlsDone = true;
}

///////////////////////////////////////////////////