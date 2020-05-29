package com.bhagvada.covid19_india_tracker


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_district.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class District : AppCompatActivity() {

    lateinit var districtAdapter: DistrictAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_district)
//        setContentView(R.layout.district_item_header)
        districtList.addHeaderView(LayoutInflater.from(this).inflate(R.layout.district_item_header,districtList,false))
        val stateName = intent.getStringExtra("stateName")
        fetchDistrictResults(stateName)

    }

    private fun fetchDistrictResults(stateName:String) {
        GlobalScope.launch {
            val districtResponse = withContext(Dispatchers.IO) { Client.district_api.execute() }
            if (districtResponse.isSuccessful) {
                val jsonObject = JSONObject(districtResponse.body?.string())
                val data = DistrictDataModel().fromJson(jsonObject,  stateName).getDistrictWise()
                Log.i("info print data","$data")
                launch(Dispatchers.Main){
                    bindDistrictWiseData(data)
                }
            }
        }
    }

    private fun bindDistrictWiseData(subList: List<DistrictwiseItem>) {
        districtAdapter = DistrictAdapter(subList)
        districtList.adapter = districtAdapter
    }

    override fun onBackPressed() {
        startActivity(Intent(this,MainActivity::class.java))
    }
}

