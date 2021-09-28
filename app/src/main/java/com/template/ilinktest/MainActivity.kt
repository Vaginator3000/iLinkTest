package com.template.ilinktest

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.squareup.picasso.Picasso
import com.template.ilinktest.apis.*
import com.template.ilinktest.apis.BASE_CAT_URL
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val gsonConverterFactory by lazy {
        GsonConverterFactory.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        backgroundAnimation()
        setOnClicks()
    }

    private fun backgroundAnimation() {
        val animDrawable = container.background as AnimationDrawable
        animDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(3000)
            start()
        }
    }

    private fun showOrHideDuckImgLoading(show: Boolean) {
        runOnUiThread {
            if (show)
                cpv_duck_loading.visibility = View.VISIBLE
            else
                cpv_duck_loading.visibility = View.GONE
        }
    }

    private fun showOrHideCatImgLoading(show: Boolean) {
        runOnUiThread {
            if (show)
                cpv_cat_loading.visibility = View.VISIBLE
            else
                cpv_cat_loading.visibility = View.GONE
        }
    }

    private fun makeCatApiRequest() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_CAT_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(CatApiRequest::class.java)

        showOrHideCatImgLoading(show = true)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getRandomCat()
                Log.e("MyLog", "1 " + response.toString())

                runOnUiThread {
                    Picasso.get().load(response.file)
                        .into(img_cat)
                }
            } catch (e: Exception) {
                Log.e("MyLog", e.message.toString())
            }
        }.invokeOnCompletion {
            showOrHideCatImgLoading(show = false)
        }
    }

    private fun makeDuckApiRequest() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_DUCK_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(DuckApiRequest::class.java)

        showOrHideDuckImgLoading(show = true)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getRandomDuck()
                Log.e("MyLog", "2 " + response.toString())

                runOnUiThread {
                    Picasso.get().load(response.url)
                        .into(img_duck)
                }
            } catch (e: Exception) {
                Log.e("MyLog", e.message.toString())
            }
        }.invokeOnCompletion {
            showOrHideDuckImgLoading(show = false)
        }
    }

    private fun setOnClicks() {
        btn_cat.setOnClickListener {
            img_cat.visibility = View.VISIBLE
            makeCatApiRequest()
        //    makeDogApiRequest()
        }

        btn_duck.setOnClickListener {
            img_duck.visibility = View.VISIBLE
            makeDuckApiRequest()
        }
    }


    // https://thatcopy.pw/catAPI/rest
}