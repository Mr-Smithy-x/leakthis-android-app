package nyc.vonley.leakthis.di.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nyc.vonley.leakthis.BuildConfig.*
import nyc.vonley.leakthis.di.annotations.SharedPreferenceStorage
import nyc.vonley.leakthis.models.auth.OAuth2
import nyc.vonley.leakthis.models.http.LeakThisResponse
import nyc.vonley.leakthis.models.http.request.CredentialRequest
import nyc.vonley.leakthis.util.SharedPreferenceManager
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.lang.reflect.Type


class OAuth2Authenticator constructor(@SharedPreferenceStorage val manager: SharedPreferenceManager) :
    Authenticator {

    private val JSON = "application/json; charset=utf-8".toMediaType()
    private val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }).build()

    @Throws(IOException::class)
    fun post(url: String, json: String?): String? {
        val body: RequestBody = json!!.toRequestBody(JSON)
        val request: Request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        client.newCall(request).execute().use { response -> return response.body!!.string() }
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        // We need to have a token in order to refresh it.
        var oAuth2 = manager.getToken()
        val token = oAuth2?.access_token ?: return null
        val refresh = oAuth2.refresh_token

        synchronized(this) {
            val newToken = oAuth2?.access_token

            // Check if the request made was previously made as an authenticated request.
            if (response.request.header("Authorization") != null) {

                // If the token has changed since the request was made, use the new token.
                if (newToken != token) {
                    return response.request
                        .newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer $newToken")
                        .build()
                }

                val responseString = post(
                    "${BASE_URL}${ROUTE_AUTH_REFRESH}",
                    Gson().toJson(CredentialRequest.refresh(refresh))
                )

                if (responseString != null) {
                    val oauth2: Type = object : TypeToken<LeakThisResponse<OAuth2, Any>>() {}.type
                    val leakthis = Gson().fromJson<LeakThisResponse<OAuth2, Any>>(responseString, oauth2)
                    oAuth2 = leakthis.data
                    manager.setToken(oAuth2)
                }

                // Retry the request with the new token.
                return response.request
                    .newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer ${oAuth2?.access_token}")
                    .build()
            }
        }
        return null
    }
}