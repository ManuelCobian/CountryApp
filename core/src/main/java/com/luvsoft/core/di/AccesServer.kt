package com.luvsoft.core.di
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
object AdapterModule {
    @Provides
    fun provideLayoutManager(@ApplicationContext context: Context): RecyclerView.LayoutManager =
        LinearLayoutManager(context)
}

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(com.luvsoft.core.Constants.URL_BASE)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(@ApplicationContext context: Context): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val requestBuilder = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                val request = requestBuilder.build()

                request.body()?.let {
                    val buffer = Buffer()
                    it.writeTo(buffer)
                }

                request.body()?.let {
                    val buffer = Buffer()
                    it.writeTo(buffer)
                }

                val response = chain.proceed(request)

                response.body()?.let {
                    val responseBody = it.string()
                    return response.newBuilder()
                        .body(ResponseBody.create(response.body()!!.contentType(), responseBody))
                        .build()
                }
                return response.newBuilder()
                    .build()
            }
        }
    }
}
