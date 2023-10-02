package com.example.fakecalling.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakecalling.RoomDatabase.HistoryDatabases
import com.example.fakecalling.databinding.FragmentHistoryBinding

import com.example.fakecalling.recyclerview.DataAdapter
import com.example.fakecalling.viewmodels.HistoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HistoryFragment : Fragment() {
    lateinit var database: HistoryDatabases
    private val binding by lazy {
       FragmentHistoryBinding.inflate(layoutInflater)
    }
    val viewModel by lazy {
        ViewModelProvider(this)[HistoryViewModel::class.java]
    }
    private lateinit var dataAdapter: DataAdapter
    val historyList=ArrayList<Any>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {



        lifecycleScope.launch(Dispatchers.Default){

        }
        viewModel.list.observe(viewLifecycleOwner){list->
            historyList.clear()
            if(list.isNotEmpty()){
                historyList.clear()
                historyList.addAll(list)
                if(historyList.size>3){
                    var i=3
                    while (i<historyList.size){
                        historyList.add(i,"abc")
                        i+=7
                    }

                }




            }
            dataAdapter= DataAdapter(requireContext(),historyList){
                viewModel.delete(it)
            }
            binding.recyclerView
                .apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    hasFixedSize()
                    adapter=dataAdapter
                }
        }

        viewModel.getHistoryData()




        return binding.root
    }
//    private fun getMockData(): List<DataModel> = listOf(
//        DataModel.history(
//            profile = R.drawable.profile,
//            name = "noman",
//            number = "03085756763",
//            call = R.drawable.call,
//            clear = R.drawable.delete_file
//
//        ),
//        DataModel.history(
//            profile = R.drawable.profile,
//            name = "muhammmad ali",
//            number = "03405995658",
//            call = R.drawable.call,
//            clear = R.drawable.delete_file
//
//        ),
//        DataModel.add(
//            ad_text = "Ad 1"
//        ),
//        DataModel.history(
//            profile = R.drawable.profile,
//            name = "Haider ali",
//            number = "030274747737",
//            call = R.drawable.call,
//            clear = R.drawable.delete_file
//
//        ),
//        DataModel.history(
//            profile = R.drawable.profile,
//            name = "subhan khan",
//            number = "0303747477474",
//            call = R.drawable.call,
//            clear = R.drawable.delete_file
//
//        ),
//        DataModel.add(
//            ad_text = "Ad 2"
//        ),
//
//        )


}
