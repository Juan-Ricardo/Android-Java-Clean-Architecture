Clean Architecture, Android Java
================================

## Nueva versión disponible escrita en Kotlin:
[Clean Architecture, Android Kotlin](https://github.com/Juan-Ricardo/Android-Kotlin-Clean-Architecture)

Introducción
-----------------
Para demostrar cómo diseñar y construir una aplicación basada en **Clean Architecture** - Tío Bob, he creado una aplicación llamada  **Zineema**. Es una aplicación para sistemas operativos android, usa minSdkVersion: 21, targetSdkVersion: 28, compileSdkVersion: 28.

Para ejecutar Zineema en un emulador o dispositivo físico, se requiere una cuenta de usuario TMDb para solicitar una clave API. https://www.themoviedb.org/
<p align="center">
  <img alt="https://www.themoviedb.org/" src="https://www.themoviedb.org/assets/2/v4/logos/primary-green-d70eebe18a5eb5b166d5c1ef0796715b8d1a2cbc698f96d311d62f894ae87085.svg" width="94" height="94"/>
</p>

## 1. Configuración de Clave API.
**Agregue la Clave API a su archivo gradle.properties:**
```javascript
# The setting is particularly useful for tweaking memory settings.
org.gradle.jvmargs=-Xmx1536m
# When configured, Gradle will run in incubating parallel mode.
# This option should only be used with decoupled projects. More details, visit
# http://www.gradle.org/docs/current/userguide/multi_project_builds.html#sec:decoupled_projects
# org.gradle.parallel=true
# AndroidX package structure to make it clearer which packages are bundled with the
# Android operating system, and which are packaged with your app's APK
# https://developer.android.com/topic/libraries/support-library/androidx-rn
android.useAndroidX=true
# Automatically convert third-party libraries to use AndroidX
android.enableJetifier=true

#Themovie
themovieApiKey=YourThemovieApiKey
```
## 2. Clean Architecture.
El objetivo principal de este enfoque es la separación de preocupaciones para separar su código en capas independientes y diseñarlo para que dependa de abstracciones en lugar de implementaciones concretas - Tío Bob. Para lograr esto, debemos seguir una regla simple pero estricta:  **la Regla de dependencia**, representada por esas flechas que apuntan al centro del gráfico.

**Zineema** esta construido en base a módulos. **¿Qué es la modularización?** La modularización en aplicaciones de Android representa un patrón de diseño de software que separa las funcionalidades en módulos. Cada aplicación de Android se puede modularizar dividiendo el módulo de aplicación en módulos de biblioteca. El módulo de la biblioteca tendrá sus recursos, archivo de manifiesto y clases. Al final, la herramienta de compilación lo fusionará en un solo APK. **Zineema**, tiene los siguientes módulos: **Presentation, UseCase, Domain, DataSource**. 

<p align="center">
  <img src="https://github.com/Juan-Ricardo/Android-Java-Clean-Architecture/blob/master/resource/src/main/assets/images/Android-Clean-Arquitectura-3.jpeg" width="724" height="424"/>
</p>

### 2.1 Presentation
        Actividades, Fragmentos, Lógica de Interfaz de Usuario.
### 2.2 UseCase: 
        Casos de Uso, Reglas de Negocio.
### 2.3 Domain: 
        Modelos.
### 2.4 DataSource: 
        Fuente de Datos de Red, Base de Datos, Memoria Caché, etc.
<p align="center">        
  <img src="https://github.com/Juan-Ricardo/Android-Java-Clean-Architecture/blob/master/resource/src/main/assets/images/Android-Clean-Arquitectura-2.jpeg" width="624" height="424"/>
  </p>
