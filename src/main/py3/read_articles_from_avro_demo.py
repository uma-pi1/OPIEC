from avro.datafile import DataFileReader
from avro.io import DatumReader
import pdb 

AVRO_SCHEMA_FILE = "../../../avroschema/WikiArticleLinkedNLP.avsc"
AVRO_FILE = "../../../data/articles.avro" # edit this line
reader = DataFileReader(open(AVRO_FILE, "rb"), DatumReader())
for article in reader:
    print(article['title'])
    # use article.keys() to see every field in the schema (it's a dictionary)
    pdb.set_trace()

reader.close()