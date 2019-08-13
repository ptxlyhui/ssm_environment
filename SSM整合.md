# SSM整合

spring和springMVC是天然集成,所以只需要解决mybatis和spring整合的问题 , 中间项目mybatis-spring项目进行整合 

- 由spring容器管理mybatis和mapper.
- 由spring利用声明式事物(==AOP==)进行事物综合管理.



## 整合目录
![image](https://github.com/ptxlyhui/ssm_environment/blob/master/iamge/%E6%95%B4%E5%90%88%E7%9B%AE%E5%BD%95.jpeg)

## 引入依赖

- pom.xml文件

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  
  <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
  
      <groupId>com.lyhui</groupId>
      <artifactId>MySSM</artifactId>
      <version>1.0-SNAPSHOT</version>
      <packaging>war</packaging>
  
      <name>MySSM Maven Webapp</name>
      <!-- FIXME change it to the project's website -->
      <url>http://www.example.com</url>
  
      <properties>
          <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
          <maven.compiler.source>1.8</maven.compiler.source>
          <maven.compiler.target>1.8</maven.compiler.target>
          <!--spring版本指定-->
          <spring.version>5.1.8.RELEASE</spring.version>
          <jackson.version>2.9.9</jackson.version>
      </properties>
  
      <dependencies>
          <dependency>
              <groupId>junit</groupId>
              <artifactId>junit</artifactId>
              <version>4.12</version>
              <scope>test</scope>
          </dependency>
          <!--spring依赖start=======-->
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-core</artifactId>
              <version>${spring.version}</version>
          </dependency>
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-context</artifactId>
              <version>${spring.version}</version>
          </dependency>
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-context-support</artifactId>
              <version>${spring.version}</version>
          </dependency>
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-beans</artifactId>
              <version>${spring.version}</version>
          </dependency>
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-web</artifactId>
              <version>${spring.version}</version>
          </dependency>
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-webmvc</artifactId>
              <version>${spring.version}</version>
          </dependency>
  
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-aop</artifactId>
              <version>${spring.version}</version>
          </dependency>
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-aspects</artifactId>
              <version>${spring.version}</version>
          </dependency>
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-jdbc</artifactId>
              <version>${spring.version}</version>
          </dependency>
  
          <!--利用这个处理事务问题-->
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-tx</artifactId>
              <version>${spring.version}</version>
          </dependency>
          <!--spring依赖======END======-->
  
  
          <!--引入Json依赖-->
          <dependency>
              <groupId>com.fasterxml.jackson.core</groupId>
              <artifactId>jackson-databind</artifactId>
              <version>${jackson.version}</version>
          </dependency>
  
          <dependency>
              <groupId>com.fasterxml.jackson.core</groupId>
              <artifactId>jackson-core</artifactId>
              <version>${jackson.version}</version>
          </dependency>
  
          <dependency>
              <groupId>com.fasterxml.jackson.core</groupId>
              <artifactId>jackson-annotations</artifactId>
              <version>${jackson.version}</version>
          </dependency>
  
          <dependency>
              <groupId>net.sf.json-lib</groupId>
              <artifactId>json-lib</artifactId>
              <version>2.4</version>
              <classifier>jdk15</classifier>
          </dependency>
  
          <!--添加处理json为javabean-->
          <dependency>
              <groupId>org.codehaus.jackson</groupId>
              <artifactId>jackson-core-asl</artifactId>
              <version>1.9.13</version>
          </dependency>
  
          <dependency>
              <groupId>org.codehaus.jackson</groupId>
              <artifactId>jackson-mapper-asl</artifactId>
              <version>1.9.13</version>
          </dependency>
          <!--添加处理json为javabean==end==-->
  
  
          <!--添加文件上传依赖-->
          <!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
          <dependency>
              <groupId>commons-fileupload</groupId>
              <artifactId>commons-fileupload</artifactId>
              <version>1.3.3</version>
          </dependency>
          <!--文件上传依赖=====END=======-->
  
  
          <!--持久层依赖-->
          <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
          <dependency>
              <groupId>mysql</groupId>
              <artifactId>mysql-connector-java</artifactId>
              <version>5.1.38</version>
          </dependency>
          <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
          <dependency>
              <groupId>org.mybatis</groupId>
              <artifactId>mybatis</artifactId>
              <version>3.4.6</version>
          </dependency>
          <!--持久层依赖====END====-->
  
          <!--日志依赖-->
          <dependency>
              <groupId>org.slf4j</groupId>
              <artifactId>slf4j-api</artifactId>
              <version>1.7.26</version>
          </dependency>
          <dependency>
              <groupId>org.slf4j</groupId>
              <artifactId>slf4j-log4j12</artifactId>
              <version>1.7.26</version>
          </dependency>
          <dependency>
              <groupId>log4j</groupId>
              <artifactId>log4j</artifactId>
              <version>1.2.17</version>
          </dependency>
          <!--日志依赖引入====END====-->
  
          <!--数据源的引入，池化技术。-->
          <dependency>
              <groupId>com.mchange</groupId>
              <artifactId>c3p0</artifactId>
              <version>0.9.5.2</version>
          </dependency>
  
          <!--mybatis与spring整合所需要的依赖-->
          <dependency>
              <groupId>org.mybatis</groupId>
              <artifactId>mybatis-spring</artifactId>
              <version>2.0.1</version>
          </dependency>
  
          <!--servlet jsp jstL 等依赖========-->
          <dependency>
              <groupId>javax.servlet</groupId>
              <artifactId>javax.servlet-api</artifactId>
              <version>3.1.0</version>
              <scope>provided</scope>
          </dependency>
          <dependency>
              <groupId>javax.servlet.jsp</groupId>
              <artifactId>javax.servlet.jsp-api</artifactId>
              <version>2.3.1</version>
              <scope>provided</scope>
          </dependency>
          <dependency>
              <groupId>javax.servlet</groupId>
              <artifactId>jstl</artifactId>
              <version>1.2</version>
          </dependency>
          <!--servlet jsp jstL等依赖====END====-->
  
          <!--处理时间日期格式-->
          <dependency>
              <groupId>joda-time</groupId>
              <artifactId>joda-time</artifactId>
              <version>2.9.9</version>
          </dependency>
  
          <!--mybatis分页依赖========-->
          <dependency>
              <groupId>com.github.pagehelper</groupId>
              <artifactId>pagehelper</artifactId>
              <version>5.1.2</version>
          </dependency>
  
          <!--apache用于MD5加密========-->
          <dependency>
              <groupId>commons-codec</groupId>
              <artifactId>commons-codec</artifactId>
              <version>1.10</version>
          </dependency>
  
          <!-- SpringAOP需要的依赖 -->
          <dependency>
              <groupId>org.aspectj</groupId>
              <artifactId>aspectjrt</artifactId>
              <version>1.9.2</version>
          </dependency>
          <dependency>
              <groupId>org.aspectj</groupId>
              <artifactId>aspectjweaver</artifactId>
              <version>1.9.2</version>
          </dependency>
      </dependencies>
  
      <build>
          <finalName>MySSM</finalName>
          <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
              <plugins>
                  <plugin>
                      <artifactId>maven-clean-plugin</artifactId>
                      <version>3.1.0</version>
                  </plugin>
                  <!-- see http://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_war_packaging -->
                  <plugin>
                      <artifactId>maven-resources-plugin</artifactId>
                      <version>3.0.2</version>
                  </plugin>
                  <plugin>
                      <artifactId>maven-compiler-plugin</artifactId>
                      <version>3.8.0</version>
                  </plugin>
                  <plugin>
                      <artifactId>maven-surefire-plugin</artifactId>
                      <version>2.22.1</version>
                  </plugin>
                  <plugin>
                      <artifactId>maven-war-plugin</artifactId>
                      <version>3.2.2</version>
                  </plugin>
                  <plugin>
                      <artifactId>maven-install-plugin</artifactId>
                      <version>2.5.2</version>
                  </plugin>
                  <plugin>
                      <artifactId>maven-deploy-plugin</artifactId>
                      <version>2.8.2</version>
                  </plugin>
              </plugins>
          </pluginManagement>
      </build>
  </project>
  
  ```



## web.xml文件

- web-inf文件夹下web.xml文件

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns="http://xmlns.jcp.org/xml/ns/javaee"
           xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
      http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
           id="WebApp_ID" version="3.1">
  
      <!-- 设置jsp/index.jsp为首页 -->
      <welcome-file-list>
          <welcome-file>jsp/index.jsp</welcome-file>
      </welcome-file-list>
  
      <!--    优先级:监听器->过滤器->拦截器->servlet  -->
  
      <!-- 指定整个程序上下文信息 -->
      <context-param>
          <param-name>contextConfigLocation</param-name>
          <param-value>classpath:spring/applicationContext.xml</param-value>
      </context-param>
  
      <!--注册一个spring启动监听器-->
      <listener>
          <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
      </listener>
  
  
      <!--处理post中文乱码问题-->
      <filter>
          <filter-name>characterEncodingFilter</filter-name>
          <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
          <!--指定编码-->
          <init-param>
              <param-name>encoding</param-name>
              <param-value>UTF-8</param-value>
          </init-param>
          <init-param>
              <param-name>forceRequestEncoding</param-name>
              <param-value>true</param-value>
          </init-param>
          <init-param>
              <param-name>forceResponseEncoding</param-name>
              <param-value>true</param-value>
          </init-param>
      </filter>
      <filter-mapping>
          <filter-name>characterEncodingFilter</filter-name>
          <url-pattern>/*</url-pattern>
      </filter-mapping>
  
      <!--注册一个支持所有http请求的过滤器-->
      <filter>
          <filter-name>hiddenHttpMethodFilter</filter-name>
          <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
      </filter>
      <filter-mapping>
          <filter-name>hiddenHttpMethodFilter</filter-name>
          <url-pattern>/*</url-pattern>
      </filter-mapping>
  
      <!--注册一个前端控制器
     DispatcherServlet
  -->
      <servlet>
          <servlet-name>springMVC</servlet-name>
          <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
          <init-param>
              <!--指定上下文配置的位置-->
              <param-name>contextConfigLocation</param-name>
              <!--类路径下的springMVC.xml文件-->
              <param-value>classpath:spring/applicationContext.xml</param-value>
          </init-param>
          <load-on-startup>1</load-on-startup>
      </servlet>
      <!--处理所有请求 -->
      <!--servlet映射配置-->
      <servlet-mapping>
          <servlet-name>springMVC</servlet-name>
          <!--这里统一写斜杆-->
          <url-pattern>/</url-pattern>
      </servlet-mapping>
      <!--注册器====END====-->
    
  </web-app>
  ```

## applicationContext.xml

- resoueces/spring文件夹下applicationContext.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <!--spring-*.xml   代指同一路径下的所有 spring-xxx.xml 文件-->
      <!--引入spring和其他整合的配置文件-->
      <import resource="classpath:spring/spring-*.xml"></import>
  </beans>
  ```

## spring-context.xml

- resoueces/spring文件夹下spring-context.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xmlns:c="http://www.springframework.org/schema/c"
         xmlns:cache="http://www.springframework.org/schema/cache"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:jdbc="http://www.springframework.org/schema/jdbc"
         xmlns:jee="http://www.springframework.org/schema/jee"
         xmlns:lang="http://www.springframework.org/schema/lang"
         xmlns:mvc="http://www.springframework.org/schema/mvc"
         xmlns:p="http://www.springframework.org/schema/p"
         xmlns:task="http://www.springframework.org/schema/task"
         xmlns:tx="http://www.springframework.org/schema/tx"
         xmlns:util="http://www.springframework.org/schema/util"
         xsi:schemaLocation="http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee.xsd
  		http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
  		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
  		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
  		http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd
  		http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc.xsd
  		http://www.springframework.org/schema/cache http://www.springframework.org/schema/cache/spring-cache.xsd
  		http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task.xsd
  		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
  		http://www.springframework.org/schema/lang http://www.springframework.org/schema/lang/spring-lang.xsd
  		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd ">
  
      <!--做spring的基础配置-->
      <!--1、spring容器注册-->
      <context:annotation-config/>
  
      <!--2、自动扫描配置-->
      <context:component-scan base-package="com.lyhui.service"/>
  
      <!--3、激活aop注解方式的代理-->
      <aop:aspectj-autoproxy/>
  
      <!--消息格式的转换-->
      <bean id="conversionService" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
          <property name="registerDefaultFormatters" value="false"/>
          <property name="formatters">
              <set>
                  <bean class="org.springframework.format.number.NumberFormatAnnotationFormatterFactory"></bean>
              </set>
          </property>
          <property name="formatterRegistrars">
              <set>
                  <bean class="org.springframework.format.datetime.joda.JodaTimeFormatterRegistrar">
                      <property name="dateFormatter">
                          <bean class="org.springframework.format.datetime.joda.DateTimeFormatterFactoryBean">
                              <property name="pattern" value="yyyyMMdd"/>
                          </bean>
                      </property>
                  </bean>
              </set>
          </property>
      </bean>
  
  </beans>
  ```



## spring-mybatis.xml文件

- resources/spring文件夹下spring-mybatis.xml文件

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xmlns:c="http://www.springframework.org/schema/c"
         xmlns:cache="http://www.springframework.org/schema/cache"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:jdbc="http://www.springframework.org/schema/jdbc"
         xmlns:jee="http://www.springframework.org/schema/jee"
         xmlns:lang="http://www.springframework.org/schema/lang"
         xmlns:mvc="http://www.springframework.org/schema/mvc"
         xmlns:p="http://www.springframework.org/schema/p"
         xmlns:task="http://www.springframework.org/schema/task"
         xmlns:tx="http://www.springframework.org/schema/tx"
         xmlns:util="http://www.springframework.org/schema/util"
         xsi:schemaLocation="http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee.xsd
  		http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
  		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
  		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
  		http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd
  		http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc.xsd
  		http://www.springframework.org/schema/cache http://www.springframework.org/schema/cache/spring-cache.xsd
  		http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task.xsd
  		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
  		http://www.springframework.org/schema/lang http://www.springframework.org/schema/lang/spring-lang.xsd
  		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd ">
  
      <!--spring和mybatis整合文件-->
  
      <!--扫描mapper接口-->
      <context:component-scan base-package="com.lyhui.mapper">
          <!--排除Controller注解-->
          <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
      </context:component-scan>
  
      <!-- 加载资源文件 -->
      <context:property-placeholder
              location="classpath:db.properties"/>
      <!-- 配置数据源 本项目使用c3p0连接池-->
      <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
          <property name="driverClass" value="${jdbc.driver}"/>
          <property name="jdbcUrl" value="${jdbc.url}"/>
          <property name="user" value="${jdbc.username}"/>
          <property name="password" value="${jdbc.password}"/>
          <!--如果有需要,请把所有的属性全部写到properties文件当中去-->
          <!-- c3po链接池的私有属性 -->
          <property name="initialPoolSize" value="10"/>
          <property name="maxPoolSize" value="30"/>
          <property name="minPoolSize" value="10"/>
          <!-- 关闭连接后不自动commit-->
          <property name="autoCommitOnClose" value="false"/>
          <!-- 获取链接超时时间 -->
          <property name="checkoutTimeout" value="100000"/>
          <!-- 当获取连接失败重试次数 -->
          <property name="acquireRetryAttempts" value="2"/>
      </bean>
  
      <!--最后关键一步，整合mybatis事务功能-->
      <!--1、注入一股mybatis的sqlsessionFactory，这就是我们所需要做的关键步骤
          2、声明式的事务管理
      -->
      <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
          <property name="dataSource" ref="dataSource"/>
          <!--引入mappers文件-->
          <!--<property name="configLocation" value="classpath:mybatis-config.xml"></property>-->
          <!--这就要求所有的mapper文件必须在com/lyhui/mapper/之下-->
          <property name="mapperLocations"
                    value="classpath:com/lyhui/mapper/**/*.xml"/>
          <property name="configuration">
              <bean class="org.apache.ibatis.session.Configuration">
                  <!--可以加入驼峰命名，其他mybatis的配置也就是mybatis.cfg.xml的相关配置都会转移到这里来-->
                  <property name="mapUnderscoreToCamelCase" value="true"/>
              </bean>
          </property>
          <!--插件配置-->
          <property name="plugins">
              <array>
                  <!--分页插件的配置 拦截器实现分页功能-->
                  <bean class="com.github.pagehelper.PageInterceptor">
                      <!--这里的几个配置主要演示如何使用，如果不理解，一定要去掉下面的配置-->
                      <property name="properties">
                          <value>
                              helperDialect=mysql
                              reasonable=true
                              supportMethodsArguments=true
                              params=count=countSql
                              autoRuntimeDialect=true
                          </value>
                      </property>
                  </bean>
              </array>
          </property>
      </bean>
  
      <!--持久层接口-->
      <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
          <property name="basePackage" value="com.lyhui.mapper"/>
          <property name="sqlSessionFactoryBeanName" value="sqlSessionFactoryBean"/>
      </bean>
  
      <!--事务管理  使用数据源事务管理类进行管理-->
      <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSource"/>
      </bean>
      <!--确定事务管理的策略transaction-manager：指向上卖弄的transactionManage-->
      <tx:advice transaction-manager="transactionManager" id="transactionAdvice">
          <!--事务处理的相关值以及他的传播性-->
          <tx:attributes>
              <!--查询相关配置为只读，select开头或者get 或者query-->
              <tx:method name="select*" read-only="true"/>
              <tx:method name="get*" read-only="true"/>
              <tx:method name="query*" read-only="true"/>
              <tx:method name="delete*" propagation="REQUIRED"
                         rollback-for="Exception"/>
              <tx:method name="update*" propagation="REQUIRED"
                         rollback-for="Exception"/>
              <tx:method name="insert*" propagation="REQUIRED"
                         rollback-for="Exception"/>
              <tx:method name="add*" propagation="REQUIRED"
                         rollback-for="Exception"/>
          </tx:attributes>
      </tx:advice>
  
      <!--使用aop对事务管理的范围进行织入，明确几个点 1、对哪些地方需要进行事务的管理 execution书写，
          明确边界2、使用什么策略去管理
          策略我们使用了tx:advice全部书写在其中，在我们的aop的advisor当中只需要去引用这个事务管理者的建议即可
      -->
      <aop:config>
          <aop:pointcut id="txCut" expression="execution(* com.lyhui.service..*.*(..))"/>
          <aop:advisor advice-ref="transactionAdvice" pointcut-ref="txCut"/>
      </aop:config>
  
      <!--采用注解进行事务管理，看清在Service的实现类上面加上@Transactional注解-->
      <tx:annotation-driven transaction-manager="transactionManager"/>
  </beans>
  ```

## spring-servlet.xml

- resources/spring文件夹下spring-servlet.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xmlns:c="http://www.springframework.org/schema/c"
         xmlns:cache="http://www.springframework.org/schema/cache"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:jdbc="http://www.springframework.org/schema/jdbc"
         xmlns:jee="http://www.springframework.org/schema/jee"
         xmlns:lang="http://www.springframework.org/schema/lang"
         xmlns:mvc="http://www.springframework.org/schema/mvc"
         xmlns:p="http://www.springframework.org/schema/p"
         xmlns:task="http://www.springframework.org/schema/task"
         xmlns:tx="http://www.springframework.org/schema/tx"
         xmlns:util="http://www.springframework.org/schema/util"
         xsi:schemaLocation="http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee.xsd
  		http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
  		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
  		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
  		http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd
  		http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc.xsd
  		http://www.springframework.org/schema/cache http://www.springframework.org/schema/cache/spring-cache.xsd
  		http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task.xsd
  		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
  		http://www.springframework.org/schema/lang http://www.springframework.org/schema/lang/spring-lang.xsd
  		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd ">
  
  
      <!--启动注解，扫描包、激活注解-->
      <context:component-scan base-package="com.lyhui">
          <!--排除了Service注解-->
          <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Service"/>
      </context:component-scan>
  
      <!--配置一个视图解析器-->
      <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
          <!--页面路径 = /jsp/ + view +.jsp-->
          <property name="prefix" value="/jsp/"/>
          <property name="suffix" value=".jsp"/>
      </bean>
  
      <!--加上MVC驱动-->
      <mvc:annotation-driven>
          <!--配置消息转换器以及支持JSON的使用-->
          <mvc:message-converters>
              <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                  <property name="supportedMediaTypes">
                      <list>
                          <value>application/json;charset=UTF-8</value>
                      </list>
                  </property>
              </bean>
          </mvc:message-converters>
      </mvc:annotation-driven>
  
      <!--配置请求映射适配器-->
      <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
          <property name="messageConverters">
              <list>
                  <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                      <property name="supportedMediaTypes">
                          <list>
                              <value>text/html;charset=UTF-8</value>
                              <value>application/json;charset=UTF-8</value>
                          </list>
                      </property>
                  </bean>
                  <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                      <property name="supportedMediaTypes">
                          <list>
                              <value>text/html;charset=UTF-8</value>
                              <value>application/json;charset=UTF-8</value>
                          </list>
                      </property>
                  </bean>
              </list>
          </property>
      </bean>
  
      <!--文件上传解析器，id必须是multipartResolver-->
      <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
          <!--定义最大上传的大小，总的单位为 bytes-->
          <property name="maxUploadSize" value="10240000"/>
          <!--指定上传的编码-->
          <property name="defaultEncoding" value="UTF-8"/>
          <!--单个文件最大上传大小-->
          <property name="maxUploadSizePerFile" value="2000000"/>
      </bean>
  
      <!--拦截器-->
      <mvc:interceptors>
          <mvc:interceptor>
              <mvc:mapping path="/**/*"/>
              <bean class="com.lyhui.interceptor.MethodTimerInterceptor"></bean>
          </mvc:interceptor>
          <mvc:interceptor>
              <!--
                  只想拦截/user/**/*
                  还需要开放登录权限       静态资源的权限
              -->
              <mvc:mapping path="/user/*"/>
              <!--排除登录的这个URI-->
              <mvc:exclude-mapping path="/user/login"/>
              <bean class="com.lyhui.interceptor.SessionInterceptor"></bean>
          </mvc:interceptor>
      </mvc:interceptors>
  
      <!--静态资源处理-->
      <mvc:default-servlet-handler/>
  
  </beans>
  ```

## db.properties

- resources文件夹下

  ```properties
  jdbc.url=jdbc:mysql://localhost:3306/ssm
  jdbc.driver=com.mysql.jdbc.Driver
  jdbc.username=root
  jdbc.password=000000lyh
  ```

## log4j.properties

- resources文件夹下

  ```properties
  #定义LOG输出级别
  log4j.rootLogger=INFO,Console,File
  
  #定义日志输出目的地为控制台
  log4j.appender.Console=org.apache.log4j.ConsoleAppender
  log4j.appender.Console.Target=System.out
  #可以灵活的指定日志输出格式，下面一行是指定具体的格式
  log4j.appender.Console.layout=org.apache.log4j.PatternLayout
  log4j.appender.Console.layout.ConversionPattern=[%c]-%m%n
  
  #mybatis显示SQL语句日志配置
  log4j.logger.com.lyhui.mapper=DEBUG
  
  #文件大小到达指定尺寸的时候产生一个新的文件
  log4j.appender.File=org.apache.log4j.RollingFileAppender
  #指定输出目录，这里会放在tomcat之下
  log4j.appender.File.File=D:/logs/log.log
  #log4j.appender.File.File=logs/ssm.log
  #定义文件最大值
  log4j.appender.File.MaxFileSize=10MB
  #输出所有日志，如果换成DEBUG表示输出DEBUG以上级别的日志
  log4j.appender.File.Threshold=All
  log4j.appender.File.layout=org.apache.log4j.PatternLayout
  log4j.appender.File.layout.ConversionPattern=[%p][%d{yyyy-MM-dd HH\:mm|\:ss}][%c]%m%n
  ```

  
