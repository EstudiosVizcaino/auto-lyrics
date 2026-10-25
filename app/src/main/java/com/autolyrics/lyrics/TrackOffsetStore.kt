package com.autolyrics.lyrics

import android.content.Context
import com.autolyrics.model.TrackInfo

/**
 * Remembers the manual sync correction per exact track: title, artist, album and duration in whole
 * seconds (the duration tells different versions of the same song apart).
 */
class TrackOffsetStore(context: Context) {

    private val prefs = context.getSharedPreferences("auto_lyrics_track_offsets", Context.MODE_PRIVATE)

    fun get(track: TrackInfo): Long = prefs.getLong(key(track), 0L)

    fun set(track: TrackInfo, offsetMs: Long) {
        val editor = prefs.edit()
        if (offsetMs == 0L) editor.remove(key(track)) else editor.putLong(key(track), offsetMs)
        editor.apply()
    }

    private fun key(track: TrackInfo): String {
        val identity = listOf(track.title, track.artist, track.album)
            .joinToString("|") { it.lowercase().trim() }
        return "$identity|${track.durationMs / 1000}"
    }
}
