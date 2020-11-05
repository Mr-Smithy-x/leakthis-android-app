package nyc.vonley.leakthis.di.modules.glide

import android.util.Base64
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import java.nio.ByteBuffer

class Base64DataFetcher(val model: String) : DataFetcher<ByteBuffer> {

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in ByteBuffer>) {
        val base64Section = getBase64SectionOfModel()
        val data: ByteArray = Base64.decode(base64Section, Base64.DEFAULT)
        val byteBuffer = ByteBuffer.wrap(data)
        callback.onDataReady(byteBuffer)
    }

    override fun cleanup() {

    }

    override fun cancel() {

    }

    private fun getBase64SectionOfModel(): String? {
        val startOfBase64Section = model.indexOf(',')
        return model.substring(startOfBase64Section + 1)
    }

    override fun getDataClass(): Class<ByteBuffer> {
        return ByteBuffer::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.LOCAL
    }

}
