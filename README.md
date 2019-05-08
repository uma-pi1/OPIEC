# OPIEC: An Open Information Extraction Corpus

<img src="img/opiec-logo.png" align="right" width=200>

OPIEC is an Open Information Extraction (OIE) corpus, consisted of more than 341M triples extracted from the entire English Wikipedia. Each triple from the corpus is consisted of rich meta-data: each token from the subj/obj/rel along with NLP annotations (POS tag, NER tag, ...), provenance sentence along with the dependency parse, original (golden) links from Wikipedia, sentence order, space/time, etc. For more detailed explanation of the meta-data, see [here](#metadata). 

There are two major corpora released with OPIEC:

1. OPIEC: an OIE corpus containing hundreds of millions of triples.
2. WikipediaNLP: the entire English Wikipedia with NLP annotations.

For more details concerning the construction, analysis and statistics of the corpus, read the AKBC paper [*"OPIEC: An Open Information Extraction Corpus*"](https://arxiv.org/pdf/1904.12324.pdf).

## Reading the data

The data is stored in [avro format](https://avro.apache.org/). For details about the metadata, see [here](#metadata). To read the data, you need the avroschema file found in the directory `avroschema`; more specifically `TripleLinked.avsc` and `WikiArticleLinkedNLP.avsc` for OPIEC and WikipediaNLP respectively. 

### Python

There are two corpora that you can read: OPIEC and WikipediaNLP. For reading OPIEC, see `src/main/py3/read_articles_from_avro_demo.py`:

```python
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
```

Similarly, for reading WikipediaNLP, see `src/main/py3/read_articles_from_avro_demo.py`:

```python
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
```

### Java

There are two corpora that you can read: OPIEC and WikipediaNLP. For reading OPIEC, see `src/main/java/de/uni_mannheim/ReadTriplesAvro.java`:

```java
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
```

Similarly, for reading WikipediaNLP, see `src/main/java/de/uni_mannheim/ReadArticlesAvro.java`:

```java
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
```

## Metadata

There are two corpora that we are releasing: OPIEC and WikipediaNLP. In this section, the metadata for the two corpora are described. 

### WikipediaNLP

WikipediaNLP is the NLP annotation corpus for the English Wikipedia. Each object is a Wikipedia article containing:

* **Title:** the title of the article.
* **ID:** the ID of the article.
* **URL:** the URL of the article.
* **Text:** the whole *clean text* of the article's content (excluding tables, infoboxes, etc.).
* **Links:** all the original links within the article. For each link there is the offset begin/end index of the link within the article, the original phrase of the link, and the link itself.
* **SentenceLinked:** The sentence itself contains 4 major metadata:
   1. ***Sentence ID:*** the ID of the sentence (which is also the index of the sentence within the article).
   2. ***Span:*** the span of the sentence within the Wikipedia page. 
   3. ***Dependency parse:*** the dependency parse of the sentence. 
   4. ***Tokens:*** the sentence is represented as a list of tokens, each containing their own metadata (see *"Tokens metadata"* below).
* **Tokens metadata:** each token contains NLP annotations: 
   * ***Word:*** the original word of the token.
   * ***Lemma:*** the lemma of the word.
   * ***POS tag:*** the POS tag of the token.
   * ***Index:*** the index of the token from within the sentence. Indexing starts from 1 (e.g. *"Index: 2"* means that the token is the second word in the sentence).
   * ***Span:*** the span indices from within the article (has beginning and end index).
   * ***NER:*** the named entity type according to [Stanford Named Entity Recognizer (NER)](https://nlp.stanford.edu/software/CRF-NER.html). Possible types: PERSON, LOCATION, ORGANIZATION, MONEY, PERCENT, DATE, NUMBER, DURATION, TIME, SET, ORDINAL, QUANTITY, MISC and O (meaning - "no entity type detected"). 
   * ***WikiLink:*** contains offset begin/end index of the link within the article, the original phrase of the link, and the link itself.

### OPIEC

Each OIE triple in OPIEC contains the following metadata:

* **Triple ID:** Each triple has unique ID composed of 4 parts: "Wiki\_" + Wikipedia article ID + "\_" + sentence index + "\_" + triple index. For example, suppose we have the triple ID: Wiki\_5644\_2\_5 -- this means that the triple comes from the Wikipedia article having an ID 5644, comes from the 3rd sentence in the article (2+1 - here indexing starts from 0), and it is the 5th extraction from this sentence (here indexing starts from 1).
* **Article ID:**  Article ID of the Wikipedia article where the triple was extracted from. 
* **Sentence:** The provenance sentence where the triple was extracted from. For more details for the sentence metadata, see *"SentenceLinked"* metadata description in [WikipediaNLP](#wikipedianlp).
* **Sentence number:** the order of the sentence from within the Wikipedia page (e.g. if *"Sentence number: 3"*, then this sentence is the 3rd sentence witin the Wikipedia article). 
* **Polarity:**  The polarity of the triple (either *positive* or *negative*).
* **Negative words:** Words indicating negative polarity (e.g. *not, never, ...*).
* **Modality:**  The modality of the triple (either *possibility* or *certainty*).
* **Certainty/Possibility words:** Certainty/Possibility words (as token objects).
* **Attribution:**  Attribution of the triple (if found) including attribution phrase, predicate, factuality, space and time.
* **Quantities:**  Quantities in the triple (if found).
* **Subject/Relation/Object:** Lists of tokens with linguistic annotations for subject, predicate, and object of the triple.
* **Dropped words:**  To minimize the triple and make it more compact, MinIE sometimes drops words considered to be semantically redundant words (e.g., determiners). All dropped words are stored here.
* **Time:** Temporal annotations, containing information about TIMEX3 type, TIMEX3 xml, disambiguated temporal expression, original core words of the temporal expression, pre-modifiers/post-modifiers of the core words and temporal predicate. 
* **Space:**  Spatial annotations, containing information about the original spatial words, the pre/post-modifiers and the spatial predicate.
* **Time/Space for phrases:** Information about the temporal annotation on phrases. This annotation contains: 1) modified word: head word of the constituent being modified, and 2) temporal/spatial words modifying the phrase.
* **Confidence score:** The confidence score of the triple.
* **Canonical links:** Canonical links for all links within the triple (follows redirections).
* **Extraction type:**  Either one of the clause types listed in ClausIE (SVO, SVA, . . . ), or one of the implicit extractions proposed in MinIE (Hearst patterns, noun phrases modifying persons, . . . ).

## Citation

If you use any of these corpora, or use the findings from the paper, please cite: 

```
@inproceedings{gashteovski2019opiec,
  title={OPIEC: An Open Information Extraction Corpus},
  author={Gashteovski, Kiril and Wanner, Sebastian and Hertling, Sven and Broscheit, Samuel and Gemulla, Rainer},
  booktitle={Proceedings of the Conference on Automatic Knowledge Base Construction (AKBC)},
  year={2019}
}
```
