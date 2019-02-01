/**
 * Syncs language inside webview with app language
 */

console.log("Scripts::Running script language_sync.js");

function LanguageSyncAddon() {
    this.TAG = 'LanguageSyncAddon';
    this.COOKIE_NAME = 'PREF';
    this.LANG_KEY = 'hl';

    this.run = function() {
        var langCode = CookieManager.getQueryCookie(this.COOKIE_NAME)[this.LANG_KEY];
        if (langCode) {
            Log.d(this.TAG, "Syncing language: " + langCode);
            DeviceUtils.sendMessage(DeviceUtils.MESSAGE_SYNC_LANG, langCode);
        }
    };
}

new LanguageSyncAddon().run();
