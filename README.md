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
### Request for permission
Requesting a permission is really easy, all you need to do is to pass in the permission(s) you want to request and the `PermissionManager` will do the work for you.
```kotlin
PermissionManager.Builder()
      .key(REQUEST_PERMISSIONS)
      .permission(PermissionEnum.READ_EXTERNAL_STORAGE, PermissionEnum.WRITE_EXTERNAL_STORAGE)
      .callback(this@MainActivity)
      .ask(this@MainActivity)
```
### Check for permission
In-order to check for permissions, simply call the code below:
```kotlin
val permissionEnum = PermissionEnum.WRITE_EXTERNAL_STORAGE
val granted = PermissionUtils.isGranted(this@MainActivity, PermissionEnum.WRITE_EXTERNAL_STORAGE)
Toast.makeText(this@MainActivity, permissionEnum.toString() + " isGranted [" + granted + "]", Toast.LENGTH_SHORT).show()
```

### Callbacks
You can use three different callbacks depending upon your needs.

- **FullCallback:** gives you all the information on permission requested by you
- **SimpleCallback:** returns a boolean that says if all permission requests were permitted
- **SmartCallback:** returns a boolean that says if all permission requests were permitted and a boolean that says if some permissions are denied forever

### Open app settings
If user selects "Never ask again" to a request for permission, you can redirect user to app settings by calling a method provided by `PermissionUtils` class as mentioned below:
```kotlin
PermissionUtils.openApplicationSettings(this@MainActivity, R::class.java.getPackage().name)
```










