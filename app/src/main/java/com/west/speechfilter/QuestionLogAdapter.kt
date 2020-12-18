package com.west.speechfilter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_data.view.*


class QuestionLogAdapter(private val questionLogList: ArrayList<DataModel>) : RecyclerView.Adapter<QuestionLogAdapter.MsgLogViewHolder>() {

    class MsgLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvQuestion: TextView = itemView.tvQuestion
        val tvAnswer: TextView = itemView.tvAnswer
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgLogViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return MsgLogViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MsgLogViewHolder, position: Int) {
        val data = questionLogList[position]
        holder.tvQuestion.text =data.question
        holder.tvAnswer.text =data.answer
    }
    fun updateData( output:DataModel){
        questionLogList.add(output)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = questionLogList.size
}