package stream2;

import java.util.LinkedList;

public class StreamMain2 {
   
	public static void working(Student student) {
    	try {
    		Thread.sleep(2000);
    	    System.out.println(student);
    	} catch(Exception e) {
    		
    	}
    }
    
	public static void main(String[] args) {
		
		LinkedList<Student> list = 
				new LinkedList<>();
			list.add(new Student(1,  "이민아", "여자", 90));
			list.add(new Student(2, "김기범", "남자", 98));
			list.add(new Student(3, "이진아", "여자", 85));
			list.add(new Student(4, "김수진", "여자", 84));
			list.add(new Student(5, "허예림", "여자", 77));
			list.add(new Student(6, "나종욱", "남자", 87));
		    list.add(new Student(7, "이민섭", "남자", 74));
			
		    long start = System.currentTimeMillis();
		    //빠른 열거를 이용해서 작업 
/*		    
		    for(Student student : list) {
			   working(student);
		    }
*/
		    
		    //일반 스트림을 이용한 처리 
/*
		    list.stream().forEach(
		    		student -> working(student));
*/		
		    
		    //병렬 스트림을 이용한 처리 
		    list.parallelStream().forEach(
		    		student -> working(student));
		    long end = System.currentTimeMillis();
		    System.out.printf("걸린시간:%d\n", end-start);
		    
		    
	}

}
