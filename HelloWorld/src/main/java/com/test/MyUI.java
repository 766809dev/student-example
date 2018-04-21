package com.test;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.sass.internal.tree.controldirective.ElseNode;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
    // Create a list of type Student (which we defined in Student.java)
    ArrayList<Student> students = new ArrayList<Student>();
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final Label studentCountLabel = new Label("You have added 0 students");

        final TextField studentTextField = new TextField();
        studentTextField.setCaption("Enter student name:");

        final TextField markTextField = new TextField();
        markTextField.setCaption("Enter student mark:");

        final Label gradeLabel = new Label();

        Button gradeButton = new Button("Grade Student");
        gradeButton.addClickListener(e -> {
            String grade = calculateGrade(markTextField);
            gradeLabel.setValue(studentTextField.getValue() + "'s grade is " + grade);
            // Now add this student to the students list
            String name = studentTextField.getValue();
            int mark = Integer.parseInt(markTextField.getValue());
            students.add(new Student(name, mark, grade));
            studentCountLabel.setValue("You have added " +
                                         students.size() + " students");
        });
        
        layout.addComponents(studentCountLabel, studentTextField, 
                            markTextField, gradeButton, gradeLabel);
        
        setContent(layout);
    }

	private String calculateGrade(final TextField markTextField) {
		// Calculate grade based on mark
            String grade = "F";
            // Convert the string 'mark' into an integer
            int markInExam = Integer.parseInt(markTextField.getValue());
            if(markInExam >= 80) {
                grade = "A";
            }
            else if(markInExam >= 70) {
                grade = "B";
            }
            else if(markInExam >= 40) {
                grade = "C";
            }
            else {
                grade = "F";
            }
		return grade;
	}

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
