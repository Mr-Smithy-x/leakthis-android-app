package nyc.vonley.leakthis.ui.main

import nyc.vonley.leakthis.base.BaseContract
import nyc.vonley.leakthis.models.LeakThisProfile

interface MainContract {

    interface View: BaseContract.View {
        fun getProfileInstance(): LeakThisProfile
    }

    interface Presenter: BaseContract.Presenter{

    }
}