package com.example.application;

import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {
    private final CanvasAPiCalls canvasApi;
    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     * @param service
     *            The message service. Automatically injected Spring managed bean.
     * @throws Exception 
     */
    public MainView(CanvasAPiCalls canvasAPi){

        //instances the api method used for calling all api's used
        this.canvasApi = canvasAPi;
        addClassName("centered-content");

        //Creates combo box and prevents users from typing their own things into it
        ComboBox <String> courseList = new ComboBox<>();
        courseList.setLabel("Currently enrolled courses");
        //added to test api connection and give basic visual example
        courseList.setItems("If You are seeing this", "the APi or URI link is broken", "Please ensure both are correct");
        courseList.setFocusSelectedItem(true);
        add(courseList);

        //populates combo box with the user's currently enrolled course
        try{
            List<String> course = canvasApi.getCourses();
            courseList.setItems(course);
            if(course.isEmpty()){
                courseList.setItems("no courses found");
            }

        }
        catch(Exception e){
            System.out.println("system has encountered the following error");
            System.out.println(e);
        }

        //updates the second api to get the assignments for the course selected via combo box
        Grid<String[]> assignments_details = new Grid<>();
        assignments_details.setVisible(false);
        add(assignments_details);

        // Creates columns used for showing thisweeks assignments
        assignments_details.addColumn(assignment -> assignment[0]).setHeader("Assignment Name");
        assignments_details.addColumn(assignment -> assignment[1]).setHeader("Due Date");
        
        // adds a button that takes you to the asignment page in a new tab
        assignments_details.addComponentColumn(assignment -> {
            Button linkButton = new Button("View", clickEvent -> {
                String linkUrl = assignment[3]; 
                UI.getCurrent().getPage().open(linkUrl, "_blank");
                Notification.show("Opening: " + assignment[0]);
            });
            return linkButton; 
        });

        //makes the grid initially invisable and adds to mainviewer
        assignments_details.setVisible(false);
        add(assignments_details);

        //  Adds all of the respective assignments to the grid while removing the old/ uneeded ones
        courseList.addValueChangeListener(event -> {
            String selectedCourse = event.getValue();
            if (selectedCourse != null) {
                try {
                    List<String[]> assignments = canvasApi.getAssignments(selectedCourse);
                    assignments_details.setItems(assignments);
                    assignments_details.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                assignments_details.setVisible(false);
            }
        });

    }
            
}
