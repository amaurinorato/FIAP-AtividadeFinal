package br.com.fiap.classe;

import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterUtil {
	
	public static AccessToken loadAccessToken() {
		String token = "2386915843-dNWkW8BexNVFIXXcwRtTr2sGo2ATeeutnOUvbu0";
		String tokenSecret = "jEYB8Ii7cC952MpvUsKuOH3mYetK9SR9xcXK2XNJ3pRZC";
		return new AccessToken(token, tokenSecret);
	}

	public static ConfigurationBuilder getConfigurationBuilder() {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey("YyT57GAQPaUqNHd3pzzM1je67");
		builder.setOAuthConsumerSecret("PWrdJkycCbWER0BdeR1nlamm6fskaYsijxWpk7t4Ci6HCHJY8k");
		return builder;
	}

}
