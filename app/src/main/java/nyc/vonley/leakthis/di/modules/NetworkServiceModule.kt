package nyc.vonley.leakthis.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import nyc.vonley.leakthis.di.annotations.AuthRetrofitClient
import nyc.vonley.leakthis.di.annotations.GuestRetrofitClient
import nyc.vonley.leakthis.di.services.*
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object NetworkServiceModule {

    @Provides
    fun provideUserService(
        @AuthRetrofitClient retrofit: Retrofit
    ): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun provideConversationService(
        @AuthRetrofitClient retrofit: Retrofit
    ): ConversationService {
        return retrofit.create(ConversationService::class.java)
    }

    @Provides
    fun provideThreadService(
        @AuthRetrofitClient retrofit: Retrofit
    ): ThreadService {
        return retrofit.create(ThreadService::class.java)
    }

    @Provides
    fun provideForumService(
        @AuthRetrofitClient retrofit: Retrofit
    ): ForumService {
        return retrofit.create(ForumService::class.java)
    }

    @Provides
    fun provideAuthenticationService(
        @GuestRetrofitClient retrofit: Retrofit
    ): AuthenticationService {
        return retrofit.create(AuthenticationService::class.java)
    }

}