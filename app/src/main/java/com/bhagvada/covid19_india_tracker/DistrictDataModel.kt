package com.bhagvada.covid19_india_tracker


import android.util.Log
import org.json.JSONException
import org.json.JSONObject

class DistrictDataModel {
    private var districtwise = ArrayList<DistrictwiseItem>()

    fun fromJson(jsonObject : JSONObject,stateName: String):DistrictDataModel{

        val districtData = DistrictDataModel()

        val iter = jsonObject.getJSONObject(stateName).getJSONObject("districtData").keys()
        val access = jsonObject.getJSONObject(stateName).getJSONObject("districtData")

        for (district in iter){
            val dataSource  = access.getJSONObject(district)

            districtData.districtwise.add(
                DistrictwiseItem
                    (district,
                    dataSource.getInt("confirmed"),
                    dataSource.getInt("active"),
                    dataSource.getInt("deceased"),
                    dataSource.getInt("recovered")))

//          Log.i("info" , "${districtData.districtwise}\n")
        }

        Log.i("info" , "${districtData.districtwise.size}\n")
        Log.i("info" , "${districtData.districtwise[0]}\n")
        return districtData
    }

    fun getDistrictWise() = this.districtwise

}



data class DistrictwiseItem(
    var districtName:String ?=null,
    var confirmed: Int? = null,
    var active: Int? = null,
    var deceased: Int? = null,
    var recovered: Int? = null
)
