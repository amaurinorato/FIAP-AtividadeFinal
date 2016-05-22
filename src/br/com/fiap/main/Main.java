package br.com.fiap.main;

import java.util.List;

import br.com.fiap.classe.TwitterAnalise;
import br.com.fiap.classe.TwitterUtil;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Main {

	public static void main(String[] args) {
		try {
			
			ConfigurationBuilder builder = TwitterUtil.getConfigurationBuilder();
			Configuration configuration = builder.build();

			TwitterFactory factory = new TwitterFactory(configuration);
			Twitter twitter = factory.getInstance();
			AccessToken accessToken = TwitterUtil.loadAccessToken();
			twitter.setOAuthAccessToken(accessToken);

			TwitterAnalise  twitterAnalise = new TwitterAnalise();
			List<Status> statuses = twitterAnalise.recuperarTweets(twitter);
			
			twitterAnalise.analisaTweets(statuses);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
