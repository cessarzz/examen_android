package com.example.examen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examen.R
import com.example.examen.dataclass.PostResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_feed.view.*

class FeedAdapter(private var listaItems:List<PostResponse>, private val listener: ViewHolderDatos.OnAdapterListener) : RecyclerView.Adapter<FeedAdapter.ViewHolderDatos>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDatos {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        return ViewHolderDatos(vista)
    }

    override fun getItemCount(): Int {
        return listaItems.size
    }

    override fun onBindViewHolder(holder: ViewHolderDatos, position: Int) {
        val post = this.listaItems[position]
        holder.bind(post)
        holder.itemView.item_comments.setOnClickListener{listener.onItemClickListener(post)}
    }

    fun updateList(list:List<PostResponse>) {
        this.listaItems = list
        this.notifyDataSetChanged()
    }

    class ViewHolderDatos(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind (post: PostResponse) {
            if(post.user_image.isNotBlank()) {
                Picasso.get().load(post.user_image).into(view.imv_item_userImage)
            }
            view.txv_item_userName.text  = post.username
            if(post.image.isNotBlank()) {
                Picasso.get().load(post.image).into(view.imv_item_postImage)
            }
            view.txv_item_body.text  = post.body
            view.txv_item_likes.text  = post.likes.toString()
        }

        interface OnAdapterListener {
            fun onItemClickListener( item: PostResponse)
        }

    }
}