/**
 * Syncs language inside webview with app language
 */

console.log("Scripts::Running script language_sync.js");

function LanguageSyncAddon() {
    this.TAG = 'LanguageSyncAddon';
    this.COOKIE_NAME = 'PREF';
    this.LANG_KEY = 'hl';
    this.SYNC_LANG_MESSAGE = 'sync_lang_message';

    this.run = function() {
        var langCode = CookieManager.getQueryCookie(this.COOKIE_NAME)[this.LANG_KEY];
        if (langCode) {
            Log.d(this.TAG, "Syncing language: " + langCode);
            DeviceUtils.sendMessage(this.SYNC_LANG_MESSAGE, langCode);
        }
    };
}

new LanguageSyncAddon().run();
