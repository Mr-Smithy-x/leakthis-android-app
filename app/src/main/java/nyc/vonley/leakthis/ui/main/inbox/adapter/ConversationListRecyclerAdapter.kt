package nyc.vonley.leakthis.ui.main.inbox.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.models.LeakThisConversation
import nyc.vonley.leakthis.ui.main.inbox.conversation.ConversationActivity
import java.util.*

class ConversationListRecyclerAdapter :
    RecyclerView.Adapter<ConversationListRecyclerAdapter.ConversationViewHolder>() {

    var conversations = ArrayList<LeakThisConversation>()

    inner class ConversationViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private lateinit var conversation: LeakThisConversation
        val title = view.findViewById<AppCompatTextView>(R.id.view_holder_conversation_list_title)
        val name = view.findViewById<AppCompatTextView>(R.id.view_holder_conversation_list_name)
        val avatar = view.findViewById<CircleImageView>(R.id.view_holder_conversation_list_user_avatar)
        val time = view.findViewById<AppCompatTextView>(R.id.view_holder_conversation_list_time)
        val card = view.findViewById<CardView>(R.id.view_holder_conversation_list_card)


        init {
            val id = R.layout.view_holder_conversation_list
            card.setOnClickListener(this)
        }

        fun setConversation(conversation: LeakThisConversation) {
            this.conversation = conversation
            title.text = conversation.title
            name.text = conversation.users.map { it.name }.joinToString(",","", " ")
            Glide.with(avatar).asBitmap().load(conversation.users.first().avatar)
                .fallback(R.drawable.ic_svg_user_2_white).into(avatar)
            time.text = conversation.last_message_at.toString()
        }

        override fun onClick(v: View?) {
            ConversationActivity.start(itemView.context, conversation);
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_conversation_list, parent, false)
        return ConversationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.setConversation(conversations[position])
    }

    override fun getItemCount(): Int {
        return conversations.size
    }

    fun setList(it: ArrayList<LeakThisConversation>?) {
        if (it != null) {
            conversations = it
        }
        notifyDataSetChanged()
    }
}