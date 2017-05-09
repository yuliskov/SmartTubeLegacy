#Smart YouTube TV

Imagine that you have ordinary Chinese android box with simple remote controller.
What YouTube client you should use? Regular client doesn't understand remote controller.
Off course you may use official YouTube TV client for Nexus Player. But there is many drawback. 
One of them is dependencies (e.g. gapps) that box usually don't have.
Another drawback is that official client needs minimum Android 5.0. 
And there comes Smart YouTube TV. It require zero dependencies, minimum Android 4.4.

##Highlights
- consist of two parts: app and standalone browser engine:
	- [smartyoutubetv]: main app that using browser engine for rendering
	- [browser]: engine with wide capabilities (e.g. display naked page) 
- browser engine can be used independently (currently in development)
- browser engine based on **marshmallow-release** branch of [android open source project][browser-origin]

##Quick start
To build project navigate to the root directory then type:
```
gradlew build
```
Generated apks will be placed in the directory ./smartyoutubetv/build/outputs/apk/

## Browser engine usage
###Basic usage
```
MyActivity extends MainBrowserActivity {
	@Override
	void onCreate(Bundle bundle) {
		super.onCreate();
		Controller controller = new SimpleUiController(this);
		controller.loadUrl("http://ya.ru");
		setController(controller);
	}
}
```
###More lifecircle handling and custom engine usage
```
MyActivity extends MainBrowserActivity {
	MyActivity() {
		// set preferred engine to XWalk (default - WebView)
		// we must set engine as early as possible
        Browser.setEngineType(EngineType.XWalk);
	}
	@Override
	void onCreate(Bundle bundle) {
		Controller controller = new SimpleUiController(this);
		Intent intent = (bundle == null) ? getIntent() : null;
		controller.start(intent, "http://ya.ru");
		setController(controller);
	}
}
```

##Benefits of this browser engine over regular WebView
- ability to choose preferred engine type: WebView, XWalk
- you don't have to deal with creating custom methods for managing WebView lifecircle (pause, resume etc)
- ability to easily add tabs, settings and other regular browser-specific features
- all internal state is saved into persistent memory instead of RAM

##Used libraries
- Crosswalk - alternative browser engine
- Otto - message bus
- OkHttp - http processing
- Android Logger - logger factory implementation
- Crashlytics - multipurpose crash analytics
- Butter Knife - simplify layout handling

##TODO
- add 60 fps support
- playlist: more informative playlist layout
- playlist: add shuffle option
- video-item: add comments section
- video-item: add description section
- show informative error when site not available

##DONE
- auto clear cache to maintain small footprint
- disable release logging
- bug with Resuming webview timers
- ability to set video quality
- adapt build system to multiple prduct flavors
- add Crashlytics crash reporting tool
- add Crosswalk backend
- apply codec fixes: MiTV3, MiTV3S-55, X92
- add DIAL (DIscovery And Launch) support
- add adblock
- add crash handler
- handle intents from external apps
- restore state (Bundle) when app unloaded from memory
- back key not closes video but exits from app
- don't play video in background
- keys not working
- add wide viewport
- fix constant activity reload
- remove ActionBar
- add system wide logging

##NOTES
- hide TitleBar and other UI (Controller.enableBasicMode())
- use logging framework slf4j + android logger backend
	- https://github.com/noveogroup/android-logger
	- http://www.slf4j.org/android/
	- http://stackoverflow.com/questions/10514175/which-android-logging-framework-to-use
- add multiple flavors: original (WebView) and beta (XWalk)
	- custom resources location: src/originalFlavor and src/regularFlavor
- use logging framework slf4j + android logger backend
	- https://github.com/noveogroup/android-logger
	- http://www.slf4j.org/android/
	- http://stackoverflow.com/questions/10514175/which-android-logging-framework-to-use
- review crash reporting tools (https://www.captechconsulting.com/blogs/a-comparison-of-android-crash-reporting-tools)
	- add Crashlytics crash reporting tool (https://fabric.io/kits/android/crashlytics/)
- Crosswalk docs:
	- https://crosswalk-project.org/documentation/android/embedding_crosswalk.html
	- https://crosswalk-project.org/documentation/shared_mode.html
	- https://crosswalk-project.org/apis/embeddingapidocs_v7/index.html?org/xwalk/core/XWalkInitializer.html
	- https://github.com/crosswalk-project/crosswalk-website/wiki/Shared-mode-for-Crosswalk-Core-Library-on-Android
- update UML diagram for MainController and SubControllers
- find more details: 
	- TabControl.SubWindow
	- DataController
	- BrowserWebView.CustomView
	- SnapshotBar
- add tests:
	- testThatPageLoadHandlerNotCalledTwise
	- testThatUrlIsNotOverridedAndHandledInternally
	- testCheckThatBackKeyNotClosesActivity
	- testThatCustomUserAgentHeaderNotOverridedBySettings
	- testThatWebViewCreatedAndAttachedToTheActivityView
	- testThatWebViewHeadersAreProperlySet
- add Chrome Custom Tabs backend (unable to implement: lack of api for setUserAgent, setUseWideViewPort)
	- https://developer.chrome.com/multidevice/android/customtabs

##Other
- how to fetch sources from [android open source project][browser-origin] via console:
	- `git clone -b marshmallow-release https://android.googlesource.com/platform/packages/apps/Browser`

[browser-origin]: https://android.googlesource.com/platform/packages/apps/Browser
[smartyoutubetv]: https://github.com/yuliskov/SmartYouTubeTV/tree/master/smartyoutubetv
[browser]: https://github.com/yuliskov/SmartYouTubeTV/tree/master/browser
