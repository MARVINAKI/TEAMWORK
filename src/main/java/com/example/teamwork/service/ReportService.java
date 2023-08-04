package com.example.teamwork.service;

import com.example.teamwork.DTO.ReportDTO;
import com.example.teamwork.model.Report;
import com.example.teamwork.repository.ReportRepository;
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

	public void updateStatus(Long id, Boolean status) {
		Optional<Report> report = findById(id);
		report.ifPresent(value -> {
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

	public Optional<Report> findById(Long id) {
		return reportRepository.findById(id);
	}

	public void deleteReport(Long id) {
		reportRepository.deleteById(id);
	}
}
