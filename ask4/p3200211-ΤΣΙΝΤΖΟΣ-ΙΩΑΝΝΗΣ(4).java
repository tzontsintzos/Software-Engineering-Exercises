
// p3200211 IOANNIS TSINTZOS

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TaxEstimatorTest {
    private TaxCalculator calculator;
    private TaxEstimator estimator;

    @Before
    public void setup() {
        calculator = new TestTaxCalculator();
        estimator = new TaxEstimator();
        estimator.setCalculator(calculator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEstimateTaxNegativeIncome() {
        estimator.estimateTax(-100);
    }

    @Test
    public void testEstimateTaxHighTax() {
        int income = 50000;
        int tax = 3500;
        ((TestTaxCalculator) calculator).setTax(income, tax);

        int result = estimator.estimateTax(income);

        int expectedTax = tax - tax / 10;
        Assert.assertEquals(expectedTax, result);
    }

    @Test
    public void testEstimateTaxMediumTax() {
        int income = 30000;
        int tax = 1500;
        ((TestTaxCalculator) calculator).setTax(income, tax);

        int result = estimator.estimateTax(income);

        int expectedTax = tax - 10;
        Assert.assertEquals(expectedTax, result);
    }

    @Test
    public void testEstimateTaxLowTax() {
        int income = 20000;
        int tax = 500;
        ((TestTaxCalculator) calculator).setTax(income, tax);

        int result = estimator.estimateTax(income);

        Assert.assertEquals(tax, result);
    }



    // Test implementation of TaxCalculator
    private static class TestTaxCalculator implements TaxCalculator {
        private final Map<Integer, Integer> taxMap;

        public TestTaxCalculator() {
            taxMap = new HashMap<>();
        }

        public void setTax(int income, int tax) {
            taxMap.put(income, tax);
        }

        @Override
        public int tax(int income) {
            return taxMap.getOrDefault(income, 0);
        }
    }
}
