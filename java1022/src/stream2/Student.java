package stream2;

public class Student {

	//필요한 속성을 정의 
	//접근 지정자가 없는 경우라면 여기서 종료 
	
	private int num;
	private String name;
	private String gender; 
	private int score;
	
	// !! 중요 Default Constructor -매개변수가 없는 생성자 
	//new 했을 때 default Constructor 
	//가 없다는 에러메시지가 보이면
	//이 클래스는 매개변수가 없는 생성자가 없는 것이므로 
	//매개변수를 대입해서 객체를 생성해야 합니다. 
	
	//어떤 클래스를 상속받았는데 동일한 메세지가 보이면 
	//생성자를 만들어서 상위클래스의 생성자(super())를 
	//반드시 호출해야 합니다.  - 안드로이드에서 많이 발생 
	public Student() {
		super();		
	}
	
	//모든 속성의 값을 매개변수로 받아서 객체를 생성하는 생성자 
	//set을 호출하지 않고 객체의 속성값들을 
	//한번에 채울 수 있습니다. 
	public Student(int num, String name, String gender, int score) {
		super();
		this.num = num;
		this.name = name;
		this.gender = gender;
		this.score = score;
	}

    //접근자 메소드 
	//속성이 private이라서 외부에서 접근을 못하기 때문에 
	//public으로 접근자 메소드를 만들어서 
	//접근하기 위해서 생성 - 가장 일반적인 목적 
	//swift나 kotlin은 이 작업은 자동  
	public int getNum() {		
		return num;
	}
	public void setNum(int num) {		
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	} 
	
	//toString는 객체를 문자열로 만들어주는 메소드 
	//속성들의 값을 빠르게 확인하기 위해서 생성 
	//toString이 없으면 get메소드를 
	//전부 호출해서 값을 확인해야 합니다.
	//toString을 재정의하면 roString만 호출하면 한번에 
	//값을 확인할 수 있습니다. 
	// 디버깅(논리적인 오류나 예외를 해결할 목적으로 수행)
	// 을 위한 메소드 
	@Override
	public String toString() {
		return "Student [num=" + num + ", name=" + name + ", gender=" + gender + ", score=" + score + "]";
	}


}
