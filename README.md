# BookCircle-Manager

This is our implementation of a Kotlin Multiplatform web-application.
The host-targets of our project were Native, Web and Mobile.
We learned a lot about different concepts which were:
- Multiple targets
- Common code concept (codesharing across targets)
- JWT-Authentication
- Model structuring

## How to start the application ?

1. You need to clone this project and download all neccesary modules and libs.
2. Run gradle-build.
3. Start ’server/src/main/kotlin/de.hsflensburg.server/Application.kt’ for running the server.
4. Now start one of the following target-main files:
- Native : ’desktop/src/jvmMain/kotlin/Main.kt’
- Mobile : ’android/src/main/java/de/hsflensburg/android/MainActivity.kt’
- Web : Run the gradle task 'jsBrowserDevelopmentRun'
5. Enjoy our work

PS we doesn't put a lot of effort into designing our ui, feel warned :D

