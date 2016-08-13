# 一个Java项目是怎么炼成的

> 起初高司令发明了Java，风靡一时。后来大家放弃了Java，因为配置太复杂了。

虽然这是个段子，但是反映了很多人学习Java的切身感受。即便工作多年的Java工程师，也不见得能轻松从零构建一个Java项目，并且对所有配置细节如数家珍。当然，Spring官方也意识到这个问题，推出新版本建议以java config的方式代替xml，简直大快人心，因为xml配置太冗长，又很难被新手理解。只会写Java的工程师可能没当回事，实际上Java是少数以xml作为配置文件的语言。所以我决定总结一下java config的使用经验，构建一个开源项目，一方面是沉淀知识，另一方面是帮助Java初学者跨过配置这道坎。

## Maven

Java项目管理以及自动构建工具。尽管gradle风头正旺，但是主要集中在安卓端，服务端介于历史原因革新缓慢，pom.xml也是项目里唯一的xml文件。

1. 划分模块。
  一个Java项目通常会有这么几个模块：web、service、dao、common。如果新建一个项目需要用到前面项目的service，只需要添加相关依赖即可。

1. 根据环境打包。
  程序运行环境通常有三种：开发、测试、线上。每种环境的配置文件当然不一样，打包的时候通过命令指定环境名称（mvn clean package -P[dev|test|prod]）可以打出相应的包。详情见winter-web中的pom.xml。

1. 集成Jetty。
  Java Web程序需要运行在容器里，需要先安装应用容器，然后打包，再把包拷到容器里，过程比较繁琐。集成Jetty后，运行`mvn jetty:run`即可启动程序。详情见winter-web中的pom.xml。

## Spring

Java开发核心框架，配置也最为复杂，主要工作是用java config代替xml。

1. MVC
  1. AppInitializer类，替代了web.xml的功能，负责加载spring配置文件，实例化一个dispatcher。
  1. RootConfig类是spring的根配置文件，加载了Webconfig和JpaConfig。
  1. Webconfig类负责配置web相关功能。viewResolver配置了jsp的解析。将项目部署在应用容器的根目录，浏览器里输入`localhost:8080`，IndexController会返回主页内容。configureMessageConverters将spring的编码设置为UTF-8。addInterceptors用于配置拦截器。MappingJackson2HttpMessageConverter用于解析JSON，Requestbody注解会用到。

1. JPA
  1. 用过mybatis和hibernate，前者配置太复杂，后者功能还不够强大。JPA更上一层楼，大部分简单SQL都自动帮你搞定，详情见官方文档。
  1. JpaConfig配置了dataSource等，UserDAO是个数据库操作的简单例子，可以在UserDAOTest里试一下效果。db.design是建表语句。



