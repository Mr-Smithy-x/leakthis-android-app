package nyc.vonley.leakthis.di.services

import nyc.vonley.leakthis.BuildConfig.*
import nyc.vonley.leakthis.models.LeakThisForum
import nyc.vonley.leakthis.models.http.LeakThisPagination
import nyc.vonley.leakthis.models.http.LeakThisResponse
import retrofit2.Response
import retrofit2.http.*

interface ThreadService {

    @GET(ROUTE_LEAKTHIS_THREADS_ID)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getPosts(@Path("thread_id") thread_id: Int, @Query("page") page: Int = 1): LeakThisResponse<ArrayList<LeakThisForum.ThreadPost>, LeakThisPagination>

    @FormUrlEncoded
    @POST(ROUTE_LEAKTHIS_THREADS_ID_REPLY)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun postComment(@Path("thread_id") thread_id: Int, @Field("comment") comment: String): Response<LeakThisResponse<ArrayList<LeakThisForum.ThreadPost>, Any>>

    @POST(ROUTE_LEAKTHIS_THREADS_ID_REPLY_POST_ID)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun postComment(@Path("thread_id") thread_id: Int, @Path("post_id") post_id: Int, @Field("comment") comment: String): Response<LeakThisResponse<ArrayList<LeakThisForum.ThreadPost>, Any>>


}