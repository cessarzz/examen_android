package com.example.examen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examen.R
import com.example.examen.dataclass.UserResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class FriendsAdapter(private var listaItems:List<UserResponse>, private val listener: ViewHolderDatos.OnAdapterListener) : RecyclerView.Adapter<FriendsAdapter.ViewHolderDatos>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDatos {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolderDatos(vista)
    }

    override fun getItemCount(): Int {
        return listaItems.size
    }

    override fun onBindViewHolder(holder: ViewHolderDatos, position: Int) {
        val user = this.listaItems[position]
        holder.bind(user)
        holder.itemView.setOnClickListener{listener.onItemClickListener(user)}
    }

    fun updateList(list:List<UserResponse>) {
        this.listaItems = list
        this.notifyDataSetChanged()
    }

    class ViewHolderDatos(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind (user: UserResponse) {
            if(user.image.isNotBlank()) {
                Picasso.get().load(user.image).into(view.imv_item_userImage)
            }
            view.txv_body.text  = "${user.name} ${user.lastname}"
        }

        interface OnAdapterListener {
            fun onItemClickListener( item: UserResponse)
        }

    }
}