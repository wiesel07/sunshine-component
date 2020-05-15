package cn.sunshine.component.runtime.spring;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * Spring上下文ApplicationContext对象
 * </p>
 *
 * @author wuj
 * @since 2019年7月18日
 */
@Slf4j
@Component
public class RuntimeApplicationContext implements BeanDefinitionRegistryPostProcessor,  ApplicationContextAware{

	 /**
    * 系统中的context对象
    */
   private static ApplicationContext context = null;
	
   
   /**
    * 
    * 获取Spring上下文ApplicationContext对象
    * 
    * @return ApplicationContext对象
    */
   public static ApplicationContext getContext() {
       if (context == null) {
         log.error("当前context为空,可能是Spring配置文件中没有配置加载本类[{}]!", RuntimeApplicationContext.class.getName());
         throw new IllegalStateException("当前没有Spring的applicationContext注入,请确定是否有配置Spring,并在Spring中配置了本类的注入!"
                  + RuntimeApplicationContext.class);
       }       
       return context;
   }
   
   /**
    * 取指定类型的Bean,如果不存在或存在多于1个,则抛出异常IllegalStateException.
    * 
    * @param <E> E
    * @param type type
    * @return 指定类型的Bean
    */
   @SuppressWarnings("unchecked")
   public static <E> E getBeanByType(Class<? extends E> type) {
       try {
           String[] beanNames = getContext().getBeanNamesForType(type);
           if (beanNames != null && beanNames.length == 1) {
               return (E) getContext().getBean(beanNames[0]);
           }

           if (beanNames == null || beanNames.length == 0) {
               throw new IllegalStateException("未找到指定类型的Bean定义.");
           }

           throw new IllegalStateException("找到多个同类型的Bean定义.");

       } catch (Exception e) {
           log.error("根据类型在Spring上下文查找对象出错:" + type, e);
           throw new IllegalStateException("根据类型在Spring上下文查找对象出错:" + type, e);
       }
   }
   
   /**
    * 
    * 从Spring Context中获取指定的Bean
    * 
    * @param <E> E
    * @param beanName bean的名称
    * @return bean对象
    */
   @SuppressWarnings("unchecked")
   public static <E> E getBean(String beanName) {
       try {
           return (E) getContext().getBean(beanName);
       } catch (Exception e) {
           // logger.error("在Spring上下文查找对象出错:" + beanName, th);
           throw new IllegalStateException("在Spring上下文查找对象出错:" + beanName);
       }
   }

   @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String,Object> getBeansWithAnnotation(Class annotationClass){
   	return getContext().getBeansWithAnnotation(annotationClass);
   }
   
   public static void initAutowireFields(Object instance) {
   	getContext().getAutowireCapableBeanFactory().autowireBean(instance);
   }
   
   @SuppressWarnings("unchecked")
	public static <T> Map<String, T> getBeansOfType(Class<T> type){
   	Map<String, T> result = getContext().getBeansOfType(type);
   	if(result.isEmpty()) {
   		log.warn("getBeansOfType获取异常使用class.forName()再次获取：{}", type);
   		//存在问题 类加载器不一致情况 暂时未解决 单独启动争财产，ide中启动存在异常。其中type对应的接口实现类已经进行初始化了
   		//此时 直接class.forname进行初始化值 
   		//怀疑是类加载器问题。
   		try {
   			result = (Map<String, T>) getContext().getBeansOfType(Class.forName(type.getName()));
			} catch (Exception e) {
				log.error("Error get spring applicationContext getBeansOfType {}", type.getName());
				throw new RuntimeException(e);
			}
   		
   	}
   	return result;
   }
   
   /**
    * 从Spring Context中获取指定的Bean
    * 
    * @param <E> E
    * @param clazz clazz
    * @return 指定的Bean
    */
   public static <E> E getBean(Class<E> clazz) {
       return getBeanByType(clazz);
       // return getBean(clazz.getName());
   }

   /**
    * 
    * 是否有指定的Bean存在.
    * 
    * @param beanName beanName
    * @return 是否有指定的Bean存在.
    */
   public static boolean containBean(String beanName) {
       return getContext().containsBean(beanName);
   }
   
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		log.debug("Prepare injection spring applicationcontext[{}]", applicationContext.toString());

       if (RuntimeApplicationContext.context != null) {
       	log.warn("注意,已经注入过Spring上下文[{}],请检查配置是否有问题导致重复注入!", RuntimeApplicationContext.context.toString());
       }
		RuntimeApplicationContext.context = applicationContext;
		
	}

	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		// TODO Auto-generated method stub
		
	}

}

