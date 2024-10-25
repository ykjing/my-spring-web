package com.ticketcenter.movie.bean;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ticketcenter.movie.data.Movie;
import com.ticketcenter.movie.log.LogExecution;

@Component
public class ApiService {

	private static final Logger logger = LoggerFactory.getLogger(ApiService.class);
	
	@LogExecution
	public Pair<Integer, String> makeGetApi(String url, Map<String, String> headers) throws IOException, InterruptedException {
		logger.info("API url: {}", url);
		
		String[] headerArr = new String[0];
		
		if(headers!=null) {
			headerArr= 
				headers.entrySet().stream().flatMap(x->Stream.of(x.getKey(), x.getValue())).toArray(String[]::new);
		}
		
		logger.info("Parameter Size: {}", headerArr.length/2);
		URI uri = URI.create(url);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest req = HttpRequest.newBuilder()
							.uri(uri)
							.headers(headerArr)
							.GET()
							.build();
		
		HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());

		return new ImmutablePair<>(resp.statusCode(), resp.body());
	}
}
