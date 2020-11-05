package nyc.vonley.leakthis.base

import kotlinx.coroutines.CoroutineScope

interface BaseContract {
    interface View {
        fun onError(e: Exception)
    }

    interface Presenter: CoroutineScope {
        fun cleanup()
    }
}