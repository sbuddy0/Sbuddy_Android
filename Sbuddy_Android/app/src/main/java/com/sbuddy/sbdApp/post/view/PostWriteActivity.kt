package com.sbuddy.sbdApp.post.view

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivityPostWriteBinding
import com.sbuddy.sbdApp.post.adapter.KeywordItemAdapter
import com.sbuddy.sbdApp.post.viewmodel.PostViewModel
import com.sbuddy.sbdApp.util.ToastMessage
import com.sbuddy.sbdApp.util.UploadUtil
import java.util.jar.Manifest


class PostWriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostWriteBinding
    private lateinit var postViewModel: PostViewModel
    private val PERMISSION_REQUEST_CODE = 100
    private val getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            postViewModel.setSelectedImageUri(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_write)
        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]
        binding.viewModel = postViewModel
        binding.lifecycleOwner = this
        binding.activity = this
        setRecyclerView()
        setObserve()
    }

    fun setRecyclerView(){
        binding.oneRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.oneRecyclerview.adapter = KeywordItemAdapter { idxCategory, idxKeyword ->
            postViewModel.checkTitleKeyword(idxCategory, idxKeyword)
        }
        binding.oneRecyclerview.setHasFixedSize(true)

        binding.twoRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.twoRecyclerview.adapter = KeywordItemAdapter { idxCategory, idxKeyword ->
            postViewModel.checkSubKeyword(idxCategory, idxKeyword)
        }
        binding.twoRecyclerview.setHasFixedSize(true)
    }

    fun setObserve() {
        postViewModel.titleKeywords.observe(this) { keywords ->
            (binding.oneRecyclerview.adapter as? KeywordItemAdapter)?.submitList(keywords)
        }

        postViewModel.subKeywords.observe(this) { keywords ->
            (binding.twoRecyclerview.adapter as? KeywordItemAdapter)?.submitList(keywords)
        }

        postViewModel.selectedImageUri.observe(this){
            val requestOptions = RequestOptions()
                .transform(RoundedCorners(30)) // 모서리를 30dp 둥글게 설정
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 캐시 설정

            Glide.with(this)
                .load(postViewModel.selectedImageUri.value)
                .apply(requestOptions)
                .into(binding.imgPreview)
        }

        postViewModel.showNextActivity.observe(this){
            if(it){
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        postViewModel.showToast.observe(this){
            if(it){
                ToastMessage.show(this, "다시 확인해주세요.")
            }
        }
    }

    fun write(){
        if(postViewModel.selectedImageUri.value != null){
            postViewModel.post(UploadUtil.uriToFile(this, postViewModel.selectedImageUri.value!!), binding.titleEdit.text.toString(), binding.contentEdit.text.toString() )
            return
        }
        ToastMessage.show(this, "사진을 선택해주세요.")
    }

    fun goBefore(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun chooseImg(){
        Log.d("keywordd", "chooseImg()")
        this.getImageContent.launch("image/*")
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(android.Manifest.permission.READ_MEDIA_IMAGES),
                PERMISSION_REQUEST_CODE
            )
        } else {
            chooseImg()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImg()
            } else {
                // 권한 거부 처리
            }
        }
    }
}