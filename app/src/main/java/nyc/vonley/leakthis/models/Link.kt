package nyc.vonley.leakthis.models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class Link(
    val title: String,
    val link: String,
) : Parcelable