import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class MathServiceTest {

    static MathService mathService;

    @BeforeAll
    static void init(){
        mathService = new MathService();
    }

    @Test
    void testPairToString() {
        Pair pair = new Pair(4.0, 2.1);
        Assertions.assertEquals("Answer{first=4.0, second=2.1}", pair.toString());
    }

    @ParameterizedTest
    @CsvSource({"1, 2, 3, -8", "3, 2, 1, -8", "1, 3, -4, 25", "-1, -3, 4, 25",
            "-10, -10, -10, -300", "1, 5, 1, 21"})
    void testGetD(int a, int b, int c, int result) {
        int d = mathService.getD(a, b, c);
        Assertions.assertEquals(result, d);
    }

    @ParameterizedTest
    @CsvSource({"1, -2, -3, 3.0, -1.0", "2, 5, 3, -1.0, -1.5"})
    void testGetAnswerDiscriminantOverZero(int a, int b, int c, double x1, double x2) throws NotFoundAnswerException {
        Pair pair = mathService.getAnswer(a, b, c);
        String result = "Answer{first=" + x1 + ", second=" + x2 + "}";
        Assertions.assertAll(() -> Assertions.assertEquals(x1, pair.first),
                () ->Assertions.assertEquals(x2, pair.second),
                () ->Assertions.assertEquals(result, pair.toString()));
    }

    @ParameterizedTest
    @CsvSource({"2, 4, 2, -1.0", "1, -2, 1, 1.0"})
    void testGetAnswerDiscriminantEqualZero(int a, int b, int c, double x) throws NotFoundAnswerException {
        Pair pair = mathService.getAnswer(a, b, c);
        String result = "Answer{first=" + x + ", second=" + x + "}";
        System.out.println(pair.toString());
        Assertions.assertAll(() -> Assertions.assertEquals(x, pair.first),
                () ->Assertions.assertEquals(x, pair.second),
                () ->Assertions.assertEquals(result, pair.toString()));
    }

    @Test
    void testExceptionInGetAnswerDiscriminantLessZero() {
        String message = "";
        Assertions.assertThrows(NotFoundAnswerException.class, () -> mathService.getAnswer(1, 1, 1));
        try {
            Pair pair = mathService.getAnswer(8, 2, 1);
        } catch (NotFoundAnswerException e) {
            message = e.getMessage();
        }
        Assertions.assertEquals("Корни не могут быть найдены", message);
    }


    @Test
    void testGetExceptionInGetAnswerWithIntOverflow() {
        Assertions.assertThrows(NotFoundAnswerException.class, () -> mathService.getAnswer(214748364+2, 5, 1));
    }

}
