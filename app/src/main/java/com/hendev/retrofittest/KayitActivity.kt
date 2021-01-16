package com.hendev.retrofittest

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_kayit.*
import kotlinx.android.synthetic.main.activity_kayit.btnSave
import kotlinx.android.synthetic.main.activity_kayit.edtEx1
import kotlinx.android.synthetic.main.activity_kayit.edtEx2
import kotlinx.android.synthetic.main.activity_kayit.edtKayitDersAdi
import kotlinx.android.synthetic.main.activity_kayit.toolbarNotKayit
import kotlinx.android.synthetic.main.activity_not_kayit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KayitActivity : AppCompatActivity() {

    private lateinit var ndi:NotlarDaoInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kayit)

        toolbarNotKayit.title = "Ders ve Not Kaydı"
        setSupportActionBar(toolbarNotKayit)

        btnSave.setOnClickListener {
            val ders_adi = edtKayitDersAdi.text.toString().trim()
            val not1 = edtEx1.text.toString().trim()
            val not2 = edtEx2.text.toString().trim()
            ndi = ApiUtiles.getNotlarDaoInterface()

            if (TextUtils.isEmpty(ders_adi)){
                Snackbar.make(toolbarNotKayit,"Ders Adını Giriniz", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(not1)){
                Snackbar.make(toolbarNotKayit,"Ders Adını Giriniz", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(not2)){
                Snackbar.make(toolbarNotKayit,"Ders Adını Giriniz", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //PHP INSERT
            ndi.notEkle(ders_adi,not1.toInt(),not2.toInt()).enqueue(object : Callback<CRUDCevap>{
                override fun onResponse(call: Call<CRUDCevap>?, response: Response<CRUDCevap>?) {
                    if (response!= null){
                        if (response.body().success == 1){
                            Toast.makeText(applicationContext,"Ekleme Başarılı",Toast.LENGTH_SHORT).show()
                            Log.e("Ekleme Başarılı", response.body().success.toString())
                        }
                    }

                }

                override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {
                    val hata = Log.e("!!", t.toString())
                    Toast.makeText(this@KayitActivity, "Hata Alındı : $hata", Toast.LENGTH_LONG).show()
                }

            })

            startActivity(Intent(this@KayitActivity,MainActivity::class.java))
            finish()
        }
    }
}