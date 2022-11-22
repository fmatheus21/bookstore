package com.fmatheus.app.controller.resource;

import com.fmatheus.app.constant.HttpStatusConstant;
import com.fmatheus.app.controller.constant.OperationConstant;
import com.fmatheus.app.controller.constant.ResourceConstant;
import com.fmatheus.app.controller.dto.request.CambiumDtoRequest;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.controller.rule.CambiumRule;
import com.fmatheus.app.exception.handler.response.MessageResponse;
import com.fmatheus.app.exception.swagger.BadRequest;
import com.fmatheus.app.exception.swagger.Forbidden;
import com.fmatheus.app.exception.swagger.ServerError;
import com.fmatheus.app.exception.swagger.Unauthorized;
import com.fmatheus.app.rule.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@Tag(name = OperationConstant.TAG_CAMBIUM)
@RestController
@RequestMapping(ResourceConstant.CAMBIUM_CONVERTER)
public class CambiumResource {

    @Autowired
    private CambiumRule rule;

    @Autowired
    private ResponseMessage responseMessage;

    @Operation(summary = OperationConstant.GET, description = OperationConstant.DESCRIPTION_GET)
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusConstant.OK_NUMBER, description = HttpStatusConstant.OK,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = HttpStatusConstant.BAD_REQUEST_NUMBER, description = HttpStatusConstant.BAD_REQUEST,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
            @ApiResponse(responseCode = HttpStatusConstant.UNAUTHORIZED_NUMBER, description = HttpStatusConstant.UNAUTHORIZED,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unauthorized.class))),
            @ApiResponse(responseCode = HttpStatusConstant.FORBIDDEN_NUMBER, description = HttpStatusConstant.FORBIDDEN,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Forbidden.class))),
            @ApiResponse(responseCode = HttpStatusConstant.INTERNAL_SERVER_ERROR_NUMBER, description = HttpStatusConstant.INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerError.class)))
    })
    @GetMapping(ResourceConstant.CONVERTER)
    public ResponseEntity<CambiumDtoResponse> convertCurrency(@PathVariable BigDecimal amount, @PathVariable String fromCurrency, @PathVariable String toCurrency) {
        return ResponseEntity.status(HttpStatus.OK).body(rule.convertCurrency(amount, fromCurrency, toCurrency));
    }

    @Operation(summary = OperationConstant.GET, description = OperationConstant.DESCRIPTION_GET)
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusConstant.OK_NUMBER, description = HttpStatusConstant.OK,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = HttpStatusConstant.BAD_REQUEST_NUMBER, description = HttpStatusConstant.BAD_REQUEST,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
            @ApiResponse(responseCode = HttpStatusConstant.UNAUTHORIZED_NUMBER, description = HttpStatusConstant.UNAUTHORIZED,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unauthorized.class))),
            @ApiResponse(responseCode = HttpStatusConstant.FORBIDDEN_NUMBER, description = HttpStatusConstant.FORBIDDEN,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Forbidden.class))),
            @ApiResponse(responseCode = HttpStatusConstant.INTERNAL_SERVER_ERROR_NUMBER, description = HttpStatusConstant.INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerError.class)))
    })
    @PutMapping(ResourceConstant.ID)
    public ResponseEntity<CambiumDtoResponse> update(@PathVariable int id, @RequestBody @Valid CambiumDtoRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(rule.update(id, request));
    }

}
