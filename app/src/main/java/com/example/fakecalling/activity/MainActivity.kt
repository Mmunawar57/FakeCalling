package com.example.fakecalling.activity
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.example.fakecalling.Admanager.AppInterstitialAds
import com.example.fakecalling.Admanager.AppNativeAds
import com.example.fakecalling.R
import com.example.fakecalling.databinding.ActivityMainBinding
import com.example.fakecalling.databinding.ExitDialogueBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class MainActivity : LocalizationActivity() {
    private lateinit var navController: NavController

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(binding.root)
//        AppLivin_BannerAd.createBannerAd(binding.mainBannerAdd,this)
        AppInterstitialAds.showInterAd(this@MainActivity){}

        navController= Navigation.findNavController(this, R.id.fragmentContainerView)
        setupWithNavController(binding.bottomNav,navController)

        val indicatorColor = ContextCompat.getColorStateList(this, R.color.indicator)

// Set the color state list as the active item indicator color
        binding.bottomNav.itemActiveIndicatorColor = indicatorColor

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val navController=Navigation.findNavController(this, R.id.fragmentContainerView)


        when(navController.currentDestination?.id){
            R.id.settingsFragment->{

                    navController.navigate(R.id.action_settingsFragment_to_homeFragment)


            }
            R.id.historyFragment->{
                navController.navigate(R.id.action_historyFragment_to_homeFragment)
            }
            R.id.homeFragment->{
                showexit()
            }
        }
    }

    private fun showexit() {
        val dialogBinding = ExitDialogueBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)
        AppNativeAds.inflateBigAds(this,dialogBinding.exitNativeAd)
        dialogBinding.exitNo.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.exitYes.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()
    }

}