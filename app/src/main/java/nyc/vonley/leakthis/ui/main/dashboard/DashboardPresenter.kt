package nyc.vonley.leakthis.ui.main.dashboard

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nyc.vonley.leakthis.base.BasePresenter
import nyc.vonley.leakthis.di.services.ForumService
import nyc.vonley.leakthis.di.services.UserService
import javax.inject.Inject

class DashboardPresenter @Inject constructor(
    val forum: ForumService,
    val user: UserService,
    val view: DashboardContract.View) : BasePresenter(), DashboardContract.Presenter {

    override fun fetch() {
        launch {
            try {
                val response = forum.getForums()
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

}