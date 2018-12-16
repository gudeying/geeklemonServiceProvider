package cn.geeklemon.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;



	@Retention(value = RetentionPolicy.RUNTIME)
	@interface Meta {

	    String length();

	    String name();

	    int age();

	}

	public class MetaTest {

	    @Meta(age = 0, length = "2", name = "测试注解")
	    public static void myMetch() throws NoSuchMethodException, SecurityException {
	        MetaTest obj = new MetaTest();

	        Class<?> c = obj.getClass();

	        System.out.println("c:" + c.getConstructors().length);

	        Method m = c.getMethod("myMetch");
	        System.out.println("m:" + m.getName());
	        Meta meta = m.getAnnotation(Meta.class);

	        System.out.println("meta.length():" + meta.length());
	        System.out.println("meta.name():" + meta.name());
	        System.out.println("meta.age():" + meta.age());

	    }

	    @Meta(age = 30, length = "80", name = "测试带参数方法注解")
	    public static void myMetch(String name, int age) throws NoSuchMethodException, SecurityException {
	        MetaTest obj = new MetaTest();

	        Class<?> c = obj.getClass();

	        System.out.println("c:" + c.getConstructors().length);

	        Method m = c.getMethod("myMetch", String.class, int.class);
	        System.out.println("m:" + m.getName());
	        Meta meta = m.getAnnotation(Meta.class);

	        System.out.println("meta.length():" + meta.length());
	        System.out.println("meta.name():" + meta.name());
	        System.out.println("meta.age():" + meta.age());

	    }

	    public static void main(String[] args) {

	        try {
	            myMetch("小熊", 31);
	        } catch (NoSuchMethodException e) {
	            e.printStackTrace();
	        } catch (SecurityException e) {
	            e.printStackTrace();
	        }
	    }

	}
