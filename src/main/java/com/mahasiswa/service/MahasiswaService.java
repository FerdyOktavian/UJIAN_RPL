package com.mahasiswa.service;

import com.mahasiswa.model.Mahasiswa;
import java.util.List;

public interface MahasiswaService {
    void saveMahasiswa(Mahasiswa mahasiswa);
    void updateMahasiswa(Mahasiswa mahasiswa);
    void deleteMahasiswa(int id);
    Mahasiswa getMahasiswaById(int id);
    List<Mahasiswa> getAllMahasiswa();
    List<Mahasiswa> searchMahasiswaByNama(String nama);
}