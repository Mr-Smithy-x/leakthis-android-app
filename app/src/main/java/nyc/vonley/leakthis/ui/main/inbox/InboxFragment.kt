package nyc.vonley.leakthis.ui.main.inbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_inbox.*
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.models.LeakThisConversation
import nyc.vonley.leakthis.models.http.LeakThisPagination
import nyc.vonley.leakthis.ui.main.inbox.adapter.ConversationListRecyclerAdapter
import javax.inject.Inject


@AndroidEntryPoint
class InboxFragment @Inject constructor(): Fragment(), InboxContract.View {

    private lateinit var inboxViewModel: InboxViewModel

    @Inject
    lateinit var presenter: InboxContract.Presenter
    var page = 1

    lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        inboxViewModel = ViewModelProvider(this).get(InboxViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_inbox, container, false)
        mRecyclerView = root?.findViewById(R.id.fragment_inbox_recycler_view)!!
        mRecyclerView.adapter = ConversationListRecyclerAdapter()
        inboxViewModel.conversations.observe(viewLifecycleOwner, Observer {
            (mRecyclerView.adapter as ConversationListRecyclerAdapter).setList(it)
        })
        presenter.getConversations(page)
        return root
    }

    override fun onConversations(
        conversations: ArrayList<LeakThisConversation>,
        pagination: LeakThisPagination?
    ) {
        (mRecyclerView.adapter as ConversationListRecyclerAdapter).setList(conversations)
    }

    override fun onError(e: Exception) {
        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
    }
}