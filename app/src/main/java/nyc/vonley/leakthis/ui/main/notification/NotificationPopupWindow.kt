package nyc.vonley.leakthis.ui.main.notification

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.models.LeakThisNotification
import nyc.vonley.leakthis.ui.main.notification.adapters.NotificationRecyclerAdapter


class NotificationPopupWindow constructor(val view: View) : PopupWindow(view) {

    private var mRecyclerView: RecyclerView? = null

    fun showPopup() {
        val popupView: View =
            LayoutInflater.from(view.context).inflate(R.layout.popup_window_notification, null)
        contentView = popupView
        width = WindowManager.LayoutParams.WRAP_CONTENT
        height = WindowManager.LayoutParams.WRAP_CONTENT
        isFocusable = true

        val recyclerView =
            contentView.findViewById<RecyclerView>(R.id.popup_window_notification_recycler_view)

        val adapter = NotificationRecyclerAdapter()
        adapter.addNotifications(
            arrayListOf(
                LeakThisNotification(),
                LeakThisNotification(),
                LeakThisNotification(),
                LeakThisNotification(),
                LeakThisNotification()
            )
        )
        recyclerView.adapter = adapter
        showAtLocation(view, Gravity.TOP, view.x.toInt(), (view.height + (view.y * 2).toInt()))
    }

    companion object {


        fun create(view: View): NotificationPopupWindow {
            return NotificationPopupWindow(
                view
            )
        }
    }

}