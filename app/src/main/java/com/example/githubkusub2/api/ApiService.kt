package com.example.githubkusub2.api

import com.example.githubkusub2.ItemsItem
import com.example.githubkusub2.UserDetailResponse
import com.example.githubkusub2.UserSearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun findUserSearch(
        @Query("q") query: String
    ): Call<UserSearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}