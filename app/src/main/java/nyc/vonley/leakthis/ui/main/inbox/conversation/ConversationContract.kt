package nyc.vonley.leakthis.ui.main.inbox.conversation

import nyc.vonley.leakthis.base.BaseContract
import nyc.vonley.leakthis.models.LeakThisConversation
import nyc.vonley.leakthis.models.http.LeakThisPagination

interface ConversationContract {
    interface View : BaseContract.View {
        fun onMessagesReceived(conversation: LeakThisConversation.LeakThisMessageContainer, meta: LeakThisPagination?)
        fun onMessagesSent(data: LeakThisConversation.LeakThisMessage)
        fun onSendFailed(string: String?)
        fun onError(printStackTrace: Throwable)
    }

    interface Presenter : BaseContract.Presenter {
        fun load(conversation_id: String, page: Int = 1)
        fun send(conversation_id: String, comment: String);
    }
}