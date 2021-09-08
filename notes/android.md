# chapter 1

## Android Overview
### concept
- Activity는 Android SDK에 있는 Class의 인스턴스이다.
- Activity는 화면에서 유저와의 상호작용 컨트롤을 담당하고 있다.
- 기능 요구 사항을 구현하기 위해 Activity의 SubClass를 만들어 사용한다.
- 간단한 어플리케이션은 단 하나의 Activity SubClass를 가지지만 복잡한 어플리케이션의 경우 여러개를 구현해야한다.
- GeoQuiz는 간단한 어플리케이션으로 단일 MainActivity 로 시작한다.
- MainActivity 에서 UI를 컨트롤 한다.
- 레이아웃은 UI 객체들과 객체들의 화면상 위치를 정의한다. 
- 레이아웃은 XML로 작성된다.
- xml의 각 tag들은 버튼이나 텍스트 처럼 화면상에 나타나도록 생성된다.

### development
- Packing Naming Convention
  - 역 DNS 형태로 구성한다.
  - Google Play Store에서 Unique하게 어플리케이션을 구별할 수 있는 단위이다.
- Android Team은 대부분의 개발자들에게 Kolitn을 사용하도록 권장한다.
- Google은 helpers와 호환성 도구들을 "support" 단일 라이브러리를 통해 제공해왔다.
  - AndroidX는 "support" 단일 라이브러리를 Jetpack이라는 이름으로 독립적으로 개발되어 버저닝되는 라이브러리들로 분리한다.
-----------------------
## Apply Change
- 특정 조건을 만족할때 앱을 다시 시작하지 않고, 코드 변경 사항만을 빠르게 반영할 수 있다.

### Apply Changes and Restart Activity
- 리소스 및 코드 변경사항을 모두 적용하기 위해 앱 재시작 없이 Acitivity만 재시작하여 변경 사항을 작용한다.
### Apply Code Changes
- 리소스 리로딩이 필요없고 메소드만 수정한 경우 변경 사항을 Activity 재시작 없이 바로 적용한다.

### principle
- 구 Instance Run은 하기 원칙을 적용하여 Apply Changes 기능으로 변경되었다.
  - 빌드/배포 속도와 손실 상태 분리
    - 상태를 잃지 않고 변경 사항을 볼 수 있는 것과 빌드/배포 속도 감속에 대한 두가지를 분리한다.
    - 빠른 빌드 및 배포는 일반 실행 디버그 세션과 코드의 핫스왑 모두의 목표이다.

### Architecture
- android 8.0 의 Android 런타임은 새 바이트 코드가 메모리의 기존 개체 레이아웃을 변경하지 않는한 로드된 클래스의 바이트 코드를 교체하는 기능을 제공한다.
- 레이아웃의 변경은 메소드와 클래스 이름 및 서명 그리고 클래스의 필드가 변경되었는지를 얘기한다.

- Apply Changes 레퍼런스
  - https://medium.com/androiddevelopers/android-studio-project-marble-apply-changes-e3048662e8cd
-----------------------
## Building Layout Resource
### layout xml resource
- layout 네이밍은 activity_액티비티명을 snake_case 형태로 짓는걸 권장

- 화면에 있는 모든 것을 view 라함
- view 중에 사용자와 상호작용 할수 있는 컴포넌트를 widget 이라고함(textView, button 등)
- view 중에 그 자체로 컨탠츠를 표기할 수는 없지만 다른 view 들을 묶어 오케스트레이션 할 수 있는 컴포넌트를 viewGroup이라고함(layout이라 통칭함)
- viewGroup과 기타 widget을 계층구조로 만들어 전체 layout을 표현함
- viewGroup이 포함하고 있는 view 들을 child라고함
- 각 view는 attributes 로 그 속성을 정함

#### attributes
- android:layout_width, android:layout_height
  - 거의 모든 widget에 존재함
- android:orientation
  - orientation의 경우 레이아웃 배치 방향은 안드로이드 사용자 언어 설저엥 따라 달라짐(arabic이나 hebrew처럼 오른쪽에서 왼쪽으로 읽는 언어의 경우 오른쪽 우선으로 배치됨)
- android:text
  - literal text로 설정, 즉 하드코딩을 할 수는 있지만 유지보수 및 국제화를 고려할 때 비추천
  - @string/ 형태로 "string resources"의 reference를 설정
  - layout xml에서 지정한 레퍼런스가 실제 resource에 없으면 에러 발생

### string xml resource
- string resource는 기본적으로 res/values/string.xml에서 관리
- android studio 버전에 따라 추가 strings 파일이 만들어 질 수 있는데 삭제 시 영향이 있을 수 있으므로 주의
- @string 레퍼런스로 xml 지정해 놓으면 runtime 시 literal string 로딩됨
- res/values 위치에 <resources> 태그를 root, <string> 태그를 child로 하는 xml을 만들면 resource 사용 가능
-----------------------
## Building Layout Source
### Directory Structure
- Activity 소스는 app/java 디렉토리에 작성한다.
- 초기 android는 java만 지원했어서 프로젝트 구조가 java로 되어 있는데, kotlin에서 java 호환을 제공하므로 app/java 디렉토리에서 kotlin 소스 관리가 가능하다.
  - kotlin 디렉토리를 만들어서 언어별 소스 분리가 가능하지만 대부분의 경우 유의미한 이득은 없어 통상적으로 java 디렉토리를 사용한다.
- 녹색 package는 테스트 package이다.
### Activity Object
- AppCompactActivity는 하위 안드로이드 버전과의 호환성을 위해 Android SDK에서 제공하는 Activity 서브 클래스이다.
### Activity.onCreate(Bundle?)
-  Activity가 생성될 때 호출되는 함수이다.
### Activity.setContentView(layoutResID: Int)
- Activity가 생성될 때 관리할 UI를 설정한다.
- layoutResID 에 해당하는 layout을 inflate 시킨다.
  - layout XML file을 파싱해서 view 객체 인스턴스로 만드는 과정을 inflate라고 한다.
### R.java
- Resource는 어플리케이션에서 code가 아닌 이미지 파일, 오디오 파일, XML 파일들(layout, string 등)로 app/res 디렉토리에서 관리된다.
- Code 에서는 resource ID를 통해 resource에 접근한다.
  - layout의 경우 R.layout에 상수로 존재한다.
- resource ID는 R.class 에서 상수로 관리된다.
  - inner class로 category를 나눠 관리된다.
  - 각 inner class에서 int 형 상수로 관리된다.
- android build tool에 의해 빌드 시 R.class 파일이 생성되며, 자동으로 ID가 생성된다.
- android build tool 버전이 높아짐에 따라 R.class 컴파일 방법이 달라지고 있으며, 최신 build tool에서는 1차적으로 jar로 만들어 관리하는 것으로 보인다.
- Archive Browser 플러그인 사용 시 jar 등의 압축파일 브라우징이 가능하다.
- 빌드 완료된 apk 파일 내부 확인 시 dex 파일에서 R 클래스 확인이 가능하다.
- layout.xml 파일의 전체 레이아웃에 대해서는 자동으로 resource ID를 생성해주지만 내부 widget은 android:id attribute를 명시해야지 ID가 생성된다.
- android:id attribute 지정 시 string resource ID와 다르게 + 기호가 사용되는데 string 의 경우 referencing 용으로만 사용하기 때문이다.
  - +id로 지정된 widget 들은 inflate를 통해 동적 생성이 가능하다.
### Activity.findViewById(int)
- inflate 된 widget의 참조를 얻을 수 있다.
#### inflated view reference call
- layout에 있는 view들은 Activity.setContentView(layoutResID: Int) 를 통해 inflate 된 후에 인스턴스화 되어 메모리에 적재되므로 lateinit 키워드를 사용해서 Late-initialized properties 임을 명시한다.
  - ? 연산자를 사용해 nullable 로 만들어도 되지만 view의 경우 null이면 사용을 못하는게 맞으므로 non null로 선언하고 lateinit 이 가능하게 한다.
  - lateinit 으로 선언된 변수에 초기화되기 전 참조할 경우 throwUninitializedPropertyAccessException 예외가 발생한다.

### Android Event-Driven
#### overview
- android 어플리케이션은 일반적으로 event driven 방식으로 동작한다.
- command line 프로그램이나 스크립트와 달리 Event-Driven 어플리케이션은 구동 후 버튼 누름과 같은 이벤트를 기다린다.
- 이벤트는 os나 다른 application에 의해 실행되기도 하지만 대부분 유저가 실행하게 된다.
- 이벤트가 발생하면 listener interface를 구현한 listener 객체가 응답을 한다.
  - Observer Pattern 형태로 동작한다.
    - event가 발생하면 event에 등록된 listener에게 event 발생 내용을 알려준다.
#### implementation
- SAM(Single Abstract Method)
  - 단일 추상 메소드만 있는 interface를 얘기한다.
  - java에서 parameter passing 시 객체 대신 lambda 사용이 가능하다.
  - SAM 패턴의 interface를 lambda로 작성할 시 SAM conversion 프로세스를 통해 컴파일 과정에서 자동으로 lambda를 interface를 구현한 객체 인스턴스로 만들어 준다.
- Android의 View.OnClickListener는 SAM 패턴의 인터페이스로 lambda 로 구현체 작성이 가능하다.
- Kotlin은 SAM 패턴에 대해 java 와 호환가능하도록 지원하므로 kotlin lambda 형식으로 함수 작성 시 SAM Conversion이 동작한다.

### Toast
- 사용자에게 짧은 메시지는 안내할 수 있는 기능이다.
- Toast.makeText(Context!, Int, Int) 함수는 Toast 객체를 구성하고 생성한다.
- Toast.setGravity() 함수는 Target API 30 부터 text toast 에서는 호출 불가하다.
### Context
- 구동중인 어플케이션의 환경 정보를 담고 있는 객체이다.
- 리소스나 클래스 접근, acitivty 시작, 브로드 캐스트, 인텐트 수신 등 application level의 작업을할때 사용된다.
- Activity가 Context의 subclass이기 때문에 Context가 필요한 경우 Activity를 캐스팅해서 넘겨준다.
-----------------------
## 안드로이브 빌드 과정
- 빌드 결과 : resoruces, code, AndroidManifest.xml 파일을 .apk로 만든다.
- 배포하기 위해선 release key를 서명을 apk 생성시 사용해야한다.
- layout 리소스들은 aapt2 에 의해 컴파일 되어 apk 파일에 패키징된다.
- setContentView() 함수는 onCreate(Bundle?)에 의해 호출되며, LayoutInflater 클래스를 사용하여 동적으로 class를 로딩하여 inflate 즉, view들을 인스턴스화 한다.
- 특정 이유에 의해 android studio 를 사용하지 않고 빌드를 해야한다면 gradle 빌드 툴을 사용하면 된다.

# Chapter2

## MVC
- Android는 MVC 아키텍처 기반으로 설계되어 있다.
  - MVC는 Android application의 presentation layer에서 주로 사용되는 디자인 패턴이다.
  - application의 기능이 다양해져서 복잡해 질 수록 가독성과 유지보수성을 위해 OOP 원칙에 따른 적절한 객체 역할 분리가 필요한데 디자인 패턴은 정형화된 역할 분리가 가능하게 도와준다.

### Model
#### overview
- Model 객체는 어플리케이션의 데이터와 비즈니스 로직을 담고 있다.
- Model Class 는 일반적으로 사용자, 저장소의 제품, 서버에 있는 사진과 같이 어플리케이션과 관련된 것들의 모델로 디자인된다.
  - 어플리케이션의 구성 도메인이라고 하는게 더 정확할 듯 싶다.
- Model 객체는 UI에 대해서는 전혀 알지 못하며 데이터를 관리하는 목적으로만 사용된다.(UI에 영향을 받지 않으므로 알 이유도 없다.)
- 어플리케이션의 모든 model 객체는 model layer를 구성한다.
#### implementation
- android에서는 kotlin, java로 구현된 model data 객체와 business logic 객체가 Model 역할을 한다.
- compiler가 자동 생성해주는 model data 객체 function을 사용하기 위해 통상적으로 model data은 data class로 구현한다.
  - primary constructor에 있는 properties에 대해 equals(), hashCode(), toString(), copy(), componentN() 함수를 자동 구현해준다.
- 코드에서 String Resource를 사용할때는 @StringRes 어노테이션 사용을 권장한다.
  - 잘못 레퍼런스 될 경우 complie 단계에서 에러 발생하며, 보통 IDE에서 먼저 error 경고 보여준다.
  - 가독성을 높여주는 장점이 있다.

### View
#### overview
- View 객체는 화면에 내용을 출력하고, 유저의 액션을 입력 받는 방법을 알고있는 객체이다.
- view class를 구성할 수 있는 다양한 방법을 제공한다.
- 어플리케이션의 view 객체들은 view layer를 구성한다.
#### implementation
- android에서는 res/ 디렉토리의 resources들과 view object가 view 역할을 한다.

### Controller
#### overview
- Controller 객체는 view와 model 객체를 결합하고 어플리케이션 로직을 담고있다.
- Controller는 view 객체로 부터 trigger되는다양한 이벤트에 반응하게 설계되어 있고 model 객체와 view layer 사이의 데이터 흐름을 관리한다.

#### implementation
- android에서는 주로 Activity나 Fragment에서 controller 역할을 한다.