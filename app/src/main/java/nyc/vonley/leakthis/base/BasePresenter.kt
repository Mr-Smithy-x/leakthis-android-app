package nyc.vonley.leakthis.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import nyc.vonley.leakthis.base.BaseContract
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter : BaseContract.Presenter {

    protected val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    override fun cleanup() {
        job.cancel()
    }
}