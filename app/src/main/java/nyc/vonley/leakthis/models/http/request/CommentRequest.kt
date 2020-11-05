package nyc.vonley.leakthis.models.http.request

class CommentRequest private constructor() : HashMap<String, Any>() {

    companion object {

        fun create(comment: String): CommentRequest {
            val c = CommentRequest()
            c["comment"] = comment
            return c;
        }

    }

}