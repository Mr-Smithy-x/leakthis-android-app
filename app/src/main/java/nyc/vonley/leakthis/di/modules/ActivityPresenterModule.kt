package nyc.vonley.leakthis.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import nyc.vonley.leakthis.ui.login.LoginActivity
import nyc.vonley.leakthis.ui.login.LoginContract
import nyc.vonley.leakthis.ui.login.LoginPresenter
import nyc.vonley.leakthis.ui.main.MainActivity
import nyc.vonley.leakthis.ui.main.MainContract
import nyc.vonley.leakthis.ui.main.MainPresenter
import nyc.vonley.leakthis.ui.main.inbox.conversation.ConversationActivity
import nyc.vonley.leakthis.ui.main.inbox.conversation.ConversationContract
import nyc.vonley.leakthis.ui.main.inbox.conversation.ConversationPresenter
import nyc.vonley.leakthis.ui.main.forum.ForumThreadActivity
import nyc.vonley.leakthis.ui.main.forum.ForumThreadContract
import nyc.vonley.leakthis.ui.main.forum.ForumThreadPresenter
import nyc.vonley.leakthis.ui.main.thread.ThreadActivity
import nyc.vonley.leakthis.ui.main.thread.ThreadContract
import nyc.vonley.leakthis.ui.main.thread.ThreadPresenter

@Module
@InstallIn(ActivityComponent::class)
abstract class ActivityPresenterModule {

    @ActivityScoped
    @Binds
    abstract fun bindMainActivity(impl: MainActivity): MainContract.View

    @ActivityScoped
    @Binds
    abstract fun bindMainPresenter(impl: MainPresenter): MainContract.Presenter

    @ActivityScoped
    @Binds
    abstract fun bindLoginActivity(impl: LoginActivity): LoginContract.View

    @ActivityScoped
    @Binds
    abstract fun bindLoginPresenter(impl: LoginPresenter): LoginContract.Presenter

    @ActivityScoped
    @Binds
    abstract fun bindConversationActivity(impl: ConversationActivity): ConversationContract.View

    @ActivityScoped
    @Binds
    abstract fun bindConversationPresenter(impl: ConversationPresenter): ConversationContract.Presenter

    @ActivityScoped
    @Binds
    abstract fun bindForumThreadActivity(impl: ForumThreadActivity): ForumThreadContract.View

    @ActivityScoped
    @Binds
    abstract fun bindForumThreadPresenter(impl: ForumThreadPresenter): ForumThreadContract.Presenter

  @ActivityScoped
    @Binds
    abstract fun bindThreadActivity(impl: ThreadActivity): ThreadContract.View

    @ActivityScoped
    @Binds
    abstract fun bindThreadPresenter(impl: ThreadPresenter): ThreadContract.Presenter



}