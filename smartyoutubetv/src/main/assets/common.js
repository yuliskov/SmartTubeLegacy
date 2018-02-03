function firstRun() {
    if (!arguments.callee.done)
        return arguments.callee.done = true;

    return false;
}

////////////////////////////////////////////

// detection is based on url hash change (http://mysite.com/path#another-path)
function delayTillElementBeInitializedNew(callback, testFn) {
    var res = testFn();
    if (res) {
        callback();
        return;
    }

    var delayFn = function(event) {
        var res = testFn();
        if (!res)
            return;

        console.log('delayTillElementBeInitialized: prepare to fire callback');

        // cleanup
        document.removeEventListener('hashchange', delayFn, false);
        // actual call
        callback();
    };

    document.addEventListener('hashchange', delayFn, false); // useCapture: true    
}

// detection is based on key events
function delayTillElementBeInitialized(callback, testFn) {
	var res = testFn();
	if (res) {
		callback();
		return;
	}

    var delayFn = function(event) {
        var up = 38;
        var down = 40;
        var left = 37;
        var right = 39;
        var esc = 27;
        var enter = 13;
        var keyCode = event.keyCode;

        if (keyCode != up && keyCode != down && keyCode != left && keyCode != right && keyCode != esc && keyCode != enter) {
            return;
        }

        setTimeout(function() {
            var res = testFn();
            if (!res)
                return;

            console.log('delayTillElementBeInitialized: prepare to fire callback');

            // cleanup
            document.removeEventListener('keydown', delayFn, true);
            // actual call
            callback();
        }, 500);
    };

	document.addEventListener('keydown', delayFn, true); // useCapture: true	
}

function getVideoPlayerPlayButton() {
	return document.querySelector('.icon-player-play');
}

function delayUntilPlayerBeInitialized(fn) {
    delayTillElementBeInitialized(fn, getVideoPlayerPlayButton);
}

function getExitDialogOKButton() {
	return document.getElementById('dialog-ok-button');
}

function delayUnitlExitDialogBeInitialized(fn) {
	delayTillElementBeInitialized(fn, getExitDialogOKButton);
}

///////////////////////////////////////////////

function addExitEvent() {
    console.log("addExitEvent");
	if (window.eventAdded)
        return;

    delayUnitlExitDialogBeInitialized(function() {
	    var element = getExitDialogOKButton();
	    
	    element.addEventListener('keydown', function (e) {
	        if (e.which == 13) { // click
	            e.preventDefault();
                console.log('before app.closeApp');
	            app.closeApp();
	        }
	    });
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

// returns true even when the second string is empty: strCmp('abc', '')
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


//////////// Helpers ////////////

function Helpers() {
    function isSelector(el) {
        return typeof el === 'string' || el instanceof String;
    }

    this.triggerEvent = function(element, type, keyCode) {
        var el = element;
        if (isSelector(element)) {
            el = this.$(element);
        }

        console.log("Helpers.triggerEvent: " + element + " " + type + " " + keyCode);

        if (!el) {
            console.warn("Helpers.triggerEvent: unable to find " + element);
            return;
        }

        if ('createEvent' in document) {
            // modern browsers, IE9+
            var e = document.createEvent('HTMLEvents');
            e.keyCode = keyCode;
            e.initEvent(type, false, true);
            el.dispatchEvent(e);
        } else {
            // IE 8
            var e = document.createEventObject();
            e.keyCode = keyCode;
            e.eventType = type;
            el.fireEvent('on'+e.eventType, e);
        }
    };

    this.$ = function(selector) {
        if (!isSelector(selector))
            return selector;
        return document.querySelectorAll(selector)[0];
    };

    this.triggerButton = function(keyCode) {
        var container = this.$(this.keysContainerSelector);
        var type = 'keydown';
        this.triggerEvent(container, type, keyCode);
    };

    this.triggerEnter = function(selector) {
        // simulate mouse/enter key press
        this.triggerEvent(selector, 'keyup', 13);
    };
}

window.helpers = new Helpers();

///////////  End Helpers //////////

function replaceOverlappedTextInRussian(paramButton) {
    var stringToFind = "Дополнительные параметры";
    var stringToFind2 = "Другие параметры";
    var stringToReplace = "Параметры";
    if (paramButton.innerHTML == stringToFind || paramButton.innerHTML == stringToFind2) {
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

function overrideProp(propStr, value) {
    var arr = propStr.split(".");      // Split the string using dot as separator
    var lastVal = arr.pop();       // Get last element
    var firstVal = arr.join(".");  // Re-join the remaining substrings, using dot as separatos

    Object.defineProperty(eval(firstVal), lastVal, { get: function(){return value}, configurable: true, enumerable: true });
}

function applyFakeResolution() {
    if (!app)
        return;
    
    // android resolution (can differ from physical resolution)
    var arr = app.getDeviceResolution().split('x');
    var w = arr[0], h = arr[1];
    
    // fake resolution (does't have influence on video resolution)
    // var w = 2560, h = 1440;

    overrideProp("window.innerWidth", w);
    overrideProp("window.innerHeight", h);

    // NOTE: there is no need to override props below

    // overrideProp("document.documentElement.clientWidth", w);
    // overrideProp("document.documentElement.clientHeight", h);

    // overrideProp("window.screen.availWidth", w);
    // overrideProp("window.screen.availHeight", h);

    // overrideProp("window.screen.width", w);
    // overrideProp("window.screen.height", h);
}

function fixWrongPixelRatio() {
    // WONT WORK PROPERLY
    
    // fix ugly Dimensions value like "950x640*2"
    window.devicePixelRatio = 1.0;
}

function commonLogs() {
    console.log("window.devicePixelRatio = " + window.devicePixelRatio);
    console.log("window.innerWidth = " + window.innerWidth);
    console.log("window.innerHeight = " + window.innerHeight);
    console.log("document.documentElement.clientWidth = " + document.documentElement.clientWidth);
    console.log("document.documentElement.clientHeight = " + document.documentElement.clientHeight);
    console.log("window.screen.availWidth = " + window.screen.availWidth);
    console.log("window.screen.availHeight = " + window.screen.availHeight);
    console.log("window.screen.width = " + window.screen.width);
    console.log("window.screen.height = " + window.screen.height);
}


function enableExternalKeyboard() {
    var searchSelector = '#search-input';

    var testFn = function() {
        var searchInput = helpers.$(searchSelector);
        return searchInput;
    }

    var callback = function() {
        var upKey = 38;
        var searchInput = helpers.$(searchSelector);
        helpers.triggerEvent(searchInput, 'keyup', upKey);
    }
    
    delayTillElementBeInitialized(callback, testFn);
}

////////////////////////////////////////////

delayUntilPlayerBeInitialized(fixOverlappedTextInRussian);
// applyFakeResolution();
fixWrongPixelRatio();
commonLogs();
enableExternalKeyboard();

console.log('injecting common.js into ' + document.location.href);