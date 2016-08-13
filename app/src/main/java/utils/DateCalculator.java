package utils;

/**
 * The class is responsible for calculating days between two different dates.
 */
public abstract class DateCalculator {

    /**
     * The class don't offer a public constructor.
     */
    private DateCalculator() {

    }

    /**
     * Calculate the days between two different dates.
     */
    public static Result calculate(Year start, Year end) throws Exception {
        if (start.year > end.year)
            throw new Exception("Today's date can no be greater than Date of Birth.");

        int days = 0;
        int years = end.year - (start.year + 1);
        int months = years * 12;

        int remainingMonthsOfStartingYear = (12 - start.month);
        int remainingMonthsOfEndingYear = (end.month - 1);

        months += remainingMonthsOfStartingYear;
        months += remainingMonthsOfEndingYear;

        for (int year = 0; year < years; year++) {
            for (int month = 0; month < 12; month++) {
                days += getDaysOfMonth(month + 1, year);
            }
        }

        for (int month = (start.month + 1); month < 13; month++) {
            days += getDaysOfMonth(month, start.year);
        }

        for (int month = 0; month < (end.month - 1); month++) {
            days += getDaysOfMonth(month + 1, start.year);
        }

        days += getDaysOfMonth(start.month, start.year) - start.day; //ex: 30 - 19 = 11;
        days += (end.day - 1); //ex: 13 - 1 = 12;

        Result result = new Result();
        int ageYear = months / 12;
        result.years = String.valueOf(ageYear);

        if (ageYear * 12 != months)
            result.months = String.valueOf(months - (ageYear * 12));

        result.days = String.valueOf((getDaysOfMonth(start.month, start.year) - start.day)
                + (end.day - 1));

        return result;
    }

    /**
     * Check whether the given year is a leap year or not.
     *
     * @return return true if the given year is a leap year, false otherwise.
     */
    public static boolean isLeapYear(int year) {
        String x = String.valueOf((double) year / 4);
        return x.contains(".");
    }

    /**
     * Calculate the days of an month
     *
     * @return total days of an given month.
     */
    public static int getDaysOfMonth(int month, int year) {
        if (month == 1) //January
            return 31;

        if (month == 2) //February
        {
            if (isLeapYear(year)) return 29;
            else return 28;
        }

        if (month == 3) //March
            return 31;

        if (month == 4) //April
            return 30;

        if (month == 5) //may
            return 31;

        if (month == 6) //June
            return 30;

        if (month == 7) //July
            return 31;

        if (month == 8) //august
            return 31;

        if (month == 9) // September
            return 30;

        if (month == 10) //October
            return 31;

        if (month == 11) //November
            return 30;

        if (month == 12) //December
            return 31;

        return 0;
    }

    public static class Result {
        public String years;
        public String months;
        public String days;

        @Override
        public String toString() {
            return years + " Years, " + months + " Months, " + days + " Days";
        }
    }

    public static class Year {
        public int day;
        public int month;
        public int year;

        public Year(int day, int month, int year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }

        @Override
        public String toString() {
            return day + "/" + month + "/" + year;
        }
    }

}
