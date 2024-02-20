package com.lipisak.vaadin.slidetab;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.Arrays;


@CssImport("./styles/demo-styles.css")
@Route("")
public class DemoView extends Div {

    public DemoView() {
        addClassName("demo-view");

        Div leftPanel = new Div();
        leftPanel.addClassName("left-panel");
        Div rightPanel = new Div();
        rightPanel.addClassName("right-panel");
        add(leftPanel, rightPanel);

        SlideTab tabToControl = createLeftBottomSlideTab();

        leftPanel.add(
                createLeftSlideTab(),
                tabToControl);

        createRightPanelControls(rightPanel, tabToControl);
        rightPanel.add(createRightPanelGridTab());

        add(createOuterTab());
    }

    private void createRightPanelControls(Div container, SlideTab tabToControl) {
        TextField durationField = new TextField("Animation duration");
        durationField.setValue("1000");
        durationField.setPattern("[0-9]+");

        TextField delayField = new TextField("Delay");
        delayField.setValue("1000");
        delayField.setPattern("[0-9]+");

        Checkbox expandCheckbox = new Checkbox("Expand");
        Checkbox animatedCheckbox = new Checkbox("Animated");
        Div checkBoxWrapper = new Div(expandCheckbox, animatedCheckbox);

        Button scheduleOptions = new Button("Schedule by checkboxes");
        scheduleOptions.addClickListener(event -> {
            tabToControl.setAnimationDuration(getIntValue(durationField));
            tabToControl.scheduleExpand(
                    expandCheckbox.getValue(),
                    animatedCheckbox.getValue(),
                    getIntValue(delayField));
        });
        Button scheduleExpand = new Button("Schedule expand");
        scheduleExpand.addClickListener(event -> {
            tabToControl.setAnimationDuration(getIntValue(durationField));
            tabToControl.scheduleExpand(getIntValue(delayField));
        });
        Button scheduleCollapse = new Button("Schedule collapse");
        scheduleCollapse.addClickListener(event -> {
            tabToControl.setAnimationDuration(getIntValue(durationField));
            tabToControl.scheduleCollapse(getIntValue(delayField));
        });
        Button scheduleToggle = new Button("Schedule toggle");
        scheduleToggle.addClickListener(event -> {
            tabToControl.setAnimationDuration(getIntValue(durationField));
            tabToControl.scheduleToggle(getIntValue(delayField));
        });

        container.add(durationField, delayField, scheduleExpand, scheduleCollapse, scheduleToggle, scheduleOptions, checkBoxWrapper);
    }

    private int getIntValue(TextField field) {
        try {
            field.setErrorMessage(null);
            return Integer.valueOf(field.getValue());
        } catch (NumberFormatException e) {
            field.setErrorMessage("Not an integer");
            return 0;
        }
    }

    private SlideTab createLeftSlideTab() {
        Div content = new Div();
        content.setId("left-panel-content");

        // Slide tab for the left panel, shows some text and a button to close
        SlideTab slideTab = new SlideTabBuilder(content, "Important information")
                .mode(SlideMode.LEFT).tabPosition(SlideTabPosition.MIDDLE).build();
        slideTab.addClassName("important");

        Button button = new Button("Click to close with 1s delay");
        button.addClickListener(event -> slideTab.scheduleCollapse(1000));

        content.add(new Text(
                "Dizzle i'm in the shizzle dolor. Fusce magna bow wow wow, dignissizzle sizzle amet, fringilla bizzle, " +
                        "nizzle nizzle, ass. Maecenizzle crackalackin nisi. Uhuh ... yih! malesuada neque fo shizzle my nizzle " +
                        "daahng dawg. Suspendisse . Crizzle aliquizzle tristique i'm in the shizzle. Suspendisse blandit " +
                        "ultricizzle purus. Break it down bow wow wow libero, pharetra interdizzle, posuere izzle, uhuh ... " +
                        "yih! gizzle, dang."), button);

        return slideTab;
    }

    private SlideTab createLeftBottomSlideTab() {
        Div content = new Div();
        content.setId("left-panel-bottom-content");

        SlideTab slideTab = new SlideTabBuilder(content, "Remote controlled")
                .mode(SlideMode.BOTTOM).tabPosition(SlideTabPosition.END).build();

        content.add(
                new Paragraph("Hello, I am remote controlled, you can control me through the controls in the right panel :)"),
                new Paragraph("I'm just another paragraph that is along for the ride."));

        return slideTab;
    }

    private SlideTab createRightPanelGridTab() {
        Div content = new Div();
        content.setId("right-panel-bottom-content");

        SlideTab slideTab = new SlideTabBuilder(content, "Grid")
                .mode(SlideMode.RIGHT).tabPosition(SlideTabPosition.BEGINNING).build();

        Grid<Test> testGrid = new Grid<>(Test.class);
        testGrid.setItems(Arrays.asList(
                new Test("Aaaa", "Aaaaaaaa", "aaa@aaa.com", 0),
                new Test("Bbbb", "Bbbbbbbb", "bbb@bbb.com", 1),
                new Test("Cccc", "Cccccccc", "ccc@ccc.com", 2),
                new Test("Dddd", "Dddddddd", "ddd@ddd.com", 3),
                new Test("Eeee", "Eeeeeeee", "eee@eee.com", 4),
                new Test("Ffff", "Ffffffff", "fff@fff.com", 5),
                new Test("Gggg", "Gggggggg", "ggg@ggg.com", 6),
                new Test("Hhhh", "Hhhhhhhh", "hhh@hhh.com", 7)
        ));

        content.add(testGrid);

        return slideTab;
    }

    private SlideTab createOuterTab() {
        Div content = new Div();
        content.setId("outer-panel-content");

        SlideTab slideTab = new SlideTabBuilder(content, "Fancy Styles")
                .mode(SlideMode.TOP).tabPosition(SlideTabPosition.MIDDLE).autoCollapseSlider(true).build();
        slideTab.addToggleListener(event -> Notification.show(
                "Top center SlideTab " + (event.isExpand() ? "expanded!" : "closed!"), 2000, Notification.Position.MIDDLE));
        slideTab.setId("outer-panel");
        slideTab.setExpandComponent(VaadinIcon.EXPAND.create());
        slideTab.setCollapseComponent(new Span("<Collapse>"));

        content.add(
                new Paragraph("This closes when clicking outside"),
                new Text(
                        "Dizzle i'm in the shizzle dolor. Fusce magna bow wow wow, dignissizzle sizzle amet, fringilla bizzle, " +
                                "nizzle nizzle, ass. Maecenizzle crackalackin nisi. Uhuh ... yih! malesuada neque fo shizzle my nizzle " +
                                "daahng dawg. Suspendisse . Crizzle aliquizzle tristique i'm in the shizzle. Suspendisse blandit " +
                                "ultricizzle purus. Break it down bow wow wow libero, pharetra interdizzle, posuere izzle, uhuh ... " +
                                "yih! gizzle, dang."));

        return slideTab;
    }

    public static class Test {
        private String firstName;
        private String lastName;
        private String email;
        private int age;

        public Test(String firstName, String lastName, String email, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    }
}
