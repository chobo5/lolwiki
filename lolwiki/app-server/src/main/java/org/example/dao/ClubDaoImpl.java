package org.example.dao;

import org.example.vo.Club;

import java.util.List;

public class ClubDaoImpl implements Dao<Club>{
    @Override
    public void add(Club club) {

    }

    @Override
    public List<Club> findAll() {
        return null;
    }

    @Override
    public Club findBy(int id) {
        return null;
    }

    @Override
    public Club findBy(String keyword) {
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
    public int update(Club club) {
        return 0;
    }
}
