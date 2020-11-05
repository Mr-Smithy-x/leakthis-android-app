package nyc.vonley.leakthis.di.services

import nyc.vonley.leakthis.BuildConfig.*
import nyc.vonley.leakthis.models.LeakThisConversation
import nyc.vonley.leakthis.models.http.LeakThisResponse
import retrofit2.Response
import retrofit2.http.*

interface ConversationService {

    @GET(ROUTE_CONVERSATIONS)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getConversations(@Query("page") page: Int = 1):
            LeakThisResponse<ArrayList<LeakThisConversation>, Any>

    @GET(ROUTE_CONVERSATIONS_ID)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getMessages(
        @Path("conversation_id") conversation_id: String,
        @Query("page") page: Int = 1
    ): LeakThisResponse<LeakThisConversation.LeakThisMessageContainer, Any>

    @FormUrlEncoded
    @POST(ROUTE_CONVERSATIONS_ID_REPLY)
    @Headers("Accept: application/json")
    suspend fun reply(
        @Path("conversation_id") conversation_id: String,
        @Field("comment", encoded = true) comment: String
    ): Response<LeakThisResponse<LeakThisConversation.LeakThisMessage, Any>>


}