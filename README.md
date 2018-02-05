# XNRetrofit
基于[Spring](https://spring.io)框架的[Retrofit](http://square.github.io/retrofit/)简化配置及使用
1. 定义API接口
```java
@Component
public interface ExampleApi {
    @GET("/api/path")
    //@DoNotIntercept //若配置了拦截器但不想拦截该方法
    Call<Data> exampleMethod(@Query("param1") String param1, @Query("param2") String param2);
}
```
若有需要，基于AInterceptor抽象类实现自定义拦截器
```java
@Component
public class ExampleInterceptor extends AInterceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!shouldBeIntercepted(request)) return chain.proceed(request);   //判断是否该被拦截
        // TODO: 拦截处理逻辑
        return chain.proceed(request);
    }
}
```
2. 配置applicationContext.xml文件
```xml
    <bean id="exampleInterceptor" class="com.demo.service.interceptor.ExampleInterceptor" />
    <bean id="exampleApi" class="com.demo.factory.RetrofitApiFactoryBean">
        <property name="serviceUrl" value="http://example/service/url"/>
        <property name="serviceInterface" value="com.demo.service.ExampleApi"/>
        <!-- <property name="readTimeOut" value="5000" /> -->
        <!-- <property name="writeTimeOut" value="5000" /> -->
        <property name="interceptors">
            <list>
                <ref bean="exampleInterceptor"/>
            </list>
        </property>
    </bean>
```
3. 使用
```java
@Service
public class ExampleService {
    
    @Resource
    private ExampleApi exampleApi;
    @Resource
    private RetrofitUtil<Data> exampleRetrofit;

    public Data exampleMethod(String param1, String param2) {
        Call<Data> dataCall = exampleApi.exampleMethod(param1, param1);
        return exampleRetrofit.fetch(dataCall);
    }
}
```
