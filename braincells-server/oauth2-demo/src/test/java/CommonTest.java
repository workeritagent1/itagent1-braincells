import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author itagent1.worker
 * @version 1.0
 * @ClassName CommonTest
 * @description:
 * @date 2023/12/20 22:24
 */
public class CommonTest {

    @Test
    public void login() {
        //  以下是一个使用 Java 代码示例的基于 Spring Security 的登录过程：

        String loginUrl = "http://localhost:7000/login";
        String username = "admin";
        String password = "123456";

        // 创建 RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // 构建请求体参数
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username", username);
        requestBody.add("password", password);

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

        // 发送 POST 请求进行登录
        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST,
                new HttpEntity<>(requestBody, headers), String.class);

        // 获取响应结果
        String result = response.getBody();

        // 处理登录结果
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("登录成功");
            // 根据需要，可以从响应中获取身份验证令牌或者设置 Cookie 等
        } else {
            System.out.println("登录失败");
            // 处理登录失败的情况
        }
    }

    @Test
    public void code() {
        // 测试获取授权码
        String authorizeUrl = "http://localhost:7000/oauth/authorize?response_type=code&client_id=client001&redirect_uri=http://example.com/callback&scope=read%20write";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(authorizeUrl, String.class);
        System.out.println(response);
    }

}
