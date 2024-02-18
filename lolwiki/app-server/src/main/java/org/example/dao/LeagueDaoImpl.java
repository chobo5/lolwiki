package org.example.dao;

import org.example.vo.League;

import java.util.List;

public class LeagueDaoImpl implements Dao<League> {
    @Override
    public void add(League league) {

    }

    @Override
    public List<League> findAll() {
        return null;
    }

    @Override
    public League findBy(int id) {
        return null;
    }

    @Override
    public List<League> findBy(String keyword) {
        return null;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int delete(String keyword) {
        return 0;
    }

    @Override
    public int update(League league) {
        return 0;
    }
}
