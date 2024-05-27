package secondhandmarket.context;

import secondhandmarket.controller.Component;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    ApplicationContext parent;
    String[] basePackages;
    Map<String, Object> beans = new HashMap<>();

    public ApplicationContext(Map<String, Object> beanMap, String... basePackages) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        beans.putAll(beanMap);
        this.basePackages = basePackages;

        for (String basePackage : basePackages) {
            findComponents(new File("./build/classes/java/main"), basePackage, "");
        }
    }

    public ApplicationContext(ApplicationContext parent, String... basePackages) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.parent = parent;
        this.basePackages = basePackages;
        for (String basePackage : basePackages) {
            findComponents(new File("./build/classes/java/main"), basePackage, "");
        }
    }

    private void findComponents(File dir, String basePackage, String packageName) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //패키지이거나 클래스 파일이면서 중첩클래스가 아닌 파일
        File[] files = dir.listFiles(file ->
                file.isDirectory() ||
                        (file.isFile() && !file.getName().contains("$") && file.getName().contains(".class"))
        );

        if (packageName.length() > 0) {
            packageName += ".";
        }

        for (File file : files) {
            if (file.isFile()) {
                //basePackage의 패키지가 아니면 스킵한다.
                if (!packageName.startsWith(basePackage)) {
                    continue;
                }

                //파일명에 packageName을 붙이고 .class를 지워 클래스를 얻는다
                Class<?> clazz = Class.forName(packageName + file.getName().replace(".class",""));

                //@Component가 붙은 클래스를 찾는다.
                Component componentAnno = clazz.getAnnotation(Component.class);
                if (componentAnno == null) {
                    continue;
                }

                //객체를 만들 준비를 한다.
                Constructor<?> constructor = clazz.getConstructors()[0]; //클래스의 기본생성자를 얻는다.
                Parameter[] params = constructor.getParameters();
                Object[] args = getArguments(params);
                //객체를 beans에 넣는다.
                beans.put(componentAnno.value().length() == 0 ? clazz.getName() : componentAnno.value(),
                        constructor.newInstance(args));


            } else { //패키지 내부로 들어가 findComponent를 실행한다.
                findComponents(file, basePackage, packageName + file.getName());
            }
        }
    }

    private Object[] getArguments(Parameter[] params) {
        Object[] arguments = new Object[params.length];
        for (int i = 0; i < arguments.length; i++) {
            arguments[i] = getBean(params[i].getType());
        }
        return arguments;
    }

    private Object getBean(Class<?> paramType) {
        //현재 IoC 컨테이너에 등록된 bean들에서 찾는다.
        Collection<Object> objs = beans.values();
        for(Object obj : objs) {
            if (paramType.isInstance(obj)) {
                return obj;
            }
        }

        //없으면 부모 IoC 컨테이너에서 찾는다.
        if (parent != null) {
            return parent.getBean(paramType);
        }

        //그래도 없다면 해당 빈을 찾을 수 없다는 예외를 던진다.
        return new NoSuchBeanDefinitionException(paramType);

    }

    public Collection<Object> getBeans() {
        return beans.values();
    }
}
