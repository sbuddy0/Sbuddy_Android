package com.sbuddy.sbdApp.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sbuddy.sbdApp.R

object ImageViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("tools:profileUrl")
    fun loadProfile(view: ImageView, imageUrl: String?) {
        Log.w("sbuddyy", "view : " + imageUrl)
        // 이미지 URL이 null이 아닌 경우 Glide를 사용하여 이미지 로드
        if(imageUrl.isNullOrEmpty()){
            view.visibility = View.GONE
        }else{
            view.visibility = View.VISIBLE
            imageUrl.let {
                Glide.with(view.context)
                    .load(imageUrl)
                    .apply(RequestOptions.centerCropTransform())
                    .placeholder(R.drawable.circle_layout) // 필요에 따라 Placeholder 이미지 설정
                    .into(view)

            }
        }

    }

    @JvmStatic
    @BindingAdapter("app:contentUrl")
    fun loadContent(view: ImageView, contentUrl: String?) {
        Log.w("sbuddyy", "view : " + contentUrl)
        // 이미지 URL이 null이 아닌 경우 Glide를 사용하여 이미지 로드
        contentUrl?.let {
            Glide.with(view.context)
                .load(contentUrl)
                .apply(RequestOptions.centerCropTransform())
                .placeholder(R.drawable.gray_rounded_square) // 필요에 따라 Placeholder 이미지 설정
                .into(view)

        }
    }

    @JvmStatic
    @BindingAdapter("app:isLiked")
    fun setLikeIcon(view: ImageView, isLiked: Boolean) {
        val resourceId = if (isLiked) R.drawable.baseline_favorite_24 // 좋아요 이미지
        else R.drawable.outline_favorite_border_24 // 좋아요 안 한 상태 이미지
        view.setImageResource(resourceId)
    }

}