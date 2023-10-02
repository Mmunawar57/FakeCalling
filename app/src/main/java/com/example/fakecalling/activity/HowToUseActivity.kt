package com.example.fakecalling.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.example.fakecalling.Admanager.AppNativeAds
import com.example.fakecalling.R
import com.example.fakecalling.adapters.SliderAdapter
import com.example.fakecalling.application.MyApplication
import com.example.fakecalling.databinding.ActivityHowToUseBinding
import com.example.fakecalling.viewmodels.Slider

class HowToUseActivity : LocalizationActivity() {

    private lateinit var binding:ActivityHowToUseBinding
    private  var itemList= mutableListOf<Slider>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHowToUseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MyApplication.prefs!!.putBoolean("HOW_TO_USE_IS_OPENED",true)
        itemList.add(Slider(R.drawable.set_name, getString(R.string.write_down_phone_number_name_and_set_profile_picture_from_the_gallery)))
        itemList.add(Slider(R.drawable.set_theme,
            getString(R.string.here_two_options_here_one_to_set_call_time_duration_and_second_one_for_set_theme)))
        itemList.add(Slider(R.drawable.set_timer,
            getString(R.string.here_you_would_have_to_select_time_duration_for_the_prank_call)))
        itemList.add(Slider(R.drawable.chose_theme,
            getString(R.string.just_click_on_the_theme_that_you_want_on_call_screen)))

        val adapter= SliderAdapter(itemList)
        binding.apply {
            AppNativeAds.inflateBigAds(this@HowToUseActivity,frameLayoutAds)
            viewPager.adapter=adapter
            indicator.attachTo(binding.viewPager)
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == itemList.size - 1) {
                        binding.btnFinish.visibility = View.VISIBLE
                    } else {
                        binding.btnFinish.visibility = View.INVISIBLE
                    }
                }
            })
            btnSkip.setOnClickListener {
                startActivity(Intent(this@HowToUseActivity,MainActivity::class.java))
                finish()
            }
            btnFinish.setOnClickListener {
                startActivity(Intent(this@HowToUseActivity,MainActivity::class.java))
                finish()
            }
        }
    }
}