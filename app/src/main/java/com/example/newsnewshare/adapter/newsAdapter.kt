package com.example.newsnewshare.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsnewshare.R
import com.example.newsnewshare.model.NewsModel
import com.example.newsnewshare.ui.activity.UpdatenewsActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class newsAdapter(
    val context: android.content.Context, var data: ArrayList<NewsModel>
) : RecyclerView.Adapter<newsAdapter.newsViewHolder>() {

    class newsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //sample news
        val nName: TextView = itemView.findViewById(R.id.newsname)
//        val ccName: TextView = itemView.findViewById(R.id.categorynametexti)
        val ndesc: TextView = itemView.findViewById(R.id.newsdesc)
        val editbttn: TextView = itemView.findViewById(R.id.lbllEdit)
        //spinner
        val spinner: TextView = itemView.findViewById(R.id.spinnertext)
        val loading : ProgressBar = itemView.findViewById(R.id.progressBar6)


        //
        val imageView: ImageView = itemView.findViewById(R.id.outputimage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
        //sample
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.sample_news, parent, false)
        return newsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: newsAdapter.newsViewHolder, position: Int) {


        holder.nName.text = data[position].newsNamee
        holder.spinner.text = data[position].categoryNamme
        holder.ndesc.text = data[position].descrription
        //spinner
        //holder.categ.
        holder.loading.visibility = View.GONE

        if (data[position].imageUrl.isEmpty()) {
            Picasso.get()
                .load(R.drawable.iimage).into(holder.imageView, object : Callback {
                    override fun onSuccess() {
//                holder.loading.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {

                    }

                })
        }else{
            Picasso.get()
                .load(data[position].imageUrl).into(holder.imageView, object : Callback {
                    override fun onSuccess() {
//                holder.loading.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {

                    }

                })
        }


        holder.editbttn.setOnClickListener {
            var intent = Intent(context, UpdatenewsActivity::class.java)
            intent.putExtra("newsidd", data[position].newsidd)
            context.startActivity(intent)
        }
    }


    fun updateData(news: List<NewsModel>) {
        data.clear()
        data.addAll(news)
        notifyDataSetChanged()
    }
    //delete ko lagi

    fun getnewsId(position: Int): String {
        return data[position].newsidd
    }
}