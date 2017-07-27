# AndroidApp
#### 介绍：
    Android学习、实践
    
## Git分支结构
![image](https://github.com/pengzhiming/AndroidApp/blob/feture/ft-pzm-dev1.0/app/src/main/assets/git_branch.png)

## Android项目结构
> AndroidLib库，用来存放与业务无关的逻辑
* component 用来存放公用组件
* util 用来存放公用方法
* widget 用来存放自定义控件
> Android主项目
* api  用来存放项目中所有的网络请求
* base 用来存放基类
* component 用来存跟业务关联的组件
* helper 用来存帮助类（基础、用户数据）
* model 用来存放项目中的业务数据
* module 用来存放项目中的业务模块
* widget 用来存放跟业务相关联的自定义控件

## Gradle构建