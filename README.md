#结构
###config－项目配置
`dependencies.gradle`多module项目的统一依赖管理，避免依赖不同版本的相同库。  
`key.properties`各个第三方平台的key管理，本来不应该加入版本控制。  
`signing.properties`签名设置，也不应该放进版本控制。  

###data－数据处理
data层负责与服务器，文件，第三方等等提供数据交互以及数据解析。
Model是data层面向presentation层的接口，向presentation层提供数据服务。拥有生命周期。从Application启动开始，便一直存在，为presentation提供数据处理支持。
模块指数据库支持模块，网络支持模块，第三方支持模块等等，提供Dagger注入。供Model选择使用。(模块注入部分还没有很规范的实现，毕竟刚刚整合Dagger)  
网络模块使用Retrofit

###domain－数据定义
包含了数据结构定义，解析定义，异常定义。
一般不依赖其他module。但被data及presentation所依赖。

###presentation－表现层
表示层以一个界面为一个单位。每个单位分为presenter与ui2部分。presenter与ui双向绑定。ui层负责数据展示与输入。presenter负责业务逻辑处理（主要是data层进行交互）。

#开发框架
项目采用[Beam](https://github.com/Jude95/Beam)开发框架，详情见其wiki。MVP架构。

##[Rx响应式编程](https://github.com/ReactiveX/RxJava)。
RX是一个事件驱动编程框架。用链式调用构造事件流。有效处理同步异步的问题，各层之间(特别是data与presentation之间)均使用Rx作为接口。拓展性更强。
##[Lambda表达式](https://github.com/evant/gradle-retrolambda)
大量使用Lambda表达式加速开发
##[ButterKnife](https://github.com/JakeWharton/butterknife)
JakeWharton大神～
配合[ButterKnifeZelezny](https://github.com/avast/android-butterknife-zelezny)插件，十分酸爽。
##[Dagger](https://github.com/google/dagger)
依赖注入神器，相见恨晚，所以框架对他的适配还不是很好。只有网络模块使用的注入。按新设计应该数据库部分，第三方部分都是使用它注入Model，以实现模块与框架解耦。