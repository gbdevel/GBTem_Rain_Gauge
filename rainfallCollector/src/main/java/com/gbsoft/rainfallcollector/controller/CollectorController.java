package com.gbsoft.rainfallcollector.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gbsoft.rainfallcollector.controller.request.DLTransmitRequest;
import com.gbsoft.rainfallcollector.controller.request.InitEquipmentRequest;
import com.gbsoft.rainfallcollector.controller.request.InitTerminalRequest;
import com.gbsoft.rainfallcollector.controller.request.RagEquipmentRequest;
import com.gbsoft.rainfallcollector.controller.request.RainRecvDateRequest;
import com.gbsoft.rainfallcollector.controller.request.RebootEquipmentRequest;
import com.gbsoft.rainfallcollector.controller.request.RebootTerminalRequest;
import com.gbsoft.rainfallcollector.controller.request.SaveRainfallDataRequest;
import com.gbsoft.rainfallcollector.controller.response.CommonResponse;
import com.gbsoft.rainfallcollector.controller.response.InitEquipmentResponse;
import com.gbsoft.rainfallcollector.controller.response.InitEquipmentValue;
import com.gbsoft.rainfallcollector.controller.response.InitTerminalResponse;
import com.gbsoft.rainfallcollector.controller.response.InitTerminalValue;
import com.gbsoft.rainfallcollector.controller.response.RagEquipmentResponse;
import com.gbsoft.rainfallcollector.controller.response.RagEquipmentValue;
import com.gbsoft.rainfallcollector.controller.response.RainRecvDateResoponse;
import com.gbsoft.rainfallcollector.controller.response.RainRecvDateValue;
import com.gbsoft.rainfallcollector.controller.response.RainfallSaveResponse;
import com.gbsoft.rainfallcollector.controller.response.RainfallSaveValue;
import com.gbsoft.rainfallcollector.controller.response.RebootEquipmentResponse;
import com.gbsoft.rainfallcollector.controller.response.RebootEquipmentValue;
import com.gbsoft.rainfallcollector.controller.response.RebootTerminalResponse;
import com.gbsoft.rainfallcollector.controller.response.RebootTerminalValue;
import com.gbsoft.rainfallcollector.domain.EquipByCust;
import com.gbsoft.rainfallcollector.domain.Equipment;
import com.gbsoft.rainfallcollector.domain.Terminal;
import com.gbsoft.rainfallcollector.exception.EssentialParameterNotExsitsException;
import com.gbsoft.rainfallcollector.service.EquipmentService;
import com.gbsoft.rainfallcollector.service.RainGaugeService;
import com.gbsoft.rainfallcollector.service.RainfallService;
import com.gbsoft.rainfallcollector.service.TerminalService;
import com.gbsoft.rainfallcollector.util.ResponseUtil;

import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/raingauge")
@RestController
@RequiredArgsConstructor
public class CollectorController {

	private final TerminalService terminalService;
	private final RainfallService rainfallService;
	
	private final EquipmentService equipmentService;
	
	private final RainGaugeService rainGaugeService;

	@PostMapping("/init")
	public ResponseEntity<? extends CommonResponse> init(
		@RequestBody @Valid InitEquipmentRequest equipmentRequest,
//		@RequestBody @Valid InitTerminalRequest initTerminalRequest,
		BindingResult bindingResult,
		HttpServletRequest request
	) {
		// 맥 어드레스 필수값 검증.
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();

			for (FieldError fieldError: fieldErrors) {
				errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			throw new EssentialParameterNotExsitsException(errors);
		}

//		initTerminalRequest.validate();

//		Terminal terminal = terminalService.initializeTerminal(initTerminalRequest);
		
		Equipment equipment = equipmentService.initalizeEquipement(equipmentRequest);
		
		InitEquipmentResponse response = new InitEquipmentResponse(new InitEquipmentValue(equipment));
//		InitTerminalResponse response = new InitTerminalResponse(new InitTerminalValue(terminal));
		return ResponseUtil.makeResponseEntity(request, response);
	}

	@PostMapping("/reboot")
	public ResponseEntity<? extends CommonResponse> reboot(
//		@RequestBody @Valid RebootTerminalRequest rebootTerminalRequest,
		@RequestBody @Valid RebootEquipmentRequest rebootEquipmentRequest,	
		BindingResult bindingResult,
		HttpServletRequest request
	) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();

			for (FieldError fieldError: fieldErrors) {
				errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			throw new EssentialParameterNotExsitsException(errors);
		}

//		rebootTerminalRequest.validate();
		
		rebootEquipmentRequest.validate();

		EquipByCust equipByCust = equipmentService.rebootEquipment(rebootEquipmentRequest);

		RebootEquipmentResponse response = new RebootEquipmentResponse(new RebootEquipmentValue(equipByCust));
		return ResponseUtil.makeResponseEntity(request, response);
	}

	@PostMapping("/save")
	public ResponseEntity<? extends CommonResponse> collect(
		@RequestBody @Valid SaveRainfallDataRequest saveRainfallDataRequest,
		BindingResult bindingResult,
		HttpServletRequest request) {
		try {
			if (bindingResult.hasErrors()) {
				Map<String, String> errors = new HashMap<>();
				List<FieldError> fieldErrors = bindingResult.getFieldErrors();

				for (FieldError fieldError: fieldErrors) {
					errors.put(fieldError.getField(), fieldError.getDefaultMessage());
				}
				throw new EssentialParameterNotExsitsException(errors);
			}

			saveRainfallDataRequest.validate();

			rainfallService.saveRainFall(saveRainfallDataRequest);

			RainfallSaveResponse response = new RainfallSaveResponse(new RainfallSaveValue());
			return ResponseUtil.makeResponseEntity(request, response);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	
	@PostMapping("/rgsend")
	public ResponseEntity<? extends CommonResponse> rainRecv(
//		@RequestBody @Valid SaveRainfallDataRequest saveRaindfallDataRequest,
		@RequestBody @Valid RainRecvDateRequest rainRecvDateRequest,
		BindingResult bindingResult,
		HttpServletRequest request) {
		try {
			if (bindingResult.hasErrors()) {
				Map<String, String> errors = new HashMap<>();
				List<FieldError> fieldErrors = bindingResult.getFieldErrors();

				for (FieldError fieldError: fieldErrors) {
					errors.put(fieldError.getField(), fieldError.getDefaultMessage());
				}
				throw new EssentialParameterNotExsitsException(errors);
			}

			rainRecvDateRequest.validate();

			rainGaugeService.rainRecvDate(rainRecvDateRequest);

			RainRecvDateResoponse response = new RainRecvDateResoponse(new RainRecvDateValue());
			return ResponseUtil.makeResponseEntity(request, response);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	@PostMapping("/regEquipment")
	public ResponseEntity<? extends CommonResponse> regEquipment(
		@RequestBody @Valid RagEquipmentRequest ragEquipmentRequest,
		BindingResult bindingResult,
		HttpServletRequest request) {
		try {
			if (bindingResult.hasErrors()) {
				Map<String, String> errors = new HashMap<>();
				List<FieldError> fieldErrors = bindingResult.getFieldErrors();

				for (FieldError fieldError: fieldErrors) {
					errors.put(fieldError.getField(), fieldError.getDefaultMessage());
				}
				throw new EssentialParameterNotExsitsException(errors);
			}

			Equipment equipment = equipmentService.regEquipment(ragEquipmentRequest);

			RagEquipmentResponse response = new RagEquipmentResponse(new RagEquipmentValue(equipment));
			return ResponseUtil.makeResponseEntity(request, response);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
}