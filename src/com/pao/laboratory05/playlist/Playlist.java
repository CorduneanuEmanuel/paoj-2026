package com.pao.laboratory05.playlist;

import java.util.Arrays;

public class Playlist {
    private String name;
    private Song[] songs;
    public Playlist(String name){
        songs=new Song[0];
        this.name=name;
    }
    public void addSong(Song song){
        Song[] sngs = new Song[songs.length+1];
        System.arraycopy(songs, 0, sngs, 0, songs.length);
        sngs[songs.length]=song;
        this.songs=sngs;

    }

    public void printSortedByTitle(){
        Song[] sngs=new Song[songs.length];
        System.arraycopy(songs, 0, sngs, 0, songs.length);
        Arrays.sort(sngs);
        for(var i : sngs){
            System.out.println(i.title());
        }

    }

    void printSortedByDuration(){
        Song[] sngs=new Song[songs.length];
        System.arraycopy(songs, 0, sngs, 0, songs.length);
        Arrays.sort(sngs, new SongDurationComparator());
        for(var i : sngs){
            System.out.println(i.title()+ " "+i.durationSeconds());
        }
    }
    int getTotalDuration(){
        Song[] sngs=new Song[songs.length];
        System.arraycopy(songs, 0, sngs, 0, songs.length);
        int suma=0;
        for(var i : sngs){
            suma+=i.durationSeconds();
        }
        return suma;

    }

    public String getName() {
        return name;
    }
}
