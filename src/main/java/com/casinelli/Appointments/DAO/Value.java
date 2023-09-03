package com.casinelli.Appointments.DAO;

/**
 * Template Class to hold any object needed to pass to a database query lambda expression
 * @param <T>
 */
public class Value<T> {
    private T t;

    public Value(T t){
        this.t = t;
    }

    public T getValue(){
        return t;
    }
}
