import java.util.Scanner;

public class Main {

    public static class Node
    {
        int data;
        double price;
        String foodname;
        int quantity;
        Node next;
        Node prev;
    }
    public static class Restaurant
    {
        Node newnode;
        Node head_cus = null;
        Node tail_cus = null;
        Node head_adm = null;
        Node tail_adm = null;
        Node head_sale;
        Scanner sc = new Scanner(System.in);
        private int id = 1190;
        private String password = "12072000@mount";

        //CUSTOMER MENU
        void customermenu()
        {
            System.out.println("\t\t\t\t\t\t\t1. Place your order");
            System.out.println("\t\t\t\t\t\t\t2. View your ordered items");
            System.out.println("\t\t\t\t\t\t\t3. Delete an item from order");
            System.out.println("\t\t\t\t\t\t\t4. Display final bill");
            System.out.println("\t\t\t\t\t\t\t5. Back To Main Menu");
            System.out.println();
            System.out.print("\t\t\t\t\t\t\tEnter Your Choice: -->");
        }


        //CREATE ITEM
        Node CreateItem(Node head,int data,int quantity)
        {
            newnode = new Node();

            Node temp1 = head_adm;
            int flag = 0;
            while(temp1 != null)
            {
                if(temp1.data == data)
                {
                    flag = 1;
                    break;
                }
                temp1 = temp1.next;
            }

            if(flag==1)
            {
                newnode.data = data;
                newnode.price = quantity*(temp1.price);
                newnode.quantity = quantity;
                newnode.foodname = temp1.foodname;
                newnode.next = null;
                newnode.prev = null;

                Node temp = head;

                if(temp==null)
                    head_cus = tail_cus = newnode;
                else
                {
                    while(temp.next != null)
                        temp=temp.next;

                    temp.next=newnode;
                    newnode.prev = tail_cus;
                    tail_cus = newnode;
                }


            }
            else
            {
                System.out.println("\t\t\t\t\t\t\tThis item is not present in the menu!");
            }
            return head_cus;
        }


        //TOTAL SALE
        Node totalsales(int data,int quantity)
        {
            newnode = new Node();
            int flag = 0;

            Node temp1 = head_adm;
            while(temp1.data != data)
            {
                temp1 = temp1.next;
            }

            newnode.data = data;
            newnode.price = quantity*(temp1.price);
            newnode.quantity = quantity;
            newnode.foodname=temp1.foodname;
            newnode.next = null;
            newnode.prev = null;

            Node temp = head_sale;

            if(temp==null)
                head_sale = newnode;
            else
            {
                while(temp.next != null)
                {
                    if(temp.data == data)
                    {
                        flag = 1;
                        break;
                    }
                    temp=temp.next;
                }

                if(flag==1)
                {
                    temp.quantity += newnode.quantity;
                    temp.price += newnode.price;
                }
                else
                {
                    temp.next = newnode;
                }
            }

            return head_sale;
        }

        // CALCULATE TOTAL SALE
        void calculatetotsales()
        {
            Node temp = head_cus;
            while(temp != null)
            {
                head_sale = totalsales(temp.data,temp.quantity);
                temp = temp.next;
            }
        }

        // DELETE ITEM
        boolean deleteItem()
        {
            System.out.print("\t\t\t\t\tEnter serial no. of the food item which is to be deleted: ");
            int num1 = sc.nextInt();
            System.out.print("\t\t\t\t\tFor reducing the quantity only type 1 and for Deleting whole item type 2: ");
            int num2 = sc.nextInt();

            Node temp=head_cus;
            if(num2 == 2)
            {
                while (temp != null)
                {
                    if (temp.data == num1)
                    {
                        head_cus = Delete(num1, head_cus, tail_cus);
                        return true;
                    }
                    temp = temp.next;
                }
            }
            else
            {
                while (temp != null)
                {
                    if (temp.data == num1 && temp.quantity > 1)
                    {
                        head_cus = Decreasequant(num1, head_cus, tail_cus);
                        return true;
                    }
                    temp = temp.next;
                }
            }

            return false;
        }

        //DISPLAY TOTAL BILL
        void displaybill()
        {
            displaylist(head_cus);
            Node temp = head_cus;
            double total_price = 0;
            while (temp!=null)
            {
                total_price += temp.price;
                temp = temp.next;
            }

            System.out.println("\t\t\t\t\t\t\tTotal price:" + total_price);

        }

        // DELETE LIST OF PARTICULAR ORDER
        Node deletelist(Node head)
        {
            if(head==null)
            {
                return null;
            }
            else
            {
                Node temp = head;
                while(temp.next != null)
                {
                    temp = temp.next;
                    temp.prev = null;
                }
                //free(temp);
                temp = null;
                head = null;
            }

            return head;
        }



        //CUSTOMER BAR
        void customer()
        {
            int flag=0,j=1;
            char ch;
            System.out.println("\t\t------------------------------------------------------------------------");
            System.out.println("\t\t\t\t\t    CUSTOMER SECTION");
            System.out.println("\t\t------------------------------------------------------------------------");
            while(true)
            {
                customermenu();

                int opt = sc.nextInt();

                if(opt==5)
                    break;

                switch (opt)
                {
                    case 1:
                        displaylist(head_adm);
                        while(true)
                        {
                            System.out.print("\t\t\t\t\t\tEnter number corresponding to the item you want to order and 0 for exit Food menu: ");
                            int n = sc.nextInt();
                            if(n == 0)
                            {
                                System.out.println("\t\t\t\t\t**Your order complete!!**");
                                break;
                            }
                            System.out.print("\t\t\t\t\t\tEnter quantity: ");
                            int quantity = sc.nextInt();
                            head_cus = CreateItem(head_cus, n, quantity);
                        }
                        break;
                    case 2:
                        System.out.println("\t\t\t\t\t\t\t  ### List of ordered items ###");
                        displaylist(head_cus);
                        break;
                    case 3:
                        if(deleteItem())
                        {
                            System.out.println("\t\t\t\t\t\t### Updated list of your ordered food items ###");
                            displaylist(head_cus);
                        }
                        else
                            System.out.println("\t\t\t\t\t\tFood item with given serial number doesn't exist!!");
                        break;
                    case 4:
                        calculatetotsales();
                        System.out.println("\t\t\t\t\t\t\t  ### Final Bill ###");
                        displaybill();
                        head_cus = deletelist(head_cus);
                        flag=1;
                        break;

                    default:
                        System.out.println("\t\t\t\t\t\tWrong Input !! PLease choose valid option");
                        break;
                }
                if(flag==1)
                    break;
            }
        }


        // CREATE  ADMIN
        Node Createadmin (Node head, int data, String foodname, double price)
        {
            newnode = new Node ();
            newnode.data = data;
            newnode.price = price;
            newnode.quantity = 0;
            newnode.foodname = foodname;
            newnode.next = null;
            newnode.prev = null;

            Node temp = head;
            if (temp == null)
            {
                head_adm = tail_adm = newnode;
            }
            else
            {
                while (temp.next != null)
                    temp = temp.next;

                temp.next = newnode;
                newnode.prev = tail_adm;
                tail_adm = newnode;
            }
            return head_adm;
        }


        //ADMIN MENU
        void Adminmenu()
        {
            System.out.println("\t\t\t1. View total sales");
            System.out.println("\t\t\t2. Add new items in the order menu");
            System.out.println("\t\t\t3. Delete items from the order menu");
            System.out.println("\t\t\t4. Display order menu");
            System.out.println("\t\t\t5. Back To MainScreen");
            System.out.println();
            System.out.print("\t\t\t\t\tEnter Your Choice: -->");
        }
        //DELETE WHOLE DATA
        Node Delete(int data,Node head,Node tail)
        {

            if(head == null)
            {
                System.out.println("\t\t\t\t\t\t\tList is empty");
            }
            else
            {
                Node temp;
                if(data == head.data)
                {
                    temp = head;
                    head = head.next;
                    if (head != null)
                        head.prev = null;
                }
                else if(data == tail.data)
                {
                    temp = tail;
                    tail = tail.prev;
                    tail.next = null;
                }
                else
                {
                    temp = head;
                    while(data != temp.data)
                    {
                        temp = temp.next;
                    }
                    temp.prev.next = temp.next;
                    temp.next.prev = temp.prev;
                }
            }

            return head;

        }

        // DECREASING QUNATITY OF ITEM
        Node Decreasequant(int data, Node head, Node tail)
        {
            if(head == null)
            {
                System.out.println("\t\t\t\t\t\t\tList is empty");
            }
            else
            {
                Node temp;
                if(data == head.data)
                {
                    temp = head;
                    head.quantity = head.quantity - 1;
                    if (head != null) {
                        head.prev = null;
                    }
                }
                else if(data == tail.data)
                {
                    temp = tail;
                    tail.quantity = tail.quantity - 1;
                }
                else
                {
                    temp = head;
                    while(data != temp.data)
                    {
                        temp = temp.next;
                    }
                    temp.quantity = temp.quantity - 1;
                }
            }


            return head;
        }

        // FUNCTION TO DELETE ITEMS
        boolean Deleteadmin()
        {
            System.out.print("\t\t\t\t\tEnter serial no. of the food item which is to be deleted: ");
            int num = sc.nextInt();

            Node temp=head_adm;
            while(temp != null)
            {
                if (temp.data == num)
                {
                    head_adm = Delete(num, head_adm, tail_adm);
                    return true;
                }
                temp=temp.next;
            }

            return false;
        }
        // TO DISPLAY TOTAL SALE IN DAY
        void displaylist(Node head)
        {
            Node temp1 = head;
            if(temp1 == null)
            {
                System.out.println("\t\t\t\t\t\t\t\tList is empty!!");
            }
            else
            {
                //printf("\n");
                while(temp1 != null)
                {
                    if(temp1.quantity == 0)
                        System.out.println("\t\t\t\t\t\t" + temp1.data + "." + " " + temp1.foodname + " " + temp1.price);
                    else
                    {
                        System.out.println("\t\t\t\t\t\t" + temp1.data + "." + " " + temp1.foodname + " " + temp1.quantity + " " + temp1.price);
                    }

                    temp1 = temp1.next;
                }
            }

        }
        // ADMIN BAR
        void Admin()
        {
            System.out.println();
            System.out.println("\t\t-------------------------------------------------------------------------");
            System.out.println("\t\t\t\t\tADMIN SECTION");
            System.out.println("\t\t-------------------------------------------------------------------------");
            while(true)
            {
                Adminmenu(); //prints admin functionality list
                int option = sc.nextInt();
                if(option == 5)
                {
                    break;
                }

                switch(option)
                {
                    case 1:
                        displaylist(head_sale);
                        break;

                    case 2:
                        System.out.print("\t\t\t\t\t\t\tEnter serial no. of the food item:");
                        int num = sc.nextInt();
                        int flag = 0;
                        String foodname;
                        double price;
                        Node temp = head_adm;

                        while(temp != null)
                        {
                            if(temp.data == num)
                            {
                                System.out.println("\t\t\t\t\t\tFood item with given serial number already exists!!");
                                flag = 1;
                                break;
                            }
                            temp = temp.next;
                        }

                        if(flag == 1)
                            break;

                        System.out.print("\t\t\t\t\t\t\tEnter price: ");
                        price = sc.nextDouble();
                        System.out.print("\t\t\t\t\t\t\tEnter food-item: ");
                        foodname = sc.next();
                        head_adm = Createadmin(head_adm, num, foodname, price);
                        System.out.println("\t\t\t\t\t\t\tSuccessfull! New food item added to the list!!");
                        sc.nextLine();
                        break;

                    case 3:
                        if(Deleteadmin())
                        {
                            System.out.println("\t\t\t\t\t\t### Updated list of food items menu ###");
                            displaylist(head_adm);
                        }
                        else
                            System.out.println("\t\t\t\t\t\tFood item with given serial number doesn't exist!");
                        break;

                    case 4:
                        System.out.println("\t\t\t\t\t\t\t ### Order menu ###");
                        displaylist(head_adm);
                        break;

                    default:
                        System.out.println("\t\t\t\t\t\tWrong Input !! PLease choose valid option");
                        break;
                }

            }
        }

        // MAIN DISPLAY
        void MainScreen()
        {
            System.out.println("\t\t-------------------------------------------------------------------------");
            System.out.println("\t\t\t\t\tWELCOME TO HOTEL MOUNTAINIUM");
            System.out.println("\t\t-------------------------------------------------------------------------");
            System.out.println("\t\t\t\t1.ADMIN SECTION");
            System.out.println("\t\t\t\t2.CUSTOMER SECTION");
            System.out.println("\t\t\t\t3.EXIT");
            System.out.println();
            System.out.print("\t\tEnter Your Choice: -->");

        }
    }


    // MAIN FUNCTION
    public static void main (String[]args)
    {
        Scanner sc = new Scanner(System.in);
        Restaurant re = new Restaurant();
        re.head_adm = re.Createadmin(re.head_adm, 1, "Noodles", 120);
        re.head_adm = re.Createadmin(re.head_adm, 2, "Manchurian", 100);
        re.head_adm = re.Createadmin(re.head_adm, 3, "hot n sour Soup", 50);
        re.head_adm = re.Createadmin(re.head_adm, 4, "Cheese Burger", 60);
        re.head_adm = re.Createadmin(re.head_adm, 5, "Spring rolls", 80);
        boolean flag = true;
        //int id = 1190;
        //String password = "12072000@mount";
        while(true)
        {
            re.MainScreen();
            int choice = sc.nextInt();

            switch(choice)
            {
                case 1:
                    System.out.print("\t\t\t\t\tEnter the id please:");
                    int i_d = sc.nextInt();
                    System.out.print("\t\t\t\t\tEnter the password please:");
                    String pass = sc.next();
                    if(i_d == re.id && pass.equals(re.password))
                    {
                        System.out.println("\t\t\t\t\t****Welcome to Admin section****");
                        re.Admin();
                    }
                    else
                    {
                        System.out.println("\t\t\t\t\t****UNAUTHORIZED ACCESS****");
                    }
                    break;
                case 2:
                    re.customer();
                    break;
                case 3:
                    System.out.println("\t\t\t\t\t\t********Thank you for visiting!!**********");
                    flag = false;
                    break;

                default:
                    System.out.println("\t\t\t\t\t\tWrong Input !! Please choose valid option");
                    break;
            }
            if(flag == false)
                break;


        }
    }

}
