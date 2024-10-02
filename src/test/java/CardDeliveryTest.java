import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldCardDeliveryTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Новосибирск");
        String planinngDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(planinngDate);
        $("[data-test-id=name] input").setValue("Петров-Иванов Тимофей");
        $("[data-test-id=phone] input").setValue("+79521361515");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planinngDate));

    }

}
