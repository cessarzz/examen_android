package com.example.examen.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen.R
import com.example.examen.adapters.FeedAdapter
import com.example.examen.dataclass.PostResponse
import com.example.examen.rest.Repository
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FeedActivity : AppCompatActivity(), FeedAdapter.ViewHolderDatos.OnAdapterListener {

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Feed"

        adapter = FeedAdapter(ArrayList(), this)
        layoutManager = LinearLayoutManager(this@FeedActivity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        progressBar.isIndeterminate = true
        progressBar.animate()

        callService()
    }

    private fun callService() {
        val service = Repository.RetrofitRepository.getService()

        GlobalScope.launch (Dispatchers.IO) {
            val response =  service.getPosts()

            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful) {
                        val listPosts : List<PostResponse>? = response.body()
                        if( listPosts != null) {
                            progressBar.invalidate()
                            progressBar.visibility = GONE
                            adapter.updateList(listPosts)
                        }
                    }else{
                        Toast.makeText(this@FeedActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@FeedActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onItemClickListener(item: PostResponse) {
        val intent = Intent(this, CommentActivity::class.java)
        intent.putExtra(CommentActivity.KEY_POST, Gson().toJson(item, PostResponse::class.java))
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}