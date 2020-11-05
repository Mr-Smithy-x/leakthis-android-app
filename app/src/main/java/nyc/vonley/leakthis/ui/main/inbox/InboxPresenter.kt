package nyc.vonley.leakthis.ui.main.inbox

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nyc.vonley.leakthis.base.BasePresenter
import nyc.vonley.leakthis.di.services.ConversationService
import javax.inject.Inject

class InboxPresenter @Inject constructor(
    val service: ConversationService,
    val view: InboxContract.View
) : BasePresenter(), InboxContract.Presenter {
    override fun getConversations(page: Int) {
        launch {
            try {
                val response = service.getConversations(page)
                withContext(Dispatchers.Main) {
                    response.data?.let {
                        view.onConversations(it, response.meta)
                    }
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main) {
                    view.onError(e)
                }
            }
        }
    }
}