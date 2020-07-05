package com.example.examen.views

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen.R
import com.example.examen.adapters.FriendsAdapter
import com.example.examen.dataclass.PostResponse
import com.example.examen.dataclass.UserResponse
import com.example.examen.rest.Repository
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FriendsActivity : AppCompatActivity(), FriendsAdapter.ViewHolderDatos.OnAdapterListener {

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: FriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        adapter = FriendsAdapter(ArrayList(), this)
        layoutManager = LinearLayoutManager(this@FriendsActivity)
        recyclerView_friends.layoutManager = layoutManager
        recyclerView_friends.adapter = adapter

        progressBar.isIndeterminate = true
        progressBar.animate()

        callService()
    }

    private fun callService() {
        val service = Repository.RetrofitRepository.getService()

        GlobalScope.launch (Dispatchers.IO) {
            val response =  service.getUsers()

            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful) {
                        val listUsers : List<UserResponse>? = response.body()
                        if( listUsers != null) {
                            progressBar.invalidate()
                            progressBar.visibility = View.GONE
                            adapter.updateList(listUsers)
                        }
                    }else{
                        Toast.makeText(this@FriendsActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@FriendsActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onItemClickListener(item: UserResponse) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+item.phone))
        startActivity(intent)
    }
}