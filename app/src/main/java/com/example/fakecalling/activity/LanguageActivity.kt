package com.example.fakecalling.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.example.fakecalling.Admanager.AppNativeAds
import com.example.fakecalling.R
import com.example.fakecalling.application.MyApplication
import com.example.fakecalling.databinding.ActivityLanguageBinding
import com.example.fakecalling.interfaces.OnItemClickListener
import com.example.fakecalling.recyclerview.LanguageAdapter
import com.example.fakecalling.viewmodels.Languages

class LanguageActivity : LocalizationActivity() ,OnItemClickListener{
    private lateinit var binding: ActivityLanguageBinding
    private val items = mutableListOf(
        Languages(R.drawable.english_flag_icon, "English", "en"),
        Languages(R.drawable.arabic_flag, "Arabic", "ar"),
        Languages(R.drawable.philipino_flag, "Philipino", "fil"),
        Languages(R.drawable.french_flag, "French", "fr"),
        Languages(R.drawable.hindi_flag, "Hindi", "hi"),
        Languages(R.drawable.portuguese_flag, "Portuguese", "pt"),
        Languages(R.drawable.russian_flag, "Russian", "ru"),
        Languages(R.drawable.turkey_flag, "Turkish", "tr"),
        Languages(R.drawable.pakistan_flag, "Urdu", "ur"),

        )
    private lateinit var adapter: LanguageAdapter
    private var selectedItemPosition: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppNativeAds.inflateBigAds(this,binding.framelayoutAds)

        val savedLanguageCode = MyApplication.prefs!!.getString("selected_language", "en")
        val savedLanguagePosition = items.indexOfFirst { it.languageCode == savedLanguageCode }

        selectedItemPosition = if (savedLanguagePosition != -1) savedLanguagePosition else 0

        binding.done.setOnClickListener {
            val selectedLanguageCode = items[selectedItemPosition].languageCode
            if(!MyApplication.prefs!!.getBoolean("IS_OPENED",false)) {
                Log.d("language_checked", "gold activity is not opened ")
                setLanguage(selectedLanguageCode)
                recreate()
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }else{
                // Set the selected language using LocalizationActivityDelegate
                setLanguage(selectedLanguageCode)
                Log.d("language_checked", "gold activity already opened ")
                // Recreate the current activity to apply the language change
                recreate()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.languagesRecycler.layoutManager = LinearLayoutManager(this)

        adapter = LanguageAdapter(items, this, selectedItemPosition) // Pass selectedItemPosition
        binding.languagesRecycler.adapter = adapter

    }

    override fun onItemClick(position: Int) {
        if (position != selectedItemPosition) {
            val previousSelectedItemPosition = selectedItemPosition
            selectedItemPosition = position
            adapter.setSelectedItemPosition(selectedItemPosition)
            adapter.notifyItemChanged(previousSelectedItemPosition)
            adapter.notifyItemChanged(selectedItemPosition)

            // Save the selected language code in SharedPreferences
            val selectedLanguageCode = items[selectedItemPosition].languageCode
            MyApplication.prefs!!.putString("selected_language", selectedLanguageCode)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}