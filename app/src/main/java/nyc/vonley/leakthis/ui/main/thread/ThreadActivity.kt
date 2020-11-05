package nyc.vonley.leakthis.ui.main.thread

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_thread.*
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.di.annotations.SharedPreferenceStorage
import nyc.vonley.leakthis.models.LeakThisForum
import nyc.vonley.leakthis.models.http.LeakThisPagination
import nyc.vonley.leakthis.ui.main.thread.adapter.ThreadRecyclerAdapter
import nyc.vonley.leakthis.util.PaginationScrollListener
import nyc.vonley.leakthis.util.SharedPreferenceManager
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class ThreadActivity : AppCompatActivity(), ThreadContract.View, View.OnClickListener {

    private var meta: LeakThisPagination? = null

    @Inject
    lateinit var presenter: ThreadContract.Presenter

    @Inject
    @SharedPreferenceStorage
    lateinit var preferences: SharedPreferenceManager

    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.hasExtra(KEY)) {
            setContentView(R.layout.activity_thread)
            activity_thread_recycler.adapter = ThreadRecyclerAdapter()
            activity_thread_recycler.addOnScrollListener(object : PaginationScrollListener(
                activity_thread_recycler.layoutManager as LinearLayoutManager?
            ) {
                override fun loadMoreItems() {
                    Toast.makeText(this@ThreadActivity,"Loading more threads...", Toast.LENGTH_SHORT).show()
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
            val thread: LeakThisForum.Thread = intent.getParcelableExtra(KEY)!!
            activity_thread_section_title.text = thread.topic
            activity_thread_section_desc.text = thread.title
            id = thread.id
            fetch(thread.id, 1)
            activity_thread_edit_text.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {

                    if (s !== null && s.isNotEmpty()) {
                        activity_thread_send_message.backgroundTintList =
                            resources.getColorStateList(R.color.leakthis_pastel_red, theme)
                    } else {
                        activity_thread_send_message.backgroundTintList =
                            resources.getColorStateList(R.color.gray_600, theme)
                    }
                }
            })
            activity_thread_send_message.setOnClickListener(this)
        } else {
            Toast.makeText(
                this,
                "Something went horribly wrong. Unable to see the thread :I",
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
    }

    fun fetch(id: Int, page: Int) {
        presenter.fetch(id, page)
    }

    fun next() {
        if(meta != null) {
            fetch(id, meta!!.current + 1)
        }
    }

    override fun onThreadResponse(
        it: ArrayList<LeakThisForum.ThreadPost>,
        meta: LeakThisPagination?
    ) {
        (activity_thread_recycler.adapter as ThreadRecyclerAdapter).addPosts(it)
        this.meta = meta
    }

    override fun onPostSubmitted(it: ArrayList<LeakThisForum.ThreadPost>) {
        (activity_thread_recycler.adapter as ThreadRecyclerAdapter).addPosts(it)
    }

    override fun onCommentFailed(comment: String?) {
        Toast.makeText(this, comment, Toast.LENGTH_SHORT).show()
    }

    override fun onError(e: Exception) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }

    companion object {

        const val KEY = "THREAD"

        fun start(context: Context, thread: LeakThisForum.Thread) {
            val options = Bundle()
            val intent = Intent(context, ThreadActivity::class.java).apply {
                putExtra(KEY, thread)
            }
            context.startActivity(intent, options)
        }
    }

    override fun onClick(v: View?) {
        Log.e("SENDING", activity_thread_edit_text.text.toString())
        presenter.comment(
            id,
            activity_thread_edit_text.text.toString()
        )
    }
}