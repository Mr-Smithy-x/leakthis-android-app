package nyc.vonley.leakthis.util

import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import nyc.vonley.leakthis.models.LeakThisProfile
import nyc.vonley.leakthis.models.User
import nyc.vonley.leakthis.models.auth.LeakCookie
import nyc.vonley.leakthis.models.auth.OAuth2

class SharedPreferenceManagerImpl(private val sharedPreferences: SharedPreferences) :
    SharedPreferenceManager {


    override fun setId(user_id: Int) {
        val edit = sharedPreferences.edit()
        edit.putInt(USER_ID, user_id)
        edit.apply()
        Log.e(tag(), "LOGGED USER ID: ($user_id)")
    }

    override fun getId(): Int {
        return sharedPreferences.getInt(USER_ID, 0)
    }

    override fun isLoggedIn(): Boolean {
        return getUser() != null && getToken() != null
    }


    override fun getUser(): User? {
        val userJson = sharedPreferences.getString(USER, null)
        return if (!userJson.isNullOrEmpty()) Gson().fromJson(userJson, User::class.java) else null
    }

    override fun setUser(user: User?) {
        if (user == null) return
        try {
            val edit = sharedPreferences.edit()
            val userJson = Gson().toJson(user)
            edit.putString(USER, userJson)
            edit.apply()
            Log.e(tag(), "LOGGED USER JSON: ($userJson)")
        } catch (e: Exception) {
            Log.e("Authentication", "Error", e)
        }
    }

    override fun setToken(token: OAuth2?) {
        if (token == null) return
        try {
            val edit = sharedPreferences.edit()
            val tokenJson = Gson().toJson(token)
            edit.putString(OAUTH2_TOKEN, tokenJson)
            edit.apply()
            Log.e(
                tag(),
                "SAVED USER USER TOKEN: (${token.token_type}) - (${
                    token.access_token.substring(
                        0,
                        16
                    )
                }"
            )
        } catch (e: Exception) {
            Log.e(tag(), "Error2", e)
        }
    }

    override fun getToken(): OAuth2? {
        val tokenJson = sharedPreferences.getString(OAUTH2_TOKEN, null)
        return if (!tokenJson.isNullOrEmpty()) Gson().fromJson(
            tokenJson,
            OAuth2::class.java
        ) else null
    }

    override fun logout() {
        val edit = sharedPreferences.edit()
        edit.clear()
        edit.apply()
    }

    override fun setUUID(uuid: String, force: Boolean) {
        val hasUUID = hasUUID()
        if (force || !hasUUID) {
            val edit = sharedPreferences.edit()
            edit.putString(UUID, uuid)
            edit.apply()
        }
    }

    override fun hasUUID(): Boolean {
        return sharedPreferences.contains(UUID)
    }

    override fun setProfile(profile: LeakThisProfile?) {
        if (profile == null) return;
        val edit = sharedPreferences.edit()
        edit.putString(PROFILE, Gson().toJson(profile))
        edit.putInt(PROFILE_ID, profile.id!!)
        edit.apply()
    }

    override fun getProfile(): LeakThisProfile? {
        val profile = sharedPreferences.getString(PROFILE, null)
        return if (!profile.isNullOrEmpty()) Gson().fromJson(
            profile,
            LeakThisProfile::class.java
        ) else null
    }

    override fun hasProfile(): Boolean {
        return sharedPreferences.contains(PROFILE);
    }

    override fun getProfileId(): Int {
        return sharedPreferences.getInt(PROFILE_ID, -1)
    }

    private fun getCookie(): Array<LeakCookie> {
        if(this.hasCookies()) {
            val encoded_cstr = sharedPreferences.getString(COOKIE_KEY_ENCRYPTED, null)
            if (encoded_cstr != null) {
                val decoded_cstr = String(Base64.decode(encoded_cstr, Base64.DEFAULT))
                return Gson().fromJson(decoded_cstr, Array<LeakCookie>::class.java)
            }
        }
        return arrayOf()
    }

    override fun getCookieString(): String {
        return getCookie().joinToString("; ") { "${it.Name}=${it.Value}" }
    }

    override fun setCookieEncoded(cstr: String) {
        val edit = sharedPreferences.edit()
        edit.putString(COOKIE_KEY_ENCRYPTED, cstr)
        edit.apply()
    }

    override fun hasCookies(): Boolean {
        return sharedPreferences.contains(COOKIE_KEY_ENCRYPTED)
    }

    override fun getUUID(): String? {
        return sharedPreferences.getString(UUID, null)
    }

    companion object {
        fun tag(): String {
            return this::class.toString()
        }

        private const val UUID: String = "nyc.vonley.leakthis.UUID"
        private const val USER: String = "nyc.vonley.leakthis.USER"
        private const val PROFILE: String = "nyc.vonley.leakthis.PROILE"
        private const val PROFILE_ID: String = "nyc.vonley.leakthis.PROILE.ID"
        private const val USER_ID: String = "nyc.vonley.leathis.USER.ID"
        private const val OAUTH2_TOKEN: String = "nyc.vonley.leakthis.OAUTH2.TOKEN"
        private const val COOKIE_KEY_ENCRYPTED: String = "nyc.vonley.leakthis.COOKIE.TOKEN"

    }
}