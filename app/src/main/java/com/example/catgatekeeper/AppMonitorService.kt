package com.example.catgatekeeper

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.content.Intent

class AppMonitorService : AccessibilityService() {

    private var startTime = 0L
    private val LIMIT = 60 * 1000 // 1 минута

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {

            val pkg = event.packageName?.toString() ?: return

            if (pkg == "com.google.android.youtube") {

                if (startTime == 0L) startTime = System.currentTimeMillis()

                val timeSpent = System.currentTimeMillis() - startTime

                if (timeSpent > LIMIT) {
                    startService(Intent(this, OverlayService::class.java))
                }

            } else {
                startTime = 0
            }
        }
    }

    override fun onInterrupt() {}
}
