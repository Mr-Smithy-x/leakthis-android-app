package nyc.vonley.leakthis.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import nyc.vonley.leakthis.ui.main.dashboard.DashboardContract
import nyc.vonley.leakthis.ui.main.dashboard.DashboardFragment
import nyc.vonley.leakthis.ui.main.dashboard.DashboardPresenter
import nyc.vonley.leakthis.ui.main.inbox.InboxContract
import nyc.vonley.leakthis.ui.main.inbox.InboxFragment
import nyc.vonley.leakthis.ui.main.inbox.InboxPresenter
import nyc.vonley.leakthis.ui.main.profile.ProfileContract
import nyc.vonley.leakthis.ui.main.profile.ProfileFragment
import nyc.vonley.leakthis.ui.main.profile.ProfilePresenter

@Module
@InstallIn(FragmentComponent::class)
abstract class FragmentPresenterModule {

    @FragmentScoped
    @Binds
    abstract fun bindDashboardFragment(impl: DashboardFragment): DashboardContract.View

    @FragmentScoped
    @Binds
    abstract fun bindDashboardPresenter(impl: DashboardPresenter): DashboardContract.Presenter


    @FragmentScoped
    @Binds
    abstract fun bindProfileFragment(impl: ProfileFragment): ProfileContract.View

    @FragmentScoped
    @Binds
    abstract fun bindProfilePresenter(impl: ProfilePresenter): ProfileContract.Presenter


    @FragmentScoped
    @Binds
    abstract fun bindInboxFragment(impl: InboxFragment): InboxContract.View

    @FragmentScoped
    @Binds
    abstract fun bindInboxPresenter(impl: InboxPresenter): InboxContract.Presenter



}