/**
 * Description:
 * Change default app settings like set sound off and disable spying by Google
 */

console.log("Scripts::Running script default_settings.js");

function DefaultSettingsAddon() {
    this.run = function() {
        this.applySettings();
    };

    this.applySettings = function() {
        if (!localStorage)
            return;

        var keys = Object.keys(DefaultSettingsData);
        for (var idx in keys) {
            var key = keys[idx];
            var value = DefaultSettingsData[key];
            if (!localStorage.getItem(key)) // only if not exists
                localStorage.setItem(key, value);
        }
    };
}

new DefaultSettingsAddon().run();