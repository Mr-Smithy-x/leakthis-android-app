package nyc.vonley.leakthis.ui.main.inbox.conversation.adapter

import android.graphics.drawable.Drawable
import android.text.Html
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import net.danlew.android.joda.DateUtils
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.models.LeakThisConversation
import nyc.vonley.leakthis.util.URLImageParser
import org.joda.time.DateTime
import java.util.*


class MessageRecyclerAdapter(val me: Int): RecyclerView.Adapter<MessageRecyclerAdapter.MessageViewHolder>(),
    URLImageParser.URLImageParserCallBack {

    var messages = ArrayList<LeakThisConversation.LeakThisMessage>()

    fun add(messages: ArrayList<LeakThisConversation.LeakThisMessage>?) {
        if(messages != null){
            this.messages.addAll(messages.filter { this.messages.map { m -> m.id }.indexOf(it.id) < 0 })
        }
        notifyDataSetChanged()
    }

    inner class MessageViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var message_text = view.findViewById<AppCompatTextView>(R.id.view_holder_conversation_message_text)
        var message_bubble = view.findViewById<RelativeLayout>(R.id.view_holder_conversation_message_bubble)
        var message_time = view.findViewById<AppCompatTextView>(R.id.view_holder_conversation_message_time)

        fun setMessage(message: LeakThisConversation.LeakThisMessage) {
            message_text.text =
                Html.fromHtml(message.message,
                    Html.FROM_HTML_MODE_COMPACT,
                    URLImageParser(message_text, this@MessageRecyclerAdapter),
                    Html.TagHandler { opening, tag, output, xmlReader ->  }
                )
            message_time.text = DateUtils.getRelativeTimeSpanString(itemView.context, DateTime(message.created * 1000))
            val params = message_bubble.layoutParams as RelativeLayout.LayoutParams
            val timeParams = message_time.layoutParams as RelativeLayout.LayoutParams
            val padding_80 = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                80f,
                itemView.resources.displayMetrics
            ).toInt();
            val padding_16 = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                16f,
                itemView.resources.displayMetrics
            ).toInt();
            when (me) {
                message.user.id -> {
                    params.removeRule(RelativeLayout.ALIGN_PARENT_START)
                    timeParams.removeRule(RelativeLayout.ALIGN_PARENT_START)

                    params.addRule(RelativeLayout.ALIGN_PARENT_END)
                    timeParams.addRule(RelativeLayout.ALIGN_PARENT_END)

                    params.marginEnd = padding_16
                    params.marginStart = padding_80

                    timeParams.marginStart = padding_16
                    timeParams.marginEnd = padding_16

                    message_bubble.backgroundTintList = itemView.resources.getColorStateList(R.color.leakthis_pastel_red, itemView.context.theme)
                }
                else -> {
                    message_bubble.backgroundTintList = itemView.resources.getColorStateList(R.color.gray_600, itemView.context.theme)
                    params.removeRule(RelativeLayout.ALIGN_PARENT_END)
                    timeParams.removeRule(RelativeLayout.ALIGN_PARENT_END)

                    params.addRule(RelativeLayout.ALIGN_PARENT_START)
                    timeParams.addRule(RelativeLayout.ALIGN_PARENT_START)

                    timeParams.marginStart = padding_16
                    timeParams.marginEnd = padding_16
                    params.marginEnd = padding_80
                    params.marginStart = padding_16
                }
            }
            message_bubble.layoutParams = params
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.view_holder_conversation_message,
            parent,
            false
        )
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.setMessage(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun add(messages: LeakThisConversation.LeakThisMessage) {
        this.messages.add(messages)
        notifyDataSetChanged()
    }

    override fun onLoaded(url: String?, drawable: Drawable?) {
        Log.e("LOADED", url ?: "FAILED")
    }
}