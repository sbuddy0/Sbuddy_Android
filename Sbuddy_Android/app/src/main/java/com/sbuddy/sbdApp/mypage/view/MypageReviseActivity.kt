package com.sbuddy.sbdApp.mypage.view

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivityMypageReviseBinding
import com.sbuddy.sbdApp.databinding.ActivityPostReviseBinding
import com.sbuddy.sbdApp.mypage.viewmodel.MypageViewModel
import com.sbuddy.sbdApp.post.adapter.KeywordItemAdapter
import com.sbuddy.sbdApp.post.view.MainActivity
import com.sbuddy.sbdApp.post.viewmodel.PostViewModel
import com.sbuddy.sbdApp.util.ToastMessage
import com.sbuddy.sbdApp.util.UploadUtil

class MypageReviseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageReviseBinding
    private lateinit var mypageViewModel: MypageViewModel
    private var position: Int = 0
    private val PERMISSION_REQUEST_CODE = 100
    private val getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            mypageViewModel.setSelectedImageUri(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypage_revise)
        mypageViewModel = ViewModelProvider(this)[MypageViewModel::class.java]
        binding.viewModel = mypageViewModel
        binding.lifecycleOwner = this
        binding.activity = this

        position = intent.getIntExtra("position", 0)
        Log.e("positionn", "position : " + position)
        mypageViewModel.detail(position)

        setRecyclerView()
        setObserve()
    }

    fun setRecyclerView(){
        binding.oneRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.oneRecyclerview.adapter = KeywordItemAdapter { idxCategory, idxKeyword ->
            mypageViewModel.checkTitleKeyword(idxCategory, idxKeyword)
        }
        binding.oneRecyclerview.setHasFixedSize(true)

        binding.twoRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.twoRecyclerview.adapter = KeywordItemAdapter { idxCategory, idxKeyword ->
            mypageViewModel.checkSubKeyword(idxCategory, idxKeyword)
        }
        binding.twoRecyclerview.setHasFixedSize(true)
    }

    fun setObserve() {
        mypageViewModel.titleKeywords.observe(this) { keywords ->
            (binding.oneRecyclerview.adapter as? KeywordItemAdapter)?.submitList(keywords)
        }

        mypageViewModel.subKeywords.observe(this) { keywords ->
            (binding.twoRecyclerview.adapter as? KeywordItemAdapter)?.submitList(keywords)
        }

        mypageViewModel.selectedImageUri.observe(this){
            val requestOptions = RequestOptions()
                .transform(RoundedCorners(30)) // 모서리를 30dp 둥글게 설정
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 캐시 설정

            Glide.with(this)
                .load(mypageViewModel.selectedImageUri.value)
                .apply(requestOptions)
                .into(binding.imgPreview)
        }

        mypageViewModel.showNextActivity.observe(this){
            if(it){
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        mypageViewModel.showToast.observe(this){
            if(it){
                ToastMessage.show(this, "다시 확인해주세요.")
            }
        }

        mypageViewModel.detail.observe(this) { detail ->
            binding.titleEdit.setText(detail.title)
            binding.contentEdit.setText(detail.content)

        }

        mypageViewModel.updateFinished.observe(this){
            if(it){
                finish()
            }
        }
    }

    fun revise(){
        if(mypageViewModel.selectedImageUri.value != null){
            mypageViewModel.revise(UploadUtil.uriToFile(this, mypageViewModel.selectedImageUri.value!!), binding.titleEdit.text.toString(), binding.contentEdit.text.toString(), position )
        }else{
            mypageViewModel.revise(null, binding.titleEdit.text.toString(), binding.contentEdit.text.toString(), position )
        }
    }

    fun goBefore(){
        finish()
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