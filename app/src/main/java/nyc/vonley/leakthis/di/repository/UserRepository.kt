package nyc.vonley.leakthis.di.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import nyc.vonley.leakthis.di.services.UserService
import nyc.vonley.leakthis.models.User
import nyc.vonley.leakthis.persistence.UserDao
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val service: UserService,
    private val userDao: UserDao
) : Repository {
    @WorkerThread
    suspend fun getUser(
        id: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = flow<List<User>?> {
        val user = userDao.getUser(id)
        if (user == null) {
            /*val data = service.search("", 1)
            data.data?.let {
                userDao.insertUsers(it)
                emit(it)
                onSuccess()
            }*/
        } else {
            emit(user)
            onSuccess()
        }
    }.flowOn(Dispatchers.IO)

}
