/**
 * Description:
 * Constant account selection fix addon
 */

console.log("Scripts::Running script sign_in_fix.js");

function SingInFixAddon() {
    this.TAG = 'SingInFixAddon';
    this.KEY = 'yt.leanback.default::recurring_actions';
    this.VALUE = '{"data":{"data":{"whos_watching_fullscreen":{"timesFired":15,"lastFired":1898016029659},"welcome_direct_sign_in":{"timesFired":0},"whos_watching":{"timesFired":0},"whos_watching_fullscreen_zero_accounts":{"timesFired":0},"menu_hint_toast":{"timesFired":6,"lastFired":1582165074513}},"lastPruned":1580748722423},"expiration":1897913296916,"creation":1582553296916}';

    this.run = function() {
        if (localStorage) {
            localStorage.setItem(this.KEY, this.VALUE);
        }
    };
}

new SingInFixAddon().run();
