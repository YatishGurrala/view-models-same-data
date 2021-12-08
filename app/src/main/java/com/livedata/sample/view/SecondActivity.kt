package com.livedata.sample.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.livedata.sample.R
import com.livedata.sample.databinding.SecondActivityBinding
import com.livedata.sample.viewmodel.SecondViewModel

class SecondActivity : AppCompatActivity() {
    //Declaring variables
    private lateinit var binding: SecondActivityBinding
    lateinit var viewModel: SecondViewModel


    //initiating Adapter
    val adapter = SecondAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        binding = SecondActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initiating SecondViewModel
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)

        //Next button click listener
        binding.backBt.setOnClickListener {
            finish()
        }
        //setting adpter into recycler view
        binding.recyclerview.adapter = adapter

        //Swipe to refresh listener
        binding.swipe.setOnRefreshListener {
            //Make the progress visibile before calling the API
            binding.progress.visibility = View.VISIBLE

            //Call API flow thorugh viewmodel
            viewModel.getAllUsers()
            binding.swipe.isRefreshing = false
        }

        //this call back will get call when  live data is updated from viewmodel
        viewModel.userList.observe(this, Observer {
            adapter.setuserList(it)
            binding.progress.visibility = View.GONE
        })

        //this call back will get call when the API is get failure
        viewModel.errorMessage.observe(this, Observer {

        })

        //Make the progress visibile before calling the API
        binding.progress.visibility = View.VISIBLE
        //Call API flow thorugh viewmodel
        viewModel.getAllUsers()
    }
}