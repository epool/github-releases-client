package dev.epool.githubreleases

import dev.epool.githubreleases.models.ReleaseInfo
import org.spekframework.spek2.Spek
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GithubReleasesClientTest : Spek({

    val tagName = "1.0.0"
    val releaseName = "v1.0.0"

    val client = GithubReleasesClient.newInstance(
        "epool",
        "github-releases-client-test",
        "015bee8e53b41fa1d114c563b694a203cf57b4e9"
    )

    test("Get No Releases") {
        //arrange

        //act
        val releases = client.getAllReleases()

        //assert
        assertTrue(releases.isEmpty())
    }

    test("Create Release") {
        val description = "Description"

        //act
        val release = client.createRelease(releaseName, tagName, description)

        //assert
        val expectedReleaseInfo = ReleaseInfo(
            body = description,
            name = releaseName,
            preRelease = true,
            tagName = tagName
        )
        assertEquals(expectedReleaseInfo, release.releaseInfo)
    }

    test("Create Specific Release") {
        val description = "Description"
        val expectedReleaseInfo = ReleaseInfo(
            body = description,
            name = "$releaseName-specific",
            preRelease = true,
            tagName = "$tagName-specific"
        )

        //act
        val release = client.createRelease(expectedReleaseInfo, File("hello-world.txt"))

        //assert
        assertEquals(expectedReleaseInfo, release.releaseInfo)
        client.deleteRelease(release)
    }

    test("Get All Releases") {
        //arrange

        //act
        val releases = client.getAllReleases()

        //assert
        assertTrue(releases.isNotEmpty())
    }

    test("Get Release By Name") {
        //arrange

        //act
        val release = client.findReleaseByName(releaseName)

        //assert
        assertNotNull(release)
    }

    test("Grant Release") {
        //arrange
        val release = client.findReleaseByName(releaseName)
        assertNotNull(release)
        assertTrue(release.preRelease)

        //act
        val grantedRelease = client.grantRelease(release)

        //assert
        assertNotNull(grantedRelease)
        assertFalse(grantedRelease.preRelease)
    }

    test("Delete Release") {
        //arrange
        val release = client.findReleaseByName(releaseName)

        //act
        release?.let { client.deleteRelease(it) }

        //assert
        assertNotNull(release)
    }

})