package br.com.hold.adega.adega.Interfaces;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public interface CRUDInterface <T> {

    public void create(T classe);

    public T read(String key);

    public List<T> readAll();

    public void update(T classeVelha);

    public void delete(T classe);


}
