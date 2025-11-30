package com.mahasiswa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mahasiswa")
public class Mahasiswa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(unique = true, nullable = false)
    private String nim;
    
    @Column(nullable = false)
    private String nama;
    
    private String jurusan;
    private int angkatan;
    private String email;
    
    // Constructor kosong (wajib untuk Hibernate)
    public Mahasiswa() {}
    
    // Constructor dengan parameter
    public Mahasiswa(String nim, String nama, String jurusan, int angkatan, String email) {
        this.nim = nim;
        this.nama = nama;
        this.jurusan = jurusan;
        this.angkatan = angkatan;
        this.email = email;
    }
    
    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }
    
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    
    public String getJurusan() { return jurusan; }
    public void setJurusan(String jurusan) { this.jurusan = jurusan; }
    
    public int getAngkatan() { return angkatan; }
    public void setAngkatan(int angkatan) { this.angkatan = angkatan; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}