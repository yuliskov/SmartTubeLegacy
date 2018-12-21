/**
 * Description:
 * Opens search page when pressing voice input button.
 */

console.log("Scripts::Running script voice_search.js");

var VoiceSearch = {
    TAG: 'VoiceSearch',
    open: function(searchText) {
        Log.d(this.TAG, "going to the search page");

        this.navigateToTheSearchPage();
        this.typeSearchText(searchText);
        this.commitChanges();
    },

    navigateToTheSearchPage: function() {
        if (location.hash.indexOf('search') != -1) {
            return;
        }

        location.hash = '/search?resume';
    },

    typeSearchText: function(searchText) {
        var input = Utils.$('#text-input input');
        if (!input) {
            return;
        }

        input.value = searchText;
    },

    commitChanges: function() {
        EventUtils.triggerEvent('#text-input input', 'input', DefaultKeys.ENTER);
    }
};