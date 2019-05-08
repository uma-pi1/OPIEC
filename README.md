# OPIEC: An Open Information Extraction corpus

<img src="img/opiec-logo.png" align="right" width=200>

OPIEC is an Open Information Extraction (OIE) corpus, consisted of more than 341M triples extracted from the entire English Wikipedia. Each triple is consisted of rich meta-data: each token from the subj/obj/rel along with NLP annotations (POS tag, NER tag, ...), provenance sentence along with the dependency parse, original (golden) links from Wikipedia, ... For more detailed explanation of the meta-data, see here. 

As a suplement to OPIEC, we release a dataset 

## Metadata

Each OIE triple in OPIEC contains the following metadata:

* **Article ID:**  Article ID of the Wikipedia article where the triple was extracted from. 
* **Sentence:** The provenance sentence where the triple was extracted from. The sentence itself contains 4 major metadata:
   1. ***Sentence number:*** the order of the sentence from within the Wikipedia page (e.g. if *"Sentence number: 3"*, then this sentence is the 3rd sentence witin the Wikipedia article). 
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
