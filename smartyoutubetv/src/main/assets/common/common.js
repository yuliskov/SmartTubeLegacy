function firstRun() {
    if (!arguments.callee.done)
        return arguments.callee.done = true;

    return false;
}

////////////////////////////////////////////

// detection is based on url hash change (http://mysite.com/path#another-path)
// NOTE: fired too lately and doesn't work in all cases (like exit btn)
function delayTillElementBeInitializedOnHash(callback, testFn, runOnce) {
    var res = testFn();
    if (res) {
        callback();
        if (runOnce)
            return;
    }

    function delayFn(event) {
        setTimeout(function() { // wait till some elms be initialized like exit btn, etc
            var res = testFn();
            if (!res)
                return;

            console.log('delayTillElementBeInitialized: prepare to fire callback: ' + callback.toString().slice(0, 50));

            // cleanup
            if (runOnce) {
                console.log('delayTillElementBeInitialized: removing callback: ' + callback.toString().slice(0, 50));
                window.removeEventListener('hashchange', delayFn, false); // useCapture: false
            }
            // actual call
            callback();

        }, 500);
    }

    window.addEventListener('hashchange', delayFn, false); // useCapture: false
}

// detection is based on key events
// combined detection: first by interval then by key in sake of performance
function delayTillElementBeInitialized(callback, testFn, runOnce) {
	var res = testFn();
	if (res) {
		callback();
        if (runOnce)
            return;
	}

    var delayFnKey = function(event) {
        clearInterval(interval); // remove concurrent event

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

        setTimeout(function() { // wait till some elms be initialized like exit btn, etc
            var res = testFn();
            if (!res)
                return;

            // console.log('delayTillElementBeInitialized2: onkeydown: prepare to fire callback: ' + callback.toString().slice(0, 50));

            // cleanup
            if (runOnce) {
                console.log('delayTillElementBeInitialized: onkeydown: removing callback: ' + callback.toString().slice(0, 50));
                document.removeEventListener('keydown', delayFnKey, true);
            }
            // actual call
            callback();
        }, 500);
    };

    var delayFnInt = function() {
        setTimeout(function() { // wait till some elms be initialized like exit btn, etc
            var res = testFn();
            if (!res)
                return;

            console.log('common.js: delayTillElementBeInitialized: interval: prepare to fire callback: ' + callback.toString().slice(0, 50));

            // cleanup
            if (runOnce) {
                console.log('delayTillElementBeInitialized: interval: removing callback: ' + callback.toString().slice(0, 50));
                clearInterval(interval);
            }

            // actual call
            callback();
        }, 500);
    };

    // concurrent triggers (only one left in the end)
	document.addEventListener('keydown', delayFnKey, true); // useCapture: true
    var interval = setInterval(delayFnInt, 500);
}

function getVideoPlayerPlayButton() {
	return document.querySelector('.icon-player-play');
}

function getExitDialogOKButton() {
    return document.getElementById('dialog-ok-button');
}

function delayUntilPlayerBeInitialized(fn, runOnce) {
    delayTillElementBeInitialized(fn, getVideoPlayerPlayButton, runOnce);
}

function delayUntilExitDialogBeInitialized(fn) {
	delayTillElementBeInitialized(fn, getExitDialogOKButton, true);
}

///////////////////////////////////////////////

// function addExitEvent() {
//     console.log("addExitEvent");
// 	if (window.eventAdded)
//         return;
//
//     delayUntilExitDialogBeInitialized(function() {
// 	    var element = getExitDialogOKButton();
//
// 	    element.addEventListener('keydown', function (e) {
// 	        if (e.which == 13) { // click
// 	            e.preventDefault();
//                 console.log('before app.closeApp');
// 	            app.closeApp();
// 	        }
// 	    });
//     });
//
//     window.eventAdded = true;
// }

//// CODEC FIX

// function applyCodecFixes(deviceMap) {
//     for (var device in deviceMap) {
//         if (isThisDevice(device)) {
//         	var codecs = deviceMap[device].split(',');
//         	var codecsLen = codecs.length;
//         	for (var i = 0; i < codecsLen; i++) {
// 	            disableCodec(codecs[i].trim());
//         	}
//             break;
//         }
//     }
// }
//
// // variable number of arguments
// function isThisDevice() {
// 	if (arguments.length == 0)
// 		return false;
// 	if (!window.thisDevice)
// 		window.thisDevice = app.getDeviceName();
// 	var argsLen = arguments.length;
// 	for (var i = 0; i < argsLen; i++) {
// 		var device = arguments[i];
// 		if (strCmp(window.thisDevice, device)) {
// 			return true;
// 		}
// 	}
//     return false;
// }
//
// function disableCodec(codec) {
//     if (!codec) {
//         console.log('disableCodec: codec is null');
//         return;
//     }
//     if (!window.MediaSource) {
//         console.log('disableCodec: MediaSource is null');
//         return;
//     }
//
//     console.log('disableCodec: ' + codec + ' ' + window.thisDevice);
//
//     window.MediaSource.isTypeSupported = function(native) {
//         return function(str) {
//             if (strCmp(str, codec))
//                 return false;
//             return native.call(window.MediaSource, str);
//         }
//     }(window.MediaSource.isTypeSupported);
// }
//
// // returns true even when the second string is empty: strCmp('abc', '')
// function strCmp(str1, str2) {
//     str1 = str1.toLowerCase();
//     str2 = str2.toLowerCase();
//     return str1.indexOf(str2) >= 0;
// }

//// END CODEC FIX

// var observeDOM = (function(){
//     var MutationObserver = window.MutationObserver || window.WebKitMutationObserver,
//         eventListenerSupported = window.addEventListener;
//
//     return function(obj, callback){
//         if( MutationObserver ){
//             // define a new observer
//             var obs = new MutationObserver(function(mutations, observer){
//                 if( mutations[0].addedNodes.length || mutations[0].removedNodes.length )
//                     callback(obj);
//             });
//             // have the observer observe foo for changes in children
//             obs.observe( obj, { childList:true, subtree:true });
//         }
//         else if( eventListenerSupported ){
//             obj.addEventListener('DOMNodeInserted', callback, false);
//             obj.addEventListener('DOMNodeRemoved', callback, false);
//         }
//     }
// })();


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
            e.initEvent(type, true, false);
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

    this.appendHtml = function(el, str) {
        var div = document.createElement('div');
        div.innerHTML = str;

        var child;
        while (div.children.length > 0) {
            child = el.appendChild(div.children[0]);
        }
        return child;
    };
}

window.helpers = new Helpers();

///////////  End Helpers //////////

// function replaceOverlappedTextInRussian(paramButton) {
//     var stringToFind = "Дополнительные параметры";
//     var stringToFind2 = "Другие параметры";
//     var stringToReplace = "Параметры";
//     if (paramButton.innerHTML == stringToFind || paramButton.innerHTML == stringToFind2) {
//         paramButton.innerHTML = stringToReplace;
//         return true;
//     }
//     return false;
// }
//
// function doObserveOverlappedTextInRussian(paramButton) {
//     observeDOM(paramButton, function(el) {
//         console.log('dom changed, ' + el);
//         replaceOverlappedTextInRussian(el);
// 	});
// }
//
// function fixOverlappedTextInRussian() {
//     // console.log("fixOverlappedTextInRussian");
// 	var paramButton = document.getElementById("transport-more-button").children[0];
//     if (replaceOverlappedTextInRussian(paramButton))
//     	doObserveOverlappedTextInRussian(paramButton);
// }

////////////////////////////////////////////

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


// function enableExternalKeyboard() {
//     var searchSelector = '#search-input';
//
//     var testFn = function() {
//         return helpers.$(searchSelector);
//     };
//
//     var callback = function() {
//         var upKey = 38;
//         var searchInput = helpers.$(searchSelector);
//         helpers.triggerEvent(searchInput, 'keyup', upKey);
//     };
//
//     delayTillElementBeInitialized(callback, testFn);
// }

////////////////////////////////////////////

function init() {
    // delayUntilPlayerBeInitialized(fixOverlappedTextInRussian);
    commonLogs();
    // enableExternalKeyboard();
    console.log('injecting common.js into ' + document.location.href);
}

function waitTillInit(modName, callback, depName) {
    if (window[modName] === 'ok') {
        console.log(modName + ': module already initialized');
        return;
    }

    window[modName] = 'ok';

    if (window[depName] || !depName) {
        console.log(modName + ': all deps initialized. perform callback');
        callback();
        return;
    }

    var interval = setInterval(function() {
        console.log(modName + ': check that all deps are initialized');
        if (window[depName]) {
            console.log(modName + ': all deps initialized. perform callback');
            callback();
            clearInterval(interval);
        }
    }, 100);
}

waitTillInit('common.js', init);

