package nyc.vonley.leakthis.di.services

import nyc.vonley.leakthis.BuildConfig.*
import nyc.vonley.leakthis.models.LeakThisForum
import nyc.vonley.leakthis.models.http.LeakThisPagination
import nyc.vonley.leakthis.models.http.LeakThisResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ForumService {

    @GET(ROUTE_LEAKTHIS_FORUMS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getForums(): LeakThisResponse<ArrayList<LeakThisForum>, Any>

    @GET(ROUTE_LEAKTHIS_FORUMS_ID)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getForumThread(@Path("forum_id") forum_id: String, @Query("page") page: Int = 1): LeakThisResponse<ArrayList<LeakThisForum.Thread>, LeakThisPagination>

}