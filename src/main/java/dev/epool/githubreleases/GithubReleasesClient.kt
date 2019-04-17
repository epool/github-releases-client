package dev.epool.githubreleases

import dev.epool.githubreleases.models.Release
import dev.epool.githubreleases.models.ReleaseInfo
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

class GithubReleasesClient(api: GithubApi) : GithubApi by api {

    companion object {

        @JvmStatic
        fun newInstance(owner: String, repo: String, token: String): GithubReleasesClient =
            GithubReleasesClient(createApi(owner, repo, token))

    }

    @JvmOverloads
    fun createRelease(
        releaseName: String,
        tagName: String,
        description: String,
        isPreRelease: Boolean = true,
        branch: String = "master",
        vararg assets: File
    ): Release {
        val releaseInfo = ReleaseInfo(
            body = description,
            name = releaseName,
            preRelease = isPreRelease,
            tagName = tagName,
            targetCommitish = branch
        )
        return createRelease(releaseInfo, *assets)
    }

    fun createRelease(releaseInfo: ReleaseInfo, vararg assets: File): Release =
        createRelease(releaseInfo).get().apply {
            uploadAssets(this, *assets)
        }

    fun uploadAssets(release: Release, vararg assets: File) = assets.forEach {
        try {
            val body = RequestBody.create(MediaType.parse("application/octet-stream"), it)
            uploadAsset(release.uploadUrl, body, it.name).get()
        } catch (throwable: Throwable) {
            System.err.println(throwable.message)
        }
    }

    @JvmOverloads
    fun getAllReleases(perPage: Int = 100): Set<Release> {
        val releases = mutableSetOf<Release>()
        var page = 1
        do {
            val response = getReleases(page, perPage).execute()
            if (response.isSuccessful) {
                releases.addAll(response.body()!!)
                page = response.nextPage()
            } else {
                with(response.errorBody()!!) {
                    throw IOException("Status Code: ${response.code()}, Content: ${string()}")
                }
            }
        } while (page > 0)
        return releases
    }

    fun findReleaseByName(name: String): Release? =
        getAllReleases().firstOrNull { it.name == name }

    fun grantRelease(release: Release) =
        updateRelease(release.id, release.releaseInfo.copy(preRelease = false)).get()

    fun deleteRelease(release: Release) {
        deleteRelease(release.id).get()
        deleteTag(release.tagName).get()
    }

    @JvmOverloads
    fun deleteAssetFromRelease(release: Release, assetName: String? = null) = release.assets
        .filter { asset -> assetName?.let { it == asset.name } ?: true }
        .forEach { deleteAsset(it.id).get() }

}