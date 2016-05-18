package br.com.fiap.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import br.com.fiap.classe.TwitterUtil;
import br.com.fiap.comparator.StatusDataComparator;
import br.com.fiap.comparator.StatusNomeComparator;

public class Main {

	public static void main(String[] args) {
		try {
			
			ConfigurationBuilder builder = TwitterUtil.getConfigurationBuilder();
			Configuration configuration = builder.build();

			TwitterFactory factory = new TwitterFactory(configuration);
			Twitter twitter = factory.getInstance();
			AccessToken accessToken = TwitterUtil.loadAccessToken();
			twitter.setOAuthAccessToken(accessToken);

			List<Status> statuses = recuperarTweets(twitter);
			
			analisaTweets(statuses);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void analisaTweets(List<Status> statuses) {
		int qtdRetweetsDia = 0;
		int favoritacoes = 0;
		int qtdTweets = 0;
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dataString = format.format(statuses.get(0).getCreatedAt());
		
		for (Status status : statuses) {
			if (!dataString.equals(format.format(status.getCreatedAt()))) {
				imprimeInformações(qtdRetweetsDia, favoritacoes, qtdTweets, dataString);
				dataString = format.format(status.getCreatedAt());
				qtdRetweetsDia = 0;
				favoritacoes = 0;
				qtdTweets = 0;
			}
			qtdRetweetsDia += status.getRetweetCount();
			favoritacoes += status.getFavoriteCount();
			qtdTweets ++;
		}
		imprimeInformações(qtdRetweetsDia, favoritacoes, qtdTweets, dataString);
		
		Collections.sort(statuses, new StatusDataComparator());
		
		System.out.println("Data mais antiga: " + statuses.get(0).getCreatedAt());
		System.out.println("Data mais recente: "  + statuses.get(statuses.size() - 1).getCreatedAt());
		
		Collections.sort(statuses, new StatusNomeComparator());
		System.out.println("Primeiro nome: " + statuses.get(0).getUser().getScreenName());
		System.out.println("Ultimo nome: "  + statuses.get(statuses.size() - 1).getUser().getScreenName());
	}
	
	public static List<Status> recuperarTweets(Twitter twitter) throws TwitterException {
		
		List<Status> statuses = new ArrayList<Status>();
		
		Query query = new Query("#java");
		
		LocalDate umaSemanaAtras = LocalDate.now().minusWeeks(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		query.setSince(formatter.format(umaSemanaAtras));
		query.setUntil(formatter.format(umaSemanaAtras.plusDays(1)));
		
		QueryResult result = twitter.search(query);
		
		for (int i = 1; i < 8; i++) {
			while (result.hasNext())
			{
				query = result.nextQuery();
				statuses.addAll(result.getTweets());
				result = twitter.search(query);
			}
			umaSemanaAtras = umaSemanaAtras.plusDays(1);
			query = new Query("#java");
			query.setSince(formatter.format(umaSemanaAtras));
			query.setUntil(formatter.format(umaSemanaAtras.plusDays(1)));
			
			result = twitter.search(query);
		}
		return statuses;
	}

	private static void imprimeInformações(int qtdRetweetsDia,
			int favoritacoes, int qtdTweets, String dataString) {
		System.out.println("Dia: " + dataString);
		System.out.println("Quantidade tweets: " + qtdTweets);
		System.out.println("Quantidade retweets: " + qtdRetweetsDia);
		System.out.println("Quantidade favoritações: " + favoritacoes);
		System.out.println("-----------------------------------------");
	}
}
