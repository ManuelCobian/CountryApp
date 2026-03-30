CountriesApp

Aplicación Android desarrollada en Kotlin que consume información de países utilizando la API pública de REST Countries.

Descripción

CountriesApp permite consultar información detallada de países como:

Nombre
Capital
Región
Población
Bandera

La aplicación está diseñada con buenas prácticas modernas de desarrollo Android, priorizando escalabilidad, mantenimiento y separación de responsabilidades.

API utilizada

La app consume datos desde la API pública:

 https://restcountries.com/v3.1/

Endpoint principal utilizado:
https://restcountries.com/v3.1/all
Consideraciones:
No requiere autenticación
Respuestas en formato JSON
Se maneja parsing con modelos tipados en Kotlin
Se implementa manejo de errores para:
Timeouts
Errores de red
Respuestas vacías o mal formadas
Arquitectura

Se implementó arquitectura:

 MVVM (Model - View - ViewModel)

¿Por qué MVVM?

Separación clara de responsabilidades
Facilita testing
Compatible con LiveData
Mejor manejo del ciclo de vida
Capas:
View (UI) → Activities / Fragments
ViewModel → Lógica de presentación
Model → Repositorios + fuentes de datos
Modularización

La aplicación está dividida en módulos con responsabilidades específicas:

room
Implementación de Room
core
Consumo de API (Retrofit)
App
UI + ViewModels
Beneficios:
Escalabilidad
Bajo acoplamiento
Reutilización de código
Fácil mantenimiento

Persistencia de datos

Se utilizó:

Room Database

¿Por qué Room?

Abstracción sobre SQLite
Integración con Kotlin Coroutines / Flow
Seguridad en tiempo de compilación
Manejo eficiente de cache local
Estrategia:
Se cachean los países localmente

Consumo de API

Se utilizó:

Retrofit
Coroutines
Manejo de errores con Result / sealed classes
Buenas prácticas implementadas:
Manejo de estados:
Loading
Success
Error
Manejo de excepciones:
IOException
HttpException
Uso de repository pattern
Requisitos
SDK
Java 17 (JDK 17)

Puedes instalarlo desde:

Amazon Corretto (recomendado)
Android
Min SDK: (define según tu proyecto)
Target SDK: (define según tu proyecto)

Tecnologías utilizadas
Kotlin
MVVM
Retrofit
Room
Coroutines
LiveData
Dagger
Decisiones técnicas relevantes
Separación Data Sources

Se separaron:

Remote Data Source (API)
Local Data Source (Room)

Esto permite:

Manejo offline
Mejor control de errores
