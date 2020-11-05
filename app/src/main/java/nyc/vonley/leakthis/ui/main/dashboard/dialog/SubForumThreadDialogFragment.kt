package nyc.vonley.leakthis.ui.main.dashboard.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.models.LeakThisForum
import nyc.vonley.leakthis.ui.main.forum.ForumThreadActivity
import javax.inject.Inject

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    SubForumThreadDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */

@AndroidEntryPoint
class SubForumThreadDialogFragment @Inject constructor() : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_sub_forum_thread_dialog,
            container,
            false
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val titleView = view.findViewById<AppCompatTextView>(R.id.fragment_sub_forum_thread_dialog_title)
        val recyclerView =
            view.findViewById<RecyclerView>(R.id.fragment_sub_forum_thread_dialog_recycler)
        recyclerView.layoutManager =
            LinearLayoutManager(context)
        val items = requireArguments().getParcelable<LeakThisForum.ForumThread>(KEY)
        val adapter = SubForumRecyclerAdapter(items!!.sub_forums)

        recyclerView.adapter = adapter
        recyclerView?.adapter?.notifyDataSetChanged()
        titleView.text = items.title
    }


    private inner class SubForumRecyclerAdapter internal constructor(private val items: ArrayList<LeakThisForum.SubForum>) :
        RecyclerView.Adapter<SubForumRecyclerAdapter.SubForumViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubForumViewHolder {
            return SubForumViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: SubForumViewHolder, position: Int) {
            holder.text.text = items[position].title
        }

        override fun getItemCount(): Int {
            return items.size
        }


        private inner class SubForumViewHolder internal constructor(
            inflater: LayoutInflater,
            parent: ViewGroup
        ) : RecyclerView.ViewHolder(
            inflater.inflate(
                R.layout.view_holder_sub_forum_thread,
                parent,
                false
            )
        ), View.OnClickListener {
            internal val text: TextView = itemView.findViewById(R.id.view_holder_sub_forum_thread_title)

            init {
                this.itemView.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                ForumThreadActivity.start(this.itemView.context, items[adapterPosition])
            }
        }

    }

    companion object {

        const val KEY = "SUBFORUMS"

        fun create(items: LeakThisForum.ForumThread): SubForumThreadDialogFragment =
            SubForumThreadDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY, items)
                }
            }
    }
}