package com.livedata.sample.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.livedata.sample.R
import com.livedata.sample.databinding.FirstActivityBinding
import com.livedata.sample.viewmodel.FirstViewModel

class FirstActivity : AppCompatActivity() {
    //Declaring variables
    private lateinit var binding: FirstActivityBinding
    lateinit var viewModel: FirstViewModel

    //initiating Adapter
    val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_activity)
        binding = FirstActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initiating FirstViewModel
        viewModel = ViewModelProvider(this).get(FirstViewModel::class.java)

        //Next button click listener
        binding.nextBt.setOnClickListener {
            val secondActivity = Intent(this, SecondActivity::class.java)
            startActivity(secondActivity)
        }
        //setting adpter into recycler view
        binding.recyclerview.adapter = adapter

        //Swipe to refresh listener
        binding.swipe.setOnRefreshListener {
            binding.progress.visibility = View.VISIBLE
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