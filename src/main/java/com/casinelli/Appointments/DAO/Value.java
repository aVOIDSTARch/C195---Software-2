package com.casinelli.Appointments.DAO;

/**
 * Template Class to hold any object needed to pass to a database query lambda expression
 * @param <T> value you intend to in this generic Value type
 */
public class Value<T> {
    private final T t;
    public Value(T t){
        this.t = t;
    }
    public T getValue(){
        return t;
    }
}
