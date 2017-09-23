//////// Event Handling ////////

// run event on document object
function fireEvent(data, type) {
    var event; // The custom event that will be created

    if (document.createEvent) {
        event = document.createEvent("HTMLEvents");
        event.initEvent(type, true, true);
    } else {
        event = document.createEventObject();
        event.eventType = type;
    }

    event.eventName = type;
    event.data = data;

    if (document.createEvent) {
        document.dispatchEvent(event);
    } else {
        document.fireEvent("on" + event.eventType, event);
    }
}

//////// End Event Handling ///////

//////// Localization //////////

function getCurrentLanguage() {
    var language = navigator.languages && navigator.languages[0] || // Chrome / Firefox
               navigator.language ||   // All browsers
               navigator.userLanguage; // IE <= 10

    return language;
}

function detectLanguage() {
    var language = getCurrentLanguage();
    return language.split('-')[0].toLowerCase();
}

function base64Decode(str) {
    var decoded = str;
    try {
        decoded = window.atob(str);
    } catch (e) {
    }
    return decoded;
}

function localize(str) {
    var ruHash = {
        'Quality': "Качество", 
    };
    var userLang = detectLanguage();
    console.log("current language is " + userLang + " " + str);
    var curHash = {}; // don't translate if language not detected
    switch (userLang) {
        case "ru":
        case "uk":
            curHash = ruHash;
            break;
    }
    var result = curHash[str] || str;
    return result;
}

//////// End Localization //////////

///// Helpers //////

function firstRun() {
    if (!arguments.callee.done)
        return arguments.callee.done = true;

    return false;
}

function setOnInterfaceVisible(fn) {
    var up = 38;
    var down = 40;
    var left = 37;
    var right = 39;
    var enter = 13;
    var esc = 27;
    var container = document.getElementById('watch');
    container.addEventListener('keydown', function(event){
        var keyCode = event.keyCode;

        console.log('setOnInterfaceVisible pre handler called');
        var mainRow = querySelector('#transport-controls');
        var firstMoveSet = keyCode == enter || keyCode == esc;
        var secondMoveSet = keyCode == left || keyCode == right || keyCode == up || keyCode == down;
        var isInterfaceVisible = !hasClass(mainRow, 'hidden');
        if (isInterfaceVisible && firstMoveSet)
            return;
        if (!isInterfaceVisible && secondMoveSet)
            return;

        console.log('setOnInterfaceVisible handler called');
        fn(event);
    });
}

function setVisibility(obj, isVisible) {
    if (obj == null)
        return;
    obj.style.display = isVisible ? '' : 'none';
}

function setEnabled(obj, isEnabled) {
    if (obj == null)
        return;
    isEnabled ? removeClass(obj, 'disabled') : addClass(obj, 'disabled');
}

/////////////////////////////////////////////////

function delayTillElementBeInitialized(callback, testFn) {
	var res = testFn();
	if (res) {
        console.log('delayTillElementBeInitialized: prepare to fire callback');
		callback();
		return;
	}

	document.addEventListener('keydown', function delayTillElementBeInitializedListener(event){
	    var up = 38;
	    var down = 40;
	    var esc = 27;
	    var enter = 13;
	    var keyCode = event.keyCode;

	    if (keyCode != up && keyCode != down && keyCode != esc && keyCode != enter) {
	        return;
	    }

    	setTimeout(function() {
    		var res = testFn();
	    	if (!res)
	    		return;

	    	console.log('delayTillElementBeInitialized: prepare to fire callback');

	    	// cleanup
		    document.removeEventListener('keydown', delayTillElementBeInitializedListener);
		    // actual call
		    callback();
    	}, 500);
    });	
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


/////////////////////////////

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


///////////////////////////

function createElement(html) {
    var div = document.createElement('div');
    div.innerHTML = html;
    return div.firstChild;
}

function addClass(el, className) {
    if (el == null)
        return;
    if (el.classList) {
        var names = className.split(' ');
        for (var i = 0; i < names.length; i++) {
            el.classList.add(names[i]);
        }
    } else {
      el.className += ' ' + className;
    }
}

function removeClass(el, className) {
    if (el == null)
        return;
    if (el.classList) {
        var names = className.split(' ');
        for (var i = 0; i < names.length; i++) {
            el.classList.remove(names[i]);
        }
    } else {
        el.className = el.className.replace(new RegExp('(^|\\b)' + className.split(' ').join('|') + '(\\b|$)', 'gi'), ' ');
    }
}

function hasClass( elem, klass ) {
     return (" " + elem.className + " " ).indexOf( " "+klass+" " ) > -1;
}

function hasId( elem, id ) {
     return (" " + elem.id + " " ).indexOf( " " + id + " " ) > -1;
}

//Where el is the DOM element you'd like to test for visibility
function isHidden(el) {
    return (el.offsetParent === null)
}

function isInterfaceHidden() {
	var controls = document.querySelector('#transport-controls');
	return hasClass(controls, 'hidden');
}

function addEventListenerAll(type, fn, objArr) {
    for (var i = 0; i < objArr.length; i++) {
        objArr[i].addEventListener(type, fn);
    }
}

function removeEventListenerAll(type, fn, objArr) {
    for (var i = 0; i < objArr.length; i++) {
        objArr[i].removeEventListener(type, fn);
    }
}

function append(parent, elem) {
    parent.appendChild(elem);
}

function querySelector(selector) {
    return document.querySelector(selector);
}

function querySelectorAll(selector) {
    return document.querySelectorAll(selector);
}

function remove(el) {
    if (!el)
        return;

    el.parentNode.removeChild(el);
}

function prepend(parent, elems) {
    if (!parent || !elems)
        return;

    parent.insertBefore(elems, parent.firstChild);
}

function put(key, value) {
    if (!window.globalMap)
        window.globalMap = {};

    window.globalMap[key] = value;
}

function get(key) {
    if (!window.globalMap)
        return null;

    return window.globalMap[key];
}

function removeEventListenerAll(el, type, fnArr) {
    for (var i = 0; i < fnArr.length; i++) {
        el.removeEventListener(type, fnArr[i]);
    }
}

function addEventListenerAll(el, type, fnArr) {
    for (var i = 0; i < fnArr.length; i++) {
        el.addEventListener(type, fnArr[i]);
    }
}

////// End Helpers //////

function createQualityToggleButton() {
    return createElement(
    '<div id="transport-more-button" class="my-button toggle-button quality-toggle-buton" tabindex="-1"> \
        <span>' + localize('Quality') + '</span> \
    </div>');
}


function createQualityButtonsRow2(videoFormats) {
    // NOTE: styling buttons-list: fixing obvious layout mess
    var container = createElement('<div id="buttons-list" class=" list" data-enable-sounds="false" tabindex="-1" style="position: relative; overflow: hidden; height: 100%;"></div>');
    var container2 = createElement('<div class="new-list-container horizontal" style="margin-left: 0em;"></div>');
    for (var idx in videoFormats) {
        var textColor = '';
        var disabledClass = '';
        if (videoFormats[idx].selected){
            textColor = 'color: red';
            disabledClass = 'disabled';
        }
        var el = createElement('<div class="my-button toggle-button ' + disabledClass + '" tabindex="-1" data-format-name="' + videoFormats[idx].name + '" style="min-width: 2.3em; width: initial; ' + textColor + '">' + videoFormats[idx].name + '</div>')
        append(container2, el);
        append(container, container2);
    }

    return container;
}

// function createQualityButtonsRow() {
//     return createElement(
//     '<div id="buttons-list" class=" list" data-enable-sounds="false" tabindex="-1"> \
//         <div class="new-list-container horizontal" style="margin-left: 0em;"> \
//             <div class="toggle-button" tabindex="-1" data-itag="278" style="min-width: 2.3em; width: initial;">144p</div> \
//             <div class="toggle-button" tabindex="-1" data-itag="134" style="min-width: 2.3em; width: initial;">360p</div> \
//             <div class="toggle-button" tabindex="-1" data-itag="136" style="min-width: 2.3em; width: initial;">720p</div> \
//             <div class="toggle-button" tabindex="-1" data-itag="137" style="min-width: 2.3em; width: initial;">1080p</div> \
//         </div> \
//     </div>');
// }

///// Remote Controller Navigation Handling /////

function sortButtons(nodes) {
    // sort buttons based on horizontal position on screen
    function compareByOffset(a, b) {
      if (a.offsetLeft < b.offsetLeft)
        return -1;
      if (a.offsetLeft > b.offsetLeft)
        return 1;
      return 0;
    }

    // move transport-more-button to the left
    function compareById(a, b) {
        var leftId = a.getAttribute('id');
        var rightId = b.getAttribute('id');
        var leftContains = leftId ? leftId.indexOf('transport-more-button') >= 0 : false;
        var rightContains = rightId ? rightId.indexOf('transport-more-button') >= 0 : false;
        if (leftContains && rightContains)
            return 0;
        if (rightContains)
            return 1;
        return 0;
    }


    var objArr = Array.prototype.slice.call(nodes);

    return objArr.sort(compareById);
}

function addArrowKeysHandling(container) {
    if (!container)
        return;

    // classes: 
    // focused - button highlighted
    // selected - button will have next focus
    // disabled - button not active 

    var buttons = container.querySelectorAll('.button, .toggle-button');
    buttons = sortButtons(buttons); // convert to array and sort

    if (!buttons)
        console.log('Houston we have problems: cant find buttons');

    var listener1 = function (event) {arrowKeysListener(event, buttons)};  
    var listener2 = function (event) {moveOutListener(event, buttons)};
    // var listener3 = function (event) {resetButtonsState(null, buttons)};  
    addEventListenerAll(container, 'keydown', [listener1, listener2]);
    
    var onDomChanged = function(el) {
    	console.log('onDomChanged');
        // refill buttons
        buttons = container.querySelectorAll('.button, .toggle-button');
        buttons = sortButtons(buttons); // convert to array and sort

        // reattach handlers
        // remove event handlers when content is changed
        removeEventListenerAll(container, 'keydown', [listener1, listener2]);
        addEventListenerAll(container, 'keydown', [listener1, listener2]);
        showHideCustomElements();
    };

    observeDOM(container, onDomChanged);
    // reset state of buttons that managed in this script
    setOnInterfaceVisible(function(ev){resetButtonsState(null, buttons);});
}

function showHideCustomElements() {
    var testElem = document.querySelector('.icon-bug_report.toggle-button');
    var mainButton = document.querySelector('.quality-toggle-buton');
    setVisibility(mainButton, testElem == null);
    setEnabled(mainButton, testElem == null);
}

function moveOutListener(event, objArr) {
    // console.log('moveOutListener fired');
    var up = 38;
    var down = 40;
    var esc = 27;
    var keyCode = event.keyCode;

    if (keyCode != up && keyCode != down && keyCode != esc) {
        return;
    }    

    console.log('moveOutListener');

    if (keyCode == esc) {
	    // fix hide interface on esc key
	    setTimeout(function(){resetButtonsState(event.currentTarget, objArr)}, 500);
    	return;
    }

    resetButtonsState(event.currentTarget, objArr);
}

// reset state of buttons that managed in this script
function resetButtonsState(container, objArr) {
    console.log('resetButtonsState called');

    var focusedAll = objArr;
    
    for (var i = 0; i < focusedAll.length; i++) {
        if (hasClass(focusedAll[i], 'my-disabled'))
            removeClass(focusedAll[i], 'disabled my-disabled');
        if (hasId(focusedAll[i], 'transport-more-button'))
        	removeFocus(focusedAll[i]);
        if (hasClass(focusedAll[i], 'my-button'))
        	removeFocus(focusedAll[i]);
    }

    hideQualityControls();
}

function hideQualityControls() {
    // TODO: rewrite

    // Are quality buttons shown?
    if (get('buttons-list') == null) {
        return;
    }

    var backedButtons = get('buttons-list');
    put('buttons-list', null);

    var parent = querySelector('.controls-row'); // root container
    var qualityRow = parent.querySelector('#buttons-list');
    remove(qualityRow);
    prepend(parent, backedButtons);

    var buttons = qualityRow.querySelectorAll('.button, .toggle-button');
    for (var i = 0; i < buttons.length; i++) {
        removeFocus(buttons[i]);
    }
}

function arrowKeysListener(event, objArr) {
    var left = 37;
    var right = 39;
    var keyCode = event.keyCode;

    if (event.keyCode != left && event.keyCode != right) {
        return;
    }

    switch(keyCode) {
        case left:
            var obj = getNextLeftFocus(objArr, event.target);
            if (obj)
                swapFocus(event.target, obj);
            break;
        case right:
            var obj = getNextRightFocus(objArr, event.target);
            if (obj)
                swapFocus(event.target, obj);
            break;
    } 

    event.stopPropagation(); // I will handle this event 
}

function getNextLeftFocus(objArr, currentFocus) {
    var currIdx = objArr.indexOf(currentFocus);
    if (currIdx == -1)
        return null;
    if (currIdx == 0)
        return null;

    var prevObj;

    for (var i = (currIdx - 1); i >= 0; i--) {
        prevObj = objArr[i];
        if (!hasClass(prevObj, 'disabled'))
            break;
        prevObj = null;
    }

    if (!prevObj)
        return null;

    return prevObj;
}

function getNextRightFocus(objArr, currentFocus) {
    var currIdx = objArr.indexOf(currentFocus);
    if (currIdx == -1)
        return null;
    if (currIdx == (objArr.length - 1))
        return null;

    var nextObj;

    for (var i = (currIdx + 1); i < objArr.length; i++) {
        nextObj = objArr[i];
        if (!hasClass(nextObj, 'disabled'))
            break;
        nextObj = null;
    }

    if (!nextObj)
        return null;

    return nextObj;    
}

function addFocus(elem) {
	if (!elem)
		return;
    addClass(elem, 'focused');
    addClass(elem, 'selected');  
    elem.focus();
}


function removeFocus(elem) {
    removeClass(elem, 'focused');
    removeClass(elem, 'selected');    
}

function swapFocus(oldFocus, newFocus) {
    removeFocus(oldFocus);
    
    addFocus(newFocus);

    if (isHidden(newFocus)) {
        console.log("Houston we have probles here!"); 
    }
}

///// End Remote Controller Navigation Handling /////

///// Event Handlers /////

function qualityButtonRowOnClick(event) {
    if (event.keyCode != 13) // enter/click
        return;
    
    console.log('switchResolution ' + event.target.dataset.formatName);

    if (window.app)
        window.app.switchResolution(event.target.dataset.formatName);
    
    event.stopPropagation(); 
}

function setupAutoHide() {
	var checkInterfaceHidden = function(){
		console.log('checking that interface is hidden');
		if (isInterfaceHidden()) {
			clearInterval(interval);
			hideQualityControls();
		}
	};
	var interval = setInterval(checkInterfaceHidden, 500);
	console.log('setupAutoHide');
}

function qualityToggleButtonOnClick(event, qualityButtonRow) {
    if (event.keyCode != 13) // enter or click
        return;

    if (get('buttons-list')) { // hide
        var elems = get('buttons-list');
        put('buttons-list', null);
        removeClass(querySelector('#transport-more-button'), 'disabled my-disabled');
    } else { // show
        var buttonList = querySelector('#buttons-list');
        put('buttons-list', buttonList);
        var elems = qualityButtonRow;
        addClass(querySelector('#transport-more-button'), 'disabled my-disabled');
        setupAutoHide();
    }
    
    var buttons = querySelector('#buttons-list');
    remove(buttons);

    var parent = querySelector('.controls-row'); // root container
    prepend(parent, elems);

    // I'll handle event myself
    event.stopPropagation();
}

function transportMoreButtonOnClick(event, qualityToggleButton) {
    if (event.keyCode != 13) // enter or click
        return;

    if (hasClass(qualityToggleButton, 'disabled')) {
        removeClass(qualityToggleButton, 'disabled my-disabled');
    } else {
        addClass(qualityToggleButton, 'disabled my-disabled');
    }
}

function removePreviouslyCreatedButtons() {
    var button = get('quality-toggle-buton');
    remove(button);
}

function setupQualityButtons(videoFormats) {
    if (!videoFormats.length)
        return;

    // removePreviouslyCreatedButtons
    remove(get('quality-toggle-buton'));

    // create and add my toggle-button
    var qualityToggleButton = createQualityToggleButton();
    var parentContainer = querySelector('.controls-row');
    append(parentContainer, qualityToggleButton);
    var qualityButtonsRow = createQualityButtonsRow2(videoFormats);
    // save
    put('quality-toggle-buton', qualityToggleButton);


    var qualityOnClick = function(event){qualityButtonRowOnClick(event)};
    var toggleOnClick = function(event){qualityToggleButtonOnClick(event, qualityButtonsRow)};

    // attach event handler to my toggle-button
    qualityToggleButton.addEventListener('keyup', toggleOnClick);

    // attach event handler to quality-button-row
    qualityButtonsRow.addEventListener('keyup', qualityOnClick);
}

///// End Event Handlers /////

function notifyAboutSupportedVideoFormats(formats) {
    console.log("Supported formats are: ", formats);
}

function addQualityControls() {
    if (window.addQualityControlsDone)
        return;

    // setup default, before 'videoformats' event is fired
    setupQualityButtons([{name: '144p'},{name: '360p'},{name: '720p'},{name: '1080p'}]);

    document.addEventListener('videoformats', function(event){console.log('videoformats event fired'); setupQualityButtons(event.data)});

    // var transportMoreButton = querySelector('#transport-more-button');
    // transportMoreButton.addEventListener('keyup', function(event){transportMoreButtonOnClick(event, qualityToggleButton)});

    // add arrow keys handling
    // TODO: not done
    addArrowKeysHandling(querySelector('.controls-row'));


    window.addQualityControlsDone = true;
}

///////////////////////////////////////////////////

// add quality settings to video
delayUntilPlayerBeInitialized(addQualityControls);