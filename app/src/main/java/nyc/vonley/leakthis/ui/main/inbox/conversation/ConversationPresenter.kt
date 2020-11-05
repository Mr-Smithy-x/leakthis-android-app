package nyc.vonley.leakthis.ui.main.inbox.conversation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nyc.vonley.leakthis.base.BasePresenter
import nyc.vonley.leakthis.di.services.ConversationService
import javax.inject.Inject

class ConversationPresenter @Inject constructor(
    val view: ConversationContract.View,
    val service: ConversationService
) : BasePresenter(), ConversationContract.Presenter {
    override fun load(conversation_id: String, page: Int) {
        launch {
            try {
                val response = service.getMessages(conversation_id, page)
                withContext(Dispatchers.Main) {
                    response.data?.let {
                        view.onMessagesReceived(it, response.meta)
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun send(conversation_id: String, comment: String) {
        launch {
            try {
                val response = service.reply(conversation_id = conversation_id, comment = comment)
                withContext(Dispatchers.Main) {
                    if(response.isSuccessful){
                        response.body()?.data?.let { view.onMessagesSent(it) }
                    }else{
                        view.onSendFailed(response.errorBody()?.string())
                    }
                }
            }catch (e: Exception){
                view.onError(e)
            }
        }
    }
}