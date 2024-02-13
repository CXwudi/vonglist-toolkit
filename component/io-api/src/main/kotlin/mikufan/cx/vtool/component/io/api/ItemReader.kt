package mikufan.cx.vtool.component.io.api

/**
 * Read a list of item from somewhere
 */
interface ItemReader<T> {
  fun readAll(): List<T> = buildList {
    do {
      val item = read()
      if (item != null) add(item)
    } while (item != null)
  }

  /**
   * read next item, return null if no more item
   * @return T? the next item, or null if no more item
   */
  fun read(): T?
}