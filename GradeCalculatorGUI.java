import javax.swing.*;
import java.awt.*;

public class GradeCalculatorGUI extends JFrame {

    private JTextField studentNameField;
    private JTextField rollNumberField;
    private JTextField numberOfSubjectsField;

    private JTextField[] subjectFields;
    private JTextField[] maximumMarksFields;
    private JTextField[] obtainedMarksFields;

    private JPanel subjectsPanel;

    private JLabel totalLabel;
    private JLabel percentageLabel;
    private JLabel gradeLabel;
    private JLabel resultLabel;

    private int numberOfSubjects = 0;

    public GradeCalculatorGUI() {

        setTitle("Student Grade Calculator");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        createTopPanel();
        createSubjectsPanel();
        createBottomPanel();

        setVisible(true);
    }

    // ---------------- STUDENT DETAILS ----------------

    private void createTopPanel() {

        JPanel topPanel = new JPanel(
                new GridLayout(4, 2, 10, 10));

        topPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Student Details"));

        topPanel.add(new JLabel("Student Name:"));

        studentNameField = new JTextField();
        topPanel.add(studentNameField);

        topPanel.add(new JLabel("Roll Number:"));

        rollNumberField = new JTextField();
        topPanel.add(rollNumberField);

        topPanel.add(new JLabel("Number of Subjects:"));

        numberOfSubjectsField = new JTextField();
        topPanel.add(numberOfSubjectsField);

        JButton createButton =
                new JButton("Create Subjects");

        createButton.addActionListener(
                e -> createSubjectFields());

        topPanel.add(new JLabel(""));
        topPanel.add(createButton);

        add(topPanel, BorderLayout.NORTH);
    }

    // ---------------- SUBJECT PANEL ----------------

    private void createSubjectsPanel() {

        subjectsPanel = new JPanel();

        subjectsPanel.setLayout(
                new BoxLayout(
                        subjectsPanel,
                        BoxLayout.Y_AXIS));

        JScrollPane scrollPane =
                new JScrollPane(subjectsPanel);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Enter Subject Details"));

        add(scrollPane, BorderLayout.CENTER);
    }

    // ---------------- CREATE SUBJECT FIELDS ----------------

    private void createSubjectFields() {

        try {

            numberOfSubjects = Integer.parseInt(
                    numberOfSubjectsField
                            .getText()
                            .trim());

            if (numberOfSubjects <= 0 ||
                    numberOfSubjects > 20) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter subjects between 1 and 20.");

                return;
            }

            // Remove old fields
            subjectsPanel.removeAll();

            // Create arrays
            subjectFields =
                    new JTextField[numberOfSubjects];

            maximumMarksFields =
                    new JTextField[numberOfSubjects];

            obtainedMarksFields =
                    new JTextField[numberOfSubjects];

            // -------- HEADINGS --------

            JPanel headingPanel =
                    new JPanel(
                            new GridLayout(
                                    1, 3, 10, 10));

            JLabel subjectHeading =
                    new JLabel(
                            "Subject Name",
                            SwingConstants.CENTER);

            JLabel maximumHeading =
                    new JLabel(
                            "Maximum Marks",
                            SwingConstants.CENTER);

            JLabel obtainedHeading =
                    new JLabel(
                            "Obtained Marks",
                            SwingConstants.CENTER);

            headingPanel.add(subjectHeading);
            headingPanel.add(maximumHeading);
            headingPanel.add(obtainedHeading);

            subjectsPanel.add(headingPanel);

            // -------- INPUT ROWS --------

            for (int i = 0;
                    i < numberOfSubjects;
                    i++) {

                JPanel rowPanel =
                        new JPanel(
                                new GridLayout(
                                        1, 3, 10, 10));

                // SUBJECT NAME BOX
                subjectFields[i] =
                        new JTextField();

                subjectFields[i].setToolTipText(
                        "Enter subject name");

                // MAXIMUM MARKS BOX
                maximumMarksFields[i] =
                        new JTextField();

                maximumMarksFields[i].setToolTipText(
                        "Enter maximum marks");

                // OBTAINED MARKS BOX
                obtainedMarksFields[i] =
                        new JTextField();

                obtainedMarksFields[i].setToolTipText(
                        "Enter obtained marks");

                rowPanel.add(subjectFields[i]);
                rowPanel.add(maximumMarksFields[i]);
                rowPanel.add(obtainedMarksFields[i]);

                subjectsPanel.add(rowPanel);
            }

            subjectsPanel.revalidate();
            subjectsPanel.repaint();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid number.");
        }
    }

    // ---------------- RESULT PANEL ----------------

    private void createBottomPanel() {

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout());

        JPanel resultPanel =
                new JPanel(
                        new GridLayout(
                                4, 2, 10, 10));

        resultPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Result"));

        resultPanel.add(
                new JLabel("Total Marks:"));

        totalLabel = new JLabel("-");
        resultPanel.add(totalLabel);

        resultPanel.add(
                new JLabel("Percentage:"));

        percentageLabel = new JLabel("-");
        resultPanel.add(percentageLabel);

        resultPanel.add(
                new JLabel("Grade:"));

        gradeLabel = new JLabel("-");
        resultPanel.add(gradeLabel);

        resultPanel.add(
                new JLabel("Result:"));

        resultLabel = new JLabel("-");
        resultPanel.add(resultLabel);

        // Buttons
        JPanel buttonPanel = new JPanel();

        JButton calculateButton =
                new JButton("Calculate");

        JButton clearButton =
                new JButton("Clear");

        JButton exitButton =
                new JButton("Exit");

        calculateButton.addActionListener(
                e -> calculateGrade());

        clearButton.addActionListener(
                e -> clearFields());

        exitButton.addActionListener(
                e -> System.exit(0));

        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        bottomPanel.add(
                resultPanel,
                BorderLayout.CENTER);

        bottomPanel.add(
                buttonPanel,
                BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ---------------- CALCULATE ----------------

    private void calculateGrade() {

        if (studentNameField
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter student name.");

            return;
        }

        if (rollNumberField
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter roll number.");

            return;
        }

        if (subjectFields == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "First create the subject fields.");

            return;
        }

        int[] maximumMarks =
                new int[numberOfSubjects];

        int[] obtainedMarks =
                new int[numberOfSubjects];

        try {

            for (int i = 0;
                    i < numberOfSubjects;
                    i++) {

                // SUBJECT NAME
                String subjectName =
                        subjectFields[i]
                                .getText()
                                .trim();

                if (subjectName.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter the name of Subject "
                                    + (i + 1));

                    subjectFields[i].requestFocus();

                    return;
                }

                // MAXIMUM MARKS
                String maximumText =
                        maximumMarksFields[i]
                                .getText()
                                .trim();

                if (maximumText.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter maximum marks for "
                                    + subjectName);

                    maximumMarksFields[i].requestFocus();

                    return;
                }

                maximumMarks[i] =
                        Integer.parseInt(maximumText);

                if (maximumMarks[i] <= 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Maximum marks must be greater than 0.");

                    return;
                }

                // OBTAINED MARKS
                String obtainedText =
                        obtainedMarksFields[i]
                                .getText()
                                .trim();

                if (obtainedText.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter obtained marks for "
                                    + subjectName);

                    obtainedMarksFields[i].requestFocus();

                    return;
                }

                obtainedMarks[i] =
                        Integer.parseInt(obtainedText);

                if (obtainedMarks[i] < 0 ||
                        obtainedMarks[i] >
                                maximumMarks[i]) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Obtained marks for "
                                    + subjectName
                                    + " must be between 0 and "
                                    + maximumMarks[i]);

                    return;
                }
            }

            // TOTAL
            int totalObtained =
                    GradeCalculator.calculateTotal(
                            obtainedMarks);

            int totalMaximum =
                    GradeCalculator.calculateMaximumTotal(
                            maximumMarks);

            // PERCENTAGE
            double percentage =
                    GradeCalculator.calculatePercentage(
                            totalObtained,
                            totalMaximum);

            // GRADE
            String grade =
                    GradeCalculator.calculateGrade(
                            percentage);

            // RESULT
            String result =
                    GradeCalculator.calculateResult(
                            obtainedMarks,
                            maximumMarks);

            // DISPLAY
            totalLabel.setText(
                    totalObtained
                            + " / "
                            + totalMaximum);

            percentageLabel.setText(
                    String.format(
                            "%.2f%%",
                            percentage));

            gradeLabel.setText(grade);

            resultLabel.setText(result);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter numbers only in marks fields.");
        }
    }

    // ---------------- CLEAR ----------------

    private void clearFields() {

        studentNameField.setText("");
        rollNumberField.setText("");
        numberOfSubjectsField.setText("");

        subjectsPanel.removeAll();

        subjectsPanel.revalidate();
        subjectsPanel.repaint();

        subjectFields = null;
        maximumMarksFields = null;
        obtainedMarksFields = null;

        numberOfSubjects = 0;

        totalLabel.setText("-");
        percentageLabel.setText("-");
        gradeLabel.setText("-");
        resultLabel.setText("-");
    }
}