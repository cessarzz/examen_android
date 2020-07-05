package com.example.examen.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen.R
import com.example.examen.adapters.CommentAdapter
import com.example.examen.dataclass.Comment
import com.example.examen.dataclass.PostResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : AppCompatActivity(), CommentAdapter.ViewHolderDatos.OnAdapterListener {

    companion object {
        const val KEY_POST = "post"
    }

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val post: PostResponse = Gson().fromJson(intent.getStringExtra(KEY_POST), PostResponse::class.java)
        val listComments = post.comment

        adapter = CommentAdapter(listComments, this)
        layoutManager = LinearLayoutManager(this@CommentActivity)
        recyclerView_comment.layoutManager = layoutManager
        recyclerView_comment.adapter = adapter

    }

    override fun onItemClickListener(item: Comment) {
        Toast.makeText(this, "Comentario de ${item.user_name}", Toast.LENGTH_LONG).show()
    }
}