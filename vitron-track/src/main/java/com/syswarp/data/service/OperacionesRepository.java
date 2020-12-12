package com.syswarp.data.service;

import com.syswarp.data.entity.Operaciones;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface OperacionesRepository extends JpaRepository<Operaciones, Integer> {

}