package nyc.vonley.leakthis.ui.main.thread.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import net.danlew.android.joda.DateUtils
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.models.LeakThisForum
import nyc.vonley.leakthis.util.LeakThisHtmlTagHandler
import nyc.vonley.leakthis.util.URLImageParser
import org.joda.time.DateTime

class ThreadRecyclerAdapter: RecyclerView.Adapter<ThreadRecyclerAdapter.ThreadHolder>(),
    URLImageParser.URLImageParserCallBack {

    private val posts: ArrayList<LeakThisForum.ThreadPost> = ArrayList()


    inner class ThreadHolder(val view: View): RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var post: LeakThisForum.ThreadPost

        val desc = view.findViewById<AppCompatTextView>(R.id.view_holder_thread_description)
        val name = view.findViewById<AppCompatTextView>(R.id.view_holder_thread_user_name)
        val avatar = view.findViewById<CircleImageView>(R.id.view_holder_thread_user_avatar)
        val time = view.findViewById<AppCompatTextView>(R.id.view_holder_thread_time)
        val card = view.findViewById<CardView>(R.id.view_holder_thread_card)


        init {
            card.setOnClickListener(this)
        }

        fun setForumThread(post: LeakThisForum.ThreadPost) {
            this.post = post
            name.text = this.post.user.name
            desc.text = LeakThisHtmlTagHandler.create(desc, this.post.body_html)
            time.text = DateUtils.getRelativeTimeSpanString(itemView.context, DateTime(this.post.created * 1000))
            Glide.with(avatar).asBitmap().load(this.post.user.avatar)
                .fallback(R.drawable.ic_svg_user_2_white).into(avatar)
        }

        override fun onClick(v: View?) {

        }

    }

    fun addPosts(posts: ArrayList<LeakThisForum.ThreadPost>){
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_thread, parent, false)
        return ThreadHolder(view)
    }

    override fun onBindViewHolder(holder: ThreadHolder, position: Int) {
        holder.setForumThread(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onLoaded(url: String?, drawable: Drawable?) {
        Log.e("LOADED", url ?: "FAILED")
    }
}