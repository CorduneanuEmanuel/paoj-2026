package com.pao.laboratory05.playlist;

import java.util.Comparator;

public class SongDurationComparator implements Comparator<Song> {
    public int compare(Song a, Song b){
        return a.durationSeconds()-b.durationSeconds();
    }
}
