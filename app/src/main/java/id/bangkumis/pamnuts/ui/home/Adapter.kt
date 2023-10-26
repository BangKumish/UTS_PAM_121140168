package id.bangkumis.pamnuts.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.bangkumis.pamnuts.R

class MyAdapter(private var list:ArrayList<Data>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var onItemClick: ((Data) -> Unit)?= null

    @SuppressLint("NotifyDataSetChanged")
    fun selfFIlteredList(list: ArrayList<Data>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.tvHeading.text = currentItem.username
        holder.tvEmail.text = currentItem.email

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item , parent , false)
        return MyViewHolder(itemView)
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvHeading : TextView = itemView.findViewById(R.id.titleImage)
        val tvEmail : TextView = itemView.findViewById(R.id.textEmail)
    }
}
