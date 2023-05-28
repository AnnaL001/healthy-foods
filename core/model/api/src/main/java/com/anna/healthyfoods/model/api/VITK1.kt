package com.anna.healthyfoods.model.api

import com.google.gson.annotations.SerializedName


data class VITK1 (

  @SerializedName("label"    ) var label    : String? = null,
  @SerializedName("quantity" ) var quantity : Double? = null,
  @SerializedName("unit"     ) var unit     : String? = null

)