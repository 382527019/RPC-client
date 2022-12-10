## RPC 客户端实现demo
### 3.1.1客户端
* 服务消费 Main.java
~~~
public class Main 
~~~
* 动态代理 RpcProxyClient .java
~~~
public class RpcProxyClient 
~~~
* 实现InvocationHandler 接口动态代理。RemoteInvocationHandler .java
~~~
//实现 InvocationHandler动态代理
public class RemoteInvocationHandler implements InvocationHandler 
~~~
* RpcNetTransport RPC连接通道
* 序列化请求消息
* 反序列化响应消息
~~~
//RPC连接通道
public class RpcNetTransport 
~~~

## 注解

### 3.2.2 客户端实现
* 定义注解 Target字段上 Refernce.java
~~~
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)//运行时用
@Component
public @interface Refernce {
}
~~~
* 实现spring bean后置处理器，容器创建前扫描注解设置代理
* RefernceInvokeProxy.java
* 扫描到指定注解代理执行remoteInvocationHandler
~~~
@Component
public class RefernceInvokeProxy implements BeanPostProcessor 
~~~
* 实现InvocationHandler接口进行动态代理
* RemoteInvocationHandler.java
* send（）发送数据
~~~
//实现 InvocationHandler动态代理
@Component
public class RemoteInvocationHandler implements InvocationHandler 
~~~
* RpcNetTransport.java
* RPC连接通道
* 发送数据、响应结果
~~~
//RPC连接通道
public class RpcNetTransport
}
~~~
* 启动springboot
~~~
@SpringBootApplication
public class Main 
~~~
* 配置文件
* 客户端启动8080，服务端启动8888
~~~
server.port=8080
me.host=127.0.0.1
me.port=8888
~~~
* TestController.java
* 访问8080controller接口得到服务端的回调结果。
~~~
@RestController
public class TestController 
~~~

