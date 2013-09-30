package com.starsoftware.allfifebooks;


import com.starsoftware.allfifebooks.commands.*;
import com.starsoftware.allfifebooks.userPrompts.QuestionAsker;
import com.starsoftware.allfifebooks.userPrompts.UserPromptFields;
import com.starsoftware.allfifebooks.userPrompts.UserPrompts;
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

    public void runApplication(QuestionAsker questionAsker) {
        System.out.println("Welcome to All Fife Books, Please select an option:");
        String optionSelection = questionAsker.ask(" Press 1 to add a new book \n Press 2 to sell a book" +
                "\n Press 3 to bin a book \n Press 4 to refurbish a book" +
                "\n Press 5 to view a book report");
        //  open up standard input
        if (optionSelection.trim().equals("1")) {
            System.out.println(" ### Add Book ###");
            Command addCommand = new AddBookCommand();
            List<UserPrompts> userPrompts = addCommand.executeCommand();
            userPrompts = askUserPrompts(userPrompts, addCommand, questionAsker);
            addCommand.save(userPrompts);
        } else if (optionSelection.trim().equals("2")) {
            System.out.println(" ### Sell Book ###");
            Command sellCommand = new SellBookCommand();
            List<UserPrompts> userPrompts = sellCommand.executeCommand();
            userPrompts = askUserPrompts(userPrompts, sellCommand, questionAsker);
            sellCommand.save(userPrompts);
        } else if (optionSelection.trim().equals("3")) {
            System.out.println(" ### Bin Book ###");
            Command binCommand = new BinBookCommand();
            List<UserPrompts> userPrompts = binCommand.executeCommand();
            userPrompts = askUserPrompts(userPrompts, binCommand, questionAsker);
            binCommand.save(userPrompts);
        } else {
            log.debug("Not Implemented Yet");
        }
        System.out.println("Selection" + optionSelection);
    }

    private List<UserPrompts> askUserPrompts(List<UserPrompts> userPrompts, Command selectedCommand, QuestionAsker questionAsker) {
        for (UserPrompts userPrompt : userPrompts) {
            userPrompt = askUserPrompt(selectedCommand, questionAsker, userPrompt);
            if ((userPrompt.getField().equals(UserPromptFields.BOOK_LISTING)) && (userPrompt.getValue().toUpperCase().equals("YES"))) {
                selectedCommand.displayBookListing();

            }
        }
        return userPrompts;
    }

    private UserPrompts askUserPrompt(Command selectedCommand, QuestionAsker questionAsker, UserPrompts userPrompt) {
        userPrompt.setValue(questionAsker.ask(userPrompt.getMessage()));
        validateInput(selectedCommand, questionAsker, userPrompt);
        return userPrompt;
    }

    private void validateInput(Command selectedCommand, QuestionAsker questionAsker, UserPrompts userPrompt) {
        if (userPrompt.getValue().length() <= 1) {
            System.out.println("Sorry you must enter a value. Please try again");
            askUserPrompt(selectedCommand, questionAsker, userPrompt);
        }
        if (userPrompt.getRequiresValidation()) {
            boolean validResponse = selectedCommand.validatePrompt(userPrompt);
            if (!validResponse) {
                if ((selectedCommand.getCommand().equals(Commands.ADD)) && (userPrompt.getField().equals(UserPromptFields.BOOK_ID))) {
                    System.out.println("Sorry That Book ID is already In use. Please try again");
                    askUserPrompt(selectedCommand, questionAsker, userPrompt);
                } else if (userPrompt.getField().equals(UserPromptFields.BOOK_ID)) {
                    System.out.println("Sorry That Book ID is invalid. Please try again");
                    askUserPrompt(selectedCommand, questionAsker, userPrompt);
                }
                if ((userPrompt.getField().equals(UserPromptFields.STATUS))) {
                    System.out.println("Sorry That is Invalid only 'NEW' or 'REFURB' are excepted. Please try again");
                    askUserPrompt(selectedCommand, questionAsker, userPrompt);
                } else if ((userPrompt.getField().equals(UserPromptFields.BOOK_LISTING))) {
                    System.out.println("Sorry That is Invalid only 'YES' or 'NO' are excepted. Please try again");
                    askUserPrompt(selectedCommand, questionAsker, userPrompt);
                }
            }
        }
    }
}
