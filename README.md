# Filament

[![Android Build Status](https://github.com/google/filament/workflows/Android/badge.svg)](https://github.com/google/filament/actions?query=workflow%3AAndroid)
[![iOS Build Status](https://github.com/google/filament/workflows/iOS/badge.svg)](https://github.com/google/filament/actions?query=workflow%3AiOS)
[![Linux Build Status](https://github.com/google/filament/workflows/Linux/badge.svg)](https://github.com/google/filament/actions?query=workflow%3ALinux)
[![macOS Build Status](https://github.com/google/filament/workflows/macOS/badge.svg)](https://github.com/google/filament/actions?query=workflow%3AmacOS)
[![Windows Build Status](https://github.com/google/filament/workflows/Windows/badge.svg)](https://github.com/google/filament/actions?query=workflow%3AWindows)
[![Web Build Status](https://github.com/google/filament/workflows/Web/badge.svg)](https://github.com/google/filament/actions?query=workflow%3AWeb)

Filament - это физический движок рендеринга в реальном времени для Android, iOS, Linux, macOS, Windows,
и WebGL. Он разработан, чтобы быть как можно меньше по размеру и максимально эффективно использовать Android.

## Загрузка

[Загрузите релиз Filament](https://github.com/google/filament/releases) для доступа к стабильным сборкам.
Filament release архивы содержат инструменты на стороне хоста, необходимые для создания активов.

Убедитесь, что вы всегда используете инструменты из той же версии, что и библиотека времени выполнения. 
Это особенно важно для `matc` (material compiler).

Если вы предпочитаете самостоятельно собирать Filament, обратитесь к нашему [build manual](BUILDING.md).

### Android

Android проекты могут просто объявить библиотеки Filament как зависимости Maven:

```gradle
repositories {
    // ...
    mavenCentral()
}

dependencies {
    implementation 'com.google.android.filament:filament-android:1.9.9'
}
```

Вот все библиотеки, доступные в группе `com.google.android.filament`:

[![filament-android](https://maven-badges.herokuapp.com/maven-central/com.google.android.filament/filament-android/badge.svg?subject=filament-android)](https://maven-badges.herokuapp.com/maven-central/com.google.android.filament/filament-android)  
Собственно движок рендеринга Filament.

[![gltfio-android](https://maven-badges.herokuapp.com/maven-central/com.google.android.filament/gltfio-android/badge.svg?subject=gltfio-android)](https://maven-badges.herokuapp.com/maven-central/com.google.android.filament/gltfio-android)  
Загрузчик glTF 2.0 для Filament, зависящий от `filament-android`  .

[![gltfio-android-lite](https://maven-badges.herokuapp.com/maven-central/com.google.android.filament/gltfio-android-lite/badge.svg?subject=gltfio-android-lite)](https://maven-badges.herokuapp.com/maven-central/com.google.android.filament/gltfio-android-lite)  
Урезанная версия `gltfio` не поддерживающая некоторые фичи glTF.

[![filament-utils-android](https://maven-badges.herokuapp.com/maven-central/com.google.android.filament/filament-utils-android/badge.svg?subject=filament-utils-android)](https://maven-badges.herokuapp.com/maven-central/com.google.android.filament/filament-utils-android)  
KTX loading, Kotlin math, and camera utilities, depends on `gltfio-android`.

[![filament-utils-android-lite](https://maven-badges.herokuapp.com/maven-central/com.google.android.filament/filament-utils-android-lite/badge.svg?subject=filament-utils-lite)](https://maven-badges.herokuapp.com/maven-central/com.google.android.filament/filament-utils-android-lite)  
Урезанная версия `filament-utils` не поддерживающая некоторые фичи glTF.

[![filamat-android](https://maven-badges.herokuapp.com/maven-central/com.google.android.filament/filamat-android/badge.svg?subject=filamat-android)](https://maven-badges.herokuapp.com/maven-central/com.google.android.filament/filamat-android)  
Построитель / компилятор материалов во время выполнения. Эта библиотека большая, но содержит полный компилятор / валидатор / оптимизатор шейдеров..

[![filamat-android-lite](https://maven-badges.herokuapp.com/maven-central/com.google.android.filament/filamat-android-lite/badge.svg?subject=filamat-android-lite)](https://maven-badges.herokuapp.com/maven-central/com.google.android.filament/filamat-android-lite)  
Уменьшенная альтернатива `filamat-android` который может генерировать только шейдеры OpenGL. Он не обеспечивает проверки или оптимизации.

### iOS

iOS проекты могут использовать CocoaPods для установки последней версии:

```
pod 'Filament', '~> 1.9.9'
```

### Snapshots

Если вы предпочитаете жить на грани, вы можете загрузить непрерывную сборку, выполнив следующие действия.:

1. Find the [commit](https://github.com/google/filament/commits/main) you're interested in.
2. Click the green check mark under the commit message.
3. Click on the _Details_ link for the platform you're interested in.
4. On the top right, click on the _Artifacts_ dropdown and choose an artifact.

## Documentation

- [Filament](https://google.github.io/filament/Filament.html), подробное объяснение 
  физического рендеринга в реальном времени, графических возможностей и реализации Filament.
  Этот документ объясняет математические расчеты и обоснование большинства наших решений. 
  Этот документ - хорошее введение в PBR для программистов графики.
- [Materials](https://google.github.io/filament/Materials.html), полнописание и документация
    для нашей системы материалов. В этом документе объясняются наши различные модели материалов,
    как использовать компилятор материалов `matc` и как писать собственные материалы.
- [Material Properties](https://google.github.io/filament/Material%20Properties.pdf), таблица
  ссылок на standard material model.

## Примеры

![Night scene](docs/images/samples/example_bistro1.jpg)
![Night scene](docs/images/samples/example_bistro2.jpg)
![Materials](docs/images/samples/example_materials1.jpg)
![Materials](docs/images/samples/example_materials2.jpg)
![Helmet](docs/images/samples/example_helmet.jpg)
![Screen-space refraction](docs/images/samples/example_ssr.jpg)


### Приложения

Вот несколько скриншотов приложений, которые используют Filament в производстве.:

#### Google Maps AR Navigation

![Google Maps AR Navigation](docs/images/samples/app_gmm_ar_nav.jpg)

#### Google Search 3D/AR Viewer on Android

![Google Search 3D/AR Viewer on Android](docs/images/samples/app_google_3d_viewer.jpg)

## Features

### APIs

- Native C++ API for Android, iOS, Linux, macOS and Windows
- Java/JNI API for Android, Linux, macOS and Windows
- JavaScript API

### Backends

- OpenGL 4.1+ for Linux, macOS and Windows
- OpenGL ES 3.0+ for Android and iOS
- Metal for macOS and iOS
- Vulkan 1.0 for Android, Linux, macOS, and Windows
- WebGL 2.0 for all platforms

### Rendering

- Clustered forward renderer
- Cook-Torrance microfacet specular BRDF
- Ламбертовский диффузный BRDF
- HDR/linear lighting
- Metallic workflow
- Clear coat
- Anisotropic lighting
- Приблизительные полупрозрачные (подповерхностные) материалы
- Затенение ткани
- Normal mapping & ambient occlusion mapping
- Image-based lighting
- Physically-based camera (shutter speed, sensitivity and aperture)
- Physical light units
- Point lights, spot lights and directional light
- Spot and directional light shadows
- Cascaded shadows
- VSM or PCF shadows
- Contact shadows
- Screen-space ambient occlusion
- Screen-space refraction
- Global fog
- HDR bloom
- Depth of field bokeh
- Multiple tone mappers: ACES, filmic, etc.
- Color grading: white balance, channel mixer, shadows/mid-tones/highlights, ASC CDL,
  contrast, saturation, etc.
- TAA, FXAA, MSAA and specular anti-aliasing
- Dynamic resolution

## Rendering with Filament

### Native Linux, macOS and Windows

Вы должны создать `Engine`, `Renderer` и `SwapChain`. `SwapChain` создается из
native window pointer (`NSView` в macOS или `HWND` в Windows например):

```c++
Engine* engine = Engine::create();
SwapChain* swapChain = engine->createSwapChain(nativeWindow);
Renderer* renderer = engine->createRenderer();
```

Для рендеринга кадра вы должны затем создать `View`, `Scene` и `Camera`:

```c++
Camera* camera = engine->createCamera(EntityManager::get().create());
View* view = engine->createView();
Scene* scene = engine->createScene();

view->setCamera(camera);
view->setScene(scene);
```

Renderables добавлены в сцену:

```c++
Entity renderable = EntityManager::get().create();
// build a quad
RenderableManager::Builder(1)
        .boundingBox({{ -1, -1, -1 }, { 1, 1, 1 }})
        .material(0, materialInstance)
        .geometry(0, RenderableManager::PrimitiveType::TRIANGLES, vertexBuffer, indexBuffer, 0, 6)
        .culling(false)
        .build(*engine, renderable);
scene->addEntity(renderable);
```

Экземпляр материала получается из материала, который сам загружается из сгенерированного 
двоичного объекта `matc`:

```c++
Material* material = Material::Builder()
        .package((void*) BAKED_MATERIAL_PACKAGE, sizeof(BAKED_MATERIAL_PACKAGE))
        .build(*engine);
MaterialInstance* materialInstance = material->createInstance();
```

To learn more about materials and `matc`, please refer to the
[materials documentation](./docs/Materials.md.html).

Для рендеринга просто передайте `View` в `Renderer`:

```c++
// beginFrame() returns false if we need to skip a frame
if (renderer->beginFrame(swapChain)) {
    // for each View
    renderer->render(view);
    renderer->endFrame();
}
```

Для полных примеров Linux, macOS и Windows Filament приложений, смотрите исходные файлы
в папке `samples/` directory. Все эти образцы основаны на `samples/app/` которые содержат код
созданный нативным window с SDL2 и initializes the Filament engine, renderer и views.

### Java в Linux, macOS и Windows

After building Filament, you can use `filament-java.jar` and its companion `filament-jni` native
library to use Filament in desktop Java applications.

You must always first initialize Filament by calling `Filament.init()`.

You can use Filament either with AWT or Swing, using respectively a `FilamentCanvas` or a
`FilamentPanel`.

Following the steps above (how to use Filament from native code), create an `Engine` and a
`Renderer`, but instead of calling `beginFrame` and `endFrame` on the renderer itself, call
these methods on `FilamentCanvas` or `FilamentPanel`.

### Android

See `android/samples` for examples of how to use Filament on Android.

You must always first initialize Filament by calling `Filament.init()`.

Rendering with Filament on Android is similar to rendering from native code (the APIs are largely
the same across languages). You can render into a `Surface` by passing a `Surface` to the
`createSwapChain` method. This allows you to render to a `SurfaceTexture`, a `TextureView` or
a `SurfaceView`. To make things easier we provide an Android specific API called `UiHelper` in the
package `com.google.android.filament.android`. All you need to do is set a render callback on the
helper and attach your `SurfaceView` or `TextureView` to it. You are still responsible for
creating the swap chain in the `onNativeWindowChanged()` callback.

### iOS

Filament is supported on iOS 11.0 and above. See `ios/samples` for examples of using Filament on
iOS.

Filament on iOS is largely the same as native rendering with C++. A `CAEAGLLayer` or `CAMetalLayer`
is passed to the `createSwapChain` method. Filament for iOS supports both Metal (preferred) and
OpenGL ES.

## Assets

Для начала вы можете использовать текстуры и карты окружения соответственно в
`third_party/textures` и `third_party/environments`. These assets are under CC0 license. Please
refer to their respective `URL.txt` files to know more about the original authors.

## How to make contributions

Please read and follow the steps in [CONTRIBUTING.md](/CONTRIBUTING.md). Make sure you are
familiar with the [code style](/CODE_STYLE.md).

## Directory structure

Этот репозиторий содержит не только основной движок Filament, но и его вспомогательные библиотеки
и инструменты..

- `android`:                  Android libraries and projects
  - `filamat-android`:        Filament material generation library (AAR) for Android
  - `filament-android`:       Filament library (AAR) for Android
  - `filament-utils-android`: Extra utilities (KTX loader, math types, etc.)
  - `gltfio-android`:         Filament glTF loading library (AAR) for Android
  - `samples`:                Android-specific Filament samples
- `art`:                      Source for various artworks (logos, PDF manuals, etc.)
- `assets`:                   3D assets to use with sample applications
- `build`:                    CMake build scripts
- `docs`:                     Documentation
  - `math`:                   Mathematica notebooks used to explore BRDFs, equations, etc.
- `filament`:                 Filament rendering engine (minimal dependencies)
- `ide`:                      Configuration files for IDEs (CLion, etc.)
- `ios`:                      Sample projects for iOS
- `java`:                     Java bindings for Filament libraries
- `libs`:                     Libraries
  - `bluegl`:                 OpenGL bindings for macOS, Linux and Windows
  - `bluevk`:                 Vulkan bindings for macOS, Linux, Windows and Android
  - `camutils`:               Camera manipulation utilities
  - `filabridge`:             Library shared by the Filament engine and host tools
  - `filaflat`:               Serialization/deserialization library used for materials
  - `filagui`:                Helper library for [Dear ImGui](https://github.com/ocornut/imgui)
  - `filamat`:                Material generation library
  - `filamentapp`:            SDL2 skeleton to build sample apps
  - `filameshio`:             Tiny filamesh parsing library (see also `tools/filamesh`)
  - `geometry`:               Mesh-related utilities
  - `gltfio`:                 Loader for glTF 2.0
  - `ibl`:                    IBL generation tools
  - `image`:                  Image filtering and simple transforms
  - `imageio`:                Image file reading / writing, only intended for internal use
  - `matdbg`:                 DebugServer for inspecting shaders at run-time (debug builds only)
  - `math`:                   Math library
  - `mathio`:                 Math types support for output streams
  - `utils`:                  Utility library (threads, memory, data structures, etc.)
- `samples`:                  Sample desktop applications
- `shaders`:                  Shaders used by `filamat` and `matc`
- `third_party`:              External libraries and assets
  - `environments`:           Environment maps under CC0 license that can be used with `cmgen`
  - `models`:                 Models under permissive licenses
  - `textures`:               Textures under CC0 license
- `tools`:                    Host tools
  - `cmgen`:                  Image-based lighting asset generator
  - `filamesh`:               Mesh converter
  - `glslminifier`:           Minifies GLSL source code
  - `matc`:                   Material compiler
  - `matinfo`                 Displays information about materials compiled with `matc`
  - `mipgen`                  Generates a series of miplevels from a source image
  - `normal-blending`:        Tool to blend normal maps
  - `resgen`                  Aggregates binary blobs into embeddable resources
  - `roughness-prefilter`:    Pre-filters a roughness map from a normal map to reduce aliasing
  - `specular-color`:         Computes the specular color of conductors based on spectral data
- `web`:                      JavaScript bindings, documentation, and samples

## License

Please see [LICENSE](/LICENSE).

## Disclaimer

This is not an officially supported Google product.
