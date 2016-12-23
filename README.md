#Smart YouTube TV

Imagine that you have ordinary chinese android box with simple remote controller.
What YouTube client you should use? Regular client doesnt understand remote controller.
Off course you may use official YouTube TV client for Nexus Player. But there is many drawback. 
One of them is deps (e.g. gapps) that box usually don't have.
Another drawback is that official client needs minimum Android 5.0. 
And there comes Smart YouTube TV. It require zero deps, minimum Android 4.4.

##Highlights
- consist of two parts: app and standalone browser engine
- browser engine can be used independently (currently in development)
- browser engine based on android open source browser project marshmallow branch (link1)

##Pros of browser engine
- you don't have to deal with creating custom methods for managing WebView lifecircle (pause, resume etc)
- with regular WebView you have to deal with some issues. For example WebView continue to play video at background.
- browser can have tabs, settings and other regular browser features
- all internal state is saved into persistent memory instead of RAM

(link1)
Based on: git clone -b marshmallow-release https://android.googlesource.com/platform/packages/apps/Browser
