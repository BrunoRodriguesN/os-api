package com.bruno.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bruno.os.domain.Cliente;
import com.bruno.os.domain.Tecnico;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	

	@Query("SELECT obj FROM Cliente obj WHERE obj.cpf =:cpf")
	Tecnico findByCPF(@Param("cpf") String cpf);
	
}
