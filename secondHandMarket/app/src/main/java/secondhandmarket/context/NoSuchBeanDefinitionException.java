package secondhandmarket.context;

public class NoSuchBeanDefinitionException extends RuntimeException {
    public NoSuchBeanDefinitionException(Class<?> type) {
        super("No bean of type " + type.getName() + " found");
    }
}
