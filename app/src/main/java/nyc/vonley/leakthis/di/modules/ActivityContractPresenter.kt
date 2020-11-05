package nyc.vonley.leakthis.di.modules

import android.app.Activity

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import nyc.vonley.leakthis.ui.login.LoginActivity
import nyc.vonley.leakthis.ui.main.MainActivity
import nyc.vonley.leakthis.ui.main.inbox.conversation.ConversationActivity
import nyc.vonley.leakthis.ui.main.forum.ForumThreadActivity
import nyc.vonley.leakthis.ui.main.thread.ThreadActivity

@Module
@InstallIn(ActivityComponent::class)
object ActivityContractPresenter {

    @Provides
    fun provideMainActivity(activity: Activity): MainActivity {
        return activity as MainActivity
    }

    @Provides
    fun provideLoginActivity(activity: Activity): LoginActivity {
        return activity as LoginActivity
    }

    @Provides
    fun provideConversationActivity(activity: Activity): ConversationActivity {
        return activity as ConversationActivity
    }

    @Provides
    fun provideForumThreadActivity(activity: Activity): ForumThreadActivity {
        return activity as ForumThreadActivity
    }

    @Provides
    fun provideThreadActivity(activity: Activity): ThreadActivity {
        return activity as ThreadActivity
    }

}