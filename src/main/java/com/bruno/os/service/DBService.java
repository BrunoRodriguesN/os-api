package com.bruno.os.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.os.domain.Cliente;
import com.bruno.os.domain.OS;
import com.bruno.os.domain.Tecnico;
import com.bruno.os.domain.enuns.Prioridade;
import com.bruno.os.domain.enuns.Status;
import com.bruno.os.repositories.ClienteRepository;
import com.bruno.os.repositories.OSRepository;
import com.bruno.os.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;

	
	public void instanciaDB() {

		Tecnico t1 = new Tecnico(null, "Bruno Rodrigues", "393.999.578-96", "(19) 99375-5608");
		Cliente c1 = new Cliente(null, "Betina", "478.754.500-04", "(19) 99375-5000");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste Create OS", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));

	}

}
