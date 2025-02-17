# Sbuddy_Android
프로그래밍 스터디를 모집하기 위한 커뮤니티 앱 입니다. 사용자는 게시물 작성, 검색, 채팅 등 여러 기능을 통해 앱을 활용할 수 있습니다.


## 주요 기능
* 회원 가입 및 로그인 : 회원 가입 및 로그인 기능을 통해 개인화된 서비스를 제공합니다.
* 게시물 작성 및 수정 : 사용자가 게시물을 작성하고 수정할 수 있습니다.
* 검색 : 다양한 항목을 검색하고 검색 결과를 확인할 수 있습니다.
* 채팅 : 다른 사용자와 채팅을 통해 소통할 수 있습니다.
* 마이페이지 : 내 정보를 수정하고 관리할 수 있는 기능을 제공합니다.


## 권한
* 미디어 파일 접근 : 외부 저장소에서 이미지 및 비디오를 읽기 위한 권한을 요청합니다.


## 기술 스택

* 언어 : Kotlin
* 안드로이드 SDK : Android 12 이상
* 라이브러리 : RecyclerView, Retrofit, Glide 등


## MVVM 패턴

1. ViewModel을 사용하여 데이터 로직을 View에서 분리
2. LiveData를 사용하여 UI 상태를 자동 업데이트

```ruby
class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        binding.viewModel = chatViewModel  // ✅ DataBinding을 사용해 ViewModel과 UI 연결
        binding.lifecycleOwner = this  // ✅ LiveData를 사용하기 위해 Lifecycle 설정
        binding.fragment = this  // ✅ Fragment 내부 함수 바인딩

        setReceivedRecyclerView()
        setSendRecyclerView()
        setObserve()
    }

    override fun onResume() {
        super.onResume()
        chatViewModel.receivedChatList("R")  // ✅ ViewModel을 통해 데이터 요청
        chatViewModel.sendChatList("S")
    }
}
```


## Coroutine 비동기 통신

1. viewModelScope.launch {} 를 활용한 비동기 처리

```ruby
viewModelScope.launch {
    val response = repository.messageList(type)
    Log.w("chatt", "chatt response : " + response.body())
    if(response.isSuccessful){
        if(response.code() == 200){
            _receivedChats.value = response.body()?.data?.list
        }
    }
}

```

2. suspend 함수로 네트워크 요청 최적화

```ruby
suspend fun messageList(type: String) = retrofitService.messageList(Message(type))

```

3. Retrofit을 활용한 네트워크 요청 처리

```ruby
private val retrofitService: RetrofitService = SbuddyRetrofitService.instance()

```

4. ViewModelScope 활용으로 메모리 누수 방지

```ruby
viewModelScope.launch {
    val response = repository.messageList(type)
}
```


## 개발 환경

* Android Studio : 최신 버전 권장
* JDK : Java 11 이상


## 실행 화면

1. **로그인 후 키워드 선택**
   - 키워드 선택에 따라 피드 글이 정렬되며, 마이페이지에서 키워드를 수정할 수 있다.

<img src="https://github.com/user-attachments/assets/0c8ff300-df7d-4251-89cb-9c49148b030b" alt="로고" width="300"/>
<img src="https://github.com/user-attachments/assets/83153ad9-6c5b-449a-bc96-4e6f1948e8bb" alt="로고" width="300"/>

<br><br>

2. **글 작성**
   - 이미지와 게시글 키워드를 선택
   - 키워드 검색할 때 검색한 키워드가 포함된 게시글이 조회된다.
     
<img src="https://github.com/user-attachments/assets/0b969c4c-a054-4d8d-86f5-870a90f0c4fe" alt="로고" width="300"/>
<img src="https://github.com/user-attachments/assets/4a7ff55b-ce7d-476f-a27d-fdf0f50cba13" alt="로고" width="300"/>

<br><br>

3. **글 수정, 삭제**
<img src="https://github.com/user-attachments/assets/421a8be6-8a35-4efd-a89a-911f16fe472d" alt="로고" width="300"/>
<img src="https://github.com/user-attachments/assets/3edf7e9d-4618-416e-bdc5-7107191055e8" alt="로고" width="300"/>
<br><br>

5. **좋아요**
<img src="https://github.com/user-attachments/assets/a93ba8be-9cc8-45fb-88df-7c6a33b0316d" alt="로고" width="300"/>
<img src="https://github.com/user-attachments/assets/e477ba3d-696a-4de3-a996-449efd47b2e6" alt="로고" width="300"/>
<br><br>

6. **직접 검색, 키워드 검색**
   - 검색 화면
   - 직접 검색 결과, 키워드 검색 결과
<img src="https://github.com/user-attachments/assets/13c255df-5eb4-43f8-865d-b881521b3606" alt="로고" width="300"/>
<img src="https://github.com/user-attachments/assets/7a629e76-7ea1-4ddc-830b-512cfb10e59b" alt="로고" width="300"/>
<img src="https://github.com/user-attachments/assets/dd7439e0-ba00-491c-95be-e1e3de7c4278" alt="로고" width="300"/>
<br><br>
   
7. **쪽지**
<img src="https://github.com/user-attachments/assets/c8fa9f22-5012-4f74-b7f1-13de3b108471" alt="로고" width="300"/>
<img src="https://github.com/user-attachments/assets/9cfe5a68-d4cc-4b49-9d09-98f238bb1e33" alt="로고" width="300"/>

<br><br>
8. **마이페이지**
   - 내가 좋아요 한 글
   - 내가 쓴 글
<img src="https://github.com/user-attachments/assets/d491e1d2-9504-4d69-986d-3f2eb4588458" alt="로고" width="300"/>
<img src="https://github.com/user-attachments/assets/13f993ce-8d0e-4785-87b5-0d7b658300d7" alt="로고" width="300"/>
<img src="https://github.com/user-attachments/assets/11b21535-6c81-48ca-9214-3d4888132ae1" alt="로고" width="300"/>
