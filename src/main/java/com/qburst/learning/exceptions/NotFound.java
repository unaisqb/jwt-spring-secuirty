package com.qburst.learning.exceptions;

public class NotFound  extends RuntimeException{
    public NotFound(Integer id){
        super("Couldn't find resource with " + id);
    }
}
