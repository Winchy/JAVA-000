# Spring Bean 的装配

**Spring Bean 的装配**

- XML配置

  - 配置方式：

    ``` xml
      <bean id="animal" class="com.colin.hwk.xmlbeans.Dog">
          <property name="name" value="Dog" />
      </bean>
    ```

    - 为bean标签添加`autowire`属性，可通过`@Autowired`注解注入，而在xml中不需要配置`ref`:

    ``` xml
    <bean id="farm" class="com.colin.hwk.xmlbeans.Farm" autowire="byName" />
    ```

    ``` java
      @Autowired
      private IAnimal animal;
    ```

- 通过`ComponentScan`和注解装载和注入
  
  - component scan:

    ``` xml
    <context:component-scan base-package="com.colin.hwk.annotationbeans" />
    ```

    或

    ``` java
    @ComponentScan("com.colin.hwk.autoconfig")
    public class AutoConfigureApplicationContext {
      ...
    }
    ```

  - 注解方式：

    ``` java
    @Component
    public class Duck implements IAnimal {
        public void bark() {
            System.out.println("Ducks bark like 'Quark'!");
        }
    }

    @Service
    public class DuckService {
        @Resource
        Duck duck;
        public void serve() {
            System.out.println("Duck service serves ducks");
            duck.bark();
        }
    }
    ```

- 通过`@Configuration`和`@Bean`装载Bean：

  ``` java
  @Configuration
  public class AnimalConfig {
    @Bean("cat")
    public IAnimal getCat() {
        return new Cat();
    }
  }
  ```

- 通过`@Conditional`有条件装载

  ``` java
  @Configuration
  public class AnimalConfig {

      @Bean("cat")
      public IAnimal getCat() {
          return new Cat();
      }

      @Bean("whiteCat")
      @Conditional(WhiteCondition.class)
      public IAnimal getWhiteCat() {
          return new Cat("white");
      }

      @Bean("blackCat")
      @ConditionalOnBean(name = "whiteCat")
      public IAnimal getBlackCat() {
          System.out.println("black cat goes with white cat!");
          return new Cat("black");
      }
  }

  public class WhiteCondition implements Condition {
      @Override
      public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
          return true;
      }
  }
  ```

- `@AutoConfigurationPackage`
