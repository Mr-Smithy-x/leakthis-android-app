package nyc.vonley.leakthis.ui.main.forum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_forum_thread.*
import kotlinx.android.synthetic.main.activity_thread.*
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.di.annotations.SharedPreferenceStorage
import nyc.vonley.leakthis.models.LeakThisForum
import nyc.vonley.leakthis.models.http.LeakThisPagination
import nyc.vonley.leakthis.ui.main.forum.adapter.ForumThreadRecyclerAdapter
import nyc.vonley.leakthis.util.PaginationScrollListener
import nyc.vonley.leakthis.util.SharedPreferenceManager
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class ForumThreadActivity : AppCompatActivity(), ForumThreadContract.View {


    @Inject
    lateinit var presenter: ForumThreadContract.Presenter

    @Inject
    @SharedPreferenceStorage
    lateinit var preferences: SharedPreferenceManager


    private var meta: LeakThisPagination? = null
    var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum_thread)
        activity_forum_thread_recycler.adapter = ForumThreadRecyclerAdapter()
        activity_forum_thread_recycler.addOnScrollListener(object : PaginationScrollListener(
            activity_forum_thread_recycler.layoutManager as LinearLayoutManager?
        ) {
            override fun loadMoreItems() {
                Toast.makeText(this@ForumThreadActivity,"Loading more threads...", Toast.LENGTH_SHORT).show()
                next()
            }

            override fun getTotalPageCount(): Int {
                return meta?.last ?: 1
            }

            override fun isLastPage(): Boolean {
                return meta?.current == meta?.last
            }

            override fun isLoading(): Boolean {
                return false
            }

        })
        if (intent.hasExtra(FORUMTHREAD_KEY)) {
            val thread: LeakThisForum.ForumThread = intent.getParcelableExtra(FORUMTHREAD_KEY)!!
            activity_forum_thread_section_title.text = "Forum - ${thread.title}"
            activity_forum_thread_section_desc.text = thread.description
            id = thread.id
            if (thread.bool_type.is_forum) {
                fetch(thread.id, 1)
            } else {
                Toast.makeText(
                    this,
                    "This is a category section.... That isn't setup yet",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else if (intent.hasExtra(SUBFORUM_KEY)) {
            val subforum: LeakThisForum.SubForum = intent.getParcelableExtra(SUBFORUM_KEY)!!
            id = subforum.forum_id
            activity_forum_thread_section_title.text = "Forum - ${subforum.title}"
            activity_forum_thread_section_desc.visibility = View.GONE
            fetch(subforum.forum_id, 1)
        } else {
            Toast.makeText(
                this,
                "Something went horribly wrong. Unable to see the thread :I",
                Toast.LENGTH_LONG
            ).show()
        }


    }

    fun fetch(forum_id: String, page: Int){
        presenter.fetch(forum_id, page)
    }

    fun next() {
        if(meta != null) {
            fetch(id!!, meta!!.current + 1)
        }
    }

    override fun onThreadResponse(it: ArrayList<LeakThisForum.Thread>, meta: LeakThisPagination?) {
        (activity_forum_thread_recycler.adapter as ForumThreadRecyclerAdapter).addThreads(it)
        this.meta = meta
    }

    override fun onError(e: Exception) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }


    companion object {

        const val FORUMTHREAD_KEY = "FORUMTHREAD"
        const val SUBFORUM_KEY = "SUBFORUMTHREAD"

        fun start(context: Context, thread: LeakThisForum.ForumThread) {
            val options = Bundle()
            val intent = Intent(context, ForumThreadActivity::class.java).apply {
                putExtra(FORUMTHREAD_KEY, thread)
            }
            context.startActivity(intent, options)
        }

        fun start(context: Context, thread: LeakThisForum.SubForum) {
            val options = Bundle()
            val intent = Intent(context, ForumThreadActivity::class.java).apply {
                putExtra(SUBFORUM_KEY, thread)
            }
            context.startActivity(intent, options)
        }
    }
}