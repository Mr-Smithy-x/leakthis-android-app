package nyc.vonley.leakthis.ui.main

import nyc.vonley.leakthis.base.BasePresenter
import javax.inject.Inject


class MainPresenter @Inject constructor(val view: MainContract.View): BasePresenter(), MainContract.Presenter {

}