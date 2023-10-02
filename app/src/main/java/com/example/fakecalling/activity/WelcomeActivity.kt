package com.example.fakecalling.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.example.fakecalling.R
import com.example.fakecalling.application.MyApplication
import com.example.fakecalling.databinding.ActivityWelcomeBinding
import com.example.fakecalling.utils.Prefs

class WelcomeActivity : LocalizationActivity() {
    private lateinit var binding:ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MyApplication.prefs!!.putBoolean("IS_OPENED",true)

        binding.btnContinue.setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, HowToUseActivity::class.java))
            finish()
        }

        styleText(binding.txtAppName,"Fake Video Call Prank")

    }

    private fun styleText(textView: TextView, inputString: String) {
        val spannableString = SpannableString(inputString)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#FF4738")), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(Color.WHITE), 5, inputString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannableString
    }
}