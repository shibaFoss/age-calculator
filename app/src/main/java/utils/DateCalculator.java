package utils;

/**
 * The class is responsible for calculating days between two different dates.
 */
public abstract class DateCalculator {

    public static class Result {
        String years;
        String months;
        String days;
        String hours;
        String seconds;
        String milliseconds;
        public String daysLived;
    }

    public static class Year {
        int day;
        int month;
        int year;

        public Year(int day, int month, int year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

    /**
     * The class don't offer a public constructor.
     */
    private DateCalculator() {
        
    }

    /**
     * Calculate the days between two different dates.
     */
    public Result calculate(Year from, Year to) throws Exception {
        if (to.year < from.year)
            throw new Exception("fromYear can no be greater than toYear.");

        long years = to.year - (from.year + 1);
        long months = years * 12;
        long days = 0;

        //Calculate the days.
        for (int index = 0; index < years; index++) {
            int year = from.year + index;
            if (!isLeapYear(year)) {
                days += 365;
            } else {
                days += 366;
            }
        }

        long monthInclude;
        if (to.month == 0) {
            monthInclude = (12 - from.month);
        } else {
            monthInclude = (12 - from.month) + (to.month - 1);
        }

        long dayInclude = (getDaysOfMonth(from.month, from.year) - from.day) + (to.day);

        for (int index = 12; index > from.month; index--) {
            days += getDaysOfMonth(index, from.year);
        }

        for (int index = 1; index < to.month; index++) {
            days += getDaysOfMonth(index, from.year);
        }

        days += dayInclude;

        Result result = new Result();
        result.years = String.valueOf(years);
        result.months = String.valueOf(monthInclude);
        result.days = String.valueOf(dayInclude);

        result.daysLived = String.valueOf(days);
        result.hours = String.valueOf((days * 24));
        result.seconds = String.valueOf((days * 24) * 60);
        result.milliseconds = String.valueOf(((days * 24) * 60) * 1000);
        return result;
    }

    /**
     * Check whether the given year is a leap year or not.
     * @return return true if the given year is a leap year, false otherwise.
     */
    public static boolean isLeapYear(int year) {
        String x = String.valueOf((double) year / 4);
        return x.contains(".");
    }

    /**
     * Calculate the days of an month
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


}
