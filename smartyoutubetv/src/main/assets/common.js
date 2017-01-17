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

function replaceParamButtonTitle(paramButton) {
    // encodeURI("Дополнительные параметры")
    var stringToFind = "%D0%94%D0%BE%D0%BF%D0%BE%D0%BB%D0%BD%D0%B8%D1%82%D0%B5%D0%BB%D1%8C%D0%BD%D1%8B%D0%B5%20%D0%BF%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80%D1%8B";
    // encodeURI("Параметры")
    var stringToReplace = "%D0%9F%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80%D1%8B";
    if (paramButton.innerHTML == decodeURI(stringToFind))
        paramButton.innerHTML = decodeURI(stringToReplace);
    return paramButton;
}

function fixOverlappedTextInRussian() {
	var paramButton = document.getElementById("transport-more-button").children[0];
    replaceParamButtonTitle(paramButton);

    observeDOM(paramButton, function(el) {
        console.log('dom changed, ' + el);
        replaceParamButtonTitle(el);
	});
}

/////////////////////////////////////////

fixOverlappedTextInRussian();