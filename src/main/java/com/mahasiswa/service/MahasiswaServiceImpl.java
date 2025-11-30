package com.mahasiswa.service;

import com.mahasiswa.dao.MahasiswaDAO;
import com.mahasiswa.model.Mahasiswa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MahasiswaServiceImpl implements MahasiswaService {
    
    @Autowired
    private MahasiswaDAO mahasiswaDAO;
    
    @Override
    @Transactional
    public void saveMahasiswa(Mahasiswa mahasiswa) {
        mahasiswaDAO.save(mahasiswa);
    }
    
    @Override
    @Transactional
    public void updateMahasiswa(Mahasiswa mahasiswa) {
        mahasiswaDAO.update(mahasiswa);
    }
    
    @Override
    @Transactional
    public void deleteMahasiswa(int id) {
        mahasiswaDAO.delete(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Mahasiswa getMahasiswaById(int id) {
        return mahasiswaDAO.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Mahasiswa> getAllMahasiswa() {
        return mahasiswaDAO.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Mahasiswa> searchMahasiswaByNama(String nama) {
        return mahasiswaDAO.findByNama(nama);
    }
}