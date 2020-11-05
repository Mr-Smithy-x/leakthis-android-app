package nyc.vonley.leakthis.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import nyc.vonley.leakthis.BuildConfig.*
import nyc.vonley.leakthis.di.annotations.SharedPreferenceStorage
import nyc.vonley.leakthis.persistence.AppDatabase
import nyc.vonley.leakthis.persistence.UserDao
import nyc.vonley.leakthis.util.SharedPreferenceManager
import nyc.vonley.leakthis.util.SharedPreferenceManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object LocalStorageModule {


    @Provides
    @Singleton
    @SharedPreferenceStorage
    fun provideSharePreferenceManager(
        @ApplicationContext context: Context
    ): SharedPreferenceManager {
        val preferences = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
        return SharedPreferenceManagerImpl(preferences)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, DATABASE_FILE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

}