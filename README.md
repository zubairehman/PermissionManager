# PermissionManager

## How to use
**Step 1.** Add the `JitPack` repository to your build file. Add it in your root `build.gradle` at the end of repositories:
```gradle
allprojects {
    repositories {
    ...
    maven { url 'https://jitpack.io' }
    }
}
```
**Step 2.** Add the dependency
```
dependencies {
    implementation 'com.github.zubairehman:PermissionManager:v1.0.0-alpha01'
}
```

# PermissionManager

## How to use
To get a Git project into your build:

**Step 1.** Add the `JitPack` repository to your build file. Add it in your root `build.gradle` at the end of repositories:
```gradle
allprojects {
    repositories {
    ...
    maven { url 'https://jitpack.io' }
    }
}
```
**Step 2.** Add the dependency
```
dependencies {
    implementation 'com.github.zubairehman:PermissionManager:v1.0.0-alpha01'
}
```
### Callbacks
You can use three different callbacks depending upon your needs.

- **FullCallback:** gives you all the information on permission requested by you
- **SimpleCallback:** returns a boolean that says if all permission requests were permitted
- **SmartCallback:** returns a boolean that says if all permission requests were permitted and a boolean that says if some permissions are denied forever

### Open app settings
If user selects "Never ask again" to a request for permission, you can redirect user to app settings with a method provided by `PermissionUtils` class
```kotlin
PermissionUtils.openApplicationSettings(MainActivity.this, R.class.getPackage().getName());
```










