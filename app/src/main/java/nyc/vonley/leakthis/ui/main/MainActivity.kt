package nyc.vonley.leakthis.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.di.annotations.SharedPreferenceStorage
import nyc.vonley.leakthis.models.LeakThisProfile
import nyc.vonley.leakthis.ui.main.notification.NotificationPopupWindow
import nyc.vonley.leakthis.util.SharedPreferenceManager
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContract.View, View.OnClickListener {

    @Inject
    lateinit var presenter: MainContract.Presenter

    @Inject
    @SharedPreferenceStorage
    lateinit var preferences: SharedPreferenceManager

    var profile: LeakThisProfile? = null

    var mNotificationWindow: NotificationPopupWindow? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        if (preferences.hasProfile()) {
            preferences.getProfile()?.let { initialize(it) }
        } else {
            Toast.makeText(this, "PROFILE NOT LOADED", Toast.LENGTH_LONG).show()
        }
        mNotificationWindow = NotificationPopupWindow.create(activity_main_notifications)
        activity_main_notifications.setOnClickListener(this)
        activity_main_avatar.setOnClickListener(this)
    }

    private fun initialize(profile: LeakThisProfile) {
        this.profile = profile
        this.activity_main_username.text = profile.name
        this.activity_main_user_title.text = profile.id.toString()
        Glide.with(this).asBitmap()
            .load(profile.avatar)
            .fallback(R.drawable.ic_svg_user_2_white)
            .into(activity_main_avatar)
        Toast.makeText(this, "LOADING ${profile.name}", Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            activity_main_avatar.id -> {
            }
            activity_main_notifications.id -> {
                mNotificationWindow?.showPopup()
            }
        }
    }


    override fun getProfileInstance(): LeakThisProfile {
        return profile!!
    }

    override fun onError(e: Exception) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }
}