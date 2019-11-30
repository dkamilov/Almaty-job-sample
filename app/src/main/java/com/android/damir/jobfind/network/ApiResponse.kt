package com.android.damir.jobfind.network

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("items")
    val items: List<HHVacancy>
)

data class HHVacancy(
    @SerializedName("name")
    val name: String?,
    @SerializedName("area")
    val area: Area?,
    @SerializedName("salary")
    val salary: Salary?,
    @SerializedName("address")
    val address: Address?,
    @SerializedName("employer")
    val employer: Employer?
)

data class Area(
    @SerializedName("name")
    val name: String
)

data class Salary(
    @SerializedName("from")
    val from: String?,
    @SerializedName("to")
    val to: String?,
    @SerializedName("currency")
    val currency: String?
)

data class Address(
    @SerializedName("raw")
    val raw: String?
)

data class Employer(
    @SerializedName("name")
    val name: String?
)