package bi;

public class Client {
	
	public static void main(String[] args) {
		Company c = new Company();
		
		Rent proxy = c.getProxy();
		
		System.out.println(proxy.getClass().getName());
		
		proxy.rent();
		
	}

}
