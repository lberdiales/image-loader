package com.example.imageloader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.imageloader.databinding.FragmentMainBinding
import com.example.imageloader.loaders.OnDemandResource

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private val imageLoader: ImageLoader = ImageLoaderImpl()

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageLoader.loadImage(
            imageView = binding.imageView,
            url = "https://i0.wp.com/unaaldia.hispasec.com/wp-content/uploads/2017/03/3c744-android-logo.png"
        )

        imageLoader.loadOnDemandImage(
            imageView = binding.imageViewOnDemand,
            onDemandResource = OnDemandResource(
                resourceName = "RESOURCE_NAME_6",
                useCountryCode = false,
                useLanguageCode = false
            ).apply {
                //stringUrl = "https://lh3.googleusercontent.com/OOjYcooPxIC4PHWxKGg5tfVm5qbJB4m2IMvhmXCOMl9Ps4T6dvmcA66UscrkML0lU6WR0IfswAL9QNpEL63mpLvrtDMiLnOMYCT8rhkC-eIXjhDNk6wGlx-nMJeZzyrvairQOD48KnxhY9vc-tahh7rgKoJeR1mwfoJIVfBNRwlNTSrLkrDZFAU15fvGofmKCrrvlUgUka6tpD80A1-Dm3KRE9knS0m1UHssQ6-KOFdGSndZ70ayGV5pY-n-zDsMYAzDNQMwvb2AhUddiO6VOViXztUqiYuVX5eqCnL7z-bndTcDAqfyohvw8txH5bvc1VR0XcQPjGzJ6EVkdZso2T4b5NoFufzlIP3DPjoFE37VKEGmnI-QMhz9m_IwuJ2U0WXBP9Q4pJkVPqwbIZzm-g338ZETis17D3r52v4hDsq5mN7vzV5KcRHs5l1uivdS5Wj5SQ0t96xmndOEOUISyIxGWeeDGIVSImnK6GuLEfrO4Vsi9gc4Qi8KU5aDBZ0rsbTM-hgNObqBTs-AebwR9gspWCqW7Cigfnezbf1bHAyvPjoLaJ_2IxjoF9KZxjPieYRuXMoDpdhvT5_0cfEsUQF8HjR1qBPku_asce3UtQGvIhMikw=s0"
                //stringUrl = "https://square.github.io/images/logo.png"
                //stringUrl = "https://services.grability.rappi.com/api/ms/core-lottie/name/RPPAY-PAY-RDA-VALIDATING-RISK"
                stringUrl = "https://services.grability.rappi.com/api/ms/core-lottie/name/RPPAY-DECLINED-BY-RISK"
            }
        )

        imageLoader.loadLottie(
            imageView = binding.imageViewLottie,
            lottieUrl = "https://assets5.lottiefiles.com/datafiles/zc3XRzudyWE36ZBJr7PIkkqq0PFIrIBgp4ojqShI/newAnimation.json"
        )
        // binding.imageViewLottie.setAnimationFromUrl("https://assets5.lottiefiles.com/datafiles/zc3XRzudyWE36ZBJr7PIkkqq0PFIrIBgp4ojqShI/newAnimation.json")
        configureImageButtonTogglePlayStop(PlayingState.PLAYING)
    }

    private fun configureImageButtonTogglePlayStop(playingState: PlayingState) {
        when (playingState) {
            PlayingState.PLAYING -> {
                binding.imageButtonTogglePlayStop.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_pause_24dp))
                binding.imageButtonTogglePlayStop.setOnClickListener {
                    binding.imageViewLottie.pauseAnimation()
                    configureImageButtonTogglePlayStop(playingState = PlayingState.STOPPED)
                }
            }
            PlayingState.STOPPED -> {
                binding.imageButtonTogglePlayStop.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_play_24dp))
                binding.imageButtonTogglePlayStop.setOnClickListener {
                    binding.imageViewLottie.playAnimation()
                    configureImageButtonTogglePlayStop(playingState = PlayingState.PLAYING)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private enum class PlayingState {
        PLAYING, STOPPED
    }
}