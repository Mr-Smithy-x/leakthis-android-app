package nyc.vonley.leakthis.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import nyc.vonley.leakthis.di.repository.UserRepository
import nyc.vonley.leakthis.di.services.UserService
import nyc.vonley.leakthis.persistence.UserDao


@Module
@InstallIn(value = [ActivityRetainedComponent::class])
object RepositoryModule {
    @Provides
    @ActivityRetainedScoped
    fun provideUserRepository(
        service: UserService,
        userDao: UserDao
    ): UserRepository {
        return UserRepository(service, userDao)
    }

}