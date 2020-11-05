package nyc.vonley.leakthis.ui.main.notification.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.models.LeakThisNotification

class NotificationRecyclerAdapter :
    RecyclerView.Adapter<NotificationRecyclerAdapter.NotificationViewHolder>() {
    var notifications = ArrayList<LeakThisNotification>()

    inner class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var title = view.findViewById<AppCompatTextView>(R.id.popup_view_holder_notification_title)
        var time = view.findViewById<AppCompatTextView>(R.id.popup_view_holder_notification_time)
        var avatar = view.findViewById<CircleImageView>(R.id.popup_view_holder_notification_avatar)

        fun setNotification(notification: LeakThisNotification) {
            title.text = notification.title
            time.text = notification.time
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.popup_view_holder_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.setNotification(notifications[position])
    }

    override fun getItemCount(): Int {

        return notifications.size
    }

    fun addNotifications(items: ArrayList<LeakThisNotification>) {
        notifications.addAll(items)
        notifyDataSetChanged()
    }

}
