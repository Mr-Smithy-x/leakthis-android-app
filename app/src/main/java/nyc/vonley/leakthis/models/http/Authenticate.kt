package nyc.vonley.leakthis.models.http

import nyc.vonley.leakthis.models.LeakThisProfile
import nyc.vonley.leakthis.models.User
import nyc.vonley.leakthis.models.auth.OAuth2

data class Authenticate(val user: User?, val profile: LeakThisProfile?, val oauth: OAuth2?) {
}