package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        BookInventoryOperations.loadBooksFromCsv();

        BookInventoryManager.runProgram(scnr);
    }
}