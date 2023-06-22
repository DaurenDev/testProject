import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SelenideTests {

    @BeforeAll
    public static void setUp() {
        Configuration.baseUrl = "https://ru.wikipedia.org";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 4000;
        Configuration.holdBrowserOpen = true;
    }

    @Test
    @Order(1)
    public void testSearchOnWikipedia() {
        open("/");
        $("#searchInput").setValue("Просмотр кода").pressEnter();
        $(".mw-search-results-container").shouldHave(text("Просмотр кода"));
        $$(".mw-search-results .mw-search-result").shouldHave(size(20));
    }

    @Test
    @Order(2)
    public void testReturnToMainPage() {
        open("/wiki/Просмотр кода");
        $(byLinkText("Заглавная страница")).shouldBe(visible);
        $("#n-mainpage-description")
                .lastChild()
                .shouldBe(href("https://ru.wikipedia.org/wiki/%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0"));
    }

    @Test
    @Order(3)
    public void testArticleConsistsOfParagraphs() {
        open("/wiki/Просмотр кода");
        $$(".mw-parser-output p").shouldHave(sizeGreaterThan(0));
    }

    @Test
    @Order(4)
    public void testArticleHasContent() {
        open("/wiki/Просмотр кода");
        $(".mw-parser-output").shouldNotBe(empty);
    }

    @Test
    @Order(5)
    public void testChangeLanguageOnPage() {
        open("/wiki/Просмотр кода");
        $(".interlanguage-link-target[hreflang='en']").click();
        String expectedUrl = "https://en.wikipedia.org/";
        String currentUrl = WebDriverRunner.url();
        $("[lang]").shouldHave(attribute("lang", "en"));
        assertTrue(currentUrl.contains(expectedUrl));
    }
}
