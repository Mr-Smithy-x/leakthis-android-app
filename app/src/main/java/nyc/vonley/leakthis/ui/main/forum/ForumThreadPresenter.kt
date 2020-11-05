package nyc.vonley.leakthis.ui.main.forum

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nyc.vonley.leakthis.base.BasePresenter
import nyc.vonley.leakthis.di.services.ForumService
import javax.inject.Inject


class ForumThreadPresenter @Inject constructor(
    val service: ForumService,
    val view: ForumThreadContract.View
): BasePresenter(), ForumThreadContract.Presenter {

    override fun fetch(thread_id: String, page: Int) {
        launch {
            try {
                val response = service.getForumThread(thread_id, page)
                withContext(Dispatchers.Main) {
                    response.data?.let {
                        view.onThreadResponse(it, response.meta)
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