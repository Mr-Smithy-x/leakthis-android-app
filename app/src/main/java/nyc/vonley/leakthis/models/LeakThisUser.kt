package nyc.vonley.leakthis.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class LeakThisUser(
    @PrimaryKey val id: Int,
    val username: String,
    val avatar: String?,
    val about: String?,
    val about_html: String?,
    val signature: String?,
    val signature_html: String?,
    val birthday: String?,
    val website: String?,
    val location: String?,
    val socials: LeakThisSocial?
) : Parcelable {


    @Entity
    @Parcelize
    data class LeakThisSocial(
        val instagram: String?,
        val reddit: String?,
        val discord: String?,
        val last_fm: String?,
        val skype: String?,
        val twitter: String?
    ) : Parcelable

    @Entity
    @Parcelize
    data class Lite(
        @PrimaryKey val id: Int,
        val name: String,
        val avatar: String?
    ) : Parcelable

}
