package org.elasticsearch.plugin.analysis.ik;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.AnalyzerProvider;
import org.elasticsearch.index.analysis.IKAnalyzerProvider;
import org.elasticsearch.index.analysis.IKTokenizerFactory;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.HashMap;
import java.util.Map;

public class IKAnalysisPlugin extends Plugin implements AnalysisPlugin {
    public static String PLUGIN_NAME = "ik";

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {
        Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> extra = new HashMap<>();
        extra.put("ik_smart", IKTokenizerFactory::getIKSmartTokenizerFactory);
        extra.put("ik_max_word", IKTokenizerFactory::getIKTokenizerFactory);
        return extra;
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> extra = new HashMap<>();
        extra.put("ik_smart", IKAnalyzerProvider::getIKSmartAnalyzerProvider);
        extra.put("ik_max_word", IKAnalyzerProvider::getIKSmartAnalyzerProvider);
        return extra;
    }
}
