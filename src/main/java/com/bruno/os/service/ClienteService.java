package com.bruno.os.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.os.domain.Cliente;
import com.bruno.os.domain.Pessoa;
import com.bruno.os.dtos.ClienteDTO;
import com.bruno.os.repositories.ClienteRepository;
import com.bruno.os.repositories.PessoaRepository;
import com.bruno.os.service.exceptions.DataIntegratyViolationException;
import com.bruno.os.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Cliente findById(Integer id) {
		java.util.Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Objeto nao encontrado! " + id + ", Tipo" + Cliente.class.getName()));
	}

	/*
	 * Busca todos os tecnicos da base de dados
	 */

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	/*
	 * Cria um Cliente
	 */
	public Cliente create(ClienteDTO objDTO) {
		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		Cliente newObj = new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		return repository.save(newObj);

	}
	/*
	 * Atualiza um Cliente
	 */

	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		Cliente oldObj = findById(id);

		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());

		return repository.save(oldObj);
	}

	/*
	 * Deleta um Cliente
	 */
	public void delete(Integer id) {
		Cliente obj = findById(id);

		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Cliente Possui Ordens de Serviço, Não pode ser deletado ! ");
		}

		repository.deleteById(id);
	}

	/*
	 * Busca Cliente pelo cpf
	 */
	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

}
