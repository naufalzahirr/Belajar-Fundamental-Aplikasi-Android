package com.example.submissionaplikasigithubusernaufalzahirrizqullah

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String? = null,
    var location: String? = null,
    var avatar : Int? = null,
    var username: String? = null,
    var repository: String? = null,
    var company : String? = null,
    var followers: String? = null,
    var following: String? = null
) : Parcelable