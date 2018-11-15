/**
 * Watches onto the controller's keys events.
 */

console.log("Scripts::Running script ui_watcher.js");

var UiWatcher = {
    /**
     * Integrate button into player's ui
     * @param button {@link UiButton} widget
     */
    insert: function(button) {
        var container = Utils.$(YouTubeSelectors.PLAYER_BUTTONS_CONTAINER);
        UiHelpers.append(container, button.getElem());
    },

    sortButtons: function(nodes) {
        // sort buttons based on horizontal position on screen
        function compareByOffset(a, b) {
            if (a.offsetLeft < b.offsetLeft)
                return -1;
            if (a.offsetLeft > b.offsetLeft)
                return 1;
            return 0;
        }

        // move transport-more-button to the left
        // NOTE: has un-proper sort among Options buttons
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

        return objArr.sort(compareByOffset);
    },

    addArrowKeysHandling: function(container) {
        if (!container)
            return;

        // classes:
        // focused - button highlighted
        // selected - button will have next focus
        // disabled - button not active

        var buttons = container.querySelectorAll('.button, .toggle-button');
        buttons = this.sortButtons(buttons); // convert to array and sort

        if (!buttons)
            console.log('Houston we have problems: cant find buttons');

        var listener1 = function (event) {
            var buttons = container.querySelectorAll('.button, .toggle-button');
            buttons = sortButtons(buttons); // convert to array and sort
            arrowKeysListener(event, buttons)
        };
        var listener2 = function (event) {
            var buttons = container.querySelectorAll('.button, .toggle-button');
            buttons = sortButtons(buttons); // convert to array and sort
            moveOutListener(event, buttons)
        };
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
    },

    moveOutListener: function(event, objArr) {
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
    },

    arrowKeysListener: function(event, objArr) {
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
    },

    getNextLeftFocus: function(objArr, currentFocus) {
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
    },

    getNextRightFocus: function(objArr, currentFocus) {
        var currIdx = objArr.indexOf(currentFocus);
        if (currIdx == -1)
            return null;
        if (currIdx == (objArr.length - 1))
            return null;

        var nextObj;

        for (var i = (currIdx + 1); i < objArr.length; i++) {
            nextObj = objArr[i];
            if (!Utils.hasClass(nextObj, YouTubeClasses.ELEMENT_DISABLED))
                break;
            nextObj = null;
        }

        if (!nextObj)
            return null;

        return nextObj;
    },

    addFocus: function(elem) {
        if (!elem)
            return;
        addClass(elem, 'focused');
        addClass(elem, 'selected');
        elem.focus();
    },

    removeFocus: function(elem) {
        removeClass(elem, 'focused');
        removeClass(elem, 'selected');
    },

    swapFocus: function(oldFocus, newFocus) {
        removeFocus(oldFocus);

        addFocus(newFocus);

        if (isHidden(newFocus)) {
            console.log("Houston we have problems here!");
        }
    }
};
