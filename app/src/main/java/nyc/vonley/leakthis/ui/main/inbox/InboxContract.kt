package nyc.vonley.leakthis.ui.main.inbox

import nyc.vonley.leakthis.base.BaseContract
import nyc.vonley.leakthis.models.LeakThisConversation
import nyc.vonley.leakthis.models.http.LeakThisPagination

interface InboxContract {
    interface View: BaseContract.View {
        fun onConversations(conversations: ArrayList<LeakThisConversation>,  pagination: LeakThisPagination?)
    }
    interface Presenter: BaseContract.Presenter {
        fun getConversations(page: Int = 1)
    }
}