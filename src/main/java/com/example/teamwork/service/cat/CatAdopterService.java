package com.example.teamwork.service.cat;

import com.example.teamwork.DTO.cat.CatAdopterDTO;
import com.example.teamwork.model.CatAdopter;
import com.example.teamwork.repository.cat.CatAdopterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatAdopterService {

	private final CatAdopterRepository catAdopterRepository;

	public CatAdopterService(CatAdopterRepository catAdopterRepository) {
		this.catAdopterRepository = catAdopterRepository;
	}

	public void addAdopter(CatAdopter catAdopter) {
		catAdopterRepository.save(catAdopter);
	}

	public Optional<CatAdopter> findByFullName(String fullName) {
		return catAdopterRepository.findCatAdopterByFullName(fullName);
	}

	public Optional<CatAdopter> findById(Long id) {
		return catAdopterRepository.findById(id);
	}

	public List<CatAdopterDTO> findAllAdopters() {
		List<CatAdopterDTO> listDTO = new ArrayList<>();
		for (CatAdopter catAdopter : catAdopterRepository.findAll()) {
			listDTO.add(CatAdopter.convert(catAdopter));
		}
		return listDTO;
	}

	public void deleteAdopter(Long id) {
		catAdopterRepository.deleteById(id);
	}
}
