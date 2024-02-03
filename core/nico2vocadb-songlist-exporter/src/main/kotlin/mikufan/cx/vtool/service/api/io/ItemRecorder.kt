package mikufan.cx.vtool.service.api.io

/**
 * Write a list of item to somewhere
 *
 * Implementation can choose to override [recordAll] and discard [record]
 * if writing one-by-one is not efficient/possible
 */
interface ItemRecorder<T> {

  fun recordAll(items: Iterable<T>) {
    items.forEach(this::record)
  }

  /**
   * write an item
   */
  fun record(item: T)
}