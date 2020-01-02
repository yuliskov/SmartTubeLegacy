/**
 * Description:<br/>
 * Utility for dumping all html dom tree with styles
 */

console.log("Scripts::Running script dump_dom.js");

var DomDumper = {
    TAG: 'DomDumper',

    // Mapping between tag names and css default values lookup tables. This allows to exclude default values in the result.
    defaultStylesByTagName: {},

    // Styles inherited from style sheets will not be rendered for elements with these tag names
    noStyleTags: {"BASE":true,"HEAD":true,"HTML":true,"META":true,"NOFRAME":true,"NOSCRIPT":true,"PARAM":true,"SCRIPT":true,"STYLE":true,"TITLE":true},

    // This list determines which css default values lookup tables are precomputed at load time
    // Lookup tables for other tag names will be automatically built at runtime if needed
    tagNames: ["A","ABBR","ADDRESS","AREA","ARTICLE","ASIDE","AUDIO","B","BASE","BDI","BDO","BLOCKQUOTE","BODY","BR","BUTTON","CANVAS","CAPTION","CENTER","CITE","CODE","COL","COLGROUP","COMMAND","DATALIST","DD","DEL","DETAILS","DFN","DIV","DL","DT","EM","EMBED","FIELDSET","FIGCAPTION","FIGURE","FONT","FOOTER","FORM","H1","H2","H3","H4","H5","H6","HEAD","HEADER","HGROUP","HR","HTML","I","IFRAME","IMG","INPUT","INS","KBD","KEYGEN","LABEL","LEGEND","LI","LINK","MAP","MARK","MATH","MENU","META","METER","NAV","NOBR","NOSCRIPT","OBJECT","OL","OPTION","OPTGROUP","OUTPUT","P","PARAM","PRE","PROGRESS","Q","RP","RT","RUBY","S","SAMP","SCRIPT","SECTION","SELECT","SMALL","SOURCE","SPAN","STRONG","STYLE","SUB","SUMMARY","SUP","SVG","TABLE","TBODY","TD","TEXTAREA","TFOOT","TH","THEAD","TIME","TITLE","TR","TRACK","U","UL","VAR","VIDEO","WBR"],

    init: function() {
        var $this = this;

        Log.d(this.TAG, "Initializing...");

        ListenerUtil.addListener(YouTubeSelectors.SURFACE_AREA, YouTubeEvents.MODEL_CHANGED_EVENT, function() {
            setTimeout(function() {
                if (location.href.indexOf("zylon-surface?c=default") != -1) {
                    Log.d($this.TAG, "Dumping home page...");
                    $this.dump();
                }
            }, 3000);

        });
    },

    computeDefaultStyleByTagName: function(tagName) {
        var defaultStyle = {};
        var element = document.body.appendChild(document.createElement(tagName));
        var computedStyle = getComputedStyle(element);
        for (var i = 0; i < computedStyle.length; i++) {
            defaultStyle[computedStyle[i]] = computedStyle[computedStyle[i]];
        }
        document.body.removeChild(element);
        return defaultStyle;
    },

    getDefaultStyleByTagName: function(tagName) {
        tagName = tagName.toUpperCase();
        if (!this.defaultStylesByTagName[tagName]) {
            this.defaultStylesByTagName[tagName] = this.computeDefaultStyleByTagName(tagName);
        }
        return this.defaultStylesByTagName[tagName];
    },

    serializeWithStyles: function(domRoot) {
        if (domRoot.nodeType !== Node.ELEMENT_NODE) {
            throw new TypeError();
        }

        // Precompute the lookup tables.
        for (var i = 0; i < this.tagNames.length; i++) {
            if(!this.noStyleTags[this.tagNames[i]]) {
                this.defaultStylesByTagName[this.tagNames[i]] = this.computeDefaultStyleByTagName(this.tagNames[i]);
            }
        }

        var cssTexts = [];
        var elements = domRoot.querySelectorAll("*");
        for ( var i = 0; i < elements.length; i++ ) {
            var e = elements[i];
            if (!this.noStyleTags[e.tagName]) {
                var computedStyle = getComputedStyle(e);
                var defaultStyle = this.getDefaultStyleByTagName(e.tagName);
                cssTexts[i] = e.style.cssText;
                for (var ii = 0; ii < computedStyle.length; ii++) {
                    var cssPropName = computedStyle[ii];
                    if (computedStyle[cssPropName] !== defaultStyle[cssPropName]) {
                        e.style[cssPropName] = computedStyle[cssPropName];
                    }
                }
            }
        }
        var result = domRoot.outerHTML;
        for ( var i = 0; i < elements.length; i++ ) {
            elements[i].style.cssText = cssTexts[i];
        }
        return result;
    },

    dump: function() {
        Log.d(this.TAG, "Start dumping... Page url: " + location.href + " Page content: " + this.serializeWithStyles(document.body));
    }
};

// if (DeviceUtils.logToFileEnabled()) {
//     DomDumper.init();
// }
