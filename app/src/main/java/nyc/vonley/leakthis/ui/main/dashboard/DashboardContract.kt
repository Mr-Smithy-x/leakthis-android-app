package nyc.vonley.leakthis.ui.main.dashboard

import nyc.vonley.leakthis.base.BaseContract
import nyc.vonley.leakthis.models.LeakThisForum

interface DashboardContract {

    interface View : BaseContract.View {
        fun onResponse(response: ArrayList<LeakThisForum>)
        fun onShowSubForums(subForums: LeakThisForum.ForumThread)
    }

    interface Presenter : BaseContract.Presenter {
        fun fetch()
    }
}