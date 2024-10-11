package com.sbuddy.sbdApp.http

import com.google.gson.annotations.SerializedName
import com.sbuddy.sbdApp.chat.model.ChatMemberResponse
import com.sbuddy.sbdApp.chat.model.ChatResponse
import com.sbuddy.sbdApp.mypage.model.MyLikeResponse
import com.sbuddy.sbdApp.mypage.model.MypageResponse
import com.sbuddy.sbdApp.post.model.KeywordResponse
import com.sbuddy.sbdApp.post.model.PostDetailResponse
import com.sbuddy.sbdApp.post.model.PostResponse
import com.sbuddy.sbdApp.search.model.SearchPopularReponse
import com.sbuddy.sbdApp.search.model.SearchRecentData
import com.sbuddy.sbdApp.search.model.SearchRecentResponse
import com.sbuddy.sbdApp.search.model.SearchTextResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @POST("api/v1/login")
    @Headers("Content-Type: application/json")
    suspend fun login(
        @Body params: User
    ): Response<Any>

    @POST("api/v1/join/email/send")
    @Headers("Content-Type: application/json")
    suspend fun authEmail(
        @Body params: Email
    ): Response<Any>

    @POST("api/v1/join/email/auth")
    @Headers("Content-Type: application/json")
    suspend fun authEmailNumber(
        @Body params: EmailAuth
    ):Response<Any>

    @POST("api/v1/join")
    @Headers("Content-Type: application/json")
    suspend fun signUp(
        @Body params: SignUp
    ):Response<Any>

    @POST("api/v1/member/find/password")
    @Headers("Content-Type: application/json")
    suspend fun findPwd(
        @Body params: Email
    ): Response<Any>


    @POST("/api/v1/post/list")
    @Headers("Content-Type: application/json")
    suspend fun getList(
        @Body params: Search
    ):Response<PostResponse>

    @POST("/api/v1/post/likes")
    @Headers("Content-Type: application/json")
    suspend fun like(
        @Body params: Like
    ):Response<Any>

    @POST("/api/v1/post/likes/cancel")
    @Headers("Content-Type: application/json")
    suspend fun cancelLike(
        @Body params: Like
    ):Response<Any>

    @POST("/api/v1/post/delete")
    @Headers("Content-Type: application/json")
    suspend fun delete(
        @Body params: Like
    ):Response<Any>

    @POST("api/v1/post/likes/cancel")
    @Headers("Content-Type: application/json")
    suspend fun list(
        @FieldMap params: Map<String, String>
    ): Response<Any>

    @Multipart
    @POST("api/v1/post/write")
    suspend fun post(
//        @Header("token") authHeader: String, // 헤더를 직접 추가
        @Part img:MultipartBody.Part,
        @Part("param") params: RequestBody
    ):Response<Any>

    @Multipart
    @POST("api/v1/post/update")
    suspend fun revise(
        @Part file: MultipartBody.Part? = null,
        @Part("param") params: RequestBody
    ):Response<Any>

    @POST("api/v1/keyword/list")
    @Headers("Content-Type: application/json")
    suspend fun keywordList(
        @Body params: Map<String, String>
    ): Response<KeywordResponse>

    @POST("api/v1/post/detail")
    @Headers("Content-Type: application/json")
    suspend fun detail(
        @Body params: Detail
    ): Response<PostDetailResponse>

    @POST("api/v1/post/popular/list")
    @Headers("Content-Type: application/json")
    suspend fun popularList(
        @Body params: Map<String, String>
    ): Response<SearchPopularReponse>

    @POST("api/v1/post/search/recent")
    @Headers("Content-Type: application/json")
    suspend fun searchRecent(
        @Body params: Map<String, String>
    ): Response<SearchRecentResponse>

    @POST("api/v1/post/delete/recent")
    @Headers("Content-Type: application/json")
    suspend fun deleteRecent(
        @Body params: RecentTextIdx
    ): Response<Any>

    @POST("api/v1/post/search/text")
    @Headers("Content-Type: application/json")
    suspend fun searchText(
        @Body params: SearchText
    ): Response<SearchTextResponse>

    @POST("api/v1/message/list")
    @Headers("Content-Type: application/json")
    suspend fun messageList(
        @Body params: Message
    ):Response<ChatResponse>

    @POST("api/v1/message/find/member")
    @Headers("Content-Type: application/json")
    suspend fun searchMember(
        @Body params: Member
    ):Response<ChatMemberResponse>

    @POST("api/v1/message/send")
    @Headers("Content-Type: application/json")
    suspend fun sendMessage(
        @Body params: MessageSend
    ):Response<ChatMemberResponse>

    @POST("api/v1/mypage/detail")
    @Headers("Content-Type: application/json")
    suspend fun myDetail(
        @Body params: Map<String, String>
    ):Response<MypageResponse>

    @POST("api/v1/post/like/list")
    @Headers("Content-Type: application/json")
    suspend fun myLikeList(
        @Body params: Map<String, String>
    ):Response<MyLikeResponse>

    @POST("api/v1/post/my/list")
    @Headers("Content-Type: application/json")
    suspend fun myWriteList(
        @Body params: Map<String, String>
    ):Response<MyLikeResponse>

    @POST("api/v1/mypage/modify/keyword")
    @Headers("Content-Type: application/json")
    suspend fun updateKeyword(
        @Body params: Keyword
    ): Response<Any>

    @POST("api/v1/member/keyword/insert")
    @Headers("Content-Type: application/json")
    suspend fun insertKeyword(
        @Body params: KeywordList
    ): Response<Any>


}

// 로그인 유저
data class User(
    @SerializedName(value = "id") var id: String,
    @SerializedName(value = "password") var password: String
)

// 이메일
data class Email(
    @SerializedName(value = "email") var email: String
)

// 이메일 + 이메일 인증번호
data class EmailAuth(
    @SerializedName(value = "auth_code") var authCode: String,
    @SerializedName(value = "email") var email: String
)

// 회원가입
data class SignUp(
    @SerializedName(value = "username") var username: String,
    @SerializedName(value = "email") var email: String,
    @SerializedName(value = "password") var password: String
)

//
data class Search(
    @SerializedName(value = "idx_member") var idxMember: String,
    @SerializedName(value = "search") var search: String
)

data class Like(
    @SerializedName(value = "idx_post") var idxPost: Int,
    @SerializedName(value = "idx_member") var idxMember: String
)

data class Post(
    @SerializedName(value = "title") var title:String,
    @SerializedName(value = "content") var content:String,
    @SerializedName(value = "keyword_list") var keyword:ArrayList<Int>
)

data class Update(
    @SerializedName(value = "idx_post") var idxPost: Int,
    @SerializedName(value = "title") var title:String,
    @SerializedName(value = "content") var content:String,
    @SerializedName(value = "keyword_list") var keyword:ArrayList<Int>
)

data class Detail(
    @SerializedName(value = "idx_post") var idxPost: Int
)

data class SearchText(
    @SerializedName(value = "search") var text: String
)

data class RecentTextIdx(
    @SerializedName(value = "idx_search_text") var idx: Int
)

data class Message(
    @SerializedName(value = "message_type") var type: String
)

data class Member(
    @SerializedName(value = "member") var member: String
)

data class MessageSend(
    @SerializedName(value = "idx_receiver") var idxReceiver: Int,
    @SerializedName(value = "title") var title: String,
    @SerializedName(value = "content") var content: String,
    @SerializedName(value = "idx_reply") var idxReply: String
)

data class Keyword(
    @SerializedName(value = "keyword") var keyword: String
)

data class KeywordList(
    @SerializedName(value = "keyword_list") var keywordList: String
)



