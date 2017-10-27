package com.kopernik.coincap;

import com.google.gson.Gson;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CoinCap
{
    private static final String COINCAP_API = "http://coincap.io/page/ZEN";

    public CryptoCurrencyQuote queryCoinCap() {
        CryptoCurrencyQuote cryptoCurrencyQuote = null;

        try {

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet getRequest = new HttpGet(COINCAP_API);
            getRequest.addHeader("accept", "application/json");

            CloseableHttpResponse httpResponse = httpClient.execute(getRequest);

            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                                           + httpResponse.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                new InputStreamReader((httpResponse.getEntity().getContent())));

            Gson gson = new Gson();
            cryptoCurrencyQuote = gson.fromJson(br, CryptoCurrencyQuote.class);

            httpClient.close();

        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
        return cryptoCurrencyQuote;
    }

    public Map<String, BigDecimal> getFiatConversion(){

        Map<String, BigDecimal> fiatAmounts = new HashMap<>();
        BigDecimal usdAmount;
        BigDecimal eurAmount;

        CryptoCurrencyQuote cryptoCurrencyQuote = queryCoinCap();
        usdAmount = new BigDecimal(cryptoCurrencyQuote.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
        fiatAmounts.put("USD", usdAmount);
        eurAmount = new BigDecimal(cryptoCurrencyQuote.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
        fiatAmounts.put("EUR", eurAmount);

        return fiatAmounts;
    }

}
