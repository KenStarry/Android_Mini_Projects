package com.example.amplayer.models

import android.os.Parcelable
import java.io.Serializable

class SongsModel(
    var songPath: String?,
    var songTitle: String?,
    var songDuration: String?,
    var songArtist: String?,
    var songAlbum: String?
) : Serializable