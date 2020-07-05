package com.example.examen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examen.R
import com.example.examen.dataclass.Comment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter(private var listaItems:List<Comment>, private val listener: ViewHolderDatos.OnAdapterListener) : RecyclerView.Adapter<CommentAdapter.ViewHolderDatos>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDatos {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return ViewHolderDatos(vista)
    }

    override fun getItemCount(): Int {
        return listaItems.size
    }

    override fun onBindViewHolder(holder: ViewHolderDatos, position: Int) {
        val comment = this.listaItems[position]
        holder.bind(comment)
        holder.itemView.setOnClickListener{listener.onItemClickListener(comment)}
    }

    fun updateList(list:List<Comment>) {
        this.listaItems = list
        this.notifyDataSetChanged()
    }

    class ViewHolderDatos(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind (comment: Comment) {
            if(comment.user_image.isNotBlank()) {
                Picasso.get().load(comment.user_image).into(view.imv_item_userImage)
            }
            view.txv_comment_body.text  = comment.comment
        }

        interface OnAdapterListener {
            fun onItemClickListener( item: Comment)
        }

    }
}