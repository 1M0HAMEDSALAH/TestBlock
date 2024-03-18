// MyDeviceAdminReceiver.kt

package com.example.untitled1

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

// DeviceAdminReceiver to handle device admin events
class MyDeviceAdminReceiver : DeviceAdminReceiver() {
    // Called when device admin is enabled
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        // Show toast message indicating device admin is enabled
        Toast.makeText(context, "Device Admin Enabled", Toast.LENGTH_SHORT).show()
    }

    // Called when a request to disable device admin is received
    override fun onDisableRequested(context: Context, intent: Intent): CharSequence {
        // Return confirmation message
        return "Disabling device admin will remove all app restrictions. Do you want to continue?"
    }

    // Called when device admin is disabled
    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        // Show toast message indicating device admin is disabled
        Toast.makeText(context, "Device Admin Disabled", Toast.LENGTH_SHORT).show()
    }
}
