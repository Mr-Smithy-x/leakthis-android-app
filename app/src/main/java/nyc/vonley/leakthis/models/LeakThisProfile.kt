package nyc.vonley.leakthis.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class LeakThisProfile(
    val id: Int?,
    val name: String?,
    val user_title: String?,
    val avatar: String?,
    val dob_month: String?,
    val dob_day: String?,
    val dob_year: String?,
    val about_html: String?,
    val option: OptionField,
    val custom_fields: ProfileSocialField?,
    val profile: ProfileField?,
) : Parcelable {


    @Entity
    @Parcelize
    data class ProfileField(
        val location: String?,
        val website: String?
    ) : Parcelable {

    }

    @Entity
    @Parcelize
    data class ProfileSocialField(
        val Instagram: String?,
        val Reddit: String?,
        val Discord: String?,
        val LastFM: String?,
        val skype: String?,
        val twitter: String?
    ) : Parcelable {

    }

    @Entity
    @Parcelize
    data class OptionField(
        val show_dob_date: String?,
        val receive_admin_email: String?
    ) : Parcelable {

    }

}