package com.pao.laboratory05.playlist;

public record Song(String title, String artist, int durationSeconds)
        implements Comparable<Song> {
    public Song{};
    public int compareTo(Song o){
        int a=this.title.compareTo(o.title);
        return a;
    }


}
