package com.ibm.desafio.utils;

import com.opencsv.CSVWriter;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CriaCSV {
    private static final String STRING_ARRAY_SAMPLE = "./resultado.csv";
    @Async
    public void escreve(List<String[]> tabela) throws IOException {
        Integer cont = 0;
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE));

                CSVWriter csvWriter = new CSVWriter(writer,
                        ';',
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            String[] headerRecord = {"agencia", "conta", "saldo", "status", "resultado"};
            csvWriter.writeNext(headerRecord);
            for (String[] linha : tabela) {
                String[] arr = {linha[0], linha[1], linha[2], linha[3], linha[4]};
                csvWriter.writeNext(arr);
                cont++;
                System.out.println("Temos: " + cont + " linhas.");
            }

        }
    }
}
