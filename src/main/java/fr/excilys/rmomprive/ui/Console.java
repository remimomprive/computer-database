package fr.excilys.rmomprive.ui;

import fr.excilys.rmomprive.controller.CompanyController;

public class Console 
{
    public static void main(String[] args)
    {
    	CompanyController companyController = CompanyController.getInstance();
        System.out.println(companyController.getAll());
        System.out.println(companyController.getById(1));
    }
}
