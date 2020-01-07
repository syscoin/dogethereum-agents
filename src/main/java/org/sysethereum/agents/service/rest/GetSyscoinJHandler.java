package org.sysethereum.agents.service.rest;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpsExchange;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Sha256Hash;
import org.springframework.stereotype.Service;
import org.sysethereum.agents.core.SyscoinToEthClient;
import org.sysethereum.agents.core.syscoin.SuperblockChain;
import org.sysethereum.agents.core.syscoin.SyscoinWrapper;
import org.sysethereum.agents.util.RestError;

import java.io.IOException;
import java.util.LinkedHashMap;

@Service
@Slf4j(topic = "GetSuperblockJHandler")
public class GetSyscoinJHandler extends CommonHttpHandler {

    private final Gson gson;
    private final SyscoinWrapper syscoinWrapper;

    public GetSyscoinJHandler(
            Gson gson,
            SyscoinWrapper syscoinWrapper
    ) {
        this.gson = gson;
        this.syscoinWrapper = syscoinWrapper;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HttpsExchange httpsExchange = (HttpsExchange) httpExchange;
        if (setOriginAndHandleOptionsMethod(httpsExchange)) return;

        String response;

        try {
            response = gson.toJson(syscoinWrapper.getChainHead().toString());
        } catch (Exception exception) {
            response =  gson.toJson(new RestError("Could not get Superblock, internal error!"));
        }
        writeResponse(httpsExchange, response);
    }
}
