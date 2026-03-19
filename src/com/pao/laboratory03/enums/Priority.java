package com.pao.laboratory03.enums;

public enum Priority {
    LOW(1, "green") {
        @Override
        public String getEmoji() { return "\uD83D\uDFE2"; }
    },
    MEDIUM(2, "yellow") {
        @Override
        public String getEmoji() { return "\uD83D\uDFE1"; }
    },
    HIGH(3, "orange") {
        @Override
        public String getEmoji() { return "\uD83D\uDFE0"; }
    },
    CRITICAL(4, "red") {
        @Override
        public String getEmoji() { return "\uD83D\uDD34"; }
    };
    private int level;
    private String color;


    private Priority(int level, String color){
        this.level=level;
        this.color=color;
    }

    int getLevel(){
        return level;
    }

    String getColor(){
        return color;
    }

    public abstract String getEmoji();

//
//    public static Priority valueOf(String a){
//        switch (a) {
//            case "LOW":
//                return Priority.LOW;
//            case "MEDIUM":
//                return Priority.MEDIUM;
//            case "HIGH":
//                return Priority.HIGH;
//            case "CRITICAL":
//                   return Priority.CRITICAL;
//        }
//        return Priority.LOW;
//    }

}
