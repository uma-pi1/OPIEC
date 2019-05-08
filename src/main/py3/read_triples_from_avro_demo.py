from avro.datafile import DataFileReader
from avro.io import DatumReader
import pdb 

AVRO_SCHEMA_FILE = "../../../avro/TripleLinked.avsc"
AVRO_FILE = "../../../data/triples.avro"
reader = DataFileReader(open(AVRO_FILE, "rb"), DatumReader())
for triple in reader:
    print(triple)
    # use triple.keys() to see every field in the schema (it's a dictionary)
    pdb.set_trace()
reader.close()