package com.starsoftware.allfifebooks;


import com.starsoftware.allfifebooks.commands.AddBookCommand;
import com.starsoftware.allfifebooks.commands.Command;
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
             userPrompt.setValue(questionAsker.ask(userPrompt.getMessage()));
            if(userPrompt.getRequiresValidation()){
              boolean validResponse =  selectedCommand.validatePrompt(userPrompt);

            }
        }
    }
}
