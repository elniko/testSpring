package test;
import java.lang.reflect.Field;
import java.util.Random;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;




public class RandomQuantityAnnotationBeanPostProcessor implements BeanPostProcessor {

	public Object postProcessAfterInitialization(Object bean, String arg1)
			throws BeansException {
		
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String arg1)
			throws BeansException {
		Field[] fields = bean.getClass().getDeclaredFields();
		for(Field field : fields) {
			RandomQuantity annotation = field.getAnnotation(RandomQuantity.class);
			if(annotation != null) {
				int min = annotation.min();
				int max = annotation.max(); 
				Random random = new Random();
				int rand = min + random.nextInt(max-min);
				field.setAccessible(true);
				ReflectionUtils.setField(field, bean, rand);
				
			}
		}
		return bean;
	}

}
