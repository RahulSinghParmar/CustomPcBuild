package Build;

interface PC_Types {

    interface OfficePC
    {
        void TakeCPU();
        void TakeMobo();
        void TakeFAN();
        void TakeGPU();
        void TakeRAM();
        void TakeHDD();
        void TakeSSD();
        void TakePSU();
        void TakeCase();
        void TakeOS();;
        void printTable();
    }

    interface GamingPC
    {
        void TakeCPU();
        void TakeMobo();
        void TakeFAN();
        void TakeGPU();
        void TakeRAM();
        void TakeHDD();
        void TakeSSD();
        void TakePSU();
        void TakeCase();
        void TakeOS();;
        void printTable();
    }

    interface EditingPC
    {
        void TakeCPU();
        void TakeMobo();
        void TakeFAN();
        void TakeGPU();
        void TakeRAM();
        void TakeHDD();
        void TakeSSD();
        void TakePSU();
        void TakeCase();
        void TakeOS();;
        void printTable();
    }

    class PCtaker implements OfficePC, GamingPC, EditingPC
    {
        double Budget = ExpressBuilder.getUserBudget();
        int Type = (int) ExpressBuilder.getUserType();
        double Tax = ExpressBuilder.getUserTax();
        String CPUname, MOBOname, FANname, RAMname, GPUname, HDDname, SSDname, PSUname, CASEname,OSname;
        double CPUcost, MOBOcost, FANcost, RAMcost, GPUcost, HDDcost, SSDcost, PSUcost, CASEcost,OScost;

        public void TakeCPU()
        {/*
            PartsList select = new PartsList();
            if (Budget >= 500 && Budget <= 600) {
                CPUname = select.CPUitems[0];
                CPUcost = select.CPUprice[0];
            } else if(Budget >= 600 && Budget < 700) {
                CPUname = select.CPUitems[1];
                CPUcost = select.CPUprice[1];
            } else if (Budget >= 700 && Budget < 800) {
                CPUname = select.CPUitems[2];
                CPUcost = select.CPUprice[2];
            } else if (Budget >= 800 && Budget < 900) {
                CPUname = select.CPUitems[3];
                CPUcost = select.CPUprice[3];
            }else if (Budget >= 900 && Budget < 1300) {
                CPUname = select.CPUitems[4];
                CPUcost = select.CPUprice[4];
            }else if (Budget >= 1300 && Budget < 2000) {
                CPUname = select.CPUitems[5];
                CPUcost = select.CPUprice[5];
            }else if (Budget >= 2000 && Budget < 2500) {
                CPUname = select.CPUitems[6];
                CPUcost = select.CPUprice[6];
            }else if (Budget >= 2500 && Budget < 3500) {
                CPUname = select.CPUitems[7];
                CPUcost = select.CPUprice[7];
            }else if (Budget >= 3500 && Budget <= 5000) {
                CPUname = select.CPUitems[8];
                CPUcost = select.CPUprice[8];
            }*/
        }

        public void TakeMobo()
        {

        }

        public void TakeFAN()
        {

        }

        public void TakeGPU()
        {

        }

        public void TakeRAM()
        {

        }

        public void TakeHDD()
        {

        }

        public void TakeSSD()
        {

        }

        public void TakePSU()
        {

        }

        public void TakeCase()
        {

        }

        public void TakeOS()
        {

        }

        public void PC_Build()
        {
            PCtaker Option = new PCtaker();
            Option.TakeCPU();
            Option.TakeMobo();
            Option.TakeFAN();
            Option.TakeRAM();
            Option.TakeGPU();
            Option.TakeHDD();
            Option.TakeSSD();
            Option.TakePSU();
            Option.TakeCase();
            Option.TakeOS();
            Option.printTable();
        }

        public void printTable()
        {
            String[] userParts = {CPUname, MOBOname, FANname, RAMname, GPUname, HDDname, SSDname, PSUname, CASEname, OSname};
            String[] columnNames = {"CPU", "Motherboard", "CPU FAN", "RAM", "Video Card", "HDD", "SSD", "PSU", "Case", "OS"};
            double[] grabPrice = {CPUcost, MOBOcost, FANcost, RAMcost, GPUcost, HDDcost, SSDcost, PSUcost, CASEcost, OScost};
            double taxedTotal = calcTotals() * Tax * 0.01;
            double finalTotal = calcTotals() + (calcTotals() * Tax * 0.01);

            System.out.printf("%-25s%-40s%27s%n", "Part", "Selection", "Price");
            System.out.println("-------------------------------------------------------------------------------------------------");
            for (int i = 0; i < columnNames.length; i++)
                System.out.printf("%-25s%-55s%13.2f%n", columnNames[i], userParts[i], grabPrice[i]);
                System.out.println();
                System.out.printf("%-25s%68.2f%n", "Base Total", calcTotals());
                System.out.printf("%-5s%-3.2f%-2s%82.2f%n", "Tax (", Tax, "%)", taxedTotal);
                System.out.printf("%-25s%68.2f%n", "Total", finalTotal);

            if (MainMenu.getMainMenu() == 3)
            {
                System.out.println("--------------------");
                System.out.println("Random Build Information");
                System.out.printf("%-10s%-1d%n", "PC Type: ", ExpressBuilder.getUserType());
                System.out.printf("%-10s%-2.2f%n", "Budget: ", ExpressBuilder.getUserBudget());
            }
            else {
                System.out.println("-------------------------------------------------------------------------------------------------");
            }
            System.out.println();

        }

        public double calcTotals()
        {
            double[] grabPrice = {CPUcost, MOBOcost, FANcost, RAMcost, GPUcost, HDDcost, SSDcost, PSUcost, CASEcost, OScost};
            double sum = 0;
            for (int i = 0; i < grabPrice.length; i++)
                sum += grabPrice[i];
            return sum;

        }
    }
}
