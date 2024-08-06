package com.gbsoft.rainfallcollector.controller;

import com.gbsoft.rainfallcollector.controller.request.*;
import com.gbsoft.rainfallcollector.controller.response.*;
import com.gbsoft.rainfallcollector.domain.EquipByCust;
import com.gbsoft.rainfallcollector.domain.Equipment;
import com.gbsoft.rainfallcollector.exception.EssentialParameterNotExsitsException;
import com.gbsoft.rainfallcollector.service.*;
import com.gbsoft.rainfallcollector.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/raingauge")
@RestController
@RequiredArgsConstructor
public class CollectorController {

    private final TerminalService terminalService;
    private final RainfallService rainfallService;

    private final EquipmentService equipmentService;

    private final RainGaugeService rainGaugeService;
    private final EquipByCustService equipByCustService;

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

            for (FieldError fieldError : fieldErrors) {
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

            for (FieldError fieldError : fieldErrors) {
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

                for (FieldError fieldError : fieldErrors) {
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

                for (FieldError fieldError : fieldErrors) {
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
    public ResponseEntity regEquipment(@RequestBody @Valid RagEquipmentRequest ragEquipmentRequest, HttpServletRequest request) {
        Equipment equipment = equipmentService.regEquipment(ragEquipmentRequest);
        return ResponseUtil.makeResponseEntity(request, new RagEquipmentResponse(equipment.getEquipUuid()));
    }

    @PostMapping("/saveEquipByCust")
    public void saveEquipByCust(@RequestBody @Valid AddEquipByCustRequest addEquipByCustRequest) {
        equipByCustService.saveEquipByCust(addEquipByCustRequest.getMacAddress());
    }
}