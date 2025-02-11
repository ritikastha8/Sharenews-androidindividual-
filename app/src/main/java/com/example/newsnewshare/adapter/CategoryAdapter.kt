package com.example.newsnewshare.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsnewshare.R
import com.example.newsnewshare.model.CategoryModel
import com.example.newsnewshare.ui.activity.UpdateActivity

class CategoryAdapter (val context: Context,var data:ArrayList<CategoryModel>
):RecyclerView.Adapter<CategoryAdapter.categoryviewHolder>()
{
    class categoryviewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView){
        val cId: TextView = itemView.findViewById(R.id.categoryidtext)
        val cName: TextView = itemView.findViewById(R.id.categorynametext)
        val editbtn: TextView = itemView.findViewById(R.id.textedit)



    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):categoryviewHolder {
        val itemView : View = LayoutInflater.from(context).inflate(R.layout.sample_insiderecyclerview_dashboardactivity,parent,false)
        return categoryviewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return data.size
    }



    override fun onBindViewHolder(holder: CategoryAdapter.categoryviewHolder, position: Int) {

        holder.cId.text=data[position].categoryId.toString()
        holder.cName.text=data[position].categoryNamee
        holder.editbtn.setOnClickListener{
            var intent = Intent(context,UpdateActivity::class.java)
            intent.putExtra("categoryIddd",data[position].categoryIddd)
            context.startActivity(intent)
        }

    }
    fun updateData(categories:List<CategoryModel>){
        data.clear()
        data.addAll(categories)
        notifyDataSetChanged()
    }



    fun getcategoryId(position: Int):String{
        return data[position].categoryIddd
    }
}