package nyc.vonley.leakthis.di.network

import nyc.vonley.leakthis.util.SharedPreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class AuthInterceptor constructor(val manager: SharedPreferenceManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            manager.setUUID(UUID.randomUUID().toString())
            val uuid = manager.getUUID()
            val original = chain.request()
            val request = original.newBuilder()
            val token = manager.getToken()
            if(original.header("Accept") == null) {
                request.addHeader("Accept", "application/json")
            }
            if (uuid != null) {
                request.addHeader("X-UUID", uuid)
            }
            if (token != null) {
                request.addHeader("Authorization", String.format("Bearer %s", token.access_token))
            }
            request.url(original.url)
            request.method(original.method, original.body)
            chain.proceed(request.build())
        } catch (e: Exception) {
            chain.proceed(chain.request())
        }
    }

}
