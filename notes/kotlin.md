## object
- kotlin에서 객체 인스턴스 생성 시 new 키워드 없이 생성자를 호출한다.

## properties

- properties는 object의 데이터를 관리하는 역할을 한다.
- var 키워드로 mutable property를 선언한다.
- val 키워드로 immutable(read only) property를 선언한다.
- property는 아래 형태로 선언한다.
```
// <..> : token 내용, [..] : 생략 가능
var <propertyName>[: <propertyType>] [= <property_initializer>]
    [<getter>]
    [<setter>]
val <propertyName>[: <propertyType>] [= <property_initializer>]
    [<getter>]
```
- var 로 선언된 원시형 property는 property_initializer를 필수적으로 선언해야한다.
```kotlin
var a: Car = Car()
```
- var 로 선언된 property 중 lateinit의 경우 property_initializer 사용은 불가하다.
```kotlin
lateinit var a: Car
```
- val 로 선언된 property는 property_initializer 나 getter 중 하나는 필수적으로 선언해야한다.
- val 로 선언된 property는 backing field를 사용하지 않는 경우에만 property_initializer 사용이 가능하다.
```kotlin
val a: Car = Car()
val b: Int
    get() {
        field * 2
    }
```
- 모든 property는 property_initializer 나 getter에 의해 형 추론이 가능하다면 propertyType 생략이 가능하다.
```kotlin
val a = Car()
val b
    get() {
        field * 2
    }
```
  
### data manage
#### backing field
- kotlin에서 field는 property의 데이터 실값을 메모리 담고 있는 역할을 한다.
- kotlin에서는 accessors(getter와 setter)를 제외하고 field에 접근 할 수 있는 방법은 없으며,
  정확한 명칭으론 backing field 라 부른다.
  - reflect로 field에 접근 가능한지는 추가 확인 필요
- val 로 선언된 property에서 backing field가 사용되지 않는 getter를 선언할 경우
  backing field가 없는 property가 된다.
#### accessors
- property의 accessors 중 getter는 데이터의 계산 로직을 담고있는 역할을 한다.
- property의 accessors 중 setter는 데이터 assign 제한 로직을 담고있는 역할을 한다.
- property의 accessors 에서 field 키워드를 사용해 backing field에 접근 할 수 있다.
#### backing property
- JVM에서 private property에 default getter/setter 를 호출할 경우 backing field에 직접 접근하여
  function call overhead를 피하도록 최적화 해준다.
- custom getter/setter를 사용하면서 상기 최적화를 하기 위해선 backing property를 사용하면 된다.
- 최적화 하고자 하는 property에 대해 동일 property명에 _ prefix를 붙여
  default getter/setter 로 private property 즉, backing property를 선언한 후
  custom getter/setter에서 backing property를 호출하면 function call overhead 최적화를 할 수 있다.

### Java VS Kotlin
- android application을 java로 구현할 경우 object 멤버변수 네이밍시 m prefix를 붙이는 걸 권장한다.
- android application을 kotlin으로 구현할 경우 object 멤버변수 네이밍에 m prefix를 사용하지 않는다.
  - backing property의 특수한 경우 사용한다고 하는데 실 케이스를 찾을 수 없다.
- java의 경우 object 구성을 member variable과 method로 하며
  variable은 데이터를 저장하는 변수, method는 데이터 가공 및 object의 행위를 담당하는 함수 역할을 한다.
- kotlin의 경우 object 구성을 properties와 function으로 하며
  properties는 데이터를 관리(backing field에 저장, accessors로 가공)하는 역할, function은 object의 행위를 담당하는 역할을 한다.
  - 즉, kotlin의 경우 object의 데이터를 관리하는 주체(property)를 두어 function과의 결합성을 낮춘 느낌이다.
  
### Late-initialized properties and variables
- lateinit 는 var 형 properties를 non-null 로 사용해야 하나 초기화를 생성자에서 할 수 없는 경우에 사용하며,
  <property_initializer>가 없어도 var 형 properties 선언이 가능하게 한다.
  - 의존성 주입, 유닛 테스트 초기화 메소드 등의 케이스에 사용
- lateinit 은 primary constructor에서는 사용할 수 없으며, custom getter/setter가 선언될 경우 사용할 수 없다.
- lateinit으로 선언된 var 형 properties는 non-null이어야 하며, 원시형 property에서는 사용할 수 없다.
- lateinit으로 선언된 var 형 properties에서 .isInitialzed 를 참조하면 초기화 여부를 확인 할 수 있다.
- 하기 내용 확인 필요
  - This check is only available for properties that are lexically accessible
    when declared in the same type, in one of the outer types, or at top level in the same file.

## data classes

- kotlin에서는 data 키워드로 data classes를 만들 수 있다.
  - data classes는 data를 보유하기 위한 목적으로 class를 만드는 경우가 종종 있고 이 경우 정형화된 function 을 제공한다.
  - data classes에서는 하기 함수들을 자동 구현해주며, 자동 구현 시 primary constructor에 있는 property만을 사용한다.
    - equals(), hashCode() : object의 property 상세 내용이 같을 경우 동일 hashCode 값을 가지며, 동일 레퍼런스로 판단한다.
    - toString() : object의 property 상세 내용을 추출
    - componentN() : properties 선언 순서에 따라 component1, component2 형태로 호출하여 값을 조회
    - copy() : shallow copy object 생성

- data class에서 일관적이고 유의미한 코드 작성을 위해서 아래 조건을 따른다.
  - primary constructor는 최소 하나 이상의 매개변수를 가져야 한다.
  - 모든 primary constructor는 var 또는 val 형을 명시해야한다.
  - data classes 는 abstract, open, sealed, inner 형태일 수 없다.

- data class 멤버 변수 생성 시 멤버 상속과 관련하여 아래 규칙을 따른다.
  - equals(), hasCode(), toString() function을 직접 구현하거나 상위 클래스에서 final로 선언된 경우 기구현된 function을 사용한다.
  - 상위 클래스에서 componentN() 함수가 open으로 선언되어있고 캐스팅 가능한 타입을 return 할 경우 data class 생성 시 자동으로 override된다.
  - componentN()과 copy() 함수의 명시적 구현은 허용되지 않는다.

- data classes에서 자동 생성된 component function은 destructuring declarations를 사용 가능하게 만든다.
  - destructuring declarations은 코드 반복을 줄이고 가독성을 높여준다.
```kotlin
val jane = User("Jane", 35)
val (name, age) = jane
println("$name, $age years of age")
```