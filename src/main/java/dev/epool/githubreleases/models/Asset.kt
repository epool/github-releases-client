package dev.epool.githubreleases.models

import com.google.gson.annotations.SerializedName

data class Asset(
    @SerializedName("browser_download_url")
    val browserDownloadUrl: String,
    @SerializedName("content_type")
    val contentType: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("download_count")
    val downloadCount: Int,
    @SerializedName("id")
    val id: Long,
    @SerializedName("label")
    val label: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("size")
    val size: Int,
    @SerializedName("state")
    val state: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("uploader")
    val uploader: Author,
    @SerializedName("url")
    val url: String
)