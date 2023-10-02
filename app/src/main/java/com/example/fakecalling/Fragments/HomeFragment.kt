package com.example.fakecalling.Fragments

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fakecalling.activity.CallActivity
import com.example.fakecalling.activity.SetTimeActvity
import com.example.fakecalling.activity.ThemeselectionActivity
import com.example.fakecalling.Admanager.AppInterstitialAds
import com.example.fakecalling.Admanager.AppNativeAds

import com.example.fakecalling.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private  var img: String? =null
    private val pickImage = 100
    private val CONTACT_PERMISSION_CODE = 1
    private val CONTACT_PICK_CODE = 2

    private lateinit var binding : FragmentHomeBinding

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentHomeBinding.inflate(layoutInflater)
        AppNativeAds.inflateBigAds(requireContext(),binding.frameLayoutAds)

        binding.setTime.setOnClickListener {
            val name: String = binding.editname.getText().toString()
            val number:String=binding.editnum.getText().toString()
            requireActivity().run{
                val  intent= Intent(this, SetTimeActvity::class.java)
                intent.putExtra("image_url",img)
                intent.putExtra("name",name)
                intent.putExtra("number",number)
                startActivity(intent)
            }
        }


        binding.makeCall.setOnClickListener {
            val name: String = binding.editname.getText().toString()
            val number:String=binding.editnum.getText().toString()
            requireActivity().run{
                val  intent= Intent(this, CallActivity::class.java)
                intent.putExtra("image_url",img)
                intent.putExtra("name",name)
                intent.putExtra("number",number)
                startActivity(intent)
            }
        }
        binding.getContact.setOnClickListener {
            if (checkContactPermission()){
                //allowed
                pickContact()
            }
            else{
                //not allowed, request
                requestContactPermission()
            }
        }
        binding.selectTheme.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, ThemeselectionActivity::class.java))
            }
        }

        binding.Camera.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        return binding.root
    }

    private fun checkContactPermission(): Boolean{
        //check if permission was granted/allowed or not, returns true if granted/allowed, false if not
        return  ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestContactPermission() {
        //request the READ_CONTACTS permission
        val permission = arrayOf(android.Manifest.permission.READ_CONTACTS)
        ActivityCompat.requestPermissions(requireActivity(), permission, CONTACT_PERMISSION_CODE)
    }
    private fun pickContact(){
        //intent ti pick contact
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, CONTACT_PICK_CODE)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //handle permission request results || calls when user from Permission request dialog presses Allow or Deny
        if (requestCode == CONTACT_PERMISSION_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //permission granted, can pick contact
                pickContact()
            }
            else{
                //permission denied, cann't pick contact, just show message
                Toast.makeText(requireContext(), "Permission denied...", Toast.LENGTH_SHORT).show()
            }
        }
    }



    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var resolver = requireActivity().contentResolver
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            var imageUri = data?.data
            binding.gallaryImg.setImageURI(imageUri)
            binding.gallaryIcon.visibility=View.GONE

            img= imageUri.toString()




        }
        if (resultCode == RESULT_OK){
            //calls when user click a contact from contacts (intent) list
            if (requestCode == CONTACT_PICK_CODE){
                binding.editname.setText("")
                binding.editnum.setText("")
                val cursor1: Cursor
                val cursor2: Cursor?

                //get data from intent
                val uri = data!!.data
                cursor1 = resolver.query(uri!!, null, null, null, null)!!
                if (cursor1.moveToFirst()){
                    //get contact details
                    val contactId = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID))
                    val contactName = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val contactThumbnail = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI))
                    val idResults = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    val idResultHold = idResults.toInt()
                    binding.editname.setText(contactName)

                    if (idResultHold == 1){
                        cursor2 = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+contactId,
                            null,
                            null
                        )
                        while (cursor2!!.moveToNext()){
                            //get phone number
                            val contactNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            //set phone number
                            binding.editnum.setText(contactNumber)
                        }
                        cursor2.close()
                    }
                    cursor1.close()
                }
            }

        }
        else{
            //cancelled picking contact
            Toast.makeText(requireContext(), "some fields are not self selected", Toast.LENGTH_SHORT).show()
        }
    }
}