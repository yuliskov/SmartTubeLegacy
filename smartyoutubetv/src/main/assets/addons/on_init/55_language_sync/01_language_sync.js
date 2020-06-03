/**
 * Syncs language inside webview with app language
 */

console.log("Scripts::Running script language_sync.js");

function LanguageSyncAddon() {
    this.TAG = 'LanguageSyncAddon';
    this.COOKIE_NAME = 'PREF';
    this.LANG_KEY = 'hl';

    this.run = function() {
        Log.d(this.TAG, "Running...");

        var langCode = CookieManager.getQueryCookie(this.COOKIE_NAME)[this.LANG_KEY];
        if (langCode) {
            Log.d(this.TAG, "Syncing language: " + langCode);
            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_SYNC_LANG, langCode);
            CookieManager.deleteCookie(this.COOKIE_NAME); // use app locale instead
        }
    };
}

new LanguageSyncAddon().run();
