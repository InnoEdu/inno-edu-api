package inno.edu.api.support;

import org.hamcrest.Matcher;
import org.hamcrest.number.BigDecimalCloseTo;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;

public class Matchers {
    private static final double DEFAULT_PRECISION = 0.01;

    public static Matcher<BigDecimal> closeTo(double value, double precision) {
        return BigDecimalCloseTo.closeTo(new BigDecimal(value), new BigDecimal(precision));
    }

    public static Matcher<BigDecimal> closeTo(double value) {
        return closeTo(value, DEFAULT_PRECISION);
    }

    public static Matcher<Double> closeTo(BigDecimal value) {
        return is(value.doubleValue());
    }
}
