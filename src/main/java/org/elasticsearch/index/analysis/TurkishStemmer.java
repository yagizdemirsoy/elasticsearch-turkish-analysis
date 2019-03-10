package org.elasticsearch.index.analysis;

import org.elasticsearch.SpecialPermission;
import zemberek.morphology.TurkishMorphology;
import zemberek.morphology.analysis.SingleAnalysis;
import zemberek.morphology.analysis.WordAnalysis;


import java.security.AccessController;
import java.security.PrivilegedAction;

public class TurkishStemmer {

    private TurkishMorphology morphology;

    public TurkishStemmer(){

        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            // unprivileged code such as scripts do not have SpecialPermission
            sm.checkPermission(new SpecialPermission());
        }

        morphology = AccessController.doPrivileged((PrivilegedAction<TurkishMorphology>) () -> TurkishMorphology.createWithDefaults());
    }

    public String stem(char s[], int len) {

        String word = new String(s, 0, len);

        try {
            WordAnalysis results = morphology.analyze(word);

            Integer maxLength = -1;
            String root = word;
            for(SingleAnalysis analysis: results) {
                String currentRoot = analysis.getDictionaryItem().root;

                if(!analysis.isUnknown() && currentRoot.length() > maxLength) {
                    maxLength = currentRoot.length();
                    root = currentRoot;
                }
            }

            return root;
        } catch (Exception ex) {
            return word;
        }
    }
}
