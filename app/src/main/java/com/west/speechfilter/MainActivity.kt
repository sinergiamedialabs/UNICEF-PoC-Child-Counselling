package com.west.speechfilter

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.xdrop.fuzzywuzzy.FuzzySearch
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE_STT = 1
    }

    var datalist = ArrayList<DataModel>()
    var questions = ArrayList<String>()
    private lateinit var questionLogAdapter:QuestionLogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvData.layoutManager = LinearLayoutManager(this)
        rvData.setHasFixedSize(true)
        questionLogAdapter=QuestionLogAdapter(ArrayList())
        rvData.adapter =questionLogAdapter
        datalist = AppApplication.datalist
        datalist.forEach {
            questions.add(it.question)
        }

        button.setOnClickListener {
            val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            sttIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")

            try {
                startActivityForResult(sttIntent, REQUEST_CODE_STT)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Your device does not support STT.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_STT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    result?.let { it ->
                        val recognizedText = it[0]
                        var datObject = DataModel(recognizedText, "")
                        val list = FuzzySearch.extractTop(recognizedText, questions, 1, 65)
                        datObject = if (list != null&&list.isNotEmpty()) {
                            val output = datalist.firstOrNull(){ model ->
                                model.question.contains(
                                    list[0].string,
                                    false
                                )
                            }
                            if (output != null) {
                                DataModel(recognizedText, output.answer)
                            }else{
                                DataModel(recognizedText, "No data found")
                            }
                        }else{
                            DataModel(recognizedText, "No data found")
                        }
                        questionLogAdapter.updateData(datObject)

                    }
                }
            }
        }
    }
}