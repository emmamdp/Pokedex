# Pokédex Android

Aplicación Android modular escrita en **Kotlin** con **Jetpack Compose** y **Clean Architecture**. La app muestra una lista de Pokemon; en el arranque una pantalla Splash decide si sincronizar con API o usar datos locales. La persistencia se realiza con **Room**, y la fecha de última sincronización se guarda en **DataStore**. Tema oscuro fijo y **Koin** para inyección de dependencias.

## Estructura de módulos

```
:app                      // punto de entrada (MainActivity, NavHost, arranque DI)
:domain                   // modelos de dominio, use cases, contratos de repositorio
:data                     // Retrofit/Moshi/OkHttp + Room + DataStore (implementaciones)
:core:common              // utilidades y base UI común (tipografías, helpers)
:core:ui                  // design system: tema Material3 y componentes reutilizables
:core:navigation          // rutas, destinos y helpers de navegación
:core:di                  // módulos de Koin (wiring centralizado)
:features:splash          // Splash (UI + estado sellado) y orquestación inicial
:features:pokemon-list    // Lista de Pokémon (UI + estados)
:features:pokemon-detail  // Detalle de Pokémon (UI + estados)
```

## Flujo de datos (resumen)

* Al iniciar, **Splash** entra en `Loading`.
* Un **use case** compara la fecha de hoy con la guardada en DataStore.
* Si hay que refrescar: **API → mapeo → Room → actualizar fecha**. Si no, se usan los datos de Room.
* Tras la decisión, se navega a la **lista de Pokémon**.
* Si del listado se escoge un Pokemon, se navega a su detalle.
* También en un futuro habrá la opción de añadir a favoritos los Pokémon que desee el usuario.

## Tecnologías

* UI: Jetpack **Compose** (Material3, Navigation)
* Concurrencia: **Coroutines**
* DI: **Koin**
* Red: **Retrofit** + **OkHttp** + **Moshi**
* Persistencia: **Room** + **DataStore**
* Tests: **JUnit** (+ coroutines test)

