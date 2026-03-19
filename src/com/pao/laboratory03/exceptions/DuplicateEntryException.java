package com.pao.laboratory03.exceptions;

public class DuplicateEntryException extends RuntimeException {
    String entry1;
//    String entry2;

    DuplicateEntryException(String msg)
    {
            super(msg);
//            this.entry1=entry1;
//            this.entry2=entry2;

    }
}
