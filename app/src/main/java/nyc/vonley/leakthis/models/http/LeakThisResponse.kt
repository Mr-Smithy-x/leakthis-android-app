package nyc.vonley.leakthis.models.http

data class LeakThisResponse<T, E>(
    val status: Int,
    val message: String?,
    val data: T?,
    val meta: LeakThisPagination?,
    val extra: E?,
    val error: String?
) {


    fun failed(): Boolean {
        return error != null
    }

    fun success(): Boolean {
        return error == null
    }

}