/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import java.util.List;

public interface GenericDao<T> {

    List<T> findAll();

    T findById(int id);

    void insert(T object);

    void update(T object);

    void delete(T object);

}