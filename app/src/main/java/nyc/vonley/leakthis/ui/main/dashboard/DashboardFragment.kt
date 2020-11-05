package nyc.vonley.leakthis.ui.main.dashboard

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
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.models.LeakThisForum
import nyc.vonley.leakthis.ui.main.dashboard.adapter.DashboardRecyclerAdapter
import nyc.vonley.leakthis.ui.main.dashboard.dialog.SubForumThreadDialogFragment
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment  @Inject constructor(): Fragment(), DashboardContract.View {

    private lateinit var homeViewModel: DashboardViewModel
    private lateinit var  mRecyclerView: RecyclerView

    @Inject
    lateinit var mPresenter: DashboardContract.Presenter

    lateinit var mAdapter: DashboardRecyclerAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        mRecyclerView = root.findViewById(R.id.fragment_dashboard_recycler_view)
        mAdapter = DashboardRecyclerAdapter(this)
        mRecyclerView.adapter = mAdapter
        homeViewModel.text.observe(viewLifecycleOwner, Observer {


        })
        mPresenter.fetch()
        return root
    }

    override fun onResponse(response: ArrayList<LeakThisForum>) {
        mAdapter.setForums(response)
    }

    override fun onShowSubForums(forums: LeakThisForum.ForumThread) {
        SubForumThreadDialogFragment.create(forums)
            .show(childFragmentManager, "dialog")
    }

    override fun onError(e: Exception) {
        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
    }
}