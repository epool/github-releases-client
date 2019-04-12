package dev.epool.githubreleases

import dev.epool.githubreleases.models.Asset
import dev.epool.githubreleases.models.Release
import dev.epool.githubreleases.models.ReleaseInfo
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface GithubApi {

    @GET("releases")
    fun getReleases(@Query("page") page: Int, @Query("per_page") perPage: Int): Call<List<Release>>

    @POST("releases")
    fun createRelease(@Body releaseInfo: ReleaseInfo): Call<Release>

    @PATCH("releases/{release_id}")
    fun updateRelease(@Path("release_id") releaseId: Long, @Body releaseInfo: ReleaseInfo): Call<Release>

    @DELETE("releases/{release_id}")
    fun deleteRelease(@Path("release_id") releaseId: Long): Call<Unit>

    @DELETE("git/refs/tags/{tag_name}")
    fun deleteTag(@Path("tag_name") tagName: String): Call<Unit>

    @POST
    fun uploadAsset(
        @Url uploadUrl: String,
        @Body file: RequestBody,
        @Query("name") name: String,
        @Query("label") label: String? = null
    ): Call<Asset>

}