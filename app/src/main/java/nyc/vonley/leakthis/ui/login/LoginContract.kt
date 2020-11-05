package nyc.vonley.leakthis.ui.login

import nyc.vonley.leakthis.base.BaseContract
import nyc.vonley.leakthis.models.LeakThisProfile
import nyc.vonley.leakthis.models.http.Authenticate
import nyc.vonley.leakthis.models.http.LeakThisResponse

interface LoginContract {
    interface View: BaseContract.View {
        fun onGuestResponse(response: LeakThisResponse<Authenticate, String>)
        fun onLoginResponse(response: LeakThisResponse<Authenticate, Any>)
    }
    interface Presenter: BaseContract.Presenter {

        fun guest()
        fun login(email: String, password: String)
    }
}