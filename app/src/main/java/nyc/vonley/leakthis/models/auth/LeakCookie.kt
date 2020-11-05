package nyc.vonley.leakthis.models.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeakCookie(
    val Name: String,
    val Value: String,
    val Domain: String,
    val Path: String,
    @SerializedName("Max-Age")
    val MaxAge: String?,
    val Expires: Long?,
    val Secure: Boolean,
    val Discard: Boolean,
    val HttpOnly: Boolean,
    val SameSite: String?
): Parcelable