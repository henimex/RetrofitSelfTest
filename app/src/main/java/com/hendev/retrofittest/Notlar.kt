package com.hendev.retrofittest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Notlar(
    @SerializedName("not_id") @Expose var not_id: Int,
    @SerializedName("ders_adi") @Expose var ders_adi: String,
    @SerializedName("not1") @Expose var not1: Int,
    @SerializedName("not2") @Expose var not2: Int
) : Serializable {
}