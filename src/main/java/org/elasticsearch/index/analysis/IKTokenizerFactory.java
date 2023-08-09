package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.lucene.IKTokenizer;

/**
 * IKTokenizer工厂(兼容Elasticsearch 7.x)
 */
public class IKTokenizerFactory extends AbstractTokenizerFactory {
    private Configuration configuration;

    private Environment environment;
    private Settings settings;

    public IKTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, settings, name);
        this.environment = env;
        this.settings = settings;
        configuration = DefaultConfig.getInstance();
        boolean useSmart = settings.get("use_smart", "false").equals("true");
        boolean enableLowercase = settings.get("enable_lowercase", "true").equals("true");
        boolean enableRemoteDict = settings.get("enable_remote_dict", "true").equals("true");
        long remoteExtDictRefreshInterval = Long.valueOf(settings.get("remote_ext_dict_refresh_interval", "60"));
        configuration.setUseSmart(useSmart);
        configuration.setEnableLowercase(enableLowercase);
        configuration.setEnableRemoteDict(enableRemoteDict);
        configuration.setRemoteExtDictRefreshInterval(remoteExtDictRefreshInterval);
    }

    public static IKTokenizerFactory getIKTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        return new IKTokenizerFactory(indexSettings, env, name, settings).setSmart(false);
    }

    public static IKTokenizerFactory getIKSmartTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        return new IKTokenizerFactory(indexSettings, env, name, settings).setSmart(true);
    }

    public IKTokenizerFactory setSmart(boolean smart) {
        this.configuration.setUseSmart(smart);
        return this;
    }

    @Override
    public Tokenizer create() {
        return new IKTokenizer(this.configuration.useSmart());
    }
}
