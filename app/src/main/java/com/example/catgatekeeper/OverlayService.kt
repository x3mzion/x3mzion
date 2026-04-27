package com.example.catgatekeeper

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.*
import android.view.*
import android.widget.*

class OverlayService : Service() {

    private lateinit var wm: WindowManager
    private lateinit var view: View

    override fun onCreate() {
        super.onCreate()

        wm = getSystemService(WINDOW_SERVICE) as WindowManager

        val img = ImageView(this)
        img.setImageResource(android.R.drawable.ic_menu_gallery)

        val layout = FrameLayout(this)
        layout.setBackgroundColor(0xCC000000.toInt())
        layout.addView(img)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        view = layout
        wm.addView(view, params)

        Handler(Looper.getMainLooper()).postDelayed({
            stopSelf()
        }, 20000)
    }

    override fun onDestroy() {
        super.onDestroy()
        wm.removeView(view)
    }

    override fun onBind(intent: Intent?) = null
}
