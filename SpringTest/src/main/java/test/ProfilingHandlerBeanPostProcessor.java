package test;

import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor{

	private HashMap<String, Class> map = new HashMap<String, Class>();
	
	private ProfilingController controller = new ProfilingController();
	
	
	public ProfilingHandlerBeanPostProcessor () throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException {
		System.out.println("BPP");
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		server.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
		
	}
	
	public Object postProcessAfterInitialization(final Object bean, String beanName)
			throws BeansException {
		Class beanClass = map.get(beanName);
		if(beanClass != null) {
			Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
				
				public Object invoke(Object proxy, Method method, Object[] args)
						throws Throwable {
					if(controller.isEnable()) {
					System.out.println("Profiling start");
					long before = System.nanoTime();
					Object retval = method.invoke(bean, args);
					long after = System.nanoTime();
					System.out.println(after-before);
					System.out.println("Profiling end");
					return retval;
					} else {
						return method.invoke(bean, args);
					}
				}
			});
		}
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		
		if(bean.getClass().isAnnotationPresent(Profiling.class)) {
		  map.put(beanName, bean.getClass());	
		}
		
		return bean;
	}

}
