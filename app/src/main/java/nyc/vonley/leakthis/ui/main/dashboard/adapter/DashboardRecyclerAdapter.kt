package nyc.vonley.leakthis.ui.main.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.models.LeakThisForum
import nyc.vonley.leakthis.ui.main.dashboard.DashboardContract
import nyc.vonley.leakthis.ui.main.forum.ForumThreadActivity

class DashboardRecyclerAdapter(val view: DashboardContract.View) :
    RecyclerView.Adapter<DashboardRecyclerAdapter.ForumViewHolder>() {

    private var forums = ArrayList<LeakThisForum>()

    fun setForums(forums: ArrayList<LeakThisForum>) {
        this.forums = forums
        notifyDataSetChanged()
    }


    inner class ForumViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var forum: LeakThisForum
        var section: AppCompatTextView = view.findViewById(R.id.view_holder_forum_section)
        var subtitle: AppCompatTextView = view.findViewById(R.id.view_holder_forum_section_subtitle)
        var recycler: RecyclerView = view.findViewById(R.id.view_holder_forum_recycler_view)

        fun setForum(forum: LeakThisForum) {
            this.forum = forum
            section.text = forum.category
            subtitle.text = forum.description
            recycler.adapter = ForumSectionThreadAdapter(forum.threads)
        }

        inner class ForumSectionThreadAdapter(val threads: ArrayList<LeakThisForum.ForumThread>) :
            RecyclerView.Adapter<ForumSectionThreadAdapter.ForumSectionViewHolder>() {

            inner class ForumSectionViewHolder(view: View) : RecyclerView.ViewHolder(view),
                View.OnClickListener {

                val avatar =
                    view.findViewById<CircleImageView>(R.id.view_holder_forum_section_thread_user_avatar)
                val title =
                    view.findViewById<AppCompatTextView>(R.id.view_holder_forum_section_thread_title)
                val description =
                    view.findViewById<AppCompatTextView>(R.id.view_holder_forum_section_thread_description)
                val subthread_action =
                    view.findViewById<AppCompatTextView>(R.id.view_holder_forum_section_sub_thread_action)
                val name =
                    view.findViewById<AppCompatTextView>(R.id.view_holder_forum_section_thread_user_name)
                val time =
                    view.findViewById<AppCompatTextView>(R.id.view_holder_forum_section_thread_time)

                private lateinit var thread: LeakThisForum.ForumThread

                init {
                    subthread_action.setOnClickListener(this)
                    title.setOnClickListener(this)
                    description.setOnClickListener(this)
                    avatar.setOnClickListener(this)
                    name.setOnClickListener(this)
                    time.setOnClickListener(this)
                }

                fun setThread(thread: LeakThisForum.ForumThread) {
                    this.thread = thread
                    title.text = thread.title
                    description.text = thread.description ?: thread.latest.title
                    Glide.with(avatar)
                        .asBitmap()
                        .load(thread.latest.user.avatar)
                        .fallback(R.drawable.ic_svg_user_2_white)
                        .into(avatar)
                    time.text = thread.latest.created_formatted
                    name.text = thread.latest.user.name
                    if (thread.sub_forums.size == 0) {
                        subthread_action.visibility = View.INVISIBLE
                    }
                }

                override fun onClick(v: View?) {
                    when (v?.id) {
                        title.id, description.id -> {
                            //TODO: Send To ThreadActivity
                            ForumThreadActivity.start(super.itemView.context, this.thread)
                        }
                        name.id, avatar.id, time.id -> {
                            //TODO: Send to UserActivity
                        }
                        subthread_action.id -> {
                            view.onShowSubForums(this.thread)
                        }
                    }
                }

            }

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): ForumSectionViewHolder {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_holder_forum_section_threads, parent, false)
                return ForumSectionViewHolder(view)
            }

            override fun onBindViewHolder(holder: ForumSectionViewHolder, position: Int) {
                holder.setThread(threads[position])
            }

            override fun getItemCount(): Int {
                return threads.size
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_forum, parent, false)
        return ForumViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        holder.setForum(forums[position])
    }

    override fun getItemCount(): Int {
        return forums.size
    }
}