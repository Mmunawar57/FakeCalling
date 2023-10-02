package com.example.fakecalling.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.example.fakecalling.Admanager.AppInterstitialAds
import com.example.fakecalling.R
import com.example.fakecalling.RoomDatabase.HistoryDatabases
import com.example.fakecalling.RoomDatabase.history
import com.example.fakecalling.databinding.ActivityCallBinding
import com.example.fakecalling.gesture.OnSwipeTouchListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CallActivity : LocalizationActivity() {
    lateinit var database: HistoryDatabases
    private var mediaplayer: MediaPlayer? =null
    private val binding by lazy {
       ActivityCallBinding.inflate(layoutInflater)
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(binding.root)

        database= Room.databaseBuilder(applicationContext,HistoryDatabases::class.java,
            "historyDb").build()


        playMusic()
        val imguri=intent.extras?.getString("image_url")
        if (imguri==null){
            binding.profileImg.setImageResource(R.drawable.profile)
        }else{
            binding.profileImg.setImageURI(Uri.parse(imguri))
        }

        val name= intent.extras?.getString("name")
        val number= intent.extras?.getString("number")


        name?.let {
            if (number != null) {
                CoroutineScope(Dispatchers.Default).launch {
                    database.historyDao().inserthistory(   history( it,number))
                }

            }
        }

        binding.name.text=name
       binding.number.text=number
        val sharedPref: SharedPreferences = this.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val bg1 = sharedPref.getString("background","no")
        when(bg1){
            "screen1"->{
                binding.mainCall.setBackgroundResource(R.drawable.bg1)
                binding.callAtendlayout.includeLayout.background=null
            }
            "screen2"->{
                binding.mainCall.setBackgroundResource(R.drawable.bg2)
                binding.callAtendlayout.includeLayout.background=null
            }
            "screen3"->{
                binding.mainCall.setBackgroundResource(R.drawable.bg3)
                binding.callAtendlayout.includeLayout.background=null
            }
            "screen4"->{
                binding.mainCall.setBackgroundResource(R.drawable.bg4)
                binding.callAtendlayout.includeLayout.background=null
            }
        }


        ////////for animation//////////////////
        val images = arrayOf(binding.img4, binding.img3, binding.img2, binding.img1)

        val anims = ArrayList<ObjectAnimator>(images.size * 2)
        for (v in images) anims.add(ObjectAnimator.ofFloat(v, View.ALPHA, 1f, 0f).setDuration(80))
        for (v in images) anims.add(ObjectAnimator.ofFloat(v, View.ALPHA, 0f, 1f).setDuration(80))

        val set = AnimatorSet()

        set.addListener(object : AnimatorListenerAdapter() {
           override fun onAnimationEnd(animation: Animator) = set.start()
        })

        set.startDelay = 600

        for (i in 0 until anims.size - 1) set.play(anims[i]).before(anims[i + 1])

       set.start()

        binding.decline.setOnClickListener {
            mediaplayer?.stop()
            AppInterstitialAds.showInterAd(this@CallActivity){
                finish()
            }

        }
        val shake = AnimationUtils.loadAnimation(this, com.example.fakecalling.R.anim.shake)
        binding.callPick.startAnimation(shake)
        binding.callPick.setOnTouchListener(object : OnSwipeTouchListener(this@CallActivity) {
            override fun onSwipeUp() {
                super.onSwipeUp()
                binding.callPick.clearAnimation()
                mediaplayer?.stop()
                binding.declineTxt.visibility=View.GONE
                binding.callPick.visibility=View.GONE
                binding.swipeUpText.visibility=View.GONE
                binding.arrowUpAnimation.visibility=View.GONE
                binding.decline.visibility=View.VISIBLE
               binding.callAtendlayout.includeLayout.visibility=View.VISIBLE
                binding.timer.visibility=View.VISIBLE
                startTimer()
                binding.number.visibility=View.GONE
            }
            @SuppressLint("ClickableViewAccessibility")
            override fun onSwipeDown() {
                super.onSwipeDown()
                mediaplayer?.stop()
                finish()
            }
        })

    }
    private fun playMusic(){
        val media=MediaPlayer.create(this , Settings.System.DEFAULT_RINGTONE_URI)
        if (media==null)
        {
            mediaplayer=MediaPlayer.create(this,R.raw.ringtone)
            mediaplayer?.isLooping=true

        }else{
            mediaplayer= media
        }

        mediaplayer?.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaplayer?.stop()
    }
    private fun startTimer(){
        binding.timer.onChronometerTickListener = Chronometer.OnChronometerTickListener { cArg ->
            val time = SystemClock.elapsedRealtime() - cArg.base
            val h = (time / 3600000).toInt()
            val m = (time - h * 3600000).toInt() / 60000
            val s = (time - h * 3600000 - m * 60000).toInt() / 1000
            val mm = if (m < 10) "0$m" else m.toString() + ""
            val ss = if (s < 10) "0$s" else s.toString() + ""
            cArg.text = "$mm:$ss"
        }
        binding.timer.base = SystemClock.elapsedRealtime()
        binding.timer.start()

    }

}