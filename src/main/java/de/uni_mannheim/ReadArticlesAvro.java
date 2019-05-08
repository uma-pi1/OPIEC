package de.uni_mannheim;

import avroschema.linked.WikiArticleLinkedNLP;

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;

public class ReadArticlesAvro {
    public static void main(String args[]) throws IOException {
        File f = new File("data/articles.avro");
        DatumReader<WikiArticleLinkedNLP> userDatumReader = new SpecificDatumReader<>(WikiArticleLinkedNLP.class);
        DataFileReader<WikiArticleLinkedNLP> dataFileReader = new DataFileReader<>(f, userDatumReader);

        while (dataFileReader.hasNext()) {
            WikiArticleLinkedNLP article = dataFileReader.next();
            System.out.println("Processing article: " + article.getTitle());
        }
    }
}
