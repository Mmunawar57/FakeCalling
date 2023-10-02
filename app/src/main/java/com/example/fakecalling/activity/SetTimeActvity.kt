package com.example.fakecalling.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import com.example.fakecalling.Admanager.AppBannerAds
import com.example.fakecalling.Admanager.AppInterstitialAds
import com.example.fakecalling.Admanager.AppNativeAds
import com.example.fakecalling.R
import com.example.fakecalling.databinding.ActivitySetTimeActvityBinding
import kotlinx.coroutines.*

class SetTimeActvity : LocalizationActivity() {
    var tempTime: Long? = null
    private val binding by lazy {
        ActivitySetTimeActvityBinding.inflate(layoutInflater)
    }
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(binding.root)
        AppNativeAds.inflateBigAds(this,binding.settimeNativeAdd)
        binding.BackIcon.setOnClickListener {

            AppInterstitialAds.showInterAd(this@SetTimeActvity){
                finish()
            }
        }
        SetTime()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        AppInterstitialAds.showInterAd(this@SetTimeActvity){
            finish()
        }

    }
    fun SetTime() {

        binding.t1.setOnClickListener {
            t1changecolor()
            tempTime = 10000


        }
        binding.t2.setOnClickListener {
            t2changecolor()
            tempTime = 30000


        }
        binding.t3.setOnClickListener {
            t3changecolor()
            tempTime = 60000

        }
        binding.t4.setOnClickListener {
            t4changecolor()
            tempTime = 120000
        }
        binding.t5.setOnClickListener {
            t5changecolor()
            tempTime = 300000

        }
        binding.t6.setOnClickListener {
            t6changecolor()
            tempTime = 1800000

        }
        binding.t7.setOnClickListener {
            t7changecolor()
            tempTime = 3600000

        }
        binding.Now.setOnClickListener {
            tNowchangecolor()
            tempTime = 0
        }
        binding.done.setOnClickListener {
            tempTime?.let { it1 -> doneClicked(it1) }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun doneClicked(time: Long) {
        if (job != null) {
            job!!.cancel()
        }
        job = lifecycleScope.launch {

            val timeSec = time / 1000
            Toast.makeText(this@SetTimeActvity, "Timer set for ${timeSec} sec", Toast.LENGTH_SHORT)
                .show()

            delay(time)
            withContext(Dispatchers.Main) {
                val imguri=intent.extras?.getString("image_url")
                val name= intent.extras?.getString("name")
                val number= intent.extras?.getString("number")
           val intent=Intent(this@SetTimeActvity,CallActivity::class.java)
                intent.putExtra("image_url",imguri)
                intent.putExtra("name",name)
                intent.putExtra("number",number)
                startActivity(intent)
            }


        }
    }

    fun t1changecolor() {
        binding.t1.setBackgroundResource(R.drawable.timerset_roundshape)
        binding.t2.setBackgroundResource(R.drawable.red_roundshape)
        binding.t3.setBackgroundResource(R.drawable.red_roundshape)
        binding.t4.setBackgroundResource(R.drawable.red_roundshape)
        binding.t5.setBackgroundResource(R.drawable.red_roundshape)
        binding.t6.setBackgroundResource(R.drawable.red_roundshape)
        binding.t7.setBackgroundResource(R.drawable.red_roundshape)
        binding.Now.setBackgroundResource(R.drawable.red_roundshape)
    }

    fun t2changecolor() {
        binding.t1.setBackgroundResource(R.drawable.red_roundshape)
        binding.t2.setBackgroundResource(R.drawable.timerset_roundshape)
        binding.t3.setBackgroundResource(R.drawable.red_roundshape)
        binding.t4.setBackgroundResource(R.drawable.red_roundshape)
        binding.t5.setBackgroundResource(R.drawable.red_roundshape)
        binding.t6.setBackgroundResource(R.drawable.red_roundshape)
        binding.t7.setBackgroundResource(R.drawable.red_roundshape)
        binding.Now.setBackgroundResource(R.drawable.red_roundshape)

    }

    fun t3changecolor() {
        binding.t1.setBackgroundResource(R.drawable.red_roundshape)
        binding.t2.setBackgroundResource(R.drawable.red_roundshape)
        binding.t3.setBackgroundResource(R.drawable.timerset_roundshape)
        binding.t4.setBackgroundResource(R.drawable.red_roundshape)
        binding.t5.setBackgroundResource(R.drawable.red_roundshape)
        binding.t6.setBackgroundResource(R.drawable.red_roundshape)
        binding.t7.setBackgroundResource(R.drawable.red_roundshape)
        binding.Now.setBackgroundResource(R.drawable.red_roundshape)

    }

    fun t4changecolor() {
        binding.t1.setBackgroundResource(R.drawable.red_roundshape)
        binding.t2.setBackgroundResource(R.drawable.red_roundshape)
        binding.t3.setBackgroundResource(R.drawable.red_roundshape)
        binding.t4.setBackgroundResource(R.drawable.timerset_roundshape)
        binding.t5.setBackgroundResource(R.drawable.red_roundshape)
        binding.t6.setBackgroundResource(R.drawable.red_roundshape)
        binding.t7.setBackgroundResource(R.drawable.red_roundshape)
        binding.Now.setBackgroundResource(R.drawable.red_roundshape)
    }

    fun t5changecolor() {
        binding.t1.setBackgroundResource(R.drawable.red_roundshape)
        binding.t2.setBackgroundResource(R.drawable.red_roundshape)
        binding.t3.setBackgroundResource(R.drawable.red_roundshape)
        binding.t4.setBackgroundResource(R.drawable.red_roundshape)
        binding.t5.setBackgroundResource(R.drawable.timerset_roundshape)
        binding.t6.setBackgroundResource(R.drawable.red_roundshape)
        binding.t7.setBackgroundResource(R.drawable.red_roundshape)
        binding.Now.setBackgroundResource(R.drawable.red_roundshape)
    }

    fun t6changecolor() {
        binding.t1.setBackgroundResource(R.drawable.red_roundshape)
        binding.t2.setBackgroundResource(R.drawable.red_roundshape)
        binding.t3.setBackgroundResource(R.drawable.red_roundshape)
        binding.t4.setBackgroundResource(R.drawable.red_roundshape)
        binding.t5.setBackgroundResource(R.drawable.red_roundshape)
        binding.t6.setBackgroundResource(R.drawable.timerset_roundshape)
        binding.t7.setBackgroundResource(R.drawable.red_roundshape)
        binding.Now.setBackgroundResource(R.drawable.red_roundshape)
    }

    fun t7changecolor() {
        binding.t1.setBackgroundResource(R.drawable.red_roundshape)
        binding.t2.setBackgroundResource(R.drawable.red_roundshape)
        binding.t3.setBackgroundResource(R.drawable.red_roundshape)
        binding.t4.setBackgroundResource(R.drawable.red_roundshape)
        binding.t5.setBackgroundResource(R.drawable.red_roundshape)
        binding.t6.setBackgroundResource(R.drawable.red_roundshape)
        binding.t7.setBackgroundResource(R.drawable.timerset_roundshape)
        binding.Now.setBackgroundResource(R.drawable.red_roundshape)
    }

    fun tNowchangecolor() {
        binding.t1.setBackgroundResource(R.drawable.red_roundshape)
        binding.t2.setBackgroundResource(R.drawable.red_roundshape)
        binding.t3.setBackgroundResource(R.drawable.red_roundshape)
        binding.t4.setBackgroundResource(R.drawable.red_roundshape)
        binding.t5.setBackgroundResource(R.drawable.red_roundshape)
        binding.t6.setBackgroundResource(R.drawable.red_roundshape)
        binding.t7.setBackgroundResource(R.drawable.red_roundshape)
        binding.Now.setBackgroundResource(R.drawable.timerset_roundshape)
    }
}