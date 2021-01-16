package com.hendev.retrofittest

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detailed.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Detailed : AppCompatActivity() {

    private lateinit var not: Notlar
    private lateinit var ndi:NotlarDaoInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        toolbarDetay3.title = "Not Detayları"
        setSupportActionBar(toolbarDetay3)

        ndi = ApiUtiles.getNotlarDaoInterface()
        not = intent.getSerializableExtra("tablo") as Notlar

        txtDetayDersAdi3.text = not.ders_adi
        edtDetayEx4.setText((not.not1).toString())
        edtDetayEx5.setText((not.not2).toString())
        txtDetayOrtalama3.text = "Ders Ortalamanız : ${(not.not1 + not.not2) / 2} ."

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sil -> {
                Snackbar.make(toolbarDetay3, "Kayıt Silinsinmi", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.BLACK)
                    .setTextColor(Color.WHITE)
                    .setActionTextColor(Color.RED)
                    .setAction("EVET") {
                        //PHP DELETE
                        ndi.notSil(not.not_id).enqueue(object : Callback<CRUDCevap>{
                            override fun onResponse(call: Call<CRUDCevap>?,response: Response<CRUDCevap>?) {
                                if (response != null){
                                    Toast.makeText(applicationContext,"Kayıt Silindi",Toast.LENGTH_SHORT).show()
                                    Log.e("Kayıt Silindi", response.body().success.toString())
                                    startActivity(Intent(this@Detailed, MainActivity::class.java))
                                    finish()
                                }
                            }

                            override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {
                                val hata = Log.e("!!", t.toString())
                                Toast.makeText(this@Detailed, "Hata Alındı : $hata", Toast.LENGTH_LONG).show()
                            }
                        })

                    }.show()
                return true
            }

            R.id.action_duzenle -> {
                val id = not.not_id
                val ders_adi = txtDetayDersAdi3.text.toString()
                val not1 = edtDetayEx4.text.toString().trim()
                val not2 = edtDetayEx5.text.toString().trim()

                if (TextUtils.isEmpty(not1)) {
                    Snackbar.make(toolbarDetay3, "1. Notu Giriniz", Snackbar.LENGTH_SHORT).show()
                    return false
                }
                if (TextUtils.isEmpty(not2)) {
                    Snackbar.make(toolbarDetay3, "2. Notu Giriniz", Snackbar.LENGTH_SHORT).show()
                    return false
                }
                //PHP UPDATE
                ndi.notGuncelle(id,ders_adi,not1.toInt(),not2.toInt()).enqueue(object : Callback<CRUDCevap>{
                    override fun onResponse(call: Call<CRUDCevap>?,response: Response<CRUDCevap>?) {
                        if (response != null){
                            Toast.makeText(applicationContext,"Kayıt Güncellendi",Toast.LENGTH_SHORT).show()
                            Log.e("Kayıt Güncellendi", response.body().success.toString())
                            startActivity(Intent(this@Detailed,MainActivity::class.java))
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {
                        val hata = Log.e("!!", t.toString())
                        Toast.makeText(this@Detailed, "Hata Alındı : $hata", Toast.LENGTH_LONG).show()
                    }
                })
                return true
            }
            else -> return false
        }
    }
}