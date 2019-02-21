console.log("Scripts::Running core script favorite_button.js");

/**
 * Any button that could open an overlay like favorites, user channel etc
 * @param selector btn selector
 * @constructor
 */
function FavoriteButton(selector) {
    this.TAG = "FavoriteButton";
    this.selector = selector;
    this.stateless = true;

    this.decorator.apply(this);
}

FavoriteButton.prototype = new OverlayButton();