package nyc.vonley.leakthis

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.danlew.android.joda.JodaTimeAndroid

@HiltAndroidApp
class LeakThisApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this);

    }
}