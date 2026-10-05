public class GradeCalculator {

    public static int calculateTotal(int[] marks) {
        int total = 0;

        for (int mark : marks) {
            total += mark;
        }

        return total;
    }

    public static int calculateMaximumTotal(int[] maximumMarks) {
        int total = 0;

        for (int mark : maximumMarks) {
            total += mark;
        }

        return total;
    }

    public static double calculatePercentage(
            int totalObtained,
            int totalMaximum) {

        return ((double) totalObtained / totalMaximum) * 100;
    }

    public static String calculateGrade(double percentage) {

        if (percentage >= 90) {
            return "A+";
        } else if (percentage >= 80) {
            return "A";
        } else if (percentage >= 70) {
            return "B";
        } else if (percentage >= 60) {
            return "C";
        } else if (percentage >= 50) {
            return "D";
        } else if (percentage >= 40) {
            return "E";
        } else {
            return "F";
        }
    }

    public static String calculateResult(
            int[] marks,
            int[] maximumMarks) {

        for (int i = 0; i < marks.length; i++) {

            double subjectPercentage =
                    ((double) marks[i] / maximumMarks[i]) * 100;

            if (subjectPercentage < 40) {
                return "FAIL";
            }
        }

        return "PASS";
    }
}