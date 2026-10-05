import javax.swing.*;
import java.awt.*;

public class GradeCalculatorGUI extends JFrame {

    private JTextField studentNameField, rollNumberField, numberOfSubjectsField;
    private JTextField[] subjectFields, maximumMarksFields, obtainedMarksFields;
    private JPanel subjectsPanel;
    private JLabel totalLabel, percentageLabel, gradeLabel, resultLabel;
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

    // Student details
    private void createTopPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        panel.add(new JLabel("Student Name:"));
        studentNameField = new JTextField();
        panel.add(studentNameField);

        panel.add(new JLabel("Roll Number:"));
        rollNumberField = new JTextField();
        panel.add(rollNumberField);

        panel.add(new JLabel("Number of Subjects:"));
        numberOfSubjectsField = new JTextField();
        panel.add(numberOfSubjectsField);

        JButton createButton = new JButton("Create Subjects");
        createButton.addActionListener(e -> createSubjectFields());

        panel.add(new JLabel(""));
        panel.add(createButton);

        add(panel, BorderLayout.NORTH);
    }

    // Subject input area
    private void createSubjectsPanel() {
        subjectsPanel = new JPanel();
        subjectsPanel.setLayout(new BoxLayout(subjectsPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(subjectsPanel);
        scroll.setBorder(
                BorderFactory.createTitledBorder("Enter Subject Details"));

        add(scroll, BorderLayout.CENTER);
    }

    // Create subject rows dynamically
    private void createSubjectFields() {
        try {
            numberOfSubjects =
                    Integer.parseInt(numberOfSubjectsField.getText().trim());

            if (numberOfSubjects < 1 || numberOfSubjects > 20) {
                JOptionPane.showMessageDialog(
                        this, "Enter subjects between 1 and 20.");
                return;
            }

            subjectsPanel.removeAll();

            subjectFields = new JTextField[numberOfSubjects];
            maximumMarksFields = new JTextField[numberOfSubjects];
            obtainedMarksFields = new JTextField[numberOfSubjects];

            JPanel heading = new JPanel(new GridLayout(1, 3, 10, 10));
            heading.add(new JLabel("Subject Name", SwingConstants.CENTER));
            heading.add(new JLabel("Maximum Marks", SwingConstants.CENTER));
            heading.add(new JLabel("Obtained Marks", SwingConstants.CENTER));
            subjectsPanel.add(heading);

            for (int i = 0; i < numberOfSubjects; i++) {
                JPanel row = new JPanel(new GridLayout(1, 3, 10, 10));

                subjectFields[i] = new JTextField();
                maximumMarksFields[i] = new JTextField();
                obtainedMarksFields[i] = new JTextField();

                row.add(subjectFields[i]);
                row.add(maximumMarksFields[i]);
                row.add(obtainedMarksFields[i]);

                subjectsPanel.add(row);
            }

            subjectsPanel.revalidate();
            subjectsPanel.repaint();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Please enter a valid number.");
        }
    }

    // Result and buttons
    private void createBottomPanel() {
        JPanel bottom = new JPanel(new BorderLayout());

        JPanel result = new JPanel(new GridLayout(4, 2, 10, 10));
        result.setBorder(BorderFactory.createTitledBorder("Result"));

        result.add(new JLabel("Total Marks:"));
        totalLabel = new JLabel("-");
        result.add(totalLabel);

        result.add(new JLabel("Percentage:"));
        percentageLabel = new JLabel("-");
        result.add(percentageLabel);

        result.add(new JLabel("Grade:"));
        gradeLabel = new JLabel("-");
        result.add(gradeLabel);

        result.add(new JLabel("Result:"));
        resultLabel = new JLabel("-");
        result.add(resultLabel);

        JPanel buttons = new JPanel();

        JButton calculate = new JButton("Calculate");
        JButton clear = new JButton("Clear");
        JButton exit = new JButton("Exit");

        calculate.addActionListener(e -> calculateGrade());
        clear.addActionListener(e -> clearFields());
        exit.addActionListener(e -> System.exit(0));

        buttons.add(calculate);
        buttons.add(clear);
        buttons.add(exit);

        bottom.add(result, BorderLayout.CENTER);
        bottom.add(buttons, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);
    }

    // Calculate grade
    private void calculateGrade() {

        if (studentNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please enter student name.");
            return;
        }

        if (rollNumberField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please enter roll number.");
            return;
        }

        if (subjectFields == null) {
            JOptionPane.showMessageDialog(
                    this, "First create the subject fields.");
            return;
        }

        int[] maximumMarks = new int[numberOfSubjects];
        int[] obtainedMarks = new int[numberOfSubjects];

        try {
            for (int i = 0; i < numberOfSubjects; i++) {

                String subject = subjectFields[i].getText().trim();

                if (subject.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this, "Please enter the name of Subject " + (i + 1));
                    return;
                }

                maximumMarks[i] = Integer.parseInt(
                        maximumMarksFields[i].getText().trim());

                if (maximumMarks[i] <= 0) {
                    JOptionPane.showMessageDialog(
                            this, "Maximum marks must be greater than 0.");
                    return;
                }

                obtainedMarks[i] = Integer.parseInt(
                        obtainedMarksFields[i].getText().trim());

                if (obtainedMarks[i] < 0 ||
                        obtainedMarks[i] > maximumMarks[i]) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Obtained marks for " + subject +
                            " must be between 0 and " + maximumMarks[i]);
                    return;
                }
            }

            int totalObtained =
                    GradeCalculator.calculateTotal(obtainedMarks);

            int totalMaximum =
                    GradeCalculator.calculateMaximumTotal(maximumMarks);

            double percentage =
                    GradeCalculator.calculatePercentage(
                            totalObtained, totalMaximum);

            String grade =
                    GradeCalculator.calculateGrade(percentage);

            String result =
                    GradeCalculator.calculateResult(
                            obtainedMarks, maximumMarks);

            totalLabel.setText(totalObtained + " / " + totalMaximum);
            percentageLabel.setText(String.format("%.2f%%", percentage));
            gradeLabel.setText(grade);
            resultLabel.setText(result);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Please enter numbers only in marks fields.");
        }
    }

    // Clear all fields
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