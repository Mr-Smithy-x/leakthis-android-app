package nyc.vonley.leakthis.ui.main.profile

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import nyc.vonley.leakthis.R
import nyc.vonley.leakthis.ui.main.MainContract
import nyc.vonley.leakthis.views.SubjectView
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment @Inject constructor() : Fragment(), ProfileContract.View {

    private lateinit var notificationsViewModel: ProfileViewModel

    @Inject
    lateinit var presenter: ProfileContract.Presenter

    private var mainView: MainContract.View? = null


    var name: AppCompatTextView? = null
    var user_title: AppCompatTextView? = null
    var about: AppCompatTextView? = null
    var location: SubjectView? = null
    var website: SubjectView? = null
    var discord: SubjectView? = null
    var instagram: SubjectView? = null
    var reddit: SubjectView? = null
    var lastfm: SubjectView? = null
    var skype: SubjectView? = null
    var twitter: SubjectView? = null
    var avatar: CircleImageView? = null
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        name = root.findViewById(R.id.fragment_profile_username)
        user_title = root.findViewById(R.id.fragment_profile_user_title)
        about = root.findViewById(R.id.fragment_profile_about)
        location = root.findViewById(R.id.fragment_profile_location)
        website = root.findViewById(R.id.fragment_profile_website)
        discord = root.findViewById(R.id.fragment_profile_discord)
        instagram = root.findViewById(R.id.fragment_profile_instagram)
        reddit = root.findViewById(R.id.fragment_profile_reddit)
        lastfm = root.findViewById(R.id.fragment_profile_lastfm)
        skype = root.findViewById(R.id.fragment_profile_skype)
        twitter = root.findViewById(R.id.fragment_profile_twitter)
        avatar = root.findViewById(R.id.fragment_profile_avatar)

        if (mainView != null) {
            val profile = mainView!!.getProfileInstance()
            Glide.with(this).asBitmap().load(profile.avatar)
                .fallback(R.drawable.ic_svg_user_2_white).into(avatar!!)
            name?.text = profile.name
            user_title?.text = profile.user_title
            about?.text =
                Html.fromHtml(profile.about_html, Html.FROM_HTML_MODE_COMPACT)
            website?.valueView?.text = profile.profile?.website
            location?.valueView?.text = profile.profile?.location
            discord?.valueView?.text = profile.custom_fields?.Discord
            instagram?.valueView?.text = profile.custom_fields?.Instagram
            lastfm?.valueView?.text = profile.custom_fields?.LastFM
            reddit?.valueView?.text = profile.custom_fields?.Reddit
            skype?.valueView?.text = profile.custom_fields?.skype
            twitter?.valueView?.text = profile.custom_fields?.twitter
        }
        return root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainView = context as MainContract.View
    }

    override fun onDetach() {
        super.onDetach()
        mainView = null
    }

    override fun onError(e: Exception) {
        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
    }


}