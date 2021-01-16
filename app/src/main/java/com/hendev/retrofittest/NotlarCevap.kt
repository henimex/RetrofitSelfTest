package com.hendev.retrofittest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NotlarCevap(
    @SerializedName("notlar") @Expose var notlar: List<Notlar>,
    @SerializedName("success") @Expose var success: Int
) {
}
