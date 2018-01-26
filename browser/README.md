# SmartYouTubeTV Browser Engine

__Browser engine with wide capabilities (e.g. display naked page)__

## Highlights
- Used in conjunction with [smartyoutubetv]
- Engine can be used independently (currently in development)
- Based on **marshmallow-release** branch of [android open source project][browser-origin]

## Quick start
To build project navigate to the root directory then type:
```
gradlew build
```
Generated apks will be placed in the directory ./smartyoutubetv/build/outputs/apk/

## Browser engine usage
### Basic usage
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
### More lifecircle handling and custom engine usage
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

## Benefits of this browser engine over regular WebView
- ability to choose preferred engine type: WebView, XWalk
- you don't have to deal with creating custom methods for managing WebView lifecircle (pause, resume etc)
- ability to easily add tabs, settings and other regular browser-specific features
- all internal state is saved into persistent memory instead of RAM

## Used libraries
- Crosswalk - alternative browser engine
- Otto - message bus
- OkHttp - http processing
- Android Logger - logger factory implementation
- Crashlytics - multipurpose crash analytics
- Butter Knife - simplify layout handling

## Crashlytics
Version name, code and package will be loaded from gradle build script. 
[Crashlytics credentials](https://docs.fabric.io/android/fabric/settings/working-in-teams.html#android-projects) can be controlled from `fabric.properties` file.

_app/fabric.properties_
```ini
apiSecret=7c9df6d057e7bb62b17ab364e8115a75fcf7430873b6274bb094d1a8adb
apiKey=cc238b2a4866ceb061b008839cdb49a8b77
```
You can get `apiSecret` and `apiKey` from the fabric dashboard [fabric.io/settings/organizations](https://fabric.io/settings/organizations) where they were created after successful first-time signup with Crashlytics plugin GUI for Android Studio.

## TODO
- exo: fix play from phone (because main app not running: mobizen_20170929_111409.mp4)
- exo: add 'share' button
- subtitle support

## Proposition list
- playlist: more informative playlist layout
- playlist: add shuffle option
- video-item: add comments section
- video-item: add description section

## DONE
- XWalk: remember selected resolution
- XWalk: add 4k version
- auto update
- live video > select higher quality
- beta: remember selected resolution
- exo: tune rewind step from 1min to 15 sec
- auto update
- exo: fix brightness (-webkit-filter:brightness(0))
- market: add to aptoide
- select version on launch
- System.exit(0)
- add 60 fps support
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

## NOTES
- Activity->onLowMemory() -> throw exception; tune webview to display complex sites
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
- optimization tips
    - android:largeHeap="true"
    - android:hardwareAccelerated="true"
    - assets folder xwalk-command-line: xwalk --ignore-gpu-blacklist

## Other
- how to fetch sources from [android open source project][browser-origin] via console:
	- `git clone -b marshmallow-release https://android.googlesource.com/platform/packages/apps/Browser`

[browser-origin]: https://android.googlesource.com/platform/packages/apps/Browser
[smartyoutubetv]: https://github.com/yuliskov/SmartYouTubeTV/tree/master/smartyoutubetv
[browser]: https://github.com/yuliskov/SmartYouTubeTV/tree/master/browser
