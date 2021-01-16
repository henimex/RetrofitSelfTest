package com.hendev.retrofittest

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NotlarAdapter
    private lateinit var ndi: NotlarDaoInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "   Notlar Ver 3"
        toolbar.setLogo(R.drawable.grade)

        setSupportActionBar(toolbar)

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)
        ndi = ApiUtiles.getNotlarDaoInterface()

        notlariListele()

        fabRefresh.isVisible = false

        fabBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, KayitActivity::class.java))
        }

        fabRefresh.setOnClickListener {
            startActivity(Intent(this@MainActivity, MainActivity::class.java))
            finish()
        }
    }

    fun tumNotlar() {
        val kdi = ApiUtiles.getNotlarDaoInterface()
        kdi.tumNotlar().enqueue(object : Callback<NotlarCevap> {
            override fun onResponse(call: Call<NotlarCevap>?, response: Response<NotlarCevap>?) {
                if (response != null) {
                    val notlarListe = response.body().notlar
                    for (n in notlarListe) {
                        Log.e("*********", "*********")
                        Log.e("Not ID", n.not_id.toString())
                        Log.e("Ders Ad", n.ders_adi)
                        Log.e("Not 1", n.not1.toString())
                        Log.e("Not 2", n.not2.toString())
                    }
                }
            }

            override fun onFailure(call: Call<NotlarCevap>?, t: Throwable?) {
                Log.e("Hata", t.toString())
            }
        })
    }

    fun notlariListele() {
        //PHP SELECT ALL
        ndi.tumNotlar().enqueue(object : Callback<NotlarCevap> {
            override fun onResponse(call: Call<NotlarCevap>?, response: Response<NotlarCevap>?) {
                if (response != null) {
                    val liste = response.body().notlar
                    adapter = NotlarAdapter(this@MainActivity, liste)
                    rv.adapter = adapter

                    var toplam = 0
                    for (i in liste) {
                        toplam = toplam + (i.not1 + i.not2) / 2
                    }
                    toolbar.subtitle = "   Ortalama: ${toplam / liste.size}  [CC by HenimeX]"
                    progressBar.isInvisible = true
                }
            }

            override fun onFailure(call: Call<NotlarCevap>?, t: Throwable?) {
                val hata = Log.e("!!", t.toString())
                Toast.makeText(this@MainActivity, "Hata Alındı : $hata", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}