package com.starsoftware.allfifebooks;


import com.starsoftware.allfifebooks.commands.AddBookCommand;
import com.starsoftware.allfifebooks.commands.Command;
import com.starsoftware.allfifebooks.commands.UserPromptFields;
import com.starsoftware.allfifebooks.commands.UserPrompts;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.List;

public class AllFifeBooks {
    private static final Logger log = Logger.getLogger(AllFifeBooks.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        AllFifeBooks allFifeBooks = new AllFifeBooks();
        allFifeBooks.runApplication(new QuestionAsker(System.in, System.out));

    }

    public String runApplication(QuestionAsker questionAsker) {
        System.out.println("Welcome to All Fife Books, Please select an option:");
        String optionSelection = questionAsker.ask(" Press 1 to add a new book \n Press 2 to sell a book" +
                "\n Press 3 to bin a book \n Press 4 to refurbish a book" +
                "\n Press 5 to view a book report");
        //  open up standard input
        if (optionSelection.trim().equals("1")) {
            log.debug("Add Book Selected");
            Command addCommand = new AddBookCommand();
            List<UserPrompts> userPrompts = addCommand.executeCommand();
            askUserPrompts(userPrompts, addCommand, questionAsker);

        } else {
            log.debug("Not Implemented Yet");
        }
        System.out.println("Selection" + optionSelection);


        return "Hello";
    }

    private void askUserPrompts(List<UserPrompts> userPrompts, Command selectedCommand, QuestionAsker questionAsker) {
        for (UserPrompts userPrompt : userPrompts) {
            askUserPrompt(selectedCommand, questionAsker, userPrompt);
        }
    }

    private void askUserPrompt(Command selectedCommand, QuestionAsker questionAsker, UserPrompts userPrompt) {
        userPrompt.setValue(questionAsker.ask(userPrompt.getMessage()));
        validateInput(selectedCommand, questionAsker, userPrompt);
    }

    private void validateInput(Command selectedCommand, QuestionAsker questionAsker, UserPrompts userPrompt) {
        if (userPrompt.getValue().trim().length() <= 1) {
            System.out.println("Sorry you must enter a value. Please try again");
            askUserPrompt(selectedCommand, questionAsker, userPrompt);
        }
        if (userPrompt.getRequiresValidation()) {
            boolean validResponse = selectedCommand.validatePrompt(userPrompt);
            if ((!validResponse) && (userPrompt.getField().equals(UserPromptFields.BOOK_ID.getField()))) {
                System.out.println("Sorry That Book ID is already In use. Please try again");
                askUserPrompt(selectedCommand, questionAsker, userPrompt);
            } else if ((!validResponse) && (userPrompt.getField().equals(UserPromptFields.STATUS.getField()))) {
                System.out.println("Sorry That is Invalid only 'NEW' or 'REFURB' are excepted. Please try again");
                askUserPrompt(selectedCommand, questionAsker, userPrompt);
            }
        }
    }
}
