package nyc.vonley.leakthis.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.di.annotations.SharedPreferenceStorage
import nyc.vonley.leakthis.di.services.AuthenticationService
import nyc.vonley.leakthis.models.LeakThisProfile
import nyc.vonley.leakthis.models.User
import nyc.vonley.leakthis.models.http.Authenticate
import nyc.vonley.leakthis.models.http.LeakThisResponse
import nyc.vonley.leakthis.models.http.request.CredentialRequest
import nyc.vonley.leakthis.ui.main.MainActivity
import nyc.vonley.leakthis.util.SharedPreferenceManager
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener, LoginContract.View {

    @Inject
    lateinit var presenter: LoginContract.Presenter

    @Inject
    @SharedPreferenceStorage
    lateinit var manager: SharedPreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_button_view.setOnClickListener(this)
        login_as_guest_view.setOnClickListener(this)
        if(manager.isLoggedIn()){
            manager.getUser()?.let { onSignedIn(it) }
        }
    }

    private fun onSignedIn(user: User) {
        Toast.makeText(this@LoginActivity, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }

    private fun onSignedIn(user: User, profile: LeakThisProfile){
        Toast.makeText(this@LoginActivity, "Welcome ${profile.name}!", Toast.LENGTH_SHORT).show()
        manager.setProfile(profile)
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }

    private fun guest(){
        presenter.guest()
    }

    private fun login(email: String, password: String) {
        presenter.login(email, password)
    }

    override fun onClick(v: View?) {
        when(v){
            login_button_view -> login(login_email_view.text.toString(), login_password_view.text.toString())
            login_apple_view -> login(login_email_view.text.toString(), login_password_view.text.toString())
            login_insta_view -> login(login_email_view.text.toString(), login_password_view.text.toString())
            login_tumblr_view -> login(login_email_view.text.toString(), login_password_view.text.toString())
            login_as_guest_view -> guest()
        }
    }


    override fun onGuestResponse(response: LeakThisResponse<Authenticate, String>) {
        if(response.data?.user != null){
            if(response.data.profile != null) {
                onSignedIn(response.data.user, response.data.profile)
            }else{
                onSignedIn(response.data.user)
            }
        }
    }

    override fun onLoginResponse(response: LeakThisResponse<Authenticate, Any>) {
        response.data?.user?.let { onSignedIn(it) }
    }

    override fun onError(e: Exception) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }

}