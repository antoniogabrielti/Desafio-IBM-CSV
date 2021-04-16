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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;



public class ProcessaCSV {
    public List<String[]> lerCSV(String nomeArquivo) throws IOException, ParseException, InterruptedException {

        Reader reader = Files.newBufferedReader(Paths.get(nomeArquivo));
        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).withSkipLines(1).build();

        List<String[]> linhas = csvReader.readAll();
        List<String[]> resultadoLinhas = new ArrayList<>();
        AtomicReference<Boolean> resultadoAtualiza = new AtomicReference<>(true);
        Integer contPosicaoLinha = 0;
        Integer contLinhaLidas = 0;
        AtomicReference<Integer> contError = new AtomicReference<>(0);

        for (String[] linha : linhas) {

            resultadoLinhas.add(retornalinha(linha));

            contPosicaoLinha++;
            resultadoAtualiza.set(true);
            System.out.println("Lendo linha " + contLinhaLidas++);

        }
        System.out.println("Ocorreram " + contError + " erros na chamada do Receita Service.");
        return resultadoLinhas;

    }

    //ESTE MÉTODO DEVERIA SER ASSINCRONO PARA QUE A PERFORMACE FICASSE IDEAL,
    //REALIZEI DIVERÇOS TESTES COM FUNÇÕES ASSINCRONAS MAS NÃO TIVE TEMPO DE IMPLEMENTA-LAS
    //UTILIZANDO MÉTODOS ASSINCRONOS NÃO NECESSITARIA ESPERAR O RETORNO DO ATUALIZACONTA() PARA CHAMA-LO NOVAMENTE;
    ExecutorService executor = Executors.newFixedThreadPool(10);
    public String[] retornalinha(String[] linha) throws ParseException, InterruptedException {
        ReceitaService receitaService = new ReceitaService();
        ConverteStringToDouble converteStringToDouble = new ConverteStringToDouble();
        boolean res;

        res = receitaService.atualizarConta(linha[0], linha[1], converteStringToDouble.converte(linha[2]), linha[3]);
        String[] arr = {linha[0], linha[1], linha[2], linha[3], Boolean.toString(res)};
        System.out.println("Lendo linha " + linha[0] + res);
        return arr;
    }
}
