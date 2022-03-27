-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** w(...);
    public static *** v(...);
    public static *** i(...);
}

-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.collect.MinMaxPriorityQueue
-dontwarn com.google.common.util.concurrent.FuturesGetChecked**
-dontwarn javax.lang.model.element.Modifier
-dontwarn afu.org.checkerframework.**
-dontwarn org.checkerframework.**

# Logging
-dontwarn javax.mail.**
-dontwarn javax.naming.Context
-dontwarn javax.naming.InitialContext
-dontwarn ch.qos.logback.core.net.*
-dontwarn java.awt.**,javax.activation.**,java.beans.**

# other
-keep public class * extends java.lang.Exception
-keep class ru.adchampagne.data.network.model.** { *; }
-keep class java.awt.datatransfer.DataFlavor {*;}

# Google maps
-keep class com.google.android.gms.maps.** { *; }

# Kotlinx serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt # core serialization annotations

# kotlinx-serialization-json specific. Add this if you have java.lang.NoClassDefFoundError kotlinx.serialization.json.JsonObjectSerializer
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class ru.adchampagne.data.**$$serializer { *; }
-keepclassmembers class ru.adchampagne.test.** {
    *** Companion;
}
-keepclasseswithmembers class ru.adchampagne.data.** {
    kotlinx.serialization.KSerializer serializer(...);
}
