package Build;

interface PC_Types {

    interface OfficePC
    {

    }

    interface GamingPC
    {

    }

    interface EditingPC
    {

    }

    class PCtaker implements OfficePC, GamingPC, EditingPC
    {
        double Budget = ExpressBuilder.getUserBudget();
        int Type = (int) ExpressBuilder.getUserType();
        double Tax = ExpressBuilder.getUserTax();



        public void PC_Build()
        {
            PCtaker Option = new PCtaker();


        }
    }

}
