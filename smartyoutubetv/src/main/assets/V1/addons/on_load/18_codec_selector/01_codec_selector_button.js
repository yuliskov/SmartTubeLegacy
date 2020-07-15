/**
 * Description:
 * Codec selector button that placed inside player's ui.
 */

console.log("Scripts::Running script codec_selector_button.js");

function CodecSelectorButton() {
    this.TAG = 'CodecSelectorButton';

    this.onClick = function() {
        Log.d(this.TAG, "user have clicked on the codec selector button");
        DeviceUtils.openCodecSelector();
    };

    this.getTitle = function() {
        return 'Codec';
    };

    /**
     * An predefined icon name from the material set<br/>
     * <a href="https://material.io/tools/icons/?style=baseline">Icon list</a><br/>
     * <a href="https://github.com/google/material-design-icons/blob/master/iconfont/codepoints">Code points</a><br/>
     * Fore details see <a href="https://www.youtube.com/s/tv/html5/a84e7906/airstream-prod-css.css">airstream-prod-css.css</a>
     * @returns {string} predefined class
     */
    this.getIconClass = function() {
        return 'material-icon-music-video';
    };
}
