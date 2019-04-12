package dev.epool.githubreleases

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern

inline fun <reified T> Call<T>.get(): T = execute().unwrap()

inline fun <reified T> Response<T>.unwrap(): T = if (isSuccessful) {
    if (T::class.java == Unit::class.java) Unit as T
    else body()!!
} else {
    with(errorBody()!!) {
        throw java.io.IOException("Status Code: ${code()}, Content: ${string()}")
    }
}

fun Response<*>.nextPage(): Int =
    headers()["Link"]?.let { linkHeader ->
        val matcher = Pattern.compile(""".*<(.*?)>; rel="next".*""").matcher(linkHeader)
        if (matcher.find()) {
            val url = HttpUrl.get(matcher.group(1))
            url.queryParameter("page")?.toInt()
        } else null
    } ?: -1

inline fun <reified T> createApi(owner: String, repo: String, token: String): T {
    val client = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        )
        .addInterceptor {
            with(it.request()) {
                val headers = headers().newBuilder().add("Authorization", "token $token").build()
                it.proceed(newBuilder().headers(headers).build())
            }
        }
        .build()

    return Retrofit.Builder()
        .baseUrl("https://api.github.com/repos/$owner/$repo/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(T::class.java)
}