this._yttv=this._yttv||{};(function(_){var window=this;
try{
_.m().w("em0");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("syw");
var vo=_.p("NetworkStatusService","oUhQtc");
var wo;wo={Ql:function(){},aj:function(){},Bo:function(){return!0}};_.xo=_.K(vo,function(){return wo});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Lo;_.Jo=function(){return(0,_.Sb)()+(_.pd().utcMillisecondsOffset||0)};_.Ko=function(a,b){a.H?b():(a.D||(a.D=[]),a.D.push(b))};Lo=function(a){a=String(a);if(/^\s*$/.test(a)?0:/^[\],:{}\s\u2028\u2029]*$/.test(a.replace(/\\["\\\/bfnrtu]/g,"@").replace(/(?:"[^"\\\n\r\u2028\u2029\x00-\x08\x0a-\x1f]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)[\s\u2028\u2029]*(?=:|,|]|}|$)/g,"]").replace(/(?:^|:|,)(?:[\s\u2028\u2029]*\[)+/g,"")))try{return eval("("+a+")")}catch(b){}throw Error("O`"+a);};
_.Mo=function(a){if(_.x.JSON)try{return _.x.JSON.parse(a)}catch(b){}return Lo(a)};_.m().w("sys");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var No,Oo,Po,So,Vo,Xo,op,pp,tp,wp,Ap,Cp,Ep,Gp,Fp,Jp,Kp,Up,Vp,Wp,Dp,Zp,$p,aq;No=function(a,b){return Math.min(a,b)};Oo=function(a,b){return Math.pow(2,a)*b};Po=function(a){setTimeout(function(){throw a;},0)};_.Qo=function(a){return"function"===typeof a};So=function(a){return a.reduce(function(b,c){return b.concat(c instanceof Ro?c.errors:c)},[])};_.Uo=function(a){for(;a;){var b=a,c=b.destination,d=b.ya;if(b.closed||d)return!1;c&&c instanceof To?a=c:a=null}return!0};Vo=function(){};
_.Wo=function(a){return a?1===a.length?a[0]:function(b){return a.reduce(function(c,d){return d(c)},b)}:Vo};Xo=function(a){a||(a=Promise);if(!a)throw Error("ca");return a};_.Yo=function(a){return a&&"function"===typeof a.schedule};_.$o=function(a){return new _.Zo(function(b){return b.error(a)})};_.bp=function(){return function(a){return a.Sa(new ap(a))}};_.dp=function(a){return function(b){if("function"!==typeof a)throw new TypeError("ea");return b.Sa(new cp(a))}};
_.gp=function(a,b,c,d,e){e=void 0===e?new ep(a,c,d):e;if(!e.closed)return b instanceof _.Zo?b.subscribe(e):fp(b)(e)};_.hp=function(a){return a instanceof _.Zo?a:new _.Zo(fp(a))};_.ip=function(a,b){var c=void 0===c?Number.POSITIVE_INFINITY:c;if("function"===typeof b)return function(d){return d.g(_.ip(function(e,f){return _.hp(a(e,f)).g(_.dp(function(g,h){return b(e,g,f,h)}))},c))};"number"===typeof b&&(c=b);return function(d){return d.Sa(new jp(a,c))}};
op=function(a){return new _.Zo(function(b){try{var c=a()}catch(d){b.error(d);return}return(c?_.hp(c):_.np).subscribe(b)})};pp=function(a,b,c){b=void 0===b?_.np:b;c=void 0===c?_.np:c;return op(function(){return a()?b:c})};_.rp=function(a){return!(0,_.qp)(a)&&0<=a-parseFloat(a)+1};
_.up=function(a){var b;a=void 0===a?0:a;var c=-1;_.rp(void 0)?c=1>Number(void 0)&&1||Number(void 0):_.Yo(void 0)&&(b=void 0);_.Yo(b)||(b=_.sp);return new _.Zo(function(d){var e=_.rp(a)?a:+a-b.now();return b.schedule(tp,e,{index:0,period:c,Fd:d})})};tp=function(a){var b=a.index,c=a.period,d=a.Fd;d.next(b);if(!d.closed){if(-1===c)return d.complete();a.index=b+1;this.schedule(a,c)}};wp=function(a){vp(a)};_.yp=function(a){return function(b){return b.Sa(new xp(a,b))}};
Ap=function(a,b){b=void 0===b?_.sp:b;return function(c){return c.Sa(new zp(a,b))}};Cp=function(){var a=void 0===a?_.sp:a;return Ap(_.$o(new Bp),a)};
Ep=function(){var a={Jk:2E3,maxRetries:4,shouldRetry:Dp};a="number"===typeof a?{Jk:a}:a;var b=a.Jk,c=void 0===a.maxRetries?Infinity:a.maxRetries,d=void 0===a.Ho?Infinity:a.Ho,e=void 0===a.shouldRetry?function(){return!0}:a.shouldRetry,f=void 0===a.Lm?Oo:a.Lm;return function(g){return g.g(_.yp(function(h){return h.g(_.ip(function(l,n){return pp(function(){return n<c&&e(l)},_.up(No(f(n,b),d)),_.$o(l))},1))}))}};
Gp=function(a,b){var c=[],d=void 0===b?!1:b;d=void 0===d?!1:d;b={};for(var e=Object.keys(a||{}),f=0,g=e.length;f<g;++f){var h=e[f],l=a[h];null!=l&&(d?b[h]=Fp(l):""!==l&&(b[h]=Fp(l||0)))}a=Object.keys(b);d=0;for(e=a.length;d<e;++d)f=a[d],c.push(encodeURIComponent(f)+"="+encodeURIComponent(""+b[f]));return c};
Fp=function(a){if("number"===typeof a)return a;if(a&&"string"===typeof a&&Hp.test(a))return Number(a);if(null!=a&&"string"===typeof a&&""===a.trim())return a;var b=String(a).toLowerCase();return"true"===b||!0===a?!0:"false"===b||!1===a?!1:null!=a?""+a:""};Jp=function(a,b){return a.replace(Ip,function(c,d,e){(c=b[d])?(c=encodeURIComponent(encodeURIComponent(""+c)),e&&(c+="/"),delete b[d],d=c):d="";return d})};Kp=function(a){a=a.Ma();return 401===a||403===a||404===a||400===a||402===a};
Up=function(a,b){if("string"!==typeof a)throw Error("ja");var c=b.body;if(c&&"string"!==typeof c)throw Error("ka");var d=new Lp(function(){return new Mp},function(){return b.headers||null},_.r.get(_.xo),Np,Math.random,Kp);if(a.includes(".mp3")||a.includes(".wav"))d.responseType="arraybuffer";var e=_.Op(),f=e.resolve,g=e.reject;e=e.Ad;Pp(d,function(h){var l=_.I(h);f(Object.assign(Object.assign({},Qp),{text:function(){return l},arrayBuffer:function(){return l},json:function(){return l}}))});Rp(d,function(h){g(new Sp(h.statusCode,
a))});d.send(new Tp(b.method||"GET",a,c));return e};Vp=function(a,b){return window.fetch(a,b).then(function(c){if(c.ok||428===c.status)return c;throw new Sp(c.status);})};Wp=function(a,b){return op(function(){return Vp(a,b)})};Dp=function(a){return a.status?!Xp.has(a.status):!0};_.Yp=function(a,b){return _.D("enableBedrockNetworking",!1)?op(function(){return Up(a,b)}):Wp(a,b).g(Ep(),Cp())};
Zp=function(a){var b=1;a=a.split(":");for(var c=[];0<b&&a.length;)c.push(a.shift()),b--;a.length&&c.push(a.join(":"));return c};$p=function(a){var b={};a=a.getAllResponseHeaders().split("\r\n");for(var c=0;c<a.length;c++)if(!_.Qe(a[c])){var d=Zp(a[c]),e=d[0];d=d[1];if("string"===typeof d){d=d.trim();var f=b[e]||[];b[e]=f;f.push(d)}}return _.v.map(b,function(g){return g.join(", ")})};aq=function(a,b,c){this.Ad=a;this.resolve=b;this.reject=c};
_.Op=function(){var a,b,c=new _.Zg(function(d,e){a=d;b=e});return new aq(c,a,b)};_.m().w("syf");
/*

 MIT License
 Copyright (c) 2018 Alex Okrushko
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/
/*


 Copyright (c) 2015-2018 Google, Inc., Netflix, Inc., Microsoft Corp. and contributors

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/
var bq=!0,cq={set tc(a){a?console.warn("DEPRECATED! RxJS was set to use deprecated synchronous error handling behavior by code at: \n"+Error().stack):bq&&console.log("RxJS: Back to a better error behavior. Thank you. <3");bq=a},get tc(){return bq}};
_.Ed();_.Ed();var dq="function"===typeof Symbol&&Symbol.observable||"@@observable";
var eq={closed:!0,next:function(){},error:function(a){if(cq.tc)throw a;Po(a)},complete:function(){}};
var Ro=function(){function a(b){this.message=b?b.length+" errors occurred during unsubscription:\n"+b.map(function(c,d){return d+1+") "+c.toString()}).join("\n  "):"";this.name="UnsubscriptionError";this.errors=b;return this}a.prototype=Object.create(Error.prototype);return a}();
_.qp=Array.isArray||function(a){return a&&"number"===typeof a.length};
_.fq=function(a){this.closed=!1;this.pe=this.ad=null;a&&(this.Hb=a)};
_.fq.prototype.unsubscribe=function(){if(!this.closed){var a=this.ad,b=this.Hb,c=this.pe;this.closed=!0;this.pe=this.ad=null;if(a instanceof _.fq)a.remove(this);else if(null!==a)for(var d=0;d<a.length;++d)a[d].remove(this);if(_.Qo(b))try{b.call(this)}catch(h){var e=h instanceof Ro?So(h.errors):[h]}if((0,_.qp)(c)){d=-1;for(var f=c.length;++d<f;){var g=c[d];if(null!==g&&"object"===typeof g)try{g.unsubscribe()}catch(h){e=e||[],h instanceof Ro?e=e.concat(So(h.errors)):e.push(h)}}}if(e)throw new Ro(e);
}};
_.fq.prototype.add=function(a){var b=a;if(!a)return _.fq.EMPTY;switch(typeof a){case "function":b=new _.fq(a);case "object":if(b===this||b.closed||"function"!==typeof b.unsubscribe)return b;if(this.closed)return b.unsubscribe(),b;b instanceof _.fq||(a=b,b=new _.fq,b.pe=[a]);break;default:throw Error("$`"+a);}var c=b.ad;if(null===c)b.ad=this;else if(c instanceof _.fq){if(c===this)return b;b.ad=[c,this]}else if(-1===c.indexOf(this))c.push(this);else return b;a=this.pe;null===a?this.pe=[b]:a.push(b);return b};
_.fq.prototype.remove=function(a){var b=this.pe;b&&(a=b.indexOf(a),-1!==a&&b.splice(a,1))};var gq=new _.fq;gq.closed=!0;_.fq.EMPTY=gq;
_.Ed();_.Ed();var hq="function"===typeof Symbol?Symbol("aa"):"@@rxSubscriber_"+Math.random();
var To=function(a,b,c){_.fq.call(this);this.qh=null;this.ya=this.Zb=this.ph=!1;switch(arguments.length){case 0:this.destination=eq;break;case 1:if(!a){this.destination=eq;break}if("object"===typeof a){a instanceof To?(this.Zb=a.Zb,this.destination=a,a.add(this)):(this.Zb=!0,this.destination=new iq(this,a));break}default:this.Zb=!0,this.destination=new iq(this,a,b,c)}};_.G(To,_.fq);To.EMPTY=_.fq.EMPTY;To.prototype[hq]=function(){return this};To.create=function(a,b,c){a=new To(a,b,c);a.Zb=!1;return a};
_.k=To.prototype;_.k.next=function(a){this.ya||this.Ba(a)};_.k.error=function(a){this.ya||(this.ya=!0,this.uc(a))};_.k.complete=function(){this.ya||(this.ya=!0,this.Ra())};_.k.unsubscribe=function(){this.closed||(this.ya=!0,_.fq.prototype.unsubscribe.call(this))};_.k.Ba=function(a){this.destination.next(a)};_.k.uc=function(a){this.destination.error(a);this.unsubscribe()};_.k.Ra=function(){this.destination.complete();this.unsubscribe()};
_.k.kg=function(){var a=this.ad;this.ad=null;this.unsubscribe();this.ya=this.closed=!1;this.ad=a;return this};var iq=function(a,b,c,d){To.call(this);this.g=a;var e=this;if(_.Qo(b))var f=b;else b&&(f=b.next,c=b.error,d=b.complete,b!==eq&&(e=Object.create(b),_.Qo(e.unsubscribe)&&this.add(e.unsubscribe.bind(e)),e.unsubscribe=this.unsubscribe.bind(this)));this.h=e;this.Ba=f;this.uc=c;this.Ra=d};_.G(iq,To);iq.EMPTY=To.EMPTY;iq.create=To.create;_.k=iq.prototype;
_.k.next=function(a){if(!this.ya&&this.Ba){var b=this.g;cq.tc&&b.Zb?this.Dh(b,this.Ba,a)&&this.unsubscribe():this.Eh(this.Ba,a)}};_.k.error=function(a){if(!this.ya){var b=this.g,c=cq.tc;if(this.uc)c&&b.Zb?this.Dh(b,this.uc,a):this.Eh(this.uc,a),this.unsubscribe();else if(b.Zb)c?(b.qh=a,b.ph=!0):Po(a),this.unsubscribe();else{this.unsubscribe();if(c)throw a;Po(a)}}};
_.k.complete=function(){var a=this;if(!this.ya){var b=this.g;if(this.Ra){var c=function(){return a.Ra.call(a.h)};cq.tc&&b.Zb?this.Dh(b,c):this.Eh(c)}this.unsubscribe()}};_.k.Eh=function(a,b){try{a.call(this.h,b)}catch(c){this.unsubscribe();if(cq.tc)throw c;Po(c)}};_.k.Dh=function(a,b,c){if(!cq.tc)throw Error("ba");try{b.call(this.h,c)}catch(d){return cq.tc?(a.qh=d,a.ph=!0):Po(d),!0}return!1};_.k.Hb=function(){var a=this.g;this.g=this.h=null;a.unsubscribe()};
_.Zo=function(a){this.C=!1;a&&(this.dc=a)};_.k=_.Zo.prototype;_.k.Sa=function(a){var b=new _.Zo;b.source=this;b.operator=a;return b};_.k.subscribe=function(a,b,c){var d=this.operator;a:{if(a){if(a instanceof To)break a;if(a[hq]){a=a[hq]();break a}}a=a||b||c?new To(a,b,c):new To(eq)}d?a.add(d.call(a,this.source)):a.add(this.source||cq.tc&&!a.Zb?this.dc(a):this.Fh(a));if(cq.tc&&a.Zb&&(a.Zb=!1,a.ph))throw a.qh;return a};
_.k.Fh=function(a){try{return this.dc(a)}catch(b){cq.tc&&(a.ph=!0,a.qh=b),_.Uo(a)?a.error(b):console.warn(b)}};_.k.forEach=function(a,b){var c=this;b=Xo(b);return new b(function(d,e){var f=c.subscribe(function(g){try{a(g)}catch(h){e(h),f&&f.unsubscribe()}},e,d)})};_.k.dc=function(a){var b=this.source;return b&&b.subscribe(a)};_.Zo.prototype[dq]=function(){return this};_.Zo.prototype.g=function(a){for(var b=[],c=0;c<arguments.length;++c)b[c]=arguments[c];return 0===b.length?this:_.Wo(b)(this)};
_.jq=function(a){var b=Xo(b);return new b(function(c,d){var e;a.subscribe(function(f){return e=f},function(f){return d(f)},function(){return c(e)})})};_.Zo.create=function(a){return new _.Zo(a)};
var kq=function(a,b){_.fq.call(this);this.subject=a;this.Fd=b;this.closed=!1};_.G(kq,_.fq);kq.EMPTY=_.fq.EMPTY;kq.prototype.unsubscribe=function(){if(!this.closed){this.closed=!0;var a=this.subject,b=a.Tc;this.subject=null;!b||0===b.length||a.ya||a.closed||(a=b.indexOf(this.Fd),-1!==a&&b.splice(a,1))}};
var lq=function(){function a(){this.message="object unsubscribed";this.name="ObjectUnsubscribedError";return this}a.prototype=Object.create(Error.prototype);return a}();
var mq=function(a){To.call(this,a);this.destination=a};_.G(mq,To);mq.EMPTY=To.EMPTY;mq.create=To.create;_.nq=function(){_.Zo.call(this);this.Tc=[];this.Pc=this.ya=this.closed=!1;this.sh=null};_.G(_.nq,_.Zo);_.nq.prototype[hq]=function(){return new mq(this)};_.k=_.nq.prototype;_.k.Sa=function(a){var b=new oq(this,this);b.operator=a;return b};_.k.next=function(a){if(this.closed)throw new lq;if(!this.ya){var b=this.Tc,c=b.length;b=b.slice();for(var d=0;d<c;d++)b[d].next(a)}};
_.k.error=function(a){if(this.closed)throw new lq;this.Pc=!0;this.sh=a;this.ya=!0;var b=this.Tc,c=b.length;b=b.slice();for(var d=0;d<c;d++)b[d].error(a);this.Tc.length=0};_.k.complete=function(){if(this.closed)throw new lq;this.ya=!0;var a=this.Tc,b=a.length;a=a.slice();for(var c=0;c<b;c++)a[c].complete();this.Tc.length=0};_.k.unsubscribe=function(){this.closed=this.ya=!0;this.Tc=null};_.k.Fh=function(a){if(this.closed)throw new lq;return _.Zo.prototype.Fh.call(this,a)};
_.k.dc=function(a){if(this.closed)throw new lq;if(this.Pc)return a.error(this.sh),_.fq.EMPTY;if(this.ya)return a.complete(),_.fq.EMPTY;this.Tc.push(a);return new kq(this,a)};_.nq.create=function(a,b){return new oq(a,b)};var oq=function(a,b){_.nq.call(this);this.destination=a;this.source=b};_.G(oq,_.nq);oq.create=_.nq.create;oq.prototype.next=function(a){var b=this.destination;b&&b.next&&b.next(a)};oq.prototype.error=function(a){var b=this.destination;b&&b.error&&this.destination.error(a)};
oq.prototype.complete=function(){var a=this.destination;a&&a.complete&&this.destination.complete()};oq.prototype.dc=function(a){return this.source?this.source.subscribe(a):_.fq.EMPTY};
_.pq=function(){_.nq.apply(this,arguments);this.value=null;this.hasCompleted=this.hasNext=!1};_.G(_.pq,_.nq);_.pq.create=_.nq.create;_.pq.prototype.dc=function(a){return this.Pc?(a.error(this.sh),_.fq.EMPTY):this.hasCompleted&&this.hasNext?(a.next(this.value),a.complete(),_.fq.EMPTY):_.nq.prototype.dc.call(this,a)};_.pq.prototype.next=function(a){this.hasCompleted||(this.value=a,this.hasNext=!0)};_.pq.prototype.error=function(a){this.hasCompleted||_.nq.prototype.error.call(this,a)};
_.pq.prototype.complete=function(){this.hasCompleted=!0;this.hasNext&&_.nq.prototype.next.call(this,this.value);_.nq.prototype.complete.call(this)};
_.np=new _.Zo(function(a){return a.complete()});
_.qq=function(a){return function(b){for(var c=0,d=a.length;c<d&&!b.closed;c++)b.next(a[c]);b.complete()}};
var rq=function(a,b,c){this.kind=a;this.value=b;this.error=c},sq=function(a,b){switch(a.kind){case "N":return b.next&&b.next(a.value);case "E":return b.error&&b.error(a.error);case "C":return b.complete&&b.complete()}};rq.prototype.accept=function(a,b,c){if(a&&"function"===typeof a.next)a=sq(this,a);else a:{switch(this.kind){case "N":a=a&&a(this.value);break a;case "E":a=b&&b(this.error);break a;case "C":a=c&&c();break a}a=void 0}return a};var tq=new rq("C"),uq=new rq("N",void 0);
var vq=function(a,b,c){c=void 0===c?0:c;To.call(this,a);this.Aa=b;this.delay=c};_.G(vq,To);vq.EMPTY=To.EMPTY;vq.create=To.create;vq.g=function(a){sq(a.notification,a.destination);this.unsubscribe()};var xq=function(a,b){a.destination.add(a.Aa.schedule(vq.g,a.delay,new wq(b,a.destination)))};vq.prototype.Ba=function(a){xq(this,"undefined"!==typeof a?new rq("N",a):uq)};vq.prototype.uc=function(a){xq(this,new rq("E",void 0,a));this.unsubscribe()};vq.prototype.Ra=function(){xq(this,tq);this.unsubscribe()};
var wq=function(a,b){this.notification=a;this.destination=b};
var yq=function(){_.fq.call(this)};_.G(yq,_.fq);yq.EMPTY=_.fq.EMPTY;yq.prototype.schedule=function(){return this};
var zq=function(a,b){_.fq.call(this);this.Aa=a;this.h=b;this.pending=!1};_.G(zq,yq);zq.EMPTY=yq.EMPTY;zq.prototype.schedule=function(a,b){b=void 0===b?0:b;if(this.closed)return this;this.state=a;a=this.id;var c=this.Aa;null!=a&&(this.id=Aq(this,a,b));this.pending=!0;this.delay=b;this.id=this.id||this.g(c,this.id,b);return this};zq.prototype.g=function(a,b,c){c=void 0===c?0:c;return setInterval(a.flush.bind(a,this),c)};
var Aq=function(a,b,c){c=void 0===c?0:c;if(null!==c&&a.delay===c&&!1===a.pending)return b;clearInterval(b)};zq.prototype.execute=function(a,b){if(this.closed)return Error("da");this.pending=!1;if(a=this.j(a,b))return a;!1===this.pending&&null!=this.id&&(this.id=Aq(this,this.id,null))};zq.prototype.j=function(a){var b=!1,c=void 0;try{this.h(a)}catch(d){b=!0,c=!!d&&d||Error(d)}if(b)return this.unsubscribe(),c};
zq.prototype.Hb=function(){var a=this.id,b=this.Aa.actions,c=b.indexOf(this);this.state=this.h=null;this.pending=!1;this.Aa=null;-1!==c&&b.splice(c,1);null!=a&&(this.id=Aq(this,a,null));this.delay=null};
var Bq=function(a,b){zq.call(this,a,b);this.Aa=a;this.h=b};_.G(Bq,zq);Bq.EMPTY=zq.EMPTY;Bq.prototype.schedule=function(a,b){b=void 0===b?0:b;if(0<b)return zq.prototype.schedule.call(this,a,b);this.delay=b;this.state=a;this.Aa.flush(this);return this};Bq.prototype.execute=function(a,b){return 0<b||this.closed?zq.prototype.execute.call(this,a,b):this.j(a,b)};Bq.prototype.g=function(a,b,c){c=void 0===c?0:c;return null!==c&&0<c||null===c&&0<this.delay?zq.prototype.g.call(this,a,b,c):a.flush(this)};
var Cq=function(a,b){b=void 0===b?Cq.now:b;this.g=a;this.now=b};Cq.prototype.schedule=function(a,b,c){b=void 0===b?0:b;return(new this.g(this,a)).schedule(c,b)};Cq.now=function(){return Date.now()};
var Dq=function(a,b){b=void 0===b?Cq.now:b;Cq.call(this,a,function(){return Dq.delegate&&Dq.delegate!==c?Dq.delegate.now():b()});var c=this;this.actions=[];this.active=!1;this.scheduled=void 0};_.G(Dq,Cq);Dq.now=Cq.now;Dq.prototype.schedule=function(a,b,c){b=void 0===b?0:b;return Dq.delegate&&Dq.delegate!==this?Dq.delegate.schedule(a,b,c):Cq.prototype.schedule.call(this,a,b,c)};
Dq.prototype.flush=function(a){var b=this.actions;if(this.active)b.push(a);else{var c;this.active=!0;do if(c=a.execute(a.state,a.delay))break;while(a=b.shift());this.active=!1;if(c){for(;a=b.shift();)a.unsubscribe();throw c;}}};
var Eq=function(){Dq.apply(this,arguments)};_.G(Eq,Dq);Eq.now=Dq.now;
var Fq=new Eq(Bq);
_.Gq=function(a,b,c){a=void 0===a?Number.POSITIVE_INFINITY:a;b=void 0===b?Number.POSITIVE_INFINITY:b;_.nq.call(this);this.Aa=c;this.h=[];this.A=!1;this.j=1>a?1:a;this.B=1>b?1:b;b===Number.POSITIVE_INFINITY?(this.A=!0,this.next=this.Uo):this.next=this.Vo};_.G(_.Gq,_.nq);_.Gq.create=_.nq.create;_.k=_.Gq.prototype;_.k.Uo=function(a){var b=this.h;b.push(a);b.length>this.j&&b.shift();_.nq.prototype.next.call(this,a)};
_.k.Vo=function(a){this.h.push(new Hq(this.Cj(),a));this.Hj();_.nq.prototype.next.call(this,a)};_.k.dc=function(a){var b=this.A,c=b?this.h:this.Hj(),d=this.Aa,e=c.length;if(this.closed)throw new lq;if(this.ya||this.Pc)var f=_.fq.EMPTY;else this.Tc.push(a),f=new kq(this,a);d&&a.add(a=new vq(a,d));if(b)for(d=0;d<e&&!a.closed;d++)a.next(c[d]);else for(d=0;d<e&&!a.closed;d++)a.next(c[d].value);this.Pc?a.error(this.sh):this.ya&&a.complete();return f};_.k.Cj=function(){return(this.Aa||Fq).now()};
_.k.Hj=function(){for(var a=this.Cj(),b=this.j,c=this.B,d=this.h,e=d.length,f=0;f<e&&!(a-d[f].time<c);)f++;e>b&&(f=Math.max(f,e-b));0<f&&d.splice(0,f);return d};var Hq=function(a,b){this.time=a;this.value=b};
var ap=function(a){this.gd=a};ap.prototype.call=function(a,b){var c=this.gd;c.hf++;a=new Iq(a,c);b=b.subscribe(a);a.closed||(a.connection=c.connect());return b};var Iq=function(a,b){To.call(this,a);this.gd=b;this.connection=null};_.G(Iq,To);Iq.EMPTY=To.EMPTY;Iq.create=To.create;
Iq.prototype.Hb=function(){var a=this.gd;if(a){this.gd=null;var b=a.hf;0>=b?this.connection=null:(a.hf=b-1,1<b?this.connection=null:(b=this.connection,a=a.me,this.connection=null,!a||b&&a!==b||a.unsubscribe()))}else this.connection=null};
var Jq=function(a,b){_.Zo.call(this);this.source=a;this.mm=b;this.hf=0;this.hg=!1},Mq;_.G(Jq,_.Zo);Jq.create=_.Zo.create;Jq.prototype.dc=function(a){return this.Eg().subscribe(a)};Jq.prototype.Eg=function(){var a=this.jg;if(!a||a.ya)this.jg=this.mm();return this.jg};Jq.prototype.connect=function(){var a=this.me;a||(this.hg=!1,a=this.me=new _.fq,a.add(this.source.subscribe(new Kq(this.Eg(),this))),a.closed&&(this.me=null,a=_.fq.EMPTY));return a};Jq.prototype.Ol=function(){return _.bp()(this)};Mq=Jq.prototype;
_.Lq={operator:{value:null},hf:{value:0,writable:!0},jg:{value:null,writable:!0},me:{value:null,writable:!0},dc:{value:Mq.dc},hg:{value:Mq.hg,writable:!0},Eg:{value:Mq.Eg},connect:{value:Mq.connect},Ol:{value:Mq.Ol}};var Kq=function(a,b){mq.call(this,a);this.gd=b};_.G(Kq,mq);Kq.EMPTY=mq.EMPTY;Kq.create=mq.create;Kq.prototype.uc=function(a){this.Hb();mq.prototype.uc.call(this,a)};Kq.prototype.Ra=function(){this.gd.hg=!0;this.Hb();mq.prototype.Ra.call(this)};
Kq.prototype.Hb=function(){var a=this.gd;if(a){this.gd=null;var b=a.me;a.hf=0;a.jg=null;a.me=null;b&&b.unsubscribe()}};
var cp=function(a){this.g=a};cp.prototype.call=function(a,b){return b.subscribe(new Nq(a,this.g,void 0))};var Nq=function(a,b,c){To.call(this,a);this.g=b;this.count=0;this.h=c||this};_.G(Nq,To);Nq.EMPTY=To.EMPTY;Nq.create=To.create;Nq.prototype.Ba=function(a){try{var b=this.g.call(this.h,a,this.count++)}catch(c){this.destination.error(c);return}this.destination.next(b)};
var Oq=function(){To.apply(this,arguments)};_.G(Oq,To);Oq.EMPTY=To.EMPTY;Oq.create=To.create;Oq.prototype.ae=function(a){this.destination.next(a)};Oq.prototype.Oe=function(){this.destination.complete()};
var ep=function(a,b,c){To.call(this);this.parent=a;this.tp=c;this.index=0};_.G(ep,To);ep.EMPTY=To.EMPTY;ep.create=To.create;ep.prototype.Ba=function(a){this.parent.ae(a,this.tp,this.index++)};ep.prototype.uc=function(a){this.parent.destination.error(a);this.unsubscribe()};ep.prototype.Ra=function(){this.parent.Oe(this);this.unsubscribe()};
var Pq="function"===typeof Symbol&&Symbol.iterator?Symbol.iterator:"@@iterator";
var Qq=function(a){return function(b){var c=a[Pq]();do{var d=c.next();if(d.done){b.complete();break}b.next(d.value);if(b.closed)break}while(1);"function"===typeof c.return&&b.add(function(){c.return&&c.return()});return b}};
var Rq=function(a){return function(b){var c=a[dq]();if("function"!==typeof c.subscribe)throw new TypeError("fa");return c.subscribe(b)}};
var Sq=function(a){return function(b){a.then(function(c){b.closed||(b.next(c),b.complete())},function(c){return b.error(c)}).then(null,Po);return b}};
var fp=function(a){if(a&&"function"===typeof a[dq])return Rq(a);if(a&&"number"===typeof a.length&&"function"!==typeof a)return _.qq(a);if(a&&"function"!==typeof a.subscribe&&"function"===typeof a.then)return Sq(a);if(a&&"function"===typeof a[Pq])return Qq(a);throw new TypeError("ga`"+(null!==a&&"object"===typeof a?"an invalid object":"'"+a+"'"));};
var Tq;Tq={};_.Uq=function(a,b){Oq.call(this,a);this.h=b;this.active=0;this.values=[];this.j=[]};_.G(_.Uq,Oq);_.Uq.EMPTY=Oq.EMPTY;_.Uq.create=Oq.create;_.k=_.Uq.prototype;_.k.Ba=function(a){this.values.push(Tq);this.j.push(a)};_.k.Ra=function(){var a=this.j,b=a.length;if(0===b)this.destination.complete();else{this.g=this.active=b;for(var c=0;c<b;c++){var d=a[c];this.add(_.gp(this,d,d,c))}}};_.k.Oe=function(){0===--this.active&&this.destination.complete()};
_.k.ae=function(a,b){var c=this.values,d=c[b];d=this.g?d===Tq?--this.g:this.g:0;c[b]=a;0===d&&(this.h?this.Cm(c):this.destination.next(c.slice()))};_.k.Cm=function(a){try{var b=this.h.apply(this,a)}catch(c){this.destination.error(c);return}this.destination.next(b)};
var jp=function(a,b){b=void 0===b?Number.POSITIVE_INFINITY:b;this.h=a;this.g=b};jp.prototype.call=function(a,b){return b.subscribe(new Vq(a,this.h,this.g))};var Vq=function(a,b,c){c=void 0===c?Number.POSITIVE_INFINITY:c;Oq.call(this,a);this.j=b;this.h=c;this.hasCompleted=!1;this.g=[];this.index=this.active=0};_.G(Vq,Oq);Vq.EMPTY=Oq.EMPTY;Vq.create=Oq.create;
Vq.prototype.Ba=function(a){if(this.active<this.h)a:{var b=this.index++;try{var c=this.j(a,b)}catch(d){this.destination.error(d);break a}this.active++;a=new ep(this,a,b);b=this.destination;b.add(a);c=_.gp(this,c,void 0,void 0,a);c!==a&&b.add(c)}else this.g.push(a)};Vq.prototype.Ra=function(){this.hasCompleted=!0;0===this.active&&0===this.g.length&&this.destination.complete();this.unsubscribe()};Vq.prototype.ae=function(a){this.destination.next(a)};
Vq.prototype.Oe=function(a){var b=this.g;this.remove(a);this.active--;0<b.length?this.Ba(b.shift()):0===this.active&&this.hasCompleted&&this.destination.complete()};
_.sp=new Dq(zq);
_.Wq=function(a,b,c){To.call(this,a);this.predicate=b;this.g=c;this.count=0};_.G(_.Wq,To);_.Wq.EMPTY=To.EMPTY;_.Wq.create=To.create;_.Wq.prototype.Ba=function(a){try{var b=this.predicate.call(this.g,a,this.count++)}catch(c){this.destination.error(c);return}b&&this.destination.next(a)};
_.Xq=function(){function a(){this.message="argument out of range";this.name="ArgumentOutOfRangeError";return this}a.prototype=Object.create(Error.prototype);return a}();
(function(){function a(){this.message="no elements in sequence";this.name="EmptyError";return this}a.prototype=Object.create(Error.prototype);return a})();
var Bp=function(){function a(){this.message="Timeout has occurred";this.name="TimeoutError";return this}a.prototype=Object.create(Error.prototype);return a}();
_.Yq=function(a,b,c){Oq.call(this,a);this.selector=b;this.g=c};_.G(_.Yq,Oq);_.Yq.EMPTY=Oq.EMPTY;_.Yq.create=Oq.create;_.Yq.prototype.error=function(a){if(!this.ya){try{var b=this.selector(a,this.g)}catch(c){Oq.prototype.error.call(this,c);return}this.kg();a=new ep(this,void 0,void 0);this.add(a);b=_.gp(this,b,void 0,void 0,a);b!==a&&this.add(b)}};
_.Zq=function(a,b){Oq.call(this,a);this.j=b;this.value=null;this.h=!1;this.g=null};_.G(_.Zq,Oq);_.Zq.EMPTY=Oq.EMPTY;_.Zq.create=Oq.create;_.Zq.prototype.Ba=function(a){try{var b=this.j.call(this,a);if(b){var c=this.g;this.value=a;this.h=!0;c&&(c.unsubscribe(),this.remove(c));(c=_.gp(this,b))&&!c.closed&&this.add(this.g=c)}}catch(d){this.destination.error(d)}};_.Zq.prototype.Ra=function(){$q(this);this.destination.complete()};_.Zq.prototype.ae=function(){$q(this)};_.Zq.prototype.Oe=function(){$q(this)};
var $q=function(a){if(a.h){var b=a.value,c=a.g;c&&(a.g=null,c.unsubscribe(),a.remove(c));a.value=null;a.h=!1;Oq.prototype.Ba.call(a,b)}};
_.ar=function(a,b,c){To.call(this,a);this.A=b;this.Aa=c;this.h=this.g=null;this.j=!1};_.G(_.ar,To);_.ar.EMPTY=To.EMPTY;_.ar.create=To.create;_.ar.prototype.Ba=function(a){br(this);this.h=a;this.j=!0;this.add(this.g=this.Aa.schedule(wp,this.A,this))};_.ar.prototype.Ra=function(){vp(this);this.destination.complete()};var vp=function(a){br(a);if(a.j){var b=a.h;a.h=null;a.j=!1;a.destination.next(b)}},br=function(a){var b=a.g;null!==b&&(a.remove(b),b.unsubscribe(),a.g=null)};
_.cr=function(a,b){To.call(this,a);this.defaultValue=b;this.isEmpty=!0};_.G(_.cr,To);_.cr.EMPTY=To.EMPTY;_.cr.create=To.create;_.cr.prototype.Ba=function(a){this.isEmpty=!1;this.destination.next(a)};_.cr.prototype.Ra=function(){this.isEmpty&&this.destination.next(this.defaultValue);this.destination.complete()};
_.dr=function(a,b){To.call(this,a);this.total=b;this.count=0};_.G(_.dr,To);_.dr.EMPTY=To.EMPTY;_.dr.create=To.create;_.dr.prototype.Ba=function(a){var b=this.total,c=++this.count;c<=b&&(this.destination.next(a),c===b&&(this.destination.complete(),this.unsubscribe()))};
_.er=function(a,b){To.call(this,a);this.total=b;this.g=[];this.count=0};_.G(_.er,To);_.er.EMPTY=To.EMPTY;_.er.create=To.create;_.er.prototype.Ba=function(a){var b=this.g,c=this.total,d=this.count++;b.length<c?b.push(a):b[d%c]=a};_.er.prototype.Ra=function(){var a=this.destination,b=this.count;if(0<b)for(var c=this.count>=this.total?this.total:this.count,d=this.g,e=0;e<c;e++){var f=b++%c;a.next(d[f])}a.complete()};
_.fr=function(a,b,c,d){To.call(this,a);this.j=b;this.g=c;this.h=d;this.index=0};_.G(_.fr,To);_.fr.EMPTY=To.EMPTY;_.fr.create=To.create;_.fr.prototype.Ba=function(a){var b=this.destination;if(this.h){var c=this.index++;try{var d=this.j(this.g,a,c)}catch(e){b.error(e);return}this.g=d;b.next(d)}else this.g=a,this.h=!0,b.next(a)};
var xp=function(a,b){this.Kf=a;this.source=b};xp.prototype.call=function(a,b){return b.subscribe(new gr(a,this.Kf,this.source))};var gr=function(a,b,c){Oq.call(this,a);this.Kf=b;this.source=c;this.Uf=this.Tf=this.errors=null};_.G(gr,Oq);gr.EMPTY=Oq.EMPTY;gr.create=Oq.create;
gr.prototype.error=function(a){if(!this.ya){var b=this.errors,c=this.Tf,d=this.Uf;if(c)this.Uf=this.errors=null;else{b=new _.nq;try{var e=this.Kf;c=e(b)}catch(f){return Oq.prototype.error.call(this,f)}d=_.gp(this,c)}this.kg();this.errors=b;this.Tf=c;this.Uf=d;b.next(a)}};gr.prototype.Hb=function(){var a=this.errors,b=this.Uf;a&&(a.unsubscribe(),this.errors=null);b&&(b.unsubscribe(),this.Uf=null);this.Tf=null};gr.prototype.ae=function(){var a=this.Hb;this.Hb=null;this.kg();this.Hb=a;this.source.subscribe(this)};
_.hr=function(a,b){Oq.call(this,a);this.h=b;this.index=0};_.G(_.hr,Oq);_.hr.EMPTY=Oq.EMPTY;_.hr.create=Oq.create;_.k=_.hr.prototype;_.k.Ba=function(a){var b=this.index++;try{var c=this.h(a,b)}catch(e){this.destination.error(e);return}var d=this.g;d&&d.unsubscribe();a=new ep(this,a,b);b=this.destination;b.add(a);this.g=_.gp(this,c,void 0,void 0,a);this.g!==a&&b.add(this.g)};_.k.Ra=function(){var a=this.g;a&&!a.closed||Oq.prototype.Ra.call(this);this.unsubscribe()};_.k.Hb=function(){this.g=null};
_.k.Oe=function(a){this.destination.remove(a);this.g=null;this.ya&&Oq.prototype.Ra.call(this)};_.k.ae=function(a){this.destination.next(a)};
_.ir=function(a){Oq.call(this,a);this.g=!1};_.G(_.ir,Oq);_.ir.EMPTY=Oq.EMPTY;_.ir.create=Oq.create;_.ir.prototype.ae=function(){this.g=!0;this.complete()};_.ir.prototype.Oe=function(){};
_.jr=function(a,b,c,d){To.call(this,a);this.h=this.j=this.A=Vo;this.j=c||Vo;this.h=d||Vo;_.Qo(b)?(this.g=this,this.A=b):b&&(this.g=b,this.A=b.next||Vo,this.j=b.error||Vo,this.h=b.complete||Vo)};_.G(_.jr,To);_.jr.EMPTY=To.EMPTY;_.jr.create=To.create;_.jr.prototype.Ba=function(a){try{this.A.call(this.g,a)}catch(b){this.destination.error(b);return}this.destination.next(a)};_.jr.prototype.uc=function(a){try{this.j.call(this.g,a)}catch(b){this.destination.error(b);return}this.destination.error(a)};
_.jr.prototype.Ra=function(){try{this.h.call(this.g)}catch(a){this.destination.error(a);return}return this.destination.complete()};
var zp=function(a,b){this.g=a;this.Aa=b};zp.prototype.call=function(a,b){return b.subscribe(new kr(a,!1,3E4,this.g,this.Aa))};var kr=function(a,b,c,d,e){Oq.call(this,a);this.j=b;this.g=c;this.h=d;this.Aa=e;this.action=null;lr(this)};_.G(kr,Oq);kr.EMPTY=Oq.EMPTY;kr.create=Oq.create;var mr=function(a){var b=a.h;a.kg();a.add(_.gp(a,b))},lr=function(a){var b=a.action;b?a.action=b.schedule(a,a.g):a.add(a.action=a.Aa.schedule(mr,a.g,a))};
kr.prototype.Ba=function(a){this.j||lr(this);Oq.prototype.Ba.call(this,a)};kr.prototype.Hb=function(){this.h=this.Aa=this.action=null};
var Hp=/^-?(?:|0|[1-9][0-9]*)(?:\.[0-9]*)?$/,Ip=/\{([^\{]*)\}([\/])*/g;
var Tp=function(a,b,c){this.method=a;this.withCredentials=this.headers=void 0;a={};b=Jp(b,a);if(!_.v.isEmpty(a)){var d=void 0;d=void 0===d?!1:d;if(!_.v.isEmpty(a)){var e=b.includes("?")?"&":"?";b+=e+Gp(a,void 0===d?!1:d).join("&")}}this.uri=b;this.content=!c||"string"===typeof c||"undefined"!==typeof Blob&&c instanceof Blob?c:Gp(c,!1).join("&")};Tp[_.Ji]=["method","post","opt_data"];
var nr=function(a,b,c){this.errorMessage=a;this.statusCode=b;this.url=c};nr.prototype.toString=function(){var a="";if(this.url){var b=this.url.split("?");b.length&&(a=b[0])}return"Network Error: "+this.statusCode+" "+this.errorMessage+", "+a};
var or=function(a,b,c){_.Dh.call(this);this.Y=a;this.S=b;this.networkStatus=c;this.j=null;this.N=function(){};this.errorHandler=function(){};this.responseType=""};_.G(or,_.Dh);var Pp=function(a,b){a.N=b},Rp=function(a,b){a.errorHandler=b};or.prototype.send=function(a){var b=Object.assign(this.S()||{},a.headers||{}),c=this.F();a.withCredentials&&(c.G=!0);c.send(a.uri,a.method,a.content,b)};
or.prototype.F=function(){var a=this;this.j&&_.Eh(this.j);var b=this.Y({progressHandler:null});this.j=b;b.N=this.responseType;var c=function(){a.C(b,a.errorHandler)};_.sn(b,"timeout",c);_.sn(b,"error",c);_.sn(b,"success",function(){var d=a.N;a.networkStatus.Ql();d(b.P(),$p(b));_.Eh(a)});_.Ko(this,_.Eb(_.Eh,b));return b};or.prototype.C=function(a,b){var c=a.A,d=a.Ma();if(8===c||6===c)d=0<d?d:608;this.networkStatus.aj(d,String(a.J));b(new nr(a.getLastError(),d,String(a.J)),a.P());_.Eh(this)};
or.prototype.abort=function(){this.j&&this.j.abort();_.Eh(this)};or[_.Ji]=["xhrIoFactory","getHeaders","networkStatus"];
var pr=[5E3,1E4,15E3],Lp=function(a,b,c,d,e,f){f=void 0===f?function(){return!1}:f;or.call(this,a,b,c);this.h=d;this.V=e;this.P=f;this.B=function(){};this.timeLimit=this.J=this.G=this.timeoutCount=0;this.A=-1;this.g=0};_.G(Lp,or);Lp.prototype.send=function(a){var b=this;this.timeLimit=this.h.getCurrentTime().valueOf()+3E4;this.g=3E4;this.B=function(){b.J=b.h.getCurrentTime().valueOf();or.prototype.send.call(b,a)};this.B()};
Lp.prototype.F=function(){var a=or.prototype.F.call(this),b=pr[this.timeoutCount]||this.g;this.g<b&&(b=this.g);a.F=Math.max(0,b);return a};
Lp.prototype.C=function(a,b){if(this.P(a))or.prototype.C.call(this,a,b);else{var c=this.h.getCurrentTime().valueOf();this.g=this.timeLimit-c;c=c-this.J<pr[this.timeoutCount];var d=1E3*(Math.pow(2,this.G)+this.V());0>=this.g||c&&d>this.g?or.prototype.C.call(this,a,b):c?(++this.G,this.A=this.h.setTimeout(this.B,d)):(this.timeoutCount<pr.length-1&&++this.timeoutCount,this.B())}};Lp.prototype.abort=function(){-1!==this.A&&(this.h.clearTimeout(this.A),this.A=-1);or.prototype.abort.call(this)};
Lp[_.Ji]="xhrIoFactory getHeaders networkStatus timeService getRandom opt_errorFilter".split(" ");
var qr=function(a){this.A=a};_.G(qr,_.xh);qr.prototype.g=function(){var a=new XMLHttpRequest;a.upload.addEventListener("progress",this.A,!1);return a};
var Mp=function(a){_.Gn.call(this,rr(a))};_.G(Mp,_.Gn);var rr=function(a){var b=void 0;a&&(b=new qr(function(c){c&&a(c.loaded,c.total)}));return b};
Mp.prototype.P=function(){if(this.g&&4==_.Rn(this)){var a=this.g.getResponseHeader("content-type");a=null===a?void 0:a}else a=void 0;if(null==a)return"";if("arraybuffer"===this.N)var b=_.Gn.prototype.P.call(this);else if(a&&a.includes("xml"))try{b=this.g?this.g.responseXML:null}catch(c){b=null}else if(a&&a.includes("html"))b=_.Tn(this);else if(a&&!a.includes("image"))try{b=this.g?_.Mo(this.g.responseText):void 0}catch(c){b=_.Tn(this)}return b};Mp[_.Ji]=["opt_progressHandler"];
var Sp=function(a,b){a=void 0===a?608:a;b=Error.call(this,"Network Error: "+a+" "+(void 0===b?"":b));this.message=b.message;"stack"in b&&(this.stack=b.stack);this.status=a};_.G(Sp,Error);
var Np={setTimeout:function(a,b){window.setTimeout(a,b)},clearTimeout:function(a){window.clearTimeout(a)},getCurrentTime:function(){return new Date(_.Jo())}},Qp={ok:!0,status:200};
var Xp=new Set([400,403,404,402,401]);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.sr=function(a){return void 0!==a.effectiveObfuscatedGaiaId};_.ur=function(a,b){return a===_.tr||b===_.tr?!1:a===b?!0:_.sr(b)!==_.sr(a)?!1:a.effectiveObfuscatedGaiaId===b.effectiveObfuscatedGaiaId?_.v.equals(a.identityCredentials,b.identityCredentials):!1};_.m().w("syt");
_.tr={identityType:"UNAUTHENTICATED_IDENTITY_TYPE_UNKNOWN"};
var vr;vr={};_.wr=(vr.UNAUTHENTICATED_IDENTITY_TYPE_GUEST=!0,vr.UNAUTHENTICATED_IDENTITY_TYPE_INCOGNITO=!0,vr.UNAUTHENTICATED_IDENTITY_TYPE_UNKNOWN=!0,vr);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var zr;_.xr=function(a,b){return new _.Zo(function(c){var d=new _.fq,e=0;d.add(b.schedule(function(){e===a.length?c.complete():(c.next(a[e++]),c.closed||d.add(this.schedule()))}));return d})};_.yr=function(a){for(var b=[],c=0;c<arguments.length;++c)b[c]=arguments[c];c=b[b.length-1];return _.Yo(c)?(b.pop(),_.xr(b,c)):new _.Zo(_.qq(b))};_.Ar=function(a){return function(b){var c=new zr(a);b=b.Sa(c);return c.g=b}};_.Cr=function(a){return function(b){return b.Sa(new Br(a))}};
_.Dr=function(a,b,c){return{accessToken:a,accessTokenExpiration:Math.round(1E3*Math.round(.9*b)+_.Jo()),identity:c}};zr=function(a){this.selector=a};zr.prototype.call=function(a,b){return b.subscribe(new _.Yq(a,this.selector,this.g))};var Br=function(a){this.g=a};Br.prototype.call=function(a,b){return b.subscribe(new _.hr(a,this.g))};_.Er={identityType:"UNAUTHENTICATED_IDENTITY_TYPE_GUEST"};_.m().w("syv");
var Fr=_.p("IdentityAdapter","bwGxKf");
_.Gr=new _.Dl("account-credentials");_.Hr=new _.Dl("active-account");_.Ir=new _.Dl("last-identity-used");_.Jr=new _.Dl("available-identities");
_.Kr=function(){this.storage=_.r.get(_.vd);this.g="oauth"};_.k=_.Kr.prototype;
_.k.Zi=function(a){var b=a.identityCredentials,c=_.pd().oAuthClientProfiles[b.clientIdName];if(!c)throw Error("la`"+b.clientIdName);b={method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({client_id:c.clientId,client_secret:c.rg,refresh_token:b.refreshToken,grant_type:"refresh_token"})};b=_.jq(_.Yp("/o/oauth2/token",b).g(_.Cr(function(d){return d.json()}),_.dp(function(d){return _.Dr(d.access_token,d.expires_in,a)})));return _.I(b).then(function(d){if(!d)throw Error("ma");
return d})};_.k.bj=function(a){var b=this.get(),c=this.storage.get(_.Ir);c&&_.ur(c,a)&&this.storage.remove(_.Ir);delete b[a.effectiveObfuscatedGaiaId];this.storage.set(_.Jr,b);a={method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({token:a.identityCredentials.refreshToken})};a=_.jq(_.Yp("/o/oauth2/revoke",a).g(_.Cr(function(d){return d.json()}),_.dp(function(){return!0}),_.Ar(function(d){_.C(d);return _.yr(!0)})));return _.I(a)};
_.k.Vc=function(a){var b=this.get();b[a.effectiveObfuscatedGaiaId]=a;this.storage.set(_.Jr,b);return _.I(!0)};_.k.Hi=function(){var a=this.get();return _.I(a)};_.k.Jf=function(){var a=this.storage.get(_.Ir);return a?_.wr[a.identityType]||_.sr(a)?_.I(a):(_.C(Error("na")),this.storage.remove(_.Ir),_.I(_.Er)):_.I(_.Er)};_.k.Ii=function(a){this.storage.set(_.Ir,a)};_.k.get=function(){return this.storage.get(_.Jr)||{}};
_.Lr=_.K(Fr,function(){return new _.Kr});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("syaq");

_.K(_.Lr,function(){return new _.Kr});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("em1");


_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("em3");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("em2");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.Nr=function(a,b){var c=_.Mr();c&&c.publish.call(c,a.toString(),a,b)};_.Mr=function(){return _.lb("ytPubsub2Pubsub2Instance")};_.m().w("syk");
_.Or=function(a,b){this.version=a;this.args=b};
_.Pr=function(a,b){this.topic=a;this.g=b};_.Pr.prototype.toString=function(){return this.topic};
var Qr=_.lb("ytPubsub2Pubsub2Instance")||new _.Xj;_.Xj.prototype.subscribe=_.Xj.prototype.subscribe;_.Xj.prototype.unsubscribeByKey=_.Xj.prototype.j;_.Xj.prototype.publish=_.Xj.prototype.C;_.Xj.prototype.clear=_.Xj.prototype.clear;_.ab("ytPubsub2Pubsub2Instance",Qr,void 0);_.Rr=_.lb("ytPubsub2Pubsub2SubscribedKeys")||{};_.ab("ytPubsub2Pubsub2SubscribedKeys",_.Rr,void 0);_.Sr=_.lb("ytPubsub2Pubsub2TopicToKeys")||{};_.ab("ytPubsub2Pubsub2TopicToKeys",_.Sr,void 0);
_.Tr=_.lb("ytPubsub2Pubsub2IsAsync")||{};_.ab("ytPubsub2Pubsub2IsAsync",_.Tr,void 0);_.ab("ytPubsub2Pubsub2SkipSubKey",null,void 0);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Zr,$r,bs,ds;
_.Vr=function(){a:{if(window.crypto&&window.crypto.getRandomValues)try{var a=Array(16),b=new Uint8Array(16);window.crypto.getRandomValues(b);for(var c=0;c<a.length;c++)a[c]=b[c];var d=a;break a}catch(e){}d=Array(16);for(a=0;16>a;a++){b=(0,_.Sb)();for(c=0;c<b%23;c++)d[a]=Math.random();d[a]=Math.floor(256*Math.random())}if(Ur)for(a=1,b=0;b<Ur.length;b++)d[a%16]=d[a%16]^d[(a-1)%16]/4^Ur.charCodeAt(b),a++}a=[];for(b=0;b<d.length;b++)a.push("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".charAt(d[b]&63));
return a.join("")};_.Yr=function(a){a&&_.fc("foregroundHeartbeatScreenAssociated",{clientDocumentNonce:_.Wr,clientScreenNonce:a},_.Xr)};Zr=function(a){a=void 0===a?0:a;return 0==a?"client-screen-nonce":"client-screen-nonce."+a};$r=function(a){a=void 0===a?0:a;return 0==a?"ROOT_VE_TYPE":"ROOT_VE_TYPE."+a};_.as=function(a){return _.u($r(void 0===a?0:a),void 0)};bs=function(){var a=_.u("csn-to-ctt-auth-info");a||(a={},_.db("csn-to-ctt-auth-info",a));return a};
_.cs=function(a){a=void 0===a?0:a;var b=_.u(Zr(a));if(!b&&!_.u("USE_CSN_FALLBACK",!0))return null;b||0!=a||(_.fb("kevlar_client_side_screens")||_.fb("c3_client_side_screens")?b="UNDEFINED_CSN":b=_.u("EVENT_ID"));return b?b:null};ds=function(a,b,c){var d=bs();(c=_.cs(c))&&delete d[c];b&&(d[a]=b)};_.es=function(a){return bs()[a]};
_.fs=function(a,b,c,d){c=void 0===c?0:c;if(a!==_.u(Zr(c))||b!==_.u($r(c)))if(ds(a,d,c),_.db(Zr(c),a),_.db($r(c),b),0==c||_.fb("web_screen_associated_all_layers"))b=function(){setTimeout(function(){_.Yr(a)},0)},"requestAnimationFrame"in window?window.requestAnimationFrame(b):b()};_.m().w("syi");
var Ur=(0,_.Sb)().toString();
_.Wr=_.lb("ytLoggingDocDocumentNonce_")||_.Vr();_.ab("ytLoggingDocDocumentNonce_",_.Wr,void 0);_.Xr=_.gd;
_.ab("yt_logging_screen.getRootVeType",_.as,void 0);_.ab("yt_logging_screen.getCurrentCsn",_.cs,void 0);_.ab("yt_logging_screen.getCttAuthInfo",_.es,void 0);_.ab("yt_logging_screen.setCurrentScreen",_.fs,void 0);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var hs,is,ms,ks,ps,ls,ts,ws,ss,vs,ys,Is,ht,os,jt,lt,mt,js;_.gs=function(a){_.nd(a,"WARNING")};hs=function(a){var b=_.cb.EXPERIMENT_FLAGS;return b?b[a]:void 0};
is=function(a,b){if(!a||/[?&]dsh=1(&|$)/.test(a))return null;if(/[?&]ae=1(&|$)/.test(a)){var c=/[?&]adurl=([^&]+)/.exec(a);if(!c)return null;b=b?c.index:a.length;try{return{Im:a.slice(0,b)+"&act=1"+a.slice(b),Tm:decodeURIComponent(c[1])}}catch(e){return null}}if(/[?&]ae=2(&|$)/.test(a)){c=a;var d="";b&&(b=a.indexOf("&adurl="),0<b&&(c=a.slice(0,b),d=a.slice(b)));return{Im:c+"&act=1"+d,Tm:c+"&dct=1"+d}}return null};
ms=function(a,b,c,d,e){e=void 0===e?"":e;a&&(c&&(c=_.sf,c=!(c&&0<=c.toLowerCase().indexOf("cobalt"))),c?a&&(b=_.hf(_.mf(a)),"about:invalid#zClosurez"===b?a="":(b instanceof _.zf?a=b:(d="object"==typeof b,a=null,d&&b.wi&&(a=b.$h()),b=_.Ze(d&&b.Ac?b.Jb():String(b)),a=_.Pc(b,a)),a=_.Bf(a),a=_.gc((0,_.al)(a))),_.Qe(a)||(a=js("IFRAME",{src:'javascript:"<body><img src=\\""+'+a+'+"\\"></body>"',style:"display:none"}),_.Ng(a).body.appendChild(a))):e?_.Hc(a,b,"POST",e,d):_.u("USE_NET_AJAX_FOR_PING_TRANSPORT",
!1)||d?_.Hc(a,b,"GET","",d):ks(a,b)||ls(a,b))};ks=function(a,b){if(!hs("web_use_beacon_api_for_ad_click_server_pings"))return!1;if(hs("use_sonic_js_library_for_v4_support"))try{var c,d=_.uc(_.tc(5,a));if(!(c=!(d&&d.endsWith("/aclk")))){var e=new ns({url:a});c=!(e.B?e.j&&e.g&&e.g[1]||e.A:e.h)}if(c)return!1}catch(f){return _.mb(Error("qa`"+a)),!1}else if(c=_.uc(_.tc(5,a)),!c||-1==c.indexOf("/aclk")||"1"!==os(a,"ae")||"1"!==os(a,"act"))return!1;return ps(a)?(b&&b(),!0):!1};
ps=function(a,b){try{if(window.navigator&&window.navigator.sendBeacon&&window.navigator.sendBeacon(a,void 0===b?"":b))return!0}catch(c){}return!1};ls=function(a,b){var c=new Image,d=""+qs++;rs[d]=c;c.onload=c.onerror=function(){b&&rs[d]&&b();delete rs[d]};c.src=a};_.us=function(a,b){for(var c=[],d=1;d<arguments.length;++d)c[d-1]=arguments[d];if(!ss(a)||c.some(function(e){return!ss(e)}))throw Error("ra");c=_.A(c);for(d=c.next();!d.done;d=c.next())ts(a,d.value);return a};
ts=function(a,b){for(var c in b)if(ss(b[c])){if(c in a&&!ss(a[c]))throw Error("sa");c in a||(a[c]={});ts(a[c],b[c])}else if(vs(b[c])){if(c in a&&!vs(a[c]))throw Error("ta");c in a||(a[c]=[]);ws(a[c],b[c])}else a[c]=b[c];return a};ws=function(a,b){b=_.A(b);for(var c=b.next();!c.done;c=b.next())c=c.value,ss(c)?a.push(ts({},c)):vs(c)?a.push(ws([],c)):a.push(c);return a};ss=function(a){return"object"===typeof a&&!Array.isArray(a)};vs=function(a){return"object"===typeof a&&Array.isArray(a)};
ys=function(a){var b=_.xs(a);if(b.aft)return b.aft;a=_.u((a||"")+"TIMING_AFT_KEYS",["ol"]);for(var c=a.length,d=0;d<c;d++){var e=b[a[d]];if(e)return e}return NaN};_.zs=function(a){return!!_.lb("yt.timing."+(a||"")+"pingSent_")};_.As=function(a,b){_.ab("yt.timing."+(b||"")+"pingSent_",a,void 0)};_.Cs=function(a){return _.lb("ytcsi."+(a||"")+"data_")||_.Bs(a)};_.Ds=function(a){a=_.Cs(a);a.info||(a.info={});return a.info};_.xs=function(a){a=_.Cs(a);a.tick||(a.tick={});return a.tick};
_.Es=function(a){var b=_.Cs(a).nonce;b||(b=_.Vr(),_.Cs(a).nonce=b);return b};_.Bs=function(a){var b={tick:{},info:{}};_.ab("ytcsi."+(a||"")+"data_",b,void 0);return b};Is=function(a){var b=_.xs(a||""),c=ys(a);c&&!Fs&&(_.Nr(Gs,new Hs(Math.round(c-b._start),a)),Fs=!0)};_.Js=function(){var a=_.lb("ytcsi.debug");a||(a=[],_.ab("ytcsi.debug",a,void 0),_.ab("ytcsi.reference",{},void 0));return a};
_.Ls=function(a){a=a||"";var b=_.Ks();if(b[a])return b[a];var c=_.Js(),d={timerName:a,info:{},tick:{}};c.push(d);return b[a]=d};_.Ks=function(){var a=_.lb("ytcsi.reference");if(a)return a;_.Js();return _.lb("ytcsi.reference")};_.Ms=function(a){return!!_.u("FORCE_CSI_ON_GEL",!1)||_.fb("csi_on_gel")||!!_.Cs(a).useGel};
_.Qs=function(a,b,c){var d=_.Ns(c);d.gelTicks&&(d.gelTicks["tick_"+a]=!0);c||b||(0,_.xb)();return _.Ms(c)?(_.Ls(c||"").tick[a]=b||(0,_.xb)(),d=_.Es(c),"_start"===a?(a=_.Os(),Ps(a,"baseline_"+d)||_.hd("latencyActionBaselined",{clientActionNonce:d},{timestamp:b})):_.Os().tick(a,d,b),Is(c),!0):!1};
_.Vs=function(a,b,c){c=_.Ns(c);if(c.gelInfos)c.gelInfos["info_"+a]=!0;else{var d={};c.gelInfos=(d["info_"+a]=!0,d)}if(a in Rs){if(a.match("_rid")){var e=a.split("_rid")[0];a="REQUEST_ID"}c=Rs[a];_.Cc(Ss,c)&&(b=!!b);a in Ts&&"string"===typeof b&&(b=Ts[a]+b.toUpperCase());a=b;b=c.split(".");for(var f=d={},g=0;g<b.length-1;g++){var h=b[g];f[h]={};f=f[h]}f[b[b.length-1]]="requestId"===c?[{id:a,endpoint:e}]:a;return _.us({},d)}_.Cc(Us,a)||_.gs(new _.Vj("Unknown label logged with GEL CSI",a))};
_.Ns=function(a){a=_.Cs(a);if(!("gel"in a))a.gel={gelTicks:{},gelInfos:{}};else if(a.gel){var b=a.gel;b.gelInfos||(b.gelInfos={});b.gelTicks||(b.gelTicks={})}return a.gel};_.Ws=function(a){a=_.Ns(a);"gelInfos"in a||(a.gelInfos={});return a.gelInfos};_.Zs=function(a,b,c){null!==b&&(_.Ds(c)[a]=b,_.Ms(c)?(a=_.Vs(a,b,c))&&_.Ys(a,c):_.Ls(c||"").info[a]=b)};_.Ys=function(a,b){if(_.Ms(b)){var c=_.Ls(b||"");_.us(c.info,a);_.us(_.Ws(b),a);b=_.Es(b);_.Os().info(a,b)}};
_.ct=function(a,b,c){var d=_.xs(c);if(_.fb("use_first_tick")&&_.$s(a,c))return d[a];if(!b&&"_"!=a[0]){var e=a;_.at.mark&&(_.Pe(e,"mark_")||(e="mark_"+e),c&&(e+=" ("+c+")"),_.at.mark(e))}e=b||(0,_.xb)();d[a]=e;_.Qs(a,b,c)||(_.bt(c),_.Ls(c||"").tick[a]=b||(0,_.xb)());return d[a]};_.$s=function(a,b){b=_.xs(b);return a in b};_.dt=function(a,b,c){null!==b&&(_.Ds(c)[a]=b,_.Ms(c)?(a=_.Vs(a,b,c))&&_.Ys(a,c):_.Ls(c||"").info[a]=b)};
_.bt=function(a){if(!_.zs(a)){var b=_.u((a||"")+"TIMING_ACTION",void 0),c=_.xs(a);if(_.lb("ytglobal.timing"+(a||"")+"ready_")&&b&&c._start&&ys(a))if(Is(a),a)_.et(a);else{b=!0;var d=_.u("TIMING_WAIT",[]);if(d.length)for(var e=0,f=d.length;e<f;++e)if(!(d[e]in c)){b=!1;break}b&&_.et(a)}}};
_.et=function(a){if(!_.Ms(a)){var b=_.xs(a),c=_.Ds(a),d=b._start,e=_.u("CSI_SERVICE_NAME","youtube"),f={v:2,s:e,action:_.u((a||"")+"TIMING_ACTION",void 0)},g=c.srt;void 0!==b.srt&&delete c.srt;if(c.h5jse){var h=window.location.protocol+_.lb("ytplayer.config.assets.js");(h=_.at.getEntriesByName?_.at.getEntriesByName(h)[0]:void 0)?c.h5jse=Math.round(c.h5jse-h.responseEnd):delete c.h5jse}b.aft=ys(a);var l=_.xs(a);h=l.pbr;var n=l.vc;l=l.pbs;h&&n&&l&&h<n&&n<l&&_.Ds(a).yt_pvis&&"youtube"==e&&(_.Zs("yt_lt",
"hot_bg",a),e=b.vc,h=b.pbs,delete b.aft,c.aft=Math.round(h-e));for(var q in c)"_"!=q.charAt(0)&&(f[q]=c[q]);b.ps=(0,_.xb)();q={};e=[];for(var t in b)"_"!=t.charAt(0)&&(h=Math.round(b[t]-d),q[t]=h,e.push(t+"."+h));f.rt=e.join(",");(b=_.lb("ytdebug.logTiming"))&&b(f,q);c=!!c.ap;_.fb("debug_csi_data")&&(b=_.lb("yt.timing.csiData"),b||(b=[],_.ab("yt.timing.csiData",b,void 0)),b.push({page:location.href,time:new Date,args:f}));b="";for(var w in f)f.hasOwnProperty(w)&&(b+="&"+w+"="+f[w]);f="/csi_204?"+
b.substring(1);if(window.navigator&&window.navigator.sendBeacon&&c){var y=void 0===y?"":y;ps(f,y)||ms(f,void 0,void 0,void 0,y)}else ms(f);_.As(!0,a);_.Nr(ft,new gt(q.aft+(g||0),a))}};ht=/#|$/;os=function(a,b){var c=a.search(ht);a:{var d=0;for(var e=b.length;0<=(d=a.indexOf(b,d))&&d<c;){var f=a.charCodeAt(d-1);if(38==f||63==f)if(f=a.charCodeAt(d+e),!f||61==f||38==f||35==f)break a;d+=e+1}d=-1}if(0>d)return null;e=a.indexOf("&",d);if(0>e||e>c)e=c;d+=b.length+1;return _.kc(a.substr(d,e-d))};
jt={cellpadding:"cellPadding",cellspacing:"cellSpacing",colspan:"colSpan",frameborder:"frameBorder",height:"height",maxlength:"maxLength",nonce:"nonce",role:"role",rowspan:"rowSpan",type:"type",usemap:"useMap",valign:"vAlign",width:"width"};
_.kt=function(a,b){_.v.forEach(b,function(c,d){c&&"object"==typeof c&&c.Ac&&(c=c.Jb());"style"==d?a.style.cssText=c:"class"==d?a.className=c:"for"==d?a.htmlFor=c:jt.hasOwnProperty(d)?a.setAttribute(jt[d],c):_.Pe(d,"aria-")||_.Pe(d,"data-")?a.setAttribute(d,c):a[d]=c})};lt=function(a){if(a&&"number"==typeof a.length){if(_.Qb(a))return"function"==typeof a.item||"string"==typeof a.item;if(_.ba(a))return"function"==typeof a.item}return!1};
mt=function(a,b,c){function d(g){g&&b.appendChild("string"===typeof g?a.createTextNode(g):g)}for(var e=2;e<c.length;e++){var f=c[e];!_.de(f)||_.Qb(f)&&0<f.nodeType?d(f):(0,_.ic)(lt(f)?_.we(f):f,d)}};
js=function(a,b,c){var d=arguments,e=document,f=String(d[0]),g=d[1];if(!_.Jg&&g&&(g.name||g.type)){f=["<",f];g.name&&f.push(' name="',_.Jf(g.name),'"');if(g.type){f.push(' type="',_.Jf(g.type),'"');var h={};_.v.extend(h,g);delete h.type;g=h}f.push(">");f=f.join("")}f=_.Mg(e,f);g&&("string"===typeof g?f.className=g:Array.isArray(g)?f.className=g.join(" "):_.kt(f,g));2<d.length&&mt(e,f,d);return f};_.m().w("syd");
var ns=function(a){var b=a.url,c=a.OE;this.B=!!a.cF;this.h=is(b,c);a=/[?&]dsh=1(&|$)/.test(b);this.j=!a&&/[?&]ae=1(&|$)/.test(b);this.A=!a&&/[?&]ae=2(&|$)/.test(b);this.g=/[?&]adurl=([^&]*)/.exec(b)};
var rs={},qs=0;
var Hs=function(a,b){_.Or.call(this,1,arguments)};_.G(Hs,_.Or);var gt=function(a,b){_.Or.call(this,1,arguments)};_.G(gt,_.Or);var Gs=new _.Pr("aft-recorded",Hs),ft=new _.Pr("timing-sent",gt);
var nt,ot;nt=window;ot=function(){this.timing={};this.clearResourceTimings=function(){};this.webkitClearResourceTimings=function(){};this.mozClearResourceTimings=function(){};this.msClearResourceTimings=function(){};this.oClearResourceTimings=function(){}};_.at=nt.performance||nt.mozPerformance||nt.msPerformance||nt.webkitPerformance||new ot;
var Fs=!1;
var pt=_.lb("ytLoggingLatencyUsageStats_")||{},qt;_.ab("ytLoggingLatencyUsageStats_",pt,void 0);qt=function(){this.g=0};_.Os=function(){qt.instance||(qt.instance=new qt);return qt.instance};qt.prototype.tick=function(a,b,c){Ps(this,"tick_"+a+"_"+b)||_.hd("latencyActionTicked",{tickName:a,clientActionNonce:b},{timestamp:c})};qt.prototype.info=function(a,b){var c=Object.keys(a).join("");Ps(this,"info_"+c+"_"+b)||(a.clientActionNonce=b,_.hd("latencyActionInfo",a))};
var Ps=function(a,b){pt[b]=pt[b]||{count:0};var c=pt[b];c.count++;c.time=(0,_.xb)();a.g||(a.g=_.nk(function(){var d=(0,_.xb)(),e;for(e in pt)pt[e]&&6E4<d-pt[e].time&&delete pt[e];a&&(a.g=0)},0,5E3));return 5<c.count?(6===c.count&&1>1E5*Math.random()&&(c=new _.Vj("CSI data exceeded logging limit with key",b),0===b.indexOf("info")?_.gs(c):_.nd(c)),!0):!1};
var rt,tt,Rs,Ss,ut,Ts,Us;rt={};
_.st=(rt.ad_to_ad="LATENCY_ACTION_AD_TO_AD",rt.ad_to_video="LATENCY_ACTION_AD_TO_VIDEO",rt.app_startup="LATENCY_ACTION_APP_STARTUP",rt["artist.analytics"]="LATENCY_ACTION_CREATOR_ARTIST_ANALYTICS",rt["artist.events"]="LATENCY_ACTION_CREATOR_ARTIST_CONCERTS",rt["artist.presskit"]="LATENCY_ACTION_CREATOR_ARTIST_PROFILE",rt.browse="LATENCY_ACTION_BROWSE",rt.channels="LATENCY_ACTION_CHANNELS",rt.channel="LATENCY_ACTION_CREATOR_CHANNEL_DASHBOARD",rt["channel.analytics"]="LATENCY_ACTION_CREATOR_CHANNEL_ANALYTICS",
rt["channel.comments"]="LATENCY_ACTION_CREATOR_CHANNEL_COMMENTS",rt["channel.copyright"]="LATENCY_ACTION_CREATOR_CHANNEL_COPYRIGHT",rt["channel.monetization"]="LATENCY_ACTION_CREATOR_CHANNEL_MONETIZATION",rt["channel.translations"]="LATENCY_ACTION_CREATOR_CHANNEL_TRANSLATIONS",rt["channel.videos"]="LATENCY_ACTION_CREATOR_CHANNEL_VIDEOS",rt.chips="LATENCY_ACTION_CHIPS",rt["dialog.copyright_strikes"]="LATENCY_ACTION_CREATOR_DIALOG_COPYRIGHT_STRIKES",rt["dialog.uploads"]="LATENCY_ACTION_CREATOR_DIALOG_UPLOADS",
rt.embed="LATENCY_ACTION_EMBED",rt.home="LATENCY_ACTION_HOME",rt.library="LATENCY_ACTION_LIBRARY",rt.live="LATENCY_ACTION_LIVE",rt.onboarding="LATENCY_ACTION_KIDS_ONBOARDING",rt.parent_profile_settings="LATENCY_ACTION_KIDS_PARENT_PROFILE_SETTINGS",rt.prebuffer="LATENCY_ACTION_PREBUFFER",rt.prefetch="LATENCY_ACTION_PREFETCH",rt.profile_settings="LATENCY_ACTION_KIDS_PROFILE_SETTINGS",rt.profile_switcher="LATENCY_ACTION_KIDS_PROFILE_SWITCHER",rt.results="LATENCY_ACTION_RESULTS",rt.search_ui="LATENCY_ACTION_SEARCH_UI",
rt.search_zero_state="LATENCY_ACTION_SEARCH_ZERO_STATE",rt.secret_code="LATENCY_ACTION_KIDS_SECRET_CODE",rt.settings="LATENCY_ACTION_SETTINGS",rt.tenx="LATENCY_ACTION_TENX",rt.video_to_ad="LATENCY_ACTION_VIDEO_TO_AD",rt.watch="LATENCY_ACTION_WATCH",rt.watch_it_again="LATENCY_ACTION_KIDS_WATCH_IT_AGAIN",rt["watch,watch7"]="LATENCY_ACTION_WATCH",rt["watch,watch7_html5"]="LATENCY_ACTION_WATCH",rt["watch,watch7ad"]="LATENCY_ACTION_WATCH",rt["watch,watch7ad_html5"]="LATENCY_ACTION_WATCH",rt.wn_comments=
"LATENCY_ACTION_LOAD_COMMENTS",rt.ww_rqs="LATENCY_ACTION_WHO_IS_WATCHING",rt["video.analytics"]="LATENCY_ACTION_CREATOR_VIDEO_ANALYTICS",rt["video.comments"]="LATENCY_ACTION_CREATOR_VIDEO_COMMENTS",rt["video.edit"]="LATENCY_ACTION_CREATOR_VIDEO_EDIT",rt["video.translations"]="LATENCY_ACTION_CREATOR_VIDEO_TRANSLATIONS",rt["video.video_editor"]="LATENCY_ACTION_CREATOR_VIDEO_VIDEO_EDITOR",rt["video.video_editor_async"]="LATENCY_ACTION_CREATOR_VIDEO_VIDEO_EDITOR_ASYNC",rt["video.monetization"]="LATENCY_ACTION_CREATOR_VIDEO_MONETIZATION",
rt.voice_assistant="LATENCY_ACTION_VOICE_ASSISTANT",rt.cast_load_by_entity_to_watch="LATENCY_ACTION_CAST_LOAD_BY_ENTITY_TO_WATCH",rt);tt={};
Rs=(tt.ad_allowed="adTypesAllowed",tt.yt_abt="adBreakType",tt.ad_cpn="adClientPlaybackNonce",tt.ad_docid="adVideoId",tt.yt_ad_an="adNetworks",tt.ad_at="adType",tt.browse_id="browseId",tt.p="httpProtocol",tt.t="transportProtocol",tt.cpn="clientPlaybackNonce",tt.ccs="creatorInfo.creatorCanaryState",tt.cseg="creatorInfo.creatorSegment",tt.csn="clientScreenNonce",tt.docid="videoId",tt.GetHome_rid="requestId",tt.GetSearch_rid="requestId",tt.GetPlayer_rid="requestId",tt.GetWatchNext_rid="requestId",tt.GetBrowse_rid=
"requestId",tt.GetLibrary_rid="requestId",tt.is_continuation="isContinuation",tt.is_nav="isNavigation",tt.b_p="kabukiInfo.browseParams",tt.is_prefetch="kabukiInfo.isPrefetch",tt.is_secondary_nav="kabukiInfo.isSecondaryNav",tt.prev_browse_id="kabukiInfo.prevBrowseId",tt.query_source="kabukiInfo.querySource",tt.voz_type="kabukiInfo.vozType",tt.yt_lt="loadType",tt.mver="creatorInfo.measurementVersion",tt.yt_ad="isMonetized",tt.nr="webInfo.navigationReason",tt.nrsu="navigationRequestedSameUrl",tt.ncnp=
"webInfo.nonPreloadedNodeCount",tt.pnt="performanceNavigationTiming",tt.prt="playbackRequiresTap",tt.plt="playerInfo.playbackType",tt.pis="playerInfo.playerInitializedState",tt.paused="playerInfo.isPausedOnLoad",tt.yt_pt="playerType",tt.fmt="playerInfo.itag",tt.yt_pl="watchInfo.isPlaylist",tt.yt_pre="playerInfo.preloadType",tt.yt_ad_pr="prerollAllowed",tt.pa="previousAction",tt.yt_red="isRedSubscriber",tt.rce="mwebInfo.responseContentEncoding",tt.scrh="screenHeight",tt.scrw="screenWidth",tt.st="serverTimeMs",
tt.aq="tvInfo.appQuality",tt.br_trs="tvInfo.bedrockTriggerState",tt.kebqat="kabukiInfo.earlyBrowseRequestInfo.abandonmentType",tt.kebqa="kabukiInfo.earlyBrowseRequestInfo.adopted",tt.label="tvInfo.label",tt.is_mdx="tvInfo.isMdx",tt.preloaded="tvInfo.isPreloaded",tt.upg_player_vis="playerInfo.visibilityState",tt.query="unpluggedInfo.query",tt.upg_chip_ids_string="unpluggedInfo.upgChipIdsString",tt.yt_vst="videoStreamType",tt.vph="viewportHeight",tt.vpw="viewportWidth",tt.yt_vis="isVisible",tt.rcl=
"mwebInfo.responseContentLength",tt.GetSettings_rid="requestId",tt.GetTrending_rid="requestId",tt.GetMusicSearchSuggestions_rid="requestId",tt.REQUEST_ID="requestId",tt);Ss="isContinuation isNavigation kabukiInfo.earlyBrowseRequestInfo.adopted kabukiInfo.isPrefetch kabukiInfo.isSecondaryNav isMonetized navigationRequestedSameUrl performanceNavigationTiming playerInfo.isPausedOnLoad prerollAllowed isRedSubscriber tvInfo.isMdx tvInfo.isPreloaded isVisible watchInfo.isPlaylist playbackRequiresTap".split(" ");
ut={};Ts=(ut.ccs="CANARY_STATE_",ut.mver="MEASUREMENT_VERSION_",ut.pis="PLAYER_INITIALIZED_STATE_",ut.yt_pt="LATENCY_PLAYER_",ut.pa="LATENCY_ACTION_",ut.yt_vst="VIDEO_STREAM_TYPE_",ut);Us="all_vc ap c cver cbrand cmodel cplatform ctheme ei l_an l_mm plid srt yt_fss yt_li vpst vpni2 vpil2 icrc icrt pa GetAccountOverview_rid GetHistory_rid cmt d_vpct d_vpnfi d_vpni nsru pc pfa pfeh pftr pnc prerender psc rc start tcrt tcrc ssr vpr vps yt_abt yt_fn yt_fs yt_pft yt_pre yt_pt yt_pvis ytu_pvis yt_ref yt_sts tds".split(" ");
if(_.fb("overwrite_polyfill_on_logging_lib_loaded")){var vt=window;vt.ytcsi&&(vt.ytcsi.info=_.Zs)}
;var xt;_.wt=(0,_.je)(_.at.clearResourceTimings||_.at.webkitClearResourceTimings||_.at.mozClearResourceTimings||_.at.msClearResourceTimings||_.at.oClearResourceTimings||_.$d,_.at);xt=window;_.fb("csi_on_gel")&&xt.ytcsi&&(xt.ytcsi.tick=_.ct);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("syo");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Gt,Lt,St;_.Dt=function(a,b,c,d){a=_.Ng(a).createEvent("Event");a.initEvent(b,c,!0);a.detail=d;return a};_.Et=function(a,b,c,d,e){a=_.Dt(a,b,d);a.which=c;a.charCode=c;a.keyCode=c;e&&(a.Kh=e,a.Co=e.Co);return a};Gt=function(){return Ft.reduce(function(a,b){a[b]=[];return a},{})};_.Mt=function(a){Ht?It[a.stage].push(a.X):Jt[a.stage].push(a.X);Kt||Lt()};
Lt=function(){Kt=!0;requestAnimationFrame(function(){setTimeout(function(){Ht=!0;for(var a=0;a<Ft.length;a++){for(var b=Ft[a],c=Jt[b],d=0;d<c.length;d++){var e=c[d];try{e()}catch(f){_.C(f)}}Jt[b]=[]}Ht=!1;a:{for(a=0;a<Ft.length;a++)if(0<It[Ft[a]].length){a=!0;break a}a=!1}a?(a=Jt,Jt=It,It=a,Lt()):Kt=!1})})};_.Rt=function(a){Nt||_.Ot.isLocked()||a&&a.ia&&a.ia.props.focused&&Pt.some(function(b){return b===a})&&document.activeElement===Pt[Pt.length-1]||(Nt=!0,_.Mt({stage:30,X:_.Qt}))};
_.Qt=function(){if(!_.Ot.isLocked()){var a=St(),b=Pt;if(b[b.length-1]!==a[a.length-1]||document.activeElement!==a[a.length-1])if(Pt=a,a=Pt[Pt.length-1])a.focus({preventScroll:!0}),a!==document.activeElement&&_.C(new _.Vj("Focus failed.",a.tagName));Nt=!1}};St=function(){var a=Array.from(document.body.querySelectorAll(".zylon-focus")),b=new Set(Array.from(document.body.querySelectorAll(".zylon-unfocusable .zylon-focus")));return a.filter(function(c){return!b.has(c)})};
_.Tt=function(a,b,c){return 2>=arguments.length?Array.prototype.slice.call(a,b):Array.prototype.slice.call(a,b,c)};_.Ut=function(a,b,c){return Math.min(Math.max(a,b),c)};_.Vt=function(a){for(var b=[],c=0;c<arguments.length;c++){var d=arguments[c];if(Array.isArray(d))for(var e=0;e<d.length;e+=8192)for(var f=_.Vt.apply(null,_.Tt(d,e,e+8192)),g=0;g<f.length;g++)b.push(f[g]);else b.push(d)}return b};_.m().w("syy");
var Ft=_.v.la({lC:50,$r:40,Ps:30,lr:20,kC:10,50:"TIMING",40:"DOM",30:"FOCUS",20:"CLEANUP",10:"TEST_ONLY"}).filter(function(a){return"number"===typeof a});Ft.sort(function(a,b){return b-a});var Kt=!1,Ht=!1,Jt=Gt(),It=Gt();
var Wt=function(){this.h=0;this.g=new _.Qi;this.isValid=!0};Wt.prototype.isLocked=function(){return!!this.h||!this.isValid};Wt.prototype.reset=function(){this.h=0;this.g=new _.Qi;this.isValid=!0};
var Pt,Nt;Pt=[];Nt=!1;_.Ot=new Wt;

_.m().l();

}catch(e){_._DumpException(e)}
try{
var aw,dw,ew,fw,gw,iw,kw,lw,mw,qw,uw,Bw,Dw,Hw,Iw,Mw,Ow,Qw,Pw,Rw,Sw,Vw,Yw,$w,ax,bx,cx,dx,ex,fx,gx,Ww;aw=function(a,b){for(var c=[],d=1;d<arguments.length;++d)c[d-1]=arguments[d];a.args||(a.args=[]);a.args.push.apply(a.args,_.F(c))};_.bw=function(a,b,c){a.stopPropagation();c&&a.stopImmediatePropagation&&a.stopImmediatePropagation();b||a.preventDefault()};
_.cw=function(a){function b(e){e.keyCode===a&&e.stopImmediatePropagation()}function c(e){b(e);d.removeEventListener("keydown",b,!0);d.removeEventListener("keyup",c,!0)}var d=document;d.addEventListener("keydown",b,!0);d.addEventListener("keyup",c,!0)};_.P=function(a){_.bw(a,!1,!0);"keydown"===a.type&&_.cw(a.keyCode)};
dw=function(a){if("string"===typeof a)return a;Array.isArray(a)||(a=[a]);for(var b="",c="",d=0;d<a.length;d++){var e=a[d];if(e)if("string"===typeof e)b+=c+e,c=" ";else for(var f in e)e[f]&&(b+=c+f,c=" ")}return b};ew=function(){};fw=function(a,b){for(;a.length>b;)a.pop()};gw=function(a){a=Array(a);fw(a,0);return a};iw=function(a,b,c){(_.hw[b]||_.hw.__default)(a,b,c)};kw=function(a,b,c){b=new jw(b,c);return a.__incrementalDOMData=b};
lw=function(a,b){if(a.__incrementalDOMData)return a.__incrementalDOMData;var c=1===a.nodeType?a.localName:a.nodeName,d=1===a.nodeType?a.getAttribute("key"):null;b=kw(a,c,1===a.nodeType?d||b:null);if(1===a.nodeType&&(a=a.attributes,c=a.length)){d=b.g||(b.g=gw(c));for(var e=0,f=0;e<c;e+=1,f+=2){var g=a[e],h=g.value;d[f]=g.name;d[f+1]=h}}return b};mw=function(a,b,c,d,e){return b==c&&d==e};
qw=function(a){for(var b=nw,c=ow?ow.nextSibling:nw.firstChild;c!==a;){var d=c.nextSibling;b.removeChild(c);pw.g.push(c);c=d}};
uw=function(a,b){ow=ow?ow.nextSibling:nw.firstChild;var c;a:{if(c=ow){do{var d=c,e=a,f=b,g=lw(d,f);if(rw(d,e,g.h,f,g.key))break a}while(b&&(c=c.nextSibling))}c=null}c||("#text"===a?(a=sw.createTextNode(""),kw(a,"#text",null)):(c=sw,d=nw,"function"===typeof a?c=new a:c=(d="svg"===a?"http://www.w3.org/2000/svg":"math"===a?"http://www.w3.org/1998/Math/MathML":null==d||"foreignObject"===lw(d,void 0).h?null:d.namespaceURI)?c.createElementNS(d,a):c.createElement(a),kw(c,a,b),a=c),pw.created.push(a),c=a);
a=c;if(a!==ow){if(0<=tw.indexOf(a))for(b=nw,c=a.nextSibling,d=ow;null!==d&&d!==a;)e=d.nextSibling,b.insertBefore(d,c),d=e;else nw.insertBefore(a,ow);ow=a}};_.vw=function(){ow=nw.lastChild};
Bw=function(a,b){b=void 0===b?{}:b;var c=void 0===b.matches?mw:b.matches;return function(d,e,f){var g=pw,h=sw,l=tw,n=ww,q=xw,t=ow,w=nw,y=rw;sw=d.ownerDocument;pw=new yw;rw=c;ww=[];xw=[];ow=null;var B=nw=d.parentNode,z,H=zw.call(d);if((z=11===H.nodeType||9===H.nodeType?H.activeElement:null)&&d.contains(z)){for(H=[];z!==B;)H.push(z),z=z.parentNode;B=H}else B=[];tw=B;try{return a(d,e,f)}finally{d=pw,Aw&&0<d.g.length&&Aw(d.g),sw=h,pw=g,rw=y,ww=n,xw=q,ow=t,nw=w,tw=l}}};
Dw=function(a,b,c){Cw.push(iw);Cw.push(a);Cw.push(b);Cw.push(c)};
Hw=function(a,b,c,d){for(var e=3;e<arguments.length;++e);e=ww;e[0]=a;e[1]=b;e[2]=c;for(e=3;e<arguments.length;e+=2){var f=arguments[e+1],g=xw;g.push(arguments[e]);g.push(f)}e=ww;uw(e[0],e[1]);nw=ow;ow=null;f=nw;g=lw(f,void 0);var h=e[2];if(!g.j&&(g.j=!0,h&&h.length)){var l=g.g;if(l&&l.length){for(l=0;l<h.length;l+=2)Ew[h[l]]=l+1;l=g.g||(g.g=gw(0));for(var n=0,q=0;q<l.length;q+=2){var t=l[q],w=l[q+1],y=Ew[t];y?h[y]===w&&delete Ew[t]:(l[n]=t,l[n+1]=w,n+=2)}fw(l,n);for(var B in Ew)iw(f,B,h[Ew[B]]),delete Ew[B]}else for(B=
0;B<h.length;B+=2)iw(f,h[B],h[B+1])}B=xw;g=g.g||(g.g=gw(B.length));l=!g.length;for(h=0;h<B.length;h+=2){n=B[h];if(l)g[h]=n;else if(g[h]!==n)break;q=B[h+1];if(l||g[h+1]!==q)g[h+1]=q,Dw(f,n,q)}if(h<B.length||h<g.length){for(h=l=h;h<g.length;h+=2)Fw[g[h]]=g[h+1];for(h=l;h<B.length;h+=2)l=B[h],n=B[h+1],Fw[l]!==n&&Dw(f,l,n),g[h]=l,g[h+1]=n,delete Fw[l];fw(g,B.length);for(var z in Fw)Dw(f,z,void 0),delete Fw[z]}z=Gw;Gw=g=Cw.length;for(h=z;h<g;h+=4)(0,Cw[h])(Cw[h+1],Cw[h+2],Cw[h+3]);Gw=z;fw(Cw,z);fw(B,0);
fw(e,0);return f};Iw=function(a,b){for(var c=1;c<arguments.length;++c);uw("#text",null);c=ow;var d=lw(c,void 0);if(d.text!==a){d=d.text=a;for(var e=1;e<arguments.length;e+=1)d=(0,arguments[e])(d);c.data!==d&&(c.data=d)}};Mw=function(a,b,c){if(null===c)return c;var d=Jw(a.tagName,b,function(){throw Error("La");});if(null===d)return c;d=Kw[d];if(d.sc&&c instanceof d.sc)return d.Yc(c);if(d.sc==_.Lw&&c instanceof _.Ce)return _.De(c);c=c&&c.Ac?c.Jb():c;return d.yc?d.yc(a.tagName,b,String(c)):c};
Ow=function(a,b){try{Nw(a.el,a.template(a.props,a.state))}catch(c){c.message="Render failed: "+c.message,aw(c,a.constructor.TAG_NAME),_.C(c)}!!a.props.focused!==!(!b||!b.focused)&&_.Rt(a.el)};Qw=function(a,b,c,d){d=void 0===d?!1:d;if(a.el){Pw(b);c=a.constructor.getDerivedStateFromProps(b,c);d=d||a.shouldUpdate(b,c);var e=a.props,f=a.state;a.props=b;a.state=c;d&&(Ow(a,e),a.W(e,f))}};Pw=function(a){void 0!==a.className&&"string"!==typeof a.className&&(a.className=dw(a.className))};
Rw=function(a,b){return a===b?!0:void 0===a||void 0===b?!1:_.v.equals(a,b)};Sw=function(a){for(var b=0;b<a.length;++b){var c=a[b];Array.isArray(c)?Sw(c):"string"===typeof c?Iw(c):c instanceof Function&&c()}};_.Tw=function(){};
_.R=function(a,b,c){for(var d=[],e=2;e<arguments.length;++e)d[e-2]=arguments[e];b=b||{};return function(f){f&&(b=Object.assign(Object.assign({},b),f));if("string"===typeof a){f=a;var g=b,h=ow?ow.nextSibling:nw.firstChild;if("host"===f){var l=h.ia;f=h.tagName.toLowerCase();l.props.idomKey&&(g.idomKey=l.props.idomKey);if(!f.startsWith("zn-lazy-")){var n;h=g.className||"";h=dw(h);var q=l.el.tagName.toLowerCase();h+=" "+q;l.props.focused&&(h+=" "+q+"--focused zylon-focus");l.props.className&&(h+=" "+
dw(l.props.className));if(l.props.isHidden||g.isHidden)h+=" zylon-hidden",delete g.isHidden;q=l.props;if((null===(n=null===q||void 0===q?void 0:q.data)||void 0===n?0:n.trackingParams)||l.visualElement)h+=" zylon-ve";h&&(g.className=h.trim());l.props.role&&(g.role=l.props.role);_.D("isCobaltHybridNav",!1)&&!1!==g.hybridNavFocusable&&(g.tabIndex=-2);"auto"!==g.dir||_.D("isRtl",!1)||(g.dir="ltr");"undefined"===typeof g.tabIndex&&(g.tabIndex=-1)}}var t;if(g){n=[];l=null;for(t in g)g.hasOwnProperty(t)&&
(h=g[t],"idomKey"===t?l=h:((q=Uw[t])&&(t=q),"class"===t&&(h=dw(h)),n.push(t,h)));t={attrs:n,key:l}}else t={attrs:[],key:null};Hw.apply(null,[f,t.key,null].concat(_.F(t.attrs)));Sw(d);qw(null);ow=nw;nw=nw.parentNode}else if(a.prototype instanceof _.Q)a:if(f=b,0<d.length&&(f.children=d),Pw(f),uw(a.TAG_NAME,f.idomKey),nw=ow,ow=null,t=nw,_.vw(),qw(null),ow=nw,nw=nw.parentNode,t.ia){if(a.IS_HOC){if(!t.$c||!t.$c.get(a))break a;t=t.$c.get(a)}else t=t.ia;Qw(t,f,t.state)}else g=new a(f),g.state=a.getDerivedStateFromProps(f,
g.state),g.el=t,a.IS_HOC?(t.$c||(t.$c=new Map),t.$c.set(a,g)):t.ia=g,Ow(g),g.O();else a===_.Tw?Sw(d):(a(b)||_.$d)()}};_.Xw=function(a){for(var b=0;b<a.length;b++){var c=a[b];Vw(c);c=Ww(c,function(e){return!!e.ia});for(var d=0;d<c.length;d++)Vw(c[d])}};Vw=function(a){a.ia&&(a.ia.Ca(),a.ia.props.focused&&_.Rt(),delete a.ia.el,delete a.ia);a.$c&&(a.$c.forEach(function(b){b.Ca();delete b.el}),delete a.$c)};
Yw=function(a,b){a=a.split("%s");for(var c="",d=a.length-1,e=0;e<d;e++)c+=a[e]+(e<b.length?b[e]:"%s");_.le.call(this,c+a[d])};_.ke(Yw,_.le);Yw.prototype.name="AssertionError";_.Zw=function(a){var b=a;return function(){if(b){var c=b;b=null;c()}}};$w=/\b(calc|cubic-bezier|fit-content|hsl|hsla|linear-gradient|matrix|minmax|repeat|rgb|rgba|(rotate|scale|translate)(X|Y|Z|3d)?)\([-+*/0-9a-z.%\[\], ]+\)/g;ax=/\b(url\([ \t\n]*)('[ -&(-\[\]-~]*'|"[ !#-\[\]-~]*"|[!#-&*-\[\]-~]*)([ \t\n]*\))/g;bx=/^[-,."'%_!# a-zA-Z0-9\[\]]+$/;
cx=/\/\*/;dx=function(a){for(var b=!0,c=/^[-_a-zA-Z0-9]$/,d=0;d<a.length;d++){var e=a.charAt(d);if("]"==e){if(b)return!1;b=!0}else if("["==e){if(!b)return!1;b=!1}else if(!b&&!c.test(e))return!1}return b};ex=function(a){return a.replace(ax,function(b,c,d,e){var f="";d=d.replace(/^(['"])(.*)\1$/,function(g,h,l){f=h;return l});b=_.mf(d).Jb();return c+f+b+f+e})};
fx=function(a){if(a instanceof _.gf)return'url("'+_.hf(a).replace(/</g,"%3c").replace(/[\\"]/g,"\\$&")+'")';if(a instanceof _.Ce)a=_.De(a);else{a=String(a);var b=a.replace($w,"$1").replace($w,"$1").replace(ax,"url");if(bx.test(b)){if(b=!cx.test(a)){for(var c=b=!0,d=0;d<a.length;d++){var e=a.charAt(d);"'"==e&&c?b=!b:'"'==e&&b&&(c=!c)}b=b&&c&&dx(a)}a=b?ex(a):"zClosurez"}else a="zClosurez"}if(/[{;}]/.test(a))throw new Yw("Value does not allow [{;}], got: %s.",[a]);return a};
gx=function(a,b,c,d){if(null!=a)for(a=a.firstChild;a;){if(b(a)&&(c.push(a),d)||gx(a,b,c,d))return!0;a=a.nextSibling}return!1};Ww=function(a,b){var c=[];gx(a,b,c,!1);return c};_.m().w("sye");
var hx=function(){};_.be(hx);hx.prototype.g=0;
/*

 Copyright 2018 The Incremental DOM Authors. All Rights Reserved.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS-IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/
var ix=Object.prototype.hasOwnProperty;ew.prototype=Object.create(null);
_.hw=new ew;_.hw.__default=function(a,b,c){var d=typeof c;"object"===d||"function"===d?a[b]=c:null==c?a.removeAttribute(b):(d=0===b.lastIndexOf("xml:",0)?"http://www.w3.org/XML/1998/namespace":0===b.lastIndexOf("xlink:",0)?"http://www.w3.org/1999/xlink":null)?a.setAttributeNS(d,b,String(c)):a.setAttribute(b,String(c))};_.hw.style=function(a,b,c){a=a.style;if("string"===typeof c)a.cssText=c;else{a.cssText="";for(var d in c)if(ix.call(c,d)){b=d;var e=c[d];0<=b.indexOf("-")?a.setProperty(b,e):a[b]=e}}};
var Aw=null;
var yw=function(){this.created=[];this.g=[]};
var zw="undefined"!==typeof Node&&Node.prototype.getRootNode||function(){for(var a=this,b=a;a;)b=a,a=a.parentNode;return b};
var jw=function(a,b){this.g=null;this.j=!1;this.h=a;this.key=b;this.text=void 0};
var pw,ow,nw,sw,tw,rw,ww,xw,Nw;pw=null;ow=null;nw=null;sw=null;tw=[];rw=mw;ww=[];xw=[];_.jx=function(a){return Bw(function(b,c,d){nw=ow=b;ow=null;c(d);qw(null);ow=nw;nw=nw.parentNode;return b},a)}();Nw=function(a){return Bw(function(b,c,d){var e={nextSibling:b};ow=e;c(d);nw&&qw(b.nextSibling);return e===ow?null:ow},a)}();
var Cw=[],Gw=0;
var Fw=new ew;
var Ew=new ew;
/*

 Copyright (c) 2017 The Polymer Project Authors. All rights reserved.
 This code may only be used under the BSD style license found at
 http://polymer.github.io/LICENSE.txt
 The complete set of authors may be found at
 http://polymer.github.io/AUTHORS.txt
 The complete set of contributors may be found at
 http://polymer.github.io/CONTRIBUTORS.txt
 Code distributed by Google as part of the polymer project is also
 subject to an additional IP rights grant found at
 http://polymer.github.io/PATENTS.txt
*/
var Jw=function(a,b,c){if(Object.hasOwnProperty.call(kx,a)&&(a=kx[a],Object.hasOwnProperty.call(a,b)&&(a=a[b],a instanceof Array))){for(var d=null,e=!1,f=0,g=a.length;f<g;++f){var h=a[f],l=h.Wa;if(!l)return h.U;null===d&&(d={});l=Object.hasOwnProperty.call(d,l)?d[l]:d[l]=c(l);if(l===h.ab)return h.U;null==l&&(e=!0)}if(e)return null}b=lx[b];return"number"===typeof b?b:null},lx={align:1,alt:1,"aria-activedescendant":10,"aria-atomic":1,"aria-autocomplete":1,"aria-busy":1,"aria-checked":1,"aria-disabled":1,
"aria-dropeffect":1,"aria-expanded":1,"aria-haspopup":1,"aria-hidden":1,"aria-invalid":1,"aria-label":1,"aria-level":1,"aria-live":1,"aria-multiline":1,"aria-multiselectable":1,"aria-orientation":1,"aria-posinset":1,"aria-pressed":1,"aria-readonly":1,"aria-relevant":1,"aria-required":1,"aria-selected":1,"aria-setsize":1,"aria-sort":1,"aria-valuemax":1,"aria-valuemin":1,"aria-valuenow":1,"aria-valuetext":1,async:8,autocapitalize:1,autocomplete:1,autocorrect:1,autofocus:1,bgcolor:1,border:1,checked:1,
"class":1,color:1,cols:1,colspan:1,contenteditable:1,dir:8,disabled:1,draggable:1,enctype:1,face:1,"for":10,formenctype:1,frameborder:1,height:1,hidden:1,href:4,hreflang:1,id:10,ismap:1,itemid:1,itemprop:1,itemref:1,itemscope:1,itemtype:1,label:1,lang:1,list:10,loop:1,max:1,maxlength:1,min:1,multiple:1,muted:1,name:10,placeholder:1,preload:1,rel:1,required:1,reversed:1,role:1,rows:1,rowspan:1,selected:1,shape:1,size:1,sizes:1,span:1,spellcheck:1,src:4,start:1,step:1,style:5,summary:1,tabindex:1,target:8,
title:1,translate:1,valign:1,value:1,width:1,wrap:1},kx={a:{href:[{U:3}]},area:{href:[{U:3}]},audio:{src:[{U:3}]},blockquote:{cite:[{U:3}]},button:{formaction:[{U:3}],formmethod:[{U:1}],type:[{U:1}]},command:{type:[{U:1}]},content:{select:[{U:1}]},del:{cite:[{U:3}]},details:{open:[{U:1}]},form:{action:[{U:3}],method:[{U:1}]},iframe:{srcdoc:[{U:2}]},img:{src:[{U:3}],srcset:[{U:11}]},input:{formaction:[{U:3}],formmethod:[{U:1}],pattern:[{U:1}],readonly:[{U:1}],src:[{U:3}],type:[{U:1}]},ins:{cite:[{U:3}]},
li:{type:[{U:1}]},link:{href:[{U:3,Wa:"rel",ab:"alternate"},{U:3,Wa:"rel",ab:"author"},{U:3,Wa:"rel",ab:"bookmark"},{U:3,Wa:"rel",ab:"canonical"},{U:3,Wa:"rel",ab:"cite"},{U:3,Wa:"rel",ab:"help"},{U:3,Wa:"rel",ab:"icon"},{U:3,Wa:"rel",ab:"license"},{U:3,Wa:"rel",ab:"next"},{U:3,Wa:"rel",ab:"prefetch"},{U:3,Wa:"rel",ab:"dns-prefetch"},{U:3,Wa:"rel",ab:"prerender"},{U:3,Wa:"rel",ab:"preconnect"},{U:3,Wa:"rel",ab:"preload"},{U:3,Wa:"rel",ab:"prev"},{U:3,Wa:"rel",ab:"search"},{U:3,Wa:"rel",ab:"subresource"}],
media:[{U:1}],nonce:[{U:1}],type:[{U:1}]},menuitem:{icon:[{U:3}]},ol:{type:[{U:1}]},q:{cite:[{U:3}]},script:{nonce:[{U:1}],type:[{U:1}]},source:{media:[{U:1}],src:[{U:3}],srcset:[{U:11}]},style:{media:[{U:1}],nonce:[{U:1}]},table:{cellpadding:[{U:1}],cellspacing:[{U:1}]},textarea:{readonly:[{U:1}]},time:{datetime:[{U:1}]},video:{autoplay:[{U:1}],controls:[{U:1}],poster:[{U:3}],src:[{U:3}]}},mx=[{auto:!0,ltr:!0,rtl:!0},{async:!0},{_self:!0,_blank:!0}],nx={"*":{async:1,dir:0,target:2}};
_.ox=hx.R();_.Lw=function(){this.g=""};_.Lw.prototype.toString=function(){return this.g};
var px={},Kw=(px[1]={yc:null,sc:null,Yc:null},px[2]={yc:function(a,b,c){return _.Jf(c)},sc:_.zf,Yc:_.Bf},px[3]={yc:function(a,b,c){return _.mf(c).Jb()},sc:_.gf,Yc:_.hf},px[4]={yc:function(){return"about:invalid#zClosurez"},sc:_.Le,Yc:_.Ne},px[5]={yc:function(){return"zClosurez"},sc:_.pf,Yc:function(a){if(a instanceof _.pf&&a.constructor===_.pf&&a.h===_.of)return a.g;_.ce(a);return"type_error:SafeStyle"}},px[7]={yc:function(){return" /*zClosurez*/ "},sc:_.Ge,Yc:function(a){return _.He(a).toString()}},
px[8]={yc:function(a,b,c){c=String(c).toLowerCase();a:{var d=null;(a=nx[a])&&(d=a[b]);if("number"!==typeof d&&((a=nx["*"])&&(d=a[b]),"number"!==typeof d)){b=!1;break a}b=!0===mx[d][String(c).toLowerCase()]}return b?c:"zClosurez"},sc:null,Yc:null},px[9]={yc:function(){return" /*zClosurez*/ "},sc:_.Ce,Yc:_.De},px[10]={yc:function(a,b,c){return"id"!=b&&"name"!=b||""==c?String(c):"zClosurez"},sc:_.Lw,Yc:function(a){if(!(a instanceof _.Lw&&a.constructor===_.Lw))throw Error("Ka");return a.g}},px);
_.Q=function(a){this.props=a;this.ga=[]};_.Q.getDerivedStateFromProps=function(a,b){return b||{}};_.S=function(a,b){b=Object.assign(Object.assign({},a.state),b);Qw(a,a.props,b)};_.Q.prototype.O=function(){};_.Q.prototype.Ca=function(){for(var a=this.ga,b=0;b<a.length;++b)a[b]();a.length=0};_.T=function(a,b){a.ga.push(b)};_.Q.prototype.shouldUpdate=function(a,b){return!Rw(a,this.props)||!Rw(b,this.state)};_.Q.prototype.W=function(){};_.qx=function(a){Qw(a,a.props,a.state,!0)};
_.U=function(a,b,c){a.el&&a.el.dispatchEvent(_.Dt(a.el,b,!0,c))};_.Q.prototype.g=function(a,b,c){var d=this;this.el.addEventListener(a,b,void 0===c?!1:c);_.T(this,function(){d.el.removeEventListener(a,b)})};_.Q.TAG_NAME="yt-unknown-component";
var rx;
rx={animation:"animation",background:"background",backgroundColor:"background-color",backgroundImage:"background-image",backgroundPosition:"background-position",backgroundRepeat:"background-repeat",backgroundSize:"background-size",border:"border",borderBottom:"border-bottom",borderLeft:"border-left",borderLeftColor:"border-left-color",borderTop:"border-top",borderRight:"border-right",borderRadius:"border-radius",borderStyle:"border-style",borderWidth:"border-width",boxShadow:"box-shadow",color:"color",content:"content",
display:"display",font:"font",fontFamily:"font-family",fontSize:"font-size",fontStyle:"font-style",fontWeight:"font-weight",height:"height",lineHeight:"line-height",margin:"margin",marginBottom:"margin-bottom",marginLeft:"margin-left",marginTop:"margin-top",marginRight:"margin-right",minHeight:"min-height",minWidth:"min-width",maxHeight:"max-height",maxWidth:"max-width",top:"top",right:"right",bottom:"bottom",left:"left",opacity:"opacity",overflow:"overflow",overflowWrap:"overflow-wrap",padding:"padding",
paddingBottom:"padding-bottom",paddingLeft:"padding-left",paddingTop:"padding-top",paddingRight:"padding-right",position:"position",transform:"transform",transformOrigin:"transform-origin",transition:"transition",transitionDelay:"transition-delay",transitionDuration:"transition-duration",transitionProperty:"transition-property",transitionTimingFunction:"transition-timing-function",textAlign:"text-align",textIndent:"test-indent",textTransform:"text-transform",textOverflow:"text-overflow",textShadow:"text-shadow",
verticalAlign:"vertical-align",visibility:"visibility",whiteSpace:"white-space",width:"width",zIndex:"z-index"};_.sx=_.Zw(function(){var a=_.pd().cssPrefix;Object.assign(rx,{transform:a+"transform",transition:a+"transition"})});
var Uw={className:"class",tabIndex:"tabindex",onKeyDown:"onkeydown"};
var tx=_.hw.__default,ux=_.hw.style;_.hw.__default=function(a,b,c){c=_.D("enableHardening",!1)?Mw(a,b,c):c;tx(a,b,c)};
_.hw.style=function(a,b,c){var d=_.D("enableStyleHardening",!1),e={},f="",g;for(g in c)if(c.hasOwnProperty(g)){var h=c[g];if(void 0!==h){var l=rx[g];d?e[l]=h:f+=l+":"+h+";"}}if(d){c="";for(var n in e){if(!/^[-_a-zA-Z0-9]+$/.test(n))throw Error("r`"+n);d=e[n];null!=d&&(d=Array.isArray(d)?(0,_.pe)(d,fx).join(" "):fx(d),c+=n+":"+d+";")}e=c?_.qf(c):_.rf;e=Mw(a,b,e)}else e=f;a.g&&a.g===e||(a.g=e,ux(a,b,e))};
Aw=function(a){_.Mt({stage:20,X:function(){_.Xw(a)}})};

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("syg");
/*

 MIT License

 Copyright (c) 2017 Alexander Reardon

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE
*/
var vx;vx=function(a,b){if(a.length!==b.length)return!1;for(var c=0;c<a.length;++c)if(a[c]!==b[c])return!1;return!0};_.wx=function(a){var b=void 0===b?vx:b;var c,d=[],e,f=!1;return function(g){for(var h=[],l=0;l<arguments.length;++l)h[l]=arguments[l];if(f&&c===this&&b(h,d))return e;e=a.apply(this,h);f=!0;c=this;d=h;return e}};

_.m().l();

}catch(e){_._DumpException(e)}
try{
var yx,zx,Bx,Cx,Ax;
yx=function(a){var b=_.xx,c=_.Mr();if(!c)return 0;var d=c.subscribe(b.toString(),function(e,f){var g=_.lb("ytPubsub2Pubsub2SkipSubKey");g&&g==d||(g=function(){if(_.Rr[d])try{if(f&&b instanceof _.Pr&&b!=e)try{var h=b.g,l=f;if(!l.args||!l.version)throw Error("oa");try{if(!h.$b){var n=new h;h.$b=n.version}var q=h.$b}catch(t){}if(!q||l.version!=q)throw Error("pa");try{f=Reflect.construct(h,_.we(l.args))}catch(t){throw t.message="yt.pubsub2.Data.deserialize(): "+t.message,t;}}catch(t){throw t.message="yt.pubsub2.pubsub2 cross-binary conversion error for "+
b.toString()+": "+t.message,t;}a.call(window,f)}catch(t){_.jb(t)}},_.Tr[b.toString()]?_.lb("yt.scheduler.instance")?_.$b(g):_.nb(g,0):g())});_.Rr[d]=!0;_.Sr[b.toString()]||(_.Sr[b.toString()]=[]);_.Sr[b.toString()].push(d);return d};zx=function(a){var b=_.Mr();b&&("number"===typeof a&&(a=[a]),(0,_.ic)(a,function(c){b.unsubscribeByKey(c);delete _.Rr[c]}))};Bx=function(){var a=Ax,b=yx(function(c){a.apply(void 0,arguments);zx(b)});return b};Cx=function(){return _.Wc(_.rk(Math.random()+""),3)};
_.Fx=function(a,b,c){Dx.push({payloadName:a,payload:b,options:c});Ex||(Ex=Bx())};Ax=function(a){if(Dx){for(var b=_.A(Dx),c=b.next();!c.done;c=b.next())c=c.value,c.payload&&(c.payload.csn=a.csn,_.fc(c.payloadName,c.payload,null,c.options));Dx.length=0}Ex=0};_.Gx=function(a){this.kb=a};
_.Hx=function(a){var b={};void 0!==a.kb.trackingParams?b.trackingParams=a.kb.trackingParams:(b.veType=a.kb.veType,void 0!==a.kb.veCounter&&(b.veCounter=a.kb.veCounter),void 0!==a.kb.elementIndex&&(b.elementIndex=a.kb.elementIndex));void 0!==a.kb.dataElement&&(b.dataElement=_.Hx(a.kb.dataElement));void 0!==a.kb.youtubeData&&(b.youtubeData=a.kb.youtubeData);return b};_.Gx.prototype.toString=function(){return JSON.stringify(_.Hx(this))};_.Ix=function(a){return new _.Gx({trackingParams:a})};_.m().w("syj");
var Dx,Ex;_.Jx=function(a){_.Or.call(this,1,arguments);this.csn=a};_.G(_.Jx,_.Or);_.xx=new _.Pr("screen-created",_.Jx);Dx=[];_.Kx=Cx;Ex=0;
_.Lx=function(){this.j=new Set;this.B=new Set;this.A=new Map;this.h=null;this.g=_.gd};_.Lx.prototype.clear=function(){this.j.clear();this.B.clear();this.A.clear();this.h=null};_.be(_.Lx);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Nx;Nx=function(a,b,c){b=void 0===b?{}:b;a=(a=a in Mx?Mx[a]:c)||"";c={};for(var d in b){c.ff=d;var e=function(f){return function(){return String(b[f.ff])}}(c);a=a.replace(new RegExp("\\$\\{"+c.ff+"\\}","gi"),e);a=a.replace(new RegExp("\\$"+c.ff,"gi"),e);c={ff:c.ff}}return a};_.V=function(a,b){return Nx(a,b,Ox[a])};_.m().w("syl");
var Mx=window.yt&&window.yt.msgs_||window.ytcfg&&window.ytcfg.msgs||{};_.ab("yt.msgs_",Mx,void 0);
var Ox={CAPTIONS_WHITE:"White",LANGUAGE_OVERLAY_SUBTITLE:"YouTube will restart to update your preferences.",CLEAR_WATCH_HISTORY_DIALOG_TITLE:"Clear watch history?",CONFIRM_REMOVE_ACCOUNT_BUTTON:"Remove Account",EXAMPLE_VOICE_SEARCH_FIND_ARIANA_GRANDE:"Find Ariana Grande videos",NEXT_VIDEO:"Next video",PAUSE_WATCH_HISTORY:"Your watch history has been paused on all devices",ADD_TO_PLAYLIST:"Save video",MDX_UPDATE_QUEUE_ADD_PLAYLIST_TOAST_MESSAGE:"${videoCount} videos added to the queue",WELCOME_TITLE_MUSIC:"Discover your next favorite artist",
FEEDBACK:"Improve YouTube by sending feedback",FEAT_OTHER:"Other",MDX_UPDATE_QUEUE_ADD_VIDEO_TOAST_TITLE_WITHOUT_USER:"Video was added",KEYBOARD_DELETE:"Delete",GUEST_TEXT:"Guest",LEGEND_GUIDE:"Open Guide",ERROR_CODE_LABEL:"Error code: ${errorCode}",SEAMLESS_SEARCHING:"Searching for device",SELECT_ISSUE:"Select Issue",MDX_DEVICE_CONNECTED_TOAST_TITLE:"New device connected",MDX_UPDATE_QUEUE_ADD_PLAYLIST_TOAST_TITLE_WITH_USER:"${user} added a playlist",APP_DEVICE:"Device",LINK_TV_CODE_FIRST_STEP:"On your phone, open the YouTube app.",
NETWORK_ERROR_SUBTITLE:"YouTube is unavailable. Please try again later.",VIDEO_QUALITY_AUTO:"Auto",URL_SIGN_IN_OVERLAY_TITLE:"Sign in using a web browser",CLEAR_WATCH_HISTORY_DIALOG_MSG_SIGNED_IN:"This will clear your account's watch history from all devices. You can't undo this.",CAPTIONS_OK:"OK",WELCOME_TAGLINE1_MUSIC:"Sign in to find new music, recommended just for you",VOICE_SEARCH:"Voice Search",NO_DEVICES_SUBTITLE:"There are no devices linked to this device",CAPTIONS_CYAN:"Cyan",WELCOME_TAGLINE1_MULTI_ACCOUNT:"Sharing this device with others? It\u2019s easy to switch accounts or use YouTube as a guest",
CANCEL_BUTTON_TEXT:"Cancel",CAPTIONS_MONO_SERIF:"Monospaced Serif",URL_SIGN_IN_GO_TO:"On your phone or computer, go to",LEGEND_VOICE_SEARCH:"Voice Search",REPORT_SUCCESS_CONFIRM_TITLE:"Thanks!",ACC_SIGN_IN:"Can't sign in",SEAMLESS_SIGNING_IN:"Signing in...",PURCHASES_INVALID_CVC:"Invalid CVC",MDX_UPDATE_QUEUE_REMOVED_VIDEO_TOAST_TITLE_WITHOUT_USER:"Video was removed",CAPTIONS_MONO_SANS:"Monospaced Sans-Serif",CAPTIONS_FONT_FAMILY:"Font family",APP_RELOAD_REQUIRED_BUTTON:"Continue",PURCHASE_TOS_ITEM_TERMS:"YouTube Terms of Service",
MDX_DEVICE_DISCONNECTED_WITH_NAME_TOAST_TITLE:"${username} unlinked a device",NEXT_VIDEO_HEADING:"Next in ${n}...",EXAMPLE_VOICE_PLAY_ARIANA_GRANDE:"Play Ariana Grande videos",WHOS_WATCHING:"Who's watching?",ADD_ACCOUNT_SUBTITLE:"Add as many accounts as you would like to see your favorite videos, playlists and more",PURCHASES_PAY_NOW_BUTTON_TEXT:"Pay now",USE_ACCOUNT_TEXT:"Use Account",LIVE_CHAT_CONNECTING:"Connecting to Live chat...",VP_AUTOPLAY:"Autoplay not working",FEAT_DOWNLOAD:"Want to download videos",
LINK_WIFI_FIRST_STEP:"On your phone, connect to the same Wi-Fi network as this device.",LINK_TV_CODE_SECOND_STEP:"In your phone's YouTube settings, tap Watch on TV.",APP_CRASHING:"App is crashing",VP_SKIP_AD:"Can't skip ad",ON_LABEL:"On",HELP_TITLE:"Find help online",UNLINK_ALL_DEVICES_OVERLAY_SUBTITLE:"All devices will be unlinked from this TV",SIGNIN_SEAMLESS:"Sign in with your phone",PURCHASES_COMPLETE_TEXT_FINAL:"Find your purchased movie in Purchases",VERIFY_ITS_YOU_OVERLAY_SUBTITLE:"This helps make sure it's really you signing in",
MDX_UPDATE_QUEUE_CLEAR_PLAYLIST_TOAST_TITLE_WITH_USER:"${user} cleared the queue",APP_USER_AGENT:"User Agent",CAPTIONS_ON_STATUS:"On",AUDIO_CHANNEL_SETTINGS:"Audio channel settings",CANCEL:"cancel",CAPTIONS_BLUE:"Blue",VISIT_PRIVACY:"To read YouTube's Privacy Policy, visit:",LOCATION_LABEL:"Location",RESET_APP_DIALOG_MSG:"Any accounts and paired devices will be removed. Personalization and history will be reset.",PREVIOUS_VIDEO:"Previous video",APP_FREEZING:"App is freezing",VOICE_ERROR_RECOGNITION:"Didn't hear that",
VP_OTHER:"Other",LESS:"Less",MDX_DEVICE_CONNECTED_WITH_NAME_TOAST_TITLE:"${username} linked a device",MIX:"Mix",CONFIRM_RACY_CONTENT:"I wish to proceed",SIGN_IN_GOOGLE_ACCOUNT_TITLE:"Sign in with your Google Account",EMPTY_SEARCH_RESULTS:"No videos are available",WELCOME_TAGLINE1_P13N:"Sign in to see videos you\u2019ll love based on your other YouTube activity",SIGNED_OUT_GUEST:"Skip",ACCOUNT_REMOVED:"Account removed",IN_VIDEO_LINKS_LABEL:"In-video links",MDX_AUTOPLAY_COUNTDOWN:"Up next in",CLEAR_SEARCH_HISTORY_TOAST:"All search history has been cleared",
SIGN_IN_PROMPT:"Sign in for a personalized experience",EXAMPLE_VOICE_PLAY_TRAVEL_VIDEOS:"Play travel videos",EXAMPLE_VOICE_SEARCH_SPORTS_HIGHLIGHTS:"Find soccer highlights",GUEST_SUBTITLE:"Anyone who uses the guest account can influence its recommendations and history",RESUME_VIDEO_LABEL:"Resume video",CONFIRM_CHANGE:"Confirm change",REMOVE_ACCOUNT_TEXT:"Sign out",INTERSTITIAL_REASON_REPORT_VIDEO:"To report a video, please use an account",SOMETHING_WENT_WRONG:"Something went wrong",CAPTIONS_NONE:"None",
FEEDBACK_VIDEO_PLAYBACK:"Video playback",RESET_APP_DIALOG_TITLE:"Reset app?",PURCHASES_COMPLETE_TEXT_2:"2. Search for your movie or show",SERVICE_FAILURE_TOAST_TITLE:"Something went wrong. Please try again.",URL_SIGN_IN_ENTER_CODE:"Enter this code:",CAPTIONS_PROP_SANS:"Proportional Sans-Serif",APP_RELOAD_REQUIRED_SUBTITLE:"An app reload is required to continue.",WELCOME_TITLE_SUBS:"Never miss a moment",CAPTIONS_BACKGROUND_COLOR:"Background color",CAPTIONS_RESET_STYLE:"Reset style",MDX_AUTOPLAY_CANCEL_INSTRUCTION_REMOTE:"To cancel, press Stop on your mobile device",
APP_SERVER_UA:"Server User Agent",FEEDBACK_SEARCH:"Search",EXAMPLE_VOICE_SEARCH_MAKEUP_TUTORIALS:"Find makeup tutorials",SIGNIN_SEAMLESS_TAG:"Connect with the YouTube app",VOICE_HINT_GENERIC_MESSAGE:"Press the mic on your remote to find something to watch",VIEWER_DISCRETION_ADVISED:"Viewer discretion advised",PURCHASES_COMPLETE_TEXT_4:"4. Watch it on any signed in device",SEAMLESS_OVERLAY_SUBTITLE:"Tap your account. Make sure that your mobile device is on the same Wi-Fi as this TV",EXAMPLE_VOICE_PLAY_DICE_AN_ONION:"Show me how to dice an onion",
PURCHASES_COMPLETE_TEXT_1:"1. On your phone or computer, go to youtube.com",VIDEO_SAVED_TO_WATCH_LATER:'Video saved to your "Watch later" list.',PURCHASES_COMPLETE_TEXT_3:"3. Complete the purchase",VOICE_HINT_WITH_EXAMPLE_MESSAGE:'Press the mic on your remote and try saying "${example}"',FEEDBACK_DISCLAIMER:"Go to ${report_policy} to request content changes for legal reasons. Some account and system information may be sent to Google. We will use the information you give us to help address technical issues and to improve our services, subject to our Privacy Policy at ${privacy_policy} and Terms of Service at ${tos}.",
INTERSTITIAL_REASON_SAVE_VIDEO:"Saving a video requires using an account",MENU_HINT:"Press and hold for more options",REPORT_PLAYBACK_ID:"Playback ID: ${cpn}",NETWORK_ERROR_TITLE:"A network error has occurred",FEAT_COMMENT:"Want to post and view comments",MOBILE_SMART_REMOTE_TITLE:"Try voice search",ACC_SWITCHING:"Can't switch account",LEGEND_BACK:"Back",VIDEO_SPEED_NORMAL:"Normal",VP_BLACK_SCREEN:"Seeing a black screen",SIGN_IN_FLOW_ERROR_SUBTITLE:"Sign-in failed",LOCATION_UPDATED:"Location updated",
PURCHASE_TOS_SELECTOR_OVERLAY_SUBTITLE:"By purchasing you agree to the following terms",GOTO_SETTINGS:"Go to settings",SEAMLESS_OPEN_APP:"Open the YouTube app. Make sure you're signed in.",WELCOME_TITLE_P13N:"Your recommendations are ready",VP_NO_AUDIO:"Audio not working",EXAMPLE_VOICE_PLAY_COOKING_VIDEOS:"Play cooking videos",SCH_VOICE:"Voice search isn't working",CAPTIONS_DROP_SHADOW:"Drop Shadow",EXAMPLE_VOICE_PLAY_MUSIC_VIDEOS:"Watch music videos",SIGNIN_URL_TAG:"Enter a code online",SPHERICAL_DPAD_LABEL:"Look around",
CAST_CONNECTING:"Can't connect",CAPTIONS_STYLE_TITLE:"Caption style",USER_ACCOUNT_REMOVED:"${username} has been removed from this device",CAPTIONS_RAISED:"Raised",NO_LONGER_AVAILABLE_MESSAGE:"This account will no longer be available on this device",PLEASE_TRY_AGAIN:"Please try again later.",FEEDBACK_SUCCESS_CONFIRM_MESSAGE:"Your feedback helps make YouTube better.",CAPTIONS_CASUAL:"Casual",FEEDBACK_VIEW_REPORT:"View report info",CLEAR_SEARCH_HISTORY_DIALOG_TITLE:"Clear search history?",LEGEND_ON_SCREEN_KEYBOARD:"Keyboard",
PURCHASE_TOS_LEGAL_TEXT:"Links for referenced documents are included in the terms. If referenced, you can access the YouTube Terms of Service, the YouTube Paid Service Terms of Service, and the Paid Service Usage Rules on your TV from this screen",PURCHASE_SECOND_DEVICE_OPTION_TEXT:"Purchase on another device",ADVERTISEMENT:"Advertisement",EXAMPLE_VOICE_SEARCH_TRAVEL_VIDEOS:"Find travel videos",DEFAULT_UNLINK_DEVICES_SUBTITLE:"The following devices are linked to this device",FEAT_BLOCK_CHANNEL:"Want to block a channel",
FEAT_HD4K:"Want HD or 4K video quality support",UNLINK:"Unlink",CAPTIONS_RED:"Red",WELCOME_TAGLINE1_MOVIES:"Sign in and choose from a huge selection of the latest rentals",DIAL_VERTICAL_LAUNCH_FAILURE_MSG:"Sorry, the mobile application you're attempting to use is not supported by this device.",LIVE:"Live",PURCHASES_CONTINUE_BUTTON_TEXT:"Continue",LOCATION_SUBTITLE:"This may influence the videos and channels recommended to you. It won't change the language of the app.",SEAMLESS_OVERLAY_TITLE:"Sign in using the YouTube mobile app",
CAPTIONS_EDGE_STYLE:"Character edge style",USING_ACCOUNT_TEXT:"Using Account",MDX_UPDATE_QUEUE_REMOVED_VIDEO_TOAST_TITLE_WITH_USER:"${user} removed a video",VOICE_OVERLAY_PROMPT:"Speak now",CAPTIONS_MAGENTA:"Magenta",WELCOME:"Welcome!",CAST_NO_VIDEO:"Video doesn't cast",LEGEND_SPACE:"Space",PURCHASES_CHALLENGE_TITLE:"Verify ${cardInfo}",CLEAR_WATCH_HISTORY_TOAST:"All watch history has been cleared",VP_CAPTIONS:"Captions not working",FEEDBACK_SUGGEST:"Suggest a feature",CAPTIONS_VIDEO_OVERRIDE_SUBLABEL:"Override caption style if specified by the video",
ACC_SIGNED_OUT:"Signed out unexpectedly",WELCOME_TAGLINE1_SUBS:"Sign in to stay up to date on your subscriptions",CAPTIONS_WINDOW_OPACITY:"Window opacity",VISIT_WEB_ADDRESS:"On your phone or computer, visit:",SEARCH:"Search",SENTIMENT_SURVEY_MSG:"Thank you",ERROR_UNSUPPORTED:"YouTube on TV is not supported on this device.",FEEDBACK_PLAYBACK_ERROR:"Please send us a report to help us fix it",FEEDBACK_SUBMIT:"Submit report",VOICE_HINT_TITLE:"Search with your voice",CAPTIONS_DEFAULT:"Default",COBALT_STATUS:"Cobalt Status",
FEEDBACK_REPORT_INFO:"Report info",ADD_ACCOUNT_TITLE:"Sign in for the best experience",SCH_GENERIC:"Can't search",REPORT_VIDEO_REASONS:"Report this video",CAPTIONS_VIDEO_OVERRIDE:"Video Override",CLEAR_SEARCH_HISTORY_DIALOG_MSG_SIGNED_OUT:"This will clear YouTube searches made on this device.",CONFIRM_CONTROVERSIAL_CONTENT:"Confirm",UNLINK_ALL_DEVICES:"Unlink all devices",APP_RELOAD_REQUIRED_TITLE:"App reload required",YOURE_SIGNED_OUT:"You're signed out",ADD_ACCOUNT_TEXT:"Add account",RECOMMENDED_SHELF_TITLE:"Recommended",
CAPTIONS_YELLOW:"Yellow",CAPTIONS_FONT_COLOR:"Font color",LANGUAGE_SUBTITLE:"This changes the language of the app. Videos remain in their original language unless the creator provides translations.",CAPTIONS_BACKGROUND_OPACITY:"Background opacity",REMOVE_FROM_DEVICE_TITLE:"Remove ${accountName} from this device?",FEEDBACK_SUBMISSION_FAILURE:"Feedback not submitted",FEEDBACK_SUCCESS_CONFIRM_TITLE:"Thanks!",FAILED_CREDITS:"Could not download Credits, please try again later.",FEEDBACK_APP_NOT_WORKING:"App isn't working",
MDX_DEVICE_CONNECTED_WITH_DEVICE_TOAST_TITLE:'"${device}" is now connected',OFF_LABEL:"Off",UPCOMING_EVENT_LABEL:"Starts ${liveStartDate} at ${liveStartTime}",FEEDBACK_ACCOUNTS:"Accounts",MDX_UPDATE_QUEUE_ADD_VIDEO_TOAST_TITLE_WITH_USER:"${user} added a video",SHARED_DEVICE_TEXT:"All users of this device influence what videos appear here. Sign in for a personalized experience. More info: youtube.com/tvrecs",GUEST_MODE_STATUS_TEXT:"You are in guest mode",MORE:"More",CHANGE_LANGUAGE:"Change language to ${language}?",
PURCHASES_COMPLETE_TITLE:"Complete your purchase online",PURCHASE_TOS_ITEM_PAID_SERVICE:"YouTube Paid Terms of Service",VIEW_CHANNEL:"View channel",VIDEO_SURVEY_SUBMSG:"Your input helps millions of people find better content on YouTube.",LIVE_CHAT_EDUCATION_TOAST_MESSAGE:"View live stream now with Live chat",VOICE_ERROR_MIC:"Check your microphone settings",CAPTIONS_GREEN:"Green",CAPTIONS_SMALL_CAPS:"Small Capitals",VP_SEEKING:"Can't fast forward or rewind",YOURE_SIGNED_IN_AS:"You're signed in to YouTube as ${username}",
INTERSTITIAL_REASON_SUBSCRIBE:"Subscribing to a channel requires using an account",WELCOME_TITLE_MULTI_ACCOUNT:"YouTube for everyone in your household",CAPTIONS_DEPRESSED:"Depressed",CAPTIONS_FONT_SIZE:"Font size",AUTH_ERROR_OVERLAY_SUBTITLE:"Your account couldn't be accessed. Please sign in again or use a different account.",CAPTIONS_OFF_STATUS:"Off",LOADING_LABEL:"Loading...",EXIT_YOUTUBE_SUBTITLE:"Are you sure you want to exit YouTube?",CAPTIONS_UNIFORM:"Uniform",MOBILE_SMART_REMOTE_SUBTITLE:"Open the YouTube App on your mobile device to search using the mic",
APP_VERSION:"Version",BACK:"Back",TRY_ANOTHER_WAY:"Try another way",CAPTIONS_PROP_SERIF:"Proportional Serif",LEGEND_HOME:"Go Home",PURCHASES_ADD_NEW_PAYMENT_METHOD_TEXT:"To add a payment method, go to pay.google.com",LEGEND_DELETE:"Delete",FEEDBACK_CASTING:"Casting from device to TV",INTERSTITIAL_REASON_LIKE_DISLIKE:"Likes and dislikes require using an account",APP_GEO:"Geo",LINK_WIFI_THIRD_STEP:"Tap the cast icon and select this device.",CLEAR:"Clear",FEEDBACK_SHARE_MORE:"To share more with us, go to ${url} or scan",
CAPTIONS_WINDOW_COLOR:"Window color",VIDEO_SURVEY_MSG:"Thanks!",CAPTIONS_TITLE:"Subtitles & Closed Captions",ERROR_NETWORK:"A network error has occurred. Please check your network connection.",VP_GREEN_SCREEN:"Seeing a green screen",CAPTIONS_BLACK:"Black",SIGNIN_DIRECT_TAG:"Use your Google Account",LOCATION_UPDATED_TOAST:"Your location has been updated to ${location}",CHOOSE_AN_ACCOUNT_TEXT:"Choose an account",LOADING_TEXT:"Loading...",SEND_FEEDBACK:"Send feedback",INTERSTITIAL_REASON_TRIGGER_PLAYER:"This video requires age verification",
LINK_WIFI_SECOND_STEP:"Open the YouTube app on your phone.",TRY_AGAIN:"Try again",FEEDBACK_FAILURE_SHARE_MORE:"We weren't able to submit your report. You can let us know what happened by going to ${url} or scan",ACCOUNTS_SURFACE_TITLE_MULTIPLE_ACCOUNTS:"Accounts",CLEAR_SEARCH_HISTORY_DIALOG_MSG_SIGNED_IN:"This will clear YouTube searches made with this account from all devices.",WELCOME_TAGLINE1_LIBRARY:"Sign in to see videos you've liked, saved, and watched in your YouTube library",REPORT_SUCCESS_CONFIRM_MESSAGE:"Your report helps make YouTube better",
FEAT_LIVESTREAM:"Want to live stream from device",SIGN_IN_TEXT:"Sign in",MDX_UPDATE_QUEUE_CLEAR_PLAYLIST_TOAST_TITLE_WITHOUT_USER:"Queue was cleared",EXIT_YOUTUBE_TITLE:"Exit YouTube",WELCOME_TAGLINE1:"Sign in - it's free",WELCOME_TAGLINE2:"Get personalized recommendations and quick access to your favorite videos",CAPTIONS_TEXT_OPACITY:"Text opacity",VIDEO_DISMISSED:"Got it. We'll tune your recommendations.",PURCHASES_GPAY_LOGO:"gPay logo",FEAT_VSP:"Want to control playback speed",VIDEO_SPEED:"Video speed",
CHOOSE_YOUR_LANGUAGE:"Choose your language",LANGUAGE_LABEL:"Language",CAPTIONS_STYLE_SUBTITLE:"Change text, color, and background style",CAPTIONS_CURSIVE:"Cursive",QUALITY:"Video quality",VP_VIDEO_QUALITY:"Video is poor quality",ACCOUNTS_SURFACE_TITLE_ONE_ACCOUNT:"Account",REPORT_CPN:"Please report this ID to help us fix the problem: ${cpn}",CHOOSE_YOUR_LOCATION:"Choose your location",SIGNIN_DIRECT:"Sign in on your TV",LINK_TV_CODE_THIRD_STEP_2:"Enter TV code",LINK_TV_CODE_THIRD_STEP_3:"and enter the code below.",
LINK_TV_CODE_THIRD_STEP_1:"Tap",PURCHASES_CHANGE_PAYMENT_OPTION_TEXT:"Change payment method",CLOSE:"Close",VERIFY_ITS_YOU_OVERLAY_TITLE:"Verify it's you",APP_CLIENT:"Client",MDX_UPDATE_QUEUE_ADD_PLAYLIST_TOAST_TITLE_WITHOUT_USER:"Playlist was added",VP_BUFFERING:"Buffering",LEGEND_SEARCH:"Search",PURCHASE_TOS_SELECTOR_OVERLAY_TITLE:"Terms of Service",SIGN_OUT_TEXT:"Sign out",VP_BAD_AUDIO:"Poor audio quality",VISIT_TERMS:"To read YouTube's Terms of Service, visit:",MDX_DEVICE_DISCONNECTED_TOAST_TITLE:"New device disconnected",
VOICE_ERROR_SEARCH_FINAL:"Try searching a different way",EXIT:"Exit",APP_LANGUAGE:"Language",SEAMLESS_SAME_WIFI:"Make sure your mobile device is on the same Wi-Fi as this TV",LANGUAGE_UPDATED_TOAST:"Your language has been changed",SEAMLESS_TAP_PROFILE:"Tap your profile picture",PURCHASE_TOS_ITEM_PAY_CONTENT:"Paid Service Usage rules",CAPTIONS_UNAVAILABLE_SUBTITLE:"Subtitles are currently not available.",URL_SIGN_IN_ACCOUNT:"Sign in to your Google Account",WELCOME_TITLE_MOVIES:"Find your next movie",
LOCATION_SET_TO:"Set to ${location}",UNLINK_ALL_DEVICES_OVERLAY_TITLE:"Unlink all devices?",INFO_NONE:"None",MDX_UPDATE_QUEUE_ADD_PLAYLIST_BADGE:"${videoCount} videos",PLAY:"Play",PAUSE:"Pause",WELCOME_TITLE_LIBRARY:"Enjoy your favorite videos",SPACE:"Space",APP_CONTENT_REGION:"Content Region",CLEAR_WATCH_HISTORY_DIALOG_MSG_SIGNED_OUT:"All videos watched while signed out will be cleared.",MDX_AUTOPLAY_CANCEL_INSTRUCTION_VOICE:'To cancel, say "Ok Google, stop"',PLAYER_ERROR:"Player Error",AUTH_ERROR_OVERLAY_TITLE:"Sign in error",
SIGNIN_URL:"Sign in with a web browser",VP_GENERIC:"Video doesn't play",GUEST_TITLE:"Use YouTube as a Guest",PURCHASES_COMPLETE_BUTTON_TEXT:"Go to Purchases",APP_VISITOR_ID:"Visitor ID"};

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.Px=function(a,b){a={browseId:a};b&&(a.params=b);return{browseEndpoint:a}};_.m().w("sym");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("syn");
_.zt=function(){_.Dh.apply(this,arguments);this.j={}};_.G(_.zt,_.Dh);_.zt.prototype.g=function(a,b){var c=this;At(this,a).add(b);return function(){var d=c.j[a];d&&(d.delete(b),0===d.size&&delete c.j[a])}};_.zt.prototype.h=function(a,b){for(var c=[],d=1;d<arguments.length;++d)c[d-1]=arguments[d];if(d=this.j[a]){d=_.A(d);for(var e=d.next();!e.done;e=d.next()){e=e.value;try{e.apply(null,c)}catch(f){_.C(f)}}}};var At=function(a,b){var c=a.j[b];c||(c=new Set,a.j[b]=c);return c};
_.zt.prototype.aa=function(){_.v.clear(this.j);_.Dh.prototype.aa.call(this)};
var Bt=function(){_.zt.apply(this,arguments)};_.G(Bt,_.zt);
_.Ct=function(a){Bt.call(this);this.value=this.A=a};_.G(_.Ct,Bt);_.Ct.prototype.get=function(){return this.value};_.Ct.prototype.set=function(a){var b=this.value;a!==b&&(this.value=a,this.h("value-changed",a,b));this.h("value-set",a,b)};_.Ct.prototype.reset=function(){this.set(this.A)};_.Ct[_.Ji]=["initial"];

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Qx;
Qx=function(a){return a.replace(/([^0-9,\.:]+)/g,function(b){var c=b.length;20>=c?b+=" lorem ipsum dolor sit amet, has tamquam virtute maluisset ne, errem vocent detraxit vel in. Vim affert quaeque an, has ne zril platonem tractatos. Te mel nulla minim persecuti. Detraxit dissentiunt ei cum. Nam labores oporteat hendrerit ad, nam ne labitur fabulas deterruisset.".slice(0,2*c):50>=c?b+=" lorem ipsum dolor sit amet, has tamquam virtute maluisset ne, errem vocent detraxit vel in. Vim affert quaeque an, has ne zril platonem tractatos. Te mel nulla minim persecuti. Detraxit dissentiunt ei cum. Nam labores oporteat hendrerit ad, nam ne labitur fabulas deterruisset.".slice(0,c):
(c=Math.floor(c/2),c=" lorem ipsum dolor sit amet, has tamquam virtute maluisset ne, errem vocent detraxit vel in. Vim affert quaeque an, has ne zril platonem tractatos. Te mel nulla minim persecuti. Detraxit dissentiunt ei cum. Nam labores oporteat hendrerit ad, nam ne labitur fabulas deterruisset.".repeat(Math.ceil(c/278)).slice(0,c),b+=c);return b})};
_.Sx=function(a){if(!a)return"";var b="";if("string"===typeof a)b=a;else if("string"===typeof a.simpleText)b=a.simpleText;else if(a.runs)for(var c=0,d=a.runs.length;c<d;++c)b+=a.runs[c].text;return Rx?Qx(b)+"#":b};_.m().w("syp");
var Rx=!1;

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("syx");
var Xt=_.p("LifecycleService","ij3ZSb");
var Zt=function(){var a=window.location,b=this;this.document=document;this.location=a;this.B=_.r.get(_.Oa).runtime;this.g=new _.Ct(!!_.pd().isPreloaded);this.h=new _.Ct(!1);this.F=new _.Ct(!1);this.A=_.Jo();this.j=_.pd().isPreloaded?_.Jo()-this.A:-1;this.C=function(){b.F.set(!0)};this.D=function(){b.isHidden()?(b.j=_.Jo()-b.A,b.g.set(!0)):b.resume()};this.document.addEventListener("visibilitychange",this.D);this.document.addEventListener("webkitvisibilitychange",this.D);window.addEventListener("beforeunload",
this.C);window.addEventListener("unload",this.C);window.addEventListener("focus",function(){b.focus()});window.addEventListener("blur",function(){b.blur()});this.B.isSupported()&&(this.B.onPause.addListener(function(){b.blur()}),this.B.onResume.addListener(function(){b.focus()}));this.onResume(function(){_.Ub();_.Rt()});this.onFocus(function(){_.Ub();_.Rt()});_.Yt(this,function(){_.Ib()})};Zt.prototype.onFocus=function(a){return this.h.g("value-changed",function(b){b||a()})};
Zt.prototype.onBlur=function(a){return this.h.g("value-changed",function(b){b&&a()})};Zt.prototype.onResume=function(a){return this.g.g("value-changed",function(b){b||a()})};_.Yt=function(a,b){a.F.g("value-changed",b)};_.k=Zt.prototype;_.k.resume=function(){var a=_.Jo()-this.A,b=a>36E5+this.j;0<this.j&&6912E5<a&&b&&(this.location.hash="",this.location.reload(!0));this.j=-1;this.g.set(!1)};_.k.focus=function(){this.h.set(!1)};_.k.blur=function(){this.h.set(!0)};
_.k.isActive=function(){return!this.g.get()&&!this.h.get()};_.k.isHidden=function(){return!(!this.document.hidden&&!this.document.webkitHidden)};_.$t=_.K(Xt,function(){return new Zt});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var zo=function(a){a=a.command;if(a.clientActionEndpoint)return a.clientActionEndpoint.action&&a.clientActionEndpoint.action.actionType||null;if(a.signalAction)return a.signalAction.signal||null;for(var b in a)if(!yo[b])return b;return null},Ao={ym:"UNKNOWN",ov:"HELP",GB:"SEND_FEEDBACK",jC:"TELL_US_WHY",hs:"ENABLE_CHROME_NOTIFICATIONS",jr:"CHANNEL_RELOAD_WITHOUT_POLYMER",nB:"RELOAD_PAGE",bC:"SUBMIT_FORM",xr:"CLOSE_POPUP",pB:"RELOAD_WITHOUT_POLYMER",ZA:"PAUSE_PLAYER",Fr:"COPY_SHARE_URL",Er:"COPY_SHARE_EMBED_URL",
sC:"TOGGLE_TRANSCRIPT_TIMESTAMPS",yr:"CLOSE_TRANSCRIPT_PANE",nC:"TOGGLE_DARK_THEME_ON",mC:"TOGGLE_DARK_THEME_OFF",jB:"PLAY_PLAYER",aC:"STOP_PLAYER",fB:"PLAYER_REPLAY",hB:"PLAYER_SKIP_AD",EC:"VOLUME_UP",DC:"VOLUME_DOWN",DA:"MUTE",xC:"UNMUTE",RB:"SHOW_PREVIOUS_FAMILY_DIALOG",dB:"PLAYER_PLAY_NEXT",eB:"PLAYER_PLAY_PREVIOUS",gB:"PLAYER_SHUFFLE_PLAYLIST",bB:"PLAYER_LOOP_PLAYLIST",cB:"PLAYER_LOOP_VIDEO",aB:"PLAYER_LOOP_OFF",rC:"TOGGLE_RESTRICTED_MODE_ON",qC:"TOGGLE_RESTRICTED_MODE_OFF",xB:"RETRY_UPDATE_METADATA_REQUEST",
pC:"TOGGLE_POLYMER_OFF",Cr:"COPY_CONNECTION_INVITE_URL",Gr:"CREATE_COMMUNITY_POST",tv:"HISTORY_BACK",uv:"HISTORY_FORWARD",cC:"SUBMIT_NOTIFICATION_OPTIONS_FORM",LB:"SHOW_ACCOUNT_LINK_DIALOG",NA:"OPEN_PLAYER_PAGE",OA:"OPEN_POST_COMMENT_DIALOG",rq:"ACKNOWLEDGE_YOUTHERE",vr:"CLOSE_LIVE_STREAM_ENDSCREEN",PB:"SHOW_KEYBOARD_SHORTCUT_DIALOG",JA:"OPEN_IN_NEW_TAB",qr:"CLOSE_ALERT",WB:"SKIP_NAVIGATION",Dr:"COPY_LINK_ADDRESS",rr:"CLOSE_FULLSCREEN_ERROR",dr:"CANCEL_AUTONAV",qB:"REMOVE_ITEM",OB:"SHOW_GUIDE",pv:"HIDE_GUIDE",
uB:"RESET_WARM_LOADS",qv:"HIDE_LOGO",QB:"SHOW_LOGO",Us:"GUEST_MODE",kB:"POPUP_BACK",ws:"EXIT_APP",sB:"RESET_ACCOUNTS_LIST",QA:"OPEN_SEARCH_BAR",Ar:"CONFIRM_CONTROVERSIAL_CONTENT",Br:"CONFIRM_RACY_CONTENT",oC:"TOGGLE_MORE_OPTIONS",tC:"TOGGLE_VIDEO_INFO",HA:"OPEN_AUDIO_OPTIONS_OVERLAY",MA:"OPEN_PLAYBACK_SPEED_OVERLAY",PA:"OPEN_QUALITY_SELECTOR_OVERLAY",IA:"OPEN_FILE_PICKER",oB:"RELOAD_PLAYER",YA:"PAUSED_WATCH_HISTORY",wB:"RESUMED_WATCH_HISTORY",wr:"CLOSE_PDG_BUY_FLOW",pr:"CLEARED_SEARCH_HISTORY",zr:"CLOSE_WINDOW",
tB:"RESET_APP",HB:"SEND_PAYMENT",uA:"MDX_UNLINK_ALL_DEVICES",tq:"ACTIVATE_RENTAL",dC:"SUBMIT_POPUP_FORM_FIELDS",vC:"TRANSACTION_FLOW_STARTED",wC:"TRANSACTION_FLOW_USER_CANCELED",uC:"TRANSACTION_FLOW_SECOND_SCREEN",SA:"OPEN_TERMS_OF_SERVICE",LA:"OPEN_PAID_SERVICE_TERMS",KA:"OPEN_PAID_CONTENT_TERMS"},Bo={ym:"UNKNOWN",Wq:"BACK_TO_TOP",xs:"EXPAND_PARENT",RA:"OPEN_SIGN_IN_PROMPT"};_.m().w("syz");
var Do,Co,Fo,yo;_.L=function(){this.g=new Map};_.L.R=function(){Co||(Co=new _.L);return Co};_.N=function(a,b,c,d){var e=Do(a,b);if((a=e.get(c))&&a.targetId===b.targetId&&a.kd===d)return function(){};e.set(c,{targetId:b.targetId,context:c,kd:d});return function(){var f=e.get(c);f&&f.targetId===b.targetId&&f.kd===d&&e.delete(c)}};
_.L.prototype.h=function(a){var b=zo(a);if(!b)throw Error("Z`"+JSON.stringify(a));if(b=this.g.get(b)){b=_.A(b.values());for(var c=b.next();!c.done;c=b.next())c=c.value,c.targetId&&a.id!==c.targetId||c.kd.call(c.context,a)}};_.Eo=function(a,b,c){if(a=a.g.get(b.g))for(a=_.A(a.values()),b=a.next();!b.done;b=a.next())b=b.value,b.targetId&&void 0!==b.targetId||b.kd.call(b.context,c)};Do=function(a,b){a.g.get(b.g)||a.g.set(b.g,new Map);return a.g.get(b.g)};Co=null;Fo={};
yo=(Fo.clickTrackingParams=!0,Fo.loggingUrls=!0,Fo.commandMetadata=!0,Fo);_.O=function(a,b){this.g=a;this.targetId=b;Object.values(Bo).includes(a)||Object.values(Ao).includes(a)};

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy10");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("qUNcgf");
var au=_.p("MdxSessionInfoService","qUNcgf");
var bu;bu=function(){this.g=this.h=!1;this.j=[]};_.cu=_.K(au,function(){return new bu});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var du,fu,ju;du=function(a){return function(b,c){_.u(a)[b]=c}};fu=function(a){var b={};a=_.A(a);for(var c=a.next();!c.done;b={eg:b.eg},c=a.next()){var d=c.value;c=d.params;d=d.service;c&&d&&(b.eg=eu[d],b.eg&&c.forEach(function(e){return function(f){var g=f.key;f=f.value;g&&f&&e.eg(g,f)}}(b)))}};
_.iu=function(a,b,c,d){d=void 0===d?!1:d;var e=_.r.get(_.gu),f=hu(e,c).then(function(g){b.headers=Object.assign(Object.assign({},g),b.headers);return b});return _.hp(f).g(_.Cr(function(g){return _.Yp(a,g)}),_.Cr(function(g){return g.json()}),_.Ar(function(g){g.status&&401===g.status&&_.sr(c)&&!d?(c&&e.g.remove(c.effectiveObfuscatedGaiaId),g=_.iu(a,b,c,!0)):g=_.$o(g);return g}))};ju=function(a,b){b=_.dh(b,b,void 0);b.g=!0;_.oh(a,b);return a};_.ku=function(a){if(a.j.length)return"MDX_CONTROL_MODE_REMOTE"};
_.m().w("syq");
_.db("LATEST_ECATCHER_SERVICE_TRACKING_PARAMS",{});_.db("INNERTUBE_SUGGEST_CONFIG",{});var lu={},eu=(lu.CSI=_.dt,lu.ECATCHER=du("LATEST_ECATCHER_SERVICE_TRACKING_PARAMS"),lu.SUGGEST=du("INNERTUBE_SUGGEST_CONFIG"),lu);
var nu=function(){this.g={};var a=_.Yc.get("yt-dev.CONSISTENCY",void 0);a&&mu(this,{encryptedTokenJarContents:a})};nu.prototype.replace=function(a,b){a=_.A(a);for(var c=a.next();!c.done;c=a.next())delete this.g[c.value.encryptedTokenJarContents];mu(this,b)};
var mu=function(a,b){if(b.encryptedTokenJarContents&&(a.g[b.encryptedTokenJarContents]=b,"string"===typeof b.expirationSeconds)){var c=1E3*Number(b.expirationSeconds);setTimeout(function(){delete a.g[b.encryptedTokenJarContents]},c);_.Zc("CONSISTENCY",b.encryptedTokenJarContents,c,void 0,!0)}};
var ou=_.p("AuthorizationService","deglxe");
var pu=_.p("UnauthenticatedLogger","A5kdCb");
var qu;qu=function(){this.g=_.r.get(_.$t)};_.ru=function(a,b,c){"tokenRefresh"===b&&1<100*Math.random()||(a=a.g.g.get(),_.C(Error("wa`"+b+"`"+c.message),a?"WARNING":"ERROR"))};_.yu=_.K(pu,function(){return new qu});
var zu,Bu,hu,Au;zu={eventTrigger:"ACCOUNT_EVENT_TRIGGER_OAUTH_ACCESS_TOKEN_FAILURE"};Bu=function(){this.j=_.r.get(_.Lr);this.B=_.r.get(_.yu);this.A=_.L.R();this.g=new Au(function(a){return _.Jo()<a.accessTokenExpiration});this.h=new Map};
_.Cu=function(a,b){var c=a.g.get(b.effectiveObfuscatedGaiaId);if(c)return _.I(c);if(c=a.h.get(b.effectiveObfuscatedGaiaId))return c;c=ju(_.ih(a.j.Zi(b).then(function(d){a.g.add(b.effectiveObfuscatedGaiaId,d);_.fc("tokenRefreshEvent",{refreshEventType:"TOKEN_REFRESH_EVENT_TYPE_SUCCESS",reason:"TOKEN_REFRESH_EVENT_REASON_SUCCESS"},_.gd);return d}),function(d){_.ru(a.B,"tokenRefresh",d);_.fc("tokenRefreshEvent",{refreshEventType:"TOKEN_REFRESH_EVENT_TYPE_FAILURE",reason:"TOKEN_REFRESH_EVENT_REASON_STANDARD_FAILURE"},
_.gd);d.status&&(new Set([400,401,403])).has(d.status)&&_.Eo(a.A,new _.O("removeIdentityAction"),{identity:b,identityActionContext:zu});return null}),function(){a.h.delete(b.effectiveObfuscatedGaiaId)});a.h.set(b.effectiveObfuscatedGaiaId,c);return c};hu=function(a,b){return _.sr(b)?_.Cu(a,b).then(function(c){if(!c)return{};c={Authorization:"Bearer "+c.accessToken};3===b.gaiaDelegationType&&(c["X-Goog-PageId"]=b.effectiveObfuscatedGaiaId);return c}):_.I({})};
Au=function(a){this.validation=void 0===a?function(){return!0}:a;this.store=new Map};Au.prototype.add=function(a,b){this.store.set(a,b)};Au.prototype.remove=function(a){this.store.delete(a)};Au.prototype.get=function(a){var b=this.store.get(a);if(b){if(this.validation(b))return b;this.remove(a)}};_.gu=_.K(ou,function(){return new Bu});
var Du=_.p("InnerTubeClient","mFMjh");
var Eu=function(){var a=void 0===a?window:a;nu.instance||(nu.instance=new nu);this.g=nu.instance;this.h=_.r.get(_.cu);this.environment=a.environment||{}};Eu.prototype.cd=function(){var a=_.yd(this.environment),b={consistencyTokenJars:_.v.la(this.g.g)},c=_.ku(this.h);c&&(b.mdxControlMode=c);(c=_.pd().mdxInitData)&&""!==c.mdxEnvironment&&(b.mdxEnvironment=c.mdxEnvironment);c=_.Wa(window.location,"env_sherlog");"string"===typeof c&&c&&(b.sherlogUsername=c);_.zd(a,{context:{request:b}});return a};
Eu.prototype.fetch=function(a){var b=this,c=a.clickTracking,d=a.path,e=a.identity,f=a.payload;a=void 0===a.headers?{}:a.headers;d=_.Ha(_.Ia(new _.Ga(_.u("INNERTUBE_HOST_OVERRIDE","")),d),"key",_.u("INNERTUBE_API_KEY","unknown")).toString();var g=this.cd();g.context.clickTracking=c;e&&_.sr(e)&&3===e.gaiaDelegationType&&(g.context.user=Object.assign(Object.assign({},g.context.user),{onBehalfOfUser:e.effectiveObfuscatedGaiaId}));_.zd(g,f||{});c={method:"POST",body:JSON.stringify(g),headers:Object.assign({"Content-Type":"application/json",
"X-Goog-Visitor-Id":_.u("VISITOR_DATA")},a)};return _.hp(_.iu(d,c,e||_.Er)).g(_.dp(function(h){if(h.responseContext){fu(h.responseContext.serviceTrackingParams||[]);var l=h.responseContext.visitorData;l&&_.db("VISITOR_DATA",l);(l=h.responseContext.consistencyTokenJar)&&b.g.replace(g.context.request.consistencyTokenJars,l)}return h}))};_.Fu=_.K(Du,function(){return new Eu});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.Ux=function(a){var b=_.Tx();return a/b};_.Tx=function(){var a=window;isNaN(Vx)&&(a=a.getComputedStyle(a.document.body).getPropertyValue("font-size"),Vx=Number(a.replace(/(\d+.?\d*)px/,"$1")));return Vx};_.m().w("syr");
var Vx=NaN;

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Gu=function(){this.D=!1;this.A=null;this.h=void 0;this.g=1;this.B=this.C=0;this.F=this.j=null},Hu=function(a){if(a.D)throw new TypeError("e");a.D=!0},Nu,Qu,Ou,Pu,Ru,Uu;Gu.prototype.next_=function(a){this.h=a};var Iu=function(a,b){a.j={exception:b,Nk:!0};a.g=a.C||a.B};Gu.prototype.return=function(a){this.j={return:a};this.g=this.B};_.Ju=function(a,b,c){a.g=c;return{value:b}};Gu.prototype.ua=function(a){this.g=a};_.Ku=function(a,b,c){a.C=b;void 0!=c&&(a.B=c)};_.Lu=function(a,b){a.g=b;a.C=0};
_.Mu=function(a){a.C=0;var b=a.j.exception;a.j=null;return b};Nu=function(a){this.g=new Gu;this.h=a};Nu.prototype.next_=function(a){Hu(this.g);if(this.g.A)return Ou(this,this.g.A.next,a,this.g.next_);this.g.next_(a);return Pu(this)};Qu=function(a,b){Hu(a.g);var c=a.g.A;if(c)return Ou(a,"return"in c?c["return"]:function(d){return{value:d,done:!0}},b,a.g.return);a.g.return(b);return Pu(a)};
Ou=function(a,b,c,d){try{var e=b.call(a.g.A,c);if(!(e instanceof Object))throw new TypeError("d`"+e);if(!e.done)return a.g.D=!1,e;var f=e.value}catch(g){return a.g.A=null,Iu(a.g,g),Pu(a)}a.g.A=null;d.call(a.g,f);return Pu(a)};Pu=function(a){for(;a.g.g;)try{var b=a.h(a.g);if(b)return a.g.D=!1,{value:b.value,done:!1}}catch(c){a.g.h=void 0,Iu(a.g,c)}a.g.D=!1;if(a.g.j){b=a.g.j;a.g.j=null;if(b.Nk)throw b.exception;return{value:b.return,done:!0}}return{value:void 0,done:!0}};
Ru=function(a){this.next=function(b){return a.next_(b)};this.throw=function(b){Hu(a.g);a.g.A?b=Ou(a,a.g.A["throw"],b,a.g.next_):(Iu(a.g,b),b=Pu(a));return b};this.return=function(b){return Qu(a,b)};_.Id();this[Symbol.iterator]=function(){return this}};_.Su=function(a,b){b=new Ru(new Nu(b));_.Od&&(0,_.Od)(b,a.prototype);return b};
_.Tu=function(a,b){var c=void 0,d=void 0,e;if(!(e=d)){if("undefined"!==typeof Promise)e=Promise;else throw Error("n");e=d=e}return new e(function(f,g){function h(q){try{n(b.next(q))}catch(t){g(t)}}function l(q){try{n(b["throw"](q))}catch(t){g(t)}}function n(q){q.done?f(q.value):(new d(function(t){t(q.value)})).then(h,l)}n((b=b.apply(a,c)).next())})};
Uu=function(a){return new _.Zg(function(b){var c=a.length,d=[];if(c)for(var e=function(h,l,n){c--;d[h]=l?{Wm:!0,value:n}:{Wm:!1,reason:n};0==c&&b(d)},f=0,g;f<a.length;f++)g=a[f],_.fh(g,_.Eb(e,f,!0),_.Eb(e,f,!1));else b(d)})};_.m().w("syu");
_.Vu=function(a,b,c,d,e){this.A=a;this.navigate=b;this.g=c;this.j=d;this.h=e};_.Wu=function(){return _.Vu.instance};
_.Vu.prototype.resolveCommand=function(a,b){return _.Tu(this,function d(){var e=this,f,g;return _.Su(d,function(h){if(1==h.g)return a.commandExecutorCommand?h.return(Xu(e,a)):_.Ju(h,e.A(a,b),2);f=h.h;if(0===f.type){if(!e.navigate)return _.jb(Error("za")),h.return(!1);var l=f.command;if(!(l.commandMetadata&&l.commandMetadata.webCommandMetadata&&l.commandMetadata.webCommandMetadata.url))return _.jb(Error("Aa")),h.return(!1);g=e.navigate(f);e.h&&e.h.C(f.command,g);return h.return(!0)}if(1===f.type){if(!e.g)return _.jb(Error("Ba")),
h.return(!1);e.g(f);return h.return(!0)}if(2===f.type){if(!e.j)return _.jb(Error("Ca")),h.return(!1);e.j(f);return h.return(!0)}return h.return(!1)})})};var Xu=function(a,b){if(b.commandExecutorCommand)return b=(b.commandExecutorCommand&&b.commandExecutorCommand.commands||[]).map(function(c){return a.resolveCommand(c)}),Uu(b).then(function(){return!0});_.jb(Error("Da"));return _.I(!1)};

_.m().l();

}catch(e){_._DumpException(e)}
try{
var av,$u,cv,dv;_.Yu=function(){return _.lb("ytPubsubPubsubInstance")};_.Zu=function(a,b){var c=_.Yu();if(c){var d=c.subscribe(a,function(){var e=arguments;var f=function(){_.ak[d]&&b.apply(window,e)};try{_.ck[a]?f():_.nb(f,0)}catch(g){_.jb(g)}},void 0);_.ak[d]=!0;_.bk[a]||(_.bk[a]=[]);_.bk[a].push(d);return d}return 0};av=function(){var a=$u;return function(b){var c="function"===typeof a?a:function(){return a};var d=Object.create(b,_.Lq);d.source=b;d.mm=c;return d}};$u=function(){return new _.nq};
_.bv=function(){return function(a){return _.bp()(av()(a))}};cv=function(a){var b=_.r.get(_.Fu),c=function(){};c.prototype.isReady=function(){return!0};c.prototype.cd=function(){return b.cd()};c.prototype.eh=function(d,e,f){f=f.onSuccess||function(){};b.fetch({path:"youtubei/v1/"+d,payload:e,identity:a}).subscribe(f)};return c};dv=0;_.m().w("sy12");
var ev=_.p("Identity","deglxe");
var fv=_.p("Logger","lNU6Af");
var gv=_.p("GelService","T5U7Be");
var hv=function(){this.g=_.tr;var a=void 0===a?10:a;_.Lb||(_.Lb=Math.round((0,_.xb)()));_.cc=0;_.dc=a;dv||(dv=_.Zu("pageunload",_.Ib));_.v.isEmpty(_.sb)||_.Hb();_.Nb||(_.Nb=6E4)};hv.prototype.send=function(a,b,c,d){_.ur(this.g,c)||(_.Ib(),this.g=c);c={timestamp:d};_.fc(a,b,cv(this.g),c)};_.iv=_.K(gv,function(){return new hv});
var jv,lv;jv=function(){this.g=_.r.get(_.iv)};_.kv=function(a){if(a)return a;_.C(new TypeError("Ea"));return"ACCOUNT_EVENT_TRIGGER_UNKNOWN"};lv=_.K(fv,function(){return new jv});
var mv=function(){this.A=_.r.get(lv);this.h=_.r.get(_.Lr)};mv.prototype.Vc=function(a,b,c,d){var e=this;return this.h.Vc(a,b).then(function(){e.g=void 0;var f=e.A,g=c;g=_.kv(g);g={changeType:"ACCOUNT_REGISTRY_CHANGE_TYPE_ADD_ACCOUNT",trigger:g};d&&(g.signInMethod=d);f.g.send("accountRegistryChange",g,a)})};_.nv=_.K(ev,function(){return new mv});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.ov=function(){return window.bedrockPorts||null};_.m().w("sy13");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.pv=function(a,b,c,d){var e=new Date(_.Jo());30<=e.getMinutes()&&e.setHours(e.getHours()+1);e.setMinutes(0);e.setSeconds(0);e.setMilliseconds(0);a.send(b,c,d,e.getTime())};_.m().w("sy11");
var qv=_.p("CurrentIdentityService","jDlUvb");
var rv=function(){this.g=null;this.h=_.r.get(_.nv)},sv=function(){var a=_.ov();return a&&a.network&&a.network.getCurrentIdentity&&a.network.getCurrentIdentity()};rv.prototype.get=function(){var a=this,b=sv();return b?b:this.g?_.I(this.g):this.h.h.Jf().then(function(c){a.g=c;return a.g})};
rv.prototype.set=function(a,b){var c=this;_.Ib();return this.get().then(function(d){c.g=a;var e=c.h,f=b&&b.eventTrigger;e.h.Ii(a);e=e.A;if(!_.ur(a,d)){f=_.kv(f);var g=_.sr(a),h=_.sr(d);g&&h?(e.g.send("accountStateChangeSignedIn",{newState:"ACCOUNT_STATUS_CHANGE_TYPE_ACCOUNT_SWITCH",trigger:f},a),_.pv(e.g,"accountStateChangeSignedIn",{newState:"ACCOUNT_STATUS_CHANGE_TYPE_ACCOUNT_SWITCH",trigger:f},d)):h&&!g?(_.pv(e.g,"accountStateChangeSignedOut",{newState:"ACCOUNT_STATUS_CHANGE_TYPE_LOG_OUT",trigger:f},
a),e.g.send("accountStateChangeSignedIn",{newState:"ACCOUNT_STATUS_CHANGE_TYPE_LOG_OUT",trigger:f},d)):!h&&g&&(e.g.send("accountStateChangeSignedIn",{newState:"ACCOUNT_STATUS_CHANGE_TYPE_LOG_IN",trigger:f},a),_.pv(e.g,"accountStateChangeSignedOut",{newState:"ACCOUNT_STATUS_CHANGE_TYPE_LOG_IN",trigger:f},d))}_.Wu().resolveCommand({onIdentityChanged:{identityActionContext:b}})})};_.tv=_.K(qv,function(){return new rv});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.Wx=function(a){function b(g){g.target===c&&(clearTimeout(f),c&&c.removeEventListener(d,b),e())}var c=a.element,d=a.eventType,e=a.X,f=setTimeout(function(){c&&c.removeEventListener(d,b);e()},a.ud);if(!c)return f;c.addEventListener(d,b);return f};_.m().w("sy14");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy15");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.Xx=function(a){return _.p(a,a)};_.m().w("sy16");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.Yx=function(a){return"rgba("+((a&16711680)>>>16)+", "+((a&65280)>>>8)+", "+(a&255)+", "+Number((((a&4278190080)>>>24)/255).toFixed(2))+")"};_.m().w("sy17");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.Zx=function(a,b){_.ab("ytglobal.timing"+(b||"")+"ready_",a,void 0)};_.$x=function(a){a=a||"";var b=_.Ks();b[a]&&delete b[a];var c=_.Js(),d={timerName:a,info:{},tick:{}};c.push(d);b[a]=d};_.ay=function(a){if(_.Ms(a)){var b={actionType:_.st[_.u((a||"")+"TIMING_ACTION",void 0)]||"LATENCY_ACTION_UNKNOWN",previousAction:_.st[_.u("PREVIOUS_ACTION",void 0)]||"LATENCY_ACTION_UNKNOWN"};a=_.Es(a);_.Os().info(b,a)}};
_.by=function(a){_.Bs(a);(0,_.wt)();_.As(!1,a);a||(_.u("TIMING_ACTION")&&_.db("PREVIOUS_ACTION",_.u("TIMING_ACTION")),_.db("TIMING_ACTION",""))};_.cy=function(a,b){if(!_.Ms(b)&&!_.zs(b)){var c=_.u("CSI_SERVICE_NAME","youtube");_.u((b||"")+"TIMING_ACTION",void 0)&&c&&(_.ct("aa",void 0,b),_.Zs("ap",1,b),_.Zs("yt_fss",a,b),_.et(b))}};_.dy=function(a,b,c){_.Zs("yt_sts",a,c);_.ct("_start",b,c)};
_.ey=function(a,b,c,d){d=d?d:a;_.$x(d);_.Ls(d||"").info.actionType=a;_.cy("c",d);_.by(d);_.Cs(d).useGel=!0;_.db(d+"TIMING_AFT_KEYS",b);_.db(d+"TIMING_ACTION",a);_.dy("c",c,d);_.ay(d);_.Zx(!0,d);_.bt(d)};_.fy=function(a,b){_.Dn.call(this);this.j=a||1;this.g=b||_.x;this.A=(0,_.je)(this.Sp,this);this.B=(0,_.Sb)()};_.ke(_.fy,_.Dn);_.k=_.fy.prototype;_.k.enabled=!1;_.k.ic=null;_.k.setInterval=function(a){this.j=a;this.ic&&this.enabled?(this.stop(),this.start()):this.ic&&this.stop()};
_.k.Sp=function(){if(this.enabled){var a=(0,_.Sb)()-this.B;0<a&&a<.8*this.j?this.ic=this.g.setTimeout(this.A,this.j-a):(this.ic&&(this.g.clearTimeout(this.ic),this.ic=null),this.dispatchEvent("tick"),this.enabled&&(this.stop(),this.start()))}};_.k.start=function(){this.enabled=!0;this.ic||(this.ic=this.g.setTimeout(this.A,this.j),this.B=(0,_.Sb)())};_.k.stop=function(){this.enabled=!1;this.ic&&(this.g.clearTimeout(this.ic),this.ic=null)};_.k.aa=function(){_.fy.Ha.aa.call(this);this.stop();delete this.g};
_.m().w("sy18");
var gy=_.p("CsiService","uXgssc");
var hy;hy=function(){this.bedrockPorts=_.ov();this.g=!1};_.iy=function(a,b,c){if(a.bedrockPorts)a.bedrockPorts.performance.Np(b,c,!0,void 0);else{_.ey(b,c.slice(),void 0,void 0);var d;a=_.pd();_.Ys({tvInfo:{label:a.clientLabel,isPreloaded:a.isPreloaded,appQuality:_.wd[a.legacyApplicationQuality],isMdx:!(null===(d=a.mdxInitData)||void 0===d||!d.Vf)}},b)}};hy.prototype.tick=function(a,b,c){_.ct(a,c,b)};_.jy=function(a){_.$s("_start","voice-assistant")&&!_.$s(a,"voice-assistant")&&_.ct(a,void 0,"voice-assistant")};
hy.prototype.info=function(a,b){for(var c in a)if(a.hasOwnProperty(c)){var d=a[c];d&&_.dt(c,d,b)}};_.ky=_.K(gy,function(){return new hy});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var my=function(a){var b=ly[a]||a;return _.D("isRtl",!1)?b:a},oy=function(a){a=a.icon;a="string"===typeof a?_.ny[my(a)]||"":(a=a.iconType&&my(a.iconType))&&_.ny[a]||"";return _.R("host",null,_.R("div",{className:a}))};_.m().w("sy19");
var py={},ly=(py.ARROW_FORWARD="ARROW_BACK",py.ARROW_BACK="ARROW_FORWARD",py.CHEVRON_LEFT="CHEVRON_RIGHT",py.CHEVRON_RIGHT="CHEVRON_LEFT",py);
var qy=_.Xx("k2MtF");
var W;W={};
_.ny=(W.ACCOUNT_CIRCLE="material-icon-account-circle",W.ADD="material-icon-add",W.ADD_TO_PLAYLIST="material-icon-playlist-add",W.ARROW_BACK="material-icon-arrow-back",W.ARROW_FORWARD="material-icon-arrow-forward",W.AUDIOTRACK="material-icon-record-voice-over",W.AUTOPLAY="material-icon-queue-play-next",W.BACK="material-icon-arrow-back",W.BACKSPACE="material-icon-backspace",W.BUG_REPORT="material-icon-bug-report",W.CAPTIONS="material-icon-closed-caption",W.CHECK="material-icon-done ytlr-icon--blue",W.CHECK_BOX=
"material-icon-check-box ytlr-icon--blue",W.CHECK_BOX_OUTLINE_BLANK="material-icon-check-box-outline-blank",W.CHECK_CIRCLE_THICK="material-icon-check-circle",W.CHEVRON_LEFT="material-icon-chevron-left",W.CHEVRON_RIGHT="material-icon-chevron-right",W.CLEAR_COOKIES="material-icon-reset-tv",W.CLEAR_SEARCH_HISTORY="material-icon-delete",W.CLEAR_WATCH_HISTORY="material-icon-delete",W.CONTRAST="material-icon-brightness-high",W.DISLIKE="material-icon-thumb-down",W.DISLIKE_SELECTED="material-icon-thumb-down ytlr-icon--blue",
W.DISMISSAL="material-icon-clear",W.DO_NOT_DISTURB="material-icon-do-not-disturb-alt",W.EDIT="material-icon-edit",W.ERROR_OUTLINE="material-icon-error-outline",W.EXIT_TO_APP="material-icon-exit-to-app",W.FEEDBACK="material-icon-feedback",W.FLAG="material-icon-flag",W.FORUM="material-icon-forum",W.HAPPY="material-icon-sentiment-satisfied",W.HELP="material-icon-help-outline",W.IMPROVE_YOUTUBE="material-icon-youtube-improve-tv",W.INFO="warning",W.INFO_OUTLINE="material-icon-info-outline",W.LANGUAGE=
"material-icon-language",W.LENS_BLACK="material-icon-lens ytlr-icon--lens-black",W.LENS_BLUE="material-icon-lens ytlr-icon--lens-blue",W.LENS_CYAN="material-icon-lens ytlr-icon--lens-cyan",W.LENS_GREEN="material-icon-lens ytlr-icon--lens-green",W.LENS_MAGENTA="material-icon-lens ytlr-icon--lens-magenta",W.LENS_RED="material-icon-lens ytlr-icon--lens-red",W.LENS_WHITE="material-icon-lens ytlr-icon--lens-white",W.LENS_YELLOW="material-icon-lens ytlr-icon--lens-yellow",W.LIKES_PLAYLIST="material-icon-thumb-up",
W.LIKE="material-icon-thumb-up",W.LIKE_SELECTED="material-icon-thumb-up ytlr-icon--blue",W.LIVE="material-icon-youtube-live",W.MEH="material-icon-sentiment-neutral",W.MICROPHONE_ON="material-icon-mic",W.MIX="material-icon-youtube-mix",W.MODERATOR="material-icon-build",W.MORE_HORIZ_LIGHT="material-icon-more-horiz",W.MORE_VERT="material-icon-more-vert",W.MUSIC="icon-music",W.MUSIC_VIDEO="material-icon-music-video",W.OFFICIAL_ARTIST_BADGE="material-icon-play-music",W.PAUSE="material-icon-pause",W.PHONE=
"icon-settings-pair",W.PHONELINK="material-icon-phonelink",W.PLAYING="material-icon-play-arrow",W.PLAYLISTS="material-icon-playlist-play",W.PRIVACY_INFO="material-icon-info-outline",W.PROGRESS_ACTIVITY="material-icon-progress-activity",W.PURCHASES="icon-guide-purchases",W.RELATED="icon-related",W.REPLAY="material-icon-replay",W.REPORT_PROBLEM="material-icon-flag",W.SAD="material-icon-sentiment-dissatisfied",W.SEARCH="material-icon-search",W.SEARCH_HISTORY="material-icon-history",W.SETTINGS="material-icon-settings",
W.SIGN_IN="material-icon-account-circle",W.SKIP_NEXT="material-icon-skip-next",W.SKIP_PREVIOUS="material-icon-skip-previous",W.SLOW_MOTION_VIDEO="material-icon-slow-motion-video",W.SMARTPHONE="material-icon-smartphone",W.SOCIAL="icon-guide-social",W.SOUND="material-icon-volume-up",W.STAR="material-icon-star",W.STAR_BORDER="material-icon-star-border",W.SUBSCRIBE="material-icon-video-youtube",W.SUBSCRIPTIONS="material-icon-subscriptions",W.SYNC="material-icon-sync",W.SYNC_PROBLEM="material-icon-sync-problem",
W.TAB_LIBRARY="material-icon-folder",W.TRANSLATE="material-icon-translate",W.TRENDING="material-icon-youtube-trending",W.TV="material-icon-tv",W.UNSUBSCRIBE="material-icon-video-youtube ytlr-icon--red",W.UPLOADS="icon-upload",W.UP_ARROW="material-icon-keyboard-arrow-up",W.VERIFIED="material-icon-done",W.VERY_HAPPY="material-icon-sentiment-very-satisfied",W.VERY_SAD="material-icon-sentiment-very-dissatisfied",W.VIDEO_QUALITY="material-icon-hd",W.VIDEO_QUALITY_4K="material-icon-fourk",W.VIDEO_QUALITY_8K=
"material-icon-eightk",W.VIDEO_YOUTUBE="material-icon-video-youtube",W.VIEW_ALL="icon-wii-plus",W.YOUTUBE_LINKED_TV="material-icon-youtube-linked-tv",W.WATCH_HISTORY="icon-guide-history",W.WATCH_LATER="icon-watch-later",W.WHAT_TO_WATCH="material-icon-home",W);
_.ry=function(){_.Q.apply(this,arguments);this.template=oy};_.G(_.ry,_.Q);_.ry.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.ry.TAG_NAME="ytlr-icon";_.J(qy,_.ry);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.sy=function(a){if(0===a.items.length||0===a.selectedIndex)return 0;var b=a.sa[a.selectedIndex],c=a.xa(a.items[a.selectedIndex],a.selectedIndex);b+=.5*c;if(b<a.size/2)return 0;c=a.sa.length-1;c=a.sa[c]+a.xa(a.items[c],c)+a.gb;return c<a.size?0:b>c-.5*a.size?c-a.size:b-.5*a.size};_.m().w("sy1a");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy1b");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy1c");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy1e");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy1f");
var ty;ty={};_.uy=(ty[177]=176,ty[176]=177,ty[37]=39,ty[39]=37,ty);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.vy=function(a){var b=_.uy[a]||a;return _.D("isRtl",!1)?b:a};_.m().w("sy1g");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var wy=function(a){_.bw(a)},zy=function(){xy&&(xy(),xy=void 0);yy&&(clearTimeout(yy),yy=void 0);_.Qt()},By=function(){_.C(Error("va`"+Ay()));_.Ot.reset();zy()},Dy=function(){_.Ot.isLocked()||(yy=setTimeout(By,5500));Cy();if(!xy){var a=document.activeElement;a.addEventListener("keypress",wy);a.addEventListener("keydown",wy);a.addEventListener("keyup",wy);xy=function(){a.removeEventListener("keypress",wy);a.removeEventListener("keydown",wy);a.removeEventListener("keyup",wy)}}},Ey=function(a){var b=
a.fb,c=a.sa,d=a.items,e=a.xa,f=a.size,g=a.Kl;if(!d.length)return[];for(var h=0,l=0;l<c.length;l++)if(c[l]+e(d[l],l)>b){h=l;break}l=0;for(var n=h;n<c.length;n++)if(c[n]>=b){l=n;break}n=0;for(var q=l;q<c.length;q++)if(c[q]+e(d[q],q)<b+f)n=q;else break;d=0;for(e=n;e<c.length;e++)if(c[e]<b+f)d=e;else break;f=_.D("enableAnimations",!0)&&g?1:0;c=Math.min(d+f,c.length-1);b=[];for(f=Math.max(h-f,0);f<=c;f++)g=0,f>=h&&f<=d&&(g=1),f>=l&&f<=n&&(g=2),b.push({index:f,key:f,visibility:g,selected:f===a.selectedIndex});
return b},Fy=function(a){if(a.itemSize)return function(){return a.itemSize};if(!a.xa)throw Error("Oa");return a.xa},Gy=function(a){var b=Array(a.items.length),c=Fy(a),d=a.spacing||0;b[0]=a.ea||0;for(var e=1;e<a.items.length;e++){var f=b[e-1],g=c(a.items[e-1],e-1);b[e]=f+g;0<g&&(b[e]+=d)}return b},Hy=function(a,b){for(var c=a.selectedIndex+b;c===_.Ut(c,0,a.items.length-1);){var d=a,e=d.items[c];if(d.xa&&0>=d.xa(e,c)?0:d.Yd?d.Yd(e):1)return c;c+=b}return a.selectedIndex},Iy=function(a){var b=2===a;
_.D("enablePartialVisibilityLogging",!1)&&(b=1===a||2===a);return b},Jy=function(a,b){var c=this,d="horizontal"===a.layout?"width":"height",e=b.Wg;b=b.Ue.filter(function(g){return!!a.items[g.index]});var f={};return _.R("host",{style:(f.position="relative",f[d]=a.size+"rem",f)},_.R("div",{style:this.Ae()},b.map(function(g){var h=g.index;g=g.visibility;var l=""+(e?h%e:h),n=Iy(g),q={};return _.R("div",{style:c.Be(h),idomKey:l,className:["yt-virtual-list__item","yt-virtual-list__item-"+l,(q["yt-virtual-list__item--selected"]=
a.selectedIndex===h,q["yt-virtual-list__item--visible"]=n,q["zylon-hidden"]=!n,q)]},a.Fa({item:a.items[h],index:h,selected:h===a.selectedIndex,visibility:g}))})))},Cy=function(){var a=_.Ot;a.h++;a.g.set("yt_virtual_list.view_updater",(a.g.get("yt_virtual_list.view_updater")||0)+1)},Ay=function(){var a=_.Ot;return"{"+a.g.La().sort().map(function(b){return b+": "+a.g.get(b)}).join(", ")+"}"},Ky=function(a,b){return a>b?1:a<b?-1:0},yy,xy,Ly={"tile-to-tile":{input:"BEDROCK_REPETITIVE_ACTION_TYPE_TILE_TO_TILE",
transition:"BEDROCK_REPETITIVE_ACTION_TYPE_TILE_TO_TILE_TOTAL"},"row-to-row":{input:"BEDROCK_REPETITIVE_ACTION_TYPE_ROW_TO_ROW",transition:"BEDROCK_REPETITIVE_ACTION_TYPE_ROW_TO_ROW_TOTAL"}};_.m().w("sy1d");
var My=function(a){this.container=a;this.active=new Map;this.g=[]};My.prototype.get=function(a,b){b=void 0===b?_.$d:b;var c=Ny(this,a);b(c);Oy(this,c,a);return c};My.prototype.remove=function(a){var b=this.active.get(a.index);b&&(this.container.removeChild(b.element),this.active.delete(a.index),this.g.push(b))};var Py=function(a){a=a.g.splice(0,a.g.length);_.Xw(a.map(function(b){return b.element}))};My.prototype.xa=function(){return this.g.length};
var Ny=function(a,b){var c=a.active.get(b.index);if(c)return c.element;if(a.g.length&&(c=a.g.findIndex(function(e){return e.item.selected===b.selected}),-1<c)){var d=a.g[c];a.g.splice(c,1);return d.element}return document.createElement("div")},Oy=function(a,b,c){a.active.set(c.index,{element:b,item:c});if(!b.parentElement)if(_.D("enableAnimations",!1))a.container.appendChild(b);else{var d=Qy(a).find(function(e){return e>c.index});d="number"===typeof d?a.active.get(d):null;a.container.insertBefore(b,
d?d.element:null)}},Qy=function(a){a=Array.from(a.active.keys());a.sort(Ky);return a};
var Ry=function(a){this.items=a;this.g=a.reduce(function(b,c){b[c.index]=c;return b},{})};Ry.prototype.entries=function(){return this.items.slice()};Ry.prototype.filter=function(a){return this.items.filter(a)};Ry.prototype.has=function(a){return!!this.g[a.index]};Ry.prototype.get=function(a){return this.g[a]||null};
var Wy=function(a,b){this.snapshot=a;this.type=b&&b.props.version===a.props.version?b.props.items[0]!==a.props.items[0]?"replace":"move":"replace";this.h=Sy(this,a,b);this.g=Ty(this,a,b);this.j=Uy(this,a,b);this.A=Vy(this,a,b);this.Lf=(b?b.fb:0)-a.fb;this.B=b?b.items.entries():[]},Sy=function(a,b,c){return"replace"!==a.type&&c?b.items.filter(function(d){return!c.items.has(d)}):b.items.entries()},Ty=function(a,b,c){return c?"replace"===a.type?c.items.entries():c.items.filter(function(d){return!b.items.has(d)}):
[]},Uy=function(a,b,c){return"replace"!==a.type&&c?b.items.filter(function(d){if(!c.items.has(d))return!1;var e=c.items.get(d.index).selected!==d.selected;d=d.selected;return e||d}):[]},Vy=function(a,b,c){return"replace"!==a.type&&c?b.items.filter(function(d){return c.items.has(d)?c.items.get(d.index).visibility!==d.visibility:!1}):[]};
var Xy=function(){};Xy.prototype.Be=function(a){var b="horizontal"===a.layout?"translateX":"translateY",c="horizontal"===a.layout?"width":"height",d=a.im;_.D("isRtl",!1)&&"horizontal"===a.layout&&(d=-d);var e={};return e[c]=a.itemSize+"rem",e.position="absolute",e.transform=b+"("+d+"rem)",e};
Xy.prototype.Ae=function(a){var b=a.Ue,c=a.layout,d=a.$e,e="horizontal"===c?"translateX":"translateY",f="transform "+(_.D("enableZds",!1)?300:150)+"ms "+(_.D("enableZds",!1)?"cubic-bezier(0.26, 0.86, 0.44, 0.985)":"linear");a=a.fb;_.D("isRtl",!1)&&"horizontal"===c&&(a=-a);return{display:0===b.length?"none":void 0,position:"absolute",transform:_.D("removeVirtualListTranslateZ",!1)?e+"("+-a+"rem)":e+"("+-a+"rem) translateZ(0)",transition:d?"none":f}};
var Yy=function(){};Yy.prototype.Be=function(a){var b="horizontal"===a.layout?"width":"height",c="horizontal"===a.layout?"marginRight":"marginBottom";_.D("isRtl",!1)&&"horizontal"===a.layout&&(c="marginLeft");var d={};return d[b]=a.itemSize+"rem",d.display="horizontal"===a.layout?"inline-block":void 0,d[c]=a.spacing+"rem",d.verticalAlign="top",d};
Yy.prototype.Ae=function(a){var b=a.layout,c=a.Ue;a=a.sa[c.length?c[0].index:0]-a.fb;c="horizontal"===b?"marginLeft":"marginTop";_.D("isRtl",!1)&&"horizontal"===b&&(c="marginRight");var d={};return d["horizontal"===b?"width":"height"]="160rem",d[c]=a+"rem",d};
var Zy=new Xy,$y=new Yy;
var az=new Xy,bz=new Yy;
var cz=function(a,b,c){c=void 0===c?!1:c;this.props=a;this.sa=b;var d=Fy(a);this.fb=a.alignment({gb:a.gb||0,xa:d,items:a.items,sa:b,selectedIndex:a.selectedIndex||0,size:a.size||0,ea:a.ea||0});a=Ey({fb:this.fb,xa:d,items:a.items,Kl:c,sa:b,selectedIndex:a.selectedIndex||0,size:a.size});this.items=new Ry(a)};
var dz=function(a){_.Dh.call(this);this.container=a;this.active=!1;this.enableAnimations=_.D("enableAnimations",!1);this.g=new My(a)};_.G(dz,_.Dh);dz.prototype.update=function(a,b){var c=this;if(this.isActive())throw Error("Pa");this.active=!0;Dy();var d=this.snapshot;this.snapshot=ez(this,a);a=new Wy(this.snapshot,d);this.execute(a,function(){c.active=!1;b()})};dz.prototype.isActive=function(){return this.active};
var ez=function(a,b){var c=Gy({items:b.items,ea:b.ea,itemSize:b.itemSize,xa:b.xa,spacing:b.spacing}),d=0<b.selectedIndex;return new cz(b,c,a.snapshot&&a.snapshot.props.items[0]===b.items[0]||d)};
dz.prototype.execute=function(a,b){var c=this,d=function(){_.Wg(function(){var e=_.Ot,f=e.g.get("yt_virtual_list.view_updater");void 0===f?(_.C(Error("ua`yt_virtual_list.view_updater")),e.isValid=!1):(e.h--,1<f?e.g.set("yt_virtual_list.view_updater",f-1):e.g.remove("yt_virtual_list.view_updater"));_.Ot.isLocked()||zy()});fz(c,a,b)};"move"===a.type?gz(this,a,d):this.replace(a,d)};
var gz=function(a,b,c){hz(a,b,!0);iz(a,b);b.Lf||b.g.length||b.h.length?jz(a,b,function(){kz(a,b);lz(a,b);a.enableAnimations||hz(a,b);c()}):c()};dz.prototype.replace=function(a,b){hz(this,a);kz(this,a);lz(this,a);b()};
var kz=function(a,b){for(var c=0;c<b.g.length;c++)a.g.remove(b.g[c])},lz=function(a,b){for(var c=0;c<b.h.length;c++)a.Fa(b.h[c],b)},iz=function(a,b){for(var c=0;c<b.g.length;c++){var d=b.g[c];if(d.selected){a.Fa(Object.assign(Object.assign({},d),{selected:!1}),b);break}}for(c=0;c<b.j.length;c++)a.Fa(b.j[c],b);for(c=0;c<b.A.length;c++)a.Fa(b.A[c],b,!0)};
dz.prototype.Fa=function(a,b,c){c=void 0===c?!1:c;b.snapshot.props.items[a.index]&&this.g.get(a,function(d){var e=b.snapshot.sa,f=b.snapshot.props,g=c,h=f.items[a.index];h=Fy(f)(h,a.index);e={im:e[a.index],layout:f.layout,spacing:f.spacing||0,itemSize:h};e=_.D("enableAnimations",!1)?az.Be(e):bz.Be(e);h={item:f.items[a.index],index:a.index,selected:a.selected,visibility:a.visibility};if(!g){f=f.Fa(h);try{(0,_.jx)(d,f)}catch(l){_.C(l)}}_.hw.style(d,"style",e);d.className="yt-virtual-list__item "+(a.selected?
"yt-virtual-list__item--selected":"")+" "+(Iy(a.visibility)?"yt-virtual-list__item--visible":"zylon-hidden")})};
var hz=function(a,b,c){var d="replace"===b.type||b.snapshot.props.Ub;b={Ue:(void 0===c?0:c)?b.B:b.snapshot.items.entries(),sa:b.snapshot.sa,fb:b.snapshot.fb,layout:b.snapshot.props.layout,$e:d};_.hw.style(a.container,"style",_.D("enableAnimations",!1)?Zy.Ae(b):$y.Ae(b))},jz=function(a,b,c){!a.enableAnimations||0===b.Lf||b.snapshot.props.Ub?_.Mt({stage:40,X:c}):_.Wx({eventType:_.bn,element:a.container,ud:750,X:c})},fz=function(a,b,c){b.Lf||b.g.length||b.h.length?a.enableAnimations&&!b.snapshot.props.Ub||
"move"!==b.type?_.Mt({stage:40,X:c}):setTimeout(c,75):c()};dz.prototype.aa=function(){Py(this.g)};
var mz=function(){};mz.prototype.h=function(a){setTimeout(function(){_.Mt({X:a,stage:40})},75)};mz.prototype.g=function(a){var b=a.Uc,c=a.container;0===a.Lf?_.Mt({stage:40,X:b}):_.Wx({eventType:_.bn,element:c,X:b,ud:750})};
var nz=function(){};nz.prototype.h=function(a){_.Mt({stage:40,X:a})};nz.prototype.g=function(a){var b=a.rj;setTimeout(a.Uc,null!==b&&void 0!==b?b:75)};
var oz={},pz={},qz={vertical:(oz[38]=-1,oz[40]=1,oz),horizontal:(pz[177]=-1,pz[37]=-1,pz[39]=1,pz[176]=1,pz)},sz=function(){_.Q.apply(this,arguments);var a=this;this.Se=null;this.j=!1;this.startMs=-1;this.template=function(b){var c="horizontal"===b.layout?"width":"height",d={};b={style:(d.position="relative",d[c]=b.size+"rem",d)};return _.R("host",b,_.vw)};this.A=function(b){a.startMs=_.Jo();var c=rz(a,a.startMs);a.props.M(b);_.Mt({stage:50,X:c})}};_.G(sz,_.Q);sz.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;
sz.prototype.O=function(){var a=this,b=document.createElement("div");this.el.appendChild(b);this.h=new dz(b);tz(this);_.T(this,function(){_.Eh(a.h)});this.g("keydown",function(c){a.onKeyDown(c)});this.g("keydown",function(c){if(a.h.isActive())a.onKeyDown(c)},!0)};sz.prototype.W=function(){this.h.isActive()?this.j=!0:(tz(this,this.startMs),this.startMs=-1)};sz.prototype.M=function(a){this.h.isActive()?this.Se=a:this.A(a)};var tz=function(a,b){a.j=!1;a.Se=null;a.h.update(a.props,function(){a.Uc(b)})};
sz.prototype.Uc=function(a){this.el&&(this.props.Wb&&void 0!==a&&-1<a&&(a={latency:{actionType:Ly[this.props.Wb].transition,durationMs:_.Jo()-a}},_.U(this,"yt-virtual-list__transition-end",a)),this.h.isActive()||("number"===typeof this.Se?this.A(this.Se):this.j&&(tz(this,this.startMs),this.startMs=-1)))};
var rz=function(a,b){return function(){var c={disableSounds:!!a.props.disableSounds};a.props.Wb&&(c.latency={actionType:Ly[a.props.Wb].input,durationMs:_.Jo()-b});_.U(a,"yt-virtual-list__index-change",c)}};
sz.prototype.onKeyDown=function(a){if(this.props.M){var b=qz[this.props.layout][this.props.Ni?_.vy(a.keyCode):a.keyCode];if("number"===typeof b){var c=Hy(this.props,b);c===this.props.selectedIndex?0<b&&_.U(this,"yt-virtual-list__right-edge",{disableSounds:!!this.props.disableSounds}):(this.M(c),_.bw(a,!1,!0))}}};sz.TAG_NAME="yt-virtual-list";
var uz={},vz={},wz={vertical:(uz[38]=-1,uz[40]=1,uz),horizontal:(vz[177]=-1,vz[37]=-1,vz[39]=1,vz[176]=1,vz)},xz=new mz,yz=new Xy,zz=new nz,Az=new Yy,Bz=new WeakMap,Ez=function(a){_.Q.call(this,a);var b=this;this.template=Jy;this.status={type:1};this.h=function(c){var d=_.Jo();b.status={type:0};var e=b.fb;b.props.M(c);b.Aa.g({Uc:function(){b.Uc(d,c)},container:b.el.firstChild,Lf:Math.abs(b.fb-e),rj:b.props.rj});_.Mt({stage:50,X:Cz(b,d,c)})};this.B=_.wx(function(){b.status={type:0};_.Mt({stage:40,
X:function(){_.S(b,Dz(b,b.props,!0));b.Uc()}})});this.j=_.wx(function(c,d,e,f,g,h,l,n,q){return c({items:d,sa:e,ea:f||0,gb:g||0,selectedIndex:h||0,size:l||0,xa:Fy({itemSize:n,xa:q})})});this.A=_.wx(function(c,d,e,f,g){return Gy({items:c,ea:d,itemSize:e,xa:f,spacing:g})});this.state=Object.assign(Object.assign({},Dz(this,a,!1)),{items:a.items,$e:!!a.Ub})};_.G(Ez,_.Q);
Ez.getDerivedStateFromProps=function(a,b){var c=b||{},d=a.items[0]!==c.items[0];return Object.assign(Object.assign({},c),{$e:d||!!a.Ub,Wg:d?0:b&&b.Wg||0,items:a.items})};Ez.prototype.O=function(){var a=this;this.g("keydown",function(b){a.onKeyDown(b)});this.g("scroll",function(){a.el.scrollTop=0;a.el.scrollLeft=0});this.g("focusin",function(){a.B(a.props.items)})};
Ez.prototype.Uc=function(a,b){if(this.el){var c=this.status.Se;this.status={type:1};this.props.Wb&&void 0!==a&&void 0!==b&&(a={latency:{actionType:Ly[this.props.Wb].transition,durationMs:_.Jo()-a},selectedIndex:b},_.U(this,"yt-virtual-list__transition-end",a));void 0!==c&&this.h(c)}};
var Cz=function(a,b,c){return function(){var d={selectedIndex:c,disableSounds:!!a.props.disableSounds};a.props.Wb&&(d.latency={actionType:Ly[a.props.Wb].input,durationMs:_.Jo()-b});_.U(a,"yt-virtual-list__index-change",d)}};
Ez.prototype.onKeyDown=function(a){if(this.props.M){var b=wz[this.props.layout][this.props.Ni?_.vy(a.keyCode):a.keyCode];if("number"===typeof b){var c=Hy(this.props,b);c===this.props.selectedIndex?0<b&&_.U(this,"yt-virtual-list__right-edge",{disableSounds:!!this.props.disableSounds}):(this.M(c),_.bw(a,!1,!0))}}};Ez.prototype.M=function(a){1===this.status.type?this.h(a):this.status.Se=a};
Ez.prototype.W=function(a){var b=this;if(a!==this.props){var c=this.state.$e;this.Aa.h(function(){_.S(b,Dz(b,b.props,!c))})}};var Dz=function(a,b,c){b=Ey({fb:a.fb,xa:Fy(b),items:b.items,sa:a.sa,selectedIndex:a.props.selectedIndex,size:b.size,Kl:c});var d=(c=a.props.items&&a.props.items[0])&&Bz.get(c)||0;a=Math.max(a.state&&a.state.Wg||0,b.length,d);a!==d&&Bz.set(c,a);return{Ue:b,Wg:a}};
Ez.prototype.Be=function(a){return this.styles.Be({im:this.sa[a],layout:this.props.layout,spacing:this.props.spacing||0,itemSize:Fy(this.props)(this.props.items[a],a)})};Ez.prototype.Ae=function(){return this.styles.Ae({sa:this.sa,layout:this.props.layout,fb:this.fb,Ue:this.state.Ue,$e:this.state.$e})};
_.E.Object.defineProperties(Ez.prototype,{styles:{configurable:!0,enumerable:!0,get:function(){return _.D("enableAnimations",!0)?yz:Az}},Aa:{configurable:!0,enumerable:!0,get:function(){return _.D("enableAnimations",!0)&&!this.props.Ub?xz:zz}},sa:{configurable:!0,enumerable:!0,get:function(){return this.props?this.A(this.props.items,this.props.ea,this.props.itemSize,this.props.xa,this.props.spacing):[]}},fb:{configurable:!0,enumerable:!0,get:function(){return this.props?this.j(this.props.alignment,
this.props.items,this.sa,this.props.ea,this.props.gb,this.props.selectedIndex,this.props.size,this.props.itemSize,this.props.xa):0}}});Ez.TAG_NAME="yt-virtual-list";
var Gz,Hz;_.Fz=Ez;Gz=window.environment;Hz=Gz&&Gz.flags;Hz&&Hz.enable_high_performance_list&&(_.Fz=sz);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.Iz=function(a){return{simpleText:a}};_.m().w("sy1h");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Jz=function(a){var b=a.Jh,c=a.data;a=a.id;var d=_.Sx(c),e={};a&&(e.id=a);c&&"string"!==typeof c&&c.runs&&c.runs.length&&c.runs[0].textColor&&(e.style={color:_.Yx(c.runs[0].textColor)});return _.R("host",Object.assign({"aria-hidden":b,dir:"auto"},e),d)};_.m().w("sy1i");
_.X=function(){_.Q.apply(this,arguments);this.template=Jz};_.G(_.X,_.Q);_.X.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.X.TAG_NAME="yt-formatted-string";

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.Y=function(a,b,c){c=void 0===c?function(){return null}:c;return a?b(a):c()};_.m().w("sy1j");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy1k");
_.Kz=_.p("YtlrAccountSelector","mlDGgf");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy1l");
_.Lz={eventTrigger:"ACCOUNT_EVENT_TRIGGER_SIGN_IN_PROMO",nextEndpoint:_.Px("default")};

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy1m");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy1p");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Nz;_.Mz=function(a){var b=_.Tx();return a*b};Nz=function(a){var b=void 0===a.Qh?"100%":a.Qh,c={};return _.R("host",{className:[(c["ytlr-thumbnail-details--full-height"]=void 0===a.Dg?!0:a.Dg,c),"cover"===b?"ytlr-thumbnail-details--bg-cover":"ytlr-thumbnail-details--bg-hundred-percent"],style:{backgroundColor:this.color,backgroundImage:this.backgroundImage}})};_.m().w("sy1q");
var Qz,Rz;_.Pz=function(){_.Q.apply(this,arguments);var a=this;this.template=Nz;this.h=_.wx(function(b,c){if(b.thumbnails&&0!==b.thumbnails.length&&(b=_.Oz(c,b.thumbnails,a.props.zd)))b.startsWith("//")&&(b="https:"+b);else return null;c=new Image;c.src=b||"";return c})};_.G(_.Pz,_.Q);_.Pz.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.Oz=function(a,b,c){a=Qz(a,b,c);if((b=b[a])&&b.url)return b.url};Qz=function(a,b,c){a=_.Mz(a);c&&(a=c(a));return Rz(a,b.map(function(d){return d.width}))};
Rz=function(a,b){if(!b.length)return-1;for(var c=b.length;0<=c;c--)if(b[c]<=a)return c;return 0};
_.E.Object.defineProperties(_.Pz.prototype,{color:{configurable:!0,enumerable:!0,get:function(){var a=this.props.data;if(a.placeholderColor)return _.Yx(a.placeholderColor);if(this.props.Ya)return this.props.Ya}},url:{configurable:!0,enumerable:!0,get:function(){var a=this.h(this.props.data,this.props.width);return a?a.src:null}},backgroundImage:{configurable:!0,enumerable:!0,get:function(){var a="url('"+this.url+"')";return this.props.Un?this.props.Un.join(", ")+", "+a:a}}});_.Pz.TAG_NAME="ytlr-thumbnail-details";

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Zz=function(a){var b=a.borderColor,c=a.overlay,d=a.position,e=a.size,f=a.style,g=a.thumbnail,h=Sz[f],l=c?Tz[c]:null,n=b?Uz[b]:null;return _.R("host",null,_.R("div",{className:["ytlr-avatar-icon__avatar-container",Vz[e],d?Wz[d]:null]},_.Y(a.iconText,function(q){return _.R("div",{className:h},_.R("div",{className:"ytlr-avatar-icon__text"},q))}),_.Y("bubble"===f,function(){return _.R("div",{className:[n,"ytlr-avatar-icon__thumb-border"]},_.R(_.Pz,{className:["ytlr-avatar-icon__thumbnail",h,Xz[e]],
data:g||{},width:Yz[e],Dg:!1,Qh:"cover"}))},function(){return _.Y(g,function(q){return _.R(_.Pz,{className:["ytlr-avatar-icon__thumbnail",h],data:q,width:Yz[e]})})}),_.Y(c,function(){var q={},t={};return _.R("div",{className:["ytlr-avatar-icon__outline",l,n]},_.R("div",{className:["ytlr-avatar-icon__fill",(q["ytlr-avatar-icon__blue-dot"]="fresh"===c,q),(t["material-icon-check-circle"]="checked"===c,t)]}))})))};_.m().w("sy1o");
var $z=_.Xx("Plgrde");
var aA={},Sz=(aA.square="ytlr-avatar-icon--square-style",aA.circle="ytlr-avatar-icon--circle-style",aA.bubble="ytlr-avatar-icon--bubble-style",aA),bA={},Wz=(bA.center="ytlr-avatar-icon--center-position",bA.left="ytlr-avatar-icon--left-position",bA.right="ytlr-avatar-icon--right-position",bA),cA={},Yz=(cA.huge=8.5,cA.large=3,cA.regular=2,cA),dA={},Vz=(dA.huge="ytlr-avatar-icon--huge-size",dA.large="ytlr-avatar-icon--large-size",dA.regular="ytlr-avatar-icon--regular-size",dA),eA={},Xz=(eA.huge="ytlr-avatar-icon__thumbnail--thumb-border-huge",
eA.large="ytlr-avatar-icon__thumbnail--thumb-border-large",eA.regular="ytlr-avatar-icon__thumbnail--thumb-border-regular",eA),fA={},Tz=(fA.checked="ytlr-avatar-icon--checked",fA),gA={},Uz=(gA.transparent="ytlr-avatar-icon--transparent-border",gA.white="ytlr-avatar-icon--white-border",gA);
_.hA=function(){_.Q.apply(this,arguments);this.template=Zz};_.G(_.hA,_.Q);_.hA.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.hA.TAG_NAME="ytlr-avatar-icon";_.J($z,_.hA);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy1r");
var iA=_.p("KabukiInnerTubeClient","seCV7b");
_.jA=function(){this.ra=_.r.get(_.Fu);this.qa=_.r.get(_.tv);this.Z=_.r.get(_.ky)};_.jA.prototype.isReady=function(){return!0};_.jA.prototype.cd=function(){return this.ra.cd()};_.jA.prototype.fetch=function(a){var b=this;return _.hp(this.qa.get()).g(_.Cr(function(c){return b.ra.fetch(Object.assign({identity:c},a))}))};_.jA.prototype.eh=function(a,b,c){c=c.onSuccess||function(){};this.fetch({path:"youtubei/v1/"+a,payload:b}).subscribe(c,function(d){1>1E5*Math.random()&&_.C(d,"WARNING")})};
_.kA=_.K(iA,function(){return new _.jA});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var lA,mA;lA=function(a,b,c,d){c={csn:b,ve:_.Hx(c),eventType:1};d&&(c.clientData=d);d={xe:_.es(b),Ed:b};"UNDEFINED_CSN"==b?_.Fx("visualElementShown",c,d):a?_.fc("visualElementShown",c,a,d):_.hd("visualElementShown",c,d)};
_.nA=function(a){var b=_.Lx.R();b.g=_.jA;a="string"===typeof a?{data:{trackingParams:a}}:{visualElement:a};var c=a.data&&a.data.loggingDirectives?a.data.loggingDirectives.trackingParams||"":a.data&&a.data.trackingParams||"",d=a;_.fb("enable_ve_tracker_key")&&(d=a.visualElement?a.visualElement:c);var e=b.j.has(d),f=b.A.get(d);b.j.add(d);b.A.set(d,!0);a.ho&&!e&&a.ho();if(c||a.visualElement)if(d=_.cs(void 0),null!==b.h&&d!=b.h&&(_.gs(new _.Vj("VisibilityLogger called before newScreen()",{caller:a.tagName,
previous_csn:b.csn,current_csn:d})),d=null),d){var g=!(!a.data||!a.data.loggingDirectives);if(mA(a)||g)c=a.visualElement?a.visualElement:_.Ix(c),g||e?mA(a)&4?f||(b=b.g,a={csn:d,ve:_.Hx(c),eventType:4},e={xe:_.es(d),Ed:d},"UNDEFINED_CSN"==d?_.Fx("visualElementShown",a,e):b?_.fc("visualElementShown",a,b,e):_.hd("visualElementShown",a,e)):mA(a)&1&&!e&&lA(b.g,d,c):lA(b.g,d,c,void 0)}};
_.oA=function(a){var b=_.ov();if(b)b.recordClick(a);else if(b="string"===typeof a?_.Ix(a):a,a=_.cs()){b={csn:a,ve:_.Hx(b),gestureType:"INTERACTION_LOGGING_GESTURE_TYPE_GENERIC_CLICK"};var c={xe:_.es(a),Ed:a};"UNDEFINED_CSN"==a?_.Fx("visualElementGestured",b,c):_.jA?_.fc("visualElementGestured",b,_.jA,c):_.hd("visualElementGestured",b,c)}};mA=function(a){return parseInt(a.data&&a.data.loggingDirectives&&a.data.loggingDirectives.visibility&&a.data.loggingDirectives.visibility.types||"",10)||1};_.m().w("sy1s");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var pA=function(a){var b=a.label,c=a.sublabel,d=a.avatar,e=a.thumbnail,f=a.icon,g=a.secondaryIcon,h=a.focused,l=a.style,n=void 0===a.role?"menuitem":a.role;a=a.Ro;var q=!!(d||e||f),t=1===l,w=2===l;l=3===l;d&&(w?(d.size="huge",d.style="bubble",d.borderColor=h?"white":"transparent"):(d.borderColor||(d.borderColor=h?"white":"grey"),d.size="regular"));w&&!c&&(c=" ");h=!1;if("CHECK_BOX"===(null===g||void 0===g?void 0:g.iconType)||"CHECK_BOX_OUTLINE_BLANK"===(null===g||void 0===g?void 0:g.iconType))n="checkbox",
h="CHECK_BOX"===g.iconType;var y={},B={};return _.R("host",{"aria-label":_.Sx(b),className:(y["ytlr-menu-item--bubble"]=w,y["ytlr-menu-item--is-single-line"]=!a,y["ytlr-menu-item--is-padded"]=t||l,y["ytlr-menu-item--button"]=l,y),role:n,"aria-checked":h},_.Y(q,function(){return _.R("div",{className:"ytlr-menu-item__start-image"},_.Y(d,function(z){return _.R(_.hA,Object.assign({},z))}),_.Y(e,function(z){return _.R(_.Pz,{data:z,width:2})}),_.Y(f,function(z){return _.R(_.ry,{className:"ytlr-menu-item__icon",
icon:z})}))}),_.R("div",{className:["ytlr-menu-item__text",(B["ytlr-menu-item__text--has-start-image"]=q,B["ytlr-menu-item__text--has-end-image"]=g,B["ytlr-menu-item__text--has-sublabel"]=c,B)]},_.R(_.X,{className:"ytlr-menu-item__label",data:b}),_.Y(c,function(z){return _.R(_.X,{className:"ytlr-menu-item__sublabel",data:z})})),_.Y(g,function(z){return _.R("div",{className:"ytlr-menu-item__end-image"},_.R(_.ry,{className:"ytlr-menu-item__icon",icon:z}))}))};_.m().w("sy1n");
var qA=_.Xx("n7nBAb");
_.rA=function(){_.Q.apply(this,arguments);this.template=pA};_.G(_.rA,_.Q);_.rA.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.rA.prototype.onSelect=function(a){var b=this.props.command;b&&(_.U(this,"innertube-command",b),b.clickTrackingParams&&_.oA(b.clickTrackingParams),_.P(a));if(b=this.props.jp)b(),_.P(a)};_.rA.TAG_NAME="ytlr-menu-item";_.J(qA,_.rA);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy1t");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var sA;sA=function(a){var b=a.uniqueId,c=a.updateAction,d=a.replacePopup;a={popupType:void 0===a.popupType?"FULLSCREEN_OVERLAY":a.popupType,popup:{overlaySectionRenderer:a.overlaySectionRenderer}};b&&(a.uniqueId=b);c&&(a.updateAction=c);d&&(a.replacePopup=d);return{openPopupAction:a}};
_.vA=function(a){var b=a.overlayPanelRenderer,c=a.Fl,d=a.Cl,e=a.backButton,f=a.uniqueId,g=a.updateAction,h=a.popupType,l=a.replacePopup,n=void 0===a.contextualPanelRenderer?void 0:a.contextualPanelRenderer;a=(void 0===a.jb?_.tA:a.jb)||void 0;var q;void 0===e?q={buttonRenderer:{accessibilityData:{accessibilityData:{label:uA}},command:a}}:e&&(q={buttonRenderer:e});b={actionPanel:{overlayPanelRenderer:b},backButton:q};n&&(b.contextualPanel=n);n={overlay:{overlayTwoPanelRenderer:b},dismissalCommand:a};
c&&(n.onOpenCommand=c);d&&(n.onClosedCommand=d);return sA({overlaySectionRenderer:n,uniqueId:f,updateAction:g,popupType:h,replacePopup:l})};_.wA=function(a,b,c,d){b=void 0===b?_.tA:b;a={overlay:{overlayPanelRenderer:a},dismissalCommand:b};c&&(a.onOpenCommand=c);return sA({overlaySectionRenderer:a,uniqueId:d,updateAction:void 0})};
_.yA=function(a){return{openPopupAction:{popupType:"TOAST",popup:{overlayMessageRenderer:_.xA({title:a.title,subtitle:a.subtitle,image:a.image,icon:a.icon,style:void 0===a.style?"OVERLAY_MESSAGE_STYLE_TOAST":a.style})}}}};_.xA=function(a){var b=a.subtitle,c=a.image,d=a.icon,e=a.style,f=a.secondaryIcon;a={title:{simpleText:a.title}};b&&(a.subtitle={simpleText:b});e&&(a.style=e);c&&(a.image=c);d&&(a.icon=d);f&&(a.secondaryIcon=f);return a};
_.zA=function(a){var b=a.subtitle,c=a.content,d=a.icon,e=a.image,f=a.style;a={title:{simpleText:a.title}};b&&(a.subtitle={simpleText:b});d&&(a.icon=d);e&&(a.image=e);if(c&&c.length)for(a.content=[],b=0;b<c.length;b++)a.content.push({overlayMessageRenderer:c[b]});f&&(a.style=f);return a};_.AA=function(a,b,c){a={header:{overlayPanelHeaderRenderer:a}};Array.isArray(b)?a.content={overlayPanelItemListRenderer:{items:b,selectedIndex:void 0===c?0:c}}:b&&(a.content=b);return a};
_.BA=function(a,b,c){c=null!=c?"="+_.gc(c):"";return _.Of(a,b+c)};_.m().w("sy1u");
var uA;_.tA={signalAction:{signal:"POPUP_BACK"}};uA=_.V("BACK");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.CA=function(a,b){var c=b=void 0===b?{}:b;b=c.signInType;a={identityActionContext:a,signInStyle:c.signInStyle||"SIGN_IN_STYLE_FULLSCREEN"};b&&(a.signInType=b);return{startSignInCommand:a}};_.DA=function(a){return{switchToGuestMode:{identityActionContext:a}}};_.EA=function(a,b){return{selectActiveIdentityCommand:{serviceEndpoint:b,identityActionContext:a}}};_.m().w("sy1v");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.KA=function(a){_.Ha(a,"zx",_.Kf());return a};_.LA=function(a){return new _.Zg(function(b,c){c(a)})};_.MA=function(a,b,c,d){var e=new _.Ga(null,void 0);a&&_.Wi(e,a);b&&_.Xi(e,b);c&&_.Yi(e,c);d&&_.Ia(e,d);return e};_.m().w("sy1y");
/*

 Copyright The Closure Library Authors.
 SPDX-License-Identifier: Apache-2.0
*/
_.NA=function(a,b,c){_.Dh.call(this);this.A=null!=c?(0,_.je)(a,c):a;this.j=b;this.h=(0,_.je)(this.Jn,this);this.g=[]};_.ke(_.NA,_.Dh);_.k=_.NA.prototype;_.k.Ce=!1;_.k.vf=0;_.k.Pd=null;_.k.hk=function(a){this.g=arguments;this.Pd||this.vf?this.Ce=!0:OA(this)};_.k.stop=function(){this.Pd&&(_.x.clearTimeout(this.Pd),this.Pd=null,this.Ce=!1,this.g=[])};_.k.pause=function(){this.vf++};_.k.resume=function(){this.vf--;this.vf||!this.Ce||this.Pd||(this.Ce=!1,OA(this))};
_.k.aa=function(){_.NA.Ha.aa.call(this);this.stop()};_.k.Jn=function(){this.Pd=null;this.Ce&&!this.vf&&(this.Ce=!1,OA(this))};var OA=function(a){a.Pd=_.Fn(a.h,a.j);a.A.apply(null,a.g)};
var QA;_.PA=function(a){_.Dh.call(this);this.h=a;this.g={}};_.ke(_.PA,_.Dh);QA=[];_.RA=function(a,b,c,d,e,f){Array.isArray(c)||(c&&(QA[0]=c.toString()),c=QA);for(var g=0;g<c.length;g++){var h=_.qn(b,c[g],d||a.handleEvent,e||!1,f||a.h||a);if(!h)break;a.g[h.key]=h}};_.SA=function(a){_.v.forEach(a.g,function(b,c){this.g.hasOwnProperty(c)&&_.zn(b)},a);a.g={}};_.PA.prototype.aa=function(){_.PA.Ha.aa.call(this);_.SA(this)};_.PA.prototype.handleEvent=function(){throw Error("Qa");};
_.TA=function(){};_.TA.prototype.stringify=function(a){return _.x.JSON.stringify(a,void 0)};_.TA.prototype.parse=function(a){return _.x.JSON.parse(a,void 0)};

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy26");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy27");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy1z");
/*

 Copyright The Closure Library Authors.
 SPDX-License-Identifier: Apache-2.0
*/
_.UA=function(a,b,c){this.A=a;this.B=b;this.g=this.j=a;this.C=c||0};_.UA.prototype.h=0;_.UA.prototype.reset=function(){this.g=this.j=this.A;this.h=0};_.UA.prototype.getValue=function(){return this.j};_.VA=function(a){a.g=Math.min(a.B,2*a.g);a.j=Math.min(a.B,a.g+(a.C?Math.round(a.C*(Math.random()-.5)*2*a.g):0));a.h++};

_.m().l();

}catch(e){_._DumpException(e)}
try{
var WA;WA=function(a,b){function c(l){for(;d<a.length;){var n=a.charAt(d++),q=_.zk[n];if(null!=q)return q;if(!_.Qe(n))throw Error("K`"+n);}return l}_.Ak();for(var d=0;;){var e=c(-1),f=c(0),g=c(64),h=c(64);if(64===h&&-1===e)break;b(e<<2|f>>4);64!=g&&(b(f<<4&240|g>>2),64!=h&&b(g<<6&192|h))}};_.XA=function(a){var b=[];WA(a,function(c){b.push(c)});return b};
_.YA=function(a,b,c,d,e,f,g){var h=new _.Gn;_.Jn.push(h);b&&_.sn(h,"complete",b);h.h.add("ready",h.cb,!0,void 0,void 0);f&&(h.F=Math.max(0,f));g&&(h.G=g);h.send(a,c,d,e)};_.m().w("sy22");
/*

Math.uuid.js (v1.4)
http://www.broofa.com
mailto:robert@broofa.com
Copyright (c) 2010 Robert Kieffer
Dual licensed under the MIT and GPL licenses.
*/
_.ZA="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".split("");_.$A=function(){for(var a=Array(36),b=0,c,d=0;36>d;d++)8==d||13==d||18==d||23==d?a[d]="-":14==d?a[d]="4":(2>=b&&(b=33554432+16777216*Math.random()|0),c=b&15,b>>=4,a[d]=_.ZA[19==d?c&3|8:c]);return a.join("")};

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy2d");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy2g");
var aB=_.p("EventDispatcherShim","gNoojf");
var bB=function(a){a=Error.call(this,"Screen event unhandled: "+a+".");this.message=a.message;"stack"in a&&(this.stack=a.stack)};_.G(bB,Error);var cB=function(){this.j=_.L.R();this.Z=_.r.get(_.ky)};
cB.prototype.h=function(a,b){for(var c=[],d=1;d<arguments.length;++d)c[d-1]=arguments[d];switch(a){case "remote:connected":_.Ub();this.Z.tick("r_connected");_.Eo(this.j,new _.O("mdx-session:remoteConnected"),{device:c[0],wo:c[1]});break;case "remote:disconnected":_.Ub();this.Z.tick("r_disconnected");_.Eo(this.j,new _.O("mdx-session:remoteDisconnected"),{device:c[0],zo:c[1]});break;case "remote:status":this.Z.tick("r_status_update");_.Eo(this.j,new _.O("mdx-session:remoteStatusChanged"),{devices:c[0]});
break;default:throw new bB(a);}};cB.prototype.g=function(){throw Error("Ra");};_.dB=_.K(aB,function(){return new cB});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var eB=function(a,b){var c=[],d;b=b||_.ZA.length;if(a)for(d=0;d<a;d++)c[d]=_.ZA[0|Math.random()*b];else for(c[8]=c[13]=c[18]=c[23]="-",c[14]="4",d=0;36>d;d++)c[d]||(a=0|16*Math.random(),c[d]=_.ZA[19==d?a&3|8:a]);return c.join("")};_.m().w("sy1x");
/*

 Copyright The Closure Library Authors.
 SPDX-License-Identifier: Apache-2.0
*/
_.iB=function(a){this.C=a;this.h=this.C.length/4;this.j=this.h+6;this.L=[[],[],[],[]];this.A=[[],[],[],[]];this.g=Array(fB*(this.j+1));for(a=0;a<this.h;a++)this.g[a]=[this.C[4*a],this.C[4*a+1],this.C[4*a+2],this.C[4*a+3]];var b=Array(4);for(a=this.h;a<fB*(this.j+1);a++){b[0]=this.g[a-1][0];b[1]=this.g[a-1][1];b[2]=this.g[a-1][2];b[3]=this.g[a-1][3];if(0==a%this.h){var c=b,d=c[0];c[0]=c[1];c[1]=c[2];c[2]=c[3];c[3]=d;gB(b);b[0]^=hB[a/this.h][0];b[1]^=hB[a/this.h][1];b[2]^=hB[a/this.h][2];b[3]^=hB[a/
this.h][3]}else 6<this.h&&4==a%this.h&&gB(b);this.g[a]=Array(4);this.g[a][0]=this.g[a-this.h][0]^b[0];this.g[a][1]=this.g[a-this.h][1]^b[1];this.g[a][2]=this.g[a-this.h][2]^b[2];this.g[a][3]=this.g[a-this.h][3]^b[3]}};_.iB.prototype.B=16;var fB=_.iB.prototype.B/4;
_.iB.prototype.encrypt=function(a){jB(this,a);kB(this,0);for(a=1;a<this.j;++a){lB(this,mB);nB(this);for(var b=this.L,c=this.A[0],d=0;4>d;d++)c[0]=b[0][d],c[1]=b[1][d],c[2]=b[2][d],c[3]=b[3][d],b[0][d]=oB[c[0]]^pB[c[1]]^c[2]^c[3],b[1][d]=c[0]^oB[c[1]]^pB[c[2]]^c[3],b[2][d]=c[0]^c[1]^oB[c[2]]^pB[c[3]],b[3][d]=pB[c[0]]^c[1]^c[2]^oB[c[3]];kB(this,a)}lB(this,mB);nB(this);kB(this,this.j);return qB(this)};
_.iB.prototype.decrypt=function(a){jB(this,a);kB(this,this.j);for(a=1;a<this.j;++a){rB(this);lB(this,sB);kB(this,this.j-a);for(var b=this.L,c=this.A[0],d=0;4>d;d++)c[0]=b[0][d],c[1]=b[1][d],c[2]=b[2][d],c[3]=b[3][d],b[0][d]=tB[c[0]]^uB[c[1]]^vB[c[2]]^wB[c[3]],b[1][d]=wB[c[0]]^tB[c[1]]^uB[c[2]]^vB[c[3]],b[2][d]=vB[c[0]]^wB[c[1]]^tB[c[2]]^uB[c[3]],b[3][d]=uB[c[0]]^vB[c[1]]^wB[c[2]]^tB[c[3]]}rB(this);lB(this,sB);kB(this,0);return qB(this)};
var jB=function(a,b){for(var c,d=0;d<fB;d++)for(var e=0;4>e;e++)c=4*e+d,c=b[c],a.L[d][e]=c},qB=function(a){for(var b=[],c=0;c<fB;c++)for(var d=0;4>d;d++)b[4*d+c]=a.L[c][d];return b},kB=function(a,b){for(var c=0;4>c;c++)for(var d=0;4>d;d++)a.L[c][d]^=a.g[4*b+d][c]},lB=function(a,b){for(var c=0;4>c;c++)for(var d=0;4>d;d++)a.L[c][d]=b[a.L[c][d]]},nB=function(a){for(var b=1;4>b;b++)for(var c=0;4>c;c++)a.A[b][c]=a.L[b][c];for(b=1;4>b;b++)for(c=0;4>c;c++)a.L[b][c]=a.A[b][(c+b)%fB]},rB=function(a){for(var b=
1;4>b;b++)for(var c=0;4>c;c++)a.A[b][(c+b)%fB]=a.L[b][c];for(b=1;4>b;b++)for(c=0;4>c;c++)a.L[b][c]=a.A[b][c]},gB=function(a){a[0]=mB[a[0]];a[1]=mB[a[1]];a[2]=mB[a[2]];a[3]=mB[a[3]]},mB=[99,124,119,123,242,107,111,197,48,1,103,43,254,215,171,118,202,130,201,125,250,89,71,240,173,212,162,175,156,164,114,192,183,253,147,38,54,63,247,204,52,165,229,241,113,216,49,21,4,199,35,195,24,150,5,154,7,18,128,226,235,39,178,117,9,131,44,26,27,110,90,160,82,59,214,179,41,227,47,132,83,209,0,237,32,252,177,91,106,
203,190,57,74,76,88,207,208,239,170,251,67,77,51,133,69,249,2,127,80,60,159,168,81,163,64,143,146,157,56,245,188,182,218,33,16,255,243,210,205,12,19,236,95,151,68,23,196,167,126,61,100,93,25,115,96,129,79,220,34,42,144,136,70,238,184,20,222,94,11,219,224,50,58,10,73,6,36,92,194,211,172,98,145,149,228,121,231,200,55,109,141,213,78,169,108,86,244,234,101,122,174,8,186,120,37,46,28,166,180,198,232,221,116,31,75,189,139,138,112,62,181,102,72,3,246,14,97,53,87,185,134,193,29,158,225,248,152,17,105,217,
142,148,155,30,135,233,206,85,40,223,140,161,137,13,191,230,66,104,65,153,45,15,176,84,187,22],sB=[82,9,106,213,48,54,165,56,191,64,163,158,129,243,215,251,124,227,57,130,155,47,255,135,52,142,67,68,196,222,233,203,84,123,148,50,166,194,35,61,238,76,149,11,66,250,195,78,8,46,161,102,40,217,36,178,118,91,162,73,109,139,209,37,114,248,246,100,134,104,152,22,212,164,92,204,93,101,182,146,108,112,72,80,253,237,185,218,94,21,70,87,167,141,157,132,144,216,171,0,140,188,211,10,247,228,88,5,184,179,69,6,
208,44,30,143,202,63,15,2,193,175,189,3,1,19,138,107,58,145,17,65,79,103,220,234,151,242,207,206,240,180,230,115,150,172,116,34,231,173,53,133,226,249,55,232,28,117,223,110,71,241,26,113,29,41,197,137,111,183,98,14,170,24,190,27,252,86,62,75,198,210,121,32,154,219,192,254,120,205,90,244,31,221,168,51,136,7,199,49,177,18,16,89,39,128,236,95,96,81,127,169,25,181,74,13,45,229,122,159,147,201,156,239,160,224,59,77,174,42,245,176,200,235,187,60,131,83,153,97,23,43,4,126,186,119,214,38,225,105,20,99,85,
33,12,125],hB=[[0,0,0,0],[1,0,0,0],[2,0,0,0],[4,0,0,0],[8,0,0,0],[16,0,0,0],[32,0,0,0],[64,0,0,0],[128,0,0,0],[27,0,0,0],[54,0,0,0]],oB=[0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,42,44,46,48,50,52,54,56,58,60,62,64,66,68,70,72,74,76,78,80,82,84,86,88,90,92,94,96,98,100,102,104,106,108,110,112,114,116,118,120,122,124,126,128,130,132,134,136,138,140,142,144,146,148,150,152,154,156,158,160,162,164,166,168,170,172,174,176,178,180,182,184,186,188,190,192,194,196,198,200,202,204,206,208,
210,212,214,216,218,220,222,224,226,228,230,232,234,236,238,240,242,244,246,248,250,252,254,27,25,31,29,19,17,23,21,11,9,15,13,3,1,7,5,59,57,63,61,51,49,55,53,43,41,47,45,35,33,39,37,91,89,95,93,83,81,87,85,75,73,79,77,67,65,71,69,123,121,127,125,115,113,119,117,107,105,111,109,99,97,103,101,155,153,159,157,147,145,151,149,139,137,143,141,131,129,135,133,187,185,191,189,179,177,183,181,171,169,175,173,163,161,167,165,219,217,223,221,211,209,215,213,203,201,207,205,195,193,199,197,251,249,255,253,
243,241,247,245,235,233,239,237,227,225,231,229],pB=[0,3,6,5,12,15,10,9,24,27,30,29,20,23,18,17,48,51,54,53,60,63,58,57,40,43,46,45,36,39,34,33,96,99,102,101,108,111,106,105,120,123,126,125,116,119,114,113,80,83,86,85,92,95,90,89,72,75,78,77,68,71,66,65,192,195,198,197,204,207,202,201,216,219,222,221,212,215,210,209,240,243,246,245,252,255,250,249,232,235,238,237,228,231,226,225,160,163,166,165,172,175,170,169,184,187,190,189,180,183,178,177,144,147,150,149,156,159,154,153,136,139,142,141,132,135,
130,129,155,152,157,158,151,148,145,146,131,128,133,134,143,140,137,138,171,168,173,174,167,164,161,162,179,176,181,182,191,188,185,186,251,248,253,254,247,244,241,242,227,224,229,230,239,236,233,234,203,200,205,206,199,196,193,194,211,208,213,214,223,220,217,218,91,88,93,94,87,84,81,82,67,64,69,70,79,76,73,74,107,104,109,110,103,100,97,98,115,112,117,118,127,124,121,122,59,56,61,62,55,52,49,50,35,32,37,38,47,44,41,42,11,8,13,14,7,4,1,2,19,16,21,22,31,28,25,26],wB=[0,9,18,27,36,45,54,63,72,65,90,
83,108,101,126,119,144,153,130,139,180,189,166,175,216,209,202,195,252,245,238,231,59,50,41,32,31,22,13,4,115,122,97,104,87,94,69,76,171,162,185,176,143,134,157,148,227,234,241,248,199,206,213,220,118,127,100,109,82,91,64,73,62,55,44,37,26,19,8,1,230,239,244,253,194,203,208,217,174,167,188,181,138,131,152,145,77,68,95,86,105,96,123,114,5,12,23,30,33,40,51,58,221,212,207,198,249,240,235,226,149,156,135,142,177,184,163,170,236,229,254,247,200,193,218,211,164,173,182,191,128,137,146,155,124,117,110,
103,88,81,74,67,52,61,38,47,16,25,2,11,215,222,197,204,243,250,225,232,159,150,141,132,187,178,169,160,71,78,85,92,99,106,113,120,15,6,29,20,43,34,57,48,154,147,136,129,190,183,172,165,210,219,192,201,246,255,228,237,10,3,24,17,46,39,60,53,66,75,80,89,102,111,116,125,161,168,179,186,133,140,151,158,233,224,251,242,205,196,223,214,49,56,35,42,21,28,7,14,121,112,107,98,93,84,79,70],uB=[0,11,22,29,44,39,58,49,88,83,78,69,116,127,98,105,176,187,166,173,156,151,138,129,232,227,254,245,196,207,210,217,
123,112,109,102,87,92,65,74,35,40,53,62,15,4,25,18,203,192,221,214,231,236,241,250,147,152,133,142,191,180,169,162,246,253,224,235,218,209,204,199,174,165,184,179,130,137,148,159,70,77,80,91,106,97,124,119,30,21,8,3,50,57,36,47,141,134,155,144,161,170,183,188,213,222,195,200,249,242,239,228,61,54,43,32,17,26,7,12,101,110,115,120,73,66,95,84,247,252,225,234,219,208,205,198,175,164,185,178,131,136,149,158,71,76,81,90,107,96,125,118,31,20,9,2,51,56,37,46,140,135,154,145,160,171,182,189,212,223,194,201,
248,243,238,229,60,55,42,33,16,27,6,13,100,111,114,121,72,67,94,85,1,10,23,28,45,38,59,48,89,82,79,68,117,126,99,104,177,186,167,172,157,150,139,128,233,226,255,244,197,206,211,216,122,113,108,103,86,93,64,75,34,41,52,63,14,5,24,19,202,193,220,215,230,237,240,251,146,153,132,143,190,181,168,163],vB=[0,13,26,23,52,57,46,35,104,101,114,127,92,81,70,75,208,221,202,199,228,233,254,243,184,181,162,175,140,129,150,155,187,182,161,172,143,130,149,152,211,222,201,196,231,234,253,240,107,102,113,124,95,82,
69,72,3,14,25,20,55,58,45,32,109,96,119,122,89,84,67,78,5,8,31,18,49,60,43,38,189,176,167,170,137,132,147,158,213,216,207,194,225,236,251,246,214,219,204,193,226,239,248,245,190,179,164,169,138,135,144,157,6,11,28,17,50,63,40,37,110,99,116,121,90,87,64,77,218,215,192,205,238,227,244,249,178,191,168,165,134,139,156,145,10,7,16,29,62,51,36,41,98,111,120,117,86,91,76,65,97,108,123,118,85,88,79,66,9,4,19,30,61,48,39,42,177,188,171,166,133,136,159,146,217,212,195,206,237,224,247,250,183,186,173,160,131,
142,153,148,223,210,197,200,235,230,241,252,103,106,125,112,83,94,73,68,15,2,21,24,59,54,33,44,12,1,22,27,56,53,34,47,100,105,126,115,80,93,74,71,220,209,198,203,232,229,242,255,180,185,174,163,128,141,154,151],tB=[0,14,28,18,56,54,36,42,112,126,108,98,72,70,84,90,224,238,252,242,216,214,196,202,144,158,140,130,168,166,180,186,219,213,199,201,227,237,255,241,171,165,183,185,147,157,143,129,59,53,39,41,3,13,31,17,75,69,87,89,115,125,111,97,173,163,177,191,149,155,137,135,221,211,193,207,229,235,249,
247,77,67,81,95,117,123,105,103,61,51,33,47,5,11,25,23,118,120,106,100,78,64,82,92,6,8,26,20,62,48,34,44,150,152,138,132,174,160,178,188,230,232,250,244,222,208,194,204,65,79,93,83,121,119,101,107,49,63,45,35,9,7,21,27,161,175,189,179,153,151,133,139,209,223,205,195,233,231,245,251,154,148,134,136,162,172,190,176,234,228,246,248,210,220,206,192,122,116,102,104,66,76,94,80,10,4,22,24,50,60,46,32,236,226,240,254,212,218,200,198,156,146,128,142,164,170,184,182,12,2,16,30,52,58,40,38,124,114,96,110,68,
74,88,86,55,57,43,37,15,1,19,29,71,73,91,85,127,113,99,109,215,217,203,197,239,225,243,253,167,169,187,181,159,145,131,141];
var yB,zB,DB,EB,GB,MB,IB,KB,NB,OB,JB,QB;_.xB=function(a,b){this.g=b;for(var c=[],d=!0,e=a.length-1;0<=e;e--){var f=a[e]|0;d&&f==b||(c[e]=f,d=!1)}this.h=c};yB={};zB=function(a){return-128<=a&&128>a?_.bg(yB,a,function(b){return new _.xB([b|0],0>b?-1:0)}):new _.xB([a|0],0>a?-1:0)};_.CB=function(a){if(isNaN(a)||!isFinite(a))return _.AB;if(0>a)return _.BB(_.CB(-a));for(var b=[],c=1,d=0;a>=c;d++)b[d]=a/c|0,c*=4294967296;return new _.xB(b,0)};_.AB=zB(0);DB=zB(1);EB=zB(16777216);
_.FB=function(a){return 0<a.h.length?a.h[0]:a.g};GB=function(a){if(-1==a.g)return-GB(_.BB(a));for(var b=0,c=1,d=0;d<a.h.length;d++)b+=_.HB(a,d)*c,c*=4294967296;return b};_.xB.prototype.toString=function(a){a=a||10;if(2>a||36<a)throw Error("Ta`"+a);if(IB(this))return"0";if(-1==this.g)return"-"+_.BB(this).toString(a);for(var b=_.CB(Math.pow(a,6)),c=this,d="";;){var e=JB(c,b).g,f=(_.FB(KB(c,_.LB(e,b)))>>>0).toString(a);c=e;if(IB(c))return f+d;for(;6>f.length;)f="0"+f;d=f+d}};
MB=function(a,b){return 0>b?0:b<a.h.length?a.h[b]:a.g};_.HB=function(a,b){a=MB(a,b);return 0<=a?a:4294967296+a};IB=function(a){if(0!=a.g)return!1;for(var b=0;b<a.h.length;b++)if(0!=a.h[b])return!1;return!0};_.xB.prototype.equals=function(a){if(this.g!=a.g)return!1;for(var b=Math.max(this.h.length,a.h.length),c=0;c<b;c++)if(MB(this,c)!=MB(a,c))return!1;return!0};_.xB.prototype.compare=function(a){a=KB(this,a);return-1==a.g?-1:IB(a)?0:1};_.BB=function(a){return a.not().add(DB)};
_.xB.prototype.abs=function(){return-1==this.g?_.BB(this):this};_.xB.prototype.add=function(a){for(var b=Math.max(this.h.length,a.h.length),c=[],d=0,e=0;e<=b;e++){var f=d+(MB(this,e)&65535)+(MB(a,e)&65535),g=(f>>>16)+(MB(this,e)>>>16)+(MB(a,e)>>>16);d=g>>>16;f&=65535;g&=65535;c[e]=g<<16|f}return new _.xB(c,c[c.length-1]&-2147483648?-1:0)};KB=function(a,b){return a.add(_.BB(b))};
_.LB=function(a,b){if(IB(a)||IB(b))return _.AB;if(-1==a.g)return-1==b.g?_.LB(_.BB(a),_.BB(b)):_.BB(_.LB(_.BB(a),b));if(-1==b.g)return _.BB(_.LB(a,_.BB(b)));if(0>a.compare(EB)&&0>b.compare(EB))return _.CB(GB(a)*GB(b));for(var c=a.h.length+b.h.length,d=[],e=0;e<2*c;e++)d[e]=0;for(e=0;e<a.h.length;e++)for(var f=0;f<b.h.length;f++){var g=MB(a,e)>>>16,h=MB(a,e)&65535,l=MB(b,f)>>>16,n=MB(b,f)&65535;d[2*e+2*f]+=h*n;NB(d,2*e+2*f);d[2*e+2*f+1]+=g*n;NB(d,2*e+2*f+1);d[2*e+2*f+1]+=h*l;NB(d,2*e+2*f+1);d[2*e+2*
f+2]+=g*l;NB(d,2*e+2*f+2)}for(e=0;e<c;e++)d[e]=d[2*e+1]<<16|d[2*e];for(e=c;e<2*c;e++)d[e]=0;return new _.xB(d,0)};NB=function(a,b){for(;(a[b]&65535)!=a[b];)a[b+1]+=a[b]>>>16,a[b]&=65535,b++};OB=function(a,b){this.g=a;this.h=b};
JB=function(a,b){if(IB(b))throw Error("Wa");if(IB(a))return new OB(_.AB,_.AB);if(-1==a.g)return b=JB(_.BB(a),b),new OB(_.BB(b.g),_.BB(b.h));if(-1==b.g)return b=JB(a,_.BB(b)),new OB(_.BB(b.g),b.h);if(30<a.h.length){if(-1==a.g||-1==b.g)throw Error("Va");for(var c=DB,d=b;0>=d.compare(a);)c=_.PB(c,1),d=_.PB(d,1);var e=QB(c,1),f=QB(d,1);d=QB(d,2);for(c=QB(c,2);!IB(d);){var g=f.add(d);0>=g.compare(a)&&(e=e.add(c),f=g);d=QB(d,1);c=QB(c,1)}b=KB(a,_.LB(e,b));return new OB(e,b)}for(e=_.AB;0<=a.compare(b);){c=
Math.max(1,Math.floor(GB(a)/GB(b)));d=Math.ceil(Math.log(c)/Math.LN2);d=48>=d?1:Math.pow(2,d-48);f=_.CB(c);for(g=_.LB(f,b);-1==g.g||0<g.compare(a);)c-=d,f=_.CB(c),g=_.LB(f,b);IB(f)&&(f=DB);e=e.add(f);a=KB(a,g)}return new OB(e,a)};_.xB.prototype.not=function(){for(var a=this.h.length,b=[],c=0;c<a;c++)b[c]=~this.h[c];return new _.xB(b,~this.g)};_.xB.prototype.and=function(a){for(var b=Math.max(this.h.length,a.h.length),c=[],d=0;d<b;d++)c[d]=MB(this,d)&MB(a,d);return new _.xB(c,this.g&a.g)};
_.xB.prototype.or=function(a){for(var b=Math.max(this.h.length,a.h.length),c=[],d=0;d<b;d++)c[d]=MB(this,d)|MB(a,d);return new _.xB(c,this.g|a.g)};_.xB.prototype.xor=function(a){for(var b=Math.max(this.h.length,a.h.length),c=[],d=0;d<b;d++)c[d]=MB(this,d)^MB(a,d);return new _.xB(c,this.g^a.g)};_.PB=function(a,b){var c=b>>5;b%=32;for(var d=a.h.length+c+(0<b?1:0),e=[],f=0;f<d;f++)e[f]=0<b?MB(a,f-c)<<b|MB(a,f-c-1)>>>32-b:MB(a,f-c);return new _.xB(e,a.g)};
QB=function(a,b){var c=b>>5;b%=32;for(var d=a.h.length-c,e=[],f=0;f<d;f++)e[f]=0<b?MB(a,f+c)>>>b|MB(a,f+c+1)<<32-b:MB(a,f+c);return new _.xB(e,a.g)};
_.RB=new _.Dn;
_.SB=KB(_.PB(DB,32),DB);_.TB=zB(65535);_.UB=KB(_.PB(DB,128),DB);
var YB;_.WB=function(a){this.j=a||null;this.h=new _.ri;this.g=new VB("",void 0);this.g.next=this.g.g=this.g};YB=function(a,b){if(b=a.h.get(b))b.remove(),XB(a,b);return b};_.k=_.WB.prototype;_.k.get=function(a,b){return(a=YB(this,a))?a.value:b};_.k.set=function(a,b){var c=YB(this,a);c?c.value=b:(c=new VB(a,b),this.h.set(a,c),XB(this,c))};_.k.Va=function(){return this.g.next.value};_.k.shift=function(){return ZB(this,this.g.next)};_.k.pop=function(){return ZB(this,this.g.g)};
_.k.remove=function(a){return(a=this.h.get(a))?(a.remove(),this.h.remove(a.key),!0):!1};_.k.hb=function(){return this.h.hb()};_.k.isEmpty=function(){return this.h.isEmpty()};_.k.La=function(){return this.map(function(a,b){return b})};_.k.la=function(){return this.map(function(a){return a})};_.k.contains=function(a){return this.some(function(b){return b==a})};_.k.wa=function(a){return this.h.wa(a)};_.k.clear=function(){$B(this,0)};
_.k.forEach=function(a,b){for(var c=this.g.next;c!=this.g;c=c.next)a.call(b,c.value,c.key,this)};_.k.map=function(a,b){for(var c=[],d=this.g.next;d!=this.g;d=d.next)c.push(a.call(b,d.value,d.key,this));return c};_.k.some=function(a,b){for(var c=this.g.next;c!=this.g;c=c.next)if(a.call(b,c.value,c.key,this))return!0;return!1};_.k.every=function(a,b){for(var c=this.g.next;c!=this.g;c=c.next)if(!a.call(b,c.value,c.key,this))return!1;return!0};
var XB=function(a,b){b.next=a.g.next;b.g=a.g;a.g.next=b;b.next.g=b;null!=a.j&&$B(a,a.j)},$B=function(a,b){for(;a.hb()>b;){var c=a,d=a.g.g;d.remove();c.h.remove(d.key)}},ZB=function(a,b){a.g!=b&&(b.remove(),a.h.remove(b.key));return b.value},VB=function(a,b){this.key=a;this.value=b};VB.prototype.remove=function(){this.g.next=this.next;this.next.g=this.g;delete this.g;delete this.next};
_.aC={"default":0,monoSerif:1,propSerif:2,monoSans:3,propSans:4,casual:5,cursive:6,smallCaps:7};_.bC=Object.keys(_.aC).reduce(function(a,b){a[_.aC[b]]=b;return a},{});_.cC={none:0,raised:1,depressed:2,uniform:3,dropShadow:4};Object.keys(_.cC).reduce(function(a,b){a[_.cC[b]]=b;return a},{});_.dC={normal:0,bold:1,italic:2,bold_italic:3};Object.keys(_.dC).reduce(function(a,b){a[_.dC[b]]=b;return a},{});
var eC;eC={};_.fC=(eC.auto=0,eC.tiny=144,eC.light=144,eC.small=240,eC.medium=360,eC.large=480,eC.hd720=720,eC.hd1080=1080,eC.hd1440=1440,eC.hd2160=2160,eC.hd2880=2880,eC.highres=4320,eC);
_.mC=function(){var a=Math.random;this.g=eB;this.h=a};_.mC[_.Ji]=["mdxGetRandomString","getRandom"];
var CC=_.p("MdxSessionFactory","hxoFv");
var RC;_.QC=new _.Dl("mdx-device-id");RC=function(){this.j=null;this.h=_.r.get(_.vd);this.A=_.r.get(_.dB);this.Z=_.r.get(_.ky);this.g=null};RC.prototype.init=function(a){this.j=a};_.SC=_.K(CC,function(){return new RC});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.TC=function(a,b){return a?a.map(function(c,d){return b(c,d)}):[]};_.m().w("sy2n");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var VC=function(a){var b=this,c=this.h(this.offset,this.sizes,this.sa,this.props.size);return _.R("host",{style:this.C},_.R("div",{style:this.B},_.TC(a.items,function(d,e){var f=e>=c.start&&e<c.end;return _.R("div",{className:["yt-chip-list__item",f?"yt-chip-list__item--visible":"zylon-hidden"],style:b.itemStyle},a.Fa({item:d,index:e,selected:e===UC(b,a.selectedIndex),visibility:f?2:0}))})))};_.m().w("sy20");
/*

 Copyright The Closure Library Authors.
 SPDX-License-Identifier: Apache-2.0
*/
var WC=function(a,b){this.start=a<b?a:b;this.end=a<b?b:a};WC.prototype.clone=function(){return new WC(this.start,this.end)};
_.XC=function(a){_.Q.call(this,a);this.template=VC;this.h=_.wx(function(b,c,d,e){for(var f=0,g=0;g<d.length;g++)if(d[g]+c[g]>b){f=g;break}c=0;for(g=f;g<d.length;g++)if(d[g]<b+e)c=g;else break;return new WC(Math.max(0,f-1),Math.min(c+1,d.length))});this.j=_.wx(function(b,c,d,e,f,g,h,l){return b({items:c,sa:e,ea:h||0,gb:l||0,selectedIndex:f,size:g,xa:function(n,q){return d[q]}})});this.D=_.wx(function(b,c,d){var e=Array(b.length);e[0]=d||0;for(d=1;d<b.length;d++)e[d]=e[d-1]+c[d-1];return e});this.A=
_.wx(function(b,c){return Array.from(c.children).map(function(d){var e=window.getComputedStyle(d).marginRight;return _.Ux(d.offsetWidth+Number(e.replace(/(\d+.?\d*)px/,"$1")))})});this.state={items:a.items,stage:0===a.selectedIndex?1:0}};_.G(_.XC,_.Q);_.XC.getDerivedStateFromProps=function(a,b){return b.items!==a.items?{items:a.items,stage:0===a.selectedIndex?1:0}:b};_.XC.prototype.O=function(){this.g("keydown",this.onKeyDown.bind(this));YC(this)};_.XC.prototype.W=function(){YC(this)};
var YC=function(a){switch(a.state.stage){case 0:_.Mt({stage:40,X:function(){_.S(a,{stage:1})}});break;case 1:_.Mt({stage:40,X:function(){_.S(a,{stage:2})}});break;case 2:var b=a.h(a.offset,a.sizes,a.sa,a.props.size);_.U(a,"yt-chip-list:visibility-changed",a.props.items.slice(b.start,b.end))}};
_.XC.prototype.onKeyDown=function(a){var b=ZC[a.keyCode];if("number"===typeof b){var c=UC(this,this.props.selectedIndex+b);c===this.props.selectedIndex?0<b&&_.U(this,"yt-chip-list:right-edge"):(b=$C(this,UC(this,c)),this.props.M(c,b),_.bw(a))}};var UC=function(a,b){return _.Ut(b,0,a.props.items.length-1)},$C=function(a,b){if(!a.props||0===a.sizes.length)return 0;var c=a.props;return a.j(c.alignment,c.items,a.sizes,a.sa,b,c.size,c.ea,c.gb)};
_.E.Object.defineProperties(_.XC.prototype,{isAnimated:{configurable:!0,enumerable:!0,get:function(){return!!_.D("enableAnimations",!0)}},isRtl:{configurable:!0,enumerable:!0,get:function(){return!!_.D("isRtl",!1)}},offset:{configurable:!0,enumerable:!0,get:function(){return $C(this,UC(this,this.props.selectedIndex))}},sa:{configurable:!0,enumerable:!0,get:function(){return this.props&&0!==this.sizes.length?this.D(this.props.items,this.sizes,this.props.ea):[]}},sizes:{configurable:!0,enumerable:!0,
get:function(){var a=this.el.children[0];return this.props&&a?this.A(this.props.items,a):[]}},C:{configurable:!0,enumerable:!0,get:function(){var a=this.props.ea||0,b=this.props.gb||0,c={position:"relative",width:this.props.size-a-b+"rem"};this.isRtl?(c.paddingLeft=b+"rem",c.paddingRight=a+"rem"):(c.paddingLeft=a+"rem",c.paddingRight=b+"rem");return c}},B:{configurable:!0,enumerable:!0,get:function(){var a={position:"absolute",whiteSpace:"nowrap"};this.props.items.length||(a.display="none");var b=
0;0===this.state.stage?a.opacity="0":(a.opacity="1",b=this.isRtl?this.offset:-this.offset);this.isAnimated?(a.transform="translateX("+b+"rem) translateZ(0)",2===this.state.stage&&(a.transition="transform 150ms linear")):a.marginLeft=b+"rem";return a}},itemStyle:{configurable:!0,enumerable:!0,get:function(){var a={display:"inline-block"};a[this.isRtl?"marginLeft":"marginRight"]=(this.props.spacing||0)+"rem";return a}}});_.XC.TAG_NAME="yt-chip-list";
var aD={},ZC=(aD[177]=-1,aD[37]=-1,aD[39]=1,aD[176]=1,aD);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy28");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("Px5Yxe");
var bD=_.p("DialCapabilities","Px5Yxe");
var dD,cD;dD=function(){var a=window.location;this.g=_.r.get(_.Oa).dial;this.j=cD(a);this.g.isInAppServer()?a=2:(a=0,this.j&&(a=_.D("supportsDialAdditionalData",!1)?2:1));this.h=a};cD=function(a){a=(new _.Ga(a.search)).g.la("additionalDataUrl")[0];if(!a)return null;var b=/^https?:\/\/(localhost|127\.0\.0\.1)(:\d+)?\//;if(b.test(a))return a;a=decodeURIComponent(a);if(b.test(a))return a;console.warn("Given additionalDataUrl with non-localhost domain: "+a);return null};_.eD=_.K(bD,function(){return new dD});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var hD,jD,lD,mD,oD,rD,tD,wD,xD,yD,AD,BD,CD,DD,GD,HD,XE,YE,ZE,$E,aF,bF,iF,jF,kF,mF,nF,oF,pF,qF,rF,XF,YF,$F;hD=function(a,b,c,d){return new fD(function(e,f){return new gD(e,f,!1)},a,b,c,d)};jD=function(a,b,c){return new iD(a,b,c)};lD=function(){return new kD};mD=function(a){return _.Qb(a)?(_.hc(a)?_.pe:_.v.map)(a,mD):String(a)};oD=function(){return new nD({Km:!1})};
rD=function(a){return new pD(function(b){return new qD(new _.iB(b))},function(){for(var b=[],c=0;16>c;c++)b.push(Math.floor(256*Math.random()));return b},a)};tD=function(a,b,c,d,e){return new sD(a,b,c,d,e)};_.uD=function(){return"xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g,function(a){var b=16*Math.random()|0;return("x"===a?b:b&3|8).toString(16)})};_.vD=function(a,b){return a.g.g("value-changed",function(c){c&&b()})};
wD=function(a,b){b=b instanceof _.gf?b:_.nf(b,/^data:image\//i.test(b));a.src=_.hf(b)};xD=function(a){return Array.prototype.concat.apply([],arguments)};yD=function(a,b){for(var c=[],d=0;d<b;d++)c[d]=a;return c};_.zD=function(a,b){a=String(a).toLowerCase();b=String(b).toLowerCase();return a<b?-1:a==b?0:1};AD={"\x00":"\\0","\b":"\\b","\f":"\\f","\n":"\\n","\r":"\\r","\t":"\\t","\x0B":"\\x0B",'"':'\\"',"\\":"\\\\","<":"\\u003C"};BD={"'":"\\'"};
CD={Tq:"atp",UB:"ska",mB:"que",BA:"mus",TB:"sus",nv:"dsp",CB:"seq"};DD=function(a){return(0,_.pe)(a,function(b){b=b.toString(16);return 1<b.length?b:"0"+b}).join("")};_.ED=function(a){for(var b=[],c=0,d=0;d<a.length;d++){var e=a.charCodeAt(d);128>e?b[c++]=e:(2048>e?b[c++]=e>>6|192:(55296==(e&64512)&&d+1<a.length&&56320==(a.charCodeAt(d+1)&64512)?(e=65536+((e&1023)<<10)+(a.charCodeAt(++d)&1023),b[c++]=e>>18|240,b[c++]=e>>12&63|128):b[c++]=e>>12|224,b[c++]=e>>6&63|128),b[c++]=e&63|128)}return b};
_.FD=function(a){for(var b=[],c=0,d=0;c<a.length;){var e=a[c++];if(128>e)b[d++]=String.fromCharCode(e);else if(191<e&&224>e){var f=a[c++];b[d++]=String.fromCharCode((e&31)<<6|f&63)}else if(239<e&&365>e){f=a[c++];var g=a[c++],h=a[c++];e=((e&7)<<18|(f&63)<<12|(g&63)<<6|h&63)-65536;b[d++]=String.fromCharCode(55296+(e>>10));b[d++]=String.fromCharCode(56320+(e&1023))}else f=a[c++],g=a[c++],b[d++]=String.fromCharCode((e&15)<<12|(f&63)<<6|g&63)}return b.join("")};
GD=function(a,b){for(var c=[],d=0;d<a.length;d++)c.push(a[d]^b[d]);return c};HD=function(a,b,c){_.Dh.call(this);this.g=a;this.A=b||0;this.h=c;this.j=(0,_.je)(this.In,this)};_.ke(HD,_.Dh);_.k=HD.prototype;_.k.uf=0;_.k.aa=function(){HD.Ha.aa.call(this);this.stop();delete this.g;delete this.h};_.k.start=function(a){this.stop();this.uf=_.Fn(this.j,void 0!==a?a:this.A)};_.k.stop=function(){this.isActive()&&_.x.clearTimeout(this.uf);this.uf=0};_.k.isActive=function(){return 0!=this.uf};
_.k.In=function(){this.uf=0;this.g&&this.g.call(this.h)};var iD=function(a,b,c){_.Dh.call(this);this.G=null!=c?(0,_.je)(a,c):a;this.F=b;this.C=(0,_.je)(this.Hn,this);this.h=!1;this.j=0;this.A=this.g=null;this.B=[]};_.ke(iD,_.Dh);_.k=iD.prototype;_.k.Gn=function(a){this.B=arguments;this.h=!1;this.g?this.A=(0,_.Sb)()+this.F:this.g=_.Fn(this.C,this.F)};_.k.stop=function(){this.g&&(_.x.clearTimeout(this.g),this.g=null);this.A=null;this.h=!1;this.B=[]};_.k.pause=function(){++this.j};
_.k.resume=function(){this.j&&(--this.j,!this.j&&this.h&&(this.h=!1,this.G.apply(null,this.B)))};_.k.aa=function(){this.stop();iD.Ha.aa.call(this)};_.k.Hn=function(){this.A?(this.g=_.Fn(this.C,this.A-(0,_.Sb)()),this.A=null):(this.g=null,this.j?this.h=!0:(this.h=!1,this.G.apply(null,this.B)))};var qD=function(a){this.g=a};qD.prototype.encrypt=function(a,b){var c=[],d=b;for(b=0;b<a.length;b+=this.g.B)d=GD(_.Tt(a,b,b+this.g.B),d),d=this.g.encrypt(d),_.lc(c,d);return c};
qD.prototype.decrypt=function(a,b){for(var c=[],d=0,e=b;d<a.length;){b=_.Tt(a,d,d+this.g.B);var f=this.g.decrypt(b);e=GD(e,f);_.lc(c,e);e=b;d+=this.g.B}return c};
var ID=function(a,b,c){c||(c=0);var d=Array(16);if("string"===typeof b)for(var e=0;16>e;++e)d[e]=b.charCodeAt(c++)|b.charCodeAt(c++)<<8|b.charCodeAt(c++)<<16|b.charCodeAt(c++)<<24;else for(e=0;16>e;++e)d[e]=b[c++]|b[c++]<<8|b[c++]<<16|b[c++]<<24;b=a.g[0];c=a.g[1];e=a.g[2];var f=a.g[3];var g=b+(f^c&(e^f))+d[0]+3614090360&4294967295;b=c+(g<<7&4294967295|g>>>25);g=f+(e^b&(c^e))+d[1]+3905402710&4294967295;f=b+(g<<12&4294967295|g>>>20);g=e+(c^f&(b^c))+d[2]+606105819&4294967295;e=f+(g<<17&4294967295|g>>>
15);g=c+(b^e&(f^b))+d[3]+3250441966&4294967295;c=e+(g<<22&4294967295|g>>>10);g=b+(f^c&(e^f))+d[4]+4118548399&4294967295;b=c+(g<<7&4294967295|g>>>25);g=f+(e^b&(c^e))+d[5]+1200080426&4294967295;f=b+(g<<12&4294967295|g>>>20);g=e+(c^f&(b^c))+d[6]+2821735955&4294967295;e=f+(g<<17&4294967295|g>>>15);g=c+(b^e&(f^b))+d[7]+4249261313&4294967295;c=e+(g<<22&4294967295|g>>>10);g=b+(f^c&(e^f))+d[8]+1770035416&4294967295;b=c+(g<<7&4294967295|g>>>25);g=f+(e^b&(c^e))+d[9]+2336552879&4294967295;f=b+(g<<12&4294967295|
g>>>20);g=e+(c^f&(b^c))+d[10]+4294925233&4294967295;e=f+(g<<17&4294967295|g>>>15);g=c+(b^e&(f^b))+d[11]+2304563134&4294967295;c=e+(g<<22&4294967295|g>>>10);g=b+(f^c&(e^f))+d[12]+1804603682&4294967295;b=c+(g<<7&4294967295|g>>>25);g=f+(e^b&(c^e))+d[13]+4254626195&4294967295;f=b+(g<<12&4294967295|g>>>20);g=e+(c^f&(b^c))+d[14]+2792965006&4294967295;e=f+(g<<17&4294967295|g>>>15);g=c+(b^e&(f^b))+d[15]+1236535329&4294967295;c=e+(g<<22&4294967295|g>>>10);g=b+(e^f&(c^e))+d[1]+4129170786&4294967295;b=c+(g<<
5&4294967295|g>>>27);g=f+(c^e&(b^c))+d[6]+3225465664&4294967295;f=b+(g<<9&4294967295|g>>>23);g=e+(b^c&(f^b))+d[11]+643717713&4294967295;e=f+(g<<14&4294967295|g>>>18);g=c+(f^b&(e^f))+d[0]+3921069994&4294967295;c=e+(g<<20&4294967295|g>>>12);g=b+(e^f&(c^e))+d[5]+3593408605&4294967295;b=c+(g<<5&4294967295|g>>>27);g=f+(c^e&(b^c))+d[10]+38016083&4294967295;f=b+(g<<9&4294967295|g>>>23);g=e+(b^c&(f^b))+d[15]+3634488961&4294967295;e=f+(g<<14&4294967295|g>>>18);g=c+(f^b&(e^f))+d[4]+3889429448&4294967295;c=
e+(g<<20&4294967295|g>>>12);g=b+(e^f&(c^e))+d[9]+568446438&4294967295;b=c+(g<<5&4294967295|g>>>27);g=f+(c^e&(b^c))+d[14]+3275163606&4294967295;f=b+(g<<9&4294967295|g>>>23);g=e+(b^c&(f^b))+d[3]+4107603335&4294967295;e=f+(g<<14&4294967295|g>>>18);g=c+(f^b&(e^f))+d[8]+1163531501&4294967295;c=e+(g<<20&4294967295|g>>>12);g=b+(e^f&(c^e))+d[13]+2850285829&4294967295;b=c+(g<<5&4294967295|g>>>27);g=f+(c^e&(b^c))+d[2]+4243563512&4294967295;f=b+(g<<9&4294967295|g>>>23);g=e+(b^c&(f^b))+d[7]+1735328473&4294967295;
e=f+(g<<14&4294967295|g>>>18);g=c+(f^b&(e^f))+d[12]+2368359562&4294967295;c=e+(g<<20&4294967295|g>>>12);g=b+(c^e^f)+d[5]+4294588738&4294967295;b=c+(g<<4&4294967295|g>>>28);g=f+(b^c^e)+d[8]+2272392833&4294967295;f=b+(g<<11&4294967295|g>>>21);g=e+(f^b^c)+d[11]+1839030562&4294967295;e=f+(g<<16&4294967295|g>>>16);g=c+(e^f^b)+d[14]+4259657740&4294967295;c=e+(g<<23&4294967295|g>>>9);g=b+(c^e^f)+d[1]+2763975236&4294967295;b=c+(g<<4&4294967295|g>>>28);g=f+(b^c^e)+d[4]+1272893353&4294967295;f=b+(g<<11&4294967295|
g>>>21);g=e+(f^b^c)+d[7]+4139469664&4294967295;e=f+(g<<16&4294967295|g>>>16);g=c+(e^f^b)+d[10]+3200236656&4294967295;c=e+(g<<23&4294967295|g>>>9);g=b+(c^e^f)+d[13]+681279174&4294967295;b=c+(g<<4&4294967295|g>>>28);g=f+(b^c^e)+d[0]+3936430074&4294967295;f=b+(g<<11&4294967295|g>>>21);g=e+(f^b^c)+d[3]+3572445317&4294967295;e=f+(g<<16&4294967295|g>>>16);g=c+(e^f^b)+d[6]+76029189&4294967295;c=e+(g<<23&4294967295|g>>>9);g=b+(c^e^f)+d[9]+3654602809&4294967295;b=c+(g<<4&4294967295|g>>>28);g=f+(b^c^e)+d[12]+
3873151461&4294967295;f=b+(g<<11&4294967295|g>>>21);g=e+(f^b^c)+d[15]+530742520&4294967295;e=f+(g<<16&4294967295|g>>>16);g=c+(e^f^b)+d[2]+3299628645&4294967295;c=e+(g<<23&4294967295|g>>>9);g=b+(e^(c|~f))+d[0]+4096336452&4294967295;b=c+(g<<6&4294967295|g>>>26);g=f+(c^(b|~e))+d[7]+1126891415&4294967295;f=b+(g<<10&4294967295|g>>>22);g=e+(b^(f|~c))+d[14]+2878612391&4294967295;e=f+(g<<15&4294967295|g>>>17);g=c+(f^(e|~b))+d[5]+4237533241&4294967295;c=e+(g<<21&4294967295|g>>>11);g=b+(e^(c|~f))+d[12]+1700485571&
4294967295;b=c+(g<<6&4294967295|g>>>26);g=f+(c^(b|~e))+d[3]+2399980690&4294967295;f=b+(g<<10&4294967295|g>>>22);g=e+(b^(f|~c))+d[10]+4293915773&4294967295;e=f+(g<<15&4294967295|g>>>17);g=c+(f^(e|~b))+d[1]+2240044497&4294967295;c=e+(g<<21&4294967295|g>>>11);g=b+(e^(c|~f))+d[8]+1873313359&4294967295;b=c+(g<<6&4294967295|g>>>26);g=f+(c^(b|~e))+d[15]+4264355552&4294967295;f=b+(g<<10&4294967295|g>>>22);g=e+(b^(f|~c))+d[6]+2734768916&4294967295;e=f+(g<<15&4294967295|g>>>17);g=c+(f^(e|~b))+d[13]+1309151649&
4294967295;c=e+(g<<21&4294967295|g>>>11);g=b+(e^(c|~f))+d[4]+4149444226&4294967295;b=c+(g<<6&4294967295|g>>>26);g=f+(c^(b|~e))+d[11]+3174756917&4294967295;f=b+(g<<10&4294967295|g>>>22);g=e+(b^(f|~c))+d[2]+718787259&4294967295;e=f+(g<<15&4294967295|g>>>17);g=c+(f^(e|~b))+d[9]+3951481745&4294967295;a.g[0]=a.g[0]+b&4294967295;a.g[1]=a.g[1]+(e+(g<<21&4294967295|g>>>11))&4294967295;a.g[2]=a.g[2]+e&4294967295;a.g[3]=a.g[3]+f&4294967295},JD=function(){this.blockSize=-1;this.blockSize=64;this.g=Array(4);
this.A=Array(this.blockSize);this.j=this.h=0;this.reset()};_.ke(JD,_.Xk);JD.prototype.reset=function(){this.g[0]=1732584193;this.g[1]=4023233417;this.g[2]=2562383102;this.g[3]=271733878;this.j=this.h=0};
JD.prototype.update=function(a,b){void 0===b&&(b=a.length);for(var c=b-this.blockSize,d=this.A,e=this.h,f=0;f<b;){if(0==e)for(;f<=c;)ID(this,a,f),f+=this.blockSize;if("string"===typeof a)for(;f<b;){if(d[e++]=a.charCodeAt(f++),e==this.blockSize){ID(this,d);e=0;break}}else for(;f<b;)if(d[e++]=a[f++],e==this.blockSize){ID(this,d);e=0;break}}this.h=e;this.j+=b};
JD.prototype.digest=function(){var a=Array((56>this.h?this.blockSize:2*this.blockSize)-this.h);a[0]=128;for(var b=1;b<a.length-8;++b)a[b]=0;var c=8*this.j;for(b=a.length-8;b<a.length;++b)a[b]=c&255,c/=256;this.update(a);a=Array(16);for(b=c=0;4>b;++b)for(var d=0;32>d;d+=8)a[c++]=this.g[b]>>>d&255;return a};
var KD=function(a,b){if(0==a.length)throw Error("Sa");b=b||10;if(2>b||36<b)throw Error("Ta`"+b);if("-"==a.charAt(0))return _.BB(KD(a.substring(1),b));if(0<=a.indexOf("-"))throw Error("Ua");for(var c=_.CB(Math.pow(b,8)),d=_.AB,e=0;e<a.length;e+=8){var f=Math.min(8,a.length-e),g=parseInt(a.substring(e,e+f),b);8>f?(f=_.CB(Math.pow(b,f)),d=_.LB(d,f).add(_.CB(g))):(d=_.LB(d,c),d=d.add(_.CB(g)))}return d},LD=function(a,b){switch(a){case 0:return"Non-200 return code ("+b+")";case 1:return"XMLHTTP failure (no data)";
case 2:return"HttpConnection timeout";default:return"Unknown error"}},MD={},ND={},OD=function(a){a.Ie&&(_.x.clearTimeout(a.Ie),a.Ie=null)},PD=function(a){OD(a);_.Eh(a.Kg);a.Kg=null;a.B.stop();_.SA(a.D);if(a.Za){var b=a.Za;a.Za=null;b.abort();b.dispose()}a.rc&&(a.rc=null)},QD=function(a,b){try{a.h.yk(a,b),a.h.mc(4)}catch(c){}},RD=function(a){a.h.wk()||a.Td||a.h.Ig(a)},SD=function(a,b){var c=a.Bf,d=b.indexOf("\n",c);if(-1==d)return ND;c=Number(b.substring(c,d));if(isNaN(c))return MD;d+=1;if(d+c>b.length)return ND;
b=b.substr(d,c);a.Bf=d+c;return b},TD=function(a,b){if(!_.ba(a))throw Error("ab");return _.x.setTimeout(function(){a()},b)},UD=function(a,b){if(!b||"y2f%"==b)return b;try{var c=JSON.parse(b);if(c)for(var d=0;d<c.length;d++)if(Array.isArray(c[d])){var e=c[d];if(!(2>e.length)){var f=e[1];if(Array.isArray(f)&&!(1>f.length)){var g=f[0];if("noop"!=g&&"stop"!=g)for(var h=1;h<f.length;h++)f[h]=""}}}return(0,_.al)(c)}catch(l){return a.debug("Exception parsing expected JS array - probably was not JS"),b}},
VD=function(a,b,c,d){a.info("XMLHTTP TEXT ("+b+"): "+UD(a,c)+(d?" "+d:""))},WD=function(a,b){if(null!=a.Ie)throw Error("Xa");a.Ie=TD((0,_.je)(a.On,a),b)},XD=function(a){a.ji=(0,_.Sb)()+a.Sb;WD(a,a.Sb)},YD=function(a,b){a.Fe=(0,_.Sb)();XD(a);a.Kb=a.od.clone();_.ij(a.Kb,"t",a.A);a.Bf=0;a.Za=a.h.bi(a.h.Jg()?b:null);0<a.ii&&(a.Kg=new _.NA((0,_.je)(a.Bk,a,a.Za),a.ii));_.RA(a.D,a.Za,"readystatechange",a.Qn,void 0);b=a.Ud?_.v.clone(a.Ud):{};a.qd?(a.He="POST",b["Content-Type"]="application/x-www-form-urlencoded",
a.Za.send(a.Kb,a.He,a.qd,b)):(a.He="GET",a.$l&&!_.hg&&(b.Connection="close"),a.Za.send(a.Kb,a.He,null,b));a.h.mc(1);b=a.g;var c=a.qd;if(c){var d="";c=c.split("&");for(var e=0;e<c.length;e++){var f=c[e].split("=");if(1<f.length){var g=f[0];f=f[1];var h=g.split("_");d=2<=h.length&&"type"==h[1]?d+(g+"="+f+"&"):d+(g+"=redacted&")}}}else d=null;b.info("XMLHTTP REQ ("+a.j+") [attempt "+a.A+"]: "+a.He+"\n"+a.Kb+"\n"+d)},ZD=function(a,b,c,d,e){a.Ge=1;a.od=_.KA(b.clone());a.qd=null;a.P=c;e&&(a.$l=!1);YD(a,
d)},$D=function(a,b,c){a.Ge=1;a.od=_.KA(b.clone());a.qd=c;a.P=!0;YD(a,null)},aE=function(){};aE.prototype.debug=function(a){this.info(a)};aE.prototype.info=function(){};aE.prototype.warning=function(){};
var bE=function(a){a.onload=null;a.onerror=null;a.onabort=null;a.ontimeout=null},cE=function(a,b,c){var d=new aE;d.debug("TestLoadImage: loading "+a);var e=new Image;e.onload=function(){try{d.debug("TestLoadImage: loaded"),bE(e),c(!0)}catch(f){}};e.onerror=function(){try{d.debug("TestLoadImage: error"),bE(e),c(!1)}catch(f){}};e.onabort=function(){try{d.debug("TestLoadImage: abort"),bE(e),c(!1)}catch(f){}};e.ontimeout=function(){try{d.debug("TestLoadImage: timeout"),bE(e),c(!1)}catch(f){}};_.x.setTimeout(function(){if(e.ontimeout)e.ontimeout()},
b);wD(e,a)},dE=function(a,b,c,d,e){(new aE).debug("TestLoadImageWithRetries: "+e);if(0==d)c(!1);else{var f=e||0;d--;cE(a,b,function(g){g?c(!0):_.x.setTimeout(function(){dE(a,b,c,d,f)},f)})}},eE=function(a,b){this.g=a;this.map=b;this.context=null},fE=function(a,b){_.an.call(this,"statevent",a);this.stat=b};_.ke(fE,_.an);
var gE=function(a){_.RB.dispatchEvent(new fE(_.RB,a))},hE=function(a,b){a.Fe=(0,_.Sb)();XD(a);var c=b?window.location.hostname:"";a.Kb=a.od.clone();_.Ha(a.Kb,"DOMAIN",c);_.Ha(a.Kb,"t",a.A);try{a.rc=new ActiveXObject("htmlfile")}catch(l){PD(a);a.pd=7;gE(22);RD(a);return}var d="<html><body>";if(b){var e="";for(b=0;b<c.length;b++){var f=c.charAt(b);if("<"==f)e+="\\x3c";else if(">"==f)e+="\\x3e";else{var g=f;if(g in BD)f=BD[g];else if(g in AD)f=BD[g]=AD[g];else{var h=g.charCodeAt(0);if(31<h&&127>h)f=
g;else{if(256>h){if(f="\\x",16>h||256<h)f+="0"}else f="\\u",4096>h&&(f+="0");f+=h.toString(16).toUpperCase()}f=BD[g]=f}e+=f}}d+='<script>document.domain="'+e+'"\x3c/script>'}c=_.Pc(d+"</body></html>",null);a.rc.open();a.rc.write(_.Af(c));a.rc.close();a.rc.parentWindow.m=(0,_.je)(a.np,a);a.rc.parentWindow.d=(0,_.je)(a.Il,a,!0);a.rc.parentWindow.rpcClose=(0,_.je)(a.Il,a,!1);c=a.rc.createElement("DIV");a.rc.parentWindow.document.body.appendChild(c);d=_.mf(a.Kb.toString());d=_.Jf(_.hf(d));d=_.Pc('<iframe src="'+
d+'"></iframe>',null);_.Ef(c,d);a.g.info("TRIDENT REQ ("+a.j+") [ attempt "+a.A+"]: GET\n"+a.Kb);a.h.mc(1)},iE=function(a,b,c){for(var d=!0;!a.Td&&a.Bf<c.length;){var e=SD(a,c);if(e==ND){4==b&&(a.pd=4,gE(15),d=!1);VD(a.g,a.j,null,"[Incomplete Response]");break}else if(e==MD){a.pd=4;gE(16);VD(a.g,a.j,c,"[Invalid Chunk]");d=!1;break}else VD(a.g,a.j,e,null),QD(a,e)}4==b&&0==c.length&&(a.pd=1,gE(17),d=!1);a.jc=a.jc&&d;d||(VD(a.g,a.j,c,"[Invalid Chunked Response]"),PD(a),RD(a))},jE=function(a,b,c,d,e){this.h=
a;this.g=b;this.C=c;this.j=d;this.A=e||1;this.Sb=45E3;this.D=new _.PA(this);this.B=new _.fy;this.B.setInterval(250)};_.k=jE.prototype;_.k.Ud=null;_.k.jc=!1;_.k.Ie=null;_.k.ji=null;_.k.Fe=null;_.k.Ge=null;_.k.od=null;_.k.Kb=null;_.k.qd=null;_.k.Za=null;_.k.Bf=0;_.k.rc=null;_.k.He=null;_.k.pd=null;_.k.Af=-1;_.k.$l=!0;_.k.Td=!1;_.k.ii=0;_.k.Kg=null;_.k.setTimeout=function(a){this.Sb=a};_.k.Qn=function(a){a=a.target;var b=this.Kg;b&&3==_.Rn(a)?(this.g.debug("Throttling readystatechange."),b.hk()):this.Bk(a)};
_.k.Bk=function(a){try{if(a==this.Za)a:{var b=_.Rn(this.Za),c=this.Za.A,d=this.Za.Ma();if(_.dg&&!_.xg(10)||_.hg&&!_.vg("420+")){if(4>b)break a}else if(3>b||3==b&&!_.cg&&!_.Tn(this.Za))break a;this.Td||4!=b||7==c||(8==c||0>=d?this.h.mc(3):this.h.mc(2));OD(this);var e=this.Za.Ma();this.Af=e;var f=_.Tn(this.Za);f||this.g.debug("No response text for uri "+this.Kb+" status "+e);this.jc=200==e;this.g.info("XMLHTTP RESP ("+this.j+") [ attempt "+this.A+"]: "+this.He+"\n"+this.Kb+"\n"+b+" "+e);this.jc?(4==
b&&PD(this),this.P?(iE(this,b,f),_.cg&&this.jc&&3==b&&(_.RA(this.D,this.B,"tick",this.Pn,void 0),this.B.start())):(VD(this.g,this.j,f,null),QD(this,f)),this.jc&&!this.Td&&(4==b?this.h.Ig(this):(this.jc=!1,XD(this)))):(400==e&&0<f.indexOf("Unknown SID")?(this.pd=3,gE(13),this.g.warning("XMLHTTP Unknown SID ("+this.j+")")):(this.pd=0,gE(14),this.g.warning("XMLHTTP Bad status "+e+" ("+this.j+")")),PD(this),RD(this))}else this.g.warning("Called back with an unexpected xmlhttp")}catch(g){this.g.debug("Failed call to OnXmlHttpReadyStateChanged_")}finally{}};
_.k.Pn=function(){var a=_.Rn(this.Za),b=_.Tn(this.Za);this.Bf<b.length&&(OD(this),iE(this,a,b),this.jc&&4!=a&&XD(this))};_.k.np=function(a){TD((0,_.je)(this.mp,this,a),0)};_.k.mp=function(a){if(!this.Td){var b=this.g;b.info("TRIDENT TEXT ("+this.j+"): "+UD(b,a));OD(this);QD(this,a);XD(this)}};_.k.Il=function(a){TD((0,_.je)(this.lp,this,a),0)};_.k.lp=function(a){this.Td||(this.g.info("TRIDENT TEXT ("+this.j+"): "+a?"success":"failure"),PD(this),this.jc=a,this.h.Ig(this),this.h.mc(4))};
_.k.cancel=function(){this.Td=!0;PD(this)};_.k.On=function(){this.Ie=null;var a=(0,_.Sb)();0<=a-this.ji?(this.g.info("TIMEOUT: "+this.Kb),2!=this.Ge&&this.h.mc(3),PD(this),this.pd=2,gE(18),RD(this)):(this.g.warning("WatchDog timer called too early"),WD(this,this.ji-a))};_.k.getLastError=function(){return this.pd};var kE=function(a,b,c,d){_.an.call(this,"timingevent",a);this.size=b;this.Tf=d};_.ke(kE,_.an);var lE=function(a){_.an.call(this,"serverreachability",a)};_.ke(lE,_.an);
var mE=function(a,b){a.ib&&(a=a.ib.tk())&&_.v.forEach(a,function(c,d){_.Ha(b,d,c)})},nE=function(a,b,c){var d=_.jj(c);if(""!=d.h)b&&_.Xi(d,b+"."+d.h),_.Yi(d,d.B);else{var e=window.location;d=_.MA(e.protocol,b?b+"."+e.hostname:e.hostname,+e.port,c)}a.wf&&_.v.forEach(a.wf,function(f,g){_.Ha(d,g,f)});_.Ha(d,"VER",a.Qd);mE(a,d);return d},oE=function(a,b,c){b=nE(a,a.Jg()?b:null,c);a.g.debug("GetBackChannelUri: "+b);return b},pE=function(a,b){b=nE(a,null,b);a.g.debug("GetForwardChannelUri: "+b);return b},
qE=function(a){var b=nE(a.g,a.jf,"/mail/images/cleardot.gif");_.KA(b);dE(b.toString(),5E3,(0,_.je)(a.Nm,a),3,2E3);a.mc(1)},rE=function(a){a.L=0;a.zc=-1;if(a.ib)if(0==a.j.length&&0==a.h.length)a.ib.ai();else{a.g.debug("Number of undelivered maps, pending: "+a.j.length+", outgoing: "+a.h.length);_.we(a.j);var b=_.we(a.h);a.j.length=0;a.h.length=0;a.ib.ai(b)}},sE=function(a){a.zb||a.nd||(a.nd=TD((0,_.je)(a.Ak,a),0),a.Ee=0)},tE=function(a){a.g.debug("connectChannel_()");a.Ln(1,0);a.Gg=pE(a,a.ci);sE(a)},
uE=function(a,b,c){a.g.debug("Test Connection Finished");a.di=c;a.zc=b.Sd;a.B||tE(a)},vE=function(a){a.h.debug("TestConnection: starting stage 2");var b=a.g.H;if(null!=b)a.h.debug("TestConnection: skipping stage 2, precomputed result is "+b?"Buffered":"Unbuffered"),gE(5),b?(gE(11),uE(a.g,a,!1)):(gE(12),uE(a.g,a,!0));else if(a.Bb=new jE(a,a.h,void 0,void 0,void 0),a.Bb.Ud=a.ei,b=oE(a.g,a.zf,a.gi),gE(5),!_.dg||_.xg(10))_.ij(b,"TYPE","xmlhttp"),ZD(a.Bb,b,!1,a.zf,!1);else{_.ij(b,"TYPE","html");var c=
a.Bb;a=!!a.zf;c.Ge=3;c.od=_.KA(b.clone());hE(c,a)}},wE=function(a){null!=a.Rd&&(_.x.clearTimeout(a.Rd),a.Rd=null)},xE=function(a){a.hd&&(a.hd.abort(),a.hd=null);a.Ea&&(a.Ea.cancel(),a.Ea=null);a.Oc&&(_.x.clearTimeout(a.Oc),a.Oc=null);wE(a);a.zb&&(a.zb.cancel(),a.zb=null);a.nd&&(_.x.clearTimeout(a.nd),a.nd=null)},yE=function(a,b){if(0==a.L)throw Error("Ya");1E3==a.h.length&&a.A.stringify(b);a.h.push(new eE(a.Mn++,b));2!=a.L&&3!=a.L||sE(a)},zE=function(a){var b=Math.min(a.h.length,1E3),c=["count="+
b];if(6<a.Qd&&0<b){var d=a.h[0].g;c.push("ofs="+d)}else d=0;for(var e=0;e<b;e++){var f=a.h[e].g,g=a.h[e].map;f=6>=a.Qd?e:f-d;try{_.v.forEach(g,function(h,l){c.push("req"+f+"_"+l+"="+encodeURIComponent(h))})}catch(h){c.push("req"+f+"_type="+encodeURIComponent("_badmap"))}}a.j=a.j.concat(a.h.splice(0,b));return c.join("&")},AE=function(a,b){if(b)if(6<a.Qd){a.h=a.j.concat(a.h);a.j.length=0;var c=a.yf-1;b=zE(a)}else c=b.j,b=b.qd;else c=a.yf++,b=zE(a);var d=a.Gg.clone();_.Ha(d,"SID",a.C);_.Ha(d,"RID",
c);_.Ha(d,"AID",a.xf);mE(a,d);c=new jE(a,a.g,a.C,c,a.Ee+1);c.Ud=null;c.setTimeout(1E4+Math.round(1E4*Math.random()));a.zb=c;$D(c,d,b)},BE=function(a,b){a.g.debug("startForwardChannel_");if(1==a.L){if(!b){a.g.debug("open_()");a.yf=Math.floor(1E5*Math.random());b=a.yf++;var c=new jE(a,a.g,"",b,void 0);c.Ud=null;var d=zE(a),e=a.Gg.clone();_.Ha(e,"RID",b);_.Ha(e,"CVER","1");mE(a,e);$D(c,e,d);a.zb=c;a.L=2}}else 3==a.L&&(b?AE(a,b):0==a.h.length?a.g.debug("startForwardChannel_ returned: nothing to send"):
a.zb||(AE(a),a.g.debug("startForwardChannel_ finished, sent request")))},CE=function(a){a.Ea||a.Oc||(a.D=1,a.Oc=TD((0,_.je)(a.zk,a),0),a.De=0)},DE=function(a,b){var c=5E3+Math.floor(1E4*Math.random());a.isActive()||(a.g.debug("Inactive channel"),c*=2);return c*b},EE=function(a){if(a.Ea||a.Oc||3<=a.De)return!1;a.g.debug("Going to retry GET");a.D++;a.Oc=TD((0,_.je)(a.zk,a),DE(a,a.De));a.De++;return!0},FE=function(a,b){a.g.debug("HttpChannel: error - "+b);a.L=0;a.ib&&a.ib.pk(b);rE(a);xE(a)},GE=function(a,
b){a.g.info("Error code "+b);if(2==b||9==b){var c=null;a.ib&&(c=null);var d=(0,_.je)(a.Rp,a);c||(c=new _.Ga("//www.google.com/images/cleardot.gif"),_.KA(c));cE(c.toString(),1E4,d)}else gE(2);FE(a,b)},HE=function(a,b){a.g.debug("Test Connection Failed");a.zc=b.Sd;GE(a,2)},IE=function(a,b){this.g=a;this.h=b;this.j=new _.TA};_.k=IE.prototype;_.k.ei=null;_.k.Bb=null;_.k.fh=!1;_.k.jm=null;_.k.Bg=null;_.k.Di=null;_.k.gi=null;_.k.L=null;_.k.Sd=-1;_.k.zf=null;_.k.jf=null;
_.k.connect=function(a){this.gi=a;a=pE(this.g,this.gi);gE(3);this.jm=(0,_.Sb)();var b=this.g.F;null!=b?(this.zf=b[0],(this.jf=b[1])?(this.L=1,qE(this)):(this.L=2,vE(this))):(_.ij(a,"MODE","init"),this.Bb=new jE(this,this.h,void 0,void 0,void 0),this.Bb.Ud=this.ei,ZD(this.Bb,a,!1,null,!0),this.L=0)};_.k.Nm=function(a){if(a)this.L=2,vE(this);else{gE(4);var b=this.g;b.g.debug("Test Connection Blocked");b.zc=b.hd.Sd;GE(b,9)}a&&this.mc(2)};_.k.bi=function(a){return this.g.bi(a)};
_.k.abort=function(){this.Bb&&(this.Bb.cancel(),this.Bb=null);this.Sd=-1};_.k.wk=function(){return!1};
_.k.yk=function(a,b){this.Sd=a.Af;if(0==this.L)if(this.h.debug("TestConnection: Got data for stage 1"),b){try{var c=this.j.parse(b)}catch(d){HE(this.g,this);return}this.zf=c[0];this.jf=c[1]}else this.h.debug("TestConnection: Null responseText"),HE(this.g,this);else if(2==this.L)if(this.fh)gE(7),this.Di=(0,_.Sb)();else if("11111"==b){if(gE(6),this.fh=!0,this.Bg=(0,_.Sb)(),a=this.Bg-this.jm,!_.dg||_.xg(10)||500>a)this.Sd=200,this.Bb.cancel(),this.h.debug("Test connection succeeded; using streaming connection"),
gE(12),uE(this.g,this,!0)}else gE(8),this.Bg=this.Di=(0,_.Sb)(),this.fh=!1};
_.k.Ig=function(){this.Sd=this.Bb.Af;this.Bb.jc?0==this.L?(this.h.debug("TestConnection: request complete for initial check"),this.jf?(this.L=1,qE(this)):(this.L=2,vE(this))):2==this.L&&(this.h.debug("TestConnection: request complete for stage 2"),(!_.dg||_.xg(10)?!this.fh:200>this.Di-this.Bg)?(this.h.debug("Test connection failed; not using streaming"),gE(11),uE(this.g,this,!1)):(this.h.debug("Test connection succeeded; using streaming connection"),gE(12),uE(this.g,this,!0))):(this.h.debug("TestConnection: request failed, in state "+
this.L),0==this.L?gE(9):2==this.L&&gE(10),HE(this.g,this,this.Bb.getLastError()))};_.k.Jg=function(){return this.g.Jg()};_.k.isActive=function(){return this.g.isActive()};_.k.mc=function(a){this.g.mc(a)};var JE=function(a,b,c){this.L=1;this.h=[];this.j=[];this.g=new aE;this.A=new _.TA;this.F=a||null;this.H=null!=b?b:null;this.B=c||!1};_.k=JE.prototype;_.k.wf=null;_.k.zb=null;_.k.Ea=null;_.k.ci=null;_.k.Gg=null;_.k.vk=null;_.k.Hg=null;_.k.yf=0;_.k.Mn=0;_.k.ib=null;_.k.nd=null;_.k.Oc=null;_.k.Rd=null;
_.k.hd=null;_.k.di=null;_.k.xf=-1;_.k.xk=-1;_.k.zc=-1;_.k.Ee=0;_.k.De=0;_.k.Qd=8;_.k.connect=function(a,b,c,d,e){this.g.debug("connect()");gE(0);this.ci=b;this.wf=c||{};d&&void 0!==e&&(this.wf.OSID=d,this.wf.OAID=e);this.B?(TD((0,_.je)(this.Rj,this,a),100),tE(this)):this.Rj(a)};
_.k.disconnect=function(){this.g.debug("disconnect()");xE(this);if(3==this.L){var a=this.yf++,b=this.Gg.clone();_.Ha(b,"SID",this.C);_.Ha(b,"RID",a);_.Ha(b,"TYPE","terminate");mE(this,b);a=new jE(this,this.g,this.C,a,void 0);a.Ge=2;a.od=_.KA(b.clone());wD(new Image,a.od.toString());a.Fe=(0,_.Sb)();XD(a)}rE(this)};_.k.Rj=function(a){this.g.debug("connectTest_()");this.hd=new IE(this,this.g);this.hd.ei=null;this.hd.j=this.A;this.hd.connect(a)};_.k.wk=function(){return 0==this.L};_.k.getState=function(){return this.L};
_.k.Ak=function(a){this.nd=null;BE(this,a)};_.k.zk=function(){this.Oc=null;this.g.debug("Creating new HttpRequest");this.Ea=new jE(this,this.g,this.C,"rpc",this.D);this.Ea.Ud=null;this.Ea.ii=0;var a=this.vk.clone();_.Ha(a,"RID","rpc");_.Ha(a,"SID",this.C);_.Ha(a,"CI",this.di?"0":"1");_.Ha(a,"AID",this.xf);mE(this,a);if(!_.dg||_.xg(10))_.Ha(a,"TYPE","xmlhttp"),ZD(this.Ea,a,!0,this.Hg,!1);else{_.Ha(a,"TYPE","html");var b=this.Ea,c=!!this.Hg;b.Ge=3;b.od=_.KA(a.clone());hE(b,c)}this.g.debug("New Request created")};
_.k.yk=function(a,b){if(0!=this.L&&(this.Ea==a||this.zb==a))if(this.zc=a.Af,this.zb==a&&3==this.L)if(7<this.Qd){try{var c=this.A.parse(b)}catch(d){c=null}if(Array.isArray(c)&&3==c.length)if(b=c,0==b[0])a:if(this.g.debug("Server claims our backchannel is missing."),this.Oc)this.g.debug("But we are currently starting the request.");else{if(this.Ea)if(this.Ea.Fe+3E3<this.zb.Fe)wE(this),this.Ea.cancel(),this.Ea=null;else break a;else this.g.warning("We do not have a BackChannel established");EE(this);
gE(19)}else this.xk=b[1],a=this.xk-this.xf,0<a&&(b=b[2],this.g.debug(b+" bytes (in "+a+" arrays) are outstanding on the BackChannel"),37500>b&&this.di&&0==this.De&&!this.Rd&&(this.Rd=TD((0,_.je)(this.Nn,this),6E3)));else this.g.debug("Bad POST response data returned"),GE(this,11)}else"y2f%"!=b&&(this.g.debug("Bad data returned - missing/invald magic cookie"),GE(this,11));else if(this.Ea==a&&wE(this),!_.Qe(b))for(a=c=this.A.parse(b),b=0;b<a.length;b++)c=a[b],this.xf=c[0],c=c[1],2==this.L?"c"==c[0]?
(this.C=c[1],this.Hg=c[2],c=c[3],null!=c?this.Qd=c:this.Qd=6,this.L=3,this.ib&&this.ib.rk(),this.vk=oE(this,this.Hg,this.ci),CE(this)):"stop"==c[0]&&GE(this,7):3==this.L&&("stop"==c[0]?GE(this,7):"noop"!=c[0]&&this.ib&&this.ib.qk(c),this.De=0)};_.k.Nn=function(){null!=this.Rd&&(this.Rd=null,this.Ea.cancel(),this.Ea=null,EE(this),gE(20))};
_.k.Ig=function(a){this.g.debug("Request complete");if(this.Ea==a){wE(this);this.Ea=null;var b=2}else if(this.zb==a)this.zb=null,b=1;else return;this.zc=a.Af;if(0!=this.L)if(a.jc)1==b?(b=(0,_.Sb)()-a.Fe,_.RB.dispatchEvent(new kE(_.RB,a.qd?a.qd.length:0,b,this.Ee)),sE(this),this.j.length=0):CE(this);else{var c=a.getLastError();if(3==c||7==c||0==c&&0<this.zc)this.g.debug("Not retrying due to error type");else{this.g.debug("Maybe retrying, last error: "+LD(c,this.zc));var d;if(d=1==b)this.zb||this.nd||
1==this.L||2<=this.Ee?d=!1:(this.g.debug("Going to retry POST"),this.nd=TD((0,_.je)(this.Ak,this,a),DE(this,this.Ee)),this.Ee++,d=!0);if(d||2==b&&EE(this))return;this.g.debug("Exceeded max number of retries")}this.g.debug("Error: HTTP request failed");switch(c){case 1:GE(this,5);break;case 4:GE(this,10);break;case 3:GE(this,6);break;case 7:GE(this,12);break;default:GE(this,2)}}};_.k.Ln=function(a){if(!_.Cc(arguments,this.L))throw Error("Za`"+this.L);};
_.k.Rp=function(a){a?(this.g.info("Successfully pinged google.com"),gE(2)):(this.g.info("Failed to ping google.com"),gE(1),FE(this,8))};_.k.bi=function(a){if(a)throw Error("$a");a=new _.Gn;a.G=!1;return a};_.k.isActive=function(){return!!this.ib&&this.ib.isActive(this)};_.k.mc=function(a){_.RB.dispatchEvent(new lE(_.RB,a))};_.k.Jg=function(){return!(!_.dg||_.xg(10))};var KE=function(){};_.k=KE.prototype;_.k.rk=function(){};_.k.qk=function(){};_.k.pk=function(){};_.k.ai=function(){};_.k.tk=function(){return{}};
_.k.isActive=function(){return!0};var LE=function(a,b){this.g=a;this.$b=b};LE.prototype.equals=function(a){return this.$b==a.$b&&this.g.equals(_.v.clone(a.g))};
var ME=/^[0-9.]*$/,NE=function(a){this.h=null;var b=_.AB;if(a instanceof _.xB){if(0!=a.g||0>a.compare(_.AB)||0<a.compare(_.SB))throw Error("bb");b=_.v.clone(a)}else{if(!ME.test(a))throw Error("cb`"+a);var c=a.split(".");if(4!=c.length)throw Error("cb`"+a);for(var d=0;d<c.length;d++){var e=c[d];var f=Number(e);e=0==f&&_.Qe(e)?NaN:f;if(isNaN(e)||0>e||255<e||1!=c[d].length&&_.Pe(c[d],"0"))throw Error("db`"+a+"`"+d);e=_.CB(e);b=_.PB(b,8).or(e)}}LE.call(this,b,4)};_.ke(NE,LE);
NE.prototype.toString=function(){if(this.h)return this.h;for(var a=_.HB(this.g,0),b=[],c=3;0<=c;c--)b[c]=String(a&255),a>>>=8;return this.h=b.join(".")};NE.prototype.A=function(){var a=_.FB(this.g);return 10==(a>>>24&255)||172==(a>>>24&255)&&16==(a>>>16&240)||192==(a>>>24&255)&&168==(a>>>16&255)};NE.prototype.j=function(){var a=_.FB(this.g);return 169==(a>>>24&255)&&254==(a>>>16&255)};
var OE=/^([a-fA-F0-9]*:){2}[a-fA-F0-9:.]*$/,PE=function(a){this.h=null;var b=_.AB;if(a instanceof _.xB){if(0!=a.g||0>a.compare(_.AB)||0<a.compare(_.UB))throw Error("eb");b=_.v.clone(a)}else{if(!OE.test(a))throw Error("fb`"+a);var c=a.split(":");if(-1!=c[c.length-1].indexOf(".")){a=_.HB(_.v.clone((new NE(c[c.length-1])).g),0);var d=[];d.push((a>>>16&65535).toString(16));d.push((a&65535).toString(16));Array.prototype.splice.call(c,c.length-1,1);_.lc(c,d);a=c.join(":")}d=a.split("::");if(2<d.length||
1==d.length&&8!=c.length)throw Error("fb`"+a);if(1<d.length){c=d[0].split(":");d=d[1].split(":");1==c.length&&""==c[0]&&(c=[]);1==d.length&&""==d[0]&&(d=[]);var e=8-(c.length+d.length);c=1>e?[]:xD(c,yD("0",e),d)}if(8!=c.length)throw Error("gb`"+a);for(d=0;d<c.length;d++){e=KD(c[d],16);if(0>e.compare(_.AB)||0<e.compare(_.TB))throw Error("hb`"+c[d]+"`"+a);b=_.PB(b,16).or(e)}}LE.call(this,b,6)};_.ke(PE,LE);
PE.prototype.toString=function(){if(this.h)return this.h;for(var a=[],b=3;0<=b;b--){var c=_.HB(this.g,b),d=c&65535;a.push((c>>>16).toString(16));a.push(d.toString(16))}c=b=-1;for(var e=d=0,f=0;f<a.length;f++)"0"==a[f]?(e++,-1==c&&(c=f),e>d&&(d=e,b=c)):(c=-1,e=0);0<d&&(b+d==a.length&&a.push(""),a.splice(b,d,""),0==b&&(a=[""].concat(a)));return this.h=a.join(":")};PE.prototype.A=function(){return 64768==(_.HB(this.g,3)>>>16&65280)};PE.prototype.j=function(){return 65152==(_.HB(this.g,3)>>>16&65472)};
var QE=function(a){return Math.min(1E3*Math.pow(2,a),6E4)},RE=function(a){_.an.call(this,"c");this.message=a};_.ke(RE,_.an);var SE=function(a){_.an.call(this,"b");this.data=a};_.ke(SE,_.an);var nD=function(a){_.Dn.call(this);a||(a={});this.A=0!=a.Km;this.g=a.JE||QE;this.B=a.binaryType||"blob";this.j=this.g(this.Qf)};_.ke(nD,_.Dn);_.k=nD.prototype;_.k.Lb=null;_.k.Cf=null;_.k.Pf=void 0;_.k.Lh=!1;_.k.Qf=0;_.k.Vd=null;
_.k.open=function(a,b){null!=this.Vd&&_.x.clearTimeout(this.Vd);this.Vd=null;this.Cf=a;this.Lb=(this.Pf=b)?new WebSocket(this.Cf,this.Pf):new WebSocket(this.Cf);this.Lb.binaryType=this.B;this.Lb.onopen=(0,_.je)(this.ep,this);this.Lb.onclose=(0,_.je)(this.Rn,this);this.Lb.onmessage=(0,_.je)(this.Tn,this);this.Lb.onerror=(0,_.je)(this.Sn,this)};_.k.close=function(){null!=this.Vd&&_.x.clearTimeout(this.Vd);this.Vd=null;this.Lb&&(this.Lh=!0,this.Lb.close(),this.Lb=null)};_.k.send=function(a){this.Lb.send(a)};
_.k.ep=function(){this.dispatchEvent("d");this.Qf=0;this.j=this.g(this.Qf)};_.k.Rn=function(){this.dispatchEvent("a");this.Lb=null;this.Lh?(this.Cf=null,this.Pf=void 0):this.A&&(this.Vd=_.Fn((0,_.je)(this.open,this,this.Cf,this.Pf),this.j,this),this.Qf++,this.j=this.g(this.Qf));this.Lh=!1};_.k.Tn=function(a){this.dispatchEvent(new RE(a.data))};_.k.Sn=function(a){this.dispatchEvent(new SE(a.data))};_.k.aa=function(){nD.Ha.aa.call(this);this.close()};
var TE=function(a,b){a.capabilities.clear();(0,_.oe)(b.split(","),_.Eb(_.v.contains,CD)).forEach(function(c){a.capabilities.add(c)})},UE=function(a,b){a.experiments.clear();b.split(",").forEach(function(c){a.experiments.add(c)})},VE=function(a){this.app=this.name=this.id="";this.type="REMOTE_CONTROL";this.obfuscatedGaiaId=this.avatar=this.username="";this.h=!1;this.capabilities=new Set;this.experiments=new Set;this.theme="u";this.j=-1;this.g=null;this.B=new _.ri;this.A=this.Pb="";a&&(this.id=a.id||
a.name,this.name=a.name,this.app=a.app,this.type=a.type||"REMOTE_CONTROL",this.username=a.user||"",this.avatar=a.userAvatarUri||"",this.obfuscatedGaiaId=a.obfuscatedGaiaId||"",this.theme=a.theme||"u",TE(this,a.capabilities||""),UE(this,a.experiments||""),this.Pb=a.remoteControllerUrl||"",this.A=a.localChannelEncryptionKey||"")};VE.prototype.equals=function(a){return a?this.id==a.id:!1};_.WE=function(a){var b=a.name||"";a.username&&(b=b+" ("+a.username+")");return b};
XE=function(a,b){var c=(a.h?0:1)-(b.h?0:1);return 0!=c?c:_.zD(_.WE(a),_.WE(b))};YE=function(a){return{id:a.id,name:a.name,app:a.app,type:a.type,user:a.username,userAvatarUri:a.avatar,obfuscatedGaiaId:a.obfuscatedGaiaId,theme:a.theme,capabilities:Array.from(a.capabilities.values()).join(","),experiments:Array.from(a.experiments.values()).join(",")}};ZE=function(a){var b=new VE(YE(a));b.h=a.h;b.Pb=a.Pb;b.A=a.A;return b};$E=function(a,b){this.Tp=a;this.Io=b};
aF=function(a,b){this.action=a;this.params=b||{}};bF=function(a,b){_.Dh.call(this);this.g=new HD(this.kp,0,this);_.Ko(this,_.Eb(_.Eh,this.g));this.h=5E3;this.j=0;if(_.ba(a))b&&(a=(0,_.je)(a,b));else if(a&&_.ba(a.handleEvent))a=(0,_.je)(a.handleEvent,a);else throw Error("X");this.A=a};_.ke(bF,_.Dh);_.k=bF.prototype;_.k.kp=function(){this.h=Math.min(3E5,2*this.h);this.A();this.j&&this.start()};_.k.start=function(){var a=this.h+15E3*Math.random(),b=this.g;b.isActive()||b.start(a);this.j=(0,_.Sb)()+a};
_.k.stop=function(){this.g.stop();this.j=0};_.k.isActive=function(){return this.g.isActive()};_.k.reset=function(){this.g.stop();this.h=5E3};var gD=function(a,b,c){this.J=a;this.D=b;this.B=new _.Xj;this.h=new bF(this.Gp,this);this.g=null;this.G=!1;this.A=null;this.H="";this.F=this.j=0;this.C=[];this.N=c||!1};_.ke(gD,KE);_.k=gD.prototype;_.k.subscribe=function(a,b,c){return this.B.subscribe(a,b,c)};_.k.unsubscribe=function(a,b,c){return this.B.unsubscribe(a,b,c)};
_.k.Yg=function(a,b){this.B.C.apply(this.B,arguments)};_.k.dispose=function(){this.G||(this.G=!0,_.Eh(this.B),this.disconnect(),_.Eh(this.h),this.h=null)};_.k.Qg=function(){return this.G};
_.k.connect=function(a,b,c){if(!this.g||2!=this.g.getState()){this.H="";this.h.stop();this.A=a||null;this.j=b||0;a=this.J+"/test";b=this.J+"/bind";var d=new JE(c?c.firstTestResults:null,c?c.secondTestResults:null,this.N),e=this.g;e&&(e.ib=null);d.ib=this;this.g=d;e?(e.getState(),this.g.connect(a,b,this.D,e.C,e.xf)):c?this.g.connect(a,b,this.D,c.sessionId,c.arrayId):this.g.connect(a,b,this.D)}};
_.k.disconnect=function(a){this.F=a||0;this.h.stop();this.g&&(3==this.g.getState()&&BE(this.g),this.g.disconnect());this.F=0};_.k.sendMessage=function(a,b){a={_sc:a};b&&_.v.extend(a,b);this.h.isActive()||2==(this.g?this.g.getState():0)?this.C.push(a):this.g&&3==this.g.getState()&&yE(this.g,a)};_.k.rk=function(){this.h.reset();this.A=null;this.j=0;if(this.C.length){var a=this.C;this.C=[];for(var b=0,c=a.length;b<c;++b)yE(this.g,a[b])}this.Yg("handlerOpened")};
_.k.pk=function(a){var b=2==a&&401==this.g.zc;4==a||b||this.h.start();this.Yg("handlerError",a)};_.k.ai=function(a){if(!this.h.isActive())this.Yg("handlerClosed");else if(a)for(var b=0,c=a.length;b<c;++b){var d=a[b].map;d&&this.C.push(d)}};_.k.tk=function(){var a={v:2};this.H&&(a.gsessionid=this.H);0!=this.j&&(a.ui=""+this.j);0!=this.F&&(a.ui=""+this.F);this.A&&_.v.extend(a,this.A);return a};
_.k.qk=function(a){"S"==a[0]?this.H=a[1]:"gracefulReconnect"==a[0]?(this.h.start(),this.g.disconnect()):this.Yg("handlerMessage",new aF(a[0],a[1]))};_.k.Gp=function(){var a=this.g,b=0;a.Ea&&b++;a.zb&&b++;0==b&&this.connect(this.A,this.j)};
var cF=function(){this.scheme="https";this.port=this.domain="";this.g="/api/lounge";this.h=!0;var a=document.location.href,b=Number(_.tc(4,a))||"";b&&(this.port=":"+b);this.domain=_.uc(_.tc(3,a))||"";a=_.sf;0<=a.search("MSIE")&&0>_.df(a.match(/MSIE ([\d.]+)/)[1],"10.0")&&(this.h=!1)},dF=function(a,b,c){var d=a.g;a.h&&(d=a.scheme+"://"+a.domain+a.port+a.g);return _.pc(d+b,c||{})},eF=function(a){a=a||{};this.name=a.name||"";this.id=a.id||a.screenId||"";this.token=a.token||a.loungeToken||"";this.uuid=
a.uuid||a.dialId||""},fF=function(a){return{name:a.name,screenId:a.id,loungeToken:a.token,dialId:a.uuid}},gF=function(a){_.an.call(this,"channelMessage");this.message=a};_.G(gF,_.an);var hF=function(a){_.an.call(this,"channelError");this.error=a};_.G(hF,_.an);
var fD=function(a,b,c,d,e){_.Dn.call(this);this.g=!1;b=dF(b,"/bc");c={device:"LOUNGE_SCREEN",id:c.id,name:c.name,app:c.app,theme:c.theme,capabilities:Array.from(c.capabilities.values()).join(","),mdxVersion:d?3:2};a=this.ma=a(b,c);(a.D.loungeIdToken=e)||a.h.stop();this.ma.subscribe("handlerOpened",this.fp,this);this.ma.subscribe("handlerClosed",this.Cl,this);this.ma.subscribe("handlerMessage",this.Ko,this);this.ma.subscribe("handlerError",this.onError,this)};_.G(fD,_.Dn);_.k=fD.prototype;
_.k.connect=function(){this.ma.connect()};_.k.disconnect=function(){this.ma.disconnect()};_.k.sendMessage=function(a,b){this.ma.sendMessage(a,b)};_.k.getState=function(){var a=this.ma;switch(a.g?a.g.getState():0){case 1:return 0;case 2:return 1;case 3:return 2;default:return 4}};_.k.aa=function(){_.Eh(this.ma)};_.k.fp=function(){this.dispatchEvent("channelOpened")};_.k.Cl=function(){this.dispatchEvent("channelClosed")};_.k.Ko=function(a){this.dispatchEvent(new gF(a));this.g=!1};
_.k.onError=function(a){if(this.ma.h.isActive())this.g&&(a=this.ma,a.g&&(a.g.disconnect(),a.connect(a.A,a.j)),this.g=!1);else{var b=0;null!=a&&2==a&&401==this.ma.g.zc&&(b=1);this.dispatchEvent(new hF(b))}};iF="mdxSessionStatus autoplayDismissed onAdStateChange onAudioTrackListChanged onAutoplayModeChanged onHasPreviousNextChanged onPlaylistModeChanged onStateChange onVolumeChanged".split(" ");
jF=function(a){a=void 0===a?{}:a;var b=a.videoId,c=a.listId,d=a.videoCtt,e=a.listCtt,f=a.clickTrackingParams,g=a.currentIndex,h=a.currentTime,l=a.playerParams,n=a.watchNextParams,q=void 0===a.isFling?!1:a.isFling,t=a.isMdxPlayback,w=void 0===a.isVoiceRequest?!1:a.isVoiceRequest,y=void 0===a.enableSafetyMode?!1:a.enableSafetyMode,B=void 0===a.forceReloadPlayback?!1:a.forceReloadPlayback;this.eventDetails=void 0===a.eventDetails?null:a.eventDetails;this.videoId=b;this.listId=c;this.videoCtt=d;this.listCtt=
e;this.clickTrackingParams=f;this.currentIndex=g;this.currentTime=h;this.playerParams=l;this.watchNextParams=n;this.isFling=q;this.isMdxPlayback=t;this.isVoiceRequest=w;this.enableSafetyMode=y;this.forceReloadPlayback=B};kF=function(a){var b=new Map;a.forEach(function(c,d){b.set(d.split("#")[0],c.app)});return _.F(b.values()).concat().sort()};_.lF=function(a){var b=kF(a.j);a=kF(a.B);return _.F(b).concat(_.F(a.map(function(c){return"!"+c})))};
mF=function(a){var b=[];a=_.A(a.j.values());for(var c=a.next();!c.done;c=a.next()){c=c.value;var d=c.g;d&&(4==d.getState()?(_.Eh(d),c.g=null):b.push(d))}return b};nF=function(a,b,c){if(0<a.F.length){var d=a.F;a.F=[];a=0;for(var e=d.length;a<e;++a)d[a](b,c)}};oF=function(a,b){a.h&&a.h.disconnect();var c=mF(a);c.forEach(function(d){d.disconnect()});_.Fh(a.h,c);a.h=null;a.j.clear();a.B.clear();a.kf.clear();a.hh.clear();nF(a,!1,b);a.A.h("remote:status",[])};
pF=function(a){a.A||a.localStorage.remove("yt_mdx_screen");a.screen=null};qF=function(a){a.localStorage.remove("migrated-mdx-paired-devices");a.J.clear();oF(a,"SCREEN_RESET");pF(a.C)};rF=function(){var a=Error.call(this,"Stubbed methods should NEVER be called.");this.message=a.message;"stack"in a&&(this.stack=a.stack)};_.G(rF,Error);
var sF=function(){throw new rF;},tF=function(a,b){var c=b.message.action;b=b.message.params||{};a.P.start(c);a.tl(c,b)},uF=function(a,b){b in a&&(a[b]=parseFloat(a[b]))},vF=function(a,b){a.J.set(b.id.split("#")[0],b);a.localStorage.set("migrated-mdx-paired-devices",a.J.la().map(function(c){return YE(c)}),31536E3)},wF=function(a){var b=_.F(a.j.values()).concat(_.F(a.B.values()));a.kf=b.length?b[0].capabilities:new Set;a.hh.clear();b.forEach(function(c){a.kf=new Set(_.F(a.kf).concat().filter(function(d){return c.capabilities.has(d)}));
c.experiments.forEach(function(d){a.hh.add(d)})})},xF=function(){var a=Error.call(this,"Unexpected method called.");this.message=a.message;"stack"in a&&(this.stack=a.stack)};_.G(xF,Error);var yF=function(a,b,c){c.clientActionNonce=a.nonce;_.hd(b,c)},kD=function(){var a=jD;this.messageReceived=this.nonce="";this.g=a(this.Mp,1E3,this)};_.k=kD.prototype;_.k.start=function(a){this.g.Gn(a)};
_.k.Mp=function(a){this.nonce||(this.nonce=_.Vr(),yF(this,"latencyActionBaselined",{}),this.tick("start"),this.messageReceived=a)};_.k.end=function(a){this.nonce&&(this.tick("end"),this.info({actionType:"LATENCY_ACTION_MDX_COMMAND",mdxInfo:{messageReceived:this.messageReceived,messageSent:a}}),this.nonce=this.messageReceived="")};_.k.tick=function(a){yF(this,"latencyActionTicked",{tickName:a})};_.k.info=function(a){yF(this,"latencyActionInfo",a)};
var pD=function(a,b,c){this.g=null;this.h=-1;this.j=a;this.A=b;this.mh(void 0===c?"":c)};pD.prototype.mh=function(a){a&&(a=_.XA(a),this.g=this.j(a))};
var zF=function(a,b){try{a.g.send((0,_.al)([b[0],mD(b[1])]))}catch(c){}},AF=function(a,b,c,d){_.Dn.call(this);this.C=0;this.B=[];this.G=c;this.g=a();this.F=b(d);this.L=0;this.j=new bF(this.No,this);this.A=new _.PA;_.RA(this.A,this.g,"d",this.gp,!1,this);_.RA(this.A,this.g,"a",this.Zo,!1,this);_.RA(this.A,this.g,"c",this.Mo,!1,this);_.RA(this.A,this.g,"b",this.Lo,!1,this)};_.G(AF,_.Dn);_.k=AF.prototype;_.k.connect=function(){this.L=1;this.j.stop();this.g.open(this.G)};
_.k.disconnect=function(){this.L=3;this.j.stop();this.g.close()};
_.k.sendMessage=function(a,b){a:{var c=this.F;if("mdxSessionStatus"==a){b.ver=1;var d=[a,b]}else if(_.Cc(iF,a))d=[a,b];else{if(c.g&&1==c.h)try{var e=c.A(),f=(0,_.al)([a,b]),g=_.ED(f),h=16-g.length%16,l=yD(h,g.length+h);for(a=0;a<g.length;a++)l[a]=g[a];var n=c.g.encrypt(l,e);d=["mdxEncrypted",{enc:_.Wc(n,3),iv:_.Wc(e,3)}];break a}catch(q){}d=null}}d&&(this.j.isActive()||1==this.L?this.B.push(d):(c=this.g,c.Lb&&1==c.Lb.readyState&&zF(this,d)))};_.k.getState=function(){return this.L};_.k.mh=function(a){this.F.mh(a)};
_.k.aa=function(){_.Fh(this.A,this.g,this.j);_.Dn.prototype.aa.call(this)};_.k.gp=function(){var a=this;this.L=2;this.C=0;this.j.reset();this.B.length&&(this.B.forEach(function(b){zF(a,b)}),this.B=[]);this.dispatchEvent("channelOpened")};_.k.Zo=function(){var a=5>this.C;3!=this.L&&a?(this.C++,this.j.start(),(0,_.Sb)()):(this.L=4,this.dispatchEvent("channelClosed"),this.C=0,this.j.reset())};_.k.Lo=function(){this.dispatchEvent(new hF(0))};
_.k.Mo=function(a){try{var b=_.Mo(a.message);a:{var c=this.F,d=b[0],e=b[1];"mdxRemoteStatus"==d&&(c.h=Number(e.ver)||-1);if("mdxEncrypted"!=d)b=[d,e];else{var f=e.enc?String(e.enc):"",g=e.iv?String(e.iv):"";if(c.g&&1==c.h&&f&&g){var h=_.XA(f),l=_.XA(g),n=c.g.decrypt(h,l);var q=_.FD(_.Tt(n,0,n.length-n[n.length-1]));try{var t=_.Mo(q);b=_.hc(t)?[t[0],t[1]]:null;break a}catch(w){}}b=null}}b&&this.dispatchEvent(new gF(new aF(String(b[0]),_.Qb(b[1])?Object(b[1]):{})))}catch(w){}};_.k.No=function(){this.connect()};
var BF=function(a){a.screen&&(a.screen.token="");_.nb(function(){BF(a)},864E5)},sD=function(a,b,c,d,e){this.localStorage=b;this.C=c;this.B=d;this.screen=null;this.g=[];this.h=[];this.D=a;this.A=void 0===e?!1:e;this.j=_.$d;a=this.localStorage.get("yt_mdx_screen");!this.A&&a&&(this.screen=new eF(a),this.screen.id||pF(this));BF(this)},CF=function(a,b,c){return dF(a.D,b,c)},DF=function(a,b,c){var d=new _.UA(1E3,6E4),e=function(g){g=_.Mo(g.responseText).screens;if(_.hc(g)&&(g=g.find(function(h){return h.screenId==
b}))){a.screen?a.screen.token=g.loungeToken:a.screen=new eF(g);c(!0);return}c(!1)},f=function(){_.Fc(CF(a,"/pairing/get_lounge_token_batch"),{Ob:{screen_ids:b},method:"POST",onSuccess:e,onError:function(){if(4>d.h)_.nb(f,d.getValue()),_.VA(d);else{var g={};g=(g.a="mdx_pairing",g.lounge_token_failure=1,g);a.C(g);c(!1)}}})};f()},EF=function(a,b,c,d,e){b={access_type:"permanent",app:c,lounge_token:a.screen.token,screen_id:a.screen.id||"",screen_name:b};var f=_.Fc(CF(a,"/pairing/get_pairing_code",{ctx:d}),
{Ob:b,method:"POST",format:"RAW",onSuccess:function(g){return e(g.responseText)},onError:_.Eb(e,"")});a.j=f?function(){f.abort()}:_.$d},FF=function(a,b){_.Fc(CF(a,"/pairing/generate_screen_id"),{method:"GET",format:"RAW",onSuccess:function(c){return b(c.responseText)},onError:function(){var c={};c=(c.a="mdx_pairing",c.screen_id_failure=1,c);a.C(c);b("")}})},GF=function(a,b){if(0<a.g.length)a.g.push(b);else{var c=function(d){!d&&a.screen&&(a.screen.token="");!a.A&&a.screen&&a.localStorage.set("yt_mdx_screen",
fF(a.screen));d=a.g;a.g=[];d.forEach(function(e){e(a.screen)})};a.screen?""==a.screen.token?(a.g.push(b),DF(a,a.screen.id,c)):b(a.screen):(a.g.push(b),FF(a,function(d){DF(a,d,c)}))}},HF=function(a,b,c,d,e){a.h.push(e);if(1<a.h.length)return function(){a.j();a.h=[]};var f=function(g){var h=a.h;a.h=[];h.forEach(function(l){l(g)})};GF(a,function(){EF(a,b,c,d,function(g){g?f(g):(a.screen.token="",GF(a,function(){EF(a,b,c,d,f)}))})});return function(){a.j();a.h=[]}},IF=function(a,b,c){return HF(a.C,a.screen.name,
a.screen.app,b,function(d){a.$=d;a.ga=(0,_.Sb)();c(d)})},JF=function(a,b){GF(a.C,function(){var c=a.C.screen;(c=c&&c.id)&&b.sendMessage("mdxSessionStatus",{senderId:a.screen.id,senderMsn:a.screen.j,screenId:c})})},KF=function(a,b,c){if(b=c?a.S(b,c):a.S(b)){_.sn(b,"channelMessage",a.ki,!1,a);try{b.connect()}catch(d){return _.Eh(b),null}JF(a,b)}return b},LF=function(a,b){var c=[],d=_.Mo(b.devices),e=(b=_.Mo(b.connectionEventDetails))&&b.deviceId||"",f=!(!b||!b.ui)||!1;if(_.hc(d)){b=_.F(a.j.keys()).concat();
var g=d.map(function(h){return h.id});b.filter(function(h){return 0>g.indexOf(h)}).forEach(function(h){if(h=a.j.get(h))if(h.h=!1,h.g&&h.g.disconnect(),a.j.delete(h.id),h.id==e)if(f){var l=!(a.j.size||a.B.size);a.A.h("remote:disconnected",ZE(h),l)}else h.h=!0,a.B.set(h.id,h)});d.forEach(function(h){h=new VE(h);if("LOUNGE_SCREEN"!=h.type){c.push(h);var l=a.j.get(h.id);if(l)l.Pb!=h.Pb&&(l.Pb=h.Pb,l.Pb&&(_.Eh(l.g),l.g=KF(a,l.Pb,l.A)));else{h.h=!0;l=!1;a.j.size||a.B.size||(l=a.V=!0);a.G&&h.id==a.G.id?
(h.g=a.G.g,h.g.mh(h.A),a.G=null):h.Pb&&(h.g=KF(a,h.Pb,h.A));var n=a.B.delete(h.id);a.j.set(h.id,h);vF(a,h);n||a.A.h("remote:connected",ZE(h),l)}}})}wF(a);a.A.h("remote:status",c)},MF=function(a,b,c,d,e,f,g,h,l,n,q,t,w,y){_.Dh.call(this);var B=this;this.Y=a;this.S=b;this.N=c;this.localStorage=d;this.player=e;this.A=f;this.screen=g;this.Ia=h;this.va=n;this.$="";this.ga=0;this.G=this.h=null;this.V=!0;this.F=[];this.C=q(this.N,d,n,y,l);this.P=t();this.j=new Map;this.B=new Map;this.J=new _.WB(10);this.kf=
new Set;this.hh=new Set;a=this.localStorage.get("migrated-mdx-paired-devices",31536E3);_.hc(a)&&a.forEach(function(z){B.J.set(z.id,new VE(z))})};_.G(MF,_.Dh);_.k=MF.prototype;_.k.connect=function(a){var b=0<this.F.length;a&&this.F.push(a);b||(a=this.Pm.bind(this),GF(this.C,a))};_.k.disconnect=function(a){oF(this,a)};_.k.aa=function(){this.F.length=0;_.Fh(this.h,mF(this));_.Dh.prototype.aa.call(this)};_.k.md=function(){var a=this.J.la();a.sort(XE);return a.map(function(b){return ZE(b)})};
_.k.sb=function(){if(sF()){if(-1E3==this.player.getPlayerState())throw new rF;this.player.getPlayerState();this.player.getCurrentTime();this.player.getDuration();throw new rF;}};_.k.sendMessage=function(a,b){this.h&&(mF(this).length&&(b.senderId=this.screen.id,b.senderMsn=++this.screen.j),this.h.sendMessage(a,b),mF(this).forEach(function(c){c.sendMessage(a,b)}),this.P.end(a))};_.k.vl=function(){sF();throw new rF;};_.k.ul=function(){throw new rF;};
_.k.Pm=function(a){if(a)if(this.h)switch(this.va({a:"connect_with_existing_channel"}),this.h.getState()){case 2:nF(this,!0);break;case 4:nF(this,!1,"CHANNEL_CLOSED")}else this.h=this.Y(this.N,this.screen,this.Ia,a.token),_.sn(this.h,"channelOpened",this.Wn,!1,this),_.sn(this.h,"channelError",this.Vn,!1,this),_.sn(this.h,"channelMessage",this.ki,!1,this),this.h.connect();else nF(this,!1,"SCREEN_INIT_FAILED")};_.k.Wn=function(){nF(this,!0)};_.k.Vn=function(){nF(this,!1,"CHANNEL_ERROR")};
_.k.ki=function(a){var b=this,c=a.message.action,d=a.message.params||{},e=parseInt(d.senderMsn,10);d=this.j.get(d.senderId);if(!isNaN(e)&&d){if("mdxRemoteStatus"==c){d.j=e;return}if(d.g&&-1<d.j){var f=d.j+1;c=d.B;if(e<f)return;if(e>f&&!c.get(e)){100>c.hb()&&(d=_.nb(function(){b.ki(a)},1E3),c.set(e,new $E(d,a)));return}for(;f<=e||c.get(f);){d.j=f;var g=c.get(f);g?(tF(this,g.Io),window.clearTimeout(g.Tp),c.remove(f)):f==e&&tF(this,a);f++}return}}tF(this,a)};
_.k.tl=function(a,b){switch(a){case "play":this.player.play();break;case "pause":this.player.pause();break;case "seekTo":b=parseFloat(b.currentTime||b.newTime);b=isNaN(b)||0>b?void 0:b;this.player.seekTo(b||0);break;case "stopVideo":this.player.stop();break;case "setPlaylist":throw b.locationInfo&&_.Mo(b.locationInfo),new rF;case "updatePlaylist":var c=b.eventDetails?_.Mo(b.eventDetails):null;c&&("VIDEO_REMOVED"!=c.eventType||sF()!=c.videoId||this.player.next().success||this.player.stop(),this.A.h("playlist:updated",
c));this.player.updatePlaylist(new jF({eventDetails:c,videoId:b.videoId,listId:b.listId,videoCtt:b.ctt,listCtt:b.listCtt}));this.V=!1;break;case "setVolume":(c="true"==b.muted||"1"==b.muted)&&!this.player.isMuted()?this.player.mute():!c&&this.player.isMuted()&&this.player.unMute();c=this.player.getVolume();a=parseInt(b.volume,10)||0;void 0!==b.delta&&(b=parseInt(b.delta,10),0<=c&&!isNaN(b)&&(a=Math.max(0,Math.min(c+b,100))));this.player.setVolume(a);break;case "getVolume":b=this.player.getVolume();
c=this.player.isMuted();-1!=b&&this.sendMessage("onVolumeChanged",{volume:b,muted:c});break;case "setAudioTrack":this.player.setAudioTrack(b.videoId,b.audioTrackId);break;case "setVideoQuality":if(!isNaN(parseInt(b.qualityLevel,10)))throw _.v.uj(_.fC),new rF;break;case "setSubtitlesTrack":if((b.videoId||b.video_id||"")===sF())throw new rF;if("style"in b){if("string"===typeof b.style){try{var d=_.Mo(b.style);c=d?d:null}catch(g){b=b.style||"";b=b.replace(/;\n}.*/,"");b=b.replace(/.*{/,"");b=b.split(/;\n/g);
c={};d=0;for(var e=b.length;d<e;++d){var f=b[d].split(/ = /);a=f.shift().trim();f=f.shift().trim();f=f.replace(/^"/,"");f=f.replace(/"$/,"");c[a]=f}}c.fontFamilyOption||(c.fontFamilyOption=_.bC[4]);uF(c,"fontSizeRelative");uF(c,"windowOpacity");uF(c,"backgroundOpacity");uF(c,"textOpacity");b=c.fontSizeRelative;void 0!==b&&(c.fontSizeIncrement=1>b?Math.floor(2*b-2+2E-15):Math.floor(4*b-4+2E-15))}throw new rF;}break;case "getSubtitlesTrack":throw new rF;case "getNowPlaying":this.vl();if(1081==this.player.getPlayerState())this.ul();
else throw new rF;break;case "skipAd":this.player.skipAd();break;case "loungeStatus":LF(this,b);break;case "forceDisconnect":qF(this);this.player.stop();this.connect();break;case "dismissAutoplay":throw new rF;case "setAutoplayMode":throw new rF;case "setPlaylistMode":throw new rF;case "onUserActivity":this.player.updateLastActiveTime();break;case "previous":this.player.previous();break;case "next":this.player.next();break;case "sendDebugCommand":if(b.debugCommand)throw new rF;break;case "reloadWatchNext":throw new rF;
case "updateRemoteTransactionStatus":(b=String(b.event||""))&&this.A.h("remote-transaction-status:updated",b);break;case "updateSignInStatus":c=String(b.event||"");a=String(b.deviceId||"");b=String(b.deviceDescription||"");c&&a&&b&&this.A.h("sign-in-status:updated",c,a,b);break;case "voiceCommand":c=String(b.text||"");a=String(b["unstable speech"]||"");switch(String(b.status)||""){case "INITIATED":this.A.h("voice-query:initiated");break;case "UPDATED":this.A.h("voice-query:updated",c,a);break;case "COMPLETED":this.A.h("voice-query:completed",
c);break;case "CANCELED":this.A.h("voice-query:canceled")}break;case "setImpactedSessionsServerEvent":b=b.serverEvent;if(!b)break;c=this.localStorage.get("mdx-impacted-sessions-server-events");_.hc(c)?c.push(b):c=[b];this.localStorage.set("mdx-impacted-sessions-server-events",c,15768E3);break;case "dpadCommand":if({UP:38,DOWN:40,LEFT:37,RIGHT:39,BACK:27,ENTER:13}[String(b.key||"")])throw new rF;}};var NF=function(a,b,c,d,e,f,g,h,l,n,q,t,w,y,B){MF.call(this,a,b,c,d,e,f,g,h,l,n,q,t,w,y);this.g=B};
_.G(NF,MF);_.k=NF.prototype;
_.k.tl=function(a,b){switch(a){case "dismissAutoplay":this.g.g(a,b);break;case "dpadCommand":this.g.g(a,b);break;case "forceDisconnect":this.g.g(a,b);break;case "getNowPlaying":this.g.g(a,b);break;case "getSubtitlesTrack":this.g.g(a,b);break;case "getVolume":this.g.g(a,b);break;case "loungeStatus":LF(this,b);break;case "next":this.g.g(a,b);break;case "onUserActivity":this.g.g(a,b);break;case "pause":this.g.g(a,b);break;case "play":this.g.g(a,b);break;case "previous":this.g.g(a,b);break;case "reloadWatchNext":this.g.g(a,
b);break;case "seekTo":this.g.g(a,b);break;case "sendDebugCommand":this.g.g(a,b);break;case "setAudioTrack":this.g.g(a,b);break;case "setAutoplayMode":this.g.g(a,b);break;case "setImpactedSessionsServerEvent":this.g.g(a,b);break;case "setPlaylist":this.g.g(a,b);break;case "setPlaylistMode":this.g.g(a,b);break;case "setSubtitlesTrack":this.g.g(a,b);break;case "setVideoQuality":this.g.g(a,b);break;case "setVolume":this.g.g(a,b);break;case "skipAd":this.g.g(a,b);break;case "stopVideo":this.g.g(a,b);
break;case "updatePlaylist":this.g.g(a,b);break;case "updateRemoteTransactionStatus":this.g.g(a,b);break;case "updateSignInStatus":this.g.g(a,b);break;case "voiceCommand":this.g.g(a,b)}};_.k.sendMessage=function(a,b){MF.prototype.sendMessage.call(this,a,b)};_.k.sb=function(){throw new xF;};_.k.ul=function(){throw new xF;};_.k.vl=function(){throw new xF;};var OF=function(){};_.k=OF.prototype;_.k.play=function(){throw new rF;};_.k.pause=function(){throw new rF;};_.k.stop=function(){throw new rF;};
_.k.seekTo=function(){throw new rF;};_.k.setVolume=function(){throw new rF;};_.k.getVolume=function(){throw new rF;};_.k.mute=function(){throw new rF;};_.k.unMute=function(){throw new rF;};_.k.isMuted=function(){throw new rF;};_.k.setAudioTrack=function(){throw new rF;};_.k.getCurrentTime=function(){throw new rF;};_.k.getDuration=function(){throw new rF;};_.k.getPlayerState=function(){throw new rF;};_.k.updatePlaylist=function(){throw new rF;};_.k.skipAd=function(){throw new rF;};
_.k.updateLastActiveTime=function(){throw new rF;};_.k.hasPrevious=function(){throw new rF;};_.k.previous=function(){throw new rF;};_.k.hasNext=function(){throw new rF;};_.k.next=function(){throw new rF;};_.k.like=function(){throw new rF;};_.k.flag=function(){throw new rF;};_.k.hj=function(){throw new rF;};
var PF=function(a){if(!a||100<a.length)return!1;for(var b=0;b<a.length;b++)if(255<a.charCodeAt(b))return!1;return!0},QF=function(a,b){a=a.g(b.length);for(var c="",d=0;d<b.length;d++)c+=b[d]+a[d];return c},RF=function(a,b){if(!PF(b))return"";var c=Math.floor(a.h()*b.length);b=QF(a,b);return b.slice(c)},SF=function(a,b){if(!PF(b))return"";a=QF(a,b);a=_.rk(a);b=Math.ceil(a.length/2);var c=a.slice(0,b);a=c.concat(GD(c,a.slice(b)));b=2%a.length;return _.Wc(a.slice(a.length-b).concat(a.slice(0,a.length-
b)))},TF=function(a,b){if(!PF(b))return"";b=SF(a,b);var c=new JD;c.update(b+a.g(2));return DD(c.digest())},UF=function(a,b,c,d){a.screen&&_.Fc(CF(a,"/pairing/register_pairing_code"),{Ob:{access_type:"permanent",app:d,pairing_code:b,screen_id:a.screen.id,screen_name:c,ropc:SF(a.B,b),se:TF(a.B,b),spc:RF(a.B,b)},method:"POST",format:"RAW",onSuccess:function(){},onError:function(){}})},VF=function(a,b,c,d){GF(a,function(){UF(a,b,c,d)})},WF=function(a){this.g=a};
WF.prototype.get=function(a,b){a=new _.Dl(a);var c=this.g.get(a);void 0!==c&&b&&this.g.set(a,c,b);return c};WF.prototype.set=function(a,b,c){this.g.set(new _.Dl(a),b,c)};WF.prototype.remove=function(a){this.g.remove(new _.Dl(a))};WF.prototype.flush=function(){};XF=function(){var a,b=new cF,c=null===(a=_.pd().mdxInitData)||void 0===a?void 0:a.mdxEnvironment;"DEV"===c?b.g="/api/loungedev":"STAGING"===c?b.g="/api/loungestaging":"SANDBOX"===c&&(b.g="/api/loungesandbox");return b};
YF=function(a){var b=a.h.get(_.QC);b||(b=_.uD(),a.h.set(_.QC,b));return b};
_.ZF=function(){var a=_.r.get(_.SC);if(a.g)return a.g;a.Z.tick("r_construction");var b=(0,a.j)(),c=!!_.D("enableLocalChannel",!1),d=XF(),e=new VE({id:YF(a),name:_.D("mdxDeviceLabel","YouTube on TV"),type:"LOUNGE_SCREEN",app:"lb-v4",theme:"cl",capabilities:"dsp"});e.j=0;a.g=new NF(function(f,g,h,l){return hD(f,g,h,l)},function(f,g){var h;if(h=c&&_.x.WebSocket)a:{try{var l=new _.Ga(f);var n=l.h;try{var q=-1!=n.indexOf(":")?new PE(n):new NE(n)}catch(t){q=null}}catch(t){h=!1;break a}h="ws"==l.A&&!!q&&
4==q.$b&&(q.A()||q.j())}return h?new AF(oD,rD,f,g):null},d,new WF(a.h),new OF,a.A,e,!!_.D("supportsMdxV3",!1),!1,function(){},tD,lD,function(){},new _.mC,b);return a.g};$F=function(){var a=_.ZF();return _.F(a.j.values()).concat(_.F(a.B.values())).map(function(b){return ZE(b)})};_.aG=function(a){return 1===a.h||2===a.h};_.m().w("sy2h");
var bG=_.p("MdxSessionService","wsJh0c");
var cG=["APP_SHUTDOWN","APP_SUSPENDED","SCREEN_RESET"],dG=function(){var a=this;this.h=_.L.R();this.Z=_.r.get(_.ky);this.g=!1;if(null===_.ov()){var b=_.r.get(_.$t);_.vD(b,function(){a.Al()&&(a.g=!0);a.disconnect("APP_SUSPENDED")});b.onResume(function(){var c=_.r.get(_.eD);c=_.aG(c)&&!c.g.isInAppServer();var d=!!a.md().length;if(a.g||c||d)a.g=!1,a.connect("Reconnecting on resume")});_.Yt(b,function(){a.disconnect("APP_SHUTDOWN")})}else _.C(Error("ib"))};_.k=dG.prototype;
_.k.connect=function(a){var b=this;this.Z.tick("r_connect_backend");var c=this.$d();return new _.Zg(function(d){_.ZF().connect(function(e,f){if(e)c!==b.$d()&&(b.Z.tick("r_connect_backend_success"),_.Eo(b.h,new _.O("mdx-session:connected"),void 0));else{var g=Error("jb`"+a+"`"+f);f&&!cG.includes(f)&&_.C(g,"WARNING")}d({success:e,disconnectReason:f})})})};_.k.disconnect=function(a){_.ZF().disconnect(a);eG(this,a)};_.k.zg=function(){qF(_.ZF());eG(this,"SCREEN_RESET")};_.k.Al=function(){return!!this.$d()};
_.k.xl=function(a,b){return IF(_.ZF(),a,b)};_.k.$d=function(){var a=_.ZF(),b=a.C.screen;return a.h&&b?b.id||null:null};_.k.Ji=function(){return $F()};_.k.md=function(){return _.ZF().md()};_.k.wl=function(a,b){var c=_.ZF();if(!c.G&&(b=KF(c,b))){var d=new VE;d.id=a;d.g=b;c.G=d}};_.k.Ki=function(a){var b=_.ZF();VF(b.C,a,b.screen.name,b.screen.app)};_.k.Ph=function(a){var b=this;this.connect("ReversePairingCode").then(function(c){c.success&&b.Ki(a)})};_.k.yl=function(){return _.lF(_.ZF())};_.k.Zg=function(){return new Set(_.ZF().kf)};
_.k.zl=function(){return _.ZF().hh};var eG=function(a,b){_.Eo(a.h,new _.O("mdx-session:disconnected"),{reason:b})},fG=function(){dG.apply(this,arguments)};_.G(fG,dG);_.k=fG.prototype;_.k.connect=function(){return _.LA("Wrong environment for new mdx")};_.k.zg=function(){};_.k.Al=function(){return!1};_.k.xl=function(){return function(){}};_.k.$d=function(){return null};_.k.Ji=function(){return[]};_.k.md=function(){return[]};_.k.wl=function(){};_.k.Ki=function(){};_.k.Ph=function(){};_.k.yl=function(){return[]};
_.k.Zg=function(){return new Set};_.k.zl=function(){return new Set};_.gG=_.K(bG,function(){return null===_.ov()?new dG:new fG});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.hG=function(a){return!!(a&&a.watchEndpointRemotePlaybackSupportedConfigs&&a.watchEndpointRemotePlaybackSupportedConfigs.remotePlaybackConfig&&a.watchEndpointRemotePlaybackSupportedConfigs.remotePlaybackConfig.isMdxPlayback)};_.m().w("sy2m");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.lG=function(a,b){b=void 0===b?window.location:b;var c=new _.Ga(b.href),d=_.Ka(new _.Ga("/tv"),a);a=iG.some(function(f){return d.g.wa(f)})?_.jG:_.jG.concat(_.kG);a=_.A(a);for(var e=a.next();!e.done;e=a.next())e=e.value,c.g.wa(e)&&!d.g.wa(e)&&_.ij(d,e,c.g.la(e));(c=_.pd().reloadToken)&&_.Ha(d,"reload_token",c);_.Ha(d,"hrld",1);_.If(b,d.toString())};
_.mG=function(a){var b=_.Ui(a);if("undefined"==typeof b)throw Error("F");var c=new _.La(null,void 0);a=_.Ti(a);for(var d=0;d<b.length;d++){var e=b[d],f=a[d];Array.isArray(f)?_.hj(c,e,f):c.add(e,f)}return c};_.m().w("sy2l");
var iG;_.jG=["additionalDataUrl","cert_scope","reload_token","sig","start_time"];
_.kG="debugjs expflag forced_experiments conditional_experiments absolute_experiments use_toa mods".split(" ").concat(_.F(Object.values({Sq:"automationRoutine",Jr:"env_ctvEnvironment",Zr:"env_disableStartupDialog",ks:"env_enableVoice",ls:"env_enableWebRtc",ns:"env_enableWebSpeechApi",Ts:"env_forceFullAnimation",Qs:"forced_experiments",Ss:"env_adsUrl",rv:"env_hideWatermark",Mw:"env_isLimitedAnimation",Nw:"env_isLimitedMemory",sA:"launch",xA:"mloader",TA:"oxredir",rB:"env_requestVideoQuality",MB:"env_showConsole",
NB:"env_showDeviceStorage",JB:"env_sherlog",fC:"env_supportsDirectSignIn",gC:"env_supportsPointerRemote",hC:"env_supportsTouchpad",yC:"env_useGaiaSandbox",IB:"env_playerUrl"})));iG=["loader","mloader","clearstickymloader"];

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy2k");
var nG=_.p("ClientSettingService","LD18oe");
var oG,qG,tG,uG;oG={};_.pG=(oG.CAPTION_FONT_FAMILY=!0,oG.CAPTION_TEXT_COLOR=!0,oG.CAPTION_BACKGROUND_COLOR=!0,oG.CAPTION_WINDOW_COLOR=!0,oG.CAPTION_EDGE_TYPE=!0,oG.CAPTION_TEXT_OPACITY=!0,oG.CAPTION_BACKGROUND_OPACITY=!0,oG.CAPTION_WINDOW_OPACITY=!0,oG.CAPTION_TEXT_SIZE=!0,oG.CAPTION_STYLE_VIDEO_OVERRIDE=!0,oG.CAPTION_SHOW_SAMPLE_SUBTITLE=!0,oG.CAPTION_HIDE_SAMPLE_SUBTITLE=!0,oG.PLAYBACK_QUALITY=!0,oG.PLAYBACK_SPEED=!0,oG.PLAYBACK_AUDIO_TRACK=!0,oG);qG={};
_.rG=(qG.AUTONAV=!0,qG.ENABLE_HIGH_CONTRAST_MODE=!1,qG.ENABLE_SOUND=!0,qG.ENABLE_MUSIC_PREVIEWS=!0,qG.MDX_AUTOPLAY_ENABLED=!0,qG.SAFETY_MODE=!1,qG);_.sG=new _.Dl("display-language-changed-toast");tG=function(){this.storage=_.r.get(_.vd);this.clientData=_.pd();this.g={}};
uG=function(a){switch(a){case "AUTONAV":a="autoplay-enabled";break;case "ENABLE_HIGH_CONTRAST_MODE":a="high-contrast-enabled";break;case "ENABLE_SOUND":a="sound-enabled";break;case "ENABLE_MUSIC_PREVIEWS":a="inline-playback-enabled";break;case "MDX_AUTOPLAY_ENABLED":a="mdx-autoplay-enabled";break;case "SAFETY_MODE":a="safe-mode-enabled"}return new _.Dl(a)};tG.prototype.isEnabled=function(a){var b=uG(a),c=this.g[b.key]||this.storage.get(b);void 0===c&&(c=_.rG[a]);this.g[b.key]=c;return!!c};
tG.prototype.setValue=function(a,b){a=uG(a);this.g[a.key]=b;this.storage.set(a,b)};_.vG=_.K(nG,function(){return new tG});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var wG,zG,CG,HG,FG;wG=function(a){var b=_.Wa(window.location,"env_playerUrl"),c="/"===a[0]&&"/"!==a[1];return b&&b===a||c?_.Oe(a):null};zG=function(a,b,c,d){c=void 0===c?{}:c;return xG(new yG({target:a,window:b,playerVars:c,context:d}))};
CG=function(){var a=window.loadParams&&window.loadParams.csp_nonce||null,b=_.AG(),c=_.pd(),d=_.r.get(_.vG);if(/^xboxone\b/i.test(_.Za().model))var e={};else{e=_.r.get(_.Oa).system;var f;e.isSupported()&&(f=e.getVideoContainerSizeOverride());f=f||window.innerWidth+"x"+window.innerHeight;e={video_container_override:f}}return Object.assign(Object.assign(Object.assign(Object.assign({},BG),b),e),{csp_nonce:a,enable_safety_mode:d.isEnabled("SAFETY_MODE"),forced_experiments:c.forcedExperiments,hl:c.clientLanguage,
deviceHasDisplay:!0,force_csi_on_gel:0,innertube_context_client_version:c.clientVersion})};_.AG=function(){var a=_.pd(),b=_.Za();return{c:a.clientName,cver:a.clientVersion,ctheme:a.clientTheme,cbrand:b.brand,cmodel:b.model,cos:b.os,cosver:b.os_version,cnetwork:b.network,cplatform:b.platform,cbr:b.browser,cbrver:b.browser_version,label:a.clientLabel,oE:a.startTime}};_.EG=function(){return DG.Ad};
HG=function(a,b){var c=_.ov(),d;c?d=c.getPlayer():d=zG(a,window,CG(),b).then(function(e){return FG(e)});return d.then(function(e){_.GG=e;DG.resolve(e);return e})};FG=function(a){return new _.Zg(function(b){var c=function(d){b(d);_.r.get(_.ky).tick("pem_r");d.removeEventListener("onReady",c)};a.addEventListener("onReady",c)})};_.m().w("sy21");
/*

 Copyright The Closure Library Authors.
 SPDX-License-Identifier: Apache-2.0
*/
var MG=function(a,b){var c=b||{};b=c.document||document;var d=_.Ne(a),e=_.Mg(document,"SCRIPT"),f={Xl:e,Sb:void 0},g=new _.ci(IG,f),h=null,l=null!=c.timeout?c.timeout:5E3;0<l&&(h=window.setTimeout(function(){JG(e,!0);g.h(new KG(1,"Timeout reached for loading script "+d))},l),f.Sb=h);e.onload=e.onreadystatechange=function(){e.readyState&&"loaded"!=e.readyState&&"complete"!=e.readyState||(JG(e,c.rE||!1,h),g.callback(null))};e.onerror=function(){JG(e,!0,h);g.h(new KG(0,"Error while loading script "+
d))};f=c.attributes||{};_.v.extend(f,{type:"text/javascript",charset:"UTF-8"});_.kt(e,f);_.Hf(e,a);LG(b).appendChild(e);return g},LG=function(a){var b;return(b=(a||document).getElementsByTagName("HEAD"))&&0!=b.length?b[0]:a.documentElement},IG=function(){if(this&&this.Xl){var a=this.Xl;a&&"SCRIPT"==a.tagName&&JG(a,!0,this.Sb)}},JG=function(a,b,c){null!=c&&_.x.clearTimeout(c);a.onload=_.$d;a.onerror=_.$d;a.onreadystatechange=_.$d;b&&window.setTimeout(function(){a&&a.parentNode&&a.parentNode.removeChild(a)},
0)},KG=function(a,b){var c="Jsloader error (code #"+a+")";b&&(c+=": "+b);_.le.call(this,c);this.code=a};_.ke(KG,_.le);
var BG={autoplay:0,BASE_YT_URL:null,canplaylive:!0,cc_load_policy:2,controls:0,csi_service_name:"youtube_tv",disablekb:1,el:"leanback",iv_load_policy:3,ps:"leanback",store_user_volume:!1};
var NG=function(){var a=Error.call(this,"The player binary URL was not specified.");this.message=a.message;"stack"in a&&(this.stack=a.stack)};_.G(NG,Error);var OG=function(){var a=Error.call(this,'Expected the player binary URL to begin with "/" or point to a valid custom player binary (see: go/debug-player-on-tvs).');this.message=a.message;"stack"in a&&(this.stack=a.stack)};_.G(OG,Error);
var yG=function(a){this.params=a;this.Z=_.r.get(_.ky)},PG=function(a){var b=_.u("WEB_PLAYER_CONTEXT_CONFIGS");return b&&a.params.context&&b[a.params.context]},QG=function(a){var b,c,d=PG(a);return null!==(b=null===d||void 0===d?void 0:d.jsUrl)&&void 0!==b?b:null===(c=a.params.window.loadParams)||void 0===c?void 0:c.player_url},xG=function(a){var b=QG(a);if(!b)return _.LA(new NG);b=wG(b);if(!b)return _.LA(new OG);var c=a.params.window.loadParams&&a.params.window.loadParams.csp_nonce||"";a.Z.tick("pljs_rq");
return MG(b,{timeout:3E4,attributes:{nonce:c}}).then(function(){a.Z.tick("pljs_r");var d=(d=PG(a))?_.lb("yt.player.instances.createFromWebPlayerContextConfig",a.params.window)(a.params.target,d):null;d||(d=a.params.window.swfConfig,Object.assign(d.args,a.params.playerVars),d.assets=Object.assign(Object.assign({},d.assets),{css:null}),d=_.lb("yt.player.instances.create",a.params.window)(a.params.target,d));return d})};
_.RG=Object.freeze({Jd:!1,clickTrackingParams:void 0,Oh:[],Uj:!1,forceReloadPlayback:!1,isFling:!1,Pk:!1,Qk:!1,isLivingRoomDeeplink:!1,isReplay:!1,startTime:NaN,pm:void 0,watchEndpoint:void 0,watchPlaylistEndpoint:void 0});
var DG;_.GG=null;DG=_.Op();_.SG=_.wx(HG);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy23");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var TG,UG,WG,ZG,aH;TG=function(a){return(a=_.as(void 0===a?0:a))?new _.Gx({veType:a,youtubeData:void 0}):null};UG=function(a,b,c,d,e){var f=(0,_.Kx)();b={csn:f,pageVe:_.Hx(new _.Gx({veType:b,youtubeData:void 0}))};c&&(b.implicitGesture={parentCsn:c.clientScreenNonce,gesturedVe:_.Hx(c.visualElement)});d&&(b.cloneCsn=d);c={xe:e,Ed:f};a?_.fc("screenCreated",b,a,c):_.hd("screenCreated",b,c);_.Nr(_.xx,new _.Jx(f));return f};
_.VG=function(a,b,c,d){c={csn:b,ve:_.Hx(c),clientData:d};d={xe:_.es(b),Ed:b};"UNDEFINED_CSN"==b?_.Fx("visualElementStateChanged",c,d):a?_.fc("visualElementStateChanged",c,a,d):_.hd("visualElementStateChanged",c,d)};
WG=function(a,b,c,d){c={csn:b,parentVe:_.Hx(c),childVes:(0,_.pe)(d,function(f){return _.Hx(f)})};d=_.A(d);for(var e=d.next();!e.done;e=d.next())e=_.Hx(e.value),(_.v.isEmpty(e)||!e.trackingParams&&!e.veType)&&_.nd(Error("Na"),"WARNING",void 0,void 0,void 0);d={xe:_.es(b),Ed:b};"UNDEFINED_CSN"==b?_.Fx("visualElementAttached",c,d):a?_.fc("visualElementAttached",c,a,d):_.hd("visualElementAttached",c,d)};
_.XG=function(a,b){a=a&&a.watchEndpointSupportedAuthorizationTokenConfig&&a.watchEndpointSupportedAuthorizationTokenConfig.videoAuthorizationToken&&a.watchEndpointSupportedAuthorizationTokenConfig.videoAuthorizationToken.credentialTransferTokens||[];for(var c=0;c<a.length;++c)if(a[c].scope===b)return a[c].token||void 0};
_.YG=function(a){if(a.watchEndpoint){var b=_.XG(a.watchEndpoint,"VIDEO");if(b)return{token:b,videoId:a.watchEndpoint.videoId}}else if(a.watchPlaylistEndpoint&&(b=_.XG(a.watchPlaylistEndpoint,"PLAYLIST")))return{token:b,playlistId:a.watchPlaylistEndpoint.playlistId}};ZG=function(){var a=_.Lx.R();a.clear();a.h=_.cs()};_.$G=function(a,b){var c=0;return function(d){_.x.clearTimeout(c);var e=arguments;c=_.x.setTimeout(function(){a.apply(void 0,e)},b)}};aH=1;
_.bH=function(a,b,c,d){var e=aH++;return new _.Gx({veType:a,veCounter:e,elementIndex:c,dataElement:b,youtubeData:d})};_.m().w("sy24");
var cH,eH,hH,iH,kH;cH=["commandMetadata","webCommandMetadata","rootVe"];eH=function(){this.A=[];this.B=[];this.g=!1;this.j=new Map;if(_.fb("c3_client_side_screens")){var a=_.bH(49980),b=_.bH(49981);_.ab("historyVes",{backButtonVe:a,forwardButtonVe:b},void 0);_.dH(this,a);_.dH(this,b)}_.fb("web_screen_manager_use_default_client")||(this.client=_.gd)};_.fH=function(){eH.instance||(eH.instance=new eH);return eH.instance};
eH.prototype.C=function(a,b){var c=_.v.Cn.apply(_.v,[a].concat(_.F(cH)));if(c){var d;a.clickTrackingParams&&(d=_.Ix(a.clickTrackingParams));_.gH(this,c,d,b,void 0,void 0,_.YG(a))}else _.nd(new _.Vj("Error: Trying to create a new screen without a rootVeType",a))};_.gH=function(a,b,c,d,e,f,g){a.A=[];a.B=[];d?hH(a,b,d,c,e,f,g):iH(a,b,c,e,f,g)};
_.jH=function(a,b,c){b.then(function(d){var e;a.g&&a.h&&a.h();var f=_.cs(c),g=TG(c);if(f&&g){d.csn=f;var h=null===(e=d.response)||void 0===e?void 0:e.trackingParams;h&&WG(a.client,f,g,[_.Ix(h)]);d.playerResponse&&d.playerResponse.trackingParams&&WG(a.client,f,g,[_.Ix(d.playerResponse.trackingParams)])}})};_.lH=function(a,b,c){b=_.Ix(b);kH(a,b,c,void 0);return b};
_.mH=function(a,b,c){if(!b.veType)return _.nd(new _.Vj("Error: Trying to graft a client VE without a veType.")),null;b=_.bH(b.veType,b.visualElement,b.elementIndex,b.g);return kH(a,b,c,void 0)?b:null};_.dH=function(a,b){a.j.set(b,void 0);"UNDEFINED_CSN"===_.cs(void 0)||a.g||kH(a,b,void 0,void 0)};
hH=function(a,b,c,d,e,f,g){a.g=!0;a.h=function(){iH(a,b,d,e,f,g);var l=TG(e);if(l){for(var n=_.A(a.A),q=n.next();!q.done;q=n.next())q=q.value,kH(a,q[0],q[1]||l,e);l=_.A(a.B);for(n=l.next();!n.done;n=l.next())n=n.value,_.nH(a,n[0],n[1])}};d||_.cs(e)||a.h();c=_.A(c);for(var h=c.next();!h.done;h=c.next())_.jH(a,h.value,e)};
iH=function(a,b,c,d,e,f){var g=_.cs(d);c=c||TG(d);var h;c&&g&&"UNDEFINED_CSN"!==g&&(h={clientScreenNonce:g,visualElement:c});e=UG(a.client,b,h,e,f);_.fs(e,b,d,f);ZG();a.g=!1;a.h=void 0;var l=TG(d);a.j.forEach(function(n,q){n===d&&l&&kH(a,q,l,d)})};_.nH=function(a,b,c){if(a.g)a.B.push([b,c]);else{b=_.Ix(b);var d=_.cs();d&&_.VG(a.client,d,b,c)}};kH=function(a,b,c,d){if(a.g&&!d)return a.A.push([b,c]),!0;var e=_.cs(d);c=c||TG(d);return e&&c?(WG(a.client,e,c,[b]),!0):!1};

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy2e");
_.oH=function(){this.g=null;this.F=[]};_.oH.prototype.A=function(){};_.oH.prototype.C=function(){};_.pH=function(a,b){a.F.push(b)};_.oH.prototype.sendMessage=function(a,b){this.g&&this.g.sendMessage(a,b)};

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy2i");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy2j");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("l25Skf");
var qH=_.p("MdxInitConfig","l25Skf");
var rH=function(){this.g=null};rH.prototype.init=function(a){this.g=a.tf};rH.prototype.tf=function(){return(0,this.g)()};_.sH=_.K(qH,function(){return new rH});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var tH=function(a){if((a=a.contents&&a.contents.singleColumnWatchNextResults&&a.contents.singleColumnWatchNextResults.autoplay&&a.contents.singleColumnWatchNextResults.autoplay.autoplay&&a.contents.singleColumnWatchNextResults.autoplay.autoplay.sets)&&0!==a.length)return(a=a.find(function(b){return b&&"NORMAL"===b.mode}))&&a.autoplayVideoRenderer&&a.autoplayVideoRenderer.mdxAutoplayVideoRenderer};_.m().w("sy2b");
var uH=_.p("MdxAutoplayService","BAYOKb");
var xH=function(){_.oH.apply(this,arguments);var a=this;this.H=_.L.R();this.G=_.r.get(_.vG);this.S=_.Wu();this.h=_.r.get(_.sH).tf();this.J=_.r.get(_.gG);this.D=null;this.B=!1;this.j=null;this.V=function(){_.vH(a)};this.Y=function(){var b=tH(a.h.watchNextResponse);b=b&&b.videoId;a.sendMessage("autoplayUpNext",b?{videoId:b}:{})};this.P=function(b){a.B=!0;a.j=setTimeout(function(){wH(a)},1E3*b.Sj);setTimeout(function(){a.sendMessage("nowAutoplaying",{videoId:b.videoId,timeout:b.Sj})},0)};this.N=function(){a.sendMessage("autoplayDismissed",
{});wH(a)}},wH;_.G(xH,_.oH);_.yH=function(a){return a.J.Zg().has("atp")&&a.G.isEnabled("MDX_AUTOPLAY_ENABLED")};xH.prototype.A=function(){_.pH(this,_.N(this.H,new _.O("mdx-session:remoteStatusChanged"),this,this.V));_.pH(this,this.h.g("watchNextResponseChanged",this.Y));_.pH(this,this.h.g("autoplayCountdownStarted",this.P));_.pH(this,this.h.g("autoplayCountdownDismissed",this.N))};xH.prototype.C=function(){_.vH(this)};
_.vH=function(a){var b=a.J.Zg().has("atp")?_.yH(a)?"ENABLED":"DISABLED":"UNSUPPORTED";b!==a.D&&(a.D=b,a.g&&a.sendMessage("onAutoplayModeChanged",{autoplayMode:b}),!a.B&&a.h.h()&&_.Eo(a.H,new _.O("mdx-watch:updatePlaylist"),{}))};wH=function(a){a.j&&(clearTimeout(a.j),a.j=null);a.B=!1};_.zH=_.K(uH,function(){return new xH});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy3v");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var uv,Cv,Gv,Iv,Jv,Kv,xv;uv=function(a){a=a.split("||");return{Vh:a[0],ownerId:a[1]||a[0],gaiaDelegationType:a[1]?3:1}};_.vv=function(a){try{var b=a.supportedTokens.find(function(c){return!!c.datasyncIdToken});return uv(b.datasyncIdToken.datasyncIdToken)}catch(c){}};_.wv=function(a){for(var b=[],c=0;c<arguments.length;++c)b[c]=arguments[c];return _.Wo(b)};
_.yv=function(a){for(var b=[],c=0;c<arguments.length;++c)b[c]=arguments[c];var d=c=void 0;_.Yo(b[b.length-1])&&(d=b.pop());"function"===typeof b[b.length-1]&&(c=b.pop());1===b.length&&(0,_.qp)(b[0])&&(b=b[0]);return(d?_.xr(b,d):new _.Zo(_.qq(b))).Sa(new xv(c))};_.Av=function(a){return function(b){return b.Sa(new zv(a))}};Cv=function(a){a=void 0===a?null:a;return function(b){return b.Sa(new Bv(a))}};_.Ev=function(){return function(a){return a.Sa(new Dv)}};Gv=function(){return function(a){return a.Sa(new Fv)}};
Iv=function(a,b){var c=!1;2<=arguments.length&&(c=!0);return function(d){return d.Sa(new Hv(a,b,c))}};Jv=function(a,b){return 2<=arguments.length?function(c){return _.wv(Iv(a,b),Gv(),Cv(b))(c)}:function(c){return _.wv(Iv(function(d,e,f){return a(d,e,f+1)}),Gv())(c)}};Kv=function(a,b,c){if(0===c)return[b];a.push(b);return a};xv=function(a){this.g=a};xv.prototype.call=function(a,b){return b.subscribe(new _.Uq(a,this.g))};var zv=function(a){this.predicate=a};
zv.prototype.call=function(a,b){return b.subscribe(new _.Wq(a,this.predicate,void 0))};var Bv=function(a){this.defaultValue=a};Bv.prototype.call=function(a,b){return b.subscribe(new _.cr(a,this.defaultValue))};var Dv=function(){this.total=1;if(0>this.total)throw new _.Xq;};Dv.prototype.call=function(a,b){return b.subscribe(new _.dr(a,this.total))};var Fv=function(){this.total=1;if(0>this.total)throw new _.Xq;};Fv.prototype.call=function(a,b){return b.subscribe(new _.er(a,this.total))};
var Hv=function(a,b,c){this.h=a;this.seed=b;this.g=void 0===c?!1:c};Hv.prototype.call=function(a,b){return b.subscribe(new _.fr(a,this.h,this.seed,this.g))};_.m().w("sy2o");
var Lv=_.p("KabukiAccountsListService","J6GTmf");
var Mv={accountReadMask:{returnBrandAccounts:!0,returnOwner:!0}},Nv=function(){this.ra=_.r.get(_.Fu)},Ov,Qv,Sv;Nv.prototype.Od=function(a,b){b=void 0===b?!1:b;return _.hp(a).g(Ov(this,b),Jv(Kv,[]),_.dp(function(c){return _.Vt(c)}))};Ov=function(a,b){return _.wv(_.ip(function(c){return _.v.la(c)},1),_.ip(function(c){return _.Pv(a,c,b)},1),_.dp(function(c){return Array.from(c.values())}))};
_.Rv=function(a,b,c,d){return a.ra.fetch({path:"/youtubei/v1/account/accounts_list",headers:{Authorization:"Bearer "+c},payload:(void 0===d?0:d)?Mv:{}}).g(Qv(b))};_.Pv=function(a,b,c){return a.ra.fetch({path:"/youtubei/v1/account/accounts_list",identity:b,payload:(void 0===c?0:c)?Mv:{}}).g(Qv(b.identityCredentials),_.Ar(function(){return _.yr(new Map)}))};Qv=function(a){return _.dp(function(b){return Sv(b,a)})};
Sv=function(a,b){try{var c=a.contents[0].accountSectionListRenderer.contents[0].accountItemSectionRenderer.contents}catch(d){c=[]}return c.reduce(function(d,e){var f;a:{if(f=e.accountItem&&e.accountItem.serviceEndpoint&&e.accountItem.serviceEndpoint.selectActiveIdentityEndpoint)if(f=_.vv(f)){f={identityType:"CORE_ID",gaiaDelegationType:f.gaiaDelegationType,ownerObfuscatedGaiaId:f.ownerId,effectiveObfuscatedGaiaId:f.Vh,identityCredentials:b};break a}f=void 0}f&&e.accountItem&&d.set(f.effectiveObfuscatedGaiaId,
{isActive:!1,identity:f,accountItemRenderer:e.accountItem});return d},new Map)};_.Tv=_.K(Lv,function(){return new Nv});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var AH;AH=function(){return["enableBrandSelectorAfterDirectSignIn","enableBrandSelectorAfterSeamlessSignIn","enableBrandSelectorAfterUrlSignIn"].some(function(a){return!!_.D(a,!1)})};_.BH=function(a){if(a.g)return _.I(a.g);if(a.j)return a.j;a.j=a.h.Hi().then(function(b){a.g=b;a.j=void 0;return a.g});return a.j};_.m().w("sy30");
var CH=_.p("AccountsListService","WaIIqe");
var DH=function(){this.qa=_.r.get(_.tv);this.g=_.r.get(_.Tv);this.h=_.r.get(_.nv)};DH.prototype.Od=function(a){a=void 0===a?AH():a;return _.yv(_.hp(this.qa.get()),this.g.Od(_.BH(this.h),a)).g(_.dp(function(b){b=_.A(b);var c=b.next().value;b=b.next().value;b.forEach(function(d){_.sr(c)&&d.identity.effectiveObfuscatedGaiaId===c.effectiveObfuscatedGaiaId?(d.isActive=!0,d.accountItemRenderer.isSelected=!0):(d.isActive=!1,d.accountItemRenderer.isSelected=!1)});return b}))};_.EH=_.K(CH,function(){return new DH});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy4m");
var FH=_.p("Cache","WSMqd");
var GH=function(){this.storage=new Map},IH,HH;GH.prototype.get=function(a){var b=a.identity,c=a.path;a=a.payload;if(!c||!a||!b)return null;c=c+":"+JSON.stringify(a);c=this.storage.get(c);return c?c.bk<=_.Jo()?(HH(this),null):_.ur(c.identity,b)?c.he:(IH(this,c.identity),null):null};
GH.prototype.set=function(a,b){var c=a.identity,d=a.path;a=a.payload;var e,f;d&&a&&c&&_.D("enablePageServiceCache",!0)&&(d=d+":"+JSON.stringify(a),(a=null===(f=null===(e=b.response)||void 0===e?void 0:e.responseContext)||void 0===f?void 0:f.maxAgeSeconds)?(a=_.Jo()+1E3*a,this.storage.set(d,{identity:c,bk:a,he:b})):this.storage.delete(d))};GH.prototype.xa=function(){return this.storage.size};IH=function(a,b){a.storage.forEach(function(c,d){_.ur(c.identity,b)&&a.storage.delete(d)})};
HH=function(a){var b=_.Jo();a.storage.forEach(function(c,d){c.bk<=b&&a.storage.delete(d)})};_.JH=_.K(FH,function(){return new GH});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy3u");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("CFMdP");
var KH=_.p("MdxSessionLoggingService","CFMdP");
var MH;_.LH=new _.Dl("mdx-impacted-sessions-server-events");MH=function(){this.localStorage=_.r.get(_.vd)};_.NH=_.K(KH,function(){return new MH});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var PH,QH,RH,SH,TH,UH,VH,WH,XH,aI,cI,jI;_.OH=function(){var a=document;if("visibilityState"in a)return a.visibilityState;var b=_.ek+"VisibilityState";if(b in a)return a[b]};PH=function(){return _.fb("csi_use_time_origin")&&_.at.timeOrigin?Math.floor(_.at.timeOrigin):_.at.timing.navigationStart};QH=function(a){return Math.round(PH()+a)};
RH=function(){var a;if(_.fb("csi_use_performance_navigation_timing")){var b,c,d,e=null===(d=null===(c=null===(b=null===(a=null===_.at||void 0===_.at?void 0:_.at.getEntriesByType)||void 0===a?void 0:a.call(_.at,"navigation"))||void 0===b?void 0:b[0])||void 0===c?void 0:c.toJSON)||void 0===d?void 0:d.call(c);e?(e.requestStart=QH(e.requestStart),e.responseEnd=QH(e.responseEnd),e.redirectStart=QH(e.redirectStart),e.redirectEnd=QH(e.redirectEnd),e.domainLookupEnd=QH(e.domainLookupEnd),e.connectStart=QH(e.connectStart),
e.connectEnd=QH(e.connectEnd),e.responseStart=QH(e.responseStart),e.secureConnectionStart=QH(e.secureConnectionStart),e.domainLookupStart=QH(e.domainLookupStart),e.isPerformanceNavigationTiming=!0,a=e):a=_.at.timing}else a=_.at.timing;return a};SH=function(){if(_.at.getEntriesByType){var a=_.at.getEntriesByType("paint");if(a=_.se(a,function(b){return"first-paint"===b.name}))return QH(a.startTime)}a=_.at.timing;return a.Qo?Math.max(0,a.Qo):0};
TH=function(){var a=_.Es(void 0);requestAnimationFrame(function(){setTimeout(function(){a===_.Es(void 0)&&_.ct("ol",void 0,void 0)},0)})};UH=function(){switch(_.OH()){case "hidden":return 0;case "visible":return 1;case "prerender":return 2;case "unloaded":return 3}return-1};
VH=function(){var a=window.location.protocol,b=_.at.getEntriesByType("resource");b=(0,_.oe)(b,function(c){return 0==c.name.indexOf(a+"//fonts.gstatic.com/s/")});(b=(0,_.qe)(b,function(c,d){return d.duration>c.duration?d:c},{duration:0}))&&0<b.startTime&&0<b.responseEnd&&(_.ct("wffs",QH(b.startTime)),_.ct("wffe",QH(b.responseEnd)))};
WH=function(a,b){a=document.querySelector(a);if(!a)return!1;var c="",d=a.nodeName;"SCRIPT"==d?(c=a.src,c||(c=a.getAttribute("data-timing-href"))&&(c=window.location.protocol+c)):"LINK"==d&&(c=a.href);_.Zd()&&a.setAttribute("nonce",_.Zd());return c?(a=_.at.getEntriesByName(c))&&a[0]&&(a=a[0],c=PH(),_.ct("rsf_"+b,c+Math.round(a.fetchStart)),_.ct("rse_"+b,c+Math.round(a.responseEnd)),void 0!==a.transferSize&&(b=_.Ds(void 0),c=_.Ws(void 0),"rc"in b||"rc"in c||_.Zs("rc",""),0===a.transferSize))?!0:!1:
!1};XH=function(a){return!!a&&!a.params&&("default"===a.browseId||"FEtopics"===a.browseId)};_.YH=function(a){return!!a&&!!a.signalNavigationEndpoint&&"ACCOUNT_SETTINGS"===a.signalNavigationEndpoint.signal};
_.ZH=function(a){return _.Tu(this,function c(){var d,e,f,g,h,l,n,q,t,w,y,B,z,H,M,aa,ya,ua,wb,rc,dd,Tb,pb,ed,Gf,ec,Xs;return _.Su(c,function(Na){if(a.browseEndpoint){a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/browse",webPageType:"WEB_PAGE_TYPE_BROWSE",rootVe:6827}};d=_.r.get(_.NH);var ae;(ae=d.localStorage.get(_.LH))&&ae.length?(ae={mdxImpactedSessionsServerEvents:ae},d.localStorage.remove(_.LH)):ae=void 0;e=ae||{};e.zylonLeftNav=!0;f={browseId:a.browseEndpoint.browseId,params:a.browseEndpoint.params,
context:{client:{tvAppInfo:e}}};return Na.return({type:0,command:a,form:f})}if(a.searchEndpoint)return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/search",webPageType:"WEB_PAGE_TYPE_SEARCH",rootVe:4724}},Na.return({type:0,command:a});if(_.YH(a))return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/account/accounts_list",webPageType:"WEB_PAGE_TYPE_ACCOUNTS",rootVe:23462}},Na.return({type:0,command:a});if(a.applicationSettingsEndpoint)return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/account/get_setting",
webPageType:"WEB_PAGE_TYPE_SETTINGS",rootVe:12924}},Na.return({type:0,command:a});if(a.watchEndpoint||a.watchPlaylistEndpoint){a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/next",webPageType:"WEB_PAGE_TYPE_WATCH",rootVe:3832}};g={};a.watchEndpoint&&a.watchEndpoint.videoId&&(g.videoId=a.watchEndpoint.videoId);h=a.watchEndpoint||a.watchPlaylistEndpoint;h.playlistId&&(g.playlistId=h.playlistId);h.params&&(g.params=h.params);l=_.r.get(_.vd);n=!!l.get(_.xd);n||(g.racyCheckOk=!0,g.contentCheckOk=
!0);if(q=null===_.ov()){if(h){if(ae=h.watchEndpointSupportedAuthorizationTokenConfig&&h.watchEndpointSupportedAuthorizationTokenConfig.videoAuthorizationToken&&h.watchEndpointSupportedAuthorizationTokenConfig.videoAuthorizationToken.credentialTransferTokens)g.context=g.context||{},g.context.user=g.context.user||{},g.context.user.credentialTransferTokens=ae;_.hG(h)&&(g.isMdxPlayback=!0)}_.yH(_.r.get(_.zH))&&(g.enableMdxAutoplay=!0)}return Na.return({type:0,command:a,form:g})}if(a.feedbackEndpoint)return a.commandMetadata=
{webCommandMetadata:{url:"/youtubei/v1/feedback"}},t=a.feedbackEndpoint.feedbackToken,w={feedbackTokens:t?[t]:[]},Na.return({type:1,command:a,form:w});if(a.subscribeEndpoint)return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/subscription/subscribe"}},y={channelIds:a.subscribeEndpoint.channelIds},Na.return({type:1,command:a,form:y});if(a.unsubscribeEndpoint)return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/subscription/unsubscribe"}},B={channelIds:a.unsubscribeEndpoint.channelIds},
Na.return({type:1,command:a,form:B});if(a.pauseWatchHistoryEndpoint)return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/history/pause_watch_history"}},Na.return({type:1,command:a});if(a.resumeWatchHistoryEndpoint)return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/history/resume_watch_history"}},Na.return({type:1,command:a});if(a.playlistEditEndpoint)return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/browse/edit_playlist"}},z=a.playlistEditEndpoint,H=z.playlistId,
M=z.actions,aa={playlistId:H,actions:M},Na.return({type:1,command:a,form:aa});if(a.flagEndpoint)return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/flag/flag"}},ya=a.flagEndpoint,ua=ya.flagAction,wb={action:ua},Na.return({type:1,command:a,form:wb});if(a.ypcGetCartEndpoint)return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/ypc/get_cart"}},rc={offerParams:a.ypcGetCartEndpoint.offerParams},Na.return({type:1,command:a,form:rc});if(a.likeEndpoint&&"LIKE"===a.likeEndpoint.status)return a.commandMetadata=
{webCommandMetadata:{url:"/youtubei/v1/like/like"}},dd={target:a.likeEndpoint.target},Na.return({type:1,command:a,form:dd});if(a.likeEndpoint&&"DISLIKE"===a.likeEndpoint.status)return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/like/dislike"}},Tb={target:a.likeEndpoint.target},Na.return({type:1,command:a,form:Tb});if(a.likeEndpoint&&"INDIFFERENT"===a.likeEndpoint.status)return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/like/removelike"}},pb={target:a.likeEndpoint.target},
Na.return({type:1,command:a,form:pb});if(a.remoteTransactionDialogEndpoint)return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/ypc/get_offer_details"}},ed={offerParams:a.remoteTransactionDialogEndpoint.offerParams},Na.return({type:1,command:a,form:ed});if(a.ypcOffersEndpoint)return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/ypc/get_offers"}},Gf={itemParams:a.ypcOffersEndpoint.params},Na.return({type:1,command:a,form:Gf});if(a.adInfoDialogEndpoint)return a.commandMetadata=
{},Na.return({type:1,command:a});if(a.startWelcomeCommand)return a.commandMetadata={webCommandMetadata:{rootVe:93044,url:"/youtubei/v1/welcome",webPageType:"WEB_PAGE_TYPE_WELCOME"}},Na.return({type:0,command:a});if(a.startAccountSelectorCommand)return a.commandMetadata={webCommandMetadata:{rootVe:93045,url:"/youtubei/v1/account_selector",webPageType:"WEB_PAGE_TYPE_ACCOUNT_SELECTOR"}},Na.return({type:0,command:a});if(a.clearSearchHistoryEndpoint)return a.commandMetadata={webCommandMetadata:{url:"/youtubei/v1/history/clear_search_history"}},
Na.return({type:1,command:a});if(a.signalAction||a.clientActionEndpoint||a.openPopupAction||a.removeItemAction||a.onIdentityChanged||a.selectActiveIdentityCommand||a.removeIdentityCommand||a.updateViewershipAction||a.switchToGuestMode||a.setClientSettingEndpoint||a.openClientOverlayAction||a.selectSubtitlesTrackCommand||a.addChatItemAction||a.markChatItemAsDeletedAction||a.markChatItemsByAuthorAsDeletedAction||a.removeChatItemAction||a.initializePurchaseCommand||a.changeDefaultPaymentCommand||a.submitPurchaseCommand||
a.startSignInCommand||a.signInEndpoint||a.authDeterminedCommand||a.authRequiredCommand||a.requestReversePairingCommand||a.volumeControlAction||a.playerControlAction||a.adPersonalizationSettingChangeEndpoint||a.adPingingEndpoint||a.pingingEndpoint||a.muteAdEndpoint||a.logPayloadCommand){a.commandMetadata={webCommandMetadata:{clientAction:!0}};ec={type:2,command:a};if(Xs=a.removeItemAction&&a.removeItemAction.parentId)ec.id=Xs;return Na.return(ec)}if(a.clearWatchHistoryEndpoint)return a.commandMetadata=
{webCommandMetadata:{url:"/youtubei/v1/history/clear_watch_history"}},Na.return({type:1,command:a});throw Error("nb");})})};_.$H=function(a){return a?(a=a.browseId)&&"FEtopics"!==a&&"default"!==a?a.startsWith("FE")?"FEfiltered_browse FEmusic_last_played FEmusic_library_corpus_artists FEmusic_liked_albums FEmusic_liked_playlists FEmusic_liked_videos FEstorefront".split(" ").includes(a):!0:!1:!1};
aI=function(a){return a?a.browseEndpoint&&!_.$H(a.browseEndpoint)||!!_.YH(a)||!!a.searchEndpoint||!!a.applicationSettingsEndpoint:!1};_.bI=function(a,b){return a.browseId===b.browseId||XH(a)&&XH(b)?(a.params||"")===(b.params||""):!1};cI=function(a){return!!a&&void 0!==a.command.browseEndpoint};_.dI=function(a,b){return void 0===a.command.browseEndpoint||void 0===b.command.browseEndpoint?!1:_.bI(a.command.browseEndpoint,b.command.browseEndpoint)};
_.eI=function(a){return!(!a||!a.command.watchEndpoint&&!a.command.watchPlaylistEndpoint)};_.gI=function(a){return fI[a.type]||!_.v.isEmpty(a.Da.response||null)};_.iI=function(){null===hI&&(hI=_.bH(18440),_.dH(_.fH(),hI));return hI};
jI={'script[name="scheduler/scheduler"]':"sj",'script[name="player/base"]':"pj",'link[rel="stylesheet"][name="www-player"]':"pc",'link[rel="stylesheet"][name="player/www-player"]':"pc",'script[name="desktop_polymer/desktop_polymer"]':"dpj",'link[rel="import"][name="desktop_polymer"]':"dph",'script[name="mobile-c3/mobile-c3"]':"mcj",'link[rel="stylesheet"][name="mobile-c3"]':"mcc",'script[name="player-plasma-ias-phone/base"]':"mcppj",'script[name="player-plasma-ias-tablet/base"]':"mcptj",'link[rel="stylesheet"][name="mobile-polymer-player-ias"]':"mcpc",
'script[name="mobile_blazer_core_mod"]':"mbcj",'link[rel="stylesheet"][name="mobile_blazer_css"]':"mbc",'script[name="mobile_blazer_logged_in_users_mod"]':"mbliuj",'script[name="mobile_blazer_logged_out_users_mod"]':"mblouj",'script[name="mobile_blazer_noncore_mod"]':"mbnj","#player_css":"mbpc",'script[name="mobile_blazer_desktopplayer_mod"]':"mbpj",'link[rel="stylesheet"][name="mobile_blazer_tablet_css"]':"mbtc",'script[name="mobile_blazer_watch_mod"]':"mbwj"};_.m().w("sy25");
var kI={},fI=(kI.WEB_PAGE_TYPE_SEARCH=!0,kI.WEB_PAGE_TYPE_WELCOME=!0,kI.WEB_PAGE_TYPE_ACCOUNT_SELECTOR=!0,kI),lI=function(){this.h=_.r.get(_.EH);this.cache=_.r.get(_.JH);this.Z=_.r.get(_.ky);this.qa=_.r.get(_.tv);this.ra=_.r.get(_.kA);this.g=!0},pI=function(a,b){var c=b.command,d=b.form,e,f,g,h,l=null===(f=null===(e=null===c||void 0===c?void 0:c.commandMetadata)||void 0===e?void 0:e.webCommandMetadata)||void 0===f?void 0:f.url,n=null===(h=null===(g=null===c||void 0===c?void 0:c.commandMetadata)||
void 0===g?void 0:g.webCommandMetadata)||void 0===h?void 0:h.webPageType;return a.qa.get().then(function(q){q={identity:q,path:l,payload:d,clickTracking:{clickTrackingParams:c.clickTrackingParams}};if(a.g){a.g=!1;var t=mI(a,q,n);if(t)return t}return fI[n]?_.I({Df:!0,he:{response:{}}}):"WEB_PAGE_TYPE_ACCOUNTS"===n?nI(a):(t=a.cache.get(q))?(q={},a.Z.info((q.yt_lt="hot",q)),{Df:!0,he:t}):oI(a,q)})},mI=function(a,b,c){var d=window.earlyBrowseRequest;if(!(!d||b.identity&&_.sr(b.identity)||"WEB_PAGE_TYPE_BROWSE"!==
c||(c=b.payload,"default"!==c.browseId&&"FEtopics"!==c.browseId||c.params)))return a.Z.tick("nreqs"),_.ih(d.then(function(e){a.Z.tick("nrese");e={response:e};a.cache.set(b,e);return{Df:!0,he:e}}),function(){return oI(a,b)})},nI=function(a){a.Z.tick("nreqs");var b=new _.Zg(function(c,d){return a.h.Od().subscribe(function(e){a.Z.tick("nrese");c({response:{items:e}})},d)});return _.I({Df:!1,Sl:b})},oI=function(a,b){a.Z.tick("nreqs");return{Df:!1,Sl:new _.Zg(function(c,d){a.ra.fetch(b).subscribe(function(e){a.Z.tick("nrese");
e={response:e};a.cache.set(b,e);c(e)},d)})}};
var qI=function(){var a=this;this.storage=[];this.g=_.L.R();_.N(this.g,new _.O("onIdentityChanged"),this,function(){a.onIdentityChanged()})};_.k=qI.prototype;_.k.onIdentityChanged=function(){for(var a=_.A(this.storage.slice(0,this.storage.length-1)),b=a.next();!b.done;b=a.next())b=b.value,"WEB_PAGE_TYPE_SEARCH"!==b.type&&(b.Da.response=void 0)};_.k.isEmpty=function(){return 0===this.length};_.k.Va=function(){return this.storage[this.length-1]};_.k.pop=function(){return this.storage.pop()};
_.k.push=function(a){_.eI(a)&&rI(this);cI(a)&&(XH(a.command.browseEndpoint)?this.clear():aI(a.command)&&sI(this));this.storage.push(a);for(a=0;a<this.length-1;a++){var b=this.storage[a],c=this.storage[a+1];cI(b)&&cI(c)&&_.dI(b,c)&&this.storage.splice(a,1)}5<this.length&&(this.storage=this.storage.slice(-5))};_.k.remove=function(a){this.storage=this.storage.filter(function(b){return b!==a})};_.k.replace=function(a){this.storage[this.length-1]=a};_.k.clear=function(){this.storage=[]};
var rI=function(a){a.storage=a.storage.filter(function(b){return!_.eI(b)})},sI=function(a){a.storage=a.storage.filter(function(b){return!aI(b.command)})};_.E.Object.defineProperties(qI.prototype,{length:{configurable:!0,enumerable:!0,get:function(){return this.storage.length}}});
var tI=_.p("NavigationHandler","eaAh1d");
var hI=null;
var uI={},vI=(uI.WEB_PAGE_TYPE_ACCOUNT_SELECTOR={action:"profile_switcher",pf:["ol"],$f:!0},uI.WEB_PAGE_TYPE_BROWSE={action:"home",pf:["ol"],$f:!0},uI.WEB_PAGE_TYPE_SEARCH={action:"search_ui",pf:["ol"],$f:!0},uI.WEB_PAGE_TYPE_WATCH={action:"watch",pf:["pbs","pbu","pbp"],$f:!1},uI.WEB_PAGE_TYPE_WELCOME={action:"onboarding",pf:["ol"],$f:!0},uI),yI=function(){var a=new qI,b=this;var c=void 0===c?new lI:c;this.history=a;this.B=c;this.j=_.L.R();this.Z=_.r.get(_.ky);this.h=_.fH();this.A=_.r.get(_.$t);this.subscribers=
new Set;_.N(this.j,new _.O("replaceRootData"),this,function(d){var e=b.history.Va();e&&(d=Object.assign(Object.assign({},e),{Da:Object.assign(Object.assign({},e.Da),{response:d.data}),command:d.command}),b.replace(d))});_.N(this.j,new _.O("reloadRootData"),this,function(d){wI(b,d)});this.A.onResume(function(){var d=b.history.Va();d&&b.isExpired(d)&&xI(b,d,!1)})},CI,HI,GI,BI,xI,wI;
yI.prototype.navigate=function(a,b){var c=this;b=void 0===b?!1:b;var d={type:_.zI(a).webPageType,command:a.command,Da:{response:{}},created:_.Jo()};b||AI(this,d);BI(this);var e=new _.Zg(function(f,g){return _.Tu(c,function l(){var n=this,q,t;return _.Su(l,function(w){if(1==w.g)return _.Ju(w,pI(n.B,a),2);if(3!=w.g){q=w.h;if(CI(n,d))return g(Error("ob")),w.return();if(q.Df)return DI(n,q.he),EI(n,q.he),f(q.he),w.return();b||FI(n,d);return _.Ju(w,q.Sl,3)}t=w.h;if(CI(n,d))return g(Error("pb")),w.return();
EI(n,t);f(t);w.g=0})})});this.history.push(d);this.g={Ad:e,props:d};return e};CI=function(a,b){var c;return b!==(null===(c=a.g)||void 0===c?void 0:c.props)};HI=function(a,b){var c=_.zI(b).webPageType,d=a.history.Va();if(!d||d.type!==c)throw Error("qb");return a.g&&!_.gI(d)?a.g.Ad.then(function(){return GI(a,b)}):GI(a,b)};GI=function(a,b){a.history.pop();return a.navigate(b,!0).then(function(c){c.csn=_.cs()||void 0;var d,e=null===(d=c.response)||void 0===d?void 0:d.trackingParams;e&&_.lH(a.h,e);return c})};
_.zI=function(a){if(!a.command.commandMetadata||!a.command.commandMetadata.webCommandMetadata)throw Error("rb");return a.command.commandMetadata.webCommandMetadata};BI=function(a){if(!a.g)return!1;a.history.remove(a.g.props);a.g.Ad.cancel();a.g=void 0;return!0};yI.prototype.subscribe=function(a){var b=this,c=this.history.Va();c&&a(c);this.subscribers.add(a);return function(){b.subscribers.delete(a)}};yI.prototype.Va=function(){return this.history.Va()};yI.prototype.clearHistory=function(){this.history.clear()};
_.JI=function(a){if(1===a.history.length)(a=a.history.Va())&&XH(a.command.browseEndpoint)||_.Wu().resolveCommand(_.Px("default"));else{BI(a)||a.history.pop();var b=a.history.Va();b.Le=!0;if(b.Da.response)if(a.isExpired(b))xI(a,b);else{AI(a,b);a.replace(b);if(b.Le&&_.D("enableBackNavigationTimelines",!0)){var c={};a.Z.info((c.yt_lt="hot",c))}II(b)}else xI(a,b)}};xI=function(a,b,c){(c=void 0===c?!0:c)&&AI(a,b);b.Da.response={};b.created=_.Jo();a.replace(b);wI(a,b.command).then(function(){c&&II(b)})};
wI=function(a,b){return _.I((0,_.ZH)(b)).then(function(c){return HI(a,c)})};yI.prototype.replace=function(a){var b,c,d=null===(c=null===(b=a.command.commandMetadata)||void 0===b?void 0:b.webCommandMetadata)||void 0===c?void 0:c.rootVe;b=_.I(a.Da);c=_.YG(a.command);_.gH(this.h,d,_.iI(),[b],void 0,a.Da.csn,c);DI(this,a.Da);FI(this,a)};
var AI=function(a,b){if(!b.Le||_.D("enableBackNavigationTimelines",!0)){var c=vI[b.type];if(c){var d=a.Z,e=c.action;c=c.pf;if(d.bedrockPorts)d.bedrockPorts.performance.Np(e,c);else{d.g&&(_.$x(),_.cy("n"),_.by(),_.Zx(!1),_.db("TIMING_AFT_KEYS",[]),_.Zs("yt_lt","warm"),_.db("TIMING_ACTION",""),_.db("TIMING_WAIT",[]),delete _.u("TIMING_INFO",{}).yt_lt,_.dy("n"));var f=c.slice();c=!d.g;_.Ls("").info.actionType=e;f&&_.db("TIMING_AFT_KEYS",f);_.db("TIMING_ACTION",e);e=_.u("TIMING_INFO",{});for(var g in e)_.Zs(g,
e[g]);_.Zs("is_nav",1);(g=_.cs())&&_.Zs("csn",g);(g=_.u("PREVIOUS_ACTION",void 0))&&!_.Ms()&&_.Zs("pa",g);g=_.Ds();e=_.u("CLIENT_PROTOCOL");f=_.u("CLIENT_TRANSPORT");e&&_.Zs("p",e);f&&_.Zs("t",f);_.Zs("yt_vis",UH());if("cold"==g.yt_lt||c){_.Zs("yt_lt","cold");c=RH();if(e=PH())_.ct("srt",c.responseStart),1!=g.prerender&&_.dy("n",e);c=SH();0<c&&_.ct("fpt",c);c=RH();c.isPerformanceNavigationTiming&&_.Zs("pnt",1,void 0);_.ct("nreqs",c.requestStart,void 0);_.ct("nress",c.responseStart,void 0);_.ct("nrese",
c.responseEnd,void 0);0<c.redirectEnd-c.redirectStart&&(_.ct("nrs",c.redirectStart,void 0),_.ct("nre",c.redirectEnd,void 0));0<c.domainLookupEnd-c.domainLookupStart&&(_.ct("ndnss",c.domainLookupStart,void 0),_.ct("ndnse",c.domainLookupEnd,void 0));0<c.connectEnd-c.connectStart&&(_.ct("ntcps",c.connectStart,void 0),_.ct("ntcpe",c.connectEnd,void 0));c.secureConnectionStart>=PH()&&0<c.connectEnd-c.secureConnectionStart&&(_.ct("nstcps",c.secureConnectionStart,void 0),_.ct("ntcpe",c.connectEnd,void 0));
_.at.getEntriesByType&&VH();c=[];if(document.querySelector&&_.at&&_.at.getEntriesByName)for(var h in jI)g=jI[h],WH(h,g)&&c.push(g);c.length&&_.Zs("rc",c.join(","))}_.ay();h=_.Ds();g=_.xs();if("cold"===h.yt_lt&&(c=_.Ns(),e=c.gelTicks?c.gelTicks:c.gelTicks={},c=c.gelInfos?c.gelInfos:c.gelInfos={},_.Ms())){for(var l in g)"tick_"+l in e||_.Qs(l,g[l]);l=_.Ws();g=_.Es();e={};for(var n in h)"info_"+n in c||!(f=_.Vs(n,h[n]))||(_.us(l,f),_.us(e,f));_.Os().info(e,g)}_.Zx(!0);_.bt();var q;n=_.pd();_.dt("aq",
n.legacyApplicationQuality);_.dt("c",n.clientName);_.dt("cver",n.clientVersion);_.dt("preloaded",n.isPreloaded?1:0);_.dt("label",n.clientLabel);l=_.wd[n.legacyApplicationQuality];_.dt("l_mm","TV_APP_QUALITY_LIMITED_MEMORY"===l?1:0);_.dt("l_an","TV_APP_QUALITY_LIMITED_ANIMATION"===l?1:0);(null===(q=n.mdxInitData)||void 0===q?0:q.Vf)&&_.dt("is_mdx",1);q=_.Za();q.brand&&_.dt("cbrand",q.brand);q.model&&_.dt("cmodel",q.model);q.platform&&_.dt("cplatform",q.platform);q.browser&&_.dt("cbr",q.browser);q.browser_version&&
_.dt("cbrver",q.browser_version);q.os&&_.dt("cos",q.os);q.os_version&&_.dt("cosver",q.os_version);d.g=!0}d=a.history.Va();var t,w,y;if(!b.Le||_.D("enableBackNavigationTimelines",!0))q={},"WEB_PAGE_TYPE_BROWSE"===b.type&&(q.browse_id=null===(t=b.command.browseEndpoint)||void 0===t?void 0:t.browseId,q.b_p=null===(w=b.command.browseEndpoint)||void 0===w?void 0:w.params),"WEB_PAGE_TYPE_BROWSE"===(null===d||void 0===d?void 0:d.type)&&(q.prev_browse_id=null===(y=d.command.browseEndpoint)||void 0===y?void 0:
y.browseId),a.Z.info(q)}}},EI=function(a,b){b=Object.assign(Object.assign({},a.history.Va()),{Da:b});a.history.replace(b);a.g=void 0;FI(a,b);II(b)},II=function(a){(!a.Le||_.D("enableBackNavigationTimelines",!0))&&(a=vI[a.type])&&a.$f&&TH()},FI=function(a,b){a=_.A(a.subscribers);for(var c=a.next();!c.done;c=a.next())c=c.value,c(b)},DI=function(a,b){var c;b=null!==(c=b.continuations)&&void 0!==c?c:[];b=_.A(b);for(var d=b.next();!d.done;d=b.next())d=d.value,_.lH(a.h,d.fc,_.Ix(d.parent))};
yI.prototype.isExpired=function(a){var b,c,d,e=null!==(d=null===(c=null===(b=a.Da.response)||void 0===b?void 0:b.responseContext)||void 0===c?void 0:c.maxAgeSeconds)&&void 0!==d?d:0;if(e)return a=a.created+1E3*e,_.Jo()>=a};_.KI=_.K(tI,function(){return new yI});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.LI=function(a){return _.sr(a)?_.Cu(_.r.get(_.gu),a):_.I(null)};_.m().w("sy29");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.MI=function(a){var b=_.GG;return b?b.getVideoData(a):{}};_.NI=function(){var a=_.GG;return a?a.getPlayerResponse():null};_.OI=function(){return _.MI().video_id||""};_.m().w("sy2q");
var PI;PI={};_.QI=(PI.auto=0,PI.tiny=144,PI.light=144,PI.small=240,PI.medium=360,PI.large=480,PI.hd720=720,PI.hd1080=1080,PI.hd1440=1440,PI.hd2160=2160,PI.hd2880=2880,PI.highres=4320,PI);_.RI=Object.freeze({volume:-1,muted:!1});_.SI=Object.freeze({errorInfo:null,cpn:null});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var TI,UI;TI=function(){var a=_.r.get(_.cu),b=_.pd().mdxInitData;switch((null===b||void 0===b?0:b.Vf)||a.h?"dial":a.g?"in-app-dial":"unknown"){case "dial":return"mdx-dial";case "in-app-dial":return"mdx-inappdial";default:return"mdx-pair"}};UI=function(a,b,c){c=void 0===c?{}:c;var d={};d.oauth_token=b||null;a.watchEndpoint&&a.watchEndpoint.playlistId&&(d.tracking_list=a.watchEndpoint.playlistId);Object.assign(d,c);return d};_.VI=function(){return _.MI().cpn||null};
_.WI=function(a){return Math.round(1E3*(isFinite(a)?a:0))/1E3};_.m().w("sy2p");
var XI=function(a){_.zt.call(this);this.value=this.A=a};_.G(XI,_.zt);XI.prototype.get=function(){return this.value};XI.prototype.set=function(a){var b=this.value;a!==b&&(this.value=a,this.h("value-changed",a,b));this.h("value-set",a,b)};XI.prototype.reset=function(){this.set(this.A)};
var YI=_.p("PlayerService","fU15Pb");
_.ZI=Object.freeze({allowSeeking:!0,clipEnd:0,clipStart:0,current:0,displayedStart:0,duration:0,ingestionTime:0,isAtLiveHead:!1,loaded:0,seekableStart:0,seekableEnd:0});
var hJ=function(){var a=this;this.F=new XI(_.ZI);this.D=new XI(-1);this.j=new XI(-1);this.ga=new XI(-1);this.G=new XI(!1);this.H=new XI(!1);this.S=new XI(_.RI);this.N=new XI(_.SI);this.Y=new XI(!1);this.Ja=new XI(!1);this.J=new XI(null);this.eb=new XI(!1);this.ypcRentalActivationRenderer=new XI(null);this.va=new XI(null);this.V=this.Ia=!1;this.markers={};this.g=null;this.qa=_.r.get(_.tv);this.P=-1;this.cb=!1;this.h=_.L.R();this.$=!1;this.C=_.Op();this.Qa=_.fH();this.ze=!1;this.cc=_.r.get(_.xo);_.EG().then(function(b){b.addEventListener("onVideoProgress",
function(){var c=b.getPresentingPlayerType();a.F.set(b.getProgressState(c))});b.addEventListener("onStateChange",function(c){var d=[-1,5,1];0===a.D.get()&&-1!==d.indexOf(c)||a.D.set(c)});b.addEventListener("onAdStateChange",function(c){a.j.set(c);switch(c){case 0:case -1:a.F.set(_.ZI)}});b.addEventListener("onVolumeChange",function(c){var d=c.volume||0;c=!!c.muted;if(a.muted!==c||a.volume!==d)a.S.set({volume:d,muted:c,ze:a.ze}),a.ze=!1});b.addEventListener("onDetailedError",function(c){a.stop();var d=
null===c||void 0===c?void 0:c.errorCode;"net.badstatus"!==d&&"net.closed"!==d&&"net.connect"!==d&&"net.retryexhausted"!==d||a.cc.aj(608);a.N.set({errorInfo:c||null,cpn:_.VI()})});b.addEventListener("videodatachange",function(c){var d,e,f=_.NI(),g=f&&f.videoDetails,h=!(!g||!g.isUpcoming);g=!(!g||!g.isLive);var l=f&&f.playabilityStatus||null,n=$I(f);a.Y.set(h);a.Ja.set(g);a.J.set(l);a.va.set(n);a.$&&(a.$=!1,a.ypcRentalActivationRenderer.set((null===(e=null===(d=a.playabilityStatus)||void 0===d?void 0:
d.ypcClickwrap)||void 0===e?void 0:e.ypcRentalActivationRenderer)||null));"dataloaded"===c&&a.C.resolve({playerResponse:f||void 0})});b.addEventListener("onHeartbeat",function(c){a.J.set(c)});b.addEventListener("ypcStateChanged",function(c){a.eb.set(c)});b.addEventListener("ypcShowRentalActivation",function(){a.$=!0})});this.j.g("value-changed",function(){aJ(a);bJ(a);cJ(a)});this.D.g("value-changed",function(){aJ(a);bJ(a);cJ(a)});_.dJ(this,function(b){b=b.current;for(var c in a.markers)if(!a.na||
1!==a.markers[c].mode){var d=a.markers[c];b>=d.time&&(d.ma(),delete a.markers[c])}});this.sb(function(b){3!==b&&1!==b&&clearTimeout(a.P)});_.N(this.h,new _.O("setClientSettingEndpoint"),this,function(b){if((b=b.command.setClientSettingEndpoint.settingDatas)&&b.length)switch(b=b[0],b&&b.clientSettingEnum&&b.clientSettingEnum.item){case "PLAYBACK_QUALITY":a.setPlaybackQuality(b.stringValue);break;case "PLAYBACK_SPEED":eJ(b.intValue);break;case "PLAYBACK_AUDIO_TRACK":fJ(b.stringValue);break;case "SAFETY_MODE":a.setSafetyMode(!!b.boolValue);
break;default:_.I(void 0)}else _.I(void 0)});_.N(this.h,new _.O("CONFIRM_CONTROVERSIAL_CONTENT"),this,function(){_.gJ(a,void 0,void 0,"cco")});_.N(this.h,new _.O("CONFIRM_RACY_CONTENT"),this,function(){_.gJ(a,void 0,void 0,"rco")});_.N(this.h,new _.O("RELOAD_PLAYER"),this,function(){_.gJ(a)});_.N(this.h,new _.O("volumeControlAction"),this,function(b){b=b.command.volumeControlAction;switch(b.volumeControlType){case "VOLUME_CONTROL_ACTION_TYPE_MUTE":a.mute();break;case "VOLUME_CONTROL_ACTION_TYPE_UNMUTE":a.unMute();
break;case "VOLUME_CONTROL_ACTION_TYPE_SET_ABSOLUTE":b.volumeControlValue&&a.setVolume(b.volumeControlValue)}});_.N(this.h,new _.O("playerControlAction"),this,function(b){b=b.command.playerControlAction;var c=Number(b.playerSeekTimeInMillis);c=isNaN(c)?void 0:Math.floor(c/1E3);switch(b.playerControlType){case "PLAYER_CONTROL_ACTION_TYPE_SEEK_RELATIVE":void 0!==c&&a.seekTo(a.currentTime+c);break;case "PLAYER_CONTROL_ACTION_TYPE_SEEK_ABSOLUTE":void 0!==c&&a.seekTo(c);break;case "PLAYER_CONTROL_ACTION_TYPE_PLAY":a.A&&
void 0!==c&&a.seekTo(c);a.B&&a.play();break;case "PLAYER_CONTROL_ACTION_TYPE_PAUSE":a.A&&void 0!==c&&a.seekTo(c);a.B&&a.pause();break;case "PLAYER_CONTROL_ACTION_TYPE_STOP":a.stop()}})},$I,aJ,bJ,cJ,lJ,nJ;hJ.prototype.getDuration=function(a){var b=_.GG;if(!b)return 0;a=a||b.getPresentingPlayerType();a=b.getDuration(a)||0;return _.WI(a)};hJ.prototype.getCurrentTime=function(a){var b=_.GG;if(!b)return 0;a=a||b.getPresentingPlayerType();a=b.getCurrentTime(a)||0;return _.WI(a)};
$I=function(a){if(!a)return null;var b=null;a.messages&&(a=a.messages.find(function(c){return!!c.youThereRenderer}))&&a.youThereRenderer&&(b=a.youThereRenderer);return b};_.dJ=function(a,b){return a.F.g("value-changed",b)};hJ.prototype.sb=function(a){return this.ga.g("value-changed",a)};_.iJ=function(a,b){return a.G.g("value-changed",b)};_.jJ=function(a,b){return a.H.g("value-changed",b)};aJ=function(a){switch(a.j.get()){case 0:case -1:a.H.set(!1);break;case 3:case 1:a.H.set(!0)}};
bJ=function(a){a.ga.set(a.na?a.j.get():a.D.get())};cJ=function(a){switch(a.state){case -1:case 0:case 2:a.G.set(!1);break;case 1:a.G.set(!0)}};hJ.prototype.load=function(a,b){this.cb||(this.cb=!0,(0,_.SG)(a,b))};
_.mJ=function(a,b){a.stop();var c=_.EG(),d=a.qa.get().then(_.LI);a.C.Ad.cancel();a.C=_.Op();b.Pk||_.kJ(a);_.gh([c,d]).then(function(e){a.g=b;var f=e[0];e=e[1];e=UI(b,e?e.accessToken:void 0);var g=_.AG(),h=_.pd();e=Object.assign(Object.assign({},e),g);e.vq=h.requestVideoQuality;e.visitor_data=_.u("VISITOR_DATA",void 0);h.forceAdsUrl&&(e.force_ads_url=h.forceAdsUrl);if(h=b.watchEndpoint)h.videoId&&(e.video_id=h.videoId),h.playlistId&&(e.list=h.playlistId),void 0!==h.startTimeSeconds&&(e.start=h.startTimeSeconds),
h.endTimeSeconds&&(e.end=h.endTimeSeconds),h.playerParams&&(e.player_params=h.playerParams);isNaN(b.startTime)||(e.start=b.startTime);b.Jd&&(e.autoplay="1",e.autonav="1");b.clickTrackingParams&&(e.itct=b.clickTrackingParams);h=b.Oh;g=0;for(var l=h.length;g<l;++g)"cco"===h[g]&&(e.cco="1"),"rco"===h[g]&&(e.rco="1");b.Qk&&(e.is_inline_playback_no_ad="1");b.isLivingRoomDeeplink&&(e.is_living_room_deeplink=!0);if(!_.u("MDX_DISABLED")){if(h=b.watchEndpoint&&_.XG(b.watchEndpoint,"VIDEO"))e.vvt=h,e.vss_credentials_token=
h,e.vss_credentials_token_type="vvt",(h=_.pd().mdxInitData)&&""!==h.mdxEnvironment&&(e.mdx_environment=h.mdxEnvironment);b.isFling&&(e.is_fling="1");b.watchEndpoint&&_.hG(b.watchEndpoint)&&(e.mdx="1",e.ctrl=TI());a:{h=_.r.get(_.cu);switch(_.ku(h)){case "MDX_CONTROL_MODE_REMOTE":h=1;break a}h=void 0}h&&(e.mdx_control_mode=h);h=_.r.get(_.gG);g=h.yl();g.length&&(e.ytr=g.join(","));g=h.Zg();g.size&&(e.ytrcc=Array.from(g.values()).join(","));h=h.zl();h.size&&(e.ytrexp=Array.from(h.values()).join(","))}a.V=
!0;b.Uj?f.cueVideoByPlayerVars(e):f.loadVideoByPlayerVars(e);_.Eo(a.h,new _.O("onVideoLoadedOrCuedAction"),null);return lJ(a)})};lJ=function(a){clearTimeout(a.P);return a.updateVideoData().then(function(b){b&&(b=b.accessTokenExpiration-_.Jo(),a.P=setTimeout(function(){lJ(a)},b))})};hJ.prototype.updateVideoData=function(a){var b=this,c=_.EG(),d=this.qa.get().then(_.LI);return _.gh([c,d]).then(function(e){if(!b.g)return null;var f=e[0];if(e=e[1]){var g=UI(b.g,e.accessToken,a);f.updateVideoData(g)}return e})};
_.gJ=function(a,b,c,d){b=void 0===b?0:b;c=void 0===c?!1:c;a.g?(a.g.startTime=b,a.g.Uj=!!c,d&&(a.g.Oh=_.F(a.g.Oh).concat([d])),_.mJ(a,a.g)):(_.C(Error("sb")),_.I(void 0))};_.k=hJ.prototype;_.k.play=function(a){var b=this;a=void 0===a?this.progress.isAtLiveHead:a;return _.EG().then(function(c){b.isLive&&a&&c.seekTo(Infinity);c.playVideo();return lJ(b)})};
_.k.stop=function(){var a=_.GG;this.B&&a&&(this.V=!1,this.F.set(_.ZI),this.D.set(-1),this.j.set(-1),this.setInline(!1),this.N.set(_.SI),this.C.Ad.cancel(),clearTimeout(this.P),a.stopVideo())};_.k.pause=function(){return _.EG().then(function(a){a.pauseVideo()})};
_.k.seekTo=function(a,b){if(this.na||-1===this.state)return _.I(void 0);this.isLive?(a=_.WI(_.Ut(this.progress.seekableStart,a,this.progress.seekableEnd)),a===this.progress.seekableEnd&&(a=Infinity)):a=_.WI(_.Ut(0,a,this.duration));return a===this.currentTime?_.I(void 0):_.EG().then(function(c){c.seekTo(a,b,void 0,c.getPresentingPlayerType())})};_.k.setVolume=function(a){return _.EG().then(function(b){b.setVolume(a)})};
_.k.mute=function(a){var b=this;a=void 0===a?!1:a;return _.EG().then(function(c){b.ze=a;c.mute()})};_.k.unMute=function(a){var b=this;a=void 0===a?!1:a;return _.EG().then(function(c){b.ze=a;c.unMute()})};_.k.setPlaybackQuality=function(a){if(!a)return _.I(void 0);var b=_.GG;return _.ov()&&b?(b.setPlaybackQualityRange(a,a),_.I(void 0)):_.EG().then(function(c){c.setPlaybackQualityRange(a,a)})};
var eJ=function(a){var b=Number(a);isNaN(b)?_.I(void 0):_.EG().then(function(c){c.setPlaybackRate(b)})},fJ=function(a){_.EG().then(function(b){var c=b.getAvailableAudioTracks();return{player:b,audioTracks:c}}).then(function(b){var c=b.player;b=b.audioTracks;for(var d=0,e=b.length;d<e;d++){var f=b[d];if(f.getLanguageInfo().getName()===a){c.setAudioTrack(f);break}}})};_.k=hJ.prototype;_.k.setSafetyMode=function(a){return _.EG().then(function(b){b.setSafetyMode(a)})};
_.k.isSpherical=function(){var a=this.getSphericalProperties();return!!a&&!_.v.isEmpty(a)};_.k.getSphericalProperties=function(){var a=_.GG;return a?a.getSphericalProperties():null};_.k.setSphericalProperties=function(a,b){var c=_.GG;c&&c.setSphericalProperties({yaw:a,pitch:b})};_.k.setConnectedRemoteApps=function(a){return _.EG().then(function(b){b.setConnectedRemoteApps(a)})};_.k.isInline=function(){return this.Ia};_.k.setInline=function(a){var b=this;return _.EG().then(function(c){b.Ia=a;c.setInline(a)})};
_.k.sendAbandonmentPing=function(){var a=_.GG;a&&a.sendAbandonmentPing()};_.kJ=function(a,b){nJ(a,b).then(function(c){var d,e=null===(d=c.playerResponse)||void 0===d?void 0:d.trackingParams;if(e){_.nA(e);c={playbackData:{clientPlaybackNonce:_.VI()||void 0}};e="string"===typeof e?_.Ix(e):e;var f=_.cs();f&&_.VG(_.jA,f,e,c)}})};
nJ=function(a,b){var c=a.C.Ad;if(b)return c.then(function(d){var e,f=_.Ix(b),g=null===(e=d.playerResponse)||void 0===e?void 0:e.trackingParams;g&&_.lH(a.Qa,g,f);return d});_.jH(a.Qa,c);return c};
_.E.Object.defineProperties(hJ.prototype,{progress:{configurable:!0,enumerable:!0,get:function(){return this.F.get()}},state:{configurable:!0,enumerable:!0,get:function(){return this.ga.get()}},qb:{configurable:!0,enumerable:!0,get:function(){return this.G.get()}},na:{configurable:!0,enumerable:!0,get:function(){return this.H.get()}},isAdPlaying:{configurable:!0,enumerable:!0,get:function(){return this.na&&1===this.j.get()}},Gb:{configurable:!0,enumerable:!0,get:function(){return this.eb.get()}},
isLive:{configurable:!0,enumerable:!0,get:function(){return!this.na&&this.Ja.get()}},isUpcoming:{configurable:!0,enumerable:!0,get:function(){return!this.na&&this.Y.get()}},B:{configurable:!0,enumerable:!0,get:function(){return this.V}},Db:{configurable:!0,enumerable:!0,get:function(){return 3===this.state&&!this.isUpcoming}},A:{configurable:!0,enumerable:!0,get:function(){return!this.na&&this.B}},duration:{configurable:!0,enumerable:!0,get:function(){return this.getDuration()}},currentTime:{configurable:!0,
enumerable:!0,get:function(){return this.getCurrentTime()}},volume:{configurable:!0,enumerable:!0,get:function(){return Math.round(this.S.get().volume)}},muted:{configurable:!0,enumerable:!0,get:function(){return this.S.get().muted}},Ug:{configurable:!0,enumerable:!0,get:function(){return this.g}},xb:{configurable:!0,enumerable:!0,get:function(){return this.N.get()}},playabilityStatus:{configurable:!0,enumerable:!0,get:function(){return this.J.get()}},youThereRenderer:{configurable:!0,enumerable:!0,
get:function(){return this.va.get()}},adCpn:{configurable:!0,enumerable:!0,get:function(){var a=_.GG;return a&&this.na?_.MI(a.getPresentingPlayerType()).cpn||null:null}}});_.oJ=_.K(YI,function(){return new hJ});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.pJ=function(a){return a.sa[a.selectedIndex]-a.ea};_.m().w("sy2u");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy3h");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var sJ=function(a){var b=a.highlighted,c=a.Ep,d=a.data;a=d.icon;var e=d.label;d=qJ.get(d.style||"BADGE_STYLE_TYPE_UNKNOWN");_.D("enableHtml5TealAdBadge",!1)&&"ytlr-metadata-badge-renderer--ad"===d&&(d="ytlr-metadata-badge-renderer--ad-experiment");var f={};return _.R("host",{className:[d,(f["ytlr-metadata-badge-renderer--highlighted"]=b,f["ytlr-metadata-badge-renderer--rounded"]=c,f)]},_.Y(a,function(g){return _.Y("PREMIUM"===g.iconType,function(){return _.R(_.Pz,{className:"ytlr-metadata-badge-renderer__thumbnail",
data:rJ,width:1,Dg:!1,idomKey:"icon-premium"})},function(){return _.R(_.ry,{icon:g,className:"ytlr-metadata-badge-renderer__icon"})})}),e||"")};_.m().w("sy3i");
var tJ={},qJ=new _.Qi((tJ.BADGE_STYLE_TYPE_SIMPLE="ytlr-metadata-badge-renderer--simple",tJ.BADGE_STYLE_TYPE_FEATURED="ytlr-metadata-badge-renderer--featured",tJ.BADGE_STYLE_TYPE_PREMIUM="ytlr-metadata-badge-renderer--premium",tJ.BADGE_STYLE_TYPE_YPC="ytlr-metadata-badge-renderer--ypc",tJ.BADGE_STYLE_TYPE_RED="ytlr-metadata-badge-renderer--red",tJ.BADGE_STYLE_TYPE_AD="ytlr-metadata-badge-renderer--ad",tJ.BADGE_STYLE_TYPE_VERIFIED="ytlr-metadata-badge-renderer--verified",tJ.BADGE_STYLE_TYPE_VERIFIED_ARTIST=
"ytlr-metadata-badge-renderer--verified-artist",tJ.BADGE_STYLE_TYPE_UNKNOWN="",tJ)),rJ={thumbnails:[{height:56,url:_.td("icon-premium.png"),width:56}]};
_.uJ=function(){_.Q.apply(this,arguments);this.template=sJ};_.G(_.uJ,_.Q);_.uJ.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.uJ.TAG_NAME="ytlr-metadata-badge-renderer";

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy3j");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy3k");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.vJ=function(a){return 224>=a||_.D("isLimitedMemory",!1)?320:525>=a?480:960};_.wJ=function(a){return 960>=a?_.vJ(a):_.D("isLimitedMemory",!1)?Math.min(a,960):Math.min(a,1920)};_.m().w("sy3m");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.xJ=function(a){var b="";if(!a.renderer)return function(){};for(var c in a.renderer)if(a.renderer.hasOwnProperty(c)){b=c;var d=a.config[c];if(d)return b=Object.assign({data:a.renderer[c]},d.props),a.focused&&(b.focused=a.focused),a.idomKey&&(b.idomKey=a.idomKey),_.R(d.I,b)}""!==b&&_.C(Error("tb`"+b));return function(){}};_.m().w("sy2v");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var yJ;
yJ=function(a){var b=a.data;return _.R("host",null,_.R(_.X,{className:"ytlr-grid-video-renderer__title",data:b.title}),_.R("div",{className:"ytlr-grid-video-renderer__details"},_.TC(this.j(b,a.style||"shelf"),function(c,d){var e=c.formattedString,f=c.style,g="ytlr-grid-video-renderer__"+(2===f?"block":"inline")+"-detail";1===f&&0===d&&(f=0);return _.R(_.Tw,null,_.Y(e&&1===f,function(){return _.R("span",{className:"ytlr-grid-video-renderer__inline-detail"},"\u2022")}),_.R(_.xJ,{renderer:c,config:{formattedString:{I:_.X,props:{className:g}},
metadataBadgeRenderer:{I:_.uJ,props:{className:g}}}}))})))};_.zJ=function(a,b){_.Mt({stage:40,X:function(){var c=a.querySelector("span");if(c){var d=c.previousSibling,e=c.nextSibling,f=3*_.Mz(.25);d.offsetWidth+c.offsetWidth+e.offsetWidth+f>a.getElementsByClassName(b+"__details")[0].offsetWidth&&_.Mt({stage:40,X:function(){c.style.visibility="hidden"}})}}})};_.m().w("sy3l");
var AJ=_.Xx("zvJKn");
_.DJ=function(){_.Q.apply(this,arguments);var a=this;this.template=yJ;this.j=_.wx(function(b,c){var d,e=[],f=a.h(b.badges||[]);var g=BJ(b);g="LIVE"===g?0:"UPCOMING"===g?1:2;var h="grid"===c;if(c=b.topStandaloneBadge&&b.topStandaloneBadge.standaloneCollectionBadgeRenderer){b={simpleText:c.iconText};g=null!==(d=c.label)&&void 0!==d?d:{simpleText:""};for(var l,n=h=0;n<f.length;n++)h+=CJ({simpleText:null!==(l=f[n].metadataBadgeRenderer.label)&&void 0!==l?l:""});l=21>=h+CJ(b)+.75+CJ(g);e.push.apply(e,
_.F(f).concat([{formattedString:c.iconText,style:0},{formattedString:c.label,style:l?1:2}]));return e}h?(e.push.apply(e,_.F(f)),b.shortBylineText&&e.push({formattedString:b.shortBylineText,style:0})):(b.shortBylineText&&e.push({formattedString:b.shortBylineText,style:0===g?0:2}),e.push.apply(e,_.F(f)));1===g&&b.upcomingEventData&&b.upcomingEventData.upcomingEventText?e.push({formattedString:b.upcomingEventData.upcomingEventText,style:h?1:0}):b.shortViewCountText&&e.push({formattedString:b.shortViewCountText,
style:(0===g||h)&&b.shortBylineText?1:0});2===g&&b.publishedTimeText&&e.push({formattedString:b.publishedTimeText,style:e&&e.length&&2===e[e.length-1].style?0:1});return e});this.h=_.wx(function(b){return b.filter(function(c){return!!c.metadataBadgeRenderer}).map(function(c){return{metadataBadgeRenderer:c.metadataBadgeRenderer,style:3}})})};_.G(_.DJ,_.Q);_.DJ.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;
var BJ=function(a){if(a.thumbnailOverlays)return(a=a.thumbnailOverlays.find(function(b){return!!b.thumbnailOverlayTimeStatusRenderer}))&&a.thumbnailOverlayTimeStatusRenderer&&a.thumbnailOverlayTimeStatusRenderer.style};_.DJ.prototype.O=function(){_.zJ(this.el,"ytlr-grid-video-renderer")};_.DJ.prototype.W=function(a){a.data!==this.props.data&&_.zJ(this.el,"ytlr-grid-video-renderer")};
var CJ=function(a){var b,c;return.5*(null!==(c=null===(b=a.simpleText)||void 0===b?void 0:b.length)&&void 0!==c?c:0)+.25};_.DJ.TAG_NAME="ytlr-grid-video-metadata";_.J(AJ,_.DJ);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var EJ=function(a){return _.R("host",null,_.R(_.ry,{className:"ytlr-thumbnail-overlay-now-playing-renderer__icon",icon:"PLAYING"}),_.R(_.X,{className:"ytlr-thumbnail-overlay-now-playing-renderer__text",data:a.data.text}))};_.m().w("sy3p");
var FJ=_.Xx("qj37ab");
_.GJ=function(){_.Q.apply(this,arguments);this.template=EJ};_.G(_.GJ,_.Q);_.GJ.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.GJ.TAG_NAME="ytlr-thumbnail-overlay-now-playing-renderer";_.J(FJ,_.GJ);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy3r");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var HJ=function(a){return _.R("host",{dir:"ltr"},_.R("div",{className:"ytlr-thumbnail-overlay-resume-playback-renderer__progress-watched",style:{width:a.data.percentDurationWatched+"%"}}))},JJ=function(a){a=a.data;var b=_.$a("ytlr-thumbnail-overlay-time-status-renderer").wd,c=a.style||"";return _.R("host",{className:c?b(c):"","aria-hidden":!0},_.R(_.ry,{className:a.icon?"ytlr-thumbnail-overlay-time-status-renderer__icon":"",icon:a.icon||IJ}),_.R(_.X,{className:"ytlr-thumbnail-overlay-time-status-renderer__text",
data:a.text}))};_.m().w("sy3q");
_.KJ=function(){_.Q.apply(this,arguments);this.template=HJ};_.G(_.KJ,_.Q);_.KJ.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.KJ.TAG_NAME="ytlr-thumbnail-overlay-resume-playback-renderer";
var IJ={};
_.LJ=function(){_.Q.apply(this,arguments);this.template=JJ};_.G(_.LJ,_.Q);_.LJ.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.LJ.TAG_NAME="ytlr-thumbnail-overlay-time-status-renderer";

_.m().l();

}catch(e){_._DumpException(e)}
try{
var NJ=function(a){return _.R("host",null,_.R(_.Pz,{data:a.thumbnail,width:21,Ya:a.Ya||"#373737",zd:_.vJ}),_.R("div",null,_.TC(a.thumbnailOverlays,function(b){return _.R(_.xJ,{renderer:b,config:MJ})})))};_.m().w("sy3o");
var OJ=_.Xx("lqiO0c");
var MJ={thumbnailOverlayResumePlaybackRenderer:{I:_.KJ,props:{className:"ytlr-grid-video-renderer__resume-playback"}},thumbnailOverlayTimeStatusRenderer:{I:_.LJ,props:{className:"ytlr-grid-video-renderer__time-status"}},thumbnailOverlayNowPlayingRenderer:{I:_.GJ,props:{className:"ytlr-grid-video-renderer__now-playing"}}};
_.PJ=function(){_.Q.apply(this,arguments);this.template=NJ};_.G(_.PJ,_.Q);_.PJ.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.PJ.TAG_NAME="ytlr-grid-video-thumbnail";_.J(OJ,_.PJ);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy3n");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var QJ=function(a){var b=a.borderRadius;return _.R("host",{style:{borderRadius:b?b+"rem":void 0,height:a.height+"rem",width:a.width+"rem"}})};_.m().w("sy35");
var RJ=_.Xx("xBXR5b");
_.SJ=function(){_.Q.apply(this,arguments);this.template=QJ};_.G(_.SJ,_.Q);_.SJ.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.SJ.TAG_NAME="ytlr-ghost-box";_.J(RJ,_.SJ);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy33");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var UJ=function(a){a=TJ[a.type];var b=a.width,c=a.lineHeight;return _.R("host",null,_.R(_.SJ,{className:"ytlr-ghost-tile__thumbnail",height:a.sj,width:b}),_.Y(c,function(){return _.R("div",null,_.R(_.SJ,{borderRadius:c/2,className:"ytlr-ghost-tile__text",height:c,width:b}),_.R(_.SJ,{borderRadius:c/2,className:"ytlr-ghost-tile__text",height:c,width:b-6}))}))};_.m().w("sy36");
var VJ=_.Xx("Uh9iTd");
var WJ={},TJ=(WJ.grid={width:21,sj:11.75,lineHeight:1.25},WJ.pivot={width:17.25,sj:9.5,lineHeight:0},WJ.shelf={width:21,sj:11.75,lineHeight:1.5},WJ);
_.XJ=function(){_.Q.apply(this,arguments);this.template=UJ};_.G(_.XJ,_.Q);_.XJ.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.XJ.TAG_NAME="ytlr-ghost-tile";_.J(VJ,_.XJ);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy37");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var ZJ=function(a){var b=!(this.props.je&&!this.state.Sh),c=b?"off":"polite",d=_.V("LOADING_TEXT"),e={};b=[YJ[a.style||0],(e["ytlr-spinner--is-hidden"]=b,e)];e={};a.height&&(e.height=a.height+"rem");return _.R("host",{className:b,"aria-live":c,"aria-label":d,style:e},_.Y(this.j,function(){return _.R(_.X,{data:d,className:"ytlr-spinner__fallback"})},function(){return _.R("div",{className:"ytlr-spinner__primary"},_.R("div",{className:"ytlr-spinner--dot1"}),_.R("div",{className:"ytlr-spinner--dot2"}),
_.R("div",{className:"ytlr-spinner--dot3"}),_.R("div",{className:"ytlr-spinner__dot4"}),_.R("div",{className:"ytlr-spinner__dot5"}),_.R("div",{className:"ytlr-spinner__dot6"}),_.R("div",{className:"ytlr-spinner__dot7"}),_.R("div",{className:"ytlr-spinner__dot8"}))}))};_.m().w("sy38");
var $J=_.p("YtlrSpinner","epSZo");
var aK={},YJ=(aK[0]="",aK[1]="ytlr-spinner--centered",aK);
_.bK=function(a){_.Q.call(this,a);this.template=ZJ;this.h=-1;this.state={Sh:!1}};_.G(_.bK,_.Q);_.bK.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.bK.prototype.O=function(){cK(this)};_.bK.prototype.W=function(a){_.v.equals(a,this.props)||cK(this)};var cK=function(a){a.props.hm&&a.props.je&&(0<a.h&&clearTimeout(a.h),_.S(a,{Sh:!0}),a.h=setTimeout(function(){_.S(a,{Sh:!1})},a.props.hm))};_.bK.prototype.Ca=function(){0<this.h&&clearTimeout(this.h)};
_.E.Object.defineProperties(_.bK.prototype,{j:{configurable:!0,enumerable:!0,get:function(){return!_.D("enableAnimations",!1)||!!_.D("isLimitedMemory",!1)}}});_.bK.TAG_NAME="ytlr-spinner";_.J($J,_.bK);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var eK=function(a){var b=a.hidden,c=a.Up,d=a.banner,e=a.em,f=a.gm,g=a.grid,h=a.pivot,l=a.tiles;a=a.width;var n=_.D("enableLeftNav",!1),q=n?1.5:1.75,t=f&&!(c||d||e||g||h||l);if(_.D("isLimitedMemory",!1))return c={},_.R("host",{className:["ytlr-ghost-surface--limited-memory",(c["ytlr-ghost-surface--hidden"]=b,c)]},_.R(_.bK,{je:!0}));var w={};return _.R("host",{className:(w["ytlr-ghost-surface--hidden"]=b,w["ytlr-ghost-surface--enable-left-nav"]=n,w["ytlr-ghost-surface--has-grid"]=g,w),style:void 0===
a?{}:{width:a+"rem"}},_.Y(h||l,function(){var y={};return _.R("div",{className:["ytlr-ghost-surface__row",(y["ytlr-ghost-surface__tiles-row"]=l,y["ytlr-ghost-surface--enable-left-nav"]=n,y)]},_.TC(dK(5),function(){var B={};return _.R(_.XJ,{className:["ytlr-ghost-surface__tile",(B["ytlr-ghost-surface__tile--shelf-tiles"]=l,B)],type:h?"pivot":"shelf"})}))}),_.Y(c,function(){return _.R("div",{className:"ytlr-ghost-surface__top-nav"},_.TC(dK(5),function(){return _.R(_.SJ,{className:"ytlr-ghost-surface__top-nav-tab",
borderRadius:1,height:2,width:7})}))}),_.Y(d,function(){return _.R(_.Tw,null,_.R(_.SJ,{className:"ytlr-ghost-surface__banner",width:73.5,height:7}),_.R("div",{className:"ytlr-ghost-surface__banner-metadata-container"},_.R(_.SJ,{className:"ytlr-ghost-surface__banner-metadata",borderRadius:1.5,height:3,width:3}),_.R(_.SJ,{className:"ytlr-ghost-surface__banner-metadata",borderRadius:1.25,height:2.5,width:18})))}),_.Y(e,function(){return _.TC(dK(2),function(y){var B={},z={};return _.R("div",{className:[(B["ytlr-ghost-surface__banner-shelves"]=
d,B),(z["ytlr-ghost-surface__banner-shelves--first-banner-shelf"]=d&&0===y,z)]},_.R(_.SJ,{className:"ytlr-ghost-surface__shelf-title",borderRadius:q/2,height:q,width:11.5}),_.R("div",{className:["ytlr-ghost-surface__row","ytlr-ghost-surface__shelf"]},_.TC(dK(4),function(){return _.R(_.XJ,{className:"ytlr-ghost-surface__tile",type:"shelf"})})))})}),_.Y(f||g,function(){var y={},B={};return _.R("div",{className:(y["ytlr-ghost-surface__side-nav-grid-container"]=f&&g||t,y)},_.Y(f,function(){return _.R(_.Tw,
null,_.R(_.SJ,{className:"ytlr-ghost-surface__side-nav-title",borderRadius:1.25,height:2.5,width:13}),_.TC(dK(10),function(){return _.R("div",{className:"ytlr-ghost-surface__side-nav-item-container"},_.R(_.SJ,{borderRadius:.875,height:1.75,width:14.375}))}))}),_.R("div",{className:(B["ytlr-ghost-surface__side-nav-grid"]=f&&g,B)},_.Y(g,function(){return _.R(_.Tw,null,_.TC(dK(3),function(){return _.R("div",{className:["ytlr-ghost-surface__row","ytlr-ghost-surface__grid-row"]},_.TC(dK(3),function(){return _.R(_.XJ,
{className:"ytlr-ghost-surface__tile",type:"grid"})}))}))})))}))},dK=function(a){var b=[];if(0>a)return[];for(var c=0;c<a;c+=1)b.push(c);return b};_.m().w("sy34");
var fK=_.Xx("x7x0Ac");
_.gK=function(){_.Q.apply(this,arguments);this.template=eK};_.G(_.gK,_.Q);_.gK.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.gK.prototype.shouldUpdate=function(a,b){return this.props.hidden&&a.hidden?!1:_.Q.prototype.shouldUpdate.call(this,a,b)};_.gK.TAG_NAME="ytlr-ghost-surface";_.J(fK,_.gK);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy4a");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy4e");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var hK=function(a){a=a.data;var b=a.icon;return _.R("host",null,_.R("div",{className:"ytlr-grid-button-renderer__highlight"}),_.R(_.X,{className:"ytlr-grid-button-renderer__title",data:a.title}),b?"SIGN_IN"===b.iconType?_.R("div",{className:"ytlr-grid-button-renderer__sign-in"}):_.R(_.ry,{className:"ytlr-grid-button-renderer__icon",icon:b}):_.R("div",null))};_.m().w("sy4f");
var iK=_.Xx("aqohWb");
_.jK=function(){_.Q.apply(this,arguments);this.template=hK};_.G(_.jK,_.Q);_.jK.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.jK.prototype.onSelect=function(a){_.U(this,"innertube-command",this.props.data.navigationEndpoint);_.P(a)};_.jK.TAG_NAME="ytlr-grid-button-renderer";_.J(iK,_.jK);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy3t");
var kK=_.p("RecurringActionTracker","evaf9e");
var mK;_.lK=new _.Dl("recurring_actions");mK=function(){this.localStorage=_.r.get(_.vd)};mK.prototype.get=function(a){var b=_.nK(this);b.data[a]||(b.data[a]={timesFired:0},this.localStorage.set(_.lK,b,31536E4));return b.data[a]};mK.prototype.clear=function(a){var b=_.nK(this);delete b.data[a];this.localStorage.set(_.lK,b,31536E4)};mK.prototype.lastPruned=function(){return _.nK(this).lastPruned};mK.prototype.info=function(){return JSON.stringify(_.nK(this),null,2)};
_.nK=function(a){var b=a.localStorage.get(_.lK);b||(b={data:{},lastPruned:_.Jo()},a.localStorage.set(_.lK,b,31536E4));return b};_.oK=_.K(kK,function(){return new mK});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy3z");
var pK=function(){this.j=_.r.get(_.oJ);this.g=function(){};this.h=!1};_.k=pK.prototype;_.k.start=function(a,b,c){c=void 0===c?function(){}:c;var d;this.g();null!==(d=this.bedrockPorts)&&void 0!==d&&d.playbackPreview&&(this.h=!0,this.j.load(),this.g=_.iJ(this.j,function(e){c(e)}),this.bedrockPorts.playbackPreview.start(a,b))};
_.k.adopt=function(a,b){b=void 0===b?function(){}:b;var c;this.g();if(a.blockAdoption)return this.suppress(),this.h=!1;if(null===(c=this.bedrockPorts)||void 0===c||!c.playbackPreview||!this.bedrockPorts.playbackPreview.adopt(a))return this.h=a=this.suppress();this.h=!0;b(!0);this.g=_.iJ(this.j,function(d){b(d)});return!0};_.k.suppress=function(){var a;return!(null===(a=this.bedrockPorts)||void 0===a||!a.playbackPreview)&&this.bedrockPorts.playbackPreview.suppress()};
_.k.end=function(){var a;this.h=!1;this.g();null!==(a=this.bedrockPorts)&&void 0!==a&&a.playbackPreview&&this.bedrockPorts.playbackPreview.end()};_.k.select=function(a){var b;this.g();return null!==(b=this.bedrockPorts)&&void 0!==b&&b.playbackPreview?a.blockAdoption?(this.end(),!1):this.bedrockPorts.playbackPreview.select():!1};_.k.isActive=function(){return this.h};_.E.Object.defineProperties(pK.prototype,{bedrockPorts:{configurable:!0,enumerable:!0,get:function(){return _.ov()}}});
var qK=_.p("PlaybackPreviewService","tu8a2");
_.rK=_.K(qK,function(){return new pK});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var sK=function(a){var b=a.children,c={};return _.R("host",{"aria-label":a.ariaLabel,role:"gridcell",className:(c["ytlr-playback-preview--adopted"]=this.state.adopted,c["ytlr-playback-preview--playing"]=this.state.Xi,c)},_.Vt(b),_.R("div",{className:"ytlr-playback-preview__focus-target"}))};_.m().w("sy3y");
var tK=_.Xx("Ud3B8b");
_.uK=function(){_.Q.apply(this,arguments);var a=this;this.B=_.r.get(_.$t);this.h=_.r.get(_.rK);this.template=sK;this.A=_.I(function(){});this.j=function(b){if(a.props.focused&&a.h.select(a.props.data))_.P(b);else a.onBlur()};this.C=function(b){_.S(a,{adopted:b,Xi:b});if(!b)a.onBlur()}};_.G(_.uK,_.Q);_.uK.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.k=_.uK.prototype;
_.k.O=function(){var a=this;if(this.props.focused)this.onFocus();_.D("enableSelectOnKeyup",!1)&&this.el.addEventListener("keyup",function(b){a.props.ee&&a.props.ee(b,a.j)});_.T(this,_.vD(this.B,function(){a.onBlur()}))};_.k.Ca=function(){_.Q.prototype.Ca.call(this);if(this.props.focused)this.onBlur()};_.k.W=function(a){if(!a.focused&&this.props.focused)this.onFocus();else if(a.focused&&!this.props.focused)this.onBlur()};_.k.onSelect=function(a){this.props.ce?this.props.ce(a,this.j):this.j(a)};
_.k.onFocus=function(){this.h.adopt(this.props.data,this.C,this.props.ug)||this.start()};_.k.onBlur=function(){this.A.cancel();this.h.end();_.S(this,{adopted:!1,Xi:!1})};
_.k.start=function(){var a=this;this.A.cancel();var b=new _.Zg(function(e){_.Wx({element:a.el,eventType:_.bn,ud:1E3,X:e})}),c=new _.Zg(function(e){setTimeout(e,a.props.data.delayMs)}),d=function(e){_.S(a,{Xi:e});if(!e)a.onBlur()};this.A=_.gh([b,c]).then(function(){a.h.start(a.props.data,a.el.querySelector(".ytlr-thumbnail-details"),d,a.props.ug)})};_.uK.TAG_NAME="ytlr-playback-preview";_.J(tK,_.uK);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy3w");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var vK=function(a){a=a.data;var b=a.text;return _.R("host",null,_.R("div",{className:["ytlr-thumbnail-overlay-bottom-panel-renderer__background","ytlr-tile-header-renderer__scale-focus-artifact"]}),_.R("div",{className:"ytlr-thumbnail-overlay-bottom-panel-renderer__content"},_.Y(a.icon,function(c){return _.R(_.ry,{className:"ytlr-thumbnail-overlay-bottom-panel-renderer__icon",icon:c})}),_.Y(b,function(c){return _.R(_.X,{data:c})})))};_.m().w("sy40");
var wK=_.Xx("PnxlYc");
_.xK=function(){_.Q.apply(this,arguments);this.template=vK};_.G(_.xK,_.Q);_.xK.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.xK.TAG_NAME="ytlr-thumbnail-overlay-bottom-panel-renderer";_.J(wK,_.xK);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var CK=function(a){var b=a.data,c=b.movingThumbnail,d=b.thumbnail,e=b.style,f=a.ariaLabel,g=a.focused,h=a.width,l=a.ce,n=a.ee,q=a.ug,t=_.R(_.Pz,{className:["ytlr-tile-header-renderer__thumbnail","ytlr-tile-header-renderer__scale-focus-artifact"],data:d||{},Ya:b.backgroundColor||"#373737",width:h,zd:_.vJ}),w=_.R("div",{className:"ytlr-tile-header-renderer__overlays"},_.TC(b.thumbnailOverlays,function(y){return _.R(_.xJ,{renderer:y,config:yK})}));a=zK(this);return _.R("host",Object.assign({},a?{}:{"aria-label":f,
role:"gridcell"},{className:AK(BK[e]+"-style")}),_.Y(a,function(y){return _.R(_.uK,{ariaLabel:f,data:y,focused:g,ce:l,ee:n,ug:q},t,w)},function(){return _.R(_.Tw,null,_.Y(c,function(y){return _.R(_.Pz,{className:["ytlr-tile-header-renderer__thumbnail","ytlr-tile-header-renderer__thumbnail--animated","ytlr-tile-header-renderer__scale-focus-artifact"],data:g?y:d||{},Ya:"transparent",width:h})}),t,w)}))};_.m().w("sy3x");
var DK=_.Xx("w6ro9c");
var EK={},BK=(EK.TILE_HEADER_STYLE_CIRCULAR="circular",EK.TILE_HEADER_STYLE_PADDED="padded",EK.TILE_HEADER_STYLE_RECTANGULAR="rectangular",EK.TILE_HEADER_STYLE_SQUARE="square",EK.TILE_HEADER_STYLE_UNSPECIFIED="unspecified",EK),yK={thumbnailOverlayResumePlaybackRenderer:{I:_.KJ,props:{className:["ytlr-tile-header-renderer__resume-playback","ytlr-tile-header-renderer__scale-focus-artifact"]}},thumbnailOverlayBottomPanelRenderer:{I:_.xK,props:{className:"ytlr-tile-header-renderer__bottom-panel"}},thumbnailOverlayTimeStatusRenderer:{I:_.LJ,
props:{className:["ytlr-tile-header-renderer__time-status","ytlr-tile-header-renderer__shift-focus-artifact"]}}},AK=_.$a("ytlr-tile-header-renderer").wd;
_.FK=function(){_.Q.apply(this,arguments);this.template=CK;this.h=_.r.get(_.vG)};_.G(_.FK,_.Q);_.FK.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;var zK=function(a){var b,c,d;if(a.h.isEnabled("ENABLE_MUSIC_PREVIEWS"))return a=a.props.onFocusCommand,(null===a||void 0===a?void 0:a.startInlinePlaybackCommand)||(null===(d=null===(c=null===(b=null===a||void 0===a?void 0:a.commandExecutorCommand)||void 0===b?void 0:b.commands)||void 0===c?void 0:c[0])||void 0===d?void 0:d.startInlinePlaybackCommand)};
_.FK.TAG_NAME="ytlr-tile-header-renderer";_.J(DK,_.FK);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var GK=function(a){a=a.data;var b={};return _.R("host",{className:(b["ytlr-line-renderer--wrap"]=a.wrap,b)},_.TC(this.getItems(a),function(c){return _.R(_.xJ,{renderer:c,config:{metadataBadgeRenderer:{I:_.uJ,props:{className:"ytlr-line-renderer__badge"}},formattedString:{I:_.X,props:{className:"ytlr-line-renderer__text"}}}})}))};_.m().w("sy42");
var HK=_.Xx("bP9VOd");
_.IK=function(){_.Q.apply(this,arguments);this.template=GK;this.getItems=_.wx(function(a){a=a.items||[];for(var b=[],c=0;c<a.length;c++){var d=a[c].lineItemRenderer;d&&(d.badge?b.push(d.badge):d.text&&b.push({formattedString:d.text}))}return b})};_.G(_.IK,_.Q);_.IK.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.IK.TAG_NAME="ytlr-line-renderer";_.J(HK,_.IK);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var JK=function(a){var b=a.data,c=b.lines;b=b.title;var d=a.width,e=a.highlighted,f={};return _.R("host",{className:(f["ytlr-tile-metadata-renderer--grid"]="grid"===a.style,f["ytlr-tile-metadata-renderer--highlighted"]=e,f)},_.R(_.X,{className:"ytlr-tile-metadata-renderer__title",data:b}),_.TC(c,function(g){return _.R(_.IK,{data:g.lineRenderer||{},className:"ytlr-tile-metadata-renderer__line",width:d})}))};_.m().w("sy41");
var KK=_.Xx("zoaawe");
_.LK=function(){_.Q.apply(this,arguments);this.template=JK};_.G(_.LK,_.Q);_.LK.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.LK.TAG_NAME="ytlr-tile-metadata-renderer";_.J(KK,_.LK);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.PK=function(a){for(var b="",c=0;c<a.length;c++){var d=a[c];d&&("string"===typeof d?b+=d+". ":_.MK(d)?b+=_.NK(d):Array.isArray(d)&&(b+=_.OK(d)))}return(a=b)?{accessibilityData:{label:a}}:null};_.QK=function(a){var b,c,d;return(a=null!==(b=null===a||void 0===a?void 0:a.text)&&void 0!==b?b:null===(d=null===(c=null===a||void 0===a?void 0:a.badge)||void 0===c?void 0:c.metadataBadgeRenderer)||void 0===d?void 0:d.label)?_.MK(a)?_.NK(a):a+". ":""};
_.NK=function(a){var b,c;return(null===(c=null===(b=a.accessibility)||void 0===b?void 0:b.accessibilityData)||void 0===c?0:c.label)?a.accessibility.accessibilityData.label+". ":_.Sx(a)+". "};
_.OK=function(a){for(var b,c,d=[],e=function(h){h=_.NK(h);d.length&&d[d.length-1]===h||d.push(h)},f=0;f<a.length;f++){var g=a[f];g&&((null===(b=g.thumbnailOverlayBottomPanelRenderer)||void 0===b?0:b.text)?e(g.thumbnailOverlayBottomPanelRenderer.text):(null===(c=g.thumbnailOverlayTimeStatusRenderer)||void 0===c?0:c.text)&&e(g.thumbnailOverlayTimeStatusRenderer.text))}return d.join("")};_.MK=function(a){return!!a&&(a.hasOwnProperty("simpleText")||a.hasOwnProperty("runs"))};_.m().w("sy43");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var RK,TK,ZK,XK,VK,UK,WK,$K,bL;
RK=function(){var a=_.Za();if(a.model.toLowerCase().includes("xbox"))a="//www.gstatic.com/ytlr/img/enter-key-xbox-with-background-4k.png";else{var b=a.model.toLowerCase();a="sony"!==a.brand||!b.includes("ps4")&&"ps3"!==b?"nintendo"===a.brand?"//www.gstatic.com/ytlr/img/enter-key-nintendo-with-background-4k.png":"roku"===a.brand?"//www.gstatic.com/ytlr/img/enter-key-roku-with-background-4k.png":"//www.gstatic.com/ytlr/img/enter-key-dpad-with-background-4k.png":"//www.gstatic.com/ytlr/img/enter-key-playstation-with-background-4k.png"}return _.yA({title:_.V("MENU_HINT"),image:{thumbnails:[{url:a}]}})};
TK=function(){if(_.D("enableHomeVoiceEduToastWithExample",!1))return _.yA({title:_.V("VOICE_HINT_TITLE"),subtitle:_.V("VOICE_HINT_WITH_EXAMPLE_MESSAGE",{example:SK[Math.floor(Math.random()*SK.length)]}),icon:{iconType:"MICROPHONE_ON"}});if(_.D("enableHomeVoiceEduToast",!1))return _.yA({title:_.V("VOICE_HINT_TITLE"),subtitle:_.V("VOICE_HINT_GENERIC_MESSAGE"),icon:{iconType:"MICROPHONE_ON"}})};
ZK=function(a,b,c){_.Tu(this,function e(){var f,g;return _.Su(e,function(h){if(1==h.g)return _.Ju(h,UK(a,b),2);f=h.h;var l=!!_.D("enableVoice",!1);g=(!!_.D("enableHomeVoiceEduToast",!1)||!!_.D("enableHomeVoiceEduToastWithExample",!1))&&VK(a,"voice_hint_toast_on_browse",l);f&&g?(l=WK(a,"menu_hint_toast"),WK(a,"voice_hint_toast_on_browse")>l?(c(TK()),XK(a)):(c(RK()),_.YK(a,"menu_hint_toast"))):g?(c(TK()),XK(a)):f&&(c(RK()),_.YK(a,"menu_hint_toast"));h.g=0})})};XK=function(a){_.YK(a,"voice_hint_toast_on_browse")};
VK=function(a,b,c){var d=a.get(b);d=d&&5<d.timesFired;a=WK(a,b);b=!0;d&&336>a?b=!1:!d&&24>a&&(b=!1);return c&&b};UK=function(a,b){return _.Tu(this,function d(){var e,f;return _.Su(d,function(g){if(1==g.g)return e=_.r.get(_.tv),_.Ju(g,e.get().then(function(h){return _.sr(h)}),2);f=g.h;return g.return(f&&VK(a,"menu_hint_toast",b))})})};WK=function(a,b){return(a=(a=a.get(b))&&a.lastFired)?(_.Jo()-a)/36E5:Infinity};
$K=function(a,b){return a.filter(function(c){c.menuNavigationItemRenderer?c=!0:c.menuServiceItemRenderer?(c=c.menuServiceItemRenderer.serviceEndpoint,c=!!((null===c||void 0===c?0:c.feedbackEndpoint)||(null===c||void 0===c?0:c.playlistEditEndpoint))):c=!1;return c}).map(function(c){var d=c.menuNavigationItemRenderer||c.menuServiceItemRenderer;c={title:d.text,trackingParams:d.trackingParams};if(d.navigationEndpoint)d=d.navigationEndpoint,c.navigationEndpoint=d.addToPlaylistEndpoint?{openClientOverlayAction:{context:d.addToPlaylistEndpoint.videoId,
type:"CLIENT_OVERLAY_TYPE_ADD_TO_PLAYLIST"}}:d.watchEndpoint||d.browseEndpoint||d.watchPlaylistEndpoint?{commandExecutorCommand:{commands:[d,{signalAction:{signal:"CLOSE_POPUP"}}]}}:d;else if(d.serviceEndpoint){d=d.serviceEndpoint;var e=[d,{signalAction:{signal:"CLOSE_POPUP"}}],f={commandExecutorCommand:{commands:e}};d.feedbackEndpoint?(e.push({removeItemAction:{childId:b}}),e.push(_.yA({title:_.V("VIDEO_DISMISSED")}))):d.playlistEditEndpoint&&e.push(_.yA({title:_.V("VIDEO_SAVED_TO_WATCH_LATER")}));
c.serviceEndpoint=f}return{compactLinkRenderer:c}})};
bL=function(a){var b=a.data,c=a.focused;a=a.style;if(!b.accessibility){var d=b.metadata&&b.metadata.tileMetadataRenderer,e="";if(d){_.MK(d.title)&&(e+=_.NK(d.title));d=d.lines||[];for(var f=0;f<d.length;++f){var g=d[f].lineRenderer;var h="";if(g){g=g.items||[];for(var l=0;l<g.length;++l)h+=_.QK(g[l].lineItemRenderer)}e+=h}}d=e;f=(f=b.header&&b.header.tileHeaderRenderer)?_.OK(f.thumbnailOverlays||[]):"";b.accessibility=_.PK([d+f])||{};d=_.qd("tile_renderer_aria_missing_report_fraction",.01);Math.random()>=
d||_.C(Error("zb`"+(b.header&&b.header.tileMetadataRenderer&&b.header.tileMetadataRenderer.title||b.trackingParams||"no title or trackingParams")))}h=b.accessibility;d=b.header;f=b.metadata;e=b.onFocusCommand;h=h&&h.accessibilityData&&h.accessibilityData.label||"";g=b.style||"TILE_STYLE_UNSPECIFIED";l={};return _.R("host",{hybridNavFocusable:!0,className:[aL[g],(l["ytlr-tile-renderer--grid"]="grid"===a,l)]},_.R(_.FK,{ariaLabel:h,className:"ytlr-tile-renderer__header",data:d&&d.tileHeaderRenderer||
{},onFocusCommand:e,focused:c,width:_.Bl[g],ce:this.h&&this.h.ce,ee:this.h&&this.h.ee,ug:b.trackingParams}),_.R(_.LK,{className:"ytlr-tile-renderer__metadata",data:f&&f.tileMetadataRenderer||{},style:a,width:_.Bl[g],highlighted:c}))};_.YK=function(a,b){var c=_.nK(a),d=a.get(b);c.data[b]={timesFired:d.timesFired+1,lastFired:_.Jo()};a.localStorage.set(_.lK,c,31536E4)};_.m().w("sy3s");
var SK=[_.V("EXAMPLE_VOICE_SEARCH_SPORTS_HIGHLIGHTS"),_.V("EXAMPLE_VOICE_SEARCH_FIND_ARIANA_GRANDE"),_.V("EXAMPLE_VOICE_SEARCH_MAKEUP_TUTORIALS"),_.V("EXAMPLE_VOICE_SEARCH_TRAVEL_VIDEOS"),_.V("EXAMPLE_VOICE_PLAY_COOKING_VIDEOS"),_.V("EXAMPLE_VOICE_PLAY_DICE_AN_ONION"),_.V("EXAMPLE_VOICE_PLAY_MUSIC_VIDEOS"),_.V("EXAMPLE_VOICE_PLAY_ARIANA_GRANDE"),_.V("EXAMPLE_VOICE_PLAY_TRAVEL_VIDEOS")];
var dL=function(a){var b=this;this.h=a;this.g=0;this.bedrockPorts=_.ov();this.ee=function(c,d){13===c.keyCode&&(clearTimeout(b.g),b.g=0,d(c))};this.ce=function(c,d){var e;if(13===c.keyCode&&(!_.D("enableSelectOnKeyup",!1)||_.D("enableVideoMenuOnBrowse",!1))){var f=_.D("enableSelectOnKeyup",!1)&&_.D("enableVideoMenuOnBrowse",!0),g=null===(e=b.h.props.data.menu)||void 0===e?void 0:e.menuRenderer;f&&g?cL(b,g):d(c)}}},eL=function(a,b){var c,d,e,f,g,h,l,n,q,t=null===(c=b.metadata)||void 0===c?void 0:c.tileMetadataRenderer;
if(t){var w=_.Sx(t.title);if(w){var y="";y=null===(l=null===(h=null===(g=null===(f=null===(e=null===(d=t.lines)||void 0===d?void 0:d[0])||void 0===e?void 0:e.lineRenderer)||void 0===f?void 0:f.items)||void 0===g?void 0:g[0])||void 0===h?void 0:h.lineItemRenderer)||void 0===l?void 0:l.text;y=_.Sx(y);if(t=null===(q=null===(n=b.header)||void 0===n?void 0:n.tileHeaderRenderer)||void 0===q?void 0:q.thumbnail){var B=t.thumbnails,z=_.Bl.TILE_STYLE_YTLR_FOUR_WIDE;if(B=B&&_.Oz(z,B)){if(z=b.contentId)return{title:w,
subtitle:y,imageUrl:B,trackingParams:b.trackingParams,Ap:function(){_.U(a.h,"innertube-command",{removeItemAction:{childId:b.contentId}})},thumbnailDetails:t,contentId:z};_.C(Error("ub"))}else _.C(Error("vb"))}else _.C(Error("wb"))}else _.C(Error("xb"))}else _.C(Error("yb"))},cL=function(a,b){0<a.g||(a.g=setTimeout(function(){clearTimeout(a.g);a.g=0;if(a.h.props.focused){_.cw(13);var c=eL(a,a.h.props.data);if(c){var d=c.title,e=c.subtitle,f=c.imageUrl,g=c.trackingParams,h=c.Ap,l=c.thumbnailDetails;
c=c.contentId;_.Eo(_.L.R(),new _.O("disableGuideAction"),void 0);a.bedrockPorts?a.bedrockPorts.showTileMenuOverlay(b,d,e,f,g,h):(f=a.h,b.items?(d=_.zA({title:d,subtitle:e,image:l,style:"OVERLAY_PANEL_HEADER_STYLE_VIDEO_THUMBNAIL"}),e=$K(b.items,c),d=_.vA({overlayPanelRenderer:_.AA(d,e)})):d={},_.U(f,"innertube-command",d))}}},500))};
var fL=_.Xx("MW17fd");
var gL={},aL=(gL.TILE_STYLE_YTLR_THREE_WIDE="ytlr-tile-renderer--three-wide",gL.TILE_STYLE_YTLR_TWO_WIDE="ytlr-tile-renderer--two-wide",gL);
_.hL=function(){_.Q.apply(this,arguments);var a=this;this.template=bL;this.bedrockPorts=_.ov();this.recurringActionTracker=_.r.get(_.oK);this.j=function(b){_.U(a,"innertube-command",a.props.data.onSelectCommand);_.P(b)}};_.G(_.hL,_.Q);_.hL.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.hL.prototype.O=function(){var a=this;_.D("enableSelectOnKeyup",!1)&&this.el.addEventListener("keyup",function(b){a.h.ee(b,a.j)})};_.hL.prototype.W=function(a){!a.focused&&this.props.focused&&iL(this)};
var iL=function(a){var b=a.props.data.onFocusCommand&&a.props.data.onFocusCommand.commandExecutorCommand&&a.props.data.onFocusCommand.commandExecutorCommand.commands;if(b){b=_.A(b);for(var c=b.next();!c.done;c=b.next())c=c.value,c.showHintCommand&&c.showHintCommand.shouldShowHint&&ZK(a.recurringActionTracker,!(!a.props.data.menu||!a.props.data.menu.menuRenderer),function(d){_.U(a,"innertube-command",d)})}};_.hL.prototype.onSelect=function(a){this.h.ce(a,this.j)};
_.E.Object.defineProperties(_.hL.prototype,{h:{configurable:!0,enumerable:!0,get:function(){this.A||(this.A=new dL(this));return this.A}}});_.hL.TAG_NAME="ytlr-tile-renderer";_.J(fL,_.hL);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.jL=function(a){return a?a.standaloneYpcBadgeRenderer?{label:_.Sx(a.standaloneYpcBadgeRenderer.label),style:"BADGE_STYLE_TYPE_YPC"}:a.standaloneRedBadgeRenderer?{label:a.standaloneRedBadgeRenderer.iconText,style:"BADGE_STYLE_TYPE_RED"}:a.metadataBadgeRenderer?a.metadataBadgeRenderer:null:null};_.m().w("sy45");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var lL=function(a){var b=a.data,c=a.focused,d=a.style;a=b.title;var e=b.shortBylineText,f=b.thumbnailOverlays,g=_.jL(b.bottomStandaloneBadge);b=b&&b.thumbnailRenderer&&b.thumbnailRenderer.showCustomThumbnailRenderer&&b.thumbnailRenderer.showCustomThumbnailRenderer.thumbnail||{};var h=_.$a("ytlr-grid-show-renderer"),l=h.el;h=h.wd;d=d?h(d+"-style"):"";return _.R("host",{role:"gridcell",className:d,"aria-label":_.Sx(a)},_.R("div",{className:l("thumbnail")},_.R(_.Pz,{data:b,width:21,Ya:"#373737"}),_.Y(f,
function(){return kL(f)})),_.R(_.X,{className:l("title"),data:a}),_.R(_.X,{className:l("details"),data:e}),_.R("div",{className:l("badges")},_.Y(g,function(){return _.R(_.uJ,{data:g,highlighted:c})})))},kL=function(a){return a.filter(function(b){return!!b.thumbnailOverlayBottomPanelRenderer}).map(function(b){return _.R(_.xK,{className:"ytlr-grid-show-renderer__bottom-panel",data:b.thumbnailOverlayBottomPanelRenderer})})};_.m().w("sy44");
var mL=_.Xx("AbRKuc");
_.nL=function(){_.Q.apply(this,arguments);this.template=lL};_.G(_.nL,_.Q);_.nL.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.nL.TAG_NAME="ytlr-grid-show-renderer";_.J(mL,_.nL);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var oL=function(a){return!a||"string"!==typeof a&&!a.runs&&!a.simpleText},pL=function(){return _.R("host",null)},qL=function(a){var b=a.focused,c=a.style,d=a.data;a=d.badges;var e=d.thumbnail,f=d.title,g=d.topMetadataItems,h=d.lengthText;d=function(){var w=h&&h.accessibility&&h.accessibility.accessibilityData&&h.accessibility.accessibilityData.label,y=g&&g[0]&&g[0].simpleText;return"\n      "+_.Sx(f)+".\n      "+y+".\n      "+(w?w+".":"")+"\n    "}();var l=_.$a("ytlr-compact-movie-renderer"),n=l.el;
l=l.wd;c=c?l(c+"-style"):"";var q=a&&a.length&&a[0].metadataBadgeRenderer,t=q&&"BADGE_STYLE_TYPE_YPC"===q.style;return _.R("host",{hybridNavFocusable:!0,className:c,role:"gridcell","aria-label":d},_.R("div",{className:n("thumbnail")},_.R(_.Pz,{data:e||{},width:21,Ya:"#373737"}),_.Y(h,function(){return _.R(_.X,{className:n("time-status"),data:h})})),_.R(_.X,{className:n("title"),data:f}),_.R("div",{className:n("details")},_.Y(!t&&q,function(){return _.R(_.uJ,{className:n("badge"),data:q||{},highlighted:b})}),
_.Y(g&&g.length,function(){return _.R(_.X,{className:n((t?"block":"inline")+"-detail"),data:g[0]})}),_.Y(t&&q,function(){return _.R(_.uJ,{className:n("badge"),data:q||{},highlighted:b})})))},rL=function(a){var b=a.data,c=a.style;a=b.title;var d=b.publishedTimeText,e=b.shortBylineText,f=b.thumbnailText||b.videoCountText,g=b.thumbnailRenderer;if(b.thumbnail)var h=b.thumbnail;else g&&(g.playlistVideoThumbnailRenderer?h=g.playlistVideoThumbnailRenderer.thumbnail:g.playlistCustomThumbnailRenderer&&(h=
g.playlistCustomThumbnailRenderer.thumbnail));b=2===[e,d].filter(function(n){return!oL(n)}).length;g=_.$a("ytlr-grid-playlist-renderer");var l=g.el;g=g.wd;c=c?g(c+"-style"):"";return _.R("host",{hybridNavFocusable:!0,className:c,role:"gridcell","aria-label":_.Sx(a)},_.R("div",{className:l("thumbnail")},_.R(_.Pz,{data:h||{},width:21,Ya:"#373737",zd:_.vJ}),_.Y(!oL(f),function(){return _.R(_.X,{className:l("thumbnail-text"),data:f})})),_.R(_.X,{className:l("title"),data:a}),_.R("div",{className:l("details")},
_.Y(!oL(e),function(){return _.R(_.X,{className:l("inline-detail"),data:e})}),_.Y(b,function(){return _.R("span",{className:l("inline-detail")},"\u2022")}),_.Y(!oL(d),function(){return _.R(_.X,{className:l("inline-detail"),data:d})})))},tL=function(a){var b=a.data,c=a.focused;a=a.style;var d=b.publishedTimeText,e=b.shortBylineText,f=b.shortViewCountText,g=b.thumbnailOverlays,h=b.title,l=b.upcomingEventData,n=b.lengthText,q=l&&l.upcomingEventText;l=a?sL(a+"-style"):"";return _.R("host",{hybridNavFocusable:!0,
className:l,role:"gridcell","aria-label":function(){return[_.Sx(h),_.Sx(e),_.Sx(f),_.Sx(q||d),n&&n.accessibility&&n.accessibility.accessibilityData&&n.accessibility.accessibilityData.label||""].filter(function(t){return!oL(t)}).join(". ")}()},_.Y(this.movingThumbnail,function(t){return _.R(_.PJ,{className:["ytlr-grid-video-renderer__thumbnail","ytlr-grid-video-renderer__thumbnail--animated"],Ya:"transparent",thumbnail:c?t:b.thumbnail||{},thumbnailOverlays:g||[]})}),_.R(_.PJ,{className:"ytlr-grid-video-renderer__thumbnail",
thumbnail:b.thumbnail||{},thumbnailOverlays:g||[]}),_.R(_.DJ,{data:b,style:a}))},wL=function(a){var b=a.data;a=(a=a.style)?uL(a+"-style"):"";return _.R("host",{hybridNavFocusable:!0,className:a,role:"gridcell","aria-label":_.Sx(b.title)},_.R("div",{className:"ytlr-grid-channel-renderer__thumbnail"},_.R(_.Pz,{data:b.thumbnail||{},width:10.75,Ya:"#373737"})),_.R(_.X,{className:"ytlr-grid-channel-renderer__title",data:b.title}),_.R("div",{className:vL("details")},_.R(_.X,{className:"ytlr-grid-channel-renderer__inline-detail",
data:b.subscriberCountText})))},yL=function(a){var b=this,c=a.alignment,d=a.data,e=a.disableSounds,f=a.sf,g=a.width,h=a.ea;a=a.Yf;var l=function(t){return _.R(_.xJ,{config:b.A(t.selected),renderer:t.item})},n=d.selectedIndex||0,q=d.items||[];n=0<q.length&&3>q.length-1-n;return _.R("host",null,_.Y(0===q.length&&a,function(){return _.R(_.gK,{className:"ytlr-horizontal-list-renderer__ghost-row",idomKey:"ytlr-horizontal-list-renderer__ghost-row",tiles:!0,width:g-3})},function(){return _.R(_.Fz,{className:"ytlr-horizontal-list-renderer__items",
idomKey:"ytlr-horizontal-list-renderer__items",alignment:c,items:q,layout:"horizontal",M:b.M,selectedIndex:d.selectedIndex||0,size:g,xa:f,spacing:_.D("enableZdsTypeAndSpacing",!1)?1.5:1,ea:h,gb:3.5,Fa:l,Wb:"tile-to-tile",version:d.version,disableSounds:!!e})}),_.R(_.xL,{data:this.reloadContinuationData,Zf:0===q.length,parentTrackingParams:d.trackingParams,Ta:this.D}),_.R(_.xL,{data:this.nextContinuationData,Zf:n,parentTrackingParams:d.trackingParams,Ta:this.C}))};_.m().w("sy2s");
_.xL=function(){_.Q.apply(this,arguments);this.template=pL;this.pending=!1;this.j=_.L.R()};_.G(_.xL,_.Q);_.xL.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.xL.prototype.O=function(){zL(this)};_.xL.prototype.W=function(){this.props.parentTrackingParams!==this.h&&(this.pending=!1);zL(this)};
var zL=function(a){if(a.props.data.continuation&&a.props.Zf&&(!a.pending||a.props.parentTrackingParams!==a.h)){a.pending=!0;var b=a.h=a.props.parentTrackingParams;_.U(a,"innertube-continuation-request",{continuation:a.props.data,parentTrackingParams:a.props.parentTrackingParams,onResponse:function(c){a.props.parentTrackingParams===b&&a.el&&(a.pending=!1,a.props.Ta(c),_.Mt({stage:20,X:function(){_.Eo(a.j,new _.O("continuationRenderedAction"),{fc:c.trackingParams,parent:b})}}))}})}};_.xL.TAG_NAME="yt-continuation";
_.AL=function(){_.Q.apply(this,arguments);this.template=qL};_.G(_.AL,_.Q);_.AL.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.AL.prototype.onSelect=function(a){_.U(this,"innertube-command",this.props.data.navigationEndpoint);_.P(a)};_.AL.TAG_NAME="ytlr-compact-movie-renderer";
_.BL=function(){_.Q.apply(this,arguments);this.template=rL};_.G(_.BL,_.Q);_.BL.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.BL.prototype.onSelect=function(a){_.U(this,"innertube-command",this.props.data.navigationEndpoint);_.P(a)};_.BL.prototype.O=function(){_.zJ(this.el,"ytlr-grid-playlist-renderer")};_.BL.prototype.W=function(){_.zJ(this.el,"ytlr-grid-playlist-renderer")};_.BL.TAG_NAME="ytlr-grid-playlist-renderer";
var sL=_.$a("ytlr-grid-video-renderer").wd;
_.CL=function(){_.Q.apply(this,arguments);this.template=tL};_.G(_.CL,_.Q);_.CL.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.CL.prototype.onSelect=function(a){_.U(this,"innertube-command",this.props.data.navigationEndpoint);_.P(a)};_.E.Object.defineProperties(_.CL.prototype,{movingThumbnail:{configurable:!0,enumerable:!0,get:function(){var a=this.props.data.richThumbnail;return a&&a.movingThumbnailRenderer&&a.movingThumbnailRenderer.movingThumbnailDetails}}});_.CL.TAG_NAME="ytlr-grid-video-renderer";
var DL=_.$a("ytlr-grid-channel-renderer"),uL=DL.wd,vL=DL.el;
var EL=function(){_.Q.apply(this,arguments);this.template=wL};_.G(EL,_.Q);EL.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;EL.prototype.onSelect=function(a){_.U(this,"innertube-command",this.props.data.navigationEndpoint);_.P(a)};EL.TAG_NAME="ytlr-grid-channel-renderer";
_.FL=function(){_.Q.apply(this,arguments);var a=this;this.template=yL;this.j=!!_.Vu.instance&&_.Wu();this.h=_.L.R();this.M=function(b,c){c=void 0===c?!0:c;a.props.data.selectedIndex=b;a.props.bh&&a.props.bh(b);c&&_.qx(a)};this.C=function(b){if(b.continuationContents&&b.continuationContents.horizontalListContinuation){var c=b.continuationContents.horizontalListContinuation;c.items&&(a.props.data.items=_.F(a.props.data.items||[]).concat(_.F(c.items)));a.props.data.continuations=c.continuations;a.props.Ta&&
b&&a.props.Ta(b);_.qx(a)}};this.D=function(b){if(b.continuationContents&&b.continuationContents.horizontalListContinuation){b=b.continuationContents.horizontalListContinuation;var c=b.trackingParams,d=b.continuations;a.props.data.items=b.items;a.props.data.trackingParams=c;a.props.data.continuations=d;_.qx(a)}};this.A=function(b){b={focused:b&&a.props.focused,style:a.props.ag||"shelf"};return{compactMovieRenderer:{I:_.AL,props:b},gridButtonRenderer:{I:_.jK,props:b},gridVideoRenderer:{I:_.CL,props:b},
gridChannelRenderer:{I:EL,props:b},gridPlaylistRenderer:{I:_.BL,props:b},gridShowRenderer:{I:_.nL,props:b},tileRenderer:{I:_.hL,props:b}}}};_.G(_.FL,_.Q);_.FL.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.FL.prototype.O=function(){_.T(this,_.N(this.h,new _.O("removeItemAction"),this,this.F));var a=this.props.data.selectedIndex||0;this.props.focused&&this.Vb&&(2<=a?GL(this,"HIDE_GUIDE"):GL(this,"SHOW_GUIDE"))};
_.FL.prototype.W=function(a){if(this.props.focused||this.props.data===a.data)a.focused&&!this.props.focused?GL(this,"SHOW_GUIDE"):(a=this.props.data.selectedIndex||0,this.props.focused&&2<=a&&this.Vb?GL(this,"HIDE_GUIDE"):this.props.focused&&2>a&&this.Vb&&GL(this,"SHOW_GUIDE"))};
var HL=function(a,b){var c;a.props.data.items&&(a.props.data.selectedIndex===a.props.data.items.length-1&&a.M(a.props.data.selectedIndex-1,!1),a.props.data.items.splice(b,1),a.props.data.items=a.props.data.items.slice(),a.props.data.version=(null!==(c=a.props.data.version)&&void 0!==c?c:0)+1)};
_.FL.prototype.F=function(a){var b=a.command.removeItemAction&&a.command.removeItemAction.childId;b&&this.props.data.items&&(a=this.props.data.items.findIndex(function(c){return!!c.tileRenderer&&c.tileRenderer.contentId===b}),0>a||(HL(this,a),0===this.props.data.items.length&&this.props.parentTrackingParams&&this.props.Zl?_.U(this,"innertube-command",{removeItemAction:{childId:this.props.parentTrackingParams,parentId:this.props.Zl}}):(this.Vb||(_.Eo(this.h,new _.O("enableGuideAction"),void 0),GL(this,
"SHOW_GUIDE")),_.qx(this),_.U(this,"innertube-command",{removeItemAction:{childId:b,parentId:"surface"}}))))};var GL=function(a,b){"pivot"!==a.props.ag&&a.j&&a.j.resolveCommand({signalAction:{signal:b}})};
_.E.Object.defineProperties(_.FL.prototype,{B:{configurable:!0,enumerable:!0,get:function(){return this.props.data.items&&this.props.data.items.length||0}},Vb:{configurable:!0,enumerable:!0,get:function(){return 4<=this.B}},nextContinuationData:{configurable:!0,enumerable:!0,get:function(){if(this.props.data.continuations)for(var a=0;a<this.props.data.continuations.length;a++){var b=this.props.data.continuations[a];if(b.nextContinuationData)return b.nextContinuationData;if(b.nextRadioContinuationData)return b.nextRadioContinuationData}return{}}},
reloadContinuationData:{configurable:!0,enumerable:!0,get:function(){if(this.props.data.continuations)for(var a=0;a<this.props.data.continuations.length;a++){var b=this.props.data.continuations[a];if(b.reloadContinuationData)return b.reloadContinuationData}return{}}}});_.FL.TAG_NAME="ytlr-horizontal-list-renderer";

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.IL=function(a){return function(b){if(b.selectedIndex<=a)return 0;var c=b.sa.length-1;c=b.sa[c]+b.xa(b.items[c],c)+b.gb;if(c<=b.size)return 0;var d=b.sa[a],e=b.sa[b.selectedIndex];return e>c-(b.size-d)?c-b.size:e-d}};
_.JL=function(a){return a?a.gridVideoRenderer||a.gridPlaylistRenderer||a.compactMovieRenderer||a.gridButtonRenderer||a.gridShowRenderer?21:a.tileRenderer?(a=_.Bl[a.tileRenderer.style||"TILE_STYLE_UNSPECIFIED"],_.D("enableZdsIncreaseTileSize",!1)&&21===a?22:_.D("enableZdsIncreaseTileSize",!1)&&12===a?12.375:a):a.gridChannelRenderer?15.5:0:0};_.m().w("sy2w");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var KL=function(a){var b=a.data;a=a.selected;b=_.Sx(b.text||b.title);var c={};return _.R("host",{role:"button","aria-label":b,className:(c["ytlr-chip--selected"]=a,c)},_.R(_.X,{data:b}))};_.m().w("sy48");
var LL=_.p("YtlrChip","nPvg2e");
_.ML=function(){_.Q.apply(this,arguments);this.template=KL};_.G(_.ML,_.Q);_.ML.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.ML.prototype.onSelect=function(a){_.U(this,"innertube-command",this.props.data.navigationEndpoint);_.P(a)};_.ML.TAG_NAME="ytlr-chip";_.J(LL,_.ML);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy46");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy49");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var NL=function(a){var b=a.data,c=a.focused,d=a.ea,e=a.width;a=a.zi;var f=_.Sx(b.title),g={};return _.R("host",{className:(g["ytlr-shelf-renderer--enable-left-nav"]=_.D("enableLeftNav",!1),g["ytlr-chip-cloud-renderer--header"]=a,g),"aria-label":f,role:"row"},_.R("div",{className:"ytlr-shelf-renderer__title",idomKey:"ytlr-shelf-renderer__title"},_.R(_.X,{className:["ytlr-chip-cloud-renderer__shelf-title"],data:f,role:"rowheader"})),_.R(_.XC,{className:"ytlr-chip-cloud-renderer__chip-list",idomKey:"ytlr-chip-cloud-renderer__chip-list",
alignment:_.sy,items:b.chips||[],Fa:function(h){var l=h.selected;return _.R(_.ML,{data:h.item.chipCloudChipRenderer||{},focused:c&&l,selected:l})},size:e,M:this.M,selectedIndex:b.selectedIndex||0,spacing:1,ea:d||3,gb:3.5}))};_.m().w("sy47");
var OL=_.p("YtlrChipCloudRenderer","eUjfrc");
_.QL=function(){_.Q.apply(this,arguments);var a=this;this.h=!!_.Vu.instance&&_.Wu();this.template=NL;this.M=function(b,c){a.props.data.selectedIndex=b;c!==a.props.data.Oi&&((a.props.data.Oi=c)?PL(a,"HIDE_GUIDE"):PL(a,"SHOW_GUIDE"));a.props.Gl&&a.props.Gl();_.qx(a)}};_.G(_.QL,_.Q);_.QL.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;
_.QL.prototype.O=function(){var a=this,b=_.L.R();this.props.focused&&(this.props.data.Oi?PL(this,"HIDE_GUIDE"):PL(this,"SHOW_GUIDE"));_.T(this,function(){a.props.focused&&PL(a,"SHOW_GUIDE")});_.T(this,_.N(b,new _.O("SHOW_GUIDE"),this,function(){a.props.focused&&a.props.data.Oi&&PL(a,"HIDE_GUIDE")}))};_.QL.prototype.W=function(a){a.focused&&!this.props.focused&&PL(this,"SHOW_GUIDE")};var PL=function(a,b){a.h&&a.h.resolveCommand({signalAction:{signal:b}})};_.QL.TAG_NAME="ytlr-chip-cloud-renderer";
_.J(OL,_.QL);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var RL=function(a){var b=_.Vt(a.children),c=0;return _.R("host",null,b.map(function(d){return d instanceof Function?function(){d({EE:a.sd?a.sd(c):!0,focused:a.selectedIndex===c&&a.focused,selected:a.selectedIndex===c});c++}:d}))};_.m().w("sy2t");
var SL=function(){_.Q.apply(this,arguments);this.template=RL};_.G(SL,_.Q);SL.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;SL.prototype.O=function(){var a=this;this.g("focusin",function(b){var c=a.el.children;b=b.target;for(var d=0,e=c.length;d<e;++d)if(a.props.selectedIndex!==d&&c[d]===b){a.props.M(d);break}})};SL.TAG_NAME="yt-focus-container";
var TL={},UL={},VL={horizontal:(TL[37]=-1,TL[39]=1,TL),vertical:(UL[38]=-1,UL[40]=1,UL)},WL=function(){_.Q.apply(this,arguments);this.template=RL};_.G(WL,_.Q);WL.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;
WL.prototype.O=function(){var a=this;this.g("keydown",function(b){var c=VL[a.props.layout][a.props.Ni?_.vy(b.keyCode):b.keyCode];if("number"===typeof c){var d=XL(a,c);d!==a.props.selectedIndex?(a.props.M(d),_.bw(b),a.props.nf&&_.U(a,"yt-virtual-list__index-change")):a.props.nf&&0<c&&_.U(a,"yt-virtual-list__right-edge")}})};
var XL=function(a,b){if(0===b)return a.props.selectedIndex;for(var c=a.props.sd||function(){return!0},d=a.el.children.length,e=a.props.selectedIndex+b;0<=e&&e<d;){if(c(e))return e;e+=b}return a.props.selectedIndex};WL.TAG_NAME="yt-focus-container";
_.YL=_.D("isCobaltHybridNav",!1)?SL:WL;

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy3d");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var fM=function(a){var b=a.data,c=b.icon,d=b.text,e=b.isDisabled;b=a.rd;var f=a.focused,g=a.Hm;a=a.Vm||e;e={};e=[ZL[this.props.shape||"rectangular"],$L[this.props.size||"small"],aM[this.props.Cc||"label-standalone"],bM[this.props.color||"footprint-gray"],cM[this.props.iconColor||"default"],dM[this.props.Zh||0],eM[this.props.iconPosition||"default"],(e["ytlr-button-renderer--disabled"]=a,e)];var h={};h=(h["ytlr-button-renderer--disabled-text"]=a,h["ytlr-button-renderer__label--hidden"]=b,h);var l=
this.props.data.accessibilityData&&this.props.data.accessibilityData.accessibilityData,n=_.Sx(d);l=l&&l.label||n;b||l!==n||(l="");return _.R("host",{hybridNavFocusable:a,role:"button","aria-pressed":g,className:e,"aria-label":l,"aria-hidden":!f},_.Y(c,function(q){return _.R(_.ry,{className:"ytlr-button-renderer__icon",icon:q})}),_.R(_.X,{className:["ytlr-button-renderer__label",h],data:d}))};_.m().w("sy3e");
var gM={},dM=(gM[0]="",gM[150]="ytlr-button-renderer--fast-focus-delay",gM[200]="ytlr-button-renderer--default-focus-delay",gM),hM={},ZL=(hM.circular="ytlr-button-renderer--circular-shape",hM.rectangular="",hM.pane="ytlr-button-renderer--pane-shape",hM["pivot-footer"]="ytlr-button-renderer--pivot-footer-shape",hM.transport="ytlr-button-renderer--transport-shape",hM["settings-list-control"]="ytlr-button-renderer--settings-list-control-shape",hM["survey-ad"]="ytlr-button-renderer--survey-ad-shape",
hM["you-there"]="ytlr-button-renderer--you-there-shape",hM),iM={},$L=(iM.small="ytlr-button-renderer--small-size",iM.regular="ytlr-button-renderer--regular-size",iM.large="ytlr-button-renderer--large-size",iM["pivot-footer"]="ytlr-button-renderer--pivot-footer-size",iM.transport="",iM["settings-list-control"]="ytlr-button-renderer--settings-list-control-size",iM["side-panel"]="ytlr-button-renderer--side-panel-size",iM["you-there"]="ytlr-button-renderer--you-there-size",iM),jM={},aM=(jM["label-below-icon"]=
"ytlr-button-renderer--label-below-icon",jM["label-pivot-footer"]="ytlr-button-renderer--label-pivot-footer",jM["label-standalone"]="ytlr-button-renderer--label-standalone",jM),kM={},bM=(kM["canvas-black"]="ytlr-button-renderer--canvas-black-color",kM["footprint-gray"]="ytlr-button-renderer--footprint-gray-color",kM.offer="ytlr-button-renderer--offer-color",kM["pivot-footer"]="ytlr-button-renderer--pivot-footer-color",kM["simple-transparent"]="ytlr-button-renderer--simple-transparent-color",kM["survey-item"]=
"ytlr-button-renderer--survey-item-color",kM.transparent="ytlr-button-renderer--transparent-color",kM["you-there"]="ytlr-button-renderer--you-there-color",kM),lM={},cM=(lM.blue="ytlr-button-renderer--blue-icon-color",lM["detail-light"]="",lM["default"]="",lM),mM={},eM=(mM.right="ytlr-button-renderer--right-icon-position",mM["default"]="",mM);
_.nM=function(){_.Q.apply(this,arguments);this.template=fM};_.G(_.nM,_.Q);_.nM.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.nM.prototype.onSelect=function(a){this.props.data.isDisabled||_.U(this,"innertube-command",this.props.data.command||this.props.data.navigationEndpoint||this.props.data.serviceEndpoint);_.P(a)};_.nM.TAG_NAME="ytlr-button-renderer";

_.m().l();

}catch(e){_._DumpException(e)}
try{
var oM=function(a){var b=a.focused,c=a.data;a=c.question;var d=c.title,e=c.dismissButton;c=c.respondButton;var f=_.Sx(d)+". "+_.Sx(a),g={};return _.R("host",{"aria-label":f,className:(g["ytlr-popup-survey-shelf-renderer--enable-left-nav"]=_.D("enableLeftNav",!1),g),role:"row"},_.R(_.X,{className:"ytlr-popup-survey-shelf-renderer__title",data:d}),_.R(_.X,{className:"ytlr-popup-survey-shelf-renderer__question",data:a}),_.R(_.YL,{layout:"horizontal",focused:b,selectedIndex:this.bb,M:this.M,nf:!0,className:"ytlr-popup-survey-shelf-renderer__buttons"},
_.Y(c&&c.buttonRenderer,function(h){return _.R(_.nM,{data:h})}),_.Y(e&&e.buttonRenderer,function(h){return _.R(_.nM,{data:h})})))};_.m().w("sy4b");
var pM=_.p("YtlrPopupSurveyShelfRenderer","r1oksf");
_.qM=function(a){_.Q.call(this,a);var b=this;this.bedrockPorts=_.ov();this.template=oM;this.M=function(c){_.S(b,{bb:c})};this.state={bb:0};this.bedrockPorts&&_.T(this,_.N(_.L.R(),new _.O("openPopupAction"),this,this.sp))};_.G(_.qM,_.Q);_.qM.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.k=_.qM.prototype;
_.k.sp=function(a){var b=this,c="FULLSCREEN_OVERLAY"===a.command.openPopupAction.popupType;this.bedrockPorts&&this.bedrockPorts.showPopupSurveyOverlay&&c&&this.bedrockPorts.showPopupSurveyOverlay(a.command,function(d){_.U(b,"innertube-command",d)})};_.k.O=function(){_.T(this,_.N(_.L.R(),new _.O("SUBMIT_FORM"),this,this.Oo))};_.k.W=function(a){if(!a.focused&&this.props.focused)this.onFocus()};
_.k.onFocus=function(){this.props.data.onVisibleCommand&&(_.U(this,"innertube-command",this.props.data.onVisibleCommand),delete this.props.data.onVisibleCommand)};_.k.Oo=function(){var a=1===this.state.bb?this.onDismissCommand:this.onSubmitCommand;this.el.style.visibility="hidden";_.U(this,"innertube-command",a)};
_.E.Object.defineProperties(_.qM.prototype,{removeItemAction:{configurable:!0,enumerable:!0,get:function(){if(this.props.parentTrackingParams)return{removeItemAction:{childId:this.props.data.trackingParams,parentId:this.props.parentTrackingParams}}}},bb:{configurable:!0,enumerable:!0,get:function(){return this.state.bb}},onDismissCommand:{configurable:!0,enumerable:!0,get:function(){return{commandExecutorCommand:{commands:_.F(this.removeItemAction?[this.removeItemAction]:[]).concat()}}}},onSubmitCommand:{configurable:!0,
enumerable:!0,get:function(){return{commandExecutorCommand:{commands:_.F(this.props.data.onSubmitCommand&&this.props.data.onSubmitCommand.commandExecutorCommand&&this.props.data.onSubmitCommand.commandExecutorCommand.commands||[]).concat(_.F(this.removeItemAction?[this.removeItemAction]:[]))}}}}});_.qM.TAG_NAME="ytlr-popup-survey-shelf-renderer";_.J(pM,_.qM);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var rM=function(a){var b=this,c=a.data,d=c.headerText,e=c.hintText,f=c.promoText,g=c.buttons,h=c.style,l=a.focused;a={};return _.R("host",null,_.Y(c.logoImage,function(n){return _.R(_.Pz,{className:["ytlr-promo-shelf-renderer__logo"],data:n,idomKey:"ytlr-promo-shelf-renderer__logo",width:15,Dg:!1})}),_.Y(d,function(n){return _.R(_.X,{className:["ytlr-shelf-renderer__title","ytlr-promo-shelf-renderer__title"],data:n,idomKey:"ytlr-promo-shelf-renderer__title",role:"rowheader"})}),_.R(_.X,{className:["ytlr-promo-shelf-renderer__promo-text",
(a["ytlr-promo-shelf-renderer--small-text"]="PROMO_SHELF_RENDERER_STYLE_SMALL_TEXT"===h,a["ytlr-promo-shelf-renderer--no-hint-text"]=!e,a)],data:f,idomKey:"ytlr-promo-shelf-renderer__promo-text"}),_.Y(e,function(n){return _.R(_.X,{className:"ytlr-promo-shelf-renderer__hint",data:n,idomKey:"ytlr-promo-shelf-renderer__hint"})}),_.Y(g,function(n){return _.R(_.YL,{layout:"horizontal",className:"ytlr-promo-shelf-renderer__buttons",focused:l,idomKey:"ytlr-promo-shelf-renderer__buttons",selectedIndex:b.props.data.bb||
0,M:b.M},_.TC(n,function(q,t){return _.R(_.nM,{data:q.buttonRenderer||{},focused:(b.props.data.bb||0)===t})}))}))},sM=function(a,b){return["3.14159rad, rgba(0, 0, 0, 0) 76.16%, "+a+" 100%","4.73717rad, rgba(0, 0, 0, 0) 0.49%, rgba(0, 0, 0, 0) 41.94%, "+b+" 58.98%, "+a+" 99.52%"]},uM=function(a,b){if(tM=tM||document.querySelector(".ytlr-app__content"))tM.style.backgroundImage=a,tM.style.backgroundSize=b},xM=function(){return(_.D("enableZds",!1)?vM:wM).map(function(a){return"linear-gradient("+a+")"}).join(", ")};
_.m().w("sy2z");
var yM=_.p("YtlrPromoShelfRenderer","n6lRM");
var wM=sM("rgb(47, 47, 47)","rgba(47, 47, 47, 0.8)"),vM=sM("rgb(24, 24, 24)","rgba(24, 24, 24, 0.8)"),tM=null;
_.zM=function(){_.Q.apply(this,arguments);var a=this;this.template=rM;this.h=_.L.R();this.M=function(b){a.props.data.bb=b;_.qx(a)}};_.G(_.zM,_.Q);_.zM.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.zM.prototype.O=function(){var a=this;this.props.selected&&(AM(this),BM(this));_.T(this,_.N(this.h,new _.O("guideResponseAction"),this,function(){a.props.selected&&BM(a)}))};_.zM.prototype.W=function(){this.props.selected?(AM(this),BM(this)):CM(this)};
_.zM.prototype.Ca=function(){_.Q.prototype.Ca.call(this);CM(this)};var CM=function(a){uM("","");a.j("showLogoAction",void 0)},BM=function(a){a.j("hideLogoAction",{Kk:!0})};_.zM.prototype.j=function(a,b){this.h&&_.Eo(this.h,new _.O(a),b)};var AM=function(a){a=a.props.data.backgroundImage;if(a=(null===a||void 0===a?void 0:a.thumbnails)&&_.Oz(80,a.thumbnails)){var b=xM();uM(b+", url('"+a+"')","auto 28.875rem")}else uM("","")};_.zM.TAG_NAME="ytlr-promo-shelf-renderer";_.J(yM,_.zM);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var DM=function(a){return Object.assign(Object.assign({},a),{command:{signalAction:{signal:"SUBMIT_FORM"}}})},EM=function(a){var b=this,c=a.data;a=a.focused;var d=c.question;c=DM(c.dismissalButton.buttonRenderer);var e=this.h,f={};return _.R("host",{className:(f["ytlr-sentiment-survey-shelf-renderer--enable-left-nav"]=_.D("enableLeftNav",!1),f),"aria-label":_.Sx(d)},_.R(_.X,{className:"ytlr-sentiment-survey-shelf-renderer__title",data:d}),_.R(_.YL,{layout:"horizontal",className:"ytlr-sentiment-survey-shelf-renderer__buttons",
focused:a,selectedIndex:this.bb,M:this.M},_.TC(e,function(g,h){g=DM(g.buttonRenderer);return _.R(_.nM,{className:"ytlr-sentiment-survey-shelf-renderer__answer",data:g,size:"large",Cc:"label-below-icon",color:"canvas-black",rd:!((0===h||h===b.j-1)&&0===b.state.selection)})}),_.R(_.nM,{className:"ytlr-sentiment-survey-shelf-renderer__dismissal",data:c,size:"large",Cc:"label-below-icon",color:"canvas-black",rd:0===this.state.selection})))};_.m().w("sy4c");
var FM=_.Xx("QQetM");
_.GM=function(a){_.Q.call(this,a);var b=this;this.template=EM;this.M=function(c){c>=b.j?_.S(b,{bb:c,selection:1}):_.S(b,{bb:c,selection:0})};this.state={selection:0,bb:Math.floor(this.j/2)}};_.G(_.GM,_.Q);_.GM.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.GM.prototype.O=function(){_.T(this,_.N(_.L.R(),new _.O("SUBMIT_FORM"),this,this.A))};_.GM.prototype.W=function(a){if(!a.focused&&this.props.focused)this.onFocus()};
_.GM.prototype.onFocus=function(){this.onVisibleCommand&&(_.U(this,"innertube-command",this.onVisibleCommand),delete this.props.data.impressionEndpoints)};
_.GM.prototype.A=function(){var a={removeItemAction:{childId:this.props.data.trackingParams,parentId:this.props.parentTrackingParams}},b=_.yA({title:_.V("SENTIMENT_SURVEY_MSG")}),c=0===this.state.selection;a={commandExecutorCommand:{commands:_.F(this.props.parentTrackingParams?[a]:[]).concat(_.F(c?[b]:[]),[this.B])}};this.el.style.visibility="hidden";_.U(this,"innertube-command",a)};
_.E.Object.defineProperties(_.GM.prototype,{onVisibleCommand:{configurable:!0,enumerable:!0,get:function(){return this.props.data.impressionEndpoints&&this.props.data.impressionEndpoints[0]}},bb:{configurable:!0,enumerable:!0,get:function(){return this.state.bb}},h:{configurable:!0,enumerable:!0,get:function(){return this.props.data.list&&this.props.data.list.horizontalListRenderer&&this.props.data.list.horizontalListRenderer.items||[]}},j:{configurable:!0,enumerable:!0,get:function(){return this.h.length}},
B:{configurable:!0,enumerable:!0,get:function(){if(0===this.state.selection){var a=this.state.bb;return this.h[a].buttonRenderer&&this.h[a].buttonRenderer.serviceEndpoint||void 0}return this.props.data.dismissalButton&&this.props.data.dismissalButton.buttonRenderer&&this.props.data.dismissalButton.buttonRenderer.serviceEndpoint||void 0}}});_.GM.TAG_NAME="ytlr-sentiment-survey-shelf-renderer";_.J(FM,_.GM);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var HM=function(a){var b=this,c=a.data,d=a.hideTitle,e=a.focused,f,g=_.R(_.xJ,{renderer:this.j(),config:this.A()});a=!(null===(f=this.header)||void 0===f||!f.chipCloudRenderer);var h={},l={};return _.R("host",{"aria-label":_.Sx(c.title),className:(h["ytlr-shelf-renderer--enable-left-nav"]=_.D("enableLeftNav",!1),h),role:"row"},_.R("div",{className:["ytlr-shelf-renderer__title",(l["ytlr-shelf-renderer__title--hidden"]=d,l)],role:"rowheader"},_.Y(c.icon,function(n){return _.R(_.ry,{className:"ytlr-shelf-renderer__icon",
icon:n})}),_.Y(!_.D("enableZds",!1)&&this.h,function(n){return _.R(_.X,{className:"ytlr-shelf-renderer__featured-label",data:n})}),_.R(_.X,{data:c.title}),_.Y(_.D("enableZds",!1)&&this.C,function(n){return _.R(_.uJ,{className:"ytlr-shelf-renderer__featured-badge",data:n})}),_.Y(this.playlistIndexText,function(n){return _.R("span",null," (",_.R(_.X,{className:"ytlr-shelf-renderer__playlist-index",data:n}),") ")})),_.Y(a,function(){return _.R(_.YL,{layout:"vertical",focused:e,selectedIndex:c.tm||0,
M:b.G,sd:b.sd},_.R(_.xJ,{renderer:b.header,config:b.F()}),g)},function(){return g}))};_.m().w("sy4d");
_.IM=function(a){_.Q.call(this,a);var b=this;this.template=HM;this.D=_.wx(function(c){if(c)return{label:c,style:"BADGE_STYLE_TYPE_FEATURED"}});this.bh=function(){b.B&&_.qx(b)};this.G=function(c){b.props.data.tm=c;_.qx(b)};this.H=function(){_.qx(b)};this.j=function(){var c,d,e,f,g,h,l;return(null===(l=null===(h=null===(e=null===(d=null===(c=b.header)||void 0===c?void 0:c.chipCloudRenderer)||void 0===d?void 0:d.chips)||void 0===e?void 0:e[(null===(g=null===(f=b.header)||void 0===f?void 0:f.chipCloudRenderer)||
void 0===g?void 0:g.selectedIndex)||0])||void 0===h?void 0:h.chipCloudChipRenderer)||void 0===l?void 0:l.content)||b.props.data.content};this.A=function(){return{horizontalListRenderer:{I:_.FL,props:{alignment:b.props.alignment,focused:b.props.focused&&!b.header,sf:b.props.sf,width:b.props.width,visibility:b.props.visibility,ag:b.props.ag,bh:b.bh,ea:b.props.ea,Zl:b.props.parentTrackingParams,parentTrackingParams:b.props.data.trackingParams,disableSounds:!!b.props.disableSounds,Yf:!0}}}};this.F=function(){return{chipCloudRenderer:{I:_.QL,
props:{focused:b.props.focused&&0===b.props.data.tm,zi:!0,width:b.props.width,ea:b.props.ea,Gl:b.H}}}};this.sd=function(c){var d,e,f;return 1!==c||!(null===(f=null===(e=null===(d=b.j())||void 0===d?void 0:d.horizontalListRenderer)||void 0===e?void 0:e.items)||void 0===f||!f.length)}};_.G(_.IM,_.Q);_.IM.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;
_.IM.prototype.W=function(){var a=this.props.data.impressionEndpoints;if(a&&2===this.props.visibility){for(var b=0;b<a.length;b++)_.U(this,"innertube-command",a[b]);delete this.props.data.impressionEndpoints}};
_.E.Object.defineProperties(_.IM.prototype,{B:{configurable:!0,enumerable:!0,get:function(){return this.props.data.icon&&this.props.data.icon.iconType&&"PLAYLISTS"===this.props.data.icon.iconType||!1}},selectedIndex:{configurable:!0,enumerable:!0,get:function(){return this.props.data&&this.props.data.content&&this.props.data.content.horizontalListRenderer&&this.props.data.content.horizontalListRenderer.selectedIndex||0}},header:{configurable:!0,enumerable:!0,get:function(){var a;return null===(a=
this.props.data)||void 0===a?void 0:a.headerRenderer}},playlistIndexText:{configurable:!0,enumerable:!0,get:function(){if(this.B){var a=this.props.data.content&&this.props.data.content.horizontalListRenderer&&this.props.data.content.horizontalListRenderer.items||[];if(a=a&&a[this.selectedIndex]&&a[this.selectedIndex].gridVideoRenderer&&a[this.selectedIndex].gridVideoRenderer.playlistIndexText)return a}}},C:{configurable:!0,enumerable:!0,get:function(){return this.D(_.Sx(this.h))}},h:{configurable:!0,
enumerable:!0,get:function(){var a,b,c;return null===(c=null===(b=null===(a=this.props.data.badges)||void 0===a?void 0:a[0])||void 0===b?void 0:b.shelfFeaturedTextBadgeRenderer)||void 0===c?void 0:c.label}}});_.IM.TAG_NAME="ytlr-shelf-renderer";

_.m().l();

}catch(e){_._DumpException(e)}
try{
var JM=function(a){return _.R("host",null,_.Y(a.data.optionsText,function(b){return _.R(_.X,{className:"ytlr-masthead-menu-hint__label",data:b})}),_.R("div",{className:"ytlr-masthead-menu-hint__icon"}))};_.m().w("sy4h");
var KM=_.p("YtlrMastheadMenuHint","RcwRHd");
_.LM=function(){_.Q.apply(this,arguments);this.template=JM};_.G(_.LM,_.Q);_.LM.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.LM.TAG_NAME="ytlr-masthead-menu-hint";_.J(KM,_.LM);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var MM=function(a){a=a.data;var b=a.adBadge&&a.adBadge.metadataBadgeRenderer;return _.R("host",null,_.Y(a.title,function(c){return _.R(_.X,{className:"ytlr-masthead-metadata__primary-text",data:c})}),_.R("div",{className:"ytlr-masthead-metadata__sublabel"},_.Y(b,function(c){return _.R(_.uJ,{data:c,Ep:!0})}),_.Y(a.subtitle,function(c){return _.R(_.X,{className:"ytlr-masthead-metadata__secondary-text",data:c})})))};_.m().w("sy4i");
var NM=_.p("YtlrMastheadMetadata","GjWyn");
_.OM=function(){_.Q.apply(this,arguments);this.template=MM};_.G(_.OM,_.Q);_.OM.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.OM.TAG_NAME="ytlr-masthead-metadata";_.J(NM,_.OM);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.PM=function(a,b){var c,d,e;if(!a)return null;var f=null===a||void 0===a?void 0:a.adInfoDialogChoiceEndpoint;a=null===(c=null===f||void 0===f?void 0:f.optInAction)||void 0===c?void 0:c.openPopupAction;var g=null===(d=null===f||void 0===f?void 0:f.optOutAction)||void 0===d?void 0:d.openPopupAction;f=null===(e=null===f||void 0===f?void 0:f.optUnknownAction)||void 0===e?void 0:e.openPopupAction;if(!a||!g||!f)return _.nd(Error("Ab")),null;var h={};return{openPopupAction:(h.AD_PERSONALIZATION_SETTING_OPT_IN=
a,h.AD_PERSONALIZATION_SETTING_OPT_OUT=g,h.AD_PERSONALIZATION_SETTING_UNKNOWN=f,h)[b]}};_.m().w("sy2y");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy4j");
var QM=_.p("AdPersonalizationService","zhDqzd");
var SM;SM=function(){var a=this;this.ra=_.r.get(_.kA);this.j=_.L.R();this.g=new _.Ct("AD_PERSONALIZATION_SETTING_UNKNOWN");_.N(this.j,new _.O("adPersonalizationSettingChangeEndpoint"),this,function(b){_.RM(a,b)})};
_.RM=function(a,b){a:switch(null===b||void 0===b?void 0:b.command.adPersonalizationSettingChangeEndpoint.newStatus){case "AD_PERSONALIZATION_SETTING_OPT_IN":b=Object.freeze({action_opt_in:1});break a;case "AD_PERSONALIZATION_SETTING_OPT_OUT":b=Object.freeze({action_opt_out:1});break a;default:b=Object.freeze({})}_.ih(_.I(_.jq(a.ra.fetch({path:"/ad_personalization",payload:b}))).then(function(c){c.isOk||_.C(Error("Cb"));c.response.isOptOut?a.g.set("AD_PERSONALIZATION_SETTING_OPT_OUT"):a.g.set("AD_PERSONALIZATION_SETTING_OPT_IN");
return _.I(null)}),function(){_.C(Error("Bb"))})};_.E.Object.defineProperties(SM.prototype,{h:{configurable:!0,enumerable:!0,get:function(){return this.g.get()}}});_.TM=_.K(QM,function(){return new SM});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy4k");
var UM=_.p("AdService","UG9EWb");
var VM,WM,XM,cN,YM,bN,ZM,$M,aN;VM={channelId:"",channelThumbnailUrl:"",channelTitle:"",channelVideoCount:0,videoTitle:"",showAdInfoIcon:!1,whyThisAdInfo:null,muteAdInfo:null};WM={renderer:null,startTimeSecs:0};XM={current:0,duration:0};
cN=function(){var a=this;this.G=new _.Ct(VM);this.C=new _.Ct({});this.F=new _.Ct({});this.N=new _.Ct(WM);this.j=new _.Ct(!1);this.A=new _.Ct(!1);this.B=new _.Ct([]);this.D=new _.Ct(!1);this.P=new _.Ct(XM);this.H=_.r.get(_.oJ);this.J=_.L.R();this.g=null;this.h={};_.EG().then(function(b){b.addEventListener("onAdMetadataAvailable",function(c){a.G.set(c)});b.addEventListener("onAdInfoChange",function(c){var d=(a.g=c)&&c.instreamAdPlayerOverlayRenderer;d?(YM(a).then(function(){a.g===c&&a.C.set(d)}),a.A.set(!1)):
a.C.set({});var e=c&&c.instreamSurveyAdRenderer;e?(a.g===c&&(a.D.set(!0),a.F.set(e)),a.A.set(!1)):(a.j.set(!1),a.D.set(!1),a.F.set({}))});b.addEventListener("onAdMessageChange",function(c){a.N.set(c)});b.addEventListener("onCueRangeMarkersUpdated",function(c){ZM(a,c)});b.addEventListener("onCueRangesAdded",function(c){$M(a,c)});b.addEventListener("onCueRangesRemoved",function(c){aN(a,c)});b.addEventListener("onAdPlaybackProgress",function(c){a.P.set(c)})});_.jJ(this.H,function(b){b||(a.g=null,a.j.set(!1),
a.C.set({}))});_.N(this.J,new _.O("muteAdEndpoint"),this,function(b){a.executeCommand(null===b||void 0===b?void 0:b.command)});_.N(this.J,new _.O("adPingingEndpoint"),this,function(b){a.executeCommand(null===b||void 0===b?void 0:b.command)});_.N(this.J,new _.O("pingingEndpoint"),this,function(b){bN(null===b||void 0===b?void 0:b.command)})};YM=function(a){return a.H.na?Promise.resolve():new Promise(function(b){var c=_.jJ(a.H,function(d){d&&(c(),b())})})};
_.dN=function(a,b){return a.C.g("value-changed",b)};_.eN=function(a,b){return a.j.g("value-changed",b)};cN.prototype.skipShown=function(){this.g?(this.g.skipShown(),this.j.get(),this.j.set(!0)):_.C(Error("Db"))};cN.prototype.skip=function(){!this.A.get()&&this.g&&(this.g.skip(),this.A.set(!0))};cN.prototype.executeCommand=function(a){this.g?this.g.executeCommand(a):this.H.na?_.C(Error("Db")):_.fN(a)};
_.fN=function(a){var b,c;a=null!==(c=null===(b=null===a||void 0===a?void 0:a.commandExecutorCommand)||void 0===b?void 0:b.commands)&&void 0!==c?c:[];for(var d=0,e=a.length;d<e;d++){var f=void 0,g=void 0,h=[],l=null!==(g=a[d].loggingUrls)&&void 0!==g?g:[];g=0;for(var n=l.length;g<n;g++){var q=null===(f=l[g])||void 0===f?void 0:f.baseUrl;q&&h.push({loggingUrls:[{baseUrl:q}],pingingEndpoint:{}})}f=h;h=0;for(l=f.length;h<l;h++)bN(f[h])}};
bN=function(a){var b;(a=(null===a||void 0===a?void 0:a.loggingUrls)&&(null===(b=null===a||void 0===a?void 0:a.loggingUrls[0])||void 0===b?void 0:b.baseUrl))&&_.jq(_.Yp(a,{method:"GET"})).then(function(c){(null===c||void 0===c?0:c.ok)||_.C(Error("Eb"))})};ZM=function(a,b){b.forEach(function(c){var d=c.getId();a.h[d]&&(a.h[d]=c)});a.B.set(Object.values(a.h))};$M=function(a,b){b.forEach(function(c){var d=c.getId();c.visible&&!a.h[d]?a.h[d]=c:c.visible||a.h[d]!==c||delete a.h[d]});a.B.set(Object.values(a.h))};
aN=function(a,b){b.forEach(function(c){var d=c.getId();a.h[d]===c&&delete a.h[d]});a.B.set(Object.values(a.h))};
_.E.Object.defineProperties(cN.prototype,{V:{configurable:!0,enumerable:!0,get:function(){return this.g}},adMetadata:{configurable:!0,enumerable:!0,get:function(){return this.G.get()}},S:{configurable:!0,enumerable:!0,get:function(){return this.j.get()}},instreamAdPlayerOverlayRenderer:{configurable:!0,enumerable:!0,get:function(){return this.C.get()}},instreamSurveyAdRenderer:{configurable:!0,enumerable:!0,get:function(){return this.F.get()}},cueRanges:{configurable:!0,enumerable:!0,get:function(){return this.B.get()}},
Tg:{configurable:!0,enumerable:!0,get:function(){return this.D.get()}}});_.gN=_.K(UM,function(){return new cN});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var hN=function(a,b){var c,d,e,f,g,h={};h.video_masthead_ad_quartile_urls={quartile_0_url:null===(c=a.startUrls)||void 0===c?void 0:c[0],quartile_25_url:null===(d=a.firstQuartileUrls)||void 0===d?void 0:d[0],quartile_50_url:null===(e=a.secondQuartileUrls)||void 0===e?void 0:e[0],quartile_75_url:null===(f=a.thirdQuartileUrls)||void 0===f?void 0:f[0],quartile_100_url:null===(g=a.completeUrls)||void 0===g?void 0:g[0]};h.video_id=b;return h},kN=function(a){var b=a.data,c=a.focused,d=a.Zn;a=(a=a.size)?
a:"medium";var e=b.creativeInfo&&b.creativeInfo.tvMastheadCreativeInfoRenderer,f=b.creative&&(b.creative.tvMastheadVideoThumbnailRenderer||b.creative.tvMastheadAutoplayVideoRenderer),g=f&&f.videoThumbnail,h=f&&f.aspectRatio||"TV_MASTHEAD_CREATIVE_ASPECT_RATIO_WIDESCREEN",l=function(){var t=f.playbackCommand,w;t&&(w={blockAdoption:!0,muted:!0,delayMs:d?160:100,playbackEndpoint:t,overrideSettingsSwitch:!0});return w}(),n=function(){var t=b.creativeInfo&&b.creativeInfo.tvMastheadCreativeInfoRenderer||
{},w=_.Sx(t.title),y=(t.adBadge&&t.adBadge.metadataBadgeRenderer||{}).label||"",B=_.Sx(t.subtitle);t=_.Sx(t.optionsText);return w+" "+y+" "+B+" "+t}(),q=_.R(_.Pz,{className:"ytlr-tv-masthead-renderer__image",data:g||{},width:65});return _.R("host",Object.assign({},l?{}:{"aria-label":n,role:"gridcell"},{hybridNavFocusable:!0,className:[iN[h],jN[a],d?"ytlr-tv-masthead-renderer--left-nav":"ytlr-tv-masthead-renderer--top-nav"]}),_.Y(d&&b.headerText,function(t){return _.R(_.X,{className:"ytlr-tv-masthead-renderer__header",
data:t})}),_.Y(l,function(t){return _.R(_.uK,{data:t,focused:c,ariaLabel:n},[q])}),_.Y(!l,function(){return _.R(_.Tw,null,q)}),_.R("div",{className:"ytlr-tv-masthead-renderer__scrim"}),_.Y(e,function(t){return _.R("div",{className:"ytlr-tv-masthead-renderer__info"},_.R(_.OM,{className:"ytlr-tv-masthead-renderer__metadata",data:t}),_.R(_.LM,{className:"ytlr-tv-masthead-renderer__hint",data:t}))}))},lN=function(a){var b;a=null===(b=a.commandExecutorCommand)||void 0===b?void 0:b.commands;if(null!==a&&
void 0!==a&&a.length)return a[0]},mN=function(a){if(a=lN(a))return a.adInfoDialogChoiceEndpoint},nN=function(a){return(a=lN(a))?!!a.adInfoDialogEndpoint:!1},oN=function(a,b){var c,d;if(b){a=null===(c=null===a||void 0===a?void 0:a.commandExecutorCommand)||void 0===c?void 0:c.commands;a=null===a||void 0===a?void 0:a.find(function(g){return g.watchEndpoint});var e=null===(d=null===a||void 0===a?void 0:a.watchEndpoint)||void 0===d?void 0:d.videoId;if(e){var f=hN(b,e);_.EG().then(function(g){var h=function(l){g.getVideoData().video_id===
e&&1===l&&(g.updateVideoData(f),g.removeEventListener("onStateChange",h))};g.addEventListener("onStateChange",h)})}}};_.m().w("sy4g");
var pN=_.p("YtlrTvMastheadRenderer","zAEowc");
var qN,sN;qN={};_.rN=(qN.small=12.75,qN.medium=17.75,qN.large=23.75,qN);sN={};_.tN=(sN.small=12.75,sN.medium=25.5,sN.large=32.75,sN);
var uN={},jN=(uN.small="ytlr-tv-masthead-renderer--small",uN.medium="ytlr-tv-masthead-renderer--medium",uN.large="ytlr-tv-masthead-renderer--large",uN),vN={},iN=(vN.TV_MASTHEAD_CREATIVE_ASPECT_RATIO_WIDESCREEN="ytlr-tv-masthead-renderer--widescreen",vN.TV_MASTHEAD_CREATIVE_ASPECT_RATIO_16_9="ytlr-tv-masthead-renderer--standard",vN.TV_MASTHEAD_CREATIVE_ASPECT_RATIO_UNSPECIFIED="ytlr-tv-masthead-renderer--widescreen",vN);
_.wN=function(){_.Q.apply(this,arguments);this.template=kN;_.r.get(_.gN);this.h=_.r.get(_.TM)};_.G(_.wN,_.Q);_.wN.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.wN.prototype.O=function(){var a=this;this.props.data.Dk||setTimeout(function(){a.props.focused&&xN(a)},100)};_.wN.prototype.W=function(a){!a.focused&&this.props.focused&&xN(this)};
var xN=function(a){if(!a.props.data.Dk){var b=a.props.data.creative&&(a.props.data.creative.tvMastheadVideoThumbnailRenderer||a.props.data.creative.tvMastheadAutoplayVideoRenderer);b&&b.impressionCommand&&(a.props.data.Dk=!0,null===_.ov()?_.fN(b.impressionCommand):_.U(a,"masthead-impression",{impressionCommand:b.impressionCommand}))}};
_.wN.prototype.onSelect=function(a){this.props.data.clickCommand&&(null===_.ov()?(_.fN(this.props.data.clickCommand),oN(this.props.data.clickCommand,this.props.data.watchPlaybackTracking)):_.U(this,"masthead-click-tracking",{clickCommand:this.props.data.clickCommand,watchPlaybackTracking:this.props.data.watchPlaybackTracking}),_.U(this,"innertube-command",this.props.data.clickCommand),_.P(a))};
_.wN.prototype.onRight=function(a){var b=this,c,d,e=null===(d=null===(c=this.props.data.creativeInfo)||void 0===c?void 0:c.tvMastheadCreativeInfoRenderer)||void 0===d?void 0:d.optionsText,f=this.props.data.optionsCommand;f&&e&&(nN(f)?(_.U(this,"masthead-wta-open",{VE:function(){_.U(b,"innertube-command",{commandExecutorCommand:{commands:[{removeItemAction:{childId:b.props.data.trackingParams,parentId:b.props.parentTrackingParams}}]}})},optionsCommand:f,optionsText:e}),_.P(a)):mN(f)&&(e=_.PM(lN(f),
this.h.h))&&(_.U(this,"innertube-command",e),_.P(a)))};_.wN.TAG_NAME="ytlr-tv-masthead-renderer";_.J(pN,_.wN);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var yN=function(a){return Object.assign(Object.assign({},a),{command:{signalAction:{signal:"SUBMIT_FORM"}}})},zN=function(a){var b=this,c=a.data;a=c.title;var d=c.question,e=c.videoMetadata,f=e.videoTitle,g=e.channelName;e=e.videoThumbnail;var h=this.h,l=_.Sx(d)+". "+_.Sx(f)+". "+_.Sx(g);c=yN(c.dismissalButton.buttonRenderer);var n={};return _.R("host",{className:(n["ytlr-video-survey-shelf-renderer--enable-left-nav"]=_.D("enableLeftNav",!1),n),"aria-label":_.Sx(a),role:"row"},_.R(_.X,{className:"ytlr-video-survey-shelf-renderer__title",
data:a||""}),_.R("div",{className:"ytlr-video-survey-shelf-renderer__survey-container","aria-label":l,role:"gridcell"},_.R(_.X,{className:"ytlr-video-survey-shelf-renderer__question",data:d||""}),_.R("div",{className:"ytlr-video-survey-shelf-renderer__metadata"},_.R(_.Pz,{className:"ytlr-video-survey-shelf-renderer__video-thumbnail",data:e||{},width:10.5}),_.R(_.X,{className:"ytlr-video-survey-shelf-renderer__video-title",data:f||""}),_.R(_.X,{className:"ytlr-video-survey-shelf-renderer__video-channel-name",
data:g||""})),_.R("div",{className:"ytlr-video-survey-shelf-renderer__answer"},_.R(_.YL,{layout:"horizontal",className:"ytlr-video-survey-shelf-renderer__answer-button-list",focused:this.isFocused(0),selectedIndex:this.ub,M:this.M},_.TC(h,function(q,t){q=yN(q.buttonRenderer);var w;b.isFocused(0)&&(t<b.ub||b.state.pj&&t===b.ub)?w={iconType:"STAR"}:w={iconType:"STAR_BORDER"};w=Object.assign(Object.assign({},q),{icon:w});return _.R(_.nM,{className:"ytlr-video-survey-shelf-renderer__answer-button",data:w,
shape:"circular",size:"regular",color:"canvas-black",iconColor:t<=b.ub&&b.state.pj&&b.isFocused(0)?"blue":"default",rd:!0,Zh:b.state.re})})),_.R(_.X,{className:"ytlr-video-survey-shelf-renderer__label",data:this.label})),_.R(_.nM,{className:"ytlr-video-survey-shelf-renderer__dismissal-button",data:c,shape:"circular",size:"small",color:"canvas-black",focused:this.isFocused(1),rd:!0})))},AN=function(){return _.D("enableAnimations",!0)?200:150};_.m().w("sy4l");
var BN=_.Xx("ptoyHc");
_.CN=function(a){_.Q.call(this,a);var b=this;this.template=zN;this.M=function(c){b.ub=c};this.state={ub:Math.floor(this.h.length/2),pj:!1,selection:0,re:AN()}};_.G(_.CN,_.Q);_.CN.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;
_.CN.prototype.O=function(){var a=this;this.g("keydown",function(b){var c=a.isFocused(0)&&0===a.ub,d=a.isFocused(0)&&a.ub===a.h.length-1;c&&37===b.keyCode||27===b.keyCode?_.S(a,{re:0}):d&&39===b.keyCode||a.isFocused(0)&&38===b.keyCode?(_.S(a,{selection:1}),_.P(b)):!a.isFocused(1)||37!==b.keyCode&&40!==b.keyCode?a.isFocused(1)&&38===b.keyCode?_.S(a,{selection:0,re:AN()}):_.S(a,{re:AN()}):(_.S(a,{selection:0}),_.P(b))});_.T(this,_.N(_.L.R(),new _.O("SUBMIT_FORM"),this,this.j))};
_.CN.prototype.W=function(a){if(!a.focused&&this.props.focused)this.onFocus()};var DN=function(a){if(0!==a.state.re){var b=setTimeout(function(){_.S(a,{re:0})},_.D("enableAnimations",!0)?200:150);_.T(a,function(){clearTimeout(b)})}};_.CN.prototype.onFocus=function(){this.onVisibleCommand&&(_.U(this,"innertube-command",this.onVisibleCommand),delete this.props.data.impressionEndpoints);DN(this)};
_.CN.prototype.j=function(){var a=this,b={removeItemAction:{childId:this.props.data.trackingParams,parentId:this.props.parentTrackingParams}},c=_.yA({title:_.V("VIDEO_SURVEY_MSG"),subtitle:_.V("VIDEO_SURVEY_SUBMSG"),style:"OVERLAY_MESSAGE_STYLE_TOAST"}),d=0===this.state.selection,e={commandExecutorCommand:{commands:[this.A].concat(_.F(this.props.parentTrackingParams?[b]:[]),_.F(d?[c]:[]))}};_.S(this,{pj:!0});var f=setTimeout(function(){a.el.style.visibility="hidden";_.U(a,"innertube-command",e)},
d?400:0);_.T(this,function(){clearTimeout(f)})};_.CN.prototype.isFocused=function(a){return this.props.focused&&this.state.selection===a};
_.E.Object.defineProperties(_.CN.prototype,{ub:{configurable:!0,enumerable:!0,get:function(){return this.state.ub},set:function(a){_.S(this,{ub:a})}},A:{configurable:!0,enumerable:!0,get:function(){if(0===this.state.selection){var a=this.state.ub;return this.h[a].buttonRenderer&&this.h[a].buttonRenderer.serviceEndpoint}return this.props.data.dismissalButton&&this.props.data.dismissalButton.buttonRenderer&&this.props.data.dismissalButton.buttonRenderer.serviceEndpoint}},h:{configurable:!0,enumerable:!0,
get:function(){return this.props.data.list&&this.props.data.list.horizontalListRenderer&&this.props.data.list.horizontalListRenderer.items||[]}},label:{configurable:!0,enumerable:!0,get:function(){var a=this.h.length;if(!(!this.props.focused||0>this.ub||this.ub>=a||this.isFocused(1)))return this.h[this.ub].buttonRenderer&&this.h[this.ub].buttonRenderer.text}},onVisibleCommand:{configurable:!0,enumerable:!0,get:function(){return this.props.data.impressionEndpoints&&this.props.data.impressionEndpoints[0]}}});
_.CN.TAG_NAME="ytlr-video-survey-shelf-renderer";_.J(BN,_.CN);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var HN=function(a){var b=a.data,c=b.defaultIcon,d=b.defaultText,e=b.isToggled,f=b.toggledIcon;b=b.toggledText;return _.R("host",{"aria-hidden":!a.focused,"aria-pressed":e,hybridNavFocusable:!0,role:"button",className:[EN[this.props.shape||"rectangular"],FN[this.props.iconColor||"default"],GN[this.props.iconPosition||"default"]]},_.R(_.X,{className:"ytlr-toggle-button-renderer__label",data:e?b:d}),_.Y(e?f:c,function(g){return _.R(_.ry,{className:"ytlr-toggle-button-renderer__icon",icon:g})}))};_.m().w("sy3g");
var IN={},EN=(IN.circular="",IN.rectangular="",IN.pane="ytlr-toggle-button-renderer--pane-shape",IN["pivot-footer"]="",IN.transport="ytlr-toggle-button-renderer--transport-shape",IN["settings-list-control"]="",IN["survey-ad"]="ytlr-toggle-button-renderer--survey-ad-shape",IN["you-there"]="",IN),JN={},FN=(JN.blue="ytlr-toggle-button-renderer--blue-icon-color",JN["detail-light"]="ytlr-toggle-button-renderer--detail-light-icon-color",JN["default"]="",JN),KN={},GN=(KN.right="ytlr-toggle-button-renderer--right-icon-position",
KN["default"]="",KN);
_.LN=function(){_.Q.apply(this,arguments);this.template=HN;this.h=this.A.bind(this);this.j=this.B.bind(this)};_.G(_.LN,_.Q);_.LN.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.LN.prototype.O=function(){var a=this;document.addEventListener("subscription-change",this.h);document.addEventListener("video-liked",this.j);_.T(this,function(){document.removeEventListener("subscription-change",a.h)});_.T(this,function(){document.removeEventListener("video-liked",a.j)})};
_.LN.prototype.onSelect=function(a){var b=this.props.data.isToggled?this.props.data.toggledServiceEndpoint:this.props.data.defaultServiceEndpoint;b&&_.U(this,"innertube-command",b);this.props.preventAutoToggle||this.props.data.preventAutoToggle||(this.props.data.isToggled=!this.props.data.isToggled,_.qx(this));_.P(a)};
_.LN.prototype.A=function(a){var b=_.A(a.detail);a=b.next().value;var c=(b=b.next().value)?this.props.data.toggledServiceEndpoint:this.props.data.defaultServiceEndpoint,d=c&&c.authDeterminedCommand&&c.authDeterminedCommand.authenticatedCommand;(c||d)&&(c=d?d.subscribeEndpoint||d.unsubscribeEndpoint:c.subscribeEndpoint||c.unsubscribeEndpoint)&&(c=c.channelIds)&&c.length&&a===c[0]&&this.props.data.isToggled!==b&&(this.props.data.isToggled=b,_.qx(this))};
_.LN.prototype.B=function(a){var b=_.A(a.detail);a=b.next().value;b=b.next().value;var c=this.props.data.defaultServiceEndpoint,d=c&&c.authDeterminedCommand&&c.authDeterminedCommand.authenticatedCommand;(c=d?d.likeEndpoint:c&&c.likeEndpoint)&&c.target&&c.target.videoId===a&&(a=b===c.status,this.props.data.isToggled!==a&&(this.props.data.isToggled=a,_.qx(this)))};_.LN.TAG_NAME="ytlr-toggle-button-renderer";

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.NN=function(a,b){var c=function(d){_.Q.call(this,d);this.template=function(e,f){return f.Ei?_.R("host",{},_.R(f.Ei,e)):b?_.R("host",{},_.R(b,e)):_.R("host",{})};this.state={Ei:null}};_.G(c,_.Q);c.TAG_NAME=_.Q.TAG_NAME;c.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;c.prototype.O=function(){var d=this;_.rj(a).then(function(e){_.S(d,{Ei:e})})};c.TAG_NAME="zn-lazy-"+ ++MN;return c};_.m().w("sy2x");
var MN=0;

_.m().l();

}catch(e){_._DumpException(e)}
try{
var ON=function(a,b){var c=this,d=a.data,e=d.accountName,f=d.accountByline,g=d.accountPhoto,h=d.buttons;d=d.unlimitedStatus;a=a.focused;b=b.selectedIndex;void 0===b&&(b=this.h[0]||0);var l={};return _.R("host",{hybridNavFocusable:!0,className:(l["ytlr-tv-account-content-renderer--single-account"]=!this.supportsAccountSwitching,l)},_.Y(g,function(n){return _.R(_.hA,{thumbnail:n,position:"center",size:"huge",style:"circle"})}),_.R("div",{className:"ytlr-tv-account-content-renderer__text-container",
"aria-live":"polite"},_.R(_.X,{className:"ytlr-tv-account-content-renderer__account-name",data:e}),_.R(_.X,{className:["ytlr-tv-account-content-renderer__subtitle-text","ytlr-tv-account-content-renderer__subtitle-text--email-text"],data:f}),_.TC(d,function(n){return _.R(_.X,{className:["ytlr-tv-account-content-renderer__subtitle-text","ytlr-tv-account-content-renderer__subtitle-text--premium-text"],data:n})})),_.R(_.YL,{layout:"vertical",focused:a,selectedIndex:b,M:this.M,sd:function(n){return c.h.includes(n)}},
_.TC(h,function(n){return _.R(_.nM,{className:"ytlr-tv-account-content-renderer__account-button",data:n.buttonRenderer,shape:"pane",size:"regular"})})))},PN=function(a){var b=a.data;b=(void 0===b.buttons?[]:b.buttons).findIndex(function(c){return!c.buttonRenderer.isDisabled})||0;return{data:a.data,selectedIndex:b}};_.m().w("sy3c");
var QN=_.Xx("Dk0Lpb");
_.RN=function(a){_.Q.call(this,a);var b=this;this.template=ON;this.A=_.r.get(_.Lr);this.j=_.wx(function(c){return c.reduce(function(d,e,f){e.buttonRenderer&&!e.buttonRenderer.isDisabled&&d.push(f);return d},[])});this.M=function(c){_.S(b,{selectedIndex:c})};this.state=PN(a)};_.G(_.RN,_.Q);_.RN.getDerivedStateFromProps=function(a,b){return a.data!==b.data?PN(a):b};
_.E.Object.defineProperties(_.RN.prototype,{supportsAccountSwitching:{configurable:!0,enumerable:!0,get:function(){return!!_.D("supportsAccountSwitching","oauth"===this.A.g)}},h:{configurable:!0,enumerable:!0,get:function(){var a=this.props.data.buttons;return a?this.j(a):[]}}});_.RN.TAG_NAME="ytlr-tv-account-content-renderer";_.J(QN,_.RN);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var SN=function(a){var b=a.data;a=b.title;var c=b.bodyText;b=b.actionButton;var d={};return _.R("host",{className:[(d["ytlr-generic-promo-renderer--enable-left-nav"]=_.D("enableLeftNav",!1),d)]},_.R("div",{className:"ytlr-generic-promo-renderer__text","aria-live":"polite"},_.R(_.X,{className:"ytlr-generic-promo-renderer__title",data:a}),_.R(_.X,{className:"ytlr-generic-promo-renderer__body-text",data:c})),_.R(_.xJ,{config:this.h(),renderer:b}))};_.m().w("sy3f");
var TN=_.Xx("zKSK7");
_.UN=function(){_.Q.apply(this,arguments);var a=this;this.template=SN;this.h=function(){var b={focused:a.props.focused,shape:"pane",size:"regular",preventAutoToggle:!0};return{buttonRenderer:{I:_.nM,props:b},toggleButtonRenderer:{I:_.LN,props:b}}}};_.G(_.UN,_.Q);_.UN.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.UN.TAG_NAME="ytlr-generic-promo-renderer";_.J(TN,_.UN);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy4n");
_.VN=_.Xx("AbjwFd");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy4o");
_.WN=_.Xx("R7gZ9");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy4p");
_.XN=_.Xx("g0vKWc");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy4q");
_.YN=_.Xx("l9HEne");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy4r");
_.ZN=_.Xx("oipy2b");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy4s");
_.$N=_.Xx("kxGG6c");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy4t");
_.aO=_.Xx("RW4XIc");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var bO=function(){return _.D("enableMastheadLarge",!1)?"large":_.D("enableMastheadSmall",!1)?"small":"medium"},cO=function(a){a=a.creative&&(a.creative.tvMastheadVideoThumbnailRenderer||a.creative.tvMastheadAutoplayVideoRenderer);var b=_.D("enableLeftNav",!1)?2.25:0;return"TV_MASTHEAD_CREATIVE_ASPECT_RATIO_16_9"===(a&&a.aspectRatio)?_.rN[bO()]+b:_.tN[bO()]+b},dO=function(a,b){var c=b.Mh,d=b.Pj,e=b.Nh,f=b.itemWidth,g=b.Fa,h=b.Xc,l=b.width,n=b.Xg;return function(q){return _.R(_.Fz,{role:"row",alignment:function(t){return-t.ea},
layout:"horizontal",items:q.item||[],selectedIndex:q.selected?h.column:-1,M:a,Fa:g,size:l,itemSize:f,spacing:c,ea:e,gb:d,Wb:n?"tile-to-tile":void 0})}},eO=function(a){return _.R("host",null,_.R(_.Fz,{role:"rowgroup",alignment:a.alignment||_.pJ,items:this.rows,layout:"vertical",M:this.A,selectedIndex:a.Xc.row,size:a.height,spacing:a.dj,ea:a.Ul,gb:a.Tl,itemSize:a.itemHeight,Wb:a.Xg?"row-to-row":void 0,Fa:dO(this.j,a)}))},gO=function(a){var b=this,c=a.data;return _.R("host",{role:"grid","aria-label":a.ariaLabel},
_.R(fO,{width:a.width,height:a.height,jh:a.jh,items:c.items||[],Xc:this.Xc,itemWidth:a.itemWidth,itemHeight:a.itemHeight,Mh:a.Mh,Nh:a.Nh,Pj:a.Pj,dj:a.dj,Ul:a.Ul,Tl:a.Tl,We:this.We,Fa:function(d){return _.R(_.xJ,{config:b.h(d.selected),renderer:d.item})},focused:a.focused,Xg:a.Xg}),_.R(_.xL,{data:this.nextContinuationData,Zf:this.j,parentTrackingParams:c.trackingParams,Ta:this.Ta}))},iO=function(a,b){return function(c){var d=c.selected,e=_.D("enableLeftNav",!1);return _.R(_.xJ,{renderer:c.item,config:{chipCloudRenderer:{I:_.QL,
props:{focused:a&&d,width:e?80:71.5,ea:e?6.5:3}},promoShelfRenderer:{I:_.zM,props:{focused:a&&d,selected:d,width:80,parentTrackingParams:b}},shelfRenderer:{I:_.IM,props:{alignment:hO,focused:a&&d,sf:_.JL,width:e?80:71.5,ea:e?6.5:3,parentTrackingParams:b,visibility:c.visibility}},sentimentSurveyShelfRenderer:{I:_.GM,props:{focused:a&&d,parentTrackingParams:b}},videoSurveyShelfRenderer:{I:_.CN,props:{focused:a&&d,parentTrackingParams:b}},popupSurveyShelfRenderer:{I:_.qM,props:{focused:a&&d,parentTrackingParams:b}},
tvMastheadRendererOld:{I:_.wN,props:{focused:a&&d,size:bO(),Zn:e,parentTrackingParams:b}}}})}},jO=function(a){var b=a.data,c=a.height,d=a.focused,e=a.ri,f=b.contents||[];a=b.selectedIndex||0;f=0<f.length&&3>f.length-1-a;var g={};return _.R("host",{className:(g["ytlr-section-list-renderer--has-header"]=e,g)},_.R(_.Fz,{alignment:_.pJ,items:b.contents||[],layout:"vertical",M:this.M,selectedIndex:a,size:c,Ub:!!b.Ub,xa:function(h){return h.tvMastheadRendererOld?_.D("enableZdsTypeAndSpacing",!1)?cO(h.tvMastheadRendererOld)+
3:cO(h.tvMastheadRendererOld):h.chipCloudRenderer?_.D("enableZdsTypeAndSpacing",!1)?10.5:7.5:h.popupSurveyShelfRenderer?_.D("enableZdsTypeAndSpacing",!1)?18.5:15.5:e?_.D("enableZdsTypeAndSpacing",!1)?_.D("enableZdsIncreaseTileSize",!1)?28.875:27:26.375:_.D("enableLeftNav",!1)?26.875:25.375},Fa:iO(d,b.trackingParams),Wb:"row-to-row",version:b.version}),_.R(_.xL,{data:this.nextContinuationData,Zf:f,parentTrackingParams:b.trackingParams,Ta:this.Ta}))},lO=function(a){var b=a.data,c=b.badges,d=b.banner,e=
b.buttons,f=b.iconText,g=b.style,h=b.subtitle,l=b.thumbnail;b=b.title;a=a.focused;g=!d&&"TV_SURFACE_CONTENT_HEADER_STYLE_BANNER"===g;var n={thumbnail:l,iconText:f,size:"large",style:"circle"},q={},t={},w={};return _.R("host",{className:(q["ytlr-tv-surface-header-renderer--has-banner"]=d,q["ytlr-tv-surface-header-renderer--enable-left-nav"]=_.D("enableLeftNav",!1),q)},_.Y(d,function(){return _.R(_.Pz,{className:"ytlr-tv-surface-header-renderer__banner",data:d||{},width:68.5})}),_.R("div",{className:["ytlr-tv-surface-header-renderer__bar",
(t["ytlr-tv-surface-header-renderer--banner-style"]=g,t)]},_.Y(f||l,function(){return _.R(_.hA,Object.assign({className:"ytlr-tv-surface-header-renderer__avatar"},n))}),_.R("div",{className:"ytlr-tv-surface-header-renderer__metadata"},_.Y(h,function(){return _.R(_.X,{className:"ytlr-tv-surface-header-renderer__subtitle",data:h})}),_.R("div",null,_.R(_.X,{className:["ytlr-tv-surface-header-renderer__title",(w["ytlr-tv-surface-header-renderer__title--has-subtitle"]=h,w)],data:b}),_.R("span",{className:"ytlr-tv-surface-header-renderer__badges"},
_.TC(c,function(y){return _.R(_.uJ,{data:y.metadataBadgeRenderer})})))),_.R(_.YL,{layout:"horizontal",className:"ytlr-tv-surface-header-renderer__buttons",focused:a,selectedIndex:this.props.data.bb||0,M:this.M,nf:!0},_.TC(e,function(y){return _.R(_.xJ,{config:kO,renderer:y})}))))},oO=function(a){var b=a.data,c=b.content,d=b.continuation,e=b.header;b=b.trackingParams;var f=void 0===a.lj?!0:a.lj,g=void 0===a.Fk?!1:a.Fk,h=a.yo,l=e&&e.tvSurfaceHeaderRenderer;a=!(!l||!l.banner);var n=this.isFocused("header");
e={};return _.R("host",{className:(e["ytlr-tv-surface-content-renderer--has-banner"]=a,e["ytlr-tv-surface-content-renderer--header-focused"]=n,e["ytlr-tv-surface-content-renderer--enable-left-nav"]=_.D("enableLeftNav",!1),e)},_.Y(f&&!this.j,function(){return _.R(mO,{idomKey:"header",className:"ytlr-tv-surface-content-renderer__header",data:l||{},focused:n,focusId:"header",onDownFocus:"content"})}),_.R(_.xJ,{idomKey:"content",config:nO(this,this.isFocused("content"),a,f,g),renderer:c}),_.Y(this.state.Yf,
function(){return _.R(_.gK,{idomKey:"ghost-surface",className:f?"ytlr-tv-surface-content-renderer__ghost-grid":"ytlr-tv-surface-content-renderer__ghost-shelves",hidden:!!c,grid:f||h,em:!f&&!h})}),_.R(_.xL,{idomKey:"continuation",data:d&&d.reloadContinuationData||{},parentTrackingParams:b,Zf:!c,Ta:this.Ta}))};_.m().w("sy2r");
var fO=function(){_.Q.apply(this,arguments);var a=this;this.template=eO;this.j=function(b){a.We({row:a.props.Xc.row,column:b})};this.A=function(b){a.We({row:b,column:Math.min(a.props.Xc.column,a.rows[b].length-1)})};this.h=_.wx(function(b,c){for(var d=[],e=0;e<c.length;e+=b)d.push(c.slice(e,e+b));return d})};_.G(fO,_.Q);fO.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;fO.prototype.We=function(a){this.props.We(a,{row:this.rows.length-1,column:this.rows.length?this.rows[0].length:-1})};
_.E.Object.defineProperties(fO.prototype,{rows:{configurable:!0,enumerable:!0,get:function(){var a=this.props;return this.h(a.jh,a.items)}}});fO.TAG_NAME="yt-grid";
var pO=function(){_.Q.apply(this,arguments);var a=this;this.template=gO;this.Ta=function(b){b.continuationContents&&b.continuationContents.gridContinuation&&(b=b.continuationContents.gridContinuation,b.items&&(a.props.data.items=_.F(a.props.data.items||[]).concat(_.F(b.items))),a.props.data.continuations=b.continuations,_.qx(a))};this.We=function(b,c){a.props.data.Xc=b;a.props.data.Xk=c;_.qx(a)};this.h=function(b){b={focused:a.props.focused&&b,style:"grid"};return{compactMovieRenderer:{I:_.AL,props:b},
gridVideoRenderer:{I:_.CL,props:b},gridPlaylistRenderer:{I:_.BL,props:b},gridShowRenderer:{I:_.nL,props:b},tileRenderer:{I:_.hL,props:b}}}};_.G(pO,_.Q);pO.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;
_.E.Object.defineProperties(pO.prototype,{Xc:{configurable:!0,enumerable:!0,get:function(){return this.props.data.Xc||{row:0,column:0}}},j:{configurable:!0,enumerable:!0,get:function(){var a=this.props.data.items&&this.props.data.items.length;if(void 0===a)return!0;a=this.props.data.Xk?this.props.data.Xk.row:Math.ceil(a/this.props.jh)-1;return 0>a||3>a-this.Xc.row}},nextContinuationData:{configurable:!0,enumerable:!0,get:function(){return this.props.data.continuations&&this.props.data.continuations[0]&&
this.props.data.continuations[0].nextContinuationData||{}}}});pO.TAG_NAME="ytlr-grid-renderer";
var hO=_.IL(1);
var qO=function(){_.Q.apply(this,arguments);var a=this;this.navigation=_.r.get(_.KI);this.template=jO;this.M=function(b){a.props.data.selectedIndex=b;_.qx(a)};this.Ta=function(b){b.continuationContents&&b.continuationContents.sectionListContinuation&&(b=b.continuationContents.sectionListContinuation,b.contents&&(a.props.data.contents=_.F(a.props.data.contents||[]).concat(_.F(b.contents))),a.props.data.continuations=b.continuations,_.qx(a))}};_.G(qO,_.Q);qO.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;
qO.prototype.O=function(){var a=_.L.R();_.T(this,_.N(a,new _.O("BACK_TO_TOP"),this,this.h));_.T(this,_.N(a,new _.O("OPEN_SIGN_IN_PROMPT"),this,this.A));this.props.data.trackingParams&&_.T(this,_.N(a,new _.O("removeItemAction",this.props.data.trackingParams),this,this.j))};qO.prototype.h=function(){this.M(0)};qO.prototype.A=function(){var a=this.navigation.Va();_.U(this,"innertube-command",{signInEndpoint:{nextEndpoint:(null===a||void 0===a?void 0:a.command)||{browseEndpoint:{browseId:"FEtopics"}}}})};
qO.prototype.j=function(a){var b=this,c,d,e,f=null===(c=a.command.removeItemAction)||void 0===c?void 0:c.childId;if(f&&(a=this.props.data.contents)&&a.length){var g=a.findIndex(function(h){var l;return(null===(l=_.v.la(h)[0])||void 0===l?void 0:l.trackingParams)===f});0>g||(a=g===a.length-1,!_.D("enableAnimations",!0)||a?(this.props.data.contents.splice(g,1),this.props.data.version=(null!==(d=this.props.data.version)&&void 0!==d?d:0)+1,this.props.data.selectedIndex=Math.min(this.props.data.contents.length-
1,null!==(e=this.props.data.selectedIndex)&&void 0!==e?e:0),_.qx(this)):(this.M(this.props.data.selectedIndex+1),_.Wx({eventType:"transitionend",element:this.el.firstChild.firstChild,X:function(){return _.Tu(b,function l(){var n,q=this;return _.Su(l,function(t){q.props.data.contents.splice(g,1);q.props.data.version=(null!==(n=q.props.data.version)&&void 0!==n?n:0)+1;q.props.data.Ub=!0;--q.props.data.selectedIndex;_.qx(q);q.props.data.Ub=!1;t.g=0})})},ud:750})))}};
_.E.Object.defineProperties(qO.prototype,{nextContinuationData:{configurable:!0,enumerable:!0,get:function(){return this.props.data.continuations&&this.props.data.continuations[0]&&this.props.data.continuations[0].nextContinuationData||{}}}});qO.TAG_NAME="ytlr-section-list-renderer";
var kO={buttonRenderer:{I:_.nM,props:{}},toggleButtonRenderer:{I:_.LN,props:{}}};
var mO=function(){_.Q.apply(this,arguments);var a=this;this.template=lO;this.M=function(b){a.props.data.bb=b;_.qx(a)}};_.G(mO,_.Q);mO.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;mO.TAG_NAME="ytlr-tv-surface-header-renderer";
var rO,sO,tO,uO,vO,wO,xO;rO=_.NN(_.XN);sO=_.NN(_.YN);tO=_.NN(_.ZN);uO=_.NN(_.$N);vO=_.NN(_.WN);wO=_.NN(_.VN);xO=_.NN(_.aO);
_.yO=function(a){_.Q.call(this,a);var b=this;this.template=function(c){b.Z.tick("c_rns","home");return oO.call(b,c)};this.Z=_.r.get(_.ky);this.Ta=function(c){if(c.continuationContents&&c.continuationContents.tvSurfaceContentContinuation&&c.continuationContents.tvSurfaceContentContinuation.content){c=c.continuationContents.tvSurfaceContentContinuation;var d=c.header,e=c.trackingParams;b.props.data.content=c.content;b.props.data.header=d;b.props.data.trackingParams=e;_.qx(b)}};this.state={Yf:!a.data.content}};
_.G(_.yO,_.Q);_.yO.getDerivedStateFromProps=function(a,b){return{Yf:!a.data.content||b.Yf}};_.yO.prototype.h=function(a){this.props.data.selection=a;_.qx(this)};
var nO=function(a,b,c,d,e){c=_.D("enableLeftNav",!1)?c?31:e||a.j?41.125:37.75:c?33:e?38.375:42;b=b&&a.props.focused;e=_.D("enableLeftNav",!1);var f=a.props.data.header&&a.props.data.header.tvSurfaceHeaderRenderer;f=f?_.Sx(f.title)+". "+_.Sx(f.subtitle)+".":"";var g={};return{genericPromoRenderer:{I:_.UN,props:{className:"ytlr-tv-surface-content-renderer__content",focused:b}},gridRenderer:{I:pO,props:{className:"ytlr-tv-surface-content-renderer__content",itemHeight:e?22.25:18.625,itemWidth:e?24.125:
22,Mh:0,Nh:1.5,height:e?37.75:39.25,jh:3,dj:0,width:e?74.5:71.5,Xg:!0,focused:b,focusId:"content",onUpFocus:a.A&&"header",ariaLabel:f}},sectionListRenderer:{I:qO,props:{className:["ytlr-tv-surface-content-renderer__content",(g["ytlr-tv-surface-content-renderer__content--top-level-topic"]=a.j,g)],height:c,ri:d,focused:b,focusId:"content",onUpFocus:a.A&&"header"}},tvAccountContentRenderer:{I:_.RN,props:{className:"ytlr-tv-surface-content-renderer__content",focused:b}},settingActionRenderer:{I:rO,props:{className:"ytlr-tv-surface-content-renderer__content",
focused:b}},settingBooleanRenderer:{I:sO,props:{className:"ytlr-tv-surface-content-renderer__content",focused:b}},settingReadOnlyItemRenderer:{I:tO,props:{className:"ytlr-tv-surface-content-renderer__content",focused:b}},settingSingleOptionMenuRenderer:{I:uO,props:{className:"ytlr-tv-surface-content-renderer__content",focused:b}},linkPhoneWithWiFiRenderer:{I:vO,props:{className:"ytlr-tv-surface-content-renderer__content",focused:b}},linkPhoneWithTvCodeRenderer:{I:wO,props:{className:"ytlr-tv-surface-content-renderer__content",
focused:b}},unlinkDevicesRenderer:{I:xO,props:{className:"ytlr-tv-surface-content-renderer__content",focused:b}}}},zO=function(a){requestAnimationFrame(function(){a.Z.tick("ol","home")})};_.yO.prototype.O=function(){this.Z.tick("c_rnf","home")};_.yO.prototype.W=function(){this.Z.tick("c_rnf","home");this.props.data.content&&zO(this)};_.yO.prototype.isFocused=function(a){return this.props.focused&&a===this.T};
_.E.Object.defineProperties(_.yO.prototype,{T:{configurable:!0,enumerable:!0,get:function(){return this.props.data.selection||"content"}},A:{configurable:!0,enumerable:!0,get:function(){return!!(this.props.data.header&&this.props.data.header.tvSurfaceHeaderRenderer&&this.props.data.header.tvSurfaceHeaderRenderer.buttons&&this.props.data.header.tvSurfaceHeaderRenderer.buttons.length)}},ri:{configurable:!0,enumerable:!0,get:function(){return!(!this.props.data.header||!this.props.data.header.tvSurfaceHeaderRenderer)}},
j:{configurable:!0,enumerable:!0,get:function(){return!!this.props.Do&&!this.props.lj&&!this.ri}}});_.yO.TAG_NAME="ytlr-tv-surface-content-renderer";

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.AO=function(a){return{thumbnails:[{height:a,url:_.td("avatar-guest_"+a+".png"),width:a}]}};_.m().w("sy31");
_.BO=_.p("YtlrAccountsContainer","rKP20b");
_.CO={tvSurfaceHeaderRenderer:{title:{runs:[{text:_.V("ACCOUNTS_SURFACE_TITLE_ONE_ACCOUNT")}]}}};

_.m().l();

}catch(e){_._DumpException(e)}
try{
var DO=function(a){return _.R("host",null,_.R("div",{className:["ytlr-legend-item__icon",a.kc]}),_.R("div",{className:"ytlr-legend-item__label"},a.label))};_.m().w("sy3a");
var EO=_.p("YtlrLegendItem","VCKRC");
_.FO=function(){_.Q.apply(this,arguments);this.template=DO};_.G(_.FO,_.Q);_.FO.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.FO.prototype.O=function(){var a=this.props.ma;a&&this.g("click",function(b){_.P(b);a()})};_.FO.TAG_NAME="ytlr-legend-item";_.J(EO,_.FO);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var HO=function(a){var b=this;a=a.Sc;return _.R("host",{"aria-hidden":"true"},_.TC(this.j?a:[],function(c){var d=c.Xb;return _.R(_.FO,{className:b.j,ma:c.ma,kc:GO[d].kc,label:GO[d].label})}))};_.m().w("sy39");
var IO={},GO=(IO.back={kc:"ytlr-legend-item__icon--back",Rc:[27],label:_.V("LEGEND_BACK")},IO["delete"]={kc:"ytlr-legend-item__icon--delete",Rc:[172],label:_.V("LEGEND_DELETE")},IO.guide={kc:"ytlr-legend-item__icon--guide",Rc:[172],label:_.V("LEGEND_GUIDE")},IO.home={kc:"ytlr-legend-item__icon--home",Rc:[71,172],label:_.V("LEGEND_HOME")},IO["on-screen-keyboard"]={kc:"ytlr-legend-item__icon--on-screen-keyboard",Rc:[170],label:_.V("LEGEND_ON_SCREEN_KEYBOARD")},IO.search={kc:"ytlr-legend-item__icon--search",
Rc:[83,170],label:_.V("LEGEND_SEARCH")},IO.space={kc:"ytlr-legend-item__icon--space",Rc:[170],label:_.V("LEGEND_SPACE")},IO["voice-search"]={kc:"ytlr-legend-item__icon--voice-search",Rc:[227],label:_.V("LEGEND_VOICE_SEARCH")},IO["voice-search-space"]={kc:"ytlr-legend-item__icon--voice-search-space",Rc:[170],label:_.V("LEGEND_VOICE_SEARCH")},IO);
var JO=_.p("YtlrLegend","mZyekd");
_.KO=function(){_.Q.apply(this,arguments);this.h={};this.j=_.D("legendConfig",_.Tj).className;this.template=HO};_.G(_.KO,_.Q);_.KO.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.KO.prototype.O=function(){this.h=LO(this.props.Sc);var a=this.A.bind(this);document.addEventListener("keydown",a);_.T(this,function(){document.removeEventListener("keydown",a)})};_.KO.prototype.W=function(){this.h=LO(this.props.Sc)};
var LO=function(a){for(var b={},c=0;c<a.length;c++){var d=a[c],e=d.ma;d=GO[d.Xb].Rc;if(e)for(var f=0;f<d.length;f++)b[d[f]]=e}return b};_.KO.prototype.A=function(a){var b=this.h[a.keyCode];b&&(b(),_.P(a))};_.KO.TAG_NAME="ytlr-legend";_.J(JO,_.KO);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy4u");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var QO=function(a){var b=a.focused,c=a.selected,d=a.style,e=a.wg,f=a.data,g=f.icon,h=f.presentationStyle;a=f.title;var l=f.thumbnail,n=f.iconText,q=void 0;h&&"NEW_CONTENT"===h.style?e?delete f.presentationStyle:q="fresh":h&&"CHECKED"===h.style&&(q="checked");var t={borderColor:b?"white":"grey",thumbnail:l,iconText:n,size:"regular",overlay:q,style:!g||"MIX"!==g.iconType&&"PLAYLISTS"!==g.iconType?"circle":"square"};b=n||l;e=1===d;f={};return _.R("host",{role:"tab",hybridNavFocusable:!0,className:[PO[d||
0],(f["ytlr-tab-renderer--selected"]=c,f["ytlr-tab-renderer--has-avatar"]=b,f["ytlr-tab-renderer--enable-left-nav"]=_.D("enableLeftNav",!1),f)]},_.Y(b,function(){return _.R(_.hA,Object.assign({className:"ytlr-tab-renderer__avatar-icon"},t))}),_.R(_.X,{className:"ytlr-tab-renderer__title",data:a}),_.Y(e,function(){return _.R("div",{className:"ytlr-tab-renderer__top-nav-tab-stroke"})}))},UO=function(a){var b=this;a=a.data;var c=a.$n,d=a.subtitle,e=a.title,f=this.A,g=this.D,h=this.B,l=this.selectedIndex,
n=this.isFocused("nav"),q=_.D("enableLeftNav",!1);a=this.isSelected("nav");a=!h&&(a||this.isSelected("promo"));var t=function(H){var M=H.item;return _.R(_.xJ,{config:b.J(M,H.selected,H.index,n,g),renderer:M})};if(g)var w=_.R(_.XC,{className:"ytlr-tv-secondary-nav-renderer__chip-list",idomKey:"ytlr-tv-secondary-nav-renderer__chip-list",alignment:_.sy,items:f||[],focused:n,focusId:"nav",Fa:t,size:65,M:this.M,selectedIndex:l||0,spacing:1,ea:0,gb:3,onDown:this.C});else if(h)w=_.R(_.YL,{role:"tablist",
className:"ytlr-tv-secondary-nav-renderer__top-nav",layout:"horizontal",selectedIndex:l,M:this.M,focused:n,focusId:"nav",onDown:this.C,onSelect:this.C,nf:!0},_.TC(f,function(H,M){return _.R(RO,{data:H.tabRenderer||{},wg:l===M,style:1,onSelect:function(){b.M(M)}})}));else{var y={};w=_.R(_.Fz,{role:"tablist",className:["ytlr-tv-secondary-nav-renderer__side-nav",(y["ytlr-tv-secondary-nav-renderer__side-nav--shifted"]=!a,y)],alignment:_.sy,Yd:this.Yd,isHidden:!a,items:f,layout:"vertical",M:this.M,selectedIndex:l,
size:q?37:38,xa:function(H){return SO(H)?q?1.125:2:q?3.25:4},ea:0,gb:4.25,Fa:t,Ub:!0,focusId:"nav",onRight:this.C,onSelect:this.C})}f=h?"ytlr-tv-secondary-nav-renderer--with-top-nav":"ytlr-tv-secondary-nav-renderer--with-side-nav";t=TO[this.T];y=this.isFocused("content")||this.isFocused("promo");var B=a&&!y,z={};return _.R("host",{className:[t,f,(z["ytlr-tv-secondary-nav-renderer--enable-left-nav"]=q,z["ytlr-tv-secondary-nav-renderer--shift-content"]=a,z["ytlr-tv-secondary-nav-renderer--chip-nav"]=
g,z)]},_.Y(!(q&&h)&&!g,function(){return _.R("div",{className:"ytlr-tv-secondary-nav-renderer__nav-container",idomKey:"ytlr-tv-secondary-nav-renderer__nav-container"},_.Y(!h,function(){return _.R(_.X,{className:"ytlr-tv-secondary-nav-renderer__title",data:e})}),w)}),_.Y(g,function(){var H={};return _.R("div",{className:"ytlr-tv-secondary-nav-renderer__nav-container",idomKey:"ytlr-tv-secondary-nav-renderer__nav-container"},_.R("div",{className:["ytlr-tv-secondary-nav-renderer__title-container",(H["ytlr-tv-secondary-nav-renderer__title-container--hidden-title-container"]=
c,H)]},_.R(_.X,{className:"ytlr-tv-secondary-nav-renderer__title",data:e}),_.R(_.X,{className:"ytlr-tv-secondary-nav-renderer__subtitle",data:d})),w)}),_.R(_.yO,{className:"ytlr-tv-secondary-nav-renderer__content",data:this.F||{},focusId:"content",focused:y,Fk:h,isHidden:B,onBack:this.S,onLeft:this.P,onUpFocus:h&&"nav",lj:!h,yo:g}))};_.m().w("sy4v");
var VO={},PO=(VO[1]="ytlr-tab-renderer--top-nav",VO[0]="ytlr-tab-renderer--side-nav",VO);
var RO=function(){_.Q.apply(this,arguments);this.template=QO};_.G(RO,_.Q);RO.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;RO.prototype.shouldUpdate=function(a){return this.props.data!==a.data||this.props.focused!==a.focused||this.props.selected!==a.selected||this.props.wg!==a.wg||this.props.style!==a.style};RO.TAG_NAME="ytlr-tab-renderer";
var WO={},TO=(WO.content="ytlr-tv-secondary-nav-renderer--content-selected",WO.nav="ytlr-tv-secondary-nav-renderer--navigation-selected",WO.promo="ytlr-tv-secondary-nav-renderer--button-pane-selected",WO);
_.ZO=function(){_.Q.apply(this,arguments);var a=this;this.template=UO;this.bedrockPorts=_.ov();this.H=_.L.R();this.Z=_.r.get(_.ky);this.N=!!_.Vu.instance&&_.Wu()||void 0;this.G=_.wx(function(b){if(!b)return[];var c=[];if(b.length)for(var d=0;d<b.length;d++){var e=b[d]&&b[d].tvSecondaryNavSectionRenderer;e&&(e.title&&c.push({title:e.title}),e.tabs&&c.push.apply(c,_.F(e.tabs)))}return c});this.J=function(b,c,d,e,f){var g={};return{tabRenderer:{I:f?_.ML:RO,props:{focused:c&&e,selected:c,wg:c&&!e,style:0,
onSelect:function(){a.M(d)}}},title:{I:_.X,props:{className:["ytlr-tv-secondary-nav-renderer__section-title",(g["ytlr-tv-secondary-nav-renderer__section-title--divider"]=SO(b),g)]}}}};this.C=function(b){var c=a.A[a.selectedIndex];if(c&&XO(a,c)){var d=(c=(c=a.A[a.selectedIndex].tabRenderer)&&c.content)&&c.tvSurfaceContentRenderer&&c.tvSurfaceContentRenderer.content;c=YO(c)||YO(d)?"promo":c&&d?d.sectionListRenderer||d.gridRenderer?"content":"nav":"nav";a.props.data.selection=c;13===b.keyCode?_.P(b):
_.bw(b);_.qx(a)}};this.S=function(b){_.D("enableLeftNav",!1)&&a.B&&!a.D||a.props.data.selectedIndex&&0>a.props.data.selectedIndex||(a.props.data.selection="nav",13===b.keyCode?_.P(b):_.bw(b),_.qx(a),_.P(b))};this.P=function(b){a.B||(a.h("nav"),a.j("showLogoAction",void 0),_.P(b))};this.M=function(b,c){var d=!1;a.props.data.$n=c;a.props.data.selection="nav";b!==a.props.data.selectedIndex&&(_.iy(a.Z,"home",["ol"]),c={},a.Z.info((c.pa="home",c.prev_browse_id=a.browseId,c),"home"),d=!0);a.props.data.selectedIndex=
b;d&&(b=a.F&&a.F.content?"hot":"warm",c={},a.Z.info((c.browse_id=a.browseId,c.yt_lt=b,c),"home"));a.browseEndpoint&&_.U(a,"update-history-token",a.browseEndpoint);a.endpoint&&a.endpoint.clickTrackingParams&&a.bedrockPorts&&a.bedrockPorts.recordClick(a.endpoint.clickTrackingParams);_.qx(a)}};_.G(_.ZO,_.Q);_.ZO.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.ZO.prototype.h=function(a){this.props.data.selection=a;_.qx(this)};_.ZO.prototype.W=function(){$O(this)};
_.ZO.prototype.O=function(){var a=this;$O(this,!0);_.T(this,function(){a.j("showLogoAction",void 0)})};_.ZO.prototype.Yd=function(a){return!(!a||!a.tabRenderer)};
var XO=function(a,b){b=b.tabRenderer;if(!b||!b.content)return!1;b=b.content;if(b.linkPhoneWithWiFiRenderer||b.linkPhoneWithTvCodeRenderer)return!1;if(b.settingReadOnlyItemRenderer)return"ABOUT_OPEN_SOURCE_LICENSES"===b.settingReadOnlyItemRenderer.itemId;a=null===a.bedrockPorts;return b.unlinkDevicesRenderer&&a?!!_.r.get(_.gG).md().length:!0};_.ZO.prototype.isFocused=function(a){return!_.v.isEmpty(this.props.data)&&this.props.focused&&this.T===a};
_.ZO.prototype.isSelected=function(a){return!_.v.isEmpty(this.props.data)&&this.T===a};
var YO=function(a){return a&&(a&&a.genericPromoRenderer&&a.genericPromoRenderer.actionButton||a.tvAccountContentRenderer&&aP(a.tvAccountContentRenderer)||a.settingBooleanRenderer||a.settingActionRenderer||a.settingReadOnlyItemRenderer||a.settingSingleOptionMenuRenderer||a.linkPhoneWithTvCodeRenderer||a.linkPhoneWithWiFiRenderer||a.unlinkDevicesRenderer)},aP=function(a){return a.buttons?a.buttons.reduce(function(b,c){return!c.buttonRenderer.isDisabled||b},!1):!1};
_.ZO.prototype.j=function(a,b){this.H&&_.Eo(this.H,new _.O(a),b)};var $O=function(a,b){b=void 0===b?!1:b;var c;"content"!==a.props.data.selection||a.B?(a.j("showLogoAction",void 0),null===(c=a.N)||void 0===c?void 0:c.resolveCommand({signalAction:{signal:"SHOW_GUIDE"}})):a.j("hideLogoAction",{Kk:b})},SO=function(a){var b;return""===(null===(b=a.title)||void 0===b?void 0:b.simpleText)};
_.E.Object.defineProperties(_.ZO.prototype,{T:{configurable:!0,enumerable:!0,get:function(){return this.props.data.selection?_.D("enableLeftNav",!1)&&this.B&&!this.D&&"nav"===this.props.data.selection?"content":this.props.data.selection:this.B?"content":"nav"}},A:{configurable:!0,enumerable:!0,get:function(){return this.G(this.props.data.sections)}},selectedIndex:{configurable:!0,enumerable:!0,get:function(){var a=this;if(void 0!==this.props.data.selectedIndex)return this.props.data.selectedIndex;
var b=this.A;this.props.data.selectedIndex=Math.max(b.findIndex(function(c){return!(!a.Yd(c)||!c.tabRenderer.selected)}),b.findIndex(function(c){return a.Yd(c)}));return this.props.data.selectedIndex}},F:{configurable:!0,enumerable:!0,get:function(){var a=this.A[this.selectedIndex].tabRenderer,b=a&&a.content;return b&&b.tvSurfaceContentRenderer||a}},browseEndpoint:{configurable:!0,enumerable:!0,get:function(){var a=this.endpoint;return a&&a.browseEndpoint}},endpoint:{configurable:!0,enumerable:!0,
get:function(){var a=this.A[this.selectedIndex].tabRenderer;return a&&a.endpoint}},browseId:{configurable:!0,enumerable:!0,get:function(){var a=this.browseEndpoint;return a&&a.browseId||""}},B:{configurable:!0,enumerable:!0,get:function(){var a=this.browseId;return"default"===a||"FEtopics"===a||this.D}},D:{configurable:!0,enumerable:!0,get:function(){return"TV_SECONDARY_NAV_RENDERER_STYLE_CHIPS"===this.props.data.style}}});_.ZO.TAG_NAME="ytlr-tv-secondary-nav-renderer";

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy4y");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var mP,oP,nP,pP,qP,tP,sP,uP,wP,vP,zP,BP;mP=function(a){var b;if(null===(b=a.Kh)||void 0===b||!b.xo){var c=a.keyCode;if(37===c||39===c||176===c||177===c)_.bw(a,!1,!0),c=_.Et(a.target,a.type,_.uy[c],!0,Object.assign(Object.assign({},a),{xo:!0})),a.target.dispatchEvent(c)}};oP=function(a){"focusin"===a.type&&nP(a.target,a)};
nP=function(a,b){if(a){var c=(a=pP(a))&&qP(a.parentElement);if(c)if(c.ia.T===a.focusId)_.bw(b);else{try{if(c.ia.h(a.focusId),"focusin"!==b.type){_.bw(b);return}}catch(d){_.C(d)}nP(c,b)}}};pP=function(a){if(!a)return null;if(!a.ia)return pP(a.parentElement);var b=a.ia.props.focusId;return b?{focusId:b,parentElement:a.parentElement}:pP(a.parentElement)};qP=function(a){return a?a.ia?a.ia.h&&a.ia.T?a:qP(a.parentElement):qP(a.parentElement):null};tP=function(a){rP[a.keyCode]&&sP(a,a.target)};
sP=function(a,b){if(b=uP(b,a)){try{b.handle()}catch(c){_.C(c)}a.defaultPrevented||sP(a,b.element.parentElement)}};uP=function(a,b){if(!a)return null;if(!a.ia)return uP(a.parentElement,b);var c=vP(a.ia,b.keyCode);if(c){var d=qP(a.parentElement);if(d&&d.ia.T!==c)return{handle:function(){d.ia.h(c);_.P(b)},element:a}}var e=wP(a.ia,b.keyCode);return e?{handle:function(){e.call(a.ia,b)},element:a}:uP(a.parentElement,b)};wP=function(a,b){b=rP[b];return a.props[b]?a.props[b]:a[b]};
vP=function(a,b){b=xP[b];return a.props[b]?a.props[b]:a[b]};zP=function(a){var b=yP[a.type],c=a.target;do{var d=c.ia;if(d){var e=d.props[b]||d[b];if(e){try{e.call(d,a)}catch(f){_.C(f)}if(a.defaultPrevented)break}}c=c.parentElement}while(c)};
BP=function(a){_.D("isCobaltHybridNav",!1)&&a.addEventListener("focusin",oP);a.addEventListener("keydown",tP);_.D("isRtl",!1)&&(a.addEventListener("keydown",mP,!0),a.addEventListener("keyup",mP,!0));if(_.D("shouldInstallTouchListeners",!1))for(var b=0;b<AP.length;b++)a.addEventListener(AP[b],zP)};_.CP=function(a,b,c){(0,_.jx)(a,_.R(b,c));var d=a.firstChild.ia;BP(d.el);_.Rt(d.el);new Promise(function(e){_.Mt({X:function(){e(d)},stage:20})})};_.m().w("sy50");
var DP=_.Zw(function(){if(window.h5vcc){var a=document.createTextNode("");Object.defineProperty(Text.prototype,"data",{get:function(){return this.textContent},set:function(b){this.textContent=b;this.parentNode.appendChild(a);this.parentNode.removeChild(a)}})}});
var EP={},rP=(EP[8]="onBack",EP[13]="onSelect",EP[27]="onBack",EP[38]="onUp",EP[40]="onDown",EP[37]="onLeft",EP[177]="onLeft",EP[176]="onRight",EP[39]="onRight",EP),FP={},xP=(FP[8]="onBackFocus",FP[27]="onBackFocus",FP[13]="onSelectFocus",FP[38]="onUpFocus",FP[40]="onDownFocus",FP[37]="onLeftFocus",FP[177]="onLeftFocus",FP[176]="onRightFocus",FP[39]="onRightFocus",FP);
var yP={touchstart:"onTouchStart",touchend:"onTouchEnd",touchcancel:"onTouchCancel",touchmove:"onTouchMove",click:"onClick"},AP=Object.keys(yP);
DP();

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy51");
_.GP=_.p("Automation","z27KAb");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy57");
var rS=_.p("DialAdditionalDataService","BrI0Nd");
var sS=function(){var a={};this.g=(a.testYWRkaXR="c0ef1ca",a);this.j=_.r.get(_.eD)},tS;sS.prototype.update=function(a){this.g=Object.assign(Object.assign({},this.g),a);tS(this)};sS.prototype.h=function(a){for(var b=[],c=0;c<arguments.length;++c)b[c]=arguments[c];b=_.A(b);for(c=b.next();!c.done;c=b.next())delete this.g[c.value];tS(this)};
tS=function(a){var b=a.j.j;b&&(_.jq(_.Yp(b,{method:"POST",headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"},body:_.Jc(a.g)})).catch(function(){}),a=_.pc(b,a.g),_.jq(_.Yp(a,{method:"GET"})).catch(function(){}))};_.uS=_.K(rS,function(){return new sS});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy58");
var vS=_.p("DialSeamlessService","M13ddd");
var wS,yS;wS={};_.xS=(wS.smoothPairing=["screenId","theme"],wS.seamlessSignIn=["authCode","signInSessionId"],wS.seamlessSmartRemote=["smartRemoteRequestedTime"],wS);yS=function(){_.oH.apply(this,arguments);this.h=_.r.get(_.uS);this.B=_.r.get(_.eD);this.j=_.r.get(_.gG)};_.G(yS,_.oH);yS.prototype.A=function(){if(_.aG(this.B)){var a=this.j.$d();a&&this.h.update({screenId:a,theme:"cl"})}};
yS.prototype.C=function(a){_.aG(this.B)&&(this.h.h.apply(this.h,_.F(_.xS.smoothPairing)),"SCREEN_RESET"!==a||this.B.g.isInAppServer()||this.j.connect("screenReset,smoothPairing reconnnect"))};_.zS=_.K(vS,function(){return new yS});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy56");
var AS=_.p("MdxRemoteSignInService","rq4Z4c");
var BS=function(){_.oH.apply(this,arguments);this.j=_.r.get(_.eD);this.h=_.r.get(_.zS)};_.G(BS,_.oH);_.CS=function(a){return 2===a.j.h&&"account_manager"!==_.D("supportedIdentityEnvironment","oauth")};_.DS=_.K(AS,function(){return new BS});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.ES=function(a){var b;if(3===(null===a||void 0===a?void 0:a.F()))return null===(b=null===a||void 0===a?void 0:a.D())||void 0===b?void 0:b.F()};_.FS=function(a){var b;if(2===(null===a||void 0===a?void 0:a.F()))return null===(b=null===a||void 0===a?void 0:a.D())||void 0===b?void 0:b.D()};_.GS={browseEndpoint:{browseId:"default"}};_.HS={eventTrigger:"ACCOUNT_EVENT_TRIGGER_STANDARD",nextEndpoint:_.GS};_.m().w("sy55");
var IS=_.p("DirectSignInStartupService","pctuIe");
var NS,KS,LS,MS,OS;NS=function(){var a=this;this.getResult=_.wx(function(){var b;if(_.JS(a))return KS(a);var c,d,e=_.ES(null===(c=a.data)||void 0===c?void 0:c.$j);return[11,12,5].includes(e)||null!==(d=a.data)&&void 0!==d&&d.Jm?LS(a,{signInStyle:"SIGN_IN_STYLE_VERIFY_ITS_YOU_FULLSCREEN",signInType:"SIGN_IN_METHOD_TYPE_SEAMLESS"}):(c=_.FS(null===(b=a.data)||void 0===b?void 0:b.$j))?2===c?LS(a,{signInStyle:"SIGN_IN_STYLE_FULLSCREEN",signInType:"SIGN_IN_METHOD_TYPE_SEAMLESS"}):MS(a)?KS(a):LS(a,{}):KS(a)})};
_.JS=function(a){var b;return null===(b=a.data)||void 0===b?void 0:b.pb};KS=function(a){var b;var c=(null===(b=a.data)||void 0===b?void 0:b.mg.command)||_.GS;var d;b=(null===(d=a.data)||void 0===d?void 0:d.mg.context)||_.HS;var e;null!==(e=a.data)&&void 0!==e&&e.mg.command.watchEndpoint&&(a=OS([c,b.nextEndpoint]),b=Object.assign(Object.assign({},b),{nextEndpoint:a}),c=_.GS);return{Mi:c,kl:void 0,identityActionContext:b}};
LS=function(a,b){var c=KS(a),d=c.Mi;c=c.identityActionContext;if("SIGN_IN_METHOD_TYPE_SEAMLESS"===b.signInType){var e=_.CS(_.r.get(_.DS))?"DIRECT_SIGN_IN_EVENT_ALTERNATIVE_FLOW_TYPE_SEAMLESS":"DIRECT_SIGN_IN_EVENT_ALTERNATIVE_FLOW_TYPE_URL",f={};e={payloadName:"directSignInEvent",clientEvent:(f.directSignInEvent={eventType:"DIRECT_SIGN_IN_EVENT_TYPE_ALTERNATIVE_SIGN_IN_SUCCEEDED",details:{altFlowType:e}},f)};e=OS([c.nextEndpoint,{logPayloadCommand:e}]);c=Object.assign(Object.assign({},c),{nextEndpoint:e})}b=
{startSignInCommand:Object.assign(Object.assign({},b),{identityActionContext:c})};MS(a)&&(d=_.GS);return{Mi:d,kl:b,identityActionContext:c}};MS=function(a){var b;return!(null===(b=a.data)||void 0===b||!b.mg.command.startWelcomeCommand)};OS=function(a){return{commandExecutorCommand:{commands:a.filter(function(b){return!!b})}}};_.PS=_.K(IS,function(){return new NS});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy59");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var WS,XS,YS;WS=function(a){return a.arrayBuffer()};XS=function(a){return function(b){return new _.Zo(function(c){a.decodeAudioData(b,function(d){c.next(d)},function(d){c.error(d)})})}};YS=function(){var a=void 0===a?window:a;return a.AudioContext?new a.AudioContext:a.webkitAudioContext?new a.webkitAudioContext:null};_.ZS=function(a){var b=0,c=function(){b=0};return function(d){b||(b=_.x.setTimeout(c,100),a.apply(void 0,arguments))}};_.m().w("sy5b");
var $S=function(a,b,c){var d=this;this.id=a;this.g=null;var e=_.Op();_.Yp("https://www.gstatic.com/"+c.app+"/sound/"+a+"."+c.format,{}).g(_.ip(WS,1),_.ip(XS(b),1)).subscribe(function(f){d.g=f;e.resolve(f)},_.C)};
var aT=_.p("SoundService","Jhv2Rd");
var bT;bT=function(){var a=YS(),b=_.pd().sounds,c=this;this.g=a;this.config=b;this.h=_.r.get(_.vG);this.sounds={};this.play=_.ZS(function(d){if(c.h.isEnabled("ENABLE_SOUND")&&c.g&&c.config&&c.config.supportedSounds[d]&&(d=c.config.supportedSounds[d],c.sounds[d]||(c.sounds[d]=new $S(d,c.g,c.config)),d=c.sounds[d],d.g)){var e=c.g.createBufferSource();e.buffer=d.g;e.connect(c.g.destination);e.start(0)}})};_.cT=_.K(aT,function(){return new bT});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.dT=function(a,b){var c=_.r.get(_.tv).get();return _.hp(c).g(_.Cr(function(d){return _.iu(a,b,d)}))};_.m().w("sy5d");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var fT;_.eT=function(a){var b,c=null===(b=a.values().next().value)||void 0===b?void 0:b.identity.ownerObfuscatedGaiaId;return a.get(c)};_.gT=function(){var a=_.zA({title:_.V("APP_RELOAD_REQUIRED_TITLE"),subtitle:_.V("APP_RELOAD_REQUIRED_SUBTITLE"),icon:{iconType:"INFO_OUTLINE"}}),b={buttonRenderer:{text:{simpleText:_.V("APP_RELOAD_REQUIRED_BUTTON")},command:{signalAction:{signal:"RELOAD_PAGE"}}}};return _.wA(_.AA(a,[b]),fT)};
_.hT=function(){function a(c){c&&_.Qb(c)&&c.dogfood_changed&&(_.Wu().resolveCommand(_.gT()),setTimeout(function(){_.lG()},5E3))}if(!_.qd("disable_dogfood_checks",!1)){var b={action_check_df:!0,client:"lb4",theme:"cl"};return new _.Zg(function(c){var d=_.Ka(new _.Ga("/leanback_ajax"),_.mG(b));_.dT(d.toString(),{}).subscribe(function(e){a(e);c(void 0)},function(e){_.C(e);c(void 0)})})}};fT={signalAction:{signal:"UNKNOWN"}};_.m().w("sy5c");
_.iT=_.p("afterAppStartTasks","HZgGtc");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy5f");
_.vT=_.p("YtlrSearchContainer","iGSLje");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy5g");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy5h");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var wT;wT=function(a,b){for(var c="string"===typeof a?a.split(""):a,d=a.length-1;0<=d;--d)d in c&&b.call(void 0,c[d],d,a)};_.Ll=function(){_.Dl.apply(this,arguments)};_.G(_.Ll,_.Dl);_.m().w("sy5i");
var xT=_.p("SearchHistoryService","nUEjEc");
var AT,zT;_.yT=new _.Ll("yt.leanback.default.search-history::recent-searches");AT=function(){var a=this;this.storage=_.r.get(_.vd);this.qa=_.r.get(_.tv);this.h=_.L.R();this.g=new _.WB(30);zT(this);_.N(this.h,new _.O("CLEARED_SEARCH_HISTORY"),this,function(){a.g.clear();a.storage.remove(_.yT)})};zT=function(a){var b=a.storage.get(_.yT)||[];wT(b,function(c){var d=c[0];a.g.set(d,{query:d,count:c[1],timestamp:c[2]})})};_.BT=_.K(xT,function(){return new AT});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy5j");
_.CT=_.p("YtlrWatchPage","HpJhd");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var QU=function(){var a=void 0===a?window:a;return{auto:{execute:function(b){return OU(b)}},co:{execute:function(b){a:{var c=a.h5vcc,d,e;if(1===b.length&&null!==(d=null===c||void 0===c?void 0:c.updater)&&void 0!==d&&d.setUpdaterChannel){try{null===(e=null===c||void 0===c?void 0:c.updater)||void 0===e?void 0:e.setUpdaterChannel(b[0])}catch(f){console.log(f);b=!1;break a}b=!0}else b=!1}return b}},disabledsi:{execute:function(){a:{_.r.get(_.vd).remove(_.El);try{_.lG()}catch(c){_.C(c);var b=!1;break a}b=
!0}return b}},enabledsi:{execute:function(){a:{_.r.get(_.vd).set(_.El,!0);try{_.lG()}catch(c){_.C(c);var b=!1;break a}b=!0}return b}},rldat:{execute:function(){return PU()}}}},OU=function(a){if(1>a.length)return!1;var b=RU[a[0]];return b?(_.rj(_.GP).then(function(c){c.Ve(b,document,window,_.r.get(_.vd))}),!0):!1},PU=function(){_.r.get(_.tv).get().then(_.LI).then(function(a){var b=null===a||void 0===a?void 0:a.accessToken,c=new _.La;(null===a||void 0===a?0:a.accessToken)&&c.set("access_token",b);_.lG(c)});
return!0},TU=function(){var a={ld:{sm:SU,key:"loader"},mld:{sm:SU,key:"mloader"}},b={},c={},d;for(d in a){if(a.hasOwnProperty(d)){var e=a[d];c.Ch=e.sm;c.Ah=e.key;b[d]={execute:function(f){return function(g){if(1===g.length){var h=f.Ah;g=g[0];f.Ch.test(g)?(_.lG((new _.La).set(h,g)),h=!0):h=!1}else h=!1;return h}}(c)}}c={Ah:c.Ah,Ch:c.Ch}}return b};_.m().w("sy5p");
var RU={hn:"horizontalNavigationTest",vn:"verticalNavigationTest",chn:"cobaltHorizontalNavigationRoutine",cvnr:"cobaltVerticalNavigationRoutine",csr:"cobaltStartupRoutine",br:"browseRoutine",bwr:"browseWatchRoutine",cer:"certEnduranceRoutine",cpr:"certPerformanceRoutine"};
var SU=/^[a-z]+$/;
var UU=_.p("DebugCallService","NVNi1b");
var VU=new _.Dl("yt.bedrock::sticky-loader"),WU=window,XU=function(){this.enabled=WU.environment&&WU.environment.debug;this.commands=TU();this.g=QU();this.storage=_.r.get(_.Il);this.Ke=new _.Ct(!!_.D("initiallyShowDebugConsole",!1))};
XU.prototype.process=function(a){switch(a){case "bugchomp ":return this.enabled=!0;case "lclr ":return this.storage.remove(VU),a=_.mG({clearstickymloader:1}),_.lG(a),!0}if(!this.enabled)return!1;switch(a){case "console ":return this.Ke.set(!this.Ke.get()),!0}var b=a.match(/^(\S+)(?:\s+(\S+(?:\s+\S+)*\s*))?\s+$/);if(b){a=b[1].toLowerCase();b=b[2]?b[2].toLowerCase().split(" "):[];if(this.commands[a])return this.commands[a].execute(b);if(this.g[a])return this.g[a].execute(b)}return!1};
XU.prototype.isEnabled=function(){return this.enabled};_.YU=_.K(UU,function(){return new XU});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy5s");
var rV=_.p("LocaleService","puBxCe");
var sV=function(){};_.E.Object.defineProperties(sV.prototype,{bedrockPorts:{configurable:!0,enumerable:!0,get:function(){return _.ov()}},g:{configurable:!0,enumerable:!0,get:function(){return this.bedrockPorts&&this.bedrockPorts.locale}},language:{configurable:!0,enumerable:!0,get:function(){return this.g&&this.g.g()||"en"}},region:{configurable:!0,enumerable:!0,get:function(){return this.g&&this.g.region||"US"}}});_.tV=_.K(rV,function(){return new sV});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy5x");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy5y");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy5z");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var XX,ZX,$X,dY,eY,fY,bY;XX=function(a){return a};_.YX=function(a,b){return new _.Zo(function(c){var d=function(f){for(var g=[],h=0;h<arguments.length;++h)g[h]=arguments[h];return c.next(1===g.length?g[0]:g)};try{var e=a(d)}catch(f){c.error(f);return}if(_.Qo(b))return function(){return b(d,e)}})};ZX=function(a){var b=a.Fd,c=a.counter;a=a.period;b.next(c);this.schedule({Fd:b,counter:c+1,period:a},a)};
$X=function(a){a=void 0===a?0:a;var b=void 0===b?_.sp:b;if(!_.rp(a)||0>a)a=0;b&&"function"===typeof b.schedule||(b=_.sp);return new _.Zo(function(c){c.add(b.schedule(ZX,a,{Fd:c,counter:0,period:a}));return c})};
_.aY=function(a){for(var b=[],c=0;c<arguments.length;++c)b[c]=arguments[c];var d=Number.POSITIVE_INFINITY;c=void 0;var e=b[b.length-1];_.Yo(e)?(c=b.pop(),1<b.length&&"number"===typeof b[b.length-1]&&(d=b.pop())):"number"===typeof e&&(d=b.pop());!c&&1===b.length&&b[0]instanceof _.Zo?b=b[0]:(d=void 0===d?Number.POSITIVE_INFINITY:d,b=_.ip(XX,d)(c?_.xr(b,c):new _.Zo(_.qq(b))));return b};_.cY=function(a){return function(b){return b.Sa(new bY(a))}};
dY=function(){return function(a){return a.g(_.ip(function(b){return $X(1E3*b.interval).g(_.dp(function(){return b}))},1))}};eY=function(){return function(a){return a.g(_.ip(function(b){var c=_.pd().oAuthClientProfiles[0];b={method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({client_id:c.clientId,client_secret:c.rg,code:b.device_code,grant_type:"http://oauth.net/grant_type/device/1.0"})};return _.Yp("/o/oauth2/token",b)},1),_.ip(function(b){return b.json()},1))}};
fY=function(a){return a.g(dY(),eY(),_.ip(function(b){return b.error?"authorization_pending"===b.error||"slow_down"===b.error?_.np:_.$o(b.error):_.yr(b)},1),_.Ev())};bY=function(a){this.g=a;this.complete=this.error=void 0};bY.prototype.call=function(a,b){return b.subscribe(new _.jr(a,this.g,this.error,this.complete))};_.m().w("sy61");
var gY=_.p("SignInWithDeviceFlowService","ZgFIpc");
var hY=function(){this.g=_.r.get(_.Tv);this.h=_.r.get(_.gu)};hY.prototype.signIn=function(a,b,c){var d=_.Yp("/o/oauth2/device/code",iY()).g(_.ip(function(f){return f.json()},1),_.bv()),e=d.g(jY());d=fY(d).g(_.bv());b=d.g(kY(this,b,c));a=_.yv(b,d,a).g(lY(),mY(this),nY());return _.aY(e,b,a).g(_.bv(),_.yp(function(f){return f.g(_.dp(function(g){if("expired_token"===g)return g;throw g;}))}))};
var iY=function(){var a=_.pd().oAuthClientProfiles[0],b={client_id:a.clientId,scope:a.fj};a.gj&&(b.redirect_uri=a.gj);return{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(b)}},jY=function(){return _.dp(function(a){return{type:0,code:a.user_code}})},kY=function(a,b,c){return _.wv(_.Cr(function(d){return _.Rv(a.g,{refreshToken:d.refresh_token,clientIdName:0},d.access_token).g(_.dp(function(e){return{pb:e,accessToken:d.access_token}}))}),_.dp(function(d){a.Vc(d,b,c);return{type:4,
pb:d.pb}}))},mY=function(a){return _.cY(function(b){a.h.g.add(b.identity.effectiveObfuscatedGaiaId,b)})},nY=function(){return _.dp(function(a){return{type:5,identity:a.identity}})},lY=function(){return _.dp(function(a){var b=_.A(a);a=b.next().value;var c=b.next().value;b=b.next().value;return _.Dr(c.access_token,c.expires_in,a.pb.get(b).identity)})};
hY.prototype.Vc=function(a,b,c){var d=a.pb.values().next().value.identity.ownerObfuscatedGaiaId;(d=a.pb.get(d))?(a={accessToken:a.accessToken,refreshToken:d.identity.identityCredentials.refreshToken},_.r.get(_.nv).Vc(d.identity,a,b,c)):_.C(Error("dc"))};_.oY=_.K(gY,function(){return new hY});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.IY=function(a,b,c){for(c=void 0===c?!1:c;a;){c?c=!1:a=a.parentElement;var d=void 0,e=a,f=b;if(null!==e&&void 0!==e&&e.className&&((null===(d=e.classList)||void 0===d?0:d.contains)?e.classList.contains(f):e.className.split(" ").includes(f)))return a}return null};_.m().w("sy63");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.qY=function(a){return _.vA({overlayPanelRenderer:_.AA(_.zA({title:_.V("SOMETHING_WENT_WRONG"),subtitle:_.V("SIGN_IN_FLOW_ERROR_SUBTITLE")}),[{compactLinkRenderer:{title:{simpleText:_.V("TRY_AGAIN")},serviceEndpoint:{commandExecutorCommand:{commands:[{signalAction:{signal:"CLOSE_POPUP"}},a]}}}}])})};_.m().w("sy66");
var rY;rY={};_.sY=(rY.ACCOUNT_EVENT_TRIGGER_LIKE_DISLIKE="INTERSTITIAL_REASON_LIKE_DISLIKE",rY.ACCOUNT_EVENT_TRIGGER_SAVE_VIDEO="INTERSTITIAL_REASON_SAVE_VIDEO",rY.ACCOUNT_EVENT_TRIGGER_SUBSCRIBE="INTERSTITIAL_REASON_SUBSCRIBE",rY.ACCOUNT_EVENT_TRIGGER_REPORT_VIDEO="INTERSTITIAL_REASON_REPORT_VIDEO",rY.ACCOUNT_EVENT_TRIGGER_PLAYER="INTERSTITIAL_REASON_TRIGGER_PLAYER",rY);_.tY=["SIGN_IN_STYLE_FULLSCREEN","SIGN_IN_STYLE_VERIFY_ITS_YOU_FULLSCREEN"];

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.uY=function(a,b){var c="SIGN_IN_STYLE_VERIFY_ITS_YOU_FULLSCREEN"===b?_.zA({title:_.V("VERIFY_ITS_YOU_OVERLAY_TITLE"),subtitle:_.V("VERIFY_ITS_YOU_OVERLAY_SUBTITLE")}):"SIGN_IN_STYLE_SIDE_PANEL"===b?_.zA({title:_.V("SEAMLESS_OVERLAY_TITLE"),image:{thumbnails:[{height:216,url:_.td("sign_in_tap_account_2.png"),width:216}]}}):_.zA({title:_.V("SEAMLESS_OVERLAY_TITLE")});var d={width:80,height:45,thumbnailDetails:{thumbnails:[{url:_.td(_.D("isRtl",!1)?"sign-in-ctx-panel-720p-seamless-rtl.png":"sign-in-ctx-panel-720p-seamless.png"),
width:1280,height:720}]}};return _.vA({overlayPanelRenderer:{header:{overlayPanelHeaderRenderer:c},content:{seamlessSignInRenderer:{identityActionContext:a}},footer:{overlayPanelItemListRenderer:{items:[{compactLinkRenderer:{title:{runs:[{text:_.V("TRY_ANOTHER_WAY")}]},serviceEndpoint:_.CA(a||{},{signInType:"SIGN_IN_METHOD_TYPE_URL",signInStyle:b})}}]}}},contextualPanelRenderer:_.tY.includes(b)?{messageThumbnailRenderer:d}:void 0})};_.m().w("sy67");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.gZ=function(a,b){var c="SIGN_IN_STYLE_VERIFY_ITS_YOU_FULLSCREEN"===b?_.zA({title:_.V("VERIFY_ITS_YOU_OVERLAY_TITLE"),subtitle:_.V("VERIFY_ITS_YOU_OVERLAY_SUBTITLE")}):"SIGN_IN_STYLE_SIDE_PANEL"===b?_.zA({title:_.V("URL_SIGN_IN_OVERLAY_TITLE"),image:{thumbnails:[{height:216,url:_.td("browser-youtube.png"),width:216}]}}):_.zA({title:_.V("URL_SIGN_IN_OVERLAY_TITLE")});var d={width:80,height:45,thumbnailDetails:{thumbnails:[{url:_.td(_.D("isRtl",!1)?"sign-in-ctx-panel-720p-browser-rtl.png":"sign-in-ctx-panel-720p-browser.png"),
width:1280,height:720}]}};return _.vA({overlayPanelRenderer:{header:{overlayPanelHeaderRenderer:c},content:{urlSignInRenderer:{identityActionContext:a}}},contextualPanelRenderer:_.tY.includes(b)?{messageThumbnailRenderer:d}:void 0})};_.m().w("sy68");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var LY=function(a){var b=a.data;a=!(!b.image&&!b.icon);var c=b.image&&b.image.thumbnails&&b.image.thumbnails[0],d=!!(c&&c.height&&c.width&&1>c.height/c.width);c=!b.subtitle;var e={},f={},g={};return _.R("host",{className:[KY(b.style),(e["ytlr-overlay-message-renderer--has-image"]=a,e),(f["ytlr-overlay-message-renderer--has-video-thumbnail"]=d,f),(g["ytlr-overlay-message-renderer--has-no-subtitle"]=c,g)]},_.Y(a,function(){return _.R("div",{className:"ytlr-overlay-message-renderer__image-container",
idomKey:"image-container"},_.Y(b.image,function(h){a:switch(b.style){case "OVERLAY_MESSAGE_STYLE_TOAST":var l=d?12.25:7;break a;default:l=1.25}return _.R(_.Pz,{className:"ytlr-overlay-message-renderer__image",idomKey:"image",data:h,width:l})}),_.Y(b.icon,function(h){return _.R(_.ry,{className:"ytlr-overlay-message-renderer__icon",icon:h,idomKey:"icon"})}))}),_.R("div",{className:"ytlr-overlay-message-renderer__text-container",idomKey:"text-container"},_.Y(b.label,function(h){return _.R(_.X,{className:"ytlr-overlay-message-renderer__label",
data:h,idomKey:"label"})}),_.Y(b.title,function(h){return _.R(_.X,{className:"ytlr-overlay-message-renderer__title",data:h,idomKey:"title"})}),_.Y(b.subtitle,function(h){return _.R(_.X,{className:"ytlr-overlay-message-renderer__subtitle",data:h,idomKey:"subtitle"})})),_.Y(b.secondaryIcon,function(h){return _.R(_.ry,{className:"ytlr-overlay-message-renderer__secondary-icon",icon:h,idomKey:"secondary-icon"})}))},KY=function(a){switch(a){case "OVERLAY_MESSAGE_STYLE_TOAST":return"ytlr-overlay-message-renderer--toast";
case "OVERLAY_MESSAGE_STYLE_DEFAULT_PAYMENT":return"ytlr-overlay-message-renderer--payment";case "OVERLAY_MESSAGE_STYLE_DISCLAIMER":return"ytlr-overlay-message-renderer--disclaimer";case "OVERLAY_MESSAGE_STYLE_BULLET_SENTENCE":return"ytlr-overlay-message-renderer--bullet-sentence";case "OVERLAY_MESSAGE_STYLE_HIGHLIGHT":return"ytlr-overlay-message-renderer--highlight";case "OVERLAY_MESSAGE_STYLE_NUMBERED_SENTENCE":return"ytlr-overlay-message-renderer--numbered-sentence";default:return""}};_.m().w("sy6e");
var MY=_.p("YtlrOverlayMessageRenderer","Jlxo5e");
_.NY=function(){_.Q.apply(this,arguments);this.template=LY};_.G(_.NY,_.Q);_.NY.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.NY.TAG_NAME="ytlr-overlay-message-renderer";_.J(MY,_.NY);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.JY=function(a){a=void 0===a?!1:a;var b=_.V("PURCHASES_COMPLETE_TITLE"),c=[_.xA({title:_.V("PURCHASES_COMPLETE_TEXT_1")}),_.xA({title:_.V("PURCHASES_COMPLETE_TEXT_2")}),_.xA({title:_.V("PURCHASES_COMPLETE_TEXT_3")}),_.xA({title:_.V("PURCHASES_COMPLETE_TEXT_4")}),_.xA({title:_.V("PURCHASES_COMPLETE_TEXT_FINAL")})];b=_.zA({title:b,content:c});c=[{signalAction:{signal:"CLOSE_POPUP"}},{browseEndpoint:{browseId:"FEmy_youtube",params:"cAU%3D"}}];a&&c.push({signalAction:{signal:"TRANSACTION_FLOW_USER_CANCELED"}});
c={compactLinkRenderer:{title:{simpleText:_.V("PURCHASES_COMPLETE_BUTTON_TEXT")},navigationEndpoint:{commandExecutorCommand:{commands:c}}}};b=_.AA(b,[c]);c=a?{signalAction:{signal:"POPUP_BACK"}}:{signalAction:{signal:"CLOSE_POPUP"}};var d=void 0;a||(d={signalAction:{signal:"TRANSACTION_FLOW_SECOND_SCREEN"}});return _.vA({overlayPanelRenderer:b,jb:c,backButton:{icon:{iconType:a?"ARROW_BACK":"DISMISSAL"},command:c},Fl:d})};_.m().w("sy6i");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy6t");
_.i_=_.p("YtlrOverlaySectionRenderer","PnjIf");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy6z");
var t_=_.p("VozScreenContext","wLNWGd");
var u_,v_,x_,w_;u_=function(){this.h=_.r.get(_.KI);this.g=null};v_=function(a){a=a.h.Va();switch(null===a||void 0===a?void 0:a.type){case "WEB_PAGE_TYPE_BROWSE":return"SCREEN_PAGE_TYPE_BROWSE";case "WEB_PAGE_TYPE_WATCH":return"SCREEN_PAGE_TYPE_WATCH";case "WEB_PAGE_TYPE_SEARCH":return"SCREEN_PAGE_TYPE_SEARCH";default:return"SCREEN_PAGE_TYPE_UNKNOWN"}};
_.y_=function(a){var b=void 0===b?80:b;var c={pageType:v_(a)},d=Array.from(document.querySelectorAll(".ytlr-tile-renderer")),e,f=w_(a);d.forEach(function(g){var h;if(h=0<b){var l=f;l||(l=w_(a));if(l){h=g.getBoundingClientRect();var n=Math.max(0,Math.min(h.right,l.right)-Math.max(h.left,l.left));l=Math.max(0,Math.min(h.bottom,l.bottom)-Math.max(h.top,l.top));h=n&&l?Math.round(n*l/((h.right-h.left)*(h.bottom-h.top))*100):0}else h=-1;h=h<b}if(!h&&(h=x_(g,_.hL))){h=h.props.data;var q,t,w,y,B,z,H,M;n=
h.contentType;l=null===(q=h.metadata)||void 0===q?void 0:q.tileMetadataRenderer;q=null===l||void 0===l?void 0:l.title;l=null===(H=null===(z=null===(B=null===(y=null===(w=null===(t=null===l||void 0===l?void 0:l.lines)||void 0===t?void 0:t[0])||void 0===w?void 0:w.lineRenderer)||void 0===y?void 0:y.items)||void 0===B?void 0:B[0])||void 0===z?void 0:z.lineItemRenderer)||void 0===H?void 0:H.text;t="YOUTUBE_DOC_TYPE_UNKNOWN";"TILE_CONTENT_TYPE_VIDEO"===n?t="YOUTUBE_DOC_TYPE_VIDEO":"TILE_CONTENT_TYPE_PLAYLIST"===
n?t=(null===(M=h.contentId)||void 0===M?0:M.startsWith("RD"))?"YOUTUBE_DOC_TYPE_MIX":"YOUTUBE_DOC_TYPE_PLAYLIST":"TILE_CONTENT_TYPE_CHANNEL"===n&&(t="YOUTUBE_DOC_TYPE_CHANNEL");M={itemDocType:t};h.contentId&&(M.id=h.contentId);q&&(M.title=_.Sx(q));l&&(M.byline=_.Sx(l));h.onSelectCommand&&(M.navigationEndpoint=h.onSelectCommand);c.displayItemShelves||(c.displayItemShelves=[]);t=c.displayItemShelves;if(w=_.IY(g,"yt-virtual-list"))e!==w?(g={displayItems:[]},y=_.IY(w,"ytlr-shelf-renderer"),y=(y=x_(y,
_.IM))?(y=y.props.data.title)?_.Sx(y):void 0:void 0,y&&(g.shelfName=y),t.push(g)):g=t[t.length-1],g.displayItems.push(M),e=w}});return c};x_=function(a,b){a=null===a||void 0===a?void 0:a.ia;return!a||b&&a.constructor!==b?void 0:a};w_=function(a){a.g||(a.g=document.getElementById("container"));if(a.g)return a.g.getBoundingClientRect()};_.z_=_.K(t_,function(){return new u_});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy70");
_.A_=_.p("VozService","f2iALc");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy71");
var B_,C_,D_,E_,F_,G_,I_,J_;B_=[100,200,300,400,500,700,1E3,1500,2E3];C_={};D_=(C_.ACTION_BOX="SEARCH_BUTTON",C_.KEYBOARD="SEARCH_BUTTON",C_.PRE_FETCH="PREFETCH",C_.SUGGESTIONS="CLICKED_SUGGESTION",C_.VOICE="SPEECH_RECOGNITION",C_.KEYBOARD_DEEPLINK="SEARCH_BUTTON",C_.VOICE_DEEPLINK="SPEECH_RECOGNITION",C_);E_={};F_=(E_.ACTION_BOX="KEYBOARD",E_.KEYBOARD="KEYBOARD",E_.PRE_FETCH="KEYBOARD",E_.SUGGESTIONS="KEYBOARD",E_.VOICE="SPEECH",E_.KEYBOARD_DEEPLINK="KEYBOARD",E_.VOICE_DEEPLINK="SPEECH",E_);G_={};
_.H_=(G_.ACTION_BOX="SEARCH_QUERY_SOURCE_SEARCH_BUTTON",G_.KEYBOARD="SEARCH_QUERY_SOURCE_SEARCH_BUTTON",G_.PRE_FETCH="SEARCH_QUERY_SOURCE_UNKNOWN",G_.SUGGESTIONS="SEARCH_QUERY_SOURCE_SUGGESTION",G_.VOICE="SEARCH_QUERY_SOURCE_VOICE",G_.KEYBOARD_DEEPLINK="SEARCH_QUERY_SOURCE_KEYBOARD_DEEPLINK",G_.VOICE_DEEPLINK="SEARCH_QUERY_SOURCE_VOICE_DEEPLINK",G_);I_={};J_=(I_[41]="NONE",I_[35]="COMPLETE_SERVER",I_[0]="COMPLETE_SERVER",I_[25]="COMPLETE_SERVER",I_);
_.K_=_.p("SearchboxStatsService","frqR5");
var L_=function(){this.intervals=B_;this.g=Array.from({length:this.intervals.length+1}).fill(0)};L_.prototype.toString=function(){return this.g.toString()};
var M_;M_=function(){var a=void 0===a?"youtube-lr":a;this.assistedQueryInfo=null;this.h=0;this.A=!1;this.D=this.querySource=this.C=null;this.F=new L_;this.g=0;this.j=_.Jo();this.stats={};this.H=null;this.bedrockPorts=_.ov();this.G=_.fH();this.J={clientName:a,availableSuggestions:[],validationStatus:"VALID",parameterValidationStatus:"VALID_PARAMETERS",zeroPrefixEnabled:!0};this.reset()};
_.N_=function(a){var b=_.Jo()-a.j;a.stats.firstEditTimeMsec||(a.stats.firstEditTimeMsec=b);a.stats.lastEditTimeMsec=b};_.P_=function(a,b,c){a.stats.originalQuery=c;a.querySource=b;a.D=b;a.stats.inputMethods=[F_[b]];a.stats.searchMethod=D_[b];a.B&&(b=a.B())&&(a.stats.availableSuggestions=b.map(_.O_))};M_.prototype.reset=function(){this.stats=Object.assign({},this.J);this.assistedQueryInfo=null;this.h=0;this.A=!1;this.querySource=null;this.F=new L_;this.g=0;this.j=_.Jo()};
_.O_=function(a){var b=a.typeCode;return{index:a.index,source:b?J_[b]:"COMPLETE_SERVER",subtypes:a.subtypeCodes,type:b}};_.Q_=_.J(_.K_,new M_);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var a0;
_.Y_=function(a,b,c,d){a=void 0===a?_.V("SOMETHING_WENT_WRONG"):a;b=void 0===b?_.V("PLEASE_TRY_AGAIN"):b;a=_.zA({title:a,subtitle:b});b={signalAction:{signal:"CLOSE_POPUP"}};var e=[];c&&e.push({compactLinkRenderer:{title:{simpleText:_.V("TRY_AGAIN")},serviceEndpoint:c}});e.push({compactLinkRenderer:{navigationEndpoint:_.JY(),title:{simpleText:_.V("PURCHASE_SECOND_DEVICE_OPTION_TEXT")}}},{compactLinkRenderer:{title:{simpleText:_.V("CANCEL_BUTTON_TEXT")},serviceEndpoint:b}});return _.vA({overlayPanelRenderer:_.AA(a,e),
contextualPanelRenderer:d,jb:b,backButton:{icon:{iconType:"DISMISSAL"},command:b}})};_.Z_=function(a,b,c){var d={content:{loadingRenderer:{}}},e={signalAction:{signal:"CLOSE_POPUP"}};return a?_.wA(d,e,void 0,b):_.vA({overlayPanelRenderer:d,contextualPanelRenderer:c,uniqueId:b})};_.$_=[];a0=function(a){var b=a.B+a.A;a.h[b]||(a.j=a.h[b]={})};_.b0=function(a,b){if(b<a.B){b+=a.A;var c=a.h[b];return c===_.$_?a.h[b]=[]:c}if(a.j)return c=a.j[b],c===_.$_?a.j[b]=[]:c};
_.c0=function(a,b,c){b<a.B?a.h[b+a.A]=c:(a0(a),a.j[b]=c);return a};_.d0=function(a,b){for(var c,d,e=0;e<b.length;e++){var f=b[e],g=_.b0(a,f);null!=g&&(c=f,d=g,_.c0(a,f,void 0))}return c?(_.c0(a,c,d),c):0};
_.e0=function(a,b,c,d,e){a.g=null;b||(b=[]);a.G=void 0;a.A=-1;a.h=b;a:{var f=a.h.length;b=-1;if(f&&(b=f-1,f=a.h[b],!(null===f||"object"!=typeof f||Array.isArray(f)||_.Ck&&f instanceof Uint8Array))){a.B=b-a.A;a.j=f;break a}-1<c?(a.B=Math.max(c,b+1-a.A),a.j=null):a.B=Number.MAX_VALUE}a.H={};if(d)for(c=0;c<d.length;c++)b=d[c],b<a.B?(b+=a.A,a.h[b]=a.h[b]||_.$_):(a0(a),a.j[b]=a.j[b]||_.$_);if(e&&e.length)for(c=0;c<e.length;c++)_.d0(a,e[c])};_.m().w("sy73");
_.f0=_.p("PaymentsService","ZYsjef");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.g0=function(){return{dividerRenderer:{style:"DIVIDER_STYLE_LR_INSET_THIN"}}};_.m().w("sy74");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Rba;Rba=function(a){b2++;for(var b=a.querySelectorAll(".zylon-hidden.zylon-ve, .zylon-hidden .zylon-ve"),c=0;c<b.length;c++)b[c].ao=b2;a=a.querySelectorAll(".zylon-ve");b=[];for(c=0;c<a.length;c++){var d=a[c];d.ao!==b2&&b.push(d)}return b};_.c2=function(a){return Rba(a).map(function(b){var c,d;b=b.ia;return null!==(d=null===(c=null===b||void 0===b?void 0:b.props.data)||void 0===c?void 0:c.trackingParams)&&void 0!==d?d:null===b||void 0===b?void 0:b.visualElement})};_.m().w("sy79");
var b2=0;

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.f2=function(a,b,c){d2[a]||(d2[a]={Dn:b,rp:c},e2||(e2=!0,setTimeout(function(){requestAnimationFrame(function(){setTimeout(function(){e2=!1;for(var d=_.A(Object.entries(d2)),e=d.next();!e.done;e=d.next())e=_.A(e.value),e.next(),e=e.next().value,e.rp(e.Dn());d2={}})})},75)))};_.g2=function(a){return{kb:a}};_.m().w("sy7a");
var e2=!1,d2={};

_.m().l();

}catch(e){_._DumpException(e)}
try{
var o2,q2,v2,u2,p2,Yba,n2,m2,l2,y2,r2,k2,t2,j2,x2,w2,cca,aca,bca,s2,Tba;
_.h2=function(a){var b,c,d,e,f,g,h,l,n,q=a.title,t=a.icon,w=a.dialogMessages,y=null!==(b=a.confirmEndpoint)&&void 0!==b?b:null===(d=null===(c=a.confirmButton)||void 0===c?void 0:c.buttonRenderer)||void 0===d?void 0:d.serviceEndpoint,B=null!==(e=a.confirmLabel)&&void 0!==e?e:null===(g=null===(f=a.confirmButton)||void 0===f?void 0:f.buttonRenderer)||void 0===g?void 0:g.text;a=null!==(h=a.cancelLabel)&&void 0!==h?h:null===(n=null===(l=a.cancelButton)||void 0===l?void 0:l.buttonRenderer)||void 0===n?
void 0:n.text;q=_.Sx(q)||"";w=w&&w[0]?_.Sx(w[0]):void 0;t=_.zA({title:q,subtitle:w,icon:t});w=[];y&&B&&w.push({buttonRenderer:{command:y,text:B}});a&&w.push({buttonRenderer:{command:{signalAction:{signal:"POPUP_BACK"}},text:a}});return _.wA({header:{overlayPanelHeaderRenderer:t},content:{overlayPanelItemListRenderer:{items:w}}})};
_.i2=function(a,b,c,d,e){b=void 0===b?!1:b;c=void 0===c?!1:c;d=void 0===d?Sba:d;e=void 0===e?!1:e;if(a&&"object"===typeof a)if(Array.isArray(a))for(var f=0;f<a.length;f++){var g=d.kj,h=d.tj;_.i2(a[f],b,c,Object.assign(Object.assign({},d),{kj:a[f].shelfRenderer?f:g,tj:Tba(a[f])?f:h}),e)}else{if(b){if(a.gridVideoRenderer){var l=a.gridVideoRenderer,n=c,q=d,t={style:"TILE_STYLE_YTLR_FOUR_WIDE"},w=l.inlinePlaybackEndpoint,y=l.navigationEndpoint,B=l.richThumbnail,z=l.thumbnail,H=l.trackingParams,M=l.title,
aa=l.menu,ya=l.videoId,ua=l.thumbnailOverlays||[];if(_.D("enableZds",!1))for(var wb=0;wb<ua.length;wb++){var rc=ua[wb].thumbnailOverlayTimeStatusRenderer;!rc||"LIVE"!==rc.style&&"UPCOMING"!==rc.style||(rc.icon={iconType:"LIVE"})}var dd=j2(ua,n,l,void 0),Tb=dd.lines,pb=dd.Qj;if(M||Tb.length)t.metadata={tileMetadataRenderer:{title:M,lines:Tb}};if(z||ua.length||B)t.header=k2(z,ua,B&&B.movingThumbnailRenderer&&B.movingThumbnailRenderer.movingThumbnailDetails);var ed=_.PK([M,pb,ua]);ed&&(t.accessibility=
ed);y&&(t.onSelectCommand=y);t.contentType="TILE_CONTENT_TYPE_VIDEO";var Gf=[];w&&Gf.push(l2(w,0,!0));Gf.push.apply(Gf,_.F(m2(q)));n2(t,Gf);H&&(t.trackingParams=H);aa&&(t.menu=aa);ya&&(t.contentId=ya);a.tileRenderer=t;delete a.gridVideoRenderer;o2(a.tileRenderer);return}if(a.gridPlaylistRenderer){a.tileRenderer=p2(a.gridPlaylistRenderer,d);delete a.gridPlaylistRenderer;o2(a.tileRenderer);return}if(a.gridChannelRenderer){a.tileRenderer=q2(a.gridChannelRenderer,d);delete a.gridChannelRenderer;o2(a.tileRenderer);
return}}if(a.compactVideoRenderer){var ec=a.compactVideoRenderer,Xs=c,Na={style:"TILE_STYLE_YTLR_FOUR_WIDE"},ae=ec.inlinePlaybackEndpoint,hR=ec.navigationEndpoint,iR=ec.lengthText,su=ec.richThumbnail,jR=ec.thumbnail,kR=ec.trackingParams,gC=ec.title,lR=ec.menu,mR=ec.videoId,kp=ec.badges,Uba=ec.upcomingEventData,$e=ec.thumbnailOverlays||[],hC=!1;if(kp){a:{for(var iC=0;iC<kp.length;iC++){var jC=kp[iC].liveBadge;if(jC&&jC.label){$e.push(r2(jC.label,"LIVE",_.D("enableZds",!1)?"LIVE":void 0));hC=!0;break a}}hC=
!1}if(Uba)for(var kC=0;kC<kp.length;kC++){var lC=kp[kC].textBadge;lC&&lC.label&&$e.push(r2(lC.label,"UPCOMING",_.D("enableZds",!1)?"LIVE":void 0))}}!iR||hC||s2($e)||$e.push(r2(iR,"DEFAULT"));var nR=j2($e,Xs,void 0,ec),oR=nR.lines,Vba=nR.Qj;if(gC||oR.length)Na.metadata={tileMetadataRenderer:{title:gC,lines:oR}};if(jR||$e.length||su)Na.header=k2(jR,$e,su&&su.movingThumbnailRenderer&&su.movingThumbnailRenderer.movingThumbnailDetails);var pR=_.PK([gC,Vba,$e]);pR&&(Na.accessibility=pR);hR&&(Na.onSelectCommand=
hR);ae&&(Na.onFocusCommand={commandExecutorCommand:{commands:[l2(ae,0,!0,!0)]}});kR&&(Na.trackingParams=kR);lR&&(Na.menu=lR);mR&&(Na.contentId=mR);if(_.D("enableZds",!1))for(var nC=0;nC<$e.length;nC++){var tu=$e[nC].thumbnailOverlayTimeStatusRenderer;!tu||"LIVE"!==tu.style&&"UPCOMING"!==tu.style||(tu.icon={iconType:"LIVE"})}a.tileRenderer=Na;delete a.compactVideoRenderer}else if(a.compactChannelRenderer)a.tileRenderer=q2(a.compactChannelRenderer),delete a.compactChannelRenderer;else if(a.compactPlaylistRenderer)a.tileRenderer=
p2(a.compactPlaylistRenderer),delete a.compactPlaylistRenderer;else if(a.navigationItemRenderer){var nl=a.navigationItemRenderer,ol={style:"TILE_STYLE_YTLR_THREE_WIDE"},qR=nl.navigationEndpoint,oC=nl.title,pC=nl.subtitle,qC=nl.thumbnailRenderer,rC=nl.focusedThumbnailRenderer,rR=nl.trackingParams,sR=qC&&qC.staticAssetThumbnailRenderer&&qC.staticAssetThumbnailRenderer.thumbnail,Wba=rC&&rC.staticAssetThumbnailRenderer&&rC.staticAssetThumbnailRenderer.thumbnail;sR&&(ol.header=k2(sR,void 0,Wba,"TILE_HEADER_STYLE_CIRCULAR"));
if(oC||pC)ol.metadata=t2(oC,[pC]);qR&&(ol.onSelectCommand=qR);rR&&(ol.trackingParams=rR);var tR=_.PK([oC,pC]);tR&&(ol.accessibility=tR);a.tileRenderer=ol;delete a.navigationItemRenderer;o2(a.tileRenderer)}else if(a.tvMusicVideoRenderer){var me=a.tvMusicVideoRenderer,uR=d,af={style:"TILE_STYLE_YTLR_FOUR_WIDE"},pl=me.navigationEndpoint,vR=me.inlinePlaybackEndpoint,wR=me.lengthText,sC=me.primaryText,tC=me.secondaryText,uC=me.tertiaryText,xR=me.thumbnail,uu=me.richThumbnail,yR=me.trackingParams,zR=me.menu,
lp=me.thumbnailOverlays||[];wR&&!s2(lp)&&lp.push(r2(wR));if(xR||lp.length||uu)af.header=k2(xR,lp,uu&&uu.movingThumbnailRenderer&&uu.movingThumbnailRenderer.movingThumbnailDetails);pl&&(af.onSelectCommand=pl);var vu=[];vR&&vu.push(l2(vR));uR&&vu.push.apply(vu,_.F(m2(uR)));n2(af,vu);yR&&(af.trackingParams=yR);if(sC||tC||uC)af.metadata=t2(sC,[tC,uC]);zR&&(af.menu=zR);pl&&pl.watchEndpoint&&pl.watchEndpoint.videoId&&(af.contentId=pl.watchEndpoint.videoId);af.contentType="TILE_CONTENT_TYPE_VIDEO";var AR=
_.PK([sC,tC,uC,lp]);AR&&(af.accessibility=AR);a.tileRenderer=af;delete a.tvMusicVideoRenderer;o2(a.tileRenderer)}else if(a.musicWideAlbumRenderer){var ah=a.musicWideAlbumRenderer,BR=d,vC=!c&&e,bf={style:vC?"TILE_STYLE_YTLR_TWO_WIDE":"TILE_STYLE_YTLR_FOUR_WIDE"},ql=ah.navigationEndpoint,wC=ah.title,xC=ah.bylineText,yC=ah.releaseInfo,zC=ah.backgroundColor,AC=ah.thumbnailRenderer,CR=ah.trackingParams,DR=ah.menu,ER=AC&&AC.musicAlbumPaddedThumbnailRenderer&&AC.musicAlbumPaddedThumbnailRenderer.thumbnail;
if(ER||zC){var Xba=zC?_.Yx(zC):void 0;bf.header=k2(ER,void 0,void 0,vC?"TILE_HEADER_STYLE_SQUARE":"TILE_HEADER_STYLE_PADDED",vC?"":Xba)}ql&&(bf.onSelectCommand=ql);CR&&(bf.trackingParams=CR);if(wC||xC||yC)bf.metadata=t2(wC,[xC,yC]);var FR=_.PK([wC,xC,yC]);FR&&(bf.accessibility=FR);DR&&(bf.menu=DR);ql&&ql.watchPlaylistEndpoint&&ql.watchPlaylistEndpoint.playlistId&&(bf.contentId=ql.watchPlaylistEndpoint.playlistId);bf.contentType="TILE_CONTENT_TYPE_PLAYLIST";var BC=[];BR&&BC.push.apply(BC,_.F(m2(BR)));
n2(bf,BC);a.tileRenderer=bf;delete a.musicWideAlbumRenderer;o2(a.tileRenderer)}else if(a.gridRadioRenderer)a.tileRenderer=u2(a.gridRadioRenderer,d),delete a.gridRadioRenderer,o2(a.tileRenderer);else if(a.compactRadioRenderer)a.tileRenderer=u2(a.compactRadioRenderer),delete a.compactRadioRenderer;else if(a.compactPremiumShowRenderer)a.tileRenderer=v2(a.compactPremiumShowRenderer,!0,d),delete a.compactPremiumShowRenderer,o2(a.tileRenderer);else if(a.compactMovieRenderer)a.tileRenderer=v2(a.compactMovieRenderer,
!1,d),delete a.compactMovieRenderer,o2(a.tileRenderer);else if(a.compactShowRenderer)a.tileRenderer=v2(a.compactShowRenderer,!1,d),delete a.compactShowRenderer,o2(a.tileRenderer);else if(a.gridShowRenderer)a.tileRenderer=Yba(a.gridShowRenderer),delete a.gridShowRenderer,o2(a.tileRenderer);else for(var bh=Object.keys(a),Vf=0;Vf<bh.length;Vf++)if(Zba.has(bh[Vf])){if("header"===bh[Vf]&&null===_.ov()){var DC=void 0,EC=void 0,FC=void 0,GC=void 0,HC=void 0,IC=void 0,JC=void 0,KC=void 0,LC=void 0,MC=void 0,
NC=void 0,OC=void 0,PC=void 0,rl=a[bh[Vf]],wu=null===(NC=null===(OC=null===(PC=null===rl||void 0===rl?void 0:rl.tvSurfaceHeaderRenderer)||void 0===PC?void 0:PC.buttons)||void 0===OC?void 0:OC[1])||void 0===NC?void 0:NC.buttonRenderer;if(wu){var xu=null===(KC=null===(LC=null===(MC=wu.navigationEndpoint)||void 0===MC?void 0:MC.confirmDialogEndpoint)||void 0===LC?void 0:LC.content)||void 0===KC?void 0:KC.confirmDialogRenderer;if(xu){delete wu.navigationEndpoint;var Ki=null===(JC=xu.confirmButton)||void 0===
JC?void 0:JC.buttonRenderer;if(null===(IC=null===Ki||void 0===Ki?void 0:Ki.serviceEndpoint)||void 0===IC?0:IC.clearWatchHistoryEndpoint){var HR=null===(HC=null===Ki||void 0===Ki?void 0:Ki.serviceEndpoint)||void 0===HC?void 0:HC.clearWatchHistoryEndpoint;HR.actions=[_.Px("FEmy_youtube"),_.yA({title:_.V("CLEAR_WATCH_HISTORY_TOAST"),icon:{iconType:"CLEAR_WATCH_HISTORY"}})];Ki.serviceEndpoint={commandExecutorCommand:{commands:[{clearWatchHistoryEndpoint:HR},{signalAction:{signal:"POPUP_BACK"}}]}};xu.icon=
{iconType:"CLEAR_WATCH_HISTORY"}}wu.command=_.h2(xu);var mp=null===(EC=null===(FC=null===(GC=null===rl||void 0===rl?void 0:rl.tvSurfaceHeaderRenderer)||void 0===GC?void 0:GC.buttons)||void 0===FC?void 0:FC[0])||void 0===EC?void 0:EC.toggleButtonRenderer,IR=null===(DC=null===mp||void 0===mp?void 0:mp.defaultServiceEndpoint)||void 0===DC?void 0:DC.pauseWatchHistoryEndpoint;mp&&IR&&(mp.defaultServiceEndpoint={commandExecutorCommand:{commands:[{pauseWatchHistoryEndpoint:IR},_.yA({title:_.V("PAUSE_WATCH_HISTORY")})]}})}}}}else if(!$ba.has(bh[Vf])||
b)c=c||"gridRenderer"===bh[Vf]||"gridContinuation"===bh[Vf],_.i2(a[bh[Vf]],b,c,d,e)}};
o2=function(a){if(a.onSelectCommand&&_.D("enableVideoMenuOnBrowse",!1)&&_.D("addDefaultActionsMenuOption",!1)){if(a.onSelectCommand.browseEndpoint)var b=_.V("VIEW_CHANNEL");else if(a.onSelectCommand.watchEndpoint||a.onSelectCommand.watchPlaylistEndpoint)b=_.V("PLAY");else return;b={menuNavigationItemRenderer:{text:{runs:[{text:b}]},navigationEndpoint:a.onSelectCommand}};var c=a.menu&&a.menu.menuRenderer;c&&c.items?c.items.unshift(b):c={items:[b]};a.menu={menuRenderer:c}}};
q2=function(a,b){var c={style:"TILE_STYLE_YTLR_THREE_WIDE"},d=a.navigationEndpoint,e=a.subscriberCountText,f=a.thumbnail,g=a.title,h=a.trackingParams,l=a.menu;a=a.channelId;f&&(c.header=k2(f,void 0,void 0,"TILE_HEADER_STYLE_CIRCULAR"));if(e||g)c.metadata=t2(g,[e]);d&&(c.onSelectCommand=d);h&&(c.trackingParams=h);if(d=_.PK([g,e]))c.accessibility=d;l&&(c.menu=l);a&&(c.contentId=a);c.contentType="TILE_CONTENT_TYPE_CHANNEL";l=[];b&&l.push.apply(l,_.F(m2(b)));n2(c,l);return c};
v2=function(a,b,c){var d,e={style:"TILE_STYLE_YTLR_FOUR_WIDE"},f=a.badges,g=a.bottomStandaloneBadges,h=a.lengthText,l=a.navigationEndpoint,n=a.thumbnail,q=a.thumbnailRenderer,t=a.title,w=a.topMetadataItems,y=a.trackingParams,B=a.menu,z=a.videoId;a=a.thumbnailOverlays||[];var H=a.filter(function(M){return!!M.thumbnailOverlayBottomPanelRenderer}).map(function(M){return M.thumbnailOverlayBottomPanelRenderer})[0];_.D("enableZds",!1)&&H&&H.text?a.push(r2(H.text,"DEFAULT","PLAYLISTS")):h&&!s2(a)&&a.push(r2(h));
if((h=null!==n&&void 0!==n?n:null===(d=null===q||void 0===q?void 0:q.showCustomThumbnailRenderer)||void 0===d?void 0:d.thumbnail)||a.length)e.header=k2(h,a);w=w&&w.length&&w[0];if(t||w)if(e.metadata=t2(t,[w||void 0]),h=e.metadata.tileMetadataRenderer.lines,b){if(g=f&&f.length&&f[0].metadataBadgeRenderer)f={lineItemRenderer:{badge:{metadataBadgeRenderer:g}}},"BADGE_STYLE_TYPE_YPC"===g.style?(1===h.length&&h[0].lineRenderer&&(h[0].lineRenderer.wrap=!1),h.push({lineRenderer:{items:[f],wrap:!1}})):h&&
h[0]&&h[0].lineRenderer&&h[0].lineRenderer.items&&h[0].lineRenderer.items.unshift(f)}else f&&(b=w2(f,2)[0])&&h[0]&&h[0].lineRenderer&&h[0].lineRenderer.items&&h[0].lineRenderer.items.unshift({lineItemRenderer:b}),(f=x2(f,"BADGE_STYLE_TYPE_YPC")[0])||(f=aca(g)),f&&(1===h.length&&h[0].lineRenderer&&(h[0].lineRenderer.wrap=!1),h.push({lineRenderer:{items:[{lineItemRenderer:f}],wrap:!1}}));if(t=_.PK([t,w||"",a]))e.accessibility=t;l&&(e.onSelectCommand=l);y&&(e.trackingParams=y);B&&(e.menu=B);z&&(e.contentId=
z);n2(e,m2(c));return e};
u2=function(a,b){var c={style:"TILE_STYLE_YTLR_FOUR_WIDE"},d=a.navigationEndpoint,e=a.inlinePlaybackEndpoint,f=a.title,g=a.shortBylineText,h=a.longBylineText,l=a.thumbnail,n=a.thumbnailText,q=a.trackingParams,t=a.menu;a=a.playlistId;var w=[];_.D("enableZds",!1)&&n?w.push(r2({runs:[{text:_.V("MIX")}]},"DEFAULT","MIX")):w.push(y2(void 0,"MIX"));c.header=k2(l,w);g=h===g?[h]:[g,h];if(f||g)c.metadata=t2(f,g);d&&(c.onSelectCommand=d);d=[];e&&d.push(l2(e));b&&d.push.apply(d,_.F(m2(b)));n2(c,d);q&&(c.trackingParams=
q);if(b=_.PK([f].concat(_.F(g),[w])))c.accessibility=b;t&&(c.menu=t);a&&(c.contentId=a);c.contentType="TILE_CONTENT_TYPE_PLAYLIST";return c};
p2=function(a,b){var c={style:"TILE_STYLE_YTLR_FOUR_WIDE"},d=a.inlinePlaybackEndpoint,e=a.navigationEndpoint,f=a.publishedTimeText,g=a.shortBylineText,h=a.thumbnailRenderer,l=a.title,n=a.trackingParams,q=a.videoCountText,t=a.menu,w=a.playlistId;q=a.thumbnailText||q;a=a.thumbnail||h&&(h.playlistVideoThumbnailRenderer&&h.playlistVideoThumbnailRenderer.thumbnail||h.playlistCustomThumbnailRenderer&&h.playlistCustomThumbnailRenderer.thumbnail);h=[];_.D("enableZds",!1)&&q?h.push(r2(q,"DEFAULT","PLAYLISTS")):
q&&h.push(y2(q));c.header=k2(a,h);if(l||g||f)c.metadata=t2(l,[g,f]);e&&(c.onSelectCommand=e);e=[];d&&e.push(l2(d));b&&e.push.apply(e,_.F(m2(b)));n2(c,e);n&&(c.trackingParams=n);if(b=_.PK([l,g,f,q]))c.accessibility=b;t&&(c.menu=t);w&&(c.contentId=w);c.contentType="TILE_CONTENT_TYPE_PLAYLIST";return c};
Yba=function(a){var b={style:"TILE_STYLE_YTLR_FOUR_WIDE"},c=a.title,d=a.shortBylineText,e=a.navigationEndpoint,f=a.thumbnailRenderer,g=a.trackingParams,h=a.bottomStandaloneBadge;a=a.thumbnailOverlays||[];var l=a.filter(function(n){return!!n.thumbnailOverlayBottomPanelRenderer}).map(function(n){return n.thumbnailOverlayBottomPanelRenderer})[0];_.D("enableZds",!1)&&l&&l.text&&a.push(r2(l.text,"DEFAULT","PLAYLISTS"));f&&f.showCustomThumbnailRenderer&&f.showCustomThumbnailRenderer.thumbnail&&(b.header=
k2(f.showCustomThumbnailRenderer.thumbnail,a||[]));c&&(b.metadata=t2(c,[d]),h&&(d=_.jL(h))&&b.metadata&&b.metadata.tileMetadataRenderer&&b.metadata.tileMetadataRenderer.lines&&(f=b.metadata.tileMetadataRenderer.lines,1===f.length&&f[0].lineRenderer&&(f[0].lineRenderer.wrap=!1),f.push({lineRenderer:{items:[{lineItemRenderer:{badge:{metadataBadgeRenderer:d}}}],wrap:!1}})));e&&(b.onSelectCommand=e,e.watchEndpoint?b.contentId=e.watchEndpoint.playlistId:e.browseEndpoint&&(b.contentId=e.browseEndpoint.browseId));
if(c=_.PK([c]))b.accessibility=c;g&&(b.trackingParams=g);return b};n2=function(a,b){0!==b.length&&(a.onFocusCommand={commandExecutorCommand:{commands:b}})};m2=function(a){var b=a.kj,c=a.tj;return a.Lk&&(0===b&&1===c||1===b&&0===c)?[{showHintCommand:{shouldShowHint:!0}}]:[]};l2=function(a,b,c,d){return a.startInlinePlaybackCommand?a:{startInlinePlaybackCommand:{blockAdoption:void 0===c?!1:c,delayMs:void 0===b?1E3:b,muted:void 0===d?!1:d,playbackEndpoint:a}}};
y2=function(a,b){var c={};a&&(c.text=a);b&&(c.icon={iconType:b});return{thumbnailOverlayBottomPanelRenderer:c}};r2=function(a,b,c){a={text:a,style:void 0===b?"DEFAULT":b};c&&(a.icon={iconType:c});return{thumbnailOverlayTimeStatusRenderer:a}};k2=function(a,b,c,d,e){d={style:void 0===d?"TILE_HEADER_STYLE_RECTANGULAR":d};e&&(d.backgroundColor=e);a&&(d.thumbnail=a);c&&(d.movingThumbnail=c);b&&b.length&&(d.thumbnailOverlays=b);return{tileHeaderRenderer:d}};
t2=function(a,b){var c={};a&&(c.title=a);if(b){a=[];for(var d=0;d<b.length;d++)b[d]&&a.push({lineRenderer:{items:[{lineItemRenderer:{text:b[d]}}],wrap:!1}});1===a.length&&(a[0].lineRenderer.wrap=!0);c.lines=a}return{tileMetadataRenderer:c}};
j2=function(a,b,c,d){function e(y){n.push({lineRenderer:{items:[],wrap:void 0===y?!1:y}})}function f(y){for(var B=0;B<y.length;B++){var z=y[B];n[n.length-1].lineRenderer.items.push({lineItemRenderer:z});q+=_.QK(z)}}function g(){if(!n.length)return!0;var y=n[n.length-1].lineRenderer.items;y=y[y.length-1];return!y||y.lineItemRenderer.badge?!0:!1}function h(y){for(var B,z,H,M=0,aa=0;aa<y.length;aa++)M+=.5*_.Sx({simpleText:null!==(H=null===(z=null===(B=y[aa].badge)||void 0===B?void 0:B.metadataBadgeRenderer)||
void 0===z?void 0:z.label)&&void 0!==H?H:""}).length+.25;return M}b=void 0===b?!1:b;var l,n=[],q="",t=bca(a);a=c?x2(c.badges):d?w2(d.badges||[],t):[];var w=c||d||{};c={text:{simpleText:"\u2022"}};_.D("enableZds",!1)?(d=w.topStandaloneBadge&&w.topStandaloneBadge.standaloneCollectionBadgeRenderer,e(),f([{text:w.shortBylineText}]),d&&f([c,{text:{simpleText:d.iconText}}]),e(),f(a),d&&(a={style:"BADGE_STYLE_TYPE_SIMPLE",label:_.Sx(d.label)},f([{badge:{metadataBadgeRenderer:a}}])),1===t&&w.upcomingEventData?
f([{text:w.upcomingEventData.upcomingEventText}]):w.shortViewCountText&&f([{text:w.shortViewCountText}]),2===t&&w.publishedTimeText&&(g()||f([c]),f([{text:w.publishedTimeText}]))):w.topStandaloneBadge&&w.topStandaloneBadge.standaloneCollectionBadgeRenderer?(t=w.topStandaloneBadge.standaloneCollectionBadgeRenderer,e(),w.shortBylineText&&d&&(f([{text:w.shortBylineText}]),e()),f(a),w={simpleText:t.iconText},f([{text:w}]),t=null!==(l=t.label)&&void 0!==l?l:{simpleText:""},21<h(a)+(.5*_.Sx(w).length+.25)+
.75+(.5*_.Sx(t).length+.25)?e():f([c]),f([{text:t}])):(e(),b?(f(a),w.shortBylineText&&f([{text:w.shortBylineText}])):(w.shortBylineText&&(f([{text:w.shortBylineText}]),2===t&&e()),f(a)),1===t&&w.upcomingEventData&&w.upcomingEventData.upcomingEventText?(a=w.upcomingEventData.upcomingEventText,21>=.5*_.Sx(w.shortBylineText).length+.25+(.5*_.Sx(a).length+.25)+.75?f([c]):e(),f([{text:a}])):w.shortViewCountText&&((0===t||b)&&w.shortBylineText&&f([c]),f([{text:w.shortViewCountText}])),2===t&&w.publishedTimeText&&
(g()||f([c]),f([{text:w.publishedTimeText}])));1===n.length&&n[0].lineRenderer&&(n[0].lineRenderer.wrap=!0);return{lines:n,Qj:q}};x2=function(a,b){a=a||[];a=a.filter(function(c){return!!c.metadataBadgeRenderer});b&&(a=a.filter(function(c){return c.metadataBadgeRenderer.style===b}));return a.map(function(c){return{badge:{metadataBadgeRenderer:c.metadataBadgeRenderer}}})};
w2=function(a,b){return a.filter(function(c){return!!c.textBadge&&!!c.textBadge.label&&1!==b}).map(function(c){c=c.textBadge;return{badge:{metadataBadgeRenderer:{style:"BADGE_STYLE_TYPE_SIMPLE",label:c.label.simpleText?c.label.simpleText:_.Sx(c.label)}}}})};cca=function(a){if(a)return(a=a.find(function(b){return!!b.thumbnailOverlayTimeStatusRenderer}))&&a.thumbnailOverlayTimeStatusRenderer&&a.thumbnailOverlayTimeStatusRenderer.style};
aca=function(a){var b,c;a=a&&(null===(b=a[0])||void 0===b?void 0:b.standaloneYpcBadgeRenderer);if((null===a||void 0===a?0:a.label)&&(null===(c=null===a||void 0===a?void 0:a.style)||void 0===c?0:c.badgeStyle)){var d="STYLE_PURCHASED"===a.style.badgeStyle?"BADGE_STYLE_TYPE_SIMPLE":"BADGE_STYLE_TYPE_YPC";return{badge:{metadataBadgeRenderer:{label:a.label.simpleText||_.Sx(a.label),style:d}}}}};bca=function(a){a=cca(a);return"LIVE"===a?0:"UPCOMING"===a?1:2};
s2=function(a){for(var b=0;b<a.length;++b){var c=a[b];if(c&&c.thumbnailOverlayTimeStatusRenderer)return!0}return!1};Tba=function(a){return!!(a.gridVideoRenderer||a.gridPlaylistRenderer||a.gridChannelRenderer||a.compactVideoRenderer||a.compactChannelRenderer||a.compactPlaylistRenderer||a.navigationItemRenderer||a.tvMusicVideoRenderer||a.musicWideAlbumRenderer||a.gridRadioRenderer||a.compactRadioRenderer||a.compactPremiumShowRenderer||a.compactMovieRenderer||a.compactShowRenderer)};
_.z2=function(a){var b,c,d,e,f,g;if(!(g=null!==(f=null===(e=null===(d=null===(c=null===(b=null===a||void 0===a?void 0:a.contents)||void 0===b?void 0:b.sectionListRenderer)||void 0===c?void 0:c.contents)||void 0===d?void 0:d[0])||void 0===e?void 0:e.itemSectionRenderer)&&void 0!==f?f:null)){var h,l;g=null!==(l=null===(h=null===a||void 0===a?void 0:a.continuationContents)||void 0===h?void 0:h.itemSectionContinuation)&&void 0!==l?l:null}d=g||{};b={};d&&(c=d.contents,d=d.continuations,c&&(b.items=c),
a.trackingParams&&(b.trackingParams=a.trackingParams),d&&(b.continuations=d));_.i2(b,!1,!1);return b};_.m().w("sy7b");
var A2;A2={};_.dca=(A2.I18N_LANGUAGE=_.V("CHOOSE_YOUR_LANGUAGE"),A2.I18N_REGION=_.V("CHOOSE_YOUR_LOCATION"),A2);
var Sba={Lk:!1,kj:0,tj:0},Zba=new Set("continuations endpoint header responseContext tileRenderer title trackingParams".split(" ")),$ba=new Set(["gridChannelRenderer","gridPlaylistRenderer","gridVideoRenderer"]);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.B2=function(a,b){return{openClientOverlayAction:{type:"CLIENT_OVERLAY_TYPE_VOICE",updateAction:!1,context:a+","+(b||_.pd().clientLanguage||"en")}}};_.m().w("sy7c");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy7l");
var Raa=_.p("HistoryPausedStateService","vFAVQc");
var b1=function(){var a=this;this.ra=_.r.get(_.kA);this.g=_.L.R();this.searchHistoryPaused=this.watchHistoryPaused=!1;_.N(this.g,new _.O("PAUSED_WATCH_HISTORY"),this,function(){a.watchHistoryPaused=!0});_.N(this.g,new _.O("RESUMED_WATCH_HISTORY"),this,function(){a.watchHistoryPaused=!1})};b1.prototype.onError=function(a){_.C(a);return _.yr({})};
_.E.Object.defineProperties(b1.prototype,{isWatchHistoryPaused:{configurable:!0,enumerable:!0,get:function(){return this.watchHistoryPaused}},isSearchHistoryPaused:{configurable:!0,enumerable:!0,get:function(){return this.searchHistoryPaused}}});_.c1=_.K(Raa,function(){return new b1});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Ada,Bda;_.x3=function(a,b){var c=_.GG;return c?c.getOption("captions",a,b):null};Ada=function(){var a={};Object.entries(y3).forEach(function(b){a[b[1]]=b[0]});return a};Bda=/^#(?:[0-9a-f]{3}){1,2}$/i;_.m().w("sy84");
var z3,A3,B3,C3,D3,E3,Dda,F3,Eda,G3,H3,I3,Fda,J3,L3,N3,O3,y3;z3="#080808 #ff0 #0f0 #0ff #00f #f0f #f00 #fff".split(" ");A3=[0,.25,.5,.75,1];B3={};_.Cda=(B3["#fff"]="LENS_WHITE",B3["#ff0"]="LENS_YELLOW",B3["#0f0"]="LENS_GREEN",B3["#0ff"]="LENS_CYAN",B3["#00f"]="LENS_BLUE",B3["#f0f"]="LENS_MAGENTA",B3["#f00"]="LENS_RED",B3["#080808"]="LENS_BLACK",B3);C3={};
D3=(C3["#fff"]=_.V("CAPTIONS_WHITE"),C3["#ff0"]=_.V("CAPTIONS_YELLOW"),C3["#0f0"]=_.V("CAPTIONS_GREEN"),C3["#0ff"]=_.V("CAPTIONS_CYAN"),C3["#00f"]=_.V("CAPTIONS_BLUE"),C3["#f0f"]=_.V("CAPTIONS_MAGENTA"),C3["#f00"]=_.V("CAPTIONS_RED"),C3["#080808"]=_.V("CAPTIONS_BLACK"),C3);E3={};
Dda=(E3[0]=_.V("CAPTIONS_DEFAULT"),E3[1]=_.V("CAPTIONS_MONO_SERIF"),E3[2]=_.V("CAPTIONS_PROP_SERIF"),E3[3]=_.V("CAPTIONS_MONO_SANS"),E3[4]=_.V("CAPTIONS_PROP_SANS"),E3[5]=_.V("CAPTIONS_CASUAL"),E3[6]=_.V("CAPTIONS_CURSIVE"),E3[7]=_.V("CAPTIONS_SMALL_CAPS"),E3);F3={};Eda=(F3[0]=_.V("CAPTIONS_NONE"),F3[1]=_.V("CAPTIONS_RAISED"),F3[2]=_.V("CAPTIONS_DEPRESSED"),F3[3]=_.V("CAPTIONS_UNIFORM"),F3[4]=_.V("CAPTIONS_DROP_SHADOW"),F3);G3={};
H3=(G3[0]="0%",G3[.25]="25%",G3[.5]="50%",G3[.75]="75%",G3[1]="100%",G3);I3={};Fda=(I3[-2]="50%",I3[-1]="75%",I3[0]="100%",I3[2]="200%",I3[4]="300%",I3);J3={};
_.K3=(J3.fontFamily=_.V("CAPTIONS_FONT_FAMILY"),J3.color=_.V("CAPTIONS_FONT_COLOR"),J3.background=_.V("CAPTIONS_BACKGROUND_COLOR"),J3.windowColor=_.V("CAPTIONS_WINDOW_COLOR"),J3.charEdgeStyle=_.V("CAPTIONS_EDGE_STYLE"),J3.textOpacity=_.V("CAPTIONS_TEXT_OPACITY"),J3.backgroundOpacity=_.V("CAPTIONS_BACKGROUND_OPACITY"),J3.windowOpacity=_.V("CAPTIONS_WINDOW_OPACITY"),J3.fontSizeIncrement=_.V("CAPTIONS_FONT_SIZE"),J3);L3={};
_.M3=(L3.fontFamily=Dda,L3.color=D3,L3.background=D3,L3.windowColor=D3,L3.charEdgeStyle=Eda,L3.textOpacity=H3,L3.backgroundOpacity=H3,L3.windowOpacity=H3,L3.fontSizeIncrement=Fda,L3);N3={};_.Gda=(N3.fontFamily=[5,6,3,1,4,2,7],N3.color="#fff #ff0 #0f0 #0ff #00f #f0f #f00 #080808".split(" "),N3.background=z3,N3.windowColor=z3,N3.charEdgeStyle=[0,1,2,3,4],N3.textOpacity=[.5,.75,1],N3.backgroundOpacity=A3,N3.windowOpacity=A3,N3.fontSizeIncrement=[-2,-1,0,2,4],N3);O3={};
y3=(O3.CAPTION_FONT_FAMILY="fontFamily",O3.CAPTION_TEXT_COLOR="color",O3.CAPTION_BACKGROUND_COLOR="background",O3.CAPTION_WINDOW_COLOR="windowColor",O3.CAPTION_EDGE_TYPE="charEdgeStyle",O3.CAPTION_TEXT_OPACITY="textOpacity",O3.CAPTION_BACKGROUND_OPACITY="backgroundOpacity",O3.CAPTION_WINDOW_OPACITY="windowOpacity",O3.CAPTION_TEXT_SIZE="fontSizeIncrement",O3);_.Hda=Ada();
var Ida=_.p("CaptionsService","mFcB5d");
var T3,P3,S3,Y3,W3,X3,R3,Q3;
T3=function(){var a=this;this.h=new _.Ct(!1);this.j=_.L.R();this.g=new _.Ct(P3());_.EG().then(function(b){b.addEventListener("onCaptionsTrackListChanged",function(){a.h.set(!0)});b.addEventListener("captionschanged",function(){a.h.set(!0)});b.addEventListener("videodatachange",function(c){"dataloaded"===c&&a.g.set(P3())});_.N(a.j,new _.O("selectSubtitlesTrackCommand"),a,function(c){(c=c.command.selectSubtitlesTrackCommand.subtitlesTrackMetadata)?(Q3(a,c),R3(!c.skipPreview)):(Q3(a,{}),R3(!1))});_.N(a.j,
new _.O("setClientSettingEndpoint"),a,function(c){var d=c.command.setClientSettingEndpoint.settingDatas;if(d&&d.length){c=d[0];var e=c.clientSettingEnum&&c.clientSettingEnum.item;switch(e){case "CAPTION_FONT_FAMILY":case "CAPTION_TEXT_COLOR":case "CAPTION_BACKGROUND_COLOR":case "CAPTION_WINDOW_COLOR":case "CAPTION_EDGE_TYPE":case "CAPTION_TEXT_OPACITY":case "CAPTION_BACKGROUND_OPACITY":case "CAPTION_WINDOW_OPACITY":case "CAPTION_TEXT_SIZE":c=c.stringValue||c.intValue;e=y3[e];void 0!==e&&void 0!==
c&&(d={},a.updateSubtitlesUserSettings((d[e]=c,d)));break;case "CAPTION_STYLE_RESET":a.resetSubtitlesUserSettings();break;case "CAPTION_STYLE_VIDEO_OVERRIDE":if(e=(e=d[1])&&e.clientSettingEnum&&e.clientSettingEnum.item)c=c.boolValue,e=y3[e],void 0!==e&&void 0!==c&&(d={},a.updateSubtitlesUserSettings((d[e+"Override"]=c,d)));break;case "CAPTION_SHOW_SAMPLE_SUBTITLE":_.v.isEmpty(_.x3("track"))||R3(!0);break;case "CAPTION_HIDE_SAMPLE_SUBTITLE":R3(!1)}}});_.N(a.j,new _.O("mdx-watch:setSubtitlesStyle"),
a,function(c){c.nm?S3(a,c.nm):a.resetSubtitlesUserSettings()})})};P3=function(){var a,b=_.NI();b=!(null===(a=null===b||void 0===b?void 0:b.captions)||void 0===a||!a.playerCaptionsRenderer);var c=(c=_.GG)&&c.getOptions("captions");c=!(!c||!c.length);return b||c};_.U3=function(a){a.g.get()&&(a=_.GG)&&a.loadModule("captions")};_.V3=function(){return _.x3("tracklist",{includeAsr:1})};
S3=function(a,b){if(b){var c={},d=a.getSubtitlesUserSettings();null!=b.color&&(c.color=W3(b.color,d.color),c.colorOverride=!0);null!=b.background&&(c.background=W3(b.background,d.background),c.backgroundOverride=!0);null!=b.windowColor&&(c.windowColor=W3(b.windowColor,d.windowColor),c.windowColorOverride=!0);null!=b.textOpacity&&(c.textOpacity=X3(b.textOpacity,d.textOpacity),c.textOpacityOverride=!0);null!=b.backgroundOpacity&&(c.backgroundOpacity=X3(b.backgroundOpacity,d.backgroundOpacity),c.backgroundOpacityOverride=
!0);null!=b.windowOpacity&&(c.windowOpacity=X3(b.windowOpacity,d.windowOpacity),c.windowOpacityOverride=!0);if(null!=b.fontSizeIncrement){var e=b.fontSizeIncrement;c.fontSizeIncrement="number"!==typeof e||isNaN(e)?d.fontSizeIncrement:Math.min(Math.max(e,-2),4);c.fontSizeIncrementOverride=!0}null!=b.charEdgeStyle&&(c.charEdgeStyle=Y3(b.charEdgeStyle,d.charEdgeStyle,_.cC),c.charEdgeStyleOverride=!0);if(null!=b.fontFamily||null!=b.fontFamilyOption)c.fontFamily=Y3(b.fontFamily||b.fontFamilyOption,d.fontFamily,
_.aC),c.fontFamilyOverride=!0;null!=b.fontStyle&&(c.fontStyle=Y3(b.fontStyle,d.fontStyle,_.dC),c.fontStyleOverride=!0);a.updateSubtitlesUserSettings(c)}};Y3=function(a,b,c){var d;if("number"===typeof a)return a;"string"===typeof a&&(a=c[a])&&(d=a);!d&&b&&(d=b);return d};W3=function(a,b){return a&&"string"===typeof a&&Bda.test(a)?a:b};X3=function(a,b){return"number"!==typeof a||isNaN(a)?b:Math.max(0,Math.min(1,a))};R3=function(a){var b=_.GG;b&&b.setOption("captions","sampleSubtitles",a)};
T3.prototype.getSubtitlesUserSettings=function(){var a=_.GG;return a?a.getSubtitlesUserSettings()||{}:{}};T3.prototype.resetSubtitlesUserSettings=function(){var a=_.GG;return a?(a.resetSubtitlesUserSettings(),!0):!1};T3.prototype.updateSubtitlesUserSettings=function(a){var b=_.GG;return b?(b.updateSubtitlesUserSettings(a),!0):!1};Q3=function(a,b){b&&b.style&&S3(a,b.style);(a=_.GG)&&a.setOption("captions","track",b)};_.Z3=function(a,b){return a.h.g("value-set",b)};_.$3=_.K(Ida,function(){return new T3});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy88");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy8c");
var gea=_.p("AdSeekService","mcz3Cc");
var G4,F4,I4,H4;G4=function(){var a=this;this.g=_.r.get(_.oJ);this.h=new _.Ct(_.ZI);this.j=function(){};_.jJ(this.g,function(b){F4(a,b)});F4(this,this.g.na)};F4=function(a,b){a.j();b?(b=0,a.g.isAdPlaying&&0!==H4()&&0!==a.g.progress.duration&&(b=a.g.progress.current),I4(a,Object.assign(Object.assign({},a.g.progress),{current:b,duration:H4()})),a.j=_.dJ(a.g,function(c){I4(a,Object.assign(Object.assign({},c),{duration:H4()}))})):(I4(a,_.ZI),a.j=function(){})};
_.J4=function(a,b){return a.h.g("value-changed",b)};I4=function(a,b){_.D("enableZylonAdAttribution",!0)?b.current>b.duration?a.h.set(Object.assign(Object.assign({},b),{current:0,duration:1})):a.h.set(b):a.h.set(b)};H4=function(){var a=_.GG,b=a&&a.getDuration(2)||0;return a&&2===a.getPresentingPlayerType()?_.WI(b):0};_.E.Object.defineProperties(G4.prototype,{progress:{configurable:!0,enumerable:!0,get:function(){return this.h.get()}}});_.K4=_.K(gea,function(){return new G4});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Uea=function(a){a:switch(this.props.style||""){case "round":var b="ytlr-icon-button--round";break a;default:b=""}return _.R("host",{hybridNavFocusable:!0,className:b,"aria-label":a.ariaLabel},_.R(_.ry,{className:"ytlr-icon-button__icon",icon:a.icon}))};_.m().w("sy8u");
_.i5=function(){_.Q.apply(this,arguments);this.template=Uea};_.G(_.i5,_.Q);_.i5.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.i5.prototype.onSelect=function(a){this.props.command&&(_.U(this,"innertube-command",this.props.command),_.P(a))};_.i5.TAG_NAME="ytlr-icon-button";

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("hSlMif");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var p5;_.j5=function(a){var b;return null===(b=a.contents)||void 0===b?void 0:b.singleColumnWatchNextResults};_.k5=function(a){var b;a=_.j5(a);return null===(b=null===a||void 0===a?void 0:a.autoplay)||void 0===b?void 0:b.autoplay};_.l5=function(a){var b;a=_.k5(a);if(null===(b=null===a||void 0===a?void 0:a.sets)||void 0===b?0:b.length)return a.sets.find(function(c){return"NORMAL"===c.mode})};_.m5=function(a){a=_.l5(a);return null===a||void 0===a?void 0:a.nextVideoRenderer};
_.n5=function(a){a=_.l5(a);return null===a||void 0===a?void 0:a.previousVideoRenderer};_.o5=function(a){var b;return null===(b=a.transportControls)||void 0===b?void 0:b.transportControlsRenderer};p5=function(){var a={};this.j=(a.playbackImminent=new Set,a.watchNextResponseChanged=new Set,a.syncedPlaybackChanged=new Set,a.playbackAbandoned=new Set,a.autoplayCountdownStarted=new Set,a.autoplayCountdownDismissed=new Set,a)};p5.prototype.g=function(a,b){var c=this.j[a];c.add(b);return function(){c.delete(b)}};
_.q5=function(a,b,c){a=_.A(a.j[b]);for(b=a.next();!b.done;b=a.next()){b=b.value;try{b(c)}catch(d){_.C(d)}}};_.m().w("sy8w");
var Vea=_.p("KabukiMdxPlaybackLifecycleService","BR92Cc");
var r5=function(){p5.apply(this,arguments);this.watchNextResponse={};this.A=_.r.get(_.KI)};_.G(r5,p5);r5.prototype.h=function(){var a=this.A.Va();return!(!a||"WEB_PAGE_TYPE_WATCH"!==a.type)};r5.prototype.hasNext=function(){return!!_.m5(this.watchNextResponse)};r5.prototype.hasPrevious=function(){return!!_.n5(this.watchNextResponse)};_.s5=_.K(Vea,function(){return new r5});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy8x");
var Wea=_.p("PlayerStyleStore","ugx4Kd");
var Xea,v5,Yea,Zea,$ea,afa,t5;Xea=Object.freeze({left:0,top:0,width:window.innerWidth,height:window.innerHeight});v5=function(){var a=this;this.g={};this.B=document.getElementById("container");this.h=new _.Ct(this.j);window.addEventListener("resize",function(){var b=a.lb.width===a.ac.width&&a.lb.height===a.ac.height,c=t5(a);b?_.u5(a,{lb:a.A,ac:c}):_.u5(a,{ac:c})})};
_.u5=function(a,b){var c=Yea(a,b.Ka),d=Zea(a,b.lb),e=$ea(a,b.ac),f=afa(a,b.transition),g=b.Ld,h=b.fd;c=Object.assign(Object.assign(Object.assign(Object.assign({},c),d),e),f);g&&(c.Ld=g);h&&(c.fd=h);_.v.equals(c,{})||a.h.set(Object.assign(Object.assign({},a.styles),c));return function(){if(b.Ka){for(var l=b.Ka,n=!1,q=0,t=l.length;q<t;++q){var w=l[q];a.g[w]--;a.g[w]||(delete a.g[w],n=!0)}n&&(l={Ka:Object.keys(a.g)},a.h.set(Object.assign(Object.assign({},a.styles),l)))}}};
Yea=function(a,b){if(!b)return{};for(var c=!1,d=0,e=b.length;d<e;++d){var f=b[d];a.g[f]?a.g[f]++:(a.g[f]=1,c=!0)}return c?{Ka:Object.keys(a.g)}:{}};Zea=function(a,b){return!b||_.v.equals(a.lb,b)?{}:{lb:b}};$ea=function(a,b){return!b||_.v.equals(a.ac,b)?{}:{ac:b}};afa=function(a,b){void 0===b?b={}:(a=a.transition,b=b&&a&&!_.v.equals(b,a)||b!==a?{transition:b}:{});return b};t5=function(a){a=a.B&&a.B.getBoundingClientRect()||Xea;return{left:a.left,top:a.top,height:a.height,width:a.width}};
_.E.Object.defineProperties(v5.prototype,{styles:{configurable:!0,enumerable:!0,get:function(){return this.h.get()}},Ka:{configurable:!0,enumerable:!0,get:function(){return this.styles.Ka}},lb:{configurable:!0,enumerable:!0,get:function(){return this.styles.lb}},ac:{configurable:!0,enumerable:!0,get:function(){return this.styles.ac}},transition:{configurable:!0,enumerable:!0,get:function(){return this.styles.transition}},Ld:{configurable:!0,enumerable:!0,get:function(){return this.styles.Ld}},fd:{configurable:!0,
enumerable:!0,get:function(){return this.styles.fd}},A:{configurable:!0,enumerable:!0,get:function(){return t5(this)}},j:{configurable:!0,enumerable:!0,get:function(){var a=t5(this);return{Ka:[],lb:a,ac:a,transition:null,Ld:"contain",fd:"default"}}}});_.w5=_.K(Wea,function(){return new v5});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.y5=function(){var a={};a=_.V("FEEDBACK_SHARE_MORE",(a["{url}"]="|youtube.com/tv/feedback|",a)).split("|").map(function(b){return b.trim()});return{qrCodeRenderer:{qrCodeImage:{thumbnails:[{url:_.x5,width:490,height:490}]},labels:[{simpleText:a[0]},{runs:[{text:a[1],textColor:4293651435}]},{simpleText:a[2]}]}}};
_.z5=function(){var a={};a=_.V("FEEDBACK_DISCLAIMER",(a["{report_policy}"]="youtube.com/reportingtool/legal",a["{privacy_policy}"]="youtube.com/privacy",a["{tos}"]="youtube.com/terms",a));return{overlayMessageRenderer:_.xA({title:a,style:"OVERLAY_MESSAGE_STYLE_DISCLAIMER"})}};
_.A5=function(a,b){b=void 0===b?!1:b;var c,d,e;a={title:{simpleText:_.V("FEEDBACK_SUBMIT")},serviceEndpoint:{commandExecutorCommand:{commands:[{signalAction:{signal:"CLOSE_POPUP"}},{signalAction:{signal:"SEND_FEEDBACK",targetId:""+a}}]}}};b&&(null===(e=null===(d=null===(c=a.serviceEndpoint)||void 0===c?void 0:c.commandExecutorCommand)||void 0===d?void 0:d.commands)||void 0===e?void 0:e.push({signalAction:{signal:"HISTORY_BACK"}}));return{compactLinkRenderer:a}};_.m().w("sy8y");
var H5;_.B5={seq:1,children:[{seq:2},{seq:3},{seq:4},{seq:5},{seq:6},{seq:7},{seq:8},{seq:9},{seq:10},{seq:11},{seq:12},{seq:13}]};_.C5={seq:14,children:[{seq:15},{seq:16}]};_.D5={seq:17,children:[{seq:18},{seq:19}]};_.E5={seq:20,children:[{seq:21},{seq:22},{seq:23}]};_.F5={seq:24,children:[{seq:25},{seq:26}]};_.G5={seq:27,children:[{seq:28},{seq:29},{seq:30},{seq:31},{seq:32},{seq:33},{seq:34}]};H5={};_.I5=(H5[1]=_.B5,H5[14]=_.C5,H5[17]=_.D5,H5[20]=_.E5,H5[24]=_.F5,H5[27]=_.G5,H5);_.x5=_.td("feedback-default.png");

_.m().l();

}catch(e){_._DumpException(e)}
try{
var L5;_.J5=function(a,b){return{title:{simpleText:a},subtitle:{runs:[{text:b,textColor:4280191205}]}}};
_.M5=function(a){_.r.get(_.$3);var b=_.x3("track");var c=!_.v.isEmpty(b),d=_.V("CAPTIONS_TITLE"),e=!b||_.v.isEmpty(b)?_.V("CAPTIONS_OFF_STATUS"):_.V("CAPTIONS_ON_STATUS")+" \u2022 "+b.displayName;d=_.J5(d,e);var f=_.V3();e=[];for(var g=-1,h=0;h<f.length;h++){var l=f[h],n={commands:[{selectSubtitlesTrackCommand:{subtitlesTrackMetadata:l}},K5]},q=void 0;l.displayName===b.displayName&&l.languageCode===b.languageCode&&(g=h,q="CHECK");e.push(L5({title:l.displayName,command:{commandExecutorCommand:n},secondaryIcon:q}))}b=
g;c=c?void 0:"CHECK";f={commands:[{selectSubtitlesTrackCommand:{}},K5]};c=[L5({title:_.V("CAPTIONS_OFF_STATUS"),command:{commandExecutorCommand:f},secondaryIcon:c})].concat(_.F(e),[_.g0(),L5({title:_.V("CAPTIONS_STYLE_TITLE"),subtitle:_.V("CAPTIONS_STYLE_SUBTITLE"),command:{openClientOverlayAction:{type:"CLIENT_OVERLAY_TYPE_CAPTIONS_STYLE"}},secondaryIcon:"CHEVRON_RIGHT"})]);return _.vA({updateAction:a,overlayPanelRenderer:_.AA(d,c,b+1),jb:{commandExecutorCommand:{commands:[{signalAction:{signal:"CLOSE_POPUP"}},
{setClientSettingEndpoint:{settingDatas:[{clientSettingEnum:{item:"CAPTION_HIDE_SAMPLE_SUBTITLE"}}]}}]}},Fl:{setClientSettingEndpoint:{settingDatas:[{clientSettingEnum:{item:"CAPTION_SHOW_SAMPLE_SUBTITLE"}}]}},uniqueId:"CLIENT_OVERLAY_TYPE_CAPTIONS_LANGUAGE",popupType:"DIALOG"})};L5=function(a){var b=a.subtitle,c=a.command,d=a.secondaryIcon;a={title:{simpleText:a.title}};b&&(a.subtitle={simpleText:b});c&&(a.serviceEndpoint=c);d&&(a.secondaryIcon={iconType:d});return{compactLinkRenderer:a}};_.m().w("sy90");
var K5={openClientOverlayAction:{type:"CLIENT_OVERLAY_TYPE_CAPTIONS_LANGUAGE",updateAction:!0}};

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy9r");
_.F7=_.p("AndroidAssistant","Ef4rCc");
var $ga,Zga;
$ga=function(){var a=this;this.g=null;this.ra=_.r.get(_.kA);this.A=_.r.get(_.z_);this.h=new _.nq;this.j=function(c,d){a:{if(c=_.FD(new Uint8Array(d)))try{var e=JSON.parse(c);break a}catch(f){}e=void 0}if(e)if(3===e.g){e=a.ra.cd();if(c=_.u("VISITOR_DATA"))e.context=e.context||{},e.context.client=e.context.client||{},e.context.client.visitorData=c;e.screenContext=_.y_(a.A);e={XE:JSON.stringify(e),yE:Zga(e.screenContext)};_.G7(a,e)}else 6===e.g&&a.h.next({state:2})};var b=window.H5vccPlatformService;if(null===
b||void 0===b?0:b.has("com.google.android.assistantService"))this.g=b.open("com.google.android.assistantService",this.j)};Zga=function(a){var b,c,d={};a=null===(b=a.displayItemShelves)||void 0===b?void 0:b[0];if(null===(c=null===a||void 0===a?void 0:a.displayItems)||void 0===c?0:c.length)d.zE=[{SE:a.shelfName,displayItems:a.displayItems.map(function(e){return{title:e.title}})}];return d};_.G7=function(a,b){a.g&&(b=JSON.stringify(b),b=Uint8Array.from(_.ED(b)).buffer,a.g.send(b))};_.K(_.F7,function(){return new $ga});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.I7=function(a,b){var c=_.Pa(a,"launch");if(!b||"preload"!==c){b={launchUrlComponentType:b?"LAUNCH_URL_COMPONENT_TYPE_PREFIX":"LAUNCH_URL_COMPONENT_TYPE_FRAGMENT",clientScreenNonce:_.cs()||void 0};if(c=a.j)b.fragmentIdentifier=_.Ri.get(c.toUpperCase())||"FRAGMENT_IDENTIFIER_INVALID";if(c=_.Pa(a,"launch"))b.launchValue=_.Si.get(c.toUpperCase())||"LAUNCH_VALUE_INVALID";var d=a.g.la("launch");d.includes("voice")&&d.includes("search")&&(b.launchValue="LAUNCH_VALUE_SEARCH",b.launchTag="voice");c&&"LAUNCH_VALUE_INVALID"!==
b.launchValue||(c=_.Pa(a,"dialLaunch"),"watch"===c?b.launchValue="LAUNCH_VALUE_DIAL_WATCH":"browse"===c&&(b.launchValue="LAUNCH_VALUE_DIAL_BROWSE"));if(c=_.Pa(a,"launch_tag"))b.launchTag=c;if(c=_.Pa(a,"vq"))b.voiceQuery=c,d=_.Za(),c=d.brand,d=d.model,"samsung"===c?b.virtualAssistant="VIRTUAL_ASSISTANT_BIXBY":"amazon"===c&&"Fire TV"===d&&(b.virtualAssistant="VIRTUAL_ASSISTANT_ALEXA");if(c=_.Pa(a,"va"))b.voiceAction=c;c=_.Pa(a,"inApp");void 0!==c&&(c=c.toLowerCase());b.inApp="true"===c||"1"===c||""===
c;_.Pa(a,"voice")&&(b.virtualAssistant="VIRTUAL_ASSISTANT_GOOGLE");a=H7;a&&b.fragmentIdentifier===a.fragmentIdentifier&&b.launchValue===a.launchValue&&b.launchTag===a.launchTag&&b.voiceQuery===a.voiceQuery&&b.voiceAction===a.voiceAction&&b.inApp===a.inApp||(H7=b,_.hd("tvhtml5LaunchUrlComponentChanged",b))}};_.m().w("sy9s");
var aha=_.p("DeeplinkManager","IfAJie");
var H7=null;
var K7=function(){var a,b=this;this.location=a=void 0===a?window.location:a;this.C=_.Wu();this.A=_.r.get(_.Oa).runtime;this.g=null;this.B=function(c){return _.Tu(b,function e(){var f=this;return _.Su(e,function(g){return _.Ju(g,J7(f,_.Ua(c)),0)})})};this.h=function(c){_.Tu(b,function e(){var f,g=this;return _.Su(e,function(h){f=_.Ua(c);if(g.j&&g.j.toString()===f.toString())return h.return();g.j=f;return _.Ju(h,J7(g,f),0)})})}},J7;
K7.prototype.initialize=function(a){var b=this;this.g||(this.g=a,this.A.isSupported()?this.A.onDeepLink.addListener(this.B):(window.addEventListener("hashchange",function(){b.h(b.location.hash)}),window.addEventListener("popstate",function(){b.h(b.location.hash)})))};J7=function(a,b){return _.Tu(a,function d(){var e=this,f;return _.Su(d,function(g){if(1==g.g)return _.I7(b),_.Ju(g,_.L7(e,b),2);f=g.h;return _.Ju(g,e.C.resolveCommand(f),0)})})};
_.L7=function(a,b){return _.Tu(a,function d(){var e=this,f,g,h,l,n,q,t,w,y;return _.Su(d,function(B){switch(B.g){case 1:f=e.g,g=[],h=0,l=f.length;case 2:if(!(h<l)){B.ua(4);break}n=f[h];_.Ku(B,5);return _.Ju(B,n(b),7);case 7:q=B.h;t=q.command;w=q.oa;t&&g.push(t);if(w){B.ua(4);break}_.Lu(B,3);break;case 5:y=_.Mu(B),_.C(y);case 3:++h;B.ua(2);break;case 4:return B.return({commandExecutorCommand:{commands:g}})}})})};_.M7=_.K(aha,function(){return new K7});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy9t");
var bha=_.p("MdxRemoteSignInMsgHandler","eWKRY");
var cha;cha=function(){this.g=_.L.R()};_.dha=_.K(bha,function(){return new cha});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy9u");
var eha=_.p("MdxSmartRemoteService","zA9hBd");
var N7,gha;N7={};_.fha=(N7.UP=38,N7.DOWN=40,N7.LEFT=37,N7.RIGHT=39,N7.BACK=27,N7.ENTER=13,N7);gha=function(){this.g=_.L.R()};_.hha=_.K(eha,function(){return new gha});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var iha=function(a,b){a=b.volume;var c=b.hidden,d=b.disabled,e="";b.muted?e="mdx-volume-component__icon--muted":40>a?e="mdx-volume-component__icon--low":80>a&&(e="mdx-volume-component__icon--medium");if(d)return _.R("host",{style:{display:"none"}});b={};return _.R("host",{className:(b["mdx-volume-component--hidden"]=c||-1===a,b)},_.R("div",{className:["mdx-volume-component__icon",e]}),_.R("div",{className:"mdx-volume-component__volume-bar-container"},_.R("div",{className:"mdx-volume-component__volume-bar",
style:{width:a+"%"}})),_.R("div",{className:"mdx-volume-component__volume-number"},""+Math.floor(a/10)))},jha=function(a){return 0>a?-1:100<a?100:a};_.m().w("sy9v");
var kha=_.p("MdxVolumeComponent","ek4G3b");
_.O7=function(a){_.Q.call(this,a);var b=this;this.B=_.r.get(_.oJ);this.template=iha;this.j=-1;this.h=!1;this.A=function(c){b.h?c.ze||(b.state.disabled?(_.S(b,{disabled:!1}),_.Mt({stage:40,X:function(){b.A(c)}})):(clearTimeout(b.j),_.S(b,{hidden:!1,muted:c.muted,volume:jha(c.volume)}),b.j=setTimeout(function(){_.S(b,{hidden:!0})},7500))):b.h=!0};this.state={disabled:!0,hidden:!0,muted:!1,volume:-1}};_.G(_.O7,_.Q);_.O7.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;
_.O7.prototype.O=function(){_.T(this,this.B.S.g("value-changed",this.A))};_.O7.TAG_NAME="mdx-volume-component";_.J(kha,_.O7);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy9w");
var lha=_.p("CobaltInAppDialService","tGlfc");
var P7=function(){this.j=this.h=null;this.D=_.r.get(_.gG);this.g=_.r.get(_.Oa).dial;this.B=(this.mdxInitData=_.pd().mdxInitData)&&this.mdxInitData.theme||"cl";this.C=_.r.get(_.uS);this.A=_.r.get(_.cu)},mha,Q7;
P7.prototype.initialize=function(){var a=this;if(!this.h&&!this.j&&this.g.isSupported())if(this.g.isInAppServer())try{(this.h=this.g.createInAppServer("YouTube"))&&mha(this,this.h)}catch(b){_.C(Error("Bc`"+b))}else if(this.g.isNative())try{this.j=this.g.createNativeServiceHandler(function(b){Q7(a,new _.La(b))})}catch(b){_.C(Error("Cc`"+b))}};
mha=function(a,b){b.addRequestHandler("GET","/",function(c,d){d.mimeType='text/xml; charset="utf-8"';d.responseCode=200;c='<?xml version="1.0" encoding="UTF-8"?><service xmlns="urn:dial-multiscreen-org:schemas:dial"><name>YouTube</name><options allowStop="false" /><state>running</state><link rel="run" href="'+("http://"+c.host+c.path+'/run" />');var e=document.implementation.createDocument("http://www.youtube.com/dial","additionalData",null),f=e.documentElement,g=Object.assign({},a.C.g);for(l in g)if(g.hasOwnProperty(l)){var h=
e.createElement(l);h.appendChild(e.createTextNode(g[l]));f.appendChild(h)}var l=(new XMLSerializer).serializeToString(e);d.body=c+l+"</service>";return!0});b.addRequestHandler("POST","/",function(c,d){Q7(a,new _.La(c.body))?(d.responseCode=201,d.addHeader("LOCATION","http://"+c.host+c.path+"/run"),d.mimeType='text/plain; charset="utf-8"',d.body="",c=!0):c=!1;return c});b.addRequestHandler("DELETE","/run",function(c,d){d.responseCode=501;d.body="";return!0})};
Q7=function(a,b){var c=_.Va(b);if(!c)return!1;var d=b.get("theme");if(d&&d!==a.B){if(!_.D("supportsInAppDialVerticalLaunch",!1))return _.Eo(_.L.R(),new _.O("showInAppDialVerticalLaunchFailureToastAction"),void 0),!1;_.lG(b)}else a.g.isInAppServer()?a.A.g=!0:a.g.isNative()&&(a.A.h=!0),a.D.Ph(c);return!0};_.nha=_.K(lha,function(){return new P7});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy9y");
var oha=_.p("MdxSessionMsgHandler","ie5Qqb");
var pha;pha=function(){this.h=_.Wu();this.g=_.r.get(_.gG)};_.qha=_.K(oha,function(){return new pha});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sy9z");
var rha=_.p("MdxVolumeService","SAbplc");
var T7=function(){_.oH.apply(this,arguments);var a=this;this.G=_.L.R();this.B=_.Wu();this.h=_.r.get(_.oJ);this.D=function(b){_.R7(a,b)};this.j=!1;this.H=function(b){a.j&&b.zo&&_.S7(a,100)}};_.G(T7,_.oH);T7.prototype.A=function(){var a=this;this.supportsMdxVolume()&&(_.EG().then(function(b){b.addEventListener("onVolumeChange",a.D)}),_.pH(this,function(){_.EG().then(function(b){b.removeEventListener("onVolumeChange",a.D)})}),_.pH(this,_.N(this.G,new _.O("mdx-session:remoteDisconnected"),this,this.H)))};
_.R7=function(a,b){-1!==b.volume&&a.sendMessage("onVolumeChanged",{volume:b.volume||0,muted:!!b.muted})};_.S7=function(a,b){var c={volumeControlType:"VOLUME_CONTROL_ACTION_TYPE_SET_ABSOLUTE"};c.volumeControlValue=b;a.B.resolveCommand({volumeControlAction:c});a.j=!0};T7.prototype.supportsMdxVolume=function(){return!!_.D("supportsMdxVolume",!0)};_.U7=_.K(rha,function(){return new T7});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sya1");
var sha=_.p("MdxPlaybackService","GyVqgb");
var tha={state:3,currentTime:0,duration:0,loadedTime:0,seekableStartTime:0,seekableEndTime:0},Z7=function(){_.oH.apply(this,arguments);var a=this;this.G=_.L.R();this.j=_.r.get(_.sH).tf();this.h=_.r.get(_.oJ);this.B=null;this.H=function(b){_.V7(a,_.W7(b.za),!0)};this.S=function(){var b=a.j.watchNextResponse;b.currentVideoEndpoint&&_.V7(a,b.currentVideoEndpoint);a.hj()};this.D=function(){_.X7(a)};this.P=function(b){var c;0<b.devices.length&&a.hj();a.h.setConnectedRemoteApps((null===(c=a.g)||void 0===
c?void 0:_.lF(c))||[])};this.J=function(b){a.j.h()&&(a.B&&(clearTimeout(a.B),a.B=null),a.h.na||(0===b?a.sendMessage("onStateChange",_.Y7(a)):a.B=setTimeout(function(){a.sendMessage("onStateChange",_.Y7(a))},50)))};this.N=function(b,c){a.j.h()&&2<=Math.abs(b.current-c.current)&&a.sendMessage("onStateChange",_.Y7(a))}},uha;_.G(Z7,_.oH);
Z7.prototype.A=function(){_.pH(this,this.j.g("playbackImminent",this.H));_.pH(this,this.j.g("watchNextResponseChanged",this.S));_.pH(this,this.j.g("playbackAbandoned",this.D));_.pH(this,this.h.sb(this.J));_.pH(this,_.dJ(this.h,this.N));_.pH(this,_.N(this.G,new _.O("mdx-session:remoteStatusChanged"),this,this.P))};
_.V7=function(a,b,c){c=void 0===c?!1:c;if(b.watchEndpoint)if(a.g){var d=b.watchEndpoint,e=d.videoId,f=d.playlistId,g=d.index,h=d.playerParams;d=d.params;c=c?tha:_.Y7(a);e=Object.assign({videoId:e},c);f&&(e.listId=f);if(f=_.XG(b.watchEndpoint,"VIDEO"))e.ctt=f;if(b=_.XG(b.watchEndpoint,"PLAYLIST"))e.listCtt=b;"number"===typeof g&&(e.currentIndex=g);h&&(e.playerParams=h);d&&(e.params=d);a.sendMessage("nowPlaying",e)}else _.C(Error("Dc"));else _.C(Error("Ec"))};
_.X7=function(a){a.sendMessage("nowPlaying",{})};_.Y7=function(a){var b=a.h.progress;a={state:uha(a),currentTime:a.h.currentTime,duration:a.h.duration,loadedTime:b.loaded,seekableStartTime:b.seekableStart,seekableEndTime:b.seekableEnd};b.ingestionTime&&(a.liveIngestionTime=b.ingestionTime);if(b=_.VI())a.cpn=b;return a};uha=function(a){if(a.h.na)return 1081;switch(a.h.state){case 0:return 0;case 1:return 1;case 2:return 2;case 3:return 3;case 5:return 5;case -1E3:return-1E3;default:return-1}};
Z7.prototype.hj=function(){this.sendMessage("onHasPreviousNextChanged",{hasPrevious:this.j.hasPrevious(),hasNext:this.j.hasNext()})};_.W7=function(a){return{clickTrackingParams:a.clickTrackingParams,watchEndpoint:a.watchEndpoint}};_.$7=_.K(sha,function(){return new Z7});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sya0");
var vha=_.p("MdxAdsService","emELdc");
var c8=function(){_.oH.apply(this,arguments);var a=this;this.h=_.r.get(_.gN);this.D=_.r.get(_.K4);this.N=_.Wu();this.S=_.r.get(_.$7);this.j=_.r.get(_.oJ);this.B=!1;this.G=function(b){b?(_.a8(a),b=_.Y7(a.S),b.state=1081,a.sendMessage("onStateChange",b)):a.B||b8(a,0);a.B=!1};this.P=function(){a.j.na&&b8(a,a.getAdState())};this.J=function(b){b&&b8(a,a.getAdState())};this.H=function(b){b&&(a.B=!0,b8(a,1082))}},b8;_.G(c8,_.oH);
c8.prototype.A=function(){_.pH(this,_.jJ(this.j,this.G));_.pH(this,this.j.sb(this.P));_.pH(this,_.eN(this.h,this.J));_.pH(this,this.h.A.g("value-changed",this.H))};
_.a8=function(a){var b={},c=a.h.V;c&&(c.videoId&&(b.adVideoId=c.videoId),c.videoUrl&&(b.adVideoUri=c.videoUrl),b.adTitle=a.h.adMetadata.videoTitle,b.isBumper=c.isBumper,b.isSkippable=c.isSkippable,b.isSkipEnabled=a.h.S,b.clickThroughUrl=c.clickThroughUrl,b.adSystem=c.adSystem,b.adNextParams=c.adNextParams);b.adState=a.getAdState();b.contentVideoId=_.OI();b.duration=a.D.progress.duration;b.currentTime=a.D.progress.current;a.sendMessage("adPlaying",b)};
b8=function(a,b){a.sendMessage("onAdStateChange",{adState:b,currentTime:a.D.progress.current,isSkipEnabled:1082!==b&&a.h.S})};c8.prototype.getAdState=function(){if(!this.j.na)return-1;switch(this.j.state){case 0:return 0;case 1:return 1;case 2:return 2;case -1:return-1;default:return-1}};_.d8=_.K(vha,function(){return new c8});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sya2");
var wha=_.p("MdxAudioTrackService","ad7IYd");
var e8=function(){_.oH.apply(this,arguments);var a=this;this.B=_.Wu();this.h=function(){xha(a)};this.j=function(){yha(a)}},xha,yha;_.G(e8,_.oH);e8.prototype.A=function(){var a=this;_.EG().then(function(b){b.addEventListener("onPlaybackAudioChange",a.h);b.addEventListener("videodatachange",a.j)});_.pH(this,function(){_.EG().then(function(b){b.removeEventListener("onPlaybackAudioChange",a.h);b.removeEventListener("videodatachange",a.j)})})};
xha=function(a){_.EG().then(function(b){if(b=b.getAudioTrack())b={videoId:_.OI(),audioTrackId:b.getLanguageInfo().getId()},a.sendMessage("onAudioTrackChanged",b)})};yha=function(a){_.EG().then(function(b){b=b.getAvailableAudioTracks();1>=b.length||(b=b.map(function(c){return{id:c.getLanguageInfo().getId(),displayName:c.getLanguageInfo().getName(),isDefault:c.getLanguageInfo().getIsDefault()}}),a.sendMessage("onAudioTrackListChanged",{audioTracks:JSON.stringify(b)}))})};_.f8=_.K(wha,function(){return new e8});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sya3");
var zha=_.p("MdxPlaybackMsgHandler","rHrABf");
var g8=function(){_.oH.apply(this,arguments);var a=this;this.h=_.Wu();this.H=_.r.get(_.sH).tf();this.j=_.L.R();this.G=_.r.get(_.oJ);this.B=!0;this.D=function(b){b.wo&&(a.B=!0)}};_.G(g8,_.oH);g8.prototype.A=function(){_.pH(this,_.N(this.j,new _.O("mdx-session:remoteConnected"),this,this.D))};_.h8=_.K(zha,function(){return new g8});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sya4");
var Aha=_.p("MdxSubtitlesService","cE5lee");
var k8=function(){_.oH.apply(this,arguments);var a=this;this.h=_.r.get(_.$3);this.B=_.Wu();this.H=_.L.R();this.j=null;this.D=function(b){_.OI()&&(!b||b.vss_id||b.name||b.languageCode||(b=null),_.i8(a.j,b)||(a.j=b,_.j8(a,b)))}};_.G(k8,_.oH);k8.prototype.A=function(){var a=this;_.EG().then(function(b){b.addEventListener("captionschanged",a.D)})};k8.prototype.C=function(){var a=this;_.EG().then(function(b){b.removeEventListener("captionschanged",a.D)})};
_.j8=function(a,b){var c={videoId:_.OI()};if(b){var d=b.languageCode||"";c=Object.assign(Object.assign({},c),{trackName:b.name||"",languageCode:d,sourceLanguageCode:d,languageName:b.languageName||"",kind:b.kind||"",vss_id:b.vss_id||""})}if(b=a.h.getSubtitlesUserSettings())c.style=JSON.stringify(b);a.sendMessage("onSubtitlesTrackChanged",c)};_.i8=function(a,b){return a&&b?a.vss_id&&b.vss_id&&a.vss_id===b.vss_id?!0:a.kind===b.kind&&a.name===b.name&&a.languageCode===b.languageCode:!a===!b};
_.l8=_.K(Aha,function(){return new k8});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m8=function(){var a=_.GG;a=a&&a.getAvailableQualityLevels()||[];for(var b=[],c=0,d=a.length;c<d;c++)"auto"===a[c]?b.unshift(a[c]):b.push(a[c]);return b};_.m().w("sya5");
var Bha=_.p("MdxVideoQualityService","lQ1erd");
var n8=function(){_.oH.apply(this,arguments);var a=this;this.j=_.Wu();this.h=function(b){Cha(a,b)}},Cha;_.G(n8,_.oH);n8.prototype.A=function(){var a=this;_.EG().then(function(b){b.addEventListener("onPlaybackQualityChange",a.h)});_.pH(this,function(){_.EG().then(function(b){b.removeEventListener("onPlaybackQualityChange",a.h)})})};
Cha=function(a,b){b=_.QI[b];var c=_.m8().map(function(d){return _.QI[d]});b={videoId:_.OI(),qualityLevel:b,availableQualityLevels:JSON.stringify(c)};a.sendMessage("onVideoQualityChanged",b)};_.o8=_.K(Bha,function(){return new n8});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var p8=function(a){a.h.resolveCommand({playerControlAction:{playerControlType:"PLAYER_CONTROL_ACTION_TYPE_PLAY"}})},q8=function(a){a.h.resolveCommand({playerControlAction:{playerControlType:"PLAYER_CONTROL_ACTION_TYPE_NEXT"}})},r8=function(a){a.h.resolveCommand({playerControlAction:{playerControlType:"PLAYER_CONTROL_ACTION_TYPE_STOP"}})},s8=function(a){a=Number(a);if(!(isNaN(a)||0>a))return a},Dha=function(a,b){var c={};b&&(c={nm:b});_.Eo(a.H,new _.O("mdx-watch:setSubtitlesStyle"),c)},t8=function(a){var b=
{};a&&(b.subtitlesTrackMetadata=a);return{selectSubtitlesTrackCommand:b}},u8=function(a){a=Number(a);return isNaN(a)?void 0:a},Eha=function(a){if(!a)return null;var b={};try{b=JSON.parse(a)}catch(c){return _.C(c),null}b.fontFamilyOption||(b.fontFamilyOption=_.bC[4]);a=u8(b.fontSizeRelative);b.fontSizeRelative=a;b.windowOpacity=u8(b.windowOpacity);b.backgroundOpacity=u8(b.backgroundOpacity);b.textOpacity=u8(b.textOpacity);void 0!==a&&(b.fontSizeIncrement=Math.floor((1>a?2*a-2:4*a-4)+2E-15));return b},
Fha=function(a,b){return a&&b?a.vss_id&&b.vss_id&&a.vss_id===b.vss_id?!0:(!a.kind||!b.kind||a.kind===b.kind)&&(!a.name||!b.name||a.name===b.name)&&a.languageCode===b.languageCode:!a===!b},Gha=function(a){var b=_.V3(),c=null;if("rawcc"===a.languageCode)return c=b.find(function(e){return"rawcc"===e.languageCode})||null;for(var d=0;d<b.length&&(!Fha(b[d],a)||(c=b[d],!_.i8(c,a)));d++);return c},v8=function(a,b){if(b=Gha(b))b.skipPreview=!0,a.B.resolveCommand(t8(b))},Hha=function(a,b){if(a.h.g.get()&&
b.videoId&&b.videoId===_.OI())if(b.vss_id||b.languageCode||b.trackName){var c={name:b.trackName,languageName:b.languageName,languageCode:b.languageCode,kind:b.kind,vss_id:b.vss_id};b.style&&(b=Eha(b.style),Dha(a,b));if((b=_.V3())&&b.length)v8(a,c);else{var d=_.Z3(a.h,function(){v8(a,c);d()});_.U3(a.h)}}else a.B.resolveCommand(t8(null))},Iha=function(a,b){if(b.videoId&&b.qualityLevel&&b.videoId===_.OI()){var c=Number(b.qualityLevel);isNaN(c)||(b=_.m8().find(function(d){return _.QI[d]===c})||"auto",
a.j.resolveCommand({setClientSettingEndpoint:{settingDatas:[{clientSettingEnum:{item:"PLAYBACK_QUALITY"},stringValue:b}]}}))}};_.m().w("sy9x");
var Jha=_.p("DefaultMdxIncomingMessageHandler","aTfCs");
var w8={},x8=(w8.dismissAutoplay=function(){return!0},w8.dpadCommand=function(){return!0},w8.next=function(){return!0},w8.onUserActivity=function(){return!0},w8.pause=function(){return!0},w8.play=function(){return!0},w8.previous=function(){return!0},w8.seekTo=function(){return!0},w8.sendDebugCommand=function(){return!0},w8.setAudioTrack=function(){return!0},w8.setAutoplayMode=function(){return!0},w8.setPlaylist=function(){return!0},w8.setPlaylistMode=function(){return!0},w8.setSubtitlesTrack=function(){return!0},
w8.setVideoQuality=function(){return!0},w8.setVolume=function(){return!0},w8.skipAd=function(){return!0},w8.stopVideo=function(){return!0},w8.updatePlaylist=function(a){return!!a.eventDetails},w8.updateRemoteTransactionStatus=function(){return!0},w8.updateSignInStatus=function(){return!0},w8.voiceCommand=function(){return!0},w8),y8=function(){this.j=_.r.get(_.d8);this.A=_.r.get(_.zH);this.F=_.r.get(_.dha);this.h=_.r.get(_.h8);this.P=_.r.get(_.$7);this.J=_.r.get(_.hha);this.B=_.r.get(_.l8);this.N=
_.r.get(_.o8);this.D=_.r.get(_.f8);this.C=_.r.get(_.U7);this.G=_.r.get(_.qha);this.H=_.r.get(_.NH)};
y8.prototype.g=function(a,b){x8[a]&&x8[a](b)&&_.Ub();switch(a){case "dismissAutoplay":this.A.S.resolveCommand({signalAction:{signal:"CANCEL_AUTONAV"}});break;case "dpadCommand":a=null;b.key&&(a=_.fha[b.key]);if(a&&(b=document.activeElement)){var c=_.Et(b,"keydown",a,!0);b.dispatchEvent(c);a=_.Et(b,"keyup",a,!0);b.dispatchEvent(a)}break;case "forceDisconnect":b=this.G;b.g.zg();b.h.resolveCommand({signalAction:{signal:"STOP_PLAYER"}});b.g.connect("forceDisconnect");break;case "getNowPlaying":b=this.P;
b.g&&(b.j.h()?(a=b.j.watchNextResponse,a.currentVideoEndpoint?_.V7(b,a.currentVideoEndpoint):b.h.Ug?_.V7(b,_.W7(b.h.Ug)):_.X7(b)):_.X7(b));b=this.j;b.g&&b.j.na&&_.a8(b);break;case "getSubtitlesTrack":b=this.B;_.OI()&&(a=_.x3("track"),_.j8(b,!_.v.isEmpty(a)&&a||null));break;case "getVolume":b=this.C;b.supportsMdxVolume()&&_.R7(b,{volume:b.h.volume,muted:b.h.muted});break;case "next":q8(this.h);break;case "pause":this.h.h.resolveCommand({playerControlAction:{playerControlType:"PLAYER_CONTROL_ACTION_TYPE_PAUSE"}});
break;case "play":p8(this.h);break;case "previous":this.h.h.resolveCommand({playerControlAction:{playerControlType:"PLAYER_CONTROL_ACTION_TYPE_PREVIOUS"}});break;case "reloadWatchNext":_.Eo(this.h.j,new _.O("mdx-watch:updatePlaylist"),{});break;case "seekTo":this.h.h.resolveCommand({playerControlAction:{playerControlType:"PLAYER_CONTROL_ACTION_TYPE_SEEK_ABSOLUTE",playerSeekTimeInMillis:String(1E3*(s8(b.newTime)||0))}});break;case "setAudioTrack":a=this.D;if(b.videoId&&b.audioTrackId&&b.videoId===
_.OI()){c=(c=_.GG)&&c.getAvailableAudioTracks()||[];for(var d=0,e=c.length;d<e;d++){var f=c[d].getLanguageInfo();if(f&&f.getId()===b.audioTrackId){b={setClientSettingEndpoint:{settingDatas:[{clientSettingEnum:{item:"PLAYBACK_AUDIO_TRACK"},stringValue:f.getName()}]}};a.B.resolveCommand(b);break}}}break;case "setAutoplayMode":a=this.A;a.G.setValue("MDX_AUTOPLAY_ENABLED","ENABLED"===b.autoplayMode);_.vH(a);break;case "setImpactedSessionsServerEvent":a=this.H;if(b=b.serverEvent)c=a.localStorage.get(_.LH)||
[],c.push(b),a.localStorage.set(_.LH,c,15768E3);break;case "setPlaylist":a=this.h;if(b.videoId)if(d=Number(b.currentIndex),isNaN(d)&&(d=0),c=s8(b.currentTime),e="true"===b.forceReloadPlayback,b.videoId!==_.OI()||!a.G.A||e){f=b.listId;var g=d,h=b.playerParams,l=b.params,n=b.ctt,q=b.listCtt;d=b.clickTrackingParams;e={};e.videoId=b.videoId;"number"===typeof g&&(e.index=g);f&&(e.playlistId=f);void 0!==c&&(e.startTimeSeconds=c);h&&(e.playerParams=h);l&&(e.params=l);c=[];n&&c.push({scope:"VIDEO",token:n});
q&&c.push({scope:"PLAYLIST",token:q});if(n||0<c.length)e.watchEndpointSupportedAuthorizationTokenConfig={videoAuthorizationToken:{credentialTransferTokens:c}};e.watchEndpointRemotePlaybackSupportedConfigs={remotePlaybackConfig:{isMdxPlayback:!0}};c={};d&&(c.clickTrackingParams=d);c.watchEndpoint=e;d=null;a.B&&(d={isFling:!0});"true"===b.forceReloadPlayback&&(d=Object.assign(Object.assign({},d),{forceReloadPlayback:!0}));d&&(c.Gc=d);a.h.resolveCommand(c)}else p8(a),_.Eo(a.j,new _.O("mdx-watch:updatePlaylist"),
{listId:b.listId});else _.C(Error("Fc"));a.B=!1;break;case "setSubtitlesTrack":Hha(this.B,b);break;case "setVideoQuality":Iha(this.N,b);break;case "setVolume":a=this.C;!a.supportsMdxVolume()||a.h.muted&&a.h.isInline()||(c="true"===b.muted||"1"===b.muted,c!==a.h.muted&&a.B.resolveCommand({volumeControlAction:{volumeControlType:c?"VOLUME_CONTROL_ACTION_TYPE_MUTE":"VOLUME_CONTROL_ACTION_TYPE_UNMUTE"}}),c=a.h.volume,d=Number(b.volume)||0,void 0!==b.delta&&(b=Number(b.delta),0<=c&&!isNaN(b)&&(d=Math.max(0,
Math.min(c+b,100)))),_.S7(a,d));break;case "skipAd":this.j.N.resolveCommand({signalAction:{signal:"PLAYER_SKIP_AD"}});break;case "stopVideo":r8(this.h);break;case "updatePlaylist":a=this.h;a.B=!1;c=null;if(b.eventDetails)try{c=JSON.parse(b.eventDetails)}catch(t){_.C(Error("Gc`"+t))}c&&"VIDEO_REMOVED"===c.eventType&&_.OI()===c.videoId&&(a.H.hasNext()?q8(a):r8(a));_.Eo(a.j,new _.O("mdx-watch:updatePlaylist"),{listId:b.listId,eventDetails:c});break;case "updateSignInStatus":a=null;switch(b.event){case "canceled":a=
"exited";break;case "completed":a="completed";break;case "denied":a="canceled";break;case "started":a="started"}null!==a&&_.Eo(this.F.g,new _.O("mdx-sign-in:status-updated"),{event:a,deviceId:b.deviceId||"",deviceDescription:b.deviceDescription||""});break;case "voiceCommand":switch(a=this.J,c=String(b.text||""),d=String(b["unstable speech"]||""),String(b.status)||""){case "INITIATED":_.Eo(a.g,new _.O("mdx-voice-query:initiated"),void 0);break;case "UPDATED":_.Eo(a.g,new _.O("mdx-voice-query:updated"),
{text:c,Yp:d});break;case "COMPLETED":_.Eo(a.g,new _.O("mdx-voice-query:completed"),{text:c});break;case "CANCELED":_.Eo(a.g,new _.O("mdx-voice-query:canceled"),void 0)}}};_.Kha=_.K(Jha,function(){return new y8});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("t8Naub");
var Uv=_.p("H5vccAccountManager","t8Naub"),Vv=function(){};Vv.prototype.getAuthToken=function(a){a(null,-1)};Vv.prototype.requestPairing=function(){};Vv.prototype.requestUnpairing=function(){};_.Wv=_.K(Uv,function(){if("undefined"!==typeof H5vccAccountManager)return new H5vccAccountManager;_.C(Error("Ga"));return new Vv});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Lha=function(a){var b=a.subject;b.next(a.value);b.complete()},Mha=function(a){var b=this,c=a.args,d=a.Fd,e=a.params;a=e.Mm;var f=e.context,g=e.Aa,h=e.subject;if(!h){h=e.subject=new _.pq;e=function(l){for(var n=[],q=0;q<arguments.length;++q)n[q]=arguments[q];b.add(g.schedule(Lha,0,{value:1>=n.length?n[0]:n,subject:h}))};try{a.apply(f,_.F(c).concat([e]))}catch(l){h.error(l)}}this.add(h.subscribe(d))},z8=function(a,b){if(b)if(_.Yo(b))var c=b;else return function(d){for(var e=[],f=0;f<arguments.length;++f)e[f]=
arguments[f];return z8(a,c).apply(null,_.F(e)).g(_.dp(function(g){return(0,_.qp)(g)?b.apply(null,_.F(g)):b(g)}))};return function(d){for(var e=[],f=0;f<arguments.length;++f)e[f]=arguments[f];var g=this,h,l={context:g,subject:void 0,Mm:a,Aa:c};return new _.Zo(function(n){if(c)return c.schedule(Mha,0,{args:e,Fd:n,params:l});if(!h){h=new _.pq;var q=function(t){for(var w=[],y=0;y<arguments.length;++y)w[y]=arguments[y];h.next(1>=w.length?w[0]:w);h.complete()};try{a.apply(g,_.F(e).concat([q]))}catch(t){_.Uo(h)?
h.error(t):console.warn(t)}}return h.subscribe(n)})}};_.m().w("sya7");
var Nha=_.p("SignInWithAccountManagerService","DwcTwf");
var A8=function(){this.j=_.r.get(_.nv);this.g=_.r.get(_.Tv);this.h=_.r.get(_.gu);this.accountManager=_.r.get(_.Wv)};
A8.prototype.signIn=function(a,b){var c=this;return z8(this.accountManager.requestPairing).call(this.accountManager).g(_.Cr(function(d){var e=d[0];if(!e)return _.np;var f=d[1];return _.Rv(c.g,{},e).g(_.dp(function(g){return{pb:g,accessToken:e,durationSec:f}}))}),_.dp(function(d){var e=d.pb.values().next().value.identity.ownerObfuscatedGaiaId;e=d.pb.get(e);if(!e)throw Error("Hc");e=e.identity;c.j.Vc(e,{},a,b);d=_.Dr(d.accessToken,d.durationSec,e);c.h.g.add(d.identity.effectiveObfuscatedGaiaId,d);return e}),
_.Ev())};_.Oha=_.K(Nha,function(){return new A8});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.D8=function(a){Object.assign(_.C8,a)};_.m().w("sya8");
_.C8={};

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("sya9");
_.B8={10009:27,415:250,10252:179,413:178,417:228,412:227,46:71,10225:83,10232:177,10233:176};

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("syaa");
var Pha=_.p("OverlayConfigurationService","AJs93e");
var Qha;Qha=function(){};_.F8=_.K(Pha,function(){return new Qha});

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Rha=function(a,b){return _.R("host",{className:a.active?"yt-debug-console--active":""},_.R("div",{className:"yt-debug-console__title"},"Console"),_.R("div",{className:"yt-debug-console__messages"},_.TC(b.messages,function(c){return _.R("div",{className:"yt-debug-console__message"},c)}),_.R("div",{className:"yt-debug-console__message"},">>")))};_.m().w("syac");
var Sha=_.p("YtDebugConsole","OomWde");
var G8,H8;G8=["debug","error","info","log","warn"];H8={};_.I8=function(a){_.Q.call(this,a);this.template=Rha;this.state={messages:[]}};_.G(_.I8,_.Q);_.I8.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.I8.prototype.O=function(){this.props.active?this.install():this.uninstall()};_.I8.prototype.W=function(a){this.props.active&&!a.active?this.install():!this.props.active&&a.active&&this.uninstall()};
_.I8.prototype.install=function(){for(var a=this,b={},c=_.A(G8),d=c.next();!d.done;b={Zc:b.Zc},d=c.next())b.Zc=d.value,H8[b.Zc]||(H8[b.Zc]=console[b.Zc]),console[b.Zc]=function(e){return function(f){for(var g=[],h=0;h<arguments.length;++h)g[h]=arguments[h];H8[e.Zc].apply(console,g);g=[e.Zc.toUpperCase()].concat(g).join(" ");_.S(a,{messages:_.F(a.state.messages.slice(-28)).concat([g])})}}(b)};
_.I8.prototype.uninstall=function(){for(var a=_.A(G8),b=a.next();!b.done;b=a.next())b=b.value,H8[b]&&(console[b]=H8[b],delete H8[b])};_.I8.TAG_NAME="yt-debug-console";_.J(Sha,_.I8);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Tha=function(a){return _.R("host",{className:a.active?"yt-debug-watermark--active":""},_.R("div",null,"CURRENT FRAMEWORK: TECTONIC"),_.R("div",null,a.text))};_.m().w("syad");
var Uha=_.p("YtDebugWatermark","cqy6Vc");
_.J8=function(a){_.Q.call(this,a);this.template=Tha};_.G(_.J8,_.Q);_.J8.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.J8.TAG_NAME="yt-debug-watermark";_.J(Uha,_.J8);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Wha=function(a,b){var c=this;a={};return _.R("host",{className:(a["yt-overlay-stage--hidden"]=1===b.visibility,a)},_.R("div",{className:Vha(this)}),_.TC(b.Ua,function(d,e){var f=b.Ua.length-1,g=e===f,h=d.action.popup,l=d.action.popup,n=d.isVisible,q=g&&0===b.visibility,t=d.idomKey,w=["yt-overlay-stage__overlay"];(g||e===f-1&&!b.Ua[f].isVisible)&&w.push("yt-overlay-stage__overlay--top");g||_.D("enableAnimations",!1)||w.push("yt-overlay-stage--hidden");f=g?(f=c.state.Ua[e-1])&&f.action.replacePopup?
e-1:e:e;e=c.C;d={data:l,isVisible:n,focused:q,idomKey:t,className:w,Ye:f,rb:d.rb,Eb:d.Eb};d=e.Jl&&e.Jl(d)||{};return _.R(_.xJ,{renderer:h,config:d})}))};_.m().w("syae");
var Xha=_.p("YtOverlayStage","BMSAZe");
_.N8=function(a){_.Q.call(this,a);var b=this;this.template=Wha;this.h=_.L.R();this.C=_.r.get(_.F8);this.j=function(c){c=c.command.openPopupAction;if("TOAST"!==c.popupType){10<b.state.Ua.length+1&&_.C(Error("Ic"));var d=_.F(b.state.Ua).concat();a:{var e=c.updateAction;if(c.uniqueId&&e){e=0;for(var f=d.length;e<f;++e)if(d[e].action.uniqueId===c.uniqueId)break a}e=-1}if(0<=e)d[e].action=c;else{e=Yha(b,c);f=K8(b,d);if(null===f||void 0===f?0:f.action.replacePopup)L8(b,d,f),M8(b,f);d.push(e);var g,h;(e=
null===(h=null===(g=e.action.popup)||void 0===g?void 0:g.overlaySectionRenderer)||void 0===h?void 0:h.onOpenCommand)&&_.U(b,"innertube-command",e);Zha(b,c)}_.S(b,{Ua:d,visibility:0})}};this.A=function(){var c=K8(b);if(c){var d=[c];L8(b,d,c);b.state.Ua.forEach(function(e){M8(b,e)});_.S(b,{Ua:d,visibility:0<d.length?0:1})}};this.B=function(){var c=K8(b);if(c){var d=_.F(b.state.Ua).concat();L8(b,d,c);M8(b,c);_.S(b,{Ua:d,visibility:0<d.length?0:1})}};this.state={Ua:[],visibility:1}};_.G(_.N8,_.Q);
_.N8.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.N8.prototype.isAnimated=function(){return!!_.D("enableAnimations",!0)};_.N8.prototype.O=function(){_.T(this,_.N(this.h,new _.O("openPopupAction"),this,this.j));_.T(this,_.N(this.h,new _.O("CLOSE_POPUP"),this,this.A));_.T(this,_.N(this.h,new _.O("POPUP_BACK"),this,this.B))};
var K8=function(a,b){b=void 0===b?a.state.Ua:b;return b[b.length-1]},L8=function(a,b,c){var d=b.indexOf(c);0>d||(a.isAnimated()&&c.isVisible?(c.isVisible=!1,c.rb=function(){var e=a.state.Ua,f=e.indexOf(c);0>f||(e.splice(f,1),_.S(a,{Ua:_.F(e).concat(),visibility:0<e.length?0:1}))}):b.splice(d,1))},Zha=function(a,b){a.isAnimated()&&_.Mt({stage:40,X:function(){for(var c=a.state.Ua.length-1;0<=c;c--){var d=a.state.Ua[c],e=b.uniqueId&&b.uniqueId===d.action.uniqueId;if((b===d.action||e)&&!d.isVisible){d.isVisible=
!0;_.qx(a);break}}}})};_.N8.prototype.W=function(a,b){_.Q.prototype.W.call(this,a,b);this.state.visibility!==b.visibility&&this.props.qp(this.state.visibility)};
var Yha=function(a,b){return{action:b,idomKey:"goog_"+_.Lf++,isVisible:!a.isAnimated(),Eb:function(){},rb:function(){}}},M8=function(a,b){var c,d;(b=null===(d=null===(c=b.action.popup)||void 0===c?void 0:c.overlaySectionRenderer)||void 0===d?void 0:d.onClosedCommand)&&_.U(a,"innertube-command",b)},Vha=function(a){var b,c=["yt-overlay-stage__overlay"];"DIALOG"!==(null===(b=a.state.Ua[a.state.Ua.length-1])||void 0===b?void 0:b.action.popupType)&&c.push("yt-overlay-stage__scrim");return c};
_.N8.TAG_NAME="yt-overlay-stage";_.J(Xha,_.N8);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var $ha=function(){return _.R("host",null)};_.m().w("syaf");
var aia=_.p("YtSimpleVe","UKpcte");
_.O8=function(){_.Q.apply(this,arguments);this.template=$ha};_.G(_.O8,_.Q);_.O8.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.E.Object.defineProperties(_.O8.prototype,{visualElement:{configurable:!0,enumerable:!0,get:function(){return this.props.visualElement}}});_.O8.TAG_NAME="yt-simple-client-ve";_.J(aia,_.O8);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var cia=function(a,b){a=b.step;var c=this.h;return _.R("host",{className:bia[a],"aria-live":"polite"},_.Y("fade-in"===a||"display"===a||"fade-out"===a,function(){return _.R(_.NY,{data:c[0].popup.overlayMessageRenderer})}))};_.m().w("syag");
var dia=_.p("YtToastStage","KoivF");
var P8={},bia=(P8.idle=null,P8["fade-in"]="yt-toast-stage--fade-in",P8.display="yt-toast-stage--display",P8["fade-out"]="yt-toast-stage--fade-out",P8.hide=null,P8);
var Q8,eia,R8,fia;Q8={};eia=(Q8.idle={xd:"fade-in"},Q8["fade-in"]={durationMs:400,Gk:!0,xd:"display"},Q8.display={durationMs:5E3,xd:"fade-out"},Q8["fade-out"]={durationMs:700,xd:"hide",Gk:!0},Q8.hide={durationMs:200,fm:!0,xd:"fade-in"},Q8);R8={};fia=(R8.idle={xd:"display"},R8.display={durationMs:5E3,xd:"hide"},R8.hide={durationMs:200,fm:!0,xd:"display"},R8);
_.S8=function(){_.Q.apply(this,arguments);var a=this;this.template=cia;this.j=_.L.R();this.state={step:"idle"};this.h=[];this.A=function(b){b=b.command.openPopupAction;"TOAST"===b.popupType&&(a.h.push(b),a.props.Oa||"idle"===a.state.step&&a.tick())};this.tick=function(){var b=a.h,c=_.D("enableAnimations",!0)?eia:fia,d=c[a.state.step],e=d.xd;c=c[e];d.fm&&(b.shift(),0===b.length&&(e="idle"));_.S(a,{step:e});"idle"!==e&&(d=c.durationMs||0,"display"===e&&(d=b[0].durationHintMs||d),b=d,c.Gk?_.Wx({eventType:_.bn,
element:a.el,X:a.tick,ud:5*b}):setTimeout(a.tick,b))}};_.G(_.S8,_.Q);_.S8.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.S8.prototype.O=function(){_.T(this,_.N(this.j,new _.O("openPopupAction"),this,this.A))};_.S8.prototype.W=function(a){a.Oa&&!this.props.Oa&&0<this.h.length&&"idle"===this.state.step&&this.tick();!a.Oa&&this.props.Oa&&"idle"!==this.state.step&&_.S(this,{step:"hide"})};_.S8.TAG_NAME="yt-toast-stage";_.J(dia,_.S8);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var hia=function(){var a=_.D("welcomeVersion",void 0);return a&&gia.has(a)?a:"default"},kia=function(a,b){var c=a.command;a=c.welcomeTheme||"WELCOME_THEME_SLOMO";a=iia["WELCOME_THEME_UNKNOWN"===a?"WELCOME_THEME_MEGAN":a];b=b.selectedIndex||0;var d=[];c=_.A(c.welcomeButtons||[]);for(var e=c.next();!e.done;e=c.next())(e=jia[e.value])&&d.push(e);switch(hia()){case "mult-acc-copy":var f=_.Iz(_.V("WELCOME_TITLE_MULTI_ACCOUNT"));var g=_.Iz(_.V("WELCOME_TAGLINE1_MULTI_ACCOUNT"));break;case "p13n-copy":f=
_.Iz(_.V("WELCOME_TITLE_P13N"));g=_.Iz(_.V("WELCOME_TAGLINE1_P13N"));break;case "subs-copy":f=_.Iz(_.V("WELCOME_TITLE_SUBS"));g=_.Iz(_.V("WELCOME_TAGLINE1_SUBS"));break;case "library-copy":f=_.Iz(_.V("WELCOME_TITLE_LIBRARY"));g=_.Iz(_.V("WELCOME_TAGLINE1_LIBRARY"));break;case "music-copy":f=_.Iz(_.V("WELCOME_TITLE_MUSIC"));g=_.Iz(_.V("WELCOME_TAGLINE1_MUSIC"));break;case "movies-copy":f=_.Iz(_.V("WELCOME_TITLE_MOVIES"));g=_.Iz(_.V("WELCOME_TAGLINE1_MOVIES"));break;case "default":f=_.Iz(_.V("WELCOME"));
g=_.Iz(_.V("WELCOME_TAGLINE1"));var h=_.Iz(_.V("WELCOME_TAGLINE2"))}return _.R("host",{className:a.yg},_.R("div",{className:"ytlr-welcome__yt-logo"}),_.R("div",{className:"ytlr-welcome__messages"},_.R(_.X,{className:"ytlr-welcome__title",data:f}),_.Y(!h,function(){return _.R(_.X,{className:"ytlr-welcome__tagline2",data:g})},function(){return[_.R(_.X,{className:"ytlr-welcome__tagline1",data:g}),_.R(_.X,{className:"ytlr-welcome__tagline2",data:h})]})),_.R("div",{className:"ytlr-welcome__theme-label-wrapper"},
_.R(_.ry,{className:"ytlr-welcome__theme-label-icon",icon:"VIDEO_YOUTUBE"}),_.R(_.X,{className:"ytlr-welcome__theme-label-text",data:a.label})),_.R(_.YL,{className:"ytlr-welcome__buttons",layout:"vertical",focused:!0,selectedIndex:b,M:this.M,sd:function(){return!0}},_.TC(d,function(l){return _.R(_.rA,Object.assign({},l))})))};_.m().w("syah");
var lia=_.p("YtlrWelcome","WhW0H");
var gia=new Set(Object.values({Kr:"default",AA:"mult-acc-copy",UA:"p13n-copy",eC:"subs-copy",tA:"library-copy",CA:"music-copy",zA:"movies-copy"}));
var T8={},iia=(T8.WELCOME_THEME_UNKNOWN=void 0,T8.WELCOME_THEME_HIGH_CONTRAST={label:"",yg:"ytlr-welcome--theme-high-contrast"},T8.WELCOME_THEME_SLOMO={label:"The Slow Mo Guys",yg:"ytlr-welcome--theme-slomo"},T8.WELCOME_THEME_VAGABROTHERS={label:"Vagabrothers",yg:"ytlr-welcome--theme-vaga"},T8.WELCOME_THEME_MEGAN={label:"Megan Davies",yg:"ytlr-welcome--theme-megan"},T8),U8={},jia=(U8.WELCOME_BUTTON_TYPE_UNKNOWN=void 0,U8.WELCOME_BUTTON_TYPE_DEVICE_SIGN_IN={label:_.V("SIGN_IN_TEXT"),icon:{iconType:"ACCOUNT_CIRCLE"},
style:1,className:"ytlr-welcome__menu-item",command:_.CA(_.Lz,{signInType:"SIGN_IN_METHOD_TYPE_NATIVE",signInStyle:"SIGN_IN_STYLE_FULLSCREEN"})},U8.WELCOME_BUTTON_TYPE_SEAMLESS_SIGN_IN={label:_.V("SIGNIN_SEAMLESS"),sublabel:_.V("SIGNIN_SEAMLESS_TAG"),icon:{iconType:"SMARTPHONE"},style:1,className:"ytlr-welcome__menu-item",command:_.CA(_.Lz,{signInType:"SIGN_IN_METHOD_TYPE_SEAMLESS",signInStyle:"SIGN_IN_STYLE_FULLSCREEN"})},U8.WELCOME_BUTTON_TYPE_URL_SIGN_IN={label:_.V("SIGNIN_URL"),sublabel:_.V("SIGNIN_URL_TAG"),
icon:{iconType:"PHONELINK"},style:1,className:"ytlr-welcome__menu-item",command:_.CA(_.Lz,{signInType:"SIGN_IN_METHOD_TYPE_URL",signInStyle:"SIGN_IN_STYLE_FULLSCREEN"})},U8.WELCOME_BUTTON_TYPE_DIRECT_SIGN_IN={label:_.V("SIGNIN_DIRECT"),sublabel:_.V("SIGNIN_DIRECT_TAG"),icon:{iconType:"TV"},style:1,className:"ytlr-welcome__menu-item",command:_.CA(_.Lz,{signInType:"SIGN_IN_METHOD_TYPE_DIRECT",signInStyle:"SIGN_IN_STYLE_FULLSCREEN"})},U8.WELCOME_BUTTON_TYPE_GUEST={label:_.V("SIGNED_OUT_GUEST"),icon:{iconType:"EXIT_TO_APP"},
style:1,className:"ytlr-welcome__menu-item",command:_.DA(_.Lz)},U8);
_.V8=function(a){_.Q.call(this,a);var b=this;this.template=kia;this.M=function(c){_.S(b,{selectedIndex:c})};this.state={selectedIndex:0}};_.G(_.V8,_.Q);_.V8.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.V8.TAG_NAME="ytlr-welcome";_.J(lia,_.V8);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var mia=function(a){var b=a.data,c=b.title;b=b.thumbnail;var d=a.focused,e=a.Ok;a=a.selected;var f={};return _.R("host",{className:(f["ytlr-guide-account-entry-renderer--guide-open"]=e,f),role:"tab","aria-hidden":!e||!a||!d,"aria-label":c&&c.accessibility&&c.accessibility.accessibilityData&&c.accessibility.accessibilityData.label||""},_.Y(d||!_.D("isLimitedMemory",!1),function(){return _.R("div",{className:"ytlr-guide-account-entry-renderer__highlight"})}),_.R(_.Pz,{className:"ytlr-guide-account-entry-renderer__avatar",
data:b||{},width:2.5}),_.R(_.X,{Jh:!0,className:"ytlr-guide-account-entry-renderer__label",data:c}))};_.m().w("syai");
var nia=_.Xx("dCisNd");
_.W8=function(){_.Q.apply(this,arguments);this.template=mia};_.G(_.W8,_.Q);_.W8.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.W8.TAG_NAME="ytlr-guide-account-entry-renderer";_.J(nia,_.W8);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var pia=function(a){var b=a.data,c=b.icon,d=a.focused,e=a.selected,f=a.zi;a=a.Ok;c=c&&c.iconType||"UNKNOWN";var g=oia[c]||"",h=_.Sx(b.formattedTitle);b={};var l={};return _.R("host",{className:(b["ytlr-guide-entry-renderer--sign-in"]="SIGN_IN"===c,b["ytlr-guide-entry-renderer--guide-open"]=a,b["ytlr-guide-entry-renderer--selected"]=e,b),"aria-hidden":!a||!e||!d,"aria-label":h,role:"tab"},_.Y(d||!_.D("isLimitedMemory",!1),function(){return _.R("div",{className:"ytlr-guide-entry-renderer__highlight"})}),
_.Y(f,function(){return _.R(_.X,{Jh:!0,className:["ytlr-guide-entry-renderer__label","ytlr-guide-entry-renderer__header"],data:h})}),_.Y(!d&&!f&&e,function(){return _.R("div",{className:"ytlr-guide-entry-renderer__underline"})}),_.R("div",{className:["ytlr-guide-entry-renderer__icon",g,(l["ytlr-guide-entry-renderer__icon--icon-selected"]=e,l)]}),_.R(_.X,{Jh:!0,className:"ytlr-guide-entry-renderer__label",data:h}))};_.m().w("syaj");
var qia=_.Xx("rWT9Be");
var X8={},oia=(X8.CARDBOARD="ytlr-guide-entry-renderer__icon--vr",X8.ENTERTAINMENT="ytlr-guide-entry-renderer__icon--entertainment",X8.GAMING="ytlr-guide-entry-renderer__icon--gaming",X8.HAPPENING_NOW="ytlr-guide-entry-renderer__icon--latest",X8.NEWS="ytlr-guide-entry-renderer__icon--latest",X8.MOVIES="ytlr-guide-entry-renderer__icon--entertainment",X8.MUSIC="ytlr-guide-entry-renderer__icon--music",X8.SEARCH="ytlr-guide-entry-renderer__icon--search",X8.SETTINGS="ytlr-guide-entry-renderer__icon--settings",
X8.SUBSCRIPTIONS="ytlr-guide-entry-renderer__icon--subscriptions",X8.TAB_LIBRARY="ytlr-guide-entry-renderer__icon--library",X8.WHAT_TO_WATCH="ytlr-guide-entry-renderer__icon--home",X8.YOUTUBE_RED_ORIGINALS="ytlr-guide-entry-renderer__icon--originals",X8);
_.Y8=function(){_.Q.apply(this,arguments);this.template=pia};_.G(_.Y8,_.Q);_.Y8.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;_.Y8.TAG_NAME="ytlr-guide-entry-renderer";_.J(qia,_.Y8);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var uia;_.Z8=function(a,b){return ria(b).findIndex(function(c){return a.searchEndpoint&&c.searchEndpoint?!0:a.browseEndpoint&&c.browseEndpoint?_.bI(a.browseEndpoint,c.browseEndpoint):_.YH(a)&&_.YH(c)?!0:a.signalNavigationEndpoint&&c.signalNavigationEndpoint?_.v.equals(a.signalNavigationEndpoint,c.signalNavigationEndpoint):a.applicationSettingsEndpoint&&c.applicationSettingsEndpoint?!0:!1})};
_.$8=function(a){var b=_.L.R();a.fetch({path:"/youtubei/v1/guide",payload:{context:{client:{tvAppInfo:{zylonLeftNav:!0}}}}}).subscribe(function(c){_.Eo(b,new _.O("guideResponseAction"),{response:c})})};
uia=function(a,b){var c=this,d=a.data,e=a.focused,f=a.Ai,g=a.g;a=a.Bl;var h=b.yi,l=_.D("enableDogfoodUI",!1),n={},q={},t={},w={};return _.R("host",{className:[sia[b.wc],(n["ytlr-guide-response--logo-hidden"]=b.Fi,n)]},_.R("div",{className:["ytlr-guide-response__logo",(q["ytlr-guide-response__logo--premium-user"]=tia(d),q["ytlr-guide-response__logo--instant-hide-logo"]=h,q["ytlr-guide-response__logo--dogfood"]=l,q)]}),_.Y(_.D("enableAnimations",!1),function(){return _.R("div",{className:"ytlr-guide-response__gradient"})}),
_.R("div",{className:["ytlr-guide-response__mask","ytlr-guide-response__mask--top",(t["ytlr-guide-response__mask--search-selected"]=this.C,t)]}),_.R("div",{className:["ytlr-guide-response__mask","ytlr-guide-response__mask--bottom",(w["ytlr-guide-response__mask--search-selected"]=this.C,w)]}),_.R(_.X,{data:this.selectedItemTitle,className:"ytlr-guide-response__subtitle"}),_.R(_.YL,{className:"ytlr-guide-response__container",layout:"vertical",selectedIndex:this.selectedIndex,M:this.M,focused:e,onBack:this.onBack,
onDown:g,onLeft:g,onRight:a,onUp:g,role:"tablist",nf:f},_.TC(a9(d),function(y,B){return _.R(_.xJ,{config:c.N(B),renderer:y})})))};_.m().w("syak");
var a9,b9,tia,via,ria;a9=_.wx(function(a){for(var b=[],c=a.items||[],d=0,e=c.length;d<e;d++){var f=c[d];f&&f.guideSectionRenderer&&f.guideSectionRenderer.items&&b.push.apply(b,_.F(f.guideSectionRenderer.items))}(a=b9(a))&&b.push(a);return b});_.c9=_.wx(function(a){a=a9(a);for(var b=0,c=a.length;b<c;b++){var d=a[b].guideEntryRenderer;if((d=d&&d.navigationEndpoint&&d.navigationEndpoint.browseEndpoint)&&!d.params&&("FEtopics"===d.browseId||"default"===d.browseId))return b}return Math.min(2,a.length)});
b9=_.wx(function(a){return a.footer&&a.footer.guideSectionRenderer&&a.footer.guideSectionRenderer.items&&a.footer.guideSectionRenderer.items[0]});tia=_.wx(function(a){a=a9(a)[0];return!!(a&&a.guideAccountEntryRenderer&&a.guideAccountEntryRenderer.hasUnlimitedEntitlement)});via=_.wx(function(a){return(a=a9(a)[0])&&a.guideAccountEntryRenderer?!0:"SIGN_IN"===(a&&a.guideEntryRenderer&&a.guideEntryRenderer.icon&&a.guideEntryRenderer.icon.iconType)});
ria=_.wx(function(a){return a9(a).map(function(b){return(b.guideAccountEntryRenderer||b.guideEntryRenderer||{}).navigationEndpoint||{}})});
var wia=_.p("YtlrGuideResponse","OoTvYc");
var d9={},sia=(d9[0]="",d9[1]="ytlr-guide-response--hide-horizontal",d9[2]="ytlr-guide-response--hide-horizontal-collapsing",d9[3]="ytlr-guide-response--hide-vertical",d9[4]="ytlr-guide-response--prepare-show-horizontal",d9[5]="ytlr-guide-response--prepare-show-vertical",d9[6]="ytlr-guide-response--show-horizontal",d9[7]="ytlr-guide-response--show-vertical",d9[8]="ytlr-guide-response--collapsing",d9[9]="ytlr-guide-response--expanding",d9[10]="ytlr-guide-response--prepare-expanding",d9);
_.g9=function(a){_.Q.call(this,a);var b=this;this.template=uia;this.bedrockPorts=_.ov();this.A=this.h=0;this.B=!1;this.j=_.L.R();this.enableAnimations=_.D("enableAnimations",!0);this.ra=_.r.get(_.kA);this.navigation=_.r.get(_.KI);this.P=_.qd("reset_guide_delay_ms",3E3);this.H=_.wx(function(c,d){c=d[c];return!!(c&&c.guideEntryRenderer&&c.guideEntryRenderer.navigationEndpoint&&c.guideEntryRenderer.navigationEndpoint.searchEndpoint)});this.G=_.wx(function(c,d){d=(c=d[c])&&c.guideEntryRenderer&&c.guideEntryRenderer.navigationEndpoint&&
c.guideEntryRenderer.navigationEndpoint.browseEndpoint&&c.guideEntryRenderer.navigationEndpoint.browseEndpoint.browseId;return("FEtopics"===d||"default"===d)&&c.guideEntryRenderer.formattedTitle||""});this.J=_.wx(function(c){for(var d=[],e=0,f=c.length;e<f;++e){var g=c[e];g=(null===g||void 0===g?void 0:g.guideAccountEntryRenderer)||(null===g||void 0===g?void 0:g.guideEntryRenderer);(g=null===g||void 0===g?void 0:g.trackingParams)&&d.push(g)}return d});this.F=_.$G(function(c){b.B=!1;var d=2<=b.h;b.h=
0;var e=b.state.disabled||b.props.disabled,f=!c&&b.props.focused;b.isVisible===c||e||f||(d?c?b.enableAnimations?e9(b,5,7):e9(b,7):e9(b,3):c?b.enableAnimations?e9(b,4,6):e9(b,6):e9(b,8===b.state.wc?2:1))},0);this.M=function(c){if(b.props.data.selectedIndex!==c){var d=a9(b.props.data)[c];(d=d.guideEntryRenderer||d.guideAccountEntryRenderer)?d.navigationEndpoint?(f9(b,!1),_.U(b,"innertube-command",d.navigationEndpoint),b.props.data.selectedIndex=c,b.props.Ai&&_.qx(b)):_.C(Error("Jc")):_.C(Error("Kc"))}};
this.onBack=function(c){var d=(0,_.c9)(b.props.data);b.selectedIndex===d?b.props.ak&&b.props.ak():b.M(d);_.P(c)};this.S=_.wx(function(c){c=a9(c);for(var d=[],e={};d.length<c.length;)e.Bh=d.length,d.push(function(f){return function(){b.M(f.Bh)}}(e)),e={Bh:e.Bh};return d});this.N=function(c){var d=b.selectedIndex===c&&10!==b.state.wc,e=via(b.props.data)&&0===c,f=!!b9(b.props.data)&&a9(b.props.data).length-1===c,g=b.props.focused&&9===b.state.wc;c={zi:e,Ok:g,focused:d&&g,selected:d,onSelect:b.S(b.props.data)[c],
className:e?"ytlr-guide-response__header":f?"ytlr-guide-response__footer":""};return{guideAccountEntryRenderer:{I:_.W8,props:c},guideEntryRenderer:{I:_.Y8,props:c}}};this.state={wc:0}};_.G(_.g9,_.Q);_.g9.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;
_.g9.prototype.O=function(){var a=this;this.props.data.qf&&(this.props.data.qf=!1,h9(this));_.T(this,_.N(this.j,new _.O("SHOW_GUIDE"),this,this.D));_.T(this,_.N(this.j,new _.O("HIDE_GUIDE"),this,this.D));_.T(this,_.N(this.j,new _.O("showLogoAction"),this,function(){f9(a,!1)}));_.T(this,_.N(this.j,new _.O("hideLogoAction"),this,function(b){f9(a,!0,b.Kk)}));_.T(this,_.N(this.j,new _.O("disableGuideAction"),this,function(){a.state.disabled||_.S(a,{disabled:!0})}));_.T(this,_.N(this.j,new _.O("enableGuideAction"),
this,function(){a.state.disabled&&_.S(a,{disabled:!1})}));_.T(this,_.N(this.j,new _.O("resetGuideAction"),this,function(){a.isVisible||a.state.disabled||a.props.disabled||h9(a)}));this.props.Ai&&(setInterval(function(){_.$8(a.ra)},36E5),_.T(this,this.navigation.subscribe(function(b){b=_.Z8(b.command,a.props.data);0>b||a.props.data.selectedIndex===b||(a.props.data.selectedIndex=b,i9(a))})));this.props.disabled||this.state.disabled||j9(this,a9(this.props.data))};
_.g9.prototype.W=function(a){this.props.data.qf&&(this.props.data.qf=!1,h9(this));a.focused&&!this.props.focused?e9(this,8):!a.focused&&this.props.focused&&(this.props.Bl&&this.props.Bl(),this.isVisible||!this.enableAnimations?e9(this,9):e9(this,10,9));this.props.disabled||this.state.disabled||j9(this,a9(this.props.data))};_.g9.prototype.shouldUpdate=function(a,b){return a.data.qf||_.Q.prototype.shouldUpdate.call(this,a,b)};
var j9=function(a,b){var c=a.bedrockPorts;c&&_.f2("ytlr-guide-response",function(){return a.J(b)},function(d){c&&(c.attachChild(a.props.data.trackingParams),c.markVisible(d.map(_.g2)))})};_.g9.prototype.D=function(a){clearTimeout(this.A);a=a.command.signalAction&&a.command.signalAction.signal;"SHOW_GUIDE"===a?(this.h+=1,this.F(!this.B||2<this.h)):"HIDE_GUIDE"===a&&(this.h+=1,this.B=!0,this.F(!1))};
var f9=function(a,b,c){c=void 0===c?!1:c;clearTimeout(a.A);b===a.state.Fi&&c===a.state.yi||_.S(a,{Fi:b,yi:c})},e9=function(a,b,c){9===b&&_.U(a,"guide-expanding");_.S(a,{wc:b});c&&_.Mt({X:function(){a.state.wc===b&&e9(a,c)},stage:40})},h9=function(a){clearTimeout(a.A);a.props.focused||(a.A=setTimeout(function(){a.props.focused||a.state.yi||i9(a)},a.P))},i9=function(a){_.S(a,{wc:0,Fi:!1})};
_.E.Object.defineProperties(_.g9.prototype,{selectedItemTitle:{configurable:!0,enumerable:!0,get:function(){return this.G(this.selectedIndex,a9(this.props.data))}},C:{configurable:!0,enumerable:!0,get:function(){return this.H(this.selectedIndex,a9(this.props.data))}},isVisible:{configurable:!0,enumerable:!0,get:function(){return 1!==this.state.wc&&2!==this.state.wc&&3!==this.state.wc}},selectedIndex:{configurable:!0,enumerable:!0,get:function(){return void 0===this.props.data.selectedIndex?(0,_.c9)(this.props.data):
this.props.data.selectedIndex}}});_.g9.TAG_NAME="ytlr-guide-response";_.J(wia,_.g9);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var l9=function(a,b){return a&&b?k9({qc:a.left-b.left,Gd:a.top-b.top,Bd:b.width?a.width/b.width:1,Cd:b.height?a.height/b.height:1}):{qc:0,Gd:0,Bd:1,Cd:1}},k9=function(a){return Object.assign(Object.assign({},a),{qc:_.D("isRtl",!1)?-a.qc:a.qc})},yia=function(a,b){var c=a.g,d=b.transition,e=b.Ka;b=["ytlr-player__player-container",xia[b.fd]];a.enabled&&(e=e.concat(["ytlr-player--enabled"]));c&&(b=b.concat(["ytlr-player__player-container--test-mode"]));a=_.D("supportsPlayerResizeAnimation",!1)&&d?{transform:m9(l9(this.state.lb,
this.state.ac)),transitionDuration:d.durationMs+"ms",transitionTimingFunction:d.timingFunction}:{transform:m9(l9(this.state.lb,this.state.ac))};e={className:e,style:a};a=(a=_.GG)?a.getVideoContentRect():null;c=this.state.lb;d=this.state.ac;var f="cover"===this.state.Ld;if(a&&c&&d){var g=c.width/a.width,h=c.height/a.height;f=(void 0===f?0:f)?Math.max(g,h):Math.min(g,h);g=d.width?c.width/d.width:1;d=d.height?c.height/d.height:1;a=k9({qc:-((a.width+2*a.left)*f-c.width)/2/g,Gd:-((a.height+2*a.top)*f-
c.height)/2/d,Bd:f/g,Cd:f/d})}else a={qc:0,Gd:0,Bd:1,Cd:1};a=m9(a);return _.R("host",e,_.R("div",{className:b,style:{transform:a}},_.vw))},zia=function(a,b){return a.h.g("value-changed",b)};_.m().w("syal");
var Aia=_.p("YtlrPlayer","IcQfQb");
var n9={},xia=(n9["default"]="",n9.preview="ytlr-player__player-container--preview",n9["above-controls"]="ytlr-player__player-container--above-controls",n9);
_.o9=function(a){_.Q.call(this,a);this.template=yia;this.C=_.r.get(_.oJ);this.j=_.r.get(_.w5);this.A=null;this.h=-1;this.D=_.L.R();this.B=!1;this.state=this.j.styles};_.G(_.o9,_.Q);_.o9.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;
_.o9.prototype.O=function(){var a=this;this.A=this.el.firstChild;this.C.load(this.A,"WEB_PLAYER_CONTEXT_CONFIG_ID_LIVING_ROOM_WATCH");_.T(this,zia(this.j,function(b){var c=a.state.transition,d=b.transition;c&&-1!==a.h&&(c&&d&&!_.v.equals(c,d)||c!==d)&&(clearTimeout(a.h),a.h=-1,_.u5(a.j,{transition:null}),c.cancelCallback());_.S(a,b);Bia(a)}));_.T(this,_.jJ(this.C,function(){_.qx(a)}));_.T(this,_.N(this.D,new _.O("TOGGLE_VIDEO_INFO"),this,function(){Cia(a)}))};
var m9=function(a){a={translateX:a.qc+"px",translateY:a.Gd+"px",scaleX:""+a.Bd,scaleY:""+a.Cd};var b=[],c;for(c in a)c&&b.push(c+"("+a[c]+")");return b.join(" ")},Bia=function(a){var b=a.state.transition;if(b){var c=function(){a.h=-1;_.u5(a.j,{transition:null});b.completeCallback()};_.D("supportsPlayerResizeAnimation",!1)?a.h=_.Wx({eventType:_.bn,element:a.A,X:c,ud:b.durationMs}):c()}},Cia=function(a){a.B?_.EG().then(function(b){b.hideVideoInfo();a.B=!1}):_.EG().then(function(b){b.showVideoInfo();
a.B=!0})};_.o9.TAG_NAME="ytlr-player";_.J(Aia,_.o9);

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Dia=function(a){return _.R("host",{className:this.h(a)},_.R(_.bK,{style:1,je:!0}))};_.m().w("syam");
var Eia=_.p("YtlrPlaceholderOverlaySection","KH7Xnf");
_.p9=function(){_.Q.apply(this,arguments);var a=this;this.template=Dia;this.h=_.wx(function(b){var c,d=!(null===(c=b.data.overlay)||void 0===c||!c.overlayTwoPanelRenderer),e={};return e["ytlr-placeholder-overlay-section--side-panel"]=d,e["ytlr-placeholder-overlay-section--fullscreen"]=!d,e["ytlr-placeholder-overlay-section--is-visible"]=b.isVisible,e["ytlr-placeholder-overlay-section--is-showing"]=a.isAnimated&&b.isVisible,e})};_.G(_.p9,_.Q);_.p9.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;
_.p9.prototype.W=function(a){this.props.isVisible!==a.isVisible&&(a=Fia(this),this.isAnimated?_.Wx({eventType:_.bn,element:this.el,X:a,ud:5E3*(this.props.isVisible?.2:.1)}):_.Mt({stage:40,X:a}))};var Fia=function(a){return a.props.isVisible&&a.props.Eb?a.props.Eb:!a.props.isVisible&&a.props.rb?a.props.rb:function(){}};_.E.Object.defineProperties(_.p9.prototype,{isAnimated:{configurable:!0,enumerable:!0,get:function(){return!!_.D("enableAnimations",!0)}}});_.p9.TAG_NAME="ytlr-placeholder-overlay-section";
_.J(Eia,_.p9);

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("syan");
_.q9={NONE:"none",RC:"welcome",mq:"account-selector"};_.r9=Object.keys(_.q9);
var Gia=_.p("StartupScreenTracker","Q81C2c");
var s9=function(){this.recurringActionTracker=_.r.get(_.oK)};s9.prototype.info=function(){return this.recurringActionTracker.info()};_.t9=_.K(Gia,function(){return new s9});

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("Bw715b");
var MT=_.p("StorageIntegrityMonitor","Bw715b");
var OT=function(){this.localStorage=_.r.get(_.vd);this.supportsReadingCookies=_.D("supportsReadingCookies",!0);var a=void 0!==this.localStorage.get(NT),b=_.Yc.wa("yt-dev.storage-integrity");this.state=b&&a?"TV_DEVICE_STORAGE_STATE_ALL":b?"TV_DEVICE_STORAGE_STATE_COOKIE":a?"TV_DEVICE_STORAGE_STATE_LOCALSTORAGE":"TV_DEVICE_STORAGE_STATE_NONE";try{this.localStorage.set(NT,!0)}catch(c){_.C(c)}try{_.Yc.set("yt-dev.storage-integrity","true",{domain:document.domain||window.location.hostname,Gi:31536E4,path:"/"})}catch(c){_.C(c)}},
NT;OT.prototype.getState=function(){return this.state};_.PT=function(a){return a.supportsReadingCookies?"TV_DEVICE_STORAGE_STATE_ALL":"TV_DEVICE_STORAGE_STATE_LOCALSTORAGE"};_.QT=function(a){return"TV_DEVICE_STORAGE_STATE_ALL"===a.getState()?!1:a.getState()!==_.PT(a)};
_.RT=function(a){var b="BAD",c="BAD",d=a.getState();a=_.PT(a);switch(d){case "TV_DEVICE_STORAGE_STATE_ALL":c=b="OK";break;case "TV_DEVICE_STORAGE_STATE_LOCALSTORAGE":c="OK";"TV_DEVICE_STORAGE_STATE_LOCALSTORAGE"===a&&(b="N/A");break;case "TV_DEVICE_STORAGE_STATE_COOKIE":"TV_DEVICE_STORAGE_STATE_LOCALSTORAGE"!==a&&(b="OK");break;case "TV_DEVICE_STORAGE_STATE_NONE":b="TV_DEVICE_STORAGE_STATE_LOCALSTORAGE"!==a?"NEW":"N/A",c="NEW"}return" Cookie storage: "+b+"  Local storage: "+c};_.ST=_.K(MT,function(){return new OT});
NT=new _.Dl("storage-integrity");

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.m().w("JudhL");
_.u9=[];

_.m().l();

}catch(e){_._DumpException(e)}
try{
_.y9=function(a){_.x9=Object.assign(Object.assign({},_.x9),a)};_.m().w("m3vGtb");
_.x9={um:!1,Vl:!1,ej:!1,Wl:!1};

_.m().l();

}catch(e){_._DumpException(e)}
try{
var Iia=function(){if(!z9){_.jA&&(_.fd=_.jA,_.db("ytLoggingEventsDefaultDisabled",!0));var a=_.gb("log_web_meta_interval_ms");a||(a=3E5);_.ob(_.id,a);z9=!0}},Jia=function(a){return function(b){return b.Sa(new A9(a))}},B9=function(a){var b=0;-1!=a.indexOf("h")&&(a=a.split("h"),b=3600*a[0],a=a[1]);-1!=a.indexOf("m")&&(a=a.split("m"),b=60*a[0]+b,a=a[1]);-1!=a.indexOf("s")?(a=a.split("s"),b=1*a[0]+b):b=1*a+b;return b},C9=function(a){var b;return(null===(b=a.commandExecutorCommand)||void 0===b?void 0:
b.commands)||[]},D9=function(a){var b=a.videoId;a=a.startTimeSeconds;var c={};b&&(c.videoId=b);a&&(c.startTimeSeconds=B9(a));return{watchEndpoint:c}},E9=function(a){a=Number(a);return isNaN(a)?0:a},F9=function(a){var b=a.playlistId;a=a.index;var c={};b&&(c.playlistId=b);a&&(c.index=E9(a));return{watchPlaylistEndpoint:c}},G9=function(a){var b=a.type;a=a.time;var c={playerControlType:b};a&&(c.playerSeekTimeInMillis=String(1E3*("PLAYER_CONTROL_ACTION_TYPE_PLAY"===b||"PLAYER_CONTROL_ACTION_TYPE_PAUSE"===
b?B9(a):E9(a))));return{playerControlAction:c}},Kia=function(a){return{startAccountSelectorCommand:{items:a.map(function(b){return{accountItemRenderer:b}})}}},Mia=function(){var a=[];if("account_manager"===_.D("supportedIdentityEnvironment","oauth"))a.push("WELCOME_BUTTON_TYPE_DEVICE_SIGN_IN");else{var b=_.D("directSignInConfig",H9).isSupported,c=_.CS(_.r.get(_.DS));b&&a.push("WELCOME_BUTTON_TYPE_DIRECT_SIGN_IN");c&&a.push("WELCOME_BUTTON_TYPE_SEAMLESS_SIGN_IN");2>a.length&&a.push("WELCOME_BUTTON_TYPE_URL_SIGN_IN")}a.push("WELCOME_BUTTON_TYPE_GUEST");
a={welcomeButtons:a,welcomeTheme:Lia};a=void 0===a?{welcomeButtons:["WELCOME_BUTTON_TYPE_DEVICE_SIGN_IN","WELCOME_BUTTON_TYPE_GUEST"],welcomeTheme:"WELCOME_THEME_SLOMO"}:a;return{startWelcomeCommand:{welcomeButtons:a.welcomeButtons,welcomeTheme:a.welcomeTheme}}},I9=function(){var a=_.GG;return a&&"auto"!==a.getUserPlaybackQualityPreference()?a.getPlaybackQuality():"auto"},Nia=function(a,b,c){c=void 0===c?!1:c;var d=[];if(b){b=_.A(b);for(var e=b.next();!e.done;e=b.next())if(e=e.value.accountItemRenderer)e.serviceEndpoint=
_.EA(a,e.serviceEndpoint),d.push({accountItemRenderer:e})}c&&d.push({compactLinkRenderer:{title:_.Iz(_.V("GUEST_TEXT")),serviceEndpoint:_.DA(a),thumbnail:_.AO(48)}});d.push({compactLinkRenderer:{title:_.Iz(_.V("ADD_ACCOUNT_TEXT")),serviceEndpoint:_.CA(a),icon:{iconType:"ADD"}}});return d},J9=function(a){return _.r.get(_.tv).get().then(function(b){_.pv(_.r.get(_.iv),"directSignInEvent",a,b)})},Oia=function(a){return _.Tu(this,function c(){var d,e,f,g;return _.Su(c,function(h){if(1==h.g)return d=_.pd().oAuthClientProfiles[1],
e={client_id:d.clientId,client_secret:d.rg,code:a,grant_type:"authorization_code",redirect_uri:d.Yi},f={method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(e)},_.Ku(h,2),_.Ju(h,_.jq(_.Yp("/o/oauth2/token",f)).then(function(l){return l.json()}),4);if(2!=h.g){g=h.h;if(g.error)throw Error("Xb");return h.return(g)}_.Mu(h);throw new K9(2);})})},Pia=function(a,b){return _.Tu(this,function d(){var e,f;return _.Su(d,function(g){if(1==g.g)return e={refreshToken:b,clientIdName:1},
_.Ku(g,2),_.Ju(g,_.jq(_.Rv(_.r.get(_.Tv),e,a,!0)),4);if(2!=g.g){f=g.h;if(!_.eT(f))throw Error("Yb");return g.return(f)}_.Mu(g);throw new K9(3);})})},Qia=function(a,b,c){return _.Tu(this,function e(){var f;return _.Su(e,function(g){if(1==g.g)return f=_.eT(a).identity,_.Ku(g,2),_.Ju(g,_.r.get(_.nv).Vc(f,b,c,"SIGN_IN_METHOD_TYPE_DIRECT"),4);if(2!=g.g)return _.Lu(g,0);_.Mu(g);throw new K9(4);})})},Ria=function(a,b){b=void 0===b?"ACCOUNT_EVENT_TRIGGER_STANDARD":b;return _.Tu(this,function d(){var e,f,
g,h,l;return _.Su(d,function(n){switch(n.g){case 1:return _.Ju(n,Oia(a),2);case 2:return e=n.h,f=e.access_token,g=e.refresh_token,_.Ju(n,Pia(f,g),3);case 3:h=n.h;if(1<h.size)throw new K9(1);l={accessToken:f,refreshToken:g};return _.Ju(n,Qia(h,l,b),4);case 4:return n.return(h)}})})},Uia=function(a){return new _.Zg(function(b){var c=_.r.get(_.vd),d=Object.assign(Object.assign({},Sia),c.get(_.Fl));c.remove(_.Fl);c=_.Pa(a,"oxrt");var e=_.Pa(a,"code"),f=_.Pa(a,"oxe");if(c){var g=f?new L9(f?JSON.parse(f):
null):void 0;(_.ES(g)||_.FS(g))&&J9(Tia(g));var h,l,n;e?n=Ria(e,d.context.eventTrigger).then(function(q){J9({event_type:5});l=q}).catch(function(q){J9({event_type:3,details:{errorType:q.type}});h=q.type}):n=Promise.resolve();n.then(function(){var q=_.r.get(_.PS);q.data={mg:d,$j:g,pb:l,Jm:h};b({oa:!0,command:q.getResult().Mi})})}else b({oa:!1})})},Via=function(){var a=_.r.get(_.PS);return a.data?_.I(a.getResult().kl):_.I(void 0)},Wia=function(a,b){if(!(a in _.I5))return{};a=_.V(M9[a]);var c=_.V(M9[b]);
a=_.zA({title:a,subtitle:c});a=_.AA(a);c=_.y5();var d=_.z5(),e=_.A5(b);b={compactLinkRenderer:{title:{simpleText:_.V("FEEDBACK_VIEW_REPORT")},serviceEndpoint:{openClientOverlayAction:{type:"CLIENT_OVERLAY_TYPE_REPORT_FEEDBACK_INFO",context:""+b}}}};a.footer={overlayPanelItemListRenderer:{items:[c,d,e,b]}};return _.vA({overlayPanelRenderer:a})},Xia=function(){for(;_.u9.length;)try{_.u9.pop()()}catch(a){_.C(a)}},Zia=function(a){var b,c;if(null===(c=null===(b=a.navigator)||void 0===b?void 0:b.mediaDevices)||
void 0===c||!c.enumerateDevices)return _.LA(Error("Mc"));var d=new _.Zg(function(e,f){setTimeout(function(){f(Error("Nc"))},2E3)});return Yia([d,a.navigator.mediaDevices.enumerateDevices()])},N9=function(){var a=void 0===a?window:a;return _.ih(Zia(a).then(function(b){return b.some(function(c){return"audioinput"===c.kind})?0:1}),function(b){_.C(b);return 2})},$ia=function(a){if(_.Wa(a.location,"env_enableVoice"))return _.I(!0);if(_.x9.Vl)return _.x9.ej?N9().then(function(e){return 1!==e}):_.I(!0);
if(_.x9.um){var b=_.Za(),c=b.model_year,d=b.chipset;if("LG"!==b.brand||2018!==c||!["LM18A","M16PP","O18"].includes(d)||[", _TV_LM18A/",", _TV_M16PP/",", _TV_O18/"].some(function(e){return a.navigator.userAgent.includes(e)}))return _.I(!0)}return _.x9.ej?N9().then(function(e){return 0===e}):_.I(!1)},O9=function(a){if(_.Wa(a.location,"env_enableWebSpeechApi"))return!0;var b=a.webkitSpeechRecognition;return!(!a.SpeechRecognition&&!b)},aja=function(a){a=void 0===a?window:a;return $ia(a).then(function(b){b=
b&&O9(a);var c=a;var d;c=_.Wa(c.location,"env_enableWebRtc")?!0:_.x9.Wl&&!(null===(d=null===c||void 0===c?void 0:c.navigator)||void 0===d||!d.mediaDevices);_.bb({enableVoice:b,enableWebRtc:c,enableWebSpeechApi:O9(a)})})},dja=function(){P9||(_.Yb(),_.jA&&(_.Xr=Q9=_.jA),R9("FOREGROUND_HEARTBEAT_TRIGGER_ON_FOREGROUND"),_.ob(_.Eb(R9,"FOREGROUND_HEARTBEAT_TRIGGER_ON_INTERVAL"),3E4),bja.push(_.Vb(document,"visibilitychange",cja)),P9=!0);var a=_.cs();a&&"UNDEFINED_CSN"!==a&&_.Yr(a)},cja=function(){if("hidden"==
_.OH())var a="FOREGROUND_HEARTBEAT_TRIGGER_ON_BACKGROUND";else"visible"==_.OH()&&(a="FOREGROUND_HEARTBEAT_TRIGGER_ON_FOREGROUND",S9=null);a&&R9(a)},R9=function(a){if("FOREGROUND_HEARTBEAT_TRIGGER_ON_BACKGROUND"==a||"FOREGROUND_HEARTBEAT_TRIGGER_ON_FOREGROUND"==a){if(T9==a)return;T9=a}var b=9E4+2E3*Math.random();if("FOREGROUND_HEARTBEAT_TRIGGER_ON_INTERVAL"!=a||!(_.ac()>b)&&"visible"==_.OH()){b=-1;S9&&(b=Math.round((0,_.xb)()-S9));var c=String;var d=_.lb("_fact",window);d=null==d||-1==d?-1:Math.max((0,_.Sb)()-
d,0);a={firstActivityMs:c(d),clientDocumentNonce:_.Wr,index:String(U9),lastEventDeltaMs:String(b),trigger:a};_.fc("foregroundHeartbeat",a,Q9);_.ab("_fact",-1,window);U9++;S9=(0,_.xb)()}},V9=function(){return eja.map(function(a){return _.r.get(a)})},hja=function(a){var b=!1,c=_.pd().mdxInitData;if(null===c||void 0===c?0:c.rl)a.zp(c.rl),b=!0;_.r.get(_.sH).init(a);_.r.get(_.SC).init(function(){return _.r.get(_.Kha)});b||(fja(),gja())},fja=function(){var a=_.L.R();_.N(a,new _.O("mdx-session:connected"),
W9,function(){for(var b=_.ZF(),c=_.A(V9()),d=c.next();!d.done;d=c.next())d=d.value,d.g=b,d.A()});_.N(a,new _.O("mdx-session:disconnected"),W9,function(b){for(var c=_.A(V9()),d=c.next();!d.done;d=c.next()){d=d.value;for(var e=b.reason;0<d.F.length;)d.F.pop()();d.g=null;d.C(e)}});_.N(a,new _.O("mdx-session:remoteStatusChanged"),W9,function(){var b=_.r.get(_.gG),c=_.r.get(_.cu);b=b.Ji();c.j=b});_.N(a,new _.O("requestReversePairingCommand"),W9,function(b){(b=b.command.requestReversePairingCommand&&b.command.requestReversePairingCommand.pairingCode)&&
_.r.get(_.gG).Ph(b)});_.N(a,new _.O("MDX_UNLINK_ALL_DEVICES"),W9,function(){_.r.get(_.gG).zg()})},gja=function(){var a=_.r.get(_.gG),b=_.r.get(_.eD),c=_.pd().mdxInitData,d=c&&c.Vf,e=c&&c.deviceId,f=c&&c.Pb;b.g.isInAppServer()&&_.r.get(_.nha).initialize();b=_.aG(b)&&!b.g.isInAppServer();c=!!a.md().length;var g="InitializeMdx,";d?g+="dialPairing":c?g+="manualPairing":b&&(g+="smoothPairing");(d||c||b)&&a.connect(g).then(function(h){h.success?(e&&f&&a.wl(e,f),d&&a.Ki(d)):_.C(Error("Wc`"+g+"`"+h.disconnectReason))})},
ija=function(a){if(!a.get(_.Ir)){var b=a.get(_.Gr);if(b){var c=_.v.map(b,function(e,f){a:switch(e.oAuthClientIdName){case "direct":var g=1;break a;default:g=0}return{identityCredentials:{refreshToken:e.refreshToken,clientIdName:g},identityType:"CORE_ID",effectiveObfuscatedGaiaId:f,ownerObfuscatedGaiaId:f,gaiaDelegationType:1}});a.set(_.Jr,c);if(b=a.get(_.Hr)){c=_.A(Object.values(c));for(var d=c.next();!d.done;d=c.next())if(d=d.value,d.effectiveObfuscatedGaiaId===b.gaiaId){a.set(_.Ir,d);return}a.remove(_.Jr)}}a.set(_.Ir,
_.Er)}},kja=function(){if("oauth"===_.D("supportedIdentityEnvironment","oauth")){var a=_.r.get(_.vd);ija(a);a.remove(_.Gr);a.remove(_.Hr);a=document.domain||window.location.hostname;for(var b=_.A(jja),c=b.next();!c.done;c=b.next())_.Yc.remove(c.value,"/",a)}},mja=function(){var a=_.r.get(_.nv),b=_.r.get(_.gu),c=_.L.R();_.N(c,new _.O("removeIdentityCommand"),document,function(d){var e=d.command.removeIdentityCommand;X9(a,e.effectiveObfuscatedGaiaId).then(function(f){f={identity:f,identityActionContext:e.identityActionContext,
accountName:e.accountName};_.Eo(c,new _.O("removeIdentityAction"),f)})});_.N(c,new _.O("removeIdentityAction"),document,function(d){var e=d.identityActionContext;lja(b,d.identity).then(function(f){if(f){f=d.identity;var g=a.A,h=e&&e.eventTrigger;h=_.kv(h);g.g.send("accountRegistryChange",{changeType:"ACCOUNT_REGISTRY_CHANGE_TYPE_REMOVE_ACCOUNT",trigger:h},f);a.g&&delete a.g[f.effectiveObfuscatedGaiaId];_.Eo(c,new _.O("onIdentityRemovedAction"),d)}})})},Y9=function(a){nja[a.keyCode]&&_.bw(a,!1,!0)},
Z9=function(a){if(2!==a.button){var b=a.target,c=_.Et(b,oja[a.type],13,!0,a);b.dispatchEvent(c);a.preventDefault()}},$9=function(a){if(!a.Kh){_.Ub();var b=Number.isInteger(_.C8[a.keyCode])?_.C8[a.keyCode]:a.keyCode;b!==a.keyCode&&(_.bw(a,!1,!0),b=_.Et(a.target,a.type,b,!0,a),a.target.dispatchEvent(b))}},pja=function(a){if(0!==a.deltaY){var b=document.activeElement;b&&(a=_.Et(b,"keydown",0>a.deltaY?38:40,!0,a),b.dispatchEvent(a))}},rja=function(){var a=document;var b=void 0===b?window.navigator.userAgent:
b;var c=_.Za();"lg"===c.brand.toLowerCase()&&_.D8({404:172,405:170,406:191});b.toLowerCase().includes("tizen")&&_.D8(_.B8);/^ps4( pro )? vr$/i.test(c.model)?(delete _.C8[32777],b={},_.D8((b[179]=32777,b))):/^xboxone\b/i.test(c.model)?(delete _.C8[32776],delete _.C8[32777]):c.model&&"switch"===c.model.toLowerCase()&&(b={},_.D8((b[32770]=170,b[32771]=172,b)));b=window;c={};b.VK_FAST_FWD&&(c[b.VK_FAST_FWD]=228);b.VK_REWIND&&(c[b.VK_REWIND]=227);b.VK_TRACK_PREV&&(c[b.VK_TRACK_PREV]=177);b.VK_TRACK_NEXT&&
(c[b.VK_TRACK_NEXT]=176);b.VK_PLAY&&(c[b.VK_PLAY]=250);b.VK_STOP&&(c[b.VK_STOP]=178);_.D8(c);_.Yb();a.addEventListener("keydown",Y9,!0);a.addEventListener("keyup",Y9,!0);a.addEventListener("keydown",$9,!0);a.addEventListener("keyup",$9,!0);a.addEventListener("mousedown",Z9,!0);a.addEventListener("mouseup",Z9,!0);a.addEventListener("wheel",qja,!0)},sja=function(a,b){var c=a.I,d=["yt-route--inactive","zylon-unfocusable"];return _.R("host",{className:a.active?"yt-route--active":d},_.Y(a.active,function(){return _.R(c,
Object.assign({},a.props))}),_.Y(!a.active&&a.Wj&&b.oi,function(){return _.R(c,Object.assign({},a.Wj,{isHidden:!0}))}))},wja=function(a){var b=this,c=a.data,d=a.hasBackButton,e=a.command,f=a.isLoggedIn;a=e&&e.browseEndpoint&&e.browseEndpoint.browseId;e="FEsubscriptions"===a||"FEmy_youtube"===a;a="FEfiltered_browse"===a;var g=!(!c||!c.settingsResponse),h=_.D("enableLeftNav",!1);f=e&&!f;var l=!_.v.isEmpty(c&&c.browseResponse||{});f=!f&&!a$(this);var n=this.isFocused("primary-nav")&&l,q={};return _.R("host",
{className:(q["ytlr-surface-page--enable-left-nav"]=h,q["ytlr-surface-page--has-back-button"]=d,q)},_.Y(h&&d&&_.D("enableAnimations",!1),function(){return _.R("div",{idomKey:"mask",className:"ytlr-surface-page__mask"})}),_.Y(d,function(){var t={};return _.R(_.i5,{idomKey:"back",role:"button",ariaLabel:n?_.V("BACK"):"",className:["ytlr-surface-page__back",(t["ytlr-surface-page__back--back-focused"]=n,t["ytlr-surface-page__back--back-hidden"]=h&&b.state.hideBackButton,t)],command:tja,icon:h?"ARROW_BACK":
"CHEVRON_LEFT",focusId:"primary-nav",onRightFocus:"browse",focused:b.isFocused("primary-nav")&&l})}),_.R(_.gK,Object.assign({idomKey:"ghost",hidden:!f},uja(d,h,e,g,a))),_.R(_.xJ,{config:this.D(this.isFocused("browse")),renderer:c}),_.R(_.KO,{Sc:vja(this)}))},uja=function(a,b,c,d,e){d=c||d;var f={};return{className:[(f["ytlr-surface-page__add-left-margin"]=a,f["ytlr-surface-page__ghost-filtered-browse"]=e,f["ytlr-surface-page__ghost-top-nav-shelves"]=!a&&!d,f)],grid:!a&&c,gm:!a&&d,Up:!a&&!d&&!b,banner:a&&
!e,em:a||!d}},xja=function(a){var b,c;if(!a)return[];if(_.D("enableZylonBrowseAutoVeLogging",!1))return _.c2(a);var d=Array.prototype.slice.call(a.querySelectorAll(".yt-virtual-list__item--selected .yt-virtual-list .yt-virtual-list__item--visible")).map(function(l){return l.firstElementChild}),e=Array.prototype.slice.call(a.querySelectorAll(".ytlr-tv-secondary-nav-renderer__nav-container .yt-virtual-list .yt-virtual-list__item--visible")).map(function(l){return l.firstElementChild}),f=a.querySelector(".ytlr-tv-browse-renderer--navless .ytlr-tv-surface-header-renderer__buttons")||
a.querySelector(".ytlr-tv-secondary-nav-renderer--content-selected .ytlr-tv-surface-header-renderer__buttons"),g=Array.prototype.slice.call(Array.prototype.slice.call(a.querySelectorAll(".ytlr-tv-secondary-nav-renderer__nav-container .ytlr-tv-secondary-nav-renderer__top-nav ytlr-tab-renderer"))),h=[];a=_.A(d.concat(Array.prototype.slice.call(f?f.children:[]),a.querySelector(".yt-virtual-list__item--visible .ytlr-horizontal-list-renderer")||[],a.querySelector(".yt-virtual-list__item--visible .ytlr-popup-survey-shelf-renderer")||
[],a.querySelector(".ytlr-section-list-renderer")||[],a.querySelector(".yt-virtual-list__item--visible .ytlr-sentiment-survey-shelf-renderer")||[],0<g.length?g:Array.prototype.slice.call(e),Array.prototype.slice.call(a.querySelectorAll(".yt-virtual-list__item--visible .yt-chip-list__item--visible .ytlr-chip")),a.querySelector(".yt-virtual-list__item--visible .ytlr-video-survey-shelf-renderer")||[],Array.prototype.slice.call(a.querySelectorAll(".yt-virtual-list__item--visible .ytlr-video-survey-shelf-renderer__answer-button, .yt-virtual-list__item--visible .ytlr-video-survey-shelf-renderer__dismissal-button"))));
for(d=a.next();!d.done;d=a.next())(d=null===(c=null===(b=d.value.ia)||void 0===b?void 0:b.props.data)||void 0===c?void 0:c.trackingParams)&&h.push(d);return h},yja=function(a){var b=a.data;a=a.focused;var c={};return _.R("host",{className:(c["ytlr-tv-browse-renderer--navless"]=!b.content||!b.content.tvSecondaryNavRenderer,c)},_.R(_.xJ,{config:this.h(a),renderer:b.content}))},Dja=function(a,b){var c=b.Je,d=b.Oa,e=b.Ke,f=b.nj,g=b.xj,h="WEB_PAGE_TYPE_BROWSE"===a.ha.type||"WEB_PAGE_TYPE_SETTINGS"===a.ha.type,
l="WEB_PAGE_TYPE_SEARCH"===a.ha.type,n="WEB_PAGE_TYPE_WATCH"===a.ha.type,q="WEB_PAGE_TYPE_WATCH"===a.ha.type||this.hasBackButton,t="WEB_PAGE_TYPE_BROWSE"===a.ha.type?{browseResponse:a.ha.Da.response}:{settingsResponse:a.ha.Da.response},w="WEB_PAGE_TYPE_ACCOUNT_SELECTOR"===a.ha.type,y="WEB_PAGE_TYPE_WELCOME"===a.ha.type;b={};var B={};f={className:(b["ytlr-app--shift-content"]=!this.hasBackButton&&this.V,b),isHidden:f};b=_.R(_.o9,{enabled:n,className:"ytlr-app__player"});h=_.R(b$,{active:h,I:c$,props:{command:a.ha.command,
data:t,focused:this.isFocused("content")&&h,focusId:"content",hasBackButton:this.hasBackButton,onLeftFocus:!this.hasBackButton&&"guide",onBackFocus:!this.hasBackButton&&"guide",className:"ytlr-app__content"}});l=_.R(b$,{active:l,I:zja,props:{command:a.ha.command.searchEndpoint,data:a.ha.Da.response,clickTrackingParams:a.ha.command.clickTrackingParams,isActive:l,focused:this.isFocused("content")&&l,focusId:"content",onLeftFocus:"guide",onBackFocus:"guide",className:"ytlr-app__content"}});t=_.R(b$,
{active:"WEB_PAGE_TYPE_ACCOUNTS"===a.ha.type,I:Aja,props:{data:a.ha.Da.response,focused:this.isFocused("content")&&"WEB_PAGE_TYPE_ACCOUNTS"===a.ha.type,focusId:"content",onLeftFocus:"guide",onBackFocus:"guide",className:"ytlr-app__content"}});c=_.R(_.g9,{className:["ytlr-app__guide",(B["ytlr-app__guide--hidden-guide"]=q,B)],data:c,disabled:q,focused:this.isFocused("guide")&&!q,focusId:"guide",isHidden:q,Ai:!0,onRightFocus:"content",onSelect:this.S,ak:this.F});g=_.R(b$,{active:n,I:Bja,props:{isActive:n,
data:a.ha.Da.response,className:"ytlr-app__watch-page",command:a.ha.command,focused:!d&&n,Oa:d,bd:g},Wj:{isActive:!1,data:{},command:{},focused:!1,Oa:!1,bd:g}});y=_.R(b$,{active:y,I:_.V8,className:"ytlr-app__welcome",props:{command:a.ha.command.startWelcomeCommand,focused:y&&!d}});a=_.R(b$,{active:w,I:Cja,className:"ytlr-app__account-selector",props:{command:a.ha.command.startAccountSelectorCommand,focused:w&&!d}});w=_.R(_.N8,{className:"ytlr-app__overlay",qp:this.P});d=_.R(_.S8,{className:"ytlr-app__toast",
Oa:d});n=_.R("div",{className:"ytlr-app__mdx-volume"},_.R(_.O7,null));e=_.R(_.I8,{className:"ytlr-app__debug-console",active:e});if(q=this.D){B=[];var z=_.Wa(window.location,"mloader");z&&B.push("mloader="+z);z=_.r.get(_.ST);(_.QT(z)||_.D("enableDeviceStorageIntegrityDebugging",!1))&&B.push(_.RT(z));B=B.join(" ")}else B="";return _.R("host",f,b,h,l,t,c,g,y,a,w,d,n,e,_.R(_.J8,{className:"ytlr-app__debug-watermark",active:q,text:B}),_.R(_.O8,{visualElement:_.iI()}))},Eja=function(){var a=_.zA({title:_.V("NETWORK_ERROR_TITLE"),
subtitle:_.V("NETWORK_ERROR_SUBTITLE"),icon:{iconType:"ERROR_OUTLINE"}}),b={buttonRenderer:{text:{simpleText:_.V("EXIT_YOUTUBE_TITLE")},command:{signalAction:{signal:"EXIT_APP"}}}};return _.wA(_.AA(a,[b]))},Fja=function(a){var b=_.r.get(_.kA);a={videoIds:[a||_.OI()]};b=_.jq(b.fetch({path:"/youtubei/v1/playlist/get_add_to_playlist",payload:a}));return _.I(b).then(function(c){c=c.contents;if(!c||!c.length)return null;for(var d=0;d<c.length;d++){var e=c[d];if(e.addToPlaylistRenderer)return e.addToPlaylistRenderer}return null})},
d$=function(a,b){b=void 0===b?!1:b;var c=_.V("ADD_TO_PLAYLIST");c=_.zA({title:c});return _.vA({overlayPanelRenderer:_.AA(c,a),uniqueId:"CLIENT_OVERLAY_TYPE_ADD_TO_PLAYLIST",updateAction:b})},Hja=function(){var a=_.V("SEND_FEEDBACK");a=_.zA({title:a});var b=Gja.map(function(c){c=c.seq;return{compactLinkRenderer:{title:{simpleText:_.V(M9[c])},serviceEndpoint:{openClientOverlayAction:{type:"CLIENT_OVERLAY_TYPE_SEND_FEEDBACK_SECONDARY",context:""+c}},secondaryIcon:{iconType:"CHEVRON_RIGHT"}}}});return _.vA({overlayPanelRenderer:_.AA(a,
b)})},Ija=function(a){if(!(a in _.I5))return{};var b=_.I5[a];a=_.V("SEND_FEEDBACK");var c=_.V(M9[b.seq]);a=_.zA({title:a,subtitle:c});c=b.children.map(function(d){var e=b.seq;d=d.seq;return{compactLinkRenderer:{title:{simpleText:_.V(M9[d])},serviceEndpoint:{openClientOverlayAction:{type:"CLIENT_OVERLAY_TYPE_REPORT_FEEDBACK",context:e+","+d}}}}});return _.vA({overlayPanelRenderer:_.AA(a,c)})},e$=function(a,b){b=void 0===b?!1:b;var c=_.r.get(_.tV),d=_.r.get(_.oJ),e=_.pd(),f=window.environment,g=M9[a],
h=_.Za();e={label:e.clientLabel,name:e.clientName,version:e.clientVersion};f=f&&f.experiments||[];c=c.g&&c.g.h()||{};b=b?"":document.URL;var l;35===a&&(l=d.xb&&d.xb.errorInfo||void 0);if(35===a||-1!==M9[a].indexOf("VP_"))var n=_.VI()||void 0;return{seq:a,category:g,device:h,client:e,localeReport:c,experiments:f,url:b,errorInfo:l,cpn:n}},Jja=function(a){var b=void 0===b?!1:b;var c=_.V("FEEDBACK_REPORT_INFO"),d=_.zA({title:c});c=[];a=e$(a,b);c.push(f$("category",a.seq+" - "+a.category));var e=g$(a.device);
c.push(f$("device",e));e=g$(a.client);c.push(f$("client",e));e=g$(a.localeReport);c.push(f$("locale_report",e));c.push(f$("cpn",a.cpn));e=g$(a.errorInfo);c.push(f$("error_info",e));c.push(f$("url",a.url));c.push(f$("e",a.experiments.join(", ")));b&&c.push(f$("*For test*","Lorem ipsum dolor sit amet, consectetur adipiscing elit,sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Utenim ad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip exea commodo consequat. Duis aute irure dolor in reprehenderitin voluptate velit esse cillum dolore eu fugiat nulla pariatur.Excepteur sint occaecat cupidatat non proident, sunt in culpa quiofficia deserunt mollit anim id est laborum."));
b=_.AA(d);b.content={scrollPaneRenderer:{content:{scrollPaneItemListRenderer:{items:c}}}};return _.vA({overlayPanelRenderer:b})},f$=function(a,b){return{overlayMessageRenderer:{title:{runs:[{text:a,textColor:4294967295}]},subtitle:{simpleText:b||_.V("INFO_NONE")}}}},g$=function(a){if(!a)return _.V("INFO_NONE");var b=[];a=_.A(Object.entries(a));for(var c=a.next();!c.done;c=a.next()){var d=_.A(c.value);c=d.next().value;d=d.next().value;b.push(c+": "+d)}return b.join(", ")},Kja=function(a){a=a.split(",");
return 2!==a.length?null:{openPopupAction:{popupType:"FULLSCREEN_OVERLAY",popup:{overlaySectionRenderer:{overlay:{tvVoiceOverlayRenderer:{trigger:a[0],language:a[1]}}}}}}},Lja=function(){var a=_.r.get(_.kA),b={clientSideParams:{videoId:_.OI(),reportFormType:"REPORT_FORM_TYPE_TOU",reportFormContext:"REPORT_FORM_CONTEXT_WATCH",isLiveNow:!!_.MI().isLive}};a=_.jq(a.fetch({path:"/youtubei/v1/flag/get_form",payload:b}));return _.I(a).then(function(c){return c.reportFormResponseSupportedRenderers&&c.reportFormResponseSupportedRenderers.menuRenderer||
null})},Mja=function(){var a=_.GG,b=a&&a.getAvailableAudioTracks()||[];a=a&&a.getAudioTrack()||null;if(!b.length||!a)return{};var c=b.indexOf(a);a=_.J5(_.V("AUDIO_CHANNEL_SETTINGS"),a.getLanguageInfo().getName());b=b.map(function(d,e){e=e===c;d=d.getLanguageInfo().getName();d={title:{simpleText:d},serviceEndpoint:{commandExecutorCommand:{commands:[{signalAction:{signal:"CLOSE_POPUP"}},{setClientSettingEndpoint:{settingDatas:[{clientSettingEnum:{item:"PLAYBACK_AUDIO_TRACK"},stringValue:d}]}}]}}};e&&
(d.secondaryIcon={iconType:"CHECK"});return{compactLinkRenderer:d}});return _.vA({overlayPanelRenderer:_.AA(a,b,c)})},Nja=function(){var a=_.GG,b=[.25,.5,1,1.25,1.5,2];a=a&&a.getPlaybackRate()||1;var c=b.indexOf(a);a=_.J5(_.V("VIDEO_SPEED"),h$(a));b=b.map(function(d,e){e=e===c;d={title:{simpleText:h$(d)},serviceEndpoint:{commandExecutorCommand:{commands:[{signalAction:{signal:"CLOSE_POPUP"}},{setClientSettingEndpoint:{settingDatas:[{clientSettingEnum:{item:"PLAYBACK_SPEED"},intValue:""+d}]}}]}}};
e&&(d.secondaryIcon={iconType:"CHECK"});return{compactLinkRenderer:d}});return _.vA({overlayPanelRenderer:_.AA(a,b,c)})},h$=function(a){return 1===a?_.V("VIDEO_SPEED_NORMAL"):a+"x"},Oja=function(){var a=_.m8(),b=I9(),c=a.indexOf(b);b=_.J5(_.V("QUALITY"),i$(b));a=a.map(function(d,e){e=e===c;d={title:{simpleText:i$(d)},serviceEndpoint:{commandExecutorCommand:{commands:[{setClientSettingEndpoint:{settingDatas:[{clientSettingEnum:{item:"PLAYBACK_QUALITY"},stringValue:d}]}},{signalAction:{signal:"CLOSE_POPUP"}}]}}};
e&&(d.secondaryIcon={iconType:"CHECK"});return{compactLinkRenderer:d}});return _.vA({overlayPanelRenderer:_.AA(b,a,c)})},i$=function(a){if("auto"===a){a=_.V("VIDEO_QUALITY_AUTO");var b=_.GG;b&&"auto"===I9()&&(b=b.getPlaybackQuality(),a+=" ("+j$(b)+")");return a}return j$(a)},j$=function(a){return"hd2160"===a?"2160p \u2022 4K":_.QI[a].toString()+"p"},Pja=function(a){var b=_.V("CAPTIONS_STYLE_TITLE");b=_.zA({title:b});var c=_.r.get(_.$3),d=[],e=k$({title:_.V("CAPTIONS_RESET_STYLE"),command:{commandExecutorCommand:{commands:[{setClientSettingEndpoint:{settingDatas:[{clientSettingEnum:{item:"CAPTION_STYLE_RESET"}}]}},
l$]}},Zj:!1});d.push(e);d.push(_.g0());c=c.getSubtitlesUserSettings();e=0;for(var f=m$.length;e<f;++e){var g=m$[e],h=_.K3[g];if(g){var l=c[g];l=void 0===l?_.V("CAPTIONS_DEFAULT"):_.M3[g][l]||_.V("CAPTIONS_DEFAULT")}else l="";d.push(k$({title:h,subtitle:l,command:{openClientOverlayAction:{type:"CLIENT_OVERLAY_TYPE_CAPTIONS_SETTING",context:g}}}))}return _.vA({updateAction:a,overlayPanelRenderer:_.AA(b,d,2),uniqueId:"CLIENT_OVERLAY_TYPE_CAPTIONS_STYLE",popupType:"DIALOG"})},k$=function(a){var b=a.subtitle,
c=void 0===a.Zj?!0:a.Zj;a={title:{simpleText:a.title},serviceEndpoint:a.command};b&&(a.subtitle={simpleText:b});c&&(a.secondaryIcon={iconType:"CHEVRON_RIGHT"});return{compactLinkRenderer:a}},Qja=function(a,b){var c=_.zA({title:_.K3[a]}),d=_.r.get(_.$3).getSubtitlesUserSettings(),e=_.Gda[a],f=_.M3[a];a:{switch(a){case "color":case "background":case "windowColor":var g=_.Cda;break a}g=void 0}var h=_.Hda[a],l=[],n=-1,q=!!d[a+"Override"],t=n$([o$("CAPTION_STYLE_VIDEO_OVERRIDE",!q),o$(h)],a);q=p$({title:_.V("CAPTIONS_VIDEO_OVERRIDE"),
command:t,subtitle:_.V("CAPTIONS_VIDEO_OVERRIDE_SUBLABEL"),secondaryIcon:q?"CHECK_BOX_OUTLINE_BLANK":"CHECK_BOX"});l.push(q);l.push(_.g0());d=d[a];for(q=0;q<e.length;++q){var w=e[q];t=f[w];var y=g?g[w]:void 0,B=void 0;d===w&&(B="CHECK",n=q);w=n$([o$(h,w)],a);l.push(p$({title:t,command:w,secondaryIcon:B,icon:y}))}return _.vA({overlayPanelRenderer:_.AA(c,l,n+2),updateAction:b,jb:{commandExecutorCommand:{commands:[l$,{signalAction:{signal:"POPUP_BACK"}}]}},uniqueId:"CLIENT_OVERLAY_TYPE_CAPTIONS_SETTING",
popupType:"DIALOG"})},o$=function(a,b){var c=Rja[typeof b],d={};return d.clientSettingEnum={item:a},d[c]=b,d},p$=function(a){var b=a.subtitle,c=a.secondaryIcon,d=a.icon;a={title:{simpleText:a.title},serviceEndpoint:a.command};b&&(a.subtitle={simpleText:b});d&&(a.icon={iconType:d});c&&(a.secondaryIcon={iconType:c});return{compactLinkRenderer:a}},n$=function(a,b){return{commandExecutorCommand:{commands:[{setClientSettingEndpoint:{settingDatas:a}},{openClientOverlayAction:Object.assign(Object.assign({},
Sja),{context:b})}]}}},Tja=function(a){var b=a.title&&a.title.menuTitleRenderer&&a.title.menuTitleRenderer.title||{simpleText:_.V("REPORT_VIDEO_REASONS")},c=a.title&&a.title.menuTitleRenderer&&a.title.menuTitleRenderer.subtitle;b={title:b};c&&(b.subtitle=c);a=(a.items||[]).filter(function(d){return!!d.menuServiceItemRenderer}).map(function(d){d=d.menuServiceItemRenderer;var e={title:d.text,serviceEndpoint:{commandExecutorCommand:{commands:[{signalAction:{signal:"CLOSE_POPUP"}}]}}};d.serviceEndpoint&&
e.serviceEndpoint.commandExecutorCommand.commands.push(d.serviceEndpoint);return{compactLinkRenderer:e}});return _.vA({overlayPanelRenderer:_.AA(b,a)})},Vja=function(a){Uja(a).then(function(b){return _.Wu().resolveCommand(b||{})})},Uja=function(a){a=a.command.openClientOverlayAction;if(!a||!a.type)return _.I(null);var b=a.context,c=a.updateAction;switch(a.type){case "CLIENT_OVERLAY_TYPE_VOICE":return b?_.I(Kja(b)):_.I(null);case "CLIENT_OVERLAY_TYPE_VIDEO_QUALITY":return _.I(Oja());case "CLIENT_OVERLAY_TYPE_AUDIO_OPTIONS":return _.I(Mja());
case "CLIENT_OVERLAY_TYPE_VIDEO_PLAYBACK_SPEED":return _.I(Nja());case "CLIENT_OVERLAY_TYPE_ADD_TO_PLAYLIST":return Fja(b).then(function(d){var e=_.Wu();d?e.resolveCommand(d$(d.playlists||[],!0)):e.resolveCommand({signalAction:{signal:"POPUP_BACK"}})}),_.I(d$({loadingRenderer:{}}));case "CLIENT_OVERLAY_TYPE_SEND_FEEDBACK":return _.I(Hja());case "CLIENT_OVERLAY_TYPE_SEND_FEEDBACK_SECONDARY":return b?_.I(Ija(Number(b))):_.I(null);case "CLIENT_OVERLAY_TYPE_REPORT_FEEDBACK":return b?(a=b.split(","),_.I(Wia(Number(a[0]),
Number(a[1])))):_.I(null);case "CLIENT_OVERLAY_TYPE_REPORT_FEEDBACK_INFO":return b?_.I(Jja(Number(b))):_.I(null);case "CLIENT_OVERLAY_TYPE_CAPTIONS_LANGUAGE":return _.I(_.M5(c));case "CLIENT_OVERLAY_TYPE_CAPTIONS_STYLE":return _.I(Pja(c));case "CLIENT_OVERLAY_TYPE_CAPTIONS_SETTING":return _.I(Qja(b,c));case "CLIENT_OVERLAY_TYPE_VIDEO_REPORTING":return Lja().then(function(d){return d?Tja(d):null});default:return _.I(null)}},Wja=function(){var a={};a=_.V("FEEDBACK_FAILURE_SHARE_MORE",(a["{url}"]="|youtube.com/tv/feedback|",
a)).split("|").map(function(b){return b.trim()});return{qrCodeRenderer:{qrCodeImage:{thumbnails:[{url:_.x5,width:490,height:490}]},labels:[{simpleText:a[0]},{runs:[{text:a[1],textColor:4293651435}]},{simpleText:a[2]}]}}},Xja=function(a){a=e$(a);a={seq:""+a.seq,category:a.category,device:JSON.stringify(a.device),client:JSON.stringify(a.client),locale_report:JSON.stringify(a.localeReport),experiments:a.experiments.join(", "),url:a.url,errorInfo:JSON.stringify(a.errorInfo),cpn:a.cpn||""};var b={method:"POST",
headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"},body:_.Jc(a)};return new _.Zg(function(c){_.dT("/tv/feedback?action_feedback",b).g(_.Ar(function(d){_.C(d);d=_.V("SOMETHING_WENT_WRONG");var e=_.V("FEEDBACK_SUBMISSION_FAILURE");d=_.zA({title:d,subtitle:e});e=Wja();c(_.vA({overlayPanelRenderer:_.AA(d,[e])}));return _.np})).subscribe(function(){var d=_.V("FEEDBACK_SUCCESS_CONFIRM_TITLE"),e=_.V("FEEDBACK_SUCCESS_CONFIRM_MESSAGE");c({openPopupAction:{popupType:"TOAST",popup:{overlayMessageRenderer:_.xA({title:d,
subtitle:e,style:"OVERLAY_MESSAGE_STYLE_TOAST"})}}})})})},Yja=function(a){(a=a.command.signalAction)&&a.targetId?Xja(Number(a.targetId)).then(function(b){_.Wu().resolveCommand(b)}):_.I(void 0)},$ja=function(a,b,c){var d=a&&a.title,e=a&&a.trackingParams;a=a&&a.items;if(!a)return{};for(var f=[],g=0;g<a.length;g++){var h=a[g],l;if(l=_.D("enableZds",!1)){var n=void 0,q=void 0,t=void 0,w=void 0,y=void 0,B=void 0;l="ENABLE_HIGH_CONTRAST_MODE"===(null===(n=null===(q=null===(t=null===(w=null===(y=null===
(B=h.settingBooleanRenderer)||void 0===B?void 0:B.enableServiceEndpoint)||void 0===y?void 0:y.setClientSettingEndpoint)||void 0===w?void 0:w.settingDatas)||void 0===t?void 0:t[0])||void 0===q?void 0:q.clientSettingEnum)||void 0===n?void 0:n.item)}l||f.push({tabRenderer:Zja(h,b,c)})}return{title:d,trackingParams:e,tabs:f}},Zja=function(a,b,c){var d=a.settingActionRenderer||a.settingBooleanRenderer||a.settingReadOnlyItemRenderer||a.settingSingleOptionMenuRenderer||a.linkPhoneWithWiFiRenderer||a.linkPhoneWithTvCodeRenderer||
a.unlinkDevicesRenderer||{},e=a.settingActionRenderer&&!a.settingActionRenderer.serviceEndpoint;if(a.settingReadOnlyItemRenderer){var f=a.settingReadOnlyItemRenderer;"ABOUT_APP_VERSION"===(f.serviceEndpoint&&f.serviceEndpoint.setClientSettingEndpoint&&f.serviceEndpoint.setClientSettingEndpoint.settingDatas&&f.serviceEndpoint.setClientSettingEndpoint.settingDatas[0]&&f.serviceEndpoint.setClientSettingEndpoint.settingDatas[0].clientSettingEnum&&f.serviceEndpoint.setClientSettingEndpoint.settingDatas[0].clientSettingEnum.item)&&
(f.itemId="ABOUT_APP_VERSION")}if(e){if(b=a.settingActionRenderer)b.actionLabel={runs:[{text:_.V("SELECT_ISSUE")}]},b.serviceEndpoint={clickTrackingParams:b.trackingParams,openClientOverlayAction:{type:"CLIENT_OVERLAY_TYPE_SEND_FEEDBACK"}}}else if(a.settingActionRenderer){e=a.settingActionRenderer;var g;f=e.title;var h=e.serviceEndpoint,l=e.icon,n=e.confirmDialog;l&&l.iconType&&(n&&n.confirmDialogRenderer?(n.confirmDialogRenderer.icon=l,"CLEAR_WATCH_HISTORY"===l.iconType?(n.confirmDialogRenderer.title=
{simpleText:_.V("CLEAR_WATCH_HISTORY_DIALOG_TITLE")},b={commandExecutorCommand:{commands:[{clearWatchHistoryEndpoint:{actions:[_.yA({title:_.V("CLEAR_WATCH_HISTORY_TOAST"),icon:{iconType:"CLEAR_WATCH_HISTORY"}})]},clickTrackingParams:null===(g=n.confirmDialogRenderer.confirmEndpoint)||void 0===g?void 0:g.clickTrackingParams},{signalAction:{signal:"POPUP_BACK"}}]}},n.confirmDialogRenderer.confirmEndpoint=b):"CLEAR_COOKIES"===l.iconType&&(n.confirmDialogRenderer.title={simpleText:_.V("RESET_APP_DIALOG_TITLE")},
n.confirmDialogRenderer.confirmEndpoint={commandExecutorCommand:{commands:[{clickTrackingParams:h&&h.clickTrackingParams},{signalAction:{signal:"CLOSE_POPUP"}},{signalAction:{signal:"RESET_APP"}}]}})):"CLEAR_SEARCH_HISTORY"===l.iconType&&(g=b?_.V("CLEAR_SEARCH_HISTORY_DIALOG_MSG_SIGNED_IN"):_.V("CLEAR_SEARCH_HISTORY_DIALOG_MSG_SIGNED_OUT"),b=b?[{clearSearchHistoryEndpoint:{}},{signalAction:{signal:"POPUP_BACK"}}]:[{signalAction:{signal:"CLEARED_SEARCH_HISTORY"}},{signalAction:{signal:"POPUP_BACK"}},
_.yA({title:_.V("CLEAR_SEARCH_HISTORY_TOAST"),icon:{iconType:"CLEAR_SEARCH_HISTORY"}})],b={title:{simpleText:_.V("CLEAR_SEARCH_HISTORY_DIALOG_TITLE")},confirmLabel:f,cancelLabel:{simpleText:_.V("CANCEL")},confirmEndpoint:{commandExecutorCommand:{commands:b}},dialogMessages:[{simpleText:g}],trackingParams:h&&h.clickTrackingParams,icon:l},e.confirmDialog={confirmDialogRenderer:b}));if(b=a.settingActionRenderer.confirmDialog&&a.settingActionRenderer.confirmDialog.confirmDialogRenderer)b=_.h2(b),a.settingActionRenderer.serviceEndpoint=
b}b=!1;if(a.settingSingleOptionMenuRenderer){c&&a.settingSingleOptionMenuRenderer.itemId&&c===a.settingSingleOptionMenuRenderer.itemId&&(b=!0);c=a.settingSingleOptionMenuRenderer;g=(e=c.items)?Math.max(e.findIndex(aka),0):-1;if(e=c.items&&c.items[g]&&c.items[g].settingMenuItemRenderer)e.selected=!0;"I18N_LANGUAGE"===c.itemId&&(c.confirmChangeDialog={confirmDialogRenderer:{icon:{iconType:"TRANSLATE"},confirmLabel:{simpleText:_.V("CONFIRM_CHANGE")},cancelLabel:{simpleText:_.V("CANCEL")},dialogMessages:[{simpleText:_.V("LANGUAGE_OVERLAY_SUBTITLE")}]}});
f=c.items;h=c.confirmChangeDialog&&c.confirmChangeDialog.confirmDialogRenderer;if(f){l=[];for(n=0;n<f.length;n++){var q=f[n];if(q&&q.settingMenuItemRenderer){var t=q.settingMenuItemRenderer;q=t.name;var w=t.updateServiceEndpoint,y=t.selected;t={title:{runs:[{text:q}]},trackingParams:t.trackingParams};y&&(t.secondaryIcon={iconType:"CHECK"});h?y||(h.confirmEndpoint=w,q&&(h.title={simpleText:_.V("CHANGE_LANGUAGE",{language:q})}),t.serviceEndpoint=_.h2(h)):w&&(t.serviceEndpoint={commandExecutorCommand:{commands:[w,
{signalAction:{signal:"POPUP_BACK"}},_.yA({title:_.V("LOCATION_UPDATED_TOAST",{location:q||""}),icon:{iconType:"LANGUAGE"}})]}});l.push({compactLinkRenderer:t})}}f=l}else f=[];f&&(h=c.itemId,h=_.zA({title:h&&_.dca[h]||""}),g=_.vA({overlayPanelRenderer:_.AA(h,f,g)}),c.button={buttonRenderer:{text:{simpleText:bka(c.itemId,e&&e.name)},icon:{iconType:"EDIT"},command:g}})}return{title:_.Sx(d.title),content:a,selected:b}},bka=function(a,b){return a?"I18N_LANGUAGE"===a?(a=b||"English (US)",_.V("LANGUAGE_LABEL")+
": "+a):"I18N_REGION"===a?(a=b||"United States",_.V("LOCATION_LABEL")+": "+a):"":""},aka=function(a){var b=a&&a.settingMenuItemRenderer&&a.settingMenuItemRenderer.updateServiceEndpoint&&a.settingMenuItemRenderer.updateServiceEndpoint.setClientSettingEndpoint&&a.settingMenuItemRenderer.updateServiceEndpoint.setClientSettingEndpoint.settingDatas&&a.settingMenuItemRenderer.updateServiceEndpoint.setClientSettingEndpoint.settingDatas[0]&&a.settingMenuItemRenderer.updateServiceEndpoint.setClientSettingEndpoint.settingDatas[0].clientSettingEnum&&
a.settingMenuItemRenderer.updateServiceEndpoint.setClientSettingEndpoint.settingDatas[0].clientSettingEnum.item;a=a&&a.settingMenuItemRenderer&&a.settingMenuItemRenderer.value;if(b&&a){var c=_.pd();switch(b){case "I18N_LANGUAGE":return a===c.clientLanguage;case "I18N_REGION":return a===c.clientLocation}}return!1},r$=function(a){for(var b=0;b<a.length;b++){var c=a[b];c.pivotVideoRenderer?(c=cka(c.pivotVideoRenderer),a[b]={gridVideoRenderer:c}):c.pivotChannelRenderer?(c=c.pivotChannelRenderer,a[b]=
{gridChannelRenderer:{thumbnail:c.thumbnail,title:c.title,subscriberCountText:c.subscriberCountText,navigationEndpoint:c.navigationEndpoint,trackingParams:c.trackingParams}}):c.pivotPlaylistRenderer?a[b]={gridPlaylistRenderer:q$(c.pivotPlaylistRenderer)}:c.pivotRadioRenderer&&(a[b]={gridPlaylistRenderer:q$(c.pivotRadioRenderer)})}},cka=function(a){var b={videoId:a.videoId,thumbnail:a.thumbnail,title:a.title,shortBylineText:a.shortBylineText,navigationEndpoint:a.navigationEndpoint,trackingParams:a.trackingParams},
c=a.badges&&a.badges.find(function(e){return!!e.liveBadge}),d=a.badges&&a.badges.find(function(e){return!!e.textBadge});c?(a=[{thumbnailOverlayTimeStatusRenderer:{text:c.liveBadge.label,style:"LIVE"}}],b=Object.assign(Object.assign({},b),{thumbnailOverlays:a})):d?(a=[{thumbnailOverlayTimeStatusRenderer:{text:d.textBadge.label,style:"UPCOMING"}}],b=Object.assign(Object.assign({},b),{thumbnailOverlays:a})):(c=a.thumbnailOverlays||[],a.overlayIcon&&"PLAYING"===a.overlayIcon.iconType&&a.overlayLabel&&
c.push({thumbnailOverlayNowPlayingRenderer:{text:a.overlayLabel}}),a.lengthText&&(d=Object.assign({},a.lengthText),delete d.accessibility,c.push({thumbnailOverlayTimeStatusRenderer:{text:d,style:"DEFAULT"}}),b=Object.assign(Object.assign({},b),{lengthText:a.lengthText})),c.length&&(b=Object.assign(Object.assign({},b),{thumbnailOverlays:c})),a.playlistIndexText&&(b=Object.assign(Object.assign({},b),{playlistIndexText:a.playlistIndexText})),a.viewCountText&&(b=Object.assign(Object.assign({},b),{shortViewCountText:a.viewCountText})),
c=[],d=a.badges&&a.badges.find(function(e){return!!e.metadataBadgeRenderer}),a=a.standaloneBadge&&a.standaloneBadge.standaloneYpcBadgeRenderer,d?c.push(d):a&&c.push({metadataBadgeRenderer:{label:_.Sx(a.label),style:"BADGE_STYLE_TYPE_YPC"}}),c.length&&(b=Object.assign(Object.assign({},b),{badges:c})));return b},q$=function(a){return{playlistId:a.playlistId,thumbnailRenderer:a.thumbnailRenderer,title:a.title,shortBylineText:a.shortBylineText,videoCountText:a.videoCountText,navigationEndpoint:a.navigationEndpoint,
trackingParams:a.trackingParams}},s$=function(a,b){a={openClientOverlayAction:{type:a}};b&&(a.openClientOverlayAction.context=b);return a},u$=function(a,b){b=void 0===b?"ACCOUNT_EVENT_TRIGGER_UNKNOWN":b;var c=a.defaultServiceEndpoint,d=a.toggledServiceEndpoint;c&&!c.authDeterminedCommand&&(a.defaultServiceEndpoint=t$(c,b,!0));d&&!d.authDeterminedCommand&&(a.toggledServiceEndpoint=t$(d,b,!0));return a},t$=function(a,b,c){b=void 0===b?"ACCOUNT_EVENT_TRIGGER_UNKNOWN":b;var d=a;(void 0===c?0:c)&&(d={commandExecutorCommand:{commands:[{signalAction:{signal:"CLOSE_POPUP"}},
Object.assign({},a)]}});return{authDeterminedCommand:{authenticatedCommand:a,unauthenticatedCommand:{authRequiredCommand:{identityActionContext:{eventTrigger:b,nextEndpoint:d}}}}}},v$=function(a,b){if("WEB_PAGE_TYPE_WATCH"===b.type){var c=a,d=c.playerOverlays&&c.playerOverlays.playerOverlayRenderer;if(d){var e=d&&d.autoplay&&d.autoplay.playerOverlayTvAutoplayRenderer;e&&(delete d.autoplay.playerOverlayTvAutoplayRenderer,e={title:e.title,videoTitle:e.videoTitle,byline:e.byline,nextEndpoint:e.nextEndpoint,
countDownSecs:e.countDownSecs,trackingParams:e.trackingParams,background:e.thumbnail},d=Object.assign(Object.assign({},d),{autoplay:{playerOverlayAutoplayRenderer:e}}));var f=(e=c.contents&&c.contents.singleColumnWatchNextResults&&c.contents.singleColumnWatchNextResults.autoplay&&c.contents.singleColumnWatchNextResults.autoplay.autoplay)&&e.replayVideoRenderer&&e.replayVideoRenderer.pivotVideoRenderer;f&&(delete e.replayVideoRenderer,e={background:f.thumbnail,navigationEndpoint:f.navigationEndpoint,
overlayIcon:f.overlayIcon,overlayLabel:f.overlayLabel,shortBylineText:f.shortBylineText,title:f.title,trackingParams:f.trackingParams},d=Object.assign(Object.assign({},d),{replay:{playerOverlayReplayRenderer:e}}));c.playerOverlays={playerOverlayRenderer:d}}if(d=c.contents&&c.contents.singleColumnWatchNextResults&&c.contents.singleColumnWatchNextResults.pivot&&c.contents.singleColumnWatchNextResults.pivot.pivot)if(d=d.contents)for(e=0;e<d.length;e++)(f=d[e].shelfRenderer)&&f.icon&&"PLAYLISTS"!==f.icon.iconType&&
"MIX"!==f.icon.iconType&&delete f.icon,(f=f&&f.content&&f.content.horizontalListRenderer&&f.content.horizontalListRenderer.items)&&r$(f);(d=c.continuationContents&&c.continuationContents.horizontalListContinuation&&c.continuationContents.horizontalListContinuation.items)&&r$(d);e=c;var g,h,l,n,q,t,w,y,B,z;c=_.o5(e);d=_.ov();if(c&&!_.D("disableTransportControlsShim",!1)){if(null===(g=c.qualityButton)||void 0===g?0:g.buttonRenderer)c.qualityButton.buttonRenderer.command=s$("CLIENT_OVERLAY_TYPE_VIDEO_QUALITY");
if(null===(h=c.addToButton)||void 0===h?0:h.buttonRenderer)g=s$("CLIENT_OVERLAY_TYPE_ADD_TO_PLAYLIST"),c.addToButton.buttonRenderer.command=d?g:t$(g,"ACCOUNT_EVENT_TRIGGER_SAVE_VIDEO");if(null===(l=c.speedButton)||void 0===l?0:l.buttonRenderer)c.speedButton.buttonRenderer.command=s$("CLIENT_OVERLAY_TYPE_VIDEO_PLAYBACK_SPEED");if(null===(n=c.statsForNerdsButton)||void 0===n?0:n.buttonRenderer)c.statsForNerdsButton.buttonRenderer.command={signalAction:{signal:"TOGGLE_VIDEO_INFO"}};if(null===(q=c.reportButton)||
void 0===q?0:q.buttonRenderer)l=s$("CLIENT_OVERLAY_TYPE_VIDEO_REPORTING"),c.reportButton.buttonRenderer.command=d?l:t$(l,"ACCOUNT_EVENT_TRIGGER_REPORT_VIDEO");if(null===(t=c.audioTracksButton)||void 0===t?0:t.buttonRenderer)c.audioTracksButton.buttonRenderer.command=s$("CLIENT_OVERLAY_TYPE_AUDIO_OPTIONS");if(null===(w=c.feedbackButton)||void 0===w?0:w.buttonRenderer)c.feedbackButton.buttonRenderer.command=s$("CLIENT_OVERLAY_TYPE_SEND_FEEDBACK_SECONDARY","1");var H;b:{var M,aa;t=_.j5(e);if(t=null===
(aa=null===(M=null===t||void 0===t?void 0:t.results)||void 0===M?void 0:M.results)||void 0===aa?void 0:aa.contents)for(M=0;M<t.length;M++)if(aa=t[M].itemSectionRenderer,null!==(H=null===aa||void 0===aa?void 0:aa.contents)&&void 0!==H&&H.length)for(w=0;w<aa.contents.length;w++)if(l=aa.contents[w].offerButtonListRenderer){H=l;break b}H=void 0}if(H){if(H.offerButtons&&H.offerButtons.length)for(M=0;M<H.offerButtons.length;M++)t=(aa=H.offerButtons[M].buttonRenderer)&&(aa.serviceEndpoint||aa.command),aa&&
t&&t.signInEndpoint&&(t={clickTrackingParams:t.clickTrackingParams,authDeterminedCommand:{authenticatedCommand:t.signInEndpoint.nextEndpoint,unauthenticatedCommand:{authRequiredCommand:{identityActionContext:{eventTrigger:"ACCOUNT_EVENT_TRIGGER_PAYMENT",nextEndpoint:t.signInEndpoint.nextEndpoint}}}}},aa.serviceEndpoint=t,aa.command=t);c.offerButtonList={offerButtonListRenderer:H}}if(!d){if(null===(y=c.likeButton)||void 0===y?0:y.toggleButtonRenderer)c.likeButton.toggleButtonRenderer=u$(c.likeButton.toggleButtonRenderer,
"ACCOUNT_EVENT_TRIGGER_LIKE_DISLIKE");if(null===(B=c.dislikeButton)||void 0===B?0:B.toggleButtonRenderer)c.dislikeButton.toggleButtonRenderer=u$(c.dislikeButton.toggleButtonRenderer,"ACCOUNT_EVENT_TRIGGER_LIKE_DISLIKE");if(null===(z=c.subscribeButton)||void 0===z?0:z.toggleButtonRenderer)c.subscribeButton.toggleButtonRenderer=u$(c.subscribeButton.toggleButtonRenderer,"ACCOUNT_EVENT_TRIGGER_SUBSCRIBE")}}}"WEB_PAGE_TYPE_BROWSE"===b.type&&_.i2(a,!0,!1,{Lk:!0});if("WEB_PAGE_TYPE_SETTINGS"===b.type&&(y=
b.command,a.items||a.title)){y=y&&y.applicationSettingsEndpoint&&y.applicationSettingsEndpoint.itemId;if(B=a&&a.items){b:{if(z=a.responseContext&&a.responseContext.serviceTrackingParams)for(H=0;H<z.length;H++)if(aa=z[H],M=aa.params,aa=aa.service,M&&"GFEEDBACK"===aa)for(aa=0;aa<M.length;aa++)if(w=M[aa],t=w.value,w=w.key,t&&"logged_in"===w){z="1"===t;break b}z=!1}H=[];for(M=0;M<B.length;M++)(aa=B[M]&&B[M].settingCategoryCollectionRenderer)&&H.push({tvSecondaryNavSectionRenderer:$ja(aa,z,y)});y={title:a.title,
sections:H}}else y={};delete a.items;delete a.title;a.contents={tvBrowseRenderer:{content:{tvSecondaryNavRenderer:y}}}}"WEB_PAGE_TYPE_SEARCH"===b.type&&(a.continuationContents={horizontalListContinuation:_.z2(a)},delete a.continuationContents.itemSectionContinuation)},eka=function(a){var b=_.r.get(_.gG),c=_.r.get(_.oY),d=_.r.get(_.nv),e=_.L.R();_.BH(d).then(function(f){if(0!==Object.keys(f).length||1!==b.Ji().length||!a.obfuscatedGaiaId)return!1;var g=new _.Gq(1),h={eventTrigger:"ACCOUNT_EVENT_TRIGGER_STANDARD",
nextEndpoint:{browseEndpoint:{browseId:"FEtopics"}}};f=c.signIn(g,h.eventTrigger,"SIGN_IN_METHOD_TYPE_MDX_ASSISTED");var l=_.YX(function(n){return _.N(e,new _.O("mdx-sign-in:status-updated"),document,n)},function(n,q){q()}).g(dka());f.g(Jia(l)).subscribe(function(n){var q=_.r.get(_.DS),t=_.r.get(_.tv);if(0===n.type)t=_.uD(),n={authCode:n.code,signInSessionId:t},q.g&&q.g.sendMessage("requestAssistedSignIn",n);else if(4===n.type)if(q=Array.from(n.pb.keys()),1===q.length)g.next(q[0]);else throw Error("$c");
else 5===n.type&&t.set(n.identity,h)});return!0})},dka=function(){return _.Av(function(a){return"exited"===a.event||"canceled"===a.event})},gka=function(a){a=void 0===a?_.HS:a;var b=void 0===b?window.location:b;_.Tu(this,function d(){var e,f,g,h,l,n,q,t,w,y,B,z,H,M,aa;return _.Su(d,function(ya){if(1==ya.g){f=(e=!!_.Wa(b,"env_useGaiaSandbox"))?"https://accounts.sandbox.google.com/o/oauth2/cl":"https://accounts.google.com/o/oauth2/cl";g=_.jj(f);h=_.u("VISITOR_DATA");l=_.as();n=_.qd("oxbow_exceptions_sw",
0);var ua=(new w$).setVisitorData(h);ua=_.c0(ua,6,_.cs());ua=_.c0(ua,7,l);var wb=fka();ua=_.c0(ua,4,wb);ua=_.c0(ua,9,"en");q=_.c0(ua,10,n);if(t=_.D("directSignInConfig",H9).enableDebugParameters)_.c0(q,8,!0),w=_.Wa(b,"oxredir").toString()||null,_.c0(q,5,w);y=_.pd().oAuthClientProfiles[1];B=_.jj(y.Yi);z=new _.Ga(b.href);H=_.A(_.jG.concat(_.kG));for(M=H.next();!M.done;M=H.next())aa=M.value,z.g.wa(aa)&&_.ij(B,aa,z.g.la(aa));_.c0(q,2,B.toString());_.Ha(g,"signin_state",q.C());_.Ha(g,"redirect_uri","urn:ietf:wg:oauth:2.0:oob:auto");
_.Ha(g,"client_id",y.clientId);_.Ha(g,"scope",y.fj);_.Ha(g,"response_type","code");_.Ha(g,"access_type","offline");return _.Ju(ya,J9({event_type:4}),2)}_.Ib();ua=a;wb=_.r.get(_.KI).Va();wb=(null===wb||void 0===wb?void 0:wb.command)||_.Px("default");_.r.get(_.vd).set(_.Fl,{command:wb,context:ua});_.If(b,g.toString());ya.g=0})})},fka=function(){var a,b=new Set(hka),c=_.v.filter(_.Za(),function(d,e){return b.has(e)});c.browser_engine=null===(a=window.environment)||void 0===a?void 0:a.browser_engine;
return JSON.stringify(c)},x$=function(a,b,c){var d={title:{simpleText:_.V("SIGN_IN_GOOGLE_ACCOUNT_TITLE")},image:{thumbnails:[{url:_.td("sign-in-octo.png"),width:346,height:234}]}},e=a.eventTrigger&&_.sY[a.eventTrigger];e&&(d.subtitle={simpleText:_.V(e)});d={header:{overlayPanelHeaderRenderer:d}};_.D("directSignInConfig",H9).isSupported?(e=[{compactLinkRenderer:{title:{simpleText:_.V("SIGNIN_DIRECT")},serviceEndpoint:_.CA(a,{signInType:"SIGN_IN_METHOD_TYPE_DIRECT",signInStyle:c})}}],_.CS(_.r.get(_.DS))?
e.push({compactLinkRenderer:{title:{simpleText:_.V("SIGNIN_SEAMLESS")},serviceEndpoint:_.CA(a,{signInType:"SIGN_IN_METHOD_TYPE_SEAMLESS",signInStyle:c})}}):e.push({compactLinkRenderer:{title:{simpleText:_.V("SIGNIN_URL")},serviceEndpoint:_.CA(a,{signInType:"SIGN_IN_METHOD_TYPE_URL",signInStyle:c})}}),d.footer={overlayPanelItemListRenderer:{items:e}}):(a=[{compactLinkRenderer:{title:{simpleText:_.V("SIGN_IN_TEXT")},serviceEndpoint:_.CA(a,{signInStyle:c})}}],d.content={overlayPanelItemListRenderer:{items:a}});
return _.vA({overlayPanelRenderer:d,jb:b})},y$=function(a){switch(a){case "SIGN_IN_METHOD_TYPE_NATIVE":return"account_manager"===_.D("supportedIdentityEnvironment","oauth");case "SIGN_IN_METHOD_TYPE_DIRECT":return _.D("directSignInConfig",H9).isSupported;case "SIGN_IN_METHOD_TYPE_SEAMLESS":return _.CS(_.r.get(_.DS));case "SIGN_IN_METHOD_TYPE_URL":return!0;default:return!1}},ika=function(a,b){var c,d=_.r.get(_.Oha),e=_.r.get(_.tv);d.signIn(null===(c=a.identityActionContext)||void 0===c?void 0:c.eventTrigger,
b).subscribe({next:function(f){e.set(f,a.identityActionContext)},error:function(f){_.nd(f);_.Wu().resolveCommand(_.qY({startSignInCommand:a}))}})},jka=function(a){var b=void 0===b?!1:b;return _.jq(_.r.get(_.EH).Od()).then(function(c){var d=c.findIndex(function(e){return e.accountItemRenderer.isSelected});0>d&&(d=b?c.length:0);return{items:Nia(a,c,b),selectedIndex:d}})},z$=function(a,b){b=void 0===b?!0:b;_.Tu(this,function d(){var e,f,g,h,l,n;return _.Su(d,function(q){if(1==q.g)return e=_.r.get(_.nv),
f=a.identityActionContext||{},g=a.dismissalCommand,_.Ju(q,_.BH(e),2);h=q.h;l=0<Object.keys(h).length;n=_.Wu();return l?q.return(kka(f,g)):b?q.return(n.resolveCommand(x$(f,g,"SIGN_IN_STYLE_SIDE_PANEL"))):q.return(n.resolveCommand(_.CA(f)))})})},kka=function(a,b){var c=_.Wu(),d=_.V("CHOOSE_AN_ACCOUNT_TEXT"),e=a.eventTrigger&&_.sY[a.eventTrigger];e=e?_.V(e):void 0;var f={icon:{iconType:"SIGN_IN"},title:{simpleText:_.V("GUEST_MODE_STATUS_TEXT")}},g=_.zA({title:d,subtitle:e,content:[f]});c.resolveCommand(_.vA({overlayPanelRenderer:_.AA(g,
{loadingRenderer:{}}),jb:b,uniqueId:"auth-required-overlay",updateAction:!0}));return jka(a).then(function(h){c.resolveCommand(_.vA({overlayPanelRenderer:{header:{overlayPanelHeaderRenderer:g},content:{overlayPanelItemListRenderer:h}},jb:b,replacePopup:!0,uniqueId:"auth-required-overlay",updateAction:!0}))})},lka=function(a){var b={title:_.V("ACCOUNT_REMOVED")};a&&(b.subtitle=_.V("USER_ACCOUNT_REMOVED",{username:a}));return _.yA(b)},mka=function(a){return _.sr(a)?_.jq(_.Pv(_.r.get(_.Tv),a)).then(function(b){b=
Array.from(b.values()).find(function(d){return _.ur(d.identity,a)});if(!b)return _.C(Error("bd")),null;var c=_.Sx(b.accountItemRenderer.accountName);return _.yA({title:_.V("WELCOME"),subtitle:_.V("YOURE_SIGNED_IN_AS",{username:c}),image:b.accountItemRenderer.accountPhoto})}):(_.C(Error("ad")),Promise.resolve(null))},qka=function(){mja();var a=_.r.get(_.nv),b=_.r.get(_.tv),c=_.r.get(_.kA),d=_.r.get(_.c1),e=_.L.R();_.N(e,new _.O("selectActiveIdentityCommand"),A$,function(f){nka(a,f.command.selectActiveIdentityCommand.serviceEndpoint.selectActiveIdentityEndpoint).then(function(g){b.set(g,
f.command.selectActiveIdentityCommand.identityActionContext)})});_.N(e,new _.O("onIdentityRemovedAction"),A$,function(f){b.get().then(function(g){var h=_.Wu();_.sr(g)&&g.ownerObfuscatedGaiaId===f.identity.effectiveObfuscatedGaiaId?b.set(_.Er,f.identityActionContext):h.resolveCommand(lka(f.accountName));h.resolveCommand(oka)})});_.N(e,new _.O("switchToGuestMode"),A$,function(f){b.set(_.Er,f.command.switchToGuestMode.identityActionContext)});_.N(e,new _.O("onIdentityChanged"),A$,function(f){var g=_.Wu();
g.resolveCommand((f.command.onIdentityChanged.identityActionContext||{}).nextEndpoint||{browseEndpoint:{browseId:"FEtopics"}});_.$8(c);B$(d);b.get().then(function(h){h===_.Er?g.resolveCommand(pka):mka(h).then(function(l){l&&g.resolveCommand(l)})});_.hT()});_.N(e,new _.O("startSignInCommand"),A$,function(f){f=f.command;var g=void 0===f.startSignInCommand?{}:f.startSignInCommand;f=_.Wu();var h=g.signInType;if(h&&"SIGN_IN_METHOD_TYPE_UNKNOWN"!==h||!y$("SIGN_IN_METHOD_TYPE_DIRECT"))switch(h=(h=g.signInType)&&
y$(h)?h:y$("SIGN_IN_METHOD_TYPE_SEAMLESS")?"SIGN_IN_METHOD_TYPE_SEAMLESS":y$("SIGN_IN_METHOD_TYPE_NATIVE")?"SIGN_IN_METHOD_TYPE_NATIVE":"SIGN_IN_METHOD_TYPE_URL",h){case "SIGN_IN_METHOD_TYPE_NATIVE":ika(g,h);break;case "SIGN_IN_METHOD_TYPE_DIRECT":gka(g.identityActionContext);break;case "SIGN_IN_METHOD_TYPE_SEAMLESS":g=_.uY(g.identityActionContext,g.signInStyle);f.resolveCommand(g);break;default:g=_.gZ(g.identityActionContext,g.signInStyle),f.resolveCommand(g)}else g=x$(g.identityActionContext||{},
void 0,g.signInStyle),f.resolveCommand(g);B$(d)});_.N(e,new _.O("mdx-session:remoteConnected"),A$,function(f){"oauth"===_.D("supportedIdentityEnvironment","oauth")&&eka(f.device)});_.N(e,new _.O("authDeterminedCommand"),A$,function(f){var g=f.command.authDeterminedCommand;b.get().then(function(h){(h=_.sr(h)?g.authenticatedCommand:g.unauthenticatedCommand)&&_.Wu().resolveCommand(h)})});_.N(e,new _.O("authRequiredCommand"),A$,function(f){z$(f.command.authRequiredCommand)});_.N(e,new _.O("signInEndpoint"),
A$,function(f){f={eventTrigger:"ACCOUNT_EVENT_TRIGGER_STANDARD",nextEndpoint:{commandExecutorCommand:{commands:[{signalAction:{signal:"CLOSE_POPUP"}},Object.assign({},f.command.signInEndpoint.nextEndpoint)]}}};z$({identityActionContext:f},!1)})},rka=function(){var a=_.NN(_.i_,_.p9);_.r.get(_.F8).Jl=_.wx(function(b){return{overlaySectionRenderer:{I:a,props:{data:b.data.overlaySectionRenderer,Ye:b.Ye,focused:b.focused,className:b.className,idomKey:b.idomKey,isVisible:b.isVisible,Eb:b.Eb,rb:b.rb}}}})},
tka=function(a){var b=_.Pa(a,"topic");return b&&_.Cl.contains(b)?(a=_.Pa(a,"v"),b=_.Px("FEtopics",_.Cl.get(b)),a?(ska(b),_.I({oa:!1})):_.I({oa:!0,command:b})):(b=_.Pa(a,"c"))?(a=_.Px(b,_.Pa(a,"params")),_.I({oa:!0,command:a})):_.I({oa:!1})},C$=function(){return _.Px("default")},wka=function(a){var b=_.pd().mdxInitData;if(!b)return _.I({oa:!1});var c=_.Va(a.g);if(!c)return _.I({oa:!1});var d=(_.Pa(a,"theme")||"cl")===b.theme;if(c===b.Vf&&d){if("watch"!==_.Pa(a,"dialLaunch"))return _.I({oa:!1});uka();
return _.I({oa:!0,command:vka})}return d?_.I({oa:!1,command:{requestReversePairingCommand:{pairingCode:c}}}):(_.lG(a.g),_.I({oa:!1}))},uka=function(){var a=_.r.get(_.KI),b=_.qd("mdx_startup_fallback_wait_time_ms",5E3),c=setTimeout(function(){_.Wu().resolveCommand(C$())},b),d=a.subscribe(function(){clearTimeout(c);d()})},xka=function(a){var b=a.j,c=a.g,d=c.get("t");a=_.r.get(_.$t);if("play"===b)return a.resume(),_.I({oa:!0,command:G9({type:"PLAYER_CONTROL_ACTION_TYPE_PLAY",time:d})});if("pause"===
b)return a.resume(),_.I({oa:!0,command:G9({type:"PLAYER_CONTROL_ACTION_TYPE_PAUSE",time:d})});d=c.get("to");if("seek"===b&&d)return _.I({oa:!0,command:G9({type:"PLAYER_CONTROL_ACTION_TYPE_SEEK_ABSOLUTE",time:d})});c=c.get("by");return"seek"===b&&c?_.I({oa:!0,command:G9({type:"PLAYER_CONTROL_ACTION_TYPE_SEEK_RELATIVE",time:c})}):"suspend"===b?(a.j=_.Jo()-a.A,a.g.set(!0),_.I({oa:!0})):"resume"===b?(a.resume(),_.I({oa:!0})):_.I({oa:!1})},yka=function(a){var b=_.r.get(_.Q_);a=_.Pa(a,"q");if(!a)return _.I({oa:!1});
a={searchEndpoint:{query:a,yb:"KEYBOARD_DEEPLINK"}};_.P_(b,"KEYBOARD_DEEPLINK","");return _.I({oa:!0,command:a})},zka=function(a){return"settings"!==_.Pa(a,"row")?_.I({oa:!1}):_.I({oa:!0,command:{applicationSettingsEndpoint:{}}})},Aka=function(a){var b=this;return new _.Zg(function(c){return _.Tu(b,function e(){var f,g,h,l,n,q,t,w,y,B,z;return _.Su(e,function(H){switch(H.g){case 1:f={};a:{var M=_.Pa(a,"voice");if(M)try{var aa=JSON.parse(M);if(aa&&"object"===typeof aa){var ya=aa;break a}}catch(Gf){_.C(Gf)}ya=
void 0}g=ya;h=null===g||void 0===g?void 0:g.youtubeAssistantRequest;if(null===h||void 0===h?0:h.query)f.requestOrigin="REQUEST_ORIGIN_GOOGLE_ASSISTANT",f.query=h.query,h.queryIntent&&(f.queryIntent=h.queryIntent),h.youtubeAssistantParams&&(f.youtubeAssistantParams=h.youtubeAssistantParams),l=_.Pa(a,"command_id")||void 0;else if((ya=_.Pa(a,"vq"))||("voice"===_.Pa(a,"launch")?ya=_.Pa(a,"q"):ya=void 0),f.query=ya,!f.query)return c({oa:!1}),H.return();return _.Ju(H,_.rj(_.F7),2);case 2:return n=H.h,(null===
g||void 0===g?0:g.hasEntityBar)&&n.g&&n.h.next({state:1,uE:l}),_.Ju(H,_.rj(_.A_),3);case 3:return q=H.h,_.Ku(H,4),_.Ju(H,q.fetch(f),6);case 6:t=H.h;_.Lu(H,5);break;case 4:w=_.Mu(H),_.C(w);case 5:if(!t)return l&&D$(n,l,2),c({oa:!1}),H.return();l&&D$(n,l,1);y=_.Pa(a,"va");if("play"===y){a:{var ua,wb,rc,dd,Tb,pb,ed;ya=(ya=null===(rc=null===(wb=null===(ua=t.searchEndpoint)||void 0===ua?void 0:ua.prefetchConfig)||void 0===wb?void 0:wb.searchPrefetchDataConfig)||void 0===rc?void 0:rc.searchResponseData)?
JSON.parse(ya):void 0;if((ya=null===(ed=null===(pb=null===(Tb=null===(dd=null===(M=null===ya||void 0===ya?void 0:ya.contents)||void 0===M?void 0:M.sectionListRenderer)||void 0===dd?void 0:dd.contents)||void 0===Tb?void 0:Tb[0])||void 0===pb?void 0:pb.itemSectionRenderer)||void 0===ed?void 0:ed.contents)&&ya.length)for(M=0,dd=ya.length;M<dd;++M)if(Tb=void 0,pb=ya[M],ed=pb.compactMovieRenderer||pb.compactPlaylistRenderer||pb.compactRadioRenderer||pb.compactVideoRenderer||pb.tvMusicVideoRenderer||pb.compactShowRenderer||
pb.compactPremiumShowRenderer,(pb=(null===ed||void 0===ed?void 0:ed.navigationEndpoint)||(null===(Tb=pb.tileRenderer)||void 0===Tb?void 0:Tb.onSelectCommand))&&pb.watchEndpoint){pb=pb.watchEndpoint;Tb=pb.playlistId;if(pb=pb.videoId){ya=D9({videoId:pb});break a}if(Tb){ya=F9({playlistId:Tb});break a}}ya=void 0}(B=ya)&&(t=B);H.ua(7);break}if(!t.searchEndpoint){H.ua(7);break}t.searchEndpoint.yb="VOICE_DEEPLINK";return _.Ju(H,_.rj(_.K_),9);case 9:z=H.h,_.P_(z,"VOICE_DEEPLINK","");case 7:c({oa:!0,command:t}),
H.g=0}})})})},Bka=function(a){var b=_.Pa(a,"list");if(b)return a=_.Pa(a,"index"),_.I({oa:!0,command:F9({playlistId:b,index:a})});var c=_.Pa(a,"reversePairingCode")||_.Pa(a,"pairingCode");b=_.Pa(a,"v");return!c&&b?(a=_.Pa(a,"t"),_.I({oa:!0,command:D9({videoId:b,startTimeSeconds:a})})):_.I({oa:!1})},Cka=function(){_.r.get(_.M7).initialize([wka,Uia,Aka,yka,tka,Bka,zka,xka])},Dka=function(a){a=void 0===a?window:a;var b=_.fH();b.client=_.jA;_.gH(b,37935);dja();Iia();_.db("FORCE_CSI_ON_GEL",!1);_.D("enableRepetitiveActionTiming",
!1)&&(b=function(e){if(e=e.detail&&e.detail.latency)e.clientScreenNonce=_.cs()||void 0,_.hd("bedrockRepetitiveActionTimed",e)},a.document.addEventListener("yt-virtual-list__index-change",b),a.document.addEventListener("yt-virtual-list__transition-end",b));a=_.L.R();var c=_.r.get(_.tv),d=_.r.get(_.iv);_.N(a,new _.O("logPayloadCommand"),{},function(e){var f;e=e.command.logPayloadCommand;var g=e.payloadName,h=null===(f=e.clientEvent)||void 0===f?void 0:f[g];h?c.get().then(function(l){_.pv(d,g,h,l)}):
_.C(Error("dd`"+g))})},Eka=function(a){var b=a.clickTrackingParams;a=a.watchEndpoint;return Object.assign(Object.assign({},_.RG),{Jd:!0,Pk:!0,Qk:!0,pm:b,clickTrackingParams:b,watchEndpoint:a})},Fka=function(){_.K(_.rK,function(){return new E$})},Gka=function(){var a=_.r.get(_.vG);a.storage.get(_.sG)?(a.storage.set(_.sG,!1),a=_.yA({title:_.V("LANGUAGE_UPDATED_TOAST"),icon:{iconType:"TRANSLATE"}})):a=void 0;return _.I(a)},Ika=function(){var a=_.D("startupArm",void 0);return a&&Hka[a]||"welcome-and-account-selector"},
Jka=function(a){var b=a.accountSelectorMinDeviceAccounts,c=a.Dm,d=a.numberOfAccounts;a=a.Op;if("skip"===a)return"none";if("force-account-selector"===a)return"account-selector";a=_.r.get(_.t9);var e;a:{for(e=0;e<F$.length;e++){var f=a.recurringActionTracker.get(G$(F$[e]));if(0<(f?f.timesFired||0:0)){e=!1;break a}}e=!0}if(e&&0===d)b="welcome";else{if(!(c=!c))a:{var g=void 0===g?1440:g;for(c=0;c<_.r9.length;c++)if(e=g,e=void 0===e?1440:e,f=(f=a.recurringActionTracker.get(G$(_.q9[_.r9[c]])).lastFired)?
_.Jo()-f:Infinity,(f||0===f?f/1E3/60:Infinity)<e){c=!0;break a}c=!1}b=c?"none":d>=b?"account-selector":"none"}return b},Nka=function(a,b){b=void 0===b?{Rm:C$,Lp:_.Wa(window.location,"env_disableStartupDialog"),Pp:Kka}:b;var c=b.Lp,d=b.Pp,e=b.Rm;return new _.Zg(function(f){Lka(a)||Mka(a)?f(a):c?f(e()):_.jq(_.r.get(_.EH).Od()).then(function(g){g=d(g);f(g?g:C$())}).catch(function(){f(e())})})},Kka=function(a,b){b=void 0===b?Jka:b;b=b({numberOfAccounts:a.length,accountSelectorMinDeviceAccounts:_.D("accountSelectorMinDeviceAccounts",
1),Dm:"oauth"===_.D("supportedIdentityEnvironment","oauth"),Op:Ika()});Oka(b);switch(b){case "welcome":return Mia();case "account-selector":return Kia(a.map(function(c){return c.accountItemRenderer}))}},Qka=function(a){var b=_.r.get(_.tV);a={id:a,part:"snippet",hl:b.language,key:_.u("INNERTUBE_API_KEY","unknown")};var c=_.Ka(_.Ia(new _.Ga("https://www.googleapis.com"),"/youtube/v3/videos"),_.Jc(a));return new _.Zg(function(d){_.dT(c.toString(),{method:"GET"}).g(_.Ar(function(e){_.C(e);d({});return _.np})).subscribe(function(e){var f,
g,h,l={};if(e=null===(f=e.items)||void 0===f?void 0:f[0]){if(null===(g=null===e||void 0===e?void 0:e.snippet)||void 0===g?0:g.title)l.title=e.snippet.title;if(null===(h=null===e||void 0===e?void 0:e.snippet)||void 0===h?0:h.thumbnails)l.thumbnailDetails={thumbnails:Object.values(e.snippet.thumbnails||[]).filter(Pka)}}d(l)})})},Pka=function(a){return!!a},H$=function(a){if(a.h)if(a.username&&a.name){var b=_.V("MDX_DEVICE_CONNECTED_WITH_NAME_TOAST_TITLE",{username:a.username});var c=_.V("MDX_DEVICE_CONNECTED_WITH_DEVICE_TOAST_TITLE",
{device:a.name});b={title:b,subtitle:c}}else b=a.username?{title:_.V("MDX_DEVICE_CONNECTED_WITH_NAME_TOAST_TITLE",{username:a.username})}:a.name?{title:_.V("MDX_DEVICE_CONNECTED_WITH_DEVICE_TOAST_TITLE",{device:a.name})}:{title:_.V("MDX_DEVICE_CONNECTED_TOAST_TITLE")};else b=a.username?{title:_.V("MDX_DEVICE_DISCONNECTED_WITH_NAME_TOAST_TITLE",{username:a.username})}:{title:_.V("MDX_DEVICE_DISCONNECTED_TOAST_TITLE")};b=_.xA({title:b.title,subtitle:b.subtitle,style:"OVERLAY_MESSAGE_STYLE_TOAST"});
a.avatar?b.image={thumbnails:[{url:a.avatar,width:90,height:90},{url:a.avatar,width:120,height:120}]}:b.image={thumbnails:[{url:Rka,width:168,height:168}]};return{openPopupAction:{popupType:"TOAST",popup:{overlayMessageRenderer:b}}}},I$=function(a,b){var c={title:Ska(a),style:"OVERLAY_MESSAGE_STYLE_TOAST"};c.subtitle="PLAYLIST_ADDED"===a.eventType?_.V("MDX_UPDATE_QUEUE_ADD_PLAYLIST_TOAST_MESSAGE",{videoCount:String(a.videoIds.length)}):null===b||void 0===b?void 0:b.title;(null===b||void 0===b?0:b.thumbnailDetails)?
c.image=b.thumbnailDetails:a.userAvatarUri&&(c.image={thumbnails:[{url:a.userAvatarUri,width:120,height:120}]});return{openPopupAction:{popupType:"TOAST",popup:{overlayMessageRenderer:_.xA(c)}}}},Ska=function(a){var b=a.user;a=a.eventType;if(b)switch(a){case "VIDEO_ADDED":return _.V("MDX_UPDATE_QUEUE_ADD_VIDEO_TOAST_TITLE_WITH_USER",{user:b});case "VIDEO_REMOVED":return _.V("MDX_UPDATE_QUEUE_REMOVED_VIDEO_TOAST_TITLE_WITH_USER",{user:b});case "PLAYLIST_ADDED":return _.V("MDX_UPDATE_QUEUE_ADD_PLAYLIST_TOAST_TITLE_WITH_USER",
{user:b});case "PLAYLIST_CLEARED":return _.V("MDX_UPDATE_QUEUE_CLEAR_PLAYLIST_TOAST_TITLE_WITH_USER",{user:b});default:return""}else switch(a){case "VIDEO_ADDED":return _.V("MDX_UPDATE_QUEUE_ADD_VIDEO_TOAST_TITLE_WITHOUT_USER");case "VIDEO_REMOVED":return _.V("MDX_UPDATE_QUEUE_REMOVED_VIDEO_TOAST_TITLE_WITHOUT_USER");case "PLAYLIST_ADDED":return _.V("MDX_UPDATE_QUEUE_ADD_PLAYLIST_TOAST_TITLE_WITHOUT_USER");case "PLAYLIST_CLEARED":return _.V("MDX_UPDATE_QUEUE_CLEAR_PLAYLIST_TOAST_TITLE_WITHOUT_USER");
default:return""}},Uka=function(){var a=_.L.R(),b=_.Wu();_.D("supportsInAppDialVerticalLaunch",!1)||_.N(a,new _.O("showInAppDialVerticalLaunchFailureToastAction"),document,function(){b.resolveCommand(Tka)});_.N(a,new _.O("mdx-session:remoteConnected"),document,function(c){b.resolveCommand(H$(c.device))});_.N(a,new _.O("mdx-session:remoteDisconnected"),document,function(c){b.resolveCommand(H$(c.device))});_.N(a,new _.O("mdx-watch:updatePlaylist"),document,function(c){var d=c.eventDetails,e;d&&((c=
(null===(e=d.videoIds)||void 0===e?void 0:e[0])||d.videoId)?Qka(c).then(function(f){b.resolveCommand(I$(d,f))}):"PLAYLIST_CLEARED"===d.eventType&&b.resolveCommand(I$(d)))});hja({tf:function(){return _.r.get(_.s5)},zp:_.lG})},Vka=function(){return{Om:[Xia,Dka,kja,Cka,Uka,Fka,aja],To:Nka,Jo:[Via,Gka],$m:function(){return _.I(_.rj(_.iT))}}},Wka=function(){var a=_.r.get(_.gG),b=_.r.get(_.KI),c=_.r.get(_.tv),d=_.r.get(_.nv),e=_.r.get(_.vd),f=_.r.get(_.vG),g=_.r.get(_.BT),h=_.L.R(),l={eventTrigger:"ACCOUNT_EVENT_TRIGGER_CLEAR_STORAGE",
nextEndpoint:{browseEndpoint:{browseId:"FEtopics"}}},n=_.Ka(new _.Ga("/leanback_ajax"),_.mG({action_clear:1,client:"lb4"}));_.jq(_.dT(n.toString(),{}));_.gh([c.get(),_.BH(d)]).then(function(q){var t=_.A(q);q=t.next().value;t=t.next().value;t=_.A(Object.values(t));for(var w=t.next();!w.done;w=t.next())w=w.value,_.ur(q,w)||(w={identity:w,identityActionContext:l},_.Eo(h,new _.O("removeIdentityAction"),w));_.Yc.clear();e.clear();for(var y in _.rG)_.rG.hasOwnProperty(y)&&f.setValue(y,_.rG[y]);a.zg();b.clearHistory();
g.g.clear();g.storage.remove(_.yT);_.sr(q)&&(y={identity:q,identityActionContext:l},_.Eo(h,new _.O("removeIdentityAction"),y));_.Wu().resolveCommand(_.gT());return!0})},Yka=function(a){var b=_.r.get(_.kA);if(!a.command.commandMetadata||!a.command.commandMetadata.webCommandMetadata)throw Error("rb");b=_.jq(b.fetch({path:a.command.commandMetadata.webCommandMetadata.url,payload:a.form,clickTracking:{clickTrackingParams:a.command.clickTrackingParams}}));return[_.I(b).then(function(c){return(c=Xka(a.command,
c))?_.Wu().resolveCommand(c):!0})]},Xka=function(a,b){var c,d;if(a&&a.subscribeEndpoint||a.unsubscribeEndpoint){var e=!!a.subscribeEndpoint;e=_.Dt(document,"subscription-change",!1,[e?a.subscribeEndpoint.channelIds[0]:a.unsubscribeEndpoint.channelIds[0],e])}else e=null;e&&document.dispatchEvent(e);(e=a&&a.likeEndpoint&&a.likeEndpoint.target&&a.likeEndpoint.target.videoId?_.Dt(document,"video-liked",!1,[a.likeEndpoint.target.videoId,a.likeEndpoint.status]):null)&&document.dispatchEvent(e);if(a.remoteTransactionDialogEndpoint)return _.JY();
if(a.ypcOffersEndpoint){if(a=b&&b.offers&&b.offers.tvfilmRichOffersRenderer){b=_.Sx(a.title);b=_.zA({title:b});e=a.offersRenderer&&a.offersRenderer.offerButtonListRenderer&&a.offersRenderer.offerButtonListRenderer.offerButtons||[];for(var f=[],g=0;g<e.length;g++){var h=e[g].buttonRenderer;h&&f.push({compactLinkRenderer:{navigationEndpoint:h.command,secondaryIcon:{iconType:"CHEVRON_RIGHT"},title:h.text,trackingParams:h.trackingParams}})}a=_.vA({overlayPanelRenderer:_.AA(b,f),contextualPanelRenderer:a.sellableItemRenderer})}else a=
_.Y_();return a}if(a.ypcGetCartEndpoint)return null!==b&&void 0!==b&&b.encryptedPurchaseParams?{commandExecutorCommand:{commands:[_.Z_(!0),{initializePurchaseCommand:{encryptedPurchaseParams:b.encryptedPurchaseParams,offerParams:b.offerParams,serializedTransactionFlowLoggingParams:b.serializedTransactionFlowLoggingParams}}]}}:_.Y_();if(a.flagEndpoint)return b=(a=b.reportActionResultSupportedRenderers)&&a.reportActionResultMessageRenderer&&a.reportActionResultMessageRenderer.responseMessage,a&&a.videoReportActionResultRenderer&&
a.videoReportActionResultRenderer.responseMessage||b?(a=_.V("REPORT_SUCCESS_CONFIRM_TITLE"),b=_.V("REPORT_SUCCESS_CONFIRM_MESSAGE"),a={openPopupAction:{popupType:"TOAST",popup:{overlayMessageRenderer:_.xA({title:a,subtitle:b,style:"OVERLAY_MESSAGE_STYLE_TOAST"})}}}):(a=_.V("SERVICE_FAILURE_TOAST_TITLE"),a={openPopupAction:{popupType:"TOAST",popup:{overlayMessageRenderer:_.xA({title:a,style:"OVERLAY_MESSAGE_STYLE_TOAST"})}}}),a;if(a.pauseWatchHistoryEndpoint||a.resumeWatchHistoryEndpoint)return{browseEndpoint:{browseId:"FEmy_youtube"}};
if(a.clearSearchHistoryEndpoint)return _.yA({title:_.V("CLEAR_SEARCH_HISTORY_TOAST"),icon:{iconType:"CLEAR_SEARCH_HISTORY"}});if(null===(d=null===(c=a.clearWatchHistoryEndpoint)||void 0===c?void 0:c.actions)||void 0===d?0:d.length)return{commandExecutorCommand:{commands:a.clearWatchHistoryEndpoint.actions}}},lja=function(a,b){return a.j.bj(b).then(function(c){c&&b&&a.g.remove(b.effectiveObfuscatedGaiaId);return c})},Zka=function(a){return!!a.storage.find(function(b){return _.eI(b)})},B$=function(a){a.ra.fetch({path:"youtubei/v1/history/get_history_paused_state"}).g(_.Ar(function(b){return a.onError(b)})).subscribe(function(b){a.watchHistoryPaused=
!!b.watchHistoryPaused;a.searchHistoryPaused=!!b.searchHistoryPaused})},D$=function(a,b,c){_.G7(a,{ME:{type:c,LE:b}})},F$=["welcome"],G$=function(a){switch(a){case "welcome":return"whos_watching_fullscreen_zero_accounts";case "account-selector":return"whos_watching_fullscreen"}return"startup-screen-none"},Oka=function(a){var b=_.r.get(_.t9);_.YK(b.recurringActionTracker,G$(a))},X9=function(a,b){return _.BH(a).then(function(c){c=c[b];if(!c)throw Error("Fa`"+b);return c})},nka=function(a,b){var c=_.vv(b);
return c?1===c.gaiaDelegationType?X9(a,c.Vh):X9(a,c.ownerId).then(function(d){return{identityType:"CORE_ID",identityCredentials:d.identityCredentials,effectiveObfuscatedGaiaId:c.Vh,ownerObfuscatedGaiaId:c.ownerId,gaiaDelegationType:c.gaiaDelegationType}}):_.I(_.Er)},$ka=function(a,b){_.Yp("/tv?persist_gl=1&gl="+b+"&prev_gl="+a.clientData.clientLocation,{method:"GET"}).subscribe(function(c){c.ok&&(a.clientData.clientLocation=b,_.Wu().resolveCommand({applicationSettingsEndpoint:{itemId:"I18N_REGION"}}))})},
ala=function(a,b){b={method:"POST",headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"},body:_.Jc({base_url:"/tv",session_token:a.clientData.xsrfToken||"",hl:b})};_.Yp("/picker_ajax?action_update_language=1",b).subscribe(function(c){c.ok&&(a.storage.set(_.sG,!0),_.lG())})},ska=function(a){var b=_.r.get(_.KI);(0,_.ZH)(a).then(function(c){var d=_.zI(c).webPageType;b.history.push({type:d,command:c.command,Da:{},created:_.Jo()})})},bla=function(){return"fontSize".replace(/\-([a-z])/g,
function(a,b){return b.toUpperCase()})},cla=function(a){return a.replace(/(^|[\s]+)([a-z])/g,function(b,c,d){return c+d.toUpperCase()})},J$=function(a,b){var c=[];if(_.Cc(b,a))c.push("[...circular reference...]");else if(a&&50>b.length){c.push(_.Ag(a)+"(");for(var d=a.arguments,e=0;d&&e<d.length;e++){0<e&&c.push(", ");var f=d[e];switch(typeof f){case "object":f=f?"object":"null";break;case "string":break;case "number":f=String(f);break;case "boolean":f=f?"true":"false";break;case "function":f=(f=
_.Ag(f))?f:"[fn]";break;default:f=typeof f}40<f.length&&(f=f.substr(0,40)+"...");c.push(f)}b.push(a);c.push(")\n");try{c.push(J$(a.caller,b))}catch(g){c.push("[exception trying to get caller]\n")}}else a?c.push("[...long stack...]"):c.push("[end]");return c.join("")},K$=function(a){var b=Error();if(Error.captureStackTrace)Error.captureStackTrace(b,a||K$),b=String(b.stack);else{try{throw b;}catch(c){b=c}b=(b=b.stack)?String(b):null}b||(b=J$(a||arguments.callee.caller,[]));return b},L$=function(a,b){a instanceof
Error||(a=Error(a),Error.captureStackTrace&&Error.captureStackTrace(a,L$));a.stack||(a.stack=K$(L$));if(b){for(var c=0;a["message"+c];)++c;a["message"+c]=String(b)}return a},Yia=function(a){return new _.Zg(function(b,c){a.length||b(void 0);for(var d=0,e;d<a.length;d++)e=a[d],_.fh(e,b,c)})},M$={},N$=function(){var a=document.documentElement,b=Math.min(window.innerHeight/720*100,window.innerWidth/1280*100)+"%";var c=M$.fontSize;if(!c){var d=bla();c=d;void 0===a.style[d]&&(d=(_.hg?"Webkit":_.gg?"Moz":
_.dg?"ms":_.cg?"O":null)+cla(d),void 0!==a.style[d]&&(c=d));M$.fontSize=c}c&&(a.style[c]=b)},z9=!1,Sia={context:_.HS,command:_.GS},A9=function(a){this.Kf=a};A9.prototype.call=function(a,b){a=new _.ir(a);var c=_.gp(a,this.Kf);return c&&!a.g?(a.add(c),b.subscribe(a)):a};var dla=function(a,b,c,d){var e=_.fH();_.Vu.instance?_.jb(Error("xa")):_.Vu.instance=new _.Vu(a,b,c,d,e)},O$=function(a){_.e0(this,a,-1,null,null)};_.ke(O$,_.Bk);O$.prototype.F=function(){return _.b0(this,1)};
O$.prototype.D=function(){return _.b0(this,2)};var L9=function(a){_.e0(this,a,-1,null,null)};_.ke(L9,_.Bk);var Tia=function(a){var b,c=null==(b=_.b0(a,1))?void 0:b;if(a=b=a.D()){var d;a={errorType:null==(d=_.b0(b,1))?void 0:d,cancelType:null==(d=_.b0(b,2))?void 0:d,altFlowType:null==(d=_.b0(b,3))?void 0:d,gaiaLookupStatus:null==(d=_.b0(b,4))?void 0:d,gaiaChallengeStatus:null==(d=_.b0(b,5))?void 0:d,httpStatusCode:null==(d=_.b0(b,6))?void 0:d}}return{eventType:c,details:a}};
L9.prototype.F=function(){return _.b0(this,1)};L9.prototype.D=function(){this.g||(this.g={});if(!this.g[2]){var a=_.b0(this,2);a&&(this.g[2]=new O$(a))}return this.g[2]};var K9=function(a){var b=Error.call(this,"DSIError - type "+a);this.message=b.message;"stack"in b&&(this.stack=b.stack);this.type=a};_.G(K9,Error);
var m$="fontFamily color fontSizeIncrement textOpacity charEdgeStyle background backgroundOpacity windowColor windowOpacity".split(" "),Rja={number:"intValue",string:"stringValue","boolean":"boolValue"},M9={Js:0,Os:1,KC:2,QC:3,IC:4,MC:5,GC:6,HC:7,LC:8,OC:9,JC:10,PC:11,FC:12,NC:13,Is:14,Mq:15,Nq:16,Ks:17,er:18,gr:19,Hs:20,oq:21,nq:22,qq:23,Ms:24,AB:25,BB:26,Ns:27,Bs:28,Ds:29,Gs:30,Es:31,Cs:32,As:33,Fs:34,Ls:35,0:"FEEDBACK_BASE_CATEGORY",1:"FEEDBACK_VIDEO_PLAYBACK",2:"VP_GENERIC",3:"VP_VIDEO_QUALITY",
4:"VP_BUFFERING",5:"VP_NO_AUDIO",6:"VP_BAD_AUDIO",7:"VP_BLACK_SCREEN",8:"VP_GREEN_SCREEN",9:"VP_SEEKING",10:"VP_CAPTIONS",11:"VP_SKIP_AD",12:"VP_AUTOPLAY",13:"VP_OTHER",14:"FEEDBACK_APP_NOT_WORKING",15:"APP_CRASHING",16:"APP_FREEZING",17:"FEEDBACK_CASTING",18:"CAST_CONNECTING",19:"CAST_NO_VIDEO",20:"FEEDBACK_ACCOUNTS",21:"ACC_SIGN_IN",22:"ACC_SIGNED_OUT",23:"ACC_SWITCHING",24:"FEEDBACK_SEARCH",25:"SCH_GENERIC",26:"SCH_VOICE",27:"FEEDBACK_SUGGEST",28:"FEAT_COMMENT",29:"FEAT_HD4K",30:"FEAT_VSP",31:"FEAT_LIVESTREAM",
32:"FEAT_DOWNLOAD",33:"FEAT_BLOCK_CHANNEL",34:"FEAT_OTHER",35:"FEEDBACK_PLAYBACK_ERROR"},Gja=[_.B5,_.C5,_.D5,_.E5,_.F5,_.G5],Lka=function(a){var b=["startWelcomeCommand","startAccountSelectorCommand"];return C9(a).some(function(c){return Object.keys(c).some(function(d){return d.endsWith("Endpoint")||b.includes(d)})})},Mka=function(a){return C9(a).some(function(b){return Object.keys(b).some(function(c){return c.endsWith("Action")})})},P$=["WELCOME_THEME_SLOMO","WELCOME_THEME_VAGABROTHERS","WELCOME_THEME_MEGAN"];
_.m().w("main");
/*

 Copyright (c) 2014-2016 GitHub, Inc.

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
(function(a){function b(z){"string"!==typeof z&&(z=String(z));if(/[^a-z0-9\-#$%&'*+.\^_`|~]/i.test(z))throw new TypeError("Oc");return z.toLowerCase()}function c(z){"string"!==typeof z&&(z=String(z));return z}function d(z){this.map={};z instanceof d?z.forEach(function(H,M){this.append(M,H)},this):z&&Object.getOwnPropertyNames(z).forEach(function(H){this.append(H,z[H])},this)}function e(z){if(z.bodyUsed)return Promise.reject(new TypeError("Pc"));z.bodyUsed=!0}function f(z){return new Promise(function(H,
M){z.onload=function(){H(z.result)};z.onerror=function(){M(z.error)}})}function g(z){var H=new FileReader;H.readAsArrayBuffer(z);return f(H)}function h(){this.bodyUsed=!1;this.Dj=function(z){this.fg=z;if("string"===typeof z)this.gg=z;else if(w.blob&&Blob.prototype.isPrototypeOf(z))this.Hd=z;else if(w.formData&&FormData.prototype.isPrototypeOf(z))this.Aj=z;else if(!z)this.gg="";else if(!w.arrayBuffer||!ArrayBuffer.prototype.isPrototypeOf(z))throw Error("Qc");this.headers.get("content-type")||("string"===
typeof z?this.headers.set("content-type","text/plain;charset=UTF-8"):this.Hd&&this.Hd.type&&this.headers.set("content-type",this.Hd.type))};w.blob?(this.blob=function(){var z=e(this);if(z)return z;if(this.Hd)return Promise.resolve(this.Hd);if(this.Aj)throw Error("Rc");return Promise.resolve(new Blob([this.gg]))},this.arrayBuffer=function(){return this.blob().then(g)},this.text=function(){var z=e(this);if(z)return z;if(this.Hd){z=this.Hd;var H=new FileReader;H.readAsText(z);return f(H)}if(this.Aj)throw Error("Sc");
return Promise.resolve(this.gg)}):this.text=function(){var z=e(this);return z?z:Promise.resolve(this.gg)};w.formData&&(this.formData=function(){return this.text().then(n)});this.json=function(){return this.text().then(JSON.parse)};return this}function l(z,H){H=H||{};var M=H.body;if(l.prototype.isPrototypeOf(z)){if(z.bodyUsed)throw new TypeError("Pc");this.url=z.url;this.credentials=z.credentials;H.headers||(this.headers=new d(z.headers));this.method=z.method;this.mode=z.mode;M||(M=z.fg,z.bodyUsed=
!0)}else this.url=z;this.credentials=H.credentials||this.credentials||"omit";if(H.headers||!this.headers)this.headers=new d(H.headers);z=H.method||this.method||"GET";var aa=z.toUpperCase();this.method=-1<y.indexOf(aa)?aa:z;this.mode=H.mode||this.mode||null;this.referrer=null;if(("GET"===this.method||"HEAD"===this.method)&&M)throw new TypeError("Tc");this.Dj(M)}function n(z){var H=new FormData;z.trim().split("&").forEach(function(M){if(M){var aa=M.split("=");M=aa.shift().replace(/\+/g," ");aa=aa.join("=").replace(/\+/g,
" ");H.append(decodeURIComponent(M),decodeURIComponent(aa))}});return H}function q(z){var H=new d;z.getAllResponseHeaders().trim().split("\n").forEach(function(M){M=M.trim().split(":");var aa=M.shift().trim();H.append(aa,M.join(":").trim())});return H}function t(z,H){H||(H={});this.type="default";this.status=H.status;this.ok=200<=this.status&&300>this.status;this.statusText=H.statusText;this.headers=H.headers instanceof d?H.headers:new d(H.headers);this.url=H.url||"";this.Dj(z)}if(!a.fetch){d.prototype.append=
function(z,H){z=b(z);H=c(H);var M=this.map[z];M||(M=[],this.map[z]=M);M.push(H)};d.prototype["delete"]=function(z){delete this.map[b(z)]};d.prototype.get=function(z){return(z=this.map[b(z)])?z[0]:null};d.prototype.has=function(z){return this.map.hasOwnProperty(b(z))};d.prototype.set=function(z,H){this.map[b(z)]=[c(H)]};d.prototype.forEach=function(z,H){Object.getOwnPropertyNames(this.map).forEach(function(M){this.map[M].forEach(function(aa){z.call(H,aa,M,this)},this)},this)};var w={blob:"FileReader"in
a&&"Blob"in a&&!0,formData:"FormData"in a,arrayBuffer:"ArrayBuffer"in a},y="DELETE GET HEAD OPTIONS POST PUT".split(" ");l.prototype.clone=function(){return new l(this)};h.call(l.prototype);h.call(t.prototype);t.prototype.clone=function(){return new t(this.fg,{status:this.status,statusText:this.statusText,headers:new d(this.headers),url:this.url})};t.error=function(){var z=new t(null,{status:0,statusText:""});z.type="error";return z};var B=[301,302,303,307,308];t.redirect=function(z,H){if(-1===B.indexOf(H))throw new RangeError("Uc");
return new t(null,{status:H,headers:{location:z}})};a.Headers=d;a.Request=l;a.Response=t;a.fetch=function(z,H){return new Promise(function(M,aa){var ya=l.prototype.isPrototypeOf(z)&&!H?z:new l(z,H);var ua=new XMLHttpRequest;ua.onload=function(){var wb=1223===ua.status?204:ua.status;if(100>wb||599<wb)aa(new TypeError("Vc"));else{var rc=ua.statusText,dd=q(ua);var Tb="responseURL"in ua?ua.responseURL:/^X-Request-URL:/m.test(ua.getAllResponseHeaders())?ua.getResponseHeader("X-Request-URL"):void 0;M(new t("response"in
ua?ua.response:ua.responseText,{status:wb,statusText:rc,headers:dd,url:Tb}))}};ua.onerror=function(){aa(new TypeError("Vc"))};ua.open(ya.method,ya.url,!0);"include"===ya.credentials&&(ua.withCredentials=!0);"responseType"in ua&&w.blob&&(ua.responseType="blob");ya.headers.forEach(function(wb,rc){ua.setRequestHeader(rc,wb)});ua.send("undefined"===typeof ya.fg?null:ya.fg)})};a.fetch.ZE=!0}})("undefined"!==typeof self?self:this);
var ela=Math.floor(1E3/60);
_.al=JSON.stringify;
(function(a){if(a.requestAnimationFrame&&(a.cancelAnimationFrame||a.cancelRequestAnimationFrame))a.cancelAnimationFrame=a.cancelAnimationFrame||a.cancelRequestAnimationFrame;else{for(var b=["webkit","moz","o","ms"],c=0;c<b.length;++c){var d=b[c],e=d+"RequestAnimationFrame",f=d+"CancelAnimationFrame";d+="CancelRequestAnimationFrame";a[e]&&(a[f]||a[d])&&(a.requestAnimationFrame=a[e],a.cancelAnimationFrame=a[f]||a[d])}a.requestAnimationFrame&&a.cancelAnimationFrame||(a.requestAnimationFrame=function(g){return a.setTimeout(function(){g((new Date).getTime())},
ela)},a.cancelAnimationFrame=a.clearTimeout)}})(window);_.sh=function(a){_.nd(L$(a,"goog.Promise rejected!"))};
var w$=function(a){_.e0(this,a,-1,null,null)};_.ke(w$,_.Bk);w$.prototype.getVisitorData=function(){return _.b0(this,1)};w$.prototype.setVisitorData=function(a){return _.c0(this,1,a)};
var P9=!1,bja=[],U9=0,S9=null,T9=null,Q9=_.gd;
var eja=[_.zS,_.d8,_.h8,_.$7,_.f8,_.l8,_.o8,_.U7,_.zH,_.DS];
var W9={};
var fla=function(){var a=Vka();var b=void 0===b?window.location:b;this.g=a;this.location=b;this.h=_.Wu();this.j=_.r.get(_.M7)},gla=function(a){_.Tu(a,function c(){var d=this,e,f;return _.Su(c,function(g){e=d.g;f=e.Om;return _.Ju(g,Q$(d,f),0)})})},hla=function(a){_.Tu(a,function c(){var d=this,e,f,g,h,l,n,q,t;return _.Su(c,function(w){switch(w.g){case 1:return e=d.g,f=e.To,h=(g=_.Qa(d.location))?g:(d.location.search||"")+"&"+(d.location.hash||"").replace(/#[^?]*\??/,""),l=_.Ua(h),_.I7(l,!0),_.Ku(w,
2),_.Ju(w,_.L7(d.j,l),4);case 4:return n=w.h,_.Ju(w,f(n),5);case 5:return q=w.h,_.Ju(w,d.h.resolveCommand(q),6);case 6:_.Lu(w,0);break;case 2:t=_.Mu(w),_.C(t),w.g=0}})})},ila=function(a,b){return a.g.Jo.reduce(function(c,d){return _.ih(c.then(function(e){return e?e:d(b).then(function(f){return f})}),function(e){_.C(e);return c})},_.I(void 0)).then(function(c){c&&a.h.resolveCommand(c)})},jla=function(a){_.Tu(a,function c(){var d=this,e;return _.Su(c,function(f){if(1==f.g)return _.Ju(f,d.g.$m(),2);
e=f.h;return _.Ju(f,Q$(d,e),0)})})},Q$=function(a,b){return _.Tu(a,function d(){var e,f,g,h;return _.Su(d,function(l){switch(l.g){case 1:e=0,f=b.length;case 2:if(!(e<f)){l.ua(0);break}_.Ku(l,5);g=b[e]();if(!g){l.ua(7);break}return _.Ju(l,g,7);case 7:_.Lu(l,3);break;case 5:h=_.Mu(l),_.C(h);case 3:++e,l.ua(2)}})})};
var jja=["yt-dev.token-time-c","yt-dev.account-credentials-c","yt-dev.active-account-c"];
var H9={isSupported:!1,enableDebugParameters:!1};
var R$={},nja=(R$[9]=!0,R$[0]=!0,R$);
var oja={mouseup:"keyup",mousedown:"keydown"};
var qja=_.ZS(pja);
var b$=function(a){_.Q.call(this,a);this.template=sja;this.state={oi:!1}};_.G(b$,_.Q);b$.getDerivedStateFromProps=function(a,b){return{oi:b.oi||a.active}};b$.TAG_NAME="yt-route";
var kla=_.p("YtlrSurfacePage","cjuHYd");
var tja={signalAction:{signal:"HISTORY_BACK"}};
var S$=function(){_.Q.apply(this,arguments);var a=this;this.template=yja;this.h=function(b){return{tvSecondaryNavRenderer:{I:_.ZO,props:{focused:b&&a.props.focused}},tvSurfaceContentRenderer:{I:_.yO,props:{focused:b&&a.props.focused,Do:!0}}}}};_.G(S$,_.Q);S$.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;S$.TAG_NAME="ytlr-tv-browse-renderer";
var c$=function(a){_.Q.call(this,a);var b=this;this.template=wja;this.bedrockPorts=_.ov();this.recurringActionTracker=_.r.get(_.oK);this.A=_.L.R();this.H=_.r.get(_.oJ);this.F=_.r.get(_.$t);this.legendConfig=_.D("legendConfig",_.Tj).Jj;this.C=_.$G(function(c){_.S(b,{hideBackButton:c})},0);this.D=function(c){return{browseResponse:{I:S$,props:{idomKey:"content",data:b.props.data.browseResponse&&b.props.data.browseResponse.contents&&b.props.data.browseResponse.contents.tvBrowseRenderer||{},focused:c,
focusId:"browse",onLeftFocus:b.props.hasBackButton&&"primary-nav"}},settingsResponse:{I:S$,props:{idomKey:"content",data:b.props.data.settingsResponse&&b.props.data.settingsResponse.contents&&b.props.data.settingsResponse.contents.tvBrowseRenderer||{},focused:c,focusId:"browse",onLeftFocus:!1}}}};this.state={hideBackButton:!1}};_.G(c$,_.Q);c$.getDerivedStateFromProps=_.Q.getDerivedStateFromProps;
c$.prototype.W=function(a){T$(this,a);this.props.data.browseResponse&&!_.v.isEmpty(this.props.data.browseResponse)&&a.data!==this.props.data&&this.B()};
c$.prototype.O=function(){var a=this;_.T(this,function(){a.props.hasBackButton&&a.j("enableGuideAction")});_.T(this,_.vD(this.F,function(){a.H.stop()}));this.g("focusin",function(){a.B()});T$(this);_.T(this,_.N(_.L.R(),new _.O("continuationRenderedAction"),this,this.B));_.T(this,_.N(this.A,new _.O("SHOW_GUIDE"),this,function(){a.props.hasBackButton&&a.C(!1)}));_.T(this,_.N(this.A,new _.O("HIDE_GUIDE"),this,function(){a.props.hasBackButton&&a.C(!0)}))};
c$.prototype.h=function(a){this.props.data.selection=a;_.qx(this)};c$.prototype.isFocused=function(a){return this.props.focused?this.props.data.settingsResponse&&!_.v.isEmpty(this.props.data.settingsResponse)?!0:!this.props.data.browseResponse||_.v.isEmpty(this.props.data.browseResponse)?!!this.props.hasBackButton&&"primary-nav"===a:a===this.T:!1};
var T$=function(a,b){a.props.hasBackButton?(a.j("disableGuideAction"),b&&!a$(a)&&a.C(!1)):(a$(a)||_.v.isEmpty(a.props.command||{}))&&a.props.focused&&(a.j("enableGuideAction"),b&&!b.hasBackButton&&b.focused&&!a$(a,b)&&a$(a)&&a.j("resetGuideAction"))};c$.prototype.j=function(a){_.Eo(this.A,new _.O(a),void 0)};var a$=function(a,b){b=void 0===b?a.props:b;return!_.v.isEmpty(b.data.browseResponse||b.data.settingsResponse||{})};
c$.prototype.B=function(){var a=this,b=this.bedrockPorts;b&&_.f2("ytlr-surface-page",function(){return xja(a.el)},function(c){b.markVisible(c.map(_.g2))})};var vja=function(a){if(a.bedrockPorts)return[];var b=[];a.props.focused&&b.push({Xb:"back"});a.legendConfig.vd&&b.push({Xb:"search",ma:function(){_.U(a,"innertube-command",{searchEndpoint:{query:""}})}});return b};_.E.Object.defineProperties(c$.prototype,{T:{configurable:!0,enumerable:!0,get:function(){return this.props.data.selection||"browse"}}});
c$.TAG_NAME="ytlr-surface-page";_.J(kla,c$);
var Aja=_.NN(_.BO),Cja=_.NN(_.Kz),zja=_.NN(_.vT),Bja=_.NN(_.CT);
var l$={openClientOverlayAction:{type:"CLIENT_OVERLAY_TYPE_CAPTIONS_STYLE",updateAction:!0}};
var Sja={type:"CLIENT_OVERLAY_TYPE_CAPTIONS_SETTING",updateAction:!0};
var lla=_.vA({overlayPanelRenderer:_.AA(_.zA({title:_.V("EXIT_YOUTUBE_TITLE"),subtitle:_.V("EXIT_YOUTUBE_SUBTITLE")}),[{compactLinkRenderer:{title:{simpleText:_.V("EXIT")},serviceEndpoint:{commandExecutorCommand:{commands:[{signalAction:{signal:"CLOSE_POPUP"}},{signalAction:{signal:"EXIT_APP"}}]}}}}])});
var U$=function(a){_.Q.call(this,a);var b=this;this.template=Dja;this.j=_.L.R();this.A=_.Wu();this.ra=_.r.get(_.kA);this.navigation=_.r.get(_.KI);this.B=_.fH();this.C=_.r.get(_.YU);this.J=_.r.get(_.oJ);this.S=function(c){b.h("content");_.P(c)};this.H=_.wx(function(c){return _.$H(null===c||void 0===c?void 0:c.browseEndpoint)});this.P=function(c){_.S(b,{Oa:0===c})};this.G=function(c){_.S(b,{Ke:c})};this.F=function(c){c=(void 0===c?window:c).h5vcc;2!==(c&&c.system&&c.system.userOnExitStrategy)&&b.A.resolveCommand(lla)};
this.state={T:"content",Rg:!1,Oa:!1,Je:{selectedIndex:0},Ke:!!_.D("initiallyShowDebugConsole",!1),xj:"absent",nj:!_.gI(a.ha)}};_.G(U$,_.Q);var mla=function(a,b){var c=_.r.get(_.KI);return _.eI(a.ha)?a.ha.Le&&"stashed"===b.xj?"reinstated":"new":Zka(c.history)?"stashed":"absent"};U$.getDerivedStateFromProps=function(a,b){return Object.assign(Object.assign({},b),{Rg:b.Rg&&"guide"===b.T,xj:mla(a,b),nj:b.nj&&!_.gI(a.ha)})};
U$.prototype.O=function(){var a=this;this.g("focusin",function(){V$(a)});_.T(this,_.N(this.j,new _.O("guideResponseAction"),this,this.N));_.$8(this.ra);this.g("guide-expanding",function(){_.S(a,{Rg:!0})});this.g("innertube-continuation-request",function(b){nla(a,b.detail)});_.T(this,_.N(this.j,new _.O("continuationRenderedAction"),this,function(b){if(b.fc)if(b.parent){_.lH(a.B,b.fc,_.Ix(b.parent));var c=a.props.ha.Da;c.continuations||(c.continuations=[]);c.continuations.push(b)}else _.C(Error("Yc"));
else _.C(Error("Zc"));V$(a)}));_.T(this,_.N(this.j,new _.O("openClientOverlayAction"),this,function(b){Vja(b)}));_.T(this,_.N(this.j,new _.O("SEND_FEEDBACK"),this,function(b){Yja(b)}));_.T(this,_.N(this.j,new _.O("networkFailedAction"),this,function(){a.J.stop();_.Wu().resolveCommand(Eja())}));_.T(this,this.C.Ke.g("value-changed",this.G));_.N(this.j,new _.O("initializePurchaseCommand"),this,function(b){_.rj(_.f0).then(function(c){c.initialize(b)})});document.addEventListener("keydown",function(b){191===
b.keyCode&&a.A.resolveCommand(_.B2("TV_VOICE_OVERLAY_TRIGGER_UNKNOWN"))});_.T(this,_.N(this.j,new _.O("mdx-voice-query:initiated"),this,function(){a.A.resolveCommand(_.B2("TV_VOICE_OVERLAY_TRIGGER_MDX"))}))};
U$.prototype.W=function(a,b){if(b.Je!==this.state.Je){var c=b.Je.trackingParams;if(c){b=this.B;c=_.Ix(c);for(var d=_.A(b.j.keys()),e=d.next();!e.done;e=d.next())if(e=e.value,e.toString()===c.toString()){b.j.delete(e);break}}(b=this.state.Je.trackingParams)&&_.dH(this.B,_.Ix(b));V$(this)}this.props.ha.Da.response!==a.ha.Da.response&&_.gI(this.props.ha)&&V$(this)};U$.prototype.h=function(a){_.r.get(_.cT).play(0);_.S(this,{T:a})};U$.prototype.isFocused=function(a){return this.T===a&&!this.state.Oa};
var V$=function(a){_.f2("ytlr-app",function(){return _.c2(a.el)},function(b){for(var c=0;c<b.length;c++)_.nA(b[c])})};U$.prototype.N=function(a){a=a.response;var b=this.navigation.Va();b=(b=null===b||void 0===b?void 0:b.command)?_.Z8(b,a):-1;a.selectedIndex=0>b?(0,_.c9)(a):b;a.qf=!0;_.S(this,{Je:a})};
var nla=function(a,b){var c=a.props.ha.command.commandMetadata.webCommandMetadata.url;c?a.ra.fetch({path:c,payload:{context:{clickTracking:{clickTrackingParams:b.continuation.clickTrackingParams}},continuation:b.continuation.continuation}}).subscribe(function(d){return _.Tu(a,function f(){var g=this;return _.Su(f,function(h){v$(d,g.props.ha);b.onResponse(d);h.g=0})})}):_.C(Error("Xc"))};
_.E.Object.defineProperties(U$.prototype,{hasBackButton:{configurable:!0,enumerable:!0,get:function(){return this.H(this.props.ha.command)}},T:{configurable:!0,enumerable:!0,get:function(){return this.state.T}},V:{configurable:!0,enumerable:!0,get:function(){return this.state.Rg&&!this.state.Oa}},D:{configurable:!0,enumerable:!0,get:function(){return!!_.D("enableWatermark",!1)}}});U$.TAG_NAME="ytlr-app";
var hka="brand model model_year platform platform_detail chipset browser browser_version network os os_version".split(" ");
var pka=_.yA({title:_.V("YOURE_SIGNED_OUT"),subtitle:_.V("SIGN_IN_PROMPT"),icon:{iconType:"ACCOUNT_CIRCLE"}});
var oka={signalAction:{signal:"RESET_ACCOUNTS_LIST"}},A$={};
var ola=_.Zw(function(){var a=document.getElementById("loader");a&&(_.ct("remove_splash"),a&&a.parentNode&&a.parentNode.removeChild(a));document.renderPostponed&&(document.renderPostponed=!1)});
var vka={signalAction:{signal:"SHOW_LOGO"}};
var E$=function(){var a=this;this.g=_.r.get(_.oJ);this.h=_.r.get(_.w5);this.F=_.r.get(_.s5);this.navigation=_.r.get(_.KI);this.D=this.navigation.Va();this.B=function(){};this.j=function(){};this.A=this.C=!1;this.navigation.subscribe(function(b){_.eI(b)||a.D&&_.dI(b,a.D)||(a.D=b,a.end())})};
E$.prototype.start=function(a,b,c,d){c=void 0===c?function(){}:c;var e=a.playbackEndpoint;a=a.muted;e&&(this.A=!0,this.g.load(),this.lb=b?b?b.getBoundingClientRect():void 0:void 0,W$(this,c),_.mJ(this.g,Eka(e)),a&&this.g.mute(!0),_.kJ(this.g,d))};
E$.prototype.adopt=function(a,b,c){var d=this;b=void 0===b?function(){}:b;if(!pla(this,a))return this.end(),!1;this.A=!0;_.kJ(this.g,c);b(!0);a=function(){X$(d)};W$(this,b,{timingFunction:"ease-in-out",durationMs:200,completeCallback:a,cancelCallback:a});return!0};var W$=function(a,b,c){c=void 0===c?null:c;a.j();a.j=_.iJ(a.g,function(d){b(d);d||Y$(a)});a.g.setInline(!0);X$(a,!!c);_.u5(a.h,{lb:a.lb,Ld:"cover",transition:c})};
E$.prototype.end=function(){this.A=!1;this.j();this.g.isInline()&&!this.C&&this.g.stop();this.g.muted&&this.g.unMute(!0);this.C?this.C=!1:(Y$(this),this.g.setInline(!1))};E$.prototype.select=function(a){this.j();if(a.blockAdoption||!this.g.qb)return this.end(),!1;this.C=!0;X$(this,!0);_.u5(this.h,{lb:this.h.A,Ld:"contain",transition:{timingFunction:"ease-in-out",durationMs:300,completeCallback:Z$(this,a),cancelCallback:Z$(this,a)}});return!0};E$.prototype.isActive=function(){return this.A};
var Z$=function(a,b){return function(){b&&b.playbackEndpoint&&(_.Wu().resolveCommand({watchEndpoint:Object.assign({},b.playbackEndpoint.watchEndpoint)}),Y$(a),_.q5(a.F,"syncedPlaybackChanged",void 0))}},X$=function(a,b){a.B();a.B=_.u5(a.h,{Ka:["ytlr-player--enabled","ytlr-player--playbackPreview",b?"ytlr-player--transitioning":""]})},Y$=function(a){a.B();a.B=function(){};_.u5(a.h,a.h.j)},pla=function(a,b){if(b.blockAdoption||!a.g.qb||a.g.na)return!1;a=b.playbackEndpoint&&b.playbackEndpoint.watchEndpoint&&
b.playbackEndpoint.watchEndpoint.videoId;return!!a&&_.OI()===a};
var $$={VB:"skip",SC:"welcome-and-account-selector",Rs:"force-account-selector"},Hka=Object.keys($$).reduce(function(a,b){b=$$[b];a[b]=b;return a},{});
var Lia=P$[Math.floor(Math.random()*P$.length)];
var Rka=_.td("mdx-unknown-device-connection-toast-icon.png");
var Tka=_.yA({title:_.V("DIAL_VERTICAL_LAUNCH_FAILURE_MSG")});
(function(){var a=this;(0,_.sx)();qka();rka();rja();_.r.get(_.kA);var b=_.r.get(_.KI),c=_.r.get(_.vG),d=_.r.get(_.ky),e=_.L.R();_.N(e,new _.O("HISTORY_BACK"),document,function(){_.JI(b)});_.N(e,new _.O("EXIT_APP"),document,function(){var l=window;_.Ib();_.r.get(_.oJ).stop();var n=l.h5vcc&&l.h5vcc.system&&l.h5vcc.system.userOnExitStrategy;if(void 0!==n)switch(n){case 0:l.close();break;case 1:l.minimize()}else l.NetCastExit?l.NetCastExit():(n=l.tizen&&l.tizen.application&&l.tizen.application.getCurrentApplication&&
l.tizen.application.getCurrentApplication())?n.hide():l.curWidget&&l.curWidget.setPreference?l.curWidget.setPreference("return","true"):l.$badger&&l.$badger.shutdown?l.$badger.shutdown():l.close()});_.N(e,new _.O("RESET_APP"),document,function(){Wka()});_.N(e,new _.O("RELOAD_PAGE"),document,function(){_.lG()});_.N(e,new _.O("setClientSettingEndpoint"),document,function(l){if((l=l.command.setClientSettingEndpoint.settingDatas)&&l.length){l=_.A(l);for(var n=l.next();!n.done;n=l.next()){n=n.value;var q=
n.clientSettingEnum&&n.clientSettingEnum.item;q&&(n.stringValue&&"I18N_LANGUAGE"===q?ala(c,n.stringValue):n.stringValue&&"I18N_REGION"===q?$ka(c,n.stringValue):_.pG[q]||void 0===n.boolValue||(c.setValue(q,n.boolValue),"ENABLE_HIGH_CONTRAST_MODE"===q&&(document.body.classList.contains("high-contrast")?document.body.classList.remove("high-contrast"):document.body.classList.add("high-contrast"))))}}});dla(_.ZH,function(l){return[b.navigate(l)]},Yka,e.h.bind(e));var f=document.getElementById("container");
f.addEventListener("innertube-command",function(l){_.Wu().resolveCommand(l.detail)});document.addEventListener("keydown",function(l){27===l.keyCode&&_.Wu().resolveCommand({signalAction:{signal:"HISTORY_BACK"}})});_.D("isCobaltHybridNav",!1)||window.addEventListener("scroll",function(l){l.target instanceof Element&&(l.target.scrollLeft||l.target.scrollTop)&&(l.target.scrollLeft=0,l.target.scrollTop=0)},!0);var g=new fla;gla(g);var h=_.Zw(function(){_.Mt({stage:20,X:function(){ila(g,b.Va().type).then(function(){jla(g)})}})});
b.subscribe(function(l){return _.Tu(a,function q(){var t,w;return _.Su(q,function(y){v$(l.Da.response,l);(t=_.gI(l)&&"WEB_PAGE_TYPE_BROWSE"===l.type&&(!l.Le||_.D("enableBackNavigationTimelines",!0)))&&d.tick("c_rns");_.gI(l)&&(ola(),h());var B=[];_.D("enableAnimations",!0)?B.push("full-animation"):_.D("isLimitedMemory",!1)?B.push("limited-animation limited-memory"):B.push("limited-animation");_.D("enableZds",!1)&&B.push("exp-zds");_.D("enableZdsTypeAndSpacing",!1)&&B.push("exp-zds-enable-type-and-spacing");
_.D("enableZdsRoundedCorners",!1)&&B.push("exp-zds-tile-rounded-corners");_.D("enableZdsIncreaseTileSize",!1)&&B.push("exp-zds-increase-tile-size");var z=_.D("zdsTileTitleSizeRem",void 0);void 0!==z&&B.push("exp-zds-tile-title-size-rem--"+z.toString().replace(/\./g,"-"));_.D("enableNonCobaltLgStyles",!1)&&B.push("lg");w=B.join(" ");document.body.className=l.type+" "+w;_.CP(f,U$,{ha:l});t&&d.tick("c_rnf");y.g=0})})});hla(g);N$();window.addEventListener("resize",function(){N$()})})();


_.m().l();

}catch(e){_._DumpException(e)}
})(this._yttv);
// Google Inc.
