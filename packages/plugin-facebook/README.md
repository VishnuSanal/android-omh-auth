# Module plugin-facebook

## Set up your Facebook application

To access Facebook APIs, generate a unique **App ID** and **App Secret** for your app in the Meta
for Developers. Add the **App ID** and **App Secret** to your app's code and complete the required
Meta for Developers setup steps:

1. [Go to the Meta for Developers](https://developers.facebook.com/apps).
2. Click on "Create App" to start creating a new Facebook application.
3. Go to the App Settings -> Basic.
4. Set your application **Package Names** (Use "com.openmobilehub.android.auth.sample" if you are
   following the starter-code)
5. Set your application **Class Name** (Use "
   com.openmobilehub.android.auth.sample.base.MainActivity" if you are following the starter-code)
6. Provide the Development and Release Key Hashes for Your App To ensure the authenticity of the
   interactions between your app and Facebook, you need to supply us with the Android key hash for
   your development environment. If your app has already been published, you should add your release
   key hash too.

   ### Generating a Development Key Hash

   You'll have a unique development key hash for each Android development environment.

   #### Mac OS

   You will need the Key and Certificate Management Tool (keytool) from the Java Development Kit. To
   generate a development key hash, open a terminal window and run the following command:

   ```bash
   keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore | openssl sha1 -binary | openssl base64
   ```

   #### Windows

   You will need the following:

   - Key and Certificate Management Tool (keytool) from the Java Development Kit
   - openssl-for-windows openssl library for Windows from the Google Code Archive

   To generate a development key hash, run the following command in a command prompt in the Java SDK
   folder:

   ```bash
   keytool -exportcert -alias androiddebugkey -keystore "C:\Users\USERNAME\android\debug.keystore" | "PATH_TO_OPENSSL_LIBRARY\bin\openssl" sha1 -binary | "PATH_TO_OPENSSL_LIBRARY\bin\openssl" base64
   ```

   This command will generate a 28-character key hash unique to your development environment. Copy
   and paste it into the field below. You will need to provide a development key hash for the
   development environment of each person who works on your app.

   ### Generating a Release Key Hash

   Android apps must be digitally signed with a release key before you can upload them to the store.
   To generate a hash of your release key, run the following command on Mac or Windows substituting
   your release key alias and the path to your keystore:

   ```bash
   keytool -exportcert -alias YOUR_RELEASE_KEY_ALIAS -keystore YOUR_RELEASE_KEY_PATH | openssl sha1 -binary | openssl base64
   ```

   This will generate a 28-character string that you should copy and paste into the field below.
   Also, see the Android documentation for signing your apps.

   ### Adding the generated key hashes

   Once you generated the debug and release key hashes, add them under **Key Hashes** section and
   save your changes.

> To enable the email scope: in your [Meta for Developers](https://developers.facebook.com/apps)
> app, navigate to "Use cases" -> "Customize" -> "Permissions" and click **Add** for the **Email**
> permissions.

## Edit Your Resources and Manifest

Create strings for your Facebook app ID and for those needed to enable Chrome Custom Tabs. Also,
add `FacebookActivity` to your Android manifest.

1. Open your `/app/res/values/strings.xml` file.

2 Add string elements with the names `facebook_app_id`, `fb_login_protocol_scheme`
and `facebook_client_token`, and set the values to your **App ID** and **Client Token**. For
example, if your app ID is 1234 and your client token is 56789 your code looks like the following:

```xml
<string name="facebook_app_id">1234</string>
<string name="fb_login_protocol_scheme">fb1234</string>
<string name="facebook_client_token">56789</string>
```

3. Open the `/app/manifest/AndroidManifest.xml` file.

4. Add meta-data elements to the application element for your app ID and client token:

```xml
   <application android:label="@string/app_name" ...>
     ...
     <meta-data android:name="com.facebook.sdk.ApplicationId" android:value="@string/facebook_app_id"/>
     <meta-data android:name="com.facebook.sdk.ClientToken" android:value="@string/facebook_client_token"/>
     ...
   </application>
```

5. Add an activity for Facebook, and an activity and intent filter for Chrome Custom Tabs inside
   your application element:

```xml
<activity android:name="com.facebook.FacebookActivity"
    android:configChanges=
        "keyboard|keyboardHidden|screenLayout|screenSize|orientation"
    android:label="OMH" />

<activity
    android:name="com.facebook.CustomTabActivity"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="@string/fb_login_protocol_scheme" />
    </intent-filter>
</activity>
```

6. Add a uses-permission element to the manifest after the application element:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

7. (Optional) To opt out of the Advertising ID Permission, add a uses-permission element to the
   manifest after the application element:

```xml
<uses-permission android:name="com.google.android.gms.permission.AD_ID" tools:node="remove"/>
```

> Note: You may directly set the auto-logging of App Events to “true” or “false” by setting the
> AutoLogAppEventsEnabled flag in the AndroidManifest.xml file.

> You can find more information on how to integrate the Facebook SDK by following
> the [official documentation](https://github.com/facebook/facebook-android-sdk).

## Gradle configuration

To incorporate Facebook plugin into your project, you have to directly include the Facebook plugin
as a dependency. In the `build.gradle.kts`, add the following implementation statement to
the `dependencies{}` section:

```groovy
implementation("com.openmobilehub.android.auth:plugin-facebook:2.0.0")
```

Save the file
and [sync your project with Gradle](https://developer.android.com/studio/build#sync-files).

## Provide the Facebook OMH Auth Client

In the `SingletonModule.kt` file in the `:auth-starter-sample` module add the following code to
provide the Facebook OMH Auth Client.

```kotlin
@Provides
fun providesFacebookAuthClient(@ApplicationContext context: Context): FacebookAuthClient {
    return FacebookAuthClient(
        scopes = arrayListOf("public_profile", "email"),
        context = context,
    )
}
```

> We'd recommend to store the client as a singleton with your preferred dependency injection library
> as this will be your only gateway to the OMH Auth SDK and it doesn't change in runtime at all.
