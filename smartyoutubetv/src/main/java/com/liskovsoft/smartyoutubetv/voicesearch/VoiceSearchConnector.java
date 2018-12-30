package com.liskovsoft.smartyoutubetv.voicesearch;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;

class VoiceSearchConnector extends GenericCommand {
    private static final String VOICE_SEARCH_PATTERN = "VoiceSearch && VoiceSearch.open('%s')";
    public void openSearchPage(String searchText) {
        if (searchText == null) {
            return;
        }

        // send msg to the browser with text
        passToBrowser(String.format(VOICE_SEARCH_PATTERN, searchText));
    }

    @Override
    public boolean call() {
        return false;
    }
}
