package fr.excilys.rmomprive.app;

import fr.excilys.rmomprive.persistence.CompanyDao;

public class Console 
{
    public static void main( String[] args )
    {
        CompanyDao companyDao = new CompanyDao();
        System.out.println(companyDao.getAll());
    }
}
