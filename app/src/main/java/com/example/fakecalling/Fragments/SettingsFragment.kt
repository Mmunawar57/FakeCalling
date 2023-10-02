package com.example.fakecalling.Fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fakecalling.activity.ThemeselectionActivity
import com.example.fakecalling.Admanager.AppNativeAds
import com.example.fakecalling.R
import com.example.fakecalling.activity.HowToUseActivity
import com.example.fakecalling.activity.LanguageActivity
import com.example.fakecalling.databinding.FragmentSettings2Binding


class SettingsFragment : Fragment() {
    private val binding by lazy {
        FragmentSettings2Binding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        AppNativeAds.inflateBigAds(requireContext(),binding.frameLayoutAds)
        binding.backButton.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_homeFragment)
        }
        binding.privacy1.setOnClickListener {

            try{
                val uri = Uri.parse("https://sites.google.com/view/fakecallprankprivacy/home")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            catch(e : ActivityNotFoundException){
            }
        }
        binding.callscreen1.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, ThemeselectionActivity::class.java))
            }
        }
        binding.MoreApps.setOnClickListener {
            try{
                val uri = Uri.parse("https://play.google.com/store/apps/developer?id=Bitz+Tech")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            catch(e : ActivityNotFoundException){
            }
        }
        binding.rate.setOnClickListener {
            try{
                val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.bitz.fake.video.call.prank.friends")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            catch(e : ActivityNotFoundException){
            }
        }
        binding.Languages.setOnClickListener {
            startActivity(Intent(requireContext(),LanguageActivity::class.java))
        }
        binding.howtouse.setOnClickListener {
            startActivity(Intent(requireContext(),HowToUseActivity::class.java))
        }
        return binding.root
    }
}