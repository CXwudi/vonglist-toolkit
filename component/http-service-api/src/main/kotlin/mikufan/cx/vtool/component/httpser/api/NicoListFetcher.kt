package mikufan.cx.vtool.component.httpser.api

import mikufan.cx.vtool.shared.model.niconico.NicoListItem
import mikufan.cx.vtool.shared.model.niconico.NicoListSortPreference

/**
 * Interface for fetching song lists from NicoNico.
 *
 * Provides a method for reading all songs from a list given its ID and a sort preference.
 *
 * The implementation of this interface should lazily read the list page by page using NicoNico API,
 * and should return an iterator of `NicoListItem` which represents the songs in the list.
 *
 * @see NicoListItemIterator
 * @see NicoListItem
 * @see NicoListSortPreference
 */
interface NicoListFetcher {
  /**
   * Reads all songs from a list on NicoNico given its ID and a sort preference.
   *
   * @param id The ID of the song list.
   * @param sortPreference The preference for sorting the songs in the list.
   * @return An iterator of `NicoListItem` which represents the songs in the list.
   */
  fun readAllSongsFromList(
    id: Long,
    sortPreference: NicoListSortPreference,
  ): NicoListItemIterator
}

interface NicoListItemIterator : Iterator<NicoListItem>