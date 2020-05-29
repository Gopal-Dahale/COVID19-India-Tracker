package com.bhagvada.covid19_india_tracker

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.bhagvada.covid19_india_tracker.StatewiseItem as StateWiseItem


class MainActivity : AppCompatActivity() {

    lateinit var stateAdapter: StateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var net = hasNetworkAvailable(this)
        if(!net){
            val intent = Intent(this,CheckConnection::class.java)
            startActivity(intent)

        }
        else{
            list.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header,list,false))
            fetchResults()
        }



        list.setOnItemClickListener { parent, view, position, id ->
            val myIntent = Intent(this,District::class.java)
            myIntent.putExtra("stateName" , view.stateTv.text)    // position starts from 1
            startActivity(myIntent)
        }

        more.setOnClickListener {
            val myIntent = Intent(this,AboutMe::class.java)
            startActivity(myIntent)
        }
        map.setOnClickListener{
            startActivity(Intent(this,IndiaMap::class.java))
        }
    }

    private fun hasNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        Log.i("info", "hasNetworkAvailable: ${(network != null)}")
        return (network != null)
    }

    private fun fetchResults(){

//        val states = ArrayList<String>()

        GlobalScope.launch {

            val response = withContext(Dispatchers.IO) {
                Client.api.execute()
            }

            if(response.isSuccessful){
                //      Log.i("info",response.body?.string())

                val data = Gson().fromJson(response.body?.string(), Response::class.java)


                launch(Dispatchers.Main){
                    bindCombineData(data.statewise[0])
                    bindStateWiseData(data.statewise.subList(1,data.statewise.size ))
                }

            }
            else{
                Log.i("info", "not connected")
            }

        }

    }


    private fun bindStateWiseData(subList: List<StateWiseItem>) {
        stateAdapter = StateAdapter(subList)
        list.adapter = stateAdapter
    }

    private fun bindCombineData(get: StateWiseItem) {
        val lastUpdatedTime = get.lastupdatedtime
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        lastUpdatedTv.text = "Last Updated\n ${getTimeAgo(simpleDateFormat.parse(lastUpdatedTime))}"

        confirmedTv.text = get.confirmed
        recoveredTv.text = get.recovered
        activeTv.text = get.active
        deathsTv.text = get.deaths

    }
    fun getTimeAgo(past: Date):String {
        val now = Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time -past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time -past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time -past.time)

        return when{
            seconds < 60 -> {
                "$seconds seconds ago"
            }
            minutes < 60 -> {
                "$minutes minutes ago"
            }
            hours < 24 -> {
                "$hours hour ${minutes%60} min ago"
            }
            else ->{
                SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(past).toString()
            }
        }

    }
}
