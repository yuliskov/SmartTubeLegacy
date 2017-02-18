function createElement(html) {
    var div = document.createElement('div');
    div.innerHTML = html;
    return div.firstChild;
}

function addClass(el, className) {
    if (el.classList)
      el.classList.add(className);
    else
      el.className += ' ' + className;
}

function removeClass(el, className) {
    if (el.classList)
      el.classList.remove(className);
    else
      el.className = el.className.replace(new RegExp('(^|\\b)' + className.split(' ').join('|') + '(\\b|$)', 'gi'), ' ');
}

function hasClass( elem, klass ) {
     return (" " + elem.className + " " ).indexOf( " "+klass+" " ) > -1;
}

function handleRemoteControllerKeysTEMP(obj) {
    // focused selected
    // var buttons = obj.querySelectorAll('.toggle-button');
    var parent = document.querySelector('#transport-controls');

    parent.addEventListener('keydown', function(event) {
        console.log("handleRemoteControllerKeys");

        if (event.keyCode != 37 && event.keyCode != 39) { // arrow left/arrow right
            return;
        }

        switch(event.keyCode) {
            case 37: // left
                var selected = parent.querySelector('#transport-more-button.focused');
                if (selected == null) {
                    return;
                }
                // addClass
                addClass(obj, 'focused');
                obj.focus();
                removeClass(selected, 'focused');
                break;
            case 39: // right
                var selected = parent.querySelector('.icon-player-prev.focused');
                if (selected == null) {
                    return;
                }
                removeClass(obj, 'focused');
                break;
        }

    });
}

function handleRemoteControllerKeys(obj) {
    // focused selected
    // var buttons = obj.querySelectorAll('.toggle-button');
    var parent = document.querySelector('#transport-controls');

    parent.addEventListener('keydown', function(event) {
        console.log("handleRemoteControllerKeys");
        var left = 37;
        var right = 39;
        var keyCode = event.keyCode;

        if (event.keyCode != left && event.keyCode != right) {
            return;
        }

        var selectedList = parent.querySelector('.focused.list');
        var selected = parent.querySelector('.focused');
        if (selectedList) 
            selected = parent.querySelector('.focused.selected');
        var thisButoon = obj;
        if (selected == thisButoon) {
            var prev = parent.querySelector('#transport-more-button');
            var next = parent.querySelector('.list .selected');
            switch(keyCode) {
                case left:
                    prev.focus();
                    addClass(prev, 'focused');
                    break;
                case right:
                    next.focus();
                    addClass(next, 'focused');
                    break;
            }
            removeClass(selected, 'focused');
        } else {
            var prev = parent.querySelector('#transport-more-button');
            var next = parent.querySelector('.focused.list .focused.selected');
            if (selected == prev) {
                switch(keyCode) {
                    case left:
                        addClass(obj, 'focused');
                        obj.focus();
                        removeClass(selected, 'focused');
                        break;
                    case right:
                        break;
                }
            } else if (selected == next) {
                switch(keyCode) {
                    case left:
                        break;
                    case right:
                        if (hasClass(obj, 'focused')) {
                            removeClass(obj, 'focused');
                            addClass(next, 'focused');
                            next.focus();
                        } else {
                            addClass(obj, 'focused');
                            obj.focus();
                            removeClass(selected, 'focused');
                        }
                        break;
                }
            }
        }

    });
}

function addQualityControls() {
    if (window.addQualityControlsDone)
        return;

    // note: $ is wrapper for document.querySelector

    // toggle-selected - when not collapsed
    var qualityToggle = createElement(
    '<div id="transport-more-button" class="toggle-button" tabindex="-1"> \
        <span>Quality Options</span> \
    </div>');
    var qualityButtonRow = createElement(
    '<div id="buttons-list" class=" list" data-enable-sounds="false" tabindex="-1"> \
        <div class="toggle-button" tabindex="-1" data-itag="160,278">144p</div> \
        <div class="toggle-button" tabindex="-1" data-itag="133,242">240p</div> \
        <div class="toggle-button" tabindex="-1" data-itag="243,134">360p</div> \
        <div class="toggle-button" tabindex="-1" data-itag="244,135">480p</div> \
        <div class="toggle-button" tabindex="-1" data-itag="247,136">720p</div> \
        <div class="toggle-button" tabindex="-1" data-itag="248,137">1080p</div> \
    </div>');

    // handleRemoteControllerKeys(qualityButtonRow);
    handleRemoteControllerKeys(qualityToggle);

    qualityButtonRow.addEventListener('keyup', function(event){
        if (event.keyCode != 13) // enter
            return;
        console.log(event.target.dataset.itag);

        // app.switchResolution(event.target.dataset.itag);
        event.stopPropagation();
    });
    
    // append()
    document.querySelector('.controls-row').appendChild(qualityToggle);

    var backup;
    qualityToggle.addEventListener('keyup', function(event) {
        if (event.keyCode != 13) // enter
            return;

        if (backup) {
            var elems = backup;
            backup = null;
        } else {
            backup = document.querySelector('#buttons-list');
            var elems = qualityButtonRow;
        }
        // remove()
        var el = document.querySelector('#buttons-list');
        el.parentNode.removeChild(el);
        // prepend()
        var parent = document.querySelector('.controls-row');
        parent.insertBefore(elems, parent.firstChild);

        event.stopPropagation();
    });

    window.addQualityControlsDone = true;
}

///////////////////////////////////////////////////

// add quality settings to video
addQualityControls();