package mikufan.cx.vtool.service.api.shared

/**
 * Write a list of item to somewhere
 */
interface ItemRecorder<T> {

  fun recordAll(items: Iterable<T>) {
    items.forEach(this::record)
  }

  fun record(item: T)
}