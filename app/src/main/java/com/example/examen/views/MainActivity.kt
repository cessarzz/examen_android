package com.example.examen.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.examen.R
import com.example.examen.dataclass.UserResponse
import com.example.examen.rest.Repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callService()

        btn_feed.setOnClickListener {
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
        }
    }

    private fun callService() {
        val service = Repository.RetrofitRepository.getService()

        GlobalScope.launch (Dispatchers.IO) {
            val response =  service.getProfile()

            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful) {
                        val user : UserResponse?  = response.body()
                        if( user != null) {
                            Picasso.get().load(user.image).into(imv_perfil)
                            txv_name.text = "${user.name} ${user.lastname}"

                            txv_likes.text = user.social.likes.toString()
                            txv_posts.text = user.social.posts.toString()
                            txv_shares.text = user.social.shares.toString()
                            txv_friends.text = user.social.friends.toString()

                            txv_years.text = user.age
                            txv_email.text = user.email
                            txv_location.text = user.location
                            txv_occupation.text = user.occupation
                        }
                    }else{
                        Toast.makeText(this@MainActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@MainActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}