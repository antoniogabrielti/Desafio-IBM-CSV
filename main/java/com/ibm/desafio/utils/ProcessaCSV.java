package com.ibm.desafio.utils;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class ProcessaCSV {
    public List<String[]> lerCSV(String nomeArquivo) throws IOException, ParseException, InterruptedException {

        ReceitaService receitaService = new ReceitaService();
        ConverteStringToDouble converteStringToDouble = new ConverteStringToDouble();

        Reader reader = Files.newBufferedReader(Paths.get(nomeArquivo));
        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).withSkipLines(1).build();

        List<String[]> linhas = csvReader.readAll();
        List<String[]> resultadoLinhas = new ArrayList<>();
        AtomicReference<Boolean> resultadoAtualiza = new AtomicReference<>(true);
        Integer cont = 0;

        for (String[] linha : linhas) {
            new Thread(() -> {
                try {
                    resultadoAtualiza.set(receitaService.atualizarConta(linha[0], linha[1], converteStringToDouble.converte(linha[2]), linha[3]));
                }
                catch (ParseException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            String[] arr = {linha[0], linha[1], linha[2], linha[3], Boolean.toString(resultadoAtualiza.get())};
            resultadoLinhas.add(arr);
            System.out.println("Lendo linha " + cont++);
        }
        return resultadoLinhas;

    }
}
