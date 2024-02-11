# Wonderous Compose

![badge android][badge-android]
![badge ios][badge-ios]
![badge linux][badge-linux]
![badge windows][badge-windows]
![badge macos][badge-macos]
![badge web javascript][badge-js]

Checkout live web version (based on JS) here
https://wonderous.shreyashkore.com

> 🚧 WORK IN PROGRESS 🚧

[![Banner](./readme_images/banner.png)](https://youtube.com/shorts/f-wM_MCiQmo?feature=share)

Wonderous Compose is a port of Wonderous
in [Compose Multiplatform.](https://www.jetbrains.com/lp/compose-multiplatform/)
Wonderous Compose is a visual showcase of eight wonders of the world.
The [original project](https://flutter.gskinner.com/wonderous/) was built
by team [gskinner](https://gskinner.com/flutter/)
using [Flutter.](https://flutter.dev/) This project is a tribute to their original
work, with an aim to explore the design possibilities with Compose.

Artworks and logos are taken from the original
project's [GitHub repo.](https://github.com/gskinnerTeam/flutter-wonderous-app)
Public-domain artwork from
[The Metropolitan Museum of Art, New Your.](https://www.metmuseum.org/about-the-met/policies-and-documents/open-access")
Photography from [Unsplash.](https://unsplash.com/@gskinner/collections)

![Wonderous Collage](./readme_images/wonderous_collage.png)

## Outline

* For navigation, the PreCompose library is used. This has a similar API to the AndroidX navigation
  library.
* HomeScreen shows the usage of HorizontalPager along with AnimatedVisibility for animating
  foreground and background elements
* ArtifactListScreen uses a ViewModel to store business logic.
* ArtifactDetailsScreen demonstrates writing business logic in the UI layer itself.
* Editorial Screen uses LazyColumn layout and usage of scroll APIs to drive animations for elements
  when they appear on the screen.
* Map View demonstrates how KMP and Compose's interoperability layer can be used to embed native UIs
  in Compose.
* The Photo Gallery screen makes use of a custom layout and also uses a custom gesture detection
  modifier.

## TODO

* WASM support
* Collectibles and My Collection Screen
* Localization
* Gesture support on Web and Desktop platforms
* Shared Element Transition on the home screen
* Haptics

## Set up the environment

> **Warning**
> You need a Mac with macOS to write and run iOS-specific code on simulated or real devices.
> This is an Apple requirement.

> For compose multiplatform setup information
> checkout its [template repo](https://github.com/JetBrains/compose-multiplatform-template).

- Add Google Maps key in `local.properties` at the project root.

```properties
MAPS_API_KEY=YOUR_KEY
```

[badge-android]: http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat

[badge-ios]: http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat

[badge-js]: http://img.shields.io/badge/platform-js-F8DB5D.svg?style=flat

[badge-jvm]: http://img.shields.io/badge/platform-jvm-DB413D.svg?style=flat

[badge-linux]: http://img.shields.io/badge/platform-linux-2D3F6C.svg?style=flat

[badge-windows]: http://img.shields.io/badge/platform-windows-4D76CD.svg?style=flat

[badge-macos]: http://img.shields.io/badge/platform-macos-111111.svg?style=flat

[badge-wasm]: https://img.shields.io/badge/platform-wasm-624FE8.svg?style=flat
