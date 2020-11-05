package nyc.vonley.leakthis.models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class LeakThisForum(
    val category: String,
    val description: String?,
    val threads: ArrayList<ForumThread>
) : Parcelable {


    @Parcelize
    enum class ForumType : Parcelable {
        FORUM,
        CATEGORY,
        UNKNOWN
    }

    @Entity
    @Parcelize
    data class ForumThread(
        val id: String,
        val title: String,
        val description: String?,
        val thread: String?,
        val latest: LatestThread,
        val type: ForumType,
        val bool_type: ForumBoolType,
        val sub_forums: ArrayList<SubForum>
    ) : Parcelable


    @Entity
    @Parcelize
    data class Thread(
        val id: Int,
        val user: LeakThisUser.Lite,
        val title: String,
        val topic: String,
        val thread: String,
        val link: String,
        val created: Int,
        val created_formatted: String
    ) : Parcelable


    @Entity
    @Parcelize
    data class ForumBoolType(
        val is_category: Boolean,
        val is_forum: Boolean
    ) : Parcelable

    @Entity
    @Parcelize
    data class LatestThread(
        val user: LeakThisUser.Lite,
        val link: String,
        val title: String,
        val thread_id: String,
        val thread_post: String,
        val created: Int,
        val created_formatted: String
    ) : Parcelable

    @Entity
    @Parcelize
    data class SubForum(
        val title: String,
        val link: String,
        val forum_id: String
    ) : Parcelable


    @Entity
    @Parcelize
    data class ThreadPost(
        val user: LeakThisUser.Lite,
        val thread_id: Int,
        val post_id: Int,
        val body: String,
        val body_html: String,
        val links: ArrayList<Link>,
        val created: Long,
        val created_formatted: String
    ) : Parcelable


    @Entity
    @Parcelize
    data class Song(
        val user: LeakThisUser.Lite,
        val thread: String,
        val artist: String,
        val song: String,
        val link: String,
        val links: ArrayList<Link>,
        val created: Int,
        val created_formatted: String
    ) : Parcelable
}

/*

@Entity
@Parcelize
data class ThreadSong(
    val user: User,
    val thread: String,
    val artist: String,
    val song: String,
    val link: String,
    val links: ArrayList<Link>,
    val created: Int,
    val created_formatted: String
) : Parcelable {

}

 */