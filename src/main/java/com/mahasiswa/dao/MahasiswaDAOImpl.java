package com.mahasiswa.dao;

import com.mahasiswa.model.Mahasiswa;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class MahasiswaDAOImpl implements MahasiswaDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void save(Mahasiswa mahasiswa) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(mahasiswa);
    }
    
    @Override
    public void update(Mahasiswa mahasiswa) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(mahasiswa);
    }
    
    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Mahasiswa mhs = session.get(Mahasiswa.class, id);
        if (mhs != null) {
            session.remove(mhs);
        }
    }
    
    @Override
    public Mahasiswa findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Mahasiswa.class, id);
    }
    
    @Override
    public List<Mahasiswa> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Mahasiswa> query = session.createQuery("from Mahasiswa", Mahasiswa.class);
        return query.getResultList();
    }
    
    @Override
    public List<Mahasiswa> findByNama(String nama) {
        Session session = sessionFactory.getCurrentSession();
        Query<Mahasiswa> query = session.createQuery(
            "from Mahasiswa where nama like :nama", Mahasiswa.class);
        query.setParameter("nama", "%" + nama + "%");
        return query.getResultList();
    }
}