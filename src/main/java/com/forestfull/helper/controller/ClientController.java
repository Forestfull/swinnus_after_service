package com.forestfull.helper.controller;

import com.forestfull.helper.domain.Client;
import com.forestfull.helper.entity.Json;
import com.forestfull.helper.entity.NetworkVO;
import com.forestfull.helper.handler.JsonTypeHandler;
import com.forestfull.helper.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    public static class URI {
        public static final String SUPPORT = "/support";
        public static final String SUPPORT_HISTORY = "/support/history";
    }

    @GetMapping(URI.SUPPORT_HISTORY)
    NetworkVO.Response<List<Client.History>> getHistoriesByClientToken(
            @RequestParam(name = "token") String token
            , @RequestParam(name = "exceptedIds", required = false) List<Long> exceptedIds) {
        return NetworkVO.Response.ok(NetworkVO.DATA_TYPE.JSON, clientService.getHistoriesByClientToken(token, exceptedIds));
    }

    @PostMapping(URI.SUPPORT)
    NetworkVO.Response<String> toRequestForSolution(
            @RequestBody Json requestData
            , @RequestHeader String client
            , @RequestHeader String ipAddress
    ) throws IOException {
        clientService.toRequestForSolution(JsonTypeHandler.reader.readValue(client, Client.class).getId(), ipAddress, requestData);
        return NetworkVO.Response.ok(NetworkVO.DATA_TYPE.STRING, "Success");
    }
}