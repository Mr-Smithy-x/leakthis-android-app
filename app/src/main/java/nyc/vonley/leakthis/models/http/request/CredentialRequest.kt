package nyc.vonley.leakthis.models.http.request

class CredentialRequest private constructor() : HashMap<String, String>() {

    companion object {

        fun login(email: String, password: String): CredentialRequest {
            val c = CredentialRequest()
            c["email"] = email
            c["username"] = email
            c["password"] = password
            return c;
        }

        fun register(name: String, email: String, password: String): CredentialRequest {
            val c = CredentialRequest()
            c["name"] = name
            c["email"] = email
            c["password"] = password
            c["password_confirmed"] = password
            return c
        }

        fun refresh(refresh_token: String): CredentialRequest {
            val c = CredentialRequest()
            c["refresh_token"] = refresh_token
            return c
        }
    }

}