function overrideProp(obj, propName, propValue) {
    Object.defineProperty(obj, propName, {
        get: function() { return propValue; },
        set: function(newValue) { ; },
        enumerable: true,
        configurable: true
    });
}

function setNewDimensions(width, height) {
    var newWidth = width;
    var newHeight = height;
    
    overrideProp(window.screen, 'availHeight', newHeight);
    overrideProp(window.screen, 'availWidth', newWidth);
    overrideProp(window.screen, 'height', newHeight);
    overrideProp(window.screen, 'width', newWidth);
}

setNewDimensions(640, 360);

window.labels={'default':'406c6fcd'};(function(){var f=this;function g(c,b){c=c.split(".");b=b||f;for(var a;a=c.shift();)if(null!=b[a])b=b[a];else return null;return b}function l(c,b){var a=Array.prototype.slice.call(arguments,1);return function(){var b=a.slice();b.push.apply(b,arguments);return c.apply(this,b)}};function p(c,b,a,d,e){b?(b=q(c,a),b.setAttribute("src",e),c.body.appendChild(b)):a?c.write('<script src="'+e+'" nonce="'+a+'">\x3c/script>'):c.write('<script src="'+e+'">\x3c/script>');d()}function u(c,b,a,d,e){b?(c=q(c,a),c.innerHTML=e,d.push(c)):a?c.write('<script nonce="'+a+'">'+e+"\x3c/script>"):c.write("<script>"+e+"\x3c/script>")}function q(c,b){c=c.createElement("script");b&&c.setAttribute("nonce",b);return c}
function v(c,b){var a=c.createElement("link");a.setAttribute("rel","stylesheet");a.setAttribute("type","text/css");a.setAttribute("href",b);c.head.appendChild(a)};f.CLOSURE_UNCOMPILED_DEFINES={"bedrock.DEV_JS":!0};var w={initializerPath:"yt.tv.initialize"};"Google"==f.environment.brand&&"Eureka"==f.environment.model?(w.devJsInitializer="/video/youtube/tv/cast/js/initialize_chromeless.js",w.debugJsInitializer="/chromecast-concat-bundle.js"):(w.devJsInitializer="/video/youtube/tv/hh/js/initialize_kabuki.js",w.debugJsInitializer="/app-concat-bundle.js");
(function(c,b){var a=b||f,d=a.loadParams;a.label=function(a,b){return b?b:a&&a["default"]?a["default"]:"unknown"}(a.labels,d.label_requested);b=d.scs_path+a.label;var e,r=!1,m=[],h=l(p,document,d.is_cobalt,d.csp_nonce,function(){d.use_reset_logic&&n("resetTimeout();")}),n=l(u,document,d.is_cobalt,d.csp_nonce,m),t=l(v,document);a.resetTimeout=function(){a.clearTimeout(e);r||(e=a.setTimeout(function(){var b="local:///network_failure.html";d.is_cobalt&&(b="h5vcc://network-failure?retry-url="+encodeURIComponent(a.location.href.split("#")[0]));
a.location.replace(b)},4E4))};d.use_reset_logic&&(a.resetTimeout(),a.applicationLoaded=function(){r=!0;a.clearTimeout(e)});a.initializeOrRedirect=function(b){var d=g("ytcsi.tick",a);d&&d("js_r");(d=g(c.initializerPath))?d(b):a.location="http://www.youtube.com/error?src=404"};d.load_steel_api&&h(b+"/api-compiled.js");var k=c.prodCssOverride||d.tv_css;d.debugjs?(a.CLOSURE_NO_DEPS=!0,k&&t(b+k),h(b+c.debugJsInitializer)):d.devjs||(k&&t(b+k),h(b+(c.prodJsOverride||d.tv_binary)));d.load_player&&h(d.player_url);
a.checkBrokenLabel=function(){"undefined"==typeof yt&&d.label_requested&&(a.location.href=a.location.href.replace(/([?&])label=[^&]+&?/,"$1stick=0&"))};n("checkBrokenLabel()");n("initializeOrRedirect('"+b+"');");d.is_cobalt&&(a.onload=function(){for(var a=0,b=m.length;a<b;++a)document.body.appendChild(m[a])})})(w,f);}).call(this);
