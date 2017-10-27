package com.kopernik.coincap;


import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class CoinCapTest {

    @Test
    public void testCoinCapQuery(){
        CoinCap coinCap = new CoinCap();
        CryptoCurrencyQuote cryptoCurrencyQuote = coinCap.queryCoinCap();
        assertNotNull("CryptoCurrencyQuote was null! " + cryptoCurrencyQuote);
    }

    @Test
    public void testConversion(){
        CoinCap coinCap = new CoinCap();
        Map<String, BigDecimal> fiatCurrencies = coinCap.getFiatConversion();

        assertTrue("Expected USD amount: ", fiatCurrencies.containsKey("USD"));
        assertTrue("Expected EUR amount: ", fiatCurrencies.containsKey("EUR"));
        BigDecimal usdAmount = fiatCurrencies.get("USD");
        assertEquals("Expected decimal precision of 2 for USD: ", 2, usdAmount.scale());
        BigDecimal eurAmount = fiatCurrencies.get("EUR");
        assertEquals("Expected decimal precision of 2 for EUR: ", 2, eurAmount.scale());

    }

}
