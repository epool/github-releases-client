package dev.epool.githubreleases.models

import com.google.gson.annotations.SerializedName

data class ReleaseInfo(
    @SerializedName("body")
    val body: String,
    @SerializedName("draft")
    val draft: Boolean = false,
    @SerializedName("name")
    val name: String,
    @SerializedName("prerelease")
    val preRelease: Boolean,
    @SerializedName("tag_name")
    val tagName: String,
    @SerializedName("target_commitish")
    val targetCommitish: String = "master"
)