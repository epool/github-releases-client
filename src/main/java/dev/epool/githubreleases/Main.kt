package dev.epool.githubreleases

fun main() {
    val client = GithubReleasesClient.newInstance(
        "BreakthroughBehavioralInc",
        "mobile_dist",
        "e76b6869b5bb8d9d95eaafe3a0da9686237b7662"
    )

//    client.createRelease("development_999", "Description of the release", listOf(File("appWhiteLabel-mdLive-debug.apk")))
//    client.createRelease("Feature_CM-123_999", "Description of the release", listOf(File("appWhiteLabel-mdLive-debug.apk")))
//    client.createRelease("Release_mdLive_x.y.z_999", "Description of the release", listOf(File("appWhiteLabel-mdLive-debug.apk")))
//    release?.let { client.grantRelease(it) }
    client.getAllReleases().forEach {
        //        val release = client.findReleaseByName(it.name)
//        release?.let { client.deleteRelease(release) }
    }
}