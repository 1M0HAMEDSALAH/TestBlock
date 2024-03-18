// MainActivity.kt

package com.example.untitled1

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.os.Build
import androidx.annotation.RequiresApi
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

// MainActivity.java


class MainActivity : FlutterActivity() {
    private var devicePolicyManager: DevicePolicyManager? = null
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        devicePolicyManager = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        channel.setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
            if (call.method == "disableApp") {
                val packageName = call.argument<String>("packageName")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    disableApp(packageName, result)
                }
            } else {
                result.notImplemented()
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun disableApp(packageName: String?, result: MethodChannel.Result) {
        val componentName = ComponentName(
            this,
            MyDeviceAdminReceiver::class.java
        )
        val packageNames = arrayOf(packageName)
        if (devicePolicyManager!!.isDeviceOwnerApp(packageName)) {
            devicePolicyManager!!.setPackagesSuspended(componentName, packageNames, true)
            result.success("App disabled successfully")
        } else {
            result.error("NOT_DEVICE_OWNER", "This app is not a device owner.", null)
        }
    }

    companion object {
        private const val CHANNEL = "app_suspension_channel"
    }
}

