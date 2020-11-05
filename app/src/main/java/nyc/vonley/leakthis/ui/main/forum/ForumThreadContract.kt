package nyc.vonley.leakthis.ui.main.forum

import nyc.vonley.leakthis.base.BaseContract
import nyc.vonley.leakthis.models.LeakThisForum
import nyc.vonley.leakthis.models.http.LeakThisPagination
import java.util.ArrayList

interface ForumThreadContract {

    interface View: BaseContract.View {
        fun onThreadResponse(it: ArrayList<LeakThisForum.Thread>, meta: LeakThisPagination?)
    }

    interface Presenter: BaseContract.Presenter{
        fun fetch(thread_id: String, page: Int = 1)
    }
}