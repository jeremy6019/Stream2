package definition;

public class stream2{
/*
 
**  스트림의 집계 (aggregate:기술 통계의 값 - 합계, 평균, 최대, 최소, 카운트 등) 
=>리턴 타입 앞에 Optional이 붙으면 바로 사용할 수 없고 get이나 orElse같은 
메소드를 호출해서 사용 

1.DTO클래스 생성 

=>최종연산중에서 count()는 데이터의 개수를 정수로 리턴 
    //스트림.중간연산.최종연산 
	    //중간연산은 생략가능하고 중복가능 
	    //최종연산은 필수이고 1번만 사용 가능 
	    long cnt =stream.filter(student ->{
	    	return student.getGender().equals("여자");
	    }).count();
	    System.out.printf("여자:%d명\n", cnt);

=>합계는 sum()으로 구하는데 숫자 데이터만 가능 
숫자가 아니면 mapTo?연산을 이용해서 데이터를 변경 
/Student의 객체를 getScore의 결과로 변환해서 
	    //합계를 구합니다. 
	    int sum =stream.filter(student ->{
	    	return student.getGender()
	    			.equals("여자");
	    }).mapToInt(Student::getScore).sum();
	    
	    System.out.printf("여자 점수 합계:%d\n",sum);

=>평균은 average연산을 이용해서 구할 수 있는데 결과 자료형이 Optional타입 
입니다.
get이나 orElse메소드를 이용해서 결과를 다시 추출해야합니다. 
  //여자의 평균 
	    OptionalDouble avg= stream.filter(
	    		student -> {return student.getGender().equals("여자");
	    }).mapToInt(Student::getScore).average();	    
	    double a =avg.getAsDouble();
	    System.out.printf("여자 점수 합계:%.3f\n",a); 

**reduce연산 
=>카운트, 합계, 평균, 최대,최소는 만들어져 있지만 그이외의 연산은 제공되지 않습니다.
=>그 이외의 연산을 하고자 할 때 사용하는 최종연산 메소드가 reduce입니다. 
reduce에는 매개변수로 첫번째는 시작하는 값(초기값)을 주고 두번째는 매개변수가 
2개이고 연산식을 수행하는 람다식을 대입하면 됩니다.   

두번째로 대입되는 람다식의 매개변수는 첫번째는 연산의 결과이고 두번째는 새로 대입되는 
데이터입니다. 

 //여자인 데이터의 곱을 구하기 
	   int prod =  stream.filter(
	    		student -> {return student.getGender().equals("여자");
	    }).mapToInt(Student::getScore)
	    .reduce(1, (n1, n2) -> n1 * n2);
	   System.out.printf("여자 점수 곱:%d\n",prod);

**collect 
=> 중간 연산을 수행한 데이터를 수집(저장)하는 최종연산
=> 리턴 라입은 collect<데이터의 자료형, List 또는 Set 또는 Map> 
=> 메소드 이름은 toList, toSet, toMap, toConcurrentMap 
    //gender가 남자인 데이터만 모아서 List만들기 
	    List<Student> li = 
	    		stream.filter((student) ->{ 
	    		return student.getGender().equals("남자");
	    }).collect(Collectors.toList());
	    System.out.printf("li:%s\n", li);

=>Map을 만들때는 toMap의 첫번째 매개변수로 Key로 사용할 데이터를 리턴해주는 
메소드를 설정해야하고 두번째 매개변수로는 하나의 매개변수를 가지고 하나의 데이터 
를 리턴하는 람다식을 대입해야 합니다. 
첫번째 매개변수의 결과가 key가 되고 두번째 매개변수가 Value가 됩니다. 
    
    	    //남자인 데이터를 골라내서 이름을 key로 하고 
	    //전체 데이터를 value로하는 Map만들기 
	    Map<String, Student> map =
	    stream.filter((student) ->{ 
    		return student.getGender()
    		.equals("남자");
        }).collect(Collectors.toMap(
        		Student::getName, student -> student));
//     System.out.printf("%s\n", map);
      
	    //Map의 데이터를 각 키마다 출력 
	    // Map은 key를 알지 못해도 출력이 가능 
	    // DTO클래스는 속성의 이름을 알아야 출력이 가능 
	    //DTO를 사용하면 구조변경이 어렵다 -RDBMS 
	    //Map을 이용하면 구조 변경이 쉽다 - NoSQL 
	    
	    Set <String> keyset = map.keySet();
	    for(String key : keyset) {
	    	System.out.printf("%s\n",
	    			map.get(key));
	    }
	    //최근에 등장한 언어들은 자신들이 만든 API의 
	    //결과를 Map(Dictionary)으로 
	    //리턴하는 경우가 많습니다. 

**그룹화 
=>groupingBy()함수를 이용하면 특정한 조건을 기준으로 그룹화한 데이터를 
만들수 있습니다.
.collect(collectors.groupingBy(그룹화할 함수명))을 호출하면 함수의 결과로 
그룹화를 수행해서 Map으로 리턴해 줍니다. 
Map의 key는 그룹화하는 함수의 결과이고 Value는 List입니다. 

성별로 그룹화하기 
  //gender별로 그룹화하기 
	    Map<String, List<Student>> map = 
	    		stream.collect(Collectors.groupingBy(
	    		Student::getGender));
	    
	    Set <String> keyset = map.keySet();
	    for(String key : keyset) {
	    	System.out.printf("%s:%s\n",
	    			key, map.get(key));
	    }  

=>그룹화 한 후 집계를 하고자 하는 경우 
Collectors.groupingBy(그룹화할 함수이름, 집계함수)를 호출하면 Map으로 리턴 

평균은 Collectors.averagingDouble(평균을 구할 컬럼의 메소드) 
최대나 최소는 Collectors.maxBy(Comparator.comparing자료형(최대나 최소를 
구할 컬럼의 메소드)) 

데이터 개수는 Collectors.counting() 

합계는 Collectors.summingInt(합계를 구할 컬럼의 메소드) 

Int대신에 Long과 Double도 사용 가능 

    //남자와 여자 평균 점수 구하기 
	    Map<String, Double> genderAvg = 
	    		stream.collect(
	    		Collectors.groupingBy(
	    	    Student::getGender,
	    	    Collectors.averagingDouble(	    	    		
	    		Student::getScore)));
	    
	    Set <String> keyset = genderAvg.keySet();
	    for(String key : keyset) {
	    	System.out.printf("%s:%.3f\n",
	    			key, genderAvg.get(key));
	    }
	    
   //남자와 여자 최하 점수 구하기 
	    Map<String, Optional<Student>> genderMin= 
	    		stream.collect(
	    		Collectors.groupingBy(
	    	    Student::getGender,
	    		Collectors.minBy(
	    		Comparator.comparingInt(
	    		Student::getScore))));
	    
	    Set <String> keyset = genderMin.keySet();
	    for(String key : keyset) {
	    	System.out.printf("%s:%s\n",
	    			key, genderMin.get(key));
	    }
	    
=>파이썬이나 R로 데이터 분석을 많이 하는 이유가 이러한 Stream API같은 
라이브러리가 많기 때문입니다. 


**동시성과 병렬처리 
=>동시성은 하나의 CPU Core(연산장치)를 이용해서 여러 개의 스레드를 처리하는 
방식으로 실제로는 동시에 되는 것이 아니고 번갈아 가면서 처리하는 개념입니다.
=>병렬처리는 실제로 CPU가 여러 개 이거나 MultiCore CPU를 가지고 작업을 동시에 
처리하는 방식입니다. 
=>JDK1.8부터 Stream을 이용한 병렬 처리가 가능합니다. 
병렬처리를 만들 때는 고민을 해야합니다.
각각의 처리가 다른 처리에 영향이 없을 때 병렬처리를 수행할 수 있습니다. 
컬렉션을 만들고 parallelStream()을 호출해서 병렬 처리 스트림을 만들거나 기존의 
스트림에서 parallel()이라는 메소드를 호출해서 병렬 처리 스트림을 만들어서 처리하 
면 됩니다.  

		    //빠른 열거를 이용해서 작업 
	    
		    for(Student student : list) {
			   working(student);
		    }

		    
		    //일반 스트림을 이용한 처리 

		    list.stream().forEach(
		    		student -> working(student));
	
		    
		    //병렬 스트림을 이용한 처리 
		    list.parallelStream().forEach(
		    		student -> working(student));
		    long end = System.currentTimeMillis();
		    System.out.printf("걸린시간:%d\n", end-start);
		    
		    
 
  

  
 */
}
