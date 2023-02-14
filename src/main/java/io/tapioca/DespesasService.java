package io.tapioca;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class DespesasService {

    public static void main(String[] args) {
        
        String despesasPath = "/home/pesilva/workspace/code/pessoal/data-etl/despesas_sjc/";
        
        try (Stream<Path> stream = Files.walk(Paths.get(despesasPath))) {
            
            stream.map(Path::normalize)
                  .filter(Files::isRegularFile)
                  .forEach(p -> {
                      
                      try {
                        
                          List<String[]> readAllLines = readAllLines(p);
                          
                            String[] firstLine = readAllLines.get(1);

                            String ano = firstLine[0];
                            String mes = firstLine[1];

                            String newFileName = ano + mes + ".csv";
                            String newPath = despesasPath + ano + "/";
                            createDirectoryIfDoesntExists(newPath);
                            
                            FileWriter outputfile = new FileWriter(Paths.get(newPath + newFileName).toFile());
                            CSVWriter writer = new CSVWriter(outputfile);
                            writer.writeAll(readAllLines);
                            writer.close();
                        
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                  });
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("FIM da GAMBIARRA!!!!");

    }
    
    public static List<String[]> readAllLines(Path filePath) throws Exception {
        FileReader filereader = new FileReader(filePath.toFile());
        
        // create csvReader object and skip first Line
        CSVReader csvReader = new CSVReaderBuilder(filereader)
                                  .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                                  .build();
        return csvReader.readAll();
    }
    
    public static void createDirectoryIfDoesntExists(String directoryName) {

        var directory = new File(directoryName);
        
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

}
