# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/admin/Documents/sdk/tools/proguard/proguard-android.txt
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
-keepattributes Signature


#Weather相关混淆文件
-dontwarn cn.com.weather.util.**


#讯飞相关混淆文件
-dontwarn com.iflytek.**
-keep class com.iflytek.**{*;}


#高德相关混淆文件
-dontwarn com.amap.api.**
-dontwarn com.aps.**
#Location
-keep class com.amap.api.location.**{*;}
-keep class com.aps.**{*;}
#Service
-keep class com.amap.api.services.**{*;}
#2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}

#下载
-keep class com.j256.ormlite.**{*;}
-keep class com.github.snowdream.android.**{*;}
-keep class org.apache.commons.lang3.**{*;}

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference