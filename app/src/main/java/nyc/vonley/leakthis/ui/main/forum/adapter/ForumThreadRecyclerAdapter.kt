package nyc.vonley.leakthis.ui.main.forum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.models.LeakThisForum
import nyc.vonley.leakthis.ui.main.thread.ThreadActivity

class ForumThreadRecyclerAdapter: RecyclerView.Adapter<ForumThreadRecyclerAdapter.ForumThreadHolder>(){

    private val forumThreads: ArrayList<LeakThisForum.Thread> = ArrayList()


    inner class ForumThreadHolder(val view: View): RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var thread: LeakThisForum.Thread

        val title = view.findViewById<AppCompatTextView>(R.id.view_holder_forum_thread_title)
        val name = view.findViewById<AppCompatTextView>(R.id.view_holder_forum_thread_name)
        val avatar = view.findViewById<CircleImageView>(R.id.view_holder_forum_thread_user_avatar)
        val time = view.findViewById<AppCompatTextView>(R.id.view_holder_forum_thread_time)
        val card = view.findViewById<CardView>(R.id.view_holder_forum_thread_card)


        init {
            card.setOnClickListener(this)
        }

        fun setForumThread(forumThread: LeakThisForum.Thread) {
            this.thread = forumThread
            title.text = thread.title
            name.text = thread.user.name
            Glide.with(avatar).asBitmap().load(thread.user.avatar)
                .fallback(R.drawable.ic_svg_user_2_white).into(avatar)
            time.text = thread.created.toString()
        }

        override fun onClick(v: View?) {
            ThreadActivity.start(this.itemView.context, this.thread)
        }

    }

    fun addThreads(threads: ArrayList<LeakThisForum.Thread>){
        forumThreads.addAll(threads)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumThreadHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_forum_thread, parent, false)
        return ForumThreadHolder(view)
    }

    override fun onBindViewHolder(holder: ForumThreadHolder, position: Int) {
        holder.setForumThread(forumThreads[position])
    }

    override fun getItemCount(): Int {
        return forumThreads.size
    }
}