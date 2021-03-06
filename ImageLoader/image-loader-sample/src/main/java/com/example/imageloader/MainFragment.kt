package com.example.imageloader

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.imageloader.databinding.FragmentMainBinding
import com.example.imageloader.imageloaderlib.ImageLoader
import com.example.imageloader.imageloaderlib.ImageLoaderImpl
import com.example.imageloader.imageloaderlib.models.OnDemandRemoteResource
import com.example.imageloader.imageloaderlib.models.RemoteResource
import com.facebook.shimmer.Shimmer

class MainFragment : Fragment() {

    private val imageLoader: ImageLoader = ImageLoaderImpl()

    private var _binding: FragmentMainBinding? = null
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
            resource = RemoteResource(url = "https://i0.wp.com/unaaldia.hispasec.com/wp-content/uploads/2017/03/3c744-android-logo.png"),
            targetView = binding.imageViewRemoteResourceImage,
            onResourceLoaded = {
                Log.d("LMB", "imageLoader.loadResource(targetView = binding.imageViewRemoteResourceImage):: onResourceLoaded")
            },
            onResourceLoadFailed = { exception ->
                Log.d("LMB", "imageLoader.loadResource(targetView = binding.imageViewRemoteResourceImage):: onResourceLoadFailed($exception)")
            }
        )

        imageLoader.loadImage(
            resource = OnDemandRemoteResource(
                resourceName = "RPPAY-DECLINED-BY-RISK",
                useCountryCode = false,
                useLanguageCode = false
            ),
            targetView = binding.imageViewOnDemandResourceImage,
            onResourceLoaded = {
                Log.d("LMB", "imageLoader.loadResource(targetView = binding.imageViewOnDemandResourceImage):: onResourceLoaded")
            },
            onResourceLoadFailed = { exception ->
                Log.d("LMB", "imageLoader.loadResource(targetView = binding.imageViewOnDemandResourceImage):: onResourceLoadFailed($exception)")
            }
        )

        imageLoader.loadAnimation(
            resource = RemoteResource(url = "https://assets5.lottiefiles.com/datafiles/zc3XRzudyWE36ZBJr7PIkkqq0PFIrIBgp4ojqShI/newAnimation.json"),
            targetView = binding.lottieAnimationViewRemoteResourceLottie,
            onResourceLoaded = {
                Log.d("LMB", "imageLoader.loadResource(targetView = binding.lottieAnimationViewRemoteResourceLottie):: onResourceLoaded")
            },
            onResourceLoadFailed = { exception ->
                Log.d("LMB", "imageLoader.loadResource(targetView = binding.lottieAnimationViewRemoteResourceLottie):: onResourceLoadFailed($exception)")
            }
        )

        imageLoader.loadAnimation(
            resource = OnDemandRemoteResource(
                resourceName = "RPPAY-PAY-RDA-VALIDATING-RISK",
                useCountryCode = false,
                useLanguageCode = false
            ),
            targetView = binding.lottieAnimationViewOnDemandResourceLottie,
            onResourceLoaded = {
                Log.d("LMB", "imageLoader.loadResource(targetView = binding.lottieAnimationViewOnDemandResourceLottie):: onResourceLoaded")
            },
            onResourceLoadFailed = { exception ->
                Log.d("LMB", "imageLoader.loadResource(targetView = binding.lottieAnimationViewOnDemandResourceLottie):: onResourceLoadFailed($exception)")
            }
        )

        configureImageButtonTogglePlayStop(PlayingState.PLAYING)


        val shimmer = Shimmer.ColorHighlightBuilder()
            .setBaseColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))
            //.setHighlightColor(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark))
            .build()
        binding.shimmerFrameLayout.setShimmer(shimmer)
        binding.shimmerFrameLayout.startShimmer()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.shimmerFrameLayout.setShimmer(null)
        }, 2000)
    }

    private fun configureImageButtonTogglePlayStop(playingState: PlayingState) {
        when (playingState) {
            PlayingState.PLAYING -> {
                binding.imageButtonTogglePlayStop.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_pause_24dp))
                binding.imageButtonTogglePlayStop.setOnClickListener {
                    binding.lottieAnimationViewRemoteResourceLottie.pauseAnimation()
                    binding.lottieAnimationViewOnDemandResourceLottie.pauseAnimation()
                    configureImageButtonTogglePlayStop(playingState = PlayingState.STOPPED)
                }
            }
            PlayingState.STOPPED -> {
                binding.imageButtonTogglePlayStop.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_play_24dp))
                binding.imageButtonTogglePlayStop.setOnClickListener {
                    binding.lottieAnimationViewRemoteResourceLottie.resumeAnimation()
                    binding.lottieAnimationViewOnDemandResourceLottie.resumeAnimation()
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
