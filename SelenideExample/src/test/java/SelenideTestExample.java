import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTestExample {
    @Test
    public void userCanSearchKeywordWithGoogle() {
        open("https://google.com");
        $(By.name("q")).setValue("Selenide").pressEnter();
        $("#res").shouldBe(exist).shouldHave(text("Selenide: concise UI tests in Java"));
    }
}