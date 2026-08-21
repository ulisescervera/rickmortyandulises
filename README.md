# RickMortyAndUlises

Cliente **Android offline-first** de la [Rick and Morty API](https://rickandmortyapi.com/)
que combina **GraphQL y REST en una estrategia de carga en dos fases**, con **Room + Paging 3**
paginando directamente desde base de datos y Jetpack Compose en la capa de presentación.

![Kotlin](https://img.shields.io/badge/Kotlin-100%25-7F52FF?logo=kotlin&logoColor=white)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-Material3-4285F4?logo=jetpackcompose&logoColor=white)
![Hilt](https://img.shields.io/badge/DI-Hilt-2C4AA8)
![Paging 3](https://img.shields.io/badge/Room%20%2B%20Paging%203-offline--first-3DDC84?logo=android&logoColor=white)
![GraphQL](https://img.shields.io/badge/GraphQL-%2B%20REST-E10098?logo=graphql&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

<!-- TODO: añadir 2-3 capturas (listado de personajes, detalle con relacionados, filtros)
     o un GIF corto del scroll infinito. Es lo primero que mira quien abre el repo. -->

---

## Qué hace

Explorador del universo de Rick and Morty: listado paginado de personajes con filtros,
ficha de detalle, episodios y localizaciones, y un apartado de **personajes relacionados**
calculado en el propio cliente.

## Lo que este repositorio demuestra

El interés del proyecto no está en la API que consume, sino en cómo la consume:

### 1. Carga en dos fases: GraphQL para el índice, REST para los datos

La API pública ofrece las dos interfaces, y cada una es mejor en una cosa distinta:

```
       ┌──────────────┐   1. consulta el índice        ┌─────────────────┐
       │  GraphQL     │◄───────────────────────────────│                 │
       │  /graphql    │    solo IDs, filtrados         │   Repositorio   │
       └──────────────┘───────────────────────────────►│                 │
                                                       │                 │
       ┌──────────────┐   2. hidrata en lote           │                 │
       │  REST        │◄───────────────────────────────│                 │
       │ /character/… │    character/1,2,3,4,5         │                 │
       └──────────────┘───────────────────────────────►└────────┬────────┘
                                                                │ 3. cachea
                                                       ┌────────▼────────┐
                                                       │   Room + Paging │
                                                       └─────────────────┘
```

**GraphQL** devuelve únicamente la lista de IDs que cumple el filtro —una respuesta
mínima—, y **REST** hidrata después los objetos completos **en lote**, aprovechando que sus
endpoints aceptan listas (`character/1,2,3`). El resultado: menos bytes por página y muchas
menos peticiones que paginando solo por REST.

### 2. Paginación desde la base de datos, no desde la red

Room es la fuente de verdad. Paging 3 (`room-paging`) pagina sobre las entidades locales,
así que el scroll funciona sin conexión y la red solo rellena huecos. La UI observa un
`Flow<PagingData<…>>` y no sabe de dónde vienen los datos.

### 3. Un pequeño algoritmo de relacionados en la capa de dominio

`GetRelatedCharacters` no hace ninguna llamada extra: cuenta las co-apariciones de
personajes en los episodios del personaje seleccionado, las ordena por frecuencia y devuelve
un `PagingData` con los más relacionados primero. Lógica de negocio pura, testeable y fuera
de la UI.

### 4. Separación por capas con casos de uso

`data` → `domain` → `ui`, con DTOs y entidades de Room que nunca cruzan hacia la
presentación (`DTOMapper` y `Mappers` se encargan), casos de uso de una sola
responsabilidad agrupados por recurso, e inyección con Hilt.

## Estructura del proyecto

```
app/src/main/java/com/gmail/uli153/rickmortyandulises/
├── data/
│   ├── dto/            # respuestas de red (CharacterDTO, EpisodeDTO, LocationDTO)
│   ├── entities/       # entidades de Room + respuestas de IDs de GraphQL
│   ├── daos/           # CharacterDao, EpisodeDao, LocationDao
│   ├── datasource/     # RMULocalDataSource / RMURemoteDataSource (+ impls)
│   ├── services/       # ApiService (REST) · GraphQLService (índices de IDs)
│   ├── RMUDatabase.kt  # base de datos Room + Converters
│   └── DTOMapper.kt
├── domain/
│   ├── models/         # modelos de dominio
│   ├── paging/         # ResourcePagingData y variantes por recurso / por IDs
│   ├── usecases/       # GetAllCharacters, GetCharacterById, GetRelatedCharacters, ...
│   └── RMURepository.kt (+ Impl)
├── ui/
│   ├── screens/        # CharacterList, CharacterDetail, EpisodeList, LocationList
│   ├── views/          # TopBar, BottomBar, CharacterFilter, RelatedCharacterCell
│   ├── dialogs/        # EpisodeListDialog
│   ├── viewmodels/
│   └── theme/          # Color, Dimens, Font, Theme
├── di/                 # AppModule, ActivityModule (Hilt)
└── navigation/         # NavigationGraph, NavigationItem
```

## Stack

| Área | Tecnología |
|---|---|
| Lenguaje | Kotlin (JVM 17) |
| UI | Jetpack Compose + Material 3, ConstraintLayout Compose |
| Navegación | Navigation Compose |
| Inyección de dependencias | Hilt (Dagger) |
| Red | Retrofit + OkHttp + Gson · REST y GraphQL |
| Persistencia y paginación | Room + `room-paging` + Paging 3 |
| Imágenes y animación | Coil · Lottie · Accompanist |
| SDK | minSdk 24 · targetSdk 33 |

## Cómo ejecutarlo

Requisitos: **Android Studio** reciente, **JDK 17** y un emulador o dispositivo con **API 24+**.
No hace falta ninguna clave: la API es pública y las URLs base ya vienen en `BuildConfig`
(`API_BASE_URL` y `GRAPH_QL_BASE_URL`).

```bash
git clone https://github.com/ulisescervera/rickmortyandulises.git
cd rickmortyandulises
./gradlew installDebug
```

## Pendiente

- [ ] Tests unitarios de casos de uso y mappers (hoy solo está el instrumentado de plantilla).
- [ ] Sustituir las consultas GraphQL en texto plano por Apollo Kotlin, para tener tipado y
      generación de código.
- [ ] `RemoteMediator` explícito, en lugar de coordinar red y caché en el repositorio.
- [ ] Activar R8 en `release` y actualizar `compileSdk`/`targetSdk`.
- [ ] Migrar de kapt a KSP (mejora notable de tiempos de build con Hilt y Room).

## Licencia

MIT — ver [LICENSE](LICENSE).
