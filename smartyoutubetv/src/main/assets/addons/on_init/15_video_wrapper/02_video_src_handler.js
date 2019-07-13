/**
 * Fix <b>Global AFR</b> on some devices<br/>
 * Fix excessive resource consumption<br/>
 */
function VideoSrcHandler() {
    this.TAG = 'VideoSrcHandler';

    this.onCreate = function(video) {
        if (DeviceUtils.isGlobalAfrFixEnabled()) {
            this.overrideVideoSrc(video);
        }
    };

    this.overrideVideoSrc = function(video) {
        Utils.overrideProp(video, 'src', '');
        Utils.overrideProp(video, 'currentSrc', '');
    };
}
