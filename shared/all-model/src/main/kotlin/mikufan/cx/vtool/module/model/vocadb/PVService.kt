package mikufan.cx.vtool.module.model.vocadb

enum class PvService {
  NicoNicoDouga,
  Youtube,
  SoundCloud,
  Vimeo,
  Piapro,
  Bilibili,
  File,
  LocalFile,
  Creofuga,
  Bandcamp;

  companion object {
    fun fromString(value: String): PvService {
      return when (value) {
        "NicoNicoDouga" -> NicoNicoDouga
        "Youtube" -> Youtube
        "SoundCloud" -> SoundCloud
        "Vimeo" -> Vimeo
        "Piapro" -> Piapro
        "Bilibili" -> Bilibili
        "File" -> File
        "LocalFile" -> LocalFile
        "Creofuga" -> Creofuga
        "Bandcamp" -> Bandcamp
        else -> throw IllegalArgumentException("No enum constant mikufan.cx.vtool.module.model.vocadb.PvService.$value")
      }
    }
  }
}