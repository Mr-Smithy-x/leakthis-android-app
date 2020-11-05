package nyc.vonley.leakthis.models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class LeakThisConversation(
    val conversation_id: String,
    val users: ArrayList<LeakThisUser.Lite>,
    val link: String,
    val title: String,
    val started_at: Int?,
    val last_message_at: Int?,
    val last_message_by: String?,
    val replied: String?,
    val participants: String?
) : Parcelable {


    fun getRealConversationId(): Int{
        return conversation_id.substring(conversation_id.indexOf(".")+1, conversation_id.length).toInt()
    }

    @Entity
    @Parcelize
    data class LeakThisMessageContainer(
        val title: String,
        val created: Long?,
        val created_formatted: String?,
        val messages: ArrayList<LeakThisMessage>
    ) : Parcelable

    @Entity
    @Parcelize
    data class LeakThisMessage(
        val id: String,
        val user: LeakThisUser.Lite,
        val message: String,
        val message_html: String,
        val created: Long,
        val created_formatted: String?
    ) : Parcelable
}