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

1. **회원 가입**

2. **로그인**

3. **글 작성**

4. **글 수정**

5. **글 삭제**

6. **검색**

7. **채팅**

8. **마이페이지**
  
