package com.example.teamwork.controller;

import com.example.teamwork.DTO.ReportDTO;
import com.example.teamwork.model.Report;
import com.example.teamwork.service.ReportService;
import com.example.teamwork.service.dog.DogRegisterService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.teamwork.constant.Constant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {


	@InjectMocks
	private ReportController controller;

	@Mock
	private ReportService service;

	@Mock
	private TelegramBot telegramBot;

	@Mock
	private DogRegisterService dogRegisterService;

	@Test
	void changeStatusOfReportToTrueTest() {
		Report report = new Report(FILE_NAME_1, DESCRIPTION_1);
		report.setId(ID_1);
		report.setChatId(CHAT_ID_1);
		report.setDispatchTime(DISPATCH_TIME_1);
		report.setReportsStatus(REPORTS_STATUS_1);

		when(service.findById(ID_1)).thenReturn(Optional.of(report));
		controller.changeStatusOfReport(ID_1, true);

		verify(service, times(1)).findById(report.getId());
		verify(service, times(1)).updateStatus(ID_1, true);
		verify(dogRegisterService, only()).updateReportsDate(CHAT_ID_1, DISPATCH_TIME_1);
		verify(telegramBot, never()).execute(new SendMessage(report.getChatId(), "test"));
		verify(service, never()).deleteReport(ID_1);
	}

	@Test
	void changeStatusOfReportToFalseTest() {
		Report report = new Report(FILE_NAME_1, DESCRIPTION_1);
		report.setId(ID_1);
		report.setChatId(CHAT_ID_1);
		report.setDispatchTime(DISPATCH_TIME_1);
		report.setReportsStatus(REPORTS_STATUS_2);


		when(service.findById(ID_1)).thenReturn(Optional.of(report));
		controller.changeStatusOfReport(ID_1, false);

		verify(service, times(1)).findById(report.getId());
		verify(service, never()).updateStatus(ID_1, true);
		verify(dogRegisterService, never()).updateReportsDate(CHAT_ID_1, DISPATCH_TIME_1);
		verify(telegramBot).execute(any());
		verify(service, times(1)).deleteReport(report.getId());
	}

	@Test
	void findAllByChatIdTest() {
		ReportDTO reportDTO1 = new ReportDTO(ID_1, CHAT_ID_1, FILE_NAME_1, DESCRIPTION_1, DISPATCH_TIME_1, REPORTS_STATUS_1);
		ReportDTO reportDTO2 = new ReportDTO(ID_2, CHAT_ID_1, FILE_NAME_2, DESCRIPTION_2, DISPATCH_TIME_2, REPORTS_STATUS_2);
		List<ReportDTO> listDTO = Arrays.asList(reportDTO1, reportDTO2);

		when(service.findAllByChatId(CHAT_ID_1)).thenReturn(listDTO);

		ResponseEntity<List<ReportDTO>> listResponseEntity = controller.findAllByChatId(CHAT_ID_1);

		verify(service, only()).findAllByChatId(CHAT_ID_1);

		assertTrue(listResponseEntity.getStatusCode().is2xxSuccessful());
		assertFalse(service.findAllByChatId(CHAT_ID_1).isEmpty());
	}

	@Test
	void findByIdTest() {
		Report report = new Report(FILE_NAME_1, DESCRIPTION_1);

		when(service.findById(ID_1)).thenReturn(Optional.of(report));

		ResponseEntity<ReportDTO> responseEntity = controller.findById(ID_1);

		verify(service, only()).findById(ID_1);

		assertFalse(responseEntity.getStatusCode().is4xxClientError());
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertTrue(service.findById(ID_1).isPresent());
		assertNotNull(service.findById(ID_1));
	}

	@Test
	void deleteReportTest() {
		controller.deleteReport(ID_1);
		verify(service, only()).deleteReport(ID_1);
		verify(service, never()).deleteReport(ID_2);
	}
}