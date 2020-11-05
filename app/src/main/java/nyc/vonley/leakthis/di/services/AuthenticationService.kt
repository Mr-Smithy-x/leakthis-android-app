package nyc.vonley.leakthis.di.services

import nyc.vonley.leakthis.BuildConfig.*
import nyc.vonley.leakthis.models.auth.OAuth2
import nyc.vonley.leakthis.models.http.Authenticate
import nyc.vonley.leakthis.models.http.LeakThisAuth
import nyc.vonley.leakthis.models.http.LeakThisResponse
import nyc.vonley.leakthis.models.http.request.CredentialRequest
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticationService {

    @FormUrlEncoded
    @POST(ROUTE_AUTH_LOGIN)
    suspend fun login(@FieldMap(encoded = true) credential: CredentialRequest): LeakThisResponse<Authenticate, Any>

    @POST(ROUTE_AUTH_GUEST)
    suspend fun guest(): LeakThisResponse<Authenticate, String>

    @FormUrlEncoded
    @POST(ROUTE_AUTH_GUEST_LOGIN)
    suspend fun guest(@FieldMap(encoded = true) credential: CredentialRequest): LeakThisResponse<Authenticate, String>


    @FormUrlEncoded
    @POST(ROUTE_AUTH_REFRESH)
    suspend fun refresh(@FieldMap(encoded = true) credential: CredentialRequest): LeakThisResponse<OAuth2, Any>

    @FormUrlEncoded
    @POST(ROUTE_AUTH_REGISTER)
    suspend fun register(@FieldMap(encoded = true) credential: CredentialRequest): LeakThisResponse<Authenticate, Any>

    @FormUrlEncoded
    @POST(ROUTE_LEAKTHIS_LOGIN)
    suspend fun lt_login(@FieldMap(encoded = true) credential: CredentialRequest): LeakThisResponse<LeakThisAuth, String>

    @FormUrlEncoded
    @POST(ROUTE_LEAKTHIS_LOGOUT)
    suspend fun lt_logout(@FieldMap(encoded = true) credential: CredentialRequest): LeakThisResponse<String, Any>

}