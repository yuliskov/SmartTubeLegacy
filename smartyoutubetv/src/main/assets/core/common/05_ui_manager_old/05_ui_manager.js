/**
 * Helps to add new buttons to the player's UI.
 */

console.log("Scripts::Running script ui_manager.js");

var UiManager = {
    /**
     * Creates button from the supplied data and adds it to the player
     * @param buttonData object with onClick method
     */
    addToPlayer: function(buttonData) {
        var btn = new UiButton(buttonData);
        UiWatcher.insert(btn);
    }
};