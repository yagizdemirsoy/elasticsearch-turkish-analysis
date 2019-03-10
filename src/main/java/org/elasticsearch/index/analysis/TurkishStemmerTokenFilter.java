package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

import java.io.IOException;

public class TurkishStemmerTokenFilter extends TokenFilter {

    private final TurkishStemmer stemmer;

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final KeywordAttribute keywordAttr = addAttribute(KeywordAttribute.class);

    public TurkishStemmerTokenFilter(TokenStream input) {
        super(input);

        stemmer = new TurkishStemmer();
    }

    @Override
    public final boolean incrementToken() throws IOException {
        String stem;

        if (input.incrementToken()) {
            if (!keywordAttr.isKeyword()) {
                stem = stemmer.stem(termAtt.buffer(), termAtt.length());
                termAtt.copyBuffer(stem.toCharArray(), 0, stem.length());
            }
            return true;
        } else {
            return false;
        }
    }
}
