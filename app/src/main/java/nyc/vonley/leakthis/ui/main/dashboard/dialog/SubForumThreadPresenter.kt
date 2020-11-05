package nyc.vonley.leakthis.ui.main.dashboard.dialog

import nyc.vonley.leakthis.base.BasePresenter
import nyc.vonley.leakthis.di.services.ForumService
import javax.inject.Inject

class SubForumThreadPresenter @Inject constructor(
    val forum: ForumService,
    val view: SubForumThreadContract.View) : BasePresenter(), SubForumThreadContract.Presenter {
/*
    override fun fetch() {
        launch {
            try {
                val response = forum.getForumThread()
                withContext(Dispatchers.Main) {
                    response.data?.let {
                        view.onResponse(response = it)
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

 */

}