package org.example.dao;

public interface RelationshipDao<T> {
    void add(T t);
    T findByForeignKey(int foreignKeyId); // 예시 메서드
    int delete(int id, int foreignKeyId); // 예시 메서드
    int update(T t);
}
