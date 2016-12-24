#Smart YouTube TV

Imagine that you have ordinary chinese android box with simple remote controller.
What YouTube client you should use? Regular client doesnt understand remote controller.
Off course you may use official YouTube TV client for Nexus Player. But there is many drawback. 
One of them is deps (e.g. gapps) that box usually don't have.
Another drawback is that official client needs minimum Android 5.0. 
And there comes Smart YouTube TV. It require zero deps, minimum Android 4.4.

##Highlights
- consist of two parts: app and standalone browser engine:
	- [smartyoutubetv]: main app that using browser engine for rendering
	- [browser]: engine with wide capabilities (e.g. display naked page) 
- browser engine can be used independently (currently in development)
- browser engine based on **marshmallow-release** branch of [android open source project][browser-origin]

##Pros of browser engine over regular WebView
- you don't have to deal with creating custom methods for managing WebView lifecircle (pause, resume etc)
- ability to easily add tabs, settings and other regular browser-specific features
- all internal state is saved into persistent memory instead of RAM

##Basic usage
```
controller = new SimpleUiController(context);
controller.loadUrl("http://ya.ru");
```

##TODO
- investigate intent routing inside Browser app when opening url
- update UML diagram for MainController and SubControllers
- add txt file with concise project description
- show informative error when site not available
- more details: 
	- TabControl.SubWindow
	- DataController
	- BrowserWebView.CustomView
	- SnapshotBar
- add tests:
  - testCheckBackKeyIsHandled
  - testThatUserAgentHeaderMustNotBeOverriden 
  - testControllers
  - testThatWebViewCreatedAndAttachedToTheActivityRootView
  - testThatWevViewHeadersProperlySet

##DONE
- restore state (Bundle) when app unloaded from memory
- back key not closes video but exits from app
- dont play video in background
- keys not working
- add wide viewport
- hide TitleBar and other UI (Controller.enableBasicMode())
- fix constant activity reload
- remove ActionBar
- use logging framework slf4j + android logger backend
  - https://github.com/noveogroup/android-logger
  - http://www.slf4j.org/android/
  - http://stackoverflow.com/questions/10514175/which-android-logging-framework-to-use

##Other
- if you curious on which sources browser engine based:
	- `git clone -b marshmallow-release https://android.googlesource.com/platform/packages/apps/Browser`

[browser-origin]: https://android.googlesource.com/platform/packages/apps/Browser
[smartyoutubetv]: https://github.com/yuliskov/SmartYouTubeTV/tree/master/smartyoutubetv
[browser]: https://github.com/yuliskov/SmartYouTubeTV/tree/master/browser
