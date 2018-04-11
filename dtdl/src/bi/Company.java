package bi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;

/**
 * 中介公司
 * 要实现接口Rent
 * 
 * InvocationHandler : 处理执行器. 类似公司企业中的岗前培训, 让员工有对应的业务能力.
 */
public class Company implements InvocationHandler {

	private Rent host = new Host();
	
	/**
	 * 在公司找房产中介工作人员. 房产经纪人
	 * Proxy.newProxyInstance(loader, interfaces, h)
	 * Proxy - JDK提供的专门用于创建接口的动态代理实现类型的工具.
	 * newProxyInstance - 创建动态代理实现类对象的方法.
	 * 参数 - loader - 类加载器. 用于通过反射,动态的编写一个类型的工具.
	 * 参数 - interfaces - 动态代理实现类型要实现的接口数组. Class[]
	 * 参数 - h - InvocationHandler类型的对象. 动态代理实现类的对象,在提供接口方法的时候,应该怎么做.
	 * 
	 * @return
	 */
	public Rent getProxy(){
		ClassLoader loader = host.getClass().getClassLoader();
		// 要实现的接口,应该是真实的对象,实现的所有的接口.
		Class[] interfaces = host.getClass().getInterfaces();
		InvocationHandler h = new Company();
		
		Rent emp = (Rent) Proxy.newProxyInstance(loader, interfaces, h);
		
		return emp;
	}

	/**
	 * 岗前培训. 教员工如何工作.
	 * 最终的目的,就是调用业主的出租房屋方法.
	 * 
	 * @param proxy - 代理对象, 就是员工.
	 * @param method - 真实的方法, 就是要调用的最终方法. 业主出租访问的方法.
	 * @param args - 真实方法要执行的时候,需要的参数.
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object returnValue =  null;
		int i = 0;
		int j = 0;
		Random r = new Random();
		i = r.nextInt(100);
		
		if(this.show(i)){
			j = r.nextInt(100);
			if(this.lower(j)){
				// 调用业主的出租方法.
				returnValue = method.invoke(host, args);
				this.keepOn();
			}
		}
		return returnValue;
	}
	
	private void keepOn(){
		System.out.println("续签");
	}
	
	private boolean lower(int i){
		if(i%2 == 0){
			System.out.println("讲价 - 满意");
			return true;
		}else{
			System.out.println("讲价 - 不满意");
			return false;
		}
	}
	
	private boolean show(int i){
		if(i%2 == 0){
			System.out.println("看房 - 满意");
			return true;
		}else{
			System.out.println("看房 - 不满意");
			return false;
		}
	}
	
}
