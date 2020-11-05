package nyc.vonley.leakthis.ui.main.thread

import nyc.vonley.leakthis.base.BaseContract
import nyc.vonley.leakthis.models.LeakThisForum
import nyc.vonley.leakthis.models.http.LeakThisPagination
import java.util.ArrayList

interface ThreadContract {

    interface View: BaseContract.View {
        fun onThreadResponse(it: ArrayList<LeakThisForum.ThreadPost>, meta: LeakThisPagination?)
        fun onPostSubmitted(it: LeakThisForum.ThreadPost)
        fun onCommentFailed(comment: String?)
    }

    interface Presenter: BaseContract.Presenter{
        fun fetch(thread_id: Int, page: Int = 1)
        fun comment(id: Int, comment: String, post_id: Int = 0)
    }
}