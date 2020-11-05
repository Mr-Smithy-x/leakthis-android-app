package nyc.vonley.leakthis.di.sources

import nyc.vonley.leakthis.di.services.AuthenticationService
import nyc.vonley.leakthis.models.LeakThisProfile
import nyc.vonley.leakthis.models.auth.OAuth2
import nyc.vonley.leakthis.models.http.Authenticate
import nyc.vonley.leakthis.models.http.LeakThisAuth
import nyc.vonley.leakthis.models.http.LeakThisResponse
import nyc.vonley.leakthis.models.http.request.CredentialRequest

class AuthenticationSource constructor(val service: AuthenticationService) : AuthenticationService {
    override suspend fun login(credential: CredentialRequest): LeakThisResponse<Authenticate, Any> {
        return service.login(credential)
    }

    override suspend fun guest(): LeakThisResponse<Authenticate, String> {
        return service.guest()
    }

    override suspend fun guest(credential: CredentialRequest): LeakThisResponse<Authenticate, String> {
        return service.guest(credential)
    }

    override suspend fun refresh(credential: CredentialRequest): LeakThisResponse<OAuth2, Any> {
        return service.refresh(credential)
    }

    override suspend fun register(credential: CredentialRequest): LeakThisResponse<Authenticate, Any> {
        return service.register(credential)
    }

    override suspend fun lt_login(credential: CredentialRequest): LeakThisResponse<LeakThisAuth, String> {
        return service.lt_login(credential)
    }

    override suspend fun lt_logout(credential: CredentialRequest): LeakThisResponse<String, Any> {
        return service.lt_logout(credential)
    }


}