# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\portable\dev\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keepattributes *Annotation*
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

# Fix for 'dnsjava' warnings: Ignoring InnerClasses attribute for an anonymous inner class
# Details: https://stackoverflow.com/questions/35796144/progaurd-issue-warningignoring-innerclasses-attribute-for-an-anonymous-inner-c
-dontwarn InnerClasses

## obfuscate only app code but not libs
#-keep class !com.liskovsoft.** {*;}
#-keep interface !com.liskovsoft.** {*;}
#-keep enum !com.liskovsoft.** {*;}

