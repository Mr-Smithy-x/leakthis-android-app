package nyc.vonley.leakthis.ui.main.thread

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nyc.vonley.leakthis.base.BasePresenter
import nyc.vonley.leakthis.di.services.ForumService
import nyc.vonley.leakthis.di.services.ThreadService
import javax.inject.Inject


class ThreadPresenter @Inject constructor(
    val service: ThreadService,
    val view: ThreadContract.View
): BasePresenter(), ThreadContract.Presenter {

    override fun fetch(thread_id: Int, page: Int) {
        launch {
            try {
                val response = service.getPosts(thread_id, page)
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

    override fun comment(id: Int, comment: String, post_id: Int) {
        launch {
            try {
                val response = if(post_id <= 0)
                    service.postComment(thread_id = id, comment = comment)
                else
                    service.postComment(thread_id = id, post_id = post_id, comment = comment)
                withContext(Dispatchers.Main) {
                    if(response.isSuccessful){
                        response.body()?.data?.let { view.onPostSubmitted(it) }
                    }else{
                        view.onCommentFailed(response.errorBody()?.string())
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