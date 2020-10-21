import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {

	public static void main(String[] args) {
		try {
			Class<?> helloClass = new HelloClassLoader().findClass("Hello");
			invoke(helloClass, "hello");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Class<?> findClass(String className) throws ClassNotFoundException {
		byte[] bytes = readClassFile(className);
		for (int i = 0; i < bytes.length; ++i) {
			bytes[i] = (byte)(255 - bytes[i]);
		}
		return defineClass(className, bytes, 0, bytes.length);
	}

	private byte[] readClassFile(String className) {
		try {
			String filePath = System.getProperty("user.dir");
			Path path = Paths.get(filePath).resolve(className + ".xlass");
			return Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static void invoke(Class<?> CL, String methodName) {
		try {
			Object obj = CL.getDeclaredConstructor().newInstance();
			Method method = CL.getMethod("hello");
			if (method != null) {
				method.setAccessible(true);
				method.invoke(obj);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
}