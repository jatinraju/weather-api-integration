package com.wai.service.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpServiceEngine {

	@Autowired
	private RestTemplate restTemplate;

	public ResponseEntity<String> makeHttpRequest(HttpRequest httpRequest) {
		try {
			HttpHeaders httpHeader = httpRequest.getHttpHeader();
			if (httpHeader == null) {
				httpHeader = new HttpHeaders();
			}
			HttpEntity<Object> entity = new HttpEntity<>(httpRequest.getRequest(), httpHeader);
			ResponseEntity<String> response = restTemplate.exchange(httpRequest.getUrl(), httpRequest.getMethod(),
					entity, String.class);
			return response;
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			return ResponseEntity.status(ex.getStatusCode().value()).body(ex.getResponseBodyAsString());
		} catch (ResourceAccessException ex) {
			return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(null);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
