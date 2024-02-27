package secondhandmarket.dao;

import java.util.List;

public interface GeneralDao<T> {
    void add(T t);

    List<T> findAll();

    T findBy(int id);

    List<T> findBy(String keyword);

    int delete(int id);

    int delete(String keyword);

    int update(T t);

    default int getPrimaryKeyNo(String name) {
        return 0;
    }


}
