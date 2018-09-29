/**
 * Description: Imitate press on OK button after seek
 */

console.log("Scripts::Running script codec_fix.js");

function CodecFixAddon() {
    this.run = function() {
        var config = this.findProperConfig();
        this.applyCodecFixes(config);
    };

    /**
     * Obtains device map for WebView or XWalk respectively
     */
    this.findProperConfig = function() {
        if (DeviceUtils.isWebView()) {
            return WebViewConfig;
        }

        if (DeviceUtils.isXWalk()) {
            return XWalkConfig;
        }

        console.log("CodecFixAddon::unknown engine type " + DeviceUtils.getEngineType());
        return {};
    };

    /**
     * Consumes hash map, that consists of {'device name': 'disabled codec list'}
     * Device name could be obtained from the CPU-Z for example.
     * For details see: WebViewJavaScriptInterface.java
     */
    this.applyCodecFixes = function(deviceMap) {
        for (var device in deviceMap) {
            if (this.isThisDevice(device)) {
                // multiple codecs supported, divided by comma
                var codecs = deviceMap[device].split(',');
                var codecsLen = codecs.length;
                for (var i = 0; i < codecsLen; i++) {
                    DeviceUtils.disableCodec(codecs[i].trim());
                }
                break;
            }
        }
    };

    // variable number of arguments
    this.isThisDevice = function() {
        if (arguments.length == 0)
            return false;
        if (!window.thisDevice)
            window.thisDevice = DeviceUtils.getDeviceName();
        var argsLen = arguments.length;
        for (var i = 0; i < argsLen; i++) {
            var device = arguments[i];
            if (DeviceUtils.specCmp(window.thisDevice, device)) {
                return true;
            }
        }
        return false;
    };
}

new CodecFixAddon().run();