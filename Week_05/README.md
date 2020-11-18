# 作业

## 周四第二题

工程：hwk05-1

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

## 周六第四题

工程：hwk05-2

实现自动配置和Starter流程：

- 为主类添加`@SpringBootApplication`注解，该注解包含`@EnableAutoConfiguration`;
- 创建对象`io.kimmking.config.SchoolAutoConfiguration`并添加注解`@Configuration`;
- 在`io.kimmking.config.SchoolAutoConfiguration`中实现Bean工厂方法：
    
    ```java
    @Bean("student100")
	  public Student getStudent100() {
      return new Student(100, "liangchaowei");
    }
    ```

- 在resources目录下创建`META-INF`目录，在该目录下创建文件`spring.factories`，并输入：

    ``` text
    org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
    io.kimmking.config.SchoolAutoConfiguration
    ```

- 在`META-INF`目录下创建文件`spring.provides`，输入：

  ```
  provides: school-starter
  ```

## 周六第六题

工程：hwk05-2

实现：`io.kimmking.jdbc.JDBCService`