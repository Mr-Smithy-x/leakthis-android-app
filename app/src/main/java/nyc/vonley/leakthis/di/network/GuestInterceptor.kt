package nyc.vonley.leakthis.di.network

import android.util.Log
import com.google.gson.Gson
import nyc.vonley.leakthis.BuildConfig.*
import nyc.vonley.leakthis.models.LeakThisProfile
import nyc.vonley.leakthis.models.User
import nyc.vonley.leakthis.models.auth.OAuth2
import nyc.vonley.leakthis.util.SharedPreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import java.util.*

class GuestInterceptor constructor(val manager: SharedPreferenceManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        manager.setUUID(UUID.randomUUID().toString())
        val uuid = manager.getUUID()
        val original = chain.request()
        val request = original.newBuilder()
        if (uuid != null) {
            request.addHeader("X-UUID", uuid)
        }
        request.url(original.url)
        request.method(original.method, original.body)
        val response = chain.proceed(request.build())
        if(LOG) {
            Log.e("GUEST INTERCEPTOR", original.url.toString())
        }
        var data: String? = ""
        response.body?.let {
            if (chain.request().url.encodedPath.startsWith("/api/v1/auth")) {
                if (response.isSuccessful) {
                    data = it.string()
                    val json = JSONObject(data)
                    if (json.has("extra")){
                        val extra = json.getString("extra")
                        val cstr = extra.toString();
                        manager.setCookieEncoded(cstr)
                    }
                    if (json.has("data")) {
                        if (chain.request().url.encodedPathSegments.contains("refresh")) {
                            val oauth = json.getJSONObject("data")
                            val oAuth2 = Gson().fromJson(oauth.toString(), OAuth2::class.java)
                            manager.setToken(oAuth2)
                        } else {
                            val dataField = json.getJSONObject("data")

                            if(dataField.has("user")) {
                                val userObject = dataField.getJSONObject("user")
                                val user = Gson().fromJson(userObject.toString(), User::class.java)
                                manager.setUser(user)
                            }

                            if(dataField.has("oauth")){
                                val oauthObject = dataField.getJSONObject("oauth")
                                val oAuth2 = Gson().fromJson(oauthObject.toString(), OAuth2::class.java)
                                manager.setToken(oAuth2)
                            }

                            if(dataField.has("profile")) {
                                val profileObject = dataField.getJSONObject("profile")
                                val profile = Gson().fromJson(profileObject.toString(), LeakThisProfile::class.java)
                                manager.setProfile(profile)
                            }
                        }
                    }
                }
            }
        }
        return if (data != null) response.newBuilder()
            .body(data?.toResponseBody(response.body?.contentType())).build() else response
    }

}
