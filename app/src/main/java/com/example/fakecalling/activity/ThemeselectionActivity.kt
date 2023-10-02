package com.example.fakecalling.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.view.isGone
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.example.fakecalling.Admanager.AppBannerAds
import com.example.fakecalling.Admanager.AppInterstitialAds
import com.example.fakecalling.Admanager.AppNativeAds
import com.example.fakecalling.databinding.ActivityThemeselectionBinding

class ThemeselectionActivity : LocalizationActivity() {
    private val binding by lazy {
       ActivityThemeselectionBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(binding.root)
        AppNativeAds.inflateBigAds(this,binding.themeNativeadd)
        val sharedPref = this.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        binding.screen1.setOnClickListener{
            if (binding.select1.isGone){
                AppInterstitialAds.showInterAd(this@ThemeselectionActivity){}
                binding.select1.visibility= View.VISIBLE
                binding.select2.visibility= View.GONE
                binding.select3.visibility= View.GONE
                binding.select4.visibility= View.GONE
            }
            editor.putString("background", "screen1")
            editor.apply()
        }
        binding.screen2.setOnClickListener {
            if (binding.select2.isGone){
                AppInterstitialAds.showInterAd(this@ThemeselectionActivity){}
                binding.select2.visibility= View.VISIBLE
                binding.select1.visibility= View.GONE
                binding.select4.visibility= View.GONE
                binding.select3.visibility= View.GONE
            }
            editor.putString("background", "screen2")
            editor.apply()
        }
        binding.screen3.setOnClickListener {
            if (binding.select3.isGone){
                AppInterstitialAds.showInterAd(this@ThemeselectionActivity){}
                binding.select3.visibility= View.VISIBLE
                binding.select1.visibility= View.GONE
                binding.select2.visibility= View.GONE
                binding.select4.visibility= View.GONE

            }
            editor.putString("background", "screen3")
            editor.apply()
        }
        binding.screen4.setOnClickListener {
            if (binding.select4.isGone){
                AppInterstitialAds.showInterAd(this@ThemeselectionActivity){}
                binding.select1.visibility= View.GONE
                binding.select2.visibility= View.GONE
                binding.select3.visibility= View.GONE
                binding.select4.visibility= View.VISIBLE
            }
            editor.putString("background", "screen4")
            editor.apply()
        }


        binding.backButton.setOnClickListener {
           finish()
        }
    }
}