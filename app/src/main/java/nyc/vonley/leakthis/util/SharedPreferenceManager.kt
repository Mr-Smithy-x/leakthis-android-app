package nyc.vonley.leakthis.util

import nyc.vonley.leakthis.models.LeakThisProfile
import nyc.vonley.leakthis.models.User
import nyc.vonley.leakthis.models.auth.LeakCookie
import nyc.vonley.leakthis.models.auth.OAuth2
import okhttp3.Cookie


/**
 * Created by Charlton on 1/2/18.
 */

interface SharedPreferenceManager {

    fun setUser(user: User?)
    fun getUser(): User?
    fun setId(user_id: Int)
    fun getId(): Int
    fun setToken(token: OAuth2?)
    fun getToken(): OAuth2?
    fun isLoggedIn(): Boolean
    fun logout(): Unit
    fun setUUID(uuid: String, force: Boolean = false)
    fun getUUID(): String?
    fun hasUUID(): Boolean
    fun setProfile(profile: LeakThisProfile?)
    fun getProfile(): LeakThisProfile?
    fun hasProfile(): Boolean
    fun getProfileId(): Int
    fun getCookieString(): String
    fun setCookieEncoded(cstr: String)
    fun hasCookies(): Boolean
}
