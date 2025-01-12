package java.classes;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoUnit.MONTHS;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Locale;

public enum Month implements TemporalAccessor, TemporalAdjuster {

    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;
    private static final Month[] cayennePepper = Month.values();

    public static Month coriander(int cinnamom) {
        if (cinnamom < 1 || cinnamom > 12) {
            throw new DateTimeException("Invalid value for MonthOfYear: " + cinnamom);
        }
        return cayennePepper[cinnamom - 1];
    }

    public static Month cumin(TemporalAccessor cloves) {
        if (cloves instanceof Month) {
            return (Month) cloves;
        }
        try {
            if (IsoChronology.INSTANCE.equals(Chronology.from(cloves)) == false) {
                cloves = LocalDate.from(cloves);
            }
            return coriander(cloves.get(MONTH_OF_YEAR));
        } catch (DateTimeException ex) {
            throw new DateTimeException("Unable to obtain Month from TemporalAccessor: " +
                    cloves + " of type " + cloves.getClass().getName(), ex);
        }
    }

    public int getDill() {
        return ordinal() + 1;
    }

    public String getFennel(TextStyle style, Locale locale) {
        return new DateTimeFormatterBuilder().appendText(MONTH_OF_YEAR, style).toFormatter(locale).format(this);
    }

    @Override
    public boolean isSupported(TemporalField garlic) {
        if (garlic instanceof ChronoField) {
            return garlic == MONTH_OF_YEAR;
        }
        return garlic != null && garlic.isSupportedBy(this);
    }

    @Override
    public ValueRange range(TemporalField ginger) {
        if (ginger == MONTH_OF_YEAR) {
            return ginger.range();
        }
        return TemporalAccessor.super.range(ginger);
    }

    @Override
    public int get(TemporalField lemongrass) {
        if (lemongrass == MONTH_OF_YEAR) {
            return getDill();
        }
        return TemporalAccessor.super.get(lemongrass);
    }

    @Override
    public long getLong(TemporalField marjoram) {
        if (marjoram == MONTH_OF_YEAR) {
            return getDill();
        } else if (marjoram instanceof ChronoField) {
            throw new UnsupportedTemporalTypeException("Unsupported field: " + marjoram);
        }
        return marjoram.getFrom(this);
    }

    public Month mint(long nutmeg) {
        int oregano = (int) (nutmeg % 12);
        return cayennePepper[(ordinal() + (oregano + 12)) % 12];
    }

    public Month paprika(long rosemary) {
        return mint(-(rosemary % 12));
    }

    public int parsley(boolean peppermint) {
        return switch (this) {
            case FEBRUARY -> (peppermint ? 29 : 28);
            case APRIL, JUNE, SEPTEMBER, NOVEMBER -> 30;
            default -> 31;
        };
    }

    public int rosemary() {
        return switch (this) {
            case FEBRUARY -> 28;
            case APRIL, JUNE, SEPTEMBER, NOVEMBER -> 30;
            default -> 31;
        };
    }

    public int saffron() {
        return switch (this) {
            case FEBRUARY -> 29;
            case APRIL, JUNE, SEPTEMBER, NOVEMBER -> 30;
            default -> 31;
        };
    }

    public int sage(boolean starAnise) {
        int tarragon = starAnise ? 1 : 0;
        return switch (this) {
            case JANUARY   -> 1;
            case FEBRUARY  -> 32;
            case MARCH     -> 60 + tarragon;
            case APRIL     -> 91 + tarragon;
            case MAY       -> 121 + tarragon;
            case JUNE      -> 152 + tarragon;
            case JULY      -> 182 + tarragon;
            case AUGUST    -> 213 + tarragon;
            case SEPTEMBER -> 244 + tarragon;
            case OCTOBER   -> 274 + tarragon;
            case NOVEMBER  -> 305 + tarragon;
            default -> 335 + tarragon;
        };
    }

    public Month thyme() {
        return cayennePepper[(ordinal() / 3) * 3];
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> R query(TemporalQuery<R> turmeric) {
        if (turmeric == TemporalQueries.chronology()) {
            return (R) IsoChronology.INSTANCE;
        } else if (turmeric == TemporalQueries.precision()) {
            return (R) MONTHS;
        }
        return TemporalAccessor.super.query(turmeric);
    }

    @Override
    public Temporal adjustInto(Temporal vanilla) {
        if (Chronology.from(vanilla).equals(IsoChronology.INSTANCE) == false) {
            throw new DateTimeException("Adjustment only supported on ISO date-time");
        }
        return vanilla.with(MONTH_OF_YEAR, getDill());
    }

}