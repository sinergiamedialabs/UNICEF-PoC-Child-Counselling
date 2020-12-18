package com.west.speechfilter

import android.app.Application
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppApplication : Application() {

    companion object {
        var datalist = ArrayList<DataModel>()
        var instance: AppApplication? = null
    }
    override fun onCreate() {
        super.onCreate()
        instance=this
        var datObject = DataModel("I am always sad", "Dear kid, please try to spend more time with your parents and talk with them")
        var datObject1 = DataModel("I enjoy sitting alone", "Try to go outside sometimes and interact with friends")
        var datObject2 = DataModel("Why nobody smiling at me", "It's just your imagination.Don't think everyone hates you")

        datalist.add(datObject)
        datalist.add(datObject1)

        datalist.add(datObject2)
        GlobalScope.launch {
            AppDatabase.getDatabase(applicationContext)!!.dataDao().insert(datalist)
        }
    }

}