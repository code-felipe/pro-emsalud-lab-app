package com.proemsalud.laboratory.oder.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.proemsalud.laboratory.doctor.domain.Doctor;
import com.proemsalud.laboratory.oder.domain.Order;
import com.proemsalud.laboratory.oder.dto.create.CreateOrderRequest;
import com.proemsalud.laboratory.oder.dto.response.OrderReportResponse;
import com.proemsalud.laboratory.oder.dto.response.OrderResponse;

import com.proemsalud.laboratory.patient.domain.Patient;
import com.proemsalud.laboratory.result.dto.response.TestResultReportResponse;
import com.proemsalud.laboratory.result.mapper.TestResultMapper;
import com.proemsalud.laboratory.test.simpleschemistry.dto.response.SimpleChemistryReportResponse;
import com.proemsalud.laboratory.test.simpleschemistry.mapper.SimpleChemistryMapper;
import com.proemsalud.laboratory.test.stool.dto.response.StoolReportResponse;
import com.proemsalud.laboratory.test.stool.mapper.StoolMapper;
import com.proemsalud.laboratory.test.torch.dto.response.TorchReportResponse;
import com.proemsalud.laboratory.test.torch.mapper.TorchMapper;
import com.proemsalud.laboratory.test.urinalysis.dto.response.UrinalysisReportResponse;
import com.proemsalud.laboratory.test.urinalysis.mapper.UrinalysisMapper;
import com.proemsalud.laboratory.test.widal.dto.response.WidalReactionReportResponse;
import com.proemsalud.laboratory.test.widal.dto.response.WidalReactionResponse;
import com.proemsalud.laboratory.test.widal.mapper.WidalReactionMapper;

@Component
public class OrderMapper {
	
	@Autowired
	private SimpleChemistryMapper simpleChemistryMapper;
	
	@Autowired
	private WidalReactionMapper widalReactionMapper;
	
	@Autowired
	private UrinalysisMapper urinalysisMapper;
	
	@Autowired
	private StoolMapper stoolMapper;
	
	@Autowired
	private TorchMapper torchMapper;
	
	public Order toEntity(CreateOrderRequest request, Doctor doctor, Patient patient) {
		return Order.builder()
			.doctor(doctor)
			.patient(patient)
			.build();
	}

	public OrderResponse toResponse(Order order) {
		return OrderResponse.builder()
			.id(order.getId())
			.status(order.getStatus())
			.patientId(order.getPatient().getId())
			.doctorId(order.getDoctor().getId())
			.doctorName(order.getDoctor().getFirstName())
			.doctorFullName(order.getDoctor().getFullName())
			.totalOrder(this.totalOrder(order))
			.doctorTitle(order.getDoctor().getTitle())
			.createdAt(order.getCreatedAt())
			.updatedAt(order.getUpdatedAt())
			.build();
	}
	
	public OrderReportResponse toReport(Order order) {
	    

	    return OrderReportResponse.builder()
	            .id(order.getId())
	            .patientId(order.getPatient().getId())
	            .patientFullName(order.getPatient().getFullName())
	            .patientAge(order.getPatient().calculateAgeDisplay())
	            .patientPhoneNumber(order.getPatient().getPrimaryPhoneNumber())
	            .doctorFullName(order.getDoctor().getFullName())
	            .doctorTitle(order.getDoctor().getTitle())
	            .createdAt(order.getCreatedAt())
	            .simpleChemistries(this.simpleChemistries(order))
	            .widalReactions(this.widalReactions(order))
	            .urinalysis(this.urinalysis(order))
	            .stools(this.stool(order))
	            .torchs(this.torch(order))
	            .build();
	}
	
	private BigDecimal totalOrder(Order order) {
		
	    BigDecimal simpleChemistry = order.calcTotalPriceSimpleChemistry();
	    BigDecimal widalReaction = order.calcTotalPriceWidalReaction();
	    BigDecimal urinalysis = order.calcTotalUrinalysis();
	    BigDecimal stool = order.calcTotalStool();
	    BigDecimal torch = order.calcTotalTorch();

	    BigDecimal totalSimple = (simpleChemistry != null) ? simpleChemistry : BigDecimal.ZERO;
	    BigDecimal totalWidal = (widalReaction != null) ? widalReaction : BigDecimal.ZERO;
	    BigDecimal totalUrinalysis = (urinalysis != null) ? urinalysis : BigDecimal.ZERO;
	    BigDecimal totalStool = (stool != null) ? stool : BigDecimal.ZERO;
	    BigDecimal totalTorch = (torch != null) ? torch : BigDecimal.ZERO;

	    return totalSimple
	    		.add(totalWidal)
	    		.add(totalUrinalysis)
	    		.add(totalStool)
	    		.add(totalTorch);
	}
	
	private List<SimpleChemistryReportResponse> simpleChemistries(Order order){
		return  order.getSimpleChemistries()
				   .stream()
				   .map(simpleChemistrie -> simpleChemistryMapper.toReport(simpleChemistrie))
				   .toList();
	}
	
	private List<WidalReactionReportResponse> widalReactions(Order order){
		return  order.getWidalReactions()
				   .stream()
				   .map(widalReaction -> widalReactionMapper.toReport(widalReaction))
				   .toList();
	}
	
	private List<UrinalysisReportResponse> urinalysis(Order order){
		return  order.getUrinalysis()
				   .stream()
				   .map(uri -> urinalysisMapper.toReport(uri))
				   .toList();
	}
	
	
	private List<StoolReportResponse> stool(Order order){
		return  order.getStools()
				   .stream()
				   .map(stool -> stoolMapper.toReport(stool))
				   .toList();
	}
	
	private List<TorchReportResponse> torch(Order order){
		return  order.getTorchs()
				   .stream()
				   .map(stool -> torchMapper.toReport(stool))
				   .toList();
	}
//	public PendingOrderNotificationResponse toNotification(Order order) {
//		
//		return PendingOrderNotificationResponse.builder()
//				.id(order.getId())
//				.patientId(order.getPatient().getId())
//				.patientFullName(order.getPatient().getFullName())
//				.status(order.getStatus())
//				.createdAt(order.getCreatedAt())
//				.build();
//	}
	
//	private Double totalOrder(Order order) {
//		
//		Double total = order.getResults().stream()
//				.map(price -> price.get)
//	}
	
}
