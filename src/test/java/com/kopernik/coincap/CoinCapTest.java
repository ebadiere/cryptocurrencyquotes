package com.kopernik.coincap;


import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by badiere.eric on 10/22/17.
 */
public class CoinCapTest {

    @Test
    public void testCoinCapQuery(){
        CoinCap coinCap = new CoinCap();
        CryptoCurrencyQuote cryptoCurrencyQuote = coinCap.queryCoinCap();
        assertNotNull("CryptoCurrencyQuote was null! " + cryptoCurrencyQuote);
    }
}
