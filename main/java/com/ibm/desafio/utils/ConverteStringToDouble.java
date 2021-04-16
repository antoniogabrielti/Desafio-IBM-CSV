package com.ibm.desafio.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ConverteStringToDouble {
    public Double converte(String saldo) throws ParseException {
        Locale locale = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getInstance(locale);
        nf.setGroupingUsed(false);
        return nf.parse(saldo).doubleValue();
    }
}
