package ru.evsmanko.mankoff.utils;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Service
public class FormattingUtils {
    public String formatMoney(BigDecimal bigDecimal) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setGroupingUsed(false);
        return decimalFormat.format(bigDecimal);
    }
}
