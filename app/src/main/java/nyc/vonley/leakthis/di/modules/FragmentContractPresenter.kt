package nyc.vonley.leakthis.di.modules

import android.app.Activity
import nyc.vonley.leakthis.ui.main.inbox.InboxFragment

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import nyc.vonley.leakthis.ui.main.MainActivity
import nyc.vonley.leakthis.ui.main.dashboard.DashboardFragment
import nyc.vonley.leakthis.ui.main.profile.ProfileFragment

@Module
@InstallIn(FragmentComponent::class)
object FragmentContractPresenter {
    @Provides
    fun provideDashboardFragment(fragment: Fragment): DashboardFragment {
        return fragment as DashboardFragment
    }


    @Provides
    fun provideInboxFragment(fragment: Fragment): InboxFragment {
        return fragment as InboxFragment
    }

    @Provides
    fun provideProfileFragment(fragment: Fragment): ProfileFragment {
        return fragment as ProfileFragment
    }
}