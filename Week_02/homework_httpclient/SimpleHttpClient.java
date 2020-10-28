import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 对HttpClient进行简单封装
 * 实现通过Get方法从指定url获取字符串
 */
public class SimpleHttpClient {

  public SimpleHttpClient() {
  }

  /**
   * 通过Get方法从指定url获取字符串
   * @param url 目标url
   * @return 响应字符串
   */
  public String get(String url) {
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response.body();
    } catch (IOException e) {
      e.printStackTrace();
      return e.getLocalizedMessage();
    } catch (InterruptedException e) {
      e.printStackTrace();
      return e.getLocalizedMessage();
    }
  }

  public static void main(String[] args) {
    SimpleHttpClient client = new SimpleHttpClient();
    String response = client.get("http://localhost:8801");
    System.out.println(response);
  }

}