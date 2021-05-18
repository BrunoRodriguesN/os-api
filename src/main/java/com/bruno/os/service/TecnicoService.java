package com.bruno.os.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.os.domain.Pessoa;
import com.bruno.os.domain.Tecnico;
import com.bruno.os.dtos.TecnicoDTO;
import com.bruno.os.repositories.PessoaRepository;
import com.bruno.os.repositories.TecnicoRepository;
import com.bruno.os.service.exceptions.DataIntegratyViolationException;
import com.bruno.os.service.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Tecnico findById(Integer id) {
		java.util.Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Objeto nao encontrado! " + id + ", Tipo" + Tecnico.class.getName()));
	}

	/*
	 * Busca todos os tecnicos da base de dados
	 */

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	/*
	 * Cria um tecnico
	 */
	public Tecnico create(TecnicoDTO objDTO) {
		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		Tecnico newObj = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		return repository.save(newObj);

	}
	/*
	 * Atualiza um tecnico
	 */

	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);

		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());

		return repository.save(oldObj);
	}

	/*
	 * Deleta um tecnico
	 */
	public void delete(Integer id) {
		Tecnico obj = findById(id);

		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Tecnico Possui Ordens de Serviço, Não pode ser deletado ! ");
		}

		repository.deleteById(id);
	}

	/*
	 * Busca tecnico pelo cpf
	 */
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

}
