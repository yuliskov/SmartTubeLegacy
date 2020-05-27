/**
 * Description:
 * Control screensaver on LITE.
 */

console.log("Scripts::Running script masthead_fix.js");

function MastheadFixAddon() {
    this.TAG = 'MastheadFixAddon';

    this.run = function() {
        Utils.removeElement(YouTubeSelectors.MASTHEAD_SECTION);
    };
}

// new MastheadFixAddon().run();
