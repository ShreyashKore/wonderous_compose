# Wonderous Compose

> 🚧 WORK IN PROGRESS 🚧

Wonderous Compose is a port of Wonderous
in [Compose Multiplatform.](https://www.jetbrains.com/lp/compose-multiplatform/)
Wonderous Compose is a visual showcase of eight wonders of the world.
The [original project](https://flutter.gskinner.com/wonderous/) was built
by team [gskinner](https://gskinner.com/flutter/)
using [Flutter.](https://flutter.dev/) This project is a tribute to their original
work, with an aim to explore the design possibilities with Compose.

Artworks and logos are taken from original
project's [github repo.](https://github.com/gskinnerTeam/flutter-wonderous-app)
Public-domain artwork from
[The Metropolitan Museum of Art, New Your.](https://www.metmuseum.org/about-the-met/policies-and-documents/open-access")
Photography from [Unsplash.](https://unsplash.com/@gskinner/collections)

![Wonderous Collage](./readme_images/wonderous_collage.png)

## Outline

* For navigation, PreCompose library is used. This has similar API to the AndroidX navigation
  library.
* HomeScreen shows the usage of HorizontalPager along with AnimatedVisibility for animating
  foreground and background elements
* ArtifactListScreen uses a ViewModel to store business logic.
* ArtifactDetailsScreen demonstrates writing business logic in the UI layer itself.
* Editorial Screen uses LazyColumn layout and usage of scroll APIs to drive animations for elements
  when they appear on the screen.
* Map View demonstrates how KMP and Compose's interoperability layer can be used to embed native UIs
  in Compose.
* Photo Gallery screen makes use of a custom layout and also uses a custom gesture detection
  modifier.

## Work In Progress

* Web support
* Collectibles and My Collection Screen
* Localization
* Shared Element Transition on home screen
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