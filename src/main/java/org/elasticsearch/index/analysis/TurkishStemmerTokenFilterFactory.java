package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;

public class TurkishStemmerTokenFilterFactory extends AbstractTokenFilterFactory {

    @Inject
    public TurkishStemmerTokenFilterFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);
    }


    public TokenStream create(TokenStream tokenStream) {
        return new TurkishStemmerTokenFilter(tokenStream);
    }

    public boolean breaksFastVectorHighlighter() {
        return false;
    }
}
