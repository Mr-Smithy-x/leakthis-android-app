package nyc.vonley.leakthis.di.services

import nyc.vonley.leakthis.BuildConfig.*
import nyc.vonley.leakthis.models.LeakThisUser
import nyc.vonley.leakthis.models.http.LeakThisResponse
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @POST(ROUTE_LEAKTHIS_USERS_ID)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun getUser(@Path("user_id") user_id: Int): LeakThisResponse<LeakThisUser, Any>
    

}