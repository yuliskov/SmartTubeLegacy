/**
 * Description:
 * Change default app settings like set sound off and disable spying by Google
 */

console.log("Scripts::Running script default_settings.js");

function DefaultSettingsAddon() {
    this.TAG = 'DefaultSettingsAddon';

    this.run = function() {
        this.applySettings();
        this.applyFlags();
    };

    this.applySettings = function() {
        if (!localStorage) {
            return;
        }

        var keys = Object.keys(DefaultSettingsData);
        for (var idx in keys) {
            var key = keys[idx];
            var value = DefaultSettingsData[key];
            if (!localStorage.getItem(key)) // only if not exists
                localStorage.setItem(key, value);
        }
    };

    this.applyFlags = function() {
        Log.d(this.TAG, "Apply flags");

        if (window.environment) {
            Log.d(this.TAG, "Environment flags is enabled: " + (window.environment.flags != null));

            // FLAGS DON'T WORK AT ALL!

            // window.environment.flags.enable_masthead_large = false;
            // window.environment.flags.enable_masthead_medium = false;
            // window.environment.flags.enable_masthead_small = false;
            // window.environment.flags.enable_masthead_thumbnail_only = false;

            // window.environment.feature_switches.limited_memory = true;
            // window.environment.feature_switches.limited_animation = true;
        }
    };
}

new DefaultSettingsAddon().run();