console.log("Scripts::Running script global_afr_fix.js");

function GlobalAfrFixAddon() {
    this.TAG = 'GlobalAfrFixAddon';

    this.run = function() {
        var $this = this;
        EventUtils.onLoad(function() {
            $this.overrideVideoSrc();
        });
    };

    this.overrideVideoSrc = function() {
        var $this = this;
        var v = YouTubeUtils.getPlayer();

        if (!v) {
            setTimeout(function() {
                $this.overrideVideoSrc();
            }, 1000);
            return;
        }

        Utils.overrideProp(v, 'src', '');
        Utils.overrideProp(v, 'currentSrc', '');
    };
}

// if (DeviceUtils.isExo() && DeviceUtils.isAfrFixEnabled()) {
//     new GlobalAfrFixAddon().run();
// }