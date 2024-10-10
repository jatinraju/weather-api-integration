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

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HttpServiceEngine {

	@Autowired
	private RestTemplate restTemplate;

	public ResponseEntity<String> makeHttpRequest(HttpRequest httpRequest, String pathVariable) {
		log.info("Received Request for makeHttpRequest: {}", httpRequest);
		try {
			HttpHeaders httpHeader = httpRequest.getHttpHeader();
			if (httpHeader == null) {
				httpHeader = new HttpHeaders();
			}
			HttpEntity<Object> entity = new HttpEntity<>(httpRequest.getRequest(), httpHeader);
			log.debug("Prepared HttpEntity to make Request: {}", entity);

			// Construct the full URL with the path variable
			String urlWithVariable = httpRequest.getUrl().concat(pathVariable);
			log.debug("urlWithVariable : {}", urlWithVariable);

			ResponseEntity<String> response = restTemplate.exchange(urlWithVariable, httpRequest.getMethod(), entity,
					String.class);
			log.debug("ResponseEntity Response:{}", response.getBody());
			return response;
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			log.error("Got Client/Server Error While Making HTTP Call To Stripe System");
			return ResponseEntity.status(ex.getStatusCode().value()).body(ex.getResponseBodyAsString());
		} catch (ResourceAccessException ex) {
			log.error("Got Timeout Error While Making HTTP Call To Stripe System");
			return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(null);
		} catch (Exception ex) {
			log.error("Unable to Proceed with Stripe System");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
