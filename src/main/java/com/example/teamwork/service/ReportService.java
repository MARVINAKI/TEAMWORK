package com.example.teamwork.service;

import com.example.teamwork.DTO.ReportDTO;
import com.example.teamwork.model.Report;
import com.example.teamwork.repository.ReportRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

	private final ReportRepository reportRepository;

	public ReportService(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}

	public void addReport(Report report) {
		reportRepository.save(report);
	}


	@Caching(
			evict = {@CacheEvict("reports")},
			put = {@CachePut(value = "reports")},
			cacheable = {@Cacheable("reports")}
	)
	public void updateStatus(Long id, Boolean status) {
		findById(id).ifPresent(value -> {
			value.setReportsStatus(status);
			reportRepository.saveAndFlush(value);
		});
	}

	public List<ReportDTO> findAllByChatId(Long chatId) {
		List<ReportDTO> listDTO = new ArrayList<>();
		for (Report report : reportRepository.findReportsByChatId(chatId)) {
			listDTO.add(Report.convert(report));
		}
		return listDTO;
	}

	@Cacheable(value = "reports")
	public Optional<Report> findById(Long id) {
		return reportRepository.findById(id);
	}

	@CacheEvict("reports")
	public void deleteReport(Long id) {
		reportRepository.deleteById(id);
	}
}
