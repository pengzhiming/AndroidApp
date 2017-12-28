# AndroidApp
#### 介绍：
    Android学习、实践
    
## Git分支结构
![image](https://github.com/pengzhiming/AndroidApp/blob/feture/ft-pzm-dev1.0/app/src/main/assets/git_branch.png)

## Android项目结构
> AndroidLib库，用来存放与业务无关的逻辑
* component 用来存放公用组件，如net、log、cache、map等一系列组件
* util 用来存放公用方法
* widget 用来存放自定义控件
> Android主项目
* api  用来存放项目中所有的网络请求
* base 用来存放基类。比如BaseActivity、BaseAdapter等
* component 用来存跟业务关联的组件，（一般为Lib库component的派生）
* helper 用来存放帮助类，比如用户管理器、基础数据管理器等
* model 用来存放项目中的业务数据。比如数据库、实体类等
* module 用来存放项目中的业务模块。比如登陆模块、注册模块等
* widget 用来存放跟业务相关联的自定义控件

## Gradle构建：
<p>
    config.gradle用来存放项目中所有的配置;
    build.gradle(Module)通过buildType构建不同变种,如debug(面向开发者)、beta(面向测试小伙伴)、release(面向正式环境)，根据不同变种配置其所需的工程项目环境信息。
</p>