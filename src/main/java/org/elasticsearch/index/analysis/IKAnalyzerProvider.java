package org.elasticsearch.index.analysis;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IKAnalyzerProvider extends AbstractIndexAnalyzerProvider<IKAnalyzer> {
    private final IKAnalyzer analyzer;

    public IKAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings, boolean useSmart) {
        super(indexSettings,name, settings);
        Configuration configuration = DefaultConfig.getInstance();
        boolean enableLowercase = settings.get("enable_lowercase", "true").equals("true");
        boolean enableRemoteDict = settings.get("enable_remote_dict", "true").equals("true");
        long remoteExtDictRefreshInterval = Long.valueOf(settings.get("remote_ext_dict_refresh_interval", "60"));
        configuration.setUseSmart(useSmart);
        configuration.setEnableLowercase(enableLowercase);
        configuration.setEnableRemoteDict(enableRemoteDict);
        configuration.setRemoteExtDictRefreshInterval(remoteExtDictRefreshInterval);
        analyzer = new IKAnalyzer(configuration.useSmart());
    }

    public static IKAnalyzerProvider getIKSmartAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        return new IKAnalyzerProvider(indexSettings, env, name, settings, true);
    }

    public static IKAnalyzerProvider getIKAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        return new IKAnalyzerProvider(indexSettings,env,name, settings, false);
    }

    @Override public IKAnalyzer get() {
        return this.analyzer;
    }
}
