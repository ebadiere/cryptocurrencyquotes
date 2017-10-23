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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Hello world!
 *
 */
public class CoinCap
{
    private static final String COINCAP_API = "http://coincap.io/page/ZEN";

    public CryptoCurrencyQuote pollCoinBase(){
        final CryptoCurrencyQuote[] cryptoCurrencyQuote = {null};

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run()
            {
                cryptoCurrencyQuote[0] = queryCoinCap();
            }
        }, 0, 3000);

        return cryptoCurrencyQuote[0];
    }

    public CryptoCurrencyQuote queryCoinCap() {
        String output = null;
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

    public static void main( String[] args )
    {
        CoinCap coinCap = new CoinCap();
        System.out.println("DEBUG: " + coinCap.pollCoinBase());

    }

}
