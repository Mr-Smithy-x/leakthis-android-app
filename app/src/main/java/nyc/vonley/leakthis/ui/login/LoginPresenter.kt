package nyc.vonley.leakthis.ui.login

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nyc.vonley.leakthis.base.BasePresenter
import nyc.vonley.leakthis.di.services.AuthenticationService
import nyc.vonley.leakthis.models.http.request.CredentialRequest
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    val service: AuthenticationService,
    val view: LoginContract.View
): BasePresenter(), LoginContract.Presenter {


    override fun guest() {
        launch {
            try {
                val response = service.guest()
                withContext(Dispatchers.Main) {
                    view.onGuestResponse(response)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun login(email: String, password: String) {
        launch {
            try {
                val response = service.guest(CredentialRequest.login(email, password))
                withContext(Dispatchers.Main) {
                    view.onGuestResponse(response)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}