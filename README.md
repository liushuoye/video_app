Markdown语法参考

# 一级标题

## 二级标题

##### 五级标题

- 列表第一项
- 列表第二项

1. 有序列表第一项
2. 有序列表第二项
   `[标题](链接地址)`
   `![图片描述](图片链接地址)`
   *斜体*
   **粗体**

> 引用段落
```代码块```
#使用的框架：
------------

### [使用 Hilt 实现依赖项注入：](https://developer.android.google.cn/training/dependency-injection/hilt-android?hl=zh_cn)

**Hilt 是 Android 的依赖项注入库，可减少在项目中执行手动依赖项注入的样板代码。执行手动依赖项注入要求您手动构造每个类及其依赖项， 并借助容器重复使用和管理依赖项。 Hilt
通过为项目中的每个 Android 类提供容器并自动管理其生命周期，提供了一种在应用中使用 DI（依赖项注入）的标准方法。 Hilt 在热门 DI 库 Dagger 的基础上构建而成，因而能够受益于
Dagger 的编译时正确性、运行时性能、可伸缩性和 Android Studio 支持。**
*依赖:*

```
    implementation "com.google.dagger:hilt-android:2.38.1"
    kapt "com.google.dagger:hilt-android-compiler:2.38.1"
    ///使用 Hilt 注入 ViewModel 对象
    implementation 'androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03'
    // 使用 Kotlin 时。
    kapt 'androidx.hilt:hilt-compiler:1.0.0'
    // 使用 Java 时。
    annotationProcessor 'androidx.hilt:hilt-compiler:1.0.0'
    ///使用 Hilt 注入 WorkManager
    implementation 'androidx.hilt:hilt-work:1.0.0'
    // 使用 Kotlin 时。
    kapt 'androidx.hilt:hilt-compiler:1.0.0'
    // 使用 Java 时。
    annotationProcessor 'androidx.hilt:hilt-compiler:1.0.0'
```

### [Lifecycle 生命周期感知型组件](https://developer.android.google.cn/jetpack/androidx/releases/lifecycle#groovy)

**生命周期感知型组件可执行操作来响应另一个组件（如 Activity 和 Fragment）的生命周期状态的变化。 这些组件有助于您写出更有条理且往往更精简的代码，这样的代码更易于维护。**
*依赖:*

``` 
   def lifecycle_version = "2.4.0-rc01"
    def arch_version = "2.1.0"
    // ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    // LiveData
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
    // 仅生命周期（没有 ViewModel 或 LiveData）
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"
    // 为 ViewModel 保存状态模块
    implementation "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"
    // 注释处理器
    kapt "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"
    // alternately - if using Java8, use the following instead of lifecycle-compiler
    implementation "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"
    // 可选 - 在服务中实现 LifecycleOwner 的助手
    implementation "androidx.lifecycle:lifecycle-service:$lifecycle_version"
    // 可选 - ProcessLifecycleOwner 为整个应用程序进程提供生命周期
    implementation "androidx.lifecycle:lifecycle-process:$lifecycle_version"
    // 可选 - 对 LiveData 的 ReactiveStreams 支持
    implementation "androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version"
    // 可选 - LiveData 的测试助手
    testImplementation "androidx.arch.core:core-testing:$arch_version"
```

### [Navigation 导航组件:](https://developer.android.google.cn/guide/navigation/navigation-getting-started#groovy)

**导航是指支持用户导航、进入和退出应用中不同内容片段的交互。 Android Jetpack
的导航组件可帮助您实现导航，无论是简单的按钮点击，还是应用栏和抽屉式导航栏等更为复杂的模式，该组件均可应对。 导航组件还通过遵循一套既定原则来确保一致且可预测的用户体验。**
*依赖:*

```
     def nav_version = "2.3.5"
     // Java语言实现
     implementation "androidx.navigation:navigation-fragment:$nav_version"
     implementation "androidx.navigation:navigation-ui:$nav_version"
     // Kotlin
     implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
     implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
     // 功能模块支持
     implementation "androidx.navigation:navigation-dynamic-features-fragment:$nav_version"
     // 测试导航
     androidTestImplementation "androidx.navigation:navigation-testing:$nav_version"
     // Jetpack Compose 集成
     implementation "androidx.navigation:navigation-compose:2.4.0-alpha10"
```

### [Room 持久性库](https://developer.android.google.cn/jetpack/androidx/releases/room#groovy)

**Room 持久性库在 SQLite 的基础上提供了一个抽象层，让用户能够在充分利用 SQLite 的强大功能的同时，获享更强健的数据库访问机制。**
*依赖:*

```
    val roomVersion = "2.3.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin Symbolic Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:$roomVersion")
    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$roomVersion")
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$roomVersion")
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$roomVersion")
    // optional - Paging 3 Integration 和 prging 框架有冲突 
    implementation("androidx.room:room-paging:2.4.0-beta01")
```

### [Paging 分页](https://developer.android.google.cn/jetpack/androidx/releases/paging?hl=zh_cn#3.0.0)

**使用 Paging 库，您可以更加轻松地在应用的 RecyclerView 中逐步、流畅地加载数据。**

```
  def paging_version = "3.0.1"

  implementation "androidx.paging:paging-runtime:$paging_version"

  // 或者 - 没有用于测试的 Android 依赖项
  testImplementation "androidx.paging:paging-common:$paging_version"

  // 可选 - RxJava2 支持
  implementation "androidx.paging:paging-rxjava2:$paging_version"

  // 可选 - RxJava3 支持
  implementation "androidx.paging:paging-rxjava3:$paging_version"

  // 可选 - Guava ListenableFuture 支持
  implementation "androidx.paging:paging-guava:$paging_version"

  // 可选 - Jetpack Compose 集成
  implementation "androidx.paging:paging-compose:1.0.0-alpha13"
```

### 网络请求框架 Retrofit2 + Okhttp3

```
    implementation('com.squareup.okhttp3:okhttp:4.9.2')
    implementation('com.squareup.retrofit2:retrofit:2.9.0')
    // ConverterFactory的Gson依赖包
    implementation('com.squareup.retrofit2:converter-gson:2.9.0')
    // 日志过滤器
    implementation('com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2')
    // Fastjson 是一个用 Java 编写的 JSON 处理器（JSON 解析器 + JSON 生成器）
    implementation('com.alibaba:fastjson:1.2.78')
    // Java 标量值类型的改造转换器。
    implementation('com.squareup.retrofit2:converter-scalars:2.9.0')
```