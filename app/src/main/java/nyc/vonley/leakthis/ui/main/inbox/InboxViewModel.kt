package nyc.vonley.leakthis.ui.main.inbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nyc.vonley.leakthis.models.LeakThisConversation

class InboxViewModel: ViewModel() {
    fun add(conversations: java.util.ArrayList<LeakThisConversation>) {
        this._conversations.value?.addAll(conversations)
    }

    private val _conversations = MutableLiveData<ArrayList<LeakThisConversation>>().apply {
        value = ArrayList()
    }
    val conversations: LiveData<ArrayList<LeakThisConversation>> = _conversations
}