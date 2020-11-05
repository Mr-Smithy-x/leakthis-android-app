package nyc.vonley.leakthis.di.modules

import android.os.Environment
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import nyc.vonley.leakthis.BuildConfig.*
import nyc.vonley.leakthis.di.annotations.*
import nyc.vonley.leakthis.di.network.AuthInterceptor
import nyc.vonley.leakthis.di.network.GuestInterceptor
import nyc.vonley.leakthis.di.network.OAuth2Authenticator
import nyc.vonley.leakthis.util.SharedPreferenceManager
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {


    @AuthInterceptorOkHttpClient
    @Provides
    fun provideAuthInterceptor(
        @SharedPreferenceStorage manager: SharedPreferenceManager
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .authenticator(OAuth2Authenticator(manager))
            .addInterceptor(AuthInterceptor(manager))
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .cache(Cache(Environment.getDownloadCacheDirectory(), (20 * 1024 * 1024).toLong()))
        if (LOG) {
            builder.addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.HEADERS
            }).addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return builder.build()
    }

    @GuestInterceptorOkHttpClient
    @Provides
    fun provideGuestInterceptor(
        @SharedPreferenceStorage manager: SharedPreferenceManager
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(GuestInterceptor(manager))
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .cache(Cache(Environment.getDownloadCacheDirectory(), (20 * 1024 * 1024).toLong()))
        if (LOG) {
            builder.addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.HEADERS
            }).addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return builder.build()
    }


    @Provides
    @AuthRetrofitClient
    fun provideAuthenticatedRetrofit(
        @AuthInterceptorOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create()
                )
            )
            .client(okHttpClient)
            .build()
    }


    @Provides
    @GuestRetrofitClient
    fun provideGuestService(
        @GuestInterceptorOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create()
                )
            )
            .client(okHttpClient)
            .build()
    }


}