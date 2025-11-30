package com.mahasiswa.dao;

import com.mahasiswa.model.Mahasiswa;
import java.util.List;

public interface MahasiswaDAO {
    void save(Mahasiswa mahasiswa);           // CREATE
    void update(Mahasiswa mahasiswa);         // UPDATE
    void delete(int id);                      // DELETE
    Mahasiswa findById(int id);               // READ by ID
    List<Mahasiswa> findAll();                // READ All
    List<Mahasiswa> findByNama(String nama);  // SEARCH by nama
}