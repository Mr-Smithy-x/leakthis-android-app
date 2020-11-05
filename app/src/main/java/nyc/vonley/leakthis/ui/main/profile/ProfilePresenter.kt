package nyc.vonley.leakthis.ui.main.profile

import nyc.vonley.leakthis.base.BasePresenter
import nyc.vonley.leakthis.di.services.UserService
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    val service: UserService,
    val view: ProfileContract.View,
): BasePresenter(), ProfileContract.Presenter {

}