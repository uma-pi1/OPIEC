package de.uni_mannheim;

import avroschema.linked.TripleLinked;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.File;
import java.io.IOException;

public class ReadTriplesAvro {
    public static void main(String args[]) throws IOException {
        File f = new File("data/triples.avro");
        DatumReader<TripleLinked> userDatumReader = new SpecificDatumReader<>(TripleLinked.class);
        DataFileReader<TripleLinked> dataFileReader = new DataFileReader<>(f, userDatumReader);

        while (dataFileReader.hasNext()) {
            // Debugging variables
            TripleLinked triple = dataFileReader.next();
            System.out.print("Processing triple: " + triple.getTripleId());
        }
    }
}
