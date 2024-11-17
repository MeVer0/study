package api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class RequestsManager {

    private String setQueryParamsToUrl(String url, Map<String, String> queryParams) {
        url += "?";

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            url += (key + "=" + value + "&");
        }

        url = url.substring(0, url.length() - 1);
        return url;
    };


    private void setHeaders(HttpRequest.Builder requestBuilder, Map<String, List<String>> headers) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            for (String value : values) {
                requestBuilder.setHeader(key, value);
            }
        }
    }

    public String get(
            String url,
            Optional<Map<String, List<String>>> headersOpt,
            Optional<Map<String, String>> queryParamsOpt
    ) throws IOException, InterruptedException {

        if (queryParamsOpt.isPresent()) {
            Map<String, String> queryParams = queryParamsOpt.get();
            url = setQueryParamsToUrl(url, queryParams);
        }

        URI uri = URI.create(url);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(uri).GET();

        if (headersOpt.isPresent()) {
            Map<String, List<String>> headers = headersOpt.get();
            setHeaders(requestBuilder, headers);
        }
        HttpRequest request = requestBuilder.build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    };
}
