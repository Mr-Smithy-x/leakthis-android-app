package nyc.vonley.leakthis.ui.main.inbox.conversation

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
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_conversation.*
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.di.annotations.SharedPreferenceStorage
import nyc.vonley.leakthis.models.LeakThisConversation
import nyc.vonley.leakthis.models.http.LeakThisPagination
import nyc.vonley.leakthis.ui.main.inbox.conversation.adapter.MessageRecyclerAdapter
import nyc.vonley.leakthis.util.PaginationScrollListener
import nyc.vonley.leakthis.util.SharedPreferenceManager
import javax.inject.Inject

@AndroidEntryPoint
class ConversationActivity : AppCompatActivity(), ConversationContract.View, View.OnClickListener {


    @Inject
    lateinit var presenter: ConversationContract.Presenter

    @Inject
    @SharedPreferenceStorage
    lateinit var preferences: SharedPreferenceManager

    var adapter: MessageRecyclerAdapter? = null
    var data: LeakThisConversation? = null

    private var meta: LeakThisPagination? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)
        data = intent.getParcelableExtra(KEY)
        if (data != null) {
            adapter = MessageRecyclerAdapter(preferences.getProfileId())
            activity_conversation_recycler_view.adapter = adapter
            activity_conversation_recycler_view.addOnScrollListener(object :
                PaginationScrollListener(
                    activity_conversation_recycler_view.layoutManager as LinearLayoutManager?
                ) {
                override fun loadMoreItems() {
                    next()
                }

                override fun getTotalPageCount(): Int {
                    return meta?.current ?: 1
                }

                override fun isLastPage(): Boolean {
                    return meta?.current == meta?.last
                }

                override fun isLoading(): Boolean {
                    return false;
                }

            })
            activity_conversation_edit_text.addTextChangedListener(object : TextWatcher {

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
                        activity_conversation_send_message.backgroundTintList =
                            resources.getColorStateList(R.color.leakthis_pastel_red, theme)
                    } else {
                        activity_conversation_send_message.backgroundTintList =
                            resources.getColorStateList(R.color.gray_600, theme)
                    }
                }
            })
            activity_conversation_send_message.setOnClickListener(this)
            title = data!!.title
            activity_conversation_title_message.text = data!!.title
            activity_conversation_user_name.text =
                data!!.users.joinToString(",", "", " ") { it.name }
            Glide.with(activity_conversation_avatar).asBitmap().load(data!!.users.first().avatar)
                .fallback(R.drawable.ic_svg_user_2_white)
                .into(activity_conversation_avatar)
            fetch(data!!.getRealConversationId().toString(), 1)
        } else {
            Toast.makeText(this, "Error loading", Toast.LENGTH_SHORT).show()
        }
    }

    fun next() {
        if (data != null && meta != null) {
            fetch(data!!.conversation_id, meta!!.current + 1)
        }
    }

    fun fetch(conversation_id: String, page: Int) {
        presenter.load(conversation_id, page)
    }

    override fun onMessagesReceived(
        conversation: LeakThisConversation.LeakThisMessageContainer,
        meta: LeakThisPagination?
    ) {
        adapter?.add(conversation.messages)
        this.meta = meta
    }

    override fun onMessagesSent(data: LeakThisConversation.LeakThisMessage) {
        activity_conversation_edit_text.text?.clear()
        Toast.makeText(this, data.message, Toast.LENGTH_SHORT).show()
        adapter?.add(data)
    }

    override fun onSendFailed(string: String?) {
        Toast.makeText(this, string ?: "nothing", Toast.LENGTH_SHORT).show()
        Log.e("RESPONSE", "e ${string ?: "empty"}")
    }

    override fun onError(printStackTrace: Throwable) {
        printStackTrace.printStackTrace()
    }

    override fun onError(e: Exception) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }

    companion object {

        const val KEY = "CONVERSATION"

        fun start(context: Context, conversation: LeakThisConversation) {
            val options = Bundle()
            val intent = Intent(context, ConversationActivity::class.java).apply {
                putExtra(KEY, conversation)
            }
            context.startActivity(intent, options)
        }
    }

    override fun onClick(v: View?) {
        Log.e("SENDING", activity_conversation_edit_text.text.toString())
        presenter.send(
            data!!.getRealConversationId().toString(),
            activity_conversation_edit_text.text.toString()
        )
    }
}